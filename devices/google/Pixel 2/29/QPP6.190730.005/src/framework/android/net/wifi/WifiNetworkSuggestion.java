/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.app.ActivityThread;
import android.content.Context;
import android.net.MacAddress;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.text.TextUtils;
import com.android.internal.util.Preconditions;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import java.util.Objects;

public final class WifiNetworkSuggestion
implements Parcelable {
    public static final Parcelable.Creator<WifiNetworkSuggestion> CREATOR = new Parcelable.Creator<WifiNetworkSuggestion>(){

        @Override
        public WifiNetworkSuggestion createFromParcel(Parcel parcel) {
            return new WifiNetworkSuggestion((WifiConfiguration)parcel.readParcelable(null), parcel.readBoolean(), parcel.readBoolean(), parcel.readInt(), parcel.readString());
        }

        public WifiNetworkSuggestion[] newArray(int n) {
            return new WifiNetworkSuggestion[n];
        }
    };
    public final boolean isAppInteractionRequired;
    public final boolean isUserInteractionRequired;
    public final String suggestorPackageName;
    public final int suggestorUid;
    public final WifiConfiguration wifiConfiguration;

    public WifiNetworkSuggestion() {
        this.wifiConfiguration = null;
        this.isAppInteractionRequired = false;
        this.isUserInteractionRequired = false;
        this.suggestorUid = -1;
        this.suggestorPackageName = null;
    }

    public WifiNetworkSuggestion(WifiConfiguration wifiConfiguration, boolean bl, boolean bl2, int n, String string2) {
        Preconditions.checkNotNull(wifiConfiguration);
        Preconditions.checkNotNull(string2);
        this.wifiConfiguration = wifiConfiguration;
        this.isAppInteractionRequired = bl;
        this.isUserInteractionRequired = bl2;
        this.suggestorUid = n;
        this.suggestorPackageName = string2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof WifiNetworkSuggestion)) {
            return false;
        }
        object = (WifiNetworkSuggestion)object;
        if (!(Objects.equals(this.wifiConfiguration.SSID, object.wifiConfiguration.SSID) && Objects.equals(this.wifiConfiguration.BSSID, object.wifiConfiguration.BSSID) && Objects.equals(this.wifiConfiguration.allowedKeyManagement, object.wifiConfiguration.allowedKeyManagement) && this.suggestorUid == ((WifiNetworkSuggestion)object).suggestorUid && TextUtils.equals(this.suggestorPackageName, ((WifiNetworkSuggestion)object).suggestorPackageName))) {
            bl = false;
        }
        return bl;
    }

    public int hashCode() {
        return Objects.hash(this.wifiConfiguration.SSID, this.wifiConfiguration.BSSID, this.wifiConfiguration.allowedKeyManagement, this.suggestorUid, this.suggestorPackageName);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("WifiNetworkSuggestion [");
        stringBuilder.append(", SSID=");
        stringBuilder.append(this.wifiConfiguration.SSID);
        stringBuilder.append(", BSSID=");
        stringBuilder.append(this.wifiConfiguration.BSSID);
        stringBuilder.append(", isAppInteractionRequired=");
        stringBuilder.append(this.isAppInteractionRequired);
        stringBuilder.append(", isUserInteractionRequired=");
        stringBuilder.append(this.isUserInteractionRequired);
        stringBuilder.append(", suggestorUid=");
        stringBuilder.append(this.suggestorUid);
        stringBuilder.append(", suggestorPackageName=");
        stringBuilder.append(this.suggestorPackageName);
        return stringBuilder.append("]").toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.wifiConfiguration, n);
        parcel.writeBoolean(this.isAppInteractionRequired);
        parcel.writeBoolean(this.isUserInteractionRequired);
        parcel.writeInt(this.suggestorUid);
        parcel.writeString(this.suggestorPackageName);
    }

    public static final class Builder {
        private static final int UNASSIGNED_PRIORITY = -1;
        private MacAddress mBssid = null;
        private boolean mIsAppInteractionRequired = false;
        private boolean mIsEnhancedOpen = false;
        private boolean mIsHiddenSSID = false;
        private boolean mIsMetered = false;
        private boolean mIsUserInteractionRequired = false;
        private int mPriority = -1;
        private String mSsid = null;
        private WifiEnterpriseConfig mWpa2EnterpriseConfig = null;
        private String mWpa2PskPassphrase = null;
        private WifiEnterpriseConfig mWpa3EnterpriseConfig = null;
        private String mWpa3SaePassphrase = null;

        private WifiConfiguration buildWifiConfiguration() {
            WifiConfiguration wifiConfiguration = new WifiConfiguration();
            Object object = new StringBuilder();
            ((StringBuilder)object).append("\"");
            ((StringBuilder)object).append(this.mSsid);
            ((StringBuilder)object).append("\"");
            wifiConfiguration.SSID = ((StringBuilder)object).toString();
            object = this.mBssid;
            if (object != null) {
                wifiConfiguration.BSSID = ((MacAddress)object).toString();
            }
            this.setSecurityParamsInWifiConfiguration(wifiConfiguration);
            wifiConfiguration.hiddenSSID = this.mIsHiddenSSID;
            wifiConfiguration.priority = this.mPriority;
            int n = this.mIsMetered ? 1 : 0;
            wifiConfiguration.meteredOverride = n;
            return wifiConfiguration;
        }

        private void setSecurityParamsInWifiConfiguration(WifiConfiguration wifiConfiguration) {
            if (!TextUtils.isEmpty(this.mWpa2PskPassphrase)) {
                wifiConfiguration.setSecurityParams(2);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("\"");
                stringBuilder.append(this.mWpa2PskPassphrase);
                stringBuilder.append("\"");
                wifiConfiguration.preSharedKey = stringBuilder.toString();
            } else if (!TextUtils.isEmpty(this.mWpa3SaePassphrase)) {
                wifiConfiguration.setSecurityParams(4);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("\"");
                stringBuilder.append(this.mWpa3SaePassphrase);
                stringBuilder.append("\"");
                wifiConfiguration.preSharedKey = stringBuilder.toString();
            } else if (this.mWpa2EnterpriseConfig != null) {
                wifiConfiguration.setSecurityParams(3);
                wifiConfiguration.enterpriseConfig = this.mWpa2EnterpriseConfig;
            } else if (this.mWpa3EnterpriseConfig != null) {
                wifiConfiguration.setSecurityParams(5);
                wifiConfiguration.enterpriseConfig = this.mWpa3EnterpriseConfig;
            } else if (this.mIsEnhancedOpen) {
                wifiConfiguration.setSecurityParams(6);
            } else {
                wifiConfiguration.setSecurityParams(0);
            }
        }

        private void validateSecurityParams() {
            int n = this.mIsEnhancedOpen;
            boolean bl = TextUtils.isEmpty(this.mWpa2PskPassphrase);
            boolean bl2 = TextUtils.isEmpty(this.mWpa3SaePassphrase);
            WifiEnterpriseConfig wifiEnterpriseConfig = this.mWpa2EnterpriseConfig;
            int n2 = 0;
            int n3 = wifiEnterpriseConfig != null ? 1 : 0;
            if (this.mWpa3EnterpriseConfig != null) {
                n2 = 1;
            }
            if (0 + n + (bl ^ true) + (bl2 ^ true) + n3 + n2 <= 1) {
                return;
            }
            throw new IllegalStateException("only one of setIsEnhancedOpen, setWpa2Passphrase,setWpa3Passphrase, setWpa2EnterpriseConfig or setWpa3EnterpriseConfig can be invoked for network specifier");
        }

        public WifiNetworkSuggestion build() {
            Object object = this.mSsid;
            if (object != null) {
                if (!TextUtils.isEmpty((CharSequence)object)) {
                    object = this.mBssid;
                    if (object != null && (((MacAddress)object).equals(MacAddress.BROADCAST_ADDRESS) || this.mBssid.equals(MacAddress.ALL_ZEROS_ADDRESS))) {
                        throw new IllegalStateException("invalid bssid for suggestion");
                    }
                    this.validateSecurityParams();
                    return new WifiNetworkSuggestion(this.buildWifiConfiguration(), this.mIsAppInteractionRequired, this.mIsUserInteractionRequired, Process.myUid(), ActivityThread.currentApplication().getApplicationContext().getOpPackageName());
                }
                throw new IllegalStateException("invalid ssid for suggestion");
            }
            throw new IllegalStateException("setSsid should be invoked for suggestion");
        }

        public Builder setBssid(MacAddress macAddress) {
            Preconditions.checkNotNull(macAddress);
            this.mBssid = MacAddress.fromBytes(macAddress.toByteArray());
            return this;
        }

        public Builder setIsAppInteractionRequired(boolean bl) {
            this.mIsAppInteractionRequired = bl;
            return this;
        }

        public Builder setIsEnhancedOpen(boolean bl) {
            this.mIsEnhancedOpen = bl;
            return this;
        }

        public Builder setIsHiddenSsid(boolean bl) {
            this.mIsHiddenSSID = bl;
            return this;
        }

        public Builder setIsMetered(boolean bl) {
            this.mIsMetered = bl;
            return this;
        }

        public Builder setIsUserInteractionRequired(boolean bl) {
            this.mIsUserInteractionRequired = bl;
            return this;
        }

        public Builder setPriority(int n) {
            if (n >= 0) {
                this.mPriority = n;
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid priority value ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public Builder setSsid(String string2) {
            Preconditions.checkNotNull(string2);
            if (StandardCharsets.UTF_8.newEncoder().canEncode(string2)) {
                this.mSsid = new String(string2);
                return this;
            }
            throw new IllegalArgumentException("SSID is not a valid unicode string");
        }

        public Builder setWpa2EnterpriseConfig(WifiEnterpriseConfig wifiEnterpriseConfig) {
            Preconditions.checkNotNull(wifiEnterpriseConfig);
            this.mWpa2EnterpriseConfig = new WifiEnterpriseConfig(wifiEnterpriseConfig);
            return this;
        }

        public Builder setWpa2Passphrase(String string2) {
            Preconditions.checkNotNull(string2);
            if (StandardCharsets.US_ASCII.newEncoder().canEncode(string2)) {
                this.mWpa2PskPassphrase = string2;
                return this;
            }
            throw new IllegalArgumentException("passphrase not ASCII encodable");
        }

        public Builder setWpa3EnterpriseConfig(WifiEnterpriseConfig wifiEnterpriseConfig) {
            Preconditions.checkNotNull(wifiEnterpriseConfig);
            this.mWpa3EnterpriseConfig = new WifiEnterpriseConfig(wifiEnterpriseConfig);
            return this;
        }

        public Builder setWpa3Passphrase(String string2) {
            Preconditions.checkNotNull(string2);
            if (StandardCharsets.US_ASCII.newEncoder().canEncode(string2)) {
                this.mWpa3SaePassphrase = string2;
                return this;
            }
            throw new IllegalArgumentException("passphrase not ASCII encodable");
        }
    }

}

