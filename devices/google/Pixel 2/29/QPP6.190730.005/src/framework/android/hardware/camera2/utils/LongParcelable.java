/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.utils;

import android.os.Parcel;
import android.os.Parcelable;

public class LongParcelable
implements Parcelable {
    public static final Parcelable.Creator<LongParcelable> CREATOR = new Parcelable.Creator<LongParcelable>(){

        @Override
        public LongParcelable createFromParcel(Parcel parcel) {
            return new LongParcelable(parcel);
        }

        public LongParcelable[] newArray(int n) {
            return new LongParcelable[n];
        }
    };
    private long number;

    public LongParcelable() {
        this.number = 0L;
    }

    public LongParcelable(long l) {
        this.number = l;
    }

    private LongParcelable(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getNumber() {
        return this.number;
    }

    public void readFromParcel(Parcel parcel) {
        this.number = parcel.readLong();
    }

    public void setNumber(long l) {
        this.number = l;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.number);
    }

}

