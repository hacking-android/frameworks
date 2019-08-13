/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.display;

import android.annotation.SystemApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.hardware.display.IColorDisplayManager;
import android.hardware.display.Time;
import android.metrics.LogMaker;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import com.android.internal.logging.MetricsLogger;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.LocalTime;

@SystemApi
public final class ColorDisplayManager {
    @SystemApi
    public static final int AUTO_MODE_CUSTOM_TIME = 1;
    @SystemApi
    public static final int AUTO_MODE_DISABLED = 0;
    @SystemApi
    public static final int AUTO_MODE_TWILIGHT = 2;
    @SystemApi
    public static final int CAPABILITY_HARDWARE_ACCELERATION_GLOBAL = 2;
    @SystemApi
    public static final int CAPABILITY_HARDWARE_ACCELERATION_PER_APP = 4;
    @SystemApi
    public static final int CAPABILITY_NONE = 0;
    @SystemApi
    public static final int CAPABILITY_PROTECTED_CONTENT = 1;
    public static final int COLOR_MODE_AUTOMATIC = 3;
    public static final int COLOR_MODE_BOOSTED = 1;
    public static final int COLOR_MODE_NATURAL = 0;
    public static final int COLOR_MODE_SATURATED = 2;
    public static final int VENDOR_COLOR_MODE_RANGE_MAX = 511;
    public static final int VENDOR_COLOR_MODE_RANGE_MIN = 256;
    private final ColorDisplayManagerInternal mManager = ColorDisplayManagerInternal.getInstance();
    private MetricsLogger mMetricsLogger;

    public static boolean areAccessibilityTransformsEnabled(Context object) {
        object = ((Context)object).getContentResolver();
        boolean bl = false;
        if (Settings.Secure.getInt((ContentResolver)object, "accessibility_display_inversion_enabled", 0) == 1 || Settings.Secure.getInt((ContentResolver)object, "accessibility_display_daltonizer_enabled", 0) == 1) {
            bl = true;
        }
        return bl;
    }

    public static int getMaximumColorTemperature(Context context) {
        return context.getResources().getInteger(17694858);
    }

    private MetricsLogger getMetricsLogger() {
        if (this.mMetricsLogger == null) {
            this.mMetricsLogger = new MetricsLogger();
        }
        return this.mMetricsLogger;
    }

    public static int getMinimumColorTemperature(Context context) {
        return context.getResources().getInteger(17694859);
    }

    public static boolean isColorTransformAccelerated(Context context) {
        return context.getResources().getBoolean(17891506);
    }

    public static boolean isDisplayWhiteBalanceAvailable(Context context) {
        return context.getResources().getBoolean(17891412);
    }

    public static boolean isNightDisplayAvailable(Context context) {
        return context.getResources().getBoolean(17891486);
    }

    public int getColorMode() {
        return this.mManager.getColorMode();
    }

    @SystemApi
    public int getNightDisplayAutoMode() {
        return this.mManager.getNightDisplayAutoMode();
    }

    public int getNightDisplayAutoModeRaw() {
        return this.mManager.getNightDisplayAutoModeRaw();
    }

    public int getNightDisplayColorTemperature() {
        return this.mManager.getNightDisplayColorTemperature();
    }

    public LocalTime getNightDisplayCustomEndTime() {
        return this.mManager.getNightDisplayCustomEndTime().getLocalTime();
    }

    public LocalTime getNightDisplayCustomStartTime() {
        return this.mManager.getNightDisplayCustomStartTime().getLocalTime();
    }

    @SystemApi
    public int getTransformCapabilities() {
        return this.mManager.getTransformCapabilities();
    }

    public boolean isDeviceColorManaged() {
        return this.mManager.isDeviceColorManaged();
    }

    public boolean isDisplayWhiteBalanceEnabled() {
        return this.mManager.isDisplayWhiteBalanceEnabled();
    }

    public boolean isNightDisplayActivated() {
        return this.mManager.isNightDisplayActivated();
    }

    public boolean isSaturationActivated() {
        return this.mManager.isSaturationActivated();
    }

    @SystemApi
    public boolean setAppSaturationLevel(String string2, int n) {
        return this.mManager.setAppSaturationLevel(string2, n);
    }

    public void setColorMode(int n) {
        this.mManager.setColorMode(n);
    }

    public boolean setDisplayWhiteBalanceEnabled(boolean bl) {
        return this.mManager.setDisplayWhiteBalanceEnabled(bl);
    }

    public boolean setNightDisplayActivated(boolean bl) {
        return this.mManager.setNightDisplayActivated(bl);
    }

    @SystemApi
    public boolean setNightDisplayAutoMode(int n) {
        if (n != 0 && n != 1 && n != 2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid autoMode: ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        if (this.mManager.getNightDisplayAutoMode() != n) {
            this.getMetricsLogger().write(new LogMaker(1309).setType(4).setSubtype(n));
        }
        return this.mManager.setNightDisplayAutoMode(n);
    }

    public boolean setNightDisplayColorTemperature(int n) {
        return this.mManager.setNightDisplayColorTemperature(n);
    }

    @SystemApi
    public boolean setNightDisplayCustomEndTime(LocalTime localTime) {
        if (localTime != null) {
            this.getMetricsLogger().write(new LogMaker(1310).setType(4).setSubtype(1));
            return this.mManager.setNightDisplayCustomEndTime(new Time(localTime));
        }
        throw new IllegalArgumentException("endTime cannot be null");
    }

    @SystemApi
    public boolean setNightDisplayCustomStartTime(LocalTime localTime) {
        if (localTime != null) {
            this.getMetricsLogger().write(new LogMaker(1310).setType(4).setSubtype(0));
            return this.mManager.setNightDisplayCustomStartTime(new Time(localTime));
        }
        throw new IllegalArgumentException("startTime cannot be null");
    }

    @SystemApi
    public boolean setSaturationLevel(int n) {
        return this.mManager.setSaturationLevel(n);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface AutoMode {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface CapabilityType {
    }

    private static class ColorDisplayManagerInternal {
        private static ColorDisplayManagerInternal sInstance;
        private final IColorDisplayManager mCdm;

        private ColorDisplayManagerInternal(IColorDisplayManager iColorDisplayManager) {
            this.mCdm = iColorDisplayManager;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static ColorDisplayManagerInternal getInstance() {
            synchronized (ColorDisplayManagerInternal.class) {
                Object object = sInstance;
                if (object != null) return sInstance;
                try {
                    ColorDisplayManagerInternal colorDisplayManagerInternal;
                    object = ServiceManager.getServiceOrThrow("color_display");
                    sInstance = colorDisplayManagerInternal = new ColorDisplayManagerInternal(IColorDisplayManager.Stub.asInterface((IBinder)object));
                    return sInstance;
                }
                catch (ServiceManager.ServiceNotFoundException serviceNotFoundException) {
                    IllegalStateException illegalStateException = new IllegalStateException(serviceNotFoundException);
                    throw illegalStateException;
                }
            }
        }

        int getColorMode() {
            try {
                int n = this.mCdm.getColorMode();
                return n;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        int getNightDisplayAutoMode() {
            try {
                int n = this.mCdm.getNightDisplayAutoMode();
                return n;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        int getNightDisplayAutoModeRaw() {
            try {
                int n = this.mCdm.getNightDisplayAutoModeRaw();
                return n;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        int getNightDisplayColorTemperature() {
            try {
                int n = this.mCdm.getNightDisplayColorTemperature();
                return n;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        Time getNightDisplayCustomEndTime() {
            try {
                Time time = this.mCdm.getNightDisplayCustomEndTime();
                return time;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        Time getNightDisplayCustomStartTime() {
            try {
                Time time = this.mCdm.getNightDisplayCustomStartTime();
                return time;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        int getTransformCapabilities() {
            try {
                int n = this.mCdm.getTransformCapabilities();
                return n;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        boolean isDeviceColorManaged() {
            try {
                boolean bl = this.mCdm.isDeviceColorManaged();
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        boolean isDisplayWhiteBalanceEnabled() {
            try {
                boolean bl = this.mCdm.isDisplayWhiteBalanceEnabled();
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        boolean isNightDisplayActivated() {
            try {
                boolean bl = this.mCdm.isNightDisplayActivated();
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        boolean isSaturationActivated() {
            try {
                boolean bl = this.mCdm.isSaturationActivated();
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        boolean setAppSaturationLevel(String string2, int n) {
            try {
                boolean bl = this.mCdm.setAppSaturationLevel(string2, n);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        void setColorMode(int n) {
            try {
                this.mCdm.setColorMode(n);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        boolean setDisplayWhiteBalanceEnabled(boolean bl) {
            try {
                bl = this.mCdm.setDisplayWhiteBalanceEnabled(bl);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        boolean setNightDisplayActivated(boolean bl) {
            try {
                bl = this.mCdm.setNightDisplayActivated(bl);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        boolean setNightDisplayAutoMode(int n) {
            try {
                boolean bl = this.mCdm.setNightDisplayAutoMode(n);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        boolean setNightDisplayColorTemperature(int n) {
            try {
                boolean bl = this.mCdm.setNightDisplayColorTemperature(n);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        boolean setNightDisplayCustomEndTime(Time time) {
            try {
                boolean bl = this.mCdm.setNightDisplayCustomEndTime(time);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        boolean setNightDisplayCustomStartTime(Time time) {
            try {
                boolean bl = this.mCdm.setNightDisplayCustomStartTime(time);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        boolean setSaturationLevel(int n) {
            try {
                boolean bl = this.mCdm.setSaturationLevel(n);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ColorMode {
    }

}

