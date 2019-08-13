/*
 * Decompiled with CFR 0.145.
 */
package android.os.health;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.health.TimerStat;
import android.util.ArrayMap;
import java.util.Arrays;
import java.util.Map;

public class HealthStats {
    private String mDataType;
    private int[] mMeasurementKeys;
    private long[] mMeasurementValues;
    private int[] mMeasurementsKeys;
    private ArrayMap<String, Long>[] mMeasurementsValues;
    private int[] mStatsKeys;
    private ArrayMap<String, HealthStats>[] mStatsValues;
    private int[] mTimerCounts;
    private int[] mTimerKeys;
    private long[] mTimerTimes;
    private int[] mTimersKeys;
    private ArrayMap<String, TimerStat>[] mTimersValues;

    private HealthStats() {
        throw new RuntimeException("unsupported");
    }

    public HealthStats(Parcel parcel) {
        int n;
        this.mDataType = parcel.readString();
        int n2 = parcel.readInt();
        this.mTimerKeys = new int[n2];
        this.mTimerCounts = new int[n2];
        this.mTimerTimes = new long[n2];
        for (n = 0; n < n2; ++n) {
            this.mTimerKeys[n] = parcel.readInt();
            this.mTimerCounts[n] = parcel.readInt();
            this.mTimerTimes[n] = parcel.readLong();
        }
        n2 = parcel.readInt();
        this.mMeasurementKeys = new int[n2];
        this.mMeasurementValues = new long[n2];
        for (n = 0; n < n2; ++n) {
            this.mMeasurementKeys[n] = parcel.readInt();
            this.mMeasurementValues[n] = parcel.readLong();
        }
        n2 = parcel.readInt();
        this.mStatsKeys = new int[n2];
        this.mStatsValues = new ArrayMap[n2];
        for (n = 0; n < n2; ++n) {
            this.mStatsKeys[n] = parcel.readInt();
            this.mStatsValues[n] = HealthStats.createHealthStatsMap(parcel);
        }
        n2 = parcel.readInt();
        this.mTimersKeys = new int[n2];
        this.mTimersValues = new ArrayMap[n2];
        for (n = 0; n < n2; ++n) {
            this.mTimersKeys[n] = parcel.readInt();
            this.mTimersValues[n] = HealthStats.createParcelableMap(parcel, TimerStat.CREATOR);
        }
        n2 = parcel.readInt();
        this.mMeasurementsKeys = new int[n2];
        this.mMeasurementsValues = new ArrayMap[n2];
        for (n = 0; n < n2; ++n) {
            this.mMeasurementsKeys[n] = parcel.readInt();
            this.mMeasurementsValues[n] = HealthStats.createLongsMap(parcel);
        }
    }

    private static ArrayMap<String, HealthStats> createHealthStatsMap(Parcel parcel) {
        int n = parcel.readInt();
        ArrayMap<String, HealthStats> arrayMap = new ArrayMap<String, HealthStats>(n);
        for (int i = 0; i < n; ++i) {
            arrayMap.put(parcel.readString(), new HealthStats(parcel));
        }
        return arrayMap;
    }

    private static ArrayMap<String, Long> createLongsMap(Parcel parcel) {
        int n = parcel.readInt();
        ArrayMap<String, Long> arrayMap = new ArrayMap<String, Long>(n);
        for (int i = 0; i < n; ++i) {
            arrayMap.put(parcel.readString(), parcel.readLong());
        }
        return arrayMap;
    }

    private static <T extends Parcelable> ArrayMap<String, T> createParcelableMap(Parcel parcel, Parcelable.Creator<T> creator) {
        int n = parcel.readInt();
        ArrayMap<String, Parcelable> arrayMap = new ArrayMap<String, Parcelable>(n);
        for (int i = 0; i < n; ++i) {
            arrayMap.put(parcel.readString(), (Parcelable)creator.createFromParcel(parcel));
        }
        return arrayMap;
    }

    private static int getIndex(int[] arrn, int n) {
        return Arrays.binarySearch(arrn, n);
    }

    public String getDataType() {
        return this.mDataType;
    }

    public long getMeasurement(int n) {
        int n2 = HealthStats.getIndex(this.mMeasurementKeys, n);
        if (n2 >= 0) {
            return this.mMeasurementValues[n2];
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad measurement key dataType=");
        stringBuilder.append(this.mDataType);
        stringBuilder.append(" key=");
        stringBuilder.append(n);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public int getMeasurementKeyAt(int n) {
        return this.mMeasurementKeys[n];
    }

    public int getMeasurementKeyCount() {
        return this.mMeasurementKeys.length;
    }

    public Map<String, Long> getMeasurements(int n) {
        int n2 = HealthStats.getIndex(this.mMeasurementsKeys, n);
        if (n2 >= 0) {
            return this.mMeasurementsValues[n2];
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad measurements key dataType=");
        stringBuilder.append(this.mDataType);
        stringBuilder.append(" key=");
        stringBuilder.append(n);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public int getMeasurementsKeyAt(int n) {
        return this.mMeasurementsKeys[n];
    }

    public int getMeasurementsKeyCount() {
        return this.mMeasurementsKeys.length;
    }

    public Map<String, HealthStats> getStats(int n) {
        int n2 = HealthStats.getIndex(this.mStatsKeys, n);
        if (n2 >= 0) {
            return this.mStatsValues[n2];
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad stats key dataType=");
        stringBuilder.append(this.mDataType);
        stringBuilder.append(" key=");
        stringBuilder.append(n);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public int getStatsKeyAt(int n) {
        return this.mStatsKeys[n];
    }

    public int getStatsKeyCount() {
        return this.mStatsKeys.length;
    }

    public TimerStat getTimer(int n) {
        int n2 = HealthStats.getIndex(this.mTimerKeys, n);
        if (n2 >= 0) {
            return new TimerStat(this.mTimerCounts[n2], this.mTimerTimes[n2]);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad timer key dataType=");
        stringBuilder.append(this.mDataType);
        stringBuilder.append(" key=");
        stringBuilder.append(n);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public int getTimerCount(int n) {
        int n2 = HealthStats.getIndex(this.mTimerKeys, n);
        if (n2 >= 0) {
            return this.mTimerCounts[n2];
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad timer key dataType=");
        stringBuilder.append(this.mDataType);
        stringBuilder.append(" key=");
        stringBuilder.append(n);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public int getTimerKeyAt(int n) {
        return this.mTimerKeys[n];
    }

    public int getTimerKeyCount() {
        return this.mTimerKeys.length;
    }

    public long getTimerTime(int n) {
        int n2 = HealthStats.getIndex(this.mTimerKeys, n);
        if (n2 >= 0) {
            return this.mTimerTimes[n2];
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad timer key dataType=");
        stringBuilder.append(this.mDataType);
        stringBuilder.append(" key=");
        stringBuilder.append(n);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public Map<String, TimerStat> getTimers(int n) {
        int n2 = HealthStats.getIndex(this.mTimersKeys, n);
        if (n2 >= 0) {
            return this.mTimersValues[n2];
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad timers key dataType=");
        stringBuilder.append(this.mDataType);
        stringBuilder.append(" key=");
        stringBuilder.append(n);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public int getTimersKeyAt(int n) {
        return this.mTimersKeys[n];
    }

    public int getTimersKeyCount() {
        return this.mTimersKeys.length;
    }

    public boolean hasMeasurement(int n) {
        boolean bl = HealthStats.getIndex(this.mMeasurementKeys, n) >= 0;
        return bl;
    }

    public boolean hasMeasurements(int n) {
        boolean bl = HealthStats.getIndex(this.mMeasurementsKeys, n) >= 0;
        return bl;
    }

    public boolean hasStats(int n) {
        boolean bl = HealthStats.getIndex(this.mStatsKeys, n) >= 0;
        return bl;
    }

    public boolean hasTimer(int n) {
        boolean bl = HealthStats.getIndex(this.mTimerKeys, n) >= 0;
        return bl;
    }

    public boolean hasTimers(int n) {
        boolean bl = HealthStats.getIndex(this.mTimersKeys, n) >= 0;
        return bl;
    }
}

