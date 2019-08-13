/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.Trie2;
import android.icu.impl.Trie2_16;
import android.icu.impl.Trie2_32;
import java.io.PrintStream;
import java.util.Iterator;

public class Trie2Writable
extends Trie2 {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int UNEWTRIE2_DATA_0800_OFFSET = 2176;
    private static final int UNEWTRIE2_DATA_NULL_OFFSET = 192;
    private static final int UNEWTRIE2_DATA_START_OFFSET = 256;
    private static final int UNEWTRIE2_INDEX_2_NULL_OFFSET = 2656;
    private static final int UNEWTRIE2_INDEX_2_START_OFFSET = 2720;
    private static final int UNEWTRIE2_INITIAL_DATA_LENGTH = 16384;
    private static final int UNEWTRIE2_MEDIUM_DATA_LENGTH = 131072;
    private static final int UTRIE2_MAX_DATA_LENGTH = 262140;
    private static final int UTRIE2_MAX_INDEX_LENGTH = 65535;
    private boolean UTRIE2_DEBUG = false;
    private int[] data;
    private int dataCapacity;
    private int firstFreeBlock;
    private int[] index1 = new int[544];
    private int[] index2 = new int[35488];
    private int index2Length;
    private int index2NullOffset;
    private boolean isCompacted;
    private int[] map = new int[34852];

    public Trie2Writable(int n, int n2) {
        this.init(n, n2);
    }

    public Trie2Writable(Trie2 object) {
        this.init(((Trie2)object).initialValue, ((Trie2)object).errorValue);
        object = ((Trie2)object).iterator();
        while (object.hasNext()) {
            this.setRange((Trie2.Range)object.next(), true);
        }
    }

    private int allocDataBlock(int n) {
        int n2;
        int[] arrn;
        if (this.firstFreeBlock != 0) {
            n2 = this.firstFreeBlock;
            this.firstFreeBlock = -this.map[n2 >> 5];
        } else {
            int n3 = this.dataLength;
            int n4 = n3 + 32;
            n2 = this.dataCapacity;
            if (n4 > n2) {
                if (n2 < 131072) {
                    n2 = 131072;
                } else {
                    if (n2 >= 1115264) {
                        throw new IllegalStateException("Internal error in Trie2 creation.");
                    }
                    n2 = 1115264;
                }
                arrn = new int[n2];
                System.arraycopy(this.data, 0, arrn, 0, this.dataLength);
                this.data = arrn;
                this.dataCapacity = n2;
            }
            this.dataLength = n4;
            n2 = n3;
        }
        arrn = this.data;
        System.arraycopy(arrn, n, arrn, n2, 32);
        this.map[n2 >> 5] = 0;
        return n2;
    }

    private int allocIndex2Block() {
        int n = this.index2Length;
        int n2 = n + 64;
        int[] arrn = this.index2;
        if (n2 <= arrn.length) {
            this.index2Length = n2;
            System.arraycopy(arrn, this.index2NullOffset, arrn, n, 64);
            return n;
        }
        throw new IllegalStateException("Internal error in Trie2 creation.");
    }

    private void compactData() {
        int[] arrn;
        int n = 192;
        int n2 = 0;
        int n3 = 0;
        while (n2 < 192) {
            this.map[n3] = n2;
            n2 += 32;
            ++n3;
        }
        int n4 = 64;
        int n5 = 64 >> 5;
        n2 = 192;
        block1 : while (n2 < this.dataLength) {
            int n6;
            int n7;
            int n8 = n4;
            n3 = n5;
            if (n2 == 2176) {
                n8 = 32;
                n3 = 1;
            }
            if (this.map[n2 >> 5] <= 0) {
                n2 += n8;
                n4 = n8;
                n5 = n3;
                continue;
            }
            n5 = this.findSameDataBlock(n, n2, n8);
            if (n5 >= 0) {
                n7 = n3;
                n6 = n2 >> 5;
                while (n7 > 0) {
                    this.map[n6] = n5;
                    n5 += 32;
                    --n7;
                    ++n6;
                }
                n2 += n8;
                n4 = n8;
                n5 = n3;
                continue;
            }
            for (n6 = n8 - 4; n6 > 0 && !this.equal_int(this.data, n - n6, n2, n6); n6 -= 4) {
            }
            if (n6 <= 0 && n >= n2) {
                n6 = n3;
                n = n2 >> 5;
                while (n6 > 0) {
                    this.map[n] = n2;
                    n2 += 32;
                    --n6;
                    ++n;
                }
                n = n2;
                n4 = n8;
                n5 = n3;
                continue;
            }
            n4 = n - n6;
            n7 = n3;
            n5 = n2 >> 5;
            while (n7 > 0) {
                this.map[n5] = n4;
                n4 += 32;
                --n7;
                ++n5;
            }
            n2 += n6;
            int n9 = n8 - n6;
            n6 = n2;
            n7 = n;
            do {
                n = n7++;
                n2 = ++n6;
                n4 = n8;
                n5 = n3;
                if (n9 <= 0) continue block1;
                arrn = this.data;
                arrn[n7] = arrn[n6];
                --n9;
            } while (true);
        }
        n3 = 0;
        while (n3 < this.index2Length) {
            n2 = n3;
            if (n3 == 2080) {
                n2 = n3 + 576;
            }
            arrn = this.index2;
            arrn[n2] = this.map[arrn[n2] >> 5];
            n3 = n2 + 1;
        }
        this.dataNullOffset = this.map[this.dataNullOffset >> 5];
        while ((n & 3) != 0) {
            this.data[n] = this.initialValue;
            ++n;
        }
        if (this.UTRIE2_DEBUG) {
            System.out.printf("compacting UTrie2: count of 32-bit data words %d->%d%n", this.dataLength, n);
        }
        this.dataLength = n;
    }

    private void compactIndex2() {
        int[] arrn;
        int n = 0;
        int n2 = 0;
        while (n < 2080) {
            this.map[n2] = n;
            n += 64;
            ++n2;
        }
        n2 = 2080 + ((this.highStart - 65536 >> 11) + 32);
        n = 2656;
        block1 : while (n < this.index2Length) {
            int n3 = this.findSameIndex2Block(n2, n);
            if (n3 >= 0) {
                this.map[n >> 6] = n3;
                n += 64;
                continue;
            }
            for (n3 = 63; n3 > 0 && !this.equal_int(this.index2, n2 - n3, n, n3); --n3) {
            }
            if (n3 <= 0 && n2 >= n) {
                this.map[n >> 6] = n;
                n2 = n += 64;
                continue;
            }
            this.map[n >> 6] = n2 - n3;
            n += n3;
            int n4 = 64 - n3;
            n3 = n;
            int n5 = n2;
            do {
                n2 = n5++;
                n = ++n3;
                if (n4 <= 0) continue block1;
                arrn = this.index2;
                arrn[n5] = arrn[n3];
                --n4;
            } while (true);
        }
        for (n = 0; n < 544; ++n) {
            arrn = this.index1;
            arrn[n] = this.map[arrn[n] >> 6];
        }
        this.index2NullOffset = this.map[this.index2NullOffset >> 6];
        while ((n2 & 3) != 0) {
            this.index2[n2] = 262140;
            ++n2;
        }
        if (this.UTRIE2_DEBUG) {
            System.out.printf("compacting UTrie2: count of 16-bit index-2 words %d->%d%n", this.index2Length, n2);
        }
        this.index2Length = n2;
    }

    private void compactTrie() {
        int n = this.get(1114111);
        int n2 = this.findHighStart(n) + 2047 & -2048;
        if (n2 == 1114112) {
            n = this.errorValue;
        }
        this.highStart = n2;
        if (this.UTRIE2_DEBUG) {
            System.out.printf("UTrie2: highStart U+%04x  highValue 0x%x  initialValue 0x%x%n", this.highStart, n, this.initialValue);
        }
        if (this.highStart < 1114112) {
            n2 = this.highStart <= 65536 ? 65536 : this.highStart;
            this.setRange(n2, 1114111, this.initialValue, true);
        }
        this.compactData();
        if (this.highStart > 65536) {
            this.compactIndex2();
        } else if (this.UTRIE2_DEBUG) {
            System.out.printf("UTrie2: highStart U+%04x  count of 16-bit index-2 words %d->%d%n", this.highStart, this.index2Length, 2112);
        }
        int[] arrn = this.data;
        n2 = this.dataLength;
        this.dataLength = n2 + 1;
        arrn[n2] = n;
        while ((this.dataLength & 3) != 0) {
            arrn = this.data;
            n = this.dataLength;
            this.dataLength = n + 1;
            arrn[n] = this.initialValue;
        }
        this.isCompacted = true;
    }

    private boolean equal_int(int[] arrn, int n, int n2, int n3) {
        for (int i = 0; i < n3; ++i) {
            if (arrn[n + i] == arrn[n2 + i]) continue;
            return false;
        }
        return true;
    }

    private void fillBlock(int n, int n2, int n3, int n4, int n5, boolean bl) {
        n3 = n + n3;
        if (bl) {
            n += n2;
            while (n < n3) {
                this.data[n] = n4;
                ++n;
            }
        } else {
            n += n2;
            while (n < n3) {
                int[] arrn = this.data;
                if (arrn[n] == n5) {
                    arrn[n] = n4;
                }
                ++n;
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int findHighStart(int var1_1) {
        if (var1_1 == this.initialValue) {
            var2_2 = this.index2NullOffset;
            var3_3 = this.dataNullOffset;
        } else {
            var2_2 = -1;
            var3_3 = -1;
        }
        var4_4 = 544;
        var5_5 = 1114112;
        var6_6 = var3_3;
        var7_7 = var2_2;
        block0 : while (var5_5 > 0) {
            var8_8 = this.index1;
            var9_9 = var4_4 - 1;
            var10_10 = var8_8[var9_9];
            if (var10_10 == var7_7) {
                var5_5 -= 2048;
                var4_4 = var9_9;
                continue;
            }
            var3_3 = var10_10;
            if (var10_10 == this.index2NullOffset) {
                if (var1_1 != this.initialValue) {
                    return var5_5;
                }
                var5_5 -= 2048;
                var7_7 = var3_3;
                var4_4 = var9_9;
                continue;
            }
            var11_11 = 64;
            var2_2 = var5_5;
            var12_12 = var6_6;
            block1 : do {
                var7_7 = var3_3;
                var6_6 = var12_12;
                var4_4 = var9_9;
                var5_5 = var2_2;
                if (var11_11 <= 0) continue block0;
                var8_8 = this.index2;
                var7_7 = var11_11 - 1;
                var13_13 = var8_8[var10_10 + var7_7];
                if (var13_13 == var12_12) {
                    var2_2 -= 32;
                    var11_11 = var7_7;
                    continue;
                }
                var5_5 = var13_13;
                if (var13_13 == this.dataNullOffset) {
                    if (var1_1 != this.initialValue) {
                        return var2_2;
                    }
                    var2_2 -= 32;
                    var12_12 = var5_5;
                    var11_11 = var7_7;
                    continue;
                }
                var6_6 = 32;
                var4_4 = var2_2;
                do {
                    var12_12 = var5_5;
                    var2_2 = var4_4--;
                    var11_11 = var7_7;
                    if (var6_6 > 0) ** break;
                    continue block1;
                    var8_8 = this.data;
                    if (var8_8[var13_13 + --var6_6] == var1_1) continue;
                    return var4_4;
                } while (true);
                break;
            } while (true);
        }
        return 0;
    }

    private int findSameDataBlock(int n, int n2, int n3) {
        for (int i = 0; i <= n - n3; i += 4) {
            if (!this.equal_int(this.data, i, n2, n3)) continue;
            return i;
        }
        return -1;
    }

    private int findSameIndex2Block(int n, int n2) {
        for (int i = 0; i <= n - 64; ++i) {
            if (!this.equal_int(this.index2, i, n2, 64)) continue;
            return i;
        }
        return -1;
    }

    private void freeze(Trie2 trie2, Trie2.ValueWidth valueWidth) {
        if (!this.isCompacted) {
            this.compactTrie();
        }
        int n = this.highStart <= 65536 ? 2112 : this.index2Length;
        int n2 = valueWidth == Trie2.ValueWidth.BITS_16 ? n : 0;
        if (n <= 65535 && this.dataNullOffset + n2 <= 65535 && n2 + 2176 <= 65535 && this.dataLength + n2 <= 262140) {
            int n3;
            Appendable appendable;
            int n4 = n;
            if (valueWidth == Trie2.ValueWidth.BITS_16) {
                n4 += this.dataLength;
            } else {
                trie2.data32 = new int[this.dataLength];
            }
            trie2.index = new char[n4];
            trie2.indexLength = n;
            trie2.dataLength = this.dataLength;
            trie2.index2NullOffset = this.highStart <= 65536 ? 65535 : this.index2NullOffset + 0;
            trie2.initialValue = this.initialValue;
            trie2.errorValue = this.errorValue;
            trie2.highStart = this.highStart;
            trie2.highValueIndex = this.dataLength + n2 - 4;
            trie2.dataNullOffset = this.dataNullOffset + n2;
            trie2.header = new Trie2.UTrie2Header();
            trie2.header.signature = 1416784178;
            Object object = trie2.header;
            n = valueWidth == Trie2.ValueWidth.BITS_16 ? 0 : 1;
            ((Trie2.UTrie2Header)object).options = n;
            trie2.header.indexLength = trie2.indexLength;
            trie2.header.shiftedDataLength = trie2.dataLength >> 2;
            trie2.header.index2NullOffset = trie2.index2NullOffset;
            trie2.header.dataNullOffset = trie2.dataNullOffset;
            trie2.header.shiftedHighStart = trie2.highStart >> 11;
            n = 0;
            n4 = 0;
            while (n4 < 2080) {
                trie2.index[n] = (char)(this.index2[n4] + n2 >> 2);
                ++n4;
                ++n;
            }
            if (this.UTRIE2_DEBUG) {
                appendable = System.out;
                object = new StringBuilder();
                ((StringBuilder)object).append("\n\nIndex2 for BMP limit is ");
                ((StringBuilder)object).append(Integer.toHexString(n));
                ((PrintStream)appendable).println(((StringBuilder)object).toString());
            }
            n4 = 0;
            int n5 = n;
            do {
                n = n5++;
                n3 = ++n4;
                if (n4 >= 2) break;
                trie2.index[n5] = (char)(n2 + 128);
            } while (true);
            while (n3 < 32) {
                trie2.index[n] = (char)(this.index2[n3 << 1] + n2);
                ++n3;
                ++n;
            }
            if (this.UTRIE2_DEBUG) {
                object = System.out;
                appendable = new StringBuilder();
                ((StringBuilder)appendable).append("Index2 for UTF-8 2byte values limit is ");
                ((StringBuilder)appendable).append(Integer.toHexString(n));
                ((PrintStream)object).println(((StringBuilder)appendable).toString());
            }
            n4 = n;
            if (this.highStart > 65536) {
                n3 = this.highStart - 65536 >> 11;
                n5 = n3 + 2112;
                n4 = 0;
                while (n4 < n3) {
                    trie2.index[n] = (char)(this.index1[n4 + 32] + 0);
                    ++n4;
                    ++n;
                }
                if (this.UTRIE2_DEBUG) {
                    object = System.out;
                    appendable = new StringBuilder();
                    ((StringBuilder)appendable).append("Index 1 for supplementals, limit is ");
                    ((StringBuilder)appendable).append(Integer.toHexString(n));
                    ((PrintStream)object).println(((StringBuilder)appendable).toString());
                }
                n4 = 0;
                while (n4 < this.index2Length - n5) {
                    trie2.index[n] = (char)(this.index2[n5 + n4] + n2 >> 2);
                    ++n4;
                    ++n;
                }
                n4 = n;
                if (this.UTRIE2_DEBUG) {
                    object = System.out;
                    appendable = new StringBuilder();
                    ((StringBuilder)appendable).append("Index 2 for supplementals, limit is ");
                    ((StringBuilder)appendable).append(Integer.toHexString(n));
                    ((PrintStream)object).println(((StringBuilder)appendable).toString());
                    n4 = n;
                }
            }
            if ((n = 1.$SwitchMap$android$icu$impl$Trie2$ValueWidth[valueWidth.ordinal()]) != 1) {
                if (n == 2) {
                    for (n = 0; n < this.dataLength; ++n) {
                        trie2.data32[n] = this.data[n];
                    }
                }
            } else {
                trie2.data16 = n4;
                n = 0;
                while (n < this.dataLength) {
                    trie2.index[n4] = (char)this.data[n];
                    ++n;
                    ++n4;
                }
            }
            return;
        }
        throw new UnsupportedOperationException("Trie2 data is too large.");
    }

    private int get(int n, boolean bl) {
        if (n >= this.highStart && (n < 55296 || n >= 56320 || bl)) {
            return this.data[this.dataLength - 4];
        }
        int n2 = n >= 55296 && n < 56320 && bl ? (n >> 5) + 320 : this.index1[n >> 11] + (n >> 5 & 63);
        n2 = this.index2[n2];
        return this.data[(n & 31) + n2];
    }

    private int getDataBlock(int n, boolean bl) {
        int n2 = this.index2[n = this.getIndex2Block(n, bl) + (n >> 5 & 63)];
        if (this.isWritableBlock(n2)) {
            return n2;
        }
        n2 = this.allocDataBlock(n2);
        this.setIndex2Entry(n, n2);
        return n2;
    }

    private int getIndex2Block(int n, boolean bl) {
        int n2;
        if (n >= 55296 && n < 56320 && bl) {
            return 2048;
        }
        int n3 = n >> 11;
        n = n2 = this.index1[n3];
        if (n2 == this.index2NullOffset) {
            this.index1[n3] = n = this.allocIndex2Block();
        }
        return n;
    }

    private void init(int n, int n2) {
        int n3;
        int n4;
        this.initialValue = n;
        this.errorValue = n2;
        this.highStart = 1114112;
        this.data = new int[16384];
        this.dataCapacity = 16384;
        this.initialValue = n;
        this.errorValue = n2;
        this.highStart = 1114112;
        this.firstFreeBlock = 0;
        this.isCompacted = false;
        n = 0;
        do {
            if (n >= 128) break;
            this.data[n] = this.initialValue;
        } while (true);
        for (n2 = ++n; n2 < 192; ++n2) {
            this.data[n2] = this.errorValue;
        }
        for (n = 192; n < 256; ++n) {
            this.data[n] = this.initialValue;
        }
        this.dataNullOffset = 192;
        this.dataLength = 256;
        n2 = 0;
        n = 0;
        do {
            n4 = n2++;
            if (n >= 128) break;
            this.index2[n2] = n;
            this.map[n2] = 1;
            n += 32;
        } while (true);
        for (n3 = n; n3 < 192; n3 += 32) {
            this.map[n4] = 0;
            ++n4;
        }
        int[] arrn = this.map;
        n = n4 + 1;
        arrn[n4] = 34845;
        for (n2 = n3 + 32; n2 < 256; n2 += 32) {
            this.map[n] = 0;
            ++n;
        }
        for (n = 4; n < 2080; ++n) {
            this.index2[n] = 192;
        }
        for (n = 0; n < 576; ++n) {
            this.index2[n + 2080] = -1;
        }
        for (n = 0; n < 64; ++n) {
            this.index2[n + 2656] = 192;
        }
        this.index2NullOffset = 2656;
        this.index2Length = 2720;
        n = 0;
        n2 = 0;
        do {
            if (n >= 32) break;
            this.index1[n] = n2;
            n2 += 64;
        } while (true);
        for (n3 = ++n; n3 < 544; ++n3) {
            this.index1[n3] = 2656;
        }
        for (n = 128; n < 2048; n += 32) {
            this.set(n, this.initialValue);
        }
    }

    private boolean isInNullBlock(int n, boolean bl) {
        n = Character.isHighSurrogate((char)n) && bl ? (n >> 5) + 320 : this.index1[n >> 11] + (n >> 5 & 63);
        bl = this.index2[n] == this.dataNullOffset;
        return bl;
    }

    private boolean isWritableBlock(int n) {
        int n2 = this.dataNullOffset;
        boolean bl = true;
        if (n == n2 || 1 != this.map[n >> 5]) {
            bl = false;
        }
        return bl;
    }

    private void releaseDataBlock(int n) {
        this.map[n >> 5] = -this.firstFreeBlock;
        this.firstFreeBlock = n;
    }

    private Trie2Writable set(int n, boolean bl, int n2) {
        if (this.isCompacted) {
            this.uncompact();
        }
        int n3 = this.getDataBlock(n, bl);
        this.data[(n & 31) + n3] = n2;
        return this;
    }

    private void setIndex2Entry(int n, int n2) {
        int n3;
        int[] arrn = this.map;
        int n4 = n2 >> 5;
        arrn[n4] = arrn[n4] + 1;
        int n5 = this.index2[n];
        n4 = n5 >> 5;
        arrn[n4] = n3 = arrn[n4] - 1;
        if (n3 == 0) {
            this.releaseDataBlock(n5);
        }
        this.index2[n] = n2;
    }

    private void uncompact() {
        Trie2Writable trie2Writable = new Trie2Writable(this);
        this.index1 = trie2Writable.index1;
        this.index2 = trie2Writable.index2;
        this.data = trie2Writable.data;
        this.index2Length = trie2Writable.index2Length;
        this.dataCapacity = trie2Writable.dataCapacity;
        this.isCompacted = trie2Writable.isCompacted;
        this.header = trie2Writable.header;
        this.index = trie2Writable.index;
        this.data16 = trie2Writable.data16;
        this.data32 = trie2Writable.data32;
        this.indexLength = trie2Writable.indexLength;
        this.dataLength = trie2Writable.dataLength;
        this.index2NullOffset = trie2Writable.index2NullOffset;
        this.initialValue = trie2Writable.initialValue;
        this.errorValue = trie2Writable.errorValue;
        this.highStart = trie2Writable.highStart;
        this.highValueIndex = trie2Writable.highValueIndex;
        this.dataNullOffset = trie2Writable.dataNullOffset;
    }

    private void writeBlock(int n, int n2) {
        for (int i = n; i < n + 32; ++i) {
            this.data[i] = n2;
        }
    }

    @Override
    public int get(int n) {
        if (n >= 0 && n <= 1114111) {
            return this.get(n, true);
        }
        return this.errorValue;
    }

    @Override
    public int getFromU16SingleLead(char c) {
        return this.get(c, false);
    }

    public Trie2Writable set(int n, int n2) {
        if (n >= 0 && n <= 1114111) {
            this.set(n, true, n2);
            this.fHash = 0;
            return this;
        }
        throw new IllegalArgumentException("Invalid code point.");
    }

    public Trie2Writable setForLeadSurrogateCodeUnit(char c, int n) {
        this.fHash = 0;
        this.set(c, false, n);
        return this;
    }

    public Trie2Writable setRange(int n, int n2, int n3, boolean bl) {
        block16 : {
            int n4;
            if (n > 1114111 || n < 0 || n2 > 1114111 || n2 < 0 || n > n2) break block16;
            if (!bl && n3 == this.initialValue) {
                return this;
            }
            this.fHash = 0;
            if (this.isCompacted) {
                this.uncompact();
            }
            int n5 = n2 + 1;
            if ((n & 31) != 0) {
                n4 = this.getDataBlock(n, true);
                n2 = n + 32 & -32;
                if (n2 <= n5) {
                    this.fillBlock(n4, n & 31, 32, n3, this.initialValue, bl);
                    n = n2;
                } else {
                    this.fillBlock(n4, n & 31, n5 & 31, n3, this.initialValue, bl);
                    return this;
                }
            }
            int n6 = n5 & 31;
            n2 = n3 == this.initialValue ? this.dataNullOffset : -1;
            while (n < (n5 & -32)) {
                int n7;
                int n8;
                block18 : {
                    block19 : {
                        int n9;
                        block17 : {
                            n7 = 0;
                            if (n3 == this.initialValue && this.isInNullBlock(n, true)) {
                                n += 32;
                                continue;
                            }
                            n8 = this.getIndex2Block(n, true) + (n >> 5 & 63);
                            n9 = this.index2[n8];
                            if (!this.isWritableBlock(n9)) break block17;
                            if (bl && n9 >= 2176) {
                                n4 = 1;
                            } else {
                                this.fillBlock(n9, 0, 32, n3, this.initialValue, bl);
                                n4 = n7;
                            }
                            break block18;
                        }
                        n4 = n7;
                        if (this.data[n9] == n3) break block18;
                        if (bl) break block19;
                        n4 = n7;
                        if (n9 != this.dataNullOffset) break block18;
                    }
                    n4 = 1;
                }
                n7 = n2;
                if (n4 != 0) {
                    if (n2 >= 0) {
                        this.setIndex2Entry(n8, n2);
                        n7 = n2;
                    } else {
                        n7 = this.getDataBlock(n, true);
                        this.writeBlock(n7, n3);
                    }
                }
                n += 32;
                n2 = n7;
            }
            if (n6 > 0) {
                this.fillBlock(this.getDataBlock(n, true), 0, n6, n3, this.initialValue, bl);
            }
            return this;
        }
        throw new IllegalArgumentException("Invalid code point range.");
    }

    public Trie2Writable setRange(Trie2.Range range, boolean bl) {
        this.fHash = 0;
        if (range.leadSurrogate) {
            for (int i = range.startCodePoint; i <= range.endCodePoint; ++i) {
                if (!bl && this.getFromU16SingleLead((char)i) != this.initialValue) continue;
                this.setForLeadSurrogateCodeUnit((char)i, range.value);
            }
        } else {
            this.setRange(range.startCodePoint, range.endCodePoint, range.value, bl);
        }
        return this;
    }

    public Trie2_16 toTrie2_16() {
        Trie2_16 trie2_16 = new Trie2_16();
        this.freeze(trie2_16, Trie2.ValueWidth.BITS_16);
        return trie2_16;
    }

    public Trie2_32 toTrie2_32() {
        Trie2_32 trie2_32 = new Trie2_32();
        this.freeze(trie2_32, Trie2.ValueWidth.BITS_32);
        return trie2_32;
    }

}

