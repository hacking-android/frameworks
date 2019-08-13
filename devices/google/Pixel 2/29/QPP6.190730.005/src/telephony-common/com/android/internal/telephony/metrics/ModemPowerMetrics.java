/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.RemoteException
 *  android.os.ServiceManager
 *  android.os.connectivity.CellularBatteryStats
 *  com.android.internal.app.IBatteryStats
 *  com.android.internal.app.IBatteryStats$Stub
 */
package com.android.internal.telephony.metrics;

import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.connectivity.CellularBatteryStats;
import com.android.internal.app.IBatteryStats;
import com.android.internal.telephony.nano.TelephonyProto;

public class ModemPowerMetrics {
    private final IBatteryStats mBatteryStats = IBatteryStats.Stub.asInterface((IBinder)ServiceManager.getService((String)"batterystats"));

    private CellularBatteryStats getStats() {
        try {
            CellularBatteryStats cellularBatteryStats = this.mBatteryStats.getCellularBatteryStats();
            return cellularBatteryStats;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    public TelephonyProto.ModemPowerStats buildProto() {
        TelephonyProto.ModemPowerStats modemPowerStats = new TelephonyProto.ModemPowerStats();
        CellularBatteryStats cellularBatteryStats = this.getStats();
        if (cellularBatteryStats != null) {
            modemPowerStats.loggingDurationMs = cellularBatteryStats.getLoggingDurationMs();
            modemPowerStats.energyConsumedMah = (double)cellularBatteryStats.getEnergyConsumedMaMs() / 3600000.0;
            modemPowerStats.numPacketsTx = cellularBatteryStats.getNumPacketsTx();
            modemPowerStats.cellularKernelActiveTimeMs = cellularBatteryStats.getKernelActiveTimeMs();
            if (cellularBatteryStats.getTimeInRxSignalStrengthLevelMs() != null && cellularBatteryStats.getTimeInRxSignalStrengthLevelMs().length > 0) {
                modemPowerStats.timeInVeryPoorRxSignalLevelMs = cellularBatteryStats.getTimeInRxSignalStrengthLevelMs()[0];
            }
            modemPowerStats.sleepTimeMs = cellularBatteryStats.getSleepTimeMs();
            modemPowerStats.idleTimeMs = cellularBatteryStats.getIdleTimeMs();
            modemPowerStats.rxTimeMs = cellularBatteryStats.getRxTimeMs();
            long[] arrl = cellularBatteryStats.getTxTimeMs();
            modemPowerStats.txTimeMs = new long[arrl.length];
            System.arraycopy(arrl, 0, modemPowerStats.txTimeMs, 0, arrl.length);
            modemPowerStats.numBytesTx = cellularBatteryStats.getNumBytesTx();
            modemPowerStats.numPacketsRx = cellularBatteryStats.getNumPacketsRx();
            modemPowerStats.numBytesRx = cellularBatteryStats.getNumBytesRx();
            arrl = cellularBatteryStats.getTimeInRatMs();
            modemPowerStats.timeInRatMs = new long[arrl.length];
            System.arraycopy(arrl, 0, modemPowerStats.timeInRatMs, 0, arrl.length);
            arrl = cellularBatteryStats.getTimeInRxSignalStrengthLevelMs();
            modemPowerStats.timeInRxSignalStrengthLevelMs = new long[arrl.length];
            System.arraycopy(arrl, 0, modemPowerStats.timeInRxSignalStrengthLevelMs, 0, arrl.length);
            modemPowerStats.monitoredRailEnergyConsumedMah = (double)cellularBatteryStats.getMonitoredRailChargeConsumedMaMs() / 3600000.0;
        }
        return modemPowerStats;
    }
}

