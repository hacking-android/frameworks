/*
 * Decompiled with CFR 0.145.
 */
package android.app.prediction;

import android.annotation.SystemApi;
import android.app.prediction.AppTarget;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
public final class AppTargetEvent
implements Parcelable {
    public static final int ACTION_DISMISS = 2;
    public static final int ACTION_LAUNCH = 1;
    public static final int ACTION_PIN = 3;
    public static final Parcelable.Creator<AppTargetEvent> CREATOR = new Parcelable.Creator<AppTargetEvent>(){

        @Override
        public AppTargetEvent createFromParcel(Parcel parcel) {
            return new AppTargetEvent(parcel);
        }

        public AppTargetEvent[] newArray(int n) {
            return new AppTargetEvent[n];
        }
    };
    private final int mAction;
    private final String mLocation;
    private final AppTarget mTarget;

    private AppTargetEvent(AppTarget appTarget, String string2, int n) {
        this.mTarget = appTarget;
        this.mLocation = string2;
        this.mAction = n;
    }

    private AppTargetEvent(Parcel parcel) {
        this.mTarget = (AppTarget)parcel.readParcelable(null);
        this.mLocation = parcel.readString();
        this.mAction = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl;
        block1 : {
            Class<?> class_ = this.getClass();
            Class<?> class_2 = object != null ? object.getClass() : null;
            boolean bl2 = class_.equals(class_2);
            bl = false;
            if (!bl2) {
                return false;
            }
            object = (AppTargetEvent)object;
            if (!this.mTarget.equals(((AppTargetEvent)object).mTarget) || !this.mLocation.equals(((AppTargetEvent)object).mLocation) || this.mAction != ((AppTargetEvent)object).mAction) break block1;
            bl = true;
        }
        return bl;
    }

    public int getAction() {
        return this.mAction;
    }

    public String getLaunchLocation() {
        return this.mLocation;
    }

    public AppTarget getTarget() {
        return this.mTarget;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mTarget, 0);
        parcel.writeString(this.mLocation);
        parcel.writeInt(this.mAction);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ActionType {
    }

    @SystemApi
    public static final class Builder {
        private int mAction;
        private String mLocation;
        private AppTarget mTarget;

        public Builder(AppTarget appTarget, int n) {
            this.mTarget = appTarget;
            this.mAction = n;
        }

        public AppTargetEvent build() {
            return new AppTargetEvent(this.mTarget, this.mLocation, this.mAction);
        }

        public Builder setLaunchLocation(String string2) {
            this.mLocation = string2;
            return this;
        }
    }

}

