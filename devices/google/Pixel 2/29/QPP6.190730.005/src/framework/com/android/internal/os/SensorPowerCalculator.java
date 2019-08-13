/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.BatteryStats;
import android.util.SparseArray;
import com.android.internal.os.BatterySipper;
import com.android.internal.os.PowerCalculator;
import com.android.internal.os.PowerProfile;
import java.util.List;

public class SensorPowerCalculator
extends PowerCalculator {
    private final double mGpsPower;
    private final List<Sensor> mSensors;

    public SensorPowerCalculator(PowerProfile powerProfile, SensorManager sensorManager, BatteryStats batteryStats, long l, int n) {
        this.mSensors = sensorManager.getSensorList(-1);
        this.mGpsPower = this.getAverageGpsPower(powerProfile, batteryStats, l, n);
    }

    private double getAverageGpsPower(PowerProfile powerProfile, BatteryStats batteryStats, long l, int n) {
        double d = powerProfile.getAveragePowerOrDefault("gps.on", -1.0);
        if (d != -1.0) {
            return d;
        }
        d = 0.0;
        long l2 = 0L;
        double d2 = 0.0;
        for (int i = 0; i < 2; ++i) {
            long l3 = batteryStats.getGpsSignalQualityTime(i, l, n);
            l2 += l3;
            d2 += powerProfile.getAveragePower("gps.signalqualitybased", i) * (double)l3;
        }
        if (l2 != 0L) {
            d = d2 / (double)l2;
        }
        return d;
    }

    @Override
    public void calculateApp(BatterySipper batterySipper, BatteryStats.Uid object, long l, long l2, int n) {
        object = ((BatteryStats.Uid)object).getSensorStats();
        int n2 = ((SparseArray)object).size();
        block0 : for (int i = 0; i < n2; ++i) {
            Object object2 = (BatteryStats.Uid.Sensor)((SparseArray)object).valueAt(i);
            int n3 = ((SparseArray)object).keyAt(i);
            l2 = ((BatteryStats.Uid.Sensor)object2).getSensorTime().getTotalTimeLocked(l, n) / 1000L;
            if (n3 != -10000) {
                int n4 = this.mSensors.size();
                for (int j = 0; j < n4; ++j) {
                    object2 = this.mSensors.get(j);
                    if (((Sensor)object2).getHandle() != n3) continue;
                    batterySipper.sensorPowerMah += (double)((float)l2 * ((Sensor)object2).getPower() / 3600000.0f);
                    continue block0;
                }
                continue;
            }
            batterySipper.gpsTimeMs = l2;
            batterySipper.gpsPowerMah = (double)batterySipper.gpsTimeMs * this.mGpsPower / 3600000.0;
        }
    }
}

