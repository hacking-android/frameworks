/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.p2p;

import android.annotation.UnsupportedAppUsage;
import android.net.MacAddress;
import android.net.wifi.WpsInfo;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.regex.PatternSyntaxException;

public class WifiP2pConfig
implements Parcelable {
    public static final Parcelable.Creator<WifiP2pConfig> CREATOR = new Parcelable.Creator<WifiP2pConfig>(){

        @Override
        public WifiP2pConfig createFromParcel(Parcel parcel) {
            WifiP2pConfig wifiP2pConfig = new WifiP2pConfig();
            wifiP2pConfig.deviceAddress = parcel.readString();
            wifiP2pConfig.wps = (WpsInfo)parcel.readParcelable(null);
            wifiP2pConfig.groupOwnerIntent = parcel.readInt();
            wifiP2pConfig.netId = parcel.readInt();
            wifiP2pConfig.networkName = parcel.readString();
            wifiP2pConfig.passphrase = parcel.readString();
            wifiP2pConfig.groupOwnerBand = parcel.readInt();
            return wifiP2pConfig;
        }

        public WifiP2pConfig[] newArray(int n) {
            return new WifiP2pConfig[n];
        }
    };
    public static final int GROUP_OWNER_BAND_2GHZ = 1;
    public static final int GROUP_OWNER_BAND_5GHZ = 2;
    public static final int GROUP_OWNER_BAND_AUTO = 0;
    public static final int MAX_GROUP_OWNER_INTENT = 15;
    @UnsupportedAppUsage
    public static final int MIN_GROUP_OWNER_INTENT = 0;
    public String deviceAddress = "";
    public int groupOwnerBand = 0;
    public int groupOwnerIntent = -1;
    @UnsupportedAppUsage
    public int netId = -2;
    public String networkName = "";
    public String passphrase = "";
    public WpsInfo wps;

    public WifiP2pConfig() {
        this.wps = new WpsInfo();
        this.wps.setup = 0;
    }

    public WifiP2pConfig(WifiP2pConfig wifiP2pConfig) {
        if (wifiP2pConfig != null) {
            this.deviceAddress = wifiP2pConfig.deviceAddress;
            this.wps = new WpsInfo(wifiP2pConfig.wps);
            this.groupOwnerIntent = wifiP2pConfig.groupOwnerIntent;
            this.netId = wifiP2pConfig.netId;
            this.networkName = wifiP2pConfig.networkName;
            this.passphrase = wifiP2pConfig.passphrase;
            this.groupOwnerBand = wifiP2pConfig.groupOwnerBand;
        }
    }

    @UnsupportedAppUsage
    public WifiP2pConfig(String arrstring) throws IllegalArgumentException {
        arrstring = arrstring.split(" ");
        if (arrstring.length >= 2 && arrstring[0].equals("P2P-GO-NEG-REQUEST")) {
            this.deviceAddress = arrstring[1];
            this.wps = new WpsInfo();
            if (arrstring.length > 2) {
                int n;
                arrstring = arrstring[2].split("=");
                try {
                    n = Integer.parseInt(arrstring[1]);
                }
                catch (NumberFormatException numberFormatException) {
                    n = 0;
                }
                this.wps.setup = n != 1 ? (n != 4 ? (n != 5 ? 0 : 2) : 0) : 1;
            }
            return;
        }
        throw new IllegalArgumentException("Malformed supplicant event");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void invalidate() {
        this.deviceAddress = "";
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("\n address: ");
        stringBuffer.append(this.deviceAddress);
        stringBuffer.append("\n wps: ");
        stringBuffer.append(this.wps);
        stringBuffer.append("\n groupOwnerIntent: ");
        stringBuffer.append(this.groupOwnerIntent);
        stringBuffer.append("\n persist: ");
        stringBuffer.append(this.netId);
        stringBuffer.append("\n networkName: ");
        stringBuffer.append(this.networkName);
        stringBuffer.append("\n passphrase: ");
        String string2 = TextUtils.isEmpty(this.passphrase) ? "<empty>" : "<non-empty>";
        stringBuffer.append(string2);
        stringBuffer.append("\n groupOwnerBand: ");
        stringBuffer.append(this.groupOwnerBand);
        return stringBuffer.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.deviceAddress);
        parcel.writeParcelable(this.wps, n);
        parcel.writeInt(this.groupOwnerIntent);
        parcel.writeInt(this.netId);
        parcel.writeString(this.networkName);
        parcel.writeString(this.passphrase);
        parcel.writeInt(this.groupOwnerBand);
    }

    public static final class Builder {
        private static final MacAddress MAC_ANY_ADDRESS = MacAddress.fromString("02:00:00:00:00:00");
        private MacAddress mDeviceAddress = MAC_ANY_ADDRESS;
        private int mGroupOperatingBand = 0;
        private int mGroupOperatingFrequency = 0;
        private int mNetId = -1;
        private String mNetworkName = "";
        private String mPassphrase = "";

        public WifiP2pConfig build() {
            if (!TextUtils.isEmpty(this.mNetworkName)) {
                if (!TextUtils.isEmpty(this.mPassphrase)) {
                    if (this.mGroupOperatingFrequency > 0 && this.mGroupOperatingBand > 0) {
                        throw new IllegalStateException("Preferred frequency and band are mutually exclusive.");
                    }
                    WifiP2pConfig wifiP2pConfig = new WifiP2pConfig();
                    wifiP2pConfig.deviceAddress = this.mDeviceAddress.toString();
                    wifiP2pConfig.networkName = this.mNetworkName;
                    wifiP2pConfig.passphrase = this.mPassphrase;
                    wifiP2pConfig.groupOwnerBand = 0;
                    int n = this.mGroupOperatingFrequency;
                    if (n > 0) {
                        wifiP2pConfig.groupOwnerBand = n;
                    } else {
                        n = this.mGroupOperatingBand;
                        if (n > 0) {
                            wifiP2pConfig.groupOwnerBand = n;
                        }
                    }
                    wifiP2pConfig.netId = this.mNetId;
                    return wifiP2pConfig;
                }
                throw new IllegalStateException("passphrase must be non-empty.");
            }
            throw new IllegalStateException("network name must be non-empty.");
        }

        public Builder enablePersistentMode(boolean bl) {
            this.mNetId = bl ? -2 : -1;
            return this;
        }

        public Builder setDeviceAddress(MacAddress macAddress) {
            this.mDeviceAddress = macAddress == null ? MAC_ANY_ADDRESS : macAddress;
            return this;
        }

        public Builder setGroupOperatingBand(int n) {
            if (n != 0 && n != 1 && n != 2) {
                throw new IllegalArgumentException("Invalid constant for the group operating band!");
            }
            this.mGroupOperatingBand = n;
            return this;
        }

        public Builder setGroupOperatingFrequency(int n) {
            if (n >= 0) {
                this.mGroupOperatingFrequency = n;
                return this;
            }
            throw new IllegalArgumentException("Invalid group operating frequency!");
        }

        public Builder setNetworkName(String string2) {
            if (!TextUtils.isEmpty(string2)) {
                try {
                    if (!string2.matches("^DIRECT-[a-zA-Z0-9]{2}.*")) {
                        IllegalArgumentException illegalArgumentException = new IllegalArgumentException("network name must starts with the prefix DIRECT-xy.");
                        throw illegalArgumentException;
                    }
                }
                catch (PatternSyntaxException patternSyntaxException) {
                    // empty catch block
                }
                this.mNetworkName = string2;
                return this;
            }
            throw new IllegalArgumentException("network name must be non-empty.");
        }

        public Builder setPassphrase(String string2) {
            if (!TextUtils.isEmpty(string2)) {
                if (string2.length() >= 8 && string2.length() <= 63) {
                    this.mPassphrase = string2;
                    return this;
                }
                throw new IllegalArgumentException("The length of a passphrase must be between 8 and 63.");
            }
            throw new IllegalArgumentException("passphrase must be non-empty.");
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface GroupOperatingBandType {
    }

}

