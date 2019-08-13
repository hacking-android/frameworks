/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.os.BatteryStats;
import com.android.internal.os.BatterySipper;
import com.android.internal.os.PowerCalculator;
import com.android.internal.os.PowerProfile;

public class CameraPowerCalculator
extends PowerCalculator {
    private final double mCameraPowerOnAvg;

    public CameraPowerCalculator(PowerProfile powerProfile) {
        this.mCameraPowerOnAvg = powerProfile.getAveragePower("camera.avg");
    }

    @Override
    public void calculateApp(BatterySipper batterySipper, BatteryStats.Uid object, long l, long l2, int n) {
        if ((object = ((BatteryStats.Uid)object).getCameraTurnedOnTimer()) != null) {
            batterySipper.cameraTimeMs = l = ((BatteryStats.Timer)object).getTotalTimeLocked(l, n) / 1000L;
            batterySipper.cameraPowerMah = (double)l * this.mCameraPowerOnAvg / 3600000.0;
        } else {
            batterySipper.cameraTimeMs = 0L;
            batterySipper.cameraPowerMah = 0.0;
        }
    }
}

