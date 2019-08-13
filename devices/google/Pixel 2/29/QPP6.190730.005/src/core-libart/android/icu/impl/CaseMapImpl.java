/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.Trie2_16;
import android.icu.impl.UCaseProps;
import android.icu.impl.UCharacterProperty;
import android.icu.text.BreakIterator;
import android.icu.text.Edits;
import android.icu.util.ICUUncheckedIOException;
import android.icu.util.ULocale;
import java.io.IOException;
import java.text.CharacterIterator;
import java.util.Locale;

public final class CaseMapImpl {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Trie2_16 CASE_TRIE = UCaseProps.getTrie();
    private static final int LNS = 251792942;
    public static final int OMIT_UNCHANGED_TEXT = 16384;
    private static final int TITLECASE_ADJUSTMENT_MASK = 1536;
    public static final int TITLECASE_ADJUST_TO_CASED = 1024;
    private static final int TITLECASE_ITERATOR_MASK = 224;
    public static final int TITLECASE_SENTENCES = 64;
    public static final int TITLECASE_WHOLE_STRING = 32;

    public static int addTitleAdjustmentOption(int n, int n2) {
        int n3 = n & 1536;
        if (n3 != 0 && n3 != n2) {
            throw new IllegalArgumentException("multiple titlecasing index adjustment options");
        }
        return n | n2;
    }

    public static int addTitleIteratorOption(int n, int n2) {
        int n3 = n & 224;
        if (n3 != 0 && n3 != n2) {
            throw new IllegalArgumentException("multiple titlecasing iterator options");
        }
        return n | n2;
    }

    private static int appendCodePoint(Appendable appendable, int n) throws IOException {
        if (n <= 65535) {
            appendable.append((char)n);
            return 1;
        }
        appendable.append((char)((n >> 10) + 55232));
        appendable.append((char)((n & 1023) + 56320));
        return 2;
    }

    private static void appendResult(int n, Appendable appendable, int n2, int n3, Edits edits) throws IOException {
        if (n < 0) {
            if (edits != null) {
                edits.addUnchanged(n2);
            }
            if ((n3 & 16384) != 0) {
                return;
            }
            CaseMapImpl.appendCodePoint(appendable, n);
        } else if (n <= 31) {
            if (edits != null) {
                edits.addReplace(n2, n);
            }
        } else {
            n = CaseMapImpl.appendCodePoint(appendable, n);
            if (edits != null) {
                edits.addReplace(n2, n);
            }
        }
    }

    private static final void appendUnchanged(CharSequence charSequence, int n, int n2, Appendable appendable, int n3, Edits edits) throws IOException {
        if (n2 > 0) {
            if (edits != null) {
                edits.addUnchanged(n2);
            }
            if ((n3 & 16384) != 0) {
                return;
            }
            appendable.append(charSequence, n, n + n2);
        }
    }

    private static String applyEdits(CharSequence charSequence, StringBuilder stringBuilder, Edits object) {
        if (!((Edits)object).hasChanges()) {
            return charSequence.toString();
        }
        StringBuilder stringBuilder2 = new StringBuilder(charSequence.length() + ((Edits)object).lengthDelta());
        object = ((Edits)object).getCoarseIterator();
        while (((Edits.Iterator)object).next()) {
            int n;
            if (((Edits.Iterator)object).hasChange()) {
                n = ((Edits.Iterator)object).replacementIndex();
                stringBuilder2.append(stringBuilder, n, ((Edits.Iterator)object).newLength() + n);
                continue;
            }
            n = ((Edits.Iterator)object).sourceIndex();
            stringBuilder2.append(charSequence, n, ((Edits.Iterator)object).oldLength() + n);
        }
        return stringBuilder2.toString();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static <A extends Appendable> A fold(int var0, CharSequence var1_1, A var2_3, Edits var3_4) {
        if (var3_4 == null) ** GOTO lbl4
        try {
            var3_4.reset();
lbl4: // 2 sources:
            CaseMapImpl.internalToLower(-1, var0, var1_1, 0, var1_1.length(), null, var2_3, var3_4);
        }
        catch (IOException var1_2) {
            throw new ICUUncheckedIOException(var1_2);
        }
        return var2_3;
    }

    public static String fold(int n, CharSequence charSequence) {
        if (charSequence.length() <= 100 && (n & 16384) == 0) {
            if (charSequence.length() == 0) {
                return charSequence.toString();
            }
            Edits edits = new Edits();
            return CaseMapImpl.applyEdits(charSequence, CaseMapImpl.fold(n | 16384, charSequence, new StringBuilder(), edits), edits);
        }
        return CaseMapImpl.fold(n, charSequence, new StringBuilder(charSequence.length()), null).toString();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static BreakIterator getTitleBreakIterator(ULocale uLocale, int n, BreakIterator breakIterator) {
        if ((n &= 224) != 0) {
            if (breakIterator != null) throw new IllegalArgumentException("titlecasing iterator option together with an explicit iterator");
        }
        BreakIterator breakIterator2 = breakIterator;
        if (breakIterator != null) return breakIterator2;
        if (n == 0) return BreakIterator.getWordInstance(uLocale);
        if (n == 32) return new WholeStringBreakIterator();
        if (n != 64) throw new IllegalArgumentException("unknown titlecasing iterator option");
        return BreakIterator.getSentenceInstance(uLocale);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static BreakIterator getTitleBreakIterator(Locale locale, int n, BreakIterator breakIterator) {
        if ((n &= 224) != 0) {
            if (breakIterator != null) throw new IllegalArgumentException("titlecasing iterator option together with an explicit iterator");
        }
        BreakIterator breakIterator2 = breakIterator;
        if (breakIterator != null) return breakIterator2;
        if (n == 0) return BreakIterator.getWordInstance(locale);
        if (n == 32) return new WholeStringBreakIterator();
        if (n != 64) throw new IllegalArgumentException("unknown titlecasing iterator option");
        return BreakIterator.getSentenceInstance(locale);
    }

    private static void internalToLower(int n, int n2, CharSequence charSequence, int n3, int n4, StringContextIterator stringContextIterator, Appendable appendable, Edits edits) throws IOException {
        int n5 = n;
        byte[] arrby = n5 != 1 && !(n5 >= 0 ? n5 != 2 && n5 != 3 : (n2 & 7) == 0) ? UCaseProps.LatinCase.TO_LOWER_TR_LT : UCaseProps.LatinCase.TO_LOWER_NORMAL;
        int n6 = n3;
        do {
            int n7;
            block17 : {
                block18 : {
                    char c;
                    int n8;
                    int n9;
                    block16 : {
                        block15 : {
                            n8 = n;
                            if (n3 >= n4) {
                                CaseMapImpl.appendUnchanged(charSequence, n6, n3 - n6, appendable, n2, edits);
                                return;
                            }
                            n7 = charSequence.charAt(n3);
                            if (n7 >= 383) break block15;
                            n5 = arrby[n7];
                            if (n5 == -128) break block16;
                            ++n3;
                            if (n5 == 0) {
                                continue;
                            }
                            break block17;
                        }
                        if (n7 < 55296 && !UCaseProps.propsHasException(n5 = CASE_TRIE.getFromU16SingleLead((char)n7))) break block18;
                    }
                    n5 = n3 + 1;
                    if (Character.isHighSurrogate((char)n7) && n5 < n4 && Character.isLowSurrogate(c = charSequence.charAt(n5))) {
                        n9 = Character.toCodePoint((char)n7, c);
                        ++n5;
                    } else {
                        n9 = n7;
                    }
                    if (n8 >= 0) {
                        if (stringContextIterator == null) {
                            stringContextIterator = new StringContextIterator(charSequence, n3, n5);
                        } else {
                            stringContextIterator.setCPStartAndLimit(n3, n5);
                        }
                        n8 = UCaseProps.INSTANCE.toFullLower(n9, stringContextIterator, appendable, n8);
                    } else {
                        n8 = UCaseProps.INSTANCE.toFullFolding(n9, appendable, n2);
                    }
                    if (n8 >= 0) {
                        n9 = n5;
                        CaseMapImpl.appendUnchanged(charSequence, n6, n3 - n6, appendable, n2, edits);
                        CaseMapImpl.appendResult(n8, appendable, n9 - n3, n2, edits);
                        n6 = n9;
                    }
                    n3 = n5;
                    continue;
                }
                ++n3;
                if (!UCaseProps.isUpperOrTitleFromProps(n5) || (n5 = UCaseProps.getDelta(n5)) == 0) continue;
            }
            n7 = (char)(n7 + n5);
            CaseMapImpl.appendUnchanged(charSequence, n6, n3 - 1 - n6, appendable, n2, edits);
            appendable.append((char)n7);
            if (edits != null) {
                edits.addReplace(1, 1);
            }
            n6 = n3;
        } while (true);
    }

    private static void internalToUpper(int n, int n2, CharSequence charSequence, Appendable appendable, Edits edits) throws IOException {
        byte[] arrby = n == 2 ? UCaseProps.LatinCase.TO_UPPER_TR : UCaseProps.LatinCase.TO_UPPER_NORMAL;
        int n3 = charSequence.length();
        StringContextIterator stringContextIterator = null;
        int n4 = 0;
        int n5 = 0;
        do {
            int n6;
            int n7;
            block15 : {
                block16 : {
                    int n8;
                    char c;
                    block14 : {
                        block13 : {
                            if (n5 >= n3) {
                                CaseMapImpl.appendUnchanged(charSequence, n4, n5 - n4, appendable, n2, edits);
                                return;
                            }
                            n6 = charSequence.charAt(n5);
                            if (n6 >= 383) break block13;
                            n7 = arrby[n6];
                            if (n7 == -128) break block14;
                            ++n5;
                            if (n7 == 0) {
                                continue;
                            }
                            break block15;
                        }
                        if (n6 < 55296 && !UCaseProps.propsHasException(n7 = CASE_TRIE.getFromU16SingleLead((char)n6))) break block16;
                    }
                    n7 = n5 + 1;
                    if (Character.isHighSurrogate((char)n6) && n7 < n3 && Character.isLowSurrogate(c = charSequence.charAt(n7))) {
                        n8 = Character.toCodePoint((char)n6, c);
                        ++n7;
                    } else {
                        n8 = n6;
                    }
                    if (stringContextIterator == null) {
                        stringContextIterator = new StringContextIterator(charSequence, n5, n7);
                    } else {
                        stringContextIterator.setCPStartAndLimit(n5, n7);
                    }
                    int n9 = UCaseProps.INSTANCE.toFullUpper(n8, stringContextIterator, appendable, n);
                    if (n9 >= 0) {
                        n8 = n7;
                        CaseMapImpl.appendUnchanged(charSequence, n4, n5 - n4, appendable, n2, edits);
                        CaseMapImpl.appendResult(n9, appendable, n8 - n5, n2, edits);
                        n4 = n8;
                    }
                    n5 = n7;
                    continue;
                }
                ++n5;
                if (UCaseProps.getTypeFromProps(n7) != 1 || (n7 = UCaseProps.getDelta(n7)) == 0) continue;
            }
            n6 = (char)(n6 + n7);
            CaseMapImpl.appendUnchanged(charSequence, n4, n5 - 1 - n4, appendable, n2, edits);
            appendable.append((char)n6);
            if (edits != null) {
                edits.addReplace(1, 1);
            }
            n4 = n5;
        } while (true);
    }

    private static boolean isLNS(int n) {
        boolean bl;
        block0 : {
            int n2 = UCharacterProperty.INSTANCE.getType(n);
            bl = true;
            if ((1 << n2 & 251792942) != 0 || n2 == 4 && UCaseProps.INSTANCE.getType(n) != 0) break block0;
            bl = false;
        }
        return bl;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static <A extends Appendable> A toLower(int var0, int var1_1, CharSequence var2_2, A var3_4, Edits var4_5) {
        if (var4_5 == null) ** GOTO lbl4
        try {
            var4_5.reset();
lbl4: // 2 sources:
            CaseMapImpl.internalToLower(var0, var1_1, var2_2, 0, var2_2.length(), null, var3_4, var4_5);
        }
        catch (IOException var2_3) {
            throw new ICUUncheckedIOException(var2_3);
        }
        return var3_4;
    }

    public static String toLower(int n, int n2, CharSequence charSequence) {
        if (charSequence.length() <= 100 && (n2 & 16384) == 0) {
            if (charSequence.length() == 0) {
                return charSequence.toString();
            }
            Edits edits = new Edits();
            return CaseMapImpl.applyEdits(charSequence, CaseMapImpl.toLower(n, n2 | 16384, charSequence, new StringBuilder(), edits), edits);
        }
        return CaseMapImpl.toLower(n, n2, charSequence, new StringBuilder(charSequence.length()), null).toString();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static <A extends Appendable> A toTitle(int var0, int var1_1, BreakIterator var2_2, CharSequence var3_4, A var4_5, Edits var5_6) {
        if (var5_6 == null) ** GOTO lbl4
        try {
            var5_6.reset();
lbl4: // 2 sources:
            var6_7 = new StringContextIterator(var3_4);
            var7_8 = var3_4.length();
            var8_9 = true;
            var9_10 = 0;
            while (var9_10 < var7_8) {
                block17 : {
                    if (var8_9) {
                        var10_11 = var2_2.first();
                        var8_9 = false;
                    } else {
                        var10_11 = var2_2.next();
                    }
                    if (var10_11 == -1 || var10_11 > var7_8) {
                        var10_11 = var7_8;
                    }
                    if (var9_10 >= var10_11) break block17;
                    var6_7.setLimit(var10_11);
                    var11_12 = var6_7.nextCaseMapCP();
                    if ((var1_1 & 512) == 0) {
                        var12_13 = (var1_1 & 1024) != 0 ? 1 : 0;
                        while (var12_13 != 0 ? UCaseProps.INSTANCE.getType(var11_12) == 0 : CaseMapImpl.isLNS(var11_12) == false) {
                            var11_12 = var13_14 = var6_7.nextCaseMapCP();
                            if (var13_14 >= 0) continue;
                        }
                        if (var9_10 < (var12_13 = var6_7.getCPStart())) {
                            CaseMapImpl.appendUnchanged(var3_4, var9_10, var12_13 - var9_10, var4_5, var1_1, var5_6);
                        }
                        var9_10 = var12_13;
                    }
                    if (var9_10 >= var10_11) break block17;
                    var12_13 = var6_7.getCPLimit();
                    CaseMapImpl.appendResult(UCaseProps.INSTANCE.toFullTitle(var11_12, var6_7, var4_5, var0), var4_5, var6_7.getCPLength(), var1_1, var5_6);
                    if (var9_10 + 1 >= var10_11 || var0 != 5 || (var11_12 = (int)var3_4.charAt(var9_10)) != 105 && var11_12 != 73) ** GOTO lbl-1000
                    var11_12 = var3_4.charAt(var9_10 + 1);
                    if (var11_12 == 106) {
                        var4_5.append('J');
                        if (var5_6 != null) {
                            var5_6.addReplace(1, 1);
                        }
                        var6_7.nextCaseMapCP();
                        var9_10 = var12_13 + 1;
                    } else if (var11_12 == 74) {
                        CaseMapImpl.appendUnchanged(var3_4, var9_10 + 1, 1, var4_5, var1_1, var5_6);
                        var6_7.nextCaseMapCP();
                        var9_10 = var12_13 + 1;
                    } else lbl-1000: // 2 sources:
                    {
                        var9_10 = var12_13;
                    }
                    if (var9_10 < var10_11) {
                        if ((var1_1 & 256) == 0) {
                            CaseMapImpl.internalToLower(var0, var1_1, var3_4, var9_10, var10_11, var6_7, var4_5, var5_6);
                        } else {
                            CaseMapImpl.appendUnchanged(var3_4, var9_10, var10_11 - var9_10, var4_5, var1_1, var5_6);
                        }
                        var6_7.moveToLimit();
                    }
                }
                var9_10 = var10_11;
            }
            return var4_5;
        }
        catch (IOException var2_3) {
            throw new ICUUncheckedIOException(var2_3);
        }
    }

    public static String toTitle(int n, int n2, BreakIterator breakIterator, CharSequence charSequence) {
        if (charSequence.length() <= 100 && (n2 & 16384) == 0) {
            if (charSequence.length() == 0) {
                return charSequence.toString();
            }
            Edits edits = new Edits();
            return CaseMapImpl.applyEdits(charSequence, CaseMapImpl.toTitle(n, n2 | 16384, breakIterator, charSequence, new StringBuilder(), edits), edits);
        }
        return CaseMapImpl.toTitle(n, n2, breakIterator, charSequence, new StringBuilder(charSequence.length()), null).toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static <A extends Appendable> A toUpper(int n, int n2, CharSequence charSequence, A a, Edits edits) {
        if (edits != null) {
            try {
                edits.reset();
            }
            catch (IOException iOException) {
                throw new ICUUncheckedIOException(iOException);
            }
        }
        if (n == 4) {
            return (A)GreekUpper.toUpper(n2, charSequence, a, edits);
        }
        CaseMapImpl.internalToUpper(n, n2, charSequence, a, edits);
        return a;
    }

    public static String toUpper(int n, int n2, CharSequence charSequence) {
        if (charSequence.length() <= 100 && (n2 & 16384) == 0) {
            if (charSequence.length() == 0) {
                return charSequence.toString();
            }
            Edits edits = new Edits();
            return CaseMapImpl.applyEdits(charSequence, CaseMapImpl.toUpper(n, n2 | 16384, charSequence, new StringBuilder(), edits), edits);
        }
        return CaseMapImpl.toUpper(n, n2, charSequence, new StringBuilder(charSequence.length()), null).toString();
    }

    private static final class GreekUpper {
        private static final int AFTER_CASED = 1;
        private static final int AFTER_VOWEL_WITH_ACCENT = 2;
        private static final int HAS_ACCENT = 16384;
        private static final int HAS_COMBINING_DIALYTIKA = 65536;
        private static final int HAS_DIALYTIKA = 32768;
        private static final int HAS_EITHER_DIALYTIKA = 98304;
        private static final int HAS_OTHER_GREEK_DIACRITIC = 131072;
        private static final int HAS_VOWEL = 4096;
        private static final int HAS_VOWEL_AND_ACCENT = 20480;
        private static final int HAS_VOWEL_AND_ACCENT_AND_DIALYTIKA = 53248;
        private static final int HAS_YPOGEGRAMMENI = 8192;
        private static final int UPPER_MASK = 1023;
        private static final char[] data0370 = new char[]{'\u0370', '\u0370', '\u0372', '\u0372', '\u0000', '\u0000', '\u0376', '\u0376', '\u0000', '\u0000', '\u037a', '\u03fd', '\u03fe', '\u03ff', '\u0000', '\u037f', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u5391', '\u0000', '\u5395', '\u5397', '\u5399', '\u0000', '\u539f', '\u0000', '\u53a5', '\u53a9', '\uffffd399', '\u1391', '\u0392', '\u0393', '\u0394', '\u1395', '\u0396', '\u1397', '\u0398', '\u1399', '\u039a', '\u039b', '\u039c', '\u039d', '\u039e', '\u139f', '\u03a0', '\u03a1', '\u0000', '\u03a3', '\u03a4', '\u13a5', '\u03a6', '\u03a7', '\u03a8', '\u13a9', '\uffff9399', '\uffff93a5', '\u5391', '\u5395', '\u5397', '\u5399', '\uffffd3a5', '\u1391', '\u0392', '\u0393', '\u0394', '\u1395', '\u0396', '\u1397', '\u0398', '\u1399', '\u039a', '\u039b', '\u039c', '\u039d', '\u039e', '\u139f', '\u03a0', '\u03a1', '\u03a3', '\u03a3', '\u03a4', '\u13a5', '\u03a6', '\u03a7', '\u03a8', '\u13a9', '\uffff9399', '\uffff93a5', '\u539f', '\u53a5', '\u53a9', '\u03cf', '\u0392', '\u0398', '\u03d2', '\u43d2', '\uffff83d2', '\u03a6', '\u03a0', '\u03cf', '\u03d8', '\u03d8', '\u03da', '\u03da', '\u03dc', '\u03dc', '\u03de', '\u03de', '\u03e0', '\u03e0', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u039a', '\u03a1', '\u03f9', '\u037f', '\u03f4', '\u1395', '\u0000', '\u03f7', '\u03f7', '\u03f9', '\u03fa', '\u03fa', '\u03fc', '\u03fd', '\u03fe', '\u03ff'};
        private static final char[] data1F00 = new char[]{'\u1391', '\u1391', '\u5391', '\u5391', '\u5391', '\u5391', '\u5391', '\u5391', '\u1391', '\u1391', '\u5391', '\u5391', '\u5391', '\u5391', '\u5391', '\u5391', '\u1395', '\u1395', '\u5395', '\u5395', '\u5395', '\u5395', '\u0000', '\u0000', '\u1395', '\u1395', '\u5395', '\u5395', '\u5395', '\u5395', '\u0000', '\u0000', '\u1397', '\u1397', '\u5397', '\u5397', '\u5397', '\u5397', '\u5397', '\u5397', '\u1397', '\u1397', '\u5397', '\u5397', '\u5397', '\u5397', '\u5397', '\u5397', '\u1399', '\u1399', '\u5399', '\u5399', '\u5399', '\u5399', '\u5399', '\u5399', '\u1399', '\u1399', '\u5399', '\u5399', '\u5399', '\u5399', '\u5399', '\u5399', '\u139f', '\u139f', '\u539f', '\u539f', '\u539f', '\u539f', '\u0000', '\u0000', '\u139f', '\u139f', '\u539f', '\u539f', '\u539f', '\u539f', '\u0000', '\u0000', '\u13a5', '\u13a5', '\u53a5', '\u53a5', '\u53a5', '\u53a5', '\u53a5', '\u53a5', '\u0000', '\u13a5', '\u0000', '\u53a5', '\u0000', '\u53a5', '\u0000', '\u53a5', '\u13a9', '\u13a9', '\u53a9', '\u53a9', '\u53a9', '\u53a9', '\u53a9', '\u53a9', '\u13a9', '\u13a9', '\u53a9', '\u53a9', '\u53a9', '\u53a9', '\u53a9', '\u53a9', '\u5391', '\u5391', '\u5395', '\u5395', '\u5397', '\u5397', '\u5399', '\u5399', '\u539f', '\u539f', '\u53a5', '\u53a5', '\u53a9', '\u53a9', '\u0000', '\u0000', '\u3391', '\u3391', '\u7391', '\u7391', '\u7391', '\u7391', '\u7391', '\u7391', '\u3391', '\u3391', '\u7391', '\u7391', '\u7391', '\u7391', '\u7391', '\u7391', '\u3397', '\u3397', '\u7397', '\u7397', '\u7397', '\u7397', '\u7397', '\u7397', '\u3397', '\u3397', '\u7397', '\u7397', '\u7397', '\u7397', '\u7397', '\u7397', '\u33a9', '\u33a9', '\u73a9', '\u73a9', '\u73a9', '\u73a9', '\u73a9', '\u73a9', '\u33a9', '\u33a9', '\u73a9', '\u73a9', '\u73a9', '\u73a9', '\u73a9', '\u73a9', '\u1391', '\u1391', '\u7391', '\u3391', '\u7391', '\u0000', '\u5391', '\u7391', '\u1391', '\u1391', '\u5391', '\u5391', '\u3391', '\u0000', '\u1399', '\u0000', '\u0000', '\u0000', '\u7397', '\u3397', '\u7397', '\u0000', '\u5397', '\u7397', '\u5395', '\u5395', '\u5397', '\u5397', '\u3397', '\u0000', '\u0000', '\u0000', '\u1399', '\u1399', '\uffffd399', '\uffffd399', '\u0000', '\u0000', '\u5399', '\uffffd399', '\u1399', '\u1399', '\u5399', '\u5399', '\u0000', '\u0000', '\u0000', '\u0000', '\u13a5', '\u13a5', '\uffffd3a5', '\uffffd3a5', '\u03a1', '\u03a1', '\u53a5', '\uffffd3a5', '\u13a5', '\u13a5', '\u53a5', '\u53a5', '\u03a1', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u73a9', '\u33a9', '\u73a9', '\u0000', '\u53a9', '\u73a9', '\u539f', '\u539f', '\u53a9', '\u53a9', '\u33a9', '\u0000', '\u0000', '\u0000'};
        private static final char data2126 = '\u13a9';

        private GreekUpper() {
        }

        /*
         * Exception decompiling
         */
        private static final int getDiacriticData(int var0) {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
            // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:478)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:376)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
            // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
            // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:778)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:886)
            // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
            // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
            // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
            // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
            // org.benf.cfr.reader.Main.main(Main.java:48)
            throw new IllegalStateException("Decompilation failed");
        }

        private static final int getLetterData(int n) {
            if (n >= 880 && 8486 >= n && (1023 >= n || n >= 7936)) {
                if (n <= 1023) {
                    return data0370[n - 880];
                }
                if (n <= 8191) {
                    return data1F00[n - 7936];
                }
                if (n == 8486) {
                    return 5033;
                }
                return 0;
            }
            return 0;
        }

        private static boolean isFollowedByCasedLetter(CharSequence charSequence, int n) {
            while (n < charSequence.length()) {
                int n2 = Character.codePointAt(charSequence, n);
                int n3 = UCaseProps.INSTANCE.getTypeOrIgnorable(n2);
                if ((n3 & 4) != 0) {
                    n += Character.charCount(n2);
                    continue;
                }
                return n3 != 0;
            }
            return false;
        }

        private static <A extends Appendable> A toUpper(int n, CharSequence charSequence, A a, Edits edits) throws IOException {
            int n2 = 0;
            int n3 = 0;
            do {
                int n4;
                int n5;
                block37 : {
                    int n6;
                    block34 : {
                        int n7;
                        int n8;
                        int n9;
                        CharSequence charSequence2;
                        int n10;
                        block35 : {
                            block36 : {
                                charSequence2 = charSequence;
                                if (n3 >= charSequence.length()) break;
                                n4 = Character.codePointAt(charSequence2, n3);
                                n6 = Character.charCount(n4) + n3;
                                n5 = 0;
                                n9 = UCaseProps.INSTANCE.getTypeOrIgnorable(n4);
                                if ((n9 & 4) != 0) {
                                    n5 = false | n2 & true;
                                } else if (n9 != 0) {
                                    n5 = false | true;
                                }
                                n9 = GreekUpper.getLetterData(n4);
                                if (n9 <= 0) break block34;
                                n10 = n9 & 1023;
                                n7 = n9;
                                if ((n9 & 4096) == 0) break block35;
                                n7 = n9;
                                if ((n2 & 2) == 0) break block35;
                                if (n10 == 921) break block36;
                                n7 = n9;
                                if (n10 != 933) break block35;
                            }
                            n7 = n9 | 32768;
                        }
                        n9 = 0;
                        n4 = n6;
                        int n11 = n7;
                        if ((n7 & 8192) != 0) {
                            n9 = 1;
                            n11 = n7;
                            n4 = n6;
                        }
                        while (n4 < charSequence.length() && (n6 = GreekUpper.getDiacriticData(charSequence2.charAt(n4))) != 0) {
                            n11 |= n6;
                            n7 = n9;
                            if ((n6 & 8192) != 0) {
                                n7 = n9 + 1;
                            }
                            ++n4;
                            n9 = n7;
                        }
                        n7 = n5;
                        if ((53248 & n11) == 20480) {
                            n7 = n5 | 2;
                        }
                        n6 = 0;
                        if (n10 == 919 && (n11 & 16384) != 0 && n9 == 0 && (n2 & 1) == 0 && !GreekUpper.isFollowedByCasedLetter(charSequence2, n4)) {
                            if (n3 == n4) {
                                n5 = 905;
                                n2 = n11;
                                n8 = n6;
                            } else {
                                n8 = 1;
                                n2 = n11;
                                n5 = n10;
                            }
                        } else {
                            n2 = n11;
                            n5 = n10;
                            n8 = n6;
                            if ((32768 & n11) != 0) {
                                if (n10 == 921) {
                                    n5 = 938;
                                    n2 = n11 & -98305;
                                    n8 = n6;
                                } else {
                                    n2 = n11;
                                    n5 = n10;
                                    n8 = n6;
                                    if (n10 == 933) {
                                        n5 = 939;
                                        n2 = n11 & -98305;
                                        n8 = n6;
                                    }
                                }
                            }
                        }
                        if (edits == null && (n & 16384) == 0) {
                            n11 = 1;
                        } else {
                            n11 = charSequence2.charAt(n3);
                            int n12 = 0;
                            n6 = n11 == n5 && n9 <= 0 ? 0 : 1;
                            n10 = n3 + 1;
                            if ((n2 & 98304) != 0) {
                                n11 = n10 < n4 && charSequence2.charAt(n10) == '\u0308' ? 0 : 1;
                                n6 |= n11;
                                n11 = n10 + 1;
                            } else {
                                n11 = n10;
                            }
                            n10 = n6;
                            int n13 = n11;
                            if (n8 != 0) {
                                n10 = n11 < n4 && charSequence2.charAt(n11) == '\u0301' ? 0 : 1;
                                n10 = n6 | n10;
                                n13 = n11 + 1;
                            }
                            int n14 = n4 - n3;
                            n11 = n14 != (n3 = n13 - n3 + n9) ? 1 : 0;
                            n6 = n10 | n11;
                            if (n6 != 0) {
                                n11 = n6;
                                if (edits != null) {
                                    edits.addReplace(n14, n3);
                                    n11 = n6;
                                }
                            } else {
                                if (edits != null) {
                                    edits.addUnchanged(n14);
                                }
                                n11 = n12;
                                if ((n & 16384) == 0) {
                                    n11 = 1;
                                }
                            }
                        }
                        if (n11 != 0) {
                            a.append((char)n5);
                            if ((n2 & 98304) != 0) {
                                a.append('\u0308');
                            }
                            n5 = n9;
                            if (n8 != 0) {
                                a.append('\u0301');
                                n5 = n9;
                            }
                            while (n5 > 0) {
                                a.append('\u0399');
                                --n5;
                            }
                        }
                        n5 = n7;
                        break block37;
                    }
                    CaseMapImpl.appendResult(UCaseProps.INSTANCE.toFullUpper(n4, (UCaseProps.ContextIterator)null, a, 4), a, n6 - n3, n, edits);
                    n4 = n6;
                }
                n2 = n5;
                n3 = n4;
            } while (true);
            return a;
        }
    }

    public static final class StringContextIterator
    implements UCaseProps.ContextIterator {
        protected int cpLimit;
        protected int cpStart;
        protected int dir;
        protected int index;
        protected int limit;
        protected CharSequence s;

        public StringContextIterator(CharSequence charSequence) {
            this.s = charSequence;
            this.limit = charSequence.length();
            this.index = 0;
            this.cpLimit = 0;
            this.cpStart = 0;
            this.dir = 0;
        }

        public StringContextIterator(CharSequence charSequence, int n, int n2) {
            this.s = charSequence;
            this.index = 0;
            this.limit = charSequence.length();
            this.cpStart = n;
            this.cpLimit = n2;
            this.dir = 0;
        }

        public int getCPLength() {
            return this.cpLimit - this.cpStart;
        }

        public int getCPLimit() {
            return this.cpLimit;
        }

        public int getCPStart() {
            return this.cpStart;
        }

        public void moveToLimit() {
            int n;
            this.cpLimit = n = this.limit;
            this.cpStart = n;
        }

        @Override
        public int next() {
            int n;
            if (this.dir > 0 && this.index < this.s.length()) {
                int n2 = Character.codePointAt(this.s, this.index);
                this.index += Character.charCount(n2);
                return n2;
            }
            if (this.dir < 0 && (n = this.index) > 0) {
                n = Character.codePointBefore(this.s, n);
                this.index -= Character.charCount(n);
                return n;
            }
            return -1;
        }

        public int nextCaseMapCP() {
            int n;
            this.cpStart = n = this.cpLimit;
            if (n < this.limit) {
                n = Character.codePointAt(this.s, n);
                this.cpLimit += Character.charCount(n);
                return n;
            }
            return -1;
        }

        @Override
        public void reset(int n) {
            if (n > 0) {
                this.dir = 1;
                this.index = this.cpLimit;
            } else if (n < 0) {
                this.dir = -1;
                this.index = this.cpStart;
            } else {
                this.dir = 0;
                this.index = 0;
            }
        }

        public void setCPStartAndLimit(int n, int n2) {
            this.cpStart = n;
            this.cpLimit = n2;
            this.dir = 0;
        }

        public void setLimit(int n) {
            this.limit = n >= 0 && n <= this.s.length() ? n : this.s.length();
        }
    }

    private static final class WholeStringBreakIterator
    extends BreakIterator {
        private int length;

        private WholeStringBreakIterator() {
        }

        private static void notImplemented() {
            throw new UnsupportedOperationException("should not occur");
        }

        @Override
        public int current() {
            WholeStringBreakIterator.notImplemented();
            return 0;
        }

        @Override
        public int first() {
            return 0;
        }

        @Override
        public int following(int n) {
            WholeStringBreakIterator.notImplemented();
            return 0;
        }

        @Override
        public CharacterIterator getText() {
            WholeStringBreakIterator.notImplemented();
            return null;
        }

        @Override
        public int last() {
            WholeStringBreakIterator.notImplemented();
            return 0;
        }

        @Override
        public int next() {
            return this.length;
        }

        @Override
        public int next(int n) {
            WholeStringBreakIterator.notImplemented();
            return 0;
        }

        @Override
        public int previous() {
            WholeStringBreakIterator.notImplemented();
            return 0;
        }

        @Override
        public void setText(CharSequence charSequence) {
            this.length = charSequence.length();
        }

        @Override
        public void setText(String string) {
            this.length = string.length();
        }

        @Override
        public void setText(CharacterIterator characterIterator) {
            this.length = characterIterator.getEndIndex();
        }
    }

}

