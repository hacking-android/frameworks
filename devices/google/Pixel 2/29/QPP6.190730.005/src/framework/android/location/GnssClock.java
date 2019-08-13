/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.os.Parcel;
import android.os.Parcelable;

public final class GnssClock
implements Parcelable {
    public static final Parcelable.Creator<GnssClock> CREATOR = new Parcelable.Creator<GnssClock>(){

        @Override
        public GnssClock createFromParcel(Parcel parcel) {
            GnssClock gnssClock = new GnssClock();
            gnssClock.mFlags = parcel.readInt();
            gnssClock.mLeapSecond = parcel.readInt();
            gnssClock.mTimeNanos = parcel.readLong();
            gnssClock.mTimeUncertaintyNanos = parcel.readDouble();
            gnssClock.mFullBiasNanos = parcel.readLong();
            gnssClock.mBiasNanos = parcel.readDouble();
            gnssClock.mBiasUncertaintyNanos = parcel.readDouble();
            gnssClock.mDriftNanosPerSecond = parcel.readDouble();
            gnssClock.mDriftUncertaintyNanosPerSecond = parcel.readDouble();
            gnssClock.mHardwareClockDiscontinuityCount = parcel.readInt();
            gnssClock.mElapsedRealtimeNanos = parcel.readLong();
            gnssClock.mElapsedRealtimeUncertaintyNanos = parcel.readDouble();
            return gnssClock;
        }

        public GnssClock[] newArray(int n) {
            return new GnssClock[n];
        }
    };
    private static final int HAS_BIAS = 8;
    private static final int HAS_BIAS_UNCERTAINTY = 16;
    private static final int HAS_DRIFT = 32;
    private static final int HAS_DRIFT_UNCERTAINTY = 64;
    private static final int HAS_ELAPSED_REALTIME_NANOS = 128;
    private static final int HAS_ELAPSED_REALTIME_UNCERTAINTY_NANOS = 256;
    private static final int HAS_FULL_BIAS = 4;
    private static final int HAS_LEAP_SECOND = 1;
    private static final int HAS_NO_FLAGS = 0;
    private static final int HAS_TIME_UNCERTAINTY = 2;
    private double mBiasNanos;
    private double mBiasUncertaintyNanos;
    private double mDriftNanosPerSecond;
    private double mDriftUncertaintyNanosPerSecond;
    private long mElapsedRealtimeNanos;
    private double mElapsedRealtimeUncertaintyNanos;
    private int mFlags;
    private long mFullBiasNanos;
    private int mHardwareClockDiscontinuityCount;
    private int mLeapSecond;
    private long mTimeNanos;
    private double mTimeUncertaintyNanos;

    public GnssClock() {
        this.initialize();
    }

    private void initialize() {
        this.mFlags = 0;
        this.resetLeapSecond();
        this.setTimeNanos(Long.MIN_VALUE);
        this.resetTimeUncertaintyNanos();
        this.resetFullBiasNanos();
        this.resetBiasNanos();
        this.resetBiasUncertaintyNanos();
        this.resetDriftNanosPerSecond();
        this.resetDriftUncertaintyNanosPerSecond();
        this.setHardwareClockDiscontinuityCount(Integer.MIN_VALUE);
        this.resetElapsedRealtimeNanos();
        this.resetElapsedRealtimeUncertaintyNanos();
    }

    private boolean isFlagSet(int n) {
        boolean bl = (this.mFlags & n) == n;
        return bl;
    }

    private void resetFlag(int n) {
        this.mFlags &= n;
    }

    private void setFlag(int n) {
        this.mFlags |= n;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public double getBiasNanos() {
        return this.mBiasNanos;
    }

    public double getBiasUncertaintyNanos() {
        return this.mBiasUncertaintyNanos;
    }

    public double getDriftNanosPerSecond() {
        return this.mDriftNanosPerSecond;
    }

    public double getDriftUncertaintyNanosPerSecond() {
        return this.mDriftUncertaintyNanosPerSecond;
    }

    public long getElapsedRealtimeNanos() {
        return this.mElapsedRealtimeNanos;
    }

    public double getElapsedRealtimeUncertaintyNanos() {
        return this.mElapsedRealtimeUncertaintyNanos;
    }

    public long getFullBiasNanos() {
        return this.mFullBiasNanos;
    }

    public int getHardwareClockDiscontinuityCount() {
        return this.mHardwareClockDiscontinuityCount;
    }

    public int getLeapSecond() {
        return this.mLeapSecond;
    }

    public long getTimeNanos() {
        return this.mTimeNanos;
    }

    public double getTimeUncertaintyNanos() {
        return this.mTimeUncertaintyNanos;
    }

    public boolean hasBiasNanos() {
        return this.isFlagSet(8);
    }

    public boolean hasBiasUncertaintyNanos() {
        return this.isFlagSet(16);
    }

    public boolean hasDriftNanosPerSecond() {
        return this.isFlagSet(32);
    }

    public boolean hasDriftUncertaintyNanosPerSecond() {
        return this.isFlagSet(64);
    }

    public boolean hasElapsedRealtimeNanos() {
        return this.isFlagSet(128);
    }

    public boolean hasElapsedRealtimeUncertaintyNanos() {
        return this.isFlagSet(256);
    }

    public boolean hasFullBiasNanos() {
        return this.isFlagSet(4);
    }

    public boolean hasLeapSecond() {
        return this.isFlagSet(1);
    }

    public boolean hasTimeUncertaintyNanos() {
        return this.isFlagSet(2);
    }

    public void reset() {
        this.initialize();
    }

    public void resetBiasNanos() {
        this.resetFlag(8);
        this.mBiasNanos = Double.NaN;
    }

    public void resetBiasUncertaintyNanos() {
        this.resetFlag(16);
        this.mBiasUncertaintyNanos = Double.NaN;
    }

    public void resetDriftNanosPerSecond() {
        this.resetFlag(32);
        this.mDriftNanosPerSecond = Double.NaN;
    }

    public void resetDriftUncertaintyNanosPerSecond() {
        this.resetFlag(64);
        this.mDriftUncertaintyNanosPerSecond = Double.NaN;
    }

    public void resetElapsedRealtimeNanos() {
        this.resetFlag(128);
        this.mElapsedRealtimeNanos = 0L;
    }

    public void resetElapsedRealtimeUncertaintyNanos() {
        this.resetFlag(256);
        this.mElapsedRealtimeUncertaintyNanos = Double.NaN;
    }

    public void resetFullBiasNanos() {
        this.resetFlag(4);
        this.mFullBiasNanos = Long.MIN_VALUE;
    }

    public void resetLeapSecond() {
        this.resetFlag(1);
        this.mLeapSecond = Integer.MIN_VALUE;
    }

    public void resetTimeUncertaintyNanos() {
        this.resetFlag(2);
        this.mTimeUncertaintyNanos = Double.NaN;
    }

    public void set(GnssClock gnssClock) {
        this.mFlags = gnssClock.mFlags;
        this.mLeapSecond = gnssClock.mLeapSecond;
        this.mTimeNanos = gnssClock.mTimeNanos;
        this.mTimeUncertaintyNanos = gnssClock.mTimeUncertaintyNanos;
        this.mFullBiasNanos = gnssClock.mFullBiasNanos;
        this.mBiasNanos = gnssClock.mBiasNanos;
        this.mBiasUncertaintyNanos = gnssClock.mBiasUncertaintyNanos;
        this.mDriftNanosPerSecond = gnssClock.mDriftNanosPerSecond;
        this.mDriftUncertaintyNanosPerSecond = gnssClock.mDriftUncertaintyNanosPerSecond;
        this.mHardwareClockDiscontinuityCount = gnssClock.mHardwareClockDiscontinuityCount;
        this.mElapsedRealtimeNanos = gnssClock.mElapsedRealtimeNanos;
        this.mElapsedRealtimeUncertaintyNanos = gnssClock.mElapsedRealtimeUncertaintyNanos;
    }

    public void setBiasNanos(double d) {
        this.setFlag(8);
        this.mBiasNanos = d;
    }

    public void setBiasUncertaintyNanos(double d) {
        this.setFlag(16);
        this.mBiasUncertaintyNanos = d;
    }

    public void setDriftNanosPerSecond(double d) {
        this.setFlag(32);
        this.mDriftNanosPerSecond = d;
    }

    public void setDriftUncertaintyNanosPerSecond(double d) {
        this.setFlag(64);
        this.mDriftUncertaintyNanosPerSecond = d;
    }

    public void setElapsedRealtimeNanos(long l) {
        this.setFlag(128);
        this.mElapsedRealtimeNanos = l;
    }

    public void setElapsedRealtimeUncertaintyNanos(double d) {
        this.setFlag(256);
        this.mElapsedRealtimeUncertaintyNanos = d;
    }

    public void setFullBiasNanos(long l) {
        this.setFlag(4);
        this.mFullBiasNanos = l;
    }

    public void setHardwareClockDiscontinuityCount(int n) {
        this.mHardwareClockDiscontinuityCount = n;
    }

    public void setLeapSecond(int n) {
        this.setFlag(1);
        this.mLeapSecond = n;
    }

    public void setTimeNanos(long l) {
        this.mTimeNanos = l;
    }

    public void setTimeUncertaintyNanos(double d) {
        this.setFlag(2);
        this.mTimeUncertaintyNanos = d;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("GnssClock:\n");
        boolean bl = this.hasLeapSecond();
        Object var3_3 = null;
        Number number = bl ? Integer.valueOf(this.mLeapSecond) : null;
        stringBuilder.append(String.format("   %-15s = %s\n", "LeapSecond", number));
        long l = this.mTimeNanos;
        number = this.hasTimeUncertaintyNanos() ? Double.valueOf(this.mTimeUncertaintyNanos) : null;
        stringBuilder.append(String.format("   %-15s = %-25s   %-26s = %s\n", "TimeNanos", l, "TimeUncertaintyNanos", number));
        number = this.hasFullBiasNanos() ? Long.valueOf(this.mFullBiasNanos) : null;
        stringBuilder.append(String.format("   %-15s = %s\n", "FullBiasNanos", number));
        number = this.hasBiasNanos() ? Double.valueOf(this.mBiasNanos) : null;
        Double d = this.hasBiasUncertaintyNanos() ? Double.valueOf(this.mBiasUncertaintyNanos) : null;
        stringBuilder.append(String.format("   %-15s = %-25s   %-26s = %s\n", "BiasNanos", number, "BiasUncertaintyNanos", d));
        number = this.hasDriftNanosPerSecond() ? Double.valueOf(this.mDriftNanosPerSecond) : null;
        d = this.hasDriftUncertaintyNanosPerSecond() ? Double.valueOf(this.mDriftUncertaintyNanosPerSecond) : null;
        stringBuilder.append(String.format("   %-15s = %-25s   %-26s = %s\n", "DriftNanosPerSecond", number, "DriftUncertaintyNanosPerSecond", d));
        stringBuilder.append(String.format("   %-15s = %s\n", "HardwareClockDiscontinuityCount", this.mHardwareClockDiscontinuityCount));
        number = this.hasElapsedRealtimeNanos() ? Long.valueOf(this.mElapsedRealtimeNanos) : null;
        stringBuilder.append(String.format("   %-15s = %s\n", "ElapsedRealtimeNanos", number));
        number = var3_3;
        if (this.hasElapsedRealtimeUncertaintyNanos()) {
            number = this.mElapsedRealtimeUncertaintyNanos;
        }
        stringBuilder.append(String.format("   %-15s = %s\n", "ElapsedRealtimeUncertaintyNanos", number));
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mFlags);
        parcel.writeInt(this.mLeapSecond);
        parcel.writeLong(this.mTimeNanos);
        parcel.writeDouble(this.mTimeUncertaintyNanos);
        parcel.writeLong(this.mFullBiasNanos);
        parcel.writeDouble(this.mBiasNanos);
        parcel.writeDouble(this.mBiasUncertaintyNanos);
        parcel.writeDouble(this.mDriftNanosPerSecond);
        parcel.writeDouble(this.mDriftUncertaintyNanosPerSecond);
        parcel.writeInt(this.mHardwareClockDiscontinuityCount);
        parcel.writeLong(this.mElapsedRealtimeNanos);
        parcel.writeDouble(this.mElapsedRealtimeUncertaintyNanos);
    }

}

