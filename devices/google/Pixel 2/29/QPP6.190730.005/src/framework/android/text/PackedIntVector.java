/*
 * Decompiled with CFR 0.145.
 */
package android.text;

import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;

@VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
public class PackedIntVector {
    private final int mColumns;
    private int mRowGapLength;
    private int mRowGapStart;
    private int mRows;
    private int[] mValueGap;
    private int[] mValues;

    public PackedIntVector(int n) {
        this.mColumns = n;
        this.mRows = 0;
        this.mRowGapStart = 0;
        this.mRowGapLength = this.mRows;
        this.mValues = null;
        this.mValueGap = new int[n * 2];
    }

    private final void growBuffer() {
        int n = this.mColumns;
        int[] arrn = ArrayUtils.newUnpaddedIntArray(GrowingArrayUtils.growSize(this.size()) * n);
        int n2 = arrn.length / n;
        int[] arrn2 = this.mValueGap;
        int n3 = this.mRowGapStart;
        int n4 = this.mRows - (this.mRowGapLength + n3);
        int[] arrn3 = this.mValues;
        if (arrn3 != null) {
            System.arraycopy(arrn3, 0, arrn, 0, n * n3);
            System.arraycopy(this.mValues, (this.mRows - n4) * n, arrn, (n2 - n4) * n, n4 * n);
        }
        for (n4 = 0; n4 < n; ++n4) {
            if (arrn2[n4] < n3) continue;
            arrn2[n4] = arrn2[n4] + (n2 - this.mRows);
            if (arrn2[n4] >= n3) continue;
            arrn2[n4] = n3;
        }
        this.mRowGapLength += n2 - this.mRows;
        this.mRows = n2;
        this.mValues = arrn;
    }

    private final void moveRowGapTo(int n) {
        int n2 = this.mRowGapStart;
        if (n == n2) {
            return;
        }
        if (n > n2) {
            int n3;
            int n4 = this.mRowGapLength;
            int n5 = this.mColumns;
            int[] arrn = this.mValueGap;
            int[] arrn2 = this.mValues;
            for (int i = n3 = n2 + n4; i < n3 + (n + n4 - (n2 + n4)); ++i) {
                int n6 = i - n3 + this.mRowGapStart;
                for (int j = 0; j < n5; ++j) {
                    int n7;
                    int n8 = n7 = arrn2[i * n5 + j];
                    if (i >= arrn[j]) {
                        n8 = n7 + arrn[j + n5];
                    }
                    n7 = n8;
                    if (n6 >= arrn[j]) {
                        n7 = n8 - arrn[j + n5];
                    }
                    arrn2[n6 * n5 + j] = n7;
                }
            }
        } else {
            int n9 = n2 - n;
            int n10 = this.mColumns;
            int[] arrn = this.mValueGap;
            int[] arrn3 = this.mValues;
            int n11 = this.mRowGapLength;
            for (int i = n + n9 - 1; i >= n; --i) {
                int n12 = i - n + (n2 + n11) - n9;
                for (int j = 0; j < n10; ++j) {
                    int n13;
                    int n14 = n13 = arrn3[i * n10 + j];
                    if (i >= arrn[j]) {
                        n14 = n13 + arrn[j + n10];
                    }
                    n13 = n14;
                    if (n12 >= arrn[j]) {
                        n13 = n14 - arrn[j + n10];
                    }
                    arrn3[n12 * n10 + j] = n13;
                }
            }
        }
        this.mRowGapStart = n;
    }

    private final void moveValueGapTo(int n, int n2) {
        int[] arrn = this.mValueGap;
        int[] arrn2 = this.mValues;
        int n3 = this.mColumns;
        if (n2 == arrn[n]) {
            return;
        }
        if (n2 > arrn[n]) {
            for (int i = arrn[n]; i < n2; ++i) {
                int n4 = i * n3 + n;
                arrn2[n4] = arrn2[n4] + arrn[n + n3];
            }
        } else {
            for (int i = n2; i < arrn[n]; ++i) {
                int n5 = i * n3 + n;
                arrn2[n5] = arrn2[n5] - arrn[n + n3];
            }
        }
        arrn[n] = n2;
    }

    private void setValueInternal(int n, int n2, int n3) {
        int n4 = n;
        if (n >= this.mRowGapStart) {
            n4 = n + this.mRowGapLength;
        }
        int[] arrn = this.mValueGap;
        n = n3;
        if (n4 >= arrn[n2]) {
            n = n3 - arrn[this.mColumns + n2];
        }
        this.mValues[this.mColumns * n4 + n2] = n;
    }

    public void adjustValuesBelow(int n, int n2, int n3) {
        if ((n | n2) >= 0 && n <= this.size() && n2 < this.width()) {
            int n4 = n;
            if (n >= this.mRowGapStart) {
                n4 = n + this.mRowGapLength;
            }
            this.moveValueGapTo(n2, n4);
            int[] arrn = this.mValueGap;
            n = this.mColumns + n2;
            arrn[n] = arrn[n] + n3;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n);
        stringBuilder.append(", ");
        stringBuilder.append(n2);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public void deleteAt(int n, int n2) {
        if ((n | n2) >= 0 && n + n2 <= this.size()) {
            this.moveRowGapTo(n + n2);
            this.mRowGapStart -= n2;
            this.mRowGapLength += n2;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n);
        stringBuilder.append(", ");
        stringBuilder.append(n2);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public int getValue(int n, int n2) {
        int n3 = this.mColumns;
        if ((n | n2) >= 0 && n < this.size() && n2 < n3) {
            int n4 = n;
            if (n >= this.mRowGapStart) {
                n4 = n + this.mRowGapLength;
            }
            int n5 = this.mValues[n4 * n3 + n2];
            int[] arrn = this.mValueGap;
            n = n5;
            if (n4 >= arrn[n2]) {
                n = n5 + arrn[n2 + n3];
            }
            return n;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n);
        stringBuilder.append(", ");
        stringBuilder.append(n2);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public void insertAt(int n, int[] object) {
        if (n >= 0 && n <= this.size()) {
            if (object != null && ((Object)object).length < this.width()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("value count ");
                stringBuilder.append(((Object)object).length);
                throw new IndexOutOfBoundsException(stringBuilder.toString());
            }
            this.moveRowGapTo(n);
            if (this.mRowGapLength == 0) {
                this.growBuffer();
            }
            ++this.mRowGapStart;
            --this.mRowGapLength;
            if (object == null) {
                for (int i = this.mColumns - 1; i >= 0; --i) {
                    this.setValueInternal(n, i, 0);
                }
            } else {
                for (int i = this.mColumns - 1; i >= 0; --i) {
                    this.setValueInternal(n, i, (int)object[i]);
                }
            }
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("row ");
        ((StringBuilder)object).append(n);
        throw new IndexOutOfBoundsException(((StringBuilder)object).toString());
    }

    public void setValue(int n, int n2, int n3) {
        if ((n | n2) >= 0 && n < this.size() && n2 < this.mColumns) {
            int n4 = n;
            if (n >= this.mRowGapStart) {
                n4 = n + this.mRowGapLength;
            }
            int[] arrn = this.mValueGap;
            n = n3;
            if (n4 >= arrn[n2]) {
                n = n3 - arrn[this.mColumns + n2];
            }
            this.mValues[this.mColumns * n4 + n2] = n;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n);
        stringBuilder.append(", ");
        stringBuilder.append(n2);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public int size() {
        return this.mRows - this.mRowGapLength;
    }

    public int width() {
        return this.mColumns;
    }
}

