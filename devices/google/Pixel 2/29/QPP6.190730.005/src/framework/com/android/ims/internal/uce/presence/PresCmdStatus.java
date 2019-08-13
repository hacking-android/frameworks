/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims.internal.uce.presence;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.ims.internal.uce.common.StatusCode;
import com.android.ims.internal.uce.presence.PresCmdId;

public class PresCmdStatus
implements Parcelable {
    public static final Parcelable.Creator<PresCmdStatus> CREATOR = new Parcelable.Creator<PresCmdStatus>(){

        @Override
        public PresCmdStatus createFromParcel(Parcel parcel) {
            return new PresCmdStatus(parcel);
        }

        public PresCmdStatus[] newArray(int n) {
            return new PresCmdStatus[n];
        }
    };
    private PresCmdId mCmdId = new PresCmdId();
    private int mRequestId;
    private StatusCode mStatus = new StatusCode();
    private int mUserData;

    @UnsupportedAppUsage
    public PresCmdStatus() {
        this.mStatus = new StatusCode();
    }

    private PresCmdStatus(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public PresCmdId getCmdId() {
        return this.mCmdId;
    }

    public int getRequestId() {
        return this.mRequestId;
    }

    public StatusCode getStatus() {
        return this.mStatus;
    }

    public int getUserData() {
        return this.mUserData;
    }

    public void readFromParcel(Parcel parcel) {
        this.mUserData = parcel.readInt();
        this.mRequestId = parcel.readInt();
        this.mCmdId = (PresCmdId)parcel.readParcelable(PresCmdId.class.getClassLoader());
        this.mStatus = (StatusCode)parcel.readParcelable(StatusCode.class.getClassLoader());
    }

    @UnsupportedAppUsage
    public void setCmdId(PresCmdId presCmdId) {
        this.mCmdId = presCmdId;
    }

    @UnsupportedAppUsage
    public void setRequestId(int n) {
        this.mRequestId = n;
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
        parcel.writeInt(this.mRequestId);
        parcel.writeParcelable(this.mCmdId, n);
        parcel.writeParcelable(this.mStatus, n);
    }

}

