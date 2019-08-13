/*
 * Decompiled with CFR 0.145.
 */
package android.os.health;

import android.os.Parcel;
import android.os.Parcelable;

public final class TimerStat
implements Parcelable {
    public static final Parcelable.Creator<TimerStat> CREATOR = new Parcelable.Creator<TimerStat>(){

        @Override
        public TimerStat createFromParcel(Parcel parcel) {
            return new TimerStat(parcel);
        }

        public TimerStat[] newArray(int n) {
            return new TimerStat[n];
        }
    };
    private int mCount;
    private long mTime;

    public TimerStat() {
    }

    public TimerStat(int n, long l) {
        this.mCount = n;
        this.mTime = l;
    }

    public TimerStat(Parcel parcel) {
        this.mCount = parcel.readInt();
        this.mTime = parcel.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getCount() {
        return this.mCount;
    }

    public long getTime() {
        return this.mTime;
    }

    public void setCount(int n) {
        this.mCount = n;
    }

    public void setTime(long l) {
        this.mTime = l;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mCount);
        parcel.writeLong(this.mTime);
    }

}

