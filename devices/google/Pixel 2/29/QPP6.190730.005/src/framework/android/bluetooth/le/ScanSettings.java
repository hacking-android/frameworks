/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth.le;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

public final class ScanSettings
implements Parcelable {
    public static final int CALLBACK_TYPE_ALL_MATCHES = 1;
    public static final int CALLBACK_TYPE_FIRST_MATCH = 2;
    public static final int CALLBACK_TYPE_MATCH_LOST = 4;
    public static final Parcelable.Creator<ScanSettings> CREATOR = new Parcelable.Creator<ScanSettings>(){

        @Override
        public ScanSettings createFromParcel(Parcel parcel) {
            return new ScanSettings(parcel);
        }

        public ScanSettings[] newArray(int n) {
            return new ScanSettings[n];
        }
    };
    public static final int MATCH_MODE_AGGRESSIVE = 1;
    public static final int MATCH_MODE_STICKY = 2;
    public static final int MATCH_NUM_FEW_ADVERTISEMENT = 2;
    public static final int MATCH_NUM_MAX_ADVERTISEMENT = 3;
    public static final int MATCH_NUM_ONE_ADVERTISEMENT = 1;
    public static final int PHY_LE_ALL_SUPPORTED = 255;
    public static final int SCAN_MODE_BALANCED = 1;
    public static final int SCAN_MODE_LOW_LATENCY = 2;
    public static final int SCAN_MODE_LOW_POWER = 0;
    public static final int SCAN_MODE_OPPORTUNISTIC = -1;
    @SystemApi
    public static final int SCAN_RESULT_TYPE_ABBREVIATED = 1;
    @SystemApi
    public static final int SCAN_RESULT_TYPE_FULL = 0;
    private int mCallbackType;
    private boolean mLegacy;
    private int mMatchMode;
    private int mNumOfMatchesPerFilter;
    private int mPhy;
    private long mReportDelayMillis;
    private int mScanMode;
    private int mScanResultType;

    private ScanSettings(int n, int n2, int n3, long l, int n4, int n5, boolean bl, int n6) {
        this.mScanMode = n;
        this.mCallbackType = n2;
        this.mScanResultType = n3;
        this.mReportDelayMillis = l;
        this.mNumOfMatchesPerFilter = n5;
        this.mMatchMode = n4;
        this.mLegacy = bl;
        this.mPhy = n6;
    }

    private ScanSettings(Parcel parcel) {
        this.mScanMode = parcel.readInt();
        this.mCallbackType = parcel.readInt();
        this.mScanResultType = parcel.readInt();
        this.mReportDelayMillis = parcel.readLong();
        this.mMatchMode = parcel.readInt();
        this.mNumOfMatchesPerFilter = parcel.readInt();
        boolean bl = parcel.readInt() != 0;
        this.mLegacy = bl;
        this.mPhy = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getCallbackType() {
        return this.mCallbackType;
    }

    public boolean getLegacy() {
        return this.mLegacy;
    }

    public int getMatchMode() {
        return this.mMatchMode;
    }

    public int getNumOfMatches() {
        return this.mNumOfMatchesPerFilter;
    }

    public int getPhy() {
        return this.mPhy;
    }

    public long getReportDelayMillis() {
        return this.mReportDelayMillis;
    }

    public int getScanMode() {
        return this.mScanMode;
    }

    public int getScanResultType() {
        return this.mScanResultType;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mScanMode);
        parcel.writeInt(this.mCallbackType);
        parcel.writeInt(this.mScanResultType);
        parcel.writeLong(this.mReportDelayMillis);
        parcel.writeInt(this.mMatchMode);
        parcel.writeInt(this.mNumOfMatchesPerFilter);
        parcel.writeInt((int)this.mLegacy);
        parcel.writeInt(this.mPhy);
    }

    public static final class Builder {
        private int mCallbackType = 1;
        private boolean mLegacy = true;
        private int mMatchMode = 1;
        private int mNumOfMatchesPerFilter = 3;
        private int mPhy = 255;
        private long mReportDelayMillis = 0L;
        private int mScanMode = 0;
        private int mScanResultType = 0;

        private boolean isValidCallbackType(int n) {
            boolean bl = true;
            if (n != 1 && n != 2 && n != 4) {
                if (n != 6) {
                    bl = false;
                }
                return bl;
            }
            return true;
        }

        public ScanSettings build() {
            return new ScanSettings(this.mScanMode, this.mCallbackType, this.mScanResultType, this.mReportDelayMillis, this.mMatchMode, this.mNumOfMatchesPerFilter, this.mLegacy, this.mPhy);
        }

        public Builder setCallbackType(int n) {
            if (this.isValidCallbackType(n)) {
                this.mCallbackType = n;
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("invalid callback type - ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public Builder setLegacy(boolean bl) {
            this.mLegacy = bl;
            return this;
        }

        public Builder setMatchMode(int n) {
            if (n >= 1 && n <= 2) {
                this.mMatchMode = n;
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("invalid matchMode ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public Builder setNumOfMatches(int n) {
            if (n >= 1 && n <= 3) {
                this.mNumOfMatchesPerFilter = n;
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("invalid numOfMatches ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public Builder setPhy(int n) {
            this.mPhy = n;
            return this;
        }

        public Builder setReportDelay(long l) {
            if (l >= 0L) {
                this.mReportDelayMillis = l;
                return this;
            }
            throw new IllegalArgumentException("reportDelay must be > 0");
        }

        public Builder setScanMode(int n) {
            if (n >= -1 && n <= 2) {
                this.mScanMode = n;
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("invalid scan mode ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        @SystemApi
        public Builder setScanResultType(int n) {
            if (n >= 0 && n <= 1) {
                this.mScanResultType = n;
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("invalid scanResultType - ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

}

