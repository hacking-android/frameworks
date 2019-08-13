/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.util.CodePointMap;
import android.icu.util.CodePointTrie;
import java.util.Arrays;

public final class MutableCodePointTrie
extends CodePointMap
implements Cloneable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final byte ALL_SAME = 0;
    private static final int ASCII_I_LIMIT = 8;
    private static final int ASCII_LIMIT = 128;
    private static final int BMP_I_LIMIT = 4096;
    private static final int BMP_LIMIT = 65536;
    private static final byte I3_16 = 2;
    private static final byte I3_18 = 3;
    private static final byte I3_BMP = 1;
    private static final byte I3_NULL = 0;
    private static final int INDEX_3_18BIT_BLOCK_LENGTH = 36;
    private static final int INITIAL_DATA_LENGTH = 16384;
    private static final int I_LIMIT = 69632;
    private static final int MAX_DATA_LENGTH = 1114112;
    private static final int MAX_UNICODE = 1114111;
    private static final int MEDIUM_DATA_LENGTH = 131072;
    private static final byte MIXED = 1;
    private static final byte SAME_AS = 2;
    private static final int SMALL_DATA_BLOCKS_PER_BMP_BLOCK = 4;
    private static final int UNICODE_LIMIT = 1114112;
    private int[] data = new int[16384];
    private int dataLength;
    private int dataNullOffset = -1;
    private int errorValue;
    private byte[] flags = new byte[69632];
    private int highStart;
    private int highValue;
    private int[] index = new int[4096];
    private char[] index16;
    private int index3NullOffset = -1;
    private int initialValue;
    private int origInitialValue;

    public MutableCodePointTrie(int n, int n2) {
        this.origInitialValue = n;
        this.initialValue = n;
        this.errorValue = n2;
        this.highValue = n;
    }

    private static boolean allValuesSameAs(int[] arrn, int n, int n2, int n3) {
        n2 = n + n2;
        while (n < n2 && arrn[n] == n3) {
            ++n;
        }
        boolean bl = n == n2;
        return bl;
    }

    private int allocDataBlock(int n) {
        int n2 = this.dataLength;
        int n3 = n2 + n;
        int[] arrn = this.data;
        if (n3 > arrn.length) {
            if (arrn.length < 131072) {
                n = 131072;
            } else {
                if (arrn.length >= 1114112) {
                    throw new AssertionError();
                }
                n = 1114112;
            }
            arrn = new int[n];
            for (n = 0; n < this.dataLength; ++n) {
                arrn[n] = this.data[n];
            }
            this.data = arrn;
        }
        this.dataLength = n3;
        return n2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private CodePointTrie build(CodePointTrie.Type var1_1, CodePointTrie.ValueWidth var2_2) {
        block25 : {
            block27 : {
                block26 : {
                    block24 : {
                        var3_12 = 1.$SwitchMap$android$icu$util$CodePointTrie$ValueWidth[var2_11.ordinal()];
                        if (var3_12 != 1) {
                            if (var3_12 != 2) {
                                if (var3_12 != 3) throw new IllegalArgumentException();
                                this.maskValues(255);
                            } else {
                                this.maskValues(65535);
                            }
                        }
                        var4_13 = var1_1 /* !! */  == CodePointTrie.Type.FAST ? 65536 : 4096;
                        var3_12 = var5_14 = this.compactTrie(var4_13 >> 4);
                        if (var2_11 == CodePointTrie.ValueWidth.BITS_32) {
                            var3_12 = var5_14;
                            if ((var5_14 & 1) != 0) {
                                this.index16[var5_14] = (char)65518;
                                var3_12 = var5_14 + 1;
                            }
                        }
                        var6_15 = var3_12 * 2;
                        if (var2_11 != CodePointTrie.ValueWidth.BITS_16) break block24;
                        var5_14 = this.dataLength;
                        if (((var3_12 ^ var5_14) & 1) != 0) {
                            var7_16 = this.data;
                            this.dataLength = var5_14 + 1;
                            var7_16[var5_14] = this.errorValue;
                        }
                        if ((var7_16 = this.data)[(var5_14 = this.dataLength) - 1] != this.errorValue || var7_16[var5_14 - 2] != this.highValue) {
                            var7_16 = this.data;
                            var5_14 = this.dataLength;
                            this.dataLength = var5_14 + 1;
                            var7_16[var5_14] = this.highValue;
                            var5_14 = this.dataLength;
                            this.dataLength = var5_14 + 1;
                            var7_16[var5_14] = this.errorValue;
                        }
                        var5_14 = this.dataLength;
                        break block25;
                    }
                    if (var2_11 != CodePointTrie.ValueWidth.BITS_32) break block26;
                    var7_16 = this.data;
                    var5_14 = this.dataLength;
                    if (var7_16[var5_14 - 1] != this.errorValue || var7_16[var5_14 - 2] != this.highValue) {
                        var7_16 = this.data;
                        var6_15 = this.dataLength;
                        var8_17 = var7_16[var6_15 - 1];
                        var5_14 = this.highValue;
                        if (var8_17 != var5_14) {
                            this.dataLength = var6_15 + 1;
                            var7_16[var6_15] = var5_14;
                        }
                        var7_16 = this.data;
                        var5_14 = this.dataLength;
                        this.dataLength = var5_14 + 1;
                        var7_16[var5_14] = this.errorValue;
                    }
                    var5_14 = this.dataLength;
                    break block25;
                }
                var5_14 = this.dataLength;
                if ((var6_15 = var6_15 + var5_14 & 3) == 0 && (var7_16 = this.data)[var5_14 - 1] == this.errorValue && var7_16[var5_14 - 2] == this.highValue) break block27;
                var5_14 = var6_15;
                if (var6_15 != 3) ** GOTO lbl-1000
                var7_16 = this.data;
                var8_17 = this.dataLength;
                var5_14 = var6_15;
                if (var7_16[var8_17 - 1] == this.highValue) {
                    this.dataLength = var8_17 + 1;
                    var7_16[var8_17] = this.errorValue;
                } else lbl-1000: // 3 sources:
                {
                    while (var5_14 != 2) {
                        var7_16 = this.data;
                        var6_15 = this.dataLength;
                        this.dataLength = var6_15 + 1;
                        var7_16[var6_15] = this.highValue;
                        var5_14 = var5_14 + 1 & 3;
                    }
                    var7_16 = this.data;
                    var5_14 = this.dataLength;
                    this.dataLength = var5_14 + 1;
                    var7_16[var5_14] = this.highValue;
                    var5_14 = this.dataLength;
                    this.dataLength = var5_14 + 1;
                    var7_16[var5_14] = this.errorValue;
                }
            }
            var5_14 = this.dataLength;
        }
        if (this.highStart <= var4_13) {
            var7_16 = new char[var3_12];
            var5_14 = 0;
            for (var4_13 = 0; var4_13 < var3_12; var5_14 += 4, ++var4_13) {
                var7_16[var4_13] = (char)this.index[var5_14];
            }
        } else {
            var7_16 = this.index16;
            if (var3_12 == var7_16.length) {
                var7_16 = this.index16;
                this.index16 = null;
            } else {
                var7_16 = Arrays.copyOf((char[])var7_16, var3_12);
            }
        }
        var3_12 = 1.$SwitchMap$android$icu$util$CodePointTrie$ValueWidth[var2_11.ordinal()];
        if (var3_12 != 1) {
            if (var3_12 != 2) {
                if (var3_12 != 3) throw new IllegalArgumentException();
                var2_11 = new byte[this.dataLength];
                for (var3_12 = 0; var3_12 < this.dataLength; ++var3_12) {
                    var2_11[var3_12] = (byte)this.data[var3_12];
                }
                if (var1_1 /* !! */  == CodePointTrie.Type.FAST) {
                    var1_2 = new CodePointTrie.Fast8((char[])var7_16, (byte[])var2_11, this.highStart, this.index3NullOffset, this.dataNullOffset);
                    return var1_4;
                }
                var1_3 = new CodePointTrie.Small8((char[])var7_16, (byte[])var2_11, this.highStart, this.index3NullOffset, this.dataNullOffset);
                return var1_4;
            }
            var2_11 = new char[this.dataLength];
            for (var3_12 = 0; var3_12 < this.dataLength; ++var3_12) {
                var2_11[var3_12] = (char)this.data[var3_12];
            }
            if (var1_1 /* !! */  == CodePointTrie.Type.FAST) {
                var1_5 = new CodePointTrie.Fast16((char[])var7_16, (char[])var2_11, this.highStart, this.index3NullOffset, this.dataNullOffset);
                return var1_7;
            }
            var1_6 = new CodePointTrie.Small16((char[])var7_16, (char[])var2_11, this.highStart, this.index3NullOffset, this.dataNullOffset);
            return var1_7;
        }
        var2_11 = Arrays.copyOf(this.data, this.dataLength);
        if (var1_1 /* !! */  == CodePointTrie.Type.FAST) {
            var1_8 = new CodePointTrie.Fast32((char[])var7_16, var2_11, this.highStart, this.index3NullOffset, this.dataNullOffset);
            return var1_10;
        }
        var1_9 = new CodePointTrie.Small32((char[])var7_16, var2_11, this.highStart, this.index3NullOffset, this.dataNullOffset);
        return var1_10;
    }

    private void clear() {
        int n;
        this.dataNullOffset = -1;
        this.index3NullOffset = -1;
        this.dataLength = 0;
        this.initialValue = n = this.origInitialValue;
        this.highValue = n;
        this.highStart = 0;
        this.index16 = null;
    }

    private int compactData(int n, int[] arrn, int n2, MixedBlocks mixedBlocks) {
        int n3 = 0;
        int n4 = 0;
        while (n3 < 128) {
            this.index[n4] = n3;
            n3 += 64;
            n4 += 4;
        }
        int n5 = 64;
        mixedBlocks.init(arrn.length, 64);
        n4 = 0;
        mixedBlocks.extend(arrn, 0, 0, n3);
        int n6 = this.highStart;
        int n7 = 4;
        int n8 = 0;
        for (int i = 8; i < n6 >> 4; i += n7) {
            int n9;
            int n10;
            byte[] arrby;
            int n11;
            if (i == n) {
                n5 = 16;
                n7 = 1;
                n8 = n3;
                mixedBlocks.init(arrn.length, 16);
                mixedBlocks.extend(arrn, n4, n4, n3);
            }
            if ((arrby = this.flags)[i] == 0) {
                n9 = this.index[i];
                n11 = mixedBlocks.findAllSameBlock(arrn, n9);
                while (n11 >= 0 && i == n2 && i >= n && n11 < n8 && MutableCodePointTrie.isStartOfSomeFastBlock(n11, this.index, n)) {
                    n11 = MutableCodePointTrie.findAllSameBlock(arrn, n11 + 1, n3, n9, n5);
                }
                if (n11 >= 0) {
                    this.index[i] = n11;
                    continue;
                }
                n10 = MutableCodePointTrie.getAllSameOverlap(arrn, n3, n9, n5);
                this.index[i] = n3 - n10;
                n11 = n3;
                while (n10 < n5) {
                    arrn[n11] = n9;
                    ++n10;
                    ++n11;
                }
                mixedBlocks.extend(arrn, n4, n3, n11);
                n3 = n11;
                continue;
            }
            if (arrby[i] == 1) {
                n9 = this.index[i];
                n11 = mixedBlocks.findBlock(arrn, this.data, n9);
                if (n11 >= 0) {
                    this.index[i] = n11;
                    continue;
                }
                n11 = MutableCodePointTrie.getOverlap(arrn, n3, this.data, n9, n5);
                this.index[i] = n3 - n11;
                n4 = n3;
                do {
                    n10 = n4;
                    if (n11 >= n5) break;
                    arrn[n10] = this.data[n11 + n9];
                    n4 = n10 + 1;
                    ++n11;
                } while (true);
                n4 = 0;
                mixedBlocks.extend(arrn, 0, n3, n10);
                n3 = n10;
                continue;
            }
            arrby = this.index;
            arrby[i] = arrby[arrby[i]];
        }
        return n3;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private int compactIndex(int var1_1, MixedBlocks var2_2) {
        var3_3 = var1_1 >> 2;
        if (this.highStart >> 6 <= var3_3) {
            this.index3NullOffset = 32767;
            return var3_3;
        }
        var4_4 = new char[var3_3];
        var5_5 = -1;
        var6_6 = 0;
        var7_7 = 0;
        while ((var8_8 = var6_6) < var1_1) {
            var9_9 = this.index[var8_8];
            var4_4[var7_7] = (char)var9_9;
            if (var9_9 == this.dataNullOffset) {
                if (var5_5 < 0) {
                    var6_6 = var7_7;
                } else {
                    var6_6 = var5_5;
                    if (this.index3NullOffset < 0) {
                        var6_6 = var5_5;
                        if (var7_7 - var5_5 + 1 == 32) {
                            this.index3NullOffset = var5_5;
                            var6_6 = var5_5;
                        }
                    }
                }
            } else {
                var6_6 = -1;
            }
            var5_5 = var9_9;
            var9_9 = var8_8;
            while (++var9_9 < var8_8 + 4) {
                this.index[var9_9] = var5_5 += 16;
            }
            ++var7_7;
            var5_5 = var6_6;
            var6_6 = var9_9;
        }
        var2_2.init(var3_3, 32);
        var2_2.extend(var4_4, 0, 0, var3_3);
        var6_6 = 0;
        var9_9 = this.index3NullOffset;
        var10_10 = 0;
        var8_8 = 4096;
        if (var1_1 < 4096) {
            var8_8 = 0;
        }
        var11_11 = this.highStart >> 4;
        var7_7 = var8_8;
        var1_1 = var6_6;
        block2 : do {
            if (var7_7 >= var11_11) {
                var15_15 = var11_11 - var8_8 >> 5;
                var16_16 = var15_15 + 31 >> 5;
                var5_5 = var3_3 + var16_16 + var1_1 + var15_15 + 1;
                this.index16 = Arrays.copyOf(var4_4, var5_5);
                var2_2.init(var5_5, 32);
                var17_17 = null;
                if (var10_10 != 0) {
                    var17_17 = new MixedBlocks();
                    var17_17.init(var5_5, 36);
                }
                break;
            }
            var6_6 = var7_7;
            var5_5 = 0;
            var12_12 = 1;
            do {
                var13_13 = this.index[var6_6];
                var14_14 = var5_5 | var13_13;
                if (var13_13 != this.dataNullOffset) {
                    var12_12 = 0;
                }
                if ((var5_5 = var6_6 + 1) >= var7_7 + 32) {
                    if (var12_12 != 0) {
                        this.flags[var7_7] = (byte)(false ? 1 : 0);
                        var12_12 = var9_9;
                        var7_7 = var1_1;
                        var6_6 = var10_10;
                        if (var9_9 < 0) {
                            if (var14_14 <= 65535) {
                                var7_7 = var1_1 + 32;
                                var6_6 = var10_10;
                            } else {
                                var7_7 = var1_1 + 36;
                                var6_6 = 1;
                            }
                            var12_12 = 0;
                        }
                    } else if (var14_14 <= 65535) {
                        var6_6 = var2_2.findBlock(var4_4, this.index, var7_7);
                        if (var6_6 >= 0) {
                            this.flags[var7_7] = (byte)(true ? 1 : 0);
                            this.index[var7_7] = var6_6;
                        } else {
                            this.flags[var7_7] = (byte)2;
                            var1_1 += 32;
                        }
                        var12_12 = var9_9;
                        var7_7 = var1_1;
                        var6_6 = var10_10;
                    } else {
                        this.flags[var7_7] = (byte)3;
                        var7_7 = var1_1 + 36;
                        var6_6 = 1;
                        var12_12 = var9_9;
                    }
                    var9_9 = var12_12;
                    var1_1 = var7_7;
                    var10_10 = var6_6;
                    var7_7 = var5_5;
                    continue block2;
                }
                var6_6 = var5_5;
                var5_5 = var14_14;
            } while (true);
            break;
        } while (true);
        var4_4 = new char[var15_15];
        var13_13 = 0;
        var12_12 = this.index3NullOffset;
        var6_6 = var18_18 = var3_3 + var16_16;
        var7_7 = var8_8;
        var9_9 = var11_11;
        var19_19 = var1_1;
        var1_1 = var6_6;
        var6_6 = var3_3;
        block4 : do {
            block57 : {
                block54 : {
                    block56 : {
                        block55 : {
                            if (var7_7 >= var9_9) break block54;
                            var3_3 = this.flags[var7_7];
                            if (var3_3 == 0 && var12_12 < 0) {
                                var12_12 = this.dataNullOffset <= 65535 ? 2 : 3;
                                var11_11 = 0;
                                var3_3 = var12_12;
                                var12_12 = var11_11;
                            }
                            if (var3_3 != 0) break block55;
                            var14_14 = this.index3NullOffset;
                            var3_3 = var6_6;
                            var6_6 = var7_7;
                            var11_11 = var1_1;
                            var7_7 = var14_14;
                            var1_1 = var5_5;
                            var5_5 = var6_6;
                            var6_6 = var8_8;
                            var8_8 = var3_3;
                            ** GOTO lbl250
                        }
                        if (var3_3 != 1) break block56;
                        var14_14 = this.index[var7_7];
                        var3_3 = var6_6;
                        var6_6 = var7_7;
                        var11_11 = var1_1;
                        var7_7 = var14_14;
                        var1_1 = var5_5;
                        var5_5 = var6_6;
                        var6_6 = var8_8;
                        var8_8 = var3_3;
                        ** GOTO lbl250
                    }
                    if (var3_3 != 2) break block57;
                    var3_3 = var2_2.findBlock(this.index16, this.index, var7_7);
                    if (var3_3 >= 0) {
                        var11_11 = var1_1;
                        var1_1 = var3_3;
                    } else {
                        var11_11 = var1_1 == var18_18 ? 0 : MutableCodePointTrie.getOverlap(this.index16, var1_1, this.index, var7_7, 32);
                        var3_3 = var1_1 - var11_11;
                        var14_14 = var11_11;
                        var11_11 = var1_1;
                        while (var14_14 < 32) {
                            this.index16[var11_11] = (char)this.index[var14_14 + var7_7];
                            ++var11_11;
                            ++var14_14;
                        }
                        var2_2.extend(this.index16, var18_18, var1_1, var11_11);
                        if (var10_10 != 0) {
                            var17_17.extend(this.index16, var18_18, var1_1, var11_11);
                        }
                        var1_1 = var3_3;
                    }
                    var3_3 = var6_6;
                    var6_6 = var7_7;
                    var7_7 = var1_1;
                    var1_1 = var5_5;
                    var5_5 = var6_6;
                    var6_6 = var8_8;
                    var8_8 = var3_3;
                    ** GOTO lbl250
                }
                if (this.index3NullOffset < 0) {
                    this.index3NullOffset = 32767;
                }
                break;
            }
            var20_20 = var7_7;
            var21_21 = var1_1;
            var11_11 = var8_8;
            var3_3 = var7_7;
            var8_8 = var6_6;
            do {
                block58 : {
                    var14_14 = var3_3;
                    var22_22 = var21_21 + 1;
                    var3_3 = var5_5;
                    var23_23 = this.index;
                    var24_24 = var20_20 + 1;
                    var21_21 = var23_23[var20_20];
                    var6_6 = var11_11;
                    var25_25 = this.index16;
                    var5_5 = var22_22 + 1;
                    var25_25[var22_22] = (char)var21_21;
                    var20_20 = var24_24 + 1;
                    var22_22 = var23_23[var24_24];
                    var11_11 = var5_5 + 1;
                    var25_25[var5_5] = (char)var22_22;
                    var26_26 = var20_20 + 1;
                    var24_24 = var23_23[var20_20];
                    var20_20 = var11_11 + 1;
                    var5_5 = var14_14;
                    var25_25[var11_11] = (char)var24_24;
                    var27_27 = var26_26 + 1;
                    var14_14 = var23_23[var26_26];
                    var28_28 = var20_20 + 1;
                    var25_25[var20_20] = (char)var14_14;
                    var11_11 = var27_27 + 1;
                    var26_26 = var23_23[var27_27];
                    var20_20 = var28_28 + 1;
                    var25_25[var28_28] = (char)var26_26;
                    var27_27 = var11_11 + 1;
                    var28_28 = var23_23[var11_11];
                    var11_11 = var20_20 + 1;
                    var25_25[var20_20] = (char)var28_28;
                    var29_29 = var27_27 + 1;
                    var27_27 = var23_23[var27_27];
                    var30_30 = var11_11 + 1;
                    var25_25[var11_11] = (char)var27_27;
                    var20_20 = var29_29 + 1;
                    var29_29 = var23_23[var29_29];
                    var11_11 = var30_30 + 1;
                    var25_25[var30_30] = (char)var29_29;
                    var25_25[var11_11 - 9] = (char)((var14_14 & 196608) >> 8 | ((var24_24 & 196608) >> 6 | ((var21_21 & 196608) >> 2 | (var22_22 & 196608) >> 4)) | (var26_26 & 196608) >> 10 | (var28_28 & 196608) >> 12 | (var27_27 & 196608) >> 14 | (var29_29 & 196608) >> 16);
                    if (var20_20 < var7_7 + 32) break block58;
                    var7_7 = var17_17.findBlock(var25_25, var25_25, var1_1);
                    if (var7_7 >= 0) {
                        var7_7 = 32768 | var7_7;
                        var11_11 = var1_1;
                        var1_1 = var3_3;
                    } else {
                        if (var1_1 == var18_18) {
                            var7_7 = 0;
                        } else {
                            var23_23 = this.index16;
                            var7_7 = MutableCodePointTrie.getOverlap((char[])var23_23, var1_1, (char[])var23_23, var1_1, 36);
                        }
                        if (var7_7 > 0) {
                            var14_14 = var1_1;
                            for (var11_11 = var7_7; var11_11 < 36; ++var14_14, ++var11_11) {
                                var23_23 = this.index16;
                                var23_23[var14_14] = (char)var23_23[var11_11 + var1_1];
                            }
                            var11_11 = var14_14;
                        } else {
                            var11_11 = var1_1 + 36;
                        }
                        var2_2.extend(this.index16, var18_18, var1_1, var11_11);
                        if (var10_10 != 0) {
                            var17_17.extend(this.index16, var18_18, var1_1, var11_11);
                        }
                        var7_7 = 32768 | var1_1 - var7_7;
                        var1_1 = var3_3;
                    }
lbl250: // 5 sources:
                    if (this.index3NullOffset < 0 && var12_12 >= 0) {
                        this.index3NullOffset = var7_7;
                    }
                    var4_4[var13_13] = (char)var7_7;
                    var7_7 = var5_5 + 32;
                    ++var13_13;
                    var5_5 = var1_1;
                    var3_3 = var6_6;
                    var6_6 = var8_8;
                    var1_1 = var11_11;
                    var8_8 = var3_3;
                    continue block4;
                }
                var14_14 = var3_3;
                var3_3 = var5_5;
                var21_21 = var11_11;
                var5_5 = var14_14;
                var11_11 = var6_6;
            } while (true);
            break;
        } while (true);
        if (var1_1 >= 32799) throw new IndexOutOfBoundsException("The trie data exceeds limitations of the data structure.");
        var8_8 = 32;
        var9_9 = 0;
        while (var9_9 < var13_13) {
            if (var13_13 - var9_9 >= var8_8) {
                var7_7 = var2_2.findBlock(this.index16, var4_4, var9_9);
            } else {
                var8_8 = var13_13 - var9_9;
                var7_7 = MutableCodePointTrie.findSameBlock(this.index16, var18_18, var1_1, var4_4, var9_9, var8_8);
            }
            if (var7_7 >= 0) {
                var5_5 = var7_7;
            } else {
                var7_7 = var1_1 == var18_18 ? 0 : MutableCodePointTrie.getOverlap(this.index16, var1_1, var4_4, var9_9, var8_8);
                var10_10 = var1_1 - var7_7;
                var5_5 = var7_7;
                var7_7 = var1_1;
                while (var5_5 < var8_8) {
                    this.index16[var7_7] = var4_4[var5_5 + var9_9];
                    ++var7_7;
                    ++var5_5;
                }
                var2_2.extend(this.index16, var18_18, var1_1, var7_7);
                var5_5 = var10_10;
                var1_1 = var7_7;
            }
            this.index16[var6_6] = (char)var5_5;
            var9_9 += var8_8;
            ++var6_6;
        }
        return var1_1;
    }

    private int compactTrie(int n) {
        int n2;
        int n3;
        this.highValue = this.get(1114111);
        int n4 = this.findHighStart() + 511 & -512;
        if (n4 == 1114112) {
            this.highValue = this.initialValue;
        }
        if (n4 < (n3 = n << 4)) {
            for (n2 = n4 >> 4; n2 < n; ++n2) {
                this.flags[n2] = (byte)(false ? 1 : 0);
                this.index[n2] = this.highValue;
            }
            this.highStart = n3;
        } else {
            this.highStart = n4;
        }
        int[] arrn = new int[128];
        for (n2 = 0; n2 < 128; ++n2) {
            arrn[n2] = this.get(n2);
        }
        Object object = new AllSameBlocks();
        arrn = Arrays.copyOf(arrn, this.compactWholeDataBlocks(n, (AllSameBlocks)object));
        n2 = ((AllSameBlocks)object).findMostUsed();
        object = new MixedBlocks();
        n3 = this.compactData(n, arrn, n2, (MixedBlocks)object);
        this.data = arrn;
        this.dataLength = n3;
        if (this.dataLength <= 262159) {
            if (n2 >= 0) {
                this.dataNullOffset = this.index[n2];
                this.initialValue = this.data[this.dataNullOffset];
            } else {
                this.dataNullOffset = 1048575;
            }
            n = this.compactIndex(n, (MixedBlocks)object);
            this.highStart = n4;
            return n;
        }
        throw new IndexOutOfBoundsException("The trie data exceeds limitations of the data structure.");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private int compactWholeDataBlocks(int var1_1, AllSameBlocks var2_2) {
        var3_3 = 128 + 16 + 4;
        var4_4 = this.highStart;
        var5_5 = 64;
        var6_6 = 4;
        var7_7 = 0;
        while (var7_7 < var4_4 >> 4) {
            block16 : {
                block14 : {
                    block15 : {
                        if (var7_7 == var1_1) {
                            var5_5 = 16;
                            var6_6 = 1;
                        }
                        var8_8 = this.index[var7_7];
                        if (this.flags[var7_7] != 1) break block14;
                        var9_9 = this.data;
                        var10_10 = var9_9[var8_8];
                        if (!MutableCodePointTrie.allValuesSameAs(var9_9, var8_8 + 1, var5_5 - 1, var10_10)) break block15;
                        this.flags[var7_7] = (byte)(false ? 1 : 0);
                        this.index[var7_7] = var10_10;
                        ** GOTO lbl-1000
                    }
                    var11_11 = var3_3 + var5_5;
                    break block16;
                }
                var10_10 = var8_8;
                if (var6_6 <= 1) ** GOTO lbl-1000
                var12_12 = 1;
                var10_10 = var7_7 + 1;
                do {
                    var11_11 = var12_12;
                    if (var10_10 >= var7_7 + var6_6) break;
                    if (this.index[var10_10] != var8_8) {
                        var11_11 = 0;
                        break;
                    }
                    ++var10_10;
                } while (true);
                var10_10 = var8_8;
                if (var11_11 == 0) {
                    if (this.getDataBlock(var7_7) < 0) {
                        return -1;
                    }
                    var11_11 = var3_3 + var5_5;
                } else lbl-1000: // 3 sources:
                {
                    var11_11 = var12_12 = var2_2.findOrAdd(var7_7, var6_6, var10_10);
                    if (var12_12 == -2) {
                        var8_8 = 4;
                        var11_11 = 0;
                        do {
                            if (var11_11 == var7_7) {
                                var2_2.add(var7_7, var6_6, var10_10);
                                var11_11 = var12_12;
                                break;
                            }
                            if (var11_11 == var1_1) {
                                var8_8 = 1;
                            }
                            if (this.flags[var11_11] == 0 && this.index[var11_11] == var10_10) {
                                var2_2.add(var11_11, var8_8 + var6_6, var10_10);
                                break;
                            }
                            var11_11 += var8_8;
                        } while (true);
                    }
                    if (var11_11 >= 0) {
                        this.flags[var7_7] = (byte)2;
                        this.index[var7_7] = var11_11;
                        var11_11 = var3_3;
                    } else {
                        var11_11 = var3_3 + var5_5;
                    }
                }
            }
            var7_7 += var6_6;
            var3_3 = var11_11;
        }
        return var3_3;
    }

    private void ensureHighStart(int n) {
        int n2 = this.highStart;
        if (n >= n2) {
            int n3 = n + 512 & -512;
            int n4 = n3 >> 4;
            n = n2 >>= 4;
            if (n4 > this.index.length) {
                int[] arrn = new int[69632];
                for (n = 0; n < n2; ++n) {
                    arrn[n] = this.index[n];
                }
                this.index = arrn;
                n = n2;
            }
            do {
                this.flags[n] = (byte)(false ? 1 : 0);
                this.index[n] = this.initialValue;
                n = n2 = n + 1;
            } while (n2 < n4);
            this.highStart = n3;
        }
    }

    private static boolean equalBlocks(char[] arrc, int n, char[] arrc2, int n2, int n3) {
        while (n3 > 0 && arrc[n] == arrc2[n2]) {
            ++n;
            ++n2;
            --n3;
        }
        boolean bl = n3 == 0;
        return bl;
    }

    private static boolean equalBlocks(char[] arrc, int n, int[] arrn, int n2, int n3) {
        while (n3 > 0 && arrc[n] == arrn[n2]) {
            ++n;
            ++n2;
            --n3;
        }
        boolean bl = n3 == 0;
        return bl;
    }

    private static boolean equalBlocks(int[] arrn, int n, int[] arrn2, int n2, int n3) {
        while (n3 > 0 && arrn[n] == arrn2[n2]) {
            ++n;
            ++n2;
            --n3;
        }
        boolean bl = n3 == 0;
        return bl;
    }

    private void fillBlock(int n, int n2, int n3, int n4) {
        Arrays.fill(this.data, n + n2, n + n3, n4);
    }

    private static int findAllSameBlock(int[] arrn, int n, int n2, int n3, int n4) {
        while (n <= n2 - n4) {
            int n5 = n;
            if (arrn[n] == n3) {
                n5 = 1;
                do {
                    if (n5 == n4) {
                        return n;
                    }
                    if (arrn[n + n5] != n3) {
                        n5 = n + n5;
                        break;
                    }
                    ++n5;
                } while (true);
            }
            n = n5 + 1;
        }
        return -1;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private int findHighStart() {
        var1_1 = this.highStart >> 4;
        block0 : do {
            block8 : {
                var2_2 = 0;
                if (var1_1 <= 0) return 0;
                var3_3 = this.flags;
                var4_4 = var1_1 - 1;
                if (var3_3[var4_4] != 0) break block8;
                var1_1 = var2_2;
                if (this.index[var4_4] == this.highValue) {
                    var1_1 = 1;
                }
                ** GOTO lbl22
            }
            var2_2 = this.index[var4_4];
            var1_1 = 0;
            do {
                block11 : {
                    block10 : {
                        block9 : {
                            if (var1_1 != 16) break block9;
                            var1_1 = 1;
                            break block10;
                        }
                        if (this.data[var2_2 + var1_1] == this.highValue) break block11;
                        var1_1 = 0;
                    }
                    if (var1_1 == 0) {
                        return var4_4 + 1 << 4;
                    }
                    var1_1 = var4_4;
                    continue block0;
                }
                ++var1_1;
            } while (true);
            break;
        } while (true);
    }

    private static int findSameBlock(char[] arrc, int n, int n2, char[] arrc2, int n3, int n4) {
        while (n <= n2 - n4) {
            if (MutableCodePointTrie.equalBlocks(arrc, n, arrc2, n3, n4)) {
                return n;
            }
            ++n;
        }
        return -1;
    }

    public static MutableCodePointTrie fromCodePointMap(CodePointMap codePointMap) {
        int n = codePointMap.get(-1);
        int n2 = codePointMap.get(1114111);
        MutableCodePointTrie mutableCodePointTrie = new MutableCodePointTrie(n2, n);
        CodePointMap.Range range = new CodePointMap.Range();
        n = 0;
        while (codePointMap.getRange(n, null, range)) {
            int n3 = range.getEnd();
            int n4 = range.getValue();
            if (n4 != n2) {
                if (n == n3) {
                    mutableCodePointTrie.set(n, n4);
                } else {
                    mutableCodePointTrie.setRange(n, n3, n4);
                }
            }
            n = n3 + 1;
        }
        return mutableCodePointTrie;
    }

    private static int getAllSameOverlap(int[] arrn, int n, int n2, int n3) {
        int n4;
        for (n4 = n; n - (n3 - 1) < n4 && arrn[n4 - 1] == n2; --n4) {
        }
        return n - n4;
    }

    private int getDataBlock(int n) {
        if (this.flags[n] == 1) {
            return this.index[n];
        }
        if (n < 4096) {
            int[] arrn;
            int n2;
            int n3 = this.allocDataBlock(64);
            int n4 = n2 = n & -4;
            do {
                int n5 = n4;
                this.writeBlock(n3, this.index[n5]);
                this.flags[n5] = (byte)(true ? 1 : 0);
                arrn = this.index;
                n4 = n5 + 1;
                arrn[n5] = n3;
                n3 += 16;
            } while (n4 < n2 + 4);
            return arrn[n];
        }
        int n6 = this.allocDataBlock(16);
        if (n6 < 0) {
            return n6;
        }
        this.writeBlock(n6, this.index[n]);
        this.flags[n] = (byte)(true ? 1 : 0);
        this.index[n] = n6;
        return n6;
    }

    private static int getOverlap(char[] arrc, int n, char[] arrc2, int n2, int n3) {
        --n3;
        while (n3 > 0 && !MutableCodePointTrie.equalBlocks(arrc, n - n3, arrc2, n2, n3)) {
            --n3;
        }
        return n3;
    }

    private static int getOverlap(char[] arrc, int n, int[] arrn, int n2, int n3) {
        --n3;
        while (n3 > 0 && !MutableCodePointTrie.equalBlocks(arrc, n - n3, arrn, n2, n3)) {
            --n3;
        }
        return n3;
    }

    private static int getOverlap(int[] arrn, int n, int[] arrn2, int n2, int n3) {
        --n3;
        while (n3 > 0 && !MutableCodePointTrie.equalBlocks(arrn, n - n3, arrn2, n2, n3)) {
            --n3;
        }
        return n3;
    }

    private static boolean isStartOfSomeFastBlock(int n, int[] arrn, int n2) {
        for (int i = 0; i < n2; i += 4) {
            if (arrn[i] != n) continue;
            return true;
        }
        return false;
    }

    private void maskValues(int n) {
        int[] arrn;
        int n2;
        this.initialValue &= n;
        this.errorValue &= n;
        this.highValue &= n;
        int n3 = this.highStart;
        for (n2 = 0; n2 < n3 >> 4; ++n2) {
            if (this.flags[n2] != 0) continue;
            arrn = this.index;
            arrn[n2] = arrn[n2] & n;
        }
        for (n2 = 0; n2 < this.dataLength; ++n2) {
            arrn = this.data;
            arrn[n2] = arrn[n2] & n;
        }
    }

    private static final int maybeFilterValue(int n, int n2, int n3, CodePointMap.ValueFilter valueFilter) {
        if (n == n2) {
            n2 = n3;
        } else {
            n2 = n;
            if (valueFilter != null) {
                n2 = valueFilter.apply(n);
            }
        }
        return n2;
    }

    private void writeBlock(int n, int n2) {
        Arrays.fill(this.data, n, n + 16, n2);
    }

    public CodePointTrie buildImmutable(CodePointTrie.Type object, CodePointTrie.ValueWidth valueWidth) {
        if (object != null && valueWidth != null) {
            try {
                object = this.build((CodePointTrie.Type)((Object)object), valueWidth);
                return object;
            }
            finally {
                this.clear();
            }
        }
        throw new IllegalArgumentException("The type and valueWidth must be specified.");
    }

    public MutableCodePointTrie clone() {
        MutableCodePointTrie mutableCodePointTrie = (MutableCodePointTrie)Object.super.clone();
        int n = this.highStart <= 65536 ? 4096 : 69632;
        mutableCodePointTrie.index = new int[n];
        mutableCodePointTrie.flags = new byte[69632];
        int n2 = this.highStart;
        for (n = 0; n < n2 >> 4; ++n) {
            mutableCodePointTrie.index[n] = this.index[n];
            mutableCodePointTrie.flags[n] = this.flags[n];
            continue;
        }
        try {
            mutableCodePointTrie.index3NullOffset = this.index3NullOffset;
            mutableCodePointTrie.data = (int[])this.data.clone();
            mutableCodePointTrie.dataLength = this.dataLength;
            mutableCodePointTrie.dataNullOffset = this.dataNullOffset;
            mutableCodePointTrie.origInitialValue = this.origInitialValue;
            mutableCodePointTrie.initialValue = this.initialValue;
            mutableCodePointTrie.errorValue = this.errorValue;
            mutableCodePointTrie.highStart = this.highStart;
            mutableCodePointTrie.highValue = this.highValue;
            return mutableCodePointTrie;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            return null;
        }
    }

    @Override
    public int get(int n) {
        if (n >= 0 && 1114111 >= n) {
            if (n >= this.highStart) {
                return this.highValue;
            }
            int n2 = n >> 4;
            if (this.flags[n2] == 0) {
                return this.index[n2];
            }
            return this.data[this.index[n2] + (n & 15)];
        }
        return this.errorValue;
    }

    /*
     * Exception decompiling
     */
    @Override
    public boolean getRange(int var1_1, CodePointMap.ValueFilter var2_2, CodePointMap.Range var3_3) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    public void set(int n, int n2) {
        if (n >= 0 && 1114111 >= n) {
            this.ensureHighStart(n);
            int n3 = this.getDataBlock(n >> 4);
            this.data[(n & 15) + n3] = n2;
            return;
        }
        throw new IllegalArgumentException("invalid code point");
    }

    public void setRange(int n, int n2, int n3) {
        if (n >= 0 && 1114111 >= n && n2 >= 0 && 1114111 >= n2 && n <= n2) {
            int n4;
            this.ensureHighStart(n2);
            int n5 = n2 + 1;
            n2 = n;
            if ((n & 15) != 0) {
                n4 = this.getDataBlock(n >> 4);
                n2 = n + 15 & -16;
                if (n2 <= n5) {
                    this.fillBlock(n4, n & 15, 16, n3);
                } else {
                    this.fillBlock(n4, n & 15, n5 & 15, n3);
                    return;
                }
            }
            n = n5 & 15;
            while (n2 < (n5 & -16)) {
                n4 = n2 >> 4;
                if (this.flags[n4] == 0) {
                    this.index[n4] = n3;
                } else {
                    this.fillBlock(this.index[n4], 0, 16, n3);
                }
                n2 += 16;
            }
            if (n > 0) {
                this.fillBlock(this.getDataBlock(n2 >> 4), 0, n, n3);
            }
            return;
        }
        throw new IllegalArgumentException("invalid code point range");
    }

    private static final class AllSameBlocks {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final int CAPACITY = 32;
        static final int NEW_UNIQUE = -1;
        static final int OVERFLOW = -2;
        private int[] indexes = new int[32];
        private int length;
        private int mostRecent = -1;
        private int[] refCounts = new int[32];
        private int[] values = new int[32];

        AllSameBlocks() {
        }

        void add(int n, int n2, int n3) {
            int n4 = -1;
            int n5 = 69632;
            for (int i = 0; i < this.length; ++i) {
                int[] arrn = this.refCounts;
                int n6 = n5;
                if (arrn[i] < n5) {
                    n4 = i;
                    n6 = arrn[i];
                }
                n5 = n6;
            }
            this.mostRecent = n4;
            this.indexes[n4] = n;
            this.values[n4] = n3;
            this.refCounts[n4] = n2;
        }

        int findMostUsed() {
            if (this.length == 0) {
                return -1;
            }
            int n = -1;
            int n2 = 0;
            for (int i = 0; i < this.length; ++i) {
                int[] arrn = this.refCounts;
                int n3 = n2;
                if (arrn[i] > n2) {
                    n = i;
                    n3 = arrn[i];
                }
                n2 = n3;
            }
            return this.indexes[n];
        }

        int findOrAdd(int n, int n2, int n3) {
            int n4;
            int n5 = this.mostRecent;
            if (n5 >= 0 && this.values[n5] == n3) {
                int[] arrn = this.refCounts;
                arrn[n5] = arrn[n5] + n2;
                return this.indexes[n5];
            }
            for (n5 = 0; n5 < (n4 = this.length); ++n5) {
                if (this.values[n5] != n3) continue;
                this.mostRecent = n5;
                int[] arrn = this.refCounts;
                arrn[n5] = arrn[n5] + n2;
                return this.indexes[n5];
            }
            if (n4 == 32) {
                return -2;
            }
            this.mostRecent = n4;
            this.indexes[n4] = n;
            this.values[n4] = n3;
            int[] arrn = this.refCounts;
            this.length = n4 + 1;
            arrn[n4] = n2;
            return -1;
        }
    }

    private static final class MixedBlocks {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private int blockLength;
        private int length;
        private int mask;
        private int shift;
        private int[] table;

        private MixedBlocks() {
        }

        private void addEntry(int[] arrn, char[] arrc, int n, int n2, int n3) {
            if ((n = this.findEntry(arrn, arrc, arrn, arrc, n, n2)) < 0) {
                this.table[n] = n2 << this.shift | n3 + 1;
            }
        }

        private int findEntry(int[] arrn, int n, int n2) {
            int n3;
            int n4 = this.shift;
            int n5 = n3 = this.modulo(n2, this.length - 1) + 1;
            int n6;
            while ((n6 = this.table[n5]) != 0) {
                int n7 = this.mask;
                if ((n7 & n6) == n2 << n4 && MutableCodePointTrie.allValuesSameAs(arrn, (n7 & n6) - 1, this.blockLength, n)) {
                    return n5;
                }
                n5 = this.nextIndex(n3, n5);
            }
            return n5;
        }

        private int findEntry(int[] arrn, char[] arrc, int[] arrn2, char[] arrc2, int n, int n2) {
            int n3;
            int n4 = this.shift;
            int n5 = n3 = this.modulo(n2, this.length - 1) + 1;
            int n6;
            while ((n6 = this.table[n5]) != 0) {
                int n7 = this.mask;
                if ((n7 & n6) == n2 << n4) {
                    n7 = (n7 & n6) - 1;
                    if (arrn != null ? MutableCodePointTrie.equalBlocks(arrn, n7, arrn2, n, this.blockLength) : (arrn2 != null ? MutableCodePointTrie.equalBlocks(arrc, n7, arrn2, n, this.blockLength) : MutableCodePointTrie.equalBlocks(arrc, n7, arrc2, n, this.blockLength))) {
                        return n5;
                    }
                }
                n5 = this.nextIndex(n3, n5);
            }
            return n5;
        }

        private int makeHashCode(int n) {
            int n2 = n;
            for (int i = 1; i < this.blockLength; ++i) {
                n2 = n2 * 37 + n;
            }
            return n2;
        }

        private int makeHashCode(char[] arrc, int n) {
            int n2 = this.blockLength;
            int n3 = n + 1;
            int n4 = arrc[n];
            do {
                int n5 = n3 + 1;
                n4 = n4 * 37 + arrc[n3];
                if (n5 >= n2 + n) {
                    return n4;
                }
                n3 = n5;
            } while (true);
        }

        private int makeHashCode(int[] arrn, int n) {
            int n2 = this.blockLength;
            int n3 = n + 1;
            int n4 = arrn[n];
            do {
                int n5 = n3 + 1;
                n4 = n4 * 37 + arrn[n3];
                if (n5 >= n2 + n) {
                    return n4;
                }
                n3 = n5;
            } while (true);
        }

        private int modulo(int n, int n2) {
            int n3;
            n = n3 = n % n2;
            if (n3 < 0) {
                n = n3 + n2;
            }
            return n;
        }

        private int nextIndex(int n, int n2) {
            return (n2 + n) % this.length;
        }

        void extend(char[] arrc, int n, int n2, int n3) {
            if ((n2 -= this.blockLength) >= n) {
                n = n2 + 1;
            }
            n2 = this.blockLength;
            while (n <= n3 - n2) {
                this.addEntry(null, arrc, n, this.makeHashCode(arrc, n), n);
                ++n;
            }
        }

        void extend(int[] arrn, int n, int n2, int n3) {
            if ((n2 -= this.blockLength) >= n) {
                n = n2 + 1;
            }
            n2 = this.blockLength;
            while (n <= n3 - n2) {
                this.addEntry(arrn, null, n, this.makeHashCode(arrn, n), n);
                ++n;
            }
        }

        int findAllSameBlock(int[] arrn, int n) {
            if ((n = this.findEntry(arrn, n, this.makeHashCode(n))) >= 0) {
                return (this.table[n] & this.mask) - 1;
            }
            return -1;
        }

        int findBlock(char[] arrc, char[] arrc2, int n) {
            if ((n = this.findEntry(null, arrc, null, arrc2, n, this.makeHashCode(arrc2, n))) >= 0) {
                return (this.table[n] & this.mask) - 1;
            }
            return -1;
        }

        int findBlock(char[] arrc, int[] arrn, int n) {
            if ((n = this.findEntry(null, arrc, arrn, null, n, this.makeHashCode(arrn, n))) >= 0) {
                return (this.table[n] & this.mask) - 1;
            }
            return -1;
        }

        int findBlock(int[] arrn, int[] arrn2, int n) {
            if ((n = this.findEntry(arrn, null, arrn2, null, n, this.makeHashCode(arrn2, n))) >= 0) {
                return (this.table[n] & this.mask) - 1;
            }
            return -1;
        }

        void init(int n, int n2) {
            if ((n = n - n2 + 1) <= 4095) {
                n = 6007;
                this.shift = 12;
                this.mask = 4095;
            } else if (n <= 32767) {
                n = 50021;
                this.shift = 15;
                this.mask = 32767;
            } else if (n <= 131071) {
                n = 200003;
                this.shift = 17;
                this.mask = 131071;
            } else {
                n = 1500007;
                this.shift = 21;
                this.mask = 2097151;
            }
            int[] arrn = this.table;
            if (arrn != null && n <= arrn.length) {
                Arrays.fill(arrn, 0, n, 0);
            } else {
                this.table = new int[n];
            }
            this.length = n;
            this.blockLength = n2;
        }
    }

}

