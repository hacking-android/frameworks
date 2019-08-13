/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims.internal.uce.options;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.ims.internal.uce.common.CapInfo;
import com.android.ims.internal.uce.common.StatusCode;
import com.android.ims.internal.uce.options.OptionsCmdId;

public class OptionsCmdStatus
implements Parcelable {
    public static final Parcelable.Creator<OptionsCmdStatus> CREATOR = new Parcelable.Creator<OptionsCmdStatus>(){

        @Override
        public OptionsCmdStatus createFromParcel(Parcel parcel) {
            return new OptionsCmdStatus(parcel);
        }

        public OptionsCmdStatus[] newArray(int n) {
            return new OptionsCmdStatus[n];
        }
    };
    private CapInfo mCapInfo;
    private OptionsCmdId mCmdId;
    private StatusCode mStatus;
    private int mUserData;

    @UnsupportedAppUsage
    public OptionsCmdStatus() {
        this.mStatus = new StatusCode();
        this.mCapInfo = new CapInfo();
        this.mCmdId = new OptionsCmdId();
        this.mUserData = 0;
    }

    private OptionsCmdStatus(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    public static OptionsCmdStatus getOptionsCmdStatusInstance() {
        return new OptionsCmdStatus();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public CapInfo getCapInfo() {
        return this.mCapInfo;
    }

    public OptionsCmdId getCmdId() {
        return this.mCmdId;
    }

    public StatusCode getStatus() {
        return this.mStatus;
    }

    public int getUserData() {
        return this.mUserData;
    }

    public void readFromParcel(Parcel parcel) {
        this.mUserData = parcel.readInt();
        this.mCmdId = (OptionsCmdId)parcel.readParcelable(OptionsCmdId.class.getClassLoader());
        this.mStatus = (StatusCode)parcel.readParcelable(StatusCode.class.getClassLoader());
        this.mCapInfo = (CapInfo)parcel.readParcelable(CapInfo.class.getClassLoader());
    }

    @UnsupportedAppUsage
    public void setCapInfo(CapInfo capInfo) {
        this.mCapInfo = capInfo;
    }

    @UnsupportedAppUsage
    public void setCmdId(OptionsCmdId optionsCmdId) {
        this.mCmdId = optionsCmdId;
    }

    @UnsupportedAppUsage
    public void setStatus(StatusCode statusCode) {
        this.mStatus = statusCode;
    }

    @UnsupportedAppUsage
    public void setUserData(int n) {
        this.mUserData = n;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mUserData);
        parcel.writeParcelable(this.mCmdId, n);
        parcel.writeParcelable(this.mStatus, n);
        parcel.writeParcelable(this.mCapInfo, n);
    }

}

