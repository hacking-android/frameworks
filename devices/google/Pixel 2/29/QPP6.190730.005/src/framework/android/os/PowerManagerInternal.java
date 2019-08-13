/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.PowerManager;
import android.os.PowerSaveState;
import java.util.function.Consumer;

public abstract class PowerManagerInternal {
    public static final int WAKEFULNESS_ASLEEP = 0;
    public static final int WAKEFULNESS_AWAKE = 1;
    public static final int WAKEFULNESS_DOZING = 3;
    public static final int WAKEFULNESS_DREAMING = 2;

    public static boolean isInteractive(int n) {
        boolean bl;
        boolean bl2 = bl = true;
        if (n != 1) {
            bl2 = n == 2 ? bl : false;
        }
        return bl2;
    }

    public static int wakefulnessToProtoEnum(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return n;
                    }
                    return 3;
                }
                return 2;
            }
            return 1;
        }
        return 0;
    }

    public static String wakefulnessToString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return Integer.toString(n);
                    }
                    return "Dozing";
                }
                return "Dreaming";
            }
            return "Awake";
        }
        return "Asleep";
    }

    public abstract void finishUidChanges();

    public abstract PowerManager.WakeData getLastWakeup();

    public abstract PowerSaveState getLowPowerState(int var1);

    public abstract void powerHint(int var1, int var2);

    public void registerLowPowerModeObserver(final int n, final Consumer<PowerSaveState> consumer) {
        this.registerLowPowerModeObserver(new LowPowerModeListener(){

            @Override
            public int getServiceType() {
                return n;
            }

            @Override
            public void onLowPowerModeChanged(PowerSaveState powerSaveState) {
                consumer.accept(powerSaveState);
            }
        });
    }

    public abstract void registerLowPowerModeObserver(LowPowerModeListener var1);

    public abstract boolean setDeviceIdleMode(boolean var1);

    public abstract void setDeviceIdleTempWhitelist(int[] var1);

    public abstract void setDeviceIdleWhitelist(int[] var1);

    public abstract void setDozeOverrideFromDreamManager(int var1, int var2);

    public abstract void setDrawWakeLockOverrideFromSidekick(boolean var1);

    public abstract boolean setLightDeviceIdleMode(boolean var1);

    public abstract void setMaximumScreenOffTimeoutFromDeviceAdmin(int var1, long var2);

    public abstract void setScreenBrightnessOverrideFromWindowManager(int var1);

    public abstract void setUserActivityTimeoutOverrideFromWindowManager(long var1);

    public abstract void setUserInactiveOverrideFromWindowManager();

    public abstract void startUidChanges();

    public abstract void uidActive(int var1);

    public abstract void uidGone(int var1);

    public abstract void uidIdle(int var1);

    public abstract void updateUidProcState(int var1, int var2);

    public abstract boolean wasDeviceIdleFor(long var1);

    public static interface LowPowerModeListener {
        public int getServiceType();

        public void onLowPowerModeChanged(PowerSaveState var1);
    }

}

