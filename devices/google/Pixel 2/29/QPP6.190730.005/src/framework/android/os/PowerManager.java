/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Resources;
import android.os.BatterySaverPolicyConfig;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.IDeviceIdleController;
import android.os.IPowerManager;
import android.os.IThermalService;
import android.os.IThermalStatusListener;
import android.os.PowerSaveState;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.Trace;
import android.os.WorkSource;
import android.os._$$Lambda$PowerManager$1$_RL9hKNKSaGL1mmR_EjQ_Cm9KuA;
import android.os._$$Lambda$PowerManager$WakeLock$VvFzmRZ4ZGlXx7u3lSAJ_T_YUjw;
import android.service.dreams.Sandman;
import android.util.ArrayMap;
import android.util.Log;
import android.util.proto.ProtoOutputStream;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;

public final class PowerManager {
    public static final int ACQUIRE_CAUSES_WAKEUP = 268435456;
    public static final String ACTION_DEVICE_IDLE_MODE_CHANGED = "android.os.action.DEVICE_IDLE_MODE_CHANGED";
    @UnsupportedAppUsage
    public static final String ACTION_LIGHT_DEVICE_IDLE_MODE_CHANGED = "android.os.action.LIGHT_DEVICE_IDLE_MODE_CHANGED";
    public static final String ACTION_POWER_SAVE_MODE_CHANGED = "android.os.action.POWER_SAVE_MODE_CHANGED";
    public static final String ACTION_POWER_SAVE_MODE_CHANGED_INTERNAL = "android.os.action.POWER_SAVE_MODE_CHANGED_INTERNAL";
    @UnsupportedAppUsage
    public static final String ACTION_POWER_SAVE_MODE_CHANGING = "android.os.action.POWER_SAVE_MODE_CHANGING";
    public static final String ACTION_POWER_SAVE_TEMP_WHITELIST_CHANGED = "android.os.action.POWER_SAVE_TEMP_WHITELIST_CHANGED";
    public static final String ACTION_POWER_SAVE_WHITELIST_CHANGED = "android.os.action.POWER_SAVE_WHITELIST_CHANGED";
    @SystemApi
    @Deprecated
    public static final String ACTION_SCREEN_BRIGHTNESS_BOOST_CHANGED = "android.os.action.SCREEN_BRIGHTNESS_BOOST_CHANGED";
    public static final int BRIGHTNESS_DEFAULT = -1;
    public static final int BRIGHTNESS_OFF = 0;
    @UnsupportedAppUsage
    public static final int BRIGHTNESS_ON = 255;
    public static final int DOZE_WAKE_LOCK = 64;
    public static final int DRAW_WAKE_LOCK = 128;
    @UnsupportedAppUsage
    public static final String EXTRA_POWER_SAVE_MODE = "mode";
    @Deprecated
    public static final int FULL_WAKE_LOCK = 26;
    public static final int GO_TO_SLEEP_FLAG_NO_DOZE = 1;
    public static final int GO_TO_SLEEP_REASON_ACCESSIBILITY = 7;
    public static final int GO_TO_SLEEP_REASON_APPLICATION = 0;
    public static final int GO_TO_SLEEP_REASON_DEVICE_ADMIN = 1;
    public static final int GO_TO_SLEEP_REASON_FORCE_SUSPEND = 8;
    public static final int GO_TO_SLEEP_REASON_HDMI = 5;
    public static final int GO_TO_SLEEP_REASON_LID_SWITCH = 3;
    public static final int GO_TO_SLEEP_REASON_MAX = 8;
    public static final int GO_TO_SLEEP_REASON_MIN = 0;
    public static final int GO_TO_SLEEP_REASON_POWER_BUTTON = 4;
    public static final int GO_TO_SLEEP_REASON_SLEEP_BUTTON = 6;
    @UnsupportedAppUsage
    public static final int GO_TO_SLEEP_REASON_TIMEOUT = 2;
    public static final int LOCATION_MODE_ALL_DISABLED_WHEN_SCREEN_OFF = 2;
    public static final int LOCATION_MODE_FOREGROUND_ONLY = 3;
    public static final int LOCATION_MODE_GPS_DISABLED_WHEN_SCREEN_OFF = 1;
    public static final int LOCATION_MODE_NO_CHANGE = 0;
    public static final int LOCATION_MODE_THROTTLE_REQUESTS_WHEN_SCREEN_OFF = 4;
    public static final int MAX_LOCATION_MODE = 4;
    public static final int MIN_LOCATION_MODE = 0;
    public static final int ON_AFTER_RELEASE = 536870912;
    public static final int PARTIAL_WAKE_LOCK = 1;
    @SystemApi
    public static final int POWER_SAVE_MODE_TRIGGER_DYNAMIC = 1;
    @SystemApi
    public static final int POWER_SAVE_MODE_TRIGGER_PERCENTAGE = 0;
    public static final int PRE_IDLE_TIMEOUT_MODE_LONG = 1;
    public static final int PRE_IDLE_TIMEOUT_MODE_NORMAL = 0;
    public static final int PRE_IDLE_TIMEOUT_MODE_SHORT = 2;
    public static final int PROXIMITY_SCREEN_OFF_WAKE_LOCK = 32;
    public static final String REBOOT_QUIESCENT = "quiescent";
    public static final String REBOOT_RECOVERY = "recovery";
    public static final String REBOOT_RECOVERY_UPDATE = "recovery-update";
    public static final String REBOOT_REQUESTED_BY_DEVICE_OWNER = "deviceowner";
    public static final String REBOOT_SAFE_MODE = "safemode";
    public static final int RELEASE_FLAG_TIMEOUT = 65536;
    public static final int RELEASE_FLAG_WAIT_FOR_NO_PROXIMITY = 1;
    @Deprecated
    public static final int SCREEN_BRIGHT_WAKE_LOCK = 10;
    @Deprecated
    public static final int SCREEN_DIM_WAKE_LOCK = 6;
    public static final String SHUTDOWN_BATTERY_THERMAL_STATE = "thermal,battery";
    public static final String SHUTDOWN_LOW_BATTERY = "battery";
    public static final int SHUTDOWN_REASON_BATTERY_THERMAL = 6;
    public static final int SHUTDOWN_REASON_LOW_BATTERY = 5;
    public static final int SHUTDOWN_REASON_REBOOT = 2;
    public static final int SHUTDOWN_REASON_SHUTDOWN = 1;
    public static final int SHUTDOWN_REASON_THERMAL_SHUTDOWN = 4;
    public static final int SHUTDOWN_REASON_UNKNOWN = 0;
    public static final int SHUTDOWN_REASON_USER_REQUESTED = 3;
    public static final String SHUTDOWN_THERMAL_STATE = "thermal";
    public static final String SHUTDOWN_USER_REQUESTED = "userrequested";
    private static final String TAG = "PowerManager";
    public static final int THERMAL_STATUS_CRITICAL = 4;
    public static final int THERMAL_STATUS_EMERGENCY = 5;
    public static final int THERMAL_STATUS_LIGHT = 1;
    public static final int THERMAL_STATUS_MODERATE = 2;
    public static final int THERMAL_STATUS_NONE = 0;
    public static final int THERMAL_STATUS_SEVERE = 3;
    public static final int THERMAL_STATUS_SHUTDOWN = 6;
    public static final int UNIMPORTANT_FOR_LOGGING = 1073741824;
    @SystemApi
    public static final int USER_ACTIVITY_EVENT_ACCESSIBILITY = 3;
    public static final int USER_ACTIVITY_EVENT_ATTENTION = 4;
    @SystemApi
    public static final int USER_ACTIVITY_EVENT_BUTTON = 1;
    @SystemApi
    public static final int USER_ACTIVITY_EVENT_OTHER = 0;
    @SystemApi
    public static final int USER_ACTIVITY_EVENT_TOUCH = 2;
    @SystemApi
    public static final int USER_ACTIVITY_FLAG_INDIRECT = 2;
    @SystemApi
    public static final int USER_ACTIVITY_FLAG_NO_CHANGE_LIGHTS = 1;
    public static final int WAKE_LOCK_LEVEL_MASK = 65535;
    public static final int WAKE_REASON_APPLICATION = 2;
    public static final int WAKE_REASON_CAMERA_LAUNCH = 5;
    public static final int WAKE_REASON_GESTURE = 4;
    public static final int WAKE_REASON_HDMI = 8;
    public static final int WAKE_REASON_LID = 9;
    public static final int WAKE_REASON_PLUGGED_IN = 3;
    public static final int WAKE_REASON_POWER_BUTTON = 1;
    public static final int WAKE_REASON_UNKNOWN = 0;
    public static final int WAKE_REASON_WAKE_KEY = 6;
    public static final int WAKE_REASON_WAKE_MOTION = 7;
    final Context mContext;
    final Handler mHandler;
    IDeviceIdleController mIDeviceIdleController;
    private final ArrayMap<OnThermalStatusChangedListener, IThermalStatusListener> mListenerMap = new ArrayMap();
    @UnsupportedAppUsage
    final IPowerManager mService;
    IThermalService mThermalService;

    public PowerManager(Context context, IPowerManager iPowerManager, Handler handler) {
        this.mContext = context;
        this.mService = iPowerManager;
        this.mHandler = handler;
    }

    public static String locationPowerSaveModeToString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return Integer.toString(n);
                        }
                        return "THROTTLE_REQUESTS_WHEN_SCREEN_OFF";
                    }
                    return "FOREGROUND_ONLY";
                }
                return "ALL_DISABLED_WHEN_SCREEN_OFF";
            }
            return "GPS_DISABLED_WHEN_SCREEN_OFF";
        }
        return "NO_CHANGE";
    }

    public static String sleepReasonToString(int n) {
        switch (n) {
            default: {
                return Integer.toString(n);
            }
            case 8: {
                return "force_suspend";
            }
            case 7: {
                return "accessibility";
            }
            case 6: {
                return "sleep_button";
            }
            case 5: {
                return "hdmi";
            }
            case 4: {
                return "power_button";
            }
            case 3: {
                return "lid_switch";
            }
            case 2: {
                return "timeout";
            }
            case 1: {
                return "device_admin";
            }
            case 0: 
        }
        return "application";
    }

    @UnsupportedAppUsage
    public static void validateWakeLockParameters(int n, String string2) {
        if ((n = 65535 & n) != 1 && n != 6 && n != 10 && n != 26 && n != 32 && n != 64 && n != 128) {
            throw new IllegalArgumentException("Must specify a valid wake lock level.");
        }
        if (string2 != null) {
            return;
        }
        throw new IllegalArgumentException("The tag must not be null.");
    }

    public static String wakeReasonToString(int n) {
        switch (n) {
            default: {
                return Integer.toString(n);
            }
            case 9: {
                return "WAKE_REASON_LID";
            }
            case 8: {
                return "WAKE_REASON_HDMI";
            }
            case 7: {
                return "WAKE_REASON_WAKE_MOTION";
            }
            case 6: {
                return "WAKE_REASON_WAKE_KEY";
            }
            case 5: {
                return "WAKE_REASON_CAMERA_LAUNCH";
            }
            case 4: {
                return "WAKE_REASON_GESTURE";
            }
            case 3: {
                return "WAKE_REASON_PLUGGED_IN";
            }
            case 2: {
                return "WAKE_REASON_APPLICATION";
            }
            case 1: {
                return "WAKE_REASON_POWER_BUTTON";
            }
            case 0: 
        }
        return "WAKE_REASON_UNKNOWN";
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addThermalStatusListener(OnThermalStatusChangedListener onThermalStatusChangedListener) {
        Preconditions.checkNotNull(onThermalStatusChangedListener, "listener cannot be null");
        synchronized (this) {
            if (this.mThermalService == null) {
                this.mThermalService = IThermalService.Stub.asInterface(ServiceManager.getService("thermalservice"));
            }
            this.addThermalStatusListener(this.mContext.getMainExecutor(), onThermalStatusChangedListener);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addThermalStatusListener(Executor object, OnThermalStatusChangedListener onThermalStatusChangedListener) {
        Preconditions.checkNotNull(onThermalStatusChangedListener, "listener cannot be null");
        Preconditions.checkNotNull(object, "executor cannot be null");
        synchronized (this) {
            if (this.mThermalService == null) {
                this.mThermalService = IThermalService.Stub.asInterface(ServiceManager.getService("thermalservice"));
            }
            boolean bl = !this.mListenerMap.containsKey(onThermalStatusChangedListener);
            IThermalStatusListener.Stub stub = new StringBuilder();
            ((StringBuilder)((Object)stub)).append("Listener already registered: ");
            ((StringBuilder)((Object)stub)).append(onThermalStatusChangedListener);
            Preconditions.checkArgument(bl, ((StringBuilder)((Object)stub)).toString());
            stub = new IThermalStatusListener.Stub((Executor)object, onThermalStatusChangedListener){
                final /* synthetic */ Executor val$executor;
                final /* synthetic */ OnThermalStatusChangedListener val$listener;
                {
                    this.val$executor = executor;
                    this.val$listener = onThermalStatusChangedListener;
                }

                static /* synthetic */ void lambda$onStatusChange$0(OnThermalStatusChangedListener onThermalStatusChangedListener, int n) {
                    onThermalStatusChangedListener.onThermalStatusChanged(n);
                }

                @Override
                public void onStatusChange(int n) {
                    long l = Binder.clearCallingIdentity();
                    try {
                        Executor executor = this.val$executor;
                        OnThermalStatusChangedListener onThermalStatusChangedListener = this.val$listener;
                        _$$Lambda$PowerManager$1$_RL9hKNKSaGL1mmR_EjQ_Cm9KuA _$$Lambda$PowerManager$1$_RL9hKNKSaGL1mmR_EjQ_Cm9KuA = new _$$Lambda$PowerManager$1$_RL9hKNKSaGL1mmR_EjQ_Cm9KuA(onThermalStatusChangedListener, n);
                        executor.execute(_$$Lambda$PowerManager$1$_RL9hKNKSaGL1mmR_EjQ_Cm9KuA);
                        return;
                    }
                    finally {
                        Binder.restoreCallingIdentity(l);
                    }
                }
            };
            try {
                if (this.mThermalService.registerThermalStatusListener(stub)) {
                    this.mListenerMap.put(onThermalStatusChangedListener, stub);
                    return;
                }
                object = new RuntimeException("Listener failed to set");
                throw object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void boostScreenBrightness(long l) {
        try {
            this.mService.boostScreenBrightness(l);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void dream(long l) {
        Sandman.startDreamByUserRequest(this.mContext);
    }

    @SystemApi
    public boolean forceSuspend() {
        try {
            boolean bl = this.mService.forceSuspend();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getCurrentThermalStatus() {
        synchronized (this) {
            if (this.mThermalService == null) {
                this.mThermalService = IThermalService.Stub.asInterface(ServiceManager.getService("thermalservice"));
            }
            try {
                return this.mThermalService.getCurrentThermalStatus();
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public int getDefaultScreenBrightnessForVrSetting() {
        return this.mContext.getResources().getInteger(17694882);
    }

    @UnsupportedAppUsage
    public int getDefaultScreenBrightnessSetting() {
        return this.mContext.getResources().getInteger(17694885);
    }

    public int getLastShutdownReason() {
        try {
            int n = this.mService.getLastShutdownReason();
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int getLastSleepReason() {
        try {
            int n = this.mService.getLastSleepReason();
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int getLocationPowerSaveMode() {
        PowerSaveState powerSaveState = this.getPowerSaveState(1);
        if (!powerSaveState.batterySaverEnabled) {
            return 0;
        }
        return powerSaveState.locationMode;
    }

    public int getMaximumScreenBrightnessForVrSetting() {
        return this.mContext.getResources().getInteger(17694883);
    }

    @UnsupportedAppUsage
    public int getMaximumScreenBrightnessSetting() {
        return this.mContext.getResources().getInteger(17694886);
    }

    public int getMinimumScreenBrightnessForVrSetting() {
        return this.mContext.getResources().getInteger(17694884);
    }

    @UnsupportedAppUsage
    public int getMinimumScreenBrightnessSetting() {
        return this.mContext.getResources().getInteger(17694887);
    }

    @SystemApi
    public int getPowerSaveModeTrigger() {
        try {
            int n = this.mService.getPowerSaveModeTrigger();
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public PowerSaveState getPowerSaveState(int n) {
        try {
            PowerSaveState powerSaveState = this.mService.getPowerSaveState(n);
            return powerSaveState;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void goToSleep(long l) {
        this.goToSleep(l, 0, 0);
    }

    @UnsupportedAppUsage
    public void goToSleep(long l, int n, int n2) {
        try {
            this.mService.goToSleep(l, n, n2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isDeviceIdleMode() {
        try {
            boolean bl = this.mService.isDeviceIdleMode();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isIgnoringBatteryOptimizations(String string2) {
        synchronized (this) {
            if (this.mIDeviceIdleController == null) {
                this.mIDeviceIdleController = IDeviceIdleController.Stub.asInterface(ServiceManager.getService("deviceidle"));
            }
        }
        try {
            return this.mIDeviceIdleController.isPowerSaveWhitelistApp(string2);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isInteractive() {
        try {
            boolean bl = this.mService.isInteractive();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public boolean isLightDeviceIdleMode() {
        try {
            boolean bl = this.mService.isLightDeviceIdleMode();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isPowerSaveMode() {
        try {
            boolean bl = this.mService.isPowerSaveMode();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    @Deprecated
    public boolean isScreenBrightnessBoosted() {
        return false;
    }

    @Deprecated
    public boolean isScreenOn() {
        return this.isInteractive();
    }

    public boolean isSustainedPerformanceModeSupported() {
        return this.mContext.getResources().getBoolean(17891544);
    }

    public boolean isWakeLockLevelSupported(int n) {
        try {
            boolean bl = this.mService.isWakeLockLevelSupported(n);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void nap(long l) {
        try {
            this.mService.nap(l);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public WakeLock newWakeLock(int n, String string2) {
        PowerManager.validateWakeLockParameters(n, string2);
        return new WakeLock(n, string2, this.mContext.getOpPackageName());
    }

    public void reboot(String string2) {
        try {
            this.mService.reboot(false, string2, true);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void rebootSafeMode() {
        try {
            this.mService.rebootSafeMode(false, true);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeThermalStatusListener(OnThermalStatusChangedListener object) {
        Preconditions.checkNotNull(object, "listener cannot be null");
        synchronized (this) {
            IThermalStatusListener iThermalStatusListener;
            if (this.mThermalService == null) {
                this.mThermalService = IThermalService.Stub.asInterface(ServiceManager.getService("thermalservice"));
            }
            boolean bl = (iThermalStatusListener = this.mListenerMap.get(object)) != null;
            Preconditions.checkArgument(bl, "Listener was not added");
            try {
                if (this.mThermalService.unregisterThermalStatusListener(iThermalStatusListener)) {
                    this.mListenerMap.remove(object);
                    return;
                }
                object = new RuntimeException("Listener failed to remove");
                throw object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    public boolean setAdaptivePowerSaveEnabled(boolean bl) {
        try {
            bl = this.mService.setAdaptivePowerSaveEnabled(bl);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean setAdaptivePowerSavePolicy(BatterySaverPolicyConfig batterySaverPolicyConfig) {
        try {
            boolean bl = this.mService.setAdaptivePowerSavePolicy(batterySaverPolicyConfig);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setDozeAfterScreenOff(boolean bl) {
        try {
            this.mService.setDozeAfterScreenOff(bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean setDynamicPowerSaveHint(boolean bl, int n) {
        try {
            bl = this.mService.setDynamicPowerSaveHint(bl, n);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean setPowerSaveModeEnabled(boolean bl) {
        try {
            bl = this.mService.setPowerSaveModeEnabled(bl);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void shutdown(boolean bl, String string2, boolean bl2) {
        try {
            this.mService.shutdown(bl, string2, bl2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void userActivity(long l, int n, int n2) {
        try {
            this.mService.userActivity(l, n, n2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void userActivity(long l, boolean bl) {
        this.userActivity(l, 0, (int)bl);
    }

    @Deprecated
    public void wakeUp(long l) {
        this.wakeUp(l, 0, "wakeUp");
    }

    public void wakeUp(long l, int n, String string2) {
        try {
            this.mService.wakeUp(l, n, string2, this.mContext.getOpPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    @UnsupportedAppUsage
    public void wakeUp(long l, String string2) {
        this.wakeUp(l, 0, string2);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface AutoPowerSaveModeTriggers {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface LocationPowerSaveMode {
    }

    public static interface OnThermalStatusChangedListener {
        public void onThermalStatusChanged(int var1);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ServiceType {
        public static final int ANIMATION = 3;
        public static final int AOD = 14;
        public static final int BATTERY_STATS = 9;
        public static final int DATA_SAVER = 10;
        public static final int FORCE_ALL_APPS_STANDBY = 11;
        public static final int FORCE_BACKGROUND_CHECK = 12;
        public static final int FULL_BACKUP = 4;
        public static final int KEYVALUE_BACKUP = 5;
        public static final int LOCATION = 1;
        public static final int NETWORK_FIREWALL = 6;
        public static final int NIGHT_MODE = 16;
        public static final int NULL = 0;
        public static final int OPTIONAL_SENSORS = 13;
        public static final int QUICK_DOZE = 15;
        public static final int SCREEN_BRIGHTNESS = 7;
        public static final int SOUND = 8;
        public static final int VIBRATION = 2;
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ShutdownReason {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ThermalStatus {
    }

    public static class WakeData {
        public int wakeReason;
        public long wakeTime;

        public WakeData(long l, int n) {
            this.wakeTime = l;
            this.wakeReason = n;
        }
    }

    public final class WakeLock {
        private int mExternalCount;
        @UnsupportedAppUsage
        private int mFlags;
        private boolean mHeld;
        private String mHistoryTag;
        private int mInternalCount;
        private final String mPackageName;
        private boolean mRefCounted = true;
        private final Runnable mReleaser = new Runnable(){

            @Override
            public void run() {
                WakeLock.this.release(65536);
            }
        };
        @UnsupportedAppUsage
        private String mTag;
        private final IBinder mToken;
        private final String mTraceName;
        private WorkSource mWorkSource;

        WakeLock(int n, String string2, String string3) {
            this.mFlags = n;
            this.mTag = string2;
            this.mPackageName = string3;
            this.mToken = new Binder();
            PowerManager.this = new StringBuilder();
            ((StringBuilder)PowerManager.this).append("WakeLock (");
            ((StringBuilder)PowerManager.this).append(this.mTag);
            ((StringBuilder)PowerManager.this).append(")");
            this.mTraceName = ((StringBuilder)PowerManager.this).toString();
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private void acquireLocked() {
            ++this.mInternalCount;
            ++this.mExternalCount;
            if (this.mRefCounted && this.mInternalCount != 1) return;
            PowerManager.this.mHandler.removeCallbacks(this.mReleaser);
            Trace.asyncTraceBegin(131072L, this.mTraceName, 0);
            try {
                PowerManager.this.mService.acquireWakeLock(this.mToken, this.mFlags, this.mTag, this.mPackageName, this.mWorkSource, this.mHistoryTag);
                this.mHeld = true;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void acquire() {
            IBinder iBinder = this.mToken;
            synchronized (iBinder) {
                this.acquireLocked();
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void acquire(long l) {
            IBinder iBinder = this.mToken;
            synchronized (iBinder) {
                this.acquireLocked();
                PowerManager.this.mHandler.postDelayed(this.mReleaser, l);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        protected void finalize() throws Throwable {
            IBinder iBinder = this.mToken;
            synchronized (iBinder) {
                if (this.mHeld) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("WakeLock finalized while still held: ");
                    stringBuilder.append(this.mTag);
                    Log.wtf(PowerManager.TAG, stringBuilder.toString());
                    Trace.asyncTraceEnd(131072L, this.mTraceName, 0);
                    try {
                        PowerManager.this.mService.releaseWakeLock(this.mToken, 0);
                    }
                    catch (RemoteException remoteException) {
                        throw remoteException.rethrowFromSystemServer();
                    }
                }
                return;
            }
        }

        public String getTag() {
            return this.mTag;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public boolean isHeld() {
            IBinder iBinder = this.mToken;
            synchronized (iBinder) {
                return this.mHeld;
            }
        }

        public /* synthetic */ void lambda$wrap$0$PowerManager$WakeLock(Runnable runnable) {
            try {
                runnable.run();
                return;
            }
            finally {
                this.release();
            }
        }

        public void release() {
            this.release(0);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void release(int n) {
            IBinder iBinder = this.mToken;
            synchronized (iBinder) {
                if (this.mInternalCount > 0) {
                    --this.mInternalCount;
                }
                if ((65536 & n) == 0) {
                    --this.mExternalCount;
                }
                if (!this.mRefCounted || this.mInternalCount == 0) {
                    PowerManager.this.mHandler.removeCallbacks(this.mReleaser);
                    if (this.mHeld) {
                        Trace.asyncTraceEnd(131072L, this.mTraceName, 0);
                        try {
                            PowerManager.this.mService.releaseWakeLock(this.mToken, n);
                            this.mHeld = false;
                        }
                        catch (RemoteException remoteException) {
                            throw remoteException.rethrowFromSystemServer();
                        }
                    }
                }
                if (this.mRefCounted && this.mExternalCount < 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("WakeLock under-locked ");
                    stringBuilder.append(this.mTag);
                    RuntimeException runtimeException = new RuntimeException(stringBuilder.toString());
                    throw runtimeException;
                }
                return;
            }
        }

        public void setHistoryTag(String string2) {
            this.mHistoryTag = string2;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void setReferenceCounted(boolean bl) {
            IBinder iBinder = this.mToken;
            synchronized (iBinder) {
                this.mRefCounted = bl;
                return;
            }
        }

        public void setTag(String string2) {
            this.mTag = string2;
        }

        public void setUnimportantForLogging(boolean bl) {
            this.mFlags = bl ? (this.mFlags |= 1073741824) : (this.mFlags &= -1073741825);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void setWorkSource(WorkSource workSource) {
            IBinder iBinder = this.mToken;
            synchronized (iBinder) {
                Throwable throwable2;
                block14 : {
                    WorkSource workSource2;
                    boolean bl;
                    block13 : {
                        workSource2 = workSource;
                        if (workSource != null) {
                            workSource2 = workSource;
                            try {
                                if (!workSource.isEmpty()) break block13;
                                workSource2 = null;
                            }
                            catch (Throwable throwable2) {
                                break block14;
                            }
                        }
                    }
                    boolean bl2 = true;
                    if (workSource2 == null) {
                        if (this.mWorkSource == null) {
                            bl2 = false;
                        }
                        this.mWorkSource = null;
                    } else if (this.mWorkSource == null) {
                        bl2 = true;
                        this.mWorkSource = workSource = new WorkSource(workSource2);
                    } else {
                        boolean bl3;
                        bl2 = bl3 = true ^ this.mWorkSource.equals(workSource2);
                        if (bl3) {
                            this.mWorkSource.set(workSource2);
                            bl2 = bl3;
                        }
                    }
                    if (bl2 && (bl = this.mHeld)) {
                        try {
                            PowerManager.this.mService.updateWakeLockWorkSource(this.mToken, this.mWorkSource, this.mHistoryTag);
                        }
                        catch (RemoteException remoteException) {
                            throw remoteException.rethrowFromSystemServer();
                        }
                    }
                    return;
                }
                throw throwable2;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public String toString() {
            IBinder iBinder = this.mToken;
            synchronized (iBinder) {
                CharSequence charSequence = new StringBuilder();
                charSequence.append("WakeLock{");
                charSequence.append(Integer.toHexString(System.identityHashCode(this)));
                charSequence.append(" held=");
                charSequence.append(this.mHeld);
                charSequence.append(", refCount=");
                charSequence.append(this.mInternalCount);
                charSequence.append("}");
                return charSequence.toString();
            }
        }

        public Runnable wrap(Runnable runnable) {
            this.acquire();
            return new _$$Lambda$PowerManager$WakeLock$VvFzmRZ4ZGlXx7u3lSAJ_T_YUjw(this, runnable);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
            IBinder iBinder = this.mToken;
            synchronized (iBinder) {
                l = protoOutputStream.start(l);
                protoOutputStream.write(1138166333441L, this.mTag);
                protoOutputStream.write(1138166333442L, this.mPackageName);
                protoOutputStream.write(1133871366147L, this.mHeld);
                protoOutputStream.write(1120986464260L, this.mInternalCount);
                if (this.mWorkSource != null) {
                    this.mWorkSource.writeToProto(protoOutputStream, 1146756268037L);
                }
                protoOutputStream.end(l);
                return;
            }
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface WakeReason {
    }

}

