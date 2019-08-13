/*
 * Decompiled with CFR 0.145.
 */
package android.app.contentsuggestions;

import android.annotation.SystemApi;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public final class ContentClassification
implements Parcelable {
    public static final Parcelable.Creator<ContentClassification> CREATOR = new Parcelable.Creator<ContentClassification>(){

        @Override
        public ContentClassification createFromParcel(Parcel parcel) {
            return new ContentClassification(parcel.readString(), parcel.readBundle());
        }

        public ContentClassification[] newArray(int n) {
            return new ContentClassification[n];
        }
    };
    private final String mClassificationId;
    private final Bundle mExtras;

    public ContentClassification(String string2, Bundle bundle) {
        this.mClassificationId = string2;
        this.mExtras = bundle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public String getId() {
        return this.mClassificationId;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mClassificationId);
        parcel.writeBundle(this.mExtras);
    }

}

