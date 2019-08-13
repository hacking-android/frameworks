/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.IntTrie;
import android.icu.impl.IntTrieBuilder;
import android.icu.impl.PVecToTrieCompactHandler;
import android.icu.impl.Trie;
import android.icu.impl.TrieBuilder;
import java.util.Arrays;
import java.util.Comparator;

public class PropsVectors {
    public static final int ERROR_VALUE_CP = 1114113;
    public static final int FIRST_SPECIAL_CP = 1114112;
    public static final int INITIAL_ROWS = 4096;
    public static final int INITIAL_VALUE_CP = 1114112;
    public static final int MAX_CP = 1114113;
    public static final int MAX_ROWS = 1114114;
    public static final int MEDIUM_ROWS = 65536;
    private int columns;
    private boolean isCompacted;
    private int maxRows;
    private int prevRow;
    private int rows;
    private int[] v;

    public PropsVectors(int n) {
        if (n >= 1) {
            this.columns = n + 2;
            this.v = new int[this.columns * 4096];
            this.maxRows = 4096;
            this.rows = 3;
            this.prevRow = 0;
            this.isCompacted = false;
            int[] arrn = this.v;
            arrn[0] = 0;
            arrn[1] = 1114112;
            int n2 = this.columns;
            for (n = 1114112; n <= 1114113; ++n) {
                arrn = this.v;
                arrn[n2] = n;
                arrn[n2 + 1] = n + 1;
                n2 += this.columns;
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("numOfColumns need to be no less than 1; but it is ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private boolean areElementsSame(int n, int[] arrn, int n2, int n3) {
        for (int i = 0; i < n3; ++i) {
            if (this.v[n + i] == arrn[n2 + i]) continue;
            return false;
        }
        return true;
    }

    private int findRow(int n) {
        int[] arrn = this.v;
        int n2 = this.prevRow;
        int n3 = this.columns;
        int n4 = n2 * n3;
        if (n >= arrn[n4]) {
            if (n < arrn[n4 + 1]) {
                return n4;
            }
            if (n < arrn[(n4 += n3) + 1]) {
                this.prevRow = n2 + 1;
                return n4;
            }
            if (n < arrn[(n3 = n4 + n3) + 1]) {
                this.prevRow = n2 + 2;
                return n3;
            }
            if (n - arrn[n3 + 1] < 10) {
                this.prevRow = n2 + 2;
                do {
                    ++this.prevRow;
                    n3 = n2 = n3 + this.columns;
                } while (n >= this.v[n2 + 1]);
                return n2;
            }
        } else if (n < arrn[1]) {
            this.prevRow = 0;
            return 0;
        }
        n2 = 0;
        n3 = this.rows;
        while (n2 < n3 - 1) {
            arrn = this.v;
            n4 = (n2 + n3) / 2;
            int n5 = this.columns * n4;
            if (n < arrn[n5]) {
                n3 = n4;
                continue;
            }
            if (n < arrn[n5 + 1]) {
                this.prevRow = n4;
                return n5;
            }
            n2 = n4;
        }
        this.prevRow = n2;
        return this.columns * n2;
    }

    public void compact(CompactHandler compactHandler) {
        int n;
        int n2;
        int n3;
        if (this.isCompacted) {
            return;
        }
        this.isCompacted = true;
        int n4 = this.columns - 2;
        Integer[] arrinteger = new Integer[this.rows];
        for (n2 = 0; n2 < this.rows; ++n2) {
            arrinteger[n2] = this.columns * n2;
        }
        Arrays.sort(arrinteger, new Comparator<Integer>(){

            @Override
            public int compare(Integer n, Integer n2) {
                int n3 = n;
                int n4 = n2;
                int n5 = PropsVectors.this.columns;
                int n6 = 2;
                do {
                    int n7;
                    if (PropsVectors.this.v[n3 + n6] != PropsVectors.this.v[n4 + n6]) {
                        n6 = PropsVectors.this.v[n3 + n6] < PropsVectors.this.v[n4 + n6] ? -1 : 1;
                        return n6;
                    }
                    n6 = n7 = n6 + 1;
                    if (n7 != PropsVectors.this.columns) continue;
                    n6 = 0;
                } while (--n5 > 0);
                return 0;
            }
        });
        int n5 = -n4;
        for (n2 = 0; n2 < this.rows; ++n2) {
            block15 : {
                block14 : {
                    n3 = this.v[arrinteger[n2]];
                    if (n5 < 0) break block14;
                    n = n5;
                    if (this.areElementsSame(arrinteger[n2] + 2, this.v, arrinteger[n2 - 1] + 2, n4)) break block15;
                }
                n = n5 + n4;
            }
            if (n3 == 1114112) {
                compactHandler.setRowIndexForInitialValue(n);
            } else if (n3 == 1114113) {
                compactHandler.setRowIndexForErrorValue(n);
            }
            n5 = n;
        }
        n2 = n5 + n4;
        compactHandler.startRealValues(n2);
        int[] arrn = new int[n2];
        n5 = -n4;
        for (n2 = 0; n2 < this.rows; ++n2) {
            int n6;
            block17 : {
                block16 : {
                    n6 = this.v[arrinteger[n2]];
                    n3 = this.v[arrinteger[n2] + 1];
                    if (n5 < 0) break block16;
                    n = n5;
                    if (this.areElementsSame(arrinteger[n2] + 2, arrn, n5, n4)) break block17;
                }
                n = n5 + n4;
                System.arraycopy(this.v, arrinteger[n2] + 2, arrn, n, n4);
            }
            if (n6 < 1114112) {
                compactHandler.setRowIndexForRange(n6, n3 - 1, n);
            }
            n5 = n;
        }
        this.v = arrn;
        this.rows = n5 / n4 + 1;
    }

    public IntTrie compactToTrieWithRowIndexes() {
        PVecToTrieCompactHandler pVecToTrieCompactHandler = new PVecToTrieCompactHandler();
        this.compact(pVecToTrieCompactHandler);
        return pVecToTrieCompactHandler.builder.serialize(new DefaultGetFoldedValue(pVecToTrieCompactHandler.builder), new DefaultGetFoldingOffset());
    }

    public int[] getCompactedArray() {
        if (this.isCompacted) {
            return this.v;
        }
        throw new IllegalStateException("Illegal Invocation of the method before compact()");
    }

    public int getCompactedColumns() {
        if (this.isCompacted) {
            return this.columns - 2;
        }
        throw new IllegalStateException("Illegal Invocation of the method before compact()");
    }

    public int getCompactedRows() {
        if (this.isCompacted) {
            return this.rows;
        }
        throw new IllegalStateException("Illegal Invocation of the method before compact()");
    }

    public int[] getRow(int n) {
        if (!this.isCompacted) {
            if (n >= 0 && n <= this.rows) {
                int n2 = this.columns;
                int[] arrn = new int[n2 - 2];
                System.arraycopy(this.v, n * n2 + 2, arrn, 0, n2 - 2);
                return arrn;
            }
            throw new IllegalArgumentException("rowIndex out of bound!");
        }
        throw new IllegalStateException("Illegal Invocation of the method after compact()");
    }

    public int getRowEnd(int n) {
        if (!this.isCompacted) {
            if (n >= 0 && n <= this.rows) {
                return this.v[this.columns * n + 1] - 1;
            }
            throw new IllegalArgumentException("rowIndex out of bound!");
        }
        throw new IllegalStateException("Illegal Invocation of the method after compact()");
    }

    public int getRowStart(int n) {
        if (!this.isCompacted) {
            if (n >= 0 && n <= this.rows) {
                return this.v[this.columns * n];
            }
            throw new IllegalArgumentException("rowIndex out of bound!");
        }
        throw new IllegalStateException("Illegal Invocation of the method after compact()");
    }

    public int getValue(int n, int n2) {
        if (!this.isCompacted && n >= 0 && n <= 1114113 && n2 >= 0 && n2 < this.columns - 2) {
            n = this.findRow(n);
            return this.v[n + 2 + n2];
        }
        return 0;
    }

    public void setValue(int n, int n2, int n3, int n4, int n5) {
        block15 : {
            block16 : {
                int n6;
                int n7;
                int n8;
                int[] arrn;
                int n9;
                block18 : {
                    boolean bl;
                    int n10;
                    int n11;
                    boolean bl2;
                    block17 : {
                        if (n < 0 || n > n2 || n2 > 1114113 || n3 < 0 || n3 >= this.columns - 2) break block15;
                        if (this.isCompacted) break block16;
                        n11 = n2 + 1;
                        n7 = n3 + 2;
                        n6 = n4 & n5;
                        n4 = this.findRow(n);
                        n10 = this.findRow(n2);
                        arrn = this.v;
                        n2 = arrn[n4];
                        bl2 = true;
                        bl = n != n2 && n6 != (arrn[n4 + n7] & n5);
                        arrn = this.v;
                        if (n11 == arrn[n10 + 1] || n6 == (arrn[n10 + n7] & n5)) {
                            bl2 = false;
                        }
                        if (bl) break block17;
                        n8 = n4;
                        n9 = n10;
                        if (!bl2) break block18;
                    }
                    n2 = 0;
                    if (bl) {
                        n2 = 0 + 1;
                    }
                    n3 = n2;
                    if (bl2) {
                        n3 = n2 + 1;
                    }
                    if ((n9 = this.rows) + n3 > (n2 = this.maxRows)) {
                        if (n2 < 65536) {
                            n2 = 65536;
                        } else {
                            if (n2 >= 1114114) {
                                throw new IndexOutOfBoundsException("MAX_ROWS exceeded! Increase it to a higher valuein the implementation");
                            }
                            n2 = 1114114;
                        }
                        n9 = this.columns;
                        arrn = new int[n2 * n9];
                        System.arraycopy(this.v, 0, arrn, 0, this.rows * n9);
                        this.v = arrn;
                        this.maxRows = n2;
                    }
                    n9 = this.rows;
                    n2 = this.columns;
                    if ((n9 = n9 * n2 - (n10 + n2)) > 0) {
                        arrn = this.v;
                        System.arraycopy(arrn, n10 + n2, arrn, n10 + (n3 + 1) * n2, n9);
                    }
                    this.rows += n3;
                    n3 = n4;
                    n2 = n10;
                    if (bl) {
                        n2 = this.columns;
                        arrn = this.v;
                        System.arraycopy(arrn, n4, arrn, n2 + n4, n10 - n4 + n2);
                        n3 = this.columns;
                        n2 = n10 + n3;
                        arrn = this.v;
                        arrn[n4 + n3] = n;
                        arrn[n4 + 1] = n;
                        n3 = n4 + n3;
                    }
                    n8 = n3;
                    n9 = n2;
                    if (bl2) {
                        arrn = this.v;
                        n = this.columns;
                        System.arraycopy(arrn, n2, arrn, n2 + n, n);
                        arrn = this.v;
                        arrn[this.columns + n2] = n11;
                        arrn[n2 + 1] = n11;
                        n9 = n2;
                        n8 = n3;
                    }
                }
                this.prevRow = n9 / this.columns;
                n = n8 + n7;
                do {
                    arrn = this.v;
                    arrn[n] = arrn[n] & n5 | n6;
                    if (n == n9 + n7) {
                        return;
                    }
                    n += this.columns;
                } while (true);
            }
            throw new IllegalStateException("Shouldn't be called aftercompact()!");
        }
        throw new IllegalArgumentException();
    }

    public static interface CompactHandler {
        public void setRowIndexForErrorValue(int var1);

        public void setRowIndexForInitialValue(int var1);

        public void setRowIndexForRange(int var1, int var2, int var3);

        public void startRealValues(int var1);
    }

    private static class DefaultGetFoldedValue
    implements TrieBuilder.DataManipulate {
        private IntTrieBuilder builder;

        public DefaultGetFoldedValue(IntTrieBuilder intTrieBuilder) {
            this.builder = intTrieBuilder;
        }

        @Override
        public int getFoldedValue(int n, int n2) {
            int n3 = this.builder.m_initialValue_;
            int n4 = n;
            while (n4 < n + 1024) {
                boolean[] arrbl = new boolean[1];
                int n5 = this.builder.getValue(n4, arrbl);
                if (arrbl[0]) {
                    n4 += 32;
                    continue;
                }
                if (n5 != n3) {
                    return n2;
                }
                ++n4;
            }
            return 0;
        }
    }

    private static class DefaultGetFoldingOffset
    implements Trie.DataManipulate {
        private DefaultGetFoldingOffset() {
        }

        @Override
        public int getFoldingOffset(int n) {
            return n;
        }
    }

}

