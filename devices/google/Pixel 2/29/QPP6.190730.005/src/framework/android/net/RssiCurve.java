/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;

@SystemApi
public class RssiCurve
implements Parcelable {
    public static final Parcelable.Creator<RssiCurve> CREATOR = new Parcelable.Creator<RssiCurve>(){

        @Override
        public RssiCurve createFromParcel(Parcel parcel) {
            return new RssiCurve(parcel);
        }

        public RssiCurve[] newArray(int n) {
            return new RssiCurve[n];
        }
    };
    private static final int DEFAULT_ACTIVE_NETWORK_RSSI_BOOST = 25;
    public final int activeNetworkRssiBoost;
    public final int bucketWidth;
    public final byte[] rssiBuckets;
    public final int start;

    public RssiCurve(int n, int n2, byte[] arrby) {
        this(n, n2, arrby, 25);
    }

    public RssiCurve(int n, int n2, byte[] arrby, int n3) {
        this.start = n;
        this.bucketWidth = n2;
        if (arrby != null && arrby.length != 0) {
            this.rssiBuckets = arrby;
            this.activeNetworkRssiBoost = n3;
            return;
        }
        throw new IllegalArgumentException("rssiBuckets must be at least one element large.");
    }

    private RssiCurve(Parcel parcel) {
        this.start = parcel.readInt();
        this.bucketWidth = parcel.readInt();
        this.rssiBuckets = new byte[parcel.readInt()];
        parcel.readByteArray(this.rssiBuckets);
        this.activeNetworkRssiBoost = parcel.readInt();
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
            object = (RssiCurve)object;
            if (this.start != ((RssiCurve)object).start || this.bucketWidth != ((RssiCurve)object).bucketWidth || !Arrays.equals(this.rssiBuckets, ((RssiCurve)object).rssiBuckets) || this.activeNetworkRssiBoost != ((RssiCurve)object).activeNetworkRssiBoost) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.start, this.bucketWidth, this.activeNetworkRssiBoost) ^ Arrays.hashCode(this.rssiBuckets);
    }

    public byte lookupScore(int n) {
        return this.lookupScore(n, false);
    }

    public byte lookupScore(int n, boolean bl) {
        int n2 = n;
        if (bl) {
            n2 = n + this.activeNetworkRssiBoost;
        }
        if ((n2 = (n2 - this.start) / this.bucketWidth) < 0) {
            n = 0;
        } else {
            byte[] arrby = this.rssiBuckets;
            n = n2;
            if (n2 > arrby.length - 1) {
                n = arrby.length - 1;
            }
        }
        return this.rssiBuckets[n];
    }

    public String toString() {
        byte[] arrby;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("RssiCurve[start=");
        stringBuilder.append(this.start);
        stringBuilder.append(",bucketWidth=");
        stringBuilder.append(this.bucketWidth);
        stringBuilder.append(",activeNetworkRssiBoost=");
        stringBuilder.append(this.activeNetworkRssiBoost);
        stringBuilder.append(",buckets=");
        for (int i = 0; i < (arrby = this.rssiBuckets).length; ++i) {
            stringBuilder.append(arrby[i]);
            if (i >= this.rssiBuckets.length - 1) continue;
            stringBuilder.append(",");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.start);
        parcel.writeInt(this.bucketWidth);
        parcel.writeInt(this.rssiBuckets.length);
        parcel.writeByteArray(this.rssiBuckets);
        parcel.writeInt(this.activeNetworkRssiBoost);
    }

}

