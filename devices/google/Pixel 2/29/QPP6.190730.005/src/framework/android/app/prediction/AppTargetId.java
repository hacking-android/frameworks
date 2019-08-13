/*
 * Decompiled with CFR 0.145.
 */
package android.app.prediction;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public final class AppTargetId
implements Parcelable {
    public static final Parcelable.Creator<AppTargetId> CREATOR = new Parcelable.Creator<AppTargetId>(){

        @Override
        public AppTargetId createFromParcel(Parcel parcel) {
            return new AppTargetId(parcel);
        }

        public AppTargetId[] newArray(int n) {
            return new AppTargetId[n];
        }
    };
    private final String mId;

    private AppTargetId(Parcel parcel) {
        this.mId = parcel.readString();
    }

    @SystemApi
    public AppTargetId(String string2) {
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
        object = (AppTargetId)object;
        return this.mId.equals(((AppTargetId)object).mId);
    }

    public String getId() {
        return this.mId;
    }

    public int hashCode() {
        return this.mId.hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mId);
    }

}

