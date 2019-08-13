/*
 * Decompiled with CFR 0.145.
 */
package android.os.connectivity;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;

public final class WifiBatteryStats
implements Parcelable {
    public static final Parcelable.Creator<WifiBatteryStats> CREATOR = new Parcelable.Creator<WifiBatteryStats>(){

        @Override
        public WifiBatteryStats createFromParcel(Parcel parcel) {
            return new WifiBatteryStats(parcel);
        }

        public WifiBatteryStats[] newArray(int n) {
            return new WifiBatteryStats[n];
        }
    };
    private long mEnergyConsumedMaMs;
    private long mIdleTimeMs;
    private long mKernelActiveTimeMs;
    private long mLoggingDurationMs;
    private long mMonitoredRailChargeConsumedMaMs;
    private long mNumAppScanRequest;
    private long mNumBytesRx;
    private long mNumBytesTx;
    private long mNumPacketsRx;
    private long mNumPacketsTx;
    private long mRxTimeMs;
    private long mScanTimeMs;
    private long mSleepTimeMs;
    private long[] mTimeInRxSignalStrengthLevelMs;
    private long[] mTimeInStateMs;
    private long[] mTimeInSupplicantStateMs;
    private long mTxTimeMs;

    public WifiBatteryStats() {
        this.initialize();
    }

    private WifiBatteryStats(Parcel parcel) {
        this.initialize();
        this.readFromParcel(parcel);
    }

    private void initialize() {
        this.mLoggingDurationMs = 0L;
        this.mKernelActiveTimeMs = 0L;
        this.mNumPacketsTx = 0L;
        this.mNumBytesTx = 0L;
        this.mNumPacketsRx = 0L;
        this.mNumBytesRx = 0L;
        this.mSleepTimeMs = 0L;
        this.mScanTimeMs = 0L;
        this.mIdleTimeMs = 0L;
        this.mRxTimeMs = 0L;
        this.mTxTimeMs = 0L;
        this.mEnergyConsumedMaMs = 0L;
        this.mNumAppScanRequest = 0L;
        this.mTimeInStateMs = new long[8];
        Arrays.fill(this.mTimeInStateMs, 0L);
        this.mTimeInRxSignalStrengthLevelMs = new long[5];
        Arrays.fill(this.mTimeInRxSignalStrengthLevelMs, 0L);
        this.mTimeInSupplicantStateMs = new long[13];
        Arrays.fill(this.mTimeInSupplicantStateMs, 0L);
        this.mMonitoredRailChargeConsumedMaMs = 0L;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getEnergyConsumedMaMs() {
        return this.mEnergyConsumedMaMs;
    }

    public long getIdleTimeMs() {
        return this.mIdleTimeMs;
    }

    public long getKernelActiveTimeMs() {
        return this.mKernelActiveTimeMs;
    }

    public long getLoggingDurationMs() {
        return this.mLoggingDurationMs;
    }

    public long getMonitoredRailChargeConsumedMaMs() {
        return this.mMonitoredRailChargeConsumedMaMs;
    }

    public long getNumAppScanRequest() {
        return this.mNumAppScanRequest;
    }

    public long getNumBytesRx() {
        return this.mNumBytesRx;
    }

    public long getNumBytesTx() {
        return this.mNumBytesTx;
    }

    public long getNumPacketsRx() {
        return this.mNumPacketsRx;
    }

    public long getNumPacketsTx() {
        return this.mNumPacketsTx;
    }

    public long getRxTimeMs() {
        return this.mRxTimeMs;
    }

    public long getScanTimeMs() {
        return this.mScanTimeMs;
    }

    public long getSleepTimeMs() {
        return this.mSleepTimeMs;
    }

    public long[] getTimeInRxSignalStrengthLevelMs() {
        return this.mTimeInRxSignalStrengthLevelMs;
    }

    public long[] getTimeInStateMs() {
        return this.mTimeInStateMs;
    }

    public long[] getTimeInSupplicantStateMs() {
        return this.mTimeInSupplicantStateMs;
    }

    public long getTxTimeMs() {
        return this.mTxTimeMs;
    }

    public void readFromParcel(Parcel parcel) {
        this.mLoggingDurationMs = parcel.readLong();
        this.mKernelActiveTimeMs = parcel.readLong();
        this.mNumPacketsTx = parcel.readLong();
        this.mNumBytesTx = parcel.readLong();
        this.mNumPacketsRx = parcel.readLong();
        this.mNumBytesRx = parcel.readLong();
        this.mSleepTimeMs = parcel.readLong();
        this.mScanTimeMs = parcel.readLong();
        this.mIdleTimeMs = parcel.readLong();
        this.mRxTimeMs = parcel.readLong();
        this.mTxTimeMs = parcel.readLong();
        this.mEnergyConsumedMaMs = parcel.readLong();
        this.mNumAppScanRequest = parcel.readLong();
        parcel.readLongArray(this.mTimeInStateMs);
        parcel.readLongArray(this.mTimeInRxSignalStrengthLevelMs);
        parcel.readLongArray(this.mTimeInSupplicantStateMs);
        this.mMonitoredRailChargeConsumedMaMs = parcel.readLong();
    }

    public void setEnergyConsumedMaMs(long l) {
        this.mEnergyConsumedMaMs = l;
    }

    public void setIdleTimeMs(long l) {
        this.mIdleTimeMs = l;
    }

    public void setKernelActiveTimeMs(long l) {
        this.mKernelActiveTimeMs = l;
    }

    public void setLoggingDurationMs(long l) {
        this.mLoggingDurationMs = l;
    }

    public void setMonitoredRailChargeConsumedMaMs(long l) {
        this.mMonitoredRailChargeConsumedMaMs = l;
    }

    public void setNumAppScanRequest(long l) {
        this.mNumAppScanRequest = l;
    }

    public void setNumBytesRx(long l) {
        this.mNumBytesRx = l;
    }

    public void setNumBytesTx(long l) {
        this.mNumBytesTx = l;
    }

    public void setNumPacketsRx(long l) {
        this.mNumPacketsRx = l;
    }

    public void setNumPacketsTx(long l) {
        this.mNumPacketsTx = l;
    }

    public void setRxTimeMs(long l) {
        this.mRxTimeMs = l;
    }

    public void setScanTimeMs(long l) {
        this.mScanTimeMs = l;
    }

    public void setSleepTimeMs(long l) {
        this.mSleepTimeMs = l;
    }

    public void setTimeInRxSignalStrengthLevelMs(long[] arrl) {
        this.mTimeInRxSignalStrengthLevelMs = Arrays.copyOfRange(arrl, 0, Math.min(arrl.length, 5));
    }

    public void setTimeInStateMs(long[] arrl) {
        this.mTimeInStateMs = Arrays.copyOfRange(arrl, 0, Math.min(arrl.length, 8));
    }

    public void setTimeInSupplicantStateMs(long[] arrl) {
        this.mTimeInSupplicantStateMs = Arrays.copyOfRange(arrl, 0, Math.min(arrl.length, 13));
    }

    public void setTxTimeMs(long l) {
        this.mTxTimeMs = l;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.mLoggingDurationMs);
        parcel.writeLong(this.mKernelActiveTimeMs);
        parcel.writeLong(this.mNumPacketsTx);
        parcel.writeLong(this.mNumBytesTx);
        parcel.writeLong(this.mNumPacketsRx);
        parcel.writeLong(this.mNumBytesRx);
        parcel.writeLong(this.mSleepTimeMs);
        parcel.writeLong(this.mScanTimeMs);
        parcel.writeLong(this.mIdleTimeMs);
        parcel.writeLong(this.mRxTimeMs);
        parcel.writeLong(this.mTxTimeMs);
        parcel.writeLong(this.mEnergyConsumedMaMs);
        parcel.writeLong(this.mNumAppScanRequest);
        parcel.writeLongArray(this.mTimeInStateMs);
        parcel.writeLongArray(this.mTimeInRxSignalStrengthLevelMs);
        parcel.writeLongArray(this.mTimeInSupplicantStateMs);
        parcel.writeLong(this.mMonitoredRailChargeConsumedMaMs);
    }

}

