/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.protobuf.nano;

import com.android.internal.telephony.protobuf.nano.FieldData;

public final class FieldArray
implements Cloneable {
    private static final FieldData DELETED = new FieldData();
    private FieldData[] mData;
    private int[] mFieldNumbers;
    private boolean mGarbage = false;
    private int mSize;

    FieldArray() {
        this(10);
    }

    FieldArray(int n) {
        n = this.idealIntArraySize(n);
        this.mFieldNumbers = new int[n];
        this.mData = new FieldData[n];
        this.mSize = 0;
    }

    private boolean arrayEquals(int[] arrn, int[] arrn2, int n) {
        for (int i = 0; i < n; ++i) {
            if (arrn[i] == arrn2[i]) continue;
            return false;
        }
        return true;
    }

    private boolean arrayEquals(FieldData[] arrfieldData, FieldData[] arrfieldData2, int n) {
        for (int i = 0; i < n; ++i) {
            if (arrfieldData[i].equals(arrfieldData2[i])) continue;
            return false;
        }
        return true;
    }

    private int binarySearch(int n) {
        int n2 = 0;
        int n3 = this.mSize - 1;
        while (n2 <= n3) {
            int n4 = n2 + n3 >>> 1;
            int n5 = this.mFieldNumbers[n4];
            if (n5 < n) {
                n2 = n4 + 1;
                continue;
            }
            if (n5 > n) {
                n3 = n4 - 1;
                continue;
            }
            return n4;
        }
        return n2;
    }

    private void gc() {
        int n = this.mSize;
        int n2 = 0;
        int[] arrn = this.mFieldNumbers;
        FieldData[] arrfieldData = this.mData;
        for (int i = 0; i < n; ++i) {
            FieldData fieldData = arrfieldData[i];
            int n3 = n2;
            if (fieldData != DELETED) {
                if (i != n2) {
                    arrn[n2] = arrn[i];
                    arrfieldData[n2] = fieldData;
                    arrfieldData[i] = null;
                }
                n3 = n2 + 1;
            }
            n2 = n3;
        }
        this.mGarbage = false;
        this.mSize = n2;
    }

    private int idealByteArraySize(int n) {
        for (int i = 4; i < 32; ++i) {
            if (n > (1 << i) - 12) continue;
            return (1 << i) - 12;
        }
        return n;
    }

    private int idealIntArraySize(int n) {
        return this.idealByteArraySize(n * 4) / 4;
    }

    public final FieldArray clone() {
        int n = this.size();
        FieldArray fieldArray = new FieldArray(n);
        System.arraycopy(this.mFieldNumbers, 0, fieldArray.mFieldNumbers, 0, n);
        for (int i = 0; i < n; ++i) {
            FieldData[] arrfieldData = this.mData;
            if (arrfieldData[i] == null) continue;
            fieldArray.mData[i] = arrfieldData[i].clone();
        }
        fieldArray.mSize = n;
        return fieldArray;
    }

    FieldData dataAt(int n) {
        if (this.mGarbage) {
            this.gc();
        }
        return this.mData[n];
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof FieldArray)) {
            return false;
        }
        object = (FieldArray)object;
        if (this.size() != ((FieldArray)object).size()) {
            return false;
        }
        if (!this.arrayEquals(this.mFieldNumbers, ((FieldArray)object).mFieldNumbers, this.mSize) || !this.arrayEquals(this.mData, ((FieldArray)object).mData, this.mSize)) {
            bl = false;
        }
        return bl;
    }

    FieldData get(int n) {
        FieldData[] arrfieldData;
        if ((n = this.binarySearch(n)) >= 0 && (arrfieldData = this.mData)[n] != DELETED) {
            return arrfieldData[n];
        }
        return null;
    }

    public int hashCode() {
        if (this.mGarbage) {
            this.gc();
        }
        int n = 17;
        for (int i = 0; i < this.mSize; ++i) {
            n = (n * 31 + this.mFieldNumbers[i]) * 31 + this.mData[i].hashCode();
        }
        return n;
    }

    public boolean isEmpty() {
        boolean bl = this.size() == 0;
        return bl;
    }

    void put(int n, FieldData fieldData) {
        int n2 = this.binarySearch(n);
        if (n2 >= 0) {
            this.mData[n2] = fieldData;
        } else {
            Object[] arrobject;
            int n3 = n2;
            if (n3 < this.mSize && (arrobject = this.mData)[n3] == DELETED) {
                this.mFieldNumbers[n3] = n;
                arrobject[n3] = fieldData;
                return;
            }
            n2 = n3;
            if (this.mGarbage) {
                n2 = n3;
                if (this.mSize >= this.mFieldNumbers.length) {
                    this.gc();
                    n2 = this.binarySearch(n);
                }
            }
            if ((n3 = this.mSize) >= this.mFieldNumbers.length) {
                n3 = this.idealIntArraySize(n3 + 1);
                int[] arrn = new int[n3];
                arrobject = new FieldData[n3];
                Object[] arrobject2 = this.mFieldNumbers;
                System.arraycopy(arrobject2, 0, arrn, 0, arrobject2.length);
                arrobject2 = this.mData;
                System.arraycopy(arrobject2, 0, arrobject, 0, arrobject2.length);
                this.mFieldNumbers = arrn;
                this.mData = arrobject;
            }
            if ((n3 = this.mSize) - n2 != 0) {
                arrobject = this.mFieldNumbers;
                System.arraycopy(arrobject, n2, arrobject, n2 + 1, n3 - n2);
                arrobject = this.mData;
                System.arraycopy(arrobject, n2, arrobject, n2 + 1, this.mSize - n2);
            }
            this.mFieldNumbers[n2] = n;
            this.mData[n2] = fieldData;
            ++this.mSize;
        }
    }

    void remove(int n) {
        FieldData[] arrfieldData;
        FieldData fieldData;
        FieldData fieldData2;
        if ((n = this.binarySearch(n)) >= 0 && (fieldData2 = (arrfieldData = this.mData)[n]) != (fieldData = DELETED)) {
            arrfieldData[n] = fieldData;
            this.mGarbage = true;
        }
    }

    int size() {
        if (this.mGarbage) {
            this.gc();
        }
        return this.mSize;
    }
}

