/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.UnsupportedAppUsage;
import android.app.PendingIntent;
import android.net.ConnectionInfo;
import android.net.ISocketKeepaliveCallback;
import android.net.ITetheringEventCallback;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkMisc;
import android.net.NetworkQuotaInfo;
import android.net.NetworkRequest;
import android.net.NetworkState;
import android.net.ProxyInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Messenger;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ResultReceiver;
import com.android.internal.net.LegacyVpnInfo;
import com.android.internal.net.VpnConfig;
import com.android.internal.net.VpnProfile;
import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.List;

public interface IConnectivityManager
extends IInterface {
    public boolean addVpnAddress(String var1, int var2) throws RemoteException;

    public int checkMobileProvisioning(int var1) throws RemoteException;

    public ParcelFileDescriptor establishVpn(VpnConfig var1) throws RemoteException;

    public void factoryReset() throws RemoteException;

    @UnsupportedAppUsage
    public LinkProperties getActiveLinkProperties() throws RemoteException;

    public Network getActiveNetwork() throws RemoteException;

    public Network getActiveNetworkForUid(int var1, boolean var2) throws RemoteException;

    @UnsupportedAppUsage
    public NetworkInfo getActiveNetworkInfo() throws RemoteException;

    public NetworkInfo getActiveNetworkInfoForUid(int var1, boolean var2) throws RemoteException;

    public NetworkQuotaInfo getActiveNetworkQuotaInfo() throws RemoteException;

    @UnsupportedAppUsage
    public NetworkInfo[] getAllNetworkInfo() throws RemoteException;

    @UnsupportedAppUsage
    public NetworkState[] getAllNetworkState() throws RemoteException;

    public Network[] getAllNetworks() throws RemoteException;

    public String getAlwaysOnVpnPackage(int var1) throws RemoteException;

    public String getCaptivePortalServerUrl() throws RemoteException;

    public int getConnectionOwnerUid(ConnectionInfo var1) throws RemoteException;

    public NetworkCapabilities[] getDefaultNetworkCapabilitiesForUser(int var1) throws RemoteException;

    public NetworkRequest getDefaultRequest() throws RemoteException;

    public ProxyInfo getGlobalProxy() throws RemoteException;

    @UnsupportedAppUsage
    public int getLastTetherError(String var1) throws RemoteException;

    public void getLatestTetheringEntitlementResult(int var1, ResultReceiver var2, boolean var3, String var4) throws RemoteException;

    public LegacyVpnInfo getLegacyVpnInfo(int var1) throws RemoteException;

    public LinkProperties getLinkProperties(Network var1) throws RemoteException;

    public LinkProperties getLinkPropertiesForType(int var1) throws RemoteException;

    public String getMobileProvisioningUrl() throws RemoteException;

    public int getMultipathPreference(Network var1) throws RemoteException;

    public NetworkCapabilities getNetworkCapabilities(Network var1) throws RemoteException;

    public Network getNetworkForType(int var1) throws RemoteException;

    public NetworkInfo getNetworkInfo(int var1) throws RemoteException;

    public NetworkInfo getNetworkInfoForUid(Network var1, int var2, boolean var3) throws RemoteException;

    public byte[] getNetworkWatchlistConfigHash() throws RemoteException;

    public ProxyInfo getProxyForNetwork(Network var1) throws RemoteException;

    public int getRestoreDefaultNetworkDelay(int var1) throws RemoteException;

    public String[] getTetherableBluetoothRegexs() throws RemoteException;

    @UnsupportedAppUsage
    public String[] getTetherableIfaces() throws RemoteException;

    @UnsupportedAppUsage
    public String[] getTetherableUsbRegexs() throws RemoteException;

    @UnsupportedAppUsage
    public String[] getTetherableWifiRegexs() throws RemoteException;

    public String[] getTetheredDhcpRanges() throws RemoteException;

    @UnsupportedAppUsage
    public String[] getTetheredIfaces() throws RemoteException;

    @UnsupportedAppUsage
    public String[] getTetheringErroredIfaces() throws RemoteException;

    public VpnConfig getVpnConfig(int var1) throws RemoteException;

    public List<String> getVpnLockdownWhitelist(int var1) throws RemoteException;

    public boolean isActiveNetworkMetered() throws RemoteException;

    public boolean isAlwaysOnVpnPackageSupported(int var1, String var2) throws RemoteException;

    public boolean isCallerCurrentAlwaysOnVpnApp() throws RemoteException;

    public boolean isCallerCurrentAlwaysOnVpnLockdownApp() throws RemoteException;

    public boolean isNetworkSupported(int var1) throws RemoteException;

    public boolean isTetheringSupported(String var1) throws RemoteException;

    public boolean isVpnLockdownEnabled(int var1) throws RemoteException;

    public NetworkRequest listenForNetwork(NetworkCapabilities var1, Messenger var2, IBinder var3) throws RemoteException;

    public void pendingListenForNetwork(NetworkCapabilities var1, PendingIntent var2) throws RemoteException;

    public NetworkRequest pendingRequestForNetwork(NetworkCapabilities var1, PendingIntent var2) throws RemoteException;

    public boolean prepareVpn(String var1, String var2, int var3) throws RemoteException;

    public int registerNetworkAgent(Messenger var1, NetworkInfo var2, LinkProperties var3, NetworkCapabilities var4, int var5, NetworkMisc var6, int var7) throws RemoteException;

    public int registerNetworkFactory(Messenger var1, String var2) throws RemoteException;

    public void registerTetheringEventCallback(ITetheringEventCallback var1, String var2) throws RemoteException;

    public void releaseNetworkRequest(NetworkRequest var1) throws RemoteException;

    public void releasePendingNetworkRequest(PendingIntent var1) throws RemoteException;

    public boolean removeVpnAddress(String var1, int var2) throws RemoteException;

    public void reportInetCondition(int var1, int var2) throws RemoteException;

    public void reportNetworkConnectivity(Network var1, boolean var2) throws RemoteException;

    public boolean requestBandwidthUpdate(Network var1) throws RemoteException;

    public NetworkRequest requestNetwork(NetworkCapabilities var1, Messenger var2, int var3, IBinder var4, int var5) throws RemoteException;

    public boolean requestRouteToHostAddress(int var1, byte[] var2) throws RemoteException;

    public void setAcceptPartialConnectivity(Network var1, boolean var2, boolean var3) throws RemoteException;

    public void setAcceptUnvalidated(Network var1, boolean var2, boolean var3) throws RemoteException;

    public void setAirplaneMode(boolean var1) throws RemoteException;

    public boolean setAlwaysOnVpnPackage(int var1, String var2, boolean var3, List<String> var4) throws RemoteException;

    public void setAvoidUnvalidated(Network var1) throws RemoteException;

    public void setGlobalProxy(ProxyInfo var1) throws RemoteException;

    public void setProvisioningNotificationVisible(boolean var1, int var2, String var3) throws RemoteException;

    public boolean setUnderlyingNetworksForVpn(Network[] var1) throws RemoteException;

    public int setUsbTethering(boolean var1, String var2) throws RemoteException;

    public void setVpnPackageAuthorization(String var1, int var2, boolean var3) throws RemoteException;

    public boolean shouldAvoidBadWifi() throws RemoteException;

    public void startCaptivePortalApp(Network var1) throws RemoteException;

    public void startCaptivePortalAppInternal(Network var1, Bundle var2) throws RemoteException;

    @UnsupportedAppUsage
    public void startLegacyVpn(VpnProfile var1) throws RemoteException;

    public void startNattKeepalive(Network var1, int var2, ISocketKeepaliveCallback var3, String var4, int var5, String var6) throws RemoteException;

    public void startNattKeepaliveWithFd(Network var1, FileDescriptor var2, int var3, int var4, ISocketKeepaliveCallback var5, String var6, String var7) throws RemoteException;

    public IBinder startOrGetTestNetworkService() throws RemoteException;

    public void startTcpKeepalive(Network var1, FileDescriptor var2, int var3, ISocketKeepaliveCallback var4) throws RemoteException;

    public void startTethering(int var1, ResultReceiver var2, boolean var3, String var4) throws RemoteException;

    public void stopKeepalive(Network var1, int var2) throws RemoteException;

    public void stopTethering(int var1, String var2) throws RemoteException;

    public int tether(String var1, String var2) throws RemoteException;

    public void unregisterNetworkFactory(Messenger var1) throws RemoteException;

    public void unregisterTetheringEventCallback(ITetheringEventCallback var1, String var2) throws RemoteException;

    public int untether(String var1, String var2) throws RemoteException;

    public boolean updateLockdownVpn() throws RemoteException;

    public static class Default
    implements IConnectivityManager {
        @Override
        public boolean addVpnAddress(String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public int checkMobileProvisioning(int n) throws RemoteException {
            return 0;
        }

        @Override
        public ParcelFileDescriptor establishVpn(VpnConfig vpnConfig) throws RemoteException {
            return null;
        }

        @Override
        public void factoryReset() throws RemoteException {
        }

        @Override
        public LinkProperties getActiveLinkProperties() throws RemoteException {
            return null;
        }

        @Override
        public Network getActiveNetwork() throws RemoteException {
            return null;
        }

        @Override
        public Network getActiveNetworkForUid(int n, boolean bl) throws RemoteException {
            return null;
        }

        @Override
        public NetworkInfo getActiveNetworkInfo() throws RemoteException {
            return null;
        }

        @Override
        public NetworkInfo getActiveNetworkInfoForUid(int n, boolean bl) throws RemoteException {
            return null;
        }

        @Override
        public NetworkQuotaInfo getActiveNetworkQuotaInfo() throws RemoteException {
            return null;
        }

        @Override
        public NetworkInfo[] getAllNetworkInfo() throws RemoteException {
            return null;
        }

        @Override
        public NetworkState[] getAllNetworkState() throws RemoteException {
            return null;
        }

        @Override
        public Network[] getAllNetworks() throws RemoteException {
            return null;
        }

        @Override
        public String getAlwaysOnVpnPackage(int n) throws RemoteException {
            return null;
        }

        @Override
        public String getCaptivePortalServerUrl() throws RemoteException {
            return null;
        }

        @Override
        public int getConnectionOwnerUid(ConnectionInfo connectionInfo) throws RemoteException {
            return 0;
        }

        @Override
        public NetworkCapabilities[] getDefaultNetworkCapabilitiesForUser(int n) throws RemoteException {
            return null;
        }

        @Override
        public NetworkRequest getDefaultRequest() throws RemoteException {
            return null;
        }

        @Override
        public ProxyInfo getGlobalProxy() throws RemoteException {
            return null;
        }

        @Override
        public int getLastTetherError(String string2) throws RemoteException {
            return 0;
        }

        @Override
        public void getLatestTetheringEntitlementResult(int n, ResultReceiver resultReceiver, boolean bl, String string2) throws RemoteException {
        }

        @Override
        public LegacyVpnInfo getLegacyVpnInfo(int n) throws RemoteException {
            return null;
        }

        @Override
        public LinkProperties getLinkProperties(Network network) throws RemoteException {
            return null;
        }

        @Override
        public LinkProperties getLinkPropertiesForType(int n) throws RemoteException {
            return null;
        }

        @Override
        public String getMobileProvisioningUrl() throws RemoteException {
            return null;
        }

        @Override
        public int getMultipathPreference(Network network) throws RemoteException {
            return 0;
        }

        @Override
        public NetworkCapabilities getNetworkCapabilities(Network network) throws RemoteException {
            return null;
        }

        @Override
        public Network getNetworkForType(int n) throws RemoteException {
            return null;
        }

        @Override
        public NetworkInfo getNetworkInfo(int n) throws RemoteException {
            return null;
        }

        @Override
        public NetworkInfo getNetworkInfoForUid(Network network, int n, boolean bl) throws RemoteException {
            return null;
        }

        @Override
        public byte[] getNetworkWatchlistConfigHash() throws RemoteException {
            return null;
        }

        @Override
        public ProxyInfo getProxyForNetwork(Network network) throws RemoteException {
            return null;
        }

        @Override
        public int getRestoreDefaultNetworkDelay(int n) throws RemoteException {
            return 0;
        }

        @Override
        public String[] getTetherableBluetoothRegexs() throws RemoteException {
            return null;
        }

        @Override
        public String[] getTetherableIfaces() throws RemoteException {
            return null;
        }

        @Override
        public String[] getTetherableUsbRegexs() throws RemoteException {
            return null;
        }

        @Override
        public String[] getTetherableWifiRegexs() throws RemoteException {
            return null;
        }

        @Override
        public String[] getTetheredDhcpRanges() throws RemoteException {
            return null;
        }

        @Override
        public String[] getTetheredIfaces() throws RemoteException {
            return null;
        }

        @Override
        public String[] getTetheringErroredIfaces() throws RemoteException {
            return null;
        }

        @Override
        public VpnConfig getVpnConfig(int n) throws RemoteException {
            return null;
        }

        @Override
        public List<String> getVpnLockdownWhitelist(int n) throws RemoteException {
            return null;
        }

        @Override
        public boolean isActiveNetworkMetered() throws RemoteException {
            return false;
        }

        @Override
        public boolean isAlwaysOnVpnPackageSupported(int n, String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isCallerCurrentAlwaysOnVpnApp() throws RemoteException {
            return false;
        }

        @Override
        public boolean isCallerCurrentAlwaysOnVpnLockdownApp() throws RemoteException {
            return false;
        }

        @Override
        public boolean isNetworkSupported(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isTetheringSupported(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isVpnLockdownEnabled(int n) throws RemoteException {
            return false;
        }

        @Override
        public NetworkRequest listenForNetwork(NetworkCapabilities networkCapabilities, Messenger messenger, IBinder iBinder) throws RemoteException {
            return null;
        }

        @Override
        public void pendingListenForNetwork(NetworkCapabilities networkCapabilities, PendingIntent pendingIntent) throws RemoteException {
        }

        @Override
        public NetworkRequest pendingRequestForNetwork(NetworkCapabilities networkCapabilities, PendingIntent pendingIntent) throws RemoteException {
            return null;
        }

        @Override
        public boolean prepareVpn(String string2, String string3, int n) throws RemoteException {
            return false;
        }

        @Override
        public int registerNetworkAgent(Messenger messenger, NetworkInfo networkInfo, LinkProperties linkProperties, NetworkCapabilities networkCapabilities, int n, NetworkMisc networkMisc, int n2) throws RemoteException {
            return 0;
        }

        @Override
        public int registerNetworkFactory(Messenger messenger, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public void registerTetheringEventCallback(ITetheringEventCallback iTetheringEventCallback, String string2) throws RemoteException {
        }

        @Override
        public void releaseNetworkRequest(NetworkRequest networkRequest) throws RemoteException {
        }

        @Override
        public void releasePendingNetworkRequest(PendingIntent pendingIntent) throws RemoteException {
        }

        @Override
        public boolean removeVpnAddress(String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public void reportInetCondition(int n, int n2) throws RemoteException {
        }

        @Override
        public void reportNetworkConnectivity(Network network, boolean bl) throws RemoteException {
        }

        @Override
        public boolean requestBandwidthUpdate(Network network) throws RemoteException {
            return false;
        }

        @Override
        public NetworkRequest requestNetwork(NetworkCapabilities networkCapabilities, Messenger messenger, int n, IBinder iBinder, int n2) throws RemoteException {
            return null;
        }

        @Override
        public boolean requestRouteToHostAddress(int n, byte[] arrby) throws RemoteException {
            return false;
        }

        @Override
        public void setAcceptPartialConnectivity(Network network, boolean bl, boolean bl2) throws RemoteException {
        }

        @Override
        public void setAcceptUnvalidated(Network network, boolean bl, boolean bl2) throws RemoteException {
        }

        @Override
        public void setAirplaneMode(boolean bl) throws RemoteException {
        }

        @Override
        public boolean setAlwaysOnVpnPackage(int n, String string2, boolean bl, List<String> list) throws RemoteException {
            return false;
        }

        @Override
        public void setAvoidUnvalidated(Network network) throws RemoteException {
        }

        @Override
        public void setGlobalProxy(ProxyInfo proxyInfo) throws RemoteException {
        }

        @Override
        public void setProvisioningNotificationVisible(boolean bl, int n, String string2) throws RemoteException {
        }

        @Override
        public boolean setUnderlyingNetworksForVpn(Network[] arrnetwork) throws RemoteException {
            return false;
        }

        @Override
        public int setUsbTethering(boolean bl, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public void setVpnPackageAuthorization(String string2, int n, boolean bl) throws RemoteException {
        }

        @Override
        public boolean shouldAvoidBadWifi() throws RemoteException {
            return false;
        }

        @Override
        public void startCaptivePortalApp(Network network) throws RemoteException {
        }

        @Override
        public void startCaptivePortalAppInternal(Network network, Bundle bundle) throws RemoteException {
        }

        @Override
        public void startLegacyVpn(VpnProfile vpnProfile) throws RemoteException {
        }

        @Override
        public void startNattKeepalive(Network network, int n, ISocketKeepaliveCallback iSocketKeepaliveCallback, String string2, int n2, String string3) throws RemoteException {
        }

        @Override
        public void startNattKeepaliveWithFd(Network network, FileDescriptor fileDescriptor, int n, int n2, ISocketKeepaliveCallback iSocketKeepaliveCallback, String string2, String string3) throws RemoteException {
        }

        @Override
        public IBinder startOrGetTestNetworkService() throws RemoteException {
            return null;
        }

        @Override
        public void startTcpKeepalive(Network network, FileDescriptor fileDescriptor, int n, ISocketKeepaliveCallback iSocketKeepaliveCallback) throws RemoteException {
        }

        @Override
        public void startTethering(int n, ResultReceiver resultReceiver, boolean bl, String string2) throws RemoteException {
        }

        @Override
        public void stopKeepalive(Network network, int n) throws RemoteException {
        }

        @Override
        public void stopTethering(int n, String string2) throws RemoteException {
        }

        @Override
        public int tether(String string2, String string3) throws RemoteException {
            return 0;
        }

        @Override
        public void unregisterNetworkFactory(Messenger messenger) throws RemoteException {
        }

        @Override
        public void unregisterTetheringEventCallback(ITetheringEventCallback iTetheringEventCallback, String string2) throws RemoteException {
        }

        @Override
        public int untether(String string2, String string3) throws RemoteException {
            return 0;
        }

        @Override
        public boolean updateLockdownVpn() throws RemoteException {
            return false;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IConnectivityManager {
        private static final String DESCRIPTOR = "android.net.IConnectivityManager";
        static final int TRANSACTION_addVpnAddress = 74;
        static final int TRANSACTION_checkMobileProvisioning = 51;
        static final int TRANSACTION_establishVpn = 41;
        static final int TRANSACTION_factoryReset = 77;
        static final int TRANSACTION_getActiveLinkProperties = 12;
        static final int TRANSACTION_getActiveNetwork = 1;
        static final int TRANSACTION_getActiveNetworkForUid = 2;
        static final int TRANSACTION_getActiveNetworkInfo = 3;
        static final int TRANSACTION_getActiveNetworkInfoForUid = 4;
        static final int TRANSACTION_getActiveNetworkQuotaInfo = 17;
        static final int TRANSACTION_getAllNetworkInfo = 7;
        static final int TRANSACTION_getAllNetworkState = 16;
        static final int TRANSACTION_getAllNetworks = 9;
        static final int TRANSACTION_getAlwaysOnVpnPackage = 48;
        static final int TRANSACTION_getCaptivePortalServerUrl = 82;
        static final int TRANSACTION_getConnectionOwnerUid = 84;
        static final int TRANSACTION_getDefaultNetworkCapabilitiesForUser = 10;
        static final int TRANSACTION_getDefaultRequest = 72;
        static final int TRANSACTION_getGlobalProxy = 36;
        static final int TRANSACTION_getLastTetherError = 22;
        static final int TRANSACTION_getLatestTetheringEntitlementResult = 87;
        static final int TRANSACTION_getLegacyVpnInfo = 44;
        static final int TRANSACTION_getLinkProperties = 14;
        static final int TRANSACTION_getLinkPropertiesForType = 13;
        static final int TRANSACTION_getMobileProvisioningUrl = 52;
        static final int TRANSACTION_getMultipathPreference = 71;
        static final int TRANSACTION_getNetworkCapabilities = 15;
        static final int TRANSACTION_getNetworkForType = 8;
        static final int TRANSACTION_getNetworkInfo = 5;
        static final int TRANSACTION_getNetworkInfoForUid = 6;
        static final int TRANSACTION_getNetworkWatchlistConfigHash = 83;
        static final int TRANSACTION_getProxyForNetwork = 38;
        static final int TRANSACTION_getRestoreDefaultNetworkDelay = 73;
        static final int TRANSACTION_getTetherableBluetoothRegexs = 32;
        static final int TRANSACTION_getTetherableIfaces = 26;
        static final int TRANSACTION_getTetherableUsbRegexs = 30;
        static final int TRANSACTION_getTetherableWifiRegexs = 31;
        static final int TRANSACTION_getTetheredDhcpRanges = 29;
        static final int TRANSACTION_getTetheredIfaces = 27;
        static final int TRANSACTION_getTetheringErroredIfaces = 28;
        static final int TRANSACTION_getVpnConfig = 42;
        static final int TRANSACTION_getVpnLockdownWhitelist = 50;
        static final int TRANSACTION_isActiveNetworkMetered = 18;
        static final int TRANSACTION_isAlwaysOnVpnPackageSupported = 46;
        static final int TRANSACTION_isCallerCurrentAlwaysOnVpnApp = 85;
        static final int TRANSACTION_isCallerCurrentAlwaysOnVpnLockdownApp = 86;
        static final int TRANSACTION_isNetworkSupported = 11;
        static final int TRANSACTION_isTetheringSupported = 23;
        static final int TRANSACTION_isVpnLockdownEnabled = 49;
        static final int TRANSACTION_listenForNetwork = 62;
        static final int TRANSACTION_pendingListenForNetwork = 63;
        static final int TRANSACTION_pendingRequestForNetwork = 60;
        static final int TRANSACTION_prepareVpn = 39;
        static final int TRANSACTION_registerNetworkAgent = 58;
        static final int TRANSACTION_registerNetworkFactory = 55;
        static final int TRANSACTION_registerTetheringEventCallback = 88;
        static final int TRANSACTION_releaseNetworkRequest = 64;
        static final int TRANSACTION_releasePendingNetworkRequest = 61;
        static final int TRANSACTION_removeVpnAddress = 75;
        static final int TRANSACTION_reportInetCondition = 34;
        static final int TRANSACTION_reportNetworkConnectivity = 35;
        static final int TRANSACTION_requestBandwidthUpdate = 56;
        static final int TRANSACTION_requestNetwork = 59;
        static final int TRANSACTION_requestRouteToHostAddress = 19;
        static final int TRANSACTION_setAcceptPartialConnectivity = 66;
        static final int TRANSACTION_setAcceptUnvalidated = 65;
        static final int TRANSACTION_setAirplaneMode = 54;
        static final int TRANSACTION_setAlwaysOnVpnPackage = 47;
        static final int TRANSACTION_setAvoidUnvalidated = 67;
        static final int TRANSACTION_setGlobalProxy = 37;
        static final int TRANSACTION_setProvisioningNotificationVisible = 53;
        static final int TRANSACTION_setUnderlyingNetworksForVpn = 76;
        static final int TRANSACTION_setUsbTethering = 33;
        static final int TRANSACTION_setVpnPackageAuthorization = 40;
        static final int TRANSACTION_shouldAvoidBadWifi = 70;
        static final int TRANSACTION_startCaptivePortalApp = 68;
        static final int TRANSACTION_startCaptivePortalAppInternal = 69;
        static final int TRANSACTION_startLegacyVpn = 43;
        static final int TRANSACTION_startNattKeepalive = 78;
        static final int TRANSACTION_startNattKeepaliveWithFd = 79;
        static final int TRANSACTION_startOrGetTestNetworkService = 90;
        static final int TRANSACTION_startTcpKeepalive = 80;
        static final int TRANSACTION_startTethering = 24;
        static final int TRANSACTION_stopKeepalive = 81;
        static final int TRANSACTION_stopTethering = 25;
        static final int TRANSACTION_tether = 20;
        static final int TRANSACTION_unregisterNetworkFactory = 57;
        static final int TRANSACTION_unregisterTetheringEventCallback = 89;
        static final int TRANSACTION_untether = 21;
        static final int TRANSACTION_updateLockdownVpn = 45;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IConnectivityManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IConnectivityManager) {
                return (IConnectivityManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IConnectivityManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 90: {
                    return "startOrGetTestNetworkService";
                }
                case 89: {
                    return "unregisterTetheringEventCallback";
                }
                case 88: {
                    return "registerTetheringEventCallback";
                }
                case 87: {
                    return "getLatestTetheringEntitlementResult";
                }
                case 86: {
                    return "isCallerCurrentAlwaysOnVpnLockdownApp";
                }
                case 85: {
                    return "isCallerCurrentAlwaysOnVpnApp";
                }
                case 84: {
                    return "getConnectionOwnerUid";
                }
                case 83: {
                    return "getNetworkWatchlistConfigHash";
                }
                case 82: {
                    return "getCaptivePortalServerUrl";
                }
                case 81: {
                    return "stopKeepalive";
                }
                case 80: {
                    return "startTcpKeepalive";
                }
                case 79: {
                    return "startNattKeepaliveWithFd";
                }
                case 78: {
                    return "startNattKeepalive";
                }
                case 77: {
                    return "factoryReset";
                }
                case 76: {
                    return "setUnderlyingNetworksForVpn";
                }
                case 75: {
                    return "removeVpnAddress";
                }
                case 74: {
                    return "addVpnAddress";
                }
                case 73: {
                    return "getRestoreDefaultNetworkDelay";
                }
                case 72: {
                    return "getDefaultRequest";
                }
                case 71: {
                    return "getMultipathPreference";
                }
                case 70: {
                    return "shouldAvoidBadWifi";
                }
                case 69: {
                    return "startCaptivePortalAppInternal";
                }
                case 68: {
                    return "startCaptivePortalApp";
                }
                case 67: {
                    return "setAvoidUnvalidated";
                }
                case 66: {
                    return "setAcceptPartialConnectivity";
                }
                case 65: {
                    return "setAcceptUnvalidated";
                }
                case 64: {
                    return "releaseNetworkRequest";
                }
                case 63: {
                    return "pendingListenForNetwork";
                }
                case 62: {
                    return "listenForNetwork";
                }
                case 61: {
                    return "releasePendingNetworkRequest";
                }
                case 60: {
                    return "pendingRequestForNetwork";
                }
                case 59: {
                    return "requestNetwork";
                }
                case 58: {
                    return "registerNetworkAgent";
                }
                case 57: {
                    return "unregisterNetworkFactory";
                }
                case 56: {
                    return "requestBandwidthUpdate";
                }
                case 55: {
                    return "registerNetworkFactory";
                }
                case 54: {
                    return "setAirplaneMode";
                }
                case 53: {
                    return "setProvisioningNotificationVisible";
                }
                case 52: {
                    return "getMobileProvisioningUrl";
                }
                case 51: {
                    return "checkMobileProvisioning";
                }
                case 50: {
                    return "getVpnLockdownWhitelist";
                }
                case 49: {
                    return "isVpnLockdownEnabled";
                }
                case 48: {
                    return "getAlwaysOnVpnPackage";
                }
                case 47: {
                    return "setAlwaysOnVpnPackage";
                }
                case 46: {
                    return "isAlwaysOnVpnPackageSupported";
                }
                case 45: {
                    return "updateLockdownVpn";
                }
                case 44: {
                    return "getLegacyVpnInfo";
                }
                case 43: {
                    return "startLegacyVpn";
                }
                case 42: {
                    return "getVpnConfig";
                }
                case 41: {
                    return "establishVpn";
                }
                case 40: {
                    return "setVpnPackageAuthorization";
                }
                case 39: {
                    return "prepareVpn";
                }
                case 38: {
                    return "getProxyForNetwork";
                }
                case 37: {
                    return "setGlobalProxy";
                }
                case 36: {
                    return "getGlobalProxy";
                }
                case 35: {
                    return "reportNetworkConnectivity";
                }
                case 34: {
                    return "reportInetCondition";
                }
                case 33: {
                    return "setUsbTethering";
                }
                case 32: {
                    return "getTetherableBluetoothRegexs";
                }
                case 31: {
                    return "getTetherableWifiRegexs";
                }
                case 30: {
                    return "getTetherableUsbRegexs";
                }
                case 29: {
                    return "getTetheredDhcpRanges";
                }
                case 28: {
                    return "getTetheringErroredIfaces";
                }
                case 27: {
                    return "getTetheredIfaces";
                }
                case 26: {
                    return "getTetherableIfaces";
                }
                case 25: {
                    return "stopTethering";
                }
                case 24: {
                    return "startTethering";
                }
                case 23: {
                    return "isTetheringSupported";
                }
                case 22: {
                    return "getLastTetherError";
                }
                case 21: {
                    return "untether";
                }
                case 20: {
                    return "tether";
                }
                case 19: {
                    return "requestRouteToHostAddress";
                }
                case 18: {
                    return "isActiveNetworkMetered";
                }
                case 17: {
                    return "getActiveNetworkQuotaInfo";
                }
                case 16: {
                    return "getAllNetworkState";
                }
                case 15: {
                    return "getNetworkCapabilities";
                }
                case 14: {
                    return "getLinkProperties";
                }
                case 13: {
                    return "getLinkPropertiesForType";
                }
                case 12: {
                    return "getActiveLinkProperties";
                }
                case 11: {
                    return "isNetworkSupported";
                }
                case 10: {
                    return "getDefaultNetworkCapabilitiesForUser";
                }
                case 9: {
                    return "getAllNetworks";
                }
                case 8: {
                    return "getNetworkForType";
                }
                case 7: {
                    return "getAllNetworkInfo";
                }
                case 6: {
                    return "getNetworkInfoForUid";
                }
                case 5: {
                    return "getNetworkInfo";
                }
                case 4: {
                    return "getActiveNetworkInfoForUid";
                }
                case 3: {
                    return "getActiveNetworkInfo";
                }
                case 2: {
                    return "getActiveNetworkForUid";
                }
                case 1: 
            }
            return "getActiveNetwork";
        }

        public static boolean setDefaultImpl(IConnectivityManager iConnectivityManager) {
            if (Proxy.sDefaultImpl == null && iConnectivityManager != null) {
                Proxy.sDefaultImpl = iConnectivityManager;
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
                boolean bl7 = false;
                boolean bl8 = false;
                boolean bl9 = false;
                boolean bl10 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 90: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.startOrGetTestNetworkService();
                        parcel.writeNoException();
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 89: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterTetheringEventCallback(ITetheringEventCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 88: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerTetheringEventCallback(ITetheringEventCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 87: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        ResultReceiver resultReceiver = ((Parcel)object).readInt() != 0 ? ResultReceiver.CREATOR.createFromParcel((Parcel)object) : null;
                        if (((Parcel)object).readInt() != 0) {
                            bl10 = true;
                        }
                        this.getLatestTetheringEntitlementResult(n, resultReceiver, bl10, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 86: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isCallerCurrentAlwaysOnVpnLockdownApp() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 85: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isCallerCurrentAlwaysOnVpnApp() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 84: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ConnectionInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.getConnectionOwnerUid((ConnectionInfo)object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 83: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getNetworkWatchlistConfigHash();
                        parcel.writeNoException();
                        parcel.writeByteArray((byte[])object);
                        return true;
                    }
                    case 82: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getCaptivePortalServerUrl();
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 81: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Network network = ((Parcel)object).readInt() != 0 ? Network.CREATOR.createFromParcel((Parcel)object) : null;
                        this.stopKeepalive(network, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 80: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Network network = ((Parcel)object).readInt() != 0 ? Network.CREATOR.createFromParcel((Parcel)object) : null;
                        this.startTcpKeepalive(network, ((Parcel)object).readRawFileDescriptor(), ((Parcel)object).readInt(), ISocketKeepaliveCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 79: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Network network = ((Parcel)object).readInt() != 0 ? Network.CREATOR.createFromParcel((Parcel)object) : null;
                        this.startNattKeepaliveWithFd(network, ((Parcel)object).readRawFileDescriptor(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ISocketKeepaliveCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 78: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Network network = ((Parcel)object).readInt() != 0 ? Network.CREATOR.createFromParcel((Parcel)object) : null;
                        this.startNattKeepalive(network, ((Parcel)object).readInt(), ISocketKeepaliveCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 77: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.factoryReset();
                        parcel.writeNoException();
                        return true;
                    }
                    case 76: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setUnderlyingNetworksForVpn(((Parcel)object).createTypedArray(Network.CREATOR)) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 75: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.removeVpnAddress(((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 74: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.addVpnAddress(((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 73: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getRestoreDefaultNetworkDelay(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 72: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getDefaultRequest();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((NetworkRequest)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 71: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Network.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.getMultipathPreference((Network)object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 70: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.shouldAvoidBadWifi() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 69: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Network network = ((Parcel)object).readInt() != 0 ? Network.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.startCaptivePortalAppInternal(network, (Bundle)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 68: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Network.CREATOR.createFromParcel((Parcel)object) : null;
                        this.startCaptivePortalApp((Network)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 67: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Network.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setAvoidUnvalidated((Network)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 66: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Network network = ((Parcel)object).readInt() != 0 ? Network.CREATOR.createFromParcel((Parcel)object) : null;
                        bl10 = ((Parcel)object).readInt() != 0;
                        if (((Parcel)object).readInt() != 0) {
                            bl = true;
                        }
                        this.setAcceptPartialConnectivity(network, bl10, bl);
                        parcel.writeNoException();
                        return true;
                    }
                    case 65: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Network network = ((Parcel)object).readInt() != 0 ? Network.CREATOR.createFromParcel((Parcel)object) : null;
                        bl10 = ((Parcel)object).readInt() != 0;
                        bl = bl2;
                        if (((Parcel)object).readInt() != 0) {
                            bl = true;
                        }
                        this.setAcceptUnvalidated(network, bl10, bl);
                        parcel.writeNoException();
                        return true;
                    }
                    case 64: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? NetworkRequest.CREATOR.createFromParcel((Parcel)object) : null;
                        this.releaseNetworkRequest((NetworkRequest)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 63: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        NetworkCapabilities networkCapabilities = ((Parcel)object).readInt() != 0 ? NetworkCapabilities.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.pendingListenForNetwork(networkCapabilities, (PendingIntent)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 62: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        NetworkCapabilities networkCapabilities = ((Parcel)object).readInt() != 0 ? NetworkCapabilities.CREATOR.createFromParcel((Parcel)object) : null;
                        Messenger messenger = ((Parcel)object).readInt() != 0 ? Messenger.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.listenForNetwork(networkCapabilities, messenger, ((Parcel)object).readStrongBinder());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((NetworkRequest)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 61: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.releasePendingNetworkRequest((PendingIntent)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 60: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        NetworkCapabilities networkCapabilities = ((Parcel)object).readInt() != 0 ? NetworkCapabilities.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.pendingRequestForNetwork(networkCapabilities, (PendingIntent)object);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((NetworkRequest)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 59: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        NetworkCapabilities networkCapabilities = ((Parcel)object).readInt() != 0 ? NetworkCapabilities.CREATOR.createFromParcel((Parcel)object) : null;
                        Messenger messenger = ((Parcel)object).readInt() != 0 ? Messenger.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.requestNetwork(networkCapabilities, messenger, ((Parcel)object).readInt(), ((Parcel)object).readStrongBinder(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((NetworkRequest)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 58: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Messenger messenger = ((Parcel)object).readInt() != 0 ? Messenger.CREATOR.createFromParcel((Parcel)object) : null;
                        NetworkInfo networkInfo = ((Parcel)object).readInt() != 0 ? NetworkInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        LinkProperties linkProperties = ((Parcel)object).readInt() != 0 ? LinkProperties.CREATOR.createFromParcel((Parcel)object) : null;
                        NetworkCapabilities networkCapabilities = ((Parcel)object).readInt() != 0 ? NetworkCapabilities.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        NetworkMisc networkMisc = ((Parcel)object).readInt() != 0 ? NetworkMisc.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.registerNetworkAgent(messenger, networkInfo, linkProperties, networkCapabilities, n, networkMisc, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 57: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Messenger.CREATOR.createFromParcel((Parcel)object) : null;
                        this.unregisterNetworkFactory((Messenger)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 56: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Network.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.requestBandwidthUpdate((Network)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 55: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Messenger messenger = ((Parcel)object).readInt() != 0 ? Messenger.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.registerNetworkFactory(messenger, ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 54: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl10 = bl3;
                        if (((Parcel)object).readInt() != 0) {
                            bl10 = true;
                        }
                        this.setAirplaneMode(bl10);
                        parcel.writeNoException();
                        return true;
                    }
                    case 53: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl10 = bl4;
                        if (((Parcel)object).readInt() != 0) {
                            bl10 = true;
                        }
                        this.setProvisioningNotificationVisible(bl10, ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 52: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getMobileProvisioningUrl();
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 51: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.checkMobileProvisioning(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 50: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getVpnLockdownWhitelist(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeStringList((List<String>)object);
                        return true;
                    }
                    case 49: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isVpnLockdownEnabled(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 48: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAlwaysOnVpnPackage(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 47: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        String string2 = ((Parcel)object).readString();
                        bl10 = bl5;
                        if (((Parcel)object).readInt() != 0) {
                            bl10 = true;
                        }
                        n = this.setAlwaysOnVpnPackage(n, string2, bl10, ((Parcel)object).createStringArrayList()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 46: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isAlwaysOnVpnPackageSupported(((Parcel)object).readInt(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 45: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.updateLockdownVpn() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 44: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getLegacyVpnInfo(((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((LegacyVpnInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 43: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? VpnProfile.CREATOR.createFromParcel((Parcel)object) : null;
                        this.startLegacyVpn((VpnProfile)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 42: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getVpnConfig(((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((VpnConfig)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 41: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? VpnConfig.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.establishVpn((VpnConfig)object);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ParcelFileDescriptor)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 40: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string3 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        bl10 = bl6;
                        if (((Parcel)object).readInt() != 0) {
                            bl10 = true;
                        }
                        this.setVpnPackageAuthorization(string3, n, bl10);
                        parcel.writeNoException();
                        return true;
                    }
                    case 39: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.prepareVpn(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 38: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Network.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getProxyForNetwork((Network)object);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ProxyInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 37: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ProxyInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setGlobalProxy((ProxyInfo)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 36: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getGlobalProxy();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ProxyInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 35: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Network network = ((Parcel)object).readInt() != 0 ? Network.CREATOR.createFromParcel((Parcel)object) : null;
                        bl10 = bl7;
                        if (((Parcel)object).readInt() != 0) {
                            bl10 = true;
                        }
                        this.reportNetworkConnectivity(network, bl10);
                        parcel.writeNoException();
                        return true;
                    }
                    case 34: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.reportInetCondition(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 33: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl10 = bl8;
                        if (((Parcel)object).readInt() != 0) {
                            bl10 = true;
                        }
                        n = this.setUsbTethering(bl10, ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 32: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getTetherableBluetoothRegexs();
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 31: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getTetherableWifiRegexs();
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 30: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getTetherableUsbRegexs();
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getTetheredDhcpRanges();
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getTetheringErroredIfaces();
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getTetheredIfaces();
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getTetherableIfaces();
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.stopTethering(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        ResultReceiver resultReceiver = ((Parcel)object).readInt() != 0 ? ResultReceiver.CREATOR.createFromParcel((Parcel)object) : null;
                        bl10 = bl9;
                        if (((Parcel)object).readInt() != 0) {
                            bl10 = true;
                        }
                        this.startTethering(n, resultReceiver, bl10, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isTetheringSupported(((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getLastTetherError(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.untether(((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.tether(((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.requestRouteToHostAddress(((Parcel)object).readInt(), ((Parcel)object).createByteArray()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isActiveNetworkMetered() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getActiveNetworkQuotaInfo();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((NetworkQuotaInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAllNetworkState();
                        parcel.writeNoException();
                        parcel.writeTypedArray((Parcelable[])object, 1);
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Network.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getNetworkCapabilities((Network)object);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((NetworkCapabilities)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Network.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getLinkProperties((Network)object);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((LinkProperties)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getLinkPropertiesForType(((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((LinkProperties)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getActiveLinkProperties();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((LinkProperties)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isNetworkSupported(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getDefaultNetworkCapabilitiesForUser(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeTypedArray((Parcelable[])object, 1);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAllNetworks();
                        parcel.writeNoException();
                        parcel.writeTypedArray((Parcelable[])object, 1);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getNetworkForType(((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Network)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAllNetworkInfo();
                        parcel.writeNoException();
                        parcel.writeTypedArray((Parcelable[])object, 1);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Network network = ((Parcel)object).readInt() != 0 ? Network.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        bl10 = ((Parcel)object).readInt() != 0;
                        object = this.getNetworkInfoForUid(network, n, bl10);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((NetworkInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getNetworkInfo(((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((NetworkInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl10 = ((Parcel)object).readInt() != 0;
                        object = this.getActiveNetworkInfoForUid(n, bl10);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((NetworkInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getActiveNetworkInfo();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((NetworkInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl10 = ((Parcel)object).readInt() != 0;
                        object = this.getActiveNetworkForUid(n, bl10);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Network)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = this.getActiveNetwork();
                parcel.writeNoException();
                if (object != null) {
                    parcel.writeInt(1);
                    ((Network)object).writeToParcel(parcel, 1);
                } else {
                    parcel.writeInt(0);
                }
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IConnectivityManager {
            public static IConnectivityManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public boolean addVpnAddress(String string2, int n) throws RemoteException {
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
                        parcel2.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(74, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().addVpnAddress(string2, n);
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
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public int checkMobileProvisioning(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(51, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().checkMobileProvisioning(n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public ParcelFileDescriptor establishVpn(VpnConfig parcelable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((VpnConfig)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(41, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        ParcelFileDescriptor parcelFileDescriptor = Stub.getDefaultImpl().establishVpn((VpnConfig)parcelable);
                        parcel2.recycle();
                        parcel.recycle();
                        return parcelFileDescriptor;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        ParcelFileDescriptor parcelFileDescriptor = ParcelFileDescriptor.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public void factoryReset() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(77, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().factoryReset();
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
            public LinkProperties getActiveLinkProperties() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(12, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        LinkProperties linkProperties = Stub.getDefaultImpl().getActiveLinkProperties();
                        parcel.recycle();
                        parcel2.recycle();
                        return linkProperties;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                LinkProperties linkProperties = parcel.readInt() != 0 ? LinkProperties.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return linkProperties;
            }

            @Override
            public Network getActiveNetwork() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(1, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        Network network = Stub.getDefaultImpl().getActiveNetwork();
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
            public Network getActiveNetworkForUid(int n, boolean bl) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block4 : {
                    int n2;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        n2 = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    parcel2.writeInt(n2);
                    if (this.mRemote.transact(2, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block4;
                    Network network = Stub.getDefaultImpl().getActiveNetworkForUid(n, bl);
                    parcel.recycle();
                    parcel2.recycle();
                    return network;
                }
                parcel.readException();
                Network network = parcel.readInt() != 0 ? Network.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return network;
            }

            @Override
            public NetworkInfo getActiveNetworkInfo() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(3, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        NetworkInfo networkInfo = Stub.getDefaultImpl().getActiveNetworkInfo();
                        parcel.recycle();
                        parcel2.recycle();
                        return networkInfo;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                NetworkInfo networkInfo = parcel.readInt() != 0 ? NetworkInfo.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return networkInfo;
            }

            @Override
            public NetworkInfo getActiveNetworkInfoForUid(int n, boolean bl) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block4 : {
                    int n2;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        n2 = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    parcel2.writeInt(n2);
                    if (this.mRemote.transact(4, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block4;
                    NetworkInfo networkInfo = Stub.getDefaultImpl().getActiveNetworkInfoForUid(n, bl);
                    parcel.recycle();
                    parcel2.recycle();
                    return networkInfo;
                }
                parcel.readException();
                NetworkInfo networkInfo = parcel.readInt() != 0 ? NetworkInfo.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return networkInfo;
            }

            @Override
            public NetworkQuotaInfo getActiveNetworkQuotaInfo() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(17, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        NetworkQuotaInfo networkQuotaInfo = Stub.getDefaultImpl().getActiveNetworkQuotaInfo();
                        parcel.recycle();
                        parcel2.recycle();
                        return networkQuotaInfo;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                NetworkQuotaInfo networkQuotaInfo = parcel.readInt() != 0 ? NetworkQuotaInfo.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return networkQuotaInfo;
            }

            @Override
            public NetworkInfo[] getAllNetworkInfo() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        NetworkInfo[] arrnetworkInfo = Stub.getDefaultImpl().getAllNetworkInfo();
                        return arrnetworkInfo;
                    }
                    parcel2.readException();
                    NetworkInfo[] arrnetworkInfo = parcel2.createTypedArray(NetworkInfo.CREATOR);
                    return arrnetworkInfo;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public NetworkState[] getAllNetworkState() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        NetworkState[] arrnetworkState = Stub.getDefaultImpl().getAllNetworkState();
                        return arrnetworkState;
                    }
                    parcel2.readException();
                    NetworkState[] arrnetworkState = parcel2.createTypedArray(NetworkState.CREATOR);
                    return arrnetworkState;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public Network[] getAllNetworks() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Network[] arrnetwork = Stub.getDefaultImpl().getAllNetworks();
                        return arrnetwork;
                    }
                    parcel2.readException();
                    Network[] arrnetwork = parcel2.createTypedArray(Network.CREATOR);
                    return arrnetwork;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getAlwaysOnVpnPackage(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(48, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getAlwaysOnVpnPackage(n);
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
            public String getCaptivePortalServerUrl() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(82, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getCaptivePortalServerUrl();
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
            public int getConnectionOwnerUid(ConnectionInfo connectionInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (connectionInfo != null) {
                        parcel.writeInt(1);
                        connectionInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(84, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getConnectionOwnerUid(connectionInfo);
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
            public NetworkCapabilities[] getDefaultNetworkCapabilitiesForUser(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        NetworkCapabilities[] arrnetworkCapabilities = Stub.getDefaultImpl().getDefaultNetworkCapabilitiesForUser(n);
                        return arrnetworkCapabilities;
                    }
                    parcel2.readException();
                    NetworkCapabilities[] arrnetworkCapabilities = parcel2.createTypedArray(NetworkCapabilities.CREATOR);
                    return arrnetworkCapabilities;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public NetworkRequest getDefaultRequest() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(72, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        NetworkRequest networkRequest = Stub.getDefaultImpl().getDefaultRequest();
                        parcel.recycle();
                        parcel2.recycle();
                        return networkRequest;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                NetworkRequest networkRequest = parcel.readInt() != 0 ? NetworkRequest.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return networkRequest;
            }

            @Override
            public ProxyInfo getGlobalProxy() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(36, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        ProxyInfo proxyInfo = Stub.getDefaultImpl().getGlobalProxy();
                        parcel.recycle();
                        parcel2.recycle();
                        return proxyInfo;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                ProxyInfo proxyInfo = parcel.readInt() != 0 ? ProxyInfo.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return proxyInfo;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public int getLastTetherError(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(22, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getLastTetherError(string2);
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
            public void getLatestTetheringEntitlementResult(int n, ResultReceiver resultReceiver, boolean bl, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    int n2 = 1;
                    if (resultReceiver != null) {
                        parcel.writeInt(1);
                        resultReceiver.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(87, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getLatestTetheringEntitlementResult(n, resultReceiver, bl, string2);
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
            public LegacyVpnInfo getLegacyVpnInfo(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(44, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        LegacyVpnInfo legacyVpnInfo = Stub.getDefaultImpl().getLegacyVpnInfo(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return legacyVpnInfo;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                LegacyVpnInfo legacyVpnInfo = parcel2.readInt() != 0 ? LegacyVpnInfo.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return legacyVpnInfo;
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public LinkProperties getLinkProperties(Network parcelable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((Network)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        LinkProperties linkProperties = Stub.getDefaultImpl().getLinkProperties((Network)parcelable);
                        parcel2.recycle();
                        parcel.recycle();
                        return linkProperties;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        LinkProperties linkProperties = LinkProperties.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public LinkProperties getLinkPropertiesForType(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(13, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        LinkProperties linkProperties = Stub.getDefaultImpl().getLinkPropertiesForType(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return linkProperties;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                LinkProperties linkProperties = parcel2.readInt() != 0 ? LinkProperties.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return linkProperties;
            }

            @Override
            public String getMobileProvisioningUrl() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(52, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getMobileProvisioningUrl();
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
            public int getMultipathPreference(Network network) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (network != null) {
                        parcel.writeInt(1);
                        network.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(71, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getMultipathPreference(network);
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
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public NetworkCapabilities getNetworkCapabilities(Network parcelable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((Network)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        NetworkCapabilities networkCapabilities = Stub.getDefaultImpl().getNetworkCapabilities((Network)parcelable);
                        parcel2.recycle();
                        parcel.recycle();
                        return networkCapabilities;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        NetworkCapabilities networkCapabilities = NetworkCapabilities.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public Network getNetworkForType(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(8, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        Network network = Stub.getDefaultImpl().getNetworkForType(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return network;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                Network network = parcel2.readInt() != 0 ? Network.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return network;
            }

            @Override
            public NetworkInfo getNetworkInfo(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(5, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        NetworkInfo networkInfo = Stub.getDefaultImpl().getNetworkInfo(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return networkInfo;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                NetworkInfo networkInfo = parcel2.readInt() != 0 ? NetworkInfo.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return networkInfo;
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public NetworkInfo getNetworkInfoForUid(Network parcelable, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var3_8;
                    void var2_7;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 1;
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((Network)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt((int)var2_7);
                    if (var3_8 == false) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        NetworkInfo networkInfo = Stub.getDefaultImpl().getNetworkInfoForUid((Network)parcelable, (int)var2_7, (boolean)var3_8);
                        parcel2.recycle();
                        parcel.recycle();
                        return networkInfo;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        NetworkInfo networkInfo = NetworkInfo.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public byte[] getNetworkWatchlistConfigHash() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(83, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        byte[] arrby = Stub.getDefaultImpl().getNetworkWatchlistConfigHash();
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

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public ProxyInfo getProxyForNetwork(Network parcelable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((Network)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(38, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        ProxyInfo proxyInfo = Stub.getDefaultImpl().getProxyForNetwork((Network)parcelable);
                        parcel2.recycle();
                        parcel.recycle();
                        return proxyInfo;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        ProxyInfo proxyInfo = ProxyInfo.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public int getRestoreDefaultNetworkDelay(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(73, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getRestoreDefaultNetworkDelay(n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String[] getTetherableBluetoothRegexs() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(32, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String[] arrstring = Stub.getDefaultImpl().getTetherableBluetoothRegexs();
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

            @Override
            public String[] getTetherableIfaces() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(26, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String[] arrstring = Stub.getDefaultImpl().getTetherableIfaces();
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

            @Override
            public String[] getTetherableUsbRegexs() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(30, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String[] arrstring = Stub.getDefaultImpl().getTetherableUsbRegexs();
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

            @Override
            public String[] getTetherableWifiRegexs() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(31, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String[] arrstring = Stub.getDefaultImpl().getTetherableWifiRegexs();
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

            @Override
            public String[] getTetheredDhcpRanges() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(29, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String[] arrstring = Stub.getDefaultImpl().getTetheredDhcpRanges();
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

            @Override
            public String[] getTetheredIfaces() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(27, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String[] arrstring = Stub.getDefaultImpl().getTetheredIfaces();
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

            @Override
            public String[] getTetheringErroredIfaces() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(28, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String[] arrstring = Stub.getDefaultImpl().getTetheringErroredIfaces();
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

            @Override
            public VpnConfig getVpnConfig(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(42, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        VpnConfig vpnConfig = Stub.getDefaultImpl().getVpnConfig(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return vpnConfig;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                VpnConfig vpnConfig = parcel2.readInt() != 0 ? VpnConfig.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return vpnConfig;
            }

            @Override
            public List<String> getVpnLockdownWhitelist(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(50, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<String> list = Stub.getDefaultImpl().getVpnLockdownWhitelist(n);
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<String> arrayList = parcel2.createStringArrayList();
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean isActiveNetworkMetered() throws RemoteException {
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
                    if (iBinder.transact(18, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isActiveNetworkMetered();
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
            public boolean isAlwaysOnVpnPackageSupported(int n, String string2) throws RemoteException {
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
                    if (iBinder.transact(46, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isAlwaysOnVpnPackageSupported(n, string2);
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
            public boolean isCallerCurrentAlwaysOnVpnApp() throws RemoteException {
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
                    if (iBinder.transact(85, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isCallerCurrentAlwaysOnVpnApp();
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
            public boolean isCallerCurrentAlwaysOnVpnLockdownApp() throws RemoteException {
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
                    if (iBinder.transact(86, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isCallerCurrentAlwaysOnVpnLockdownApp();
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
            public boolean isNetworkSupported(int n) throws RemoteException {
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
                    if (iBinder.transact(11, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isNetworkSupported(n);
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
            public boolean isTetheringSupported(String string2) throws RemoteException {
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
                    bl = Stub.getDefaultImpl().isTetheringSupported(string2);
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
            public boolean isVpnLockdownEnabled(int n) throws RemoteException {
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
                    if (iBinder.transact(49, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isVpnLockdownEnabled(n);
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

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public NetworkRequest listenForNetwork(NetworkCapabilities parcelable, Messenger messenger, IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var3_8;
                    void var2_7;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((NetworkCapabilities)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (var2_7 != null) {
                        parcel.writeInt(1);
                        var2_7.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeStrongBinder((IBinder)var3_8);
                    if (!this.mRemote.transact(62, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        NetworkRequest networkRequest = Stub.getDefaultImpl().listenForNetwork((NetworkCapabilities)parcelable, (Messenger)var2_7, (IBinder)var3_8);
                        parcel2.recycle();
                        parcel.recycle();
                        return networkRequest;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        NetworkRequest networkRequest = NetworkRequest.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public void pendingListenForNetwork(NetworkCapabilities networkCapabilities, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (networkCapabilities != null) {
                        parcel.writeInt(1);
                        networkCapabilities.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (pendingIntent != null) {
                        parcel.writeInt(1);
                        pendingIntent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(63, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().pendingListenForNetwork(networkCapabilities, pendingIntent);
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
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public NetworkRequest pendingRequestForNetwork(NetworkCapabilities parcelable, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var2_7;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((NetworkCapabilities)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (var2_7 != null) {
                        parcel.writeInt(1);
                        var2_7.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(60, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        NetworkRequest networkRequest = Stub.getDefaultImpl().pendingRequestForNetwork((NetworkCapabilities)parcelable, (PendingIntent)var2_7);
                        parcel2.recycle();
                        parcel.recycle();
                        return networkRequest;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        NetworkRequest networkRequest = NetworkRequest.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public boolean prepareVpn(String string2, String string3, int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        parcel.writeString(string3);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(39, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().prepareVpn(string2, string3, n);
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
            public int registerNetworkAgent(Messenger messenger, NetworkInfo networkInfo, LinkProperties linkProperties, NetworkCapabilities networkCapabilities, int n, NetworkMisc networkMisc, int n2) throws RemoteException {
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
                    if (networkInfo != null) {
                        parcel.writeInt(1);
                        networkInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (linkProperties != null) {
                        parcel.writeInt(1);
                        linkProperties.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (networkCapabilities != null) {
                        parcel.writeInt(1);
                        networkCapabilities.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (networkMisc != null) {
                        parcel.writeInt(1);
                        networkMisc.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(58, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().registerNetworkAgent(messenger, networkInfo, linkProperties, networkCapabilities, n, networkMisc, n2);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int registerNetworkFactory(Messenger messenger, String string2) throws RemoteException {
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
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(55, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().registerNetworkFactory(messenger, string2);
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
            public void registerTetheringEventCallback(ITetheringEventCallback iTetheringEventCallback, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iTetheringEventCallback != null ? iTetheringEventCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(88, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerTetheringEventCallback(iTetheringEventCallback, string2);
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
            public void releaseNetworkRequest(NetworkRequest networkRequest) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (networkRequest != null) {
                        parcel.writeInt(1);
                        networkRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(64, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().releaseNetworkRequest(networkRequest);
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
            public void releasePendingNetworkRequest(PendingIntent pendingIntent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (pendingIntent != null) {
                        parcel.writeInt(1);
                        pendingIntent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(61, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().releasePendingNetworkRequest(pendingIntent);
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
            public boolean removeVpnAddress(String string2, int n) throws RemoteException {
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
                        parcel2.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(75, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().removeVpnAddress(string2, n);
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
            public void reportInetCondition(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(34, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reportInetCondition(n, n2);
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
            public void reportNetworkConnectivity(Network network, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 1;
                    if (network != null) {
                        parcel.writeInt(1);
                        network.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(35, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reportNetworkConnectivity(network, bl);
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
            public boolean requestBandwidthUpdate(Network network) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (network != null) {
                        parcel.writeInt(1);
                        network.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(56, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().requestBandwidthUpdate(network);
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
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public NetworkRequest requestNetwork(NetworkCapabilities parcelable, Messenger messenger, int n, IBinder iBinder, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var3_8;
                    void var2_7;
                    void var4_9;
                    void var5_10;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((NetworkCapabilities)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (var2_7 != null) {
                        parcel.writeInt(1);
                        var2_7.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt((int)var3_8);
                    parcel.writeStrongBinder((IBinder)var4_9);
                    parcel.writeInt((int)var5_10);
                    if (!this.mRemote.transact(59, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        NetworkRequest networkRequest = Stub.getDefaultImpl().requestNetwork((NetworkCapabilities)parcelable, (Messenger)var2_7, (int)var3_8, (IBinder)var4_9, (int)var5_10);
                        parcel2.recycle();
                        parcel.recycle();
                        return networkRequest;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        NetworkRequest networkRequest = NetworkRequest.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public boolean requestRouteToHostAddress(int n, byte[] arrby) throws RemoteException {
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
                        parcel2.writeByteArray(arrby);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(19, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().requestRouteToHostAddress(n, arrby);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setAcceptPartialConnectivity(Network network, boolean bl, boolean bl2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 1;
                    if (network != null) {
                        parcel.writeInt(1);
                        network.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    int n2 = bl ? 1 : 0;
                    parcel.writeInt(n2);
                    n2 = bl2 ? n : 0;
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(66, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAcceptPartialConnectivity(network, bl, bl2);
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
            public void setAcceptUnvalidated(Network network, boolean bl, boolean bl2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 1;
                    if (network != null) {
                        parcel.writeInt(1);
                        network.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    int n2 = bl ? 1 : 0;
                    parcel.writeInt(n2);
                    n2 = bl2 ? n : 0;
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(65, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAcceptUnvalidated(network, bl, bl2);
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
            public void setAirplaneMode(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(54, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAirplaneMode(bl);
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
            public boolean setAlwaysOnVpnPackage(int n, String string2, boolean bl, List<String> list) throws RemoteException {
                boolean bl2;
                Parcel parcel;
                Parcel parcel2;
                block4 : {
                    int n2;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeString(string2);
                        bl2 = true;
                        n2 = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    parcel2.writeInt(n2);
                    parcel2.writeStringList(list);
                    if (this.mRemote.transact(47, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().setAlwaysOnVpnPackage(n, string2, bl, list);
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

            @Override
            public void setAvoidUnvalidated(Network network) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (network != null) {
                        parcel.writeInt(1);
                        network.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(67, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAvoidUnvalidated(network);
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
            public void setGlobalProxy(ProxyInfo proxyInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (proxyInfo != null) {
                        parcel.writeInt(1);
                        proxyInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(37, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setGlobalProxy(proxyInfo);
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
            public void setProvisioningNotificationVisible(boolean bl, int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(53, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setProvisioningNotificationVisible(bl, n, string2);
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
            public boolean setUnderlyingNetworksForVpn(Network[] arrnetwork) throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    parcel.writeTypedArray((Parcelable[])arrnetwork, 0);
                    if (this.mRemote.transact(76, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().setUnderlyingNetworksForVpn(arrnetwork);
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
            public int setUsbTethering(boolean bl, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(33, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().setUsbTethering(bl, string2);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setVpnPackageAuthorization(String string2, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(40, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setVpnPackageAuthorization(string2, n, bl);
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
            public boolean shouldAvoidBadWifi() throws RemoteException {
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
                    if (iBinder.transact(70, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().shouldAvoidBadWifi();
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
            public void startCaptivePortalApp(Network network) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (network != null) {
                        parcel.writeInt(1);
                        network.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(68, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startCaptivePortalApp(network);
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
            public void startCaptivePortalAppInternal(Network network, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (network != null) {
                        parcel.writeInt(1);
                        network.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(69, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startCaptivePortalAppInternal(network, bundle);
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
            public void startLegacyVpn(VpnProfile vpnProfile) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (vpnProfile != null) {
                        parcel.writeInt(1);
                        vpnProfile.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(43, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startLegacyVpn(vpnProfile);
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
            public void startNattKeepalive(Network network, int n, ISocketKeepaliveCallback iSocketKeepaliveCallback, String string2, int n2, String string3) throws RemoteException {
                Parcel parcel;
                void var1_8;
                Parcel parcel2;
                block16 : {
                    block15 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (network != null) {
                            parcel2.writeInt(1);
                            network.writeToParcel(parcel2, 0);
                            break block15;
                        }
                        parcel2.writeInt(0);
                    }
                    try {
                        parcel2.writeInt(n);
                        IBinder iBinder = iSocketKeepaliveCallback != null ? iSocketKeepaliveCallback.asBinder() : null;
                        parcel2.writeStrongBinder(iBinder);
                    }
                    catch (Throwable throwable) {}
                    try {
                        parcel2.writeString(string2);
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
                        parcel2.writeString(string3);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        if (!this.mRemote.transact(78, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().startNattKeepalive(network, n, iSocketKeepaliveCallback, string2, n2, string3);
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
                throw var1_8;
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
            public void startNattKeepaliveWithFd(Network network, FileDescriptor fileDescriptor, int n, int n2, ISocketKeepaliveCallback iSocketKeepaliveCallback, String string2, String string3) throws RemoteException {
                Parcel parcel;
                void var1_7;
                Parcel parcel2;
                block14 : {
                    block13 : {
                        parcel = Parcel.obtain();
                        parcel2 = Parcel.obtain();
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (network != null) {
                            parcel.writeInt(1);
                            network.writeToParcel(parcel, 0);
                            break block13;
                        }
                        parcel.writeInt(0);
                    }
                    try {
                        parcel.writeRawFileDescriptor(fileDescriptor);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeInt(n2);
                        IBinder iBinder = iSocketKeepaliveCallback != null ? iSocketKeepaliveCallback.asBinder() : null;
                        parcel.writeStrongBinder(iBinder);
                    }
                    catch (Throwable throwable) {}
                    try {
                        parcel.writeString(string2);
                        parcel.writeString(string3);
                        if (!this.mRemote.transact(79, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().startNattKeepaliveWithFd(network, fileDescriptor, n, n2, iSocketKeepaliveCallback, string2, string3);
                            parcel2.recycle();
                            parcel.recycle();
                            return;
                        }
                        parcel2.readException();
                        parcel2.recycle();
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block14;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var1_7;
            }

            @Override
            public IBinder startOrGetTestNetworkService() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(90, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        IBinder iBinder = Stub.getDefaultImpl().startOrGetTestNetworkService();
                        return iBinder;
                    }
                    parcel2.readException();
                    IBinder iBinder = parcel2.readStrongBinder();
                    return iBinder;
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
            public void startTcpKeepalive(Network network, FileDescriptor fileDescriptor, int n, ISocketKeepaliveCallback iSocketKeepaliveCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (network != null) {
                        parcel.writeInt(1);
                        network.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeRawFileDescriptor(fileDescriptor);
                    parcel.writeInt(n);
                    IBinder iBinder = iSocketKeepaliveCallback != null ? iSocketKeepaliveCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(80, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startTcpKeepalive(network, fileDescriptor, n, iSocketKeepaliveCallback);
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
            public void startTethering(int n, ResultReceiver resultReceiver, boolean bl, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    int n2 = 1;
                    if (resultReceiver != null) {
                        parcel.writeInt(1);
                        resultReceiver.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(24, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startTethering(n, resultReceiver, bl, string2);
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
            public void stopKeepalive(Network network, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (network != null) {
                        parcel.writeInt(1);
                        network.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(81, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopKeepalive(network, n);
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
            public void stopTethering(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(25, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopTethering(n, string2);
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
            public int tether(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().tether(string2, string3);
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
            public void unregisterNetworkFactory(Messenger messenger) throws RemoteException {
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
                    if (!this.mRemote.transact(57, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterNetworkFactory(messenger);
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
            public void unregisterTetheringEventCallback(ITetheringEventCallback iTetheringEventCallback, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iTetheringEventCallback != null ? iTetheringEventCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(89, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterTetheringEventCallback(iTetheringEventCallback, string2);
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
            public int untether(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(21, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().untether(string2, string3);
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
            public boolean updateLockdownVpn() throws RemoteException {
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
                    if (iBinder.transact(45, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().updateLockdownVpn();
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
        }

    }

}

