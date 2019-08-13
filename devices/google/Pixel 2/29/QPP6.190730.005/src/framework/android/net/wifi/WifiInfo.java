/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.net.NetworkInfo;
import android.net.NetworkUtils;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiSsid;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.EnumMap;
import java.util.Locale;

public class WifiInfo
implements Parcelable {
    @UnsupportedAppUsage
    public static final Parcelable.Creator<WifiInfo> CREATOR;
    @UnsupportedAppUsage
    public static final String DEFAULT_MAC_ADDRESS = "02:00:00:00:00:00";
    public static final String FREQUENCY_UNITS = "MHz";
    @UnsupportedAppUsage
    public static final int INVALID_RSSI = -127;
    public static final String LINK_SPEED_UNITS = "Mbps";
    public static final int LINK_SPEED_UNKNOWN = -1;
    public static final int MAX_RSSI = 200;
    public static final int MIN_RSSI = -126;
    private static final String TAG = "WifiInfo";
    private static final EnumMap<SupplicantState, NetworkInfo.DetailedState> stateMap;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private String mBSSID;
    private boolean mEphemeral;
    private String mFqdn;
    private int mFrequency;
    @UnsupportedAppUsage
    private InetAddress mIpAddress;
    private int mLinkSpeed;
    @UnsupportedAppUsage
    private String mMacAddress = "02:00:00:00:00:00";
    private boolean mMeteredHint;
    private int mNetworkId;
    private String mNetworkSuggestionOrSpecifierPackageName;
    private boolean mOsuAp;
    private String mProviderFriendlyName;
    private int mRssi;
    private int mRxLinkSpeed;
    private SupplicantState mSupplicantState;
    private boolean mTrusted;
    private int mTxLinkSpeed;
    @UnsupportedAppUsage
    private WifiSsid mWifiSsid;
    public long rxSuccess;
    public double rxSuccessRate;
    @UnsupportedAppUsage
    public int score;
    public long txBad;
    public double txBadRate;
    public long txRetries;
    public double txRetriesRate;
    public long txSuccess;
    public double txSuccessRate;

    static {
        stateMap = new EnumMap(SupplicantState.class);
        stateMap.put(SupplicantState.DISCONNECTED, NetworkInfo.DetailedState.DISCONNECTED);
        stateMap.put(SupplicantState.INTERFACE_DISABLED, NetworkInfo.DetailedState.DISCONNECTED);
        stateMap.put(SupplicantState.INACTIVE, NetworkInfo.DetailedState.IDLE);
        stateMap.put(SupplicantState.SCANNING, NetworkInfo.DetailedState.SCANNING);
        stateMap.put(SupplicantState.AUTHENTICATING, NetworkInfo.DetailedState.CONNECTING);
        stateMap.put(SupplicantState.ASSOCIATING, NetworkInfo.DetailedState.CONNECTING);
        stateMap.put(SupplicantState.ASSOCIATED, NetworkInfo.DetailedState.CONNECTING);
        stateMap.put(SupplicantState.FOUR_WAY_HANDSHAKE, NetworkInfo.DetailedState.AUTHENTICATING);
        stateMap.put(SupplicantState.GROUP_HANDSHAKE, NetworkInfo.DetailedState.AUTHENTICATING);
        stateMap.put(SupplicantState.COMPLETED, NetworkInfo.DetailedState.OBTAINING_IPADDR);
        stateMap.put(SupplicantState.DORMANT, NetworkInfo.DetailedState.DISCONNECTED);
        stateMap.put(SupplicantState.UNINITIALIZED, NetworkInfo.DetailedState.IDLE);
        stateMap.put(SupplicantState.INVALID, NetworkInfo.DetailedState.FAILED);
        CREATOR = new Parcelable.Creator<WifiInfo>(){

            @Override
            public WifiInfo createFromParcel(Parcel parcel) {
                WifiInfo wifiInfo = new WifiInfo();
                wifiInfo.setNetworkId(parcel.readInt());
                wifiInfo.setRssi(parcel.readInt());
                wifiInfo.setLinkSpeed(parcel.readInt());
                wifiInfo.setTxLinkSpeedMbps(parcel.readInt());
                wifiInfo.setRxLinkSpeedMbps(parcel.readInt());
                wifiInfo.setFrequency(parcel.readInt());
                byte by = parcel.readByte();
                boolean bl = true;
                if (by == 1) {
                    try {
                        wifiInfo.setInetAddress(InetAddress.getByAddress(parcel.createByteArray()));
                    }
                    catch (UnknownHostException unknownHostException) {
                        // empty catch block
                    }
                }
                if (parcel.readInt() == 1) {
                    wifiInfo.mWifiSsid = WifiSsid.CREATOR.createFromParcel(parcel);
                }
                wifiInfo.mBSSID = parcel.readString();
                wifiInfo.mMacAddress = parcel.readString();
                boolean bl2 = parcel.readInt() != 0;
                wifiInfo.mMeteredHint = bl2;
                bl2 = parcel.readInt() != 0;
                wifiInfo.mEphemeral = bl2;
                bl2 = parcel.readInt() != 0;
                wifiInfo.mTrusted = bl2;
                wifiInfo.score = parcel.readInt();
                wifiInfo.txSuccess = parcel.readLong();
                wifiInfo.txSuccessRate = parcel.readDouble();
                wifiInfo.txRetries = parcel.readLong();
                wifiInfo.txRetriesRate = parcel.readDouble();
                wifiInfo.txBad = parcel.readLong();
                wifiInfo.txBadRate = parcel.readDouble();
                wifiInfo.rxSuccess = parcel.readLong();
                wifiInfo.rxSuccessRate = parcel.readDouble();
                wifiInfo.mSupplicantState = SupplicantState.CREATOR.createFromParcel(parcel);
                bl2 = parcel.readInt() != 0 ? bl : false;
                wifiInfo.mOsuAp = bl2;
                wifiInfo.mNetworkSuggestionOrSpecifierPackageName = parcel.readString();
                wifiInfo.mFqdn = parcel.readString();
                wifiInfo.mProviderFriendlyName = parcel.readString();
                return wifiInfo;
            }

            public WifiInfo[] newArray(int n) {
                return new WifiInfo[n];
            }
        };
    }

    @UnsupportedAppUsage
    public WifiInfo() {
        this.mWifiSsid = null;
        this.mBSSID = null;
        this.mNetworkId = -1;
        this.mSupplicantState = SupplicantState.UNINITIALIZED;
        this.mRssi = -127;
        this.mLinkSpeed = -1;
        this.mFrequency = -1;
    }

    public WifiInfo(WifiInfo wifiInfo) {
        if (wifiInfo != null) {
            this.mSupplicantState = wifiInfo.mSupplicantState;
            this.mBSSID = wifiInfo.mBSSID;
            this.mWifiSsid = wifiInfo.mWifiSsid;
            this.mNetworkId = wifiInfo.mNetworkId;
            this.mRssi = wifiInfo.mRssi;
            this.mLinkSpeed = wifiInfo.mLinkSpeed;
            this.mTxLinkSpeed = wifiInfo.mTxLinkSpeed;
            this.mRxLinkSpeed = wifiInfo.mRxLinkSpeed;
            this.mFrequency = wifiInfo.mFrequency;
            this.mIpAddress = wifiInfo.mIpAddress;
            this.mMacAddress = wifiInfo.mMacAddress;
            this.mMeteredHint = wifiInfo.mMeteredHint;
            this.mEphemeral = wifiInfo.mEphemeral;
            this.mTrusted = wifiInfo.mTrusted;
            this.mNetworkSuggestionOrSpecifierPackageName = wifiInfo.mNetworkSuggestionOrSpecifierPackageName;
            this.mOsuAp = wifiInfo.mOsuAp;
            this.mFqdn = wifiInfo.mFqdn;
            this.mProviderFriendlyName = wifiInfo.mProviderFriendlyName;
            this.txBad = wifiInfo.txBad;
            this.txRetries = wifiInfo.txRetries;
            this.txSuccess = wifiInfo.txSuccess;
            this.rxSuccess = wifiInfo.rxSuccess;
            this.txBadRate = wifiInfo.txBadRate;
            this.txRetriesRate = wifiInfo.txRetriesRate;
            this.txSuccessRate = wifiInfo.txSuccessRate;
            this.rxSuccessRate = wifiInfo.rxSuccessRate;
            this.score = wifiInfo.score;
        }
    }

    public static NetworkInfo.DetailedState getDetailedStateOf(SupplicantState supplicantState) {
        return stateMap.get(supplicantState);
    }

    @UnsupportedAppUsage
    public static String removeDoubleQuotes(String string2) {
        if (string2 == null) {
            return null;
        }
        int n = string2.length();
        if (n > 1 && string2.charAt(0) == '\"' && string2.charAt(n - 1) == '\"') {
            return string2.substring(1, n - 1);
        }
        return string2;
    }

    static SupplicantState valueOf(String object) {
        if ("4WAY_HANDSHAKE".equalsIgnoreCase((String)object)) {
            return SupplicantState.FOUR_WAY_HANDSHAKE;
        }
        try {
            object = SupplicantState.valueOf(((String)object).toUpperCase(Locale.ROOT));
            return object;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return SupplicantState.INVALID;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getBSSID() {
        return this.mBSSID;
    }

    public int getFrequency() {
        return this.mFrequency;
    }

    public boolean getHiddenSSID() {
        WifiSsid wifiSsid = this.mWifiSsid;
        if (wifiSsid == null) {
            return false;
        }
        return wifiSsid.isHidden();
    }

    public int getIpAddress() {
        int n = 0;
        InetAddress inetAddress = this.mIpAddress;
        if (inetAddress instanceof Inet4Address) {
            n = NetworkUtils.inetAddressToInt((Inet4Address)inetAddress);
        }
        return n;
    }

    public int getLinkSpeed() {
        return this.mLinkSpeed;
    }

    public String getMacAddress() {
        return this.mMacAddress;
    }

    @UnsupportedAppUsage
    public boolean getMeteredHint() {
        return this.mMeteredHint;
    }

    public int getNetworkId() {
        return this.mNetworkId;
    }

    public String getNetworkSuggestionOrSpecifierPackageName() {
        return this.mNetworkSuggestionOrSpecifierPackageName;
    }

    public String getPasspointFqdn() {
        return this.mFqdn;
    }

    public String getPasspointProviderFriendlyName() {
        return this.mProviderFriendlyName;
    }

    public int getRssi() {
        return this.mRssi;
    }

    public int getRxLinkSpeedMbps() {
        return this.mRxLinkSpeed;
    }

    public String getSSID() {
        Object object = this.mWifiSsid;
        Object object2 = "<unknown ssid>";
        if (object != null) {
            if (!TextUtils.isEmpty((CharSequence)(object = ((WifiSsid)object).toString()))) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("\"");
                ((StringBuilder)object2).append((String)object);
                ((StringBuilder)object2).append("\"");
                return ((StringBuilder)object2).toString();
            }
            object = this.mWifiSsid.getHexString();
            if (object != null) {
                object2 = object;
            }
            return object2;
        }
        return "<unknown ssid>";
    }

    public SupplicantState getSupplicantState() {
        return this.mSupplicantState;
    }

    public int getTxLinkSpeedMbps() {
        return this.mTxLinkSpeed;
    }

    @UnsupportedAppUsage
    public WifiSsid getWifiSsid() {
        return this.mWifiSsid;
    }

    public boolean hasRealMacAddress() {
        String string2 = this.mMacAddress;
        boolean bl = string2 != null && !DEFAULT_MAC_ADDRESS.equals(string2);
        return bl;
    }

    public boolean is24GHz() {
        return ScanResult.is24GHz(this.mFrequency);
    }

    @UnsupportedAppUsage
    public boolean is5GHz() {
        return ScanResult.is5GHz(this.mFrequency);
    }

    @UnsupportedAppUsage
    public boolean isEphemeral() {
        return this.mEphemeral;
    }

    @SystemApi
    public boolean isOsuAp() {
        return this.mOsuAp;
    }

    @SystemApi
    public boolean isPasspointAp() {
        boolean bl = this.mFqdn != null && this.mProviderFriendlyName != null;
        return bl;
    }

    public boolean isTrusted() {
        return this.mTrusted;
    }

    public void reset() {
        this.setInetAddress(null);
        this.setBSSID(null);
        this.setSSID(null);
        this.setNetworkId(-1);
        this.setRssi(-127);
        this.setLinkSpeed(-1);
        this.setTxLinkSpeedMbps(-1);
        this.setRxLinkSpeedMbps(-1);
        this.setFrequency(-1);
        this.setMeteredHint(false);
        this.setEphemeral(false);
        this.setOsuAp(false);
        this.setNetworkSuggestionOrSpecifierPackageName(null);
        this.setFQDN(null);
        this.setProviderFriendlyName(null);
        this.txBad = 0L;
        this.txSuccess = 0L;
        this.rxSuccess = 0L;
        this.txRetries = 0L;
        this.txBadRate = 0.0;
        this.txSuccessRate = 0.0;
        this.rxSuccessRate = 0.0;
        this.txRetriesRate = 0.0;
        this.score = 0;
    }

    @UnsupportedAppUsage
    public void setBSSID(String string2) {
        this.mBSSID = string2;
    }

    public void setEphemeral(boolean bl) {
        this.mEphemeral = bl;
    }

    public void setFQDN(String string2) {
        this.mFqdn = string2;
    }

    public void setFrequency(int n) {
        this.mFrequency = n;
    }

    public void setInetAddress(InetAddress inetAddress) {
        this.mIpAddress = inetAddress;
    }

    @UnsupportedAppUsage
    public void setLinkSpeed(int n) {
        this.mLinkSpeed = n;
    }

    @UnsupportedAppUsage
    public void setMacAddress(String string2) {
        this.mMacAddress = string2;
    }

    public void setMeteredHint(boolean bl) {
        this.mMeteredHint = bl;
    }

    @UnsupportedAppUsage
    public void setNetworkId(int n) {
        this.mNetworkId = n;
    }

    public void setNetworkSuggestionOrSpecifierPackageName(String string2) {
        this.mNetworkSuggestionOrSpecifierPackageName = string2;
    }

    public void setOsuAp(boolean bl) {
        this.mOsuAp = bl;
    }

    public void setProviderFriendlyName(String string2) {
        this.mProviderFriendlyName = string2;
    }

    @UnsupportedAppUsage
    public void setRssi(int n) {
        int n2 = n;
        if (n < -127) {
            n2 = -127;
        }
        n = n2;
        if (n2 > 200) {
            n = 200;
        }
        this.mRssi = n;
    }

    public void setRxLinkSpeedMbps(int n) {
        this.mRxLinkSpeed = n;
    }

    public void setSSID(WifiSsid wifiSsid) {
        this.mWifiSsid = wifiSsid;
    }

    @UnsupportedAppUsage
    public void setSupplicantState(SupplicantState supplicantState) {
        this.mSupplicantState = supplicantState;
    }

    @UnsupportedAppUsage
    void setSupplicantState(String string2) {
        this.mSupplicantState = WifiInfo.valueOf(string2);
    }

    public void setTrusted(boolean bl) {
        this.mTrusted = bl;
    }

    public void setTxLinkSpeedMbps(int n) {
        this.mTxLinkSpeed = n;
    }

    public String toString() {
        Object object;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("SSID: ");
        Object object2 = object = this.mWifiSsid;
        if (object == null) {
            object2 = "<unknown ssid>";
        }
        stringBuffer.append(object2);
        stringBuffer.append(", BSSID: ");
        object2 = object = this.mBSSID;
        if (object == null) {
            object2 = "<none>";
        }
        stringBuffer.append((String)object2);
        stringBuffer.append(", MAC: ");
        object2 = object = this.mMacAddress;
        if (object == null) {
            object2 = "<none>";
        }
        stringBuffer.append((String)object2);
        stringBuffer.append(", Supplicant state: ");
        object2 = object = this.mSupplicantState;
        if (object == null) {
            object2 = "<none>";
        }
        stringBuffer.append(object2);
        stringBuffer.append(", RSSI: ");
        stringBuffer.append(this.mRssi);
        stringBuffer.append(", Link speed: ");
        stringBuffer.append(this.mLinkSpeed);
        stringBuffer.append(LINK_SPEED_UNITS);
        stringBuffer.append(", Tx Link speed: ");
        stringBuffer.append(this.mTxLinkSpeed);
        stringBuffer.append(LINK_SPEED_UNITS);
        stringBuffer.append(", Rx Link speed: ");
        stringBuffer.append(this.mRxLinkSpeed);
        stringBuffer.append(LINK_SPEED_UNITS);
        stringBuffer.append(", Frequency: ");
        stringBuffer.append(this.mFrequency);
        stringBuffer.append(FREQUENCY_UNITS);
        stringBuffer.append(", Net ID: ");
        stringBuffer.append(this.mNetworkId);
        stringBuffer.append(", Metered hint: ");
        stringBuffer.append(this.mMeteredHint);
        stringBuffer.append(", score: ");
        stringBuffer.append(Integer.toString(this.score));
        return stringBuffer.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mNetworkId);
        parcel.writeInt(this.mRssi);
        parcel.writeInt(this.mLinkSpeed);
        parcel.writeInt(this.mTxLinkSpeed);
        parcel.writeInt(this.mRxLinkSpeed);
        parcel.writeInt(this.mFrequency);
        if (this.mIpAddress != null) {
            parcel.writeByte((byte)1);
            parcel.writeByteArray(this.mIpAddress.getAddress());
        } else {
            parcel.writeByte((byte)0);
        }
        if (this.mWifiSsid != null) {
            parcel.writeInt(1);
            this.mWifiSsid.writeToParcel(parcel, n);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeString(this.mBSSID);
        parcel.writeString(this.mMacAddress);
        parcel.writeInt((int)this.mMeteredHint);
        parcel.writeInt((int)this.mEphemeral);
        parcel.writeInt((int)this.mTrusted);
        parcel.writeInt(this.score);
        parcel.writeLong(this.txSuccess);
        parcel.writeDouble(this.txSuccessRate);
        parcel.writeLong(this.txRetries);
        parcel.writeDouble(this.txRetriesRate);
        parcel.writeLong(this.txBad);
        parcel.writeDouble(this.txBadRate);
        parcel.writeLong(this.rxSuccess);
        parcel.writeDouble(this.rxSuccessRate);
        this.mSupplicantState.writeToParcel(parcel, n);
        parcel.writeInt((int)this.mOsuAp);
        parcel.writeString(this.mNetworkSuggestionOrSpecifierPackageName);
        parcel.writeString(this.mFqdn);
        parcel.writeString(this.mProviderFriendlyName);
    }

}

