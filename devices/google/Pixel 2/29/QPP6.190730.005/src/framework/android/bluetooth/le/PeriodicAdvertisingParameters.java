/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth.le;

import android.os.Parcel;
import android.os.Parcelable;

public final class PeriodicAdvertisingParameters
implements Parcelable {
    public static final Parcelable.Creator<PeriodicAdvertisingParameters> CREATOR = new Parcelable.Creator<PeriodicAdvertisingParameters>(){

        @Override
        public PeriodicAdvertisingParameters createFromParcel(Parcel parcel) {
            return new PeriodicAdvertisingParameters(parcel);
        }

        public PeriodicAdvertisingParameters[] newArray(int n) {
            return new PeriodicAdvertisingParameters[n];
        }
    };
    private static final int INTERVAL_MAX = 65519;
    private static final int INTERVAL_MIN = 80;
    private final boolean mIncludeTxPower;
    private final int mInterval;

    private PeriodicAdvertisingParameters(Parcel parcel) {
        boolean bl = parcel.readInt() != 0;
        this.mIncludeTxPower = bl;
        this.mInterval = parcel.readInt();
    }

    private PeriodicAdvertisingParameters(boolean bl, int n) {
        this.mIncludeTxPower = bl;
        this.mInterval = n;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean getIncludeTxPower() {
        return this.mIncludeTxPower;
    }

    public int getInterval() {
        return this.mInterval;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt((int)this.mIncludeTxPower);
        parcel.writeInt(this.mInterval);
    }

    public static final class Builder {
        private boolean mIncludeTxPower = false;
        private int mInterval = 65519;

        public PeriodicAdvertisingParameters build() {
            return new PeriodicAdvertisingParameters(this.mIncludeTxPower, this.mInterval);
        }

        public Builder setIncludeTxPower(boolean bl) {
            this.mIncludeTxPower = bl;
            return this;
        }

        public Builder setInterval(int n) {
            if (n >= 80 && n <= 65519) {
                this.mInterval = n;
                return this;
            }
            throw new IllegalArgumentException("Invalid interval (must be 80-65519)");
        }
    }

}

