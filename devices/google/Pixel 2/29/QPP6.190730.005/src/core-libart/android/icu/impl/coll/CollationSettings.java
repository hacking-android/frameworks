/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.coll;

import android.icu.impl.coll.CollationData;
import android.icu.impl.coll.SharedObject;
import android.icu.impl.coll.UVector32;
import java.util.Arrays;

public final class CollationSettings
extends SharedObject {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int ALTERNATE_MASK = 12;
    public static final int BACKWARD_SECONDARY = 2048;
    public static final int CASE_FIRST = 512;
    public static final int CASE_FIRST_AND_UPPER_MASK = 768;
    public static final int CASE_LEVEL = 1024;
    public static final int CHECK_FCD = 1;
    private static final int[] EMPTY_INT_ARRAY = new int[0];
    static final int MAX_VARIABLE_MASK = 112;
    static final int MAX_VARIABLE_SHIFT = 4;
    static final int MAX_VAR_CURRENCY = 3;
    static final int MAX_VAR_PUNCT = 1;
    static final int MAX_VAR_SPACE = 0;
    static final int MAX_VAR_SYMBOL = 2;
    public static final int NUMERIC = 2;
    static final int SHIFTED = 4;
    static final int STRENGTH_MASK = 61440;
    static final int STRENGTH_SHIFT = 12;
    static final int UPPER_FIRST = 256;
    public int fastLatinOptions = -1;
    public char[] fastLatinPrimaries = new char[384];
    long minHighNoReorder;
    public int options = 8208;
    public int[] reorderCodes = EMPTY_INT_ARRAY;
    long[] reorderRanges;
    public byte[] reorderTable;
    public long variableTop;

    CollationSettings() {
    }

    static int getStrength(int n) {
        return n >> 12;
    }

    static int getTertiaryMask(int n) {
        n = CollationSettings.isTertiaryWithCaseBits(n) ? 65343 : 16191;
        return n;
    }

    static boolean isTertiaryWithCaseBits(int n) {
        boolean bl = (n & 1536) == 512;
        return bl;
    }

    private long reorderEx(long l) {
        long l2;
        if (l >= this.minHighNoReorder) {
            return l;
        }
        int n = 0;
        while ((65535L | l) >= (l2 = this.reorderRanges[n])) {
            ++n;
        }
        return ((long)((short)l2) << 24) + l;
    }

    private static boolean reorderTableHasSplitBytes(byte[] arrby) {
        for (int i = 1; i < 256; ++i) {
            if (arrby[i] != 0) continue;
            return true;
        }
        return false;
    }

    private void setReorderArrays(int[] arrn, int[] arrn2, int n, int n2, byte[] arrby) {
        int[] arrn3 = arrn;
        if (arrn == null) {
            arrn3 = EMPTY_INT_ARRAY;
        }
        this.reorderTable = arrby;
        this.reorderCodes = arrn3;
        this.setReorderRanges(arrn2, n, n2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void setReorderRanges(int[] arrn, int n, int n2) {
        if (n2 == 0) {
            this.reorderRanges = null;
            return;
        }
        this.reorderRanges = new long[n2];
        int n3 = 0;
        do {
            long[] arrl = this.reorderRanges;
            int n4 = n3 + 1;
            arrl[n3] = (long)arrn[n] & 0xFFFFFFFFL;
            if (n4 >= n2) {
                return;
            }
            n3 = n4;
            ++n;
        } while (true);
    }

    static boolean sortsTertiaryUpperCaseFirst(int n) {
        boolean bl = (n & 1792) == 768;
        return bl;
    }

    void aliasReordering(CollationData collationData, int[] arrn, int n, byte[] arrby) {
        int[] arrn2 = n == arrn.length ? arrn : Arrays.copyOf(arrn, n);
        int n2 = arrn.length;
        int n3 = n2 - n;
        if (arrby != null && (n3 == 0 ? !CollationSettings.reorderTableHasSplitBytes(arrby) : n3 >= 2 && (arrn[n] & 65535) == 0 && (arrn[n2 - 1] & 65535) != 0)) {
            this.reorderTable = arrby;
            this.reorderCodes = arrn2;
            while (n < n2 && (arrn[n] & 16711680) == 0) {
                ++n;
            }
            if (n == n2) {
                this.minHighNoReorder = 0L;
                this.reorderRanges = null;
            } else {
                this.minHighNoReorder = (long)arrn[n2 - 1] & 0xFFFF0000L;
                this.setReorderRanges(arrn, n, n2 - n);
            }
            return;
        }
        this.setReordering(collationData, arrn2);
    }

    @Override
    public CollationSettings clone() {
        CollationSettings collationSettings = (CollationSettings)super.clone();
        collationSettings.fastLatinPrimaries = (char[])this.fastLatinPrimaries.clone();
        return collationSettings;
    }

    public void copyReorderingFrom(CollationSettings collationSettings) {
        if (!collationSettings.hasReordering()) {
            this.resetReordering();
            return;
        }
        this.minHighNoReorder = collationSettings.minHighNoReorder;
        this.reorderTable = collationSettings.reorderTable;
        this.reorderRanges = collationSettings.reorderRanges;
        this.reorderCodes = collationSettings.reorderCodes;
    }

    public boolean dontCheckFCD() {
        int n = this.options;
        boolean bl = true;
        if ((n & 1) != 0) {
            bl = false;
        }
        return bl;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (!this.getClass().equals(object.getClass())) {
            return false;
        }
        object = (CollationSettings)object;
        int n = this.options;
        if (n != ((CollationSettings)object).options) {
            return false;
        }
        if ((n & 12) != 0 && this.variableTop != ((CollationSettings)object).variableTop) {
            return false;
        }
        return Arrays.equals(this.reorderCodes, ((CollationSettings)object).reorderCodes);
    }

    public boolean getAlternateHandling() {
        boolean bl = (this.options & 12) != 0;
        return bl;
    }

    public int getCaseFirst() {
        return this.options & 768;
    }

    public boolean getFlag(int n) {
        boolean bl = (this.options & n) != 0;
        return bl;
    }

    public int getMaxVariable() {
        return (this.options & 112) >> 4;
    }

    public int getStrength() {
        return CollationSettings.getStrength(this.options);
    }

    boolean hasBackwardSecondary() {
        boolean bl = (this.options & 2048) != 0;
        return bl;
    }

    public boolean hasReordering() {
        boolean bl = this.reorderTable != null;
        return bl;
    }

    public int hashCode() {
        int[] arrn;
        int n;
        int n2 = this.options;
        int n3 = n = n2 << 8;
        if ((n2 & 12) != 0) {
            n3 = (int)((long)n ^ this.variableTop);
        }
        n = this.reorderCodes.length ^ n3;
        for (n3 = 0; n3 < (arrn = this.reorderCodes).length; ++n3) {
            n ^= arrn[n3] << n3;
        }
        return n;
    }

    public boolean isNumeric() {
        boolean bl = (this.options & 2) != 0;
        return bl;
    }

    public long reorder(long l) {
        byte by = this.reorderTable[(int)l >>> 24];
        if (by == 0 && l > 1L) {
            return this.reorderEx(l);
        }
        return ((long)by & 255L) << 24 | 0xFFFFFFL & l;
    }

    public void resetReordering() {
        this.reorderTable = null;
        this.minHighNoReorder = 0L;
        this.reorderRanges = null;
        this.reorderCodes = EMPTY_INT_ARRAY;
    }

    public void setAlternateHandlingDefault(int n) {
        this.options = n & 12 | this.options & -13;
    }

    public void setAlternateHandlingShifted(boolean bl) {
        int n = this.options & -13;
        this.options = bl ? n | 4 : n;
    }

    public void setCaseFirst(int n) {
        this.options = this.options & -769 | n;
    }

    public void setCaseFirstDefault(int n) {
        this.options = n & 768 | this.options & -769;
    }

    public void setFlag(int n, boolean bl) {
        this.options = bl ? (this.options |= n) : (this.options &= n);
    }

    public void setFlagDefault(int n, int n2) {
        this.options = this.options & n | n2 & n;
    }

    public void setMaxVariable(int n, int n2) {
        int n3 = this.options & -113;
        if (n != -1) {
            if (n != 0 && n != 1 && n != 2 && n != 3) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("illegal maxVariable value ");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            this.options = n << 4 | n3;
        } else {
            this.options = n2 & 112 | n3;
        }
    }

    public void setReordering(CollationData arrby, int[] arrn) {
        if (arrn.length != 0) {
            int n = arrn.length;
            int n2 = 0;
            if (n != 1 || arrn[0] != 103) {
                int[] arrn2 = new UVector32();
                arrby.makeReorderRanges(arrn, (UVector32)arrn2);
                int n3 = arrn2.size();
                if (n3 == 0) {
                    this.resetReordering();
                    return;
                }
                arrn2 = arrn2.getBuffer();
                this.minHighNoReorder = (long)arrn2[n3 - 1] & 0xFFFF0000L;
                arrby = new byte[256];
                n = 0;
                int n4 = -1;
                for (int i = 0; i < n3; ++i) {
                    int n5 = arrn2[i];
                    int n6 = n5 >>> 24;
                    while (n < n6) {
                        arrby[n] = (byte)(n + n5);
                        ++n;
                    }
                    int n7 = n4;
                    if ((16711680 & n5) != 0) {
                        arrby[n6] = (byte)(false ? 1 : 0);
                        n = ++n6;
                        n7 = n4;
                        if (n4 < 0) {
                            n7 = i;
                            n = n6;
                        }
                    }
                    n4 = n7;
                }
                while (n <= 255) {
                    arrby[n] = (byte)n;
                    ++n;
                }
                if (n4 < 0) {
                    n4 = 0;
                    n = n2;
                } else {
                    n = n4;
                    n4 = n3 - n4;
                }
                this.setReorderArrays(arrn, arrn2, n, n4, arrby);
                return;
            }
        }
        this.resetReordering();
    }

    public void setStrength(int n) {
        int n2 = this.options;
        if (n != 0 && n != 1 && n != 2 && n != 3 && n != 15) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("illegal strength value ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.options = n << 12 | n2 & -61441;
    }

    public void setStrengthDefault(int n) {
        this.options = 61440 & n | this.options & -61441;
    }
}

