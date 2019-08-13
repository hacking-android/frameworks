/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
public final class CallQuality
implements Parcelable {
    public static final int CALL_QUALITY_BAD = 4;
    public static final int CALL_QUALITY_EXCELLENT = 0;
    public static final int CALL_QUALITY_FAIR = 2;
    public static final int CALL_QUALITY_GOOD = 1;
    public static final int CALL_QUALITY_NOT_AVAILABLE = 5;
    public static final int CALL_QUALITY_POOR = 3;
    public static final Parcelable.Creator<CallQuality> CREATOR = new Parcelable.Creator(){

        public CallQuality createFromParcel(Parcel parcel) {
            return new CallQuality(parcel);
        }

        public CallQuality[] newArray(int n) {
            return new CallQuality[n];
        }
    };
    private int mAverageRelativeJitter;
    private int mAverageRoundTripTime;
    private int mCallDuration;
    private int mCodecType;
    private int mDownlinkCallQualityLevel;
    private int mMaxRelativeJitter;
    private int mNumRtpPacketsNotReceived;
    private int mNumRtpPacketsReceived;
    private int mNumRtpPacketsTransmitted;
    private int mNumRtpPacketsTransmittedLost;
    private int mUplinkCallQualityLevel;

    public CallQuality() {
    }

    public CallQuality(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, int n11) {
        this.mDownlinkCallQualityLevel = n;
        this.mUplinkCallQualityLevel = n2;
        this.mCallDuration = n3;
        this.mNumRtpPacketsTransmitted = n4;
        this.mNumRtpPacketsReceived = n5;
        this.mNumRtpPacketsTransmittedLost = n6;
        this.mNumRtpPacketsNotReceived = n7;
        this.mAverageRelativeJitter = n8;
        this.mMaxRelativeJitter = n9;
        this.mAverageRoundTripTime = n10;
        this.mCodecType = n11;
    }

    public CallQuality(Parcel parcel) {
        this.mDownlinkCallQualityLevel = parcel.readInt();
        this.mUplinkCallQualityLevel = parcel.readInt();
        this.mCallDuration = parcel.readInt();
        this.mNumRtpPacketsTransmitted = parcel.readInt();
        this.mNumRtpPacketsReceived = parcel.readInt();
        this.mNumRtpPacketsTransmittedLost = parcel.readInt();
        this.mNumRtpPacketsNotReceived = parcel.readInt();
        this.mAverageRelativeJitter = parcel.readInt();
        this.mMaxRelativeJitter = parcel.readInt();
        this.mAverageRoundTripTime = parcel.readInt();
        this.mCodecType = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object != null && object instanceof CallQuality && this.hashCode() == object.hashCode()) {
            if (this == object) {
                return true;
            }
            object = (CallQuality)object;
            boolean bl2 = bl;
            if (this.mDownlinkCallQualityLevel == ((CallQuality)object).mDownlinkCallQualityLevel) {
                bl2 = bl;
                if (this.mUplinkCallQualityLevel == ((CallQuality)object).mUplinkCallQualityLevel) {
                    bl2 = bl;
                    if (this.mCallDuration == ((CallQuality)object).mCallDuration) {
                        bl2 = bl;
                        if (this.mNumRtpPacketsTransmitted == ((CallQuality)object).mNumRtpPacketsTransmitted) {
                            bl2 = bl;
                            if (this.mNumRtpPacketsReceived == ((CallQuality)object).mNumRtpPacketsReceived) {
                                bl2 = bl;
                                if (this.mNumRtpPacketsTransmittedLost == ((CallQuality)object).mNumRtpPacketsTransmittedLost) {
                                    bl2 = bl;
                                    if (this.mNumRtpPacketsNotReceived == ((CallQuality)object).mNumRtpPacketsNotReceived) {
                                        bl2 = bl;
                                        if (this.mAverageRelativeJitter == ((CallQuality)object).mAverageRelativeJitter) {
                                            bl2 = bl;
                                            if (this.mMaxRelativeJitter == ((CallQuality)object).mMaxRelativeJitter) {
                                                bl2 = bl;
                                                if (this.mAverageRoundTripTime == ((CallQuality)object).mAverageRoundTripTime) {
                                                    bl2 = bl;
                                                    if (this.mCodecType == ((CallQuality)object).mCodecType) {
                                                        bl2 = true;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return bl2;
        }
        return false;
    }

    public int getAverageRelativeJitter() {
        return this.mAverageRelativeJitter;
    }

    public int getAverageRoundTripTime() {
        return this.mAverageRoundTripTime;
    }

    public int getCallDuration() {
        return this.mCallDuration;
    }

    public int getCodecType() {
        return this.mCodecType;
    }

    public int getDownlinkCallQualityLevel() {
        return this.mDownlinkCallQualityLevel;
    }

    public int getMaxRelativeJitter() {
        return this.mMaxRelativeJitter;
    }

    public int getNumRtpPacketsNotReceived() {
        return this.mNumRtpPacketsNotReceived;
    }

    public int getNumRtpPacketsReceived() {
        return this.mNumRtpPacketsReceived;
    }

    public int getNumRtpPacketsTransmitted() {
        return this.mNumRtpPacketsTransmitted;
    }

    public int getNumRtpPacketsTransmittedLost() {
        return this.mNumRtpPacketsTransmittedLost;
    }

    public int getUplinkCallQualityLevel() {
        return this.mUplinkCallQualityLevel;
    }

    public int hashCode() {
        return Objects.hash(this.mDownlinkCallQualityLevel, this.mUplinkCallQualityLevel, this.mCallDuration, this.mNumRtpPacketsTransmitted, this.mNumRtpPacketsReceived, this.mNumRtpPacketsTransmittedLost, this.mNumRtpPacketsNotReceived, this.mAverageRelativeJitter, this.mMaxRelativeJitter, this.mAverageRoundTripTime, this.mCodecType);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CallQuality: {downlinkCallQualityLevel=");
        stringBuilder.append(this.mDownlinkCallQualityLevel);
        stringBuilder.append(" uplinkCallQualityLevel=");
        stringBuilder.append(this.mUplinkCallQualityLevel);
        stringBuilder.append(" callDuration=");
        stringBuilder.append(this.mCallDuration);
        stringBuilder.append(" numRtpPacketsTransmitted=");
        stringBuilder.append(this.mNumRtpPacketsTransmitted);
        stringBuilder.append(" numRtpPacketsReceived=");
        stringBuilder.append(this.mNumRtpPacketsReceived);
        stringBuilder.append(" numRtpPacketsTransmittedLost=");
        stringBuilder.append(this.mNumRtpPacketsTransmittedLost);
        stringBuilder.append(" numRtpPacketsNotReceived=");
        stringBuilder.append(this.mNumRtpPacketsNotReceived);
        stringBuilder.append(" averageRelativeJitter=");
        stringBuilder.append(this.mAverageRelativeJitter);
        stringBuilder.append(" maxRelativeJitter=");
        stringBuilder.append(this.mMaxRelativeJitter);
        stringBuilder.append(" averageRoundTripTime=");
        stringBuilder.append(this.mAverageRoundTripTime);
        stringBuilder.append(" codecType=");
        stringBuilder.append(this.mCodecType);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mDownlinkCallQualityLevel);
        parcel.writeInt(this.mUplinkCallQualityLevel);
        parcel.writeInt(this.mCallDuration);
        parcel.writeInt(this.mNumRtpPacketsTransmitted);
        parcel.writeInt(this.mNumRtpPacketsReceived);
        parcel.writeInt(this.mNumRtpPacketsTransmittedLost);
        parcel.writeInt(this.mNumRtpPacketsNotReceived);
        parcel.writeInt(this.mAverageRelativeJitter);
        parcel.writeInt(this.mMaxRelativeJitter);
        parcel.writeInt(this.mAverageRoundTripTime);
        parcel.writeInt(this.mCodecType);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface CallQualityLevel {
    }

}

