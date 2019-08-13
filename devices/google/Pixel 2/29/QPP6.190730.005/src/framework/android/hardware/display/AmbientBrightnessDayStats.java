/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.display;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.time.LocalDate;
import java.util.Arrays;

@SystemApi
public final class AmbientBrightnessDayStats
implements Parcelable {
    public static final Parcelable.Creator<AmbientBrightnessDayStats> CREATOR = new Parcelable.Creator<AmbientBrightnessDayStats>(){

        @Override
        public AmbientBrightnessDayStats createFromParcel(Parcel parcel) {
            return new AmbientBrightnessDayStats(parcel);
        }

        public AmbientBrightnessDayStats[] newArray(int n) {
            return new AmbientBrightnessDayStats[n];
        }
    };
    private final float[] mBucketBoundaries;
    private final LocalDate mLocalDate;
    private final float[] mStats;

    private AmbientBrightnessDayStats(Parcel parcel) {
        this.mLocalDate = LocalDate.parse(parcel.readString());
        this.mBucketBoundaries = parcel.createFloatArray();
        this.mStats = parcel.createFloatArray();
    }

    public AmbientBrightnessDayStats(LocalDate localDate, float[] arrf) {
        this(localDate, arrf, null);
    }

    public AmbientBrightnessDayStats(LocalDate localDate, float[] arrf, float[] arrf2) {
        block2 : {
            block5 : {
                block4 : {
                    block3 : {
                        Preconditions.checkNotNull(localDate);
                        Preconditions.checkNotNull(arrf);
                        Preconditions.checkArrayElementsInRange(arrf, 0.0f, Float.MAX_VALUE, "bucketBoundaries");
                        if (arrf.length < 1) break block2;
                        AmbientBrightnessDayStats.checkSorted(arrf);
                        if (arrf2 != null) break block3;
                        arrf2 = new float[arrf.length];
                        break block4;
                    }
                    Preconditions.checkArrayElementsInRange(arrf2, 0.0f, Float.MAX_VALUE, "stats");
                    if (arrf.length != arrf2.length) break block5;
                }
                this.mLocalDate = localDate;
                this.mBucketBoundaries = arrf;
                this.mStats = arrf2;
                return;
            }
            throw new IllegalArgumentException("Bucket boundaries and stats must be of same size.");
        }
        throw new IllegalArgumentException("Bucket boundaries must contain at least 1 value");
    }

    private static void checkSorted(float[] arrf) {
        if (arrf.length <= 1) {
            return;
        }
        float f = arrf[0];
        for (int i = 1; i < arrf.length; ++i) {
            boolean bl = f < arrf[i];
            Preconditions.checkState(bl);
            f = arrf[i];
        }
    }

    private int getBucketIndex(float f) {
        float[] arrf = this.mBucketBoundaries;
        if (f < arrf[0]) {
            return -1;
        }
        int n = 0;
        int n2 = arrf.length - 1;
        while (n < n2) {
            int n3;
            arrf = this.mBucketBoundaries;
            int n4 = (n + n2) / 2;
            if (arrf[n4] <= f && f < arrf[n4 + 1]) {
                return n4;
            }
            arrf = this.mBucketBoundaries;
            if (arrf[n4] < f) {
                n3 = n4 + 1;
            } else {
                n3 = n;
                if (arrf[n4] > f) {
                    n2 = n4 - 1;
                    n3 = n;
                }
            }
            n = n3;
        }
        return n;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (AmbientBrightnessDayStats)object;
        if (!(this.mLocalDate.equals(((AmbientBrightnessDayStats)object).mLocalDate) && Arrays.equals(this.mBucketBoundaries, ((AmbientBrightnessDayStats)object).mBucketBoundaries) && Arrays.equals(this.mStats, ((AmbientBrightnessDayStats)object).mStats))) {
            bl = false;
        }
        return bl;
    }

    public float[] getBucketBoundaries() {
        return this.mBucketBoundaries;
    }

    public LocalDate getLocalDate() {
        return this.mLocalDate;
    }

    public float[] getStats() {
        return this.mStats;
    }

    public int hashCode() {
        return ((1 * 31 + this.mLocalDate.hashCode()) * 31 + Arrays.hashCode(this.mBucketBoundaries)) * 31 + Arrays.hashCode(this.mStats);
    }

    public void log(float f, float f2) {
        int n = this.getBucketIndex(f);
        if (n >= 0) {
            float[] arrf = this.mStats;
            arrf[n] = arrf[n] + f2;
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        for (int i = 0; i < this.mBucketBoundaries.length; ++i) {
            if (i != 0) {
                stringBuilder.append(", ");
                stringBuilder2.append(", ");
            }
            stringBuilder.append(this.mBucketBoundaries[i]);
            stringBuilder2.append(this.mStats[i]);
        }
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append(this.mLocalDate);
        stringBuilder3.append(" ");
        stringBuilder3.append("{");
        stringBuilder3.append(stringBuilder);
        stringBuilder3.append("} ");
        stringBuilder3.append("{");
        stringBuilder3.append(stringBuilder2);
        stringBuilder3.append("}");
        return stringBuilder3.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mLocalDate.toString());
        parcel.writeFloatArray(this.mBucketBoundaries);
        parcel.writeFloatArray(this.mStats);
    }

}

