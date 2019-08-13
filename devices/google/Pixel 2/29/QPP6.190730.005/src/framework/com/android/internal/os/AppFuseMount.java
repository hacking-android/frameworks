/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;

public class AppFuseMount
implements Parcelable {
    public static final Parcelable.Creator<AppFuseMount> CREATOR = new Parcelable.Creator<AppFuseMount>(){

        @Override
        public AppFuseMount createFromParcel(Parcel parcel) {
            return new AppFuseMount(parcel.readInt(), (ParcelFileDescriptor)parcel.readParcelable(null));
        }

        public AppFuseMount[] newArray(int n) {
            return new AppFuseMount[n];
        }
    };
    public final ParcelFileDescriptor fd;
    public final int mountPointId;

    public AppFuseMount(int n, ParcelFileDescriptor parcelFileDescriptor) {
        Preconditions.checkNotNull(parcelFileDescriptor);
        this.mountPointId = n;
        this.fd = parcelFileDescriptor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mountPointId);
        parcel.writeParcelable(this.fd, n);
    }

}

