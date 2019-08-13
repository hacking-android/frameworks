/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.os.BatteryStats;
import android.util.LongSparseArray;
import com.android.internal.os.BatterySipper;
import com.android.internal.os.PowerCalculator;
import com.android.internal.os.PowerProfile;

public class MemoryPowerCalculator
extends PowerCalculator {
    private static final boolean DEBUG = false;
    public static final String TAG = "MemoryPowerCalculator";
    private final double[] powerAverages;

    public MemoryPowerCalculator(PowerProfile powerProfile) {
        int n = powerProfile.getNumElements("memory.bandwidths");
        this.powerAverages = new double[n];
        for (int i = 0; i < n; ++i) {
            this.powerAverages[i] = powerProfile.getAveragePower("memory.bandwidths", i);
            double d = this.powerAverages[i];
        }
    }

    @Override
    public void calculateApp(BatterySipper batterySipper, BatteryStats.Uid uid, long l, long l2, int n) {
    }

    @Override
    public void calculateRemaining(BatterySipper batterySipper, BatteryStats object, long l, long l2, int n) {
        double[] arrd;
        double d = 0.0;
        l2 = 0L;
        object = ((BatteryStats)object).getKernelMemoryStats();
        for (int i = 0; i < ((LongSparseArray)object).size() && i < (arrd = this.powerAverages).length; ++i) {
            double d2 = arrd[(int)((LongSparseArray)object).keyAt(i)];
            long l3 = ((BatteryStats.Timer)((LongSparseArray)object).valueAt(i)).getTotalTimeLocked(l, n);
            d += (double)l3 * d2 / 60000.0 / 60.0;
            l2 += l3;
        }
        batterySipper.usagePowerMah = d;
        batterySipper.usageTimeMs = l2;
    }
}

