/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import java.util.ArrayDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AsyncTask<Params, Progress, Result> {
    private static final int BACKUP_POOL_SIZE = 5;
    private static final int CORE_POOL_SIZE = 1;
    private static final int KEEP_ALIVE_SECONDS = 3;
    private static final String LOG_TAG = "AsyncTask";
    private static final int MAXIMUM_POOL_SIZE = 20;
    private static final int MESSAGE_POST_PROGRESS = 2;
    private static final int MESSAGE_POST_RESULT = 1;
    public static final Executor SERIAL_EXECUTOR;
    public static final Executor THREAD_POOL_EXECUTOR;
    private static ThreadPoolExecutor sBackupExecutor;
    private static LinkedBlockingQueue<Runnable> sBackupExecutorQueue;
    @UnsupportedAppUsage
    private static volatile Executor sDefaultExecutor;
    private static InternalHandler sHandler;
    private static final RejectedExecutionHandler sRunOnSerialPolicy;
    private static final ThreadFactory sThreadFactory;
    private final AtomicBoolean mCancelled;
    @UnsupportedAppUsage
    private final FutureTask<Result> mFuture;
    private final Handler mHandler;
    @UnsupportedAppUsage
    private volatile Status mStatus;
    @UnsupportedAppUsage
    private final AtomicBoolean mTaskInvoked;
    @UnsupportedAppUsage
    private final WorkerRunnable<Params, Result> mWorker;

    static {
        sThreadFactory = new ThreadFactory(){
            private final AtomicInteger mCount = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable runnable) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("AsyncTask #");
                stringBuilder.append(this.mCount.getAndIncrement());
                return new Thread(runnable, stringBuilder.toString());
            }
        };
        sRunOnSerialPolicy = new RejectedExecutionHandler(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void rejectedExecution(Runnable runnable, ThreadPoolExecutor object) {
                Log.w(AsyncTask.LOG_TAG, "Exceeded ThreadPoolExecutor pool size");
                synchronized (this) {
                    if (sBackupExecutor == null) {
                        object = new LinkedBlockingQueue();
                        sBackupExecutorQueue = object;
                        object = new ThreadPoolExecutor(5, 5, 3L, TimeUnit.SECONDS, (BlockingQueue<Runnable>)sBackupExecutorQueue, sThreadFactory);
                        sBackupExecutor = (ThreadPoolExecutor)object;
                        sBackupExecutor.allowCoreThreadTimeOut(true);
                    }
                }
                sBackupExecutor.execute(runnable);
            }
        };
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 20, 3L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), sThreadFactory);
        threadPoolExecutor.setRejectedExecutionHandler(sRunOnSerialPolicy);
        THREAD_POOL_EXECUTOR = threadPoolExecutor;
        sDefaultExecutor = SERIAL_EXECUTOR = new SerialExecutor();
    }

    public AsyncTask() {
        this((Looper)null);
    }

    public AsyncTask(Handler object) {
        object = object != null ? ((Handler)object).getLooper() : null;
        this((Looper)object);
    }

    public AsyncTask(Looper object) {
        this.mStatus = Status.PENDING;
        this.mCancelled = new AtomicBoolean();
        this.mTaskInvoked = new AtomicBoolean();
        object = object != null && object != Looper.getMainLooper() ? new Handler((Looper)object) : AsyncTask.getMainHandler();
        this.mHandler = object;
        this.mWorker = new WorkerRunnable<Params, Result>(){

            @Override
            public Result call() throws Exception {
                Result Result2;
                AsyncTask.this.mTaskInvoked.set(true);
                Result Result3 = Result2 = null;
                try {
                    Process.setThreadPriority(10);
                    Result3 = Result2;
                }
                catch (Throwable throwable) {
                    try {
                        AsyncTask.this.mCancelled.set(true);
                        throw throwable;
                    }
                    catch (Throwable throwable2) {
                        AsyncTask.this.postResult(Result3);
                        throw throwable2;
                    }
                }
                Result3 = Result2 = (Result)AsyncTask.this.doInBackground(this.mParams);
                Binder.flushPendingCommands();
                AsyncTask.this.postResult(Result2);
                return Result2;
            }
        };
        this.mFuture = new FutureTask<Result>(this.mWorker){

            @Override
            protected void done() {
                try {
                    AsyncTask.this.postResultIfNotInvoked(this.get());
                }
                catch (CancellationException cancellationException) {
                    AsyncTask.this.postResultIfNotInvoked(null);
                }
                catch (ExecutionException executionException) {
                    throw new RuntimeException("An error occurred while executing doInBackground()", executionException.getCause());
                }
                catch (InterruptedException interruptedException) {
                    Log.w(AsyncTask.LOG_TAG, interruptedException);
                }
            }
        };
    }

    public static void execute(Runnable runnable) {
        sDefaultExecutor.execute(runnable);
    }

    private void finish(Result Result2) {
        if (this.isCancelled()) {
            this.onCancelled(Result2);
        } else {
            this.onPostExecute(Result2);
        }
        this.mStatus = Status.FINISHED;
    }

    private Handler getHandler() {
        return this.mHandler;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static Handler getMainHandler() {
        synchronized (AsyncTask.class) {
            InternalHandler internalHandler;
            if (sHandler != null) return sHandler;
            sHandler = internalHandler = new InternalHandler(Looper.getMainLooper());
            return sHandler;
        }
    }

    private Result postResult(Result Result2) {
        this.getHandler().obtainMessage(1, new AsyncTaskResult<Object>(this, Result2)).sendToTarget();
        return Result2;
    }

    private void postResultIfNotInvoked(Result Result2) {
        if (!this.mTaskInvoked.get()) {
            this.postResult(Result2);
        }
    }

    @UnsupportedAppUsage
    public static void setDefaultExecutor(Executor executor) {
        sDefaultExecutor = executor;
    }

    public final boolean cancel(boolean bl) {
        this.mCancelled.set(true);
        return this.mFuture.cancel(bl);
    }

    protected abstract Result doInBackground(Params ... var1);

    public final AsyncTask<Params, Progress, Result> execute(Params ... arrParams) {
        return this.executeOnExecutor(sDefaultExecutor, arrParams);
    }

    public final AsyncTask<Params, Progress, Result> executeOnExecutor(Executor executor, Params ... arrParams) {
        if (this.mStatus != Status.PENDING) {
            int n = 5.$SwitchMap$android$os$AsyncTask$Status[this.mStatus.ordinal()];
            if (n != 1) {
                if (n == 2) {
                    throw new IllegalStateException("Cannot execute task: the task has already been executed (a task can be executed only once)");
                }
            } else {
                throw new IllegalStateException("Cannot execute task: the task is already running.");
            }
        }
        this.mStatus = Status.RUNNING;
        this.onPreExecute();
        this.mWorker.mParams = arrParams;
        executor.execute(this.mFuture);
        return this;
    }

    public final Result get() throws InterruptedException, ExecutionException {
        return this.mFuture.get();
    }

    public final Result get(long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.mFuture.get(l, timeUnit);
    }

    public final Status getStatus() {
        return this.mStatus;
    }

    public final boolean isCancelled() {
        return this.mCancelled.get();
    }

    protected void onCancelled() {
    }

    protected void onCancelled(Result Result2) {
        this.onCancelled();
    }

    protected void onPostExecute(Result Result2) {
    }

    protected void onPreExecute() {
    }

    protected void onProgressUpdate(Progress ... arrProgress) {
    }

    protected final void publishProgress(Progress ... arrProgress) {
        if (!this.isCancelled()) {
            this.getHandler().obtainMessage(2, new AsyncTaskResult<Progress>(this, arrProgress)).sendToTarget();
        }
    }

    private static class AsyncTaskResult<Data> {
        final Data[] mData;
        final AsyncTask mTask;

        AsyncTaskResult(AsyncTask asyncTask, Data ... arrData) {
            this.mTask = asyncTask;
            this.mData = arrData;
        }
    }

    private static class InternalHandler
    extends Handler {
        public InternalHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message message) {
            AsyncTaskResult asyncTaskResult = (AsyncTaskResult)message.obj;
            int n = message.what;
            if (n != 1) {
                if (n == 2) {
                    asyncTaskResult.mTask.onProgressUpdate(asyncTaskResult.mData);
                }
            } else {
                asyncTaskResult.mTask.finish(asyncTaskResult.mData[0]);
            }
        }
    }

    private static class SerialExecutor
    implements Executor {
        Runnable mActive;
        final ArrayDeque<Runnable> mTasks = new ArrayDeque();

        private SerialExecutor() {
        }

        @Override
        public void execute(final Runnable runnable) {
            synchronized (this) {
                ArrayDeque<Runnable> arrayDeque = this.mTasks;
                Runnable runnable2 = new Runnable(){

                    @Override
                    public void run() {
                        try {
                            runnable.run();
                            return;
                        }
                        finally {
                            SerialExecutor.this.scheduleNext();
                        }
                    }
                };
                arrayDeque.offer(runnable2);
                if (this.mActive == null) {
                    this.scheduleNext();
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        protected void scheduleNext() {
            synchronized (this) {
                Runnable runnable;
                this.mActive = runnable = this.mTasks.poll();
                if (runnable != null) {
                    THREAD_POOL_EXECUTOR.execute(this.mActive);
                }
                return;
            }
        }

    }

    public static enum Status {
        PENDING,
        RUNNING,
        FINISHED;
        
    }

    private static abstract class WorkerRunnable<Params, Result>
    implements Callable<Result> {
        Params[] mParams;

        private WorkerRunnable() {
        }
    }

}

