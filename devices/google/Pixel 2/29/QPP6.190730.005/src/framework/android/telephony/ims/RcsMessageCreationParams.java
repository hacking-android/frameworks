/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.os.Parcel;

public class RcsMessageCreationParams {
    private final double mLatitude;
    private final double mLongitude;
    private final int mMessageStatus;
    private final long mOriginationTimestamp;
    private final String mRcsMessageGlobalId;
    private final int mSubId;
    private final String mText;

    RcsMessageCreationParams(Parcel parcel) {
        this.mRcsMessageGlobalId = parcel.readString();
        this.mSubId = parcel.readInt();
        this.mMessageStatus = parcel.readInt();
        this.mOriginationTimestamp = parcel.readLong();
        this.mText = parcel.readString();
        this.mLatitude = parcel.readDouble();
        this.mLongitude = parcel.readDouble();
    }

    protected RcsMessageCreationParams(Builder builder) {
        this.mRcsMessageGlobalId = builder.mRcsMessageGlobalId;
        this.mSubId = builder.mSubId;
        this.mMessageStatus = builder.mMessageStatus;
        this.mOriginationTimestamp = builder.mOriginationTimestamp;
        this.mText = builder.mText;
        this.mLatitude = builder.mLatitude;
        this.mLongitude = builder.mLongitude;
    }

    public double getLatitude() {
        return this.mLatitude;
    }

    public double getLongitude() {
        return this.mLongitude;
    }

    public int getMessageStatus() {
        return this.mMessageStatus;
    }

    public long getOriginationTimestamp() {
        return this.mOriginationTimestamp;
    }

    public String getRcsMessageGlobalId() {
        return this.mRcsMessageGlobalId;
    }

    public int getSubId() {
        return this.mSubId;
    }

    public String getText() {
        return this.mText;
    }

    public void writeToParcel(Parcel parcel) {
        parcel.writeString(this.mRcsMessageGlobalId);
        parcel.writeInt(this.mSubId);
        parcel.writeInt(this.mMessageStatus);
        parcel.writeLong(this.mOriginationTimestamp);
        parcel.writeString(this.mText);
        parcel.writeDouble(this.mLatitude);
        parcel.writeDouble(this.mLongitude);
    }

    public static class Builder {
        private double mLatitude = Double.MIN_VALUE;
        private double mLongitude = Double.MIN_VALUE;
        private int mMessageStatus;
        private long mOriginationTimestamp;
        private String mRcsMessageGlobalId;
        private int mSubId;
        private String mText;

        public Builder(long l, int n) {
            this.mOriginationTimestamp = l;
            this.mSubId = n;
        }

        public RcsMessageCreationParams build() {
            return new RcsMessageCreationParams(this);
        }

        public Builder setLatitude(double d) {
            this.mLatitude = d;
            return this;
        }

        public Builder setLongitude(double d) {
            this.mLongitude = d;
            return this;
        }

        public Builder setRcsMessageId(String string2) {
            this.mRcsMessageGlobalId = string2;
            return this;
        }

        public Builder setStatus(int n) {
            this.mMessageStatus = n;
            return this;
        }

        public Builder setText(String string2) {
            this.mText = string2;
            return this;
        }
    }

}

