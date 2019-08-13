/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims.internal.uce.presence;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.ims.internal.uce.common.CapInfo;

public class PresCapInfo
implements Parcelable {
    public static final Parcelable.Creator<PresCapInfo> CREATOR = new Parcelable.Creator<PresCapInfo>(){

        @Override
        public PresCapInfo createFromParcel(Parcel parcel) {
            return new PresCapInfo(parcel);
        }

        public PresCapInfo[] newArray(int n) {
            return new PresCapInfo[n];
        }
    };
    private CapInfo mCapInfo;
    @UnsupportedAppUsage
    private String mContactUri = "";

    public PresCapInfo() {
        this.mCapInfo = new CapInfo();
    }

    private PresCapInfo(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @UnsupportedAppUsage
    public CapInfo getCapInfo() {
        return this.mCapInfo;
    }

    @UnsupportedAppUsage
    public String getContactUri() {
        return this.mContactUri;
    }

    public void readFromParcel(Parcel parcel) {
        this.mContactUri = parcel.readString();
        this.mCapInfo = (CapInfo)parcel.readParcelable(CapInfo.class.getClassLoader());
    }

    public void setCapInfo(CapInfo capInfo) {
        this.mCapInfo = capInfo;
    }

    public void setContactUri(String string2) {
        this.mContactUri = string2;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mContactUri);
        parcel.writeParcelable(this.mCapInfo, n);
    }

}

