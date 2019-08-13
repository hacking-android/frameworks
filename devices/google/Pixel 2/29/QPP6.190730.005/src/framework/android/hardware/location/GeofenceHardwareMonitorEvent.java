/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.annotation.SystemApi;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public class GeofenceHardwareMonitorEvent
implements Parcelable {
    public static final Parcelable.Creator<GeofenceHardwareMonitorEvent> CREATOR = new Parcelable.Creator<GeofenceHardwareMonitorEvent>(){

        @Override
        public GeofenceHardwareMonitorEvent createFromParcel(Parcel parcel) {
            ClassLoader classLoader = GeofenceHardwareMonitorEvent.class.getClassLoader();
            return new GeofenceHardwareMonitorEvent(parcel.readInt(), parcel.readInt(), parcel.readInt(), (Location)parcel.readParcelable(classLoader));
        }

        public GeofenceHardwareMonitorEvent[] newArray(int n) {
            return new GeofenceHardwareMonitorEvent[n];
        }
    };
    private final Location mLocation;
    private final int mMonitoringStatus;
    private final int mMonitoringType;
    private final int mSourceTechnologies;

    public GeofenceHardwareMonitorEvent(int n, int n2, int n3, Location location) {
        this.mMonitoringType = n;
        this.mMonitoringStatus = n2;
        this.mSourceTechnologies = n3;
        this.mLocation = location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Location getLocation() {
        return this.mLocation;
    }

    public int getMonitoringStatus() {
        return this.mMonitoringStatus;
    }

    public int getMonitoringType() {
        return this.mMonitoringType;
    }

    public int getSourceTechnologies() {
        return this.mSourceTechnologies;
    }

    public String toString() {
        return String.format("GeofenceHardwareMonitorEvent: type=%d, status=%d, sources=%d, location=%s", this.mMonitoringType, this.mMonitoringStatus, this.mSourceTechnologies, this.mLocation);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mMonitoringType);
        parcel.writeInt(this.mMonitoringStatus);
        parcel.writeInt(this.mSourceTechnologies);
        parcel.writeParcelable(this.mLocation, n);
    }

}

