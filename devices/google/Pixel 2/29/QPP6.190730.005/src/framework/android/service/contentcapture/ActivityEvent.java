/*
 * Decompiled with CFR 0.145.
 */
package android.service.contentcapture;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
public final class ActivityEvent
implements Parcelable {
    public static final Parcelable.Creator<ActivityEvent> CREATOR = new Parcelable.Creator<ActivityEvent>(){

        @Override
        public ActivityEvent createFromParcel(Parcel parcel) {
            return new ActivityEvent((ComponentName)parcel.readParcelable(null), parcel.readInt());
        }

        public ActivityEvent[] newArray(int n) {
            return new ActivityEvent[n];
        }
    };
    public static final int TYPE_ACTIVITY_DESTROYED = 24;
    public static final int TYPE_ACTIVITY_PAUSED = 2;
    public static final int TYPE_ACTIVITY_RESUMED = 1;
    public static final int TYPE_ACTIVITY_STOPPED = 23;
    private final ComponentName mComponentName;
    private final int mType;

    public ActivityEvent(ComponentName componentName, int n) {
        this.mComponentName = componentName;
        this.mType = n;
    }

    public static String getTypeAsString(int n) {
        if (n != 1) {
            if (n != 2) {
                if (n != 23) {
                    if (n != 24) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("UKNOWN_TYPE: ");
                        stringBuilder.append(n);
                        return stringBuilder.toString();
                    }
                    return "ACTIVITY_DESTROYED";
                }
                return "ACTIVITY_STOPPED";
            }
            return "ACTIVITY_PAUSED";
        }
        return "ACTIVITY_RESUMED";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public ComponentName getComponentName() {
        return this.mComponentName;
    }

    public int getEventType() {
        return this.mType;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("ActivityEvent[");
        stringBuilder.append(this.mComponentName.toShortString());
        stringBuilder.append("]:");
        stringBuilder.append(ActivityEvent.getTypeAsString(this.mType));
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mComponentName, n);
        parcel.writeInt(this.mType);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ActivityEventType {
    }

}

