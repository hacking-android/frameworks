/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill;

import android.os.Parcel;
import android.os.Parcelable;
import android.service.autofill.InternalTransformation;
import android.service.autofill.Transformation;
import android.util.Pair;
import android.view.autofill.Helper;
import android.widget.RemoteViews;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;

public final class BatchUpdates
implements Parcelable {
    public static final Parcelable.Creator<BatchUpdates> CREATOR = new Parcelable.Creator<BatchUpdates>(){

        @Override
        public BatchUpdates createFromParcel(Parcel object) {
            Builder builder = new Builder();
            int[] arrn = ((Parcel)object).createIntArray();
            if (arrn != null) {
                InternalTransformation[] arrinternalTransformation = (InternalTransformation[])object.readParcelableArray(null, InternalTransformation.class);
                int n = arrn.length;
                for (int i = 0; i < n; ++i) {
                    builder.transformChild(arrn[i], arrinternalTransformation[i]);
                }
            }
            if ((object = (RemoteViews)((Parcel)object).readParcelable(null)) != null) {
                builder.updateTemplate((RemoteViews)object);
            }
            return builder.build();
        }

        public BatchUpdates[] newArray(int n) {
            return new BatchUpdates[n];
        }
    };
    private final ArrayList<Pair<Integer, InternalTransformation>> mTransformations;
    private final RemoteViews mUpdates;

    private BatchUpdates(Builder builder) {
        this.mTransformations = builder.mTransformations;
        this.mUpdates = builder.mUpdates;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public ArrayList<Pair<Integer, InternalTransformation>> getTransformations() {
        return this.mTransformations;
    }

    public RemoteViews getUpdates() {
        return this.mUpdates;
    }

    public String toString() {
        if (!Helper.sDebug) {
            return super.toString();
        }
        StringBuilder stringBuilder = new StringBuilder("BatchUpdates: [");
        stringBuilder.append(", transformations=");
        Object object = this.mTransformations;
        object = object == null ? "N/A" : Integer.valueOf(((ArrayList)object).size());
        stringBuilder.append(object);
        stringBuilder.append(", updates=");
        stringBuilder.append(this.mUpdates);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        ArrayList<Pair<Integer, InternalTransformation>> arrayList = this.mTransformations;
        if (arrayList == null) {
            parcel.writeIntArray(null);
        } else {
            int n2 = arrayList.size();
            int[] arrn = new int[n2];
            Parcelable[] arrparcelable = new InternalTransformation[n2];
            for (int i = 0; i < n2; ++i) {
                arrayList = this.mTransformations.get(i);
                arrn[i] = (Integer)((Pair)arrayList).first;
                arrparcelable[i] = (InternalTransformation)((Pair)arrayList).second;
            }
            parcel.writeIntArray(arrn);
            parcel.writeParcelableArray(arrparcelable, n);
        }
        parcel.writeParcelable(this.mUpdates, n);
    }

    public static class Builder {
        private boolean mDestroyed;
        private ArrayList<Pair<Integer, InternalTransformation>> mTransformations;
        private RemoteViews mUpdates;

        private void throwIfDestroyed() {
            if (!this.mDestroyed) {
                return;
            }
            throw new IllegalStateException("Already called #build()");
        }

        public BatchUpdates build() {
            this.throwIfDestroyed();
            boolean bl = this.mUpdates != null || this.mTransformations != null;
            Preconditions.checkState(bl, "must call either updateTemplate() or transformChild() at least once");
            this.mDestroyed = true;
            return new BatchUpdates(this);
        }

        public Builder transformChild(int n, Transformation transformation) {
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

        public Builder updateTemplate(RemoteViews remoteViews) {
            this.throwIfDestroyed();
            this.mUpdates = Preconditions.checkNotNull(remoteViews);
            return this;
        }
    }

}

