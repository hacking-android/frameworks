/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Objects;

public final class PhysicalChannelConfig
implements Parcelable {
    public static final int CONNECTION_PRIMARY_SERVING = 1;
    public static final int CONNECTION_SECONDARY_SERVING = 2;
    public static final int CONNECTION_UNKNOWN = Integer.MAX_VALUE;
    public static final Parcelable.Creator<PhysicalChannelConfig> CREATOR = new Parcelable.Creator<PhysicalChannelConfig>(){

        @Override
        public PhysicalChannelConfig createFromParcel(Parcel parcel) {
            return new PhysicalChannelConfig(parcel);
        }

        public PhysicalChannelConfig[] newArray(int n) {
            return new PhysicalChannelConfig[n];
        }
    };
    private int mCellBandwidthDownlinkKhz;
    private int mCellConnectionStatus;
    private int mChannelNumber;
    private int[] mContextIds;
    private int mFrequencyRange;
    private int mPhysicalCellId;
    private int mRat;

    private PhysicalChannelConfig(Parcel parcel) {
        this.mCellConnectionStatus = parcel.readInt();
        this.mCellBandwidthDownlinkKhz = parcel.readInt();
        this.mRat = parcel.readInt();
        this.mChannelNumber = parcel.readInt();
        this.mFrequencyRange = parcel.readInt();
        this.mContextIds = parcel.createIntArray();
        this.mPhysicalCellId = parcel.readInt();
    }

    private PhysicalChannelConfig(Builder builder) {
        this.mCellConnectionStatus = builder.mCellConnectionStatus;
        this.mCellBandwidthDownlinkKhz = builder.mCellBandwidthDownlinkKhz;
        this.mRat = builder.mRat;
        this.mChannelNumber = builder.mChannelNumber;
        this.mFrequencyRange = builder.mFrequencyRange;
        this.mContextIds = builder.mContextIds;
        this.mPhysicalCellId = builder.mPhysicalCellId;
    }

    private String getConnectionStatusString() {
        int n = this.mCellConnectionStatus;
        if (n != 1) {
            if (n != 2) {
                if (n != Integer.MAX_VALUE) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Invalid(");
                    stringBuilder.append(this.mCellConnectionStatus);
                    stringBuilder.append(")");
                    return stringBuilder.toString();
                }
                return "Unknown";
            }
            return "SecondaryServing";
        }
        return "PrimaryServing";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof PhysicalChannelConfig)) {
            return false;
        }
        object = (PhysicalChannelConfig)object;
        if (this.mCellConnectionStatus != ((PhysicalChannelConfig)object).mCellConnectionStatus || this.mCellBandwidthDownlinkKhz != ((PhysicalChannelConfig)object).mCellBandwidthDownlinkKhz || this.mRat != ((PhysicalChannelConfig)object).mRat || this.mFrequencyRange != ((PhysicalChannelConfig)object).mFrequencyRange || this.mChannelNumber != ((PhysicalChannelConfig)object).mChannelNumber || this.mPhysicalCellId != ((PhysicalChannelConfig)object).mPhysicalCellId || !Arrays.equals(this.mContextIds, ((PhysicalChannelConfig)object).mContextIds)) {
            bl = false;
        }
        return bl;
    }

    public int getCellBandwidthDownlink() {
        return this.mCellBandwidthDownlinkKhz;
    }

    public int getChannelNumber() {
        return this.mChannelNumber;
    }

    public int getConnectionStatus() {
        return this.mCellConnectionStatus;
    }

    public int[] getContextIds() {
        return this.mContextIds;
    }

    public int getFrequencyRange() {
        return this.mFrequencyRange;
    }

    public int getPhysicalCellId() {
        return this.mPhysicalCellId;
    }

    public int getRat() {
        return this.mRat;
    }

    public int hashCode() {
        return Objects.hash(this.mCellConnectionStatus, this.mCellBandwidthDownlinkKhz, this.mRat, this.mFrequencyRange, this.mChannelNumber, this.mPhysicalCellId, this.mContextIds);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{mConnectionStatus=");
        stringBuilder.append(this.getConnectionStatusString());
        stringBuilder.append(",mCellBandwidthDownlinkKhz=");
        stringBuilder.append(this.mCellBandwidthDownlinkKhz);
        stringBuilder.append(",mRat=");
        stringBuilder.append(this.mRat);
        stringBuilder.append(",mFrequencyRange=");
        stringBuilder.append(this.mFrequencyRange);
        stringBuilder.append(",mChannelNumber=");
        stringBuilder.append(this.mChannelNumber);
        stringBuilder.append(",mContextIds=");
        stringBuilder.append(this.mContextIds.toString());
        stringBuilder.append(",mPhysicalCellId=");
        stringBuilder.append(this.mPhysicalCellId);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mCellConnectionStatus);
        parcel.writeInt(this.mCellBandwidthDownlinkKhz);
        parcel.writeInt(this.mRat);
        parcel.writeInt(this.mChannelNumber);
        parcel.writeInt(this.mFrequencyRange);
        parcel.writeIntArray(this.mContextIds);
        parcel.writeInt(this.mPhysicalCellId);
    }

    public static final class Builder {
        private int mCellBandwidthDownlinkKhz = 0;
        private int mCellConnectionStatus = Integer.MAX_VALUE;
        private int mChannelNumber = Integer.MAX_VALUE;
        private int[] mContextIds = new int[0];
        private int mFrequencyRange = -1;
        private int mPhysicalCellId = Integer.MAX_VALUE;
        private int mRat = 0;

        public PhysicalChannelConfig build() {
            return new PhysicalChannelConfig(this);
        }

        public Builder setCellBandwidthDownlinkKhz(int n) {
            this.mCellBandwidthDownlinkKhz = n;
            return this;
        }

        public Builder setCellConnectionStatus(int n) {
            this.mCellConnectionStatus = n;
            return this;
        }

        public Builder setChannelNumber(int n) {
            this.mChannelNumber = n;
            return this;
        }

        public Builder setContextIds(int[] arrn) {
            if (arrn != null) {
                Arrays.sort(arrn);
            }
            this.mContextIds = arrn;
            return this;
        }

        public Builder setFrequencyRange(int n) {
            this.mFrequencyRange = n;
            return this;
        }

        public Builder setPhysicalCellId(int n) {
            this.mPhysicalCellId = n;
            return this;
        }

        public Builder setRat(int n) {
            this.mRat = n;
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ConnectionStatus {
    }

}

