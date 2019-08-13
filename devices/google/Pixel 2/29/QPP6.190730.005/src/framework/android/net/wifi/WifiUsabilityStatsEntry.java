/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
public final class WifiUsabilityStatsEntry
implements Parcelable {
    public static final Parcelable.Creator<WifiUsabilityStatsEntry> CREATOR = new Parcelable.Creator<WifiUsabilityStatsEntry>(){

        @Override
        public WifiUsabilityStatsEntry createFromParcel(Parcel parcel) {
            return new WifiUsabilityStatsEntry(parcel.readLong(), parcel.readInt(), parcel.readInt(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readBoolean());
        }

        public WifiUsabilityStatsEntry[] newArray(int n) {
            return new WifiUsabilityStatsEntry[n];
        }
    };
    public static final int PROBE_STATUS_FAILURE = 3;
    public static final int PROBE_STATUS_NO_PROBE = 1;
    public static final int PROBE_STATUS_SUCCESS = 2;
    public static final int PROBE_STATUS_UNKNOWN = 0;
    private final int mCellularDataNetworkType;
    private final int mCellularSignalStrengthDb;
    private final int mCellularSignalStrengthDbm;
    private final boolean mIsSameRegisteredCell;
    private final int mLinkSpeedMbps;
    private final int mProbeElapsedTimeSinceLastUpdateMillis;
    private final int mProbeMcsRateSinceLastUpdate;
    private final int mProbeStatusSinceLastUpdate;
    private final int mRssi;
    private final int mRxLinkSpeedMbps;
    private final long mTimeStampMillis;
    private final long mTotalBackgroundScanTimeMillis;
    private final long mTotalBeaconRx;
    private final long mTotalCcaBusyFreqTimeMillis;
    private final long mTotalHotspot2ScanTimeMillis;
    private final long mTotalNanScanTimeMillis;
    private final long mTotalPnoScanTimeMillis;
    private final long mTotalRadioOnFreqTimeMillis;
    private final long mTotalRadioOnTimeMillis;
    private final long mTotalRadioRxTimeMillis;
    private final long mTotalRadioTxTimeMillis;
    private final long mTotalRoamScanTimeMillis;
    private final long mTotalRxSuccess;
    private final long mTotalScanTimeMillis;
    private final long mTotalTxBad;
    private final long mTotalTxRetries;
    private final long mTotalTxSuccess;

    public WifiUsabilityStatsEntry(long l, int n, int n2, long l2, long l3, long l4, long l5, long l6, long l7, long l8, long l9, long l10, long l11, long l12, long l13, long l14, long l15, long l16, long l17, int n3, int n4, int n5, int n6, int n7, int n8, int n9, boolean bl) {
        this.mTimeStampMillis = l;
        this.mRssi = n;
        this.mLinkSpeedMbps = n2;
        this.mTotalTxSuccess = l2;
        this.mTotalTxRetries = l3;
        this.mTotalTxBad = l4;
        this.mTotalRxSuccess = l5;
        this.mTotalRadioOnTimeMillis = l6;
        this.mTotalRadioTxTimeMillis = l7;
        this.mTotalRadioRxTimeMillis = l8;
        this.mTotalScanTimeMillis = l9;
        this.mTotalNanScanTimeMillis = l10;
        this.mTotalBackgroundScanTimeMillis = l11;
        this.mTotalRoamScanTimeMillis = l12;
        this.mTotalPnoScanTimeMillis = l13;
        this.mTotalHotspot2ScanTimeMillis = l14;
        this.mTotalCcaBusyFreqTimeMillis = l15;
        this.mTotalRadioOnFreqTimeMillis = l16;
        this.mTotalBeaconRx = l17;
        this.mProbeStatusSinceLastUpdate = n3;
        this.mProbeElapsedTimeSinceLastUpdateMillis = n4;
        this.mProbeMcsRateSinceLastUpdate = n5;
        this.mRxLinkSpeedMbps = n6;
        this.mCellularDataNetworkType = n7;
        this.mCellularSignalStrengthDbm = n8;
        this.mCellularSignalStrengthDb = n9;
        this.mIsSameRegisteredCell = bl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getCellularDataNetworkType() {
        return this.mCellularDataNetworkType;
    }

    public int getCellularSignalStrengthDb() {
        return this.mCellularSignalStrengthDb;
    }

    public int getCellularSignalStrengthDbm() {
        return this.mCellularSignalStrengthDbm;
    }

    public int getLinkSpeedMbps() {
        return this.mLinkSpeedMbps;
    }

    public int getProbeElapsedTimeSinceLastUpdateMillis() {
        return this.mProbeElapsedTimeSinceLastUpdateMillis;
    }

    public int getProbeMcsRateSinceLastUpdate() {
        return this.mProbeMcsRateSinceLastUpdate;
    }

    public int getProbeStatusSinceLastUpdate() {
        return this.mProbeStatusSinceLastUpdate;
    }

    public int getRssi() {
        return this.mRssi;
    }

    public int getRxLinkSpeedMbps() {
        return this.mRxLinkSpeedMbps;
    }

    public long getTimeStampMillis() {
        return this.mTimeStampMillis;
    }

    public long getTotalBackgroundScanTimeMillis() {
        return this.mTotalBackgroundScanTimeMillis;
    }

    public long getTotalBeaconRx() {
        return this.mTotalBeaconRx;
    }

    public long getTotalCcaBusyFreqTimeMillis() {
        return this.mTotalCcaBusyFreqTimeMillis;
    }

    public long getTotalHotspot2ScanTimeMillis() {
        return this.mTotalHotspot2ScanTimeMillis;
    }

    public long getTotalNanScanTimeMillis() {
        return this.mTotalNanScanTimeMillis;
    }

    public long getTotalPnoScanTimeMillis() {
        return this.mTotalPnoScanTimeMillis;
    }

    public long getTotalRadioOnFreqTimeMillis() {
        return this.mTotalRadioOnFreqTimeMillis;
    }

    public long getTotalRadioOnTimeMillis() {
        return this.mTotalRadioOnTimeMillis;
    }

    public long getTotalRadioRxTimeMillis() {
        return this.mTotalRadioRxTimeMillis;
    }

    public long getTotalRadioTxTimeMillis() {
        return this.mTotalRadioTxTimeMillis;
    }

    public long getTotalRoamScanTimeMillis() {
        return this.mTotalRoamScanTimeMillis;
    }

    public long getTotalRxSuccess() {
        return this.mTotalRxSuccess;
    }

    public long getTotalScanTimeMillis() {
        return this.mTotalScanTimeMillis;
    }

    public long getTotalTxBad() {
        return this.mTotalTxBad;
    }

    public long getTotalTxRetries() {
        return this.mTotalTxRetries;
    }

    public long getTotalTxSuccess() {
        return this.mTotalTxSuccess;
    }

    public boolean isSameRegisteredCell() {
        return this.mIsSameRegisteredCell;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.mTimeStampMillis);
        parcel.writeInt(this.mRssi);
        parcel.writeInt(this.mLinkSpeedMbps);
        parcel.writeLong(this.mTotalTxSuccess);
        parcel.writeLong(this.mTotalTxRetries);
        parcel.writeLong(this.mTotalTxBad);
        parcel.writeLong(this.mTotalRxSuccess);
        parcel.writeLong(this.mTotalRadioOnTimeMillis);
        parcel.writeLong(this.mTotalRadioTxTimeMillis);
        parcel.writeLong(this.mTotalRadioRxTimeMillis);
        parcel.writeLong(this.mTotalScanTimeMillis);
        parcel.writeLong(this.mTotalNanScanTimeMillis);
        parcel.writeLong(this.mTotalBackgroundScanTimeMillis);
        parcel.writeLong(this.mTotalRoamScanTimeMillis);
        parcel.writeLong(this.mTotalPnoScanTimeMillis);
        parcel.writeLong(this.mTotalHotspot2ScanTimeMillis);
        parcel.writeLong(this.mTotalCcaBusyFreqTimeMillis);
        parcel.writeLong(this.mTotalRadioOnFreqTimeMillis);
        parcel.writeLong(this.mTotalBeaconRx);
        parcel.writeInt(this.mProbeStatusSinceLastUpdate);
        parcel.writeInt(this.mProbeElapsedTimeSinceLastUpdateMillis);
        parcel.writeInt(this.mProbeMcsRateSinceLastUpdate);
        parcel.writeInt(this.mRxLinkSpeedMbps);
        parcel.writeInt(this.mCellularDataNetworkType);
        parcel.writeInt(this.mCellularSignalStrengthDbm);
        parcel.writeInt(this.mCellularSignalStrengthDb);
        parcel.writeBoolean(this.mIsSameRegisteredCell);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ProbeStatus {
    }

}

