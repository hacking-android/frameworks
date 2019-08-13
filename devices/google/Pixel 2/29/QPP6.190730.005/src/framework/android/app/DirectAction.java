/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.content.LocusId;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;

public final class DirectAction
implements Parcelable {
    public static final Parcelable.Creator<DirectAction> CREATOR = new Parcelable.Creator<DirectAction>(){

        @Override
        public DirectAction createFromParcel(Parcel parcel) {
            return new DirectAction(parcel);
        }

        public DirectAction[] newArray(int n) {
            return new DirectAction[n];
        }
    };
    public static final String KEY_ACTIONS_LIST = "actions_list";
    private IBinder mActivityId;
    private final Bundle mExtras;
    private final String mID;
    private final LocusId mLocusId;
    private int mTaskId;

    public DirectAction(DirectAction directAction) {
        this.mTaskId = directAction.mTaskId;
        this.mActivityId = directAction.mActivityId;
        this.mID = directAction.mID;
        this.mExtras = directAction.mExtras;
        this.mLocusId = directAction.mLocusId;
    }

    private DirectAction(Parcel object) {
        this.mTaskId = ((Parcel)object).readInt();
        this.mActivityId = ((Parcel)object).readStrongBinder();
        this.mID = ((Parcel)object).readString();
        this.mExtras = ((Parcel)object).readBundle();
        object = ((Parcel)object).readString();
        object = object != null ? new LocusId((String)object) : null;
        this.mLocusId = object;
    }

    public DirectAction(String string2, Bundle bundle, LocusId locusId) {
        this.mID = Preconditions.checkStringNotEmpty(string2);
        this.mExtras = bundle;
        this.mLocusId = locusId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object == this) {
            return true;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        return this.mID.equals(((DirectAction)object).mID);
    }

    public IBinder getActivityId() {
        return this.mActivityId;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public String getId() {
        return this.mID;
    }

    public LocusId getLocusId() {
        return this.mLocusId;
    }

    public int getTaskId() {
        return this.mTaskId;
    }

    public int hashCode() {
        return this.mID.hashCode();
    }

    public void setSource(int n, IBinder iBinder) {
        this.mTaskId = n;
        this.mActivityId = iBinder;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mTaskId);
        parcel.writeStrongBinder(this.mActivityId);
        parcel.writeString(this.mID);
        parcel.writeBundle(this.mExtras);
        parcel.writeString(this.mLocusId.getId());
    }

    public static final class Builder {
        private Bundle mExtras;
        private String mId;
        private LocusId mLocusId;

        public Builder(String string2) {
            Preconditions.checkNotNull(string2);
            this.mId = string2;
        }

        public DirectAction build() {
            return new DirectAction(this.mId, this.mExtras, this.mLocusId);
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public Builder setLocusId(LocusId locusId) {
            this.mLocusId = locusId;
            return this;
        }
    }

}

