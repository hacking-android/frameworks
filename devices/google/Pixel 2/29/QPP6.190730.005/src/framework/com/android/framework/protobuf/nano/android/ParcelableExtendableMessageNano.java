/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf.nano.android;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.framework.protobuf.nano.ExtendableMessageNano;
import com.android.framework.protobuf.nano.android.ParcelableMessageNanoCreator;

public abstract class ParcelableExtendableMessageNano<M extends ExtendableMessageNano<M>>
extends ExtendableMessageNano<M>
implements Parcelable {
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        ParcelableMessageNanoCreator.writeToParcel(this.getClass(), this, parcel);
    }
}

