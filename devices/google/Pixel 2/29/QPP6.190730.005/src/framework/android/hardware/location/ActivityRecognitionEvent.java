/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.os.Parcel;
import android.os.Parcelable;

public class ActivityRecognitionEvent
implements Parcelable {
    public static final Parcelable.Creator<ActivityRecognitionEvent> CREATOR = new Parcelable.Creator<ActivityRecognitionEvent>(){

        @Override
        public ActivityRecognitionEvent createFromParcel(Parcel parcel) {
            return new ActivityRecognitionEvent(parcel.readString(), parcel.readInt(), parcel.readLong());
        }

        public ActivityRecognitionEvent[] newArray(int n) {
            return new ActivityRecognitionEvent[n];
        }
    };
    private final String mActivity;
    private final int mEventType;
    private final long mTimestampNs;

    public ActivityRecognitionEvent(String string2, int n, long l) {
        this.mActivity = string2;
        this.mEventType = n;
        this.mTimestampNs = l;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getActivity() {
        return this.mActivity;
    }

    public int getEventType() {
        return this.mEventType;
    }

    public long getTimestampNs() {
        return this.mTimestampNs;
    }

    public String toString() {
        return String.format("Activity='%s', EventType=%s, TimestampNs=%s", this.mActivity, this.mEventType, this.mTimestampNs);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mActivity);
        parcel.writeInt(this.mEventType);
        parcel.writeLong(this.mTimestampNs);
    }

}

