/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.net.MacAddress;
import android.net.MatchAllNetworkSpecifier;
import android.net.NetworkSpecifier;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiNetworkSpecifier;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PatternMatcher;
import android.text.TextUtils;
import android.util.Pair;
import com.android.internal.util.Preconditions;
import java.util.BitSet;
import java.util.Objects;

public final class WifiNetworkAgentSpecifier
extends NetworkSpecifier
implements Parcelable {
    public static final Parcelable.Creator<WifiNetworkAgentSpecifier> CREATOR = new Parcelable.Creator<WifiNetworkAgentSpecifier>(){

        @Override
        public WifiNetworkAgentSpecifier createFromParcel(Parcel parcel) {
            return new WifiNetworkAgentSpecifier((WifiConfiguration)parcel.readParcelable(null), parcel.readInt(), parcel.readString());
        }

        public WifiNetworkAgentSpecifier[] newArray(int n) {
            return new WifiNetworkAgentSpecifier[n];
        }
    };
    private final String mOriginalRequestorPackageName;
    private final int mOriginalRequestorUid;
    private final WifiConfiguration mWifiConfiguration;

    public WifiNetworkAgentSpecifier(WifiConfiguration wifiConfiguration, int n, String string2) {
        Preconditions.checkNotNull(wifiConfiguration);
        this.mWifiConfiguration = wifiConfiguration;
        this.mOriginalRequestorUid = n;
        this.mOriginalRequestorPackageName = string2;
    }

    @Override
    public void assertValidFromUid(int n) {
        throw new IllegalStateException("WifiNetworkAgentSpecifier should never be used for requests.");
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
        if (!(object instanceof WifiNetworkAgentSpecifier)) {
            return false;
        }
        object = (WifiNetworkAgentSpecifier)object;
        if (!(Objects.equals(this.mWifiConfiguration.SSID, object.mWifiConfiguration.SSID) && Objects.equals(this.mWifiConfiguration.BSSID, object.mWifiConfiguration.BSSID) && Objects.equals(this.mWifiConfiguration.allowedKeyManagement, object.mWifiConfiguration.allowedKeyManagement) && this.mOriginalRequestorUid == ((WifiNetworkAgentSpecifier)object).mOriginalRequestorUid && TextUtils.equals(this.mOriginalRequestorPackageName, ((WifiNetworkAgentSpecifier)object).mOriginalRequestorPackageName))) {
            bl = false;
        }
        return bl;
    }

    public int hashCode() {
        return Objects.hash(this.mWifiConfiguration.SSID, this.mWifiConfiguration.BSSID, this.mWifiConfiguration.allowedKeyManagement, this.mOriginalRequestorUid, this.mOriginalRequestorPackageName);
    }

    @Override
    public NetworkSpecifier redact() {
        return null;
    }

    @Override
    public boolean satisfiedBy(NetworkSpecifier networkSpecifier) {
        if (this == networkSpecifier) {
            return true;
        }
        if (networkSpecifier != null && !(networkSpecifier instanceof MatchAllNetworkSpecifier)) {
            if (networkSpecifier instanceof WifiNetworkSpecifier) {
                return this.satisfiesNetworkSpecifier((WifiNetworkSpecifier)networkSpecifier);
            }
            return this.equals(networkSpecifier);
        }
        return true;
    }

    public boolean satisfiesNetworkSpecifier(WifiNetworkSpecifier wifiNetworkSpecifier) {
        Preconditions.checkNotNull(wifiNetworkSpecifier);
        Preconditions.checkNotNull(wifiNetworkSpecifier.ssidPatternMatcher);
        Preconditions.checkNotNull(wifiNetworkSpecifier.bssidPatternMatcher);
        Preconditions.checkNotNull(wifiNetworkSpecifier.wifiConfiguration.allowedKeyManagement);
        Preconditions.checkNotNull(this.mWifiConfiguration.SSID);
        Preconditions.checkNotNull(this.mWifiConfiguration.BSSID);
        Preconditions.checkNotNull(this.mWifiConfiguration.allowedKeyManagement);
        String string2 = this.mWifiConfiguration.SSID;
        boolean bl = string2.startsWith("\"") && string2.endsWith("\"");
        Preconditions.checkState(bl);
        string2 = string2.substring(1, string2.length() - 1);
        if (!wifiNetworkSpecifier.ssidPatternMatcher.match(string2)) {
            return false;
        }
        if (!MacAddress.fromString(this.mWifiConfiguration.BSSID).matches((MacAddress)wifiNetworkSpecifier.bssidPatternMatcher.first, (MacAddress)wifiNetworkSpecifier.bssidPatternMatcher.second)) {
            return false;
        }
        if (!wifiNetworkSpecifier.wifiConfiguration.allowedKeyManagement.equals(this.mWifiConfiguration.allowedKeyManagement)) {
            return false;
        }
        if (wifiNetworkSpecifier.requestorUid != this.mOriginalRequestorUid) {
            return false;
        }
        return TextUtils.equals(wifiNetworkSpecifier.requestorPackageName, this.mOriginalRequestorPackageName);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("WifiNetworkAgentSpecifier [");
        stringBuilder.append("WifiConfiguration=");
        stringBuilder.append(", SSID=");
        stringBuilder.append(this.mWifiConfiguration.SSID);
        stringBuilder.append(", BSSID=");
        stringBuilder.append(this.mWifiConfiguration.BSSID);
        stringBuilder.append(", mOriginalRequestorUid=");
        stringBuilder.append(this.mOriginalRequestorUid);
        stringBuilder.append(", mOriginalRequestorPackageName=");
        stringBuilder.append(this.mOriginalRequestorPackageName);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mWifiConfiguration, n);
        parcel.writeInt(this.mOriginalRequestorUid);
        parcel.writeString(this.mOriginalRequestorPackageName);
    }

}

