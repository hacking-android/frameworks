/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.LinkQualityInfo;
import android.os.Parcel;

public class WifiLinkQualityInfo
extends LinkQualityInfo {
    private String mBssid;
    private int mRssi = Integer.MAX_VALUE;
    private long mTxBad = Long.MAX_VALUE;
    private long mTxGood = Long.MAX_VALUE;
    private int mType = Integer.MAX_VALUE;

    public static WifiLinkQualityInfo createFromParcelBody(Parcel parcel) {
        WifiLinkQualityInfo wifiLinkQualityInfo = new WifiLinkQualityInfo();
        wifiLinkQualityInfo.initializeFromParcel(parcel);
        wifiLinkQualityInfo.mType = parcel.readInt();
        wifiLinkQualityInfo.mRssi = parcel.readInt();
        wifiLinkQualityInfo.mTxGood = parcel.readLong();
        wifiLinkQualityInfo.mTxBad = parcel.readLong();
        wifiLinkQualityInfo.mBssid = parcel.readString();
        return wifiLinkQualityInfo;
    }

    public String getBssid() {
        return this.mBssid;
    }

    public int getRssi() {
        return this.mRssi;
    }

    public long getTxBad() {
        return this.mTxBad;
    }

    public long getTxGood() {
        return this.mTxGood;
    }

    public int getType() {
        return this.mType;
    }

    public void setBssid(String string2) {
        this.mBssid = string2;
    }

    public void setRssi(int n) {
        this.mRssi = n;
    }

    public void setTxBad(long l) {
        this.mTxBad = l;
    }

    public void setTxGood(long l) {
        this.mTxGood = l;
    }

    public void setType(int n) {
        this.mType = n;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n, 2);
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mRssi);
        parcel.writeLong(this.mTxGood);
        parcel.writeLong(this.mTxBad);
        parcel.writeString(this.mBssid);
    }
}

