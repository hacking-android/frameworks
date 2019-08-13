/*
 * Decompiled with CFR 0.145.
 */
package com.android.server.wifi;

import android.content.pm.ParceledListSlice;
import android.net.DhcpInfo;
import android.net.Network;
import android.net.wifi.IDppCallback;
import android.net.wifi.INetworkRequestMatchCallback;
import android.net.wifi.IOnWifiUsabilityStatsListener;
import android.net.wifi.ISoftApCallback;
import android.net.wifi.ITrafficStateCallback;
import android.net.wifi.IWifiManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiActivityEnergyInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiNetworkSuggestion;
import android.net.wifi.hotspot2.IProvisioningCallback;
import android.net.wifi.hotspot2.OsuProvider;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.os.IBinder;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.WorkSource;
import java.util.List;
import java.util.Map;

public class BaseWifiService
extends IWifiManager.Stub {
    private static final String TAG = BaseWifiService.class.getSimpleName();

    @Override
    public void acquireMulticastLock(IBinder iBinder, String string2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean acquireWifiLock(IBinder iBinder, int n, String string2, WorkSource workSource) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int addNetworkSuggestions(List<WifiNetworkSuggestion> list, String string2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addOnWifiUsabilityStatsListener(IBinder iBinder, IOnWifiUsabilityStatsListener iOnWifiUsabilityStatsListener, int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int addOrUpdateNetwork(WifiConfiguration wifiConfiguration, String string2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addOrUpdatePasspointConfiguration(PasspointConfiguration passpointConfiguration, String string2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deauthenticateNetwork(long l, boolean bl) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void disableEphemeralNetwork(String string2, String string3) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean disableNetwork(int n, String string2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean disconnect(String string2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean enableNetwork(int n, boolean bl, String string2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void enableTdls(String string2, boolean bl) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void enableTdlsWithMacAddress(String string2, boolean bl) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void enableVerboseLogging(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void enableWifiConnectivityManager(boolean bl) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void factoryReset(String string2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<String, Map<Integer, List<ScanResult>>> getAllMatchingFqdnsForScanResults(List<ScanResult> list) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ParceledListSlice getConfiguredNetworks(String string2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public WifiInfo getConnectionInfo(String string2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getCountryCode() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Network getCurrentNetwork() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getCurrentNetworkWpsNfcConfigurationToken() {
        throw new UnsupportedOperationException();
    }

    @Override
    public DhcpInfo getDhcpInfo() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String[] getFactoryMacAddresses() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<OsuProvider, List<ScanResult>> getMatchingOsuProviders(List<ScanResult> list) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<OsuProvider, PasspointConfiguration> getMatchingPasspointConfigsForOsuProviders(List<OsuProvider> list) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<PasspointConfiguration> getPasspointConfigurations(String string2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ParceledListSlice getPrivilegedConfiguredNetworks(String string2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ScanResult> getScanResults(String string2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getSupportedFeatures() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getVerboseLoggingLevel() {
        throw new UnsupportedOperationException();
    }

    @Override
    public WifiConfiguration getWifiApConfiguration() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getWifiApEnabledState() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<WifiConfiguration> getWifiConfigsForPasspointProfiles(List<String> list) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getWifiEnabledState() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Messenger getWifiServiceMessenger(String string2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void initializeMulticastFiltering() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isDualBandSupported() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isMulticastEnabled() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isScanAlwaysAvailable() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int matchProviderWithCurrentNetwork(String string2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean needs5GHzToAnyApBandConversion() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void notifyUserOfApBandConversion(String string2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void queryPasspointIcon(long l, String string2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean reassociate(String string2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean reconnect(String string2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void registerNetworkRequestMatchCallback(IBinder iBinder, INetworkRequestMatchCallback iNetworkRequestMatchCallback, int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void registerSoftApCallback(IBinder iBinder, ISoftApCallback iSoftApCallback, int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void registerTrafficStateCallback(IBinder iBinder, ITrafficStateCallback iTrafficStateCallback, int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void releaseMulticastLock(String string2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean releaseWifiLock(IBinder iBinder) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeNetwork(int n, String string2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int removeNetworkSuggestions(List<WifiNetworkSuggestion> list, String string2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeOnWifiUsabilityStatsListener(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removePasspointConfiguration(String string2, String string3) {
        throw new UnsupportedOperationException();
    }

    @Override
    public WifiActivityEnergyInfo reportActivityInfo() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void requestActivityInfo(ResultReceiver resultReceiver) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void restoreBackupData(byte[] arrby) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void restoreSupplicantBackupData(byte[] arrby, byte[] arrby2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte[] retrieveBackupData() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setCountryCode(String string2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setDeviceMobilityState(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean setWifiApConfiguration(WifiConfiguration wifiConfiguration, String string2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean setWifiEnabled(String string2, boolean bl) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void startDppAsConfiguratorInitiator(IBinder iBinder, String string2, int n, int n2, IDppCallback iDppCallback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void startDppAsEnrolleeInitiator(IBinder iBinder, String string2, IDppCallback iDppCallback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int startLocalOnlyHotspot(Messenger messenger, IBinder iBinder, String string2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean startScan(String string2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean startSoftAp(WifiConfiguration wifiConfiguration) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void startSubscriptionProvisioning(OsuProvider osuProvider, IProvisioningCallback iProvisioningCallback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void startWatchLocalOnlyHotspot(Messenger messenger, IBinder iBinder) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void stopDppSession() throws RemoteException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void stopLocalOnlyHotspot() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean stopSoftAp() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void stopWatchLocalOnlyHotspot() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void unregisterNetworkRequestMatchCallback(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void unregisterSoftApCallback(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void unregisterTrafficStateCallback(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateInterfaceIpState(String string2, int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateWifiLockWorkSource(IBinder iBinder, WorkSource workSource) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateWifiUsabilityScore(int n, int n2, int n3) {
        throw new UnsupportedOperationException();
    }
}

