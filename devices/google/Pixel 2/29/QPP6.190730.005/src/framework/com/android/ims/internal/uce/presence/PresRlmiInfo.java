/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims.internal.uce.presence;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.ims.internal.uce.presence.PresSubscriptionState;

public class PresRlmiInfo
implements Parcelable {
    public static final Parcelable.Creator<PresRlmiInfo> CREATOR = new Parcelable.Creator<PresRlmiInfo>(){

        @Override
        public PresRlmiInfo createFromParcel(Parcel parcel) {
            return new PresRlmiInfo(parcel);
        }

        public PresRlmiInfo[] newArray(int n) {
            return new PresRlmiInfo[n];
        }
    };
    private boolean mFullState;
    private String mListName = "";
    private PresSubscriptionState mPresSubscriptionState;
    private int mRequestId;
    private int mSubscriptionExpireTime;
    private String mSubscriptionTerminatedReason;
    private String mUri = "";
    private int mVersion;

    @UnsupportedAppUsage
    public PresRlmiInfo() {
    }

    private PresRlmiInfo(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getListName() {
        return this.mListName;
    }

    public PresSubscriptionState getPresSubscriptionState() {
        return this.mPresSubscriptionState;
    }

    public int getRequestId() {
        return this.mRequestId;
    }

    public int getSubscriptionExpireTime() {
        return this.mSubscriptionExpireTime;
    }

    public String getSubscriptionTerminatedReason() {
        return this.mSubscriptionTerminatedReason;
    }

    public String getUri() {
        return this.mUri;
    }

    public int getVersion() {
        return this.mVersion;
    }

    public boolean isFullState() {
        return this.mFullState;
    }

    public void readFromParcel(Parcel parcel) {
        this.mUri = parcel.readString();
        this.mVersion = parcel.readInt();
        boolean bl = parcel.readInt() != 0;
        this.mFullState = bl;
        this.mListName = parcel.readString();
        this.mRequestId = parcel.readInt();
        this.mPresSubscriptionState = (PresSubscriptionState)parcel.readParcelable(PresSubscriptionState.class.getClassLoader());
        this.mSubscriptionExpireTime = parcel.readInt();
        this.mSubscriptionTerminatedReason = parcel.readString();
    }

    @UnsupportedAppUsage
    public void setFullState(boolean bl) {
        this.mFullState = bl;
    }

    @UnsupportedAppUsage
    public void setListName(String string2) {
        this.mListName = string2;
    }

    @UnsupportedAppUsage
    public void setPresSubscriptionState(PresSubscriptionState presSubscriptionState) {
        this.mPresSubscriptionState = presSubscriptionState;
    }

    @UnsupportedAppUsage
    public void setRequestId(int n) {
        this.mRequestId = n;
    }

    @UnsupportedAppUsage
    public void setSubscriptionExpireTime(int n) {
        this.mSubscriptionExpireTime = n;
    }

    @UnsupportedAppUsage
    public void setSubscriptionTerminatedReason(String string2) {
        this.mSubscriptionTerminatedReason = string2;
    }

    @UnsupportedAppUsage
    public void setUri(String string2) {
        this.mUri = string2;
    }

    @UnsupportedAppUsage
    public void setVersion(int n) {
        this.mVersion = n;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mUri);
        parcel.writeInt(this.mVersion);
        parcel.writeInt((int)this.mFullState);
        parcel.writeString(this.mListName);
        parcel.writeInt(this.mRequestId);
        parcel.writeParcelable(this.mPresSubscriptionState, n);
        parcel.writeInt(this.mSubscriptionExpireTime);
        parcel.writeString(this.mSubscriptionTerminatedReason);
    }

}

