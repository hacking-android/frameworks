/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.UnsupportedAppUsage;
import android.net.NetworkIdentity;
import android.net.NetworkStats;
import android.net.wifi.WifiInfo;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.BackupUtils;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.ArrayUtils;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Objects;

public class NetworkTemplate
implements Parcelable {
    private static final int BACKUP_VERSION = 1;
    @UnsupportedAppUsage
    public static final Parcelable.Creator<NetworkTemplate> CREATOR;
    public static final int MATCH_BLUETOOTH = 8;
    public static final int MATCH_ETHERNET = 5;
    public static final int MATCH_MOBILE = 1;
    public static final int MATCH_MOBILE_WILDCARD = 6;
    public static final int MATCH_PROXY = 9;
    public static final int MATCH_WIFI = 4;
    public static final int MATCH_WIFI_WILDCARD = 7;
    private static final String TAG = "NetworkTemplate";
    private static boolean sForceAllNetworkTypes;
    private final int mDefaultNetwork;
    private final int mMatchRule;
    private final String[] mMatchSubscriberIds;
    private final int mMetered;
    private final String mNetworkId;
    private final int mRoaming;
    private final String mSubscriberId;

    static {
        sForceAllNetworkTypes = false;
        CREATOR = new Parcelable.Creator<NetworkTemplate>(){

            @Override
            public NetworkTemplate createFromParcel(Parcel parcel) {
                return new NetworkTemplate(parcel);
            }

            public NetworkTemplate[] newArray(int n) {
                return new NetworkTemplate[n];
            }
        };
    }

    @UnsupportedAppUsage
    public NetworkTemplate(int n, String string2, String string3) {
        this(n, string2, new String[]{string2}, string3);
    }

    public NetworkTemplate(int n, String string2, String[] arrstring, String string3) {
        this(n, string2, arrstring, string3, -1, -1, -1);
    }

    public NetworkTemplate(int n, String charSequence, String[] arrstring, String string2, int n2, int n3, int n4) {
        this.mMatchRule = n;
        this.mSubscriberId = charSequence;
        this.mMatchSubscriberIds = arrstring;
        this.mNetworkId = string2;
        this.mMetered = n2;
        this.mRoaming = n3;
        this.mDefaultNetwork = n4;
        if (!NetworkTemplate.isKnownMatchRule(n)) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Unknown network template rule ");
            ((StringBuilder)charSequence).append(n);
            ((StringBuilder)charSequence).append(" will not match any identity.");
            Log.e(TAG, ((StringBuilder)charSequence).toString());
        }
    }

    private NetworkTemplate(Parcel parcel) {
        this.mMatchRule = parcel.readInt();
        this.mSubscriberId = parcel.readString();
        this.mMatchSubscriberIds = parcel.createStringArray();
        this.mNetworkId = parcel.readString();
        this.mMetered = parcel.readInt();
        this.mRoaming = parcel.readInt();
        this.mDefaultNetwork = parcel.readInt();
    }

    public static NetworkTemplate buildTemplateBluetooth() {
        return new NetworkTemplate(8, null, null);
    }

    @UnsupportedAppUsage
    public static NetworkTemplate buildTemplateEthernet() {
        return new NetworkTemplate(5, null, null);
    }

    @UnsupportedAppUsage
    public static NetworkTemplate buildTemplateMobileAll(String string2) {
        return new NetworkTemplate(1, string2, null);
    }

    @UnsupportedAppUsage
    public static NetworkTemplate buildTemplateMobileWildcard() {
        return new NetworkTemplate(6, null, null);
    }

    public static NetworkTemplate buildTemplateProxy() {
        return new NetworkTemplate(9, null, null);
    }

    @Deprecated
    @UnsupportedAppUsage
    public static NetworkTemplate buildTemplateWifi() {
        return NetworkTemplate.buildTemplateWifiWildcard();
    }

    public static NetworkTemplate buildTemplateWifi(String string2) {
        return new NetworkTemplate(4, null, string2);
    }

    @UnsupportedAppUsage
    public static NetworkTemplate buildTemplateWifiWildcard() {
        return new NetworkTemplate(7, null, null);
    }

    @VisibleForTesting
    public static void forceAllNetworkTypes() {
        sForceAllNetworkTypes = true;
    }

    private static String getMatchRuleName(int n) {
        if (n != 1) {
            switch (n) {
                default: {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("UNKNOWN(");
                    stringBuilder.append(n);
                    stringBuilder.append(")");
                    return stringBuilder.toString();
                }
                case 9: {
                    return "PROXY";
                }
                case 8: {
                    return "BLUETOOTH";
                }
                case 7: {
                    return "WIFI_WILDCARD";
                }
                case 6: {
                    return "MOBILE_WILDCARD";
                }
                case 5: {
                    return "ETHERNET";
                }
                case 4: 
            }
            return "WIFI";
        }
        return "MOBILE";
    }

    public static NetworkTemplate getNetworkTemplateFromBackup(DataInputStream object) throws IOException, BackupUtils.BadVersionException {
        int n = ((DataInputStream)object).readInt();
        if (n >= 1 && n <= 1) {
            n = ((DataInputStream)object).readInt();
            String string2 = BackupUtils.readString((DataInputStream)object);
            object = BackupUtils.readString((DataInputStream)object);
            if (NetworkTemplate.isKnownMatchRule(n)) {
                return new NetworkTemplate(n, string2, (String)object);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Restored network template contains unknown match rule ");
            ((StringBuilder)object).append(n);
            throw new BackupUtils.BadVersionException(((StringBuilder)object).toString());
        }
        throw new BackupUtils.BadVersionException("Unknown Backup Serialization Version");
    }

    private static boolean isKnownMatchRule(int n) {
        if (n != 1) {
            switch (n) {
                default: {
                    return false;
                }
                case 4: 
                case 5: 
                case 6: 
                case 7: 
                case 8: 
                case 9: 
            }
        }
        return true;
    }

    private boolean matchesBluetooth(NetworkIdentity networkIdentity) {
        return networkIdentity.mType == 7;
    }

    private boolean matchesDefaultNetwork(NetworkIdentity networkIdentity) {
        boolean bl;
        block2 : {
            boolean bl2;
            block3 : {
                int n = this.mDefaultNetwork;
                bl = bl2 = true;
                if (n == -1) break block2;
                if (n != 1) break block3;
                bl = bl2;
                if (networkIdentity.mDefaultNetwork) break block2;
            }
            bl = this.mDefaultNetwork == 0 && !networkIdentity.mDefaultNetwork ? bl2 : false;
        }
        return bl;
    }

    private boolean matchesEthernet(NetworkIdentity networkIdentity) {
        return networkIdentity.mType == 9;
    }

    private boolean matchesMetered(NetworkIdentity networkIdentity) {
        boolean bl;
        block2 : {
            boolean bl2;
            block3 : {
                int n = this.mMetered;
                bl = bl2 = true;
                if (n == -1) break block2;
                if (n != 1) break block3;
                bl = bl2;
                if (networkIdentity.mMetered) break block2;
            }
            bl = this.mMetered == 0 && !networkIdentity.mMetered ? bl2 : false;
        }
        return bl;
    }

    private boolean matchesMobile(NetworkIdentity networkIdentity) {
        int n = networkIdentity.mType;
        boolean bl = true;
        if (n == 6) {
            return true;
        }
        if (!sForceAllNetworkTypes && (networkIdentity.mType != 0 || !networkIdentity.mMetered) || ArrayUtils.isEmpty(this.mMatchSubscriberIds) || !ArrayUtils.contains(this.mMatchSubscriberIds, networkIdentity.mSubscriberId)) {
            bl = false;
        }
        return bl;
    }

    private boolean matchesMobileWildcard(NetworkIdentity networkIdentity) {
        int n = networkIdentity.mType;
        boolean bl = true;
        if (n == 6) {
            return true;
        }
        boolean bl2 = bl;
        if (!sForceAllNetworkTypes) {
            bl2 = networkIdentity.mType == 0 && networkIdentity.mMetered ? bl : false;
        }
        return bl2;
    }

    private boolean matchesProxy(NetworkIdentity networkIdentity) {
        boolean bl = networkIdentity.mType == 16;
        return bl;
    }

    private boolean matchesRoaming(NetworkIdentity networkIdentity) {
        boolean bl;
        block2 : {
            boolean bl2;
            block3 : {
                int n = this.mRoaming;
                bl = bl2 = true;
                if (n == -1) break block2;
                if (n != 1) break block3;
                bl = bl2;
                if (networkIdentity.mRoaming) break block2;
            }
            bl = this.mRoaming == 0 && !networkIdentity.mRoaming ? bl2 : false;
        }
        return bl;
    }

    private boolean matchesWifi(NetworkIdentity networkIdentity) {
        if (networkIdentity.mType != 1) {
            return false;
        }
        return Objects.equals(WifiInfo.removeDoubleQuotes(this.mNetworkId), WifiInfo.removeDoubleQuotes(networkIdentity.mNetworkId));
    }

    private boolean matchesWifiWildcard(NetworkIdentity networkIdentity) {
        int n = networkIdentity.mType;
        return n == 1 || n == 13;
    }

    @UnsupportedAppUsage
    public static NetworkTemplate normalize(NetworkTemplate networkTemplate, String[] arrstring) {
        if (networkTemplate.isMatchRuleMobile() && ArrayUtils.contains(arrstring, networkTemplate.mSubscriberId)) {
            return new NetworkTemplate(networkTemplate.mMatchRule, arrstring[0], arrstring, networkTemplate.mNetworkId);
        }
        return networkTemplate;
    }

    @VisibleForTesting
    public static void resetForceAllNetworkTypes() {
        sForceAllNetworkTypes = false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof NetworkTemplate;
        boolean bl2 = false;
        if (bl) {
            object = (NetworkTemplate)object;
            if (this.mMatchRule == ((NetworkTemplate)object).mMatchRule && Objects.equals(this.mSubscriberId, ((NetworkTemplate)object).mSubscriberId) && Objects.equals(this.mNetworkId, ((NetworkTemplate)object).mNetworkId) && this.mMetered == ((NetworkTemplate)object).mMetered && this.mRoaming == ((NetworkTemplate)object).mRoaming && this.mDefaultNetwork == ((NetworkTemplate)object).mDefaultNetwork) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    public byte[] getBytesForBackup() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        dataOutputStream.writeInt(1);
        dataOutputStream.writeInt(this.mMatchRule);
        BackupUtils.writeString(dataOutputStream, this.mSubscriberId);
        BackupUtils.writeString(dataOutputStream, this.mNetworkId);
        return byteArrayOutputStream.toByteArray();
    }

    @UnsupportedAppUsage
    public int getMatchRule() {
        return this.mMatchRule;
    }

    public String getNetworkId() {
        return this.mNetworkId;
    }

    @UnsupportedAppUsage
    public String getSubscriberId() {
        return this.mSubscriberId;
    }

    public int hashCode() {
        return Objects.hash(this.mMatchRule, this.mSubscriberId, this.mNetworkId, this.mMetered, this.mRoaming, this.mDefaultNetwork);
    }

    public boolean isMatchRuleMobile() {
        int n = this.mMatchRule;
        return n == 1 || n == 6;
    }

    public boolean isPersistable() {
        int n = this.mMatchRule;
        return n != 6 && n != 7;
    }

    public boolean matches(NetworkIdentity networkIdentity) {
        if (!this.matchesMetered(networkIdentity)) {
            return false;
        }
        if (!this.matchesRoaming(networkIdentity)) {
            return false;
        }
        if (!this.matchesDefaultNetwork(networkIdentity)) {
            return false;
        }
        int n = this.mMatchRule;
        if (n != 1) {
            switch (n) {
                default: {
                    return false;
                }
                case 9: {
                    return this.matchesProxy(networkIdentity);
                }
                case 8: {
                    return this.matchesBluetooth(networkIdentity);
                }
                case 7: {
                    return this.matchesWifiWildcard(networkIdentity);
                }
                case 6: {
                    return this.matchesMobileWildcard(networkIdentity);
                }
                case 5: {
                    return this.matchesEthernet(networkIdentity);
                }
                case 4: 
            }
            return this.matchesWifi(networkIdentity);
        }
        return this.matchesMobile(networkIdentity);
    }

    public boolean matchesSubscriberId(String string2) {
        return ArrayUtils.contains(this.mMatchSubscriberIds, string2);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("NetworkTemplate: ");
        stringBuilder.append("matchRule=");
        stringBuilder.append(NetworkTemplate.getMatchRuleName(this.mMatchRule));
        if (this.mSubscriberId != null) {
            stringBuilder.append(", subscriberId=");
            stringBuilder.append(NetworkIdentity.scrubSubscriberId(this.mSubscriberId));
        }
        if (this.mMatchSubscriberIds != null) {
            stringBuilder.append(", matchSubscriberIds=");
            stringBuilder.append(Arrays.toString(NetworkIdentity.scrubSubscriberId(this.mMatchSubscriberIds)));
        }
        if (this.mNetworkId != null) {
            stringBuilder.append(", networkId=");
            stringBuilder.append(this.mNetworkId);
        }
        if (this.mMetered != -1) {
            stringBuilder.append(", metered=");
            stringBuilder.append(NetworkStats.meteredToString(this.mMetered));
        }
        if (this.mRoaming != -1) {
            stringBuilder.append(", roaming=");
            stringBuilder.append(NetworkStats.roamingToString(this.mRoaming));
        }
        if (this.mDefaultNetwork != -1) {
            stringBuilder.append(", defaultNetwork=");
            stringBuilder.append(NetworkStats.defaultNetworkToString(this.mDefaultNetwork));
        }
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mMatchRule);
        parcel.writeString(this.mSubscriberId);
        parcel.writeStringArray(this.mMatchSubscriberIds);
        parcel.writeString(this.mNetworkId);
        parcel.writeInt(this.mMetered);
        parcel.writeInt(this.mRoaming);
        parcel.writeInt(this.mDefaultNetwork);
    }

}

