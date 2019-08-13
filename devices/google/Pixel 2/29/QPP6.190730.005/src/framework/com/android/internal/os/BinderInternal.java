/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.VMRuntime
 */
package com.android.internal.os;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.EventLog;
import android.util.SparseIntArray;
import com.android.internal.util.Preconditions;
import dalvik.system.VMRuntime;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class BinderInternal {
    private static final String TAG = "BinderInternal";
    static final BinderProxyLimitListenerDelegate sBinderProxyLimitListenerDelegate;
    static WeakReference<GcWatcher> sGcWatcher;
    static ArrayList<Runnable> sGcWatchers;
    static long sLastGcTime;
    static Runnable[] sTmpWatchers;

    static {
        sGcWatcher = new WeakReference<GcWatcher>(new GcWatcher());
        sGcWatchers = new ArrayList();
        sTmpWatchers = new Runnable[1];
        sBinderProxyLimitListenerDelegate = new BinderProxyLimitListenerDelegate();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void addGcWatcher(Runnable runnable) {
        ArrayList<Runnable> arrayList = sGcWatchers;
        synchronized (arrayList) {
            sGcWatchers.add(runnable);
            return;
        }
    }

    public static void binderProxyLimitCallbackFromNative(int n) {
        sBinderProxyLimitListenerDelegate.notifyClient(n);
    }

    public static void clearBinderProxyCountCallback() {
        sBinderProxyLimitListenerDelegate.setListener(null, null);
    }

    public static final native void disableBackgroundScheduling(boolean var0);

    static void forceBinderGc() {
        BinderInternal.forceGc("Binder");
    }

    public static void forceGc(String string2) {
        EventLog.writeEvent(2741, string2);
        VMRuntime.getRuntime().requestConcurrentGC();
    }

    @UnsupportedAppUsage
    public static final native IBinder getContextObject();

    public static long getLastGcTime() {
        return sLastGcTime;
    }

    @UnsupportedAppUsage
    static final native void handleGc();

    public static final native void joinThreadPool();

    public static final native int nGetBinderProxyCount(int var0);

    public static final native SparseIntArray nGetBinderProxyPerUidCounts();

    public static final native void nSetBinderProxyCountEnabled(boolean var0);

    public static final native void nSetBinderProxyCountWatermarks(int var0, int var1);

    public static void setBinderProxyCountCallback(BinderProxyLimitListener binderProxyLimitListener, Handler handler) {
        Preconditions.checkNotNull(handler, "Must provide NonNull Handler to setBinderProxyCountCallback when setting BinderProxyLimitListener");
        sBinderProxyLimitListenerDelegate.setListener(binderProxyLimitListener, handler);
    }

    public static final native void setMaxThreads(int var0);

    public static interface BinderProxyLimitListener {
        public void onLimitReached(int var1);
    }

    private static class BinderProxyLimitListenerDelegate {
        private BinderProxyLimitListener mBinderProxyLimitListener;
        private Handler mHandler;

        private BinderProxyLimitListenerDelegate() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void notifyClient(final int n) {
            synchronized (this) {
                if (this.mBinderProxyLimitListener != null) {
                    Handler handler = this.mHandler;
                    Runnable runnable = new Runnable(){

                        @Override
                        public void run() {
                            BinderProxyLimitListenerDelegate.this.mBinderProxyLimitListener.onLimitReached(n);
                        }
                    };
                    handler.post(runnable);
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void setListener(BinderProxyLimitListener binderProxyLimitListener, Handler handler) {
            synchronized (this) {
                this.mBinderProxyLimitListener = binderProxyLimitListener;
                this.mHandler = handler;
                return;
            }
        }

    }

    public static class CallSession {
        public Class<? extends Binder> binderClass;
        long cpuTimeStarted;
        boolean exceptionThrown;
        long timeStarted;
        public int transactionCode;
    }

    static final class GcWatcher {
        GcWatcher() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        protected void finalize() throws Throwable {
            BinderInternal.handleGc();
            sLastGcTime = SystemClock.uptimeMillis();
            ArrayList<Runnable> arrayList = sGcWatchers;
            synchronized (arrayList) {
                sTmpWatchers = sGcWatchers.toArray(sTmpWatchers);
            }
            int n = 0;
            do {
                if (n >= sTmpWatchers.length) {
                    sGcWatcher = new WeakReference<GcWatcher>(new GcWatcher());
                    return;
                }
                if (sTmpWatchers[n] != null) {
                    sTmpWatchers[n].run();
                }
                ++n;
            } while (true);
        }
    }

    public static interface Observer {
        public void callEnded(CallSession var1, int var2, int var3, int var4);

        public CallSession callStarted(Binder var1, int var2, int var3);

        public void callThrewException(CallSession var1, Exception var2);
    }

    @FunctionalInterface
    public static interface WorkSourceProvider {
        public int resolveWorkSourceUid(int var1);
    }

}

