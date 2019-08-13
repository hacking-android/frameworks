/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public class GpsClock
implements Parcelable {
    public static final Parcelable.Creator<GpsClock> CREATOR = new Parcelable.Creator<GpsClock>(){

        @Override
        public GpsClock createFromParcel(Parcel parcel) {
            GpsClock gpsClock = new GpsClock();
            gpsClock.mFlags = (short)parcel.readInt();
            gpsClock.mLeapSecond = (short)parcel.readInt();
            gpsClock.mType = parcel.readByte();
            gpsClock.mTimeInNs = parcel.readLong();
            gpsClock.mTimeUncertaintyInNs = parcel.readDouble();
            gpsClock.mFullBiasInNs = parcel.readLong();
            gpsClock.mBiasInNs = parcel.readDouble();
            gpsClock.mBiasUncertaintyInNs = parcel.readDouble();
            gpsClock.mDriftInNsPerSec = parcel.readDouble();
            gpsClock.mDriftUncertaintyInNsPerSec = parcel.readDouble();
            return gpsClock;
        }

        public GpsClock[] newArray(int n) {
            return new GpsClock[n];
        }
    };
    private static final short HAS_BIAS = 8;
    private static final short HAS_BIAS_UNCERTAINTY = 16;
    private static final short HAS_DRIFT = 32;
    private static final short HAS_DRIFT_UNCERTAINTY = 64;
    private static final short HAS_FULL_BIAS = 4;
    private static final short HAS_LEAP_SECOND = 1;
    private static final short HAS_NO_FLAGS = 0;
    private static final short HAS_TIME_UNCERTAINTY = 2;
    public static final byte TYPE_GPS_TIME = 2;
    public static final byte TYPE_LOCAL_HW_TIME = 1;
    public static final byte TYPE_UNKNOWN = 0;
    private double mBiasInNs;
    private double mBiasUncertaintyInNs;
    private double mDriftInNsPerSec;
    private double mDriftUncertaintyInNsPerSec;
    private short mFlags;
    private long mFullBiasInNs;
    private short mLeapSecond;
    private long mTimeInNs;
    private double mTimeUncertaintyInNs;
    private byte mType;

    GpsClock() {
        this.initialize();
    }

    private String getTypeString() {
        byte by = this.mType;
        if (by != 0) {
            if (by != 1) {
                if (by != 2) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("<Invalid:");
                    stringBuilder.append(this.mType);
                    stringBuilder.append(">");
                    return stringBuilder.toString();
                }
                return "GpsTime";
            }
            return "LocalHwClock";
        }
        return "Unknown";
    }

    private void initialize() {
        this.mFlags = (short)(false ? 1 : 0);
        this.resetLeapSecond();
        this.setType((byte)0);
        this.setTimeInNs(Long.MIN_VALUE);
        this.resetTimeUncertaintyInNs();
        this.resetFullBiasInNs();
        this.resetBiasInNs();
        this.resetBiasUncertaintyInNs();
        this.resetDriftInNsPerSec();
        this.resetDriftUncertaintyInNsPerSec();
    }

    private boolean isFlagSet(short s) {
        boolean bl = (this.mFlags & s) == s;
        return bl;
    }

    private void resetFlag(short s) {
        this.mFlags = (short)(this.mFlags & s);
    }

    private void setFlag(short s) {
        this.mFlags = (short)(this.mFlags | s);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public double getBiasInNs() {
        return this.mBiasInNs;
    }

    public double getBiasUncertaintyInNs() {
        return this.mBiasUncertaintyInNs;
    }

    public double getDriftInNsPerSec() {
        return this.mDriftInNsPerSec;
    }

    public double getDriftUncertaintyInNsPerSec() {
        return this.mDriftUncertaintyInNsPerSec;
    }

    public long getFullBiasInNs() {
        return this.mFullBiasInNs;
    }

    public short getLeapSecond() {
        return this.mLeapSecond;
    }

    public long getTimeInNs() {
        return this.mTimeInNs;
    }

    public double getTimeUncertaintyInNs() {
        return this.mTimeUncertaintyInNs;
    }

    public byte getType() {
        return this.mType;
    }

    public boolean hasBiasInNs() {
        return this.isFlagSet((short)8);
    }

    public boolean hasBiasUncertaintyInNs() {
        return this.isFlagSet((short)16);
    }

    public boolean hasDriftInNsPerSec() {
        return this.isFlagSet((short)32);
    }

    public boolean hasDriftUncertaintyInNsPerSec() {
        return this.isFlagSet((short)64);
    }

    public boolean hasFullBiasInNs() {
        return this.isFlagSet((short)4);
    }

    public boolean hasLeapSecond() {
        return this.isFlagSet((short)1);
    }

    public boolean hasTimeUncertaintyInNs() {
        return this.isFlagSet((short)2);
    }

    public void reset() {
        this.initialize();
    }

    public void resetBiasInNs() {
        this.resetFlag((short)8);
        this.mBiasInNs = Double.NaN;
    }

    public void resetBiasUncertaintyInNs() {
        this.resetFlag((short)16);
        this.mBiasUncertaintyInNs = Double.NaN;
    }

    public void resetDriftInNsPerSec() {
        this.resetFlag((short)32);
        this.mDriftInNsPerSec = Double.NaN;
    }

    public void resetDriftUncertaintyInNsPerSec() {
        this.resetFlag((short)64);
        this.mDriftUncertaintyInNsPerSec = Double.NaN;
    }

    public void resetFullBiasInNs() {
        this.resetFlag((short)4);
        this.mFullBiasInNs = Long.MIN_VALUE;
    }

    public void resetLeapSecond() {
        this.resetFlag((short)1);
        this.mLeapSecond = (short)-32768;
    }

    public void resetTimeUncertaintyInNs() {
        this.resetFlag((short)2);
        this.mTimeUncertaintyInNs = Double.NaN;
    }

    public void set(GpsClock gpsClock) {
        this.mFlags = gpsClock.mFlags;
        this.mLeapSecond = gpsClock.mLeapSecond;
        this.mType = gpsClock.mType;
        this.mTimeInNs = gpsClock.mTimeInNs;
        this.mTimeUncertaintyInNs = gpsClock.mTimeUncertaintyInNs;
        this.mFullBiasInNs = gpsClock.mFullBiasInNs;
        this.mBiasInNs = gpsClock.mBiasInNs;
        this.mBiasUncertaintyInNs = gpsClock.mBiasUncertaintyInNs;
        this.mDriftInNsPerSec = gpsClock.mDriftInNsPerSec;
        this.mDriftUncertaintyInNsPerSec = gpsClock.mDriftUncertaintyInNsPerSec;
    }

    public void setBiasInNs(double d) {
        this.setFlag((short)8);
        this.mBiasInNs = d;
    }

    public void setBiasUncertaintyInNs(double d) {
        this.setFlag((short)16);
        this.mBiasUncertaintyInNs = d;
    }

    public void setDriftInNsPerSec(double d) {
        this.setFlag((short)32);
        this.mDriftInNsPerSec = d;
    }

    public void setDriftUncertaintyInNsPerSec(double d) {
        this.setFlag((short)64);
        this.mDriftUncertaintyInNsPerSec = d;
    }

    public void setFullBiasInNs(long l) {
        this.setFlag((short)4);
        this.mFullBiasInNs = l;
    }

    public void setLeapSecond(short s) {
        this.setFlag((short)1);
        this.mLeapSecond = s;
    }

    public void setTimeInNs(long l) {
        this.mTimeInNs = l;
    }

    public void setTimeUncertaintyInNs(double d) {
        this.setFlag((short)2);
        this.mTimeUncertaintyInNs = d;
    }

    public void setType(byte by) {
        this.mType = by;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("GpsClock:\n");
        stringBuilder.append(String.format("   %-15s = %s\n", "Type", this.getTypeString()));
        boolean bl = this.hasLeapSecond();
        Object var3_3 = null;
        Number number = bl ? Short.valueOf(this.mLeapSecond) : null;
        stringBuilder.append(String.format("   %-15s = %s\n", "LeapSecond", number));
        long l = this.mTimeInNs;
        number = this.hasTimeUncertaintyInNs() ? Double.valueOf(this.mTimeUncertaintyInNs) : null;
        stringBuilder.append(String.format("   %-15s = %-25s   %-26s = %s\n", "TimeInNs", l, "TimeUncertaintyInNs", number));
        number = this.hasFullBiasInNs() ? Long.valueOf(this.mFullBiasInNs) : null;
        stringBuilder.append(String.format("   %-15s = %s\n", "FullBiasInNs", number));
        number = this.hasBiasInNs() ? Double.valueOf(this.mBiasInNs) : null;
        Double d = this.hasBiasUncertaintyInNs() ? Double.valueOf(this.mBiasUncertaintyInNs) : null;
        stringBuilder.append(String.format("   %-15s = %-25s   %-26s = %s\n", "BiasInNs", number, "BiasUncertaintyInNs", d));
        number = this.hasDriftInNsPerSec() ? Double.valueOf(this.mDriftInNsPerSec) : null;
        d = var3_3;
        if (this.hasDriftUncertaintyInNsPerSec()) {
            d = this.mDriftUncertaintyInNsPerSec;
        }
        stringBuilder.append(String.format("   %-15s = %-25s   %-26s = %s\n", "DriftInNsPerSec", number, "DriftUncertaintyInNsPerSec", d));
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mFlags);
        parcel.writeInt(this.mLeapSecond);
        parcel.writeByte(this.mType);
        parcel.writeLong(this.mTimeInNs);
        parcel.writeDouble(this.mTimeUncertaintyInNs);
        parcel.writeLong(this.mFullBiasInNs);
        parcel.writeDouble(this.mBiasInNs);
        parcel.writeDouble(this.mBiasUncertaintyInNs);
        parcel.writeDouble(this.mDriftInNsPerSec);
        parcel.writeDouble(this.mDriftUncertaintyInNsPerSec);
    }

}

