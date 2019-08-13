/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.ICUBinary;
import android.icu.text.UTF16;
import android.icu.text.UnicodeSet;
import android.icu.util.CodePointMap;
import android.icu.util.CodePointTrie;
import android.icu.util.ICUUncheckedIOException;
import android.icu.util.MutableCodePointTrie;
import android.icu.util.VersionInfo;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public final class Normalizer2Impl {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int CANON_HAS_COMPOSITIONS = 1073741824;
    private static final int CANON_HAS_SET = 2097152;
    private static final int CANON_NOT_SEGMENT_STARTER = Integer.MIN_VALUE;
    private static final int CANON_VALUE_MASK = 2097151;
    public static final int COMP_1_LAST_TUPLE = 32768;
    public static final int COMP_1_TRAIL_LIMIT = 13312;
    public static final int COMP_1_TRAIL_MASK = 32766;
    public static final int COMP_1_TRAIL_SHIFT = 9;
    public static final int COMP_1_TRIPLE = 1;
    public static final int COMP_2_TRAIL_MASK = 65472;
    public static final int COMP_2_TRAIL_SHIFT = 6;
    private static final int DATA_FORMAT = 1316121906;
    public static final int DELTA_SHIFT = 3;
    public static final int DELTA_TCCC_0 = 0;
    public static final int DELTA_TCCC_1 = 2;
    public static final int DELTA_TCCC_GT_1 = 4;
    public static final int DELTA_TCCC_MASK = 6;
    public static final int HAS_COMP_BOUNDARY_AFTER = 1;
    public static final int INERT = 1;
    private static final IsAcceptable IS_ACCEPTABLE = new IsAcceptable();
    public static final int IX_COUNT = 20;
    public static final int IX_EXTRA_DATA_OFFSET = 1;
    public static final int IX_LIMIT_NO_NO = 12;
    public static final int IX_MIN_COMP_NO_MAYBE_CP = 9;
    public static final int IX_MIN_DECOMP_NO_CP = 8;
    public static final int IX_MIN_LCCC_CP = 18;
    public static final int IX_MIN_MAYBE_YES = 13;
    public static final int IX_MIN_NO_NO = 11;
    public static final int IX_MIN_NO_NO_COMP_BOUNDARY_BEFORE = 15;
    public static final int IX_MIN_NO_NO_COMP_NO_MAYBE_CC = 16;
    public static final int IX_MIN_NO_NO_EMPTY = 17;
    public static final int IX_MIN_YES_NO = 10;
    public static final int IX_MIN_YES_NO_MAPPINGS_ONLY = 14;
    public static final int IX_NORM_TRIE_OFFSET = 0;
    public static final int IX_RESERVED3_OFFSET = 3;
    public static final int IX_SMALL_FCD_OFFSET = 2;
    public static final int IX_TOTAL_SIZE = 7;
    public static final int JAMO_L = 2;
    public static final int JAMO_VT = 65024;
    public static final int MAPPING_HAS_CCC_LCCC_WORD = 128;
    public static final int MAPPING_HAS_RAW_MAPPING = 64;
    public static final int MAPPING_LENGTH_MASK = 31;
    public static final int MAX_DELTA = 64;
    public static final int MIN_NORMAL_MAYBE_YES = 64512;
    public static final int MIN_YES_YES_WITH_CC = 65026;
    public static final int OFFSET_SHIFT = 1;
    private static final CodePointMap.ValueFilter segmentStarterMapper = new CodePointMap.ValueFilter(){

        @Override
        public int apply(int n) {
            return Integer.MIN_VALUE & n;
        }
    };
    private CodePointTrie canonIterData;
    private ArrayList<UnicodeSet> canonStartSets;
    private int centerNoNoDelta;
    private VersionInfo dataVersion;
    private String extraData;
    private int limitNoNo;
    private String maybeYesCompositions;
    private int minCompNoMaybeCP;
    private int minDecompNoCP;
    private int minLcccCP;
    private int minMaybeYes;
    private int minNoNo;
    private int minNoNoCompBoundaryBefore;
    private int minNoNoCompNoMaybeCC;
    private int minNoNoEmpty;
    private int minYesNo;
    private int minYesNoMappingsOnly;
    private CodePointTrie.Fast16 normTrie;
    private byte[] smallFCD;

    private void addComposites(int n, UnicodeSet unicodeSet) {
        char c;
        do {
            int n2;
            if (((c = this.maybeYesCompositions.charAt(n)) & '\u0001') == 0) {
                n2 = this.maybeYesCompositions.charAt(n + 1);
                n += 2;
            } else {
                n2 = (this.maybeYesCompositions.charAt(n + 1) & -65473) << 16 | this.maybeYesCompositions.charAt(n + 2);
                n += 3;
            }
            int n3 = n2 >> 1;
            if ((n2 & '\u0001') != 0) {
                this.addComposites(this.getCompositionsListForComposite(this.getRawNorm16(n3)), unicodeSet);
            }
            unicodeSet.add(n3);
        } while ((32768 & c) == 0);
    }

    private void addToStartSet(MutableCodePointTrie cloneable, int n, int n2) {
        int n3 = ((MutableCodePointTrie)cloneable).get(n2);
        if ((4194303 & n3) == 0 && n != 0) {
            ((MutableCodePointTrie)cloneable).set(n2, n3 | n);
        } else {
            if ((n3 & 2097152) == 0) {
                int n4 = n3 & 2097151;
                ((MutableCodePointTrie)cloneable).set(n2, 2097152 | -2097152 & n3 | this.canonStartSets.size());
                ArrayList<UnicodeSet> arrayList = this.canonStartSets;
                UnicodeSet unicodeSet = new UnicodeSet();
                cloneable = unicodeSet;
                arrayList.add(unicodeSet);
                if (n4 != 0) {
                    ((UnicodeSet)cloneable).add(n4);
                }
            } else {
                cloneable = this.canonStartSets.get(n3 & 2097151);
            }
            ((UnicodeSet)cloneable).add(n);
        }
    }

    private static int combine(String string, int n, int n2) {
        block9 : {
            if (n2 < 13312) {
                char c;
                while ((n2 <<= 1) > (c = string.charAt(n))) {
                    n += (c & '\u0001') + 2;
                }
                if (n2 == (c & 32766)) {
                    if ((c & '\u0001') != 0) {
                        return string.charAt(n + 1) << 16 | string.charAt(n + 2);
                    }
                    return string.charAt(n + 1);
                }
            } else {
                char c;
                int n3 = 13312 + (n2 >> 9 & -2);
                n2 = n2 << 6 & 65535;
                do {
                    char c2;
                    if (n3 > (c2 = string.charAt(n))) {
                        n += (c2 & '\u0001') + 2;
                        continue;
                    }
                    if (n3 != (c2 & 32766)) break block9;
                    c = string.charAt(n + 1);
                    if (n2 <= c) break;
                    if ((32768 & c2) == 0) {
                        n += 3;
                        continue;
                    }
                    break block9;
                    break;
                } while (true);
                if (n2 == (65472 & c)) {
                    return (-65473 & c) << 16 | string.charAt(n + 2);
                }
            }
        }
        return -1;
    }

    private void decompose(int n, int n2, ReorderingBuffer reorderingBuffer) {
        int n3 = n;
        int n4 = n2;
        if (n2 >= this.limitNoNo) {
            if (this.isMaybeOrNonZeroCC(n2)) {
                reorderingBuffer.append(n, Normalizer2Impl.getCCFromYesOrMaybe(n2));
                return;
            }
            n3 = this.mapAlgorithmic(n, n2);
            n4 = this.getRawNorm16(n3);
        }
        if (n4 < this.minYesNo) {
            reorderingBuffer.append(n3, 0);
        } else if (!this.isHangulLV(n4) && !this.isHangulLVT(n4)) {
            n2 = this.extraData.charAt(n4 >>= 1);
            n = (n2 & 128) != 0 ? this.extraData.charAt(n4 - 1) >> 8 : 0;
            reorderingBuffer.append(this.extraData, ++n4, n4 + (n2 & 31), true, n, n2 >> 8);
        } else {
            Hangul.decompose(n3, reorderingBuffer);
        }
    }

    private int decomposeShort(CharSequence charSequence, int n, int n2, boolean bl, boolean bl2, ReorderingBuffer reorderingBuffer) {
        while (n < n2) {
            int n3 = Character.codePointAt(charSequence, n);
            if (bl && n3 < this.minCompNoMaybeCP) {
                return n;
            }
            int n4 = this.getNorm16(n3);
            if (bl && this.norm16HasCompBoundaryBefore(n4)) {
                return n;
            }
            n += Character.charCount(n3);
            this.decompose(n3, n4, reorderingBuffer);
            if (!bl || !this.norm16HasCompBoundaryAfter(n4, bl2)) continue;
            return n;
        }
        return n;
    }

    private int findNextCompBoundary(CharSequence charSequence, int n, int n2, boolean bl) {
        int n3;
        block2 : {
            do {
                n3 = n;
                if (n >= n2) break block2;
                int n4 = Character.codePointAt(charSequence, n);
                if (this.hasCompBoundaryBefore(n4, n3 = this.normTrie.get(n4))) {
                    n3 = n;
                    break block2;
                }
                n += Character.charCount(n4);
            } while (!this.norm16HasCompBoundaryAfter(n3, bl));
            n3 = n;
        }
        return n3;
    }

    private int findNextFCDBoundary(CharSequence charSequence, int n, int n2) {
        int n3;
        block2 : {
            do {
                n3 = n;
                if (n >= n2) break block2;
                int n4 = Character.codePointAt(charSequence, n);
                n3 = n;
                if (n4 < this.minLcccCP) break block2;
                n3 = this.getNorm16(n4);
                if (this.norm16HasDecompBoundaryBefore(n3)) {
                    n3 = n;
                    break block2;
                }
                n += Character.charCount(n4);
            } while (!this.norm16HasDecompBoundaryAfter(n3));
            n3 = n;
        }
        return n3;
    }

    private int findPreviousCompBoundary(CharSequence charSequence, int n, boolean bl) {
        int n2;
        block2 : {
            int n3;
            do {
                n2 = n;
                if (n <= 0) break block2;
                n2 = Character.codePointBefore(charSequence, n);
                n3 = this.getNorm16(n2);
                if (this.norm16HasCompBoundaryAfter(n3, bl)) {
                    n2 = n;
                    break block2;
                }
                n -= Character.charCount(n2);
            } while (!this.hasCompBoundaryBefore(n2, n3));
            n2 = n;
        }
        return n2;
    }

    private int findPreviousFCDBoundary(CharSequence charSequence, int n) {
        int n2;
        block2 : {
            do {
                n2 = n;
                if (n <= 0) break block2;
                int n3 = Character.codePointBefore(charSequence, n);
                n2 = n;
                if (n3 < this.minDecompNoCP) break block2;
                n2 = this.getNorm16(n3);
                if (this.norm16HasDecompBoundaryAfter(n2)) {
                    n2 = n;
                    break block2;
                }
                n -= Character.charCount(n3);
            } while (!this.norm16HasDecompBoundaryBefore(n2));
            n2 = n;
        }
        return n2;
    }

    private int getCCFromNoNo(int n) {
        if ((this.extraData.charAt(n >>= 1) & 128) != 0) {
            return this.extraData.charAt(n - 1) & 255;
        }
        return 0;
    }

    public static int getCCFromNormalYesOrMaybe(int n) {
        return n >> 1 & 255;
    }

    public static int getCCFromYesOrMaybe(int n) {
        n = n >= 64512 ? Normalizer2Impl.getCCFromNormalYesOrMaybe(n) : 0;
        return n;
    }

    private int getCompositionsList(int n) {
        n = this.isDecompYes(n) ? this.getCompositionsListForDecompYes(n) : this.getCompositionsListForComposite(n);
        return n;
    }

    private int getCompositionsListForComposite(int n) {
        n = 64512 - this.minMaybeYes + n >> 1;
        return n + 1 + (this.maybeYesCompositions.charAt(n) & 31);
    }

    private int getCompositionsListForDecompYes(int n) {
        if (n >= 2 && 64512 > n) {
            int n2;
            int n3;
            n = n3 = (n2 = n - this.minMaybeYes);
            if (n2 < 0) {
                n = n3 + 64512;
            }
            return n >> 1;
        }
        return -1;
    }

    private int getCompositionsListForMaybe(int n) {
        return n - this.minMaybeYes >> 1;
    }

    private int getPreviousTrailCC(CharSequence charSequence, int n, int n2) {
        if (n == n2) {
            return 0;
        }
        return this.getFCD16(Character.codePointBefore(charSequence, n2));
    }

    private int hangulLVT() {
        return this.minYesNoMappingsOnly | 1;
    }

    private boolean hasCompBoundaryAfter(CharSequence charSequence, int n, int n2, boolean bl) {
        bl = n == n2 || this.hasCompBoundaryAfter(Character.codePointBefore(charSequence, n2), bl);
        return bl;
    }

    private boolean hasCompBoundaryBefore(int n, int n2) {
        boolean bl = n < this.minCompNoMaybeCP || this.norm16HasCompBoundaryBefore(n2);
        return bl;
    }

    private boolean hasCompBoundaryBefore(CharSequence charSequence, int n, int n2) {
        boolean bl = n == n2 || this.hasCompBoundaryBefore(Character.codePointAt(charSequence, n));
        return bl;
    }

    private boolean isCompYesAndZeroCC(int n) {
        boolean bl = n < this.minNoNo;
        return bl;
    }

    private boolean isDecompNoAlgorithmic(int n) {
        boolean bl = n >= this.limitNoNo;
        return bl;
    }

    private boolean isDecompYesAndZeroCC(int n) {
        boolean bl = n < this.minYesNo || n == 65024 || this.minMaybeYes <= n && n <= 64512;
        return bl;
    }

    private boolean isHangulLV(int n) {
        boolean bl = n == this.minYesNo;
        return bl;
    }

    private boolean isHangulLVT(int n) {
        boolean bl = n == this.hangulLVT();
        return bl;
    }

    private static boolean isInert(int n) {
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    private static boolean isJamoL(int n) {
        boolean bl = n == 2;
        return bl;
    }

    private static boolean isJamoVT(int n) {
        boolean bl = n == 65024;
        return bl;
    }

    private boolean isMaybe(int n) {
        boolean bl = this.minMaybeYes <= n && n <= 65024;
        return bl;
    }

    private boolean isMaybeOrNonZeroCC(int n) {
        boolean bl = n >= this.minMaybeYes;
        return bl;
    }

    private boolean isMostDecompYesAndZeroCC(int n) {
        boolean bl = n < this.minYesNo || n == 64512 || n == 65024;
        return bl;
    }

    private boolean isTrailCC01ForCompBoundaryAfter(int n) {
        boolean bl = Normalizer2Impl.isInert(n) || (this.isDecompNoAlgorithmic(n) ? (n & 6) <= 2 : this.extraData.charAt(n >> 1) <= '\u01ff');
        return bl;
    }

    private int mapAlgorithmic(int n, int n2) {
        return (n2 >> 3) + n - this.centerNoNoDelta;
    }

    private boolean norm16HasCompBoundaryAfter(int n, boolean bl) {
        bl = (n & 1) != 0 && (!bl || this.isTrailCC01ForCompBoundaryAfter(n));
        return bl;
    }

    private boolean norm16HasCompBoundaryBefore(int n) {
        boolean bl = n < this.minNoNoCompNoMaybeCC || this.isAlgorithmicNoNo(n);
        return bl;
    }

    private void recompose(ReorderingBuffer reorderingBuffer, int n, boolean bl) {
        int n2 = n;
        StringBuilder stringBuilder = reorderingBuffer.getStringBuilder();
        if (n2 == stringBuilder.length()) {
            return;
        }
        n = -1;
        int n3 = -1;
        boolean bl2 = false;
        int n4 = 0;
        do {
            int n5;
            int n6;
            int n7;
            int n8;
            block25 : {
                block24 : {
                    block22 : {
                        block23 : {
                            n7 = stringBuilder.codePointAt(n2);
                            n5 = n2 + Character.charCount(n7);
                            n8 = this.getNorm16(n7);
                            n2 = Normalizer2Impl.getCCFromYesOrMaybe(n8);
                            if (!this.isMaybe(n8) || n < 0 || n4 >= n2 && n4 != 0) break block22;
                            if (!Normalizer2Impl.isJamoVT(n8)) break block23;
                            n2 = n5;
                            if (n7 < 4519) {
                                n = (char)(stringBuilder.charAt(n3) - 4352);
                                n2 = n5;
                                if (n < 19) {
                                    n2 = n5 - 1;
                                    n8 = (char)((n * 21 + (n7 - 4449)) * 28 + 44032);
                                    n = n5;
                                    int n9 = n8;
                                    if (n5 != stringBuilder.length()) {
                                        n6 = (char)(stringBuilder.charAt(n5) - 4519);
                                        n = n5;
                                        n9 = n8;
                                        if (n6 < 28) {
                                            n = n5 + 1;
                                            n9 = n5 = (int)((char)(n8 + n6));
                                        }
                                    }
                                    stringBuilder.setCharAt(n3, (char)n9);
                                    stringBuilder.delete(n2, n);
                                }
                            }
                            if (n2 != stringBuilder.length()) {
                                n = -1;
                                continue;
                            }
                            break block24;
                        }
                        n6 = Normalizer2Impl.combine(this.maybeYesCompositions, n, n7);
                        if (n6 < 0) break block22;
                        n = n6 >> 1;
                        n2 = n5 - Character.charCount(n7);
                        stringBuilder.delete(n2, n5);
                        if (bl2) {
                            if (n > 65535) {
                                stringBuilder.setCharAt(n3, UTF16.getLeadSurrogate(n));
                                stringBuilder.setCharAt(n3 + 1, UTF16.getTrailSurrogate(n));
                            } else {
                                stringBuilder.setCharAt(n3, (char)n7);
                                stringBuilder.deleteCharAt(n3 + 1);
                                bl2 = false;
                                --n2;
                            }
                        } else if (n > 65535) {
                            bl2 = true;
                            stringBuilder.setCharAt(n3, UTF16.getLeadSurrogate(n));
                            stringBuilder.insert(n3 + 1, UTF16.getTrailSurrogate(n));
                            ++n2;
                        } else {
                            stringBuilder.setCharAt(n3, (char)n);
                        }
                        if (n2 != stringBuilder.length()) {
                            if ((n6 & 1) != 0) {
                                n = this.getCompositionsListForComposite(this.getRawNorm16(n));
                                continue;
                            }
                            n = -1;
                            continue;
                        }
                        break block24;
                    }
                    n6 = n2;
                    if (n5 != stringBuilder.length()) break block25;
                }
                reorderingBuffer.flush();
                return;
            }
            if (n2 == 0) {
                int n10;
                n8 = n10 = this.getCompositionsListForDecompYes(n8);
                n2 = n5;
                n = n8;
                n4 = n6;
                if (n10 < 0) continue;
                if (n7 <= 65535) {
                    bl2 = false;
                    n3 = n5 - 1;
                    n2 = n5;
                    n = n8;
                    n4 = n6;
                    continue;
                }
                bl2 = true;
                n3 = n5 - 2;
                n2 = n5;
                n = n8;
                n4 = n6;
                continue;
            }
            if (bl) {
                n = -1;
                n2 = n5;
                n4 = n6;
                continue;
            }
            n2 = n5;
            n4 = n6;
        } while (true);
    }

    public void addCanonIterPropertyStarts(UnicodeSet unicodeSet) {
        this.ensureCanonIterData();
        int n = 0;
        CodePointMap.Range range = new CodePointMap.Range();
        while (this.canonIterData.getRange(n, segmentStarterMapper, range)) {
            unicodeSet.add(n);
            n = range.getEnd() + 1;
        }
    }

    public void addLcccChars(UnicodeSet unicodeSet) {
        int n = 0;
        CodePointMap.Range range = new CodePointMap.Range();
        while (this.normTrie.getRange(n, CodePointMap.RangeOption.FIXED_LEAD_SURROGATES, 1, null, range)) {
            int n2 = range.getEnd();
            int n3 = range.getValue();
            if (n3 > 64512 && n3 != 65024) {
                unicodeSet.add(n, n2);
            } else if (this.minNoNoCompNoMaybeCC <= n3 && n3 < this.limitNoNo && this.getFCD16(n) > 255) {
                unicodeSet.add(n, n2);
            }
            n = n2 + 1;
        }
    }

    public void addPropertyStarts(UnicodeSet unicodeSet) {
        int n = 0;
        CodePointMap.Range range = new CodePointMap.Range();
        while (this.normTrie.getRange(n, CodePointMap.RangeOption.FIXED_LEAD_SURROGATES, 1, null, range)) {
            int n2 = range.getEnd();
            int n3 = range.getValue();
            unicodeSet.add(n);
            if (n != n2 && this.isAlgorithmicNoNo(n3) && (n3 & 6) > 2) {
                n3 = this.getFCD16(n);
                int n4 = n;
                while (++n4 <= n2) {
                    int n5 = this.getFCD16(n4);
                    n = n3;
                    if (n5 != n3) {
                        unicodeSet.add(n4);
                        n = n5;
                    }
                    n3 = n;
                }
            }
            n = n2 + 1;
        }
        for (n = 44032; n < 55204; n += 28) {
            unicodeSet.add(n);
            unicodeSet.add(n + 1);
        }
        unicodeSet.add(55204);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean compose(CharSequence var1_1, int var2_2, int var3_3, boolean var4_4, boolean var5_5, ReorderingBuffer var6_6) {
        var7_7 = var3_3;
        var8_8 = this.minCompNoMaybeCP;
        var9_9 = var2_2;
        block0 : do {
            block34 : {
                var10_10 = var2_2;
                do {
                    block35 : {
                        block38 : {
                            block39 : {
                                block41 : {
                                    block40 : {
                                        block37 : {
                                            block36 : {
                                                if (var10_10 == var7_7) {
                                                    if (var9_9 == var7_7) return true;
                                                    if (var5_5 == false) return true;
                                                    var6_6.append(var1_1, var9_9, var7_7);
                                                    return true;
                                                }
                                                var11_11 = var2_2 = (int)var1_1.charAt(var10_10);
                                                if (var2_2 < var8_8) break block35;
                                                var12_12 = var2_2 = this.normTrie.bmpGet(var11_11);
                                                if (this.isCompYesAndZeroCC(var2_2)) break block35;
                                                var13_13 = var10_10 + 1;
                                                if (UTF16Plus.isLeadSurrogate(var11_11)) break block36;
                                                var2_2 = var13_13;
                                                break block37;
                                            }
                                            var2_2 = var13_13;
                                            if (var13_13 == var7_7) break block38;
                                            var14_14 = var1_1.charAt(var13_13);
                                            var2_2 = var13_13++;
                                            if (!Character.isLowSurrogate(var14_14)) break block38;
                                            var11_11 = Character.toCodePoint((char)var11_11, var14_14);
                                            var12_12 = this.normTrie.suppGet(var11_11);
                                            var2_2 = var13_13;
                                            if (this.isCompYesAndZeroCC(var12_12)) break block38;
                                            var2_2 = var13_13;
                                        }
                                        if (!this.isMaybeOrNonZeroCC(var12_12)) {
                                            if (!var5_5) {
                                                return false;
                                            }
                                            if (this.isDecompNoAlgorithmic(var12_12)) {
                                                if (this.norm16HasCompBoundaryAfter(var12_12, var4_4) || this.hasCompBoundaryBefore(var1_1, var2_2, var7_7)) {
                                                    if (var9_9 != var10_10) {
                                                        var6_6.append(var1_1, var9_9, var10_10);
                                                    }
                                                    var6_6.append(this.mapAlgorithmic(var11_11, var12_12), 0);
                                                    var9_9 = var2_2;
                                                    continue block0;
                                                }
                                            } else if (var12_12 < this.minNoNoCompBoundaryBefore) {
                                                if (this.norm16HasCompBoundaryAfter(var12_12, var4_4) || this.hasCompBoundaryBefore(var1_1, var2_2, var7_7)) {
                                                    if (var9_9 != var10_10) {
                                                        var6_6.append(var1_1, var9_9, var10_10);
                                                    }
                                                    var10_10 = var12_12 >> 1;
                                                    var15_15 = this.extraData;
                                                    var9_9 = var10_10 + 1;
                                                    var10_10 = var15_15.charAt(var10_10);
                                                    var6_6.append(this.extraData, var9_9, var9_9 + (var10_10 & 31));
                                                    var9_9 = var2_2;
                                                    continue block0;
                                                }
                                            } else if (var12_12 >= this.minNoNoEmpty && (this.hasCompBoundaryBefore(var1_1, var2_2, var7_7) || this.hasCompBoundaryAfter(var1_1, var9_9, var10_10, var4_4))) {
                                                if (var9_9 != var10_10) {
                                                    var6_6.append(var1_1, var9_9, var10_10);
                                                }
                                                var9_9 = var2_2;
                                                continue block0;
                                            }
                                            break block34;
                                        }
                                        if (!Normalizer2Impl.isJamoVT(var12_12) || var9_9 == var10_10) break block39;
                                        var13_13 = var1_1.charAt(var10_10 - 1);
                                        if (var11_11 >= 4519) break block40;
                                        var16_16 = (char)(var13_13 - 4352);
                                        var17_17 = var2_2;
                                        if (var16_16 >= 19) break block41;
                                        if (!var5_5) {
                                            return false;
                                        }
                                        if (var2_2 == var7_7) ** GOTO lbl-1000
                                        var13_13 = var17_17 = var1_1.charAt(var2_2) - 4519;
                                        if (var17_17 > 0 && var13_13 < 28) {
                                            ++var2_2;
                                        } else lbl-1000: // 2 sources:
                                        {
                                            var13_13 = this.hasCompBoundaryBefore(var1_1, var2_2, var7_7) != false ? 0 : -1;
                                        }
                                        var17_17 = var2_2;
                                        if (var13_13 >= 0) {
                                            if (var9_9 != --var10_10) {
                                                var6_6.append(var1_1, var9_9, var10_10);
                                            }
                                            var6_6.append((char)((var16_16 * 21 + (var11_11 - 4449)) * 28 + 44032 + var13_13));
                                            var9_9 = var2_2;
                                            continue block0;
                                        }
                                        break block41;
                                    }
                                    var17_17 = var2_2;
                                    if (Hangul.isHangulLV(var13_13)) {
                                        if (!var5_5) {
                                            return false;
                                        }
                                        if (var9_9 != --var10_10) {
                                            var6_6.append(var1_1, var9_9, var10_10);
                                        }
                                        var6_6.append((char)(var13_13 + var11_11 - 4519));
                                        var9_9 = var2_2;
                                        continue block0;
                                    }
                                }
                                var2_2 = var17_17;
                                break block34;
                            }
                            if (var12_12 > 65024) {
                                var16_16 = Normalizer2Impl.getCCFromNormalYesOrMaybe(var12_12);
                                var13_13 = var2_2;
                                var17_17 = var16_16;
                                if (!var4_4) break;
                                var13_13 = var2_2;
                                var17_17 = var16_16;
                                if (this.getPreviousTrailCC(var1_1, var9_9, var10_10) <= var16_16) break;
                                if (!var5_5) {
                                    return false;
                                }
                            }
                            break block34;
                        }
                        var7_7 = var3_3;
                        var10_10 = var2_2;
                        continue;
                    }
                    ++var10_10;
                    var7_7 = var3_3;
                } while (true);
                do {
                    var2_2 = var17_17;
                    if (var13_13 == var7_7) {
                        if (var5_5 == false) return true;
                        var6_6.append(var1_1, var9_9, var7_7);
                        return true;
                    }
                    var11_11 = Character.codePointAt(var1_1, var13_13);
                    var16_16 = this.normTrie.get(var11_11);
                    if (var16_16 < 65026) break;
                    var17_17 = Normalizer2Impl.getCCFromNormalYesOrMaybe(var16_16);
                    if (var2_2 > var17_17) {
                        if (var5_5) break;
                        return false;
                    }
                    var13_13 += Character.charCount(var11_11);
                } while (true);
                if (this.norm16HasCompBoundaryBefore(var16_16)) {
                    if (this.isCompYesAndZeroCC(var16_16)) {
                        var2_2 = Character.charCount(var11_11) + var13_13;
                        continue;
                    }
                    var2_2 = var13_13;
                    continue;
                }
                var2_2 = var13_13;
            }
            if (var9_9 != var10_10 && !this.norm16HasCompBoundaryBefore(var12_12) && !this.norm16HasCompBoundaryAfter(this.normTrie.get(var13_13 = Character.codePointBefore(var1_1, var10_10)), var4_4)) {
                var10_10 -= Character.charCount(var13_13);
            }
            if (var5_5 && var9_9 != var10_10) {
                var6_6.append(var1_1, var9_9, var10_10);
            }
            var9_9 = var6_6.length();
            this.decomposeShort(var1_1, var10_10, var2_2, false, var4_4, var6_6);
            var2_2 = this.decomposeShort(var1_1, var2_2, var3_3, true, var4_4, var6_6);
            this.recompose(var6_6, var9_9, var4_4);
            if (!var5_5) {
                if (!var6_6.equals(var1_1, var10_10, var2_2)) {
                    return false;
                }
                var6_6.remove();
            }
            var9_9 = var2_2;
            var7_7 = var3_3;
        } while (true);
    }

    public void composeAndAppend(CharSequence charSequence, boolean bl, boolean bl2, ReorderingBuffer reorderingBuffer) {
        int n = 0;
        int n2 = charSequence.length();
        int n3 = n;
        if (!reorderingBuffer.isEmpty()) {
            int n4 = this.findNextCompBoundary(charSequence, 0, n2, bl2);
            n3 = n;
            if (n4 != 0) {
                n3 = this.findPreviousCompBoundary(reorderingBuffer.getStringBuilder(), reorderingBuffer.length(), bl2);
                StringBuilder stringBuilder = new StringBuilder(reorderingBuffer.length() - n3 + n4 + 16);
                stringBuilder.append(reorderingBuffer.getStringBuilder(), n3, reorderingBuffer.length());
                reorderingBuffer.removeSuffix(reorderingBuffer.length() - n3);
                stringBuilder.append(charSequence, 0, n4);
                this.compose(stringBuilder, 0, stringBuilder.length(), bl2, true, reorderingBuffer);
                n3 = n4;
            }
        }
        if (bl) {
            this.compose(charSequence, n3, n2, bl2, true, reorderingBuffer);
        } else {
            reorderingBuffer.append(charSequence, n3, n2);
        }
    }

    public int composePair(int n, int n2) {
        block13 : {
            block12 : {
                int n3;
                block11 : {
                    int n4;
                    n3 = this.getNorm16(n);
                    if (Normalizer2Impl.isInert(n3)) {
                        return -1;
                    }
                    if (n3 >= this.minYesNoMappingsOnly) break block11;
                    if (Normalizer2Impl.isJamoL(n3)) {
                        if ((n2 -= 4449) >= 0 && n2 < 21) {
                            return ((n - 4352) * 21 + n2) * 28 + 44032;
                        }
                        return -1;
                    }
                    if (this.isHangulLV(n3)) {
                        if ((n2 -= 4519) > 0 && n2 < 28) {
                            return n + n2;
                        }
                        return -1;
                    }
                    n = n4 = 64512 - this.minMaybeYes + n3 >> 1;
                    if (n3 > this.minYesNo) {
                        n = n4 + ((this.maybeYesCompositions.charAt(n4) & 31) + 1);
                    }
                    break block12;
                }
                if (n3 < this.minMaybeYes || 64512 <= n3) break block13;
                n = this.getCompositionsListForMaybe(n3);
            }
            if (n2 >= 0 && 1114111 >= n2) {
                return Normalizer2Impl.combine(this.maybeYesCompositions, n, n2) >> 1;
            }
            return -1;
        }
        return -1;
    }

    public int composeQuickCheck(CharSequence charSequence, int n, int n2, boolean bl, boolean bl2) {
        int n3 = 0;
        int n4 = n;
        int n5 = this.minCompNoMaybeCP;
        do {
            block14 : {
                int n6;
                block16 : {
                    block17 : {
                        int n7;
                        int n8;
                        int n9;
                        int n10;
                        block18 : {
                            int n11;
                            block15 : {
                                if (n == n2) {
                                    return n << 1 | n3;
                                }
                                n11 = charSequence.charAt(n);
                                if (n11 < n5) break block14;
                                n8 = n6 = this.normTrie.bmpGet(n11);
                                if (this.isCompYesAndZeroCC(n6)) break block14;
                                n7 = n + 1;
                                if (!UTF16Plus.isLeadSurrogate(n11)) break block15;
                                n6 = n7;
                                if (n7 == n2) break block16;
                                char c = charSequence.charAt(n7);
                                n6 = n7++;
                                if (!Character.isLowSurrogate(c)) break block16;
                                n6 = Character.toCodePoint((char)n11, c);
                                n8 = this.normTrie.suppGet(n6);
                                n6 = n7;
                                if (this.isCompYesAndZeroCC(n8)) break block16;
                            }
                            n10 = 1;
                            n6 = n4;
                            n11 = n10;
                            if (n4 != n) {
                                n6 = n4 = n;
                                n11 = n10;
                                if (!this.norm16HasCompBoundaryBefore(n8)) {
                                    n9 = Character.codePointBefore(charSequence, n);
                                    n = this.getNorm16(n9);
                                    n6 = n4;
                                    n11 = n10;
                                    if (!this.norm16HasCompBoundaryAfter(n, bl)) {
                                        n6 = n4 - Character.charCount(n9);
                                        n11 = n;
                                    }
                                }
                            }
                            if (!this.isMaybeOrNonZeroCC(n8)) break block17;
                            int n12 = Normalizer2Impl.getCCFromYesOrMaybe(n8);
                            n9 = n3;
                            n10 = n8;
                            n = n7;
                            n4 = n12;
                            if (!bl) break block18;
                            n9 = n3;
                            n10 = n8;
                            n = n7;
                            n4 = n12;
                            if (n12 == 0) break block18;
                            n9 = n3;
                            n10 = n8;
                            n = n7;
                            n4 = n12;
                            if (this.getTrailCCFromCompYesAndZeroCC(n11) > n12) break block17;
                        }
                        do {
                            n8 = n4;
                            n3 = n9;
                            if (n10 < 65026) {
                                if (!bl2) {
                                    n3 = 1;
                                } else {
                                    return n6 << 1;
                                }
                            }
                            if (n == n2) {
                                return n << 1 | n3;
                            }
                            n7 = Character.codePointAt(charSequence, n);
                            n10 = this.getNorm16(n7);
                            if (!this.isMaybeOrNonZeroCC(n10) || n8 > (n4 = Normalizer2Impl.getCCFromYesOrMaybe(n10)) && n4 != 0) break;
                            n += Character.charCount(n7);
                            n9 = n3;
                        } while (true);
                        if (this.isCompYesAndZeroCC(n10)) {
                            n4 = n;
                            n += Character.charCount(n7);
                            continue;
                        }
                    }
                    return n6 << 1;
                }
                n = n6;
                continue;
            }
            ++n;
        } while (true);
    }

    public int decompose(CharSequence charSequence, int n, int n2, ReorderingBuffer reorderingBuffer) {
        int n3;
        int n4 = this.minDecompNoCP;
        int n5 = 0;
        int n6 = 0;
        int n7 = n;
        int n8 = 0;
        int n9 = n;
        n = n6;
        do {
            int n10;
            int n11;
            n6 = n9;
            int n12 = n5;
            do {
                n5 = n6;
                n6 = n12;
                n12 = n;
                if (n5 == n2) break;
                n6 = n12 = (int)charSequence.charAt(n5);
                if (n12 >= n4) {
                    n = n12 = this.normTrie.bmpGet(n6);
                    if (!this.isMostDecompYesAndZeroCC(n12)) {
                        char c;
                        if (!UTF16Plus.isLeadSurrogate(n6)) {
                            n12 = n;
                            break;
                        }
                        if (n5 + 1 != n2 && Character.isLowSurrogate(c = charSequence.charAt(n5 + 1))) {
                            n11 = Character.toCodePoint((char)n6, c);
                            n = this.normTrie.suppGet(n11);
                            n6 = n11;
                            n12 = n;
                            if (!this.isMostDecompYesAndZeroCC(n)) break;
                            n6 = n5 + 2;
                            n12 = n11;
                            continue;
                        }
                        n12 = n6;
                        n6 = ++n5;
                        continue;
                    }
                }
                n12 = n6;
                n6 = ++n5;
            } while (true);
            n3 = n7;
            n11 = n8;
            if (n5 != n9) {
                if (reorderingBuffer != null) {
                    reorderingBuffer.flushAndAppendZeroCC(charSequence, n9, n5);
                    n3 = n7;
                    n11 = n8;
                } else {
                    n11 = 0;
                    n3 = n5;
                }
            }
            if (n5 == n2) {
                return n5;
            }
            int n13 = n5 + Character.charCount(n6);
            if (reorderingBuffer != null) {
                this.decompose(n6, n12, reorderingBuffer);
                n5 = n6;
                n = n12;
                n7 = n3;
                n8 = n11;
                n9 = n13;
                continue;
            }
            if (!this.isDecompYes(n12) || n11 > (n10 = Normalizer2Impl.getCCFromYesOrMaybe(n12)) && n10 != 0) break;
            n11 = n10;
            n5 = n6;
            n = n12;
            n7 = n3;
            n8 = n11;
            n9 = n13;
            if (n10 > 1) continue;
            n7 = n13;
            n5 = n6;
            n = n12;
            n8 = n11;
            n9 = n13;
        } while (true);
        return n3;
    }

    public Appendable decompose(CharSequence charSequence, StringBuilder stringBuilder) {
        this.decompose(charSequence, 0, charSequence.length(), stringBuilder, charSequence.length());
        return stringBuilder;
    }

    public void decompose(CharSequence charSequence, int n, int n2, StringBuilder stringBuilder, int n3) {
        int n4 = n3;
        if (n3 < 0) {
            n4 = n2 - n;
        }
        stringBuilder.setLength(0);
        this.decompose(charSequence, n, n2, new ReorderingBuffer(this, stringBuilder, n4));
    }

    public void decomposeAndAppend(CharSequence charSequence, boolean bl, ReorderingBuffer reorderingBuffer) {
        int n;
        int n2 = charSequence.length();
        if (n2 == 0) {
            return;
        }
        if (bl) {
            this.decompose(charSequence, 0, n2, reorderingBuffer);
            return;
        }
        int n3 = Character.codePointAt(charSequence, 0);
        int n4 = 0;
        int n5 = n = this.getCC(this.getNorm16(n3));
        int n6 = n;
        while (n5 != 0) {
            n6 = n5;
            if ((n4 += Character.charCount(n3)) >= n2) break;
            n3 = Character.codePointAt(charSequence, n4);
            n5 = this.getCC(this.getNorm16(n3));
        }
        reorderingBuffer.append(charSequence, 0, n4, false, n, n6);
        reorderingBuffer.append(charSequence, n4, n2);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public Normalizer2Impl ensureCanonIterData() {
        // MONITORENTER : this
        if (this.canonIterData != null) ** GOTO lbl17
        var1_1 = new MutableCodePointTrie(0, 0);
        var2_2 = new ArrayList();
        this.canonStartSets = var2_2;
        var3_3 = 0;
        var2_2 = new CodePointMap.Range();
        do {
            if (!this.normTrie.getRange(var3_3, CodePointMap.RangeOption.FIXED_LEAD_SURROGATES, 1, null, (CodePointMap.Range)var2_2)) ** GOTO lbl16
            var4_4 = var2_2.getEnd();
            var5_5 = var2_2.getValue();
            if (!(Normalizer2Impl.isInert(var5_5) || this.minYesNo <= var5_5 && var5_5 < this.minNoNo)) {
            } else {
                var3_3 = var4_4 + 1;
                continue;
lbl16: // 1 sources:
                this.canonIterData = var1_1.buildImmutable(CodePointTrie.Type.SMALL, CodePointTrie.ValueWidth.BITS_32);
lbl17: // 2 sources:
                // MONITOREXIT : this
                return this;
            }
            for (var6_6 = var3_3; var6_6 <= var4_4; ++var6_6) {
                block15 : {
                    block18 : {
                        block17 : {
                            block16 : {
                                var8_8 = var7_7 = var1_1.get(var6_6);
                                if (!this.isMaybeOrNonZeroCC(var5_5)) break block16;
                                var9_9 = var8_8 | Integer.MIN_VALUE;
                                if (var5_5 < 64512) {
                                    var9_9 |= 1073741824;
                                }
                                break block15;
                            }
                            if (var5_5 >= this.minYesNo) break block17;
                            var9_9 = var8_8 | 1073741824;
                            break block15;
                        }
                        var9_9 = var6_6;
                        var10_10 = var5_5;
                        var11_11 = var9_9;
                        var12_12 = var10_10;
                        if (this.isDecompNoAlgorithmic(var10_10)) {
                            var11_11 = this.mapAlgorithmic(var9_9, var10_10);
                            var12_12 = this.getRawNorm16(var11_11);
                        }
                        if (var12_12 <= this.minYesNo) break block18;
                        var10_10 = var12_12 >> 1;
                        var13_13 = this.extraData.charAt(var10_10);
                        var14_14 = var13_13 & 31;
                        var9_9 = var8_8;
                        if ((var13_13 & 128) != 0) {
                            var9_9 = var8_8;
                            if (var6_6 == var11_11) {
                                var9_9 = var8_8;
                                if ((this.extraData.charAt(var10_10 - 1) & 255) != 0) {
                                    var9_9 = var8_8 | Integer.MIN_VALUE;
                                }
                            }
                        }
                        if (var14_14 == 0) break block15;
                        var11_11 = var10_10 + 1;
                        var10_10 = this.extraData.codePointAt(var11_11);
                        this.addToStartSet(var1_1, var6_6, var10_10);
                        if (var12_12 >= this.minNoNo) {
                            var8_8 = var11_11;
                            var12_12 = var10_10;
                            do {
                                var8_8 = var12_12 = Character.charCount(var12_12) + var8_8;
                                if (var12_12 < var11_11 + var14_14) {
                                    var12_12 = this.extraData.codePointAt(var8_8);
                                    var10_10 = var1_1.get(var12_12);
                                    if ((var10_10 & Integer.MIN_VALUE) != 0) continue;
                                    var1_1.set(var12_12, var10_10 | Integer.MIN_VALUE);
                                    continue;
                                } else {
                                    ** GOTO lbl-1000
                                }
                                break;
                            } while (true);
                        }
                        break block15;
lbl-1000: // 2 sources:
                        {
                            break block15;
                        }
                    }
                    this.addToStartSet(var1_1, var6_6, var11_11);
                    var9_9 = var8_8;
                }
                if (var9_9 == var7_7) continue;
                var1_1.set(var6_6, var9_9);
            }
            var3_3 = var4_4 + 1;
        } while (true);
    }

    public int getCC(int n) {
        if (n >= 64512) {
            return Normalizer2Impl.getCCFromNormalYesOrMaybe(n);
        }
        if (n >= this.minNoNo && this.limitNoNo > n) {
            return this.getCCFromNoNo(n);
        }
        return 0;
    }

    public int getCCFromYesOrMaybeCP(int n) {
        if (n < this.minCompNoMaybeCP) {
            return 0;
        }
        return Normalizer2Impl.getCCFromYesOrMaybe(this.getNorm16(n));
    }

    public boolean getCanonStartSet(int n, UnicodeSet unicodeSet) {
        int n2 = this.canonIterData.get(n) & Integer.MAX_VALUE;
        if (n2 == 0) {
            return false;
        }
        unicodeSet.clear();
        int n3 = 2097151 & n2;
        if ((2097152 & n2) != 0) {
            unicodeSet.addAll(this.canonStartSets.get(n3));
        } else if (n3 != 0) {
            unicodeSet.add(n3);
        }
        if ((1073741824 & n2) != 0) {
            n3 = this.getRawNorm16(n);
            if (n3 == 2) {
                n = (n - 4352) * 588 + 44032;
                unicodeSet.add(n, n + 588 - 1);
            } else {
                this.addComposites(this.getCompositionsList(n3), unicodeSet);
            }
        }
        return true;
    }

    public int getCompQuickCheck(int n) {
        if (n >= this.minNoNo && 65026 > n) {
            if (this.minMaybeYes <= n) {
                return 2;
            }
            return 0;
        }
        return 1;
    }

    public String getDecomposition(int n) {
        if (n >= this.minDecompNoCP) {
            int n2;
            int n3 = n2 = this.getNorm16(n);
            if (!this.isMaybeOrNonZeroCC(n2)) {
                int n4 = -1;
                int n5 = n3;
                n2 = n;
                if (this.isDecompNoAlgorithmic(n3)) {
                    n2 = n4 = this.mapAlgorithmic(n, n3);
                    n5 = this.getRawNorm16(n2);
                }
                if (n5 < this.minYesNo) {
                    if (n4 < 0) {
                        return null;
                    }
                    return UTF16.valueOf(n4);
                }
                if (!this.isHangulLV(n5) && !this.isHangulLVT(n5)) {
                    n2 = n5 >> 1;
                    String string = this.extraData;
                    n = n2 + 1;
                    n2 = string.charAt(n2);
                    return this.extraData.substring(n, n + (n2 & 31));
                }
                StringBuilder stringBuilder = new StringBuilder();
                Hangul.decompose(n2, stringBuilder);
                return stringBuilder.toString();
            }
        }
        return null;
    }

    public int getFCD16(int n) {
        if (n < this.minDecompNoCP) {
            return 0;
        }
        if (n <= 65535 && !this.singleLeadMightHaveNonZeroFCD16(n)) {
            return 0;
        }
        return this.getFCD16FromNormData(n);
    }

    public int getFCD16FromNormData(int n) {
        int n2;
        int n3 = n2 = this.getNorm16(n);
        if (n2 >= this.limitNoNo) {
            if (n2 >= 64512) {
                n = Normalizer2Impl.getCCFromNormalYesOrMaybe(n2);
                return n << 8 | n;
            }
            if (n2 >= this.minMaybeYes) {
                return 0;
            }
            n3 = n2 & 6;
            if (n3 <= 2) {
                return n3 >> 1;
            }
            n3 = this.getRawNorm16(this.mapAlgorithmic(n, n2));
        }
        if (n3 > this.minYesNo && !this.isHangulLVT(n3)) {
            n2 = n3 >> 1;
            char c = this.extraData.charAt(n2);
            n = n3 = c >> 8;
            if ((c & 128) != 0) {
                n = n3 | this.extraData.charAt(n2 - 1) & 65280;
            }
            return n;
        }
        return 0;
    }

    public int getNorm16(int n) {
        n = UTF16Plus.isLeadSurrogate(n) ? 1 : this.normTrie.get(n);
        return n;
    }

    public String getRawDecomposition(int n) {
        int n2;
        if (n >= this.minDecompNoCP && !this.isDecompYes(n2 = this.getNorm16(n))) {
            if (!this.isHangulLV(n2) && !this.isHangulLVT(n2)) {
                if (this.isDecompNoAlgorithmic(n2)) {
                    return UTF16.valueOf(this.mapAlgorithmic(n, n2));
                }
                int n3 = this.extraData.charAt(n2 >>= 1);
                n = n3 & 31;
                if ((n3 & 64) != 0) {
                    char c = this.extraData.charAt(n3 = n2 - (n3 >> 7 & 1) - 1);
                    if (c <= '\u001f') {
                        return this.extraData.substring(n3 - c, n3);
                    }
                    StringBuilder stringBuilder = new StringBuilder(n - 1).append(c);
                    stringBuilder.append(this.extraData, n2 += 3, n2 + n - 2);
                    return stringBuilder.toString();
                }
                return this.extraData.substring(++n2, n2 + n);
            }
            StringBuilder stringBuilder = new StringBuilder();
            Hangul.getRawDecomposition(n, stringBuilder);
            return stringBuilder.toString();
        }
        return null;
    }

    public int getRawNorm16(int n) {
        return this.normTrie.get(n);
    }

    int getTrailCCFromCompYesAndZeroCC(int n) {
        if (n <= this.minYesNo) {
            return 0;
        }
        return this.extraData.charAt(n >> 1) >> 8;
    }

    public boolean hasCompBoundaryAfter(int n, boolean bl) {
        return this.norm16HasCompBoundaryAfter(this.getNorm16(n), bl);
    }

    public boolean hasCompBoundaryBefore(int n) {
        boolean bl = n < this.minCompNoMaybeCP || this.norm16HasCompBoundaryBefore(this.getNorm16(n));
        return bl;
    }

    public boolean hasDecompBoundaryAfter(int n) {
        if (n < this.minDecompNoCP) {
            return true;
        }
        if (n <= 65535 && !this.singleLeadMightHaveNonZeroFCD16(n)) {
            return true;
        }
        return this.norm16HasDecompBoundaryAfter(this.getNorm16(n));
    }

    public boolean hasDecompBoundaryBefore(int n) {
        boolean bl = n < this.minLcccCP || n <= 65535 && !this.singleLeadMightHaveNonZeroFCD16(n) || this.norm16HasDecompBoundaryBefore(this.getNorm16(n));
        return bl;
    }

    public boolean hasFCDBoundaryAfter(int n) {
        return this.hasDecompBoundaryAfter(n);
    }

    public boolean hasFCDBoundaryBefore(int n) {
        return this.hasDecompBoundaryBefore(n);
    }

    public boolean isAlgorithmicNoNo(int n) {
        boolean bl = this.limitNoNo <= n && n < this.minMaybeYes;
        return bl;
    }

    public boolean isCanonSegmentStarter(int n) {
        boolean bl = this.canonIterData.get(n) >= 0;
        return bl;
    }

    public boolean isCompInert(int n, boolean bl) {
        bl = this.isCompYesAndZeroCC(n = this.getNorm16(n)) && (n & 1) != 0 && (!bl || Normalizer2Impl.isInert(n) || this.extraData.charAt(n >> 1) <= '\u01ff');
        return bl;
    }

    public boolean isCompNo(int n) {
        boolean bl = this.minNoNo <= n && n < this.minMaybeYes;
        return bl;
    }

    public boolean isDecompInert(int n) {
        return this.isDecompYesAndZeroCC(this.getNorm16(n));
    }

    public boolean isDecompYes(int n) {
        boolean bl = n < this.minYesNo || this.minMaybeYes <= n;
        return bl;
    }

    public boolean isFCDInert(int n) {
        n = this.getFCD16(n);
        boolean bl = true;
        if (n > 1) {
            bl = false;
        }
        return bl;
    }

    public Normalizer2Impl load(String string) {
        return this.load(ICUBinary.getRequiredData(string));
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Normalizer2Impl load(ByteBuffer object) {
        int n;
        int[] arrn;
        int n2;
        int n3;
        try {
            this.dataVersion = ICUBinary.readHeaderAndDataVersion((ByteBuffer)object, 1316121906, IS_ACCEPTABLE);
            n2 = ((ByteBuffer)object).getInt() / 4;
            if (n2 > 18) {
                arrn = new int[n2];
                arrn[0] = n2 * 4;
            } else {
                object = new ICUUncheckedIOException("Normalizer2 data: not enough indexes");
                throw object;
            }
            for (n = 1; n < n2; ++n) {
                arrn[n] = ((ByteBuffer)object).getInt();
                continue;
            }
            this.minDecompNoCP = arrn[8];
            this.minCompNoMaybeCP = arrn[9];
            this.minLcccCP = arrn[18];
            this.minYesNo = arrn[10];
            this.minYesNoMappingsOnly = arrn[14];
            this.minNoNo = arrn[11];
            this.minNoNoCompBoundaryBefore = arrn[15];
            this.minNoNoCompNoMaybeCC = arrn[16];
            this.minNoNoEmpty = arrn[17];
            this.limitNoNo = arrn[12];
            this.minMaybeYes = arrn[13];
            this.centerNoNoDelta = (this.minMaybeYes >> 3) - 64 - 1;
            n = arrn[0];
            n2 = arrn[1];
            n3 = ((Buffer)object).position();
            this.normTrie = CodePointTrie.Fast16.fromBinary((ByteBuffer)object);
            n3 = ((Buffer)object).position() - n3;
            if (n3 > n2 - n) break block9;
        }
        catch (IOException iOException) {
            throw new ICUUncheckedIOException(iOException);
        }
        {
            block9 : {
                ICUBinary.skipBytes((ByteBuffer)object, n2 - n - n3);
                n = (arrn[2] - n2) / 2;
                if (n != 0) {
                    this.maybeYesCompositions = ICUBinary.getString((ByteBuffer)object, n, 0);
                    this.extraData = this.maybeYesCompositions.substring(64512 - this.minMaybeYes >> 1);
                }
                this.smallFCD = new byte[256];
                ((ByteBuffer)object).get(this.smallFCD);
                return this;
            }
            object = new ICUUncheckedIOException("Normalizer2 data: not enough bytes for normTrie");
            throw object;
        }
    }

    public int makeFCD(CharSequence charSequence, int n, int n2, ReorderingBuffer reorderingBuffer) {
        int n3 = n;
        int n4 = 0;
        n = 0;
        int n5 = 0;
        int n6 = n3;
        do {
            int n7;
            int n8;
            block30 : {
                block31 : {
                    block29 : {
                        block27 : {
                            int n9;
                            block28 : {
                                block26 : {
                                    n8 = n5;
                                    n5 = n3;
                                    while ((n7 = n5) != n2) {
                                        n9 = n5 = (int)charSequence.charAt(n7);
                                        if (n5 < this.minLcccCP) {
                                            n = n9;
                                            n5 = n7 + 1;
                                            n8 = n9;
                                            continue;
                                        }
                                        if (!this.singleLeadMightHaveNonZeroFCD16(n9)) {
                                            n = 0;
                                            n5 = n7 + 1;
                                            n8 = n9;
                                            continue;
                                        }
                                        n8 = n9;
                                        if (UTF16Plus.isLeadSurrogate(n9)) {
                                            n8 = n9;
                                            if (n7 + 1 != n2) {
                                                char c = charSequence.charAt(n7 + 1);
                                                n8 = n9;
                                                if (Character.isLowSurrogate(c)) {
                                                    n8 = Character.toCodePoint((char)n9, c);
                                                }
                                            }
                                        }
                                        n4 = n5 = this.getFCD16FromNormData(n8);
                                        if (n5 <= 255) {
                                            n = n4;
                                            n5 = n7 + Character.charCount(n8);
                                            continue;
                                        }
                                        n5 = n4;
                                        n4 = n8;
                                        break block26;
                                    }
                                    n5 = n4;
                                    n4 = n8;
                                }
                                if (n7 == n3) break block27;
                                if (n7 != n2) break block28;
                                if (reorderingBuffer != null) {
                                    reorderingBuffer.flushAndAppendZeroCC(charSequence, n3, n7);
                                }
                                break block29;
                            }
                            n6 = n7;
                            if (n < 0) {
                                if (n < this.minDecompNoCP) {
                                    n8 = 0;
                                    n = n6;
                                } else {
                                    n9 = this.getFCD16FromNormData(n);
                                    n = n6;
                                    n8 = n9;
                                    if (n9 > 1) {
                                        n = n6 - 1;
                                        n8 = n9;
                                    }
                                }
                                n6 = n8;
                            } else {
                                int n10 = n7 - 1;
                                n8 = n;
                                n9 = n10;
                                if (Character.isLowSurrogate(charSequence.charAt(n10))) {
                                    n8 = n;
                                    n9 = n10;
                                    if (n3 < n10) {
                                        n8 = n;
                                        n9 = n10;
                                        if (Character.isHighSurrogate(charSequence.charAt(n10 - 1))) {
                                            n9 = n10 - 1;
                                            n8 = this.getFCD16FromNormData(Character.toCodePoint(charSequence.charAt(n9), charSequence.charAt(n9 + 1)));
                                        }
                                    }
                                }
                                n = n6;
                                n6 = n8;
                                if (n8 > 1) {
                                    n = n9;
                                    n6 = n8;
                                }
                            }
                            if (reorderingBuffer != null) {
                                reorderingBuffer.flushAndAppendZeroCC(charSequence, n3, n);
                                reorderingBuffer.append(charSequence, n, n7);
                            }
                            n8 = n6;
                            n6 = n7;
                            break block30;
                        }
                        if (n7 != n2) break block31;
                    }
                    return n7;
                }
                n8 = n;
                n = n6;
                n6 = n3;
            }
            n3 = n7 + Character.charCount(n4);
            if ((n8 & 255) <= n5 >> 8) {
                n6 = (n5 & 255) <= 1 ? n3 : n;
                if (reorderingBuffer != null) {
                    reorderingBuffer.appendZeroCC(n4);
                }
                n = n5;
                n8 = n4;
                n4 = n5;
                n5 = n8;
                continue;
            }
            if (reorderingBuffer == null) {
                return n;
            }
            reorderingBuffer.removeSuffix(n6 - n);
            n3 = this.findNextFCDBoundary(charSequence, n3, n2);
            this.decomposeShort(charSequence, n, n3, false, false, reorderingBuffer);
            n6 = n3;
            n8 = 0;
            n = n4;
            n4 = n5;
            n5 = n;
            n = n8;
        } while (true);
    }

    public void makeFCDAndAppend(CharSequence charSequence, boolean bl, ReorderingBuffer reorderingBuffer) {
        int n = 0;
        int n2 = charSequence.length();
        int n3 = n;
        if (!reorderingBuffer.isEmpty()) {
            int n4 = this.findNextFCDBoundary(charSequence, 0, n2);
            n3 = n;
            if (n4 != 0) {
                n3 = this.findPreviousFCDBoundary(reorderingBuffer.getStringBuilder(), reorderingBuffer.length());
                StringBuilder stringBuilder = new StringBuilder(reorderingBuffer.length() - n3 + n4 + 16);
                stringBuilder.append(reorderingBuffer.getStringBuilder(), n3, reorderingBuffer.length());
                reorderingBuffer.removeSuffix(reorderingBuffer.length() - n3);
                stringBuilder.append(charSequence, 0, n4);
                this.makeFCD(stringBuilder, 0, stringBuilder.length(), reorderingBuffer);
                n3 = n4;
            }
        }
        if (bl) {
            this.makeFCD(charSequence, n3, n2, reorderingBuffer);
        } else {
            reorderingBuffer.append(charSequence, n3, n2);
        }
    }

    public boolean norm16HasDecompBoundaryAfter(int n) {
        int n2 = this.minYesNo;
        boolean bl = true;
        boolean bl2 = true;
        boolean bl3 = true;
        if (n > n2 && !this.isHangulLVT(n)) {
            if (n >= this.limitNoNo) {
                if (this.isMaybeOrNonZeroCC(n)) {
                    bl = bl3;
                    if (n > 64512) {
                        bl = n == 65024 ? bl3 : false;
                    }
                    return bl;
                }
                if ((n & 6) > 2) {
                    bl = false;
                }
                return bl;
            }
            n2 = n >> 1;
            n = this.extraData.charAt(n2);
            if (n > 511) {
                return false;
            }
            if (n <= 255) {
                return true;
            }
            bl = bl2;
            if ((n & 128) != 0) {
                bl = (this.extraData.charAt(n2 - 1) & 65280) == 0 ? bl2 : false;
            }
            return bl;
        }
        return true;
    }

    public boolean norm16HasDecompBoundaryBefore(int n) {
        int n2 = this.minNoNoCompNoMaybeCC;
        boolean bl = true;
        boolean bl2 = true;
        if (n < n2) {
            return true;
        }
        if (n >= this.limitNoNo) {
            boolean bl3 = bl2;
            if (n > 64512) {
                bl3 = n == 65024 ? bl2 : false;
            }
            return bl3;
        }
        boolean bl4 = bl;
        if ((this.extraData.charAt(n >>= 1) & 128) != 0) {
            bl4 = (this.extraData.charAt(n - 1) & 65280) == 0 ? bl : false;
        }
        return bl4;
    }

    public boolean singleLeadMightHaveNonZeroFCD16(int n) {
        byte by = this.smallFCD[n >> 8];
        boolean bl = false;
        if (by == 0) {
            return false;
        }
        if ((by >> (n >> 5 & 7) & 1) != 0) {
            bl = true;
        }
        return bl;
    }

    public static final class Hangul {
        public static final int HANGUL_BASE = 44032;
        public static final int HANGUL_COUNT = 11172;
        public static final int HANGUL_END = 55203;
        public static final int HANGUL_LIMIT = 55204;
        public static final int JAMO_L_BASE = 4352;
        public static final int JAMO_L_COUNT = 19;
        public static final int JAMO_L_END = 4370;
        public static final int JAMO_L_LIMIT = 4371;
        public static final int JAMO_T_BASE = 4519;
        public static final int JAMO_T_COUNT = 28;
        public static final int JAMO_T_END = 4546;
        public static final int JAMO_VT_COUNT = 588;
        public static final int JAMO_V_BASE = 4449;
        public static final int JAMO_V_COUNT = 21;
        public static final int JAMO_V_END = 4469;
        public static final int JAMO_V_LIMIT = 4470;

        public static int decompose(int n, Appendable appendable) {
            block3 : {
                int n2 = n - 44032;
                n = n2 % 28;
                try {
                    appendable.append((char)((n2 /= 28) / 21 + 4352));
                    appendable.append((char)(n2 % 21 + 4449));
                    if (n != 0) break block3;
                    return 2;
                }
                catch (IOException iOException) {
                    throw new ICUUncheckedIOException(iOException);
                }
            }
            appendable.append((char)(n + 4519));
            return 3;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public static void getRawDecomposition(int var0, Appendable var1_1) {
            var2_3 = var0 - 44032;
            var3_4 = var2_3 % 28;
            if (var3_4 != 0) ** GOTO lbl11
            try {
                var0 = var2_3 / 28;
                var1_1.append((char)(var0 / 21 + 4352));
                var1_1.append((char)(var0 % 21 + 4449));
                return;
lbl11: // 1 sources:
                var1_1.append((char)(var0 - var3_4));
                var1_1.append((char)(var3_4 + 4519));
                return;
            }
            catch (IOException var1_2) {
                throw new ICUUncheckedIOException(var1_2);
            }
        }

        public static boolean isHangul(int n) {
            boolean bl = 44032 <= n && n < 55204;
            return bl;
        }

        public static boolean isHangulLV(int n) {
            boolean bl = (n -= 44032) >= 0 && n < 11172 && n % 28 == 0;
            return bl;
        }

        public static boolean isJamo(int n) {
            boolean bl = 4352 <= n && n <= 4546 && (n <= 4370 || 4449 <= n && n <= 4469 || 4519 < n);
            return bl;
        }

        public static boolean isJamoL(int n) {
            boolean bl = 4352 <= n && n < 4371;
            return bl;
        }

        public static boolean isJamoT(int n) {
            boolean bl = (n -= 4519) > 0 && n < 28;
            return bl;
        }

        public static boolean isJamoV(int n) {
            boolean bl = 4449 <= n && n < 4470;
            return bl;
        }
    }

    private static final class IsAcceptable
    implements ICUBinary.Authenticate {
        private IsAcceptable() {
        }

        @Override
        public boolean isDataVersionAcceptable(byte[] arrby) {
            boolean bl = false;
            if (arrby[0] == 4) {
                bl = true;
            }
            return bl;
        }
    }

    public static final class ReorderingBuffer
    implements Appendable {
        private final Appendable app;
        private final boolean appIsStringBuilder;
        private int codePointLimit;
        private int codePointStart;
        private final Normalizer2Impl impl;
        private int lastCC;
        private int reorderStart;
        private final StringBuilder str;

        public ReorderingBuffer(Normalizer2Impl normalizer2Impl, Appendable appendable, int n) {
            this.impl = normalizer2Impl;
            this.app = appendable;
            if (this.app instanceof StringBuilder) {
                this.appIsStringBuilder = true;
                this.str = (StringBuilder)appendable;
                this.str.ensureCapacity(n);
                this.reorderStart = 0;
                if (this.str.length() == 0) {
                    this.lastCC = 0;
                } else {
                    this.setIterator();
                    this.lastCC = this.previousCC();
                    if (this.lastCC > 1) {
                        while (this.previousCC() > 1) {
                        }
                    }
                    this.reorderStart = this.codePointLimit;
                }
            } else {
                this.appIsStringBuilder = false;
                this.str = new StringBuilder();
                this.reorderStart = 0;
                this.lastCC = 0;
            }
        }

        private void insert(int n, int n2) {
            this.setIterator();
            this.skipPrevious();
            while (this.previousCC() > n2) {
            }
            if (n <= 65535) {
                this.str.insert(this.codePointLimit, (char)n);
                if (n2 <= 1) {
                    this.reorderStart = this.codePointLimit + 1;
                }
            } else {
                this.str.insert(this.codePointLimit, Character.toChars(n));
                if (n2 <= 1) {
                    this.reorderStart = this.codePointLimit + 2;
                }
            }
        }

        private int previousCC() {
            int n;
            this.codePointLimit = n = this.codePointStart;
            if (this.reorderStart >= n) {
                return 0;
            }
            n = this.str.codePointBefore(n);
            this.codePointStart -= Character.charCount(n);
            return this.impl.getCCFromYesOrMaybeCP(n);
        }

        private void setIterator() {
            this.codePointStart = this.str.length();
        }

        private void skipPrevious() {
            int n;
            this.codePointLimit = n = this.codePointStart;
            this.codePointStart = this.str.offsetByCodePoints(n, -1);
        }

        @Override
        public ReorderingBuffer append(char c) {
            this.str.append(c);
            this.lastCC = 0;
            this.reorderStart = this.str.length();
            return this;
        }

        @Override
        public ReorderingBuffer append(CharSequence charSequence) {
            if (charSequence.length() != 0) {
                this.str.append(charSequence);
                this.lastCC = 0;
                this.reorderStart = this.str.length();
            }
            return this;
        }

        @Override
        public ReorderingBuffer append(CharSequence charSequence, int n, int n2) {
            if (n != n2) {
                this.str.append(charSequence, n, n2);
                this.lastCC = 0;
                this.reorderStart = this.str.length();
            }
            return this;
        }

        public void append(int n, int n2) {
            if (this.lastCC > n2 && n2 != 0) {
                this.insert(n, n2);
            } else {
                this.str.appendCodePoint(n);
                this.lastCC = n2;
                if (n2 <= 1) {
                    this.reorderStart = this.str.length();
                }
            }
        }

        public void append(CharSequence charSequence, int n, int n2, boolean bl, int n3, int n4) {
            if (n == n2) {
                return;
            }
            if (this.lastCC > n3 && n3 != 0) {
                int n5 = Character.codePointAt(charSequence, n);
                n += Character.charCount(n5);
                this.insert(n5, n3);
                while (n < n2) {
                    n5 = Character.codePointAt(charSequence, n);
                    n3 = n + Character.charCount(n5);
                    if (n3 < n2) {
                        if (bl) {
                            n = Normalizer2Impl.getCCFromYesOrMaybe(this.impl.getNorm16(n5));
                        } else {
                            Normalizer2Impl normalizer2Impl = this.impl;
                            n = normalizer2Impl.getCC(normalizer2Impl.getNorm16(n5));
                        }
                    } else {
                        n = n4;
                    }
                    this.append(n5, n);
                    n = n3;
                }
            } else {
                if (n4 <= 1) {
                    this.reorderStart = this.str.length() + (n2 - n);
                } else if (n3 <= 1) {
                    this.reorderStart = this.str.length() + 1;
                }
                this.str.append(charSequence, n, n2);
                this.lastCC = n4;
            }
        }

        public void appendZeroCC(int n) {
            this.str.appendCodePoint(n);
            this.lastCC = 0;
            this.reorderStart = this.str.length();
        }

        public boolean equals(CharSequence charSequence, int n, int n2) {
            StringBuilder stringBuilder = this.str;
            return UTF16Plus.equal(stringBuilder, 0, stringBuilder.length(), charSequence, n, n2);
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public void flush() {
            if (this.appIsStringBuilder) {
                this.reorderStart = this.str.length();
            } else {
                this.app.append(this.str);
                this.str.setLength(0);
                this.reorderStart = 0;
            }
            this.lastCC = 0;
            return;
            catch (IOException iOException) {
                throw new ICUUncheckedIOException(iOException);
            }
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public ReorderingBuffer flushAndAppendZeroCC(CharSequence charSequence, int n, int n2) {
            if (this.appIsStringBuilder) {
                this.str.append(charSequence, n, n2);
                this.reorderStart = this.str.length();
            } else {
                this.app.append(this.str).append(charSequence, n, n2);
                this.str.setLength(0);
                this.reorderStart = 0;
            }
            this.lastCC = 0;
            return this;
            catch (IOException iOException) {
                throw new ICUUncheckedIOException(iOException);
            }
        }

        public int getLastCC() {
            return this.lastCC;
        }

        public StringBuilder getStringBuilder() {
            return this.str;
        }

        public boolean isEmpty() {
            boolean bl = this.str.length() == 0;
            return bl;
        }

        public int length() {
            return this.str.length();
        }

        public void remove() {
            this.str.setLength(0);
            this.lastCC = 0;
            this.reorderStart = 0;
        }

        public void removeSuffix(int n) {
            int n2 = this.str.length();
            this.str.delete(n2 - n, n2);
            this.lastCC = 0;
            this.reorderStart = this.str.length();
        }
    }

    public static final class UTF16Plus {
        public static boolean equal(CharSequence charSequence, int n, int n2, CharSequence charSequence2, int n3, int n4) {
            if (n2 - n != n4 - n3) {
                return false;
            }
            int n5 = n;
            n4 = n3;
            if (charSequence == charSequence2) {
                n5 = n;
                n4 = n3;
                if (n == n3) {
                    return true;
                }
            }
            while (n5 < n2) {
                if (charSequence.charAt(n5) != charSequence2.charAt(n4)) {
                    return false;
                }
                ++n5;
                ++n4;
            }
            return true;
        }

        public static boolean equal(CharSequence charSequence, CharSequence charSequence2) {
            if (charSequence == charSequence2) {
                return true;
            }
            int n = charSequence.length();
            if (n != charSequence2.length()) {
                return false;
            }
            for (int i = 0; i < n; ++i) {
                if (charSequence.charAt(i) == charSequence2.charAt(i)) continue;
                return false;
            }
            return true;
        }

        public static boolean isLeadSurrogate(int n) {
            boolean bl = (n & -1024) == 55296;
            return bl;
        }

        public static boolean isSurrogate(int n) {
            boolean bl = (n & -2048) == 55296;
            return bl;
        }

        public static boolean isSurrogateLead(int n) {
            boolean bl = (n & 1024) == 0;
            return bl;
        }

        public static boolean isTrailSurrogate(int n) {
            boolean bl = (n & -1024) == 56320;
            return bl;
        }
    }

}

