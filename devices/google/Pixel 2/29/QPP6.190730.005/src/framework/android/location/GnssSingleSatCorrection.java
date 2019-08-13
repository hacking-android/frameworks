/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.annotation.SystemApi;
import android.location.GnssReflectingPlane;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;

@SystemApi
public final class GnssSingleSatCorrection
implements Parcelable {
    public static final Parcelable.Creator<GnssSingleSatCorrection> CREATOR = new Parcelable.Creator<GnssSingleSatCorrection>(){

        @Override
        public GnssSingleSatCorrection createFromParcel(Parcel parcel) {
            boolean bl = (parcel.readInt() & 8) != 0;
            Builder builder = new Builder().setConstellationType(parcel.readInt()).setSatelliteId(parcel.readInt()).setCarrierFrequencyHz(parcel.readFloat()).setProbabilityLineOfSight(parcel.readFloat()).setExcessPathLengthMeters(parcel.readFloat()).setExcessPathLengthUncertaintyMeters(parcel.readFloat());
            if (bl) {
                builder.setReflectingPlane(GnssReflectingPlane.CREATOR.createFromParcel(parcel));
            }
            return builder.build();
        }

        public GnssSingleSatCorrection[] newArray(int n) {
            return new GnssSingleSatCorrection[n];
        }
    };
    public static final int HAS_EXCESS_PATH_LENGTH_MASK = 2;
    public static final int HAS_EXCESS_PATH_LENGTH_UNC_MASK = 4;
    public static final int HAS_PROB_SAT_IS_LOS_MASK = 1;
    public static final int HAS_REFLECTING_PLANE_MASK = 8;
    private final float mCarrierFrequencyHz;
    private final int mConstellationType;
    private final float mExcessPathLengthMeters;
    private final float mExcessPathLengthUncertaintyMeters;
    private final float mProbSatIsLos;
    private final GnssReflectingPlane mReflectingPlane;
    private final int mSatId;
    private final int mSingleSatCorrectionFlags;

    private GnssSingleSatCorrection(Builder builder) {
        this.mSingleSatCorrectionFlags = builder.mSingleSatCorrectionFlags;
        this.mSatId = builder.mSatId;
        this.mConstellationType = builder.mConstellationType;
        this.mCarrierFrequencyHz = builder.mCarrierFrequencyHz;
        this.mProbSatIsLos = builder.mProbSatIsLos;
        this.mExcessPathLengthMeters = builder.mExcessPathLengthMeters;
        this.mExcessPathLengthUncertaintyMeters = builder.mExcessPathLengthUncertaintyMeters;
        this.mReflectingPlane = builder.mReflectingPlane;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public float getCarrierFrequencyHz() {
        return this.mCarrierFrequencyHz;
    }

    public int getConstellationType() {
        return this.mConstellationType;
    }

    public float getExcessPathLengthMeters() {
        return this.mExcessPathLengthMeters;
    }

    public float getExcessPathLengthUncertaintyMeters() {
        return this.mExcessPathLengthUncertaintyMeters;
    }

    public float getProbabilityLineOfSight() {
        return this.mProbSatIsLos;
    }

    public GnssReflectingPlane getReflectingPlane() {
        return this.mReflectingPlane;
    }

    public int getSatelliteId() {
        return this.mSatId;
    }

    public int getSingleSatelliteCorrectionFlags() {
        return this.mSingleSatCorrectionFlags;
    }

    public boolean hasExcessPathLength() {
        boolean bl = (this.mSingleSatCorrectionFlags & 2) != 0;
        return bl;
    }

    public boolean hasExcessPathLengthUncertainty() {
        boolean bl = (this.mSingleSatCorrectionFlags & 4) != 0;
        return bl;
    }

    public boolean hasReflectingPlane() {
        boolean bl = (this.mSingleSatCorrectionFlags & 8) != 0;
        return bl;
    }

    public boolean hasValidSatelliteLineOfSight() {
        int n = this.mSingleSatCorrectionFlags;
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("GnssSingleSatCorrection:\n");
        stringBuilder.append(String.format("   %-29s = %s\n", "SingleSatCorrectionFlags = ", this.mSingleSatCorrectionFlags));
        stringBuilder.append(String.format("   %-29s = %s\n", "ConstellationType = ", this.mConstellationType));
        stringBuilder.append(String.format("   %-29s = %s\n", "SatId = ", this.mSatId));
        stringBuilder.append(String.format("   %-29s = %s\n", "CarrierFrequencyHz = ", Float.valueOf(this.mCarrierFrequencyHz)));
        stringBuilder.append(String.format("   %-29s = %s\n", "ProbSatIsLos = ", Float.valueOf(this.mProbSatIsLos)));
        stringBuilder.append(String.format("   %-29s = %s\n", "ExcessPathLengthMeters = ", Float.valueOf(this.mExcessPathLengthMeters)));
        stringBuilder.append(String.format("   %-29s = %s\n", "ExcessPathLengthUncertaintyMeters = ", Float.valueOf(this.mExcessPathLengthUncertaintyMeters)));
        if (this.hasReflectingPlane()) {
            stringBuilder.append(String.format("   %-29s = %s\n", "ReflectingPlane = ", this.mReflectingPlane));
        }
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mSingleSatCorrectionFlags);
        parcel.writeInt(this.mConstellationType);
        parcel.writeInt(this.mSatId);
        parcel.writeFloat(this.mCarrierFrequencyHz);
        parcel.writeFloat(this.mProbSatIsLos);
        parcel.writeFloat(this.mExcessPathLengthMeters);
        parcel.writeFloat(this.mExcessPathLengthUncertaintyMeters);
        if (this.hasReflectingPlane()) {
            this.mReflectingPlane.writeToParcel(parcel, n);
        }
    }

    public static final class Builder {
        private float mCarrierFrequencyHz;
        private int mConstellationType;
        private float mExcessPathLengthMeters;
        private float mExcessPathLengthUncertaintyMeters;
        private float mProbSatIsLos;
        private GnssReflectingPlane mReflectingPlane;
        private int mSatId;
        private int mSingleSatCorrectionFlags;

        public GnssSingleSatCorrection build() {
            return new GnssSingleSatCorrection(this);
        }

        public Builder setCarrierFrequencyHz(float f) {
            this.mCarrierFrequencyHz = f;
            return this;
        }

        public Builder setConstellationType(int n) {
            this.mConstellationType = n;
            return this;
        }

        public Builder setExcessPathLengthMeters(float f) {
            this.mExcessPathLengthMeters = f;
            this.mSingleSatCorrectionFlags = (byte)(this.mSingleSatCorrectionFlags | 2);
            return this;
        }

        public Builder setExcessPathLengthUncertaintyMeters(float f) {
            this.mExcessPathLengthUncertaintyMeters = f;
            this.mSingleSatCorrectionFlags = (byte)(this.mSingleSatCorrectionFlags | 4);
            return this;
        }

        public Builder setProbabilityLineOfSight(float f) {
            Preconditions.checkArgumentInRange(f, 0.0f, 1.0f, "probSatIsLos should be between 0 and 1.");
            this.mProbSatIsLos = f;
            this.mSingleSatCorrectionFlags = (byte)(this.mSingleSatCorrectionFlags | 1);
            return this;
        }

        public Builder setReflectingPlane(GnssReflectingPlane gnssReflectingPlane) {
            this.mReflectingPlane = gnssReflectingPlane;
            this.mSingleSatCorrectionFlags = gnssReflectingPlane != null ? (int)((byte)(this.mSingleSatCorrectionFlags | 8)) : (int)((byte)(this.mSingleSatCorrectionFlags & -9));
            return this;
        }

        public Builder setSatelliteId(int n) {
            this.mSatId = n;
            return this;
        }
    }

}

