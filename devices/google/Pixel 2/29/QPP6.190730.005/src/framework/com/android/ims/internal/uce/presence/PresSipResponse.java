/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims.internal.uce.presence;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.ims.internal.uce.presence.PresCmdId;

public class PresSipResponse
implements Parcelable {
    public static final Parcelable.Creator<PresSipResponse> CREATOR = new Parcelable.Creator<PresSipResponse>(){

        @Override
        public PresSipResponse createFromParcel(Parcel parcel) {
            return new PresSipResponse(parcel);
        }

        public PresSipResponse[] newArray(int n) {
            return new PresSipResponse[n];
        }
    };
    private PresCmdId mCmdId = new PresCmdId();
    private String mReasonPhrase = "";
    private int mRequestId = 0;
    private int mRetryAfter = 0;
    private int mSipResponseCode = 0;

    @UnsupportedAppUsage
    public PresSipResponse() {
    }

    private PresSipResponse(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @UnsupportedAppUsage
    public PresCmdId getCmdId() {
        return this.mCmdId;
    }

    @UnsupportedAppUsage
    public String getReasonPhrase() {
        return this.mReasonPhrase;
    }

    @UnsupportedAppUsage
    public int getRequestId() {
        return this.mRequestId;
    }

    @UnsupportedAppUsage
    public int getRetryAfter() {
        return this.mRetryAfter;
    }

    @UnsupportedAppUsage
    public int getSipResponseCode() {
        return this.mSipResponseCode;
    }

    public void readFromParcel(Parcel parcel) {
        this.mRequestId = parcel.readInt();
        this.mSipResponseCode = parcel.readInt();
        this.mReasonPhrase = parcel.readString();
        this.mCmdId = (PresCmdId)parcel.readParcelable(PresCmdId.class.getClassLoader());
        this.mRetryAfter = parcel.readInt();
    }

    @UnsupportedAppUsage
    public void setCmdId(PresCmdId presCmdId) {
        this.mCmdId = presCmdId;
    }

    @UnsupportedAppUsage
    public void setReasonPhrase(String string2) {
        this.mReasonPhrase = string2;
    }

    @UnsupportedAppUsage
    public void setRequestId(int n) {
        this.mRequestId = n;
    }

    @UnsupportedAppUsage
    public void setRetryAfter(int n) {
        this.mRetryAfter = n;
    }

    @UnsupportedAppUsage
    public void setSipResponseCode(int n) {
        this.mSipResponseCode = n;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mRequestId);
        parcel.writeInt(this.mSipResponseCode);
        parcel.writeString(this.mReasonPhrase);
        parcel.writeParcelable(this.mCmdId, n);
        parcel.writeInt(this.mRetryAfter);
    }

}

