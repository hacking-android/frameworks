/*
 * Decompiled with CFR 0.145.
 */
package android.os.health;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.health.HealthKeys;
import android.os.health.TimerStat;
import android.util.ArrayMap;

public class HealthStatsWriter {
    private final HealthKeys.Constants mConstants;
    private final boolean[] mMeasurementFields;
    private final long[] mMeasurementValues;
    private final ArrayMap<String, Long>[] mMeasurementsValues;
    private final ArrayMap<String, HealthStatsWriter>[] mStatsValues;
    private final int[] mTimerCounts;
    private final boolean[] mTimerFields;
    private final long[] mTimerTimes;
    private final ArrayMap<String, TimerStat>[] mTimersValues;

    public HealthStatsWriter(HealthKeys.Constants constants) {
        this.mConstants = constants;
        int n = constants.getSize(0);
        this.mTimerFields = new boolean[n];
        this.mTimerCounts = new int[n];
        this.mTimerTimes = new long[n];
        n = constants.getSize(1);
        this.mMeasurementFields = new boolean[n];
        this.mMeasurementValues = new long[n];
        this.mStatsValues = new ArrayMap[constants.getSize(2)];
        this.mTimersValues = new ArrayMap[constants.getSize(3)];
        this.mMeasurementsValues = new ArrayMap[constants.getSize(4)];
    }

    private static int countBooleanArray(boolean[] arrbl) {
        int n = 0;
        int n2 = arrbl.length;
        for (int i = 0; i < n2; ++i) {
            int n3 = n;
            if (arrbl[i]) {
                n3 = n + 1;
            }
            n = n3;
        }
        return n;
    }

    private static <T> int countObjectArray(T[] arrT) {
        int n = 0;
        int n2 = arrT.length;
        for (int i = 0; i < n2; ++i) {
            int n3 = n;
            if (arrT[i] != null) {
                n3 = n + 1;
            }
            n = n3;
        }
        return n;
    }

    private static void writeHealthStatsWriterMap(Parcel parcel, ArrayMap<String, HealthStatsWriter> arrayMap) {
        int n = arrayMap.size();
        parcel.writeInt(n);
        for (int i = 0; i < n; ++i) {
            parcel.writeString(arrayMap.keyAt(i));
            arrayMap.valueAt(i).flattenToParcel(parcel);
        }
    }

    private static void writeLongsMap(Parcel parcel, ArrayMap<String, Long> arrayMap) {
        int n = arrayMap.size();
        parcel.writeInt(n);
        for (int i = 0; i < n; ++i) {
            parcel.writeString(arrayMap.keyAt(i));
            parcel.writeLong(arrayMap.valueAt(i));
        }
    }

    private static <T extends Parcelable> void writeParcelableMap(Parcel parcel, ArrayMap<String, T> arrayMap) {
        int n = arrayMap.size();
        parcel.writeInt(n);
        for (int i = 0; i < n; ++i) {
            parcel.writeString(arrayMap.keyAt(i));
            ((Parcelable)arrayMap.valueAt(i)).writeToParcel(parcel, 0);
        }
    }

    public void addMeasurement(int n, long l) {
        n = this.mConstants.getIndex(1, n);
        this.mMeasurementFields[n] = true;
        this.mMeasurementValues[n] = l;
    }

    public void addMeasurements(int n, String string2, long l) {
        ArrayMap<String, Long> arrayMap;
        n = this.mConstants.getIndex(4, n);
        ArrayMap<String, Long>[] arrarrayMap = this.mMeasurementsValues;
        ArrayMap<String, Long> arrayMap2 = arrayMap = arrarrayMap[n];
        if (arrayMap == null) {
            arrarrayMap[n] = arrayMap2 = new ArrayMap(1);
        }
        arrayMap2.put(string2, l);
    }

    public void addStats(int n, String string2, HealthStatsWriter healthStatsWriter) {
        ArrayMap<String, HealthStatsWriter> arrayMap;
        n = this.mConstants.getIndex(2, n);
        ArrayMap<String, HealthStatsWriter>[] arrarrayMap = this.mStatsValues;
        ArrayMap<String, HealthStatsWriter> arrayMap2 = arrayMap = arrarrayMap[n];
        if (arrayMap == null) {
            arrarrayMap[n] = arrayMap2 = new ArrayMap(1);
        }
        arrayMap2.put(string2, healthStatsWriter);
    }

    public void addTimer(int n, int n2, long l) {
        n = this.mConstants.getIndex(0, n);
        this.mTimerFields[n] = true;
        this.mTimerCounts[n] = n2;
        this.mTimerTimes[n] = l;
    }

    public void addTimers(int n, String string2, TimerStat timerStat) {
        ArrayMap<String, TimerStat> arrayMap;
        n = this.mConstants.getIndex(3, n);
        ArrayMap<String, TimerStat>[] arrarrayMap = this.mTimersValues;
        ArrayMap<String, TimerStat> arrayMap2 = arrayMap = arrarrayMap[n];
        if (arrayMap == null) {
            arrarrayMap[n] = arrayMap2 = new ArrayMap(1);
        }
        arrayMap2.put(string2, timerStat);
    }

    public void flattenToParcel(Parcel parcel) {
        int n;
        parcel.writeString(this.mConstants.getDataType());
        parcel.writeInt(HealthStatsWriter.countBooleanArray(this.mTimerFields));
        int[] arrn = this.mConstants.getKeys(0);
        for (n = 0; n < arrn.length; ++n) {
            if (!this.mTimerFields[n]) continue;
            parcel.writeInt(arrn[n]);
            parcel.writeInt(this.mTimerCounts[n]);
            parcel.writeLong(this.mTimerTimes[n]);
        }
        parcel.writeInt(HealthStatsWriter.countBooleanArray(this.mMeasurementFields));
        arrn = this.mConstants.getKeys(1);
        for (n = 0; n < arrn.length; ++n) {
            if (!this.mMeasurementFields[n]) continue;
            parcel.writeInt(arrn[n]);
            parcel.writeLong(this.mMeasurementValues[n]);
        }
        parcel.writeInt(HealthStatsWriter.countObjectArray(this.mStatsValues));
        arrn = this.mConstants.getKeys(2);
        for (n = 0; n < arrn.length; ++n) {
            if (this.mStatsValues[n] == null) continue;
            parcel.writeInt(arrn[n]);
            HealthStatsWriter.writeHealthStatsWriterMap(parcel, this.mStatsValues[n]);
        }
        parcel.writeInt(HealthStatsWriter.countObjectArray(this.mTimersValues));
        arrn = this.mConstants.getKeys(3);
        for (n = 0; n < arrn.length; ++n) {
            if (this.mTimersValues[n] == null) continue;
            parcel.writeInt(arrn[n]);
            HealthStatsWriter.writeParcelableMap(parcel, this.mTimersValues[n]);
        }
        parcel.writeInt(HealthStatsWriter.countObjectArray(this.mMeasurementsValues));
        arrn = this.mConstants.getKeys(4);
        for (n = 0; n < arrn.length; ++n) {
            if (this.mMeasurementsValues[n] == null) continue;
            parcel.writeInt(arrn[n]);
            HealthStatsWriter.writeLongsMap(parcel, this.mMeasurementsValues[n]);
        }
    }
}

