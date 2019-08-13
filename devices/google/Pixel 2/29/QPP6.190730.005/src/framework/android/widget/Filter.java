/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public abstract class Filter {
    private static final int FILTER_TOKEN = -791613427;
    private static final int FINISH_TOKEN = -559038737;
    private static final String LOG_TAG = "Filter";
    private static final String THREAD_NAME = "Filter";
    private Delayer mDelayer;
    private final Object mLock = new Object();
    private Handler mResultHandler = new ResultsHandler();
    private Handler mThreadHandler;

    static /* synthetic */ Handler access$200(Filter filter) {
        return filter.mResultHandler;
    }

    static /* synthetic */ Object access$300(Filter filter) {
        return filter.mLock;
    }

    static /* synthetic */ Handler access$400(Filter filter) {
        return filter.mThreadHandler;
    }

    static /* synthetic */ Handler access$402(Filter filter, Handler handler) {
        filter.mThreadHandler = handler;
        return handler;
    }

    public CharSequence convertResultToString(Object object) {
        object = object == null ? "" : object.toString();
        return object;
    }

    public final void filter(CharSequence charSequence) {
        this.filter(charSequence, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void filter(CharSequence charSequence, FilterListener filterListener) {
        Object object = this.mLock;
        synchronized (object) {
            Object object2;
            Object object3;
            if (this.mThreadHandler == null) {
                object2 = new HandlerThread("Filter", 10);
                ((Thread)object2).start();
                this.mThreadHandler = object3 = new RequestHandler(((HandlerThread)object2).getLooper());
            }
            long l = this.mDelayer == null ? 0L : this.mDelayer.getPostingDelay(charSequence);
            object3 = this.mThreadHandler.obtainMessage(-791613427);
            object2 = null;
            RequestArguments requestArguments = new RequestArguments();
            if (charSequence != null) {
                object2 = charSequence.toString();
            }
            requestArguments.constraint = object2;
            requestArguments.listener = filterListener;
            ((Message)object3).obj = requestArguments;
            this.mThreadHandler.removeMessages(-791613427);
            this.mThreadHandler.removeMessages(-559038737);
            this.mThreadHandler.sendMessageDelayed((Message)object3, l);
            return;
        }
    }

    protected abstract FilterResults performFiltering(CharSequence var1);

    protected abstract void publishResults(CharSequence var1, FilterResults var2);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void setDelayer(Delayer delayer) {
        Object object = this.mLock;
        synchronized (object) {
            this.mDelayer = delayer;
            return;
        }
    }

    public static interface Delayer {
        public long getPostingDelay(CharSequence var1);
    }

    public static interface FilterListener {
        public void onFilterComplete(int var1);
    }

    protected static class FilterResults {
        public int count;
        public Object values;
    }

    private static class RequestArguments {
        CharSequence constraint;
        FilterListener listener;
        FilterResults results;

        private RequestArguments() {
        }
    }

    private class RequestHandler
    extends Handler {
        public RequestHandler(Looper looper) {
            super(looper);
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void handleMessage(Message var1_1) {
            block11 : {
                var2_2 = var1_1.what;
                if (var2_2 != -791613427) {
                    if (var2_2 != -559038737) {
                        return;
                    }
                    var1_1 = Filter.access$300(Filter.this);
                    synchronized (var1_1) {
                        if (Filter.access$400(Filter.this) == null) return;
                        Filter.access$400(Filter.this).getLooper().quit();
                        Filter.access$402(Filter.this, null);
                        return;
                    }
                }
                var1_1 = (RequestArguments)var1_1.obj;
                var1_1.results = Filter.this.performFiltering(var1_1.constraint);
lbl15: // 2 sources:
                do {
                    var3_3 = Filter.access$200(Filter.this).obtainMessage(var2_2);
                    var3_3.obj = var1_1;
                    var3_3.sendToTarget();
                    break block11;
                    break;
                } while (true);
                {
                    catch (Throwable var4_5) {
                    }
                    catch (Exception var4_6) {}
                    {
                        var3_3 = new FilterResults();
                        var1_1.results = var3_3;
                        Log.w("Filter", "An exception occured during performFiltering()!", var4_6);
                        ** continue;
                    }
                }
                var3_4 = Filter.access$200(Filter.this).obtainMessage(var2_2);
                var3_4.obj = var1_1;
                var3_4.sendToTarget();
                throw var4_5;
            }
            var1_1 = Filter.access$300(Filter.this);
            synchronized (var1_1) {
                if (Filter.access$400(Filter.this) == null) return;
                var3_3 = Filter.access$400(Filter.this).obtainMessage(-559038737);
                Filter.access$400(Filter.this).sendMessageDelayed((Message)var3_3, 3000L);
                return;
            }
        }
    }

    private class ResultsHandler
    extends Handler {
        private ResultsHandler() {
        }

        @Override
        public void handleMessage(Message object) {
            object = (RequestArguments)((Message)object).obj;
            Filter.this.publishResults(((RequestArguments)object).constraint, ((RequestArguments)object).results);
            if (((RequestArguments)object).listener != null) {
                int n = ((RequestArguments)object).results != null ? object.results.count : -1;
                ((RequestArguments)object).listener.onFilterComplete(n);
            }
        }
    }

}

