/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.annotation.SystemApi;
import android.location.GpsNavigationMessage;
import android.os.Parcel;
import android.os.Parcelable;
import java.security.InvalidParameterException;

@SystemApi
public class GpsNavigationMessageEvent
implements Parcelable {
    public static final Parcelable.Creator<GpsNavigationMessageEvent> CREATOR;
    public static int STATUS_GPS_LOCATION_DISABLED;
    public static int STATUS_NOT_SUPPORTED;
    public static int STATUS_READY;
    private final GpsNavigationMessage mNavigationMessage;

    static {
        STATUS_NOT_SUPPORTED = 0;
        STATUS_READY = 1;
        STATUS_GPS_LOCATION_DISABLED = 2;
        CREATOR = new Parcelable.Creator<GpsNavigationMessageEvent>(){

            @Override
            public GpsNavigationMessageEvent createFromParcel(Parcel parcel) {
                return new GpsNavigationMessageEvent((GpsNavigationMessage)parcel.readParcelable(this.getClass().getClassLoader()));
            }

            public GpsNavigationMessageEvent[] newArray(int n) {
                return new GpsNavigationMessageEvent[n];
            }
        };
    }

    public GpsNavigationMessageEvent(GpsNavigationMessage gpsNavigationMessage) {
        if (gpsNavigationMessage != null) {
            this.mNavigationMessage = gpsNavigationMessage;
            return;
        }
        throw new InvalidParameterException("Parameter 'message' must not be null.");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public GpsNavigationMessage getNavigationMessage() {
        return this.mNavigationMessage;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[ GpsNavigationMessageEvent:\n\n");
        stringBuilder.append(this.mNavigationMessage.toString());
        stringBuilder.append("\n]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mNavigationMessage, n);
    }

    @SystemApi
    public static interface Listener {
        public void onGpsNavigationMessageReceived(GpsNavigationMessageEvent var1);

        public void onStatusChanged(int var1);
    }

}

