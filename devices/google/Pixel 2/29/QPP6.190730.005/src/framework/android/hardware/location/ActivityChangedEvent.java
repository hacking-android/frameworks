/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.hardware.location.ActivityRecognitionEvent;
import android.os.Parcel;
import android.os.Parcelable;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;

public class ActivityChangedEvent
implements Parcelable {
    public static final Parcelable.Creator<ActivityChangedEvent> CREATOR = new Parcelable.Creator<ActivityChangedEvent>(){

        @Override
        public ActivityChangedEvent createFromParcel(Parcel parcel) {
            ActivityRecognitionEvent[] arractivityRecognitionEvent = new ActivityRecognitionEvent[parcel.readInt()];
            parcel.readTypedArray(arractivityRecognitionEvent, ActivityRecognitionEvent.CREATOR);
            return new ActivityChangedEvent(arractivityRecognitionEvent);
        }

        public ActivityChangedEvent[] newArray(int n) {
            return new ActivityChangedEvent[n];
        }
    };
    private final List<ActivityRecognitionEvent> mActivityRecognitionEvents;

    public ActivityChangedEvent(ActivityRecognitionEvent[] arractivityRecognitionEvent) {
        if (arractivityRecognitionEvent != null) {
            this.mActivityRecognitionEvents = Arrays.asList(arractivityRecognitionEvent);
            return;
        }
        throw new InvalidParameterException("Parameter 'activityRecognitionEvents' must not be null.");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Iterable<ActivityRecognitionEvent> getActivityRecognitionEvents() {
        return this.mActivityRecognitionEvents;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[ ActivityChangedEvent:");
        for (ActivityRecognitionEvent activityRecognitionEvent : this.mActivityRecognitionEvents) {
            stringBuilder.append("\n    ");
            stringBuilder.append(activityRecognitionEvent.toString());
        }
        stringBuilder.append("\n]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        Parcelable[] arrparcelable = this.mActivityRecognitionEvents.toArray(new ActivityRecognitionEvent[0]);
        parcel.writeInt(arrparcelable.length);
        parcel.writeTypedArray(arrparcelable, n);
    }

}

