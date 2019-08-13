/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.EventLogTags;
import android.os.IBinder;
import android.os.IServiceManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManagerNative;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.os.BinderInternal;
import com.android.internal.util.StatLogger;
import java.util.Map;

public final class ServiceManager {
    private static final int GET_SERVICE_LOG_EVERY_CALLS_CORE;
    private static final int GET_SERVICE_LOG_EVERY_CALLS_NON_CORE;
    private static final long GET_SERVICE_SLOW_THRESHOLD_US_CORE;
    private static final long GET_SERVICE_SLOW_THRESHOLD_US_NON_CORE;
    private static final int SLOW_LOG_INTERVAL_MS = 5000;
    private static final int STATS_LOG_INTERVAL_MS = 5000;
    private static final String TAG = "ServiceManager";
    @UnsupportedAppUsage
    private static Map<String, IBinder> sCache;
    @GuardedBy(value={"sLock"})
    private static int sGetServiceAccumulatedCallCount;
    @GuardedBy(value={"sLock"})
    private static int sGetServiceAccumulatedUs;
    @GuardedBy(value={"sLock"})
    private static long sLastSlowLogActualTime;
    @GuardedBy(value={"sLock"})
    private static long sLastSlowLogUptime;
    @GuardedBy(value={"sLock"})
    private static long sLastStatsLogUptime;
    private static final Object sLock;
    @UnsupportedAppUsage
    private static IServiceManager sServiceManager;
    public static final StatLogger sStatLogger;

    static {
        sLock = new Object();
        sCache = new ArrayMap<String, IBinder>();
        GET_SERVICE_SLOW_THRESHOLD_US_CORE = SystemProperties.getInt("debug.servicemanager.slow_call_core_ms", 10) * 1000;
        GET_SERVICE_SLOW_THRESHOLD_US_NON_CORE = SystemProperties.getInt("debug.servicemanager.slow_call_ms", 50) * 1000;
        GET_SERVICE_LOG_EVERY_CALLS_CORE = SystemProperties.getInt("debug.servicemanager.log_calls_core", 100);
        GET_SERVICE_LOG_EVERY_CALLS_NON_CORE = SystemProperties.getInt("debug.servicemanager.log_calls", 200);
        sStatLogger = new StatLogger(new String[]{"getService()"});
    }

    @UnsupportedAppUsage
    public static void addService(String string2, IBinder iBinder) {
        ServiceManager.addService(string2, iBinder, false, 8);
    }

    @UnsupportedAppUsage
    public static void addService(String string2, IBinder iBinder, boolean bl) {
        ServiceManager.addService(string2, iBinder, bl, 8);
    }

    @UnsupportedAppUsage
    public static void addService(String string2, IBinder iBinder, boolean bl, int n) {
        try {
            ServiceManager.getIServiceManager().addService(string2, iBinder, bl, n);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "error in addService", remoteException);
        }
    }

    @UnsupportedAppUsage
    public static IBinder checkService(String object) {
        block3 : {
            try {
                IBinder iBinder = sCache.get(object);
                if (iBinder == null) break block3;
                return iBinder;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "error in checkService", remoteException);
                return null;
            }
        }
        object = Binder.allowBlocking(ServiceManager.getIServiceManager().checkService((String)object));
        return object;
    }

    @UnsupportedAppUsage
    private static IServiceManager getIServiceManager() {
        IServiceManager iServiceManager = sServiceManager;
        if (iServiceManager != null) {
            return iServiceManager;
        }
        sServiceManager = ServiceManagerNative.asInterface(Binder.allowBlocking(BinderInternal.getContextObject()));
        return sServiceManager;
    }

    @UnsupportedAppUsage
    public static IBinder getService(String object) {
        block3 : {
            try {
                IBinder iBinder = sCache.get(object);
                if (iBinder == null) break block3;
                return iBinder;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "error in getService", remoteException);
                return null;
            }
        }
        object = Binder.allowBlocking(ServiceManager.rawGetService((String)object));
        return object;
    }

    public static IBinder getServiceOrThrow(String string2) throws ServiceNotFoundException {
        IBinder iBinder = ServiceManager.getService(string2);
        if (iBinder != null) {
            return iBinder;
        }
        throw new ServiceNotFoundException(string2);
    }

    public static void initServiceCache(Map<String, IBinder> map) {
        if (sCache.size() == 0) {
            sCache.putAll(map);
            return;
        }
        throw new IllegalStateException("setServiceCache may only be called once");
    }

    @UnsupportedAppUsage
    public static String[] listServices() {
        try {
            String[] arrstring = ServiceManager.getIServiceManager().listServices(15);
            return arrstring;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "error in listServices", remoteException);
            return null;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private static IBinder rawGetService(String var0) throws RemoteException {
        var1_4 = ServiceManager.sStatLogger.getTime();
        var3_5 = ServiceManager.getIServiceManager().getService(var0);
        var4_6 = (int)ServiceManager.sStatLogger.logDurationStat(0, var1_4);
        var5_7 = UserHandle.isCore(Process.myUid());
        var1_4 = var5_7 != false ? ServiceManager.GET_SERVICE_SLOW_THRESHOLD_US_CORE : ServiceManager.GET_SERVICE_SLOW_THRESHOLD_US_NON_CORE;
        var6_8 = ServiceManager.sLock;
        // MONITORENTER : var6_8
        ServiceManager.sGetServiceAccumulatedUs += var4_6;
        ++ServiceManager.sGetServiceAccumulatedCallCount;
        var7_9 = SystemClock.uptimeMillis();
        if ((long)var4_6 >= var1_4) {
            try {
                if (var7_9 > ServiceManager.sLastSlowLogUptime + 5000L || ServiceManager.sLastSlowLogActualTime < (long)var4_6) {
                    EventLogTags.writeServiceManagerSlow(var4_6 / 1000, var0);
                    ServiceManager.sLastSlowLogUptime = var7_9;
                    ServiceManager.sLastSlowLogActualTime = var4_6;
                }
            }
            catch (Throwable var0_1) {
                throw var0_2;
            }
        }
        if (ServiceManager.sGetServiceAccumulatedCallCount < (var4_6 = var5_7 != false ? ServiceManager.GET_SERVICE_LOG_EVERY_CALLS_CORE : ServiceManager.GET_SERVICE_LOG_EVERY_CALLS_NON_CORE) || var7_9 < ServiceManager.sLastStatsLogUptime + 5000L) ** GOTO lbl30
        var9_10 = ServiceManager.sGetServiceAccumulatedCallCount;
        var4_6 = ServiceManager.sGetServiceAccumulatedUs / 1000;
        var1_4 = ServiceManager.sLastStatsLogUptime;
        var10_11 = (int)(var7_9 - var1_4);
        try {
            EventLogTags.writeServiceManagerStats(var9_10, var4_6, var10_11);
            ServiceManager.sGetServiceAccumulatedCallCount = 0;
            ServiceManager.sGetServiceAccumulatedUs = 0;
            ServiceManager.sLastStatsLogUptime = var7_9;
lbl30: // 2 sources:
            // MONITOREXIT : var6_8
            return var3_5;
        }
        catch (Throwable var0_3) {
            throw var0_2;
        }
    }

    public static class ServiceNotFoundException
    extends Exception {
        public ServiceNotFoundException(String string2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No service published for: ");
            stringBuilder.append(string2);
            super(stringBuilder.toString());
        }
    }

    static interface Stats {
        public static final int COUNT = 1;
        public static final int GET_SERVICE = 0;
    }

}

