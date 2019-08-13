/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.UnsupportedAppUsage;
import android.net.LinkQualityInfo;
import android.os.Parcel;

public class MobileLinkQualityInfo
extends LinkQualityInfo {
    private int mCdmaDbm = Integer.MAX_VALUE;
    private int mCdmaEcio = Integer.MAX_VALUE;
    private int mEvdoDbm = Integer.MAX_VALUE;
    private int mEvdoEcio = Integer.MAX_VALUE;
    private int mEvdoSnr = Integer.MAX_VALUE;
    private int mGsmErrorRate = Integer.MAX_VALUE;
    private int mLteCqi = Integer.MAX_VALUE;
    private int mLteRsrp = Integer.MAX_VALUE;
    private int mLteRsrq = Integer.MAX_VALUE;
    private int mLteRssnr = Integer.MAX_VALUE;
    private int mLteSignalStrength = Integer.MAX_VALUE;
    private int mMobileNetworkType = Integer.MAX_VALUE;
    private int mRssi = Integer.MAX_VALUE;

    public static MobileLinkQualityInfo createFromParcelBody(Parcel parcel) {
        MobileLinkQualityInfo mobileLinkQualityInfo = new MobileLinkQualityInfo();
        mobileLinkQualityInfo.initializeFromParcel(parcel);
        mobileLinkQualityInfo.mMobileNetworkType = parcel.readInt();
        mobileLinkQualityInfo.mRssi = parcel.readInt();
        mobileLinkQualityInfo.mGsmErrorRate = parcel.readInt();
        mobileLinkQualityInfo.mCdmaDbm = parcel.readInt();
        mobileLinkQualityInfo.mCdmaEcio = parcel.readInt();
        mobileLinkQualityInfo.mEvdoDbm = parcel.readInt();
        mobileLinkQualityInfo.mEvdoEcio = parcel.readInt();
        mobileLinkQualityInfo.mEvdoSnr = parcel.readInt();
        mobileLinkQualityInfo.mLteSignalStrength = parcel.readInt();
        mobileLinkQualityInfo.mLteRsrp = parcel.readInt();
        mobileLinkQualityInfo.mLteRsrq = parcel.readInt();
        mobileLinkQualityInfo.mLteRssnr = parcel.readInt();
        mobileLinkQualityInfo.mLteCqi = parcel.readInt();
        return mobileLinkQualityInfo;
    }

    public int getCdmaDbm() {
        return this.mCdmaDbm;
    }

    public int getCdmaEcio() {
        return this.mCdmaEcio;
    }

    public int getEvdoDbm() {
        return this.mEvdoDbm;
    }

    public int getEvdoEcio() {
        return this.mEvdoEcio;
    }

    public int getEvdoSnr() {
        return this.mEvdoSnr;
    }

    public int getGsmErrorRate() {
        return this.mGsmErrorRate;
    }

    public int getLteCqi() {
        return this.mLteCqi;
    }

    public int getLteRsrp() {
        return this.mLteRsrp;
    }

    public int getLteRsrq() {
        return this.mLteRsrq;
    }

    public int getLteRssnr() {
        return this.mLteRssnr;
    }

    public int getLteSignalStrength() {
        return this.mLteSignalStrength;
    }

    @UnsupportedAppUsage
    public int getMobileNetworkType() {
        return this.mMobileNetworkType;
    }

    public int getRssi() {
        return this.mRssi;
    }

    @UnsupportedAppUsage
    public void setCdmaDbm(int n) {
        this.mCdmaDbm = n;
    }

    @UnsupportedAppUsage
    public void setCdmaEcio(int n) {
        this.mCdmaEcio = n;
    }

    @UnsupportedAppUsage
    public void setEvdoDbm(int n) {
        this.mEvdoDbm = n;
    }

    @UnsupportedAppUsage
    public void setEvdoEcio(int n) {
        this.mEvdoEcio = n;
    }

    @UnsupportedAppUsage
    public void setEvdoSnr(int n) {
        this.mEvdoSnr = n;
    }

    @UnsupportedAppUsage
    public void setGsmErrorRate(int n) {
        this.mGsmErrorRate = n;
    }

    @UnsupportedAppUsage
    public void setLteCqi(int n) {
        this.mLteCqi = n;
    }

    @UnsupportedAppUsage
    public void setLteRsrp(int n) {
        this.mLteRsrp = n;
    }

    @UnsupportedAppUsage
    public void setLteRsrq(int n) {
        this.mLteRsrq = n;
    }

    @UnsupportedAppUsage
    public void setLteRssnr(int n) {
        this.mLteRssnr = n;
    }

    @UnsupportedAppUsage
    public void setLteSignalStrength(int n) {
        this.mLteSignalStrength = n;
    }

    @UnsupportedAppUsage
    public void setMobileNetworkType(int n) {
        this.mMobileNetworkType = n;
    }

    @UnsupportedAppUsage
    public void setRssi(int n) {
        this.mRssi = n;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n, 3);
        parcel.writeInt(this.mMobileNetworkType);
        parcel.writeInt(this.mRssi);
        parcel.writeInt(this.mGsmErrorRate);
        parcel.writeInt(this.mCdmaDbm);
        parcel.writeInt(this.mCdmaEcio);
        parcel.writeInt(this.mEvdoDbm);
        parcel.writeInt(this.mEvdoEcio);
        parcel.writeInt(this.mEvdoSnr);
        parcel.writeInt(this.mLteSignalStrength);
        parcel.writeInt(this.mLteRsrp);
        parcel.writeInt(this.mLteRsrq);
        parcel.writeInt(this.mLteRssnr);
        parcel.writeInt(this.mLteCqi);
    }
}

