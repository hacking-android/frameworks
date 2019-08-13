/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.os.BatteryStats;
import com.android.internal.os.BatterySipper;
import com.android.internal.os.PowerCalculator;
import com.android.internal.os.PowerProfile;

public class MobileRadioPowerCalculator
extends PowerCalculator {
    private static final boolean DEBUG = false;
    private static final String TAG = "MobileRadioPowerController";
    private final double[] mPowerBins = new double[5];
    private final double mPowerRadioOn;
    private final double mPowerScan;
    private BatteryStats mStats;
    private long mTotalAppMobileActiveMs = 0L;

    public MobileRadioPowerCalculator(PowerProfile powerProfile, BatteryStats batteryStats) {
        int n;
        double[] arrd;
        double d = powerProfile.getAveragePowerOrDefault("radio.active", -1.0);
        if (d != -1.0) {
            this.mPowerRadioOn = d;
        } else {
            d = 0.0 + powerProfile.getAveragePower("modem.controller.rx");
            for (n = 0; n < (arrd = this.mPowerBins).length; ++n) {
                d += powerProfile.getAveragePower("modem.controller.tx", n);
            }
            this.mPowerRadioOn = d / (double)(arrd.length + 1);
        }
        if (powerProfile.getAveragePowerOrDefault("radio.on", -1.0) != -1.0) {
            for (n = 0; n < (arrd = this.mPowerBins).length; ++n) {
                arrd[n] = powerProfile.getAveragePower("radio.on", n);
            }
        } else {
            d = powerProfile.getAveragePower("modem.controller.idle");
            this.mPowerBins[0] = 25.0 * d / 180.0;
            for (n = 1; n < (arrd = this.mPowerBins).length; ++n) {
                arrd[n] = Math.max(1.0, d / 256.0);
            }
        }
        this.mPowerScan = powerProfile.getAveragePowerOrDefault("radio.scanning", 0.0);
        this.mStats = batteryStats;
    }

    private double getMobilePowerPerPacket(long l, int n) {
        double d = this.mPowerRadioOn / 3600.0;
        long l2 = this.mStats.getNetworkActivityPackets(0, n) + this.mStats.getNetworkActivityPackets(1, n);
        l = this.mStats.getMobileRadioActiveTime(l, n) / 1000L;
        double d2 = l2 != 0L && l != 0L ? (double)l2 / (double)l : 12.20703125;
        return d / d2 / 3600.0;
    }

    @Override
    public void calculateApp(BatterySipper batterySipper, BatteryStats.Uid uid, long l, long l2, int n) {
        batterySipper.mobileRxPackets = uid.getNetworkActivityPackets(0, n);
        batterySipper.mobileTxPackets = uid.getNetworkActivityPackets(1, n);
        batterySipper.mobileActive = uid.getMobileRadioActiveTime(n) / 1000L;
        batterySipper.mobileActiveCount = uid.getMobileRadioActiveCount(n);
        batterySipper.mobileRxBytes = uid.getNetworkActivityBytes(0, n);
        batterySipper.mobileTxBytes = uid.getNetworkActivityBytes(1, n);
        if (batterySipper.mobileActive > 0L) {
            this.mTotalAppMobileActiveMs += batterySipper.mobileActive;
            batterySipper.mobileRadioPowerMah = (double)batterySipper.mobileActive * this.mPowerRadioOn / 3600000.0;
        } else {
            batterySipper.mobileRadioPowerMah = (double)(batterySipper.mobileRxPackets + batterySipper.mobileTxPackets) * this.getMobilePowerPerPacket(l, n);
        }
    }

    @Override
    public void calculateRemaining(BatterySipper batterySipper, BatteryStats batteryStats, long l, long l2, int n) {
        double d = 0.0;
        long l3 = 0L;
        l2 = 0L;
        for (int i = 0; i < this.mPowerBins.length; ++i) {
            long l4 = batteryStats.getPhoneSignalStrengthTime(i, l, n) / 1000L;
            d += (double)l4 * this.mPowerBins[i] / 3600000.0;
            l3 += l4;
            if (i != 0) continue;
            l2 = l4;
        }
        d += (double)(batteryStats.getPhoneSignalScanningTime(l, n) / 1000L) * this.mPowerScan / 3600000.0;
        if ((l = this.mStats.getMobileRadioActiveTime(l, n) / 1000L - this.mTotalAppMobileActiveMs) > 0L) {
            d += this.mPowerRadioOn * (double)l / 3600000.0;
        }
        if (d != 0.0) {
            if (l3 != 0L) {
                batterySipper.noCoveragePercent = (double)l2 * 100.0 / (double)l3;
            }
            batterySipper.mobileActive = l;
            batterySipper.mobileActiveCount = batteryStats.getMobileRadioActiveUnknownCount(n);
            batterySipper.mobileRadioPowerMah = d;
        }
    }

    @Override
    public void reset() {
        this.mTotalAppMobileActiveMs = 0L;
    }

    public void reset(BatteryStats batteryStats) {
        this.reset();
        this.mStats = batteryStats;
    }
}

