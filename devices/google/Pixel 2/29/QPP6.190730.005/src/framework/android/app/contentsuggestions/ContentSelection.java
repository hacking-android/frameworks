/*
 * Decompiled with CFR 0.145.
 */
package android.app.contentsuggestions;

import android.annotation.SystemApi;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public final class ContentSelection
implements Parcelable {
    public static final Parcelable.Creator<ContentSelection> CREATOR = new Parcelable.Creator<ContentSelection>(){

        @Override
        public ContentSelection createFromParcel(Parcel parcel) {
            return new ContentSelection(parcel.readString(), parcel.readBundle());
        }

        public ContentSelection[] newArray(int n) {
            return new ContentSelection[n];
        }
    };
    private final Bundle mExtras;
    private final String mSelectionId;

    public ContentSelection(String string2, Bundle bundle) {
        this.mSelectionId = string2;
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
        return this.mSelectionId;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mSelectionId);
        parcel.writeBundle(this.mExtras);
    }

}

