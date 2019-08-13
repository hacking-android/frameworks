/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.os.Parcel;
import android.os.Parcelable;

public class FusedBatchOptions
implements Parcelable {
    public static final Parcelable.Creator<FusedBatchOptions> CREATOR = new Parcelable.Creator<FusedBatchOptions>(){

        @Override
        public FusedBatchOptions createFromParcel(Parcel parcel) {
            FusedBatchOptions fusedBatchOptions = new FusedBatchOptions();
            fusedBatchOptions.setMaxPowerAllocationInMW(parcel.readDouble());
            fusedBatchOptions.setPeriodInNS(parcel.readLong());
            fusedBatchOptions.setSourceToUse(parcel.readInt());
            fusedBatchOptions.setFlag(parcel.readInt());
            fusedBatchOptions.setSmallestDisplacementMeters(parcel.readFloat());
            return fusedBatchOptions;
        }

        public FusedBatchOptions[] newArray(int n) {
            return new FusedBatchOptions[n];
        }
    };
    private volatile int mFlags = 0;
    private volatile double mMaxPowerAllocationInMW = 0.0;
    private volatile long mPeriodInNS = 0L;
    private volatile float mSmallestDisplacementMeters = 0.0f;
    private volatile int mSourcesToUse = 0;

    @Override
    public int describeContents() {
        return 0;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public double getMaxPowerAllocationInMW() {
        return this.mMaxPowerAllocationInMW;
    }

    public long getPeriodInNS() {
        return this.mPeriodInNS;
    }

    public float getSmallestDisplacementMeters() {
        return this.mSmallestDisplacementMeters;
    }

    public int getSourcesToUse() {
        return this.mSourcesToUse;
    }

    public boolean isFlagSet(int n) {
        boolean bl = (this.mFlags & n) != 0;
        return bl;
    }

    public boolean isSourceToUseSet(int n) {
        boolean bl = (this.mSourcesToUse & n) != 0;
        return bl;
    }

    public void resetFlag(int n) {
        this.mFlags &= n;
    }

    public void resetSourceToUse(int n) {
        this.mSourcesToUse &= n;
    }

    public void setFlag(int n) {
        this.mFlags |= n;
    }

    public void setMaxPowerAllocationInMW(double d) {
        this.mMaxPowerAllocationInMW = d;
    }

    public void setPeriodInNS(long l) {
        this.mPeriodInNS = l;
    }

    public void setSmallestDisplacementMeters(float f) {
        this.mSmallestDisplacementMeters = f;
    }

    public void setSourceToUse(int n) {
        this.mSourcesToUse |= n;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeDouble(this.mMaxPowerAllocationInMW);
        parcel.writeLong(this.mPeriodInNS);
        parcel.writeInt(this.mSourcesToUse);
        parcel.writeInt(this.mFlags);
        parcel.writeFloat(this.mSmallestDisplacementMeters);
    }

    public static final class BatchFlags {
        public static int CALLBACK_ON_LOCATION_FIX;
        public static int WAKEUP_ON_FIFO_FULL;

        static {
            WAKEUP_ON_FIFO_FULL = 1;
            CALLBACK_ON_LOCATION_FIX = 2;
        }
    }

    public static final class SourceTechnologies {
        public static int BLUETOOTH;
        public static int CELL;
        public static int GNSS;
        public static int SENSORS;
        public static int WIFI;

        static {
            GNSS = 1;
            WIFI = 2;
            SENSORS = 4;
            CELL = 8;
            BLUETOOTH = 16;
        }
    }

}

