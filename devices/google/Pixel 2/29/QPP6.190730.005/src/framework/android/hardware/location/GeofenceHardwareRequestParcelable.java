/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.hardware.location.GeofenceHardwareRequest;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public final class GeofenceHardwareRequestParcelable
implements Parcelable {
    public static final Parcelable.Creator<GeofenceHardwareRequestParcelable> CREATOR = new Parcelable.Creator<GeofenceHardwareRequestParcelable>(){

        @Override
        public GeofenceHardwareRequestParcelable createFromParcel(Parcel parcel) {
            int n = parcel.readInt();
            if (n != 0) {
                Log.e("GeofenceHardwareRequest", String.format("Invalid Geofence type: %d", n));
                return null;
            }
            GeofenceHardwareRequest geofenceHardwareRequest = GeofenceHardwareRequest.createCircularGeofence(parcel.readDouble(), parcel.readDouble(), parcel.readDouble());
            geofenceHardwareRequest.setLastTransition(parcel.readInt());
            geofenceHardwareRequest.setMonitorTransitions(parcel.readInt());
            geofenceHardwareRequest.setUnknownTimer(parcel.readInt());
            geofenceHardwareRequest.setNotificationResponsiveness(parcel.readInt());
            geofenceHardwareRequest.setSourceTechnologies(parcel.readInt());
            return new GeofenceHardwareRequestParcelable(parcel.readInt(), geofenceHardwareRequest);
        }

        public GeofenceHardwareRequestParcelable[] newArray(int n) {
            return new GeofenceHardwareRequestParcelable[n];
        }
    };
    private int mId;
    private GeofenceHardwareRequest mRequest;

    public GeofenceHardwareRequestParcelable(int n, GeofenceHardwareRequest geofenceHardwareRequest) {
        this.mId = n;
        this.mRequest = geofenceHardwareRequest;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getId() {
        return this.mId;
    }

    public int getLastTransition() {
        return this.mRequest.getLastTransition();
    }

    public double getLatitude() {
        return this.mRequest.getLatitude();
    }

    public double getLongitude() {
        return this.mRequest.getLongitude();
    }

    public int getMonitorTransitions() {
        return this.mRequest.getMonitorTransitions();
    }

    public int getNotificationResponsiveness() {
        return this.mRequest.getNotificationResponsiveness();
    }

    public double getRadius() {
        return this.mRequest.getRadius();
    }

    int getSourceTechnologies() {
        return this.mRequest.getSourceTechnologies();
    }

    int getType() {
        return this.mRequest.getType();
    }

    public int getUnknownTimer() {
        return this.mRequest.getUnknownTimer();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("id=");
        stringBuilder.append(this.mId);
        stringBuilder.append(", type=");
        stringBuilder.append(this.mRequest.getType());
        stringBuilder.append(", latitude=");
        stringBuilder.append(this.mRequest.getLatitude());
        stringBuilder.append(", longitude=");
        stringBuilder.append(this.mRequest.getLongitude());
        stringBuilder.append(", radius=");
        stringBuilder.append(this.mRequest.getRadius());
        stringBuilder.append(", lastTransition=");
        stringBuilder.append(this.mRequest.getLastTransition());
        stringBuilder.append(", unknownTimer=");
        stringBuilder.append(this.mRequest.getUnknownTimer());
        stringBuilder.append(", monitorTransitions=");
        stringBuilder.append(this.mRequest.getMonitorTransitions());
        stringBuilder.append(", notificationResponsiveness=");
        stringBuilder.append(this.mRequest.getNotificationResponsiveness());
        stringBuilder.append(", sourceTechnologies=");
        stringBuilder.append(this.mRequest.getSourceTechnologies());
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.getType());
        parcel.writeDouble(this.getLatitude());
        parcel.writeDouble(this.getLongitude());
        parcel.writeDouble(this.getRadius());
        parcel.writeInt(this.getLastTransition());
        parcel.writeInt(this.getMonitorTransitions());
        parcel.writeInt(this.getUnknownTimer());
        parcel.writeInt(this.getNotificationResponsiveness());
        parcel.writeInt(this.getSourceTechnologies());
        parcel.writeInt(this.getId());
    }

}

