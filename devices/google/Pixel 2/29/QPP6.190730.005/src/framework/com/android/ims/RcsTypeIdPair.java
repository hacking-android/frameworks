/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims;

import android.os.Parcel;
import android.os.Parcelable;

public class RcsTypeIdPair
implements Parcelable {
    public static final Parcelable.Creator<RcsTypeIdPair> CREATOR = new Parcelable.Creator<RcsTypeIdPair>(){

        @Override
        public RcsTypeIdPair createFromParcel(Parcel parcel) {
            return new RcsTypeIdPair(parcel);
        }

        public RcsTypeIdPair[] newArray(int n) {
            return new RcsTypeIdPair[n];
        }
    };
    private int mId;
    private int mType;

    public RcsTypeIdPair(int n, int n2) {
        this.mType = n;
        this.mId = n2;
    }

    public RcsTypeIdPair(Parcel parcel) {
        this.mType = parcel.readInt();
        this.mId = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getId() {
        return this.mId;
    }

    public int getType() {
        return this.mType;
    }

    public void setId(int n) {
        this.mId = n;
    }

    public void setType(int n) {
        this.mType = n;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mId);
    }

}

