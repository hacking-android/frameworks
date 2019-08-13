/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;

@SystemApi
public final class TelephonyHistogram
implements Parcelable {
    private static final int ABSENT = 0;
    public static final Parcelable.Creator<TelephonyHistogram> CREATOR = new Parcelable.Creator<TelephonyHistogram>(){

        @Override
        public TelephonyHistogram createFromParcel(Parcel parcel) {
            return new TelephonyHistogram(parcel);
        }

        public TelephonyHistogram[] newArray(int n) {
            return new TelephonyHistogram[n];
        }
    };
    private static final int PRESENT = 1;
    private static final int RANGE_CALCULATION_COUNT = 10;
    public static final int TELEPHONY_CATEGORY_RIL = 1;
    private int mAverageTimeMs;
    private final int mBucketCount;
    private final int[] mBucketCounters;
    private final int[] mBucketEndPoints;
    private final int mCategory;
    private final int mId;
    private int[] mInitialTimings;
    private int mMaxTimeMs;
    private int mMinTimeMs;
    private int mSampleCount;

    public TelephonyHistogram(int n, int n2, int n3) {
        if (n3 > 1) {
            this.mCategory = n;
            this.mId = n2;
            this.mMinTimeMs = Integer.MAX_VALUE;
            this.mMaxTimeMs = 0;
            this.mAverageTimeMs = 0;
            this.mSampleCount = 0;
            this.mInitialTimings = new int[10];
            this.mBucketCount = n3;
            this.mBucketEndPoints = new int[n3 - 1];
            this.mBucketCounters = new int[n3];
            return;
        }
        throw new IllegalArgumentException("Invalid number of buckets");
    }

    public TelephonyHistogram(Parcel parcel) {
        this.mCategory = parcel.readInt();
        this.mId = parcel.readInt();
        this.mMinTimeMs = parcel.readInt();
        this.mMaxTimeMs = parcel.readInt();
        this.mAverageTimeMs = parcel.readInt();
        this.mSampleCount = parcel.readInt();
        if (parcel.readInt() == 1) {
            this.mInitialTimings = new int[10];
            parcel.readIntArray(this.mInitialTimings);
        }
        this.mBucketCount = parcel.readInt();
        this.mBucketEndPoints = new int[this.mBucketCount - 1];
        parcel.readIntArray(this.mBucketEndPoints);
        this.mBucketCounters = new int[this.mBucketCount];
        parcel.readIntArray(this.mBucketCounters);
    }

    public TelephonyHistogram(TelephonyHistogram telephonyHistogram) {
        this.mCategory = telephonyHistogram.getCategory();
        this.mId = telephonyHistogram.getId();
        this.mMinTimeMs = telephonyHistogram.getMinTime();
        this.mMaxTimeMs = telephonyHistogram.getMaxTime();
        this.mAverageTimeMs = telephonyHistogram.getAverageTime();
        this.mSampleCount = telephonyHistogram.getSampleCount();
        this.mInitialTimings = telephonyHistogram.getInitialTimings();
        this.mBucketCount = telephonyHistogram.getBucketCount();
        this.mBucketEndPoints = telephonyHistogram.getBucketEndPoints();
        this.mBucketCounters = telephonyHistogram.getBucketCounters();
    }

    private void addToBucketCounter(int[] arrn, int[] arrn2, int n) {
        int n2;
        for (n2 = 0; n2 < arrn.length; ++n2) {
            if (n > arrn[n2]) continue;
            arrn2[n2] = arrn2[n2] + 1;
            return;
        }
        arrn2[n2] = arrn2[n2] + 1;
    }

    private void calculateBucketEndPoints(int[] arrn) {
        int n;
        for (int i = 1; i < (n = this.mBucketCount); ++i) {
            int n2 = this.mMinTimeMs;
            arrn[i - 1] = n2 + (this.mMaxTimeMs - n2) * i / n;
        }
    }

    private int[] getDeepCopyOfArray(int[] arrn) {
        int[] arrn2 = new int[arrn.length];
        System.arraycopy(arrn, 0, arrn2, 0, arrn.length);
        return arrn2;
    }

    private int[] getInitialTimings() {
        return this.mInitialTimings;
    }

    public void addTimeTaken(int n) {
        int n2 = this.mSampleCount;
        if (n2 != 0 && n2 != Integer.MAX_VALUE) {
            if (n < this.mMinTimeMs) {
                this.mMinTimeMs = n;
            }
            if (n > this.mMaxTimeMs) {
                this.mMaxTimeMs = n;
            }
            long l = this.mAverageTimeMs;
            n2 = this.mSampleCount;
            long l2 = n2;
            long l3 = n;
            this.mSampleCount = ++n2;
            this.mAverageTimeMs = (int)((l * l2 + l3) / (long)n2);
            n2 = this.mSampleCount;
            if (n2 < 10) {
                this.mInitialTimings[n2 - 1] = n;
            } else if (n2 == 10) {
                this.mInitialTimings[n2 - 1] = n;
                this.calculateBucketEndPoints(this.mBucketEndPoints);
                for (n = 0; n < 10; ++n) {
                    this.addToBucketCounter(this.mBucketEndPoints, this.mBucketCounters, this.mInitialTimings[n]);
                }
                this.mInitialTimings = null;
            } else {
                this.addToBucketCounter(this.mBucketEndPoints, this.mBucketCounters, n);
            }
        } else {
            if (this.mSampleCount == 0) {
                this.mMinTimeMs = n;
                this.mMaxTimeMs = n;
                this.mAverageTimeMs = n;
            } else {
                this.mInitialTimings = new int[10];
            }
            this.mSampleCount = 1;
            Arrays.fill(this.mInitialTimings, 0);
            this.mInitialTimings[0] = n;
            Arrays.fill(this.mBucketEndPoints, 0);
            Arrays.fill(this.mBucketCounters, 0);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getAverageTime() {
        return this.mAverageTimeMs;
    }

    public int getBucketCount() {
        return this.mBucketCount;
    }

    public int[] getBucketCounters() {
        int n = this.mSampleCount;
        if (n > 1 && n < 10) {
            n = this.mBucketCount;
            int[] arrn = new int[n - 1];
            int[] arrn2 = new int[n];
            this.calculateBucketEndPoints(arrn);
            for (n = 0; n < this.mSampleCount; ++n) {
                this.addToBucketCounter(arrn, arrn2, this.mInitialTimings[n]);
            }
            return arrn2;
        }
        return this.getDeepCopyOfArray(this.mBucketCounters);
    }

    public int[] getBucketEndPoints() {
        int n = this.mSampleCount;
        if (n > 1 && n < 10) {
            int[] arrn = new int[this.mBucketCount - 1];
            this.calculateBucketEndPoints(arrn);
            return arrn;
        }
        return this.getDeepCopyOfArray(this.mBucketEndPoints);
    }

    public int getCategory() {
        return this.mCategory;
    }

    public int getId() {
        return this.mId;
    }

    public int getMaxTime() {
        return this.mMaxTimeMs;
    }

    public int getMinTime() {
        return this.mMinTimeMs;
    }

    public int getSampleCount() {
        return this.mSampleCount;
    }

    public String toString() {
        StringBuilder stringBuilder;
        int n;
        AbstractStringBuilder abstractStringBuilder = new StringBuilder();
        ((StringBuilder)abstractStringBuilder).append(" Histogram id = ");
        ((StringBuilder)abstractStringBuilder).append(this.mId);
        ((StringBuilder)abstractStringBuilder).append(" Time(ms): min = ");
        ((StringBuilder)abstractStringBuilder).append(this.mMinTimeMs);
        ((StringBuilder)abstractStringBuilder).append(" max = ");
        ((StringBuilder)abstractStringBuilder).append(this.mMaxTimeMs);
        ((StringBuilder)abstractStringBuilder).append(" avg = ");
        ((StringBuilder)abstractStringBuilder).append(this.mAverageTimeMs);
        ((StringBuilder)abstractStringBuilder).append(" Count = ");
        ((StringBuilder)abstractStringBuilder).append(this.mSampleCount);
        String string2 = ((StringBuilder)abstractStringBuilder).toString();
        if (this.mSampleCount < 10) {
            return string2;
        }
        abstractStringBuilder = new StringBuffer(" Interval Endpoints:");
        for (n = 0; n < this.mBucketEndPoints.length; ++n) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(" ");
            stringBuilder.append(this.mBucketEndPoints[n]);
            ((StringBuffer)abstractStringBuilder).append(stringBuilder.toString());
        }
        ((StringBuffer)abstractStringBuilder).append(" Interval counters:");
        for (n = 0; n < this.mBucketCounters.length; ++n) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(" ");
            stringBuilder.append(this.mBucketCounters[n]);
            ((StringBuffer)abstractStringBuilder).append(stringBuilder.toString());
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append((Object)abstractStringBuilder);
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mCategory);
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mMinTimeMs);
        parcel.writeInt(this.mMaxTimeMs);
        parcel.writeInt(this.mAverageTimeMs);
        parcel.writeInt(this.mSampleCount);
        if (this.mInitialTimings == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeIntArray(this.mInitialTimings);
        }
        parcel.writeInt(this.mBucketCount);
        parcel.writeIntArray(this.mBucketEndPoints);
        parcel.writeIntArray(this.mBucketCounters);
    }

}

