/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.location;

import android.os.Parcel;
import android.os.Parcelable;

public final class ProviderProperties
implements Parcelable {
    public static final Parcelable.Creator<ProviderProperties> CREATOR = new Parcelable.Creator<ProviderProperties>(){

        @Override
        public ProviderProperties createFromParcel(Parcel parcel) {
            boolean bl = parcel.readInt() == 1;
            boolean bl2 = parcel.readInt() == 1;
            boolean bl3 = parcel.readInt() == 1;
            boolean bl4 = parcel.readInt() == 1;
            boolean bl5 = parcel.readInt() == 1;
            boolean bl6 = parcel.readInt() == 1;
            boolean bl7 = parcel.readInt() == 1;
            return new ProviderProperties(bl, bl2, bl3, bl4, bl5, bl6, bl7, parcel.readInt(), parcel.readInt());
        }

        public ProviderProperties[] newArray(int n) {
            return new ProviderProperties[n];
        }
    };
    public final int mAccuracy;
    public final boolean mHasMonetaryCost;
    public final int mPowerRequirement;
    public final boolean mRequiresCell;
    public final boolean mRequiresNetwork;
    public final boolean mRequiresSatellite;
    public final boolean mSupportsAltitude;
    public final boolean mSupportsBearing;
    public final boolean mSupportsSpeed;

    public ProviderProperties(boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, boolean bl7, int n, int n2) {
        this.mRequiresNetwork = bl;
        this.mRequiresSatellite = bl2;
        this.mRequiresCell = bl3;
        this.mHasMonetaryCost = bl4;
        this.mSupportsAltitude = bl5;
        this.mSupportsSpeed = bl6;
        this.mSupportsBearing = bl7;
        this.mPowerRequirement = n;
        this.mAccuracy = n2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt((int)this.mRequiresNetwork);
        parcel.writeInt((int)this.mRequiresSatellite);
        parcel.writeInt((int)this.mRequiresCell);
        parcel.writeInt((int)this.mHasMonetaryCost);
        parcel.writeInt((int)this.mSupportsAltitude);
        parcel.writeInt((int)this.mSupportsSpeed);
        parcel.writeInt((int)this.mSupportsBearing);
        parcel.writeInt(this.mPowerRequirement);
        parcel.writeInt(this.mAccuracy);
    }

}

