/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.coll;

import android.icu.impl.coll.Collation;
import android.icu.impl.coll.CollationData;
import android.icu.impl.coll.CollationFastLatin;
import android.icu.impl.coll.UVector64;
import android.icu.util.CharsTrie;

final class CollationFastLatinBuilder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long CONTRACTION_FLAG = 0x80000000L;
    private static final int NUM_SPECIAL_GROUPS = 4;
    private long ce0 = 0L;
    private long ce1 = 0L;
    private long[][] charCEs = new long[448][2];
    private UVector64 contractionCEs = new UVector64();
    private long firstDigitPrimary = 0L;
    private long firstLatinPrimary = 0L;
    private long firstShortPrimary = 0L;
    private int headerLength = 0;
    private long lastLatinPrimary = 0L;
    long[] lastSpecialPrimaries = new long[4];
    private char[] miniCEs = null;
    private StringBuilder result = new StringBuilder();
    private boolean shortPrimaryOverflow = false;
    private UVector64 uniqueCEs = new UVector64();

    CollationFastLatinBuilder() {
    }

    private void addContractionEntry(int n, long l, long l2) {
        this.contractionCEs.addElement(n);
        this.contractionCEs.addElement(l);
        this.contractionCEs.addElement(l2);
        this.addUniqueCE(l);
        this.addUniqueCE(l2);
    }

    private void addUniqueCE(long l) {
        if (l != 0L && l >>> 32 != 1L) {
            int n = CollationFastLatinBuilder.binarySearch(this.uniqueCEs.getBuffer(), this.uniqueCEs.size(), l &= -49153L);
            if (n < 0) {
                this.uniqueCEs.insertElementAt(l, n);
            }
            return;
        }
    }

    private static final int binarySearch(long[] arrl, int n, long l) {
        if (n == 0) {
            return -1;
        }
        int n2 = 0;
        int n3 = n;
        n = n2;
        int n4;
        while ((n4 = CollationFastLatinBuilder.compareInt64AsUnsigned(l, arrl[n2 = (int)(((long)n + (long)n3) / 2L)])) != 0) {
            if (n4 < 0) {
                if (n2 == n) {
                    return n;
                }
                n3 = n2;
                continue;
            }
            if (n2 == n) {
                return n + 1;
            }
            n = n2;
        }
        return n2;
    }

    private static final int compareInt64AsUnsigned(long l, long l2) {
        if ((l -= Long.MIN_VALUE) < (l2 -= Long.MIN_VALUE)) {
            return -1;
        }
        return l > l2;
    }

    private void encodeCharCEs() {
        int n;
        int n2 = this.result.length();
        for (n = 0; n < 448; ++n) {
            this.result.append(0);
        }
        int n3 = this.result.length();
        for (int i = 0; i < 448; ++i) {
            int n4;
            long l = this.charCEs[i][0];
            if (CollationFastLatinBuilder.isContractionCharCE(l)) continue;
            n = n4 = this.encodeTwoCEs(l, this.charCEs[i][1]);
            if (n4 >>> 16 > 0) {
                n = this.result.length() - n3;
                if (n > 1023) {
                    n = 1;
                } else {
                    StringBuilder stringBuilder = this.result;
                    stringBuilder.append((char)(n4 >> 16));
                    stringBuilder.append((char)n4);
                    n |= 2048;
                }
            }
            this.result.setCharAt(n2 + i, (char)n);
        }
    }

    private void encodeContractions() {
        int n = this.headerLength;
        int n2 = 448;
        int n3 = this.result.length();
        block0 : for (int i = 0; i < n2; ++i) {
            long l = this.charCEs[i][0];
            if (!CollationFastLatinBuilder.isContractionCharCE(l)) continue;
            int n4 = this.result.length() - (n + 448);
            if (n4 > 1023) {
                this.result.setCharAt(this.headerLength + i, '\u0001');
                continue;
            }
            boolean bl = true;
            int n5 = (int)l & Integer.MAX_VALUE;
            do {
                long l2;
                if ((l2 = this.contractionCEs.elementAti(n5)) == 511L && !bl) {
                    this.result.setCharAt(this.headerLength + i, (char)(n4 | 1024));
                    continue block0;
                }
                n2 = this.encodeTwoCEs(this.contractionCEs.elementAti(n5 + 1), this.contractionCEs.elementAti(n5 + 2));
                if (n2 == 1) {
                    this.result.append((char)(l2 | 512L));
                } else if (n2 >>> 16 == 0) {
                    this.result.append((char)(1024L | l2));
                    this.result.append((char)n2);
                } else {
                    this.result.append((char)(1536L | l2));
                    StringBuilder stringBuilder = this.result;
                    stringBuilder.append((char)(n2 >> 16));
                    stringBuilder.append((char)n2);
                }
                bl = false;
                n5 += 3;
                n2 = 448;
            } while (true);
        }
        if (this.result.length() > n3) {
            this.result.append('\u01ff');
        }
    }

    private int encodeTwoCEs(long l, long l2) {
        int n;
        int n2;
        block11 : {
            int n3;
            int n4;
            block10 : {
                if (l == 0L) {
                    return 0;
                }
                if (l == 0x101000100L) {
                    return 1;
                }
                n = this.getMiniCE(l);
                if (n == 1) {
                    return n;
                }
                n2 = n;
                if (n >= 4096) {
                    n2 = n | (((int)l & 49152) >> 11) + 8;
                }
                if (l2 == 0L) {
                    return n2;
                }
                n3 = this.getMiniCE(l2);
                if (n3 == 1) {
                    return n3;
                }
                n4 = (int)l2 & 49152;
                if (n2 >= 4096 && (n2 & 992) == 160 && (n = n3 & 992) >= 384 && n4 == 0 && (n3 & 7) == 0) {
                    return n2 & -993 | n;
                }
                if (n3 <= 992) break block10;
                n = n3;
                if (4096 > n3) break block11;
            }
            n = n3 | (n4 >> 11) + 8;
        }
        return n2 << 16 | n;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void encodeUniqueCEs() {
        this.miniCEs = new char[this.uniqueCEs.size()];
        var1_1 = 0;
        var2_2 = this.lastSpecialPrimaries[0];
        var4_3 = 0L;
        var6_4 = 0;
        var7_5 = 0;
        var8_6 = 0;
        var9_7 = 0;
        var10_8 = 0;
        while (var10_8 < this.uniqueCEs.size()) {
            block13 : {
                block14 : {
                    block24 : {
                        block17 : {
                            block23 : {
                                block22 : {
                                    block19 : {
                                        block21 : {
                                            block20 : {
                                                block15 : {
                                                    block18 : {
                                                        block16 : {
                                                            block5 : {
                                                                block10 : {
                                                                    block12 : {
                                                                        block8 : {
                                                                            block11 : {
                                                                                block6 : {
                                                                                    block9 : {
                                                                                        block7 : {
                                                                                            var11_9 = this.uniqueCEs.elementAti(var10_8);
                                                                                            var13_10 = var11_9 >>> 32;
                                                                                            if (var13_10 == var4_3) break block5;
                                                                                            while (var13_10 > var2_2) {
                                                                                                this.result.setCharAt(var1_1 + 1, (char)(var7_5 ? 1 : 0));
                                                                                                if (++var1_1 < 4) {
                                                                                                    var2_2 = this.lastSpecialPrimaries[var1_1];
                                                                                                    continue;
                                                                                                }
                                                                                                var2_2 = 0xFFFFFFFFL;
                                                                                                break;
                                                                                            }
                                                                                            if (var13_10 >= this.firstShortPrimary) break block6;
                                                                                            if (var7_5 != 0) break block7;
                                                                                            var15_11 = 3072;
                                                                                            break block8;
                                                                                        }
                                                                                        if (var7_5 >= 4088) break block9;
                                                                                        var15_11 = var7_5 + 8;
                                                                                        break block8;
                                                                                    }
                                                                                    this.miniCEs[var10_8] = (char)(true ? 1 : 0);
                                                                                    break block10;
                                                                                }
                                                                                if (var7_5 >= 4096) break block11;
                                                                                var15_11 = 4096;
                                                                                break block8;
                                                                            }
                                                                            if (var7_5 >= 63488) break block12;
                                                                            var15_11 = var7_5 + 1024;
                                                                        }
                                                                        var4_3 = var13_10;
                                                                        var6_4 = 1280;
                                                                        var8_6 = 160;
                                                                        var9_7 = 0;
                                                                        var7_5 = var15_11;
                                                                        break block5;
                                                                    }
                                                                    this.shortPrimaryOverflow = true;
                                                                    this.miniCEs[var10_8] = (char)(true ? 1 : 0);
                                                                }
                                                                var16_12 = var6_4;
                                                                var15_11 = var8_6;
                                                                break block13;
                                                            }
                                                            var17_13 = (int)var11_9;
                                                            var18_14 = var17_13 >>> 16;
                                                            var16_12 = var6_4;
                                                            var15_11 = var8_6;
                                                            var19_15 = var9_7;
                                                            if (var18_14 == var6_4) break block14;
                                                            if (var7_5 != 0) break block15;
                                                            if (var8_6 != 0) break block16;
                                                            var15_11 = 384;
                                                            break block17;
                                                        }
                                                        if (var8_6 >= 992) break block18;
                                                        var15_11 = var8_6 + 32;
                                                        break block17;
                                                    }
                                                    this.miniCEs[var10_8] = (char)(true ? 1 : 0);
                                                    var16_12 = var6_4;
                                                    var15_11 = var8_6;
                                                    break block13;
                                                }
                                                if (var18_14 >= 1280) break block19;
                                                if (var8_6 != 160) break block20;
                                                var15_11 = 0;
                                                break block17;
                                            }
                                            if (var8_6 >= 128) break block21;
                                            var15_11 = var8_6 + 32;
                                            break block17;
                                        }
                                        this.miniCEs[var10_8] = (char)(true ? 1 : 0);
                                        var16_12 = var6_4;
                                        var15_11 = var8_6;
                                        break block13;
                                    }
                                    if (var18_14 != 1280) break block22;
                                    var15_11 = 160;
                                    break block17;
                                }
                                if (var8_6 >= 192) break block23;
                                var15_11 = 192;
                                break block17;
                            }
                            if (var8_6 >= 352) break block24;
                            var15_11 = var8_6 + 32;
                        }
                        var16_12 = var18_14;
                        var19_15 = 0;
                        break block14;
                    }
                    this.miniCEs[var10_8] = (char)(true ? 1 : 0);
                    var16_12 = var6_4;
                    var15_11 = var8_6;
                    break block13;
                }
                var9_7 = var19_15;
                if ((var17_13 & 16191) <= 1280) ** GOTO lbl115
                if (var19_15 >= 7) {
                    this.miniCEs[var10_8] = (char)(true ? 1 : 0);
                    var9_7 = var19_15;
                } else {
                    var9_7 = var19_15 + 1;
lbl115: // 2 sources:
                    this.miniCEs[var10_8] = 3072 <= var7_5 && var7_5 <= 4088 ? (char)((char)(var7_5 | var9_7)) : (char)((char)(var7_5 | var15_11 | var9_7));
                }
            }
            ++var10_8;
            var6_4 = var16_12;
            var8_6 = var15_11;
        }
    }

    private void getCEs(CollationData arrl) {
        int n = 0;
        int n2 = 0;
        do {
            long[] arrl2;
            long[][] arrl3;
            if (n == 384) {
                n = 8192;
            } else if (n == 8256) {
                this.contractionCEs.addElement(511L);
                return;
            }
            int n3 = arrl.getCE32(n);
            if (n3 == 192) {
                arrl2 = arrl.base;
                n3 = arrl2.getCE32(n);
            } else {
                arrl2 = arrl;
            }
            if (this.getCEsFromCE32((CollationData)arrl2, n, n3)) {
                long l;
                arrl3 = this.charCEs;
                arrl2 = arrl3[n2];
                arrl2[0] = l = this.ce0;
                arrl3[n2][1] = this.ce1;
                this.addUniqueCE(l);
                this.addUniqueCE(this.ce1);
            } else {
                arrl3 = this.charCEs;
                arrl2 = arrl3[n2];
                this.ce0 = 0x101000100L;
                arrl2[0] = 0x101000100L;
                arrl2 = arrl3[n2];
                this.ce1 = 0L;
                arrl2[1] = 0L;
            }
            if (n == 0 && !CollationFastLatinBuilder.isContractionCharCE(this.ce0)) {
                this.addContractionEntry(511, this.ce0, this.ce1);
                arrl2 = this.charCEs;
                arrl2[0][0] = 6442450944L;
                arrl2[0][1] = 0L;
            }
            ++n2;
            n = (char)(n + 1);
        } while (true);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean getCEsFromCE32(CollationData collationData, int n, int n2) {
        n2 = collationData.getFinalCE32(n2);
        this.ce1 = 0L;
        boolean bl = Collation.isSimpleOrLongCE32(n2);
        boolean bl2 = false;
        if (bl) {
            this.ce0 = Collation.ceFromCE32(n2);
        } else {
            int n3 = Collation.tagFromCE32(n2);
            if (n3 != 4) {
                if (n3 != 5) {
                    if (n3 != 6) {
                        if (n3 == 9) return this.getCEsFromContractionCE32(collationData, n2);
                        if (n3 != 14) {
                            return false;
                        }
                        this.ce0 = collationData.getCEFromOffsetCE32(n, n2);
                    } else {
                        n = Collation.indexFromCE32(n2);
                        if ((n2 = Collation.lengthFromCE32(n2)) > 2) return false;
                        this.ce0 = collationData.ces[n];
                        if (n2 == 2) {
                            this.ce1 = collationData.ces[n + 1];
                        }
                    }
                } else {
                    n = Collation.indexFromCE32(n2);
                    if ((n2 = Collation.lengthFromCE32(n2)) > 2) return false;
                    this.ce0 = Collation.ceFromCE32(collationData.ce32s[n]);
                    if (n2 == 2) {
                        this.ce1 = Collation.ceFromCE32(collationData.ce32s[n + 1]);
                    }
                }
            } else {
                this.ce0 = Collation.latinCE0FromCE32(n2);
                this.ce1 = Collation.latinCE1FromCE32(n2);
            }
        }
        long l = this.ce0;
        if (l == 0L) {
            if (this.ce1 != 0L) return bl2;
            return true;
        }
        long l2 = l >>> 32;
        if (l2 == 0L) {
            return false;
        }
        if (l2 > this.lastLatinPrimary) {
            return false;
        }
        n2 = (int)l;
        if (l2 < this.firstShortPrimary && (n2 & -16384) != 83886080) {
            return false;
        }
        if ((n2 & 16191) < 1280) {
            return false;
        }
        l = this.ce1;
        if (l != 0L) {
            if ((l >>>= 32) == 0L ? l2 < this.firstShortPrimary : !this.inSameGroup(l2, l)) {
                return false;
            }
            n = (int)this.ce1;
            if (n >>> 16 == 0) {
                return false;
            }
            if (l != 0L && l < this.firstShortPrimary && (n & -16384) != 83886080) {
                return false;
            }
            if ((n2 & 16191) < 1280) {
                return false;
            }
        }
        if (((this.ce0 | this.ce1) & 192L) == 0L) return true;
        return false;
    }

    private boolean getCEsFromContractionCE32(CollationData collationData, int n) {
        n = Collation.indexFromCE32(n);
        int n2 = collationData.getCE32FromContexts(n);
        int n3 = this.contractionCEs.size();
        if (this.getCEsFromCE32(collationData, -1, n2)) {
            this.addContractionEntry(511, this.ce0, this.ce1);
        } else {
            this.addContractionEntry(511, 0x101000100L, 0L);
        }
        CharsTrie.Iterator iterator = CharsTrie.iterator(collationData.contexts, n + 2, 0);
        n2 = -1;
        n = 0;
        while (iterator.hasNext()) {
            CharsTrie.Entry entry = iterator.next();
            CharSequence charSequence = entry.chars;
            int n4 = CollationFastLatin.getCharIndex(charSequence.charAt(0));
            if (n4 < 0) continue;
            if (n4 == n2) {
                if (n == 0) continue;
                this.addContractionEntry(n4, 0x101000100L, 0L);
                n = 0;
                continue;
            }
            if (n != 0) {
                this.addContractionEntry(n2, this.ce0, this.ce1);
            }
            n = entry.value;
            if (charSequence.length() == 1 && this.getCEsFromCE32(collationData, -1, n)) {
                n = 1;
            } else {
                this.addContractionEntry(n4, 0x101000100L, 0L);
                n = 0;
            }
            n2 = n4;
        }
        if (n != 0) {
            this.addContractionEntry(n2, this.ce0, this.ce1);
        }
        this.ce0 = 6442450944L | (long)n3;
        this.ce1 = 0L;
        return true;
    }

    private int getMiniCE(long l) {
        int n = CollationFastLatinBuilder.binarySearch(this.uniqueCEs.getBuffer(), this.uniqueCEs.size(), l & -49153L);
        return this.miniCEs[n];
    }

    private boolean inSameGroup(long l, long l2) {
        long l3 = this.firstShortPrimary;
        boolean bl = true;
        boolean bl2 = true;
        boolean bl3 = true;
        if (l >= l3) {
            if (l2 < l3) {
                bl3 = false;
            }
            return bl3;
        }
        if (l2 >= l3) {
            return false;
        }
        l3 = this.lastSpecialPrimaries[3];
        if (l > l3) {
            bl3 = l2 > l3 ? bl : false;
            return bl3;
        }
        if (l2 > l3) {
            return false;
        }
        int n = 0;
        do {
            if (l <= (l3 = this.lastSpecialPrimaries[n])) {
                bl3 = l2 <= l3 ? bl2 : false;
                return bl3;
            }
            if (l2 <= l3) {
                return false;
            }
            ++n;
        } while (true);
    }

    private static boolean isContractionCharCE(long l) {
        boolean bl = l >>> 32 == 1L && l != 0x101000100L;
        return bl;
    }

    private boolean loadGroups(CollationData collationData) {
        int n = this.headerLength = 5;
        this.result.append((char)(n | 512));
        for (n = 0; n < 4; ++n) {
            this.lastSpecialPrimaries[n] = collationData.getLastPrimaryForGroup(n + 4096);
            if (this.lastSpecialPrimaries[n] == 0L) {
                return false;
            }
            this.result.append(0);
        }
        this.firstDigitPrimary = collationData.getFirstPrimaryForGroup(4100);
        this.firstLatinPrimary = collationData.getFirstPrimaryForGroup(25);
        this.lastLatinPrimary = collationData.getLastPrimaryForGroup(25);
        return this.firstDigitPrimary != 0L && this.firstLatinPrimary != 0L;
        {
        }
    }

    private void resetCEs() {
        this.contractionCEs.removeAllElements();
        this.uniqueCEs.removeAllElements();
        this.shortPrimaryOverflow = false;
        this.result.setLength(this.headerLength);
    }

    boolean forData(CollationData collationData) {
        if (this.result.length() == 0) {
            boolean bl;
            if (!this.loadGroups(collationData)) {
                return false;
            }
            this.firstShortPrimary = this.firstDigitPrimary;
            this.getCEs(collationData);
            this.encodeUniqueCEs();
            if (this.shortPrimaryOverflow) {
                this.firstShortPrimary = this.firstLatinPrimary;
                this.resetCEs();
                this.getCEs(collationData);
                this.encodeUniqueCEs();
            }
            if (bl = this.shortPrimaryOverflow ^ true) {
                this.encodeCharCEs();
                this.encodeContractions();
            }
            this.contractionCEs.removeAllElements();
            this.uniqueCEs.removeAllElements();
            return bl;
        }
        throw new IllegalStateException("attempt to reuse a CollationFastLatinBuilder");
    }

    char[] getHeader() {
        int n = this.headerLength;
        char[] arrc = new char[n];
        this.result.getChars(0, n, arrc, 0);
        return arrc;
    }

    char[] getTable() {
        int n = this.result.length();
        int n2 = this.headerLength;
        char[] arrc = new char[n - n2];
        StringBuilder stringBuilder = this.result;
        stringBuilder.getChars(n2, stringBuilder.length(), arrc, 0);
        return arrc;
    }
}

