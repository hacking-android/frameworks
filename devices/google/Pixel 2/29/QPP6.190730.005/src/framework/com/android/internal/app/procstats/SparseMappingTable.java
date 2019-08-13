/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.EmptyArray
 */
package com.android.internal.app.procstats;

import android.os.Build;
import android.os.Parcel;
import android.util.EventLog;
import android.util.Slog;
import com.android.internal.util.GrowingArrayUtils;
import java.io.Serializable;
import java.util.ArrayList;
import libcore.util.EmptyArray;

public class SparseMappingTable {
    private static final int ARRAY_MASK = 255;
    private static final int ARRAY_SHIFT = 8;
    public static final int ARRAY_SIZE = 4096;
    private static final int ID_MASK = 255;
    private static final int ID_SHIFT = 0;
    private static final int INDEX_MASK = 65535;
    private static final int INDEX_SHIFT = 16;
    public static final int INVALID_KEY = -1;
    private static final String TAG = "SparseMappingTable";
    private final ArrayList<long[]> mLongs = new ArrayList();
    private int mNextIndex;
    private int mSequence;

    public SparseMappingTable() {
        this.mLongs.add(new long[4096]);
    }

    static /* synthetic */ int access$212(SparseMappingTable sparseMappingTable, int n) {
        sparseMappingTable.mNextIndex = n = sparseMappingTable.mNextIndex + n;
        return n;
    }

    public static int getArrayFromKey(int n) {
        return n >> 8 & 255;
    }

    public static byte getIdFromKey(int n) {
        return (byte)(n >> 0 & 255);
    }

    public static int getIndexFromKey(int n) {
        return n >> 16 & 65535;
    }

    private static void logOrThrow(String string2) {
        SparseMappingTable.logOrThrow(string2, new RuntimeException("Stack trace"));
    }

    private static void logOrThrow(String string2, Throwable throwable) {
        Slog.e(TAG, string2, throwable);
        if (!Build.IS_ENG) {
            return;
        }
        throw new RuntimeException(string2, throwable);
    }

    private static void readCompactedLongArray(Parcel object, long[] arrl, int n) {
        int n2;
        int n3 = arrl.length;
        if (n > n3) {
            object = new StringBuilder();
            ((StringBuilder)object).append("bad array lengths: got ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" array is ");
            ((StringBuilder)object).append(n3);
            SparseMappingTable.logOrThrow(((StringBuilder)object).toString());
            return;
        }
        int n4 = 0;
        do {
            if (n4 >= n) break;
            n2 = ((Parcel)object).readInt();
            if (n2 >= 0) {
                arrl[n4] = n2;
            } else {
                int n5 = ((Parcel)object).readInt();
                arrl[n4] = (long)n2 << 32 | (long)n5;
            }
            ++n4;
        } while (true);
        for (n2 = n4; n2 < n3; ++n2) {
            arrl[n2] = 0L;
        }
    }

    private static void writeCompactedLongArray(Parcel parcel, long[] arrl, int n) {
        for (int i = 0; i < n; ++i) {
            long l;
            long l2 = l = arrl[i];
            if (l < 0L) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Time val negative: ");
                stringBuilder.append(l);
                Slog.w(TAG, stringBuilder.toString());
                l2 = 0L;
            }
            if (l2 <= Integer.MAX_VALUE) {
                parcel.writeInt((int)l2);
                continue;
            }
            int n2 = (int)(Integer.MAX_VALUE & l2 >> 32);
            int n3 = (int)(0xFFFFFFFFL & l2);
            parcel.writeInt(n2);
            parcel.writeInt(n3);
        }
    }

    public String dumpInternalState(boolean bl) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SparseMappingTable{");
        stringBuilder.append("mSequence=");
        stringBuilder.append(this.mSequence);
        stringBuilder.append(" mNextIndex=");
        stringBuilder.append(this.mNextIndex);
        stringBuilder.append(" mLongs.size=");
        int n = this.mLongs.size();
        stringBuilder.append(n);
        stringBuilder.append("\n");
        if (bl) {
            for (int i = 0; i < n; ++i) {
                long[] arrl = this.mLongs.get(i);
                for (int j = 0; j < arrl.length && (i != n - 1 || j != this.mNextIndex); ++j) {
                    stringBuilder.append(String.format(" %4d %d 0x%016x %-19d\n", i, j, arrl[j], arrl[j]));
                }
            }
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public void readFromParcel(Parcel object) {
        this.mSequence = ((Parcel)object).readInt();
        this.mNextIndex = ((Parcel)object).readInt();
        this.mLongs.clear();
        int n = ((Parcel)object).readInt();
        for (int i = 0; i < n; ++i) {
            int n2 = ((Parcel)object).readInt();
            long[] arrl = new long[n2];
            SparseMappingTable.readCompactedLongArray((Parcel)object, arrl, n2);
            this.mLongs.add(arrl);
        }
        if (n > 0 && this.mLongs.get(n - 1).length != this.mNextIndex) {
            EventLog.writeEvent(1397638484, "73252178", -1, "");
            object = new StringBuilder();
            ((StringBuilder)object).append("Expected array of length ");
            ((StringBuilder)object).append(this.mNextIndex);
            ((StringBuilder)object).append(" but was ");
            ((StringBuilder)object).append(this.mLongs.get(n - 1).length);
            throw new IllegalStateException(((StringBuilder)object).toString());
        }
    }

    public void reset() {
        this.mLongs.clear();
        this.mLongs.add(new long[4096]);
        this.mNextIndex = 0;
        ++this.mSequence;
    }

    public void writeToParcel(Parcel parcel) {
        long[] arrl;
        parcel.writeInt(this.mSequence);
        parcel.writeInt(this.mNextIndex);
        int n = this.mLongs.size();
        parcel.writeInt(n);
        for (int i = 0; i < n - 1; ++i) {
            arrl = this.mLongs.get(i);
            parcel.writeInt(arrl.length);
            SparseMappingTable.writeCompactedLongArray(parcel, arrl, arrl.length);
        }
        arrl = this.mLongs.get(n - 1);
        parcel.writeInt(this.mNextIndex);
        SparseMappingTable.writeCompactedLongArray(parcel, arrl, this.mNextIndex);
    }

    public static class Table {
        private SparseMappingTable mParent;
        private int mSequence = 1;
        private int mSize;
        private int[] mTable;

        public Table(SparseMappingTable sparseMappingTable) {
            this.mParent = sparseMappingTable;
            this.mSequence = sparseMappingTable.mSequence;
        }

        private void assertConsistency() {
        }

        private int binarySearch(byte by) {
            int n = 0;
            int n2 = this.mSize - 1;
            while (n <= n2) {
                int n3 = n + n2 >>> 1;
                byte by2 = (byte)(this.mTable[n3] >> 0 & 255);
                if (by2 < by) {
                    n = n3 + 1;
                    continue;
                }
                if (by2 > by) {
                    n2 = n3 - 1;
                    continue;
                }
                return n3;
            }
            return n;
        }

        private boolean validateKeys(boolean bl) {
            Serializable serializable = this.mParent.mLongs;
            int n = ((ArrayList)serializable).size();
            int n2 = this.mSize;
            for (int i = 0; i < n2; ++i) {
                int n3 = this.mTable[i];
                int n4 = SparseMappingTable.getArrayFromKey(n3);
                n3 = SparseMappingTable.getIndexFromKey(n3);
                if (n4 < n && n3 < ((long[])((ArrayList)serializable).get(n4)).length) {
                    continue;
                }
                if (bl) {
                    serializable = new StringBuilder();
                    ((StringBuilder)serializable).append("Invalid stats at index ");
                    ((StringBuilder)serializable).append(i);
                    ((StringBuilder)serializable).append(" -- ");
                    ((StringBuilder)serializable).append(this.dumpInternalState());
                    Slog.w(SparseMappingTable.TAG, ((StringBuilder)serializable).toString());
                }
                return false;
            }
            return true;
        }

        public void copyFrom(Table table, int n) {
            this.mTable = null;
            this.mSize = 0;
            int n2 = table.getKeyCount();
            for (int i = 0; i < n2; ++i) {
                int n3 = table.getKeyAt(i);
                long[] arrl = (long[])table.mParent.mLongs.get(SparseMappingTable.getArrayFromKey(n3));
                int n4 = this.getOrAddKey(SparseMappingTable.getIdFromKey(n3), n);
                long[] arrl2 = (long[])this.mParent.mLongs.get(SparseMappingTable.getArrayFromKey(n4));
                System.arraycopy(arrl, SparseMappingTable.getIndexFromKey(n3), arrl2, SparseMappingTable.getIndexFromKey(n4), n);
            }
        }

        public String dumpInternalState() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SparseMappingTable.Table{mSequence=");
            stringBuilder.append(this.mSequence);
            stringBuilder.append(" mParent.mSequence=");
            stringBuilder.append(this.mParent.mSequence);
            stringBuilder.append(" mParent.mLongs.size()=");
            stringBuilder.append(this.mParent.mLongs.size());
            stringBuilder.append(" mSize=");
            stringBuilder.append(this.mSize);
            stringBuilder.append(" mTable=");
            int[] arrn = this.mTable;
            if (arrn == null) {
                stringBuilder.append("null");
            } else {
                int n = arrn.length;
                stringBuilder.append('[');
                for (int i = 0; i < n; ++i) {
                    int n2 = this.mTable[i];
                    stringBuilder.append("0x");
                    stringBuilder.append(Integer.toHexString(n2 >> 0 & 255));
                    stringBuilder.append("/0x");
                    stringBuilder.append(Integer.toHexString(n2 >> 8 & 255));
                    stringBuilder.append("/0x");
                    stringBuilder.append(Integer.toHexString(n2 >> 16 & 65535));
                    if (i == n - 1) continue;
                    stringBuilder.append(", ");
                }
                stringBuilder.append(']');
            }
            stringBuilder.append(" clazz=");
            stringBuilder.append(this.getClass().getName());
            stringBuilder.append('}');
            return stringBuilder.toString();
        }

        public long[] getArrayForKey(int n) {
            this.assertConsistency();
            return (long[])this.mParent.mLongs.get(SparseMappingTable.getArrayFromKey(n));
        }

        public int getKey(byte by) {
            this.assertConsistency();
            int n = this.binarySearch(by);
            if (n >= 0) {
                return this.mTable[n];
            }
            return -1;
        }

        public int getKeyAt(int n) {
            return this.mTable[n];
        }

        public int getKeyCount() {
            return this.mSize;
        }

        public int getOrAddKey(byte by, int n) {
            this.assertConsistency();
            int n2 = this.binarySearch(by);
            if (n2 >= 0) {
                return this.mTable[n2];
            }
            int[] arrn = this.mParent.mLongs;
            int n3 = arrn.size() - 1;
            long[] arrl = (long[])arrn.get(n3);
            int n4 = n3;
            if (this.mParent.mNextIndex + n > arrl.length) {
                arrn.add(new long[4096]);
                n4 = n3 + 1;
                this.mParent.mNextIndex = 0;
            }
            n4 = n4 << 8 | this.mParent.mNextIndex << 16 | by << 0;
            SparseMappingTable.access$212(this.mParent, n);
            arrn = this.mTable;
            if (arrn == null) {
                arrn = EmptyArray.INT;
            }
            this.mTable = GrowingArrayUtils.insert(arrn, this.mSize, n2, n4);
            ++this.mSize;
            return n4;
        }

        public long getValue(int n) {
            return this.getValue(n, 0);
        }

        public long getValue(int n, int n2) {
            this.assertConsistency();
            try {
                long l = ((long[])this.mParent.mLongs.get(SparseMappingTable.getArrayFromKey(n)))[SparseMappingTable.getIndexFromKey(n) + n2];
                return l;
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("key=0x");
                stringBuilder.append(Integer.toHexString(n));
                stringBuilder.append(" index=");
                stringBuilder.append(n2);
                stringBuilder.append(" -- ");
                stringBuilder.append(this.dumpInternalState());
                SparseMappingTable.logOrThrow(stringBuilder.toString(), indexOutOfBoundsException);
                return 0L;
            }
        }

        public long getValueForId(byte by) {
            return this.getValueForId(by, 0);
        }

        public long getValueForId(byte by, int n) {
            this.assertConsistency();
            int n2 = this.binarySearch(by);
            if (n2 >= 0) {
                int n3 = this.mTable[n2];
                try {
                    long l = ((long[])this.mParent.mLongs.get(SparseMappingTable.getArrayFromKey(n3)))[SparseMappingTable.getIndexFromKey(n3) + n];
                    return l;
                }
                catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("id=0x");
                    stringBuilder.append(Integer.toHexString(by));
                    stringBuilder.append(" idx=");
                    stringBuilder.append(n2);
                    stringBuilder.append(" key=0x");
                    stringBuilder.append(Integer.toHexString(n3));
                    stringBuilder.append(" index=");
                    stringBuilder.append(n);
                    stringBuilder.append(" -- ");
                    stringBuilder.append(this.dumpInternalState());
                    SparseMappingTable.logOrThrow(stringBuilder.toString(), indexOutOfBoundsException);
                    return 0L;
                }
            }
            return 0L;
        }

        public boolean readFromParcel(Parcel parcel) {
            this.mSequence = parcel.readInt();
            this.mSize = parcel.readInt();
            int n = this.mSize;
            if (n != 0) {
                this.mTable = new int[n];
                for (n = 0; n < this.mSize; ++n) {
                    this.mTable[n] = parcel.readInt();
                }
            } else {
                this.mTable = null;
            }
            if (this.validateKeys(true)) {
                return true;
            }
            this.mSize = 0;
            this.mTable = null;
            return false;
        }

        public void resetTable() {
            this.mTable = null;
            this.mSize = 0;
            this.mSequence = this.mParent.mSequence;
        }

        public void setValue(int n, int n2, long l) {
            this.assertConsistency();
            if (l < 0L) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("can't store negative values key=0x");
                stringBuilder.append(Integer.toHexString(n));
                stringBuilder.append(" index=");
                stringBuilder.append(n2);
                stringBuilder.append(" value=");
                stringBuilder.append(l);
                stringBuilder.append(" -- ");
                stringBuilder.append(this.dumpInternalState());
                SparseMappingTable.logOrThrow(stringBuilder.toString());
                return;
            }
            try {
                ((long[])SparseMappingTable.access$100((SparseMappingTable)this.mParent).get((int)SparseMappingTable.getArrayFromKey((int)n)))[SparseMappingTable.getIndexFromKey((int)n) + n2] = l;
                return;
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("key=0x");
                stringBuilder.append(Integer.toHexString(n));
                stringBuilder.append(" index=");
                stringBuilder.append(n2);
                stringBuilder.append(" value=");
                stringBuilder.append(l);
                stringBuilder.append(" -- ");
                stringBuilder.append(this.dumpInternalState());
                SparseMappingTable.logOrThrow(stringBuilder.toString(), indexOutOfBoundsException);
                return;
            }
        }

        public void setValue(int n, long l) {
            this.setValue(n, 0, l);
        }

        public void writeToParcel(Parcel parcel) {
            parcel.writeInt(this.mSequence);
            parcel.writeInt(this.mSize);
            for (int i = 0; i < this.mSize; ++i) {
                parcel.writeInt(this.mTable[i]);
            }
        }
    }

}

