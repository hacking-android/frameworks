/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims.internal.uce.presence;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.ims.internal.uce.presence.PresResInstanceInfo;

public class PresResInfo
implements Parcelable {
    public static final Parcelable.Creator<PresResInfo> CREATOR = new Parcelable.Creator<PresResInfo>(){

        @Override
        public PresResInfo createFromParcel(Parcel parcel) {
            return new PresResInfo(parcel);
        }

        public PresResInfo[] newArray(int n) {
            return new PresResInfo[n];
        }
    };
    private String mDisplayName = "";
    private PresResInstanceInfo mInstanceInfo;
    private String mResUri = "";

    @UnsupportedAppUsage
    public PresResInfo() {
        this.mInstanceInfo = new PresResInstanceInfo();
    }

    private PresResInfo(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getDisplayName() {
        return this.mDisplayName;
    }

    public PresResInstanceInfo getInstanceInfo() {
        return this.mInstanceInfo;
    }

    public String getResUri() {
        return this.mResUri;
    }

    public void readFromParcel(Parcel parcel) {
        this.mResUri = parcel.readString();
        this.mDisplayName = parcel.readString();
        this.mInstanceInfo = (PresResInstanceInfo)parcel.readParcelable(PresResInstanceInfo.class.getClassLoader());
    }

    @UnsupportedAppUsage
    public void setDisplayName(String string2) {
        this.mDisplayName = string2;
    }

    @UnsupportedAppUsage
    public void setInstanceInfo(PresResInstanceInfo presResInstanceInfo) {
        this.mInstanceInfo = presResInstanceInfo;
    }

    @UnsupportedAppUsage
    public void setResUri(String string2) {
        this.mResUri = string2;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mResUri);
        parcel.writeString(this.mDisplayName);
        parcel.writeParcelable(this.mInstanceInfo, n);
    }

}

