/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.lang.ref.WeakReference;

public abstract class AsyncQueryHandler
extends Handler {
    private static final int EVENT_ARG_DELETE = 4;
    private static final int EVENT_ARG_INSERT = 2;
    private static final int EVENT_ARG_QUERY = 1;
    private static final int EVENT_ARG_UPDATE = 3;
    private static final String TAG = "AsyncQuery";
    private static final boolean localLOGV = false;
    private static Looper sLooper = null;
    final WeakReference<ContentResolver> mResolver;
    private Handler mWorkerThreadHandler;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public AsyncQueryHandler(ContentResolver object) {
        this.mResolver = new WeakReference<ContentResolver>((ContentResolver)object);
        synchronized (AsyncQueryHandler.class) {
            if (sLooper == null) {
                object = new HandlerThread("AsyncQueryWorker");
                ((Thread)object).start();
                sLooper = ((HandlerThread)object).getLooper();
            }
        }
        this.mWorkerThreadHandler = this.createHandler(sLooper);
    }

    public final void cancelOperation(int n) {
        this.mWorkerThreadHandler.removeMessages(n);
    }

    protected Handler createHandler(Looper looper) {
        return new WorkerHandler(looper);
    }

    @Override
    public void handleMessage(Message message) {
        WorkerArgs workerArgs = (WorkerArgs)message.obj;
        int n = message.what;
        int n2 = message.arg1;
        if (n2 != 1) {
            if (n2 != 2) {
                if (n2 != 3) {
                    if (n2 == 4) {
                        this.onDeleteComplete(n, workerArgs.cookie, (Integer)workerArgs.result);
                    }
                } else {
                    this.onUpdateComplete(n, workerArgs.cookie, (Integer)workerArgs.result);
                }
            } else {
                this.onInsertComplete(n, workerArgs.cookie, (Uri)workerArgs.result);
            }
        } else {
            this.onQueryComplete(n, workerArgs.cookie, (Cursor)workerArgs.result);
        }
    }

    protected void onDeleteComplete(int n, Object object, int n2) {
    }

    protected void onInsertComplete(int n, Object object, Uri uri) {
    }

    protected void onQueryComplete(int n, Object object, Cursor cursor) {
    }

    protected void onUpdateComplete(int n, Object object, int n2) {
    }

    public final void startDelete(int n, Object object, Uri uri, String string2, String[] arrstring) {
        Message message = this.mWorkerThreadHandler.obtainMessage(n);
        message.arg1 = 4;
        WorkerArgs workerArgs = new WorkerArgs();
        workerArgs.handler = this;
        workerArgs.uri = uri;
        workerArgs.cookie = object;
        workerArgs.selection = string2;
        workerArgs.selectionArgs = arrstring;
        message.obj = workerArgs;
        this.mWorkerThreadHandler.sendMessage(message);
    }

    public final void startInsert(int n, Object object, Uri uri, ContentValues contentValues) {
        Message message = this.mWorkerThreadHandler.obtainMessage(n);
        message.arg1 = 2;
        WorkerArgs workerArgs = new WorkerArgs();
        workerArgs.handler = this;
        workerArgs.uri = uri;
        workerArgs.cookie = object;
        workerArgs.values = contentValues;
        message.obj = workerArgs;
        this.mWorkerThreadHandler.sendMessage(message);
    }

    public void startQuery(int n, Object object, Uri uri, String[] arrstring, String string2, String[] arrstring2, String string3) {
        Message message = this.mWorkerThreadHandler.obtainMessage(n);
        message.arg1 = 1;
        WorkerArgs workerArgs = new WorkerArgs();
        workerArgs.handler = this;
        workerArgs.uri = uri;
        workerArgs.projection = arrstring;
        workerArgs.selection = string2;
        workerArgs.selectionArgs = arrstring2;
        workerArgs.orderBy = string3;
        workerArgs.cookie = object;
        message.obj = workerArgs;
        this.mWorkerThreadHandler.sendMessage(message);
    }

    public final void startUpdate(int n, Object object, Uri uri, ContentValues contentValues, String string2, String[] arrstring) {
        Message message = this.mWorkerThreadHandler.obtainMessage(n);
        message.arg1 = 3;
        WorkerArgs workerArgs = new WorkerArgs();
        workerArgs.handler = this;
        workerArgs.uri = uri;
        workerArgs.cookie = object;
        workerArgs.values = contentValues;
        workerArgs.selection = string2;
        workerArgs.selectionArgs = arrstring;
        message.obj = workerArgs;
        this.mWorkerThreadHandler.sendMessage(message);
    }

    protected static final class WorkerArgs {
        public Object cookie;
        public Handler handler;
        public String orderBy;
        public String[] projection;
        public Object result;
        public String selection;
        public String[] selectionArgs;
        public Uri uri;
        public ContentValues values;

        protected WorkerArgs() {
        }
    }

    protected class WorkerHandler
    extends Handler {
        public WorkerHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message message) {
            Object object = (ContentResolver)AsyncQueryHandler.this.mResolver.get();
            if (object == null) {
                return;
            }
            WorkerArgs workerArgs = (WorkerArgs)message.obj;
            int n = message.what;
            int n2 = message.arg1;
            if (n2 != 1) {
                if (n2 != 2) {
                    if (n2 != 3) {
                        if (n2 == 4) {
                            workerArgs.result = ((ContentResolver)object).delete(workerArgs.uri, workerArgs.selection, workerArgs.selectionArgs);
                        }
                    } else {
                        workerArgs.result = ((ContentResolver)object).update(workerArgs.uri, workerArgs.values, workerArgs.selection, workerArgs.selectionArgs);
                    }
                } else {
                    workerArgs.result = ((ContentResolver)object).insert(workerArgs.uri, workerArgs.values);
                }
            } else {
                block11 : {
                    object = ((ContentResolver)object).query(workerArgs.uri, workerArgs.projection, workerArgs.selection, workerArgs.selectionArgs, workerArgs.orderBy);
                    if (object == null) break block11;
                    try {
                        object.getCount();
                    }
                    catch (Exception exception) {
                        Log.w(AsyncQueryHandler.TAG, "Exception thrown during handling EVENT_ARG_QUERY", exception);
                        object = null;
                    }
                }
                workerArgs.result = object;
            }
            object = workerArgs.handler.obtainMessage(n);
            ((Message)object).obj = workerArgs;
            ((Message)object).arg1 = message.arg1;
            ((Message)object).sendToTarget();
        }
    }

}

