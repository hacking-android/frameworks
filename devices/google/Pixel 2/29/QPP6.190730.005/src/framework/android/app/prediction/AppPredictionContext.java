/*
 * Decompiled with CFR 0.145.
 */
package android.app.prediction;

import android.annotation.SystemApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public final class AppPredictionContext
implements Parcelable {
    public static final Parcelable.Creator<AppPredictionContext> CREATOR = new Parcelable.Creator<AppPredictionContext>(){

        @Override
        public AppPredictionContext createFromParcel(Parcel parcel) {
            return new AppPredictionContext(parcel);
        }

        public AppPredictionContext[] newArray(int n) {
            return new AppPredictionContext[n];
        }
    };
    private final Bundle mExtras;
    private final String mPackageName;
    private final int mPredictedTargetCount;
    private final String mUiSurface;

    private AppPredictionContext(Parcel parcel) {
        this.mUiSurface = parcel.readString();
        this.mPredictedTargetCount = parcel.readInt();
        this.mPackageName = parcel.readString();
        this.mExtras = parcel.readBundle();
    }

    private AppPredictionContext(String string2, int n, String string3, Bundle bundle) {
        this.mUiSurface = string2;
        this.mPredictedTargetCount = n;
        this.mPackageName = string3;
        this.mExtras = bundle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        Class<?> class_;
        boolean bl = true;
        if (object == this) {
            return true;
        }
        Class<?> class_2 = this.getClass();
        if (!class_2.equals(class_ = object != null ? object.getClass() : null)) {
            return false;
        }
        object = (AppPredictionContext)object;
        if (this.mPredictedTargetCount != ((AppPredictionContext)object).mPredictedTargetCount || !this.mUiSurface.equals(((AppPredictionContext)object).mUiSurface) || !this.mPackageName.equals(((AppPredictionContext)object).mPackageName)) {
            bl = false;
        }
        return bl;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public int getPredictedTargetCount() {
        return this.mPredictedTargetCount;
    }

    public String getUiSurface() {
        return this.mUiSurface;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mUiSurface);
        parcel.writeInt(this.mPredictedTargetCount);
        parcel.writeString(this.mPackageName);
        parcel.writeBundle(this.mExtras);
    }

    @SystemApi
    public static final class Builder {
        private Bundle mExtras;
        private final String mPackageName;
        private int mPredictedTargetCount;
        private String mUiSurface;

        @SystemApi
        public Builder(Context context) {
            this.mPackageName = context.getPackageName();
        }

        public AppPredictionContext build() {
            return new AppPredictionContext(this.mUiSurface, this.mPredictedTargetCount, this.mPackageName, this.mExtras);
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public Builder setPredictedTargetCount(int n) {
            this.mPredictedTargetCount = n;
            return this;
        }

        public Builder setUiSurface(String string2) {
            this.mUiSurface = string2;
            return this;
        }
    }

}

