/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims.internal.uce.common;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;

public class UceLong
implements Parcelable {
    public static final Parcelable.Creator<UceLong> CREATOR = new Parcelable.Creator<UceLong>(){

        @Override
        public UceLong createFromParcel(Parcel parcel) {
            return new UceLong(parcel);
        }

        public UceLong[] newArray(int n) {
            return new UceLong[n];
        }
    };
    private int mClientId = 1001;
    private long mUceLong;

    @UnsupportedAppUsage
    public UceLong() {
    }

    private UceLong(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    public static UceLong getUceLongInstance() {
        return new UceLong();
    }

    private void writeToParcel(Parcel parcel) {
        parcel.writeLong(this.mUceLong);
        parcel.writeInt(this.mClientId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @UnsupportedAppUsage
    public int getClientId() {
        return this.mClientId;
    }

    @UnsupportedAppUsage
    public long getUceLong() {
        return this.mUceLong;
    }

    public void readFromParcel(Parcel parcel) {
        this.mUceLong = parcel.readLong();
        this.mClientId = parcel.readInt();
    }

    @UnsupportedAppUsage
    public void setClientId(int n) {
        this.mClientId = n;
    }

    @UnsupportedAppUsage
    public void setUceLong(long l) {
        this.mUceLong = l;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.writeToParcel(parcel);
    }

}

