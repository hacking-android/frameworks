/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.app.ActivityThread;
import android.content.Context;
import android.net.MacAddress;
import android.net.MatchAllNetworkSpecifier;
import android.net.NetworkSpecifier;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiNetworkAgentSpecifier;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PatternMatcher;
import android.os.Process;
import android.text.TextUtils;
import android.util.Pair;
import com.android.internal.util.Preconditions;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import java.util.Objects;

public final class WifiNetworkSpecifier
extends NetworkSpecifier
implements Parcelable {
    public static final Parcelable.Creator<WifiNetworkSpecifier> CREATOR = new Parcelable.Creator<WifiNetworkSpecifier>(){

        @Override
        public WifiNetworkSpecifier createFromParcel(Parcel parcel) {
            PatternMatcher patternMatcher = (PatternMatcher)parcel.readParcelable(null);
            MacAddress macAddress = (MacAddress)parcel.readParcelable(null);
            MacAddress macAddress2 = (MacAddress)parcel.readParcelable(null);
            return new WifiNetworkSpecifier(patternMatcher, Pair.create(macAddress, macAddress2), (WifiConfiguration)parcel.readParcelable(null), parcel.readInt(), parcel.readString());
        }

        public WifiNetworkSpecifier[] newArray(int n) {
            return new WifiNetworkSpecifier[n];
        }
    };
    public final Pair<MacAddress, MacAddress> bssidPatternMatcher;
    public final String requestorPackageName;
    public final int requestorUid;
    public final PatternMatcher ssidPatternMatcher;
    public final WifiConfiguration wifiConfiguration;

    public WifiNetworkSpecifier() throws IllegalAccessException {
        throw new IllegalAccessException("Use the builder to create an instance");
    }

    public WifiNetworkSpecifier(PatternMatcher patternMatcher, Pair<MacAddress, MacAddress> pair, WifiConfiguration wifiConfiguration, int n, String string2) {
        Preconditions.checkNotNull(patternMatcher);
        Preconditions.checkNotNull(pair);
        Preconditions.checkNotNull(wifiConfiguration);
        Preconditions.checkNotNull(string2);
        this.ssidPatternMatcher = patternMatcher;
        this.bssidPatternMatcher = pair;
        this.wifiConfiguration = wifiConfiguration;
        this.requestorUid = n;
        this.requestorPackageName = string2;
    }

    @Override
    public void assertValidFromUid(int n) {
        if (this.requestorUid == n) {
            return;
        }
        throw new SecurityException("mismatched UIDs");
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
        if (!(object instanceof WifiNetworkSpecifier)) {
            return false;
        }
        object = (WifiNetworkSpecifier)object;
        if (!(Objects.equals(this.ssidPatternMatcher.getPath(), ((WifiNetworkSpecifier)object).ssidPatternMatcher.getPath()) && Objects.equals(this.ssidPatternMatcher.getType(), ((WifiNetworkSpecifier)object).ssidPatternMatcher.getType()) && Objects.equals(this.bssidPatternMatcher, ((WifiNetworkSpecifier)object).bssidPatternMatcher) && Objects.equals(this.wifiConfiguration.allowedKeyManagement, object.wifiConfiguration.allowedKeyManagement) && this.requestorUid == ((WifiNetworkSpecifier)object).requestorUid && TextUtils.equals(this.requestorPackageName, ((WifiNetworkSpecifier)object).requestorPackageName))) {
            bl = false;
        }
        return bl;
    }

    public int hashCode() {
        return Objects.hash(this.ssidPatternMatcher.getPath(), this.ssidPatternMatcher.getType(), this.bssidPatternMatcher, this.wifiConfiguration.allowedKeyManagement, this.requestorUid, this.requestorPackageName);
    }

    @Override
    public boolean satisfiedBy(NetworkSpecifier networkSpecifier) {
        if (this == networkSpecifier) {
            return true;
        }
        if (networkSpecifier != null && !(networkSpecifier instanceof MatchAllNetworkSpecifier)) {
            if (networkSpecifier instanceof WifiNetworkAgentSpecifier) {
                return ((WifiNetworkAgentSpecifier)networkSpecifier).satisfiesNetworkSpecifier(this);
            }
            return this.equals(networkSpecifier);
        }
        return true;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("WifiNetworkSpecifier [");
        stringBuilder.append(", SSID Match pattern=");
        stringBuilder.append(this.ssidPatternMatcher);
        stringBuilder.append(", BSSID Match pattern=");
        stringBuilder.append(this.bssidPatternMatcher);
        stringBuilder.append(", SSID=");
        stringBuilder.append(this.wifiConfiguration.SSID);
        stringBuilder.append(", BSSID=");
        stringBuilder.append(this.wifiConfiguration.BSSID);
        stringBuilder.append(", requestorUid=");
        stringBuilder.append(this.requestorUid);
        stringBuilder.append(", requestorPackageName=");
        stringBuilder.append(this.requestorPackageName);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.ssidPatternMatcher, n);
        parcel.writeParcelable((Parcelable)this.bssidPatternMatcher.first, n);
        parcel.writeParcelable((Parcelable)this.bssidPatternMatcher.second, n);
        parcel.writeParcelable(this.wifiConfiguration, n);
        parcel.writeInt(this.requestorUid);
        parcel.writeString(this.requestorPackageName);
    }

    public static final class Builder {
        private static final Pair<MacAddress, MacAddress> MATCH_ALL_BSSID_PATTERN;
        private static final String MATCH_ALL_SSID_PATTERN_PATH = ".*";
        private static final String MATCH_EMPTY_SSID_PATTERN_PATH = "";
        private static final MacAddress MATCH_EXACT_BSSID_PATTERN_MASK;
        private static final Pair<MacAddress, MacAddress> MATCH_NO_BSSID_PATTERN1;
        private static final Pair<MacAddress, MacAddress> MATCH_NO_BSSID_PATTERN2;
        private Pair<MacAddress, MacAddress> mBssidPatternMatcher = null;
        private boolean mIsEnhancedOpen = false;
        private boolean mIsHiddenSSID = false;
        private PatternMatcher mSsidPatternMatcher = null;
        private WifiEnterpriseConfig mWpa2EnterpriseConfig = null;
        private String mWpa2PskPassphrase = null;
        private WifiEnterpriseConfig mWpa3EnterpriseConfig = null;
        private String mWpa3SaePassphrase = null;

        static {
            MATCH_NO_BSSID_PATTERN1 = new Pair<MacAddress, MacAddress>(MacAddress.BROADCAST_ADDRESS, MacAddress.BROADCAST_ADDRESS);
            MATCH_NO_BSSID_PATTERN2 = new Pair<MacAddress, MacAddress>(MacAddress.ALL_ZEROS_ADDRESS, MacAddress.BROADCAST_ADDRESS);
            MATCH_ALL_BSSID_PATTERN = new Pair<MacAddress, MacAddress>(MacAddress.ALL_ZEROS_ADDRESS, MacAddress.ALL_ZEROS_ADDRESS);
            MATCH_EXACT_BSSID_PATTERN_MASK = MacAddress.BROADCAST_ADDRESS;
        }

        private WifiConfiguration buildWifiConfiguration() {
            WifiConfiguration wifiConfiguration = new WifiConfiguration();
            if (this.mSsidPatternMatcher.getType() == 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("\"");
                stringBuilder.append(this.mSsidPatternMatcher.getPath());
                stringBuilder.append("\"");
                wifiConfiguration.SSID = stringBuilder.toString();
            }
            if (this.mBssidPatternMatcher.second == MATCH_EXACT_BSSID_PATTERN_MASK) {
                wifiConfiguration.BSSID = ((MacAddress)this.mBssidPatternMatcher.first).toString();
            }
            this.setSecurityParamsInWifiConfiguration(wifiConfiguration);
            wifiConfiguration.hiddenSSID = this.mIsHiddenSSID;
            return wifiConfiguration;
        }

        private boolean hasSetAnyPattern() {
            boolean bl = this.mSsidPatternMatcher != null || this.mBssidPatternMatcher != null;
            return bl;
        }

        private boolean hasSetMatchAllPattern() {
            return this.mSsidPatternMatcher.match(MATCH_EMPTY_SSID_PATTERN_PATH) && this.mBssidPatternMatcher.equals(MATCH_ALL_BSSID_PATTERN);
        }

        private boolean hasSetMatchNonePattern() {
            if (this.mSsidPatternMatcher.getType() != 1 && this.mSsidPatternMatcher.getPath().equals(MATCH_EMPTY_SSID_PATTERN_PATH)) {
                return true;
            }
            if (this.mBssidPatternMatcher.equals(MATCH_NO_BSSID_PATTERN1)) {
                return true;
            }
            return this.mBssidPatternMatcher.equals(MATCH_NO_BSSID_PATTERN2);
        }

        private void setMatchAnyPatternIfUnset() {
            if (this.mSsidPatternMatcher == null) {
                this.mSsidPatternMatcher = new PatternMatcher(MATCH_ALL_SSID_PATTERN_PATH, 2);
            }
            if (this.mBssidPatternMatcher == null) {
                this.mBssidPatternMatcher = MATCH_ALL_BSSID_PATTERN;
            }
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

        public WifiNetworkSpecifier build() {
            if (this.hasSetAnyPattern()) {
                this.setMatchAnyPatternIfUnset();
                if (!this.hasSetMatchNonePattern()) {
                    if (!this.hasSetMatchAllPattern()) {
                        if (this.mIsHiddenSSID && this.mSsidPatternMatcher.getType() != 0) {
                            throw new IllegalStateException("setSsid should also be invoked when setIsHiddenSsid is invoked for network specifier");
                        }
                        this.validateSecurityParams();
                        return new WifiNetworkSpecifier(this.mSsidPatternMatcher, this.mBssidPatternMatcher, this.buildWifiConfiguration(), Process.myUid(), ActivityThread.currentApplication().getApplicationContext().getOpPackageName());
                    }
                    throw new IllegalStateException("cannot set match-all pattern for specifier");
                }
                throw new IllegalStateException("cannot set match-none pattern for specifier");
            }
            throw new IllegalStateException("one of setSsidPattern/setSsid/setBssidPattern/setBssid should be invoked for specifier");
        }

        public Builder setBssid(MacAddress macAddress) {
            Preconditions.checkNotNull(macAddress);
            this.mBssidPatternMatcher = Pair.create(macAddress, MATCH_EXACT_BSSID_PATTERN_MASK);
            return this;
        }

        public Builder setBssidPattern(MacAddress macAddress, MacAddress macAddress2) {
            Preconditions.checkNotNull(macAddress, macAddress2);
            this.mBssidPatternMatcher = Pair.create(macAddress, macAddress2);
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

        public Builder setSsid(String string2) {
            Preconditions.checkNotNull(string2);
            if (StandardCharsets.UTF_8.newEncoder().canEncode(string2)) {
                this.mSsidPatternMatcher = new PatternMatcher(string2, 0);
                return this;
            }
            throw new IllegalArgumentException("SSID is not a valid unicode string");
        }

        public Builder setSsidPattern(PatternMatcher patternMatcher) {
            Preconditions.checkNotNull(patternMatcher);
            this.mSsidPatternMatcher = patternMatcher;
            return this;
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

