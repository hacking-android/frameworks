/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.service.autofill.FillContext;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.List;

public final class SaveRequest
implements Parcelable {
    public static final Parcelable.Creator<SaveRequest> CREATOR = new Parcelable.Creator<SaveRequest>(){

        @Override
        public SaveRequest createFromParcel(Parcel parcel) {
            return new SaveRequest(parcel);
        }

        public SaveRequest[] newArray(int n) {
            return new SaveRequest[n];
        }
    };
    private final Bundle mClientState;
    private final ArrayList<String> mDatasetIds;
    private final ArrayList<FillContext> mFillContexts;

    private SaveRequest(Parcel parcel) {
        this(parcel.createTypedArrayList(FillContext.CREATOR), parcel.readBundle(), parcel.createStringArrayList());
    }

    public SaveRequest(ArrayList<FillContext> arrayList, Bundle bundle, ArrayList<String> arrayList2) {
        this.mFillContexts = Preconditions.checkNotNull(arrayList, "fillContexts");
        this.mClientState = bundle;
        this.mDatasetIds = arrayList2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Bundle getClientState() {
        return this.mClientState;
    }

    public List<String> getDatasetIds() {
        return this.mDatasetIds;
    }

    public List<FillContext> getFillContexts() {
        return this.mFillContexts;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeTypedList(this.mFillContexts, n);
        parcel.writeBundle(this.mClientState);
        parcel.writeStringList(this.mDatasetIds);
    }

}

