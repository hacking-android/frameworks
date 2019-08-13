/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.os.BatteryStats;
import com.android.internal.os.BatterySipper;
import com.android.internal.os.PowerCalculator;
import com.android.internal.os.PowerProfile;

public class WifiPowerEstimator
extends PowerCalculator {
    private static final boolean DEBUG = false;
    private static final String TAG = "WifiPowerEstimator";
    private long mTotalAppWifiRunningTimeMs = 0L;
    private final double mWifiPowerBatchScan;
    private final double mWifiPowerOn;
    private final double mWifiPowerPerPacket;
    private final double mWifiPowerScan;

    public WifiPowerEstimator(PowerProfile powerProfile) {
        this.mWifiPowerPerPacket = WifiPowerEstimator.getWifiPowerPerPacket(powerProfile);
        this.mWifiPowerOn = powerProfile.getAveragePower("wifi.on");
        this.mWifiPowerScan = powerProfile.getAveragePower("wifi.scan");
        this.mWifiPowerBatchScan = powerProfile.getAveragePower("wifi.batchedscan");
    }

    private static double getWifiPowerPerPacket(PowerProfile powerProfile) {
        return powerProfile.getAveragePower("wifi.active") / 3600.0 / 61.03515625;
    }

    @Override
    public void calculateApp(BatterySipper batterySipper, BatteryStats.Uid uid, long l, long l2, int n) {
        BatteryStats.Uid uid2 = uid;
        l2 = l;
        int n2 = n;
        batterySipper.wifiRxPackets = uid2.getNetworkActivityPackets(2, n2);
        batterySipper.wifiTxPackets = uid2.getNetworkActivityPackets(3, n2);
        batterySipper.wifiRxBytes = uid2.getNetworkActivityBytes(2, n2);
        batterySipper.wifiTxBytes = uid2.getNetworkActivityBytes(3, n2);
        double d = batterySipper.wifiRxPackets + batterySipper.wifiTxPackets;
        double d2 = this.mWifiPowerPerPacket;
        batterySipper.wifiRunningTimeMs = uid2.getWifiRunningTime(l2, n2) / 1000L;
        this.mTotalAppWifiRunningTimeMs += batterySipper.wifiRunningTimeMs;
        double d3 = (double)batterySipper.wifiRunningTimeMs * this.mWifiPowerOn / 3600000.0;
        double d4 = (double)(uid2.getWifiScanTime(l2, n2) / 1000L) * this.mWifiPowerScan / 3600000.0;
        double d5 = 0.0;
        for (n2 = 0; n2 < 5; ++n2) {
            d5 += (double)(uid.getWifiBatchedScanTime(n2, l, n) / 1000L) * this.mWifiPowerBatchScan / 3600000.0;
        }
        batterySipper.wifiPowerMah = d * d2 + d3 + d4 + d5;
    }

    @Override
    public void calculateRemaining(BatterySipper batterySipper, BatteryStats batteryStats, long l, long l2, int n) {
        l = batteryStats.getGlobalWifiRunningTime(l, n) / 1000L;
        double d = (double)(l - this.mTotalAppWifiRunningTimeMs) * this.mWifiPowerOn / 3600000.0;
        batterySipper.wifiRunningTimeMs = l;
        batterySipper.wifiPowerMah = Math.max(0.0, d);
    }

    @Override
    public void reset() {
        this.mTotalAppWifiRunningTimeMs = 0L;
    }
}

