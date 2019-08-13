/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims.internal.uce.options;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.ims.internal.uce.common.CapInfo;

public class OptionsCapInfo
implements Parcelable {
    public static final Parcelable.Creator<OptionsCapInfo> CREATOR = new Parcelable.Creator<OptionsCapInfo>(){

        @Override
        public OptionsCapInfo createFromParcel(Parcel parcel) {
            return new OptionsCapInfo(parcel);
        }

        public OptionsCapInfo[] newArray(int n) {
            return new OptionsCapInfo[n];
        }
    };
    private CapInfo mCapInfo;
    private String mSdp = "";

    @UnsupportedAppUsage
    public OptionsCapInfo() {
        this.mCapInfo = new CapInfo();
    }

    private OptionsCapInfo(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    public static OptionsCapInfo getOptionsCapInfoInstance() {
        return new OptionsCapInfo();
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
    public String getSdp() {
        return this.mSdp;
    }

    public void readFromParcel(Parcel parcel) {
        this.mSdp = parcel.readString();
        this.mCapInfo = (CapInfo)parcel.readParcelable(CapInfo.class.getClassLoader());
    }

    @UnsupportedAppUsage
    public void setCapInfo(CapInfo capInfo) {
        this.mCapInfo = capInfo;
    }

    @UnsupportedAppUsage
    public void setSdp(String string2) {
        this.mSdp = string2;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mSdp);
        parcel.writeParcelable(this.mCapInfo, n);
    }

}

