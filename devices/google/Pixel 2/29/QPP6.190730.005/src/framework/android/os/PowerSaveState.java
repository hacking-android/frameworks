/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Parcel;
import android.os.Parcelable;

public class PowerSaveState
implements Parcelable {
    public static final Parcelable.Creator<PowerSaveState> CREATOR = new Parcelable.Creator<PowerSaveState>(){

        @Override
        public PowerSaveState createFromParcel(Parcel parcel) {
            return new PowerSaveState(parcel);
        }

        public PowerSaveState[] newArray(int n) {
            return new PowerSaveState[n];
        }
    };
    public final boolean batterySaverEnabled;
    public final float brightnessFactor;
    public final boolean globalBatterySaverEnabled;
    public final int locationMode;

    public PowerSaveState(Parcel parcel) {
        byte by = parcel.readByte();
        boolean bl = true;
        boolean bl2 = by != 0;
        this.batterySaverEnabled = bl2;
        bl2 = parcel.readByte() != 0 ? bl : false;
        this.globalBatterySaverEnabled = bl2;
        this.locationMode = parcel.readInt();
        this.brightnessFactor = parcel.readFloat();
    }

    public PowerSaveState(Builder builder) {
        this.batterySaverEnabled = builder.mBatterySaverEnabled;
        this.locationMode = builder.mLocationMode;
        this.brightnessFactor = builder.mBrightnessFactor;
        this.globalBatterySaverEnabled = builder.mGlobalBatterySaverEnabled;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeByte((byte)(this.batterySaverEnabled ? 1 : 0));
        parcel.writeByte((byte)(this.globalBatterySaverEnabled ? 1 : 0));
        parcel.writeInt(this.locationMode);
        parcel.writeFloat(this.brightnessFactor);
    }

    public static final class Builder {
        private boolean mBatterySaverEnabled = false;
        private float mBrightnessFactor = 0.5f;
        private boolean mGlobalBatterySaverEnabled = false;
        private int mLocationMode = 0;

        public PowerSaveState build() {
            return new PowerSaveState(this);
        }

        public Builder setBatterySaverEnabled(boolean bl) {
            this.mBatterySaverEnabled = bl;
            return this;
        }

        public Builder setBrightnessFactor(float f) {
            this.mBrightnessFactor = f;
            return this;
        }

        public Builder setGlobalBatterySaverEnabled(boolean bl) {
            this.mGlobalBatterySaverEnabled = bl;
            return this;
        }

        public Builder setLocationMode(int n) {
            this.mLocationMode = n;
            return this;
        }
    }

}

