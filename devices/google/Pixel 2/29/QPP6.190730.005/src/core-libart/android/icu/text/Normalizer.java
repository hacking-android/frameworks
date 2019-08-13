/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.Norm2AllModes;
import android.icu.impl.Normalizer2Impl;
import android.icu.impl.UCaseProps;
import android.icu.lang.UCharacter;
import android.icu.text.FilteredNormalizer2;
import android.icu.text.Normalizer2;
import android.icu.text.UCharacterIterator;
import android.icu.text.UTF16;
import android.icu.text.UnicodeSet;
import android.icu.util.ICUCloneNotSupportedException;
import java.nio.CharBuffer;
import java.text.CharacterIterator;

public final class Normalizer
implements Cloneable {
    public static final int COMPARE_CODE_POINT_ORDER = 32768;
    private static final int COMPARE_EQUIV = 524288;
    public static final int COMPARE_IGNORE_CASE = 65536;
    @Deprecated
    public static final int COMPARE_NORM_OPTIONS_SHIFT = 20;
    @Deprecated
    public static final Mode COMPOSE;
    @Deprecated
    public static final Mode COMPOSE_COMPAT;
    @Deprecated
    public static final Mode DECOMP;
    @Deprecated
    public static final Mode DECOMP_COMPAT;
    @Deprecated
    public static final Mode DEFAULT;
    @Deprecated
    public static final int DONE = -1;
    @Deprecated
    public static final Mode FCD;
    public static final int FOLD_CASE_DEFAULT = 0;
    public static final int FOLD_CASE_EXCLUDE_SPECIAL_I = 1;
    @Deprecated
    public static final int IGNORE_HANGUL = 1;
    public static final int INPUT_IS_FCD = 131072;
    public static final QuickCheckResult MAYBE;
    @Deprecated
    public static final Mode NFC;
    @Deprecated
    public static final Mode NFD;
    @Deprecated
    public static final Mode NFKC;
    @Deprecated
    public static final Mode NFKD;
    public static final QuickCheckResult NO;
    @Deprecated
    public static final Mode NONE;
    @Deprecated
    public static final Mode NO_OP;
    @Deprecated
    public static final int UNICODE_3_2 = 32;
    public static final QuickCheckResult YES;
    private StringBuilder buffer;
    private int bufferPos;
    private int currentIndex;
    private Mode mode;
    private int nextIndex;
    private Normalizer2 norm2;
    private int options;
    private UCharacterIterator text;

    static {
        NONE = new NONEMode();
        NFD = new NFDMode();
        NFKD = new NFKDMode();
        DEFAULT = NFC = new NFCMode();
        NFKC = new NFKCMode();
        FCD = new FCDMode();
        NO_OP = NONE;
        COMPOSE = NFC;
        COMPOSE_COMPAT = NFKC;
        DECOMP = NFD;
        DECOMP_COMPAT = NFKD;
        NO = new QuickCheckResult(0);
        YES = new QuickCheckResult(1);
        MAYBE = new QuickCheckResult(2);
    }

    @Deprecated
    public Normalizer(UCharacterIterator object, Mode mode, int n) {
        try {
            this.text = (UCharacterIterator)((UCharacterIterator)object).clone();
            this.mode = mode;
            this.options = n;
            this.norm2 = mode.getNormalizer2(n);
            this.buffer = object = new StringBuilder();
            return;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new ICUCloneNotSupportedException(cloneNotSupportedException);
        }
    }

    @Deprecated
    public Normalizer(String string, Mode mode, int n) {
        this.text = UCharacterIterator.getInstance(string);
        this.mode = mode;
        this.options = n;
        this.norm2 = mode.getNormalizer2(n);
        this.buffer = new StringBuilder();
    }

    @Deprecated
    public Normalizer(CharacterIterator characterIterator, Mode mode, int n) {
        this.text = UCharacterIterator.getInstance((CharacterIterator)characterIterator.clone());
        this.mode = mode;
        this.options = n;
        this.norm2 = mode.getNormalizer2(n);
        this.buffer = new StringBuilder();
    }

    private void clearBuffer() {
        this.buffer.setLength(0);
        this.bufferPos = 0;
    }

    /*
     * Unable to fully structure code
     */
    static int cmpEquivFold(CharSequence var0, CharSequence var1_1, int var2_2) {
        block67 : {
            block63 : {
                block66 : {
                    block65 : {
                        block64 : {
                            var3_3 = (var2_2 & 524288) != 0 ? Norm2AllModes.getNFCInstance().impl : null;
                            if ((var2_2 & 65536) != 0) {
                                var4_4 = UCaseProps.INSTANCE;
                                var5_5 = new StringBuilder();
                                var6_6 = new StringBuilder();
                            } else {
                                var4_4 = null;
                                var6_6 = null;
                                var5_5 = null;
                            }
                            var7_7 = 0;
                            var8_8 = var0.length();
                            var9_9 = var1_1.length();
                            var10_10 = 0;
                            var11_11 = -1;
                            var12_12 = -1;
                            var13_13 = 0;
                            var14_14 = null;
                            var15_15 = 0;
                            var16_16 = var1_1;
                            var17_21 = null;
                            var1_1 = var14_14;
                            do {
                                block62 : {
                                    block61 : {
                                        block60 : {
                                            block59 : {
                                                block58 : {
                                                    block57 : {
                                                        if (var12_12 < 0) {
                                                            var12_12 = var8_8;
                                                            block1 : while (var7_7 == var12_12) {
                                                                var8_8 = var10_10;
                                                                if (var10_10 == 0) {
                                                                    var8_8 = var10_10;
                                                                    var10_10 = -1;
                                                                    break block57;
                                                                }
                                                                do {
                                                                    var10_10 = var8_8 - 1;
                                                                    var0 = var17_21[var10_10].cs;
                                                                    if (var0 != null) {
                                                                        var7_7 = var17_21[var10_10].s;
                                                                        var12_12 = var0.length();
                                                                        continue block1;
                                                                    }
                                                                    var8_8 = var10_10;
                                                                } while (true);
                                                            }
                                                            var18_22 = var0.charAt(var7_7);
                                                            ++var7_7;
                                                            var8_8 = var10_10;
                                                            var10_10 = var18_22;
                                                        } else {
                                                            var18_22 = var12_12;
                                                            var12_12 = var8_8;
                                                            var8_8 = var10_10;
                                                            var10_10 = var18_22;
                                                        }
                                                    }
                                                    if (var11_11 < 0) {
                                                        block3 : while (var15_15 == var9_9) {
                                                            var11_11 = var13_13;
                                                            if (var13_13 == 0) {
                                                                var11_11 = var9_9;
                                                                var9_9 = -1;
                                                                break block58;
                                                            }
                                                            do {
                                                                var13_13 = var11_11 - 1;
                                                                var16_18 = var1_1[var13_13].cs;
                                                                if (var16_18 != null) {
                                                                    var15_15 = var1_1[var13_13].s;
                                                                    var9_9 = var16_18.length();
                                                                    continue block3;
                                                                }
                                                                var11_11 = var13_13;
                                                            } while (true);
                                                        }
                                                        var18_22 = var16_17.charAt(var15_15);
                                                        ++var15_15;
                                                        var11_11 = var9_9;
                                                        var9_9 = var18_22;
                                                    } else {
                                                        var18_22 = var11_11;
                                                        var11_11 = var9_9;
                                                        var9_9 = var18_22;
                                                    }
                                                }
                                                if (var10_10 == var9_9) {
                                                    if (var10_10 < 0) {
                                                        return 0;
                                                    }
                                                    var18_22 = -1;
                                                    var10_10 = var8_8;
                                                    var9_9 = var11_11;
                                                    var11_11 = -1;
                                                    var8_8 = var12_12;
                                                    var12_12 = var18_22;
                                                    continue;
                                                }
                                                if (var10_10 < 0) {
                                                    return -1;
                                                }
                                                if (var9_9 < 0) {
                                                    return 1;
                                                }
                                                if (!UTF16.isSurrogate((char)var10_10)) ** GOTO lbl-1000
                                                if (!Normalizer2Impl.UTF16Plus.isSurrogateLead(var10_10)) break block59;
                                                if (var7_7 == var12_12 || !Character.isLowSurrogate(var19_23 = var0.charAt(var7_7))) ** GOTO lbl-1000
                                                var20_24 = Character.toCodePoint((char)var10_10, var19_23);
                                                break block60;
                                            }
                                            if (var7_7 - 2 >= 0 && Character.isHighSurrogate(var19_23 = var0.charAt(var7_7 - 2))) {
                                                var20_24 = Character.toCodePoint(var19_23, (char)var10_10);
                                            } else lbl-1000: // 3 sources:
                                            {
                                                var20_24 = var10_10;
                                            }
                                        }
                                        var18_22 = var12_12;
                                        if (!UTF16.isSurrogate((char)var9_9)) ** GOTO lbl-1000
                                        if (!Normalizer2Impl.UTF16Plus.isSurrogateLead(var9_9)) break block61;
                                        if (var15_15 == var11_11 || !Character.isLowSurrogate(var19_23 = var16_17.charAt(var15_15))) ** GOTO lbl-1000
                                        var21_25 = Character.toCodePoint((char)var9_9, var19_23);
                                        break block62;
                                    }
                                    if (var15_15 - 2 >= 0 && Character.isHighSurrogate(var19_23 = var16_17.charAt(var15_15 - 2))) {
                                        var21_25 = Character.toCodePoint(var19_23, (char)var9_9);
                                    } else lbl-1000: // 3 sources:
                                    {
                                        var21_25 = var9_9;
                                    }
                                }
                                var12_12 = var11_11;
                                if (var8_8 == 0 && (var2_2 & 65536) != 0 && (var22_26 = var4_4.toFullFolding(var20_24, var5_5, var2_2)) >= 0) {
                                    var18_22 = var9_9;
                                    var20_24 = var7_7;
                                    var11_11 = var15_15;
                                    if (UTF16.isSurrogate((char)var10_10)) {
                                        if (Normalizer2Impl.UTF16Plus.isSurrogateLead(var10_10)) {
                                            var20_24 = var7_7 + 1;
                                            var18_22 = var9_9;
                                            var11_11 = var15_15;
                                        } else {
                                            var11_11 = var15_15 - 1;
                                            var18_22 = var16_17.charAt(var11_11 - 1);
                                            var20_24 = var7_7;
                                        }
                                    }
                                    var14_14 = var17_21;
                                    if (var17_21 == null) {
                                        var14_14 = Normalizer.createCmpEquivLevelStack();
                                    }
                                    var14_14[0].cs = var0;
                                    var14_14[0].s = var20_24;
                                    if (var22_26 <= 31) {
                                        var5_5.delete(0, var5_5.length() - var22_26);
                                    } else {
                                        var5_5.setLength(0);
                                        var5_5.appendCodePoint(var22_26);
                                    }
                                    var7_7 = 0;
                                    var9_9 = var5_5.length();
                                    var20_24 = -1;
                                    var0 = var5_5;
                                    var10_10 = var8_8 + 1;
                                    var8_8 = var9_9;
                                    var9_9 = var12_12;
                                    var17_21 = var14_14;
                                    var15_15 = var11_11;
                                    var11_11 = var18_22;
                                    var12_12 = var20_24;
                                    continue;
                                }
                                if (var13_13 == 0 && (var2_2 & 65536) != 0 && (var22_26 = var4_4.toFullFolding(var21_25, var6_6, var2_2)) >= 0) {
                                    var12_12 = var10_10;
                                    var11_11 = var7_7;
                                    var20_24 = var15_15;
                                    if (UTF16.isSurrogate((char)var9_9)) {
                                        if (Normalizer2Impl.UTF16Plus.isSurrogateLead(var9_9)) {
                                            var20_24 = var15_15 + 1;
                                            var12_12 = var10_10;
                                            var11_11 = var7_7;
                                        } else {
                                            var11_11 = var7_7 - 1;
                                            var12_12 = var0.charAt(var11_11 - 1);
                                            var20_24 = var15_15;
                                        }
                                    }
                                    if (var1_1 == null) {
                                        var1_1 = Normalizer.createCmpEquivLevelStack();
                                    }
                                    var1_1[0].cs = var16_17;
                                    var1_1[0].s = var20_24;
                                    if (var22_26 <= 31) {
                                        var6_6.delete(0, var6_6.length() - var22_26);
                                    } else {
                                        var6_6.setLength(0);
                                        var6_6.appendCodePoint(var22_26);
                                    }
                                    var16_19 = var6_6;
                                    var15_15 = 0;
                                    var9_9 = var6_6.length();
                                    var10_10 = var8_8;
                                    var8_8 = var18_22;
                                    ++var13_13;
                                    var18_22 = -1;
                                    var7_7 = var11_11;
                                    var11_11 = var18_22;
                                    continue;
                                }
                                if (var8_8 < 2 && (var2_2 & 524288) != 0 && (var23_27 = (var14_14 = var3_3).getDecomposition(var20_24)) != null) {
                                    if (UTF16.isSurrogate((char)var10_10)) {
                                        if (Normalizer2Impl.UTF16Plus.isSurrogateLead(var10_10)) {
                                            ++var7_7;
                                            var11_11 = var9_9;
                                        } else {
                                            var11_11 = var16_17.charAt(--var15_15 - 1);
                                        }
                                    } else {
                                        var11_11 = var9_9;
                                    }
                                    var3_3 = var17_21;
                                    if (var17_21 == null) {
                                        var3_3 = Normalizer.createCmpEquivLevelStack();
                                    }
                                    var3_3[var8_8].cs = var0;
                                    var3_3[var8_8].s = var7_7;
                                    var10_10 = var9_9 = var8_8 + 1;
                                    if (var9_9 < 2) {
                                        var3_3[var9_9].cs = null;
                                        var10_10 = var9_9 + 1;
                                    }
                                    var7_7 = 0;
                                    var8_8 = var23_27.length();
                                    var18_22 = -1;
                                    var0 = var23_27;
                                    var9_9 = var12_12;
                                    var17_21 = var3_3;
                                    var3_3 = var14_14;
                                    var12_12 = var18_22;
                                    continue;
                                }
                                if (var13_13 >= 2 || (var2_2 & 524288) == 0 || (var14_14 = var3_3.getDecomposition(var21_25)) == null) break;
                                var12_12 = var10_10;
                                var11_11 = var7_7;
                                var20_24 = var15_15;
                                if (UTF16.isSurrogate((char)var9_9)) {
                                    if (Normalizer2Impl.UTF16Plus.isSurrogateLead(var9_9)) {
                                        var20_24 = var15_15 + 1;
                                        var12_12 = var10_10;
                                        var11_11 = var7_7;
                                    } else {
                                        var11_11 = var7_7 - 1;
                                        var12_12 = var0.charAt(var11_11 - 1);
                                        var20_24 = var15_15;
                                    }
                                }
                                if (var1_1 == null) {
                                    var1_1 = Normalizer.createCmpEquivLevelStack();
                                }
                                var1_1[var13_13].cs = var16_17;
                                var1_1[var13_13].s = var20_24;
                                var10_10 = var13_13 + 1;
                                if (var10_10 < 2) {
                                    var1_1[var10_10].cs = null;
                                }
                                var16_20 = var14_14;
                                var15_15 = 0;
                                var9_9 = var14_14.length();
                                var20_24 = var8_8;
                                var21_25 = -1;
                                var8_8 = var18_22;
                                var13_13 = ++var10_10;
                                var7_7 = var11_11;
                                var10_10 = var20_24;
                                var11_11 = var21_25;
                            } while (true);
                            if (var10_10 < 55296 || var9_9 < 55296 || (32768 & var2_2) == 0) break block63;
                            if (var10_10 > 56319 || var7_7 == var18_22) break block64;
                            var2_2 = var10_10;
                            if (Character.isLowSurrogate(var0.charAt(var7_7))) break block65;
                        }
                        var2_2 = Character.isLowSurrogate((char)var10_10) != false && var7_7 - 1 != 0 && Character.isHighSurrogate(var0.charAt(var7_7 - 2)) != false ? var10_10 : var10_10 - 10240;
                    }
                    if (var9_9 > 56319 || var15_15 == var12_12) break block66;
                    var10_10 = var2_2;
                    var13_13 = var9_9;
                    if (Character.isLowSurrogate(var16_17.charAt(var15_15))) break block67;
                }
                if (Character.isLowSurrogate((char)var9_9) && var15_15 - 1 != 0 && Character.isHighSurrogate(var16_17.charAt(var15_15 - 2))) {
                    var10_10 = var2_2;
                    var13_13 = var9_9;
                } else {
                    var13_13 = var9_9 - 10240;
                    var10_10 = var2_2;
                }
                break block67;
            }
            var13_13 = var9_9;
        }
        return var10_10 - var13_13;
    }

    public static int compare(int n, int n2, int n3) {
        return Normalizer.internalCompare(UTF16.valueOf(n), UTF16.valueOf(n2), 131072 | n3);
    }

    public static int compare(int n, String string, int n2) {
        return Normalizer.internalCompare(UTF16.valueOf(n), string, n2);
    }

    public static int compare(String string, String string2, int n) {
        return Normalizer.internalCompare(string, string2, n);
    }

    public static int compare(char[] arrc, int n, int n2, char[] arrc2, int n3, int n4, int n5) {
        if (arrc != null && n >= 0 && n2 >= 0 && arrc2 != null && n3 >= 0 && n4 >= 0 && n2 >= n && n4 >= n3) {
            return Normalizer.internalCompare(CharBuffer.wrap(arrc, n, n2 - n), CharBuffer.wrap(arrc2, n3, n4 - n3), n5);
        }
        throw new IllegalArgumentException();
    }

    public static int compare(char[] arrc, char[] arrc2, int n) {
        return Normalizer.internalCompare(CharBuffer.wrap(arrc), CharBuffer.wrap(arrc2), n);
    }

    @Deprecated
    public static int compose(char[] object, int n, int n2, char[] object2, int n3, int n4, boolean bl, int n5) {
        object = CharBuffer.wrap(object, n, n2 - n);
        object2 = new CharsAppendable((char[])object2, n3, n4);
        Normalizer.getComposeNormalizer2(bl, n5).normalize((CharSequence)object, (Appendable)object2);
        return ((CharsAppendable)object2).length();
    }

    @Deprecated
    public static int compose(char[] arrc, char[] arrc2, boolean bl, int n) {
        return Normalizer.compose(arrc, 0, arrc.length, arrc2, 0, arrc2.length, bl, n);
    }

    @Deprecated
    public static String compose(String string, boolean bl) {
        return Normalizer.compose(string, bl, 0);
    }

    @Deprecated
    public static String compose(String string, boolean bl, int n) {
        return Normalizer.getComposeNormalizer2(bl, n).normalize(string);
    }

    @Deprecated
    public static int concatenate(char[] object, int n, int n2, char[] arrc, int n3, int n4, char[] arrc2, int n5, int n6, Mode mode, int n7) {
        if (arrc2 != null) {
            if (arrc == arrc2 && n3 < n6 && n5 < n4) {
                throw new IllegalArgumentException("overlapping right and dst ranges");
            }
            StringBuilder stringBuilder = new StringBuilder(n2 - n + n4 - n3 + 16);
            stringBuilder.append((char[])object, n, n2 - n);
            object = CharBuffer.wrap(arrc, n3, n4 - n3);
            mode.getNormalizer2(n7).append(stringBuilder, (CharSequence)object);
            n = stringBuilder.length();
            if (n <= n6 - n5) {
                stringBuilder.getChars(0, n, arrc2, n5);
                return n;
            }
            throw new IndexOutOfBoundsException(Integer.toString(n));
        }
        throw new IllegalArgumentException();
    }

    @Deprecated
    public static String concatenate(String charSequence, String string, Mode mode, int n) {
        charSequence = new StringBuilder(charSequence.length() + string.length() + 16).append((String)charSequence);
        return mode.getNormalizer2(n).append((StringBuilder)charSequence, string).toString();
    }

    @Deprecated
    public static String concatenate(char[] object, char[] arrc, Mode mode, int n) {
        object = new StringBuilder(((char[])object).length + arrc.length + 16).append((char[])object);
        return mode.getNormalizer2(n).append((StringBuilder)object, CharBuffer.wrap(arrc)).toString();
    }

    private static final CmpEquivLevel[] createCmpEquivLevelStack() {
        return new CmpEquivLevel[]{new CmpEquivLevel(), new CmpEquivLevel()};
    }

    @Deprecated
    public static int decompose(char[] object, int n, int n2, char[] object2, int n3, int n4, boolean bl, int n5) {
        object = CharBuffer.wrap(object, n, n2 - n);
        object2 = new CharsAppendable((char[])object2, n3, n4);
        Normalizer.getDecomposeNormalizer2(bl, n5).normalize((CharSequence)object, (Appendable)object2);
        return ((CharsAppendable)object2).length();
    }

    @Deprecated
    public static int decompose(char[] arrc, char[] arrc2, boolean bl, int n) {
        return Normalizer.decompose(arrc, 0, arrc.length, arrc2, 0, arrc2.length, bl, n);
    }

    @Deprecated
    public static String decompose(String string, boolean bl) {
        return Normalizer.decompose(string, bl, 0);
    }

    @Deprecated
    public static String decompose(String string, boolean bl, int n) {
        return Normalizer.getDecomposeNormalizer2(bl, n).normalize(string);
    }

    private static final Normalizer2 getComposeNormalizer2(boolean bl, int n) {
        Mode mode = bl ? NFKC : NFC;
        return mode.getNormalizer2(n);
    }

    private static final Normalizer2 getDecomposeNormalizer2(boolean bl, int n) {
        Mode mode = bl ? NFKD : NFD;
        return mode.getNormalizer2(n);
    }

    @Deprecated
    public static int getFC_NFKC_Closure(int n, char[] arrc) {
        String string = Normalizer.getFC_NFKC_Closure(n);
        n = string.length();
        if (n != 0 && arrc != null && n <= arrc.length) {
            string.getChars(0, n, arrc, 0);
        }
        return n;
    }

    @Deprecated
    public static String getFC_NFKC_Closure(int n) {
        Object object = NFKCModeImpl.INSTANCE.normalizer2;
        Object object2 = UCaseProps.INSTANCE;
        CharSequence charSequence = new StringBuilder();
        int n2 = ((UCaseProps)object2).toFullFolding(n, (Appendable)((Object)charSequence), 0);
        if (n2 < 0) {
            object2 = ((Norm2AllModes.Normalizer2WithImpl)object).impl;
            if (((Normalizer2Impl)object2).getCompQuickCheck(((Normalizer2Impl)object2).getNorm16(n)) != 0) {
                return "";
            }
            ((StringBuilder)charSequence).appendCodePoint(n);
        } else if (n2 > 31) {
            ((StringBuilder)charSequence).appendCodePoint(n2);
        }
        charSequence = ((Normalizer2)object).normalize(charSequence);
        object = ((Normalizer2)object).normalize(UCharacter.foldCase((String)charSequence, 0));
        if (((String)charSequence).equals(object)) {
            return "";
        }
        return object;
    }

    private static int internalCompare(CharSequence charSequence, CharSequence charSequence2, int n) {
        CharSequence charSequence3;
        CharSequence charSequence4;
        block6 : {
            int n2;
            block5 : {
                n2 = n >>> 20;
                if ((131072 & (n |= 524288)) == 0) break block5;
                charSequence4 = charSequence;
                charSequence3 = charSequence2;
                if ((n & 1) == 0) break block6;
            }
            Normalizer2 normalizer2 = (n & 1) != 0 ? NFD.getNormalizer2(n2) : FCD.getNormalizer2(n2);
            n2 = normalizer2.spanQuickCheckYes(charSequence);
            int n3 = normalizer2.spanQuickCheckYes(charSequence2);
            CharSequence charSequence5 = charSequence;
            if (n2 < charSequence.length()) {
                charSequence5 = normalizer2.normalizeSecondAndAppend(new StringBuilder(charSequence.length() + 16).append(charSequence, 0, n2), charSequence.subSequence(n2, charSequence.length()));
            }
            charSequence4 = charSequence5;
            charSequence3 = charSequence2;
            if (n3 < charSequence2.length()) {
                charSequence3 = normalizer2.normalizeSecondAndAppend(new StringBuilder(charSequence2.length() + 16).append(charSequence2, 0, n3), charSequence2.subSequence(n3, charSequence2.length()));
                charSequence4 = charSequence5;
            }
        }
        return Normalizer.cmpEquivFold(charSequence4, charSequence3, n);
    }

    @Deprecated
    public static boolean isNormalized(int n, Mode mode, int n2) {
        return Normalizer.isNormalized(UTF16.valueOf(n), mode, n2);
    }

    @Deprecated
    public static boolean isNormalized(String string, Mode mode, int n) {
        return mode.getNormalizer2(n).isNormalized(string);
    }

    @Deprecated
    public static boolean isNormalized(char[] object, int n, int n2, Mode mode, int n3) {
        object = CharBuffer.wrap(object, n, n2 - n);
        return mode.getNormalizer2(n3).isNormalized((CharSequence)object);
    }

    private boolean nextNormalize() {
        int n;
        this.clearBuffer();
        this.currentIndex = n = this.nextIndex;
        this.text.setIndex(n);
        n = this.text.nextCodePoint();
        boolean bl = false;
        if (n < 0) {
            return false;
        }
        StringBuilder stringBuilder = new StringBuilder().appendCodePoint(n);
        while ((n = this.text.nextCodePoint()) >= 0) {
            if (this.norm2.hasBoundaryBefore(n)) {
                this.text.moveCodePointIndex(-1);
                break;
            }
            stringBuilder.appendCodePoint(n);
        }
        this.nextIndex = this.text.getIndex();
        this.norm2.normalize((CharSequence)stringBuilder, this.buffer);
        if (this.buffer.length() != 0) {
            bl = true;
        }
        return bl;
    }

    @Deprecated
    public static int normalize(char[] object, int n, int n2, char[] object2, int n3, int n4, Mode mode, int n5) {
        object = CharBuffer.wrap(object, n, n2 - n);
        object2 = new CharsAppendable((char[])object2, n3, n4);
        mode.getNormalizer2(n5).normalize((CharSequence)object, (Appendable)object2);
        return ((CharsAppendable)object2).length();
    }

    @Deprecated
    public static int normalize(char[] arrc, char[] arrc2, Mode mode, int n) {
        return Normalizer.normalize(arrc, 0, arrc.length, arrc2, 0, arrc2.length, mode, n);
    }

    @Deprecated
    public static String normalize(int n, Mode mode) {
        return Normalizer.normalize(n, mode, 0);
    }

    @Deprecated
    public static String normalize(int n, Mode object, int n2) {
        if (object == NFD && n2 == 0) {
            String string = Normalizer2.getNFCInstance().getDecomposition(n);
            object = string;
            if (string == null) {
                object = UTF16.valueOf(n);
            }
            return object;
        }
        return Normalizer.normalize(UTF16.valueOf(n), (Mode)object, n2);
    }

    @Deprecated
    public static String normalize(String string, Mode mode) {
        return Normalizer.normalize(string, mode, 0);
    }

    @Deprecated
    public static String normalize(String string, Mode mode, int n) {
        return mode.getNormalizer2(n).normalize(string);
    }

    private boolean previousNormalize() {
        int n;
        boolean bl;
        this.clearBuffer();
        this.nextIndex = n = this.currentIndex;
        this.text.setIndex(n);
        StringBuilder stringBuilder = new StringBuilder();
        do {
            n = this.text.previousCodePoint();
            bl = false;
            if (n < 0) break;
            if (n <= 65535) {
                stringBuilder.insert(0, (char)n);
                continue;
            }
            stringBuilder.insert(0, Character.toChars(n));
        } while (!this.norm2.hasBoundaryBefore(n));
        this.currentIndex = this.text.getIndex();
        this.norm2.normalize((CharSequence)stringBuilder, this.buffer);
        this.bufferPos = this.buffer.length();
        if (this.buffer.length() != 0) {
            bl = true;
        }
        return bl;
    }

    @Deprecated
    public static QuickCheckResult quickCheck(String string, Mode mode) {
        return Normalizer.quickCheck(string, mode, 0);
    }

    @Deprecated
    public static QuickCheckResult quickCheck(String string, Mode mode, int n) {
        return mode.getNormalizer2(n).quickCheck(string);
    }

    @Deprecated
    public static QuickCheckResult quickCheck(char[] object, int n, int n2, Mode mode, int n3) {
        object = CharBuffer.wrap(object, n, n2 - n);
        return mode.getNormalizer2(n3).quickCheck((CharSequence)object);
    }

    @Deprecated
    public static QuickCheckResult quickCheck(char[] arrc, Mode mode, int n) {
        return Normalizer.quickCheck(arrc, 0, arrc.length, mode, n);
    }

    @Deprecated
    public Object clone() {
        try {
            StringBuilder stringBuilder;
            Normalizer normalizer = (Normalizer)super.clone();
            normalizer.text = (UCharacterIterator)this.text.clone();
            normalizer.mode = this.mode;
            normalizer.options = this.options;
            normalizer.norm2 = this.norm2;
            normalizer.buffer = stringBuilder = new StringBuilder(this.buffer);
            normalizer.bufferPos = this.bufferPos;
            normalizer.currentIndex = this.currentIndex;
            normalizer.nextIndex = this.nextIndex;
            return normalizer;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new ICUCloneNotSupportedException(cloneNotSupportedException);
        }
    }

    @Deprecated
    public int current() {
        if (this.bufferPos >= this.buffer.length() && !this.nextNormalize()) {
            return -1;
        }
        return this.buffer.codePointAt(this.bufferPos);
    }

    @Deprecated
    public int endIndex() {
        return this.text.getLength();
    }

    @Deprecated
    public int first() {
        this.reset();
        return this.next();
    }

    @Deprecated
    public int getBeginIndex() {
        return 0;
    }

    @Deprecated
    public int getEndIndex() {
        return this.endIndex();
    }

    @Deprecated
    public int getIndex() {
        if (this.bufferPos < this.buffer.length()) {
            return this.currentIndex;
        }
        return this.nextIndex;
    }

    @Deprecated
    public int getLength() {
        return this.text.getLength();
    }

    @Deprecated
    public Mode getMode() {
        return this.mode;
    }

    @Deprecated
    public int getOption(int n) {
        return (this.options & n) != 0;
    }

    @Deprecated
    public int getText(char[] arrc) {
        return this.text.getText(arrc);
    }

    @Deprecated
    public String getText() {
        return this.text.getText();
    }

    @Deprecated
    public int last() {
        int n;
        this.text.setToLimit();
        this.nextIndex = n = this.text.getIndex();
        this.currentIndex = n;
        this.clearBuffer();
        return this.previous();
    }

    @Deprecated
    public int next() {
        if (this.bufferPos >= this.buffer.length() && !this.nextNormalize()) {
            return -1;
        }
        int n = this.buffer.codePointAt(this.bufferPos);
        this.bufferPos += Character.charCount(n);
        return n;
    }

    @Deprecated
    public int previous() {
        if (this.bufferPos <= 0 && !this.previousNormalize()) {
            return -1;
        }
        int n = this.buffer.codePointBefore(this.bufferPos);
        this.bufferPos -= Character.charCount(n);
        return n;
    }

    @Deprecated
    public void reset() {
        this.text.setToStart();
        this.nextIndex = 0;
        this.currentIndex = 0;
        this.clearBuffer();
    }

    @Deprecated
    public int setIndex(int n) {
        this.setIndexOnly(n);
        return this.current();
    }

    @Deprecated
    public void setIndexOnly(int n) {
        this.text.setIndex(n);
        this.nextIndex = n;
        this.currentIndex = n;
        this.clearBuffer();
    }

    @Deprecated
    public void setMode(Mode mode) {
        this.mode = mode;
        this.norm2 = this.mode.getNormalizer2(this.options);
    }

    @Deprecated
    public void setOption(int n, boolean bl) {
        this.options = bl ? (this.options |= n) : (this.options &= n);
        this.norm2 = this.mode.getNormalizer2(this.options);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public void setText(UCharacterIterator object) {
        try {
            object = (UCharacterIterator)((UCharacterIterator)object).clone();
            if (object != null) {
                this.text = object;
                this.reset();
                return;
            }
            object = new IllegalStateException("Could not create a new UCharacterIterator");
            throw object;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new ICUCloneNotSupportedException("Could not clone the UCharacterIterator", cloneNotSupportedException);
        }
    }

    @Deprecated
    public void setText(String object) {
        if ((object = UCharacterIterator.getInstance((String)object)) != null) {
            this.text = object;
            this.reset();
            return;
        }
        throw new IllegalStateException("Could not create a new UCharacterIterator");
    }

    @Deprecated
    public void setText(StringBuffer object) {
        if ((object = UCharacterIterator.getInstance((StringBuffer)object)) != null) {
            this.text = object;
            this.reset();
            return;
        }
        throw new IllegalStateException("Could not create a new UCharacterIterator");
    }

    @Deprecated
    public void setText(CharacterIterator cloneable) {
        if ((cloneable = UCharacterIterator.getInstance(cloneable)) != null) {
            this.text = cloneable;
            this.reset();
            return;
        }
        throw new IllegalStateException("Could not create a new UCharacterIterator");
    }

    @Deprecated
    public void setText(char[] object) {
        if ((object = UCharacterIterator.getInstance(object)) != null) {
            this.text = object;
            this.reset();
            return;
        }
        throw new IllegalStateException("Could not create a new UCharacterIterator");
    }

    @Deprecated
    public int startIndex() {
        return 0;
    }

    private static final class CharsAppendable
    implements Appendable {
        private final char[] chars;
        private final int limit;
        private int offset;
        private final int start;

        public CharsAppendable(char[] arrc, int n, int n2) {
            this.chars = arrc;
            this.offset = n;
            this.start = n;
            this.limit = n2;
        }

        @Override
        public Appendable append(char c) {
            int n = this.offset;
            if (n < this.limit) {
                this.chars[n] = c;
            }
            ++this.offset;
            return this;
        }

        @Override
        public Appendable append(CharSequence charSequence) {
            return this.append(charSequence, 0, charSequence.length());
        }

        @Override
        public Appendable append(CharSequence charSequence, int n, int n2) {
            int n3 = n2 - n;
            int n4 = this.limit;
            int n5 = this.offset;
            if (n3 <= n4 - n5) {
                while (n < n2) {
                    char[] arrc = this.chars;
                    n3 = this.offset;
                    this.offset = n3 + 1;
                    arrc[n3] = charSequence.charAt(n);
                    ++n;
                }
            } else {
                this.offset = n5 + n3;
            }
            return this;
        }

        public int length() {
            int n = this.offset;
            int n2 = n - this.start;
            if (n <= this.limit) {
                return n2;
            }
            throw new IndexOutOfBoundsException(Integer.toString(n2));
        }
    }

    private static final class CmpEquivLevel {
        CharSequence cs;
        int s;

        private CmpEquivLevel() {
        }
    }

    private static final class FCD32ModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(new FilteredNormalizer2(Norm2AllModes.getFCDNormalizer2(), Unicode32.access$100()));

        private FCD32ModeImpl() {
        }
    }

    private static final class FCDMode
    extends Mode {
        private FCDMode() {
        }

        @Override
        protected Normalizer2 getNormalizer2(int n) {
            ModeImpl modeImpl = (n & 32) != 0 ? FCD32ModeImpl.INSTANCE : FCDModeImpl.INSTANCE;
            return modeImpl.normalizer2;
        }
    }

    private static final class FCDModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(Norm2AllModes.getFCDNormalizer2());

        private FCDModeImpl() {
        }
    }

    @Deprecated
    public static abstract class Mode {
        @Deprecated
        protected Mode() {
        }

        @Deprecated
        protected abstract Normalizer2 getNormalizer2(int var1);
    }

    private static final class ModeImpl {
        private final Normalizer2 normalizer2;

        private ModeImpl(Normalizer2 normalizer2) {
            this.normalizer2 = normalizer2;
        }
    }

    private static final class NFC32ModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(new FilteredNormalizer2(Normalizer2.getNFCInstance(), Unicode32.access$100()));

        private NFC32ModeImpl() {
        }
    }

    private static final class NFCMode
    extends Mode {
        private NFCMode() {
        }

        @Override
        protected Normalizer2 getNormalizer2(int n) {
            ModeImpl modeImpl = (n & 32) != 0 ? NFC32ModeImpl.INSTANCE : NFCModeImpl.INSTANCE;
            return modeImpl.normalizer2;
        }
    }

    private static final class NFCModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(Normalizer2.getNFCInstance());

        private NFCModeImpl() {
        }
    }

    private static final class NFD32ModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(new FilteredNormalizer2(Normalizer2.getNFDInstance(), Unicode32.access$100()));

        private NFD32ModeImpl() {
        }
    }

    private static final class NFDMode
    extends Mode {
        private NFDMode() {
        }

        @Override
        protected Normalizer2 getNormalizer2(int n) {
            ModeImpl modeImpl = (n & 32) != 0 ? NFD32ModeImpl.INSTANCE : NFDModeImpl.INSTANCE;
            return modeImpl.normalizer2;
        }
    }

    private static final class NFDModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(Normalizer2.getNFDInstance());

        private NFDModeImpl() {
        }
    }

    private static final class NFKC32ModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(new FilteredNormalizer2(Normalizer2.getNFKCInstance(), Unicode32.access$100()));

        private NFKC32ModeImpl() {
        }
    }

    private static final class NFKCMode
    extends Mode {
        private NFKCMode() {
        }

        @Override
        protected Normalizer2 getNormalizer2(int n) {
            ModeImpl modeImpl = (n & 32) != 0 ? NFKC32ModeImpl.INSTANCE : NFKCModeImpl.INSTANCE;
            return modeImpl.normalizer2;
        }
    }

    private static final class NFKCModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(Normalizer2.getNFKCInstance());

        private NFKCModeImpl() {
        }
    }

    private static final class NFKD32ModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(new FilteredNormalizer2(Normalizer2.getNFKDInstance(), Unicode32.access$100()));

        private NFKD32ModeImpl() {
        }
    }

    private static final class NFKDMode
    extends Mode {
        private NFKDMode() {
        }

        @Override
        protected Normalizer2 getNormalizer2(int n) {
            ModeImpl modeImpl = (n & 32) != 0 ? NFKD32ModeImpl.INSTANCE : NFKDModeImpl.INSTANCE;
            return modeImpl.normalizer2;
        }
    }

    private static final class NFKDModeImpl {
        private static final ModeImpl INSTANCE = new ModeImpl(Normalizer2.getNFKDInstance());

        private NFKDModeImpl() {
        }
    }

    private static final class NONEMode
    extends Mode {
        private NONEMode() {
        }

        @Override
        protected Normalizer2 getNormalizer2(int n) {
            return Norm2AllModes.NOOP_NORMALIZER2;
        }
    }

    public static final class QuickCheckResult {
        private QuickCheckResult(int n) {
        }
    }

    private static final class Unicode32 {
        private static final UnicodeSet INSTANCE = new UnicodeSet("[:age=3.2:]").freeze();

        private Unicode32() {
        }

        static /* synthetic */ UnicodeSet access$100() {
            return INSTANCE;
        }
    }

}

