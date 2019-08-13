/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.annotation.UnsupportedAppUsage;
import android.os.BatteryStats;

public class BatterySipper
implements Comparable<BatterySipper> {
    public double audioPowerMah;
    public long audioTimeMs;
    public double bluetoothPowerMah;
    public long bluetoothRunningTimeMs;
    public long btRxBytes;
    public long btTxBytes;
    public double cameraPowerMah;
    public long cameraTimeMs;
    @UnsupportedAppUsage
    public long cpuFgTimeMs;
    @UnsupportedAppUsage
    public double cpuPowerMah;
    @UnsupportedAppUsage
    public long cpuTimeMs;
    @UnsupportedAppUsage
    public DrainType drainType;
    public double flashlightPowerMah;
    public long flashlightTimeMs;
    public double gpsPowerMah;
    @UnsupportedAppUsage
    public long gpsTimeMs;
    @UnsupportedAppUsage
    public String[] mPackages;
    public long mobileActive;
    public int mobileActiveCount;
    public double mobileRadioPowerMah;
    public long mobileRxBytes;
    public long mobileRxPackets;
    public long mobileTxBytes;
    public long mobileTxPackets;
    public double mobilemspp;
    public double noCoveragePercent;
    @UnsupportedAppUsage
    public String packageWithHighestDrain;
    public double percent;
    public double proportionalSmearMah;
    public double screenPowerMah;
    public double sensorPowerMah;
    public boolean shouldHide;
    @UnsupportedAppUsage
    public double totalPowerMah;
    public double totalSmearedPowerMah;
    @UnsupportedAppUsage
    public BatteryStats.Uid uidObj;
    public double usagePowerMah;
    @UnsupportedAppUsage
    public long usageTimeMs;
    @UnsupportedAppUsage
    public int userId;
    public double videoPowerMah;
    public long videoTimeMs;
    public double wakeLockPowerMah;
    @UnsupportedAppUsage
    public long wakeLockTimeMs;
    public double wifiPowerMah;
    @UnsupportedAppUsage
    public long wifiRunningTimeMs;
    public long wifiRxBytes;
    public long wifiRxPackets;
    public long wifiTxBytes;
    public long wifiTxPackets;

    @UnsupportedAppUsage
    public BatterySipper(DrainType drainType, BatteryStats.Uid uid, double d) {
        this.totalPowerMah = d;
        this.drainType = drainType;
        this.uidObj = uid;
    }

    @UnsupportedAppUsage
    public void add(BatterySipper batterySipper) {
        this.totalPowerMah += batterySipper.totalPowerMah;
        this.usageTimeMs += batterySipper.usageTimeMs;
        this.usagePowerMah += batterySipper.usagePowerMah;
        this.audioTimeMs += batterySipper.audioTimeMs;
        this.cpuTimeMs += batterySipper.cpuTimeMs;
        this.gpsTimeMs += batterySipper.gpsTimeMs;
        this.wifiRunningTimeMs += batterySipper.wifiRunningTimeMs;
        this.cpuFgTimeMs += batterySipper.cpuFgTimeMs;
        this.videoTimeMs += batterySipper.videoTimeMs;
        this.wakeLockTimeMs += batterySipper.wakeLockTimeMs;
        this.cameraTimeMs += batterySipper.cameraTimeMs;
        this.flashlightTimeMs += batterySipper.flashlightTimeMs;
        this.bluetoothRunningTimeMs += batterySipper.bluetoothRunningTimeMs;
        this.mobileRxPackets += batterySipper.mobileRxPackets;
        this.mobileTxPackets += batterySipper.mobileTxPackets;
        this.mobileActive += batterySipper.mobileActive;
        this.mobileActiveCount += batterySipper.mobileActiveCount;
        this.wifiRxPackets += batterySipper.wifiRxPackets;
        this.wifiTxPackets += batterySipper.wifiTxPackets;
        this.mobileRxBytes += batterySipper.mobileRxBytes;
        this.mobileTxBytes += batterySipper.mobileTxBytes;
        this.wifiRxBytes += batterySipper.wifiRxBytes;
        this.wifiTxBytes += batterySipper.wifiTxBytes;
        this.btRxBytes += batterySipper.btRxBytes;
        this.btTxBytes += batterySipper.btTxBytes;
        this.audioPowerMah += batterySipper.audioPowerMah;
        this.wifiPowerMah += batterySipper.wifiPowerMah;
        this.gpsPowerMah += batterySipper.gpsPowerMah;
        this.cpuPowerMah += batterySipper.cpuPowerMah;
        this.sensorPowerMah += batterySipper.sensorPowerMah;
        this.mobileRadioPowerMah += batterySipper.mobileRadioPowerMah;
        this.wakeLockPowerMah += batterySipper.wakeLockPowerMah;
        this.cameraPowerMah += batterySipper.cameraPowerMah;
        this.flashlightPowerMah += batterySipper.flashlightPowerMah;
        this.bluetoothPowerMah += batterySipper.bluetoothPowerMah;
        this.screenPowerMah += batterySipper.screenPowerMah;
        this.videoPowerMah += batterySipper.videoPowerMah;
        this.proportionalSmearMah += batterySipper.proportionalSmearMah;
        this.totalSmearedPowerMah += batterySipper.totalSmearedPowerMah;
    }

    @Override
    public int compareTo(BatterySipper batterySipper) {
        DrainType drainType = this.drainType;
        if (drainType != batterySipper.drainType) {
            if (drainType == DrainType.OVERCOUNTED) {
                return 1;
            }
            if (batterySipper.drainType == DrainType.OVERCOUNTED) {
                return -1;
            }
        }
        return Double.compare(batterySipper.totalPowerMah, this.totalPowerMah);
    }

    public void computeMobilemspp() {
        long l = this.mobileRxPackets + this.mobileTxPackets;
        double d = l > 0L ? (double)this.mobileActive / (double)l : 0.0;
        this.mobilemspp = d;
    }

    @UnsupportedAppUsage
    public String[] getPackages() {
        return this.mPackages;
    }

    @UnsupportedAppUsage
    public int getUid() {
        BatteryStats.Uid uid = this.uidObj;
        if (uid == null) {
            return 0;
        }
        return uid.getUid();
    }

    public double sumPower() {
        double d = this.totalPowerMah = this.usagePowerMah + this.wifiPowerMah + this.gpsPowerMah + this.cpuPowerMah + this.sensorPowerMah + this.mobileRadioPowerMah + this.wakeLockPowerMah + this.cameraPowerMah + this.flashlightPowerMah + this.bluetoothPowerMah + this.audioPowerMah + this.videoPowerMah;
        this.totalSmearedPowerMah = this.screenPowerMah + d + this.proportionalSmearMah;
        return d;
    }

    public static enum DrainType {
        AMBIENT_DISPLAY,
        APP,
        BLUETOOTH,
        CAMERA,
        CELL,
        FLASHLIGHT,
        IDLE,
        MEMORY,
        OVERCOUNTED,
        PHONE,
        SCREEN,
        UNACCOUNTED,
        USER,
        WIFI;
        
    }

}

