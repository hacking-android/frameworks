/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.annotation.SystemApi;
import android.location.GpsClock;
import android.location.GpsMeasurement;
import android.os.Parcel;
import android.os.Parcelable;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

@SystemApi
public class GpsMeasurementsEvent
implements Parcelable {
    public static final Parcelable.Creator<GpsMeasurementsEvent> CREATOR = new Parcelable.Creator<GpsMeasurementsEvent>(){

        @Override
        public GpsMeasurementsEvent createFromParcel(Parcel parcel) {
            GpsClock gpsClock = (GpsClock)parcel.readParcelable(this.getClass().getClassLoader());
            GpsMeasurement[] arrgpsMeasurement = new GpsMeasurement[parcel.readInt()];
            parcel.readTypedArray(arrgpsMeasurement, GpsMeasurement.CREATOR);
            return new GpsMeasurementsEvent(gpsClock, arrgpsMeasurement);
        }

        public GpsMeasurementsEvent[] newArray(int n) {
            return new GpsMeasurementsEvent[n];
        }
    };
    public static final int STATUS_GPS_LOCATION_DISABLED = 2;
    public static final int STATUS_NOT_SUPPORTED = 0;
    public static final int STATUS_READY = 1;
    private final GpsClock mClock;
    private final Collection<GpsMeasurement> mReadOnlyMeasurements;

    public GpsMeasurementsEvent(GpsClock gpsClock, GpsMeasurement[] arrgpsMeasurement) {
        if (gpsClock != null) {
            if (arrgpsMeasurement != null && arrgpsMeasurement.length != 0) {
                this.mClock = gpsClock;
                this.mReadOnlyMeasurements = Collections.unmodifiableCollection(Arrays.asList(arrgpsMeasurement));
                return;
            }
            throw new InvalidParameterException("Parameter 'measurements' must not be null or empty.");
        }
        throw new InvalidParameterException("Parameter 'clock' must not be null.");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public GpsClock getClock() {
        return this.mClock;
    }

    public Collection<GpsMeasurement> getMeasurements() {
        return this.mReadOnlyMeasurements;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[ GpsMeasurementsEvent:\n\n");
        stringBuilder.append(this.mClock.toString());
        stringBuilder.append("\n");
        Iterator<GpsMeasurement> iterator = this.mReadOnlyMeasurements.iterator();
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
        Parcelable[] arrparcelable = this.mReadOnlyMeasurements.toArray(new GpsMeasurement[n2]);
        parcel.writeInt(arrparcelable.length);
        parcel.writeTypedArray(arrparcelable, n);
    }

    @SystemApi
    public static interface Listener {
        public void onGpsMeasurementsReceived(GpsMeasurementsEvent var1);

        public void onStatusChanged(int var1);
    }

}

