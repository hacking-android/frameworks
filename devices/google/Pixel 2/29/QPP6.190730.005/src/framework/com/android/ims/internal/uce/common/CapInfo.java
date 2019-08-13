/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims.internal.uce.common;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;

public class CapInfo
implements Parcelable {
    public static final Parcelable.Creator<CapInfo> CREATOR = new Parcelable.Creator<CapInfo>(){

        @Override
        public CapInfo createFromParcel(Parcel parcel) {
            return new CapInfo(parcel);
        }

        public CapInfo[] newArray(int n) {
            return new CapInfo[n];
        }
    };
    private long mCapTimestamp = 0L;
    private boolean mCdViaPresenceSupported = false;
    private String[] mExts = new String[10];
    private boolean mFtHttpSupported = false;
    private boolean mFtSnFSupported = false;
    private boolean mFtSupported = false;
    private boolean mFtThumbSupported = false;
    private boolean mFullSnFGroupChatSupported = false;
    private boolean mGeoPullFtSupported = false;
    private boolean mGeoPullSupported = false;
    private boolean mGeoPushSupported = false;
    private boolean mImSupported = false;
    private boolean mIpVideoSupported = false;
    private boolean mIpVoiceSupported = false;
    private boolean mIsSupported = false;
    private boolean mRcsIpVideoCallSupported = false;
    private boolean mRcsIpVideoOnlyCallSupported = false;
    private boolean mRcsIpVoiceCallSupported = false;
    private boolean mSmSupported = false;
    private boolean mSpSupported = false;
    private boolean mVsDuringCSSupported = false;
    private boolean mVsSupported = false;

    @UnsupportedAppUsage
    public CapInfo() {
    }

    private CapInfo(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @UnsupportedAppUsage
    public long getCapTimestamp() {
        return this.mCapTimestamp;
    }

    public String[] getExts() {
        return this.mExts;
    }

    @UnsupportedAppUsage
    public boolean isCdViaPresenceSupported() {
        return this.mCdViaPresenceSupported;
    }

    @UnsupportedAppUsage
    public boolean isFtHttpSupported() {
        return this.mFtHttpSupported;
    }

    @UnsupportedAppUsage
    public boolean isFtSnFSupported() {
        return this.mFtSnFSupported;
    }

    @UnsupportedAppUsage
    public boolean isFtSupported() {
        return this.mFtSupported;
    }

    @UnsupportedAppUsage
    public boolean isFtThumbSupported() {
        return this.mFtThumbSupported;
    }

    @UnsupportedAppUsage
    public boolean isFullSnFGroupChatSupported() {
        return this.mFullSnFGroupChatSupported;
    }

    @UnsupportedAppUsage
    public boolean isGeoPullFtSupported() {
        return this.mGeoPullFtSupported;
    }

    @UnsupportedAppUsage
    public boolean isGeoPullSupported() {
        return this.mGeoPullSupported;
    }

    @UnsupportedAppUsage
    public boolean isGeoPushSupported() {
        return this.mGeoPushSupported;
    }

    @UnsupportedAppUsage
    public boolean isImSupported() {
        return this.mImSupported;
    }

    @UnsupportedAppUsage
    public boolean isIpVideoSupported() {
        return this.mIpVideoSupported;
    }

    @UnsupportedAppUsage
    public boolean isIpVoiceSupported() {
        return this.mIpVoiceSupported;
    }

    @UnsupportedAppUsage
    public boolean isIsSupported() {
        return this.mIsSupported;
    }

    @UnsupportedAppUsage
    public boolean isRcsIpVideoCallSupported() {
        return this.mRcsIpVideoCallSupported;
    }

    @UnsupportedAppUsage
    public boolean isRcsIpVideoOnlyCallSupported() {
        return this.mRcsIpVideoOnlyCallSupported;
    }

    @UnsupportedAppUsage
    public boolean isRcsIpVoiceCallSupported() {
        return this.mRcsIpVoiceCallSupported;
    }

    @UnsupportedAppUsage
    public boolean isSmSupported() {
        return this.mSmSupported;
    }

    @UnsupportedAppUsage
    public boolean isSpSupported() {
        return this.mSpSupported;
    }

    @UnsupportedAppUsage
    public boolean isVsDuringCSSupported() {
        return this.mVsDuringCSSupported;
    }

    @UnsupportedAppUsage
    public boolean isVsSupported() {
        return this.mVsSupported;
    }

    public void readFromParcel(Parcel parcel) {
        int n = parcel.readInt();
        boolean bl = false;
        boolean bl2 = n != 0;
        this.mImSupported = bl2;
        bl2 = parcel.readInt() != 0;
        this.mFtSupported = bl2;
        bl2 = parcel.readInt() != 0;
        this.mFtThumbSupported = bl2;
        bl2 = parcel.readInt() != 0;
        this.mFtSnFSupported = bl2;
        bl2 = parcel.readInt() != 0;
        this.mFtHttpSupported = bl2;
        bl2 = parcel.readInt() != 0;
        this.mIsSupported = bl2;
        bl2 = parcel.readInt() != 0;
        this.mVsDuringCSSupported = bl2;
        bl2 = parcel.readInt() != 0;
        this.mVsSupported = bl2;
        bl2 = parcel.readInt() != 0;
        this.mSpSupported = bl2;
        bl2 = parcel.readInt() != 0;
        this.mCdViaPresenceSupported = bl2;
        bl2 = parcel.readInt() != 0;
        this.mIpVoiceSupported = bl2;
        bl2 = parcel.readInt() != 0;
        this.mIpVideoSupported = bl2;
        bl2 = parcel.readInt() != 0;
        this.mGeoPullFtSupported = bl2;
        bl2 = parcel.readInt() != 0;
        this.mGeoPullSupported = bl2;
        bl2 = parcel.readInt() != 0;
        this.mGeoPushSupported = bl2;
        bl2 = parcel.readInt() != 0;
        this.mSmSupported = bl2;
        bl2 = parcel.readInt() != 0;
        this.mFullSnFGroupChatSupported = bl2;
        bl2 = parcel.readInt() != 0;
        this.mRcsIpVoiceCallSupported = bl2;
        bl2 = parcel.readInt() != 0;
        this.mRcsIpVideoCallSupported = bl2;
        bl2 = parcel.readInt() == 0 ? bl : true;
        this.mRcsIpVideoOnlyCallSupported = bl2;
        this.mExts = parcel.createStringArray();
        this.mCapTimestamp = parcel.readLong();
    }

    @UnsupportedAppUsage
    public void setCapTimestamp(long l) {
        this.mCapTimestamp = l;
    }

    @UnsupportedAppUsage
    public void setCdViaPresenceSupported(boolean bl) {
        this.mCdViaPresenceSupported = bl;
    }

    @UnsupportedAppUsage
    public void setExts(String[] arrstring) {
        this.mExts = arrstring;
    }

    @UnsupportedAppUsage
    public void setFtHttpSupported(boolean bl) {
        this.mFtHttpSupported = bl;
    }

    @UnsupportedAppUsage
    public void setFtSnFSupported(boolean bl) {
        this.mFtSnFSupported = bl;
    }

    @UnsupportedAppUsage
    public void setFtSupported(boolean bl) {
        this.mFtSupported = bl;
    }

    @UnsupportedAppUsage
    public void setFtThumbSupported(boolean bl) {
        this.mFtThumbSupported = bl;
    }

    @UnsupportedAppUsage
    public void setFullSnFGroupChatSupported(boolean bl) {
        this.mFullSnFGroupChatSupported = bl;
    }

    @UnsupportedAppUsage
    public void setGeoPullFtSupported(boolean bl) {
        this.mGeoPullFtSupported = bl;
    }

    @UnsupportedAppUsage
    public void setGeoPullSupported(boolean bl) {
        this.mGeoPullSupported = bl;
    }

    @UnsupportedAppUsage
    public void setGeoPushSupported(boolean bl) {
        this.mGeoPushSupported = bl;
    }

    @UnsupportedAppUsage
    public void setImSupported(boolean bl) {
        this.mImSupported = bl;
    }

    @UnsupportedAppUsage
    public void setIpVideoSupported(boolean bl) {
        this.mIpVideoSupported = bl;
    }

    @UnsupportedAppUsage
    public void setIpVoiceSupported(boolean bl) {
        this.mIpVoiceSupported = bl;
    }

    @UnsupportedAppUsage
    public void setIsSupported(boolean bl) {
        this.mIsSupported = bl;
    }

    @UnsupportedAppUsage
    public void setRcsIpVideoCallSupported(boolean bl) {
        this.mRcsIpVideoCallSupported = bl;
    }

    @UnsupportedAppUsage
    public void setRcsIpVideoOnlyCallSupported(boolean bl) {
        this.mRcsIpVideoOnlyCallSupported = bl;
    }

    @UnsupportedAppUsage
    public void setRcsIpVoiceCallSupported(boolean bl) {
        this.mRcsIpVoiceCallSupported = bl;
    }

    @UnsupportedAppUsage
    public void setSmSupported(boolean bl) {
        this.mSmSupported = bl;
    }

    @UnsupportedAppUsage
    public void setSpSupported(boolean bl) {
        this.mSpSupported = bl;
    }

    @UnsupportedAppUsage
    public void setVsDuringCSSupported(boolean bl) {
        this.mVsDuringCSSupported = bl;
    }

    @UnsupportedAppUsage
    public void setVsSupported(boolean bl) {
        this.mVsSupported = bl;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt((int)this.mImSupported);
        parcel.writeInt((int)this.mFtSupported);
        parcel.writeInt((int)this.mFtThumbSupported);
        parcel.writeInt((int)this.mFtSnFSupported);
        parcel.writeInt((int)this.mFtHttpSupported);
        parcel.writeInt((int)this.mIsSupported);
        parcel.writeInt((int)this.mVsDuringCSSupported);
        parcel.writeInt((int)this.mVsSupported);
        parcel.writeInt((int)this.mSpSupported);
        parcel.writeInt((int)this.mCdViaPresenceSupported);
        parcel.writeInt((int)this.mIpVoiceSupported);
        parcel.writeInt((int)this.mIpVideoSupported);
        parcel.writeInt((int)this.mGeoPullFtSupported);
        parcel.writeInt((int)this.mGeoPullSupported);
        parcel.writeInt((int)this.mGeoPushSupported);
        parcel.writeInt((int)this.mSmSupported);
        parcel.writeInt((int)this.mFullSnFGroupChatSupported);
        parcel.writeInt((int)this.mRcsIpVoiceCallSupported);
        parcel.writeInt((int)this.mRcsIpVideoCallSupported);
        parcel.writeInt((int)this.mRcsIpVideoOnlyCallSupported);
        parcel.writeStringArray(this.mExts);
        parcel.writeLong(this.mCapTimestamp);
    }

}

