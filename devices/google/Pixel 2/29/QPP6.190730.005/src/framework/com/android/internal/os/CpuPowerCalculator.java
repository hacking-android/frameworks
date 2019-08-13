/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.os.BatteryStats;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.os.BatterySipper;
import com.android.internal.os.PowerCalculator;
import com.android.internal.os.PowerProfile;

public class CpuPowerCalculator
extends PowerCalculator {
    private static final boolean DEBUG = false;
    private static final long MICROSEC_IN_HR = 3600000000L;
    private static final String TAG = "CpuPowerCalculator";
    private final PowerProfile mProfile;

    public CpuPowerCalculator(PowerProfile powerProfile) {
        this.mProfile = powerProfile;
    }

    @Override
    public void calculateApp(BatterySipper batterySipper, BatteryStats.Uid arrl, long l, long l2, int n) {
        Object object;
        int n2;
        int n3;
        int n4;
        batterySipper.cpuTimeMs = (arrl.getUserCpuTimeUs(n) + arrl.getSystemCpuTimeUs(n)) / 1000L;
        int n5 = this.mProfile.getNumCpuClusters();
        double d = 0.0;
        for (n2 = 0; n2 < n5; ++n2) {
            n4 = this.mProfile.getNumSpeedStepsInCpuCluster(n2);
            for (n3 = 0; n3 < n4; ++n3) {
                d += (double)arrl.getTimeAtCpuSpeed(n2, n3, n) * this.mProfile.getAveragePowerForCpuCore(n2, n3);
            }
        }
        double d2 = d + (double)(arrl.getCpuActiveTime() * 1000L) * this.mProfile.getAveragePower("cpu.active");
        Object object2 = arrl.getCpuClusterTimes();
        d = d2;
        if (object2 != null) {
            if (((long[])object2).length == n5) {
                d = d2;
                for (n2 = 0; n2 < n5; ++n2) {
                    d += (double)(object2[n2] * 1000L) * this.mProfile.getAveragePowerForCpuCluster(n2);
                }
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("UID ");
                ((StringBuilder)object).append(arrl.getUid());
                ((StringBuilder)object).append(" CPU cluster # mismatch: Power Profile # ");
                ((StringBuilder)object).append(n5);
                ((StringBuilder)object).append(" actual # ");
                ((StringBuilder)object).append(((long[])object2).length);
                Log.w(TAG, ((StringBuilder)object).toString());
                d = d2;
            }
        }
        batterySipper.cpuPowerMah = d / 3.6E9;
        double d3 = 0.0;
        batterySipper.cpuFgTimeMs = 0L;
        object = arrl.getProcessStats();
        n4 = ((ArrayMap)object).size();
        d2 = d;
        arrl = object2;
        n3 = n5;
        for (n2 = 0; n2 < n4; ++n2) {
            object2 = (BatteryStats.Uid.Proc)((ArrayMap)object).valueAt(n2);
            String string2 = (String)((ArrayMap)object).keyAt(n2);
            batterySipper.cpuFgTimeMs += ((BatteryStats.Uid.Proc)object2).getForegroundTime(n);
            l = ((BatteryStats.Uid.Proc)object2).getUserTime(n) + ((BatteryStats.Uid.Proc)object2).getSystemTime(n) + ((BatteryStats.Uid.Proc)object2).getForegroundTime(n);
            if (batterySipper.packageWithHighestDrain != null && !batterySipper.packageWithHighestDrain.startsWith("*")) {
                d = d3;
                if (d3 < (double)l) {
                    d = d3;
                    if (!string2.startsWith("*")) {
                        d = l;
                        batterySipper.packageWithHighestDrain = string2;
                    }
                }
            } else {
                d = l;
                batterySipper.packageWithHighestDrain = string2;
            }
            d3 = d;
        }
        if (batterySipper.cpuFgTimeMs > batterySipper.cpuTimeMs) {
            batterySipper.cpuTimeMs = batterySipper.cpuFgTimeMs;
        }
    }
}

