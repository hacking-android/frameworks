/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims.internal.uce.presence;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;

public class PresServiceInfo
implements Parcelable {
    public static final Parcelable.Creator<PresServiceInfo> CREATOR = new Parcelable.Creator<PresServiceInfo>(){

        @Override
        public PresServiceInfo createFromParcel(Parcel parcel) {
            return new PresServiceInfo(parcel);
        }

        public PresServiceInfo[] newArray(int n) {
            return new PresServiceInfo[n];
        }
    };
    public static final int UCE_PRES_MEDIA_CAP_FULL_AUDIO_AND_VIDEO = 2;
    public static final int UCE_PRES_MEDIA_CAP_FULL_AUDIO_ONLY = 1;
    public static final int UCE_PRES_MEDIA_CAP_NONE = 0;
    public static final int UCE_PRES_MEDIA_CAP_UNKNOWN = 3;
    private int mMediaCap = 0;
    private String mServiceDesc = "";
    private String mServiceID = "";
    private String mServiceVer = "";

    public PresServiceInfo() {
    }

    private PresServiceInfo(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @UnsupportedAppUsage
    public int getMediaType() {
        return this.mMediaCap;
    }

    @UnsupportedAppUsage
    public String getServiceDesc() {
        return this.mServiceDesc;
    }

    @UnsupportedAppUsage
    public String getServiceId() {
        return this.mServiceID;
    }

    @UnsupportedAppUsage
    public String getServiceVer() {
        return this.mServiceVer;
    }

    public void readFromParcel(Parcel parcel) {
        this.mServiceID = parcel.readString();
        this.mServiceDesc = parcel.readString();
        this.mServiceVer = parcel.readString();
        this.mMediaCap = parcel.readInt();
    }

    public void setMediaType(int n) {
        this.mMediaCap = n;
    }

    public void setServiceDesc(String string2) {
        this.mServiceDesc = string2;
    }

    public void setServiceId(String string2) {
        this.mServiceID = string2;
    }

    public void setServiceVer(String string2) {
        this.mServiceVer = string2;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mServiceID);
        parcel.writeString(this.mServiceDesc);
        parcel.writeString(this.mServiceVer);
        parcel.writeInt(this.mMediaCap);
    }

}

