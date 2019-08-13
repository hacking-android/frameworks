/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.net.INetworkPolicyListener;
import android.net.INetworkPolicyManager;
import android.net.NetworkPolicy;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.DebugUtils;
import android.util.Pair;
import android.util.Range;
import java.time.ZonedDateTime;
import java.util.Iterator;

public class NetworkPolicyManager {
    private static final boolean ALLOW_PLATFORM_APP_POLICY = true;
    public static final String EXTRA_NETWORK_TEMPLATE = "android.net.NETWORK_TEMPLATE";
    public static final String FIREWALL_CHAIN_NAME_DOZABLE = "dozable";
    public static final String FIREWALL_CHAIN_NAME_NONE = "none";
    public static final String FIREWALL_CHAIN_NAME_POWERSAVE = "powersave";
    public static final String FIREWALL_CHAIN_NAME_STANDBY = "standby";
    public static final int FIREWALL_RULE_DEFAULT = 0;
    public static final int FOREGROUND_THRESHOLD_STATE = 6;
    public static final int MASK_ALL_NETWORKS = 240;
    public static final int MASK_METERED_NETWORKS = 15;
    public static final int OVERRIDE_CONGESTED = 2;
    public static final int OVERRIDE_UNMETERED = 1;
    public static final int POLICY_ALLOW_METERED_BACKGROUND = 4;
    public static final int POLICY_NONE = 0;
    public static final int POLICY_REJECT_METERED_BACKGROUND = 1;
    public static final int RULE_ALLOW_ALL = 32;
    public static final int RULE_ALLOW_METERED = 1;
    public static final int RULE_NONE = 0;
    public static final int RULE_REJECT_ALL = 64;
    public static final int RULE_REJECT_METERED = 4;
    public static final int RULE_TEMPORARY_ALLOW_METERED = 2;
    private final Context mContext;
    @UnsupportedAppUsage
    private INetworkPolicyManager mService;

    public NetworkPolicyManager(Context context, INetworkPolicyManager iNetworkPolicyManager) {
        if (iNetworkPolicyManager != null) {
            this.mContext = context;
            this.mService = iNetworkPolicyManager;
            return;
        }
        throw new IllegalArgumentException("missing INetworkPolicyManager");
    }

    @Deprecated
    public static Iterator<Pair<ZonedDateTime, ZonedDateTime>> cycleIterator(NetworkPolicy networkPolicy) {
        return new Iterator<Pair<ZonedDateTime, ZonedDateTime>>(){

            @Override
            public boolean hasNext() {
                return Iterator.this.hasNext();
            }

            @Override
            public Pair<ZonedDateTime, ZonedDateTime> next() {
                if (this.hasNext()) {
                    Range range = (Range)Iterator.this.next();
                    return Pair.create((ZonedDateTime)range.getLower(), (ZonedDateTime)range.getUpper());
                }
                return Pair.create(null, null);
            }
        };
    }

    @UnsupportedAppUsage
    public static NetworkPolicyManager from(Context context) {
        return (NetworkPolicyManager)context.getSystemService("netpolicy");
    }

    public static boolean isProcStateAllowedWhileIdleOrPowerSaveMode(int n) {
        boolean bl = n <= 6;
        return bl;
    }

    public static boolean isProcStateAllowedWhileOnRestrictBackground(int n) {
        boolean bl = n <= 6;
        return bl;
    }

    @Deprecated
    public static boolean isUidValidForPolicy(Context context, int n) {
        return UserHandle.isApp(n);
    }

    public static String resolveNetworkId(WifiConfiguration object) {
        object = ((WifiConfiguration)object).isPasspoint() ? ((WifiConfiguration)object).providerFriendlyName : ((WifiConfiguration)object).SSID;
        return WifiInfo.removeDoubleQuotes((String)object);
    }

    public static String resolveNetworkId(String string2) {
        return WifiInfo.removeDoubleQuotes(string2);
    }

    public static String uidPoliciesToString(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n);
        stringBuilder = stringBuilder.append(" (");
        if (n == 0) {
            stringBuilder.append("NONE");
        } else {
            stringBuilder.append(DebugUtils.flagsToString(NetworkPolicyManager.class, "POLICY_", n));
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public static String uidRulesToString(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n);
        stringBuilder = stringBuilder.append(" (");
        if (n == 0) {
            stringBuilder.append("NONE");
        } else {
            stringBuilder.append(DebugUtils.flagsToString(NetworkPolicyManager.class, "RULE_", n));
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public void addUidPolicy(int n, int n2) {
        try {
            this.mService.addUidPolicy(n, n2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void factoryReset(String string2) {
        try {
            this.mService.factoryReset(string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public NetworkPolicy[] getNetworkPolicies() {
        try {
            NetworkPolicy[] arrnetworkPolicy = this.mService.getNetworkPolicies(this.mContext.getOpPackageName());
            return arrnetworkPolicy;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public boolean getRestrictBackground() {
        try {
            boolean bl = this.mService.getRestrictBackground();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public int getUidPolicy(int n) {
        try {
            n = this.mService.getUidPolicy(n);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public int[] getUidsWithPolicy(int n) {
        try {
            int[] arrn = this.mService.getUidsWithPolicy(n);
            return arrn;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public void registerListener(INetworkPolicyListener iNetworkPolicyListener) {
        try {
            this.mService.registerListener(iNetworkPolicyListener);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void removeUidPolicy(int n, int n2) {
        try {
            this.mService.removeUidPolicy(n, n2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setNetworkPolicies(NetworkPolicy[] arrnetworkPolicy) {
        try {
            this.mService.setNetworkPolicies(arrnetworkPolicy);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public void setRestrictBackground(boolean bl) {
        try {
            this.mService.setRestrictBackground(bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public void setUidPolicy(int n, int n2) {
        try {
            this.mService.setUidPolicy(n, n2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public void unregisterListener(INetworkPolicyListener iNetworkPolicyListener) {
        try {
            this.mService.unregisterListener(iNetworkPolicyListener);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static class Listener
    extends INetworkPolicyListener.Stub {
        @Override
        public void onMeteredIfacesChanged(String[] arrstring) {
        }

        @Override
        public void onRestrictBackgroundChanged(boolean bl) {
        }

        @Override
        public void onSubscriptionOverride(int n, int n2, int n3) {
        }

        @Override
        public void onUidPoliciesChanged(int n, int n2) {
        }

        @Override
        public void onUidRulesChanged(int n, int n2) {
        }
    }

}

