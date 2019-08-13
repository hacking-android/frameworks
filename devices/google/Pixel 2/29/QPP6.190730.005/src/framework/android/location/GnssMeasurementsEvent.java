/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.location.GnssClock;
import android.location.GnssMeasurement;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public final class GnssMeasurementsEvent
implements Parcelable {
    public static final Parcelable.Creator<GnssMeasurementsEvent> CREATOR = new Parcelable.Creator<GnssMeasurementsEvent>(){

        @Override
        public GnssMeasurementsEvent createFromParcel(Parcel parcel) {
            GnssClock gnssClock = (GnssClock)parcel.readParcelable(this.getClass().getClassLoader());
            GnssMeasurement[] arrgnssMeasurement = new GnssMeasurement[parcel.readInt()];
            parcel.readTypedArray(arrgnssMeasurement, GnssMeasurement.CREATOR);
            return new GnssMeasurementsEvent(gnssClock, arrgnssMeasurement);
        }

        public GnssMeasurementsEvent[] newArray(int n) {
            return new GnssMeasurementsEvent[n];
        }
    };
    private final GnssClock mClock;
    private final Collection<GnssMeasurement> mReadOnlyMeasurements;

    public GnssMeasurementsEvent(GnssClock gnssClock, GnssMeasurement[] arrgnssMeasurement) {
        if (gnssClock != null) {
            this.mReadOnlyMeasurements = arrgnssMeasurement != null && arrgnssMeasurement.length != 0 ? Collections.unmodifiableCollection(Arrays.asList(arrgnssMeasurement)) : Collections.emptyList();
            this.mClock = gnssClock;
            return;
        }
        throw new InvalidParameterException("Parameter 'clock' must not be null.");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public GnssClock getClock() {
        return this.mClock;
    }

    public Collection<GnssMeasurement> getMeasurements() {
        return this.mReadOnlyMeasurements;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[ GnssMeasurementsEvent:\n\n");
        stringBuilder.append(this.mClock.toString());
        stringBuilder.append("\n");
        Iterator<GnssMeasurement> iterator = this.mReadOnlyMeasurements.iterator();
        while (iterator.hasNext()) {
            stringBuilder.append(iterator.next().toString());
            stringBuilder.append("\n");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mClock, n);
        int n2 = this.mReadOnlyMeasurements.size();
        Parcelable[] arrparcelable = this.mReadOnlyMeasurements.toArray(new GnssMeasurement[n2]);
        parcel.writeInt(arrparcelable.length);
        parcel.writeTypedArray(arrparcelable, n);
    }

    public static abstract class Callback {
        public static final int STATUS_LOCATION_DISABLED = 2;
        public static final int STATUS_NOT_ALLOWED = 3;
        public static final int STATUS_NOT_SUPPORTED = 0;
        public static final int STATUS_READY = 1;

        public void onGnssMeasurementsReceived(GnssMeasurementsEvent gnssMeasurementsEvent) {
        }

        public void onStatusChanged(int n) {
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface GnssMeasurementsStatus {
        }

    }

}

