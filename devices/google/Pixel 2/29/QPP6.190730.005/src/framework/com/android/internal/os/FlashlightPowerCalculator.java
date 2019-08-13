/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.os.BatteryStats;
import com.android.internal.os.BatterySipper;
import com.android.internal.os.PowerCalculator;
import com.android.internal.os.PowerProfile;

public class FlashlightPowerCalculator
extends PowerCalculator {
    private final double mFlashlightPowerOnAvg;

    public FlashlightPowerCalculator(PowerProfile powerProfile) {
        this.mFlashlightPowerOnAvg = powerProfile.getAveragePower("camera.flashlight");
    }

    @Override
    public void calculateApp(BatterySipper batterySipper, BatteryStats.Uid object, long l, long l2, int n) {
        if ((object = ((BatteryStats.Uid)object).getFlashlightTurnedOnTimer()) != null) {
            batterySipper.flashlightTimeMs = l = ((BatteryStats.Timer)object).getTotalTimeLocked(l, n) / 1000L;
            batterySipper.flashlightPowerMah = (double)l * this.mFlashlightPowerOnAvg / 3600000.0;
        } else {
            batterySipper.flashlightTimeMs = 0L;
            batterySipper.flashlightPowerMah = 0.0;
        }
    }
}

