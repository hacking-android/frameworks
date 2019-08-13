/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth.le;

import android.os.Parcel;
import android.os.Parcelable;

public final class AdvertiseSettings
implements Parcelable {
    public static final int ADVERTISE_MODE_BALANCED = 1;
    public static final int ADVERTISE_MODE_LOW_LATENCY = 2;
    public static final int ADVERTISE_MODE_LOW_POWER = 0;
    public static final int ADVERTISE_TX_POWER_HIGH = 3;
    public static final int ADVERTISE_TX_POWER_LOW = 1;
    public static final int ADVERTISE_TX_POWER_MEDIUM = 2;
    public static final int ADVERTISE_TX_POWER_ULTRA_LOW = 0;
    public static final Parcelable.Creator<AdvertiseSettings> CREATOR = new Parcelable.Creator<AdvertiseSettings>(){

        @Override
        public AdvertiseSettings createFromParcel(Parcel parcel) {
            return new AdvertiseSettings(parcel);
        }

        public AdvertiseSettings[] newArray(int n) {
            return new AdvertiseSettings[n];
        }
    };
    private static final int LIMITED_ADVERTISING_MAX_MILLIS = 180000;
    private final boolean mAdvertiseConnectable;
    private final int mAdvertiseMode;
    private final int mAdvertiseTimeoutMillis;
    private final int mAdvertiseTxPowerLevel;

    private AdvertiseSettings(int n, int n2, boolean bl, int n3) {
        this.mAdvertiseMode = n;
        this.mAdvertiseTxPowerLevel = n2;
        this.mAdvertiseConnectable = bl;
        this.mAdvertiseTimeoutMillis = n3;
    }

    private AdvertiseSettings(Parcel parcel) {
        this.mAdvertiseMode = parcel.readInt();
        this.mAdvertiseTxPowerLevel = parcel.readInt();
        boolean bl = parcel.readInt() != 0;
        this.mAdvertiseConnectable = bl;
        this.mAdvertiseTimeoutMillis = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getMode() {
        return this.mAdvertiseMode;
    }

    public int getTimeout() {
        return this.mAdvertiseTimeoutMillis;
    }

    public int getTxPowerLevel() {
        return this.mAdvertiseTxPowerLevel;
    }

    public boolean isConnectable() {
        return this.mAdvertiseConnectable;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Settings [mAdvertiseMode=");
        stringBuilder.append(this.mAdvertiseMode);
        stringBuilder.append(", mAdvertiseTxPowerLevel=");
        stringBuilder.append(this.mAdvertiseTxPowerLevel);
        stringBuilder.append(", mAdvertiseConnectable=");
        stringBuilder.append(this.mAdvertiseConnectable);
        stringBuilder.append(", mAdvertiseTimeoutMillis=");
        stringBuilder.append(this.mAdvertiseTimeoutMillis);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mAdvertiseMode);
        parcel.writeInt(this.mAdvertiseTxPowerLevel);
        parcel.writeInt((int)this.mAdvertiseConnectable);
        parcel.writeInt(this.mAdvertiseTimeoutMillis);
    }

    public static final class Builder {
        private boolean mConnectable = true;
        private int mMode = 0;
        private int mTimeoutMillis = 0;
        private int mTxPowerLevel = 2;

        public AdvertiseSettings build() {
            return new AdvertiseSettings(this.mMode, this.mTxPowerLevel, this.mConnectable, this.mTimeoutMillis);
        }

        public Builder setAdvertiseMode(int n) {
            if (n >= 0 && n <= 2) {
                this.mMode = n;
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unknown mode ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public Builder setConnectable(boolean bl) {
            this.mConnectable = bl;
            return this;
        }

        public Builder setTimeout(int n) {
            if (n >= 0 && n <= 180000) {
                this.mTimeoutMillis = n;
                return this;
            }
            throw new IllegalArgumentException("timeoutMillis invalid (must be 0-180000 milliseconds)");
        }

        public Builder setTxPowerLevel(int n) {
            if (n >= 0 && n <= 3) {
                this.mTxPowerLevel = n;
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unknown tx power level ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

}

