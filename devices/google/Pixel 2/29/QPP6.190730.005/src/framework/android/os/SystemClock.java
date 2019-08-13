/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.CriticalNative
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.app.IAlarmManager;
import android.location.ILocationManager;
import android.location.LocationTime;
import android.os.DeadSystemException;
import android.os.ParcelableException;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SimpleClock;
import android.util.Slog;
import dalvik.annotation.optimization.CriticalNative;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZoneOffset;

public final class SystemClock {
    private static final String TAG = "SystemClock";

    @UnsupportedAppUsage
    private SystemClock() {
    }

    public static Clock currentGnssTimeClock() {
        return new SimpleClock(ZoneOffset.UTC){
            private final ILocationManager mMgr = ILocationManager.Stub.asInterface(ServiceManager.getService("location"));

            @Override
            public long millis() {
                block2 : {
                    LocationTime locationTime;
                    try {
                        locationTime = this.mMgr.getGnssTimeMillis();
                        if (locationTime == null) break block2;
                    }
                    catch (RemoteException remoteException) {
                        remoteException.rethrowFromSystemServer();
                        return 0L;
                    }
                    long l = (SystemClock.elapsedRealtimeNanos() - locationTime.getElapsedRealtimeNanos()) / 1000000L;
                    return locationTime.getTime() + l;
                }
                throw new DateTimeException("Gnss based time is not available.");
            }
        };
    }

    public static Clock currentNetworkTimeClock() {
        return new SimpleClock(ZoneOffset.UTC){

            @Override
            public long millis() {
                return SystemClock.currentNetworkTimeMillis();
            }
        };
    }

    public static long currentNetworkTimeMillis() {
        IAlarmManager iAlarmManager = IAlarmManager.Stub.asInterface(ServiceManager.getService("alarm"));
        if (iAlarmManager != null) {
            try {
                long l = iAlarmManager.currentNetworkTimeMillis();
                return l;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            catch (ParcelableException parcelableException) {
                parcelableException.maybeRethrow(DateTimeException.class);
                throw new RuntimeException(parcelableException);
            }
        }
        throw new RuntimeException(new DeadSystemException());
    }

    @UnsupportedAppUsage
    @CriticalNative
    public static native long currentThreadTimeMicro();

    @CriticalNative
    public static native long currentThreadTimeMillis();

    @UnsupportedAppUsage
    @CriticalNative
    public static native long currentTimeMicro();

    @CriticalNative
    public static native long elapsedRealtime();

    public static Clock elapsedRealtimeClock() {
        return new SimpleClock(ZoneOffset.UTC){

            @Override
            public long millis() {
                return SystemClock.elapsedRealtime();
            }
        };
    }

    @CriticalNative
    public static native long elapsedRealtimeNanos();

    public static boolean setCurrentTimeMillis(long l) {
        IAlarmManager iAlarmManager = IAlarmManager.Stub.asInterface(ServiceManager.getService("alarm"));
        if (iAlarmManager == null) {
            return false;
        }
        try {
            boolean bl = iAlarmManager.setTime(l);
            return bl;
        }
        catch (SecurityException securityException) {
            Slog.e("SystemClock", "Unable to set RTC", securityException);
        }
        catch (RemoteException remoteException) {
            Slog.e("SystemClock", "Unable to set RTC", remoteException);
        }
        return false;
    }

    public static void sleep(long l) {
        long l2 = SystemClock.uptimeMillis();
        long l3 = l;
        boolean bl = false;
        do {
            try {
                Thread.sleep(l3);
            }
            catch (InterruptedException interruptedException) {
                bl = true;
            }
        } while ((l3 = l2 + l - SystemClock.uptimeMillis()) > 0L);
        if (bl) {
            Thread.currentThread().interrupt();
        }
    }

    public static Clock uptimeClock() {
        return new SimpleClock(ZoneOffset.UTC){

            @Override
            public long millis() {
                return SystemClock.uptimeMillis();
            }
        };
    }

    @CriticalNative
    public static native long uptimeMillis();

    @Deprecated
    public static Clock uptimeMillisClock() {
        return SystemClock.uptimeClock();
    }

}

