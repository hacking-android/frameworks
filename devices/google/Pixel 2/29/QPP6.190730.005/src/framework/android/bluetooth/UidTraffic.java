/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;

public class UidTraffic
implements Cloneable,
Parcelable {
    public static final Parcelable.Creator<UidTraffic> CREATOR = new Parcelable.Creator<UidTraffic>(){

        @Override
        public UidTraffic createFromParcel(Parcel parcel) {
            return new UidTraffic(parcel);
        }

        public UidTraffic[] newArray(int n) {
            return new UidTraffic[n];
        }
    };
    private final int mAppUid;
    private long mRxBytes;
    private long mTxBytes;

    public UidTraffic(int n) {
        this.mAppUid = n;
    }

    public UidTraffic(int n, long l, long l2) {
        this.mAppUid = n;
        this.mRxBytes = l;
        this.mTxBytes = l2;
    }

    UidTraffic(Parcel parcel) {
        this.mAppUid = parcel.readInt();
        this.mRxBytes = parcel.readLong();
        this.mTxBytes = parcel.readLong();
    }

    public void addRxBytes(long l) {
        this.mRxBytes += l;
    }

    public void addTxBytes(long l) {
        this.mTxBytes += l;
    }

    public UidTraffic clone() {
        return new UidTraffic(this.mAppUid, this.mRxBytes, this.mTxBytes);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getRxBytes() {
        return this.mRxBytes;
    }

    public long getTxBytes() {
        return this.mTxBytes;
    }

    public int getUid() {
        return this.mAppUid;
    }

    public void setRxBytes(long l) {
        this.mRxBytes = l;
    }

    public void setTxBytes(long l) {
        this.mTxBytes = l;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UidTraffic{mAppUid=");
        stringBuilder.append(this.mAppUid);
        stringBuilder.append(", mRxBytes=");
        stringBuilder.append(this.mRxBytes);
        stringBuilder.append(", mTxBytes=");
        stringBuilder.append(this.mTxBytes);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mAppUid);
        parcel.writeLong(this.mRxBytes);
        parcel.writeLong(this.mTxBytes);
    }

}

