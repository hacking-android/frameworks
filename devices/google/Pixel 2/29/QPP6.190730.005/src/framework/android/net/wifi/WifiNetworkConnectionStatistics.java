/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public class WifiNetworkConnectionStatistics
implements Parcelable {
    public static final Parcelable.Creator<WifiNetworkConnectionStatistics> CREATOR = new Parcelable.Creator<WifiNetworkConnectionStatistics>(){

        @Override
        public WifiNetworkConnectionStatistics createFromParcel(Parcel parcel) {
            return new WifiNetworkConnectionStatistics(parcel.readInt(), parcel.readInt());
        }

        public WifiNetworkConnectionStatistics[] newArray(int n) {
            return new WifiNetworkConnectionStatistics[n];
        }
    };
    private static final String TAG = "WifiNetworkConnnectionStatistics";
    public int numConnection;
    public int numUsage;

    public WifiNetworkConnectionStatistics() {
    }

    public WifiNetworkConnectionStatistics(int n, int n2) {
        this.numConnection = n;
        this.numUsage = n2;
    }

    public WifiNetworkConnectionStatistics(WifiNetworkConnectionStatistics wifiNetworkConnectionStatistics) {
        this.numConnection = wifiNetworkConnectionStatistics.numConnection;
        this.numUsage = wifiNetworkConnectionStatistics.numUsage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("c=");
        stringBuilder.append(this.numConnection);
        stringBuilder.append(" u=");
        stringBuilder.append(this.numUsage);
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.numConnection);
        parcel.writeInt(this.numUsage);
    }

}

