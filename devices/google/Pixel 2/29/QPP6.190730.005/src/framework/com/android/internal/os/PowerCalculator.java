/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.os.BatteryStats;
import com.android.internal.os.BatterySipper;

public abstract class PowerCalculator {
    public abstract void calculateApp(BatterySipper var1, BatteryStats.Uid var2, long var3, long var5, int var7);

    public void calculateRemaining(BatterySipper batterySipper, BatteryStats batteryStats, long l, long l2, int n) {
    }

    public void reset() {
    }
}

