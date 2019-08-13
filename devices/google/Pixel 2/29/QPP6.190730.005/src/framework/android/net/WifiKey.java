/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SystemApi
public class WifiKey
implements Parcelable {
    private static final Pattern BSSID_PATTERN;
    public static final Parcelable.Creator<WifiKey> CREATOR;
    private static final Pattern SSID_PATTERN;
    public final String bssid;
    public final String ssid;

    static {
        SSID_PATTERN = Pattern.compile("(\".*\")|(0x[\\p{XDigit}]+)", 32);
        BSSID_PATTERN = Pattern.compile("([\\p{XDigit}]{2}:){5}[\\p{XDigit}]{2}");
        CREATOR = new Parcelable.Creator<WifiKey>(){

            @Override
            public WifiKey createFromParcel(Parcel parcel) {
                return new WifiKey(parcel);
            }

            public WifiKey[] newArray(int n) {
                return new WifiKey[n];
            }
        };
    }

    private WifiKey(Parcel parcel) {
        this.ssid = parcel.readString();
        this.bssid = parcel.readString();
    }

    public WifiKey(String charSequence, String charSequence2) {
        if (charSequence != null && SSID_PATTERN.matcher(charSequence).matches()) {
            if (charSequence2 != null && BSSID_PATTERN.matcher(charSequence2).matches()) {
                this.ssid = charSequence;
                this.bssid = charSequence2;
                return;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Invalid bssid: ");
            ((StringBuilder)charSequence).append((String)charSequence2);
            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append("Invalid ssid: ");
        ((StringBuilder)charSequence2).append((String)charSequence);
        throw new IllegalArgumentException(((StringBuilder)charSequence2).toString());
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
        if (object != null && this.getClass() == object.getClass()) {
            object = (WifiKey)object;
            if (!Objects.equals(this.ssid, ((WifiKey)object).ssid) || !Objects.equals(this.bssid, ((WifiKey)object).bssid)) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.ssid, this.bssid);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("WifiKey[SSID=");
        stringBuilder.append(this.ssid);
        stringBuilder.append(",BSSID=");
        stringBuilder.append(this.bssid);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.ssid);
        parcel.writeString(this.bssid);
    }

}

