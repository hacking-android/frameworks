/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.Loader;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.OperationCanceledException;
import android.os.SystemClock;
import android.util.TimeUtils;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

@Deprecated
public abstract class AsyncTaskLoader<D>
extends Loader<D> {
    static final boolean DEBUG = false;
    static final String TAG = "AsyncTaskLoader";
    volatile AsyncTaskLoader<D> mCancellingTask;
    @UnsupportedAppUsage
    private final Executor mExecutor;
    Handler mHandler;
    long mLastLoadCompleteTime = -10000L;
    volatile AsyncTaskLoader<D> mTask;
    long mUpdateThrottle;

    public AsyncTaskLoader(Context context) {
        this(context, AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public AsyncTaskLoader(Context context, Executor executor) {
        super(context);
        this.mExecutor = executor;
    }

    public void cancelLoadInBackground() {
    }

    void dispatchOnCancelled(AsyncTaskLoader<D> asyncTaskLoader, D d) {
        this.onCanceled(d);
        if (this.mCancellingTask == asyncTaskLoader) {
            this.rollbackContentChanged();
            this.mLastLoadCompleteTime = SystemClock.uptimeMillis();
            this.mCancellingTask = null;
            this.deliverCancellation();
            this.executePendingTask();
        }
    }

    void dispatchOnLoadComplete(AsyncTaskLoader<D> asyncTaskLoader, D d) {
        if (this.mTask != asyncTaskLoader) {
            this.dispatchOnCancelled(asyncTaskLoader, d);
        } else if (this.isAbandoned()) {
            this.onCanceled(d);
        } else {
            this.commitContentChanged();
            this.mLastLoadCompleteTime = SystemClock.uptimeMillis();
            this.mTask = null;
            this.deliverResult(d);
        }
    }

    @Override
    public void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        super.dump(string2, fileDescriptor, printWriter, arrstring);
        if (this.mTask != null) {
            printWriter.print(string2);
            printWriter.print("mTask=");
            printWriter.print(this.mTask);
            printWriter.print(" waiting=");
            printWriter.println(((LoadTask)this.mTask).waiting);
        }
        if (this.mCancellingTask != null) {
            printWriter.print(string2);
            printWriter.print("mCancellingTask=");
            printWriter.print(this.mCancellingTask);
            printWriter.print(" waiting=");
            printWriter.println(((LoadTask)this.mCancellingTask).waiting);
        }
        if (this.mUpdateThrottle != 0L) {
            printWriter.print(string2);
            printWriter.print("mUpdateThrottle=");
            TimeUtils.formatDuration(this.mUpdateThrottle, printWriter);
            printWriter.print(" mLastLoadCompleteTime=");
            TimeUtils.formatDuration(this.mLastLoadCompleteTime, SystemClock.uptimeMillis(), printWriter);
            printWriter.println();
        }
    }

    void executePendingTask() {
        if (this.mCancellingTask == null && this.mTask != null) {
            if (((LoadTask)this.mTask).waiting) {
                ((LoadTask)this.mTask).waiting = false;
                this.mHandler.removeCallbacks((Runnable)((Object)this.mTask));
            }
            if (this.mUpdateThrottle > 0L && SystemClock.uptimeMillis() < this.mLastLoadCompleteTime + this.mUpdateThrottle) {
                ((LoadTask)this.mTask).waiting = true;
                this.mHandler.postAtTime((Runnable)((Object)this.mTask), this.mLastLoadCompleteTime + this.mUpdateThrottle);
                return;
            }
            ((AsyncTask)((Object)this.mTask)).executeOnExecutor(this.mExecutor, null);
        }
    }

    public boolean isLoadInBackgroundCanceled() {
        boolean bl = this.mCancellingTask != null;
        return bl;
    }

    public abstract D loadInBackground();

    @Override
    protected boolean onCancelLoad() {
        if (this.mTask != null) {
            if (!this.mStarted) {
                this.mContentChanged = true;
            }
            if (this.mCancellingTask != null) {
                if (((LoadTask)this.mTask).waiting) {
                    ((LoadTask)this.mTask).waiting = false;
                    this.mHandler.removeCallbacks((Runnable)((Object)this.mTask));
                }
                this.mTask = null;
                return false;
            }
            if (((LoadTask)this.mTask).waiting) {
                ((LoadTask)this.mTask).waiting = false;
                this.mHandler.removeCallbacks((Runnable)((Object)this.mTask));
                this.mTask = null;
                return false;
            }
            boolean bl = ((AsyncTask)((Object)this.mTask)).cancel(false);
            if (bl) {
                this.mCancellingTask = this.mTask;
                this.cancelLoadInBackground();
            }
            this.mTask = null;
            return bl;
        }
        return false;
    }

    public void onCanceled(D d) {
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
        this.cancelLoad();
        this.mTask = new LoadTask();
        this.executePendingTask();
    }

    protected D onLoadInBackground() {
        return this.loadInBackground();
    }

    public void setUpdateThrottle(long l) {
        this.mUpdateThrottle = l;
        if (l != 0L) {
            this.mHandler = new Handler();
        }
    }

    @UnsupportedAppUsage
    public void waitForLoader() {
        AsyncTaskLoader<D> asyncTaskLoader = this.mTask;
        if (asyncTaskLoader != null) {
            ((LoadTask)((Object)asyncTaskLoader)).waitForLoader();
        }
    }

    final class LoadTask
    extends AsyncTask<Void, Void, D>
    implements Runnable {
        private final CountDownLatch mDone = new CountDownLatch(1);
        boolean waiting;

        LoadTask() {
        }

        protected D doInBackground(Void ... object) {
            try {
                object = AsyncTaskLoader.this.onLoadInBackground();
            }
            catch (OperationCanceledException operationCanceledException) {
                if (this.isCancelled()) {
                    return null;
                }
                throw operationCanceledException;
            }
            return (D)object;
        }

        @Override
        protected void onCancelled(D d) {
            try {
                AsyncTaskLoader.this.dispatchOnCancelled(this, d);
                return;
            }
            finally {
                this.mDone.countDown();
            }
        }

        @Override
        protected void onPostExecute(D d) {
            try {
                AsyncTaskLoader.this.dispatchOnLoadComplete(this, d);
                return;
            }
            finally {
                this.mDone.countDown();
            }
        }

        @Override
        public void run() {
            this.waiting = false;
            AsyncTaskLoader.this.executePendingTask();
        }

        public void waitForLoader() {
            try {
                this.mDone.await();
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
    }

}

