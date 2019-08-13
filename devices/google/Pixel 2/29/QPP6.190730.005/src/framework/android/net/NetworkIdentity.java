/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import java.util.Objects;

public class NetworkIdentity
implements Comparable<NetworkIdentity> {
    @Deprecated
    public static final boolean COMBINE_SUBTYPE_ENABLED = true;
    public static final int SUBTYPE_COMBINED = -1;
    private static final String TAG = "NetworkIdentity";
    final boolean mDefaultNetwork;
    final boolean mMetered;
    final String mNetworkId;
    final boolean mRoaming;
    final int mSubType;
    final String mSubscriberId;
    final int mType;

    public NetworkIdentity(int n, int n2, String string2, String string3, boolean bl, boolean bl2, boolean bl3) {
        this.mType = n;
        this.mSubType = -1;
        this.mSubscriberId = string2;
        this.mNetworkId = string3;
        this.mRoaming = bl;
        this.mMetered = bl2;
        this.mDefaultNetwork = bl3;
    }

    public static NetworkIdentity buildNetworkIdentity(Context object, NetworkState object2, boolean bl) {
        int n = ((NetworkState)object2).networkInfo.getType();
        int n2 = ((NetworkState)object2).networkInfo.getSubtype();
        boolean bl2 = ((NetworkState)object2).networkCapabilities.hasCapability(18);
        boolean bl3 = ((NetworkState)object2).networkCapabilities.hasCapability(11);
        if (ConnectivityManager.isNetworkTypeMobile(n)) {
            if (((NetworkState)object2).subscriberId == null && ((NetworkState)object2).networkInfo.getState() != NetworkInfo.State.DISCONNECTED && ((NetworkState)object2).networkInfo.getState() != NetworkInfo.State.UNKNOWN) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Active mobile network without subscriber! ni = ");
                ((StringBuilder)object).append(((NetworkState)object2).networkInfo);
                Slog.w(TAG, ((StringBuilder)object).toString());
            }
            object = ((NetworkState)object2).subscriberId;
            object2 = null;
        } else if (n == 1) {
            if (((NetworkState)object2).networkId != null) {
                object2 = ((NetworkState)object2).networkId;
                object = null;
            } else {
                object = (object = ((WifiManager)((Context)object).getSystemService("wifi")).getConnectionInfo()) != null ? ((WifiInfo)object).getSSID() : null;
                Object var7_7 = null;
                object2 = object;
                object = var7_7;
            }
        } else {
            object = null;
            object2 = null;
        }
        return new NetworkIdentity(n, n2, (String)object, (String)object2, bl2 ^ true, bl3 ^ true, bl);
    }

    public static String scrubSubscriberId(String string2) {
        if (Build.IS_ENG) {
            return string2;
        }
        if (string2 != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2.substring(0, Math.min(6, string2.length())));
            stringBuilder.append("...");
            return stringBuilder.toString();
        }
        return "null";
    }

    public static String[] scrubSubscriberId(String[] arrstring) {
        if (arrstring == null) {
            return null;
        }
        String[] arrstring2 = new String[arrstring.length];
        for (int i = 0; i < arrstring2.length; ++i) {
            arrstring2[i] = NetworkIdentity.scrubSubscriberId(arrstring[i]);
        }
        return arrstring2;
    }

    @Override
    public int compareTo(NetworkIdentity networkIdentity) {
        String string2;
        int n;
        String string3;
        int n2 = n = Integer.compare(this.mType, networkIdentity.mType);
        if (n == 0) {
            n2 = Integer.compare(this.mSubType, networkIdentity.mSubType);
        }
        int n3 = n2;
        if (n2 == 0) {
            string3 = this.mSubscriberId;
            n3 = n2;
            if (string3 != null) {
                string2 = networkIdentity.mSubscriberId;
                n3 = n2;
                if (string2 != null) {
                    n3 = string3.compareTo(string2);
                }
            }
        }
        n = n3;
        if (n3 == 0) {
            string3 = this.mNetworkId;
            n = n3;
            if (string3 != null) {
                string2 = networkIdentity.mNetworkId;
                n = n3;
                if (string2 != null) {
                    n = string3.compareTo(string2);
                }
            }
        }
        n2 = n;
        if (n == 0) {
            n2 = Boolean.compare(this.mRoaming, networkIdentity.mRoaming);
        }
        n = n2;
        if (n2 == 0) {
            n = Boolean.compare(this.mMetered, networkIdentity.mMetered);
        }
        n2 = n;
        if (n == 0) {
            n2 = Boolean.compare(this.mDefaultNetwork, networkIdentity.mDefaultNetwork);
        }
        return n2;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof NetworkIdentity;
        boolean bl2 = false;
        if (bl) {
            object = (NetworkIdentity)object;
            if (this.mType == ((NetworkIdentity)object).mType && this.mSubType == ((NetworkIdentity)object).mSubType && this.mRoaming == ((NetworkIdentity)object).mRoaming && Objects.equals(this.mSubscriberId, ((NetworkIdentity)object).mSubscriberId) && Objects.equals(this.mNetworkId, ((NetworkIdentity)object).mNetworkId) && this.mMetered == ((NetworkIdentity)object).mMetered && this.mDefaultNetwork == ((NetworkIdentity)object).mDefaultNetwork) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    public boolean getDefaultNetwork() {
        return this.mDefaultNetwork;
    }

    public boolean getMetered() {
        return this.mMetered;
    }

    public String getNetworkId() {
        return this.mNetworkId;
    }

    public boolean getRoaming() {
        return this.mRoaming;
    }

    public int getSubType() {
        return this.mSubType;
    }

    public String getSubscriberId() {
        return this.mSubscriberId;
    }

    public int getType() {
        return this.mType;
    }

    public int hashCode() {
        return Objects.hash(this.mType, this.mSubType, this.mSubscriberId, this.mNetworkId, this.mRoaming, this.mMetered, this.mDefaultNetwork);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");
        stringBuilder.append("type=");
        stringBuilder.append(ConnectivityManager.getNetworkTypeName(this.mType));
        stringBuilder.append(", subType=");
        stringBuilder.append("COMBINED");
        if (this.mSubscriberId != null) {
            stringBuilder.append(", subscriberId=");
            stringBuilder.append(NetworkIdentity.scrubSubscriberId(this.mSubscriberId));
        }
        if (this.mNetworkId != null) {
            stringBuilder.append(", networkId=");
            stringBuilder.append(this.mNetworkId);
        }
        if (this.mRoaming) {
            stringBuilder.append(", ROAMING");
        }
        stringBuilder.append(", metered=");
        stringBuilder.append(this.mMetered);
        stringBuilder.append(", defaultNetwork=");
        stringBuilder.append(this.mDefaultNetwork);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
        l = protoOutputStream.start(l);
        protoOutputStream.write(1120986464257L, this.mType);
        String string2 = this.mSubscriberId;
        if (string2 != null) {
            protoOutputStream.write(1138166333442L, NetworkIdentity.scrubSubscriberId(string2));
        }
        protoOutputStream.write(1138166333443L, this.mNetworkId);
        protoOutputStream.write(1133871366148L, this.mRoaming);
        protoOutputStream.write(1133871366149L, this.mMetered);
        protoOutputStream.write(1133871366150L, this.mDefaultNetwork);
        protoOutputStream.end(l);
    }
}

