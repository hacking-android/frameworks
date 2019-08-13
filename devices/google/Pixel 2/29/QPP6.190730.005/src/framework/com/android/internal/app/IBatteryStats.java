/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.annotation.UnsupportedAppUsage;
import android.bluetooth.BluetoothActivityEnergyInfo;
import android.net.wifi.WifiActivityEnergyInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.WorkSource;
import android.os.connectivity.CellularBatteryStats;
import android.os.connectivity.GpsBatteryStats;
import android.os.connectivity.WifiBatteryStats;
import android.os.health.HealthStatsParceler;
import android.telephony.ModemActivityInfo;
import android.telephony.SignalStrength;

public interface IBatteryStats
extends IInterface {
    public long computeBatteryTimeRemaining() throws RemoteException;

    @UnsupportedAppUsage
    public long computeChargeTimeRemaining() throws RemoteException;

    @UnsupportedAppUsage
    public long getAwakeTimeBattery() throws RemoteException;

    public long getAwakeTimePlugged() throws RemoteException;

    public CellularBatteryStats getCellularBatteryStats() throws RemoteException;

    public GpsBatteryStats getGpsBatteryStats() throws RemoteException;

    @UnsupportedAppUsage
    public byte[] getStatistics() throws RemoteException;

    public ParcelFileDescriptor getStatisticsStream() throws RemoteException;

    public WifiBatteryStats getWifiBatteryStats() throws RemoteException;

    @UnsupportedAppUsage
    public boolean isCharging() throws RemoteException;

    public void noteBleScanResults(WorkSource var1, int var2) throws RemoteException;

    public void noteBleScanStarted(WorkSource var1, boolean var2) throws RemoteException;

    public void noteBleScanStopped(WorkSource var1, boolean var2) throws RemoteException;

    public void noteBluetoothControllerActivity(BluetoothActivityEnergyInfo var1) throws RemoteException;

    public void noteChangeWakelockFromSource(WorkSource var1, int var2, String var3, String var4, int var5, WorkSource var6, int var7, String var8, String var9, int var10, boolean var11) throws RemoteException;

    public void noteConnectivityChanged(int var1, String var2) throws RemoteException;

    public void noteDeviceIdleMode(int var1, String var2, int var3) throws RemoteException;

    public void noteEvent(int var1, String var2, int var3) throws RemoteException;

    public void noteFlashlightOff(int var1) throws RemoteException;

    public void noteFlashlightOn(int var1) throws RemoteException;

    public void noteFullWifiLockAcquired(int var1) throws RemoteException;

    public void noteFullWifiLockAcquiredFromSource(WorkSource var1) throws RemoteException;

    public void noteFullWifiLockReleased(int var1) throws RemoteException;

    public void noteFullWifiLockReleasedFromSource(WorkSource var1) throws RemoteException;

    public void noteGpsChanged(WorkSource var1, WorkSource var2) throws RemoteException;

    public void noteGpsSignalQuality(int var1) throws RemoteException;

    public void noteInteractive(boolean var1) throws RemoteException;

    public void noteJobFinish(String var1, int var2, int var3, int var4, int var5) throws RemoteException;

    public void noteJobStart(String var1, int var2, int var3, int var4) throws RemoteException;

    public void noteLongPartialWakelockFinish(String var1, String var2, int var3) throws RemoteException;

    public void noteLongPartialWakelockFinishFromSource(String var1, String var2, WorkSource var3) throws RemoteException;

    public void noteLongPartialWakelockStart(String var1, String var2, int var3) throws RemoteException;

    public void noteLongPartialWakelockStartFromSource(String var1, String var2, WorkSource var3) throws RemoteException;

    public void noteMobileRadioPowerState(int var1, long var2, int var4) throws RemoteException;

    public void noteModemControllerActivity(ModemActivityInfo var1) throws RemoteException;

    public void noteNetworkInterfaceType(String var1, int var2) throws RemoteException;

    public void noteNetworkStatsEnabled() throws RemoteException;

    public void notePhoneDataConnectionState(int var1, boolean var2) throws RemoteException;

    public void notePhoneOff() throws RemoteException;

    public void notePhoneOn() throws RemoteException;

    public void notePhoneSignalStrength(SignalStrength var1) throws RemoteException;

    public void notePhoneState(int var1) throws RemoteException;

    public void noteResetAudio() throws RemoteException;

    public void noteResetBleScan() throws RemoteException;

    public void noteResetCamera() throws RemoteException;

    public void noteResetFlashlight() throws RemoteException;

    public void noteResetVideo() throws RemoteException;

    public void noteScreenBrightness(int var1) throws RemoteException;

    public void noteScreenState(int var1) throws RemoteException;

    public void noteStartAudio(int var1) throws RemoteException;

    public void noteStartCamera(int var1) throws RemoteException;

    public void noteStartSensor(int var1, int var2) throws RemoteException;

    public void noteStartVideo(int var1) throws RemoteException;

    public void noteStartWakelock(int var1, int var2, String var3, String var4, int var5, boolean var6) throws RemoteException;

    public void noteStartWakelockFromSource(WorkSource var1, int var2, String var3, String var4, int var5, boolean var6) throws RemoteException;

    public void noteStopAudio(int var1) throws RemoteException;

    public void noteStopCamera(int var1) throws RemoteException;

    public void noteStopSensor(int var1, int var2) throws RemoteException;

    public void noteStopVideo(int var1) throws RemoteException;

    public void noteStopWakelock(int var1, int var2, String var3, String var4, int var5) throws RemoteException;

    public void noteStopWakelockFromSource(WorkSource var1, int var2, String var3, String var4, int var5) throws RemoteException;

    public void noteSyncFinish(String var1, int var2) throws RemoteException;

    public void noteSyncStart(String var1, int var2) throws RemoteException;

    public void noteUserActivity(int var1, int var2) throws RemoteException;

    public void noteVibratorOff(int var1) throws RemoteException;

    public void noteVibratorOn(int var1, long var2) throws RemoteException;

    public void noteWakeUp(String var1, int var2) throws RemoteException;

    public void noteWifiBatchedScanStartedFromSource(WorkSource var1, int var2) throws RemoteException;

    public void noteWifiBatchedScanStoppedFromSource(WorkSource var1) throws RemoteException;

    public void noteWifiControllerActivity(WifiActivityEnergyInfo var1) throws RemoteException;

    public void noteWifiMulticastDisabled(int var1) throws RemoteException;

    public void noteWifiMulticastEnabled(int var1) throws RemoteException;

    public void noteWifiOff() throws RemoteException;

    public void noteWifiOn() throws RemoteException;

    public void noteWifiRadioPowerState(int var1, long var2, int var4) throws RemoteException;

    public void noteWifiRssiChanged(int var1) throws RemoteException;

    public void noteWifiRunning(WorkSource var1) throws RemoteException;

    public void noteWifiRunningChanged(WorkSource var1, WorkSource var2) throws RemoteException;

    public void noteWifiScanStarted(int var1) throws RemoteException;

    public void noteWifiScanStartedFromSource(WorkSource var1) throws RemoteException;

    public void noteWifiScanStopped(int var1) throws RemoteException;

    public void noteWifiScanStoppedFromSource(WorkSource var1) throws RemoteException;

    public void noteWifiState(int var1, String var2) throws RemoteException;

    public void noteWifiStopped(WorkSource var1) throws RemoteException;

    public void noteWifiSupplicantStateChanged(int var1, boolean var2) throws RemoteException;

    public void setBatteryState(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) throws RemoteException;

    public boolean setChargingStateUpdateDelayMillis(int var1) throws RemoteException;

    public HealthStatsParceler takeUidSnapshot(int var1) throws RemoteException;

    public HealthStatsParceler[] takeUidSnapshots(int[] var1) throws RemoteException;

    public static class Default
    implements IBatteryStats {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public long computeBatteryTimeRemaining() throws RemoteException {
            return 0L;
        }

        @Override
        public long computeChargeTimeRemaining() throws RemoteException {
            return 0L;
        }

        @Override
        public long getAwakeTimeBattery() throws RemoteException {
            return 0L;
        }

        @Override
        public long getAwakeTimePlugged() throws RemoteException {
            return 0L;
        }

        @Override
        public CellularBatteryStats getCellularBatteryStats() throws RemoteException {
            return null;
        }

        @Override
        public GpsBatteryStats getGpsBatteryStats() throws RemoteException {
            return null;
        }

        @Override
        public byte[] getStatistics() throws RemoteException {
            return null;
        }

        @Override
        public ParcelFileDescriptor getStatisticsStream() throws RemoteException {
            return null;
        }

        @Override
        public WifiBatteryStats getWifiBatteryStats() throws RemoteException {
            return null;
        }

        @Override
        public boolean isCharging() throws RemoteException {
            return false;
        }

        @Override
        public void noteBleScanResults(WorkSource workSource, int n) throws RemoteException {
        }

        @Override
        public void noteBleScanStarted(WorkSource workSource, boolean bl) throws RemoteException {
        }

        @Override
        public void noteBleScanStopped(WorkSource workSource, boolean bl) throws RemoteException {
        }

        @Override
        public void noteBluetoothControllerActivity(BluetoothActivityEnergyInfo bluetoothActivityEnergyInfo) throws RemoteException {
        }

        @Override
        public void noteChangeWakelockFromSource(WorkSource workSource, int n, String string2, String string3, int n2, WorkSource workSource2, int n3, String string4, String string5, int n4, boolean bl) throws RemoteException {
        }

        @Override
        public void noteConnectivityChanged(int n, String string2) throws RemoteException {
        }

        @Override
        public void noteDeviceIdleMode(int n, String string2, int n2) throws RemoteException {
        }

        @Override
        public void noteEvent(int n, String string2, int n2) throws RemoteException {
        }

        @Override
        public void noteFlashlightOff(int n) throws RemoteException {
        }

        @Override
        public void noteFlashlightOn(int n) throws RemoteException {
        }

        @Override
        public void noteFullWifiLockAcquired(int n) throws RemoteException {
        }

        @Override
        public void noteFullWifiLockAcquiredFromSource(WorkSource workSource) throws RemoteException {
        }

        @Override
        public void noteFullWifiLockReleased(int n) throws RemoteException {
        }

        @Override
        public void noteFullWifiLockReleasedFromSource(WorkSource workSource) throws RemoteException {
        }

        @Override
        public void noteGpsChanged(WorkSource workSource, WorkSource workSource2) throws RemoteException {
        }

        @Override
        public void noteGpsSignalQuality(int n) throws RemoteException {
        }

        @Override
        public void noteInteractive(boolean bl) throws RemoteException {
        }

        @Override
        public void noteJobFinish(String string2, int n, int n2, int n3, int n4) throws RemoteException {
        }

        @Override
        public void noteJobStart(String string2, int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void noteLongPartialWakelockFinish(String string2, String string3, int n) throws RemoteException {
        }

        @Override
        public void noteLongPartialWakelockFinishFromSource(String string2, String string3, WorkSource workSource) throws RemoteException {
        }

        @Override
        public void noteLongPartialWakelockStart(String string2, String string3, int n) throws RemoteException {
        }

        @Override
        public void noteLongPartialWakelockStartFromSource(String string2, String string3, WorkSource workSource) throws RemoteException {
        }

        @Override
        public void noteMobileRadioPowerState(int n, long l, int n2) throws RemoteException {
        }

        @Override
        public void noteModemControllerActivity(ModemActivityInfo modemActivityInfo) throws RemoteException {
        }

        @Override
        public void noteNetworkInterfaceType(String string2, int n) throws RemoteException {
        }

        @Override
        public void noteNetworkStatsEnabled() throws RemoteException {
        }

        @Override
        public void notePhoneDataConnectionState(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void notePhoneOff() throws RemoteException {
        }

        @Override
        public void notePhoneOn() throws RemoteException {
        }

        @Override
        public void notePhoneSignalStrength(SignalStrength signalStrength) throws RemoteException {
        }

        @Override
        public void notePhoneState(int n) throws RemoteException {
        }

        @Override
        public void noteResetAudio() throws RemoteException {
        }

        @Override
        public void noteResetBleScan() throws RemoteException {
        }

        @Override
        public void noteResetCamera() throws RemoteException {
        }

        @Override
        public void noteResetFlashlight() throws RemoteException {
        }

        @Override
        public void noteResetVideo() throws RemoteException {
        }

        @Override
        public void noteScreenBrightness(int n) throws RemoteException {
        }

        @Override
        public void noteScreenState(int n) throws RemoteException {
        }

        @Override
        public void noteStartAudio(int n) throws RemoteException {
        }

        @Override
        public void noteStartCamera(int n) throws RemoteException {
        }

        @Override
        public void noteStartSensor(int n, int n2) throws RemoteException {
        }

        @Override
        public void noteStartVideo(int n) throws RemoteException {
        }

        @Override
        public void noteStartWakelock(int n, int n2, String string2, String string3, int n3, boolean bl) throws RemoteException {
        }

        @Override
        public void noteStartWakelockFromSource(WorkSource workSource, int n, String string2, String string3, int n2, boolean bl) throws RemoteException {
        }

        @Override
        public void noteStopAudio(int n) throws RemoteException {
        }

        @Override
        public void noteStopCamera(int n) throws RemoteException {
        }

        @Override
        public void noteStopSensor(int n, int n2) throws RemoteException {
        }

        @Override
        public void noteStopVideo(int n) throws RemoteException {
        }

        @Override
        public void noteStopWakelock(int n, int n2, String string2, String string3, int n3) throws RemoteException {
        }

        @Override
        public void noteStopWakelockFromSource(WorkSource workSource, int n, String string2, String string3, int n2) throws RemoteException {
        }

        @Override
        public void noteSyncFinish(String string2, int n) throws RemoteException {
        }

        @Override
        public void noteSyncStart(String string2, int n) throws RemoteException {
        }

        @Override
        public void noteUserActivity(int n, int n2) throws RemoteException {
        }

        @Override
        public void noteVibratorOff(int n) throws RemoteException {
        }

        @Override
        public void noteVibratorOn(int n, long l) throws RemoteException {
        }

        @Override
        public void noteWakeUp(String string2, int n) throws RemoteException {
        }

        @Override
        public void noteWifiBatchedScanStartedFromSource(WorkSource workSource, int n) throws RemoteException {
        }

        @Override
        public void noteWifiBatchedScanStoppedFromSource(WorkSource workSource) throws RemoteException {
        }

        @Override
        public void noteWifiControllerActivity(WifiActivityEnergyInfo wifiActivityEnergyInfo) throws RemoteException {
        }

        @Override
        public void noteWifiMulticastDisabled(int n) throws RemoteException {
        }

        @Override
        public void noteWifiMulticastEnabled(int n) throws RemoteException {
        }

        @Override
        public void noteWifiOff() throws RemoteException {
        }

        @Override
        public void noteWifiOn() throws RemoteException {
        }

        @Override
        public void noteWifiRadioPowerState(int n, long l, int n2) throws RemoteException {
        }

        @Override
        public void noteWifiRssiChanged(int n) throws RemoteException {
        }

        @Override
        public void noteWifiRunning(WorkSource workSource) throws RemoteException {
        }

        @Override
        public void noteWifiRunningChanged(WorkSource workSource, WorkSource workSource2) throws RemoteException {
        }

        @Override
        public void noteWifiScanStarted(int n) throws RemoteException {
        }

        @Override
        public void noteWifiScanStartedFromSource(WorkSource workSource) throws RemoteException {
        }

        @Override
        public void noteWifiScanStopped(int n) throws RemoteException {
        }

        @Override
        public void noteWifiScanStoppedFromSource(WorkSource workSource) throws RemoteException {
        }

        @Override
        public void noteWifiState(int n, String string2) throws RemoteException {
        }

        @Override
        public void noteWifiStopped(WorkSource workSource) throws RemoteException {
        }

        @Override
        public void noteWifiSupplicantStateChanged(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void setBatteryState(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) throws RemoteException {
        }

        @Override
        public boolean setChargingStateUpdateDelayMillis(int n) throws RemoteException {
            return false;
        }

        @Override
        public HealthStatsParceler takeUidSnapshot(int n) throws RemoteException {
            return null;
        }

        @Override
        public HealthStatsParceler[] takeUidSnapshots(int[] arrn) throws RemoteException {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IBatteryStats {
        private static final String DESCRIPTOR = "com.android.internal.app.IBatteryStats";
        static final int TRANSACTION_computeBatteryTimeRemaining = 18;
        static final int TRANSACTION_computeChargeTimeRemaining = 19;
        static final int TRANSACTION_getAwakeTimeBattery = 75;
        static final int TRANSACTION_getAwakeTimePlugged = 76;
        static final int TRANSACTION_getCellularBatteryStats = 81;
        static final int TRANSACTION_getGpsBatteryStats = 83;
        static final int TRANSACTION_getStatistics = 15;
        static final int TRANSACTION_getStatisticsStream = 16;
        static final int TRANSACTION_getWifiBatteryStats = 82;
        static final int TRANSACTION_isCharging = 17;
        static final int TRANSACTION_noteBleScanResults = 80;
        static final int TRANSACTION_noteBleScanStarted = 77;
        static final int TRANSACTION_noteBleScanStopped = 78;
        static final int TRANSACTION_noteBluetoothControllerActivity = 86;
        static final int TRANSACTION_noteChangeWakelockFromSource = 28;
        static final int TRANSACTION_noteConnectivityChanged = 43;
        static final int TRANSACTION_noteDeviceIdleMode = 73;
        static final int TRANSACTION_noteEvent = 20;
        static final int TRANSACTION_noteFlashlightOff = 10;
        static final int TRANSACTION_noteFlashlightOn = 9;
        static final int TRANSACTION_noteFullWifiLockAcquired = 58;
        static final int TRANSACTION_noteFullWifiLockAcquiredFromSource = 64;
        static final int TRANSACTION_noteFullWifiLockReleased = 59;
        static final int TRANSACTION_noteFullWifiLockReleasedFromSource = 65;
        static final int TRANSACTION_noteGpsChanged = 36;
        static final int TRANSACTION_noteGpsSignalQuality = 37;
        static final int TRANSACTION_noteInteractive = 42;
        static final int TRANSACTION_noteJobFinish = 24;
        static final int TRANSACTION_noteJobStart = 23;
        static final int TRANSACTION_noteLongPartialWakelockFinish = 32;
        static final int TRANSACTION_noteLongPartialWakelockFinishFromSource = 33;
        static final int TRANSACTION_noteLongPartialWakelockStart = 30;
        static final int TRANSACTION_noteLongPartialWakelockStartFromSource = 31;
        static final int TRANSACTION_noteMobileRadioPowerState = 44;
        static final int TRANSACTION_noteModemControllerActivity = 87;
        static final int TRANSACTION_noteNetworkInterfaceType = 71;
        static final int TRANSACTION_noteNetworkStatsEnabled = 72;
        static final int TRANSACTION_notePhoneDataConnectionState = 48;
        static final int TRANSACTION_notePhoneOff = 46;
        static final int TRANSACTION_notePhoneOn = 45;
        static final int TRANSACTION_notePhoneSignalStrength = 47;
        static final int TRANSACTION_notePhoneState = 49;
        static final int TRANSACTION_noteResetAudio = 8;
        static final int TRANSACTION_noteResetBleScan = 79;
        static final int TRANSACTION_noteResetCamera = 13;
        static final int TRANSACTION_noteResetFlashlight = 14;
        static final int TRANSACTION_noteResetVideo = 7;
        static final int TRANSACTION_noteScreenBrightness = 39;
        static final int TRANSACTION_noteScreenState = 38;
        static final int TRANSACTION_noteStartAudio = 5;
        static final int TRANSACTION_noteStartCamera = 11;
        static final int TRANSACTION_noteStartSensor = 1;
        static final int TRANSACTION_noteStartVideo = 3;
        static final int TRANSACTION_noteStartWakelock = 25;
        static final int TRANSACTION_noteStartWakelockFromSource = 27;
        static final int TRANSACTION_noteStopAudio = 6;
        static final int TRANSACTION_noteStopCamera = 12;
        static final int TRANSACTION_noteStopSensor = 2;
        static final int TRANSACTION_noteStopVideo = 4;
        static final int TRANSACTION_noteStopWakelock = 26;
        static final int TRANSACTION_noteStopWakelockFromSource = 29;
        static final int TRANSACTION_noteSyncFinish = 22;
        static final int TRANSACTION_noteSyncStart = 21;
        static final int TRANSACTION_noteUserActivity = 40;
        static final int TRANSACTION_noteVibratorOff = 35;
        static final int TRANSACTION_noteVibratorOn = 34;
        static final int TRANSACTION_noteWakeUp = 41;
        static final int TRANSACTION_noteWifiBatchedScanStartedFromSource = 68;
        static final int TRANSACTION_noteWifiBatchedScanStoppedFromSource = 69;
        static final int TRANSACTION_noteWifiControllerActivity = 88;
        static final int TRANSACTION_noteWifiMulticastDisabled = 63;
        static final int TRANSACTION_noteWifiMulticastEnabled = 62;
        static final int TRANSACTION_noteWifiOff = 51;
        static final int TRANSACTION_noteWifiOn = 50;
        static final int TRANSACTION_noteWifiRadioPowerState = 70;
        static final int TRANSACTION_noteWifiRssiChanged = 57;
        static final int TRANSACTION_noteWifiRunning = 52;
        static final int TRANSACTION_noteWifiRunningChanged = 53;
        static final int TRANSACTION_noteWifiScanStarted = 60;
        static final int TRANSACTION_noteWifiScanStartedFromSource = 66;
        static final int TRANSACTION_noteWifiScanStopped = 61;
        static final int TRANSACTION_noteWifiScanStoppedFromSource = 67;
        static final int TRANSACTION_noteWifiState = 55;
        static final int TRANSACTION_noteWifiStopped = 54;
        static final int TRANSACTION_noteWifiSupplicantStateChanged = 56;
        static final int TRANSACTION_setBatteryState = 74;
        static final int TRANSACTION_setChargingStateUpdateDelayMillis = 89;
        static final int TRANSACTION_takeUidSnapshot = 84;
        static final int TRANSACTION_takeUidSnapshots = 85;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IBatteryStats asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IBatteryStats) {
                return (IBatteryStats)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IBatteryStats getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 89: {
                    return "setChargingStateUpdateDelayMillis";
                }
                case 88: {
                    return "noteWifiControllerActivity";
                }
                case 87: {
                    return "noteModemControllerActivity";
                }
                case 86: {
                    return "noteBluetoothControllerActivity";
                }
                case 85: {
                    return "takeUidSnapshots";
                }
                case 84: {
                    return "takeUidSnapshot";
                }
                case 83: {
                    return "getGpsBatteryStats";
                }
                case 82: {
                    return "getWifiBatteryStats";
                }
                case 81: {
                    return "getCellularBatteryStats";
                }
                case 80: {
                    return "noteBleScanResults";
                }
                case 79: {
                    return "noteResetBleScan";
                }
                case 78: {
                    return "noteBleScanStopped";
                }
                case 77: {
                    return "noteBleScanStarted";
                }
                case 76: {
                    return "getAwakeTimePlugged";
                }
                case 75: {
                    return "getAwakeTimeBattery";
                }
                case 74: {
                    return "setBatteryState";
                }
                case 73: {
                    return "noteDeviceIdleMode";
                }
                case 72: {
                    return "noteNetworkStatsEnabled";
                }
                case 71: {
                    return "noteNetworkInterfaceType";
                }
                case 70: {
                    return "noteWifiRadioPowerState";
                }
                case 69: {
                    return "noteWifiBatchedScanStoppedFromSource";
                }
                case 68: {
                    return "noteWifiBatchedScanStartedFromSource";
                }
                case 67: {
                    return "noteWifiScanStoppedFromSource";
                }
                case 66: {
                    return "noteWifiScanStartedFromSource";
                }
                case 65: {
                    return "noteFullWifiLockReleasedFromSource";
                }
                case 64: {
                    return "noteFullWifiLockAcquiredFromSource";
                }
                case 63: {
                    return "noteWifiMulticastDisabled";
                }
                case 62: {
                    return "noteWifiMulticastEnabled";
                }
                case 61: {
                    return "noteWifiScanStopped";
                }
                case 60: {
                    return "noteWifiScanStarted";
                }
                case 59: {
                    return "noteFullWifiLockReleased";
                }
                case 58: {
                    return "noteFullWifiLockAcquired";
                }
                case 57: {
                    return "noteWifiRssiChanged";
                }
                case 56: {
                    return "noteWifiSupplicantStateChanged";
                }
                case 55: {
                    return "noteWifiState";
                }
                case 54: {
                    return "noteWifiStopped";
                }
                case 53: {
                    return "noteWifiRunningChanged";
                }
                case 52: {
                    return "noteWifiRunning";
                }
                case 51: {
                    return "noteWifiOff";
                }
                case 50: {
                    return "noteWifiOn";
                }
                case 49: {
                    return "notePhoneState";
                }
                case 48: {
                    return "notePhoneDataConnectionState";
                }
                case 47: {
                    return "notePhoneSignalStrength";
                }
                case 46: {
                    return "notePhoneOff";
                }
                case 45: {
                    return "notePhoneOn";
                }
                case 44: {
                    return "noteMobileRadioPowerState";
                }
                case 43: {
                    return "noteConnectivityChanged";
                }
                case 42: {
                    return "noteInteractive";
                }
                case 41: {
                    return "noteWakeUp";
                }
                case 40: {
                    return "noteUserActivity";
                }
                case 39: {
                    return "noteScreenBrightness";
                }
                case 38: {
                    return "noteScreenState";
                }
                case 37: {
                    return "noteGpsSignalQuality";
                }
                case 36: {
                    return "noteGpsChanged";
                }
                case 35: {
                    return "noteVibratorOff";
                }
                case 34: {
                    return "noteVibratorOn";
                }
                case 33: {
                    return "noteLongPartialWakelockFinishFromSource";
                }
                case 32: {
                    return "noteLongPartialWakelockFinish";
                }
                case 31: {
                    return "noteLongPartialWakelockStartFromSource";
                }
                case 30: {
                    return "noteLongPartialWakelockStart";
                }
                case 29: {
                    return "noteStopWakelockFromSource";
                }
                case 28: {
                    return "noteChangeWakelockFromSource";
                }
                case 27: {
                    return "noteStartWakelockFromSource";
                }
                case 26: {
                    return "noteStopWakelock";
                }
                case 25: {
                    return "noteStartWakelock";
                }
                case 24: {
                    return "noteJobFinish";
                }
                case 23: {
                    return "noteJobStart";
                }
                case 22: {
                    return "noteSyncFinish";
                }
                case 21: {
                    return "noteSyncStart";
                }
                case 20: {
                    return "noteEvent";
                }
                case 19: {
                    return "computeChargeTimeRemaining";
                }
                case 18: {
                    return "computeBatteryTimeRemaining";
                }
                case 17: {
                    return "isCharging";
                }
                case 16: {
                    return "getStatisticsStream";
                }
                case 15: {
                    return "getStatistics";
                }
                case 14: {
                    return "noteResetFlashlight";
                }
                case 13: {
                    return "noteResetCamera";
                }
                case 12: {
                    return "noteStopCamera";
                }
                case 11: {
                    return "noteStartCamera";
                }
                case 10: {
                    return "noteFlashlightOff";
                }
                case 9: {
                    return "noteFlashlightOn";
                }
                case 8: {
                    return "noteResetAudio";
                }
                case 7: {
                    return "noteResetVideo";
                }
                case 6: {
                    return "noteStopAudio";
                }
                case 5: {
                    return "noteStartAudio";
                }
                case 4: {
                    return "noteStopVideo";
                }
                case 3: {
                    return "noteStartVideo";
                }
                case 2: {
                    return "noteStopSensor";
                }
                case 1: 
            }
            return "noteStartSensor";
        }

        public static boolean setDefaultImpl(IBatteryStats iBatteryStats) {
            if (Proxy.sDefaultImpl == null && iBatteryStats != null) {
                Proxy.sDefaultImpl = iBatteryStats;
                return true;
            }
            return false;
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        @Override
        public String getTransactionName(int n) {
            return Stub.getDefaultTransactionName(n);
        }

        @Override
        public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
            if (n != 1598968902) {
                boolean bl = false;
                boolean bl2 = false;
                boolean bl3 = false;
                boolean bl4 = false;
                boolean bl5 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 89: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setChargingStateUpdateDelayMillis(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 88: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? WifiActivityEnergyInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.noteWifiControllerActivity((WifiActivityEnergyInfo)object);
                        return true;
                    }
                    case 87: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ModemActivityInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.noteModemControllerActivity((ModemActivityInfo)object);
                        return true;
                    }
                    case 86: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? BluetoothActivityEnergyInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.noteBluetoothControllerActivity((BluetoothActivityEnergyInfo)object);
                        return true;
                    }
                    case 85: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.takeUidSnapshots(((Parcel)object).createIntArray());
                        parcel.writeNoException();
                        parcel.writeTypedArray((Parcelable[])object, 1);
                        return true;
                    }
                    case 84: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.takeUidSnapshot(((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((HealthStatsParceler)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 83: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getGpsBatteryStats();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((GpsBatteryStats)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 82: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getWifiBatteryStats();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((WifiBatteryStats)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 81: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getCellularBatteryStats();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((CellularBatteryStats)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 80: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        WorkSource workSource = ((Parcel)object).readInt() != 0 ? WorkSource.CREATOR.createFromParcel((Parcel)object) : null;
                        this.noteBleScanResults(workSource, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 79: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteResetBleScan();
                        parcel.writeNoException();
                        return true;
                    }
                    case 78: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        WorkSource workSource = ((Parcel)object).readInt() != 0 ? WorkSource.CREATOR.createFromParcel((Parcel)object) : null;
                        if (((Parcel)object).readInt() != 0) {
                            bl5 = true;
                        }
                        this.noteBleScanStopped(workSource, bl5);
                        parcel.writeNoException();
                        return true;
                    }
                    case 77: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        WorkSource workSource = ((Parcel)object).readInt() != 0 ? WorkSource.CREATOR.createFromParcel((Parcel)object) : null;
                        bl5 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl5 = true;
                        }
                        this.noteBleScanStarted(workSource, bl5);
                        parcel.writeNoException();
                        return true;
                    }
                    case 76: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.getAwakeTimePlugged();
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 75: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.getAwakeTimeBattery();
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 74: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setBatteryState(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 73: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteDeviceIdleMode(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 72: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteNetworkStatsEnabled();
                        parcel.writeNoException();
                        return true;
                    }
                    case 71: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteNetworkInterfaceType(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 70: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteWifiRadioPowerState(((Parcel)object).readInt(), ((Parcel)object).readLong(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 69: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? WorkSource.CREATOR.createFromParcel((Parcel)object) : null;
                        this.noteWifiBatchedScanStoppedFromSource((WorkSource)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 68: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        WorkSource workSource = ((Parcel)object).readInt() != 0 ? WorkSource.CREATOR.createFromParcel((Parcel)object) : null;
                        this.noteWifiBatchedScanStartedFromSource(workSource, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 67: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? WorkSource.CREATOR.createFromParcel((Parcel)object) : null;
                        this.noteWifiScanStoppedFromSource((WorkSource)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 66: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? WorkSource.CREATOR.createFromParcel((Parcel)object) : null;
                        this.noteWifiScanStartedFromSource((WorkSource)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 65: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? WorkSource.CREATOR.createFromParcel((Parcel)object) : null;
                        this.noteFullWifiLockReleasedFromSource((WorkSource)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 64: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? WorkSource.CREATOR.createFromParcel((Parcel)object) : null;
                        this.noteFullWifiLockAcquiredFromSource((WorkSource)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 63: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteWifiMulticastDisabled(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 62: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteWifiMulticastEnabled(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 61: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteWifiScanStopped(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 60: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteWifiScanStarted(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 59: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteFullWifiLockReleased(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 58: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteFullWifiLockAcquired(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 57: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteWifiRssiChanged(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 56: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl5 = bl2;
                        if (((Parcel)object).readInt() != 0) {
                            bl5 = true;
                        }
                        this.noteWifiSupplicantStateChanged(n, bl5);
                        parcel.writeNoException();
                        return true;
                    }
                    case 55: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteWifiState(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 54: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? WorkSource.CREATOR.createFromParcel((Parcel)object) : null;
                        this.noteWifiStopped((WorkSource)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 53: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        WorkSource workSource = ((Parcel)object).readInt() != 0 ? WorkSource.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? WorkSource.CREATOR.createFromParcel((Parcel)object) : null;
                        this.noteWifiRunningChanged(workSource, (WorkSource)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 52: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? WorkSource.CREATOR.createFromParcel((Parcel)object) : null;
                        this.noteWifiRunning((WorkSource)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 51: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteWifiOff();
                        parcel.writeNoException();
                        return true;
                    }
                    case 50: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteWifiOn();
                        parcel.writeNoException();
                        return true;
                    }
                    case 49: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notePhoneState(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 48: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl5 = bl3;
                        if (((Parcel)object).readInt() != 0) {
                            bl5 = true;
                        }
                        this.notePhoneDataConnectionState(n, bl5);
                        parcel.writeNoException();
                        return true;
                    }
                    case 47: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? SignalStrength.CREATOR.createFromParcel((Parcel)object) : null;
                        this.notePhoneSignalStrength((SignalStrength)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 46: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notePhoneOff();
                        parcel.writeNoException();
                        return true;
                    }
                    case 45: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notePhoneOn();
                        parcel.writeNoException();
                        return true;
                    }
                    case 44: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteMobileRadioPowerState(((Parcel)object).readInt(), ((Parcel)object).readLong(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 43: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteConnectivityChanged(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 42: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl5 = bl4;
                        if (((Parcel)object).readInt() != 0) {
                            bl5 = true;
                        }
                        this.noteInteractive(bl5);
                        parcel.writeNoException();
                        return true;
                    }
                    case 41: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteWakeUp(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 40: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteUserActivity(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 39: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteScreenBrightness(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 38: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteScreenState(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 37: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteGpsSignalQuality(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 36: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        WorkSource workSource = ((Parcel)object).readInt() != 0 ? WorkSource.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? WorkSource.CREATOR.createFromParcel((Parcel)object) : null;
                        this.noteGpsChanged(workSource, (WorkSource)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 35: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteVibratorOff(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 34: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteVibratorOn(((Parcel)object).readInt(), ((Parcel)object).readLong());
                        parcel.writeNoException();
                        return true;
                    }
                    case 33: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        String string3 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? WorkSource.CREATOR.createFromParcel((Parcel)object) : null;
                        this.noteLongPartialWakelockFinishFromSource(string2, string3, (WorkSource)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 32: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteLongPartialWakelockFinish(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 31: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string4 = ((Parcel)object).readString();
                        String string5 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? WorkSource.CREATOR.createFromParcel((Parcel)object) : null;
                        this.noteLongPartialWakelockStartFromSource(string4, string5, (WorkSource)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 30: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteLongPartialWakelockStart(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        WorkSource workSource = ((Parcel)object).readInt() != 0 ? WorkSource.CREATOR.createFromParcel((Parcel)object) : null;
                        this.noteStopWakelockFromSource(workSource, ((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        WorkSource workSource = ((Parcel)object).readInt() != 0 ? WorkSource.CREATOR.createFromParcel((Parcel)object) : null;
                        n2 = ((Parcel)object).readInt();
                        String string6 = ((Parcel)object).readString();
                        String string7 = ((Parcel)object).readString();
                        int n3 = ((Parcel)object).readInt();
                        WorkSource workSource2 = ((Parcel)object).readInt() != 0 ? WorkSource.CREATOR.createFromParcel((Parcel)object) : null;
                        int n4 = ((Parcel)object).readInt();
                        String string8 = ((Parcel)object).readString();
                        String string9 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        bl5 = ((Parcel)object).readInt() != 0;
                        this.noteChangeWakelockFromSource(workSource, n2, string6, string7, n3, workSource2, n4, string8, string9, n, bl5);
                        parcel.writeNoException();
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        WorkSource workSource = ((Parcel)object).readInt() != 0 ? WorkSource.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        String string10 = ((Parcel)object).readString();
                        String string11 = ((Parcel)object).readString();
                        n2 = ((Parcel)object).readInt();
                        bl5 = ((Parcel)object).readInt() != 0;
                        this.noteStartWakelockFromSource(workSource, n, string10, string11, n2, bl5);
                        parcel.writeNoException();
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteStopWakelock(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        int n5 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        String string12 = ((Parcel)object).readString();
                        String string13 = ((Parcel)object).readString();
                        n2 = ((Parcel)object).readInt();
                        bl5 = ((Parcel)object).readInt() != 0;
                        this.noteStartWakelock(n5, n, string12, string13, n2, bl5);
                        parcel.writeNoException();
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteJobFinish(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteJobStart(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteSyncFinish(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteSyncStart(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteEvent(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.computeChargeTimeRemaining();
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.computeBatteryTimeRemaining();
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isCharging() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getStatisticsStream();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ParcelFileDescriptor)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getStatistics();
                        parcel.writeNoException();
                        parcel.writeByteArray((byte[])object);
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteResetFlashlight();
                        parcel.writeNoException();
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteResetCamera();
                        parcel.writeNoException();
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteStopCamera(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteStartCamera(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteFlashlightOff(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteFlashlightOn(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteResetAudio();
                        parcel.writeNoException();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteResetVideo();
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteStopAudio(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteStartAudio(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteStopVideo(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteStartVideo(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.noteStopSensor(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.noteStartSensor(((Parcel)object).readInt(), ((Parcel)object).readInt());
                parcel.writeNoException();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IBatteryStats {
            public static IBatteryStats sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public long computeBatteryTimeRemaining() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().computeBatteryTimeRemaining();
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public long computeChargeTimeRemaining() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().computeChargeTimeRemaining();
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public long getAwakeTimeBattery() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(75, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getAwakeTimeBattery();
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public long getAwakeTimePlugged() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(76, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getAwakeTimePlugged();
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public CellularBatteryStats getCellularBatteryStats() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(81, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        CellularBatteryStats cellularBatteryStats = Stub.getDefaultImpl().getCellularBatteryStats();
                        parcel.recycle();
                        parcel2.recycle();
                        return cellularBatteryStats;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                CellularBatteryStats cellularBatteryStats = parcel.readInt() != 0 ? CellularBatteryStats.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return cellularBatteryStats;
            }

            @Override
            public GpsBatteryStats getGpsBatteryStats() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(83, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        GpsBatteryStats gpsBatteryStats = Stub.getDefaultImpl().getGpsBatteryStats();
                        parcel.recycle();
                        parcel2.recycle();
                        return gpsBatteryStats;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                GpsBatteryStats gpsBatteryStats = parcel.readInt() != 0 ? GpsBatteryStats.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return gpsBatteryStats;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public byte[] getStatistics() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        byte[] arrby = Stub.getDefaultImpl().getStatistics();
                        return arrby;
                    }
                    parcel2.readException();
                    byte[] arrby = parcel2.createByteArray();
                    return arrby;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public ParcelFileDescriptor getStatisticsStream() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(16, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        ParcelFileDescriptor parcelFileDescriptor = Stub.getDefaultImpl().getStatisticsStream();
                        parcel.recycle();
                        parcel2.recycle();
                        return parcelFileDescriptor;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                ParcelFileDescriptor parcelFileDescriptor = parcel.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return parcelFileDescriptor;
            }

            @Override
            public WifiBatteryStats getWifiBatteryStats() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(82, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        WifiBatteryStats wifiBatteryStats = Stub.getDefaultImpl().getWifiBatteryStats();
                        parcel.recycle();
                        parcel2.recycle();
                        return wifiBatteryStats;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                WifiBatteryStats wifiBatteryStats = parcel.readInt() != 0 ? WifiBatteryStats.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return wifiBatteryStats;
            }

            @Override
            public boolean isCharging() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(17, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isCharging();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public void noteBleScanResults(WorkSource workSource, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (workSource != null) {
                        parcel.writeInt(1);
                        workSource.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(80, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteBleScanResults(workSource, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void noteBleScanStarted(WorkSource workSource, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 1;
                    if (workSource != null) {
                        parcel.writeInt(1);
                        workSource.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(77, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteBleScanStarted(workSource, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void noteBleScanStopped(WorkSource workSource, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 1;
                    if (workSource != null) {
                        parcel.writeInt(1);
                        workSource.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(78, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteBleScanStopped(workSource, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteBluetoothControllerActivity(BluetoothActivityEnergyInfo bluetoothActivityEnergyInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bluetoothActivityEnergyInfo != null) {
                        parcel.writeInt(1);
                        bluetoothActivityEnergyInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(86, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteBluetoothControllerActivity(bluetoothActivityEnergyInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void noteChangeWakelockFromSource(WorkSource var1_1, int var2_6, String var3_7, String var4_8, int var5_9, WorkSource var6_10, int var7_11, String var8_12, String var9_13, int var10_14, boolean var11_15) throws RemoteException {
                block13 : {
                    block14 : {
                        block12 : {
                            block11 : {
                                var12_16 = Parcel.obtain();
                                var13_17 = Parcel.obtain();
                                var12_16.writeInterfaceToken("com.android.internal.app.IBatteryStats");
                                var14_18 = 1;
                                if (var1_1 == null) break block11;
                                try {
                                    var12_16.writeInt(1);
                                    var1_1.writeToParcel(var12_16, 0);
                                    ** GOTO lbl16
                                }
                                catch (Throwable var1_2) {
                                    break block13;
                                }
                            }
                            var12_16.writeInt(0);
lbl16: // 2 sources:
                            var12_16.writeInt(var2_6);
                            var12_16.writeString(var3_7);
                            var12_16.writeString(var4_8);
                            var12_16.writeInt(var5_9);
                            if (var6_10 == null) break block12;
                            var12_16.writeInt(1);
                            var6_10.writeToParcel(var12_16, 0);
                            ** GOTO lbl28
                        }
                        var12_16.writeInt(0);
lbl28: // 2 sources:
                        var12_16.writeInt(var7_11);
                        var12_16.writeString(var8_12);
                        var12_16.writeString(var9_13);
                        var12_16.writeInt(var10_14);
                        if (!var11_15) {
                            var14_18 = 0;
                        }
                        var12_16.writeInt(var14_18);
                        if (this.mRemote.transact(28, var12_16, var13_17, 0) || Stub.getDefaultImpl() == null) break block14;
                        var15_19 = Stub.getDefaultImpl();
                        try {
                            var15_19.noteChangeWakelockFromSource((WorkSource)var1_1, var2_6, var3_7, var4_8, var5_9, var6_10, var7_11, var8_12, var9_13, var10_14, var11_15);
                            var13_17.recycle();
                            var12_16.recycle();
                            return;
                        }
                        catch (Throwable var1_3) {}
                    }
                    var1_1 = var13_17;
                    var1_1.readException();
                    var1_1.recycle();
                    var12_16.recycle();
                    return;
                    break block13;
                    catch (Throwable var1_4) {
                        // empty catch block
                    }
                }
                var13_17.recycle();
                var12_16.recycle();
                throw var1_5;
            }

            @Override
            public void noteConnectivityChanged(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(43, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteConnectivityChanged(n, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteDeviceIdleMode(int n, String string2, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(73, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteDeviceIdleMode(n, string2, n2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteEvent(int n, String string2, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteEvent(n, string2, n2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteFlashlightOff(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteFlashlightOff(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteFlashlightOn(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteFlashlightOn(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteFullWifiLockAcquired(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(58, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteFullWifiLockAcquired(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteFullWifiLockAcquiredFromSource(WorkSource workSource) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (workSource != null) {
                        parcel.writeInt(1);
                        workSource.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(64, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteFullWifiLockAcquiredFromSource(workSource);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteFullWifiLockReleased(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(59, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteFullWifiLockReleased(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteFullWifiLockReleasedFromSource(WorkSource workSource) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (workSource != null) {
                        parcel.writeInt(1);
                        workSource.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(65, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteFullWifiLockReleasedFromSource(workSource);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteGpsChanged(WorkSource workSource, WorkSource workSource2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (workSource != null) {
                        parcel.writeInt(1);
                        workSource.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (workSource2 != null) {
                        parcel.writeInt(1);
                        workSource2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(36, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteGpsChanged(workSource, workSource2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteGpsSignalQuality(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(37, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteGpsSignalQuality(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteInteractive(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(42, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteInteractive(bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteJobFinish(String string2, int n, int n2, int n3, int n4) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeInt(n4);
                    if (!this.mRemote.transact(24, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteJobFinish(string2, n, n2, n3, n4);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteJobStart(String string2, int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(23, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteJobStart(string2, n, n2, n3);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteLongPartialWakelockFinish(String string2, String string3, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(32, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteLongPartialWakelockFinish(string2, string3, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteLongPartialWakelockFinishFromSource(String string2, String string3, WorkSource workSource) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (workSource != null) {
                        parcel.writeInt(1);
                        workSource.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(33, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteLongPartialWakelockFinishFromSource(string2, string3, workSource);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteLongPartialWakelockStart(String string2, String string3, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(30, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteLongPartialWakelockStart(string2, string3, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteLongPartialWakelockStartFromSource(String string2, String string3, WorkSource workSource) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (workSource != null) {
                        parcel.writeInt(1);
                        workSource.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(31, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteLongPartialWakelockStartFromSource(string2, string3, workSource);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteMobileRadioPowerState(int n, long l, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeLong(l);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(44, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteMobileRadioPowerState(n, l, n2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteModemControllerActivity(ModemActivityInfo modemActivityInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (modemActivityInfo != null) {
                        parcel.writeInt(1);
                        modemActivityInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(87, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteModemControllerActivity(modemActivityInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void noteNetworkInterfaceType(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(71, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteNetworkInterfaceType(string2, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteNetworkStatsEnabled() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(72, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteNetworkStatsEnabled();
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notePhoneDataConnectionState(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(48, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notePhoneDataConnectionState(n, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notePhoneOff() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(46, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notePhoneOff();
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notePhoneOn() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(45, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notePhoneOn();
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notePhoneSignalStrength(SignalStrength signalStrength) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (signalStrength != null) {
                        parcel.writeInt(1);
                        signalStrength.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(47, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notePhoneSignalStrength(signalStrength);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notePhoneState(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(49, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notePhoneState(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteResetAudio() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteResetAudio();
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteResetBleScan() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(79, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteResetBleScan();
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteResetCamera() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteResetCamera();
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteResetFlashlight() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteResetFlashlight();
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteResetVideo() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteResetVideo();
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteScreenBrightness(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(39, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteScreenBrightness(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteScreenState(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(38, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteScreenState(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteStartAudio(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteStartAudio(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteStartCamera(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteStartCamera(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteStartSensor(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteStartSensor(n, n2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteStartVideo(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteStartVideo(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void noteStartWakelock(int n, int n2, String string2, String string3, int n3, boolean bl) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                void var3_11;
                block16 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeString(string3);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeInt(n3);
                        int n4 = bl ? 1 : 0;
                        parcel2.writeInt(n4);
                    }
                    catch (Throwable throwable) {}
                    try {
                        if (!this.mRemote.transact(25, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().noteStartWakelock(n, n2, string2, string3, n3, bl);
                            parcel.recycle();
                            parcel2.recycle();
                            return;
                        }
                        parcel.readException();
                        parcel.recycle();
                        parcel2.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block16;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var3_11;
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void noteStartWakelockFromSource(WorkSource workSource, int n, String string2, String string3, int n2, boolean bl) throws RemoteException {
                Parcel parcel;
                void var1_8;
                Parcel parcel2;
                block17 : {
                    int n3;
                    block16 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        n3 = 1;
                        if (workSource != null) {
                            parcel2.writeInt(1);
                            workSource.writeToParcel(parcel2, 0);
                            break block16;
                        }
                        parcel2.writeInt(0);
                    }
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel2.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel2.writeString(string3);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel2.writeInt(n2);
                        if (!bl) {
                            n3 = 0;
                        }
                        parcel2.writeInt(n3);
                    }
                    catch (Throwable throwable) {}
                    try {
                        if (!this.mRemote.transact(27, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().noteStartWakelockFromSource(workSource, n, string2, string3, n2, bl);
                            parcel.recycle();
                            parcel2.recycle();
                            return;
                        }
                        parcel.readException();
                        parcel.recycle();
                        parcel2.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block17;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_8;
            }

            @Override
            public void noteStopAudio(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteStopAudio(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteStopCamera(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteStopCamera(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteStopSensor(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteStopSensor(n, n2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteStopVideo(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteStopVideo(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteStopWakelock(int n, int n2, String string2, String string3, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(26, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteStopWakelock(n, n2, string2, string3, n3);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteStopWakelockFromSource(WorkSource workSource, int n, String string2, String string3, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (workSource != null) {
                        parcel.writeInt(1);
                        workSource.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(29, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteStopWakelockFromSource(workSource, n, string2, string3, n2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteSyncFinish(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(22, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteSyncFinish(string2, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteSyncStart(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(21, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteSyncStart(string2, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteUserActivity(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(40, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteUserActivity(n, n2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteVibratorOff(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(35, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteVibratorOff(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteVibratorOn(int n, long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(34, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteVibratorOn(n, l);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteWakeUp(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(41, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteWakeUp(string2, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteWifiBatchedScanStartedFromSource(WorkSource workSource, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (workSource != null) {
                        parcel.writeInt(1);
                        workSource.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(68, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteWifiBatchedScanStartedFromSource(workSource, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteWifiBatchedScanStoppedFromSource(WorkSource workSource) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (workSource != null) {
                        parcel.writeInt(1);
                        workSource.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(69, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteWifiBatchedScanStoppedFromSource(workSource);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteWifiControllerActivity(WifiActivityEnergyInfo wifiActivityEnergyInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (wifiActivityEnergyInfo != null) {
                        parcel.writeInt(1);
                        wifiActivityEnergyInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(88, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteWifiControllerActivity(wifiActivityEnergyInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void noteWifiMulticastDisabled(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(63, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteWifiMulticastDisabled(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteWifiMulticastEnabled(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(62, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteWifiMulticastEnabled(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteWifiOff() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(51, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteWifiOff();
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteWifiOn() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(50, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteWifiOn();
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteWifiRadioPowerState(int n, long l, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeLong(l);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(70, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteWifiRadioPowerState(n, l, n2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteWifiRssiChanged(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(57, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteWifiRssiChanged(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteWifiRunning(WorkSource workSource) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (workSource != null) {
                        parcel.writeInt(1);
                        workSource.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(52, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteWifiRunning(workSource);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteWifiRunningChanged(WorkSource workSource, WorkSource workSource2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (workSource != null) {
                        parcel.writeInt(1);
                        workSource.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (workSource2 != null) {
                        parcel.writeInt(1);
                        workSource2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(53, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteWifiRunningChanged(workSource, workSource2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteWifiScanStarted(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(60, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteWifiScanStarted(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteWifiScanStartedFromSource(WorkSource workSource) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (workSource != null) {
                        parcel.writeInt(1);
                        workSource.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(66, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteWifiScanStartedFromSource(workSource);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteWifiScanStopped(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(61, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteWifiScanStopped(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteWifiScanStoppedFromSource(WorkSource workSource) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (workSource != null) {
                        parcel.writeInt(1);
                        workSource.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(67, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteWifiScanStoppedFromSource(workSource);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteWifiState(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(55, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteWifiState(n, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteWifiStopped(WorkSource workSource) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (workSource != null) {
                        parcel.writeInt(1);
                        workSource.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(54, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteWifiStopped(workSource);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void noteWifiSupplicantStateChanged(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(56, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().noteWifiSupplicantStateChanged(n, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void setBatteryState(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) throws RemoteException {
                void var11_16;
                Parcel parcel2;
                Parcel parcel;
                block12 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeInt(n3);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeInt(n4);
                        parcel2.writeInt(n5);
                        parcel2.writeInt(n6);
                        parcel2.writeInt(n7);
                        parcel2.writeInt(n8);
                        if (!this.mRemote.transact(74, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().setBatteryState(n, n2, n3, n4, n5, n6, n7, n8);
                            parcel.recycle();
                            parcel2.recycle();
                            return;
                        }
                        parcel.readException();
                        parcel.recycle();
                        parcel2.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block12;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var11_16;
            }

            @Override
            public boolean setChargingStateUpdateDelayMillis(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(89, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().setChargingStateUpdateDelayMillis(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public HealthStatsParceler takeUidSnapshot(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(84, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        HealthStatsParceler healthStatsParceler = Stub.getDefaultImpl().takeUidSnapshot(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return healthStatsParceler;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                HealthStatsParceler healthStatsParceler = parcel2.readInt() != 0 ? HealthStatsParceler.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return healthStatsParceler;
            }

            @Override
            public HealthStatsParceler[] takeUidSnapshots(int[] arrobject) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeIntArray((int[])arrobject);
                    if (!this.mRemote.transact(85, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrobject = Stub.getDefaultImpl().takeUidSnapshots((int[])arrobject);
                        return arrobject;
                    }
                    parcel2.readException();
                    arrobject = parcel2.createTypedArray(HealthStatsParceler.CREATOR);
                    return arrobject;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

