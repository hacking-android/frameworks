/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.net.IpConfiguration;
import android.net.MacAddress;
import android.net.ProxyInfo;
import android.net.StaticIpConfiguration;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiSsid;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.BackupUtils;
import android.util.Log;
import android.util.TimeUtils;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Deprecated
public class WifiConfiguration
implements Parcelable {
    public static final int AP_BAND_2GHZ = 0;
    public static final int AP_BAND_5GHZ = 1;
    public static final int AP_BAND_ANY = -1;
    private static final int BACKUP_VERSION = 3;
    @UnsupportedAppUsage
    public static final Parcelable.Creator<WifiConfiguration> CREATOR;
    public static final int HOME_NETWORK_RSSI_BOOST = 5;
    public static final int INVALID_NETWORK_ID = -1;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public static int INVALID_RSSI = 0;
    public static final int LOCAL_ONLY_NETWORK_ID = -2;
    private static final int MAXIMUM_RANDOM_MAC_GENERATION_RETRY = 3;
    public static final int METERED_OVERRIDE_METERED = 1;
    public static final int METERED_OVERRIDE_NONE = 0;
    public static final int METERED_OVERRIDE_NOT_METERED = 2;
    public static final int RANDOMIZATION_NONE = 0;
    public static final int RANDOMIZATION_PERSISTENT = 1;
    public static final int SECURITY_TYPE_EAP = 3;
    public static final int SECURITY_TYPE_EAP_SUITE_B = 5;
    public static final int SECURITY_TYPE_OPEN = 0;
    public static final int SECURITY_TYPE_OWE = 6;
    public static final int SECURITY_TYPE_PSK = 2;
    public static final int SECURITY_TYPE_SAE = 4;
    public static final int SECURITY_TYPE_WEP = 1;
    private static final String TAG = "WifiConfiguration";
    public static final int UNKNOWN_UID = -1;
    public static final int USER_APPROVED = 1;
    public static final int USER_BANNED = 2;
    public static final int USER_PENDING = 3;
    public static final int USER_UNSPECIFIED = 0;
    public static final String bssidVarName = "bssid";
    public static final String hiddenSSIDVarName = "scan_ssid";
    public static final String pmfVarName = "ieee80211w";
    public static final String priorityVarName = "priority";
    public static final String pskVarName = "psk";
    public static final String ssidVarName = "ssid";
    public static final String updateIdentiferVarName = "update_identifier";
    @Deprecated
    @UnsupportedAppUsage
    public static final String[] wepKeyVarNames;
    @Deprecated
    public static final String wepTxKeyIdxVarName = "wep_tx_keyidx";
    public String BSSID;
    public String FQDN;
    public String SSID;
    public BitSet allowedAuthAlgorithms;
    public BitSet allowedGroupCiphers;
    public BitSet allowedGroupManagementCiphers;
    public BitSet allowedKeyManagement;
    public BitSet allowedPairwiseCiphers;
    public BitSet allowedProtocols;
    public BitSet allowedSuiteBCiphers;
    @UnsupportedAppUsage
    public int apBand = 0;
    @UnsupportedAppUsage
    public int apChannel = 0;
    public String creationTime;
    @SystemApi
    public String creatorName;
    @SystemApi
    public int creatorUid;
    @UnsupportedAppUsage
    public String defaultGwMacAddress;
    public String dhcpServer;
    public boolean didSelfAdd;
    public int dtimInterval = 0;
    public WifiEnterpriseConfig enterpriseConfig;
    public boolean ephemeral;
    public boolean fromWifiNetworkSpecifier;
    public boolean fromWifiNetworkSuggestion;
    public boolean hiddenSSID;
    public boolean isHomeProviderNetwork;
    public boolean isLegacyPasspointConfig = false;
    @UnsupportedAppUsage
    public int lastConnectUid;
    public long lastConnected;
    public long lastDisconnected;
    @SystemApi
    public String lastUpdateName;
    @SystemApi
    public int lastUpdateUid;
    public HashMap<String, Integer> linkedConfigurations;
    String mCachedConfigKey;
    @UnsupportedAppUsage
    private IpConfiguration mIpConfiguration;
    private NetworkSelectionStatus mNetworkSelectionStatus = new NetworkSelectionStatus();
    private String mPasspointManagementObjectTree;
    private MacAddress mRandomizedMacAddress;
    public int macRandomizationSetting = 1;
    @SystemApi
    public boolean meteredHint;
    public int meteredOverride = 0;
    public int networkId;
    @UnsupportedAppUsage
    public boolean noInternetAccessExpected;
    @SystemApi
    public int numAssociation;
    @UnsupportedAppUsage
    public int numNoInternetAccessReports;
    @SystemApi
    public int numScorerOverride;
    @SystemApi
    public int numScorerOverrideAndSwitchedNetwork;
    public boolean osu;
    public String peerWifiConfiguration;
    public String preSharedKey;
    @Deprecated
    public int priority;
    public String providerFriendlyName;
    public final RecentFailure recentFailure = new RecentFailure();
    public boolean requirePMF;
    public long[] roamingConsortiumIds;
    @UnsupportedAppUsage
    public boolean selfAdded;
    @UnsupportedAppUsage
    public boolean shared;
    public int status;
    public boolean trusted;
    public String updateIdentifier;
    public String updateTime;
    @SystemApi
    public boolean useExternalScores;
    public int userApproved = 0;
    @UnsupportedAppUsage
    public boolean validatedInternetAccess;
    @Deprecated
    public String[] wepKeys;
    @Deprecated
    public int wepTxKeyIndex;

    static {
        wepKeyVarNames = new String[]{"wep_key0", "wep_key1", "wep_key2", "wep_key3"};
        INVALID_RSSI = -127;
        CREATOR = new Parcelable.Creator<WifiConfiguration>(){

            @Override
            public WifiConfiguration createFromParcel(Parcel parcel) {
                WifiConfiguration wifiConfiguration = new WifiConfiguration();
                wifiConfiguration.networkId = parcel.readInt();
                wifiConfiguration.status = parcel.readInt();
                wifiConfiguration.mNetworkSelectionStatus.readFromParcel(parcel);
                wifiConfiguration.SSID = parcel.readString();
                wifiConfiguration.BSSID = parcel.readString();
                wifiConfiguration.apBand = parcel.readInt();
                wifiConfiguration.apChannel = parcel.readInt();
                wifiConfiguration.FQDN = parcel.readString();
                wifiConfiguration.providerFriendlyName = parcel.readString();
                int n = parcel.readInt();
                boolean bl = false;
                boolean bl2 = n != 0;
                wifiConfiguration.isHomeProviderNetwork = bl2;
                int n2 = parcel.readInt();
                wifiConfiguration.roamingConsortiumIds = new long[n2];
                for (n = 0; n < n2; ++n) {
                    wifiConfiguration.roamingConsortiumIds[n] = parcel.readLong();
                }
                wifiConfiguration.preSharedKey = parcel.readString();
                for (n = 0; n < wifiConfiguration.wepKeys.length; ++n) {
                    wifiConfiguration.wepKeys[n] = parcel.readString();
                }
                wifiConfiguration.wepTxKeyIndex = parcel.readInt();
                wifiConfiguration.priority = parcel.readInt();
                bl2 = parcel.readInt() != 0;
                wifiConfiguration.hiddenSSID = bl2;
                bl2 = parcel.readInt() != 0;
                wifiConfiguration.requirePMF = bl2;
                wifiConfiguration.updateIdentifier = parcel.readString();
                wifiConfiguration.allowedKeyManagement = WifiConfiguration.readBitSet(parcel);
                wifiConfiguration.allowedProtocols = WifiConfiguration.readBitSet(parcel);
                wifiConfiguration.allowedAuthAlgorithms = WifiConfiguration.readBitSet(parcel);
                wifiConfiguration.allowedPairwiseCiphers = WifiConfiguration.readBitSet(parcel);
                wifiConfiguration.allowedGroupCiphers = WifiConfiguration.readBitSet(parcel);
                wifiConfiguration.allowedGroupManagementCiphers = WifiConfiguration.readBitSet(parcel);
                wifiConfiguration.allowedSuiteBCiphers = WifiConfiguration.readBitSet(parcel);
                wifiConfiguration.enterpriseConfig = (WifiEnterpriseConfig)parcel.readParcelable(null);
                wifiConfiguration.setIpConfiguration((IpConfiguration)parcel.readParcelable(null));
                wifiConfiguration.dhcpServer = parcel.readString();
                wifiConfiguration.defaultGwMacAddress = parcel.readString();
                bl2 = parcel.readInt() != 0;
                wifiConfiguration.selfAdded = bl2;
                bl2 = parcel.readInt() != 0;
                wifiConfiguration.didSelfAdd = bl2;
                bl2 = parcel.readInt() != 0;
                wifiConfiguration.validatedInternetAccess = bl2;
                bl2 = parcel.readInt() != 0;
                wifiConfiguration.isLegacyPasspointConfig = bl2;
                bl2 = parcel.readInt() != 0;
                wifiConfiguration.ephemeral = bl2;
                bl2 = parcel.readInt() != 0;
                wifiConfiguration.trusted = bl2;
                bl2 = parcel.readInt() != 0;
                wifiConfiguration.fromWifiNetworkSuggestion = bl2;
                bl2 = parcel.readInt() != 0;
                wifiConfiguration.fromWifiNetworkSpecifier = bl2;
                bl2 = parcel.readInt() != 0;
                wifiConfiguration.meteredHint = bl2;
                wifiConfiguration.meteredOverride = parcel.readInt();
                bl2 = parcel.readInt() != 0;
                wifiConfiguration.useExternalScores = bl2;
                wifiConfiguration.creatorUid = parcel.readInt();
                wifiConfiguration.lastConnectUid = parcel.readInt();
                wifiConfiguration.lastUpdateUid = parcel.readInt();
                wifiConfiguration.creatorName = parcel.readString();
                wifiConfiguration.lastUpdateName = parcel.readString();
                wifiConfiguration.numScorerOverride = parcel.readInt();
                wifiConfiguration.numScorerOverrideAndSwitchedNetwork = parcel.readInt();
                wifiConfiguration.numAssociation = parcel.readInt();
                wifiConfiguration.userApproved = parcel.readInt();
                wifiConfiguration.numNoInternetAccessReports = parcel.readInt();
                bl2 = parcel.readInt() != 0;
                wifiConfiguration.noInternetAccessExpected = bl2;
                bl2 = parcel.readInt() != 0;
                wifiConfiguration.shared = bl2;
                wifiConfiguration.mPasspointManagementObjectTree = parcel.readString();
                wifiConfiguration.recentFailure.setAssociationStatus(parcel.readInt());
                wifiConfiguration.mRandomizedMacAddress = (MacAddress)parcel.readParcelable(null);
                wifiConfiguration.macRandomizationSetting = parcel.readInt();
                bl2 = bl;
                if (parcel.readInt() != 0) {
                    bl2 = true;
                }
                wifiConfiguration.osu = bl2;
                return wifiConfiguration;
            }

            public WifiConfiguration[] newArray(int n) {
                return new WifiConfiguration[n];
            }
        };
    }

    public WifiConfiguration() {
        String[] arrstring;
        this.networkId = -1;
        this.SSID = null;
        this.BSSID = null;
        this.FQDN = null;
        this.roamingConsortiumIds = new long[0];
        this.priority = 0;
        this.hiddenSSID = false;
        this.allowedKeyManagement = new BitSet();
        this.allowedProtocols = new BitSet();
        this.allowedAuthAlgorithms = new BitSet();
        this.allowedPairwiseCiphers = new BitSet();
        this.allowedGroupCiphers = new BitSet();
        this.allowedGroupManagementCiphers = new BitSet();
        this.allowedSuiteBCiphers = new BitSet();
        this.wepKeys = new String[4];
        for (int i = 0; i < (arrstring = this.wepKeys).length; ++i) {
            arrstring[i] = null;
        }
        this.enterpriseConfig = new WifiEnterpriseConfig();
        this.selfAdded = false;
        this.didSelfAdd = false;
        this.ephemeral = false;
        this.osu = false;
        this.trusted = true;
        this.fromWifiNetworkSuggestion = false;
        this.fromWifiNetworkSpecifier = false;
        this.meteredHint = false;
        this.meteredOverride = 0;
        this.useExternalScores = false;
        this.validatedInternetAccess = false;
        this.mIpConfiguration = new IpConfiguration();
        this.lastUpdateUid = -1;
        this.creatorUid = -1;
        this.shared = true;
        this.dtimInterval = 0;
        this.mRandomizedMacAddress = MacAddress.fromString("02:00:00:00:00:00");
    }

    @UnsupportedAppUsage
    public WifiConfiguration(WifiConfiguration wifiConfiguration) {
        if (wifiConfiguration != null) {
            Object object;
            this.networkId = wifiConfiguration.networkId;
            this.status = wifiConfiguration.status;
            this.SSID = wifiConfiguration.SSID;
            this.BSSID = wifiConfiguration.BSSID;
            this.FQDN = wifiConfiguration.FQDN;
            this.roamingConsortiumIds = (long[])wifiConfiguration.roamingConsortiumIds.clone();
            this.providerFriendlyName = wifiConfiguration.providerFriendlyName;
            this.isHomeProviderNetwork = wifiConfiguration.isHomeProviderNetwork;
            this.preSharedKey = wifiConfiguration.preSharedKey;
            this.mNetworkSelectionStatus.copy(wifiConfiguration.getNetworkSelectionStatus());
            this.apBand = wifiConfiguration.apBand;
            this.apChannel = wifiConfiguration.apChannel;
            this.wepKeys = new String[4];
            for (int i = 0; i < ((String[])(object = this.wepKeys)).length; ++i) {
                object[i] = wifiConfiguration.wepKeys[i];
            }
            this.wepTxKeyIndex = wifiConfiguration.wepTxKeyIndex;
            this.priority = wifiConfiguration.priority;
            this.hiddenSSID = wifiConfiguration.hiddenSSID;
            this.allowedKeyManagement = (BitSet)wifiConfiguration.allowedKeyManagement.clone();
            this.allowedProtocols = (BitSet)wifiConfiguration.allowedProtocols.clone();
            this.allowedAuthAlgorithms = (BitSet)wifiConfiguration.allowedAuthAlgorithms.clone();
            this.allowedPairwiseCiphers = (BitSet)wifiConfiguration.allowedPairwiseCiphers.clone();
            this.allowedGroupCiphers = (BitSet)wifiConfiguration.allowedGroupCiphers.clone();
            this.allowedGroupManagementCiphers = (BitSet)wifiConfiguration.allowedGroupManagementCiphers.clone();
            this.allowedSuiteBCiphers = (BitSet)wifiConfiguration.allowedSuiteBCiphers.clone();
            this.enterpriseConfig = new WifiEnterpriseConfig(wifiConfiguration.enterpriseConfig);
            this.defaultGwMacAddress = wifiConfiguration.defaultGwMacAddress;
            this.mIpConfiguration = new IpConfiguration(wifiConfiguration.mIpConfiguration);
            object = wifiConfiguration.linkedConfigurations;
            if (object != null && ((HashMap)object).size() > 0) {
                this.linkedConfigurations = new HashMap();
                this.linkedConfigurations.putAll(wifiConfiguration.linkedConfigurations);
            }
            this.mCachedConfigKey = null;
            this.selfAdded = wifiConfiguration.selfAdded;
            this.validatedInternetAccess = wifiConfiguration.validatedInternetAccess;
            this.isLegacyPasspointConfig = wifiConfiguration.isLegacyPasspointConfig;
            this.ephemeral = wifiConfiguration.ephemeral;
            this.osu = wifiConfiguration.osu;
            this.trusted = wifiConfiguration.trusted;
            this.fromWifiNetworkSuggestion = wifiConfiguration.fromWifiNetworkSuggestion;
            this.fromWifiNetworkSpecifier = wifiConfiguration.fromWifiNetworkSpecifier;
            this.meteredHint = wifiConfiguration.meteredHint;
            this.meteredOverride = wifiConfiguration.meteredOverride;
            this.useExternalScores = wifiConfiguration.useExternalScores;
            this.didSelfAdd = wifiConfiguration.didSelfAdd;
            this.lastConnectUid = wifiConfiguration.lastConnectUid;
            this.lastUpdateUid = wifiConfiguration.lastUpdateUid;
            this.creatorUid = wifiConfiguration.creatorUid;
            this.creatorName = wifiConfiguration.creatorName;
            this.lastUpdateName = wifiConfiguration.lastUpdateName;
            this.peerWifiConfiguration = wifiConfiguration.peerWifiConfiguration;
            this.lastConnected = wifiConfiguration.lastConnected;
            this.lastDisconnected = wifiConfiguration.lastDisconnected;
            this.numScorerOverride = wifiConfiguration.numScorerOverride;
            this.numScorerOverrideAndSwitchedNetwork = wifiConfiguration.numScorerOverrideAndSwitchedNetwork;
            this.numAssociation = wifiConfiguration.numAssociation;
            this.userApproved = wifiConfiguration.userApproved;
            this.numNoInternetAccessReports = wifiConfiguration.numNoInternetAccessReports;
            this.noInternetAccessExpected = wifiConfiguration.noInternetAccessExpected;
            this.creationTime = wifiConfiguration.creationTime;
            this.updateTime = wifiConfiguration.updateTime;
            this.shared = wifiConfiguration.shared;
            this.recentFailure.setAssociationStatus(wifiConfiguration.recentFailure.getAssociationStatus());
            this.mRandomizedMacAddress = wifiConfiguration.mRandomizedMacAddress;
            this.macRandomizationSetting = wifiConfiguration.macRandomizationSetting;
            this.requirePMF = wifiConfiguration.requirePMF;
            this.updateIdentifier = wifiConfiguration.updateIdentifier;
        }
    }

    public static WifiConfiguration getWifiConfigFromBackup(DataInputStream dataInputStream) throws IOException, BackupUtils.BadVersionException {
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        int n = dataInputStream.readInt();
        if (n >= 1 && n <= 3) {
            if (n == 1) {
                return null;
            }
            wifiConfiguration.SSID = BackupUtils.readString(dataInputStream);
            wifiConfiguration.apBand = dataInputStream.readInt();
            wifiConfiguration.apChannel = dataInputStream.readInt();
            wifiConfiguration.preSharedKey = BackupUtils.readString(dataInputStream);
            wifiConfiguration.allowedKeyManagement.set(dataInputStream.readInt());
            if (n >= 3) {
                wifiConfiguration.hiddenSSID = dataInputStream.readBoolean();
            }
            return wifiConfiguration;
        }
        throw new BackupUtils.BadVersionException("Unknown Backup Serialization Version");
    }

    public static boolean isMetered(WifiConfiguration wifiConfiguration, WifiInfo wifiInfo) {
        boolean bl;
        boolean bl2 = bl = false;
        if (wifiInfo != null) {
            bl2 = bl;
            if (wifiInfo.getMeteredHint()) {
                bl2 = true;
            }
        }
        bl = bl2;
        if (wifiConfiguration != null) {
            bl = bl2;
            if (wifiConfiguration.meteredHint) {
                bl = true;
            }
        }
        bl2 = bl;
        if (wifiConfiguration != null) {
            bl2 = bl;
            if (wifiConfiguration.meteredOverride == 1) {
                bl2 = true;
            }
        }
        bl = bl2;
        if (wifiConfiguration != null) {
            bl = bl2;
            if (wifiConfiguration.meteredOverride == 2) {
                bl = false;
            }
        }
        return bl;
    }

    public static boolean isValidMacAddressForRandomization(MacAddress macAddress) {
        boolean bl = macAddress != null && !macAddress.isMulticastAddress() && macAddress.isLocallyAssigned() && !MacAddress.fromString("02:00:00:00:00:00").equals(macAddress);
        return bl;
    }

    private static BitSet readBitSet(Parcel parcel) {
        int n = parcel.readInt();
        BitSet bitSet = new BitSet();
        for (int i = 0; i < n; ++i) {
            bitSet.set(parcel.readInt());
        }
        return bitSet;
    }

    private String trimStringForKeyId(String string2) {
        return string2.replace("\"", "").replace(" ", "");
    }

    public static String userApprovedAsString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    return "INVALID";
                }
                return "USER_BANNED";
            }
            return "USER_APPROVED";
        }
        return "USER_UNSPECIFIED";
    }

    private static void writeBitSet(Parcel parcel, BitSet bitSet) {
        int n = -1;
        parcel.writeInt(bitSet.cardinality());
        do {
            int n2;
            n = n2 = bitSet.nextSetBit(n + 1);
            if (n2 == -1) break;
            parcel.writeInt(n);
        } while (true);
    }

    public String configKey() {
        return this.configKey(false);
    }

    public String configKey(boolean bl) {
        CharSequence charSequence;
        if (bl && this.mCachedConfigKey != null) {
            charSequence = this.mCachedConfigKey;
        } else if (this.providerFriendlyName != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(this.FQDN);
            ((StringBuilder)charSequence).append(KeyMgmt.strings[2]);
            String string2 = ((StringBuilder)charSequence).toString();
            charSequence = string2;
            if (!this.shared) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string2);
                ((StringBuilder)charSequence).append("-");
                ((StringBuilder)charSequence).append(Integer.toString(UserHandle.getUserId(this.creatorUid)));
                charSequence = ((StringBuilder)charSequence).toString();
            }
        } else {
            String string3 = this.getSsidAndSecurityTypeString();
            charSequence = string3;
            if (!this.shared) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string3);
                ((StringBuilder)charSequence).append("-");
                ((StringBuilder)charSequence).append(Integer.toString(UserHandle.getUserId(this.creatorUid)));
                charSequence = ((StringBuilder)charSequence).toString();
            }
            this.mCachedConfigKey = charSequence;
        }
        return charSequence;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @UnsupportedAppUsage
    public int getAuthType() {
        if (this.allowedKeyManagement.cardinality() <= 1) {
            if (this.allowedKeyManagement.get(1)) {
                return 1;
            }
            if (this.allowedKeyManagement.get(4)) {
                return 4;
            }
            if (this.allowedKeyManagement.get(2)) {
                return 2;
            }
            if (this.allowedKeyManagement.get(3)) {
                return 3;
            }
            if (this.allowedKeyManagement.get(8)) {
                return 8;
            }
            if (this.allowedKeyManagement.get(9)) {
                return 9;
            }
            if (this.allowedKeyManagement.get(10)) {
                return 10;
            }
            return 0;
        }
        throw new IllegalStateException("More than one auth type set");
    }

    public byte[] getBytesForBackup() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        dataOutputStream.writeInt(3);
        BackupUtils.writeString(dataOutputStream, this.SSID);
        dataOutputStream.writeInt(this.apBand);
        dataOutputStream.writeInt(this.apChannel);
        BackupUtils.writeString(dataOutputStream, this.preSharedKey);
        dataOutputStream.writeInt(this.getAuthType());
        dataOutputStream.writeBoolean(this.hiddenSSID);
        return byteArrayOutputStream.toByteArray();
    }

    public ProxyInfo getHttpProxy() {
        if (this.mIpConfiguration.proxySettings == IpConfiguration.ProxySettings.NONE) {
            return null;
        }
        return new ProxyInfo(this.mIpConfiguration.httpProxy);
    }

    @UnsupportedAppUsage
    public IpConfiguration.IpAssignment getIpAssignment() {
        return this.mIpConfiguration.ipAssignment;
    }

    @UnsupportedAppUsage
    public IpConfiguration getIpConfiguration() {
        return this.mIpConfiguration;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String getKeyIdForCredentials(WifiConfiguration object) {
        CharSequence charSequence = "";
        try {
            Object object2;
            if (TextUtils.isEmpty(this.SSID)) {
                this.SSID = ((WifiConfiguration)object).SSID;
            }
            if (this.allowedKeyManagement.cardinality() == 0) {
                this.allowedKeyManagement = ((WifiConfiguration)object).allowedKeyManagement;
            }
            if (this.allowedKeyManagement.get(2)) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("");
                ((StringBuilder)object2).append(KeyMgmt.strings[2]);
                charSequence = ((StringBuilder)object2).toString();
            }
            object2 = charSequence;
            if (this.allowedKeyManagement.get(5)) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append((String)charSequence);
                ((StringBuilder)object2).append(KeyMgmt.strings[5]);
                object2 = ((StringBuilder)object2).toString();
            }
            charSequence = object2;
            if (this.allowedKeyManagement.get(3)) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append((String)object2);
                ((StringBuilder)charSequence).append(KeyMgmt.strings[3]);
                charSequence = ((StringBuilder)charSequence).toString();
            }
            object2 = charSequence;
            if (this.allowedKeyManagement.get(10)) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append((String)charSequence);
                ((StringBuilder)object2).append(KeyMgmt.strings[10]);
                object2 = ((StringBuilder)object2).toString();
            }
            if (TextUtils.isEmpty((CharSequence)object2)) {
                object = new IllegalStateException("Not an EAP network");
                throw object;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(this.trimStringForKeyId(this.SSID));
            ((StringBuilder)charSequence).append("_");
            ((StringBuilder)charSequence).append((String)object2);
            ((StringBuilder)charSequence).append("_");
            object2 = this.enterpriseConfig;
            object = object != null ? ((WifiConfiguration)object).enterpriseConfig : null;
            ((StringBuilder)charSequence).append(this.trimStringForKeyId(((WifiEnterpriseConfig)object2).getKeyId((WifiEnterpriseConfig)object)));
            return ((StringBuilder)charSequence).toString();
        }
        catch (NullPointerException nullPointerException) {
            throw new IllegalStateException("Invalid config details");
        }
    }

    public String getMoTree() {
        return this.mPasspointManagementObjectTree;
    }

    public NetworkSelectionStatus getNetworkSelectionStatus() {
        return this.mNetworkSelectionStatus;
    }

    public MacAddress getOrCreateRandomizedMacAddress() {
        for (int i = 0; !WifiConfiguration.isValidMacAddressForRandomization(this.mRandomizedMacAddress) && i < 3; ++i) {
            this.mRandomizedMacAddress = MacAddress.createRandomUnicastAddress();
        }
        if (!WifiConfiguration.isValidMacAddressForRandomization(this.mRandomizedMacAddress)) {
            this.mRandomizedMacAddress = MacAddress.fromString("02:00:00:00:00:00");
        }
        return this.mRandomizedMacAddress;
    }

    @UnsupportedAppUsage
    public String getPrintableSsid() {
        String string2 = this.SSID;
        if (string2 == null) {
            return "";
        }
        int n = string2.length();
        if (n > 2 && this.SSID.charAt(0) == '\"' && this.SSID.charAt(n - 1) == '\"') {
            return this.SSID.substring(1, n - 1);
        }
        if (n > 3 && this.SSID.charAt(0) == 'P' && this.SSID.charAt(1) == '\"' && this.SSID.charAt(n - 1) == '\"') {
            return WifiSsid.createFromAsciiEncoded(this.SSID.substring(2, n - 1)).toString();
        }
        return this.SSID;
    }

    @UnsupportedAppUsage
    public IpConfiguration.ProxySettings getProxySettings() {
        return this.mIpConfiguration.proxySettings;
    }

    public MacAddress getRandomizedMacAddress() {
        return this.mRandomizedMacAddress;
    }

    public String getSsidAndSecurityTypeString() {
        CharSequence charSequence;
        if (this.allowedKeyManagement.get(1)) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(this.SSID);
            ((StringBuilder)charSequence).append(KeyMgmt.strings[1]);
            charSequence = ((StringBuilder)charSequence).toString();
        } else if (!this.allowedKeyManagement.get(2) && !this.allowedKeyManagement.get(3)) {
            if (this.wepKeys[0] != null) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(this.SSID);
                ((StringBuilder)charSequence).append("WEP");
                charSequence = ((StringBuilder)charSequence).toString();
            } else if (this.allowedKeyManagement.get(9)) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(this.SSID);
                ((StringBuilder)charSequence).append(KeyMgmt.strings[9]);
                charSequence = ((StringBuilder)charSequence).toString();
            } else if (this.allowedKeyManagement.get(8)) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(this.SSID);
                ((StringBuilder)charSequence).append(KeyMgmt.strings[8]);
                charSequence = ((StringBuilder)charSequence).toString();
            } else if (this.allowedKeyManagement.get(10)) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(this.SSID);
                ((StringBuilder)charSequence).append(KeyMgmt.strings[10]);
                charSequence = ((StringBuilder)charSequence).toString();
            } else {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(this.SSID);
                ((StringBuilder)charSequence).append(KeyMgmt.strings[0]);
                charSequence = ((StringBuilder)charSequence).toString();
            }
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(this.SSID);
            ((StringBuilder)charSequence).append(KeyMgmt.strings[2]);
            charSequence = ((StringBuilder)charSequence).toString();
        }
        return charSequence;
    }

    @UnsupportedAppUsage
    public StaticIpConfiguration getStaticIpConfiguration() {
        return this.mIpConfiguration.getStaticIpConfiguration();
    }

    @SystemApi
    public boolean hasNoInternetAccess() {
        boolean bl = this.numNoInternetAccessReports > 0 && !this.validatedInternetAccess;
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isEnterprise() {
        WifiEnterpriseConfig wifiEnterpriseConfig;
        boolean bl = (this.allowedKeyManagement.get(2) || this.allowedKeyManagement.get(3) || this.allowedKeyManagement.get(10)) && (wifiEnterpriseConfig = this.enterpriseConfig) != null && wifiEnterpriseConfig.getEapMethod() != -1;
        return bl;
    }

    @SystemApi
    public boolean isEphemeral() {
        return this.ephemeral;
    }

    public boolean isLinked(WifiConfiguration wifiConfiguration) {
        HashMap<String, Integer> hashMap;
        return wifiConfiguration != null && (hashMap = wifiConfiguration.linkedConfigurations) != null && this.linkedConfigurations != null && hashMap.get(this.configKey()) != null && this.linkedConfigurations.get(wifiConfiguration.configKey()) != null;
    }

    @SystemApi
    public boolean isNoInternetAccessExpected() {
        return this.noInternetAccessExpected;
    }

    public boolean isOpenNetwork() {
        boolean bl;
        int n = this.allowedKeyManagement.cardinality();
        boolean bl2 = false;
        n = n != 0 && (n != 1 || !this.allowedKeyManagement.get(0) && !this.allowedKeyManagement.get(9)) ? 0 : 1;
        boolean bl3 = bl = true;
        if (this.wepKeys != null) {
            int n2 = 0;
            do {
                String[] arrstring = this.wepKeys;
                bl3 = bl;
                if (n2 >= arrstring.length) break;
                if (arrstring[n2] != null) {
                    bl3 = false;
                    break;
                }
                ++n2;
            } while (true);
        }
        boolean bl4 = bl2;
        if (n != 0) {
            bl4 = bl2;
            if (bl3) {
                bl4 = true;
            }
        }
        return bl4;
    }

    public boolean isPasspoint() {
        WifiEnterpriseConfig wifiEnterpriseConfig;
        boolean bl = !TextUtils.isEmpty(this.FQDN) && !TextUtils.isEmpty(this.providerFriendlyName) && (wifiEnterpriseConfig = this.enterpriseConfig) != null && wifiEnterpriseConfig.getEapMethod() != -1;
        return bl;
    }

    public void setHttpProxy(ProxyInfo proxyInfo) {
        Object object;
        if (proxyInfo == null) {
            this.mIpConfiguration.setProxySettings(IpConfiguration.ProxySettings.NONE);
            this.mIpConfiguration.setHttpProxy(null);
            return;
        }
        if (!Uri.EMPTY.equals(proxyInfo.getPacFileUrl())) {
            object = IpConfiguration.ProxySettings.PAC;
            proxyInfo = new ProxyInfo(proxyInfo.getPacFileUrl(), proxyInfo.getPort());
        } else {
            object = IpConfiguration.ProxySettings.STATIC;
            proxyInfo = new ProxyInfo(proxyInfo.getHost(), proxyInfo.getPort(), proxyInfo.getExclusionListAsString());
        }
        if (proxyInfo.isValid()) {
            this.mIpConfiguration.setProxySettings((IpConfiguration.ProxySettings)((Object)object));
            this.mIpConfiguration.setHttpProxy(proxyInfo);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Invalid ProxyInfo: ");
        ((StringBuilder)object).append(proxyInfo.toString());
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    @UnsupportedAppUsage
    public void setIpAssignment(IpConfiguration.IpAssignment ipAssignment) {
        this.mIpConfiguration.ipAssignment = ipAssignment;
    }

    @UnsupportedAppUsage
    public void setIpConfiguration(IpConfiguration ipConfiguration) {
        IpConfiguration ipConfiguration2 = ipConfiguration;
        if (ipConfiguration == null) {
            ipConfiguration2 = new IpConfiguration();
        }
        this.mIpConfiguration = ipConfiguration2;
    }

    public void setNetworkSelectionStatus(NetworkSelectionStatus networkSelectionStatus) {
        this.mNetworkSelectionStatus = networkSelectionStatus;
    }

    public void setPasspointManagementObjectTree(String string2) {
        this.mPasspointManagementObjectTree = string2;
    }

    @UnsupportedAppUsage
    public void setProxy(IpConfiguration.ProxySettings proxySettings, ProxyInfo proxyInfo) {
        IpConfiguration ipConfiguration = this.mIpConfiguration;
        ipConfiguration.proxySettings = proxySettings;
        ipConfiguration.httpProxy = proxyInfo;
    }

    @UnsupportedAppUsage
    public void setProxySettings(IpConfiguration.ProxySettings proxySettings) {
        this.mIpConfiguration.proxySettings = proxySettings;
    }

    public void setRandomizedMacAddress(MacAddress macAddress) {
        if (macAddress == null) {
            Log.e(TAG, "setRandomizedMacAddress received null MacAddress.");
            return;
        }
        this.mRandomizedMacAddress = macAddress;
    }

    public void setSecurityParams(int n) {
        this.allowedKeyManagement.clear();
        this.allowedProtocols.clear();
        this.allowedAuthAlgorithms.clear();
        this.allowedPairwiseCiphers.clear();
        this.allowedGroupCiphers.clear();
        this.allowedGroupManagementCiphers.clear();
        this.allowedSuiteBCiphers.clear();
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("unknown security type ");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            case 6: {
                this.allowedKeyManagement.set(9);
                this.requirePMF = true;
                break;
            }
            case 5: {
                this.allowedKeyManagement.set(10);
                this.allowedGroupCiphers.set(5);
                this.allowedGroupManagementCiphers.set(2);
                this.requirePMF = true;
                break;
            }
            case 4: {
                this.allowedKeyManagement.set(8);
                this.requirePMF = true;
                break;
            }
            case 3: {
                this.allowedKeyManagement.set(2);
                this.allowedKeyManagement.set(3);
                break;
            }
            case 2: {
                this.allowedKeyManagement.set(1);
                break;
            }
            case 1: {
                this.allowedKeyManagement.set(0);
                this.allowedAuthAlgorithms.set(0);
                this.allowedAuthAlgorithms.set(1);
                break;
            }
            case 0: {
                this.allowedKeyManagement.set(0);
            }
        }
    }

    @UnsupportedAppUsage
    public void setStaticIpConfiguration(StaticIpConfiguration staticIpConfiguration) {
        this.mIpConfiguration.setStaticIpConfiguration(staticIpConfiguration);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int n = this.status;
        if (n == 0) {
            stringBuilder.append("* ");
        } else if (n == 1) {
            stringBuilder.append("- DSBLE ");
        }
        stringBuilder.append("ID: ");
        stringBuilder.append(this.networkId);
        stringBuilder.append(" SSID: ");
        stringBuilder.append(this.SSID);
        stringBuilder.append(" PROVIDER-NAME: ");
        stringBuilder.append(this.providerFriendlyName);
        stringBuilder.append(" BSSID: ");
        stringBuilder.append(this.BSSID);
        stringBuilder.append(" FQDN: ");
        stringBuilder.append(this.FQDN);
        stringBuilder.append(" PRIO: ");
        stringBuilder.append(this.priority);
        stringBuilder.append(" HIDDEN: ");
        stringBuilder.append(this.hiddenSSID);
        stringBuilder.append(" PMF: ");
        stringBuilder.append(this.requirePMF);
        stringBuilder.append('\n');
        stringBuilder.append(" NetworkSelectionStatus ");
        Object object = new StringBuilder();
        ((StringBuilder)object).append(this.mNetworkSelectionStatus.getNetworkStatusString());
        ((StringBuilder)object).append("\n");
        stringBuilder.append(((StringBuilder)object).toString());
        if (this.mNetworkSelectionStatus.getNetworkSelectionDisableReason() > 0) {
            stringBuilder.append(" mNetworkSelectionDisableReason ");
            object = new StringBuilder();
            ((StringBuilder)object).append(this.mNetworkSelectionStatus.getNetworkDisableReasonString());
            ((StringBuilder)object).append("\n");
            stringBuilder.append(((StringBuilder)object).toString());
            object = this.mNetworkSelectionStatus;
            n = 0;
            do {
                object = this.mNetworkSelectionStatus;
                if (n >= 15) break;
                if (((NetworkSelectionStatus)object).getDisableReasonCounter(n) != 0) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append(NetworkSelectionStatus.getNetworkDisableReasonString(n));
                    ((StringBuilder)object).append(" counter:");
                    ((StringBuilder)object).append(this.mNetworkSelectionStatus.getDisableReasonCounter(n));
                    ((StringBuilder)object).append("\n");
                    stringBuilder.append(((StringBuilder)object).toString());
                }
                ++n;
            } while (true);
        }
        if (this.mNetworkSelectionStatus.getConnectChoice() != null) {
            stringBuilder.append(" connect choice: ");
            stringBuilder.append(this.mNetworkSelectionStatus.getConnectChoice());
            stringBuilder.append(" connect choice set time: ");
            stringBuilder.append(TimeUtils.logTimeOfDay(this.mNetworkSelectionStatus.getConnectChoiceTimestamp()));
        }
        stringBuilder.append(" hasEverConnected: ");
        stringBuilder.append(this.mNetworkSelectionStatus.getHasEverConnected());
        stringBuilder.append("\n");
        if (this.numAssociation > 0) {
            stringBuilder.append(" numAssociation ");
            stringBuilder.append(this.numAssociation);
            stringBuilder.append("\n");
        }
        if (this.numNoInternetAccessReports > 0) {
            stringBuilder.append(" numNoInternetAccessReports ");
            stringBuilder.append(this.numNoInternetAccessReports);
            stringBuilder.append("\n");
        }
        if (this.updateTime != null) {
            stringBuilder.append(" update ");
            stringBuilder.append(this.updateTime);
            stringBuilder.append("\n");
        }
        if (this.creationTime != null) {
            stringBuilder.append(" creation ");
            stringBuilder.append(this.creationTime);
            stringBuilder.append("\n");
        }
        if (this.didSelfAdd) {
            stringBuilder.append(" didSelfAdd");
        }
        if (this.selfAdded) {
            stringBuilder.append(" selfAdded");
        }
        if (this.validatedInternetAccess) {
            stringBuilder.append(" validatedInternetAccess");
        }
        if (this.ephemeral) {
            stringBuilder.append(" ephemeral");
        }
        if (this.osu) {
            stringBuilder.append(" osu");
        }
        if (this.trusted) {
            stringBuilder.append(" trusted");
        }
        if (this.fromWifiNetworkSuggestion) {
            stringBuilder.append(" fromWifiNetworkSuggestion");
        }
        if (this.fromWifiNetworkSpecifier) {
            stringBuilder.append(" fromWifiNetworkSpecifier");
        }
        if (this.meteredHint) {
            stringBuilder.append(" meteredHint");
        }
        if (this.useExternalScores) {
            stringBuilder.append(" useExternalScores");
        }
        if (this.didSelfAdd || this.selfAdded || this.validatedInternetAccess || this.ephemeral || this.trusted || this.fromWifiNetworkSuggestion || this.fromWifiNetworkSpecifier || this.meteredHint || this.useExternalScores) {
            stringBuilder.append("\n");
        }
        if (this.meteredOverride != 0) {
            stringBuilder.append(" meteredOverride ");
            stringBuilder.append(this.meteredOverride);
            stringBuilder.append("\n");
        }
        stringBuilder.append(" macRandomizationSetting: ");
        stringBuilder.append(this.macRandomizationSetting);
        stringBuilder.append("\n");
        stringBuilder.append(" mRandomizedMacAddress: ");
        stringBuilder.append(this.mRandomizedMacAddress);
        stringBuilder.append("\n");
        stringBuilder.append(" KeyMgmt:");
        for (n = 0; n < this.allowedKeyManagement.size(); ++n) {
            if (!this.allowedKeyManagement.get(n)) continue;
            stringBuilder.append(" ");
            if (n < KeyMgmt.strings.length) {
                stringBuilder.append(KeyMgmt.strings[n]);
                continue;
            }
            stringBuilder.append("??");
        }
        stringBuilder.append(" Protocols:");
        for (n = 0; n < this.allowedProtocols.size(); ++n) {
            if (!this.allowedProtocols.get(n)) continue;
            stringBuilder.append(" ");
            if (n < Protocol.strings.length) {
                stringBuilder.append(Protocol.strings[n]);
                continue;
            }
            stringBuilder.append("??");
        }
        stringBuilder.append('\n');
        stringBuilder.append(" AuthAlgorithms:");
        for (n = 0; n < this.allowedAuthAlgorithms.size(); ++n) {
            if (!this.allowedAuthAlgorithms.get(n)) continue;
            stringBuilder.append(" ");
            if (n < AuthAlgorithm.strings.length) {
                stringBuilder.append(AuthAlgorithm.strings[n]);
                continue;
            }
            stringBuilder.append("??");
        }
        stringBuilder.append('\n');
        stringBuilder.append(" PairwiseCiphers:");
        for (n = 0; n < this.allowedPairwiseCiphers.size(); ++n) {
            if (!this.allowedPairwiseCiphers.get(n)) continue;
            stringBuilder.append(" ");
            if (n < PairwiseCipher.strings.length) {
                stringBuilder.append(PairwiseCipher.strings[n]);
                continue;
            }
            stringBuilder.append("??");
        }
        stringBuilder.append('\n');
        stringBuilder.append(" GroupCiphers:");
        for (n = 0; n < this.allowedGroupCiphers.size(); ++n) {
            if (!this.allowedGroupCiphers.get(n)) continue;
            stringBuilder.append(" ");
            if (n < GroupCipher.strings.length) {
                stringBuilder.append(GroupCipher.strings[n]);
                continue;
            }
            stringBuilder.append("??");
        }
        stringBuilder.append('\n');
        stringBuilder.append(" GroupMgmtCiphers:");
        for (n = 0; n < this.allowedGroupManagementCiphers.size(); ++n) {
            if (!this.allowedGroupManagementCiphers.get(n)) continue;
            stringBuilder.append(" ");
            if (n < GroupMgmtCipher.strings.length) {
                stringBuilder.append(GroupMgmtCipher.strings[n]);
                continue;
            }
            stringBuilder.append("??");
        }
        stringBuilder.append('\n');
        stringBuilder.append(" SuiteBCiphers:");
        for (n = 0; n < this.allowedSuiteBCiphers.size(); ++n) {
            if (!this.allowedSuiteBCiphers.get(n)) continue;
            stringBuilder.append(" ");
            if (n < SuiteBCipher.strings.length) {
                stringBuilder.append(SuiteBCipher.strings[n]);
                continue;
            }
            stringBuilder.append("??");
        }
        stringBuilder.append('\n');
        stringBuilder.append(" PSK/SAE: ");
        if (this.preSharedKey != null) {
            stringBuilder.append('*');
        }
        stringBuilder.append("\nEnterprise config:\n");
        stringBuilder.append(this.enterpriseConfig);
        stringBuilder.append("IP config:\n");
        stringBuilder.append(this.mIpConfiguration.toString());
        if (this.mNetworkSelectionStatus.getNetworkSelectionBSSID() != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append(" networkSelectionBSSID=");
            ((StringBuilder)object).append(this.mNetworkSelectionStatus.getNetworkSelectionBSSID());
            stringBuilder.append(((StringBuilder)object).toString());
        }
        long l = SystemClock.elapsedRealtime();
        if (this.mNetworkSelectionStatus.getDisableTime() != -1L) {
            stringBuilder.append('\n');
            if ((l -= this.mNetworkSelectionStatus.getDisableTime()) <= 0L) {
                stringBuilder.append(" blackListed since <incorrect>");
            } else {
                stringBuilder.append(" blackListed: ");
                stringBuilder.append(Long.toString(l / 1000L));
                stringBuilder.append("sec ");
            }
        }
        if (this.creatorUid != 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append(" cuid=");
            ((StringBuilder)object).append(this.creatorUid);
            stringBuilder.append(((StringBuilder)object).toString());
        }
        if (this.creatorName != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append(" cname=");
            ((StringBuilder)object).append(this.creatorName);
            stringBuilder.append(((StringBuilder)object).toString());
        }
        if (this.lastUpdateUid != 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append(" luid=");
            ((StringBuilder)object).append(this.lastUpdateUid);
            stringBuilder.append(((StringBuilder)object).toString());
        }
        if (this.lastUpdateName != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append(" lname=");
            ((StringBuilder)object).append(this.lastUpdateName);
            stringBuilder.append(((StringBuilder)object).toString());
        }
        if (this.updateIdentifier != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append(" updateIdentifier=");
            ((StringBuilder)object).append(this.updateIdentifier);
            stringBuilder.append(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(" lcuid=");
        ((StringBuilder)object).append(this.lastConnectUid);
        stringBuilder.append(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" userApproved=");
        ((StringBuilder)object).append(WifiConfiguration.userApprovedAsString(this.userApproved));
        stringBuilder.append(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" noInternetAccessExpected=");
        ((StringBuilder)object).append(this.noInternetAccessExpected);
        stringBuilder.append(((StringBuilder)object).toString());
        stringBuilder.append(" ");
        if (this.lastConnected != 0L) {
            stringBuilder.append('\n');
            stringBuilder.append("lastConnected: ");
            stringBuilder.append(TimeUtils.logTimeOfDay(this.lastConnected));
            stringBuilder.append(" ");
        }
        stringBuilder.append('\n');
        object = this.linkedConfigurations;
        if (object != null) {
            for (String string2 : ((HashMap)object).keySet()) {
                stringBuilder.append(" linked: ");
                stringBuilder.append(string2);
                stringBuilder.append('\n');
            }
        }
        stringBuilder.append("recentFailure: ");
        stringBuilder.append("Association Rejection code: ");
        stringBuilder.append(this.recentFailure.getAssociationStatus());
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        int n2;
        parcel.writeInt(this.networkId);
        parcel.writeInt(this.status);
        this.mNetworkSelectionStatus.writeToParcel(parcel);
        parcel.writeString(this.SSID);
        parcel.writeString(this.BSSID);
        parcel.writeInt(this.apBand);
        parcel.writeInt(this.apChannel);
        parcel.writeString(this.FQDN);
        parcel.writeString(this.providerFriendlyName);
        parcel.writeInt((int)this.isHomeProviderNetwork);
        parcel.writeInt(this.roamingConsortiumIds.length);
        Object[] arrobject = this.roamingConsortiumIds;
        int n3 = arrobject.length;
        int n4 = 0;
        for (n2 = 0; n2 < n3; ++n2) {
            parcel.writeLong(arrobject[n2]);
        }
        parcel.writeString(this.preSharedKey);
        arrobject = this.wepKeys;
        n3 = arrobject.length;
        for (n2 = n4; n2 < n3; ++n2) {
            parcel.writeString((String)arrobject[n2]);
        }
        parcel.writeInt(this.wepTxKeyIndex);
        parcel.writeInt(this.priority);
        parcel.writeInt((int)this.hiddenSSID);
        parcel.writeInt((int)this.requirePMF);
        parcel.writeString(this.updateIdentifier);
        WifiConfiguration.writeBitSet(parcel, this.allowedKeyManagement);
        WifiConfiguration.writeBitSet(parcel, this.allowedProtocols);
        WifiConfiguration.writeBitSet(parcel, this.allowedAuthAlgorithms);
        WifiConfiguration.writeBitSet(parcel, this.allowedPairwiseCiphers);
        WifiConfiguration.writeBitSet(parcel, this.allowedGroupCiphers);
        WifiConfiguration.writeBitSet(parcel, this.allowedGroupManagementCiphers);
        WifiConfiguration.writeBitSet(parcel, this.allowedSuiteBCiphers);
        parcel.writeParcelable(this.enterpriseConfig, n);
        parcel.writeParcelable(this.mIpConfiguration, n);
        parcel.writeString(this.dhcpServer);
        parcel.writeString(this.defaultGwMacAddress);
        parcel.writeInt((int)this.selfAdded);
        parcel.writeInt((int)this.didSelfAdd);
        parcel.writeInt((int)this.validatedInternetAccess);
        parcel.writeInt((int)this.isLegacyPasspointConfig);
        parcel.writeInt((int)this.ephemeral);
        parcel.writeInt((int)this.trusted);
        parcel.writeInt((int)this.fromWifiNetworkSuggestion);
        parcel.writeInt((int)this.fromWifiNetworkSpecifier);
        parcel.writeInt((int)this.meteredHint);
        parcel.writeInt(this.meteredOverride);
        parcel.writeInt((int)this.useExternalScores);
        parcel.writeInt(this.creatorUid);
        parcel.writeInt(this.lastConnectUid);
        parcel.writeInt(this.lastUpdateUid);
        parcel.writeString(this.creatorName);
        parcel.writeString(this.lastUpdateName);
        parcel.writeInt(this.numScorerOverride);
        parcel.writeInt(this.numScorerOverrideAndSwitchedNetwork);
        parcel.writeInt(this.numAssociation);
        parcel.writeInt(this.userApproved);
        parcel.writeInt(this.numNoInternetAccessReports);
        parcel.writeInt((int)this.noInternetAccessExpected);
        parcel.writeInt((int)this.shared);
        parcel.writeString(this.mPasspointManagementObjectTree);
        parcel.writeInt(this.recentFailure.getAssociationStatus());
        parcel.writeParcelable(this.mRandomizedMacAddress, n);
        parcel.writeInt(this.macRandomizationSetting);
        parcel.writeInt((int)this.osu);
    }

    public static class AuthAlgorithm {
        public static final int LEAP = 2;
        public static final int OPEN = 0;
        @Deprecated
        public static final int SHARED = 1;
        public static final String[] strings = new String[]{"OPEN", "SHARED", "LEAP"};
        public static final String varName = "auth_alg";

        private AuthAlgorithm() {
        }
    }

    public static class GroupCipher {
        public static final int CCMP = 3;
        public static final int GCMP_256 = 5;
        public static final int GTK_NOT_USED = 4;
        public static final int TKIP = 2;
        @Deprecated
        public static final int WEP104 = 1;
        @Deprecated
        public static final int WEP40 = 0;
        public static final String[] strings = new String[]{"WEP40", "WEP104", "TKIP", "CCMP", "GTK_NOT_USED", "GCMP_256"};
        public static final String varName = "group";

        private GroupCipher() {
        }
    }

    public static class GroupMgmtCipher {
        public static final int BIP_CMAC_256 = 0;
        public static final int BIP_GMAC_128 = 1;
        public static final int BIP_GMAC_256 = 2;
        private static final String[] strings = new String[]{"BIP_CMAC_256", "BIP_GMAC_128", "BIP_GMAC_256"};
        private static final String varName = "groupMgmt";

        private GroupMgmtCipher() {
        }
    }

    public static class KeyMgmt {
        public static final int FT_EAP = 7;
        public static final int FT_PSK = 6;
        public static final int IEEE8021X = 3;
        public static final int NONE = 0;
        public static final int OSEN = 5;
        public static final int OWE = 9;
        public static final int SAE = 8;
        public static final int SUITE_B_192 = 10;
        @SystemApi
        public static final int WPA2_PSK = 4;
        public static final int WPA_EAP = 2;
        public static final int WPA_EAP_SHA256 = 12;
        public static final int WPA_PSK = 1;
        public static final int WPA_PSK_SHA256 = 11;
        public static final String[] strings = new String[]{"NONE", "WPA_PSK", "WPA_EAP", "IEEE8021X", "WPA2_PSK", "OSEN", "FT_PSK", "FT_EAP", "SAE", "OWE", "SUITE_B_192", "WPA_PSK_SHA256", "WPA_EAP_SHA256"};
        public static final String varName = "key_mgmt";

        private KeyMgmt() {
        }
    }

    public static class NetworkSelectionStatus {
        private static final int CONNECT_CHOICE_EXISTS = 1;
        private static final int CONNECT_CHOICE_NOT_EXISTS = -1;
        public static final int DISABLED_ASSOCIATION_REJECTION = 2;
        public static final int DISABLED_AUTHENTICATION_FAILURE = 3;
        public static final int DISABLED_AUTHENTICATION_NO_CREDENTIALS = 9;
        public static final int DISABLED_AUTHENTICATION_NO_SUBSCRIPTION = 14;
        public static final int DISABLED_BAD_LINK = 1;
        public static final int DISABLED_BY_WIFI_MANAGER = 11;
        public static final int DISABLED_BY_WRONG_PASSWORD = 13;
        public static final int DISABLED_DHCP_FAILURE = 4;
        public static final int DISABLED_DNS_FAILURE = 5;
        public static final int DISABLED_DUE_TO_USER_SWITCH = 12;
        public static final int DISABLED_NO_INTERNET_PERMANENT = 10;
        public static final int DISABLED_NO_INTERNET_TEMPORARY = 6;
        public static final int DISABLED_TLS_VERSION_MISMATCH = 8;
        public static final int DISABLED_WPS_START = 7;
        public static final long INVALID_NETWORK_SELECTION_DISABLE_TIMESTAMP = -1L;
        public static final int NETWORK_SELECTION_DISABLED_MAX = 15;
        public static final int NETWORK_SELECTION_DISABLED_STARTING_INDEX = 1;
        public static final int NETWORK_SELECTION_ENABLE = 0;
        public static final int NETWORK_SELECTION_ENABLED = 0;
        public static final int NETWORK_SELECTION_PERMANENTLY_DISABLED = 2;
        public static final int NETWORK_SELECTION_STATUS_MAX = 3;
        public static final int NETWORK_SELECTION_TEMPORARY_DISABLED = 1;
        public static final String[] QUALITY_NETWORK_SELECTION_DISABLE_REASON;
        public static final String[] QUALITY_NETWORK_SELECTION_STATUS;
        private ScanResult mCandidate;
        private int mCandidateScore;
        private String mConnectChoice;
        private long mConnectChoiceTimestamp = -1L;
        private boolean mHasEverConnected = false;
        private int[] mNetworkSeclectionDisableCounter = new int[15];
        private String mNetworkSelectionBSSID;
        private int mNetworkSelectionDisableReason;
        private boolean mNotRecommended;
        private boolean mSeenInLastQualifiedNetworkSelection;
        private int mStatus;
        private long mTemporarilyDisabledTimestamp = -1L;

        static {
            QUALITY_NETWORK_SELECTION_STATUS = new String[]{"NETWORK_SELECTION_ENABLED", "NETWORK_SELECTION_TEMPORARY_DISABLED", "NETWORK_SELECTION_PERMANENTLY_DISABLED"};
            QUALITY_NETWORK_SELECTION_DISABLE_REASON = new String[]{"NETWORK_SELECTION_ENABLE", "NETWORK_SELECTION_DISABLED_BAD_LINK", "NETWORK_SELECTION_DISABLED_ASSOCIATION_REJECTION ", "NETWORK_SELECTION_DISABLED_AUTHENTICATION_FAILURE", "NETWORK_SELECTION_DISABLED_DHCP_FAILURE", "NETWORK_SELECTION_DISABLED_DNS_FAILURE", "NETWORK_SELECTION_DISABLED_NO_INTERNET_TEMPORARY", "NETWORK_SELECTION_DISABLED_WPS_START", "NETWORK_SELECTION_DISABLED_TLS_VERSION", "NETWORK_SELECTION_DISABLED_AUTHENTICATION_NO_CREDENTIALS", "NETWORK_SELECTION_DISABLED_NO_INTERNET_PERMANENT", "NETWORK_SELECTION_DISABLED_BY_WIFI_MANAGER", "NETWORK_SELECTION_DISABLED_BY_USER_SWITCH", "NETWORK_SELECTION_DISABLED_BY_WRONG_PASSWORD", "NETWORK_SELECTION_DISABLED_AUTHENTICATION_NO_SUBSCRIPTION"};
        }

        public static String getNetworkDisableReasonString(int n) {
            if (n >= 0 && n < 15) {
                return QUALITY_NETWORK_SELECTION_DISABLE_REASON[n];
            }
            return null;
        }

        public void clearDisableReasonCounter() {
            Arrays.fill(this.mNetworkSeclectionDisableCounter, 0);
        }

        public void clearDisableReasonCounter(int n) {
            if (n >= 0 && n < 15) {
                this.mNetworkSeclectionDisableCounter[n] = 0;
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Illegal reason value: ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public void copy(NetworkSelectionStatus networkSelectionStatus) {
            this.mStatus = networkSelectionStatus.mStatus;
            this.mNetworkSelectionDisableReason = networkSelectionStatus.mNetworkSelectionDisableReason;
            for (int i = 0; i < 15; ++i) {
                this.mNetworkSeclectionDisableCounter[i] = networkSelectionStatus.mNetworkSeclectionDisableCounter[i];
            }
            this.mTemporarilyDisabledTimestamp = networkSelectionStatus.mTemporarilyDisabledTimestamp;
            this.mNetworkSelectionBSSID = networkSelectionStatus.mNetworkSelectionBSSID;
            this.setSeenInLastQualifiedNetworkSelection(networkSelectionStatus.getSeenInLastQualifiedNetworkSelection());
            this.setCandidate(networkSelectionStatus.getCandidate());
            this.setCandidateScore(networkSelectionStatus.getCandidateScore());
            this.setConnectChoice(networkSelectionStatus.getConnectChoice());
            this.setConnectChoiceTimestamp(networkSelectionStatus.getConnectChoiceTimestamp());
            this.setHasEverConnected(networkSelectionStatus.getHasEverConnected());
            this.setNotRecommended(networkSelectionStatus.isNotRecommended());
        }

        public ScanResult getCandidate() {
            return this.mCandidate;
        }

        public int getCandidateScore() {
            return this.mCandidateScore;
        }

        public String getConnectChoice() {
            return this.mConnectChoice;
        }

        public long getConnectChoiceTimestamp() {
            return this.mConnectChoiceTimestamp;
        }

        public int getDisableReasonCounter(int n) {
            if (n >= 0 && n < 15) {
                return this.mNetworkSeclectionDisableCounter[n];
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Illegal reason value: ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public long getDisableTime() {
            return this.mTemporarilyDisabledTimestamp;
        }

        public boolean getHasEverConnected() {
            return this.mHasEverConnected;
        }

        public String getNetworkDisableReasonString() {
            return QUALITY_NETWORK_SELECTION_DISABLE_REASON[this.mNetworkSelectionDisableReason];
        }

        public String getNetworkSelectionBSSID() {
            return this.mNetworkSelectionBSSID;
        }

        public int getNetworkSelectionDisableReason() {
            return this.mNetworkSelectionDisableReason;
        }

        public int getNetworkSelectionStatus() {
            return this.mStatus;
        }

        public String getNetworkStatusString() {
            return QUALITY_NETWORK_SELECTION_STATUS[this.mStatus];
        }

        public boolean getSeenInLastQualifiedNetworkSelection() {
            return this.mSeenInLastQualifiedNetworkSelection;
        }

        public void incrementDisableReasonCounter(int n) {
            if (n >= 0 && n < 15) {
                int[] arrn = this.mNetworkSeclectionDisableCounter;
                arrn[n] = arrn[n] + 1;
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Illegal reason value: ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public boolean isDisabledByReason(int n) {
            boolean bl = this.mNetworkSelectionDisableReason == n;
            return bl;
        }

        public boolean isNetworkEnabled() {
            boolean bl = this.mStatus == 0;
            return bl;
        }

        public boolean isNetworkPermanentlyDisabled() {
            boolean bl = this.mStatus == 2;
            return bl;
        }

        public boolean isNetworkTemporaryDisabled() {
            int n = this.mStatus;
            boolean bl = true;
            if (n != 1) {
                bl = false;
            }
            return bl;
        }

        public boolean isNotRecommended() {
            return this.mNotRecommended;
        }

        public void readFromParcel(Parcel parcel) {
            int n;
            this.setNetworkSelectionStatus(parcel.readInt());
            this.setNetworkSelectionDisableReason(parcel.readInt());
            for (n = 0; n < 15; ++n) {
                this.setDisableReasonCounter(n, parcel.readInt());
            }
            this.setDisableTime(parcel.readLong());
            this.setNetworkSelectionBSSID(parcel.readString());
            n = parcel.readInt();
            boolean bl = true;
            if (n == 1) {
                this.setConnectChoice(parcel.readString());
                this.setConnectChoiceTimestamp(parcel.readLong());
            } else {
                this.setConnectChoice(null);
                this.setConnectChoiceTimestamp(-1L);
            }
            boolean bl2 = parcel.readInt() != 0;
            this.setHasEverConnected(bl2);
            bl2 = parcel.readInt() != 0 ? bl : false;
            this.setNotRecommended(bl2);
        }

        public void setCandidate(ScanResult scanResult) {
            this.mCandidate = scanResult;
        }

        public void setCandidateScore(int n) {
            this.mCandidateScore = n;
        }

        public void setConnectChoice(String string2) {
            this.mConnectChoice = string2;
        }

        public void setConnectChoiceTimestamp(long l) {
            this.mConnectChoiceTimestamp = l;
        }

        public void setDisableReasonCounter(int n, int n2) {
            if (n >= 0 && n < 15) {
                this.mNetworkSeclectionDisableCounter[n] = n2;
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Illegal reason value: ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public void setDisableTime(long l) {
            this.mTemporarilyDisabledTimestamp = l;
        }

        public void setHasEverConnected(boolean bl) {
            this.mHasEverConnected = bl;
        }

        public void setNetworkSelectionBSSID(String string2) {
            this.mNetworkSelectionBSSID = string2;
        }

        public void setNetworkSelectionDisableReason(int n) {
            if (n >= 0 && n < 15) {
                this.mNetworkSelectionDisableReason = n;
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Illegal reason value: ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public void setNetworkSelectionStatus(int n) {
            if (n >= 0 && n < 3) {
                this.mStatus = n;
            }
        }

        public void setNotRecommended(boolean bl) {
            this.mNotRecommended = bl;
        }

        public void setSeenInLastQualifiedNetworkSelection(boolean bl) {
            this.mSeenInLastQualifiedNetworkSelection = bl;
        }

        public void writeToParcel(Parcel parcel) {
            parcel.writeInt(this.getNetworkSelectionStatus());
            parcel.writeInt(this.getNetworkSelectionDisableReason());
            for (int i = 0; i < 15; ++i) {
                parcel.writeInt(this.getDisableReasonCounter(i));
            }
            parcel.writeLong(this.getDisableTime());
            parcel.writeString(this.getNetworkSelectionBSSID());
            if (this.getConnectChoice() != null) {
                parcel.writeInt(1);
                parcel.writeString(this.getConnectChoice());
                parcel.writeLong(this.getConnectChoiceTimestamp());
            } else {
                parcel.writeInt(-1);
            }
            parcel.writeInt((int)this.getHasEverConnected());
            parcel.writeInt((int)this.isNotRecommended());
        }
    }

    public static class PairwiseCipher {
        public static final int CCMP = 2;
        public static final int GCMP_256 = 3;
        public static final int NONE = 0;
        @Deprecated
        public static final int TKIP = 1;
        public static final String[] strings = new String[]{"NONE", "TKIP", "CCMP", "GCMP_256"};
        public static final String varName = "pairwise";

        private PairwiseCipher() {
        }
    }

    public static class Protocol {
        public static final int OSEN = 2;
        public static final int RSN = 1;
        @Deprecated
        public static final int WPA = 0;
        public static final String[] strings = new String[]{"WPA", "RSN", "OSEN"};
        public static final String varName = "proto";

        private Protocol() {
        }
    }

    public static class RecentFailure {
        public static final int NONE = 0;
        public static final int STATUS_AP_UNABLE_TO_HANDLE_NEW_STA = 17;
        private int mAssociationStatus = 0;

        public void clear() {
            this.mAssociationStatus = 0;
        }

        public int getAssociationStatus() {
            return this.mAssociationStatus;
        }

        public void setAssociationStatus(int n) {
            this.mAssociationStatus = n;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SecurityType {
    }

    public static class Status {
        public static final int CURRENT = 0;
        public static final int DISABLED = 1;
        public static final int ENABLED = 2;
        public static final String[] strings = new String[]{"current", "disabled", "enabled"};

        private Status() {
        }
    }

    public static class SuiteBCipher {
        public static final int ECDHE_ECDSA = 0;
        public static final int ECDHE_RSA = 1;
        private static final String[] strings = new String[]{"ECDHE_ECDSA", "ECDHE_RSA"};
        private static final String varName = "SuiteB";

        private SuiteBCipher() {
        }
    }

}

