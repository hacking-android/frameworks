/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth.le;

import android.os.Parcel;
import android.os.Parcelable;

public final class AdvertisingSetParameters
implements Parcelable {
    public static final Parcelable.Creator<AdvertisingSetParameters> CREATOR = new Parcelable.Creator<AdvertisingSetParameters>(){

        @Override
        public AdvertisingSetParameters createFromParcel(Parcel parcel) {
            return new AdvertisingSetParameters(parcel);
        }

        public AdvertisingSetParameters[] newArray(int n) {
            return new AdvertisingSetParameters[n];
        }
    };
    public static final int INTERVAL_HIGH = 1600;
    public static final int INTERVAL_LOW = 160;
    public static final int INTERVAL_MAX = 16777215;
    public static final int INTERVAL_MEDIUM = 400;
    public static final int INTERVAL_MIN = 160;
    private static final int LIMITED_ADVERTISING_MAX_MILLIS = 180000;
    public static final int TX_POWER_HIGH = 1;
    public static final int TX_POWER_LOW = -15;
    public static final int TX_POWER_MAX = 1;
    public static final int TX_POWER_MEDIUM = -7;
    public static final int TX_POWER_MIN = -127;
    public static final int TX_POWER_ULTRA_LOW = -21;
    private final boolean mConnectable;
    private final boolean mIncludeTxPower;
    private final int mInterval;
    private final boolean mIsAnonymous;
    private final boolean mIsLegacy;
    private final int mPrimaryPhy;
    private final boolean mScannable;
    private final int mSecondaryPhy;
    private final int mTxPowerLevel;

    private AdvertisingSetParameters(Parcel parcel) {
        int n = parcel.readInt();
        boolean bl = true;
        boolean bl2 = n != 0;
        this.mConnectable = bl2;
        bl2 = parcel.readInt() != 0;
        this.mScannable = bl2;
        bl2 = parcel.readInt() != 0;
        this.mIsLegacy = bl2;
        bl2 = parcel.readInt() != 0;
        this.mIsAnonymous = bl2;
        bl2 = parcel.readInt() != 0 ? bl : false;
        this.mIncludeTxPower = bl2;
        this.mPrimaryPhy = parcel.readInt();
        this.mSecondaryPhy = parcel.readInt();
        this.mInterval = parcel.readInt();
        this.mTxPowerLevel = parcel.readInt();
    }

    private AdvertisingSetParameters(boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, int n, int n2, int n3, int n4) {
        this.mConnectable = bl;
        this.mScannable = bl2;
        this.mIsLegacy = bl3;
        this.mIsAnonymous = bl4;
        this.mIncludeTxPower = bl5;
        this.mPrimaryPhy = n;
        this.mSecondaryPhy = n2;
        this.mInterval = n3;
        this.mTxPowerLevel = n4;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getInterval() {
        return this.mInterval;
    }

    public int getPrimaryPhy() {
        return this.mPrimaryPhy;
    }

    public int getSecondaryPhy() {
        return this.mSecondaryPhy;
    }

    public int getTxPowerLevel() {
        return this.mTxPowerLevel;
    }

    public boolean includeTxPower() {
        return this.mIncludeTxPower;
    }

    public boolean isAnonymous() {
        return this.mIsAnonymous;
    }

    public boolean isConnectable() {
        return this.mConnectable;
    }

    public boolean isLegacy() {
        return this.mIsLegacy;
    }

    public boolean isScannable() {
        return this.mScannable;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AdvertisingSetParameters [connectable=");
        stringBuilder.append(this.mConnectable);
        stringBuilder.append(", isLegacy=");
        stringBuilder.append(this.mIsLegacy);
        stringBuilder.append(", isAnonymous=");
        stringBuilder.append(this.mIsAnonymous);
        stringBuilder.append(", includeTxPower=");
        stringBuilder.append(this.mIncludeTxPower);
        stringBuilder.append(", primaryPhy=");
        stringBuilder.append(this.mPrimaryPhy);
        stringBuilder.append(", secondaryPhy=");
        stringBuilder.append(this.mSecondaryPhy);
        stringBuilder.append(", interval=");
        stringBuilder.append(this.mInterval);
        stringBuilder.append(", txPowerLevel=");
        stringBuilder.append(this.mTxPowerLevel);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt((int)this.mConnectable);
        parcel.writeInt((int)this.mScannable);
        parcel.writeInt((int)this.mIsLegacy);
        parcel.writeInt((int)this.mIsAnonymous);
        parcel.writeInt((int)this.mIncludeTxPower);
        parcel.writeInt(this.mPrimaryPhy);
        parcel.writeInt(this.mSecondaryPhy);
        parcel.writeInt(this.mInterval);
        parcel.writeInt(this.mTxPowerLevel);
    }

    public static final class Builder {
        private boolean mConnectable = false;
        private boolean mIncludeTxPower = false;
        private int mInterval = 160;
        private boolean mIsAnonymous = false;
        private boolean mIsLegacy = false;
        private int mPrimaryPhy = 1;
        private boolean mScannable = false;
        private int mSecondaryPhy = 1;
        private int mTxPowerLevel = -7;

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public AdvertisingSetParameters build() {
            if (this.mIsLegacy) {
                if (this.mIsAnonymous) throw new IllegalArgumentException("Legacy advertising can't be anonymous");
                if (this.mConnectable && !this.mScannable) {
                    throw new IllegalStateException("Legacy advertisement can't be connectable and non-scannable");
                }
                if (!this.mIncludeTxPower) return new AdvertisingSetParameters(this.mConnectable, this.mScannable, this.mIsLegacy, this.mIsAnonymous, this.mIncludeTxPower, this.mPrimaryPhy, this.mSecondaryPhy, this.mInterval, this.mTxPowerLevel);
                throw new IllegalStateException("Legacy advertising can't include TX power level in header");
            }
            if (this.mConnectable && this.mScannable) {
                throw new IllegalStateException("Advertising can't be both connectable and scannable");
            }
            if (!this.mIsAnonymous || !this.mConnectable) return new AdvertisingSetParameters(this.mConnectable, this.mScannable, this.mIsLegacy, this.mIsAnonymous, this.mIncludeTxPower, this.mPrimaryPhy, this.mSecondaryPhy, this.mInterval, this.mTxPowerLevel);
            throw new IllegalStateException("Advertising can't be both connectable and anonymous");
        }

        public Builder setAnonymous(boolean bl) {
            this.mIsAnonymous = bl;
            return this;
        }

        public Builder setConnectable(boolean bl) {
            this.mConnectable = bl;
            return this;
        }

        public Builder setIncludeTxPower(boolean bl) {
            this.mIncludeTxPower = bl;
            return this;
        }

        public Builder setInterval(int n) {
            if (n >= 160 && n <= 16777215) {
                this.mInterval = n;
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unknown interval ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public Builder setLegacyMode(boolean bl) {
            this.mIsLegacy = bl;
            return this;
        }

        public Builder setPrimaryPhy(int n) {
            if (n != 1 && n != 3) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("bad primaryPhy ");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            this.mPrimaryPhy = n;
            return this;
        }

        public Builder setScannable(boolean bl) {
            this.mScannable = bl;
            return this;
        }

        public Builder setSecondaryPhy(int n) {
            if (n != 1 && n != 2 && n != 3) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("bad secondaryPhy ");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            this.mSecondaryPhy = n;
            return this;
        }

        public Builder setTxPowerLevel(int n) {
            if (n >= -127 && n <= 1) {
                this.mTxPowerLevel = n;
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unknown txPowerLevel ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

}

