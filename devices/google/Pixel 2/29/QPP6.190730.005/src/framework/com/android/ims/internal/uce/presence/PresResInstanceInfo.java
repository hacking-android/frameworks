/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims.internal.uce.presence;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.ims.internal.uce.presence.PresTupleInfo;
import java.util.Arrays;

public class PresResInstanceInfo
implements Parcelable {
    public static final Parcelable.Creator<PresResInstanceInfo> CREATOR = new Parcelable.Creator<PresResInstanceInfo>(){

        @Override
        public PresResInstanceInfo createFromParcel(Parcel parcel) {
            return new PresResInstanceInfo(parcel);
        }

        public PresResInstanceInfo[] newArray(int n) {
            return new PresResInstanceInfo[n];
        }
    };
    public static final int UCE_PRES_RES_INSTANCE_STATE_ACTIVE = 0;
    public static final int UCE_PRES_RES_INSTANCE_STATE_PENDING = 1;
    public static final int UCE_PRES_RES_INSTANCE_STATE_TERMINATED = 2;
    public static final int UCE_PRES_RES_INSTANCE_STATE_UNKNOWN = 3;
    public static final int UCE_PRES_RES_INSTANCE_UNKNOWN = 4;
    private String mId = "";
    private String mPresentityUri = "";
    private String mReason = "";
    private int mResInstanceState;
    private PresTupleInfo[] mTupleInfoArray;

    @UnsupportedAppUsage
    public PresResInstanceInfo() {
    }

    private PresResInstanceInfo(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getPresentityUri() {
        return this.mPresentityUri;
    }

    public String getReason() {
        return this.mReason;
    }

    public String getResId() {
        return this.mId;
    }

    public int getResInstanceState() {
        return this.mResInstanceState;
    }

    public PresTupleInfo[] getTupleInfo() {
        return this.mTupleInfoArray;
    }

    public void readFromParcel(Parcel arrparcelable) {
        this.mId = arrparcelable.readString();
        this.mReason = arrparcelable.readString();
        this.mResInstanceState = arrparcelable.readInt();
        this.mPresentityUri = arrparcelable.readString();
        arrparcelable = arrparcelable.readParcelableArray(PresTupleInfo.class.getClassLoader());
        this.mTupleInfoArray = new PresTupleInfo[0];
        if (arrparcelable != null) {
            this.mTupleInfoArray = (PresTupleInfo[])Arrays.copyOf(arrparcelable, arrparcelable.length, PresTupleInfo[].class);
        }
    }

    @UnsupportedAppUsage
    public void setPresentityUri(String string2) {
        this.mPresentityUri = string2;
    }

    @UnsupportedAppUsage
    public void setReason(String string2) {
        this.mReason = string2;
    }

    @UnsupportedAppUsage
    public void setResId(String string2) {
        this.mId = string2;
    }

    @UnsupportedAppUsage
    public void setResInstanceState(int n) {
        this.mResInstanceState = n;
    }

    @UnsupportedAppUsage
    public void setTupleInfo(PresTupleInfo[] arrpresTupleInfo) {
        this.mTupleInfoArray = new PresTupleInfo[arrpresTupleInfo.length];
        this.mTupleInfoArray = arrpresTupleInfo;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mId);
        parcel.writeString(this.mReason);
        parcel.writeInt(this.mResInstanceState);
        parcel.writeString(this.mPresentityUri);
        parcel.writeParcelableArray((Parcelable[])this.mTupleInfoArray, n);
    }

}

