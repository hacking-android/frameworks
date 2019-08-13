/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public final class GnssReflectingPlane
implements Parcelable {
    public static final Parcelable.Creator<GnssReflectingPlane> CREATOR = new Parcelable.Creator<GnssReflectingPlane>(){

        @Override
        public GnssReflectingPlane createFromParcel(Parcel parcel) {
            return new Builder().setLatitudeDegrees(parcel.readDouble()).setLongitudeDegrees(parcel.readDouble()).setAltitudeMeters(parcel.readDouble()).setAzimuthDegrees(parcel.readDouble()).build();
        }

        public GnssReflectingPlane[] newArray(int n) {
            return new GnssReflectingPlane[n];
        }
    };
    private final double mAltitudeMeters;
    private final double mAzimuthDegrees;
    private final double mLatitudeDegrees;
    private final double mLongitudeDegrees;

    private GnssReflectingPlane(Builder builder) {
        this.mLatitudeDegrees = builder.mLatitudeDegrees;
        this.mLongitudeDegrees = builder.mLongitudeDegrees;
        this.mAltitudeMeters = builder.mAltitudeMeters;
        this.mAzimuthDegrees = builder.mAzimuthDegrees;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public double getAltitudeMeters() {
        return this.mAltitudeMeters;
    }

    public double getAzimuthDegrees() {
        return this.mAzimuthDegrees;
    }

    public double getLatitudeDegrees() {
        return this.mLatitudeDegrees;
    }

    public double getLongitudeDegrees() {
        return this.mLongitudeDegrees;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("ReflectingPlane:\n");
        stringBuilder.append(String.format("   %-29s = %s\n", "LatitudeDegrees = ", this.mLatitudeDegrees));
        stringBuilder.append(String.format("   %-29s = %s\n", "LongitudeDegrees = ", this.mLongitudeDegrees));
        stringBuilder.append(String.format("   %-29s = %s\n", "AltitudeMeters = ", this.mAltitudeMeters));
        stringBuilder.append(String.format("   %-29s = %s\n", "AzimuthDegrees = ", this.mAzimuthDegrees));
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeDouble(this.mLatitudeDegrees);
        parcel.writeDouble(this.mLongitudeDegrees);
        parcel.writeDouble(this.mAltitudeMeters);
        parcel.writeDouble(this.mAzimuthDegrees);
    }

    public static final class Builder {
        private double mAltitudeMeters;
        private double mAzimuthDegrees;
        private double mLatitudeDegrees;
        private double mLongitudeDegrees;

        public GnssReflectingPlane build() {
            return new GnssReflectingPlane(this);
        }

        public Builder setAltitudeMeters(double d) {
            this.mAltitudeMeters = d;
            return this;
        }

        public Builder setAzimuthDegrees(double d) {
            this.mAzimuthDegrees = d;
            return this;
        }

        public Builder setLatitudeDegrees(double d) {
            this.mLatitudeDegrees = d;
            return this;
        }

        public Builder setLongitudeDegrees(double d) {
            this.mLongitudeDegrees = d;
            return this;
        }
    }

}

