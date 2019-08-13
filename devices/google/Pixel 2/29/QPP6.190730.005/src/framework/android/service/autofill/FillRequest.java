/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.service.autofill.FillContext;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public final class FillRequest
implements Parcelable {
    public static final Parcelable.Creator<FillRequest> CREATOR = new Parcelable.Creator<FillRequest>(){

        @Override
        public FillRequest createFromParcel(Parcel parcel) {
            return new FillRequest(parcel);
        }

        public FillRequest[] newArray(int n) {
            return new FillRequest[n];
        }
    };
    public static final int FLAG_COMPATIBILITY_MODE_REQUEST = 2;
    public static final int FLAG_MANUAL_REQUEST = 1;
    public static final int INVALID_REQUEST_ID = Integer.MIN_VALUE;
    private final Bundle mClientState;
    private final ArrayList<FillContext> mContexts;
    private final int mFlags;
    private final int mId;

    public FillRequest(int n, ArrayList<FillContext> arrayList, Bundle bundle, int n2) {
        this.mId = n;
        this.mFlags = Preconditions.checkFlagsArgument(n2, 3);
        this.mContexts = Preconditions.checkCollectionElementsNotNull(arrayList, "contexts");
        this.mClientState = bundle;
    }

    private FillRequest(Parcel parcel) {
        this.mId = parcel.readInt();
        this.mContexts = new ArrayList();
        parcel.readParcelableList(this.mContexts, null);
        this.mClientState = parcel.readBundle();
        this.mFlags = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Bundle getClientState() {
        return this.mClientState;
    }

    public List<FillContext> getFillContexts() {
        return this.mContexts;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public int getId() {
        return this.mId;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("FillRequest: [id=");
        stringBuilder.append(this.mId);
        stringBuilder.append(", flags=");
        stringBuilder.append(this.mFlags);
        stringBuilder.append(", ctxts= ");
        stringBuilder.append(this.mContexts);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mId);
        parcel.writeParcelableList(this.mContexts, n);
        parcel.writeBundle(this.mClientState);
        parcel.writeInt(this.mFlags);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    static @interface RequestFlags {
    }

}

