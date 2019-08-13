/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.annotation.SystemApi;
import android.content.Context;
import android.os.IStatsManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.StatsLogInternal;

public final class StatsLog
extends StatsLogInternal {
    private static final boolean DEBUG = false;
    private static final String TAG = "StatsLog";
    private static Object sLogLock = new Object();
    private static IStatsManager sService;

    private StatsLog() {
    }

    private static void enforceDumpCallingPermission(Context context) {
        context.enforceCallingPermission("android.permission.DUMP", "Need DUMP permission.");
    }

    private static void enforcesageStatsCallingPermission(Context context) {
        context.enforceCallingPermission("android.permission.PACKAGE_USAGE_STATS", "Need PACKAGE_USAGE_STATS permission.");
    }

    private static IStatsManager getIStatsManagerLocked() throws RemoteException {
        IStatsManager iStatsManager = sService;
        if (iStatsManager != null) {
            return iStatsManager;
        }
        sService = IStatsManager.Stub.asInterface(ServiceManager.getService("stats"));
        return sService;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean logBinaryPushStateChanged(String string2, long l, int n, int n2, long[] arrl) {
        Object object = sLogLock;
        synchronized (object) {
            try {
                IStatsManager iStatsManager;
                block6 : {
                    try {
                        iStatsManager = StatsLog.getIStatsManagerLocked();
                        if (iStatsManager != null) break block6;
                    }
                    catch (RemoteException remoteException) {
                        sService = null;
                        return false;
                    }
                    return false;
                }
                iStatsManager.sendBinaryPushStateChangedAtom(string2, l, n, n2, arrl);
                return true;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean logEvent(int n) {
        Object object = sLogLock;
        synchronized (object) {
            try {
                IStatsManager iStatsManager;
                block6 : {
                    try {
                        iStatsManager = StatsLog.getIStatsManagerLocked();
                        if (iStatsManager != null) break block6;
                    }
                    catch (RemoteException remoteException) {
                        sService = null;
                        return false;
                    }
                    return false;
                }
                iStatsManager.sendAppBreadcrumbAtom(n, 1);
                return true;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean logStart(int n) {
        Object object = sLogLock;
        synchronized (object) {
            try {
                IStatsManager iStatsManager;
                block6 : {
                    try {
                        iStatsManager = StatsLog.getIStatsManagerLocked();
                        if (iStatsManager != null) break block6;
                    }
                    catch (RemoteException remoteException) {
                        sService = null;
                        return false;
                    }
                    return false;
                }
                iStatsManager.sendAppBreadcrumbAtom(n, 3);
                return true;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean logStop(int n) {
        Object object = sLogLock;
        synchronized (object) {
            try {
                IStatsManager iStatsManager;
                block6 : {
                    try {
                        iStatsManager = StatsLog.getIStatsManagerLocked();
                        if (iStatsManager != null) break block6;
                    }
                    catch (RemoteException remoteException) {
                        sService = null;
                        return false;
                    }
                    return false;
                }
                iStatsManager.sendAppBreadcrumbAtom(n, 2);
                return true;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean logWatchdogRollbackOccurred(int n, String string2, long l) {
        Object object = sLogLock;
        synchronized (object) {
            try {
                IStatsManager iStatsManager;
                block6 : {
                    try {
                        iStatsManager = StatsLog.getIStatsManagerLocked();
                        if (iStatsManager != null) break block6;
                    }
                    catch (RemoteException remoteException) {
                        sService = null;
                        return false;
                    }
                    return false;
                }
                iStatsManager.sendWatchdogRollbackOccurredAtom(n, string2, l);
                return true;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    @SystemApi
    public static native void writeRaw(byte[] var0, int var1);
}

