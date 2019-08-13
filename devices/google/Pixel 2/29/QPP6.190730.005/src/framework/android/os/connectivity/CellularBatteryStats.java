/*
 * Decompiled with CFR 0.145.
 */
package android.os.connectivity;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;

public final class CellularBatteryStats
implements Parcelable {
    public static final Parcelable.Creator<CellularBatteryStats> CREATOR = new Parcelable.Creator<CellularBatteryStats>(){

        @Override
        public CellularBatteryStats createFromParcel(Parcel parcel) {
            return new CellularBatteryStats(parcel);
        }

        public CellularBatteryStats[] newArray(int n) {
            return new CellularBatteryStats[n];
        }
    };
    private long mEnergyConsumedMaMs;
    private long mIdleTimeMs;
    private long mKernelActiveTimeMs;
    private long mLoggingDurationMs;
    private long mMonitoredRailChargeConsumedMaMs;
    private long mNumBytesRx;
    private long mNumBytesTx;
    private long mNumPacketsRx;
    private long mNumPacketsTx;
    private long mRxTimeMs;
    private long mSleepTimeMs;
    private long[] mTimeInRatMs;
    private long[] mTimeInRxSignalStrengthLevelMs;
    private long[] mTxTimeMs;

    public CellularBatteryStats() {
        this.initialize();
    }

    private CellularBatteryStats(Parcel parcel) {
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
        this.mIdleTimeMs = 0L;
        this.mRxTimeMs = 0L;
        this.mEnergyConsumedMaMs = 0L;
        this.mTimeInRatMs = new long[22];
        Arrays.fill(this.mTimeInRatMs, 0L);
        this.mTimeInRxSignalStrengthLevelMs = new long[5];
        Arrays.fill(this.mTimeInRxSignalStrengthLevelMs, 0L);
        this.mTxTimeMs = new long[5];
        Arrays.fill(this.mTxTimeMs, 0L);
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

    public long getSleepTimeMs() {
        return this.mSleepTimeMs;
    }

    public long[] getTimeInRatMs() {
        return this.mTimeInRatMs;
    }

    public long[] getTimeInRxSignalStrengthLevelMs() {
        return this.mTimeInRxSignalStrengthLevelMs;
    }

    public long[] getTxTimeMs() {
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
        this.mIdleTimeMs = parcel.readLong();
        this.mRxTimeMs = parcel.readLong();
        this.mEnergyConsumedMaMs = parcel.readLong();
        parcel.readLongArray(this.mTimeInRatMs);
        parcel.readLongArray(this.mTimeInRxSignalStrengthLevelMs);
        parcel.readLongArray(this.mTxTimeMs);
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

    public void setSleepTimeMs(long l) {
        this.mSleepTimeMs = l;
    }

    public void setTimeInRatMs(long[] arrl) {
        this.mTimeInRatMs = Arrays.copyOfRange(arrl, 0, Math.min(arrl.length, 22));
    }

    public void setTimeInRxSignalStrengthLevelMs(long[] arrl) {
        this.mTimeInRxSignalStrengthLevelMs = Arrays.copyOfRange(arrl, 0, Math.min(arrl.length, 5));
    }

    public void setTxTimeMs(long[] arrl) {
        this.mTxTimeMs = Arrays.copyOfRange(arrl, 0, Math.min(arrl.length, 5));
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
        parcel.writeLong(this.mIdleTimeMs);
        parcel.writeLong(this.mRxTimeMs);
        parcel.writeLong(this.mEnergyConsumedMaMs);
        parcel.writeLongArray(this.mTimeInRatMs);
        parcel.writeLongArray(this.mTimeInRxSignalStrengthLevelMs);
        parcel.writeLongArray(this.mTxTimeMs);
        parcel.writeLong(this.mMonitoredRailChargeConsumedMaMs);
    }

}

