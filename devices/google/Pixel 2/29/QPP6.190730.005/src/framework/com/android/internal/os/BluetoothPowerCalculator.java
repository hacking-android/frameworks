/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.os.BatteryStats;
import com.android.internal.os.BatterySipper;
import com.android.internal.os.PowerCalculator;
import com.android.internal.os.PowerProfile;

public class BluetoothPowerCalculator
extends PowerCalculator {
    private static final boolean DEBUG = false;
    private static final String TAG = "BluetoothPowerCalculator";
    private double mAppTotalPowerMah = 0.0;
    private long mAppTotalTimeMs = 0L;
    private final double mIdleMa;
    private final double mRxMa;
    private final double mTxMa;

    public BluetoothPowerCalculator(PowerProfile powerProfile) {
        this.mIdleMa = powerProfile.getAveragePower("bluetooth.controller.idle");
        this.mRxMa = powerProfile.getAveragePower("bluetooth.controller.rx");
        this.mTxMa = powerProfile.getAveragePower("bluetooth.controller.tx");
    }

    @Override
    public void calculateApp(BatterySipper batterySipper, BatteryStats.Uid uid, long l, long l2, int n) {
        BatteryStats.ControllerActivityCounter controllerActivityCounter = uid.getBluetoothControllerActivity();
        if (controllerActivityCounter == null) {
            return;
        }
        l2 = controllerActivityCounter.getIdleTimeCounter().getCountLocked(n);
        long l3 = controllerActivityCounter.getRxTimeCounter().getCountLocked(n);
        l = controllerActivityCounter.getTxTimeCounters()[0].getCountLocked(n);
        long l4 = l2 + l + l3;
        double d = (double)controllerActivityCounter.getPowerCounter().getCountLocked(n) / 3600000.0;
        if (d == 0.0) {
            d = ((double)l2 * this.mIdleMa + (double)l3 * this.mRxMa + (double)l * this.mTxMa) / 3600000.0;
        }
        batterySipper.bluetoothPowerMah = d;
        batterySipper.bluetoothRunningTimeMs = l4;
        batterySipper.btRxBytes = uid.getNetworkActivityBytes(4, n);
        batterySipper.btTxBytes = uid.getNetworkActivityBytes(5, n);
        this.mAppTotalPowerMah += d;
        this.mAppTotalTimeMs += l4;
    }

    @Override
    public void calculateRemaining(BatterySipper batterySipper, BatteryStats object, long l, long l2, int n) {
        object = ((BatteryStats)object).getBluetoothControllerActivity();
        l2 = ((BatteryStats.ControllerActivityCounter)object).getIdleTimeCounter().getCountLocked(n);
        l = ((BatteryStats.ControllerActivityCounter)object).getTxTimeCounters()[0].getCountLocked(n);
        long l3 = ((BatteryStats.ControllerActivityCounter)object).getRxTimeCounter().getCountLocked(n);
        double d = (double)((BatteryStats.ControllerActivityCounter)object).getPowerCounter().getCountLocked(n) / 3600000.0;
        if (d == 0.0) {
            d = ((double)l2 * this.mIdleMa + (double)l3 * this.mRxMa + (double)l * this.mTxMa) / 3600000.0;
        }
        batterySipper.bluetoothPowerMah = Math.max(0.0, d - this.mAppTotalPowerMah);
        batterySipper.bluetoothRunningTimeMs = Math.max(0L, l2 + l + l3 - this.mAppTotalTimeMs);
    }

    @Override
    public void reset() {
        this.mAppTotalPowerMah = 0.0;
        this.mAppTotalTimeMs = 0L;
    }
}

