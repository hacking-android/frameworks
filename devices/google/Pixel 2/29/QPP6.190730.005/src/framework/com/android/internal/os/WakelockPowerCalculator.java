/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.os.BatteryStats;
import android.util.ArrayMap;
import com.android.internal.os.BatterySipper;
import com.android.internal.os.PowerCalculator;
import com.android.internal.os.PowerProfile;

public class WakelockPowerCalculator
extends PowerCalculator {
    private static final boolean DEBUG = false;
    private static final String TAG = "WakelockPowerCalculator";
    private final double mPowerWakelock;
    private long mTotalAppWakelockTimeMs = 0L;

    public WakelockPowerCalculator(PowerProfile powerProfile) {
        this.mPowerWakelock = powerProfile.getAveragePower("cpu.idle");
    }

    @Override
    public void calculateApp(BatterySipper batterySipper, BatteryStats.Uid object, long l, long l2, int n) {
        l2 = 0L;
        ArrayMap<String, ? extends BatteryStats.Uid.Wakelock> arrayMap = ((BatteryStats.Uid)object).getWakelockStats();
        int n2 = arrayMap.size();
        for (int i = 0; i < n2; ++i) {
            object = arrayMap.valueAt(i).getWakeTime(0);
            if (object == null) continue;
            l2 += ((BatteryStats.Timer)object).getTotalTimeLocked(l, n);
        }
        batterySipper.wakeLockTimeMs = l2 / 1000L;
        this.mTotalAppWakelockTimeMs += batterySipper.wakeLockTimeMs;
        batterySipper.wakeLockPowerMah = (double)batterySipper.wakeLockTimeMs * this.mPowerWakelock / 3600000.0;
    }

    @Override
    public void calculateRemaining(BatterySipper batterySipper, BatteryStats batteryStats, long l, long l2, int n) {
        l = batteryStats.getBatteryUptime(l2) / 1000L - (this.mTotalAppWakelockTimeMs + batteryStats.getScreenOnTime(l, n) / 1000L);
        if (l > 0L) {
            double d = (double)l * this.mPowerWakelock / 3600000.0;
            batterySipper.wakeLockTimeMs += l;
            batterySipper.wakeLockPowerMah += d;
        }
    }

    @Override
    public void reset() {
        this.mTotalAppWakelockTimeMs = 0L;
    }
}

