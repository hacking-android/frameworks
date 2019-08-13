/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.coll;

import android.icu.impl.Norm2AllModes;
import android.icu.impl.Normalizer2Impl;
import android.icu.impl.coll.Collation;
import android.icu.impl.coll.CollationData;
import android.icu.impl.coll.CollationDataBuilder;
import android.icu.impl.coll.CollationFastLatin;
import android.icu.impl.coll.CollationIterator;
import android.icu.impl.coll.CollationLoader;
import android.icu.impl.coll.CollationRootElements;
import android.icu.impl.coll.CollationRuleParser;
import android.icu.impl.coll.CollationSettings;
import android.icu.impl.coll.CollationTailoring;
import android.icu.impl.coll.CollationWeights;
import android.icu.impl.coll.SharedObject;
import android.icu.impl.coll.UTF16CollationIterator;
import android.icu.impl.coll.UVector32;
import android.icu.impl.coll.UVector64;
import android.icu.text.CanonicalIterator;
import android.icu.text.Normalizer2;
import android.icu.text.UnicodeSet;
import android.icu.text.UnicodeSetIterator;
import android.icu.util.ULocale;
import java.text.ParseException;

public final class CollationBuilder
extends CollationRuleParser.Sink {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final UnicodeSet COMPOSITES = new UnicodeSet("[:NFD_QC=N:]");
    private static final boolean DEBUG = false;
    private static final int HAS_BEFORE2 = 64;
    private static final int HAS_BEFORE3 = 32;
    private static final int IS_TAILORED = 8;
    private static final int MAX_INDEX = 1048575;
    private CollationTailoring base;
    private CollationData baseData;
    private long[] ces = new long[31];
    private int cesLength;
    private CollationDataBuilder dataBuilder;
    private boolean fastLatinEnabled;
    private Normalizer2 fcd = Norm2AllModes.getFCDNormalizer2();
    private Normalizer2Impl nfcImpl;
    private Normalizer2 nfd = Normalizer2.getNFDInstance();
    private UVector64 nodes;
    private UnicodeSet optimizeSet = new UnicodeSet();
    private CollationRootElements rootElements;
    private UVector32 rootPrimaryIndexes;
    private long variableTop;

    static {
        COMPOSITES.remove(44032, 55203);
    }

    public CollationBuilder(CollationTailoring collationTailoring) {
        this.nfcImpl = Norm2AllModes.getNFCInstance().impl;
        this.base = collationTailoring;
        this.baseData = collationTailoring.data;
        this.rootElements = new CollationRootElements(collationTailoring.data.rootElements);
        this.variableTop = 0L;
        this.dataBuilder = new CollationDataBuilder();
        this.fastLatinEnabled = true;
        this.cesLength = 0;
        this.rootPrimaryIndexes = new UVector32();
        this.nodes = new UVector64();
        this.nfcImpl.ensureCanonIterData();
        this.dataBuilder.initForTailoring(this.baseData);
    }

    private int addIfDifferent(CharSequence charSequence, CharSequence charSequence2, long[] arrl, int n, int n2) {
        long[] arrl2 = new long[31];
        int n3 = n2;
        if (!CollationBuilder.sameCEs(arrl, n, arrl2, this.dataBuilder.getCEs(charSequence, charSequence2, arrl2, 0))) {
            n3 = n2;
            if (n2 == -1) {
                n3 = this.dataBuilder.encodeCEs(arrl, n);
            }
            this.dataBuilder.addCE32(charSequence, charSequence2, n3);
        }
        return n3;
    }

    /*
     * Enabled aggressive block sorting
     */
    private int addOnlyClosure(CharSequence object, CharSequence charSequence, long[] arrl, int n, int n2) {
        if (object.length() == 0) {
            object = new CanonicalIterator(charSequence.toString());
            do {
                String string;
                if ((string = ((CanonicalIterator)object).next()) == null) {
                    return n2;
                }
                if (this.ignoreString(string) || string.contentEquals(charSequence)) continue;
                n2 = this.addIfDifferent("", string, arrl, n, n2);
            } while (true);
        }
        CanonicalIterator canonicalIterator = new CanonicalIterator(object.toString());
        CanonicalIterator canonicalIterator2 = new CanonicalIterator(charSequence.toString());
        String string;
        block1 : while ((string = canonicalIterator.next()) != null) {
            if (this.ignorePrefix(string)) continue;
            boolean bl = string.contentEquals((CharSequence)object);
            do {
                String string2;
                if ((string2 = canonicalIterator2.next()) == null) {
                    canonicalIterator2.reset();
                    continue block1;
                }
                if (this.ignoreString(string2) || bl && string2.contentEquals(charSequence)) continue;
                n2 = this.addIfDifferent(string, string2, arrl, n, n2);
            } while (true);
            break;
        }
        return n2;
    }

    private void addTailComposites(CharSequence charSequence, CharSequence charSequence2) {
        int n = charSequence2.length();
        while (n != 0) {
            int n2 = Character.codePointBefore(charSequence2, n);
            if (this.nfd.getCombiningClass(n2) == 0) {
                if (Normalizer2Impl.Hangul.isJamoL(n2)) {
                    return;
                }
                Object object = new UnicodeSet();
                if (!this.nfcImpl.getCanonStartSet(n2, (UnicodeSet)object)) {
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                StringBuilder stringBuilder2 = new StringBuilder();
                long[] arrl = new long[31];
                object = new UnicodeSetIterator((UnicodeSet)object);
                while (((UnicodeSetIterator)object).next()) {
                    int n3;
                    n2 = ((UnicodeSetIterator)object).codepoint;
                    if (!this.mergeCompositeIntoString(charSequence2, n, n2, this.nfd.getDecomposition(n2), stringBuilder, stringBuilder2) || (n3 = this.dataBuilder.getCEs(charSequence, stringBuilder, arrl, 0)) > 31 || (n2 = this.addIfDifferent(charSequence, stringBuilder2, arrl, n3, -1)) == -1) continue;
                    this.addOnlyClosure(charSequence, stringBuilder, arrl, n3, n2);
                }
                return;
            }
            n -= Character.charCount(n2);
        }
        return;
    }

    private int addWithClosure(CharSequence charSequence, CharSequence charSequence2, long[] arrl, int n, int n2) {
        n = this.addOnlyClosure(charSequence, charSequence2, arrl, n, this.addIfDifferent(charSequence, charSequence2, arrl, n, n2));
        this.addTailComposites(charSequence, charSequence2);
        return n;
    }

    private static final int alignWeightRight(int n) {
        int n2 = n;
        if (n != 0) {
            do {
                n2 = n;
                if ((n & 255) != 0) break;
                n >>>= 8;
            } while (true);
        }
        return n2;
    }

    private static final int binarySearchForRootPrimaryNode(int[] arrn, int n, long[] arrl, long l) {
        if (n == 0) {
            return -1;
        }
        int n2 = 0;
        long l2;
        int n3;
        while (l != (l2 = arrl[arrn[n3 = (int)(((long)n2 + (long)n) / 2L)]] >>> 32)) {
            if (l < l2) {
                if (n3 == n2) {
                    return n2;
                }
                n = n3;
                continue;
            }
            if (n3 == n2) {
                return n2 + 1;
            }
            n2 = n3;
        }
        return n3;
    }

    private static int ceStrength(long l) {
        int n = CollationBuilder.isTempCE(l) ? CollationBuilder.strengthFromTempCE(l) : ((-72057594037927936L & l) != 0L ? 0 : (((int)l & -16777216) != 0 ? 1 : (l != 0L ? 2 : 15)));
        return n;
    }

    private static long changeNodeNextIndex(long l, int n) {
        return -268435201L & l | CollationBuilder.nodeFromNextIndex(n);
    }

    private static long changeNodePreviousIndex(long l, int n) {
        return -281474708275201L & l | CollationBuilder.nodeFromPreviousIndex(n);
    }

    private void closeOverComposites() {
        UnicodeSetIterator unicodeSetIterator = new UnicodeSetIterator(COMPOSITES);
        while (unicodeSetIterator.next()) {
            String string = this.nfd.getDecomposition(unicodeSetIterator.codepoint);
            this.cesLength = this.dataBuilder.getCEs(string, this.ces, 0);
            if (this.cesLength > 31) continue;
            this.addIfDifferent("", unicodeSetIterator.getString(), this.ces, this.cesLength, -1);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static int countTailoredNodes(long[] arrl, int n, int n2) {
        long l;
        int n3 = 0;
        int n4 = n;
        n = n3;
        while (!(n4 == 0 || CollationBuilder.strengthFromNode(l = arrl[n4]) < n2)) {
            n4 = n;
            if (CollationBuilder.strengthFromNode(l) == n2) {
                if (!CollationBuilder.isTailoredNode(l)) return n;
                n4 = n + 1;
            }
            n3 = CollationBuilder.nextIndexFromNode(l);
            n = n4;
            n4 = n3;
        }
        return n;
    }

    private boolean equalSubSequences(CharSequence charSequence, int n, CharSequence charSequence2, int n2) {
        int n3 = charSequence.length();
        int n4 = n;
        int n5 = n2;
        if (n3 - n != charSequence2.length() - n2) {
            return false;
        }
        while (n4 < n3) {
            if (charSequence.charAt(n4) != charSequence2.charAt(n5)) {
                return false;
            }
            ++n4;
            ++n5;
        }
        return true;
    }

    private void finalizeCEs() {
        CollationDataBuilder collationDataBuilder = new CollationDataBuilder();
        collationDataBuilder.initForTailoring(this.baseData);
        CEFinalizer cEFinalizer = new CEFinalizer(this.nodes.getBuffer());
        collationDataBuilder.copyFrom(this.dataBuilder, cEFinalizer);
        this.dataBuilder = collationDataBuilder;
    }

    private int findCommonNode(int n, int n2) {
        long l = this.nodes.elementAti(n);
        if (CollationBuilder.strengthFromNode(l) >= n2) {
            return n;
        }
        if (n2 == 1 ? !CollationBuilder.nodeHasBefore2(l) : !CollationBuilder.nodeHasBefore3(l)) {
            return n;
        }
        n = CollationBuilder.nextIndexFromNode(l);
        l = this.nodes.elementAti(n);
        do {
            long l2;
            if (!CollationBuilder.isTailoredNode(l2 = this.nodes.elementAti(n = CollationBuilder.nextIndexFromNode(l))) && CollationBuilder.strengthFromNode(l2) <= n2) {
                l = l2;
                if (CollationBuilder.weight16FromNode(l2) < 1280) continue;
                return n;
            }
            l = l2;
        } while (true);
    }

    private int findOrInsertNodeForCEs(int n) {
        do {
            int n2;
            long l;
            if ((n2 = this.cesLength--) == 0) {
                this.ces[0] = 0L;
                l = 0L;
                this.cesLength = 1;
            } else {
                l = this.ces[n2 - 1];
                if (CollationBuilder.ceStrength(l) > n) continue;
            }
            if (CollationBuilder.isTempCE(l)) {
                return CollationBuilder.indexFromTempCE(l);
            }
            if ((int)(l >>> 56) != 254) {
                return this.findOrInsertNodeForRootCE(l, n);
            }
            throw new UnsupportedOperationException("tailoring relative to an unassigned code point not supported");
        } while (true);
    }

    private int findOrInsertNodeForPrimary(long l) {
        int n = CollationBuilder.binarySearchForRootPrimaryNode(this.rootPrimaryIndexes.getBuffer(), this.rootPrimaryIndexes.size(), this.nodes.getBuffer(), l);
        if (n >= 0) {
            return this.rootPrimaryIndexes.elementAti(n);
        }
        int n2 = this.nodes.size();
        this.nodes.addElement(CollationBuilder.nodeFromWeight32(l));
        this.rootPrimaryIndexes.insertElementAt(n2, n);
        return n2;
    }

    private int findOrInsertNodeForRootCE(long l, int n) {
        int n2;
        int n3 = n2 = this.findOrInsertNodeForPrimary(l >>> 32);
        if (n >= 1) {
            int n4 = (int)l;
            n3 = n2 = this.findOrInsertWeakNode(n2, n4 >>> 16, 1);
            if (n >= 2) {
                n3 = this.findOrInsertWeakNode(n2, n4 & 16191, 2);
            }
        }
        return n3;
    }

    private int findOrInsertWeakNode(int n, int n2, int n3) {
        long l;
        int n4;
        if (n2 == 1280) {
            return this.findCommonNode(n, n3);
        }
        long l2 = l = this.nodes.elementAti(n);
        int n5 = n;
        if (n2 != 0) {
            l2 = l;
            n5 = n;
            if (n2 < 1280) {
                n4 = n3 == 1 ? 64 : 32;
                l2 = l;
                n5 = n;
                if (((long)n4 & l) == 0L) {
                    long l3 = CollationBuilder.nodeFromWeight16(1280) | CollationBuilder.nodeFromStrength(n3);
                    long l4 = l;
                    l2 = l3;
                    if (n3 == 1) {
                        l2 = l3 | 32L & l;
                        l4 = l & -33L;
                    }
                    this.nodes.setElementAt((long)n4 | l4, n);
                    n5 = CollationBuilder.nextIndexFromNode(l4);
                    n = this.insertNodeBetween(n, n5, CollationBuilder.nodeFromWeight16(n2) | CollationBuilder.nodeFromStrength(n3));
                    this.insertNodeBetween(n, n5, l2);
                    return n;
                }
            }
        }
        while ((n = CollationBuilder.nextIndexFromNode(l2)) != 0) {
            l2 = this.nodes.elementAti(n);
            n4 = CollationBuilder.strengthFromNode(l2);
            if (n4 <= n3) {
                if (n4 < n3) break;
                if (!CollationBuilder.isTailoredNode(l2)) {
                    n4 = CollationBuilder.weight16FromNode(l2);
                    if (n4 == n2) {
                        return n;
                    }
                    if (n4 > n2) break;
                }
            }
            n5 = n;
        }
        return this.insertNodeBetween(n5, n, CollationBuilder.nodeFromWeight16(n2) | CollationBuilder.nodeFromStrength(n3));
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private long getSpecialResetPosition(CharSequence var1_1) {
        block26 : {
            var1_1 = CollationRuleParser.POSITION_VALUES[var1_1.charAt(1) - 10240];
            switch (1.$SwitchMap$android$icu$impl$coll$CollationRuleParser$Position[var1_1.ordinal()]) {
                default: {
                    return 0L;
                }
                case 14: {
                    throw new IllegalArgumentException("LDML forbids tailoring to U+FFFF");
                }
                case 13: {
                    var2_2 = Collation.makeCE(4278321664L);
                    var4_4 = 0;
                    var5_6 = 1;
                    ** GOTO lbl64
                }
                case 12: {
                    throw new UnsupportedOperationException("reset to [last implicit] not supported");
                }
                case 11: {
                    var2_2 = this.baseData.getSingleCE(19968);
                    var4_4 = 0;
                    var5_6 = 0;
                    ** GOTO lbl64
                }
                case 10: {
                    var2_2 = this.rootElements.firstCEWithPrimaryAtLeast(this.baseData.getFirstPrimaryForGroup(17));
                    var4_4 = 0;
                    var5_6 = 0;
                    ** GOTO lbl64
                }
                case 9: {
                    var2_2 = this.rootElements.firstCEWithPrimaryAtLeast(this.variableTop + 1L);
                    var4_4 = 0;
                    var5_6 = 1;
                    ** GOTO lbl64
                }
                case 8: {
                    var2_2 = this.rootElements.lastCEWithPrimaryBefore(this.variableTop + 1L);
                    var4_4 = 0;
                    var5_6 = 0;
                    ** GOTO lbl64
                }
                case 7: {
                    var2_2 = this.rootElements.getFirstPrimaryCE();
                    var4_4 = 0;
                    var5_6 = 1;
                    ** GOTO lbl64
                }
                case 6: {
                    var2_2 = this.rootElements.getLastSecondaryCE();
                    var4_4 = 1;
                    var5_6 = 0;
                    ** GOTO lbl64
                }
                case 5: {
                    var4_4 = this.findOrInsertNodeForRootCE(0L, 1);
                    var2_2 = this.nodes.elementAti(var4_4);
                    do {
                        var4_4 = var5_6 = CollationBuilder.nextIndexFromNode(var2_2);
                        if (var5_6 == 0 || (var5_6 = CollationBuilder.strengthFromNode(var6_7 = this.nodes.elementAti(var4_4))) < 1) ** GOTO lbl56
                        var2_2 = var6_7;
                    } while (var5_6 != 1);
                    if (CollationBuilder.isTailoredNode(var6_7)) {
                        if (CollationBuilder.nodeHasBefore3(var6_7) == false) return CollationBuilder.tempCEFromIndexAndStrength(var4_4, 1);
                        var4_4 = CollationBuilder.nextIndexFromNode(this.nodes.elementAti(CollationBuilder.nextIndexFromNode(var6_7)));
                        return CollationBuilder.tempCEFromIndexAndStrength(var4_4, 1);
                    }
lbl56: // 3 sources:
                    var2_2 = this.rootElements.getFirstSecondaryCE();
                    var4_4 = 1;
                    var5_6 = 0;
                    ** GOTO lbl64
                }
                case 4: {
                    var2_2 = this.rootElements.getLastTertiaryCE();
                    var4_4 = 2;
                    var5_6 = 0;
lbl64: // 9 sources:
                    var8_8 = this.findOrInsertNodeForRootCE(var2_2, var4_4);
                    var9_9 = this.nodes.elementAti(var8_8);
                    var11_10 = var8_8;
                    var6_7 = var9_9;
                    if ((var1_1.ordinal() & 1) == 0) {
                        var11_10 = var8_8;
                        var12_11 = var2_2;
                        var6_7 = var9_9;
                        if (!CollationBuilder.nodeHasAnyBefore(var9_9)) {
                            var11_10 = var8_8;
                            var12_11 = var2_2;
                            var6_7 = var9_9;
                            if (var5_6 != 0) {
                                var11_10 = var5_6 = CollationBuilder.nextIndexFromNode(var9_9);
                                if (var5_6 != 0) {
                                    var6_7 = this.nodes.elementAti(var11_10);
                                    var12_11 = CollationBuilder.tempCEFromIndexAndStrength(var11_10, var4_4);
                                } else {
                                    var5_6 = this.rootElements.findPrimary(var2_2 >>>= 32);
                                    var14_13 = this.baseData.isCompressiblePrimary(var2_2);
                                    var12_11 = Collation.makeCE(this.rootElements.getPrimaryAfter(var2_2, var5_6, var14_13));
                                    var11_10 = this.findOrInsertNodeForRootCE(var12_11, 0);
                                    var6_7 = this.nodes.elementAti(var11_10);
                                }
                            }
                        }
                        var2_2 = var12_11;
                        if (CollationBuilder.nodeHasAnyBefore(var6_7) == false) return var2_2;
                        var2_2 = var6_7;
                        if (CollationBuilder.nodeHasBefore2(var6_7)) {
                            var11_10 = CollationBuilder.nextIndexFromNode(this.nodes.elementAti(CollationBuilder.nextIndexFromNode(var6_7)));
                            var2_2 = this.nodes.elementAti(var11_10);
                        }
                        if (CollationBuilder.nodeHasBefore3(var2_2) == false) return CollationBuilder.tempCEFromIndexAndStrength(var11_10, var4_4);
                        var11_10 = CollationBuilder.nextIndexFromNode(this.nodes.elementAti(CollationBuilder.nextIndexFromNode(var2_2)));
                        return CollationBuilder.tempCEFromIndexAndStrength(var11_10, var4_4);
                    }
                    break block26;
                }
                case 3: {
                    var4_5 = this.findOrInsertNodeForRootCE(0L, 2);
                    var4_5 = CollationBuilder.nextIndexFromNode(this.nodes.elementAti(var4_5));
                    if (var4_5 == 0) return this.rootElements.getFirstTertiaryCE();
                    var2_3 = this.nodes.elementAti(var4_5);
                    if (CollationBuilder.isTailoredNode(var2_3) == false) return this.rootElements.getFirstTertiaryCE();
                    if (CollationBuilder.strengthFromNode(var2_3) != 2) return this.rootElements.getFirstTertiaryCE();
                    return CollationBuilder.tempCEFromIndexAndStrength(var4_5, 2);
                }
                case 2: {
                    return 0L;
                }
                case 1: 
            }
            return 0L;
        }
        do {
            if ((var5_6 = CollationBuilder.nextIndexFromNode(var6_7)) == 0 || CollationBuilder.strengthFromNode(var12_12 = this.nodes.elementAti(var5_6)) < var4_4) {
                if (CollationBuilder.isTailoredNode(var6_7) == false) return var2_2;
                return CollationBuilder.tempCEFromIndexAndStrength(var11_10, var4_4);
            }
            var11_10 = var5_6;
            var6_7 = var12_12;
        } while (true);
    }

    private int getWeight16Before(int n, long l, int n2) {
        int n3;
        n = CollationBuilder.strengthFromNode(l) == 2 ? CollationBuilder.weight16FromNode(l) : 1280;
        while (CollationBuilder.strengthFromNode(l) > 1) {
            n3 = CollationBuilder.previousIndexFromNode(l);
            l = this.nodes.elementAti(n3);
        }
        if (CollationBuilder.isTailoredNode(l)) {
            return 256;
        }
        n3 = CollationBuilder.strengthFromNode(l) == 1 ? CollationBuilder.weight16FromNode(l) : 1280;
        while (CollationBuilder.strengthFromNode(l) > 0) {
            int n4 = CollationBuilder.previousIndexFromNode(l);
            l = this.nodes.elementAti(n4);
        }
        if (CollationBuilder.isTailoredNode(l)) {
            return 256;
        }
        l = CollationBuilder.weight32FromNode(l);
        n = n2 == 1 ? this.rootElements.getSecondaryBefore(l, n3) : this.rootElements.getTertiaryBefore(l, n3, n);
        return n;
    }

    private boolean ignorePrefix(CharSequence charSequence) {
        return this.isFCD(charSequence) ^ true;
    }

    private boolean ignoreString(CharSequence charSequence) {
        boolean bl = this.isFCD(charSequence);
        boolean bl2 = false;
        if (!bl || Normalizer2Impl.Hangul.isHangul(charSequence.charAt(0))) {
            bl2 = true;
        }
        return bl2;
    }

    private static int indexFromTempCE(long l) {
        return (int)((l -= 4629700417037541376L) >> 43) & 1040384 | (int)(l >> 42) & 8128 | (int)(l >> 24) & 63;
    }

    private static int indexFromTempCE32(int n) {
        return (n -= 1077937696) >> 11 & 1040384 | n >> 10 & 8128 | n >> 8 & 63;
    }

    private int insertNodeBetween(int n, int n2, long l) {
        int n3 = this.nodes.size();
        long l2 = CollationBuilder.nodeFromPreviousIndex(n);
        long l3 = CollationBuilder.nodeFromNextIndex(n2);
        this.nodes.addElement(l | (l2 | l3));
        l = this.nodes.elementAti(n);
        this.nodes.setElementAt(CollationBuilder.changeNodeNextIndex(l, n3), n);
        if (n2 != 0) {
            l = this.nodes.elementAti(n2);
            this.nodes.setElementAt(CollationBuilder.changeNodePreviousIndex(l, n3), n2);
        }
        return n3;
    }

    private int insertTailoredNodeAfter(int n, int n2) {
        int n3 = n;
        if (n2 >= 1) {
            n3 = n = this.findCommonNode(n, 1);
            if (n2 >= 2) {
                n3 = this.findCommonNode(n, 2);
            }
        }
        long l = this.nodes.elementAti(n3);
        while ((n = CollationBuilder.nextIndexFromNode(l)) != 0 && CollationBuilder.strengthFromNode(l = this.nodes.elementAti(n)) > n2) {
            n3 = n;
        }
        return this.insertNodeBetween(n3, n, CollationBuilder.nodeFromStrength(n2) | 8L);
    }

    private boolean isFCD(CharSequence charSequence) {
        return this.fcd.isNormalized(charSequence);
    }

    private static boolean isTailoredNode(long l) {
        boolean bl = (8L & l) != 0L;
        return bl;
    }

    private static boolean isTempCE(long l) {
        int n = (int)l >>> 24;
        boolean bl = 6 <= n && n <= 69;
        return bl;
    }

    private static boolean isTempCE32(int n) {
        boolean bl = (n & 255) >= 2 && 6 <= (n >> 8 & 255) && (n >> 8 & 255) <= 69;
        return bl;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void makeTailoredCEs() {
        CollationWeights collationWeights = new CollationWeights();
        CollationWeights collationWeights2 = new CollationWeights();
        CollationWeights collationWeights3 = new CollationWeights();
        long[] arrl = this.nodes.getBuffer();
        for (int i = 0; i < this.rootPrimaryIndexes.size(); ++i) {
            long l = arrl[this.rootPrimaryIndexes.elementAti(i)];
            long l2 = CollationBuilder.weight32FromNode(l);
            int n = l2 == 0L ? 0 : 1280;
            int n2 = l2 == 0L ? 0 : this.rootElements.findPrimary(l2);
            int n3 = CollationBuilder.nextIndexFromNode(l);
            int n4 = n;
            int n5 = 0;
            int n6 = 0;
            int n7 = 0;
            int n8 = 0;
            while (n3 != 0) {
                l = arrl[n3];
                int n9 = CollationBuilder.nextIndexFromNode(l);
                int n10 = CollationBuilder.strengthFromNode(l);
                if (n10 == 3) {
                    if (n5 == 3) throw new UnsupportedOperationException("quaternary tailoring gap too small");
                    n10 = n5 + 1;
                    n5 = n2;
                    n2 = n10;
                    n10 = n8;
                } else {
                    if (n10 == 2) {
                        if (CollationBuilder.isTailoredNode(l)) {
                            if (n8 == 0) {
                                n10 = CollationBuilder.countTailoredNodes(arrl, n9, 2);
                                if (n4 == 0) {
                                    n8 = this.rootElements.getTertiaryBoundary() - 256;
                                    n4 = (int)this.rootElements.getFirstTertiaryCE() & 16191;
                                } else if (n6 == 0 && n7 == 0) {
                                    n5 = this.rootElements.getTertiaryAfter(n2, n, n4);
                                    n8 = n4;
                                    n4 = n5;
                                } else if (n4 == 256) {
                                    n5 = 1280;
                                    n8 = n4;
                                    n4 = n5;
                                } else {
                                    n5 = this.rootElements.getTertiaryBoundary();
                                    n8 = n4;
                                    n4 = n5;
                                }
                                collationWeights3.initForTertiary();
                                if (!collationWeights3.allocWeights(n8, n4, n10 + 1)) throw new UnsupportedOperationException("tertiary tailoring gap too small");
                                n5 = 1;
                            } else {
                                n5 = n8;
                            }
                            n4 = (int)collationWeights3.nextWeight();
                            n8 = n6;
                            n6 = n5;
                        } else {
                            n4 = CollationBuilder.weight16FromNode(l);
                            n5 = 0;
                            n8 = n6;
                            n6 = n5;
                        }
                    } else {
                        if (n10 == 1) {
                            if (CollationBuilder.isTailoredNode(l)) {
                                if (n7 == 0) {
                                    n8 = CollationBuilder.countTailoredNodes(arrl, n9, 1);
                                    if (n == 0) {
                                        n = this.rootElements.getSecondaryBoundary() - 256;
                                        n4 = (int)(this.rootElements.getFirstSecondaryCE() >> 16);
                                    } else {
                                        n4 = n6 == 0 ? this.rootElements.getSecondaryAfter(n2, n) : (n == 256 ? 1280 : this.rootElements.getSecondaryBoundary());
                                    }
                                    n7 = n;
                                    if (n == 1280) {
                                        n7 = this.rootElements.getLastCommonSecondary();
                                    }
                                    collationWeights2.initForSecondary();
                                    if (!collationWeights2.allocWeights(n7, n4, n8 + 1)) throw new UnsupportedOperationException("secondary tailoring gap too small");
                                    n7 = 1;
                                }
                                n4 = (int)collationWeights2.nextWeight();
                                n8 = n6;
                            } else {
                                n4 = CollationBuilder.weight16FromNode(l);
                                n7 = 0;
                                n8 = n6;
                            }
                        } else {
                            if (n6 == 0) {
                                n6 = CollationBuilder.countTailoredNodes(arrl, n9, 0);
                                boolean bl = this.baseData.isCompressiblePrimary(l2);
                                long l3 = this.rootElements.getPrimaryAfter(l2, n2, bl);
                                collationWeights.initForPrimary(bl);
                                if (!collationWeights.allocWeights(l2, l3, n6 + 1)) throw new UnsupportedOperationException("primary tailoring gap too small");
                                n6 = 1;
                            }
                            l2 = collationWeights.nextWeight();
                            n4 = 1280;
                            n7 = 0;
                            n8 = n6;
                        }
                        n5 = n4 == 0 ? 0 : 1280;
                        n6 = 0;
                        n = n4;
                        n4 = n5;
                    }
                    int n11 = 0;
                    n10 = n6;
                    n6 = n8;
                    n5 = n2;
                    n2 = n11;
                }
                if (CollationBuilder.isTailoredNode(l)) {
                    arrl[n3] = Collation.makeCE(l2, n, n4, n2);
                }
                n3 = n9;
                n8 = n2;
                n2 = n5;
                n5 = n8;
                n8 = n10;
            }
        }
    }

    private boolean mergeCompositeIntoString(CharSequence charSequence, int n, int n2, CharSequence charSequence2, StringBuilder stringBuilder, StringBuilder stringBuilder2) {
        int n3 = Character.offsetByCodePoints(charSequence2, 0, 1);
        if (n3 == charSequence2.length()) {
            return false;
        }
        if (this.equalSubSequences(charSequence, n, charSequence2, n3)) {
            return false;
        }
        stringBuilder.setLength(0);
        stringBuilder.append(charSequence, 0, n);
        stringBuilder2.setLength(0);
        stringBuilder2.append(charSequence, 0, n - n3);
        stringBuilder2.appendCodePoint(n2);
        n2 = -1;
        int n4 = 0;
        int n5 = 0;
        int n6 = n;
        do {
            int n7;
            block18 : {
                block17 : {
                    block15 : {
                        block16 : {
                            n = n2;
                            n7 = n4;
                            if (n2 >= 0) break block15;
                            if (n6 < charSequence.length()) break block16;
                            n = n2;
                            n7 = n4;
                            break block17;
                        }
                        n = Character.codePointAt(charSequence, n6);
                        n7 = this.nfd.getCombiningClass(n);
                    }
                    if (n3 < charSequence2.length()) break block18;
                }
                if (n >= 0) {
                    if (n7 < n5) {
                        return false;
                    }
                    stringBuilder.append(charSequence, n6, charSequence.length());
                    stringBuilder2.append(charSequence, n6, charSequence.length());
                } else if (n3 < charSequence2.length()) {
                    stringBuilder.append(charSequence2, n3, charSequence2.length());
                }
                return true;
            }
            n4 = Character.codePointAt(charSequence2, n3);
            n5 = this.nfd.getCombiningClass(n4);
            if (n5 == 0) {
                return false;
            }
            if (n7 < n5) {
                return false;
            }
            if (n5 < n7) {
                stringBuilder.appendCodePoint(n4);
                n2 = n3 + Character.charCount(n4);
            } else {
                if (n4 != n) {
                    return false;
                }
                stringBuilder.appendCodePoint(n4);
                n2 = n3 + Character.charCount(n4);
                n6 += Character.charCount(n4);
                n = -1;
            }
            n3 = n2;
            n2 = n;
            n4 = n7;
        } while (true);
    }

    private static int nextIndexFromNode(long l) {
        return (int)l >> 8 & 1048575;
    }

    private static long nodeFromNextIndex(int n) {
        return n << 8;
    }

    private static long nodeFromPreviousIndex(int n) {
        return (long)n << 28;
    }

    private static long nodeFromStrength(int n) {
        return n;
    }

    private static long nodeFromWeight16(int n) {
        return (long)n << 48;
    }

    private static long nodeFromWeight32(long l) {
        return l << 32;
    }

    private static boolean nodeHasAnyBefore(long l) {
        boolean bl = (96L & l) != 0L;
        return bl;
    }

    private static boolean nodeHasBefore2(long l) {
        boolean bl = (64L & l) != 0L;
        return bl;
    }

    private static boolean nodeHasBefore3(long l) {
        boolean bl = (32L & l) != 0L;
        return bl;
    }

    private static int previousIndexFromNode(long l) {
        return (int)(l >> 28) & 1048575;
    }

    private static boolean sameCEs(long[] arrl, int n, long[] arrl2, int n2) {
        if (n != n2) {
            return false;
        }
        for (n2 = 0; n2 < n; ++n2) {
            if (arrl[n2] == arrl2[n2]) continue;
            return false;
        }
        return true;
    }

    private void setCaseBits(CharSequence object) {
        long l;
        int n;
        long l2;
        int n2;
        int n3 = 0;
        for (n2 = 0; n2 < this.cesLength; ++n2) {
            n = n3;
            if (CollationBuilder.ceStrength(this.ces[n2]) == 0) {
                n = n3 + 1;
            }
            n3 = n;
        }
        long l3 = l = 0L;
        if (n3 > 0) {
            int n4;
            int n5;
            object = new UTF16CollationIterator(this.baseData, false, (CharSequence)object, 0);
            int n6 = ((CollationIterator)object).fetchCEs();
            n2 = 0;
            n = 0;
            int n7 = 0;
            do {
                n5 = n2;
                n4 = n;
                if (n7 >= n6 - 1) break;
                l2 = ((CollationIterator)object).getCE(n7);
                l3 = l;
                n4 = n2;
                n5 = n++;
                if (l2 >>> 32 != 0L) {
                    int n8 = (int)l2 >> 14 & 3;
                    if (n < n3) {
                        l3 = l | (long)n8 << (n - 1) * 2;
                        n4 = n2;
                        n5 = n;
                    } else if (n == n3) {
                        n4 = n8;
                        l3 = l;
                        n5 = n;
                    } else {
                        l3 = l;
                        n4 = n2;
                        n5 = n;
                        if (n8 != n2) {
                            n5 = 1;
                            n4 = n;
                            break;
                        }
                    }
                }
                ++n7;
                l = l3;
                n2 = n4;
                n = n5;
            } while (true);
            l3 = l;
            if (n4 >= n3) {
                l3 = l | (long)n5 << (n3 - 1) * 2;
            }
        }
        l = l3;
        for (n2 = 0; n2 < this.cesLength; ++n2) {
            long l4 = this.ces[n2] & -49153L;
            n = CollationBuilder.ceStrength(l4);
            if (n == 0) {
                l3 = l4 | (3L & l) << 14;
                l2 = l >>> 2;
            } else {
                l2 = l;
                l3 = l4;
                if (n == 2) {
                    l3 = l4 | 32768L;
                    l2 = l;
                }
            }
            this.ces[n2] = l3;
            l = l2;
        }
    }

    private static int strengthFromNode(long l) {
        return (int)l & 3;
    }

    private static int strengthFromTempCE(long l) {
        return (int)l >> 8 & 3;
    }

    private static long tempCEFromIndexAndStrength(int n, int n2) {
        return ((long)(1040384 & n) << 43) + 4629700417037541376L + ((long)(n & 8128) << 42) + (long)((n & 63) << 24) + (long)(n2 << 8);
    }

    private static int weight16FromNode(long l) {
        return (int)(l >> 48) & 65535;
    }

    private static long weight32FromNode(long l) {
        return l >>> 32;
    }

    @Override
    void addRelation(int n, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        int n2;
        String string;
        String string2;
        block17 : {
            int n3;
            block16 : {
                string2 = charSequence.length() == 0 ? "" : this.nfd.normalize(charSequence);
                string = this.nfd.normalize(charSequence2);
                n3 = string.length();
                if (n3 >= 2) {
                    n2 = string.charAt(0);
                    if (!Normalizer2Impl.Hangul.isJamoL(n2) && !Normalizer2Impl.Hangul.isJamoV(n2)) {
                        n2 = string.charAt(n3 - 1);
                        if (Normalizer2Impl.Hangul.isJamoL(n2) || Normalizer2Impl.Hangul.isJamoV(n2) && Normalizer2Impl.Hangul.isJamoL(string.charAt(n3 - 2))) {
                            throw new UnsupportedOperationException("contractions ending with conjoining Jamo L or L+V not supported");
                        }
                    } else {
                        throw new UnsupportedOperationException("contractions starting with conjoining Jamo L or V not supported");
                    }
                }
                if (n != 15) {
                    n3 = this.findOrInsertNodeForCEs(n);
                    long l = this.ces[this.cesLength - 1];
                    if (n == 0 && !CollationBuilder.isTempCE(l) && l >>> 32 == 0L) {
                        throw new UnsupportedOperationException("tailoring primary after ignorables not supported");
                    }
                    if (n == 3 && l == 0L) {
                        throw new UnsupportedOperationException("tailoring quaternary after tertiary ignorables not supported");
                    }
                    int n4 = this.insertTailoredNodeAfter(n3, n);
                    n3 = n2 = CollationBuilder.ceStrength(l);
                    if (n < n2) {
                        n3 = n;
                    }
                    this.ces[this.cesLength - 1] = CollationBuilder.tempCEFromIndexAndStrength(n4, n3);
                }
                this.setCaseBits(string);
                n2 = this.cesLength;
                if (charSequence3.length() != 0) {
                    charSequence3 = this.nfd.normalize(charSequence3);
                    this.cesLength = this.dataBuilder.getCEs(charSequence3, this.ces, this.cesLength);
                    if (this.cesLength > 31) {
                        throw new IllegalArgumentException("extension string adds too many collation elements (more than 31 total)");
                    }
                }
                n3 = -1;
                if (!string2.contentEquals(charSequence)) break block16;
                n = n3;
                if (string.contentEquals(charSequence2)) break block17;
            }
            n = n3;
            if (!this.ignorePrefix(charSequence)) {
                n = n3;
                if (!this.ignoreString(charSequence2)) {
                    n = this.addIfDifferent(charSequence, charSequence2, this.ces, this.cesLength, -1);
                }
            }
        }
        this.addWithClosure(string2, string, this.ces, this.cesLength, n);
        this.cesLength = n2;
    }

    @Override
    void addReset(int n, CharSequence charSequence) {
        block17 : {
            int n2;
            int n3;
            block13 : {
                block14 : {
                    long l;
                    block19 : {
                        block18 : {
                            block16 : {
                                block15 : {
                                    if (charSequence.charAt(0) != '\ufffe') break block15;
                                    this.ces[0] = this.getSpecialResetPosition(charSequence);
                                    this.cesLength = 1;
                                    break block16;
                                }
                                charSequence = this.nfd.normalize(charSequence);
                                this.cesLength = this.dataBuilder.getCEs(charSequence, this.ces, 0);
                                if (this.cesLength > 31) break block17;
                            }
                            if (n == 15) {
                                return;
                            }
                            n3 = this.findOrInsertNodeForCEs(n);
                            l = this.nodes.elementAti(n3);
                            while (CollationBuilder.strengthFromNode(l) > n) {
                                n3 = CollationBuilder.previousIndexFromNode(l);
                                l = this.nodes.elementAti(n3);
                            }
                            if (CollationBuilder.strengthFromNode(l) != n || !CollationBuilder.isTailoredNode(l)) break block18;
                            n3 = CollationBuilder.previousIndexFromNode(l);
                            n2 = n;
                            break block13;
                        }
                        if (n == 0) {
                            if ((l = CollationBuilder.weight32FromNode(l)) != 0L) {
                                if (l > this.rootElements.getFirstPrimary()) {
                                    if (l != 4278321664L) {
                                        n3 = this.findOrInsertNodeForPrimary(this.rootElements.getPrimaryBefore(l, this.baseData.isCompressiblePrimary(l)));
                                        do {
                                            if ((n2 = CollationBuilder.nextIndexFromNode(this.nodes.elementAti(n3))) == 0) {
                                                n2 = n;
                                                break block13;
                                            }
                                            n3 = n2;
                                        } while (true);
                                    }
                                    throw new UnsupportedOperationException("reset primary-before [first trailing] not supported");
                                }
                                throw new UnsupportedOperationException("reset primary-before first non-ignorable not supported");
                            }
                            throw new UnsupportedOperationException("reset primary-before ignorable not possible");
                        }
                        n3 = n2 = this.findCommonNode(n3, 1);
                        if (n >= 2) {
                            n3 = this.findCommonNode(n2, 2);
                        }
                        if (CollationBuilder.strengthFromNode(l = this.nodes.elementAti(n3)) != n) break block19;
                        if (CollationBuilder.weight16FromNode(l) == 0) {
                            charSequence = n == 1 ? "reset secondary-before secondary ignorable not possible" : "reset tertiary-before completely ignorable not possible";
                            throw new UnsupportedOperationException((String)charSequence);
                        }
                        int n4 = this.getWeight16Before(n3, l, n);
                        int n5 = n2 = CollationBuilder.previousIndexFromNode(l);
                        do {
                            block22 : {
                                block21 : {
                                    block20 : {
                                        l = this.nodes.elementAti(n5);
                                        n5 = CollationBuilder.strengthFromNode(l);
                                        if (n5 >= n) break block20;
                                        n5 = 1280;
                                        break block21;
                                    }
                                    if (n5 != n || CollationBuilder.isTailoredNode(l)) break block22;
                                    n5 = CollationBuilder.weight16FromNode(l);
                                }
                                n = n5 == n4 ? n2 : this.insertNodeBetween(n2, n3, CollationBuilder.nodeFromWeight16(n4) | CollationBuilder.nodeFromStrength(n));
                                break block14;
                            }
                            n5 = CollationBuilder.previousIndexFromNode(l);
                        } while (true);
                    }
                    n = this.findOrInsertWeakNode(n3, this.getWeight16Before(n3, l, n), n);
                }
                n2 = CollationBuilder.ceStrength(this.ces[this.cesLength - 1]);
                n3 = n;
            }
            this.ces[this.cesLength - 1] = CollationBuilder.tempCEFromIndexAndStrength(n3, n2);
            return;
        }
        throw new IllegalArgumentException("reset position maps to too many collation elements (more than 31)");
    }

    @Override
    void optimize(UnicodeSet unicodeSet) {
        this.optimizeSet.addAll(unicodeSet);
    }

    public CollationTailoring parseAndBuild(String string) throws ParseException {
        if (this.baseData.rootElements != null) {
            CollationTailoring collationTailoring = new CollationTailoring(this.base.settings);
            CollationRuleParser collationRuleParser = new CollationRuleParser(this.baseData);
            this.variableTop = this.base.settings.readOnly().variableTop;
            collationRuleParser.setSink(this);
            collationRuleParser.setImporter(new BundleImporter());
            CollationSettings collationSettings = collationTailoring.settings.copyOnWrite();
            collationRuleParser.parse(string, collationSettings);
            if (this.dataBuilder.hasMappings()) {
                this.makeTailoredCEs();
                this.closeOverComposites();
                this.finalizeCEs();
                this.optimizeSet.add(0, 127);
                this.optimizeSet.add(192, 255);
                this.optimizeSet.remove(44032, 55203);
                this.dataBuilder.optimize(this.optimizeSet);
                collationTailoring.ensureOwnedData();
                if (this.fastLatinEnabled) {
                    this.dataBuilder.enableFastLatin();
                }
                this.dataBuilder.build(collationTailoring.ownedData);
                this.dataBuilder = null;
            } else {
                collationTailoring.data = this.baseData;
            }
            collationSettings.fastLatinOptions = CollationFastLatin.getOptions(collationTailoring.data, collationSettings, collationSettings.fastLatinPrimaries);
            collationTailoring.setRules(string);
            collationTailoring.setVersion(this.base.version, 0);
            return collationTailoring;
        }
        throw new UnsupportedOperationException("missing root elements data, tailoring not supported");
    }

    @Override
    void suppressContractions(UnicodeSet unicodeSet) {
        this.dataBuilder.suppressContractions(unicodeSet);
    }

    private static final class BundleImporter
    implements CollationRuleParser.Importer {
        BundleImporter() {
        }

        @Override
        public String getRules(String string, String string2) {
            return CollationLoader.loadRules(new ULocale(string), string2);
        }
    }

    private static final class CEFinalizer
    implements CollationDataBuilder.CEModifier {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private long[] finalCEs;

        CEFinalizer(long[] arrl) {
            this.finalCEs = arrl;
        }

        @Override
        public long modifyCE(long l) {
            if (CollationBuilder.isTempCE(l)) {
                return this.finalCEs[CollationBuilder.indexFromTempCE(l)] | 49152L & l;
            }
            return 0x101000100L;
        }

        @Override
        public long modifyCE32(int n) {
            if (CollationBuilder.isTempCE32(n)) {
                return this.finalCEs[CollationBuilder.indexFromTempCE32(n)] | (long)((n & 192) << 8);
            }
            return 0x101000100L;
        }
    }

}

