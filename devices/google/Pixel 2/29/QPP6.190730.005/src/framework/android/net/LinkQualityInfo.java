/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.UnsupportedAppUsage;
import android.net.MobileLinkQualityInfo;
import android.net.WifiLinkQualityInfo;
import android.os.Parcel;
import android.os.Parcelable;

public class LinkQualityInfo
implements Parcelable {
    public static final Parcelable.Creator<LinkQualityInfo> CREATOR = new Parcelable.Creator<LinkQualityInfo>(){

        @Override
        public LinkQualityInfo createFromParcel(Parcel parcel) {
            int n = parcel.readInt();
            if (n == 1) {
                LinkQualityInfo linkQualityInfo = new LinkQualityInfo();
                linkQualityInfo.initializeFromParcel(parcel);
                return linkQualityInfo;
            }
            if (n == 2) {
                return WifiLinkQualityInfo.createFromParcelBody(parcel);
            }
            if (n == 3) {
                return MobileLinkQualityInfo.createFromParcelBody(parcel);
            }
            return null;
        }

        public LinkQualityInfo[] newArray(int n) {
            return new LinkQualityInfo[n];
        }
    };
    public static final int NORMALIZED_MAX_SIGNAL_STRENGTH = 99;
    public static final int NORMALIZED_MIN_SIGNAL_STRENGTH = 0;
    public static final int NORMALIZED_SIGNAL_STRENGTH_RANGE = 100;
    protected static final int OBJECT_TYPE_LINK_QUALITY_INFO = 1;
    protected static final int OBJECT_TYPE_MOBILE_LINK_QUALITY_INFO = 3;
    protected static final int OBJECT_TYPE_WIFI_LINK_QUALITY_INFO = 2;
    public static final int UNKNOWN_INT = Integer.MAX_VALUE;
    public static final long UNKNOWN_LONG = Long.MAX_VALUE;
    private int mDataSampleDuration = Integer.MAX_VALUE;
    private long mLastDataSampleTime = Long.MAX_VALUE;
    private int mNetworkType = -1;
    private int mNormalizedSignalStrength = Integer.MAX_VALUE;
    private long mPacketCount = Long.MAX_VALUE;
    private long mPacketErrorCount = Long.MAX_VALUE;
    private int mTheoreticalLatency = Integer.MAX_VALUE;
    private int mTheoreticalRxBandwidth = Integer.MAX_VALUE;
    private int mTheoreticalTxBandwidth = Integer.MAX_VALUE;

    @Override
    public int describeContents() {
        return 0;
    }

    public int getDataSampleDuration() {
        return this.mDataSampleDuration;
    }

    public long getLastDataSampleTime() {
        return this.mLastDataSampleTime;
    }

    public int getNetworkType() {
        return this.mNetworkType;
    }

    public int getNormalizedSignalStrength() {
        return this.mNormalizedSignalStrength;
    }

    public long getPacketCount() {
        return this.mPacketCount;
    }

    public long getPacketErrorCount() {
        return this.mPacketErrorCount;
    }

    public int getTheoreticalLatency() {
        return this.mTheoreticalLatency;
    }

    public int getTheoreticalRxBandwidth() {
        return this.mTheoreticalRxBandwidth;
    }

    public int getTheoreticalTxBandwidth() {
        return this.mTheoreticalTxBandwidth;
    }

    protected void initializeFromParcel(Parcel parcel) {
        this.mNetworkType = parcel.readInt();
        this.mNormalizedSignalStrength = parcel.readInt();
        this.mPacketCount = parcel.readLong();
        this.mPacketErrorCount = parcel.readLong();
        this.mTheoreticalTxBandwidth = parcel.readInt();
        this.mTheoreticalRxBandwidth = parcel.readInt();
        this.mTheoreticalLatency = parcel.readInt();
        this.mLastDataSampleTime = parcel.readLong();
        this.mDataSampleDuration = parcel.readInt();
    }

    @UnsupportedAppUsage
    public void setDataSampleDuration(int n) {
        this.mDataSampleDuration = n;
    }

    @UnsupportedAppUsage
    public void setLastDataSampleTime(long l) {
        this.mLastDataSampleTime = l;
    }

    public void setNetworkType(int n) {
        this.mNetworkType = n;
    }

    public void setNormalizedSignalStrength(int n) {
        this.mNormalizedSignalStrength = n;
    }

    @UnsupportedAppUsage
    public void setPacketCount(long l) {
        this.mPacketCount = l;
    }

    @UnsupportedAppUsage
    public void setPacketErrorCount(long l) {
        this.mPacketErrorCount = l;
    }

    public void setTheoreticalLatency(int n) {
        this.mTheoreticalLatency = n;
    }

    public void setTheoreticalRxBandwidth(int n) {
        this.mTheoreticalRxBandwidth = n;
    }

    public void setTheoreticalTxBandwidth(int n) {
        this.mTheoreticalTxBandwidth = n;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.writeToParcel(parcel, n, 1);
    }

    public void writeToParcel(Parcel parcel, int n, int n2) {
        parcel.writeInt(n2);
        parcel.writeInt(this.mNetworkType);
        parcel.writeInt(this.mNormalizedSignalStrength);
        parcel.writeLong(this.mPacketCount);
        parcel.writeLong(this.mPacketErrorCount);
        parcel.writeInt(this.mTheoreticalTxBandwidth);
        parcel.writeInt(this.mTheoreticalRxBandwidth);
        parcel.writeInt(this.mTheoreticalLatency);
        parcel.writeLong(this.mLastDataSampleTime);
        parcel.writeInt(this.mDataSampleDuration);
    }

}

