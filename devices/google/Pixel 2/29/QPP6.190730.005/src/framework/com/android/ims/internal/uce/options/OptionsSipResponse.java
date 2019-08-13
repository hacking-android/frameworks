/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims.internal.uce.options;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.ims.internal.uce.options.OptionsCmdId;

public class OptionsSipResponse
implements Parcelable {
    public static final Parcelable.Creator<OptionsSipResponse> CREATOR = new Parcelable.Creator<OptionsSipResponse>(){

        @Override
        public OptionsSipResponse createFromParcel(Parcel parcel) {
            return new OptionsSipResponse(parcel);
        }

        public OptionsSipResponse[] newArray(int n) {
            return new OptionsSipResponse[n];
        }
    };
    private OptionsCmdId mCmdId;
    private String mReasonPhrase = "";
    private int mRequestId = 0;
    private int mRetryAfter = 0;
    private int mSipResponseCode = 0;

    @UnsupportedAppUsage
    public OptionsSipResponse() {
        this.mCmdId = new OptionsCmdId();
    }

    private OptionsSipResponse(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public OptionsCmdId getCmdId() {
        return this.mCmdId;
    }

    public String getReasonPhrase() {
        return this.mReasonPhrase;
    }

    public int getRequestId() {
        return this.mRequestId;
    }

    public int getRetryAfter() {
        return this.mRetryAfter;
    }

    public int getSipResponseCode() {
        return this.mSipResponseCode;
    }

    public void readFromParcel(Parcel parcel) {
        this.mRequestId = parcel.readInt();
        this.mSipResponseCode = parcel.readInt();
        this.mReasonPhrase = parcel.readString();
        this.mCmdId = (OptionsCmdId)parcel.readParcelable(OptionsCmdId.class.getClassLoader());
        this.mRetryAfter = parcel.readInt();
    }

    @UnsupportedAppUsage
    public void setCmdId(OptionsCmdId optionsCmdId) {
        this.mCmdId = optionsCmdId;
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

