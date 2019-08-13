/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims.internal.uce.presence;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;

public class PresTupleInfo
implements Parcelable {
    public static final Parcelable.Creator<PresTupleInfo> CREATOR = new Parcelable.Creator<PresTupleInfo>(){

        @Override
        public PresTupleInfo createFromParcel(Parcel parcel) {
            return new PresTupleInfo(parcel);
        }

        public PresTupleInfo[] newArray(int n) {
            return new PresTupleInfo[n];
        }
    };
    private String mContactUri = "";
    private String mFeatureTag = "";
    private String mTimestamp = "";

    @UnsupportedAppUsage
    public PresTupleInfo() {
    }

    private PresTupleInfo(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getContactUri() {
        return this.mContactUri;
    }

    public String getFeatureTag() {
        return this.mFeatureTag;
    }

    public String getTimestamp() {
        return this.mTimestamp;
    }

    public void readFromParcel(Parcel parcel) {
        this.mFeatureTag = parcel.readString();
        this.mContactUri = parcel.readString();
        this.mTimestamp = parcel.readString();
    }

    @UnsupportedAppUsage
    public void setContactUri(String string2) {
        this.mContactUri = string2;
    }

    @UnsupportedAppUsage
    public void setFeatureTag(String string2) {
        this.mFeatureTag = string2;
    }

    @UnsupportedAppUsage
    public void setTimestamp(String string2) {
        this.mTimestamp = string2;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mFeatureTag);
        parcel.writeString(this.mContactUri);
        parcel.writeString(this.mTimestamp);
    }

}

