/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.SystemApi;
import android.net.WifiKey;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiSsid;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import java.util.Objects;

@SystemApi
public class NetworkKey
implements Parcelable {
    public static final Parcelable.Creator<NetworkKey> CREATOR = new Parcelable.Creator<NetworkKey>(){

        @Override
        public NetworkKey createFromParcel(Parcel parcel) {
            return new NetworkKey(parcel);
        }

        public NetworkKey[] newArray(int n) {
            return new NetworkKey[n];
        }
    };
    private static final String TAG = "NetworkKey";
    public static final int TYPE_WIFI = 1;
    public final int type;
    public final WifiKey wifiKey;

    public NetworkKey(WifiKey wifiKey) {
        this.type = 1;
        this.wifiKey = wifiKey;
    }

    private NetworkKey(Parcel object) {
        this.type = ((Parcel)object).readInt();
        if (this.type == 1) {
            this.wifiKey = WifiKey.CREATOR.createFromParcel((Parcel)object);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Parcel has unknown type: ");
        ((StringBuilder)object).append(this.type);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static NetworkKey createFromScanResult(ScanResult object) {
        if (object != null && ((ScanResult)object).wifiSsid != null) {
            String string2 = ((ScanResult)object).wifiSsid.toString();
            object = ((ScanResult)object).BSSID;
            if (!(TextUtils.isEmpty(string2) || string2.equals("<unknown ssid>") || TextUtils.isEmpty((CharSequence)object))) {
                try {
                    object = new WifiKey(String.format("\"%s\"", string2), (String)object);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    Log.e(TAG, "Unable to create WifiKey.", illegalArgumentException);
                    return null;
                }
                return new NetworkKey((WifiKey)object);
            }
        }
        return null;
    }

    public static NetworkKey createFromWifiInfo(WifiInfo object) {
        if (object != null) {
            String string2 = ((WifiInfo)object).getSSID();
            object = ((WifiInfo)object).getBSSID();
            if (!(TextUtils.isEmpty(string2) || string2.equals("<unknown ssid>") || TextUtils.isEmpty((CharSequence)object))) {
                try {
                    object = new WifiKey(string2, (String)object);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    Log.e(TAG, "Unable to create WifiKey.", illegalArgumentException);
                    return null;
                }
                return new NetworkKey((WifiKey)object);
            }
        }
        return null;
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
            object = (NetworkKey)object;
            if (this.type != ((NetworkKey)object).type || !Objects.equals(this.wifiKey, ((NetworkKey)object).wifiKey)) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.type, this.wifiKey);
    }

    public String toString() {
        if (this.type != 1) {
            return "InvalidKey";
        }
        return this.wifiKey.toString();
    }

    @Override
    public void writeToParcel(Parcel object, int n) {
        ((Parcel)object).writeInt(this.type);
        if (this.type == 1) {
            this.wifiKey.writeToParcel((Parcel)object, n);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("NetworkKey has unknown type ");
        ((StringBuilder)object).append(this.type);
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

}

