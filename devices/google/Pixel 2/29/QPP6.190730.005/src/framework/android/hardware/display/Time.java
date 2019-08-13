/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.display;

import android.os.Parcel;
import android.os.Parcelable;
import java.time.LocalTime;

public final class Time
implements Parcelable {
    public static final Parcelable.Creator<Time> CREATOR = new Parcelable.Creator<Time>(){

        @Override
        public Time createFromParcel(Parcel parcel) {
            return new Time(parcel);
        }

        public Time[] newArray(int n) {
            return new Time[n];
        }
    };
    private final int mHour;
    private final int mMinute;
    private final int mNano;
    private final int mSecond;

    public Time(Parcel parcel) {
        this.mHour = parcel.readInt();
        this.mMinute = parcel.readInt();
        this.mSecond = parcel.readInt();
        this.mNano = parcel.readInt();
    }

    public Time(LocalTime localTime) {
        this.mHour = localTime.getHour();
        this.mMinute = localTime.getMinute();
        this.mSecond = localTime.getSecond();
        this.mNano = localTime.getNano();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public LocalTime getLocalTime() {
        return LocalTime.of(this.mHour, this.mMinute, this.mSecond, this.mNano);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mHour);
        parcel.writeInt(this.mMinute);
        parcel.writeInt(this.mSecond);
        parcel.writeInt(this.mNano);
    }

}

