/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.net.INetworkManagementEventObserver;
import android.net.ITetheringStatsProvider;
import android.net.InterfaceConfiguration;
import android.net.Network;
import android.net.NetworkStats;
import android.net.RouteInfo;
import android.net.UidRange;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.INetworkActivityListener;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface INetworkManagementService
extends IInterface {
    public void addIdleTimer(String var1, int var2, int var3) throws RemoteException;

    public void addInterfaceToLocalNetwork(String var1, List<RouteInfo> var2) throws RemoteException;

    public void addInterfaceToNetwork(String var1, int var2) throws RemoteException;

    public void addLegacyRouteForNetId(int var1, RouteInfo var2, int var3) throws RemoteException;

    public void addRoute(int var1, RouteInfo var2) throws RemoteException;

    public void addVpnUidRanges(int var1, UidRange[] var2) throws RemoteException;

    public void allowProtect(int var1) throws RemoteException;

    public void clearDefaultNetId() throws RemoteException;

    @UnsupportedAppUsage
    public void clearInterfaceAddresses(String var1) throws RemoteException;

    public void denyProtect(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public void disableIpv6(String var1) throws RemoteException;

    @UnsupportedAppUsage
    public void disableNat(String var1, String var2) throws RemoteException;

    @UnsupportedAppUsage
    public void enableIpv6(String var1) throws RemoteException;

    @UnsupportedAppUsage
    public void enableNat(String var1, String var2) throws RemoteException;

    public String[] getDnsForwarders() throws RemoteException;

    @UnsupportedAppUsage
    public InterfaceConfiguration getInterfaceConfig(String var1) throws RemoteException;

    @UnsupportedAppUsage
    public boolean getIpForwardingEnabled() throws RemoteException;

    public NetworkStats getNetworkStatsDetail() throws RemoteException;

    public NetworkStats getNetworkStatsSummaryDev() throws RemoteException;

    public NetworkStats getNetworkStatsSummaryXt() throws RemoteException;

    public NetworkStats getNetworkStatsTethering(int var1) throws RemoteException;

    public NetworkStats getNetworkStatsUidDetail(int var1, String[] var2) throws RemoteException;

    @UnsupportedAppUsage
    public boolean isBandwidthControlEnabled() throws RemoteException;

    public boolean isFirewallEnabled() throws RemoteException;

    public boolean isNetworkActive() throws RemoteException;

    public boolean isNetworkRestricted(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public boolean isTetheringStarted() throws RemoteException;

    public String[] listInterfaces() throws RemoteException;

    public String[] listTetheredInterfaces() throws RemoteException;

    public void registerNetworkActivityListener(INetworkActivityListener var1) throws RemoteException;

    @UnsupportedAppUsage
    public void registerObserver(INetworkManagementEventObserver var1) throws RemoteException;

    public void registerTetheringStatsProvider(ITetheringStatsProvider var1, String var2) throws RemoteException;

    public void removeIdleTimer(String var1) throws RemoteException;

    public void removeInterfaceAlert(String var1) throws RemoteException;

    public void removeInterfaceFromLocalNetwork(String var1) throws RemoteException;

    public void removeInterfaceFromNetwork(String var1, int var2) throws RemoteException;

    public void removeInterfaceQuota(String var1) throws RemoteException;

    public void removeRoute(int var1, RouteInfo var2) throws RemoteException;

    public int removeRoutesFromLocalNetwork(List<RouteInfo> var1) throws RemoteException;

    public void removeVpnUidRanges(int var1, UidRange[] var2) throws RemoteException;

    public void setAllowOnlyVpnForUids(boolean var1, UidRange[] var2) throws RemoteException;

    public boolean setDataSaverModeEnabled(boolean var1) throws RemoteException;

    public void setDefaultNetId(int var1) throws RemoteException;

    public void setDnsForwarders(Network var1, String[] var2) throws RemoteException;

    public void setFirewallChainEnabled(int var1, boolean var2) throws RemoteException;

    public void setFirewallEnabled(boolean var1) throws RemoteException;

    public void setFirewallInterfaceRule(String var1, boolean var2) throws RemoteException;

    public void setFirewallUidRule(int var1, int var2, int var3) throws RemoteException;

    public void setFirewallUidRules(int var1, int[] var2, int[] var3) throws RemoteException;

    public void setGlobalAlert(long var1) throws RemoteException;

    @UnsupportedAppUsage
    public void setIPv6AddrGenMode(String var1, int var2) throws RemoteException;

    public void setInterfaceAlert(String var1, long var2) throws RemoteException;

    @UnsupportedAppUsage
    public void setInterfaceConfig(String var1, InterfaceConfiguration var2) throws RemoteException;

    public void setInterfaceDown(String var1) throws RemoteException;

    @UnsupportedAppUsage
    public void setInterfaceIpv6PrivacyExtensions(String var1, boolean var2) throws RemoteException;

    public void setInterfaceQuota(String var1, long var2) throws RemoteException;

    public void setInterfaceUp(String var1) throws RemoteException;

    @UnsupportedAppUsage
    public void setIpForwardingEnabled(boolean var1) throws RemoteException;

    public void setMtu(String var1, int var2) throws RemoteException;

    public void setNetworkPermission(int var1, int var2) throws RemoteException;

    public void setUidCleartextNetworkPolicy(int var1, int var2) throws RemoteException;

    public void setUidMeteredNetworkBlacklist(int var1, boolean var2) throws RemoteException;

    public void setUidMeteredNetworkWhitelist(int var1, boolean var2) throws RemoteException;

    public void shutdown() throws RemoteException;

    public void startInterfaceForwarding(String var1, String var2) throws RemoteException;

    @UnsupportedAppUsage
    public void startTethering(String[] var1) throws RemoteException;

    public void stopInterfaceForwarding(String var1, String var2) throws RemoteException;

    @UnsupportedAppUsage
    public void stopTethering() throws RemoteException;

    @UnsupportedAppUsage
    public void tetherInterface(String var1) throws RemoteException;

    public void tetherLimitReached(ITetheringStatsProvider var1) throws RemoteException;

    public void unregisterNetworkActivityListener(INetworkActivityListener var1) throws RemoteException;

    @UnsupportedAppUsage
    public void unregisterObserver(INetworkManagementEventObserver var1) throws RemoteException;

    public void unregisterTetheringStatsProvider(ITetheringStatsProvider var1) throws RemoteException;

    @UnsupportedAppUsage
    public void untetherInterface(String var1) throws RemoteException;

    public static class Default
    implements INetworkManagementService {
        @Override
        public void addIdleTimer(String string2, int n, int n2) throws RemoteException {
        }

        @Override
        public void addInterfaceToLocalNetwork(String string2, List<RouteInfo> list) throws RemoteException {
        }

        @Override
        public void addInterfaceToNetwork(String string2, int n) throws RemoteException {
        }

        @Override
        public void addLegacyRouteForNetId(int n, RouteInfo routeInfo, int n2) throws RemoteException {
        }

        @Override
        public void addRoute(int n, RouteInfo routeInfo) throws RemoteException {
        }

        @Override
        public void addVpnUidRanges(int n, UidRange[] arruidRange) throws RemoteException {
        }

        @Override
        public void allowProtect(int n) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void clearDefaultNetId() throws RemoteException {
        }

        @Override
        public void clearInterfaceAddresses(String string2) throws RemoteException {
        }

        @Override
        public void denyProtect(int n) throws RemoteException {
        }

        @Override
        public void disableIpv6(String string2) throws RemoteException {
        }

        @Override
        public void disableNat(String string2, String string3) throws RemoteException {
        }

        @Override
        public void enableIpv6(String string2) throws RemoteException {
        }

        @Override
        public void enableNat(String string2, String string3) throws RemoteException {
        }

        @Override
        public String[] getDnsForwarders() throws RemoteException {
            return null;
        }

        @Override
        public InterfaceConfiguration getInterfaceConfig(String string2) throws RemoteException {
            return null;
        }

        @Override
        public boolean getIpForwardingEnabled() throws RemoteException {
            return false;
        }

        @Override
        public NetworkStats getNetworkStatsDetail() throws RemoteException {
            return null;
        }

        @Override
        public NetworkStats getNetworkStatsSummaryDev() throws RemoteException {
            return null;
        }

        @Override
        public NetworkStats getNetworkStatsSummaryXt() throws RemoteException {
            return null;
        }

        @Override
        public NetworkStats getNetworkStatsTethering(int n) throws RemoteException {
            return null;
        }

        @Override
        public NetworkStats getNetworkStatsUidDetail(int n, String[] arrstring) throws RemoteException {
            return null;
        }

        @Override
        public boolean isBandwidthControlEnabled() throws RemoteException {
            return false;
        }

        @Override
        public boolean isFirewallEnabled() throws RemoteException {
            return false;
        }

        @Override
        public boolean isNetworkActive() throws RemoteException {
            return false;
        }

        @Override
        public boolean isNetworkRestricted(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isTetheringStarted() throws RemoteException {
            return false;
        }

        @Override
        public String[] listInterfaces() throws RemoteException {
            return null;
        }

        @Override
        public String[] listTetheredInterfaces() throws RemoteException {
            return null;
        }

        @Override
        public void registerNetworkActivityListener(INetworkActivityListener iNetworkActivityListener) throws RemoteException {
        }

        @Override
        public void registerObserver(INetworkManagementEventObserver iNetworkManagementEventObserver) throws RemoteException {
        }

        @Override
        public void registerTetheringStatsProvider(ITetheringStatsProvider iTetheringStatsProvider, String string2) throws RemoteException {
        }

        @Override
        public void removeIdleTimer(String string2) throws RemoteException {
        }

        @Override
        public void removeInterfaceAlert(String string2) throws RemoteException {
        }

        @Override
        public void removeInterfaceFromLocalNetwork(String string2) throws RemoteException {
        }

        @Override
        public void removeInterfaceFromNetwork(String string2, int n) throws RemoteException {
        }

        @Override
        public void removeInterfaceQuota(String string2) throws RemoteException {
        }

        @Override
        public void removeRoute(int n, RouteInfo routeInfo) throws RemoteException {
        }

        @Override
        public int removeRoutesFromLocalNetwork(List<RouteInfo> list) throws RemoteException {
            return 0;
        }

        @Override
        public void removeVpnUidRanges(int n, UidRange[] arruidRange) throws RemoteException {
        }

        @Override
        public void setAllowOnlyVpnForUids(boolean bl, UidRange[] arruidRange) throws RemoteException {
        }

        @Override
        public boolean setDataSaverModeEnabled(boolean bl) throws RemoteException {
            return false;
        }

        @Override
        public void setDefaultNetId(int n) throws RemoteException {
        }

        @Override
        public void setDnsForwarders(Network network, String[] arrstring) throws RemoteException {
        }

        @Override
        public void setFirewallChainEnabled(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void setFirewallEnabled(boolean bl) throws RemoteException {
        }

        @Override
        public void setFirewallInterfaceRule(String string2, boolean bl) throws RemoteException {
        }

        @Override
        public void setFirewallUidRule(int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void setFirewallUidRules(int n, int[] arrn, int[] arrn2) throws RemoteException {
        }

        @Override
        public void setGlobalAlert(long l) throws RemoteException {
        }

        @Override
        public void setIPv6AddrGenMode(String string2, int n) throws RemoteException {
        }

        @Override
        public void setInterfaceAlert(String string2, long l) throws RemoteException {
        }

        @Override
        public void setInterfaceConfig(String string2, InterfaceConfiguration interfaceConfiguration) throws RemoteException {
        }

        @Override
        public void setInterfaceDown(String string2) throws RemoteException {
        }

        @Override
        public void setInterfaceIpv6PrivacyExtensions(String string2, boolean bl) throws RemoteException {
        }

        @Override
        public void setInterfaceQuota(String string2, long l) throws RemoteException {
        }

        @Override
        public void setInterfaceUp(String string2) throws RemoteException {
        }

        @Override
        public void setIpForwardingEnabled(boolean bl) throws RemoteException {
        }

        @Override
        public void setMtu(String string2, int n) throws RemoteException {
        }

        @Override
        public void setNetworkPermission(int n, int n2) throws RemoteException {
        }

        @Override
        public void setUidCleartextNetworkPolicy(int n, int n2) throws RemoteException {
        }

        @Override
        public void setUidMeteredNetworkBlacklist(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void setUidMeteredNetworkWhitelist(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void shutdown() throws RemoteException {
        }

        @Override
        public void startInterfaceForwarding(String string2, String string3) throws RemoteException {
        }

        @Override
        public void startTethering(String[] arrstring) throws RemoteException {
        }

        @Override
        public void stopInterfaceForwarding(String string2, String string3) throws RemoteException {
        }

        @Override
        public void stopTethering() throws RemoteException {
        }

        @Override
        public void tetherInterface(String string2) throws RemoteException {
        }

        @Override
        public void tetherLimitReached(ITetheringStatsProvider iTetheringStatsProvider) throws RemoteException {
        }

        @Override
        public void unregisterNetworkActivityListener(INetworkActivityListener iNetworkActivityListener) throws RemoteException {
        }

        @Override
        public void unregisterObserver(INetworkManagementEventObserver iNetworkManagementEventObserver) throws RemoteException {
        }

        @Override
        public void unregisterTetheringStatsProvider(ITetheringStatsProvider iTetheringStatsProvider) throws RemoteException {
        }

        @Override
        public void untetherInterface(String string2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements INetworkManagementService {
        private static final String DESCRIPTOR = "android.os.INetworkManagementService";
        static final int TRANSACTION_addIdleTimer = 49;
        static final int TRANSACTION_addInterfaceToLocalNetwork = 70;
        static final int TRANSACTION_addInterfaceToNetwork = 62;
        static final int TRANSACTION_addLegacyRouteForNetId = 64;
        static final int TRANSACTION_addRoute = 13;
        static final int TRANSACTION_addVpnUidRanges = 57;
        static final int TRANSACTION_allowProtect = 68;
        static final int TRANSACTION_clearDefaultNetId = 66;
        static final int TRANSACTION_clearInterfaceAddresses = 6;
        static final int TRANSACTION_denyProtect = 69;
        static final int TRANSACTION_disableIpv6 = 10;
        static final int TRANSACTION_disableNat = 30;
        static final int TRANSACTION_enableIpv6 = 11;
        static final int TRANSACTION_enableNat = 29;
        static final int TRANSACTION_getDnsForwarders = 26;
        static final int TRANSACTION_getInterfaceConfig = 4;
        static final int TRANSACTION_getIpForwardingEnabled = 17;
        static final int TRANSACTION_getNetworkStatsDetail = 36;
        static final int TRANSACTION_getNetworkStatsSummaryDev = 34;
        static final int TRANSACTION_getNetworkStatsSummaryXt = 35;
        static final int TRANSACTION_getNetworkStatsTethering = 38;
        static final int TRANSACTION_getNetworkStatsUidDetail = 37;
        static final int TRANSACTION_isBandwidthControlEnabled = 48;
        static final int TRANSACTION_isFirewallEnabled = 52;
        static final int TRANSACTION_isNetworkActive = 61;
        static final int TRANSACTION_isNetworkRestricted = 74;
        static final int TRANSACTION_isTetheringStarted = 21;
        static final int TRANSACTION_listInterfaces = 3;
        static final int TRANSACTION_listTetheredInterfaces = 24;
        static final int TRANSACTION_registerNetworkActivityListener = 59;
        static final int TRANSACTION_registerObserver = 1;
        static final int TRANSACTION_registerTetheringStatsProvider = 31;
        static final int TRANSACTION_removeIdleTimer = 50;
        static final int TRANSACTION_removeInterfaceAlert = 42;
        static final int TRANSACTION_removeInterfaceFromLocalNetwork = 71;
        static final int TRANSACTION_removeInterfaceFromNetwork = 63;
        static final int TRANSACTION_removeInterfaceQuota = 40;
        static final int TRANSACTION_removeRoute = 14;
        static final int TRANSACTION_removeRoutesFromLocalNetwork = 72;
        static final int TRANSACTION_removeVpnUidRanges = 58;
        static final int TRANSACTION_setAllowOnlyVpnForUids = 73;
        static final int TRANSACTION_setDataSaverModeEnabled = 46;
        static final int TRANSACTION_setDefaultNetId = 65;
        static final int TRANSACTION_setDnsForwarders = 25;
        static final int TRANSACTION_setFirewallChainEnabled = 56;
        static final int TRANSACTION_setFirewallEnabled = 51;
        static final int TRANSACTION_setFirewallInterfaceRule = 53;
        static final int TRANSACTION_setFirewallUidRule = 54;
        static final int TRANSACTION_setFirewallUidRules = 55;
        static final int TRANSACTION_setGlobalAlert = 43;
        static final int TRANSACTION_setIPv6AddrGenMode = 12;
        static final int TRANSACTION_setInterfaceAlert = 41;
        static final int TRANSACTION_setInterfaceConfig = 5;
        static final int TRANSACTION_setInterfaceDown = 7;
        static final int TRANSACTION_setInterfaceIpv6PrivacyExtensions = 9;
        static final int TRANSACTION_setInterfaceQuota = 39;
        static final int TRANSACTION_setInterfaceUp = 8;
        static final int TRANSACTION_setIpForwardingEnabled = 18;
        static final int TRANSACTION_setMtu = 15;
        static final int TRANSACTION_setNetworkPermission = 67;
        static final int TRANSACTION_setUidCleartextNetworkPolicy = 47;
        static final int TRANSACTION_setUidMeteredNetworkBlacklist = 44;
        static final int TRANSACTION_setUidMeteredNetworkWhitelist = 45;
        static final int TRANSACTION_shutdown = 16;
        static final int TRANSACTION_startInterfaceForwarding = 27;
        static final int TRANSACTION_startTethering = 19;
        static final int TRANSACTION_stopInterfaceForwarding = 28;
        static final int TRANSACTION_stopTethering = 20;
        static final int TRANSACTION_tetherInterface = 22;
        static final int TRANSACTION_tetherLimitReached = 33;
        static final int TRANSACTION_unregisterNetworkActivityListener = 60;
        static final int TRANSACTION_unregisterObserver = 2;
        static final int TRANSACTION_unregisterTetheringStatsProvider = 32;
        static final int TRANSACTION_untetherInterface = 23;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static INetworkManagementService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof INetworkManagementService) {
                return (INetworkManagementService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static INetworkManagementService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 74: {
                    return "isNetworkRestricted";
                }
                case 73: {
                    return "setAllowOnlyVpnForUids";
                }
                case 72: {
                    return "removeRoutesFromLocalNetwork";
                }
                case 71: {
                    return "removeInterfaceFromLocalNetwork";
                }
                case 70: {
                    return "addInterfaceToLocalNetwork";
                }
                case 69: {
                    return "denyProtect";
                }
                case 68: {
                    return "allowProtect";
                }
                case 67: {
                    return "setNetworkPermission";
                }
                case 66: {
                    return "clearDefaultNetId";
                }
                case 65: {
                    return "setDefaultNetId";
                }
                case 64: {
                    return "addLegacyRouteForNetId";
                }
                case 63: {
                    return "removeInterfaceFromNetwork";
                }
                case 62: {
                    return "addInterfaceToNetwork";
                }
                case 61: {
                    return "isNetworkActive";
                }
                case 60: {
                    return "unregisterNetworkActivityListener";
                }
                case 59: {
                    return "registerNetworkActivityListener";
                }
                case 58: {
                    return "removeVpnUidRanges";
                }
                case 57: {
                    return "addVpnUidRanges";
                }
                case 56: {
                    return "setFirewallChainEnabled";
                }
                case 55: {
                    return "setFirewallUidRules";
                }
                case 54: {
                    return "setFirewallUidRule";
                }
                case 53: {
                    return "setFirewallInterfaceRule";
                }
                case 52: {
                    return "isFirewallEnabled";
                }
                case 51: {
                    return "setFirewallEnabled";
                }
                case 50: {
                    return "removeIdleTimer";
                }
                case 49: {
                    return "addIdleTimer";
                }
                case 48: {
                    return "isBandwidthControlEnabled";
                }
                case 47: {
                    return "setUidCleartextNetworkPolicy";
                }
                case 46: {
                    return "setDataSaverModeEnabled";
                }
                case 45: {
                    return "setUidMeteredNetworkWhitelist";
                }
                case 44: {
                    return "setUidMeteredNetworkBlacklist";
                }
                case 43: {
                    return "setGlobalAlert";
                }
                case 42: {
                    return "removeInterfaceAlert";
                }
                case 41: {
                    return "setInterfaceAlert";
                }
                case 40: {
                    return "removeInterfaceQuota";
                }
                case 39: {
                    return "setInterfaceQuota";
                }
                case 38: {
                    return "getNetworkStatsTethering";
                }
                case 37: {
                    return "getNetworkStatsUidDetail";
                }
                case 36: {
                    return "getNetworkStatsDetail";
                }
                case 35: {
                    return "getNetworkStatsSummaryXt";
                }
                case 34: {
                    return "getNetworkStatsSummaryDev";
                }
                case 33: {
                    return "tetherLimitReached";
                }
                case 32: {
                    return "unregisterTetheringStatsProvider";
                }
                case 31: {
                    return "registerTetheringStatsProvider";
                }
                case 30: {
                    return "disableNat";
                }
                case 29: {
                    return "enableNat";
                }
                case 28: {
                    return "stopInterfaceForwarding";
                }
                case 27: {
                    return "startInterfaceForwarding";
                }
                case 26: {
                    return "getDnsForwarders";
                }
                case 25: {
                    return "setDnsForwarders";
                }
                case 24: {
                    return "listTetheredInterfaces";
                }
                case 23: {
                    return "untetherInterface";
                }
                case 22: {
                    return "tetherInterface";
                }
                case 21: {
                    return "isTetheringStarted";
                }
                case 20: {
                    return "stopTethering";
                }
                case 19: {
                    return "startTethering";
                }
                case 18: {
                    return "setIpForwardingEnabled";
                }
                case 17: {
                    return "getIpForwardingEnabled";
                }
                case 16: {
                    return "shutdown";
                }
                case 15: {
                    return "setMtu";
                }
                case 14: {
                    return "removeRoute";
                }
                case 13: {
                    return "addRoute";
                }
                case 12: {
                    return "setIPv6AddrGenMode";
                }
                case 11: {
                    return "enableIpv6";
                }
                case 10: {
                    return "disableIpv6";
                }
                case 9: {
                    return "setInterfaceIpv6PrivacyExtensions";
                }
                case 8: {
                    return "setInterfaceUp";
                }
                case 7: {
                    return "setInterfaceDown";
                }
                case 6: {
                    return "clearInterfaceAddresses";
                }
                case 5: {
                    return "setInterfaceConfig";
                }
                case 4: {
                    return "getInterfaceConfig";
                }
                case 3: {
                    return "listInterfaces";
                }
                case 2: {
                    return "unregisterObserver";
                }
                case 1: 
            }
            return "registerObserver";
        }

        public static boolean setDefaultImpl(INetworkManagementService iNetworkManagementService) {
            if (Proxy.sDefaultImpl == null && iNetworkManagementService != null) {
                Proxy.sDefaultImpl = iNetworkManagementService;
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
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 74: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isNetworkRestricted(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 73: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        if (((Parcel)object).readInt() != 0) {
                            bl9 = true;
                        }
                        this.setAllowOnlyVpnForUids(bl9, ((Parcel)object).createTypedArray(UidRange.CREATOR));
                        parcel.writeNoException();
                        return true;
                    }
                    case 72: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.removeRoutesFromLocalNetwork(((Parcel)object).createTypedArrayList(RouteInfo.CREATOR));
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 71: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeInterfaceFromLocalNetwork(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 70: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.addInterfaceToLocalNetwork(((Parcel)object).readString(), ((Parcel)object).createTypedArrayList(RouteInfo.CREATOR));
                        parcel.writeNoException();
                        return true;
                    }
                    case 69: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.denyProtect(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 68: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.allowProtect(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 67: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setNetworkPermission(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 66: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.clearDefaultNetId();
                        parcel.writeNoException();
                        return true;
                    }
                    case 65: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setDefaultNetId(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 64: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        RouteInfo routeInfo = ((Parcel)object).readInt() != 0 ? RouteInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.addLegacyRouteForNetId(n, routeInfo, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 63: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeInterfaceFromNetwork(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 62: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.addInterfaceToNetwork(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 61: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isNetworkActive() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 60: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterNetworkActivityListener(INetworkActivityListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 59: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerNetworkActivityListener(INetworkActivityListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 58: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeVpnUidRanges(((Parcel)object).readInt(), ((Parcel)object).createTypedArray(UidRange.CREATOR));
                        parcel.writeNoException();
                        return true;
                    }
                    case 57: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.addVpnUidRanges(((Parcel)object).readInt(), ((Parcel)object).createTypedArray(UidRange.CREATOR));
                        parcel.writeNoException();
                        return true;
                    }
                    case 56: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl9 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl9 = true;
                        }
                        this.setFirewallChainEnabled(n, bl9);
                        parcel.writeNoException();
                        return true;
                    }
                    case 55: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setFirewallUidRules(((Parcel)object).readInt(), ((Parcel)object).createIntArray(), ((Parcel)object).createIntArray());
                        parcel.writeNoException();
                        return true;
                    }
                    case 54: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setFirewallUidRule(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 53: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        bl9 = bl2;
                        if (((Parcel)object).readInt() != 0) {
                            bl9 = true;
                        }
                        this.setFirewallInterfaceRule(string2, bl9);
                        parcel.writeNoException();
                        return true;
                    }
                    case 52: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isFirewallEnabled() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 51: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl9 = bl3;
                        if (((Parcel)object).readInt() != 0) {
                            bl9 = true;
                        }
                        this.setFirewallEnabled(bl9);
                        parcel.writeNoException();
                        return true;
                    }
                    case 50: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeIdleTimer(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 49: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.addIdleTimer(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 48: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isBandwidthControlEnabled() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 47: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setUidCleartextNetworkPolicy(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 46: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl9 = bl4;
                        if (((Parcel)object).readInt() != 0) {
                            bl9 = true;
                        }
                        n = this.setDataSaverModeEnabled(bl9) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 45: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl9 = bl5;
                        if (((Parcel)object).readInt() != 0) {
                            bl9 = true;
                        }
                        this.setUidMeteredNetworkWhitelist(n, bl9);
                        parcel.writeNoException();
                        return true;
                    }
                    case 44: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl9 = bl6;
                        if (((Parcel)object).readInt() != 0) {
                            bl9 = true;
                        }
                        this.setUidMeteredNetworkBlacklist(n, bl9);
                        parcel.writeNoException();
                        return true;
                    }
                    case 43: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setGlobalAlert(((Parcel)object).readLong());
                        parcel.writeNoException();
                        return true;
                    }
                    case 42: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeInterfaceAlert(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 41: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setInterfaceAlert(((Parcel)object).readString(), ((Parcel)object).readLong());
                        parcel.writeNoException();
                        return true;
                    }
                    case 40: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeInterfaceQuota(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 39: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setInterfaceQuota(((Parcel)object).readString(), ((Parcel)object).readLong());
                        parcel.writeNoException();
                        return true;
                    }
                    case 38: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getNetworkStatsTethering(((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((NetworkStats)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 37: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getNetworkStatsUidDetail(((Parcel)object).readInt(), ((Parcel)object).createStringArray());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((NetworkStats)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 36: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getNetworkStatsDetail();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((NetworkStats)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 35: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getNetworkStatsSummaryXt();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((NetworkStats)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 34: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getNetworkStatsSummaryDev();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((NetworkStats)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 33: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.tetherLimitReached(ITetheringStatsProvider.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 32: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterTetheringStatsProvider(ITetheringStatsProvider.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 31: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerTetheringStatsProvider(ITetheringStatsProvider.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 30: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.disableNat(((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.enableNat(((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.stopInterfaceForwarding(((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.startInterfaceForwarding(((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getDnsForwarders();
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Network network = ((Parcel)object).readInt() != 0 ? Network.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setDnsForwarders(network, ((Parcel)object).createStringArray());
                        parcel.writeNoException();
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.listTetheredInterfaces();
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.untetherInterface(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.tetherInterface(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isTetheringStarted() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.stopTethering();
                        parcel.writeNoException();
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.startTethering(((Parcel)object).createStringArray());
                        parcel.writeNoException();
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl9 = bl7;
                        if (((Parcel)object).readInt() != 0) {
                            bl9 = true;
                        }
                        this.setIpForwardingEnabled(bl9);
                        parcel.writeNoException();
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getIpForwardingEnabled() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.shutdown();
                        parcel.writeNoException();
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setMtu(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? RouteInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.removeRoute(n, (RouteInfo)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? RouteInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.addRoute(n, (RouteInfo)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setIPv6AddrGenMode(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.enableIpv6(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.disableIpv6(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string3 = ((Parcel)object).readString();
                        bl9 = bl8;
                        if (((Parcel)object).readInt() != 0) {
                            bl9 = true;
                        }
                        this.setInterfaceIpv6PrivacyExtensions(string3, bl9);
                        parcel.writeNoException();
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setInterfaceUp(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setInterfaceDown(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.clearInterfaceAddresses(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string4 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? InterfaceConfiguration.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setInterfaceConfig(string4, (InterfaceConfiguration)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getInterfaceConfig(((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((InterfaceConfiguration)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.listInterfaces();
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterObserver(INetworkManagementEventObserver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.registerObserver(INetworkManagementEventObserver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                parcel.writeNoException();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements INetworkManagementService {
            public static INetworkManagementService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void addIdleTimer(String string2, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(49, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addIdleTimer(string2, n, n2);
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
            public void addInterfaceToLocalNetwork(String string2, List<RouteInfo> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(70, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addInterfaceToLocalNetwork(string2, list);
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
            public void addInterfaceToNetwork(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(62, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addInterfaceToNetwork(string2, n);
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
            public void addLegacyRouteForNetId(int n, RouteInfo routeInfo, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (routeInfo != null) {
                        parcel.writeInt(1);
                        routeInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(64, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addLegacyRouteForNetId(n, routeInfo, n2);
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
            public void addRoute(int n, RouteInfo routeInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (routeInfo != null) {
                        parcel.writeInt(1);
                        routeInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addRoute(n, routeInfo);
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
            public void addVpnUidRanges(int n, UidRange[] arruidRange) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeTypedArray((Parcelable[])arruidRange, 0);
                    if (!this.mRemote.transact(57, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addVpnUidRanges(n, arruidRange);
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
            public void allowProtect(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(68, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().allowProtect(n);
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
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void clearDefaultNetId() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(66, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearDefaultNetId();
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
            public void clearInterfaceAddresses(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearInterfaceAddresses(string2);
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
            public void denyProtect(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(69, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().denyProtect(n);
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
            public void disableIpv6(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disableIpv6(string2);
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
            public void disableNat(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(30, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disableNat(string2, string3);
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
            public void enableIpv6(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enableIpv6(string2);
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
            public void enableNat(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(29, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enableNat(string2, string3);
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
            public String[] getDnsForwarders() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(26, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String[] arrstring = Stub.getDefaultImpl().getDnsForwarders();
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
            public InterfaceConfiguration getInterfaceConfig(String object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)object);
                        if (this.mRemote.transact(4, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getInterfaceConfig((String)object);
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
                object = parcel2.readInt() != 0 ? InterfaceConfiguration.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public boolean getIpForwardingEnabled() throws RemoteException {
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
                    bl = Stub.getDefaultImpl().getIpForwardingEnabled();
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
            public NetworkStats getNetworkStatsDetail() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(36, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        NetworkStats networkStats = Stub.getDefaultImpl().getNetworkStatsDetail();
                        parcel.recycle();
                        parcel2.recycle();
                        return networkStats;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                NetworkStats networkStats = parcel.readInt() != 0 ? NetworkStats.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return networkStats;
            }

            @Override
            public NetworkStats getNetworkStatsSummaryDev() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(34, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        NetworkStats networkStats = Stub.getDefaultImpl().getNetworkStatsSummaryDev();
                        parcel.recycle();
                        parcel2.recycle();
                        return networkStats;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                NetworkStats networkStats = parcel.readInt() != 0 ? NetworkStats.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return networkStats;
            }

            @Override
            public NetworkStats getNetworkStatsSummaryXt() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(35, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        NetworkStats networkStats = Stub.getDefaultImpl().getNetworkStatsSummaryXt();
                        parcel.recycle();
                        parcel2.recycle();
                        return networkStats;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                NetworkStats networkStats = parcel.readInt() != 0 ? NetworkStats.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return networkStats;
            }

            @Override
            public NetworkStats getNetworkStatsTethering(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(38, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        NetworkStats networkStats = Stub.getDefaultImpl().getNetworkStatsTethering(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return networkStats;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                NetworkStats networkStats = parcel2.readInt() != 0 ? NetworkStats.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return networkStats;
            }

            @Override
            public NetworkStats getNetworkStatsUidDetail(int n, String[] object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeStringArray((String[])object);
                        if (this.mRemote.transact(37, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getNetworkStatsUidDetail(n, (String[])object);
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? NetworkStats.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public boolean isBandwidthControlEnabled() throws RemoteException {
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
                    if (iBinder.transact(48, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isBandwidthControlEnabled();
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
            public boolean isFirewallEnabled() throws RemoteException {
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
                    if (iBinder.transact(52, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isFirewallEnabled();
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
            public boolean isNetworkActive() throws RemoteException {
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
                    if (iBinder.transact(61, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isNetworkActive();
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
            public boolean isNetworkRestricted(int n) throws RemoteException {
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
                    if (iBinder.transact(74, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isNetworkRestricted(n);
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
            public boolean isTetheringStarted() throws RemoteException {
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
                    if (iBinder.transact(21, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isTetheringStarted();
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
            public String[] listInterfaces() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String[] arrstring = Stub.getDefaultImpl().listInterfaces();
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
            public String[] listTetheredInterfaces() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(24, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String[] arrstring = Stub.getDefaultImpl().listTetheredInterfaces();
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void registerNetworkActivityListener(INetworkActivityListener iNetworkActivityListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iNetworkActivityListener != null ? iNetworkActivityListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(59, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerNetworkActivityListener(iNetworkActivityListener);
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
            public void registerObserver(INetworkManagementEventObserver iNetworkManagementEventObserver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iNetworkManagementEventObserver != null ? iNetworkManagementEventObserver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerObserver(iNetworkManagementEventObserver);
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
            public void registerTetheringStatsProvider(ITetheringStatsProvider iTetheringStatsProvider, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iTetheringStatsProvider != null ? iTetheringStatsProvider.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(31, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerTetheringStatsProvider(iTetheringStatsProvider, string2);
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
            public void removeIdleTimer(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(50, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeIdleTimer(string2);
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
            public void removeInterfaceAlert(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(42, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeInterfaceAlert(string2);
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
            public void removeInterfaceFromLocalNetwork(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(71, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeInterfaceFromLocalNetwork(string2);
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
            public void removeInterfaceFromNetwork(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(63, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeInterfaceFromNetwork(string2, n);
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
            public void removeInterfaceQuota(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(40, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeInterfaceQuota(string2);
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
            public void removeRoute(int n, RouteInfo routeInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (routeInfo != null) {
                        parcel.writeInt(1);
                        routeInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeRoute(n, routeInfo);
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
            public int removeRoutesFromLocalNetwork(List<RouteInfo> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(72, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().removeRoutesFromLocalNetwork(list);
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
            public void removeVpnUidRanges(int n, UidRange[] arruidRange) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeTypedArray((Parcelable[])arruidRange, 0);
                    if (!this.mRemote.transact(58, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeVpnUidRanges(n, arruidRange);
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
            public void setAllowOnlyVpnForUids(boolean bl, UidRange[] arruidRange) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    parcel.writeTypedArray((Parcelable[])arruidRange, 0);
                    if (!this.mRemote.transact(73, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAllowOnlyVpnForUids(bl, arruidRange);
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
            public boolean setDataSaverModeEnabled(boolean bl) throws RemoteException {
                boolean bl2;
                int n;
                Parcel parcel;
                Parcel parcel2;
                block4 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        bl2 = true;
                        n = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    parcel.writeInt(n);
                    if (this.mRemote.transact(46, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().setDataSaverModeEnabled(bl);
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
            public void setDefaultNetId(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(65, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDefaultNetId(n);
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
            public void setDnsForwarders(Network network, String[] arrstring) throws RemoteException {
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
                    parcel.writeStringArray(arrstring);
                    if (!this.mRemote.transact(25, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDnsForwarders(network, arrstring);
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
            public void setFirewallChainEnabled(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(56, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setFirewallChainEnabled(n, bl);
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
            public void setFirewallEnabled(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(51, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setFirewallEnabled(bl);
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
            public void setFirewallInterfaceRule(String string2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(53, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setFirewallInterfaceRule(string2, bl);
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
            public void setFirewallUidRule(int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(54, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setFirewallUidRule(n, n2, n3);
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
            public void setFirewallUidRules(int n, int[] arrn, int[] arrn2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeIntArray(arrn);
                    parcel.writeIntArray(arrn2);
                    if (!this.mRemote.transact(55, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setFirewallUidRules(n, arrn, arrn2);
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
            public void setGlobalAlert(long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(43, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setGlobalAlert(l);
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
            public void setIPv6AddrGenMode(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setIPv6AddrGenMode(string2, n);
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
            public void setInterfaceAlert(String string2, long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(41, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setInterfaceAlert(string2, l);
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
            public void setInterfaceConfig(String string2, InterfaceConfiguration interfaceConfiguration) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (interfaceConfiguration != null) {
                        parcel.writeInt(1);
                        interfaceConfiguration.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setInterfaceConfig(string2, interfaceConfiguration);
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
            public void setInterfaceDown(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setInterfaceDown(string2);
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
            public void setInterfaceIpv6PrivacyExtensions(String string2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setInterfaceIpv6PrivacyExtensions(string2, bl);
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
            public void setInterfaceQuota(String string2, long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(39, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setInterfaceQuota(string2, l);
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
            public void setInterfaceUp(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setInterfaceUp(string2);
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
            public void setIpForwardingEnabled(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setIpForwardingEnabled(bl);
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
            public void setMtu(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setMtu(string2, n);
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
            public void setNetworkPermission(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(67, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setNetworkPermission(n, n2);
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
            public void setUidCleartextNetworkPolicy(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(47, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setUidCleartextNetworkPolicy(n, n2);
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
            public void setUidMeteredNetworkBlacklist(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(44, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setUidMeteredNetworkBlacklist(n, bl);
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
            public void setUidMeteredNetworkWhitelist(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(45, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setUidMeteredNetworkWhitelist(n, bl);
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
            public void shutdown() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().shutdown();
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
            public void startInterfaceForwarding(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(27, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startInterfaceForwarding(string2, string3);
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
            public void startTethering(String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStringArray(arrstring);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startTethering(arrstring);
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
            public void stopInterfaceForwarding(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(28, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopInterfaceForwarding(string2, string3);
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
            public void stopTethering() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopTethering();
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
            public void tetherInterface(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(22, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().tetherInterface(string2);
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
            public void tetherLimitReached(ITetheringStatsProvider iTetheringStatsProvider) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iTetheringStatsProvider != null ? iTetheringStatsProvider.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(33, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().tetherLimitReached(iTetheringStatsProvider);
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
            public void unregisterNetworkActivityListener(INetworkActivityListener iNetworkActivityListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iNetworkActivityListener != null ? iNetworkActivityListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(60, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterNetworkActivityListener(iNetworkActivityListener);
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
            public void unregisterObserver(INetworkManagementEventObserver iNetworkManagementEventObserver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iNetworkManagementEventObserver != null ? iNetworkManagementEventObserver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterObserver(iNetworkManagementEventObserver);
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
            public void unregisterTetheringStatsProvider(ITetheringStatsProvider iTetheringStatsProvider) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iTetheringStatsProvider != null ? iTetheringStatsProvider.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(32, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterTetheringStatsProvider(iTetheringStatsProvider);
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
            public void untetherInterface(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(23, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().untetherInterface(string2);
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

