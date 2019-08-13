/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill;

import android.os.Parcel;
import android.os.Parcelable;
import android.service.autofill.InternalOnClickAction;
import android.service.autofill.OnClickAction;
import android.util.Slog;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.autofill.Helper;
import com.android.internal.util.Preconditions;

public final class VisibilitySetterAction
extends InternalOnClickAction
implements OnClickAction,
Parcelable {
    public static final Parcelable.Creator<VisibilitySetterAction> CREATOR = new Parcelable.Creator<VisibilitySetterAction>(){

        @Override
        public VisibilitySetterAction createFromParcel(Parcel object) {
            SparseIntArray sparseIntArray = ((Parcel)object).readSparseIntArray();
            object = null;
            for (int i = 0; i < sparseIntArray.size(); ++i) {
                int n = sparseIntArray.keyAt(i);
                int n2 = sparseIntArray.valueAt(i);
                if (object == null) {
                    object = new Builder(n, n2);
                    continue;
                }
                ((Builder)object).setVisibility(n, n2);
            }
            object = object == null ? null : ((Builder)object).build();
            return object;
        }

        public VisibilitySetterAction[] newArray(int n) {
            return new VisibilitySetterAction[n];
        }
    };
    private static final String TAG = "VisibilitySetterAction";
    private final SparseIntArray mVisibilities;

    private VisibilitySetterAction(Builder builder) {
        this.mVisibilities = builder.mVisibilities;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void onClick(ViewGroup viewGroup) {
        for (int i = 0; i < this.mVisibilities.size(); ++i) {
            int n = this.mVisibilities.keyAt(i);
            Object object = viewGroup.findViewById(n);
            if (object == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Skipping view id ");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(" because it's not found on ");
                ((StringBuilder)object).append(viewGroup);
                Slog.w(TAG, ((StringBuilder)object).toString());
                continue;
            }
            n = this.mVisibilities.valueAt(i);
            if (Helper.sVerbose) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Changing visibility of view ");
                stringBuilder.append(object);
                stringBuilder.append(" from ");
                stringBuilder.append(((View)object).getVisibility());
                stringBuilder.append(" to  ");
                stringBuilder.append(n);
                Slog.v(TAG, stringBuilder.toString());
            }
            ((View)object).setVisibility(n);
        }
    }

    public String toString() {
        if (!Helper.sDebug) {
            return Object.super.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("VisibilitySetterAction: [");
        stringBuilder.append(this.mVisibilities);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeSparseIntArray(this.mVisibilities);
    }

    public static final class Builder {
        private boolean mDestroyed;
        private final SparseIntArray mVisibilities = new SparseIntArray();

        public Builder(int n, int n2) {
            this.setVisibility(n, n2);
        }

        private void throwIfDestroyed() {
            Preconditions.checkState(this.mDestroyed ^ true, "Already called build()");
        }

        public VisibilitySetterAction build() {
            this.throwIfDestroyed();
            this.mDestroyed = true;
            return new VisibilitySetterAction(this);
        }

        public Builder setVisibility(int n, int n2) {
            this.throwIfDestroyed();
            if (n2 != 0 && n2 != 4 && n2 != 8) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid visibility: ");
                stringBuilder.append(n2);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            this.mVisibilities.put(n, n2);
            return this;
        }
    }

}

