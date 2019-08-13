/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Parcel;
import android.os.Parcelable;

public class BatteryProperty
implements Parcelable {
    public static final Parcelable.Creator<BatteryProperty> CREATOR = new Parcelable.Creator<BatteryProperty>(){

        @Override
        public BatteryProperty createFromParcel(Parcel parcel) {
            return new BatteryProperty(parcel);
        }

        public BatteryProperty[] newArray(int n) {
            return new BatteryProperty[n];
        }
    };
    private long mValueLong;

    public BatteryProperty() {
        this.mValueLong = Long.MIN_VALUE;
    }

    private BatteryProperty(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getLong() {
        return this.mValueLong;
    }

    public void readFromParcel(Parcel parcel) {
        this.mValueLong = parcel.readLong();
    }

    public void setLong(long l) {
        this.mValueLong = l;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.mValueLong);
    }

}

