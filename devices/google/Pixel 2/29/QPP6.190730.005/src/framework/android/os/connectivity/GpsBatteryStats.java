/*
 * Decompiled with CFR 0.145.
 */
package android.os.connectivity;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;

public final class GpsBatteryStats
implements Parcelable {
    public static final Parcelable.Creator<GpsBatteryStats> CREATOR = new Parcelable.Creator<GpsBatteryStats>(){

        @Override
        public GpsBatteryStats createFromParcel(Parcel parcel) {
            return new GpsBatteryStats(parcel);
        }

        public GpsBatteryStats[] newArray(int n) {
            return new GpsBatteryStats[n];
        }
    };
    private long mEnergyConsumedMaMs;
    private long mLoggingDurationMs;
    private long[] mTimeInGpsSignalQualityLevel;

    public GpsBatteryStats() {
        this.initialize();
    }

    private GpsBatteryStats(Parcel parcel) {
        this.initialize();
        this.readFromParcel(parcel);
    }

    private void initialize() {
        this.mLoggingDurationMs = 0L;
        this.mEnergyConsumedMaMs = 0L;
        this.mTimeInGpsSignalQualityLevel = new long[2];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getEnergyConsumedMaMs() {
        return this.mEnergyConsumedMaMs;
    }

    public long getLoggingDurationMs() {
        return this.mLoggingDurationMs;
    }

    public long[] getTimeInGpsSignalQualityLevel() {
        return this.mTimeInGpsSignalQualityLevel;
    }

    public void readFromParcel(Parcel parcel) {
        this.mLoggingDurationMs = parcel.readLong();
        this.mEnergyConsumedMaMs = parcel.readLong();
        parcel.readLongArray(this.mTimeInGpsSignalQualityLevel);
    }

    public void setEnergyConsumedMaMs(long l) {
        this.mEnergyConsumedMaMs = l;
    }

    public void setLoggingDurationMs(long l) {
        this.mLoggingDurationMs = l;
    }

    public void setTimeInGpsSignalQualityLevel(long[] arrl) {
        this.mTimeInGpsSignalQualityLevel = Arrays.copyOfRange(arrl, 0, Math.min(arrl.length, 2));
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.mLoggingDurationMs);
        parcel.writeLong(this.mEnergyConsumedMaMs);
        parcel.writeLongArray(this.mTimeInGpsSignalQualityLevel);
    }

}

