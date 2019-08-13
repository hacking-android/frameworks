/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill;

import android.os.Parcel;
import android.os.Parcelable;
import android.service.autofill.BatchUpdates;
import android.service.autofill.InternalOnClickAction;
import android.service.autofill.InternalTransformation;
import android.service.autofill.InternalValidator;
import android.service.autofill.OnClickAction;
import android.service.autofill.Transformation;
import android.service.autofill.Validator;
import android.util.Pair;
import android.util.SparseArray;
import android.view.autofill.Helper;
import android.widget.RemoteViews;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;

public final class CustomDescription
implements Parcelable {
    public static final Parcelable.Creator<CustomDescription> CREATOR = new Parcelable.Creator<CustomDescription>(){

        @Override
        public CustomDescription createFromParcel(Parcel arrinternalOnClickAction) {
            int n;
            int n2;
            Parcelable[] arrparcelable;
            Object object = (RemoteViews)arrinternalOnClickAction.readParcelable(null);
            if (object == null) {
                return null;
            }
            object = new Builder((RemoteViews)object);
            Object[] arrobject = arrinternalOnClickAction.createIntArray();
            if (arrobject != null) {
                arrparcelable = (InternalTransformation[])arrinternalOnClickAction.readParcelableArray(null, InternalTransformation.class);
                n = arrobject.length;
                for (n2 = 0; n2 < n; ++n2) {
                    ((Builder)object).addChild(arrobject[n2], (Transformation)((Object)arrparcelable[n2]));
                }
            }
            if ((arrparcelable = (InternalValidator[])arrinternalOnClickAction.readParcelableArray(null, InternalValidator.class)) != null) {
                arrobject = (BatchUpdates[])arrinternalOnClickAction.readParcelableArray(null, BatchUpdates.class);
                n = arrparcelable.length;
                for (n2 = 0; n2 < n; ++n2) {
                    ((Builder)object).batchUpdate((Validator)((Object)arrparcelable[n2]), (BatchUpdates)arrobject[n2]);
                }
            }
            if ((arrobject = arrinternalOnClickAction.createIntArray()) != null) {
                arrinternalOnClickAction = (InternalOnClickAction[])arrinternalOnClickAction.readParcelableArray(null, InternalOnClickAction.class);
                n = arrobject.length;
                for (n2 = 0; n2 < n; ++n2) {
                    ((Builder)object).addOnClickAction(arrobject[n2], arrinternalOnClickAction[n2]);
                }
            }
            return ((Builder)object).build();
        }

        public CustomDescription[] newArray(int n) {
            return new CustomDescription[n];
        }
    };
    private final SparseArray<InternalOnClickAction> mActions;
    private final RemoteViews mPresentation;
    private final ArrayList<Pair<Integer, InternalTransformation>> mTransformations;
    private final ArrayList<Pair<InternalValidator, BatchUpdates>> mUpdates;

    private CustomDescription(Builder builder) {
        this.mPresentation = builder.mPresentation;
        this.mTransformations = builder.mTransformations;
        this.mUpdates = builder.mUpdates;
        this.mActions = builder.mActions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public SparseArray<InternalOnClickAction> getActions() {
        return this.mActions;
    }

    public RemoteViews getPresentation() {
        return this.mPresentation;
    }

    public ArrayList<Pair<Integer, InternalTransformation>> getTransformations() {
        return this.mTransformations;
    }

    public ArrayList<Pair<InternalValidator, BatchUpdates>> getUpdates() {
        return this.mUpdates;
    }

    public String toString() {
        if (!Helper.sDebug) {
            return super.toString();
        }
        StringBuilder stringBuilder = new StringBuilder("CustomDescription: [presentation=");
        stringBuilder.append(this.mPresentation);
        stringBuilder.append(", transformations=");
        Object object = this.mTransformations;
        String string2 = "N/A";
        object = object == null ? "N/A" : Integer.valueOf(((ArrayList)object).size());
        stringBuilder.append(object);
        stringBuilder.append(", updates=");
        object = this.mUpdates;
        object = object == null ? "N/A" : Integer.valueOf(((ArrayList)object).size());
        stringBuilder.append(object);
        stringBuilder.append(", actions=");
        object = this.mActions;
        object = object == null ? string2 : Integer.valueOf(((SparseArray)object).size());
        stringBuilder.append(object);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        Parcelable[] arrparcelable;
        Pair<Object, Parcelable> pair;
        int n2;
        int n3;
        parcel.writeParcelable(this.mPresentation, n);
        if (this.mPresentation == null) {
            return;
        }
        Object object = this.mTransformations;
        if (object == null) {
            parcel.writeIntArray(null);
        } else {
            n3 = ((ArrayList)object).size();
            object = new int[n3];
            arrparcelable = new InternalTransformation[n3];
            for (n2 = 0; n2 < n3; ++n2) {
                pair = this.mTransformations.get(n2);
                object[n2] = (Integer)pair.first;
                arrparcelable[n2] = (InternalTransformation)pair.second;
            }
            parcel.writeIntArray((int[])object);
            parcel.writeParcelableArray(arrparcelable, n);
        }
        object = this.mUpdates;
        if (object == null) {
            parcel.writeParcelableArray(null, n);
        } else {
            n3 = ((ArrayList)object).size();
            object = new InternalValidator[n3];
            arrparcelable = new BatchUpdates[n3];
            for (n2 = 0; n2 < n3; ++n2) {
                pair = this.mUpdates.get(n2);
                object[n2] = (InternalValidator)pair.first;
                arrparcelable[n2] = (BatchUpdates)pair.second;
            }
            parcel.writeParcelableArray((Parcelable[])object, n);
            parcel.writeParcelableArray(arrparcelable, n);
        }
        object = this.mActions;
        if (object == null) {
            parcel.writeIntArray(null);
        } else {
            n3 = ((SparseArray)object).size();
            arrparcelable = new int[n3];
            object = new InternalOnClickAction[n3];
            for (n2 = 0; n2 < n3; ++n2) {
                arrparcelable[n2] = (Parcelable)this.mActions.keyAt(n2);
                object[n2] = this.mActions.valueAt(n2);
            }
            parcel.writeIntArray((int[])arrparcelable);
            parcel.writeParcelableArray((Parcelable[])object, n);
        }
    }

    public static class Builder {
        private SparseArray<InternalOnClickAction> mActions;
        private boolean mDestroyed;
        private final RemoteViews mPresentation;
        private ArrayList<Pair<Integer, InternalTransformation>> mTransformations;
        private ArrayList<Pair<InternalValidator, BatchUpdates>> mUpdates;

        public Builder(RemoteViews remoteViews) {
            this.mPresentation = Preconditions.checkNotNull(remoteViews);
        }

        private void throwIfDestroyed() {
            if (!this.mDestroyed) {
                return;
            }
            throw new IllegalStateException("Already called #build()");
        }

        public Builder addChild(int n, Transformation transformation) {
            this.throwIfDestroyed();
            boolean bl = transformation instanceof InternalTransformation;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("not provided by Android System: ");
            stringBuilder.append(transformation);
            Preconditions.checkArgument(bl, stringBuilder.toString());
            if (this.mTransformations == null) {
                this.mTransformations = new ArrayList();
            }
            this.mTransformations.add(new Pair<Integer, InternalTransformation>(n, (InternalTransformation)transformation));
            return this;
        }

        public Builder addOnClickAction(int n, OnClickAction onClickAction) {
            this.throwIfDestroyed();
            boolean bl = onClickAction instanceof InternalOnClickAction;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("not provided by Android System: ");
            stringBuilder.append(onClickAction);
            Preconditions.checkArgument(bl, stringBuilder.toString());
            if (this.mActions == null) {
                this.mActions = new SparseArray();
            }
            this.mActions.put(n, (InternalOnClickAction)onClickAction);
            return this;
        }

        public Builder batchUpdate(Validator validator, BatchUpdates batchUpdates) {
            this.throwIfDestroyed();
            boolean bl = validator instanceof InternalValidator;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("not provided by Android System: ");
            stringBuilder.append(validator);
            Preconditions.checkArgument(bl, stringBuilder.toString());
            Preconditions.checkNotNull(batchUpdates);
            if (this.mUpdates == null) {
                this.mUpdates = new ArrayList();
            }
            this.mUpdates.add(new Pair<InternalValidator, BatchUpdates>((InternalValidator)validator, batchUpdates));
            return this;
        }

        public CustomDescription build() {
            this.throwIfDestroyed();
            this.mDestroyed = true;
            return new CustomDescription(this);
        }
    }

}

