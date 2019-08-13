/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Parcel;
import android.os.Parcelable;

public final class CpuUsageInfo
implements Parcelable {
    public static final Parcelable.Creator<CpuUsageInfo> CREATOR = new Parcelable.Creator<CpuUsageInfo>(){

        @Override
        public CpuUsageInfo createFromParcel(Parcel parcel) {
            return new CpuUsageInfo(parcel);
        }

        public CpuUsageInfo[] newArray(int n) {
            return new CpuUsageInfo[n];
        }
    };
    private long mActive;
    private long mTotal;

    public CpuUsageInfo(long l, long l2) {
        this.mActive = l;
        this.mTotal = l2;
    }

    private CpuUsageInfo(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    private void readFromParcel(Parcel parcel) {
        this.mActive = parcel.readLong();
        this.mTotal = parcel.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getActive() {
        return this.mActive;
    }

    public long getTotal() {
        return this.mTotal;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.mActive);
        parcel.writeLong(this.mTotal);
    }

}

