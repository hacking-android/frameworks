/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.EmptyArray
 */
package android.text;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;
import java.io.PrintStream;
import libcore.util.EmptyArray;

class PackedObjectVector<E> {
    private int mColumns;
    private int mRowGapLength;
    private int mRowGapStart;
    private int mRows;
    private Object[] mValues;

    public PackedObjectVector(int n) {
        this.mColumns = n;
        this.mValues = EmptyArray.OBJECT;
        this.mRows = 0;
        this.mRowGapStart = 0;
        this.mRowGapLength = this.mRows;
    }

    private void growBuffer() {
        Object[] arrobject = ArrayUtils.newUnpaddedObjectArray(GrowingArrayUtils.growSize(this.size()) * this.mColumns);
        int n = arrobject.length;
        int n2 = this.mColumns;
        n /= n2;
        int n3 = this.mRows;
        int n4 = this.mRowGapStart;
        n3 -= this.mRowGapLength + n4;
        System.arraycopy(this.mValues, 0, arrobject, 0, n2 * n4);
        Object[] arrobject2 = this.mValues;
        n2 = this.mRows;
        n4 = this.mColumns;
        System.arraycopy(arrobject2, (n2 - n3) * n4, arrobject, (n - n3) * n4, n4 * n3);
        this.mRowGapLength += n - this.mRows;
        this.mRows = n;
        this.mValues = arrobject;
    }

    private void moveRowGapTo(int n) {
        int n2 = this.mRowGapStart;
        if (n == n2) {
            return;
        }
        if (n > n2) {
            int n3;
            int n4;
            int n5 = this.mRowGapLength;
            for (int i = n2 + n5; i < (n4 = this.mRowGapStart) + (n3 = this.mRowGapLength) + (n + n5 - (n2 + n5)); ++i) {
                int n6;
                for (int j = 0; j < (n6 = this.mColumns); ++j) {
                    Object[] arrobject = this.mValues;
                    arrobject[n6 * (i - (n3 + n4) + n4) + j] = arrobject[i * n6 + j];
                }
            }
        } else {
            int n7 = n2 - n;
            for (int i = n + n7 - 1; i >= n; --i) {
                int n8;
                n2 = this.mRowGapStart;
                int n9 = this.mRowGapLength;
                for (int j = 0; j < (n8 = this.mColumns); ++j) {
                    Object[] arrobject = this.mValues;
                    arrobject[n8 * (i - n + n2 + n9 - n7) + j] = arrobject[i * n8 + j];
                }
            }
        }
        this.mRowGapStart = n;
    }

    public void deleteAt(int n, int n2) {
        this.moveRowGapTo(n + n2);
        this.mRowGapStart -= n2;
        this.mRowGapLength += n2;
        n = this.mRowGapLength;
        this.size();
    }

    public void dump() {
        for (int i = 0; i < this.mRows; ++i) {
            int n;
            for (int j = 0; j < (n = this.mColumns); ++j) {
                Appendable appendable;
                Appendable appendable2;
                Object object = this.mValues[n * i + j];
                n = this.mRowGapStart;
                if (i >= n && i < n + this.mRowGapLength) {
                    appendable = System.out;
                    appendable2 = new StringBuilder();
                    ((StringBuilder)appendable2).append("(");
                    ((StringBuilder)appendable2).append(object);
                    ((StringBuilder)appendable2).append(") ");
                    ((PrintStream)appendable).print(((StringBuilder)appendable2).toString());
                    continue;
                }
                appendable2 = System.out;
                appendable = new StringBuilder();
                ((StringBuilder)appendable).append(object);
                ((StringBuilder)appendable).append(" ");
                ((PrintStream)appendable2).print(((StringBuilder)appendable).toString());
            }
            System.out.print(" << \n");
        }
        System.out.print("-----\n\n");
    }

    public E getValue(int n, int n2) {
        int n3 = n;
        if (n >= this.mRowGapStart) {
            n3 = n + this.mRowGapLength;
        }
        return (E)this.mValues[this.mColumns * n3 + n2];
    }

    public void insertAt(int n, E[] arrE) {
        this.moveRowGapTo(n);
        if (this.mRowGapLength == 0) {
            this.growBuffer();
        }
        ++this.mRowGapStart;
        --this.mRowGapLength;
        if (arrE == null) {
            for (int i = 0; i < this.mColumns; ++i) {
                this.setValue(n, i, null);
            }
        } else {
            for (int i = 0; i < this.mColumns; ++i) {
                this.setValue(n, i, arrE[i]);
            }
        }
    }

    public void setValue(int n, int n2, E e) {
        int n3 = n;
        if (n >= this.mRowGapStart) {
            n3 = n + this.mRowGapLength;
        }
        this.mValues[this.mColumns * n3 + n2] = e;
    }

    public int size() {
        return this.mRows - this.mRowGapLength;
    }

    public int width() {
        return this.mColumns;
    }
}

