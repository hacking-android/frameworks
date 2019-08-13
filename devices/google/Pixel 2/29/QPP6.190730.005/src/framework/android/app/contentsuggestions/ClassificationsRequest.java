/*
 * Decompiled with CFR 0.145.
 */
package android.app.contentsuggestions;

import android.annotation.SystemApi;
import android.app.contentsuggestions.ContentSelection;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

@SystemApi
public final class ClassificationsRequest
implements Parcelable {
    public static final Parcelable.Creator<ClassificationsRequest> CREATOR = new Parcelable.Creator<ClassificationsRequest>(){

        @Override
        public ClassificationsRequest createFromParcel(Parcel parcel) {
            return new ClassificationsRequest(parcel.createTypedArrayList(ContentSelection.CREATOR), parcel.readBundle());
        }

        public ClassificationsRequest[] newArray(int n) {
            return new ClassificationsRequest[n];
        }
    };
    private final Bundle mExtras;
    private final List<ContentSelection> mSelections;

    private ClassificationsRequest(List<ContentSelection> list, Bundle bundle) {
        this.mSelections = list;
        this.mExtras = bundle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Bundle getExtras() {
        Bundle bundle;
        Bundle bundle2 = bundle = this.mExtras;
        if (bundle == null) {
            bundle2 = new Bundle();
        }
        return bundle2;
    }

    public List<ContentSelection> getSelections() {
        return this.mSelections;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeTypedList(this.mSelections);
        parcel.writeBundle(this.mExtras);
    }

    @SystemApi
    public static final class Builder {
        private Bundle mExtras;
        private final List<ContentSelection> mSelections;

        public Builder(List<ContentSelection> list) {
            this.mSelections = list;
        }

        public ClassificationsRequest build() {
            return new ClassificationsRequest(this.mSelections, this.mExtras);
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }
    }

}

