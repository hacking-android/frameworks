/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.annotation.UnsupportedAppUsage;
import android.content.pm.ParceledListSlice;
import android.net.DhcpInfo;
import android.net.Network;
import android.net.wifi.IDppCallback;
import android.net.wifi.INetworkRequestMatchCallback;
import android.net.wifi.IOnWifiUsabilityStatsListener;
import android.net.wifi.ISoftApCallback;
import android.net.wifi.ITrafficStateCallback;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiActivityEnergyInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiNetworkSuggestion;
import android.net.wifi.hotspot2.IProvisioningCallback;
import android.net.wifi.hotspot2.OsuProvider;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.WorkSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IWifiManager
extends IInterface {
    public void acquireMulticastLock(IBinder var1, String var2) throws RemoteException;

    public boolean acquireWifiLock(IBinder var1, int var2, String var3, WorkSource var4) throws RemoteException;

    public int addNetworkSuggestions(List<WifiNetworkSuggestion> var1, String var2) throws RemoteException;

    public void addOnWifiUsabilityStatsListener(IBinder var1, IOnWifiUsabilityStatsListener var2, int var3) throws RemoteException;

    public int addOrUpdateNetwork(WifiConfiguration var1, String var2) throws RemoteException;

    public boolean addOrUpdatePasspointConfiguration(PasspointConfiguration var1, String var2) throws RemoteException;

    public void deauthenticateNetwork(long var1, boolean var3) throws RemoteException;

    public void disableEphemeralNetwork(String var1, String var2) throws RemoteException;

    public boolean disableNetwork(int var1, String var2) throws RemoteException;

    public boolean disconnect(String var1) throws RemoteException;

    public boolean enableNetwork(int var1, boolean var2, String var3) throws RemoteException;

    public void enableTdls(String var1, boolean var2) throws RemoteException;

    public void enableTdlsWithMacAddress(String var1, boolean var2) throws RemoteException;

    public void enableVerboseLogging(int var1) throws RemoteException;

    public void enableWifiConnectivityManager(boolean var1) throws RemoteException;

    public void factoryReset(String var1) throws RemoteException;

    public Map getAllMatchingFqdnsForScanResults(List<ScanResult> var1) throws RemoteException;

    public ParceledListSlice getConfiguredNetworks(String var1) throws RemoteException;

    public WifiInfo getConnectionInfo(String var1) throws RemoteException;

    public String getCountryCode() throws RemoteException;

    @UnsupportedAppUsage
    public Network getCurrentNetwork() throws RemoteException;

    public String getCurrentNetworkWpsNfcConfigurationToken() throws RemoteException;

    public DhcpInfo getDhcpInfo() throws RemoteException;

    public String[] getFactoryMacAddresses() throws RemoteException;

    public Map getMatchingOsuProviders(List<ScanResult> var1) throws RemoteException;

    public Map getMatchingPasspointConfigsForOsuProviders(List<OsuProvider> var1) throws RemoteException;

    public List<PasspointConfiguration> getPasspointConfigurations(String var1) throws RemoteException;

    public ParceledListSlice getPrivilegedConfiguredNetworks(String var1) throws RemoteException;

    public List<ScanResult> getScanResults(String var1) throws RemoteException;

    public long getSupportedFeatures() throws RemoteException;

    public int getVerboseLoggingLevel() throws RemoteException;

    @UnsupportedAppUsage
    public WifiConfiguration getWifiApConfiguration() throws RemoteException;

    @UnsupportedAppUsage
    public int getWifiApEnabledState() throws RemoteException;

    public List<WifiConfiguration> getWifiConfigsForPasspointProfiles(List<String> var1) throws RemoteException;

    public int getWifiEnabledState() throws RemoteException;

    public Messenger getWifiServiceMessenger(String var1) throws RemoteException;

    public void initializeMulticastFiltering() throws RemoteException;

    public boolean isDualBandSupported() throws RemoteException;

    public boolean isMulticastEnabled() throws RemoteException;

    public boolean isScanAlwaysAvailable() throws RemoteException;

    public int matchProviderWithCurrentNetwork(String var1) throws RemoteException;

    public boolean needs5GHzToAnyApBandConversion() throws RemoteException;

    public void notifyUserOfApBandConversion(String var1) throws RemoteException;

    public void queryPasspointIcon(long var1, String var3) throws RemoteException;

    public boolean reassociate(String var1) throws RemoteException;

    public boolean reconnect(String var1) throws RemoteException;

    public void registerNetworkRequestMatchCallback(IBinder var1, INetworkRequestMatchCallback var2, int var3) throws RemoteException;

    public void registerSoftApCallback(IBinder var1, ISoftApCallback var2, int var3) throws RemoteException;

    public void registerTrafficStateCallback(IBinder var1, ITrafficStateCallback var2, int var3) throws RemoteException;

    public void releaseMulticastLock(String var1) throws RemoteException;

    public boolean releaseWifiLock(IBinder var1) throws RemoteException;

    public boolean removeNetwork(int var1, String var2) throws RemoteException;

    public int removeNetworkSuggestions(List<WifiNetworkSuggestion> var1, String var2) throws RemoteException;

    public void removeOnWifiUsabilityStatsListener(int var1) throws RemoteException;

    public boolean removePasspointConfiguration(String var1, String var2) throws RemoteException;

    public WifiActivityEnergyInfo reportActivityInfo() throws RemoteException;

    public void requestActivityInfo(ResultReceiver var1) throws RemoteException;

    public void restoreBackupData(byte[] var1) throws RemoteException;

    public void restoreSupplicantBackupData(byte[] var1, byte[] var2) throws RemoteException;

    public byte[] retrieveBackupData() throws RemoteException;

    public void setCountryCode(String var1) throws RemoteException;

    public void setDeviceMobilityState(int var1) throws RemoteException;

    public boolean setWifiApConfiguration(WifiConfiguration var1, String var2) throws RemoteException;

    public boolean setWifiEnabled(String var1, boolean var2) throws RemoteException;

    public void startDppAsConfiguratorInitiator(IBinder var1, String var2, int var3, int var4, IDppCallback var5) throws RemoteException;

    public void startDppAsEnrolleeInitiator(IBinder var1, String var2, IDppCallback var3) throws RemoteException;

    public int startLocalOnlyHotspot(Messenger var1, IBinder var2, String var3) throws RemoteException;

    public boolean startScan(String var1) throws RemoteException;

    public boolean startSoftAp(WifiConfiguration var1) throws RemoteException;

    public void startSubscriptionProvisioning(OsuProvider var1, IProvisioningCallback var2) throws RemoteException;

    public void startWatchLocalOnlyHotspot(Messenger var1, IBinder var2) throws RemoteException;

    public void stopDppSession() throws RemoteException;

    public void stopLocalOnlyHotspot() throws RemoteException;

    public boolean stopSoftAp() throws RemoteException;

    public void stopWatchLocalOnlyHotspot() throws RemoteException;

    public void unregisterNetworkRequestMatchCallback(int var1) throws RemoteException;

    public void unregisterSoftApCallback(int var1) throws RemoteException;

    public void unregisterTrafficStateCallback(int var1) throws RemoteException;

    public void updateInterfaceIpState(String var1, int var2) throws RemoteException;

    public void updateWifiLockWorkSource(IBinder var1, WorkSource var2) throws RemoteException;

    public void updateWifiUsabilityScore(int var1, int var2, int var3) throws RemoteException;

    public static class Default
    implements IWifiManager {
        @Override
        public void acquireMulticastLock(IBinder iBinder, String string2) throws RemoteException {
        }

        @Override
        public boolean acquireWifiLock(IBinder iBinder, int n, String string2, WorkSource workSource) throws RemoteException {
            return false;
        }

        @Override
        public int addNetworkSuggestions(List<WifiNetworkSuggestion> list, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public void addOnWifiUsabilityStatsListener(IBinder iBinder, IOnWifiUsabilityStatsListener iOnWifiUsabilityStatsListener, int n) throws RemoteException {
        }

        @Override
        public int addOrUpdateNetwork(WifiConfiguration wifiConfiguration, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public boolean addOrUpdatePasspointConfiguration(PasspointConfiguration passpointConfiguration, String string2) throws RemoteException {
            return false;
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void deauthenticateNetwork(long l, boolean bl) throws RemoteException {
        }

        @Override
        public void disableEphemeralNetwork(String string2, String string3) throws RemoteException {
        }

        @Override
        public boolean disableNetwork(int n, String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean disconnect(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean enableNetwork(int n, boolean bl, String string2) throws RemoteException {
            return false;
        }

        @Override
        public void enableTdls(String string2, boolean bl) throws RemoteException {
        }

        @Override
        public void enableTdlsWithMacAddress(String string2, boolean bl) throws RemoteException {
        }

        @Override
        public void enableVerboseLogging(int n) throws RemoteException {
        }

        @Override
        public void enableWifiConnectivityManager(boolean bl) throws RemoteException {
        }

        @Override
        public void factoryReset(String string2) throws RemoteException {
        }

        @Override
        public Map getAllMatchingFqdnsForScanResults(List<ScanResult> list) throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice getConfiguredNetworks(String string2) throws RemoteException {
            return null;
        }

        @Override
        public WifiInfo getConnectionInfo(String string2) throws RemoteException {
            return null;
        }

        @Override
        public String getCountryCode() throws RemoteException {
            return null;
        }

        @Override
        public Network getCurrentNetwork() throws RemoteException {
            return null;
        }

        @Override
        public String getCurrentNetworkWpsNfcConfigurationToken() throws RemoteException {
            return null;
        }

        @Override
        public DhcpInfo getDhcpInfo() throws RemoteException {
            return null;
        }

        @Override
        public String[] getFactoryMacAddresses() throws RemoteException {
            return null;
        }

        @Override
        public Map getMatchingOsuProviders(List<ScanResult> list) throws RemoteException {
            return null;
        }

        @Override
        public Map getMatchingPasspointConfigsForOsuProviders(List<OsuProvider> list) throws RemoteException {
            return null;
        }

        @Override
        public List<PasspointConfiguration> getPasspointConfigurations(String string2) throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice getPrivilegedConfiguredNetworks(String string2) throws RemoteException {
            return null;
        }

        @Override
        public List<ScanResult> getScanResults(String string2) throws RemoteException {
            return null;
        }

        @Override
        public long getSupportedFeatures() throws RemoteException {
            return 0L;
        }

        @Override
        public int getVerboseLoggingLevel() throws RemoteException {
            return 0;
        }

        @Override
        public WifiConfiguration getWifiApConfiguration() throws RemoteException {
            return null;
        }

        @Override
        public int getWifiApEnabledState() throws RemoteException {
            return 0;
        }

        @Override
        public List<WifiConfiguration> getWifiConfigsForPasspointProfiles(List<String> list) throws RemoteException {
            return null;
        }

        @Override
        public int getWifiEnabledState() throws RemoteException {
            return 0;
        }

        @Override
        public Messenger getWifiServiceMessenger(String string2) throws RemoteException {
            return null;
        }

        @Override
        public void initializeMulticastFiltering() throws RemoteException {
        }

        @Override
        public boolean isDualBandSupported() throws RemoteException {
            return false;
        }

        @Override
        public boolean isMulticastEnabled() throws RemoteException {
            return false;
        }

        @Override
        public boolean isScanAlwaysAvailable() throws RemoteException {
            return false;
        }

        @Override
        public int matchProviderWithCurrentNetwork(String string2) throws RemoteException {
            return 0;
        }

        @Override
        public boolean needs5GHzToAnyApBandConversion() throws RemoteException {
            return false;
        }

        @Override
        public void notifyUserOfApBandConversion(String string2) throws RemoteException {
        }

        @Override
        public void queryPasspointIcon(long l, String string2) throws RemoteException {
        }

        @Override
        public boolean reassociate(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean reconnect(String string2) throws RemoteException {
            return false;
        }

        @Override
        public void registerNetworkRequestMatchCallback(IBinder iBinder, INetworkRequestMatchCallback iNetworkRequestMatchCallback, int n) throws RemoteException {
        }

        @Override
        public void registerSoftApCallback(IBinder iBinder, ISoftApCallback iSoftApCallback, int n) throws RemoteException {
        }

        @Override
        public void registerTrafficStateCallback(IBinder iBinder, ITrafficStateCallback iTrafficStateCallback, int n) throws RemoteException {
        }

        @Override
        public void releaseMulticastLock(String string2) throws RemoteException {
        }

        @Override
        public boolean releaseWifiLock(IBinder iBinder) throws RemoteException {
            return false;
        }

        @Override
        public boolean removeNetwork(int n, String string2) throws RemoteException {
            return false;
        }

        @Override
        public int removeNetworkSuggestions(List<WifiNetworkSuggestion> list, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public void removeOnWifiUsabilityStatsListener(int n) throws RemoteException {
        }

        @Override
        public boolean removePasspointConfiguration(String string2, String string3) throws RemoteException {
            return false;
        }

        @Override
        public WifiActivityEnergyInfo reportActivityInfo() throws RemoteException {
            return null;
        }

        @Override
        public void requestActivityInfo(ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override
        public void restoreBackupData(byte[] arrby) throws RemoteException {
        }

        @Override
        public void restoreSupplicantBackupData(byte[] arrby, byte[] arrby2) throws RemoteException {
        }

        @Override
        public byte[] retrieveBackupData() throws RemoteException {
            return null;
        }

        @Override
        public void setCountryCode(String string2) throws RemoteException {
        }

        @Override
        public void setDeviceMobilityState(int n) throws RemoteException {
        }

        @Override
        public boolean setWifiApConfiguration(WifiConfiguration wifiConfiguration, String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean setWifiEnabled(String string2, boolean bl) throws RemoteException {
            return false;
        }

        @Override
        public void startDppAsConfiguratorInitiator(IBinder iBinder, String string2, int n, int n2, IDppCallback iDppCallback) throws RemoteException {
        }

        @Override
        public void startDppAsEnrolleeInitiator(IBinder iBinder, String string2, IDppCallback iDppCallback) throws RemoteException {
        }

        @Override
        public int startLocalOnlyHotspot(Messenger messenger, IBinder iBinder, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public boolean startScan(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean startSoftAp(WifiConfiguration wifiConfiguration) throws RemoteException {
            return false;
        }

        @Override
        public void startSubscriptionProvisioning(OsuProvider osuProvider, IProvisioningCallback iProvisioningCallback) throws RemoteException {
        }

        @Override
        public void startWatchLocalOnlyHotspot(Messenger messenger, IBinder iBinder) throws RemoteException {
        }

        @Override
        public void stopDppSession() throws RemoteException {
        }

        @Override
        public void stopLocalOnlyHotspot() throws RemoteException {
        }

        @Override
        public boolean stopSoftAp() throws RemoteException {
            return false;
        }

        @Override
        public void stopWatchLocalOnlyHotspot() throws RemoteException {
        }

        @Override
        public void unregisterNetworkRequestMatchCallback(int n) throws RemoteException {
        }

        @Override
        public void unregisterSoftApCallback(int n) throws RemoteException {
        }

        @Override
        public void unregisterTrafficStateCallback(int n) throws RemoteException {
        }

        @Override
        public void updateInterfaceIpState(String string2, int n) throws RemoteException {
        }

        @Override
        public void updateWifiLockWorkSource(IBinder iBinder, WorkSource workSource) throws RemoteException {
        }

        @Override
        public void updateWifiUsabilityScore(int n, int n2, int n3) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IWifiManager {
        private static final String DESCRIPTOR = "android.net.wifi.IWifiManager";
        static final int TRANSACTION_acquireMulticastLock = 39;
        static final int TRANSACTION_acquireWifiLock = 34;
        static final int TRANSACTION_addNetworkSuggestions = 74;
        static final int TRANSACTION_addOnWifiUsabilityStatsListener = 68;
        static final int TRANSACTION_addOrUpdateNetwork = 9;
        static final int TRANSACTION_addOrUpdatePasspointConfiguration = 10;
        static final int TRANSACTION_deauthenticateNetwork = 16;
        static final int TRANSACTION_disableEphemeralNetwork = 59;
        static final int TRANSACTION_disableNetwork = 19;
        static final int TRANSACTION_disconnect = 22;
        static final int TRANSACTION_enableNetwork = 18;
        static final int TRANSACTION_enableTdls = 53;
        static final int TRANSACTION_enableTdlsWithMacAddress = 54;
        static final int TRANSACTION_enableVerboseLogging = 56;
        static final int TRANSACTION_enableWifiConnectivityManager = 58;
        static final int TRANSACTION_factoryReset = 60;
        static final int TRANSACTION_getAllMatchingFqdnsForScanResults = 6;
        static final int TRANSACTION_getConfiguredNetworks = 4;
        static final int TRANSACTION_getConnectionInfo = 25;
        static final int TRANSACTION_getCountryCode = 29;
        static final int TRANSACTION_getCurrentNetwork = 61;
        static final int TRANSACTION_getCurrentNetworkWpsNfcConfigurationToken = 55;
        static final int TRANSACTION_getDhcpInfo = 32;
        static final int TRANSACTION_getFactoryMacAddresses = 76;
        static final int TRANSACTION_getMatchingOsuProviders = 7;
        static final int TRANSACTION_getMatchingPasspointConfigsForOsuProviders = 8;
        static final int TRANSACTION_getPasspointConfigurations = 12;
        static final int TRANSACTION_getPrivilegedConfiguredNetworks = 5;
        static final int TRANSACTION_getScanResults = 21;
        static final int TRANSACTION_getSupportedFeatures = 1;
        static final int TRANSACTION_getVerboseLoggingLevel = 57;
        static final int TRANSACTION_getWifiApConfiguration = 49;
        static final int TRANSACTION_getWifiApEnabledState = 48;
        static final int TRANSACTION_getWifiConfigsForPasspointProfiles = 13;
        static final int TRANSACTION_getWifiEnabledState = 27;
        static final int TRANSACTION_getWifiServiceMessenger = 52;
        static final int TRANSACTION_initializeMulticastFiltering = 37;
        static final int TRANSACTION_isDualBandSupported = 30;
        static final int TRANSACTION_isMulticastEnabled = 38;
        static final int TRANSACTION_isScanAlwaysAvailable = 33;
        static final int TRANSACTION_matchProviderWithCurrentNetwork = 15;
        static final int TRANSACTION_needs5GHzToAnyApBandConversion = 31;
        static final int TRANSACTION_notifyUserOfApBandConversion = 51;
        static final int TRANSACTION_queryPasspointIcon = 14;
        static final int TRANSACTION_reassociate = 24;
        static final int TRANSACTION_reconnect = 23;
        static final int TRANSACTION_registerNetworkRequestMatchCallback = 72;
        static final int TRANSACTION_registerSoftApCallback = 66;
        static final int TRANSACTION_registerTrafficStateCallback = 70;
        static final int TRANSACTION_releaseMulticastLock = 40;
        static final int TRANSACTION_releaseWifiLock = 36;
        static final int TRANSACTION_removeNetwork = 17;
        static final int TRANSACTION_removeNetworkSuggestions = 75;
        static final int TRANSACTION_removeOnWifiUsabilityStatsListener = 69;
        static final int TRANSACTION_removePasspointConfiguration = 11;
        static final int TRANSACTION_reportActivityInfo = 2;
        static final int TRANSACTION_requestActivityInfo = 3;
        static final int TRANSACTION_restoreBackupData = 63;
        static final int TRANSACTION_restoreSupplicantBackupData = 64;
        static final int TRANSACTION_retrieveBackupData = 62;
        static final int TRANSACTION_setCountryCode = 28;
        static final int TRANSACTION_setDeviceMobilityState = 77;
        static final int TRANSACTION_setWifiApConfiguration = 50;
        static final int TRANSACTION_setWifiEnabled = 26;
        static final int TRANSACTION_startDppAsConfiguratorInitiator = 78;
        static final int TRANSACTION_startDppAsEnrolleeInitiator = 79;
        static final int TRANSACTION_startLocalOnlyHotspot = 44;
        static final int TRANSACTION_startScan = 20;
        static final int TRANSACTION_startSoftAp = 42;
        static final int TRANSACTION_startSubscriptionProvisioning = 65;
        static final int TRANSACTION_startWatchLocalOnlyHotspot = 46;
        static final int TRANSACTION_stopDppSession = 80;
        static final int TRANSACTION_stopLocalOnlyHotspot = 45;
        static final int TRANSACTION_stopSoftAp = 43;
        static final int TRANSACTION_stopWatchLocalOnlyHotspot = 47;
        static final int TRANSACTION_unregisterNetworkRequestMatchCallback = 73;
        static final int TRANSACTION_unregisterSoftApCallback = 67;
        static final int TRANSACTION_unregisterTrafficStateCallback = 71;
        static final int TRANSACTION_updateInterfaceIpState = 41;
        static final int TRANSACTION_updateWifiLockWorkSource = 35;
        static final int TRANSACTION_updateWifiUsabilityScore = 81;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IWifiManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IWifiManager) {
                return (IWifiManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IWifiManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 81: {
                    return "updateWifiUsabilityScore";
                }
                case 80: {
                    return "stopDppSession";
                }
                case 79: {
                    return "startDppAsEnrolleeInitiator";
                }
                case 78: {
                    return "startDppAsConfiguratorInitiator";
                }
                case 77: {
                    return "setDeviceMobilityState";
                }
                case 76: {
                    return "getFactoryMacAddresses";
                }
                case 75: {
                    return "removeNetworkSuggestions";
                }
                case 74: {
                    return "addNetworkSuggestions";
                }
                case 73: {
                    return "unregisterNetworkRequestMatchCallback";
                }
                case 72: {
                    return "registerNetworkRequestMatchCallback";
                }
                case 71: {
                    return "unregisterTrafficStateCallback";
                }
                case 70: {
                    return "registerTrafficStateCallback";
                }
                case 69: {
                    return "removeOnWifiUsabilityStatsListener";
                }
                case 68: {
                    return "addOnWifiUsabilityStatsListener";
                }
                case 67: {
                    return "unregisterSoftApCallback";
                }
                case 66: {
                    return "registerSoftApCallback";
                }
                case 65: {
                    return "startSubscriptionProvisioning";
                }
                case 64: {
                    return "restoreSupplicantBackupData";
                }
                case 63: {
                    return "restoreBackupData";
                }
                case 62: {
                    return "retrieveBackupData";
                }
                case 61: {
                    return "getCurrentNetwork";
                }
                case 60: {
                    return "factoryReset";
                }
                case 59: {
                    return "disableEphemeralNetwork";
                }
                case 58: {
                    return "enableWifiConnectivityManager";
                }
                case 57: {
                    return "getVerboseLoggingLevel";
                }
                case 56: {
                    return "enableVerboseLogging";
                }
                case 55: {
                    return "getCurrentNetworkWpsNfcConfigurationToken";
                }
                case 54: {
                    return "enableTdlsWithMacAddress";
                }
                case 53: {
                    return "enableTdls";
                }
                case 52: {
                    return "getWifiServiceMessenger";
                }
                case 51: {
                    return "notifyUserOfApBandConversion";
                }
                case 50: {
                    return "setWifiApConfiguration";
                }
                case 49: {
                    return "getWifiApConfiguration";
                }
                case 48: {
                    return "getWifiApEnabledState";
                }
                case 47: {
                    return "stopWatchLocalOnlyHotspot";
                }
                case 46: {
                    return "startWatchLocalOnlyHotspot";
                }
                case 45: {
                    return "stopLocalOnlyHotspot";
                }
                case 44: {
                    return "startLocalOnlyHotspot";
                }
                case 43: {
                    return "stopSoftAp";
                }
                case 42: {
                    return "startSoftAp";
                }
                case 41: {
                    return "updateInterfaceIpState";
                }
                case 40: {
                    return "releaseMulticastLock";
                }
                case 39: {
                    return "acquireMulticastLock";
                }
                case 38: {
                    return "isMulticastEnabled";
                }
                case 37: {
                    return "initializeMulticastFiltering";
                }
                case 36: {
                    return "releaseWifiLock";
                }
                case 35: {
                    return "updateWifiLockWorkSource";
                }
                case 34: {
                    return "acquireWifiLock";
                }
                case 33: {
                    return "isScanAlwaysAvailable";
                }
                case 32: {
                    return "getDhcpInfo";
                }
                case 31: {
                    return "needs5GHzToAnyApBandConversion";
                }
                case 30: {
                    return "isDualBandSupported";
                }
                case 29: {
                    return "getCountryCode";
                }
                case 28: {
                    return "setCountryCode";
                }
                case 27: {
                    return "getWifiEnabledState";
                }
                case 26: {
                    return "setWifiEnabled";
                }
                case 25: {
                    return "getConnectionInfo";
                }
                case 24: {
                    return "reassociate";
                }
                case 23: {
                    return "reconnect";
                }
                case 22: {
                    return "disconnect";
                }
                case 21: {
                    return "getScanResults";
                }
                case 20: {
                    return "startScan";
                }
                case 19: {
                    return "disableNetwork";
                }
                case 18: {
                    return "enableNetwork";
                }
                case 17: {
                    return "removeNetwork";
                }
                case 16: {
                    return "deauthenticateNetwork";
                }
                case 15: {
                    return "matchProviderWithCurrentNetwork";
                }
                case 14: {
                    return "queryPasspointIcon";
                }
                case 13: {
                    return "getWifiConfigsForPasspointProfiles";
                }
                case 12: {
                    return "getPasspointConfigurations";
                }
                case 11: {
                    return "removePasspointConfiguration";
                }
                case 10: {
                    return "addOrUpdatePasspointConfiguration";
                }
                case 9: {
                    return "addOrUpdateNetwork";
                }
                case 8: {
                    return "getMatchingPasspointConfigsForOsuProviders";
                }
                case 7: {
                    return "getMatchingOsuProviders";
                }
                case 6: {
                    return "getAllMatchingFqdnsForScanResults";
                }
                case 5: {
                    return "getPrivilegedConfiguredNetworks";
                }
                case 4: {
                    return "getConfiguredNetworks";
                }
                case 3: {
                    return "requestActivityInfo";
                }
                case 2: {
                    return "reportActivityInfo";
                }
                case 1: 
            }
            return "getSupportedFeatures";
        }

        public static boolean setDefaultImpl(IWifiManager iWifiManager) {
            if (Proxy.sDefaultImpl == null && iWifiManager != null) {
                Proxy.sDefaultImpl = iWifiManager;
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
                boolean bl6 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 81: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.updateWifiUsabilityScore(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 80: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.stopDppSession();
                        parcel.writeNoException();
                        return true;
                    }
                    case 79: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.startDppAsEnrolleeInitiator(((Parcel)object).readStrongBinder(), ((Parcel)object).readString(), IDppCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 78: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.startDppAsConfiguratorInitiator(((Parcel)object).readStrongBinder(), ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), IDppCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 77: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setDeviceMobilityState(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 76: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getFactoryMacAddresses();
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 75: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.removeNetworkSuggestions(((Parcel)object).createTypedArrayList(WifiNetworkSuggestion.CREATOR), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 74: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.addNetworkSuggestions(((Parcel)object).createTypedArrayList(WifiNetworkSuggestion.CREATOR), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 73: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterNetworkRequestMatchCallback(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 72: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerNetworkRequestMatchCallback(((Parcel)object).readStrongBinder(), INetworkRequestMatchCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 71: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterTrafficStateCallback(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 70: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerTrafficStateCallback(((Parcel)object).readStrongBinder(), ITrafficStateCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 69: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeOnWifiUsabilityStatsListener(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 68: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.addOnWifiUsabilityStatsListener(((Parcel)object).readStrongBinder(), IOnWifiUsabilityStatsListener.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 67: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterSoftApCallback(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 66: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerSoftApCallback(((Parcel)object).readStrongBinder(), ISoftApCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 65: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        OsuProvider osuProvider = ((Parcel)object).readInt() != 0 ? OsuProvider.CREATOR.createFromParcel((Parcel)object) : null;
                        this.startSubscriptionProvisioning(osuProvider, IProvisioningCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 64: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.restoreSupplicantBackupData(((Parcel)object).createByteArray(), ((Parcel)object).createByteArray());
                        parcel.writeNoException();
                        return true;
                    }
                    case 63: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.restoreBackupData(((Parcel)object).createByteArray());
                        parcel.writeNoException();
                        return true;
                    }
                    case 62: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.retrieveBackupData();
                        parcel.writeNoException();
                        parcel.writeByteArray((byte[])object);
                        return true;
                    }
                    case 61: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getCurrentNetwork();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Network)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 60: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.factoryReset(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 59: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.disableEphemeralNetwork(((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 58: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        if (((Parcel)object).readInt() != 0) {
                            bl6 = true;
                        }
                        this.enableWifiConnectivityManager(bl6);
                        parcel.writeNoException();
                        return true;
                    }
                    case 57: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getVerboseLoggingLevel();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 56: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.enableVerboseLogging(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 55: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getCurrentNetworkWpsNfcConfigurationToken();
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 54: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        bl6 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl6 = true;
                        }
                        this.enableTdlsWithMacAddress(string2, bl6);
                        parcel.writeNoException();
                        return true;
                    }
                    case 53: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string3 = ((Parcel)object).readString();
                        bl6 = bl2;
                        if (((Parcel)object).readInt() != 0) {
                            bl6 = true;
                        }
                        this.enableTdls(string3, bl6);
                        parcel.writeNoException();
                        return true;
                    }
                    case 52: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getWifiServiceMessenger(((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Messenger)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 51: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifyUserOfApBandConversion(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 50: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        WifiConfiguration wifiConfiguration = ((Parcel)object).readInt() != 0 ? WifiConfiguration.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.setWifiApConfiguration(wifiConfiguration, ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 49: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getWifiApConfiguration();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((WifiConfiguration)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 48: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getWifiApEnabledState();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 47: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.stopWatchLocalOnlyHotspot();
                        parcel.writeNoException();
                        return true;
                    }
                    case 46: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Messenger messenger = ((Parcel)object).readInt() != 0 ? Messenger.CREATOR.createFromParcel((Parcel)object) : null;
                        this.startWatchLocalOnlyHotspot(messenger, ((Parcel)object).readStrongBinder());
                        parcel.writeNoException();
                        return true;
                    }
                    case 45: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.stopLocalOnlyHotspot();
                        parcel.writeNoException();
                        return true;
                    }
                    case 44: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Messenger messenger = ((Parcel)object).readInt() != 0 ? Messenger.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.startLocalOnlyHotspot(messenger, ((Parcel)object).readStrongBinder(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 43: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.stopSoftAp() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 42: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? WifiConfiguration.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.startSoftAp((WifiConfiguration)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 41: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.updateInterfaceIpState(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 40: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.releaseMulticastLock(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 39: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.acquireMulticastLock(((Parcel)object).readStrongBinder(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 38: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isMulticastEnabled() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 37: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.initializeMulticastFiltering();
                        parcel.writeNoException();
                        return true;
                    }
                    case 36: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.releaseWifiLock(((Parcel)object).readStrongBinder()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 35: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        object = ((Parcel)object).readInt() != 0 ? WorkSource.CREATOR.createFromParcel((Parcel)object) : null;
                        this.updateWifiLockWorkSource(iBinder, (WorkSource)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 34: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        n = ((Parcel)object).readInt();
                        String string4 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? WorkSource.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.acquireWifiLock(iBinder, n, string4, (WorkSource)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 33: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isScanAlwaysAvailable() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 32: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getDhcpInfo();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((DhcpInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 31: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.needs5GHzToAnyApBandConversion() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 30: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isDualBandSupported() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getCountryCode();
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setCountryCode(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getWifiEnabledState();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string5 = ((Parcel)object).readString();
                        bl6 = bl3;
                        if (((Parcel)object).readInt() != 0) {
                            bl6 = true;
                        }
                        n = this.setWifiEnabled(string5, bl6) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getConnectionInfo(((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((WifiInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.reassociate(((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.reconnect(((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.disconnect(((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getScanResults(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.startScan(((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.disableNetwork(((Parcel)object).readInt(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl6 = bl4;
                        if (((Parcel)object).readInt() != 0) {
                            bl6 = true;
                        }
                        n = this.enableNetwork(n, bl6, ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.removeNetwork(((Parcel)object).readInt(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = ((Parcel)object).readLong();
                        bl6 = bl5;
                        if (((Parcel)object).readInt() != 0) {
                            bl6 = true;
                        }
                        this.deauthenticateNetwork(l, bl6);
                        parcel.writeNoException();
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.matchProviderWithCurrentNetwork(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.queryPasspointIcon(((Parcel)object).readLong(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getWifiConfigsForPasspointProfiles(((Parcel)object).createStringArrayList());
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getPasspointConfigurations(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.removePasspointConfiguration(((Parcel)object).readString(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        PasspointConfiguration passpointConfiguration = ((Parcel)object).readInt() != 0 ? PasspointConfiguration.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.addOrUpdatePasspointConfiguration(passpointConfiguration, ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        WifiConfiguration wifiConfiguration = ((Parcel)object).readInt() != 0 ? WifiConfiguration.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.addOrUpdateNetwork(wifiConfiguration, ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getMatchingPasspointConfigsForOsuProviders(((Parcel)object).createTypedArrayList(OsuProvider.CREATOR));
                        parcel.writeNoException();
                        parcel.writeMap((Map)object);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getMatchingOsuProviders(((Parcel)object).createTypedArrayList(ScanResult.CREATOR));
                        parcel.writeNoException();
                        parcel.writeMap((Map)object);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAllMatchingFqdnsForScanResults(((Parcel)object).createTypedArrayList(ScanResult.CREATOR));
                        parcel.writeNoException();
                        parcel.writeMap((Map)object);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getPrivilegedConfiguredNetworks(((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ParceledListSlice)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getConfiguredNetworks(((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ParceledListSlice)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ResultReceiver.CREATOR.createFromParcel((Parcel)object) : null;
                        this.requestActivityInfo((ResultReceiver)object);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.reportActivityInfo();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((WifiActivityEnergyInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                long l = this.getSupportedFeatures();
                parcel.writeNoException();
                parcel.writeLong(l);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IWifiManager {
            public static IWifiManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void acquireMulticastLock(IBinder iBinder, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(39, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().acquireMulticastLock(iBinder, string2);
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
            public boolean acquireWifiLock(IBinder iBinder, int n, String string2, WorkSource workSource) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    boolean bl = true;
                    if (workSource != null) {
                        parcel.writeInt(1);
                        workSource.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(34, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().acquireWifiLock(iBinder, n, string2, workSource);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public int addNetworkSuggestions(List<WifiNetworkSuggestion> list, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(list);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(74, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().addNetworkSuggestions(list, string2);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
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
            public void addOnWifiUsabilityStatsListener(IBinder iBinder, IOnWifiUsabilityStatsListener iOnWifiUsabilityStatsListener, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    IBinder iBinder2 = iOnWifiUsabilityStatsListener != null ? iOnWifiUsabilityStatsListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(68, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addOnWifiUsabilityStatsListener(iBinder, iOnWifiUsabilityStatsListener, n);
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
            public int addOrUpdateNetwork(WifiConfiguration wifiConfiguration, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (wifiConfiguration != null) {
                        parcel.writeInt(1);
                        wifiConfiguration.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().addOrUpdateNetwork(wifiConfiguration, string2);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
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
            public boolean addOrUpdatePasspointConfiguration(PasspointConfiguration passpointConfiguration, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (passpointConfiguration != null) {
                        parcel.writeInt(1);
                        passpointConfiguration.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().addOrUpdatePasspointConfiguration(passpointConfiguration, string2);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void deauthenticateNetwork(long l, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeLong(l);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().deauthenticateNetwork(l, bl);
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
            public void disableEphemeralNetwork(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(59, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disableEphemeralNetwork(string2, string3);
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
            public boolean disableNetwork(int n, String string2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(19, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().disableNetwork(n, string2);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean disconnect(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(22, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().disconnect(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean enableNetwork(int n, boolean bl, String string2) throws RemoteException {
                Parcel parcel;
                boolean bl2;
                Parcel parcel2;
                block4 : {
                    int n2;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        bl2 = true;
                        n2 = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (this.mRemote.transact(18, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().enableNetwork(n, bl, string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                bl = n != 0 ? bl2 : false;
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public void enableTdls(String string2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(53, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enableTdls(string2, bl);
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
            public void enableTdlsWithMacAddress(String string2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(54, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enableTdlsWithMacAddress(string2, bl);
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
            public void enableVerboseLogging(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(56, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enableVerboseLogging(n);
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
            public void enableWifiConnectivityManager(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(58, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enableWifiConnectivityManager(bl);
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
            public void factoryReset(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(60, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().factoryReset(string2);
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
            public Map getAllMatchingFqdnsForScanResults(List<ScanResult> object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(object);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getAllMatchingFqdnsForScanResults((List<ScanResult>)object);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readHashMap(this.getClass().getClassLoader());
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public ParceledListSlice getConfiguredNetworks(String parceledListSlice) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)((Object)parceledListSlice));
                        if (this.mRemote.transact(4, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        parceledListSlice = Stub.getDefaultImpl().getConfiguredNetworks((String)((Object)parceledListSlice));
                        parcel2.recycle();
                        parcel.recycle();
                        return parceledListSlice;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                parceledListSlice = parcel2.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return parceledListSlice;
            }

            @Override
            public WifiInfo getConnectionInfo(String object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)object);
                        if (this.mRemote.transact(25, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getConnectionInfo((String)object);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                object = parcel2.readInt() != 0 ? WifiInfo.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            @Override
            public String getCountryCode() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(29, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getCountryCode();
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public Network getCurrentNetwork() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(61, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        Network network = Stub.getDefaultImpl().getCurrentNetwork();
                        parcel.recycle();
                        parcel2.recycle();
                        return network;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                Network network = parcel.readInt() != 0 ? Network.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return network;
            }

            @Override
            public String getCurrentNetworkWpsNfcConfigurationToken() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(55, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getCurrentNetworkWpsNfcConfigurationToken();
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public DhcpInfo getDhcpInfo() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(32, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        DhcpInfo dhcpInfo = Stub.getDefaultImpl().getDhcpInfo();
                        parcel.recycle();
                        parcel2.recycle();
                        return dhcpInfo;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                DhcpInfo dhcpInfo = parcel.readInt() != 0 ? DhcpInfo.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return dhcpInfo;
            }

            @Override
            public String[] getFactoryMacAddresses() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(76, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String[] arrstring = Stub.getDefaultImpl().getFactoryMacAddresses();
                        return arrstring;
                    }
                    parcel2.readException();
                    String[] arrstring = parcel2.createStringArray();
                    return arrstring;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public Map getMatchingOsuProviders(List<ScanResult> object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(object);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getMatchingOsuProviders((List<ScanResult>)object);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readHashMap(this.getClass().getClassLoader());
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public Map getMatchingPasspointConfigsForOsuProviders(List<OsuProvider> object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(object);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getMatchingPasspointConfigsForOsuProviders((List<OsuProvider>)object);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readHashMap(this.getClass().getClassLoader());
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<PasspointConfiguration> getPasspointConfigurations(String arrayList) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)((Object)arrayList));
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrayList = Stub.getDefaultImpl().getPasspointConfigurations((String)((Object)arrayList));
                        return arrayList;
                    }
                    parcel2.readException();
                    arrayList = parcel2.createTypedArrayList(PasspointConfiguration.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public ParceledListSlice getPrivilegedConfiguredNetworks(String parceledListSlice) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)((Object)parceledListSlice));
                        if (this.mRemote.transact(5, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        parceledListSlice = Stub.getDefaultImpl().getPrivilegedConfiguredNetworks((String)((Object)parceledListSlice));
                        parcel2.recycle();
                        parcel.recycle();
                        return parceledListSlice;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                parceledListSlice = parcel2.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return parceledListSlice;
            }

            @Override
            public List<ScanResult> getScanResults(String arrayList) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)((Object)arrayList));
                    if (!this.mRemote.transact(21, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrayList = Stub.getDefaultImpl().getScanResults((String)((Object)arrayList));
                        return arrayList;
                    }
                    parcel2.readException();
                    arrayList = parcel2.createTypedArrayList(ScanResult.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public long getSupportedFeatures() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getSupportedFeatures();
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
            public int getVerboseLoggingLevel() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(57, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getVerboseLoggingLevel();
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public WifiConfiguration getWifiApConfiguration() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(49, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        WifiConfiguration wifiConfiguration = Stub.getDefaultImpl().getWifiApConfiguration();
                        parcel.recycle();
                        parcel2.recycle();
                        return wifiConfiguration;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                WifiConfiguration wifiConfiguration = parcel.readInt() != 0 ? WifiConfiguration.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return wifiConfiguration;
            }

            @Override
            public int getWifiApEnabledState() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(48, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getWifiApEnabledState();
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<WifiConfiguration> getWifiConfigsForPasspointProfiles(List<String> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStringList(list);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        list = Stub.getDefaultImpl().getWifiConfigsForPasspointProfiles(list);
                        return list;
                    }
                    parcel2.readException();
                    list = parcel2.createTypedArrayList(WifiConfiguration.CREATOR);
                    return list;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getWifiEnabledState() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(27, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getWifiEnabledState();
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public Messenger getWifiServiceMessenger(String object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)object);
                        if (this.mRemote.transact(52, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getWifiServiceMessenger((String)object);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                object = parcel2.readInt() != 0 ? Messenger.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            @Override
            public void initializeMulticastFiltering() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(37, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().initializeMulticastFiltering();
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
            public boolean isDualBandSupported() throws RemoteException {
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
                    if (iBinder.transact(30, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isDualBandSupported();
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
            public boolean isMulticastEnabled() throws RemoteException {
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
                    if (iBinder.transact(38, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isMulticastEnabled();
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
            public boolean isScanAlwaysAvailable() throws RemoteException {
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
                    if (iBinder.transact(33, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isScanAlwaysAvailable();
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
            public int matchProviderWithCurrentNetwork(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().matchProviderWithCurrentNetwork(string2);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean needs5GHzToAnyApBandConversion() throws RemoteException {
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
                    if (iBinder.transact(31, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().needs5GHzToAnyApBandConversion();
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
            public void notifyUserOfApBandConversion(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(51, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyUserOfApBandConversion(string2);
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
            public void queryPasspointIcon(long l, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().queryPasspointIcon(l, string2);
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
            public boolean reassociate(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(24, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().reassociate(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean reconnect(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(23, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().reconnect(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void registerNetworkRequestMatchCallback(IBinder iBinder, INetworkRequestMatchCallback iNetworkRequestMatchCallback, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    IBinder iBinder2 = iNetworkRequestMatchCallback != null ? iNetworkRequestMatchCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(72, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerNetworkRequestMatchCallback(iBinder, iNetworkRequestMatchCallback, n);
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
            public void registerSoftApCallback(IBinder iBinder, ISoftApCallback iSoftApCallback, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    IBinder iBinder2 = iSoftApCallback != null ? iSoftApCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(66, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerSoftApCallback(iBinder, iSoftApCallback, n);
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
            public void registerTrafficStateCallback(IBinder iBinder, ITrafficStateCallback iTrafficStateCallback, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    IBinder iBinder2 = iTrafficStateCallback != null ? iTrafficStateCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(70, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerTrafficStateCallback(iBinder, iTrafficStateCallback, n);
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
            public void releaseMulticastLock(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(40, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().releaseMulticastLock(string2);
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
            public boolean releaseWifiLock(IBinder iBinder) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder2;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeStrongBinder(iBinder);
                        iBinder2 = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder2.transact(36, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().releaseWifiLock(iBinder);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean removeNetwork(int n, String string2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(17, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().removeNetwork(n, string2);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public int removeNetworkSuggestions(List<WifiNetworkSuggestion> list, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(list);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(75, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().removeNetworkSuggestions(list, string2);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void removeOnWifiUsabilityStatsListener(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(69, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeOnWifiUsabilityStatsListener(n);
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
            public boolean removePasspointConfiguration(String string2, String string3) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString(string2);
                        parcel2.writeString(string3);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(11, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().removePasspointConfiguration(string2, string3);
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
            public WifiActivityEnergyInfo reportActivityInfo() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(2, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        WifiActivityEnergyInfo wifiActivityEnergyInfo = Stub.getDefaultImpl().reportActivityInfo();
                        parcel.recycle();
                        parcel2.recycle();
                        return wifiActivityEnergyInfo;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                WifiActivityEnergyInfo wifiActivityEnergyInfo = parcel.readInt() != 0 ? WifiActivityEnergyInfo.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return wifiActivityEnergyInfo;
            }

            @Override
            public void requestActivityInfo(ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (resultReceiver != null) {
                        parcel.writeInt(1);
                        resultReceiver.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestActivityInfo(resultReceiver);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void restoreBackupData(byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(63, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().restoreBackupData(arrby);
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
            public void restoreSupplicantBackupData(byte[] arrby, byte[] arrby2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeByteArray(arrby);
                    parcel.writeByteArray(arrby2);
                    if (!this.mRemote.transact(64, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().restoreSupplicantBackupData(arrby, arrby2);
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
            public byte[] retrieveBackupData() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(62, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        byte[] arrby = Stub.getDefaultImpl().retrieveBackupData();
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
            public void setCountryCode(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(28, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setCountryCode(string2);
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
            public void setDeviceMobilityState(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(77, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDeviceMobilityState(n);
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
            public boolean setWifiApConfiguration(WifiConfiguration wifiConfiguration, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (wifiConfiguration != null) {
                        parcel.writeInt(1);
                        wifiConfiguration.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(50, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().setWifiApConfiguration(wifiConfiguration, string2);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public boolean setWifiEnabled(String string2, boolean bl) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                int n;
                block4 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString(string2);
                        bl2 = true;
                        n = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    parcel2.writeInt(n);
                    if (this.mRemote.transact(26, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().setWifiEnabled(string2, bl);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                bl = n != 0 ? bl2 : false;
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void startDppAsConfiguratorInitiator(IBinder iBinder, String string2, int n, int n2, IDppCallback iDppCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    IBinder iBinder2 = iDppCallback != null ? iDppCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder2);
                    if (!this.mRemote.transact(78, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startDppAsConfiguratorInitiator(iBinder, string2, n, n2, iDppCallback);
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
            public void startDppAsEnrolleeInitiator(IBinder iBinder, String string2, IDppCallback iDppCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    IBinder iBinder2 = iDppCallback != null ? iDppCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder2);
                    if (!this.mRemote.transact(79, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startDppAsEnrolleeInitiator(iBinder, string2, iDppCallback);
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
            public int startLocalOnlyHotspot(Messenger messenger, IBinder iBinder, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (messenger != null) {
                        parcel.writeInt(1);
                        messenger.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(44, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().startLocalOnlyHotspot(messenger, iBinder, string2);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean startScan(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(20, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().startScan(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean startSoftAp(WifiConfiguration wifiConfiguration) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (wifiConfiguration != null) {
                        parcel.writeInt(1);
                        wifiConfiguration.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(42, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().startSoftAp(wifiConfiguration);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void startSubscriptionProvisioning(OsuProvider osuProvider, IProvisioningCallback iProvisioningCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (osuProvider != null) {
                        parcel.writeInt(1);
                        osuProvider.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iProvisioningCallback != null ? iProvisioningCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(65, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startSubscriptionProvisioning(osuProvider, iProvisioningCallback);
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
            public void startWatchLocalOnlyHotspot(Messenger messenger, IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (messenger != null) {
                        parcel.writeInt(1);
                        messenger.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(46, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startWatchLocalOnlyHotspot(messenger, iBinder);
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
            public void stopDppSession() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(80, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopDppSession();
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
            public void stopLocalOnlyHotspot() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(45, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopLocalOnlyHotspot();
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
            public boolean stopSoftAp() throws RemoteException {
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
                    if (iBinder.transact(43, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().stopSoftAp();
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
            public void stopWatchLocalOnlyHotspot() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(47, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopWatchLocalOnlyHotspot();
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
            public void unregisterNetworkRequestMatchCallback(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(73, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterNetworkRequestMatchCallback(n);
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
            public void unregisterSoftApCallback(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(67, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterSoftApCallback(n);
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
            public void unregisterTrafficStateCallback(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(71, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterTrafficStateCallback(n);
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
            public void updateInterfaceIpState(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(41, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateInterfaceIpState(string2, n);
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
            public void updateWifiLockWorkSource(IBinder iBinder, WorkSource workSource) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (workSource != null) {
                        parcel.writeInt(1);
                        workSource.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(35, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateWifiLockWorkSource(iBinder, workSource);
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
            public void updateWifiUsabilityScore(int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(81, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateWifiUsabilityScore(n, n2, n3);
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
        }

    }

}

