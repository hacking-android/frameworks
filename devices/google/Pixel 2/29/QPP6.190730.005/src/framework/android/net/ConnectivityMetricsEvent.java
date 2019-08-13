/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.NetworkCapabilities;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.BitUtils;

public final class ConnectivityMetricsEvent
implements Parcelable {
    public static final Parcelable.Creator<ConnectivityMetricsEvent> CREATOR = new Parcelable.Creator<ConnectivityMetricsEvent>(){

        @Override
        public ConnectivityMetricsEvent createFromParcel(Parcel parcel) {
            return new ConnectivityMetricsEvent(parcel);
        }

        public ConnectivityMetricsEvent[] newArray(int n) {
            return new ConnectivityMetricsEvent[n];
        }
    };
    public Parcelable data;
    public String ifname;
    public int netId;
    public long timestamp;
    public long transports;

    public ConnectivityMetricsEvent() {
    }

    private ConnectivityMetricsEvent(Parcel parcel) {
        this.timestamp = parcel.readLong();
        this.transports = parcel.readLong();
        this.netId = parcel.readInt();
        this.ifname = parcel.readString();
        this.data = parcel.readParcelable(null);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("ConnectivityMetricsEvent(");
        long l = this.timestamp;
        int n = 0;
        stringBuilder.append(String.format("%tT.%tL", l, this.timestamp));
        if (this.netId != 0) {
            stringBuilder.append(", ");
            stringBuilder.append("netId=");
            stringBuilder.append(this.netId);
        }
        if (this.ifname != null) {
            stringBuilder.append(", ");
            stringBuilder.append(this.ifname);
        }
        int[] arrn = BitUtils.unpackBits(this.transports);
        int n2 = arrn.length;
        while (n < n2) {
            int n3 = arrn[n];
            stringBuilder.append(", ");
            stringBuilder.append(NetworkCapabilities.transportNameOf(n3));
            ++n;
        }
        stringBuilder.append("): ");
        stringBuilder.append(this.data.toString());
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.timestamp);
        parcel.writeLong(this.transports);
        parcel.writeInt(this.netId);
        parcel.writeString(this.ifname);
        parcel.writeParcelable(this.data, 0);
    }

}

