/*
 * Decompiled with CFR 0.145.
 */
package android.app.prediction;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public final class AppPredictionSessionId
implements Parcelable {
    public static final Parcelable.Creator<AppPredictionSessionId> CREATOR = new Parcelable.Creator<AppPredictionSessionId>(){

        @Override
        public AppPredictionSessionId createFromParcel(Parcel parcel) {
            return new AppPredictionSessionId(parcel);
        }

        public AppPredictionSessionId[] newArray(int n) {
            return new AppPredictionSessionId[n];
        }
    };
    private final String mId;

    private AppPredictionSessionId(Parcel parcel) {
        this.mId = parcel.readString();
    }

    public AppPredictionSessionId(String string2) {
        this.mId = string2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        Class<?> class_;
        Class<?> class_2 = this.getClass();
        if (!class_2.equals(class_ = object != null ? object.getClass() : null)) {
            return false;
        }
        object = (AppPredictionSessionId)object;
        return this.mId.equals(((AppPredictionSessionId)object).mId);
    }

    public int hashCode() {
        return this.mId.hashCode();
    }

    public String toString() {
        return this.mId;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mId);
    }

}

