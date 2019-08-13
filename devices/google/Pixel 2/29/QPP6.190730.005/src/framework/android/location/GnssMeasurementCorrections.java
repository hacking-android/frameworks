/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.annotation.SystemApi;
import android.location.GnssSingleSatCorrection;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@SystemApi
public final class GnssMeasurementCorrections
implements Parcelable {
    public static final Parcelable.Creator<GnssMeasurementCorrections> CREATOR = new Parcelable.Creator<GnssMeasurementCorrections>(){

        @Override
        public GnssMeasurementCorrections createFromParcel(Parcel parcel) {
            Builder builder = new Builder().setLatitudeDegrees(parcel.readDouble()).setLongitudeDegrees(parcel.readDouble()).setAltitudeMeters(parcel.readDouble()).setHorizontalPositionUncertaintyMeters(parcel.readDouble()).setVerticalPositionUncertaintyMeters(parcel.readDouble()).setToaGpsNanosecondsOfWeek(parcel.readLong());
            ArrayList<GnssSingleSatCorrection> arrayList = new ArrayList<GnssSingleSatCorrection>();
            parcel.readTypedList(arrayList, GnssSingleSatCorrection.CREATOR);
            builder.setSingleSatelliteCorrectionList(arrayList);
            return builder.build();
        }

        public GnssMeasurementCorrections[] newArray(int n) {
            return new GnssMeasurementCorrections[n];
        }
    };
    private final double mAltitudeMeters;
    private final double mHorizontalPositionUncertaintyMeters;
    private final double mLatitudeDegrees;
    private final double mLongitudeDegrees;
    private final List<GnssSingleSatCorrection> mSingleSatCorrectionList;
    private final long mToaGpsNanosecondsOfWeek;
    private final double mVerticalPositionUncertaintyMeters;

    private GnssMeasurementCorrections(Builder object) {
        this.mLatitudeDegrees = ((Builder)object).mLatitudeDegrees;
        this.mLongitudeDegrees = ((Builder)object).mLongitudeDegrees;
        this.mAltitudeMeters = ((Builder)object).mAltitudeMeters;
        this.mHorizontalPositionUncertaintyMeters = ((Builder)object).mHorizontalPositionUncertaintyMeters;
        this.mVerticalPositionUncertaintyMeters = ((Builder)object).mVerticalPositionUncertaintyMeters;
        this.mToaGpsNanosecondsOfWeek = ((Builder)object).mToaGpsNanosecondsOfWeek;
        object = ((Builder)object).mSingleSatCorrectionList;
        boolean bl = object != null && !object.isEmpty();
        Preconditions.checkArgument(bl);
        this.mSingleSatCorrectionList = Collections.unmodifiableList(new ArrayList(object));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public double getAltitudeMeters() {
        return this.mAltitudeMeters;
    }

    public double getHorizontalPositionUncertaintyMeters() {
        return this.mHorizontalPositionUncertaintyMeters;
    }

    public double getLatitudeDegrees() {
        return this.mLatitudeDegrees;
    }

    public double getLongitudeDegrees() {
        return this.mLongitudeDegrees;
    }

    public List<GnssSingleSatCorrection> getSingleSatelliteCorrectionList() {
        return this.mSingleSatCorrectionList;
    }

    public long getToaGpsNanosecondsOfWeek() {
        return this.mToaGpsNanosecondsOfWeek;
    }

    public double getVerticalPositionUncertaintyMeters() {
        return this.mVerticalPositionUncertaintyMeters;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("GnssMeasurementCorrections:\n");
        stringBuilder.append(String.format("   %-29s = %s\n", "LatitudeDegrees = ", this.mLatitudeDegrees));
        stringBuilder.append(String.format("   %-29s = %s\n", "LongitudeDegrees = ", this.mLongitudeDegrees));
        stringBuilder.append(String.format("   %-29s = %s\n", "AltitudeMeters = ", this.mAltitudeMeters));
        stringBuilder.append(String.format("   %-29s = %s\n", "HorizontalPositionUncertaintyMeters = ", this.mHorizontalPositionUncertaintyMeters));
        stringBuilder.append(String.format("   %-29s = %s\n", "VerticalPositionUncertaintyMeters = ", this.mVerticalPositionUncertaintyMeters));
        stringBuilder.append(String.format("   %-29s = %s\n", "ToaGpsNanosecondsOfWeek = ", this.mToaGpsNanosecondsOfWeek));
        stringBuilder.append(String.format("   %-29s = %s\n", "mSingleSatCorrectionList = ", this.mSingleSatCorrectionList));
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeDouble(this.mLatitudeDegrees);
        parcel.writeDouble(this.mLongitudeDegrees);
        parcel.writeDouble(this.mAltitudeMeters);
        parcel.writeDouble(this.mHorizontalPositionUncertaintyMeters);
        parcel.writeDouble(this.mVerticalPositionUncertaintyMeters);
        parcel.writeLong(this.mToaGpsNanosecondsOfWeek);
        parcel.writeTypedList(this.mSingleSatCorrectionList);
    }

    public static final class Builder {
        private double mAltitudeMeters;
        private double mHorizontalPositionUncertaintyMeters;
        private double mLatitudeDegrees;
        private double mLongitudeDegrees;
        private List<GnssSingleSatCorrection> mSingleSatCorrectionList;
        private long mToaGpsNanosecondsOfWeek;
        private double mVerticalPositionUncertaintyMeters;

        public GnssMeasurementCorrections build() {
            return new GnssMeasurementCorrections(this);
        }

        public Builder setAltitudeMeters(double d) {
            this.mAltitudeMeters = d;
            return this;
        }

        public Builder setHorizontalPositionUncertaintyMeters(double d) {
            this.mHorizontalPositionUncertaintyMeters = d;
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

        public Builder setSingleSatelliteCorrectionList(List<GnssSingleSatCorrection> list) {
            this.mSingleSatCorrectionList = list;
            return this;
        }

        public Builder setToaGpsNanosecondsOfWeek(long l) {
            this.mToaGpsNanosecondsOfWeek = l;
            return this;
        }

        public Builder setVerticalPositionUncertaintyMeters(double d) {
            this.mVerticalPositionUncertaintyMeters = d;
            return this;
        }
    }

}

