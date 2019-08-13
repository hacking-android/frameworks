/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.os.BatteryStats;
import com.android.internal.os.BatterySipper;
import com.android.internal.os.PowerCalculator;
import com.android.internal.os.PowerProfile;

public class WifiPowerCalculator
extends PowerCalculator {
    private static final boolean DEBUG = false;
    private static final String TAG = "WifiPowerCalculator";
    private final double mIdleCurrentMa;
    private final double mRxCurrentMa;
    private double mTotalAppPowerDrain = 0.0;
    private long mTotalAppRunningTime = 0L;
    private final double mTxCurrentMa;

    public WifiPowerCalculator(PowerProfile powerProfile) {
        this.mIdleCurrentMa = powerProfile.getAveragePower("wifi.controller.idle");
        this.mTxCurrentMa = powerProfile.getAveragePower("wifi.controller.tx");
        this.mRxCurrentMa = powerProfile.getAveragePower("wifi.controller.rx");
    }

    @Override
    public void calculateApp(BatterySipper batterySipper, BatteryStats.Uid uid, long l, long l2, int n) {
        BatteryStats.ControllerActivityCounter controllerActivityCounter = uid.getWifiControllerActivity();
        if (controllerActivityCounter == null) {
            return;
        }
        long l3 = controllerActivityCounter.getIdleTimeCounter().getCountLocked(n);
        l2 = controllerActivityCounter.getTxTimeCounters()[0].getCountLocked(n);
        l = controllerActivityCounter.getRxTimeCounter().getCountLocked(n);
        batterySipper.wifiRunningTimeMs = l3 + l + l2;
        this.mTotalAppRunningTime += batterySipper.wifiRunningTimeMs;
        batterySipper.wifiPowerMah = ((double)l3 * this.mIdleCurrentMa + (double)l2 * this.mTxCurrentMa + (double)l * this.mRxCurrentMa) / 3600000.0;
        this.mTotalAppPowerDrain += batterySipper.wifiPowerMah;
        batterySipper.wifiRxPackets = uid.getNetworkActivityPackets(2, n);
        batterySipper.wifiTxPackets = uid.getNetworkActivityPackets(3, n);
        batterySipper.wifiRxBytes = uid.getNetworkActivityBytes(2, n);
        batterySipper.wifiTxBytes = uid.getNetworkActivityBytes(3, n);
    }

    @Override
    public void calculateRemaining(BatterySipper batterySipper, BatteryStats object, long l, long l2, int n) {
        object = ((BatteryStats)object).getWifiControllerActivity();
        long l3 = ((BatteryStats.ControllerActivityCounter)object).getIdleTimeCounter().getCountLocked(n);
        l = ((BatteryStats.ControllerActivityCounter)object).getTxTimeCounters()[0].getCountLocked(n);
        l2 = ((BatteryStats.ControllerActivityCounter)object).getRxTimeCounter().getCountLocked(n);
        batterySipper.wifiRunningTimeMs = Math.max(0L, l3 + l2 + l - this.mTotalAppRunningTime);
        double d = (double)((BatteryStats.ControllerActivityCounter)object).getPowerCounter().getCountLocked(n) / 3600000.0;
        if (d == 0.0) {
            d = ((double)l3 * this.mIdleCurrentMa + (double)l * this.mTxCurrentMa + (double)l2 * this.mRxCurrentMa) / 3600000.0;
        }
        batterySipper.wifiPowerMah = Math.max(0.0, d - this.mTotalAppPowerDrain);
    }

    @Override
    public void reset() {
        this.mTotalAppPowerDrain = 0.0;
        this.mTotalAppRunningTime = 0L;
    }
}

