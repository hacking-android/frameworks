/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.os.Parcel;
import android.os.Parcelable;

public final class LocationTime
implements Parcelable {
    public static final Parcelable.Creator<LocationTime> CREATOR = new Parcelable.Creator<LocationTime>(){

        @Override
        public LocationTime createFromParcel(Parcel parcel) {
            return new LocationTime(parcel.readLong(), parcel.readLong());
        }

        public LocationTime[] newArray(int n) {
            return new LocationTime[n];
        }
    };
    private final long mElapsedRealtimeNanos;
    private final long mTime;

    public LocationTime(long l, long l2) {
        this.mTime = l;
        this.mElapsedRealtimeNanos = l2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getElapsedRealtimeNanos() {
        return this.mElapsedRealtimeNanos;
    }

    public long getTime() {
        return this.mTime;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.mTime);
        parcel.writeLong(this.mElapsedRealtimeNanos);
    }

}

