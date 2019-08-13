/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 */
package com.android.phone.ecc.nano.android;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.phone.ecc.nano.MessageNano;
import com.android.phone.ecc.nano.android.ParcelableMessageNanoCreator;

public abstract class ParcelableMessageNano
extends MessageNano
implements Parcelable {
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n) {
        ParcelableMessageNanoCreator.writeToParcel(this.getClass(), this, parcel);
    }
}

