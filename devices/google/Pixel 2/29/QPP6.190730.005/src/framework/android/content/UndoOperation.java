/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.annotation.UnsupportedAppUsage;
import android.content.UndoOwner;
import android.os.Parcel;
import android.os.Parcelable;

public abstract class UndoOperation<DATA>
implements Parcelable {
    UndoOwner mOwner;

    @UnsupportedAppUsage
    public UndoOperation(UndoOwner undoOwner) {
        this.mOwner = undoOwner;
    }

    @UnsupportedAppUsage
    protected UndoOperation(Parcel parcel, ClassLoader classLoader) {
    }

    public boolean allowMerge() {
        return true;
    }

    public abstract void commit();

    @Override
    public int describeContents() {
        return 0;
    }

    public UndoOwner getOwner() {
        return this.mOwner;
    }

    public DATA getOwnerData() {
        return (DATA)this.mOwner.getData();
    }

    public boolean hasData() {
        return true;
    }

    public boolean matchOwner(UndoOwner undoOwner) {
        boolean bl = undoOwner == this.getOwner();
        return bl;
    }

    public abstract void redo();

    public abstract void undo();
}

