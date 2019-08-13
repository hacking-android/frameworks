/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.Normalizer2Impl;
import android.icu.impl.Punycode;
import android.icu.impl.UBiDiProps;
import android.icu.lang.UCharacter;
import android.icu.lang.UScript;
import android.icu.text.IDNA;
import android.icu.text.Normalizer2;
import android.icu.text.StringPrepParseException;
import android.icu.util.ICUException;
import java.util.EnumSet;
import java.util.Set;

public final class UTS46
extends IDNA {
    private static final int EN_AN_MASK;
    private static final int ES_CS_ET_ON_BN_NSM_MASK;
    private static final int L_EN_ES_CS_ET_ON_BN_NSM_MASK;
    private static final int L_EN_MASK;
    private static final int L_MASK;
    private static final int L_R_AL_MASK;
    private static final int R_AL_AN_EN_ES_CS_ET_ON_BN_NSM_MASK;
    private static final int R_AL_AN_MASK;
    private static final int R_AL_EN_AN_MASK;
    private static final int R_AL_MASK;
    private static int U_GC_M_MASK;
    private static final byte[] asciiData;
    private static final EnumSet<IDNA.Error> severeErrors;
    private static final Normalizer2 uts46Norm2;
    final int options;

    static {
        uts46Norm2 = Normalizer2.getInstance(null, "uts46", Normalizer2.Mode.COMPOSE);
        severeErrors = EnumSet.of(IDNA.Error.LEADING_COMBINING_MARK, IDNA.Error.DISALLOWED, IDNA.Error.PUNYCODE, IDNA.Error.LABEL_HAS_DOT, IDNA.Error.INVALID_ACE_LABEL);
        asciiData = new byte[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1};
        L_MASK = UTS46.U_MASK(0);
        R_AL_MASK = UTS46.U_MASK(1) | UTS46.U_MASK(13);
        int n = L_MASK;
        int n2 = R_AL_MASK;
        L_R_AL_MASK = n | n2;
        R_AL_AN_MASK = n2 | UTS46.U_MASK(5);
        n2 = UTS46.U_MASK(2);
        EN_AN_MASK = UTS46.U_MASK(5) | n2;
        R_AL_EN_AN_MASK = R_AL_MASK | EN_AN_MASK;
        L_EN_MASK = L_MASK | UTS46.U_MASK(2);
        ES_CS_ET_ON_BN_NSM_MASK = UTS46.U_MASK(3) | UTS46.U_MASK(6) | UTS46.U_MASK(4) | UTS46.U_MASK(10) | UTS46.U_MASK(18) | UTS46.U_MASK(17);
        n2 = L_EN_MASK;
        n = ES_CS_ET_ON_BN_NSM_MASK;
        L_EN_ES_CS_ET_ON_BN_NSM_MASK = n2 | n;
        R_AL_AN_EN_ES_CS_ET_ON_BN_NSM_MASK = R_AL_MASK | EN_AN_MASK | n;
        U_GC_M_MASK = UTS46.U_MASK(6) | UTS46.U_MASK(7) | UTS46.U_MASK(8);
    }

    public UTS46(int n) {
        this.options = n;
    }

    private static int U_GET_GC_MASK(int n) {
        return 1 << UCharacter.getType(n);
    }

    private static int U_MASK(int n) {
        return 1 << n;
    }

    private void checkLabelBiDi(CharSequence charSequence, int n, int n2, IDNA.Info info) {
        int n3;
        int n4;
        block11 : {
            n4 = Character.codePointAt(charSequence, n);
            n3 = n + Character.charCount(n4);
            if ((L_R_AL_MASK & (n4 = UTS46.U_MASK(UBiDiProps.INSTANCE.getClass(n4)))) != 0) {
                UTS46.setNotOkBiDi(info);
            }
            n += n2;
            do {
                if (n3 >= n) {
                    n2 = n4;
                    break block11;
                }
                n2 = Character.codePointBefore(charSequence, n);
                n -= Character.charCount(n2);
            } while ((n2 = UBiDiProps.INSTANCE.getClass(n2)) == 17);
            n2 = UTS46.U_MASK(n2);
        }
        if ((L_MASK & n4) != 0 ? (L_EN_MASK & n2) != 0 : (R_AL_EN_AN_MASK & n2) != 0) {
            UTS46.setNotOkBiDi(info);
        }
        n2 = n4 | n2;
        while (n3 < n) {
            int n5 = Character.codePointAt(charSequence, n3);
            n3 += Character.charCount(n5);
            n2 |= UTS46.U_MASK(UBiDiProps.INSTANCE.getClass(n5));
        }
        if ((L_MASK & n4) != 0) {
            if ((L_EN_ES_CS_ET_ON_BN_NSM_MASK & n2) != 0) {
                UTS46.setNotOkBiDi(info);
            }
        } else {
            if ((R_AL_AN_EN_ES_CS_ET_ON_BN_NSM_MASK & n2) != 0) {
                UTS46.setNotOkBiDi(info);
            }
            if ((n2 & (n = EN_AN_MASK)) == n) {
                UTS46.setNotOkBiDi(info);
            }
        }
        if ((R_AL_AN_MASK & n2) != 0) {
            UTS46.setBiDi(info);
        }
    }

    private void checkLabelContextO(CharSequence charSequence, int n, int n2, IDNA.Info info) {
        int n3 = n + n2 - 1;
        int n4 = 0;
        for (int i = n; i <= n3; ++i) {
            block18 : {
                int n5;
                block19 : {
                    block25 : {
                        block24 : {
                            block22 : {
                                block23 : {
                                    block20 : {
                                        block21 : {
                                            block17 : {
                                                n5 = charSequence.charAt(i);
                                                if (n5 >= 183) break block17;
                                                n2 = n4;
                                                break block18;
                                            }
                                            if (n5 > 1785) break block19;
                                            if (n5 != 183) break block20;
                                            if (n >= i || charSequence.charAt(i - 1) != 'l' || i >= n3) break block21;
                                            n2 = n4;
                                            if (charSequence.charAt(i + 1) == 'l') break block18;
                                        }
                                        UTS46.addLabelError(info, IDNA.Error.CONTEXTO_PUNCTUATION);
                                        n2 = n4;
                                        break block18;
                                    }
                                    if (n5 != 885) break block22;
                                    if (i >= n3) break block23;
                                    n2 = n4;
                                    if (14 == UScript.getScript(Character.codePointAt(charSequence, i + 1))) break block18;
                                }
                                UTS46.addLabelError(info, IDNA.Error.CONTEXTO_PUNCTUATION);
                                n2 = n4;
                                break block18;
                            }
                            if (n5 == 1523 || n5 == 1524) break block24;
                            n2 = n4;
                            if (1632 <= n5) {
                                if (n5 <= 1641) {
                                    if (n4 > 0) {
                                        UTS46.addLabelError(info, IDNA.Error.CONTEXTO_DIGITS);
                                    }
                                    n2 = -1;
                                } else {
                                    n2 = n4;
                                    if (1776 <= n5) {
                                        if (n4 < 0) {
                                            UTS46.addLabelError(info, IDNA.Error.CONTEXTO_DIGITS);
                                        }
                                        n2 = 1;
                                    }
                                }
                            }
                            break block18;
                        }
                        if (n >= i) break block25;
                        n2 = n4;
                        if (19 == UScript.getScript(Character.codePointBefore(charSequence, i))) break block18;
                    }
                    UTS46.addLabelError(info, IDNA.Error.CONTEXTO_PUNCTUATION);
                    n2 = n4;
                    break block18;
                }
                n2 = n4;
                if (n5 == 12539) {
                    n5 = n;
                    do {
                        if (n5 > n3) {
                            UTS46.addLabelError(info, IDNA.Error.CONTEXTO_PUNCTUATION);
                            n2 = n4;
                            break;
                        }
                        int n6 = Character.codePointAt(charSequence, n5);
                        int n7 = UScript.getScript(n6);
                        n2 = n4;
                        if (n7 == 20) break;
                        n2 = n4;
                        if (n7 == 22) break;
                        if (n7 == 17) {
                            n2 = n4;
                            break;
                        }
                        n5 += Character.charCount(n6);
                    } while (true);
                }
            }
            n4 = n2;
        }
    }

    private static boolean isASCIIOkBiDi(CharSequence charSequence, int n) {
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            int n3;
            block9 : {
                block12 : {
                    char c;
                    block10 : {
                        block11 : {
                            block8 : {
                                c = charSequence.charAt(i);
                                if (c != '.') break block8;
                                if (!(i <= n2 || 97 <= (n2 = (int)charSequence.charAt(i - 1)) && n2 <= 122 || 48 <= n2 && n2 <= 57)) {
                                    return false;
                                }
                                n3 = i + 1;
                                break block9;
                            }
                            if (i != n2) break block10;
                            if ('a' > c) break block11;
                            n3 = n2;
                            if (c <= 'z') break block9;
                        }
                        return false;
                    }
                    n3 = n2;
                    if (c > ' ') break block9;
                    if (c >= '\u001c') break block12;
                    n3 = n2;
                    if ('\t' > c) break block9;
                    n3 = n2;
                    if (c > '\r') break block9;
                }
                return false;
            }
            n2 = n3;
        }
        return true;
    }

    private static boolean isASCIIString(CharSequence charSequence) {
        int n = charSequence.length();
        for (int i = 0; i < n; ++i) {
            if (charSequence.charAt(i) <= '') continue;
            return false;
        }
        return true;
    }

    private boolean isLabelOkContextJ(CharSequence charSequence, int n, int n2) {
        int n3 = n + n2;
        for (n2 = n; n2 < n3; ++n2) {
            int n4;
            if (charSequence.charAt(n2) == '\u200c') {
                if (n2 == n) {
                    return false;
                }
                int n5 = Character.codePointBefore(charSequence, n2);
                n4 = n2 - Character.charCount(n5);
                int n6 = n5;
                if (uts46Norm2.getCombiningClass(n5) == 9) continue;
                while ((n6 = UBiDiProps.INSTANCE.getJoiningType(n6)) == 5) {
                    if (n4 == 0) {
                        return false;
                    }
                    n6 = Character.codePointBefore(charSequence, n4);
                    n4 -= Character.charCount(n6);
                }
                if (n6 != 3 && n6 != 2) {
                    return false;
                }
                n4 = n2 + 1;
                do {
                    if (n4 == n3) {
                        return false;
                    }
                    n6 = Character.codePointAt(charSequence, n4);
                    n4 += Character.charCount(n6);
                } while ((n6 = UBiDiProps.INSTANCE.getJoiningType(n6)) == 5);
                if (n6 == 4 || n6 == 2) continue;
                return false;
            }
            if (charSequence.charAt(n2) != '\u200d') continue;
            if (n2 == n) {
                return false;
            }
            n4 = Character.codePointBefore(charSequence, n2);
            if (uts46Norm2.getCombiningClass(n4) == 9) continue;
            return false;
        }
        return true;
    }

    private static boolean isNonASCIIDisallowedSTD3Valid(int n) {
        boolean bl = n == 8800 || n == 8814 || n == 8815;
        return bl;
    }

    private int mapDevChars(StringBuilder stringBuilder, int n, int n2) {
        int n3 = stringBuilder.length();
        int n4 = 0;
        int n5 = n2;
        n2 = n4;
        while (n5 < n3) {
            n4 = stringBuilder.charAt(n5);
            if (n4 != 223) {
                if (n4 != 962) {
                    if (n4 != 8204 && n4 != 8205) {
                        ++n5;
                        continue;
                    }
                    n2 = 1;
                    stringBuilder.delete(n5, n5 + 1);
                    --n3;
                    continue;
                }
                n2 = 1;
                stringBuilder.setCharAt(n5, '\u03c3');
                ++n5;
                continue;
            }
            n2 = 1;
            n4 = n5 + 1;
            stringBuilder.setCharAt(n5, 's');
            n5 = n4 + 1;
            stringBuilder.insert(n4, 's');
            ++n3;
        }
        if (n2 != 0) {
            stringBuilder.replace(n, Integer.MAX_VALUE, uts46Norm2.normalize(stringBuilder.subSequence(n, stringBuilder.length())));
            return stringBuilder.length();
        }
        return n3;
    }

    private int markBadACELabel(StringBuilder stringBuilder, int n, int n2, boolean bl, IDNA.Info info) {
        boolean bl2 = (this.options & 2) != 0;
        boolean bl3 = true;
        char c = '\u0001';
        int n3 = n + 4;
        do {
            char c2;
            boolean bl4;
            if ((c2 = stringBuilder.charAt(n3)) <= '') {
                if (c2 == '.') {
                    UTS46.addLabelError(info, IDNA.Error.LABEL_HAS_DOT);
                    stringBuilder.setCharAt(n3, '\ufffd');
                    c = '\u0000';
                    bl4 = false;
                } else {
                    bl4 = bl3;
                    if (asciiData[c2] < 0) {
                        c2 = '\u0000';
                        bl4 = bl3;
                        c = c2;
                        if (bl2) {
                            stringBuilder.setCharAt(n3, '\ufffd');
                            bl4 = false;
                            c = c2;
                        }
                    }
                }
            } else {
                c = '\u0000';
                bl4 = false;
            }
            if (++n3 >= n + n2) {
                if (c != '\u0000') {
                    stringBuilder.insert(n + n2, '\ufffd');
                    n = n2 + 1;
                } else {
                    n = n2;
                    if (bl) {
                        n = n2;
                        if (bl4) {
                            n = n2;
                            if (n2 > 63) {
                                UTS46.addLabelError(info, IDNA.Error.LABEL_TOO_LONG);
                                n = n2;
                            }
                        }
                    }
                }
                return n;
            }
            bl3 = bl4;
        } while (true);
    }

    private StringBuilder process(CharSequence charSequence, boolean bl, boolean bl2, StringBuilder stringBuilder, IDNA.Info info) {
        block17 : {
            if (stringBuilder == charSequence) break block17;
            boolean bl3 = false;
            stringBuilder.delete(0, Integer.MAX_VALUE);
            UTS46.resetInfo(info);
            int n = charSequence.length();
            if (n == 0) {
                UTS46.addError(info, IDNA.Error.EMPTY_LABEL);
                return stringBuilder;
            }
            if ((this.options & 2) != 0) {
                bl3 = true;
            }
            int n2 = 0;
            int n3 = 0;
            do {
                int n4;
                block20 : {
                    block24 : {
                        block18 : {
                            char c;
                            block21 : {
                                block23 : {
                                    block22 : {
                                        block19 : {
                                            if (n2 == n) {
                                                if (bl2) {
                                                    if (n2 - n3 > 63) {
                                                        UTS46.addLabelError(info, IDNA.Error.LABEL_TOO_LONG);
                                                    }
                                                    if (!(bl || n2 < 254 || n2 <= 254 && n3 >= n2)) {
                                                        UTS46.addError(info, IDNA.Error.DOMAIN_NAME_TOO_LONG);
                                                    }
                                                }
                                                UTS46.promoteAndResetLabelErrors(info);
                                                return stringBuilder;
                                            }
                                            c = charSequence.charAt(n2);
                                            if (c > '') break block18;
                                            n4 = asciiData[c];
                                            if (n4 <= 0) break block19;
                                            stringBuilder.append((char)(c + 32));
                                            n4 = n3;
                                            break block20;
                                        }
                                        if (n4 < 0 && bl3) break block18;
                                        stringBuilder.append(c);
                                        if (c != '-') break block21;
                                        if (n2 != n3 + 3 || charSequence.charAt(n2 - 1) != '-') break block22;
                                        ++n2;
                                        break block18;
                                    }
                                    if (n2 == n3) {
                                        UTS46.addLabelError(info, IDNA.Error.LEADING_HYPHEN);
                                    }
                                    if (n2 + 1 == n) break block23;
                                    n4 = n3;
                                    if (charSequence.charAt(n2 + 1) != '.') break block20;
                                }
                                UTS46.addLabelError(info, IDNA.Error.TRAILING_HYPHEN);
                                n4 = n3;
                                break block20;
                            }
                            n4 = n3;
                            if (c != '.') break block20;
                            if (!bl) break block24;
                            ++n2;
                        }
                        UTS46.promoteAndResetLabelErrors(info);
                        this.processUnicode(charSequence, n3, n2, bl, bl2, stringBuilder, info);
                        if (UTS46.isBiDi(info) && !UTS46.hasCertainErrors(info, severeErrors) && (!UTS46.isOkBiDi(info) || n3 > 0 && !UTS46.isASCIIOkBiDi(stringBuilder, n3))) {
                            UTS46.addError(info, IDNA.Error.BIDI);
                        }
                        return stringBuilder;
                    }
                    if (n2 == n3) {
                        UTS46.addLabelError(info, IDNA.Error.EMPTY_LABEL);
                    }
                    if (bl2 && n2 - n3 > 63) {
                        UTS46.addLabelError(info, IDNA.Error.LABEL_TOO_LONG);
                    }
                    UTS46.promoteAndResetLabelErrors(info);
                    n4 = n2 + 1;
                }
                ++n2;
                n3 = n4;
            } while (true);
        }
        throw new IllegalArgumentException();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private int processLabel(StringBuilder var1_1, int var2_3, int var3_4, boolean var4_5, IDNA.Info var5_6) {
        block30 : {
            block29 : {
                block28 : {
                    var6_7 = var3_4;
                    if (var6_7 < 4 || var1_1.charAt(var2_3) != 'x' || var1_1.charAt(var2_3 + 1) != 'n' || var1_1.charAt(var2_3 + 2) != '-' || var1_1.charAt(var2_3 + 3) != '-') break block29;
                    try {
                        var7_8 = Punycode.decode(var1_1.subSequence(var2_3 + 4, var2_3 + var6_7), null);
                        if (UTS46.uts46Norm2.isNormalized((CharSequence)var7_8)) break block28;
                    }
                    catch (StringPrepParseException var8_10) {
                        UTS46.addLabelError(var5_6, IDNA.Error.PUNYCODE);
                        return this.markBadACELabel(var1_1, var2_3, var3_4, var4_5, var5_6);
                    }
                    UTS46.addLabelError(var5_6, IDNA.Error.INVALID_ACE_LABEL);
                    return this.markBadACELabel(var1_1, var2_3, var3_4, var4_5, var5_6);
                }
                var8_9 = var7_8;
                var9_11 = 0;
                var6_7 = var7_8.length();
                var10_12 = true;
                break block30;
            }
            var8_9 = var1_1;
            var9_11 = var2_3;
            var10_12 = false;
        }
        if (var6_7 == 0) {
            UTS46.addLabelError(var5_6, IDNA.Error.EMPTY_LABEL);
            return UTS46.replaceLabel(var1_1, var2_3, var3_4, (CharSequence)var8_9, var6_7);
        }
        if (var6_7 >= 4 && var8_9.charAt(var9_11 + 2) == '-' && var8_9.charAt(var9_11 + 3) == '-') {
            UTS46.addLabelError(var5_6, IDNA.Error.HYPHEN_3_4);
        }
        if (var8_9.charAt(var9_11) == '-') {
            UTS46.addLabelError(var5_6, IDNA.Error.LEADING_HYPHEN);
        }
        if (var8_9.charAt(var9_11 + var6_7 - 1) == '-') {
            UTS46.addLabelError(var5_6, IDNA.Error.TRAILING_HYPHEN);
        }
        var11_13 = var9_11;
        var12_14 = '\u0000';
        var13_15 = (this.options & 2) != 0 ? 1 : 0;
        do {
            block31 : {
                var7_8 = this;
                var14_16 = var8_9.charAt(var11_13);
                if (var14_16 <= '') {
                    if (var14_16 == '.') {
                        UTS46.addLabelError(var5_6, IDNA.Error.LABEL_HAS_DOT);
                        var8_9.setCharAt(var11_13, '\ufffd');
                        var15_17 = var12_14;
                    } else {
                        var15_17 = var12_14;
                        if (var13_15 != 0) {
                            var15_17 = var12_14;
                            if (UTS46.asciiData[var14_16] < 0) {
                                UTS46.addLabelError(var5_6, IDNA.Error.DISALLOWED);
                                var8_9.setCharAt(var11_13, '\ufffd');
                                var15_17 = var12_14;
                            }
                        }
                    }
                } else {
                    var15_17 = (char)(var12_14 | var14_16);
                    if (var13_15 != 0 && UTS46.isNonASCIIDisallowedSTD3Valid(var14_16)) {
                        UTS46.addLabelError(var5_6, IDNA.Error.DISALLOWED);
                        var8_9.setCharAt(var11_13, '\ufffd');
                    } else if (var14_16 == '\ufffd') {
                        UTS46.addLabelError(var5_6, IDNA.Error.DISALLOWED);
                    }
                }
                if (++var11_13 < var9_11 + var6_7) break block31;
                var13_15 = var8_9.codePointAt(var9_11);
                if ((UTS46.U_GET_GC_MASK(var13_15) & UTS46.U_GC_M_MASK) == 0) ** GOTO lbl-1000
                UTS46.addLabelError(var5_6, IDNA.Error.LEADING_COMBINING_MARK);
                var8_9.setCharAt(var9_11, '\ufffd');
                if (var13_15 > 65535) {
                    var8_9.deleteCharAt(var9_11 + 1);
                    var13_15 = var6_7 - 1;
                    if (var8_9 == var1_1) {
                        var6_7 = var3_4 - 1;
                        var3_4 = var13_15;
                    } else {
                        var6_7 = var3_4;
                        var3_4 = var13_15;
                    }
                } else lbl-1000: // 2 sources:
                {
                    var13_15 = var6_7;
                    var6_7 = var3_4;
                    var3_4 = var13_15;
                }
                if (UTS46.hasCertainLabelErrors(var5_6, UTS46.severeErrors)) {
                    if (var10_12 == false) return UTS46.replaceLabel(var1_1, var2_3, var6_7, (CharSequence)var8_9, var3_4);
                    UTS46.addLabelError(var5_6, IDNA.Error.INVALID_ACE_LABEL);
                    return this.markBadACELabel(var1_1, var2_3, var6_7, var4_5, var5_6);
                }
                if ((var7_8.options & 4) != 0 && (!UTS46.isBiDi(var5_6) || UTS46.isOkBiDi(var5_6))) {
                    UTS46.super.checkLabelBiDi((CharSequence)var8_9, var9_11, var3_4, var5_6);
                }
                if ((var7_8.options & 8) != 0 && (var15_17 & 8204) == 8204 && !UTS46.super.isLabelOkContextJ((CharSequence)var8_9, var9_11, var3_4)) {
                    UTS46.addLabelError(var5_6, IDNA.Error.CONTEXTJ);
                }
                if ((var7_8.options & 64) != 0 && var15_17 >= '\u00b7') {
                    UTS46.super.checkLabelContextO((CharSequence)var8_9, var9_11, var3_4, var5_6);
                }
                if (var4_5 == false) return UTS46.replaceLabel(var1_1, var2_3, var6_7, (CharSequence)var8_9, var3_4);
                if (var10_12) {
                    if (var6_7 <= 63) return var6_7;
                    UTS46.addLabelError(var5_6, IDNA.Error.LABEL_TOO_LONG);
                    return var6_7;
                }
                if (var15_17 < '') {
                    if (var3_4 <= 63) return UTS46.replaceLabel(var1_1, var2_3, var6_7, (CharSequence)var8_9, var3_4);
                    UTS46.addLabelError(var5_6, IDNA.Error.LABEL_TOO_LONG);
                    return UTS46.replaceLabel(var1_1, var2_3, var6_7, (CharSequence)var8_9, var3_4);
                }
                try {
                    var8_9 = Punycode.encode(var8_9.subSequence(var9_11, var9_11 + var3_4), null);
                    var8_9.insert(0, "xn--");
                }
                catch (StringPrepParseException var1_2) {
                    throw new ICUException(var1_2);
                }
                if (var8_9.length() <= 63) return UTS46.replaceLabel(var1_1, var2_3, var6_7, (CharSequence)var8_9, var8_9.length());
                UTS46.addLabelError(var5_6, IDNA.Error.LABEL_TOO_LONG);
                return UTS46.replaceLabel(var1_1, var2_3, var6_7, (CharSequence)var8_9, var8_9.length());
            }
            var12_14 = var15_17;
        } while (true);
    }

    private StringBuilder processUnicode(CharSequence charSequence, int n, int n2, boolean bl, boolean bl2, StringBuilder stringBuilder, IDNA.Info info) {
        if (n2 == 0) {
            uts46Norm2.normalize(charSequence, stringBuilder);
        } else {
            uts46Norm2.normalizeSecondAndAppend(stringBuilder, charSequence.subSequence(n2, charSequence.length()));
        }
        n2 = 0;
        if (bl2) {
            if ((this.options & 16) == 0) {
                n2 = 1;
            }
        } else if ((this.options & 32) == 0) {
            n2 = 1;
        }
        int n3 = stringBuilder.length();
        int n4 = n;
        int n5 = n2;
        n2 = n;
        n = n3;
        while (n2 < n) {
            char c = stringBuilder.charAt(n2);
            if (c == '.' && !bl) {
                n3 = n2 - n4;
                n2 = this.processLabel(stringBuilder, n4, n3, bl2, info);
                UTS46.promoteAndResetLabelErrors(info);
                n += n2 - n3;
                n4 = n2 = n2 + 1 + n4;
                continue;
            }
            if (c >= '\u00df') {
                if (c <= '\u200d' && (c == '\u00df' || c == '\u03c2' || c >= '\u200c')) {
                    UTS46.setTransitionalDifferent(info);
                    if (n5 != 0) {
                        n = this.mapDevChars(stringBuilder, n4, n2);
                        n5 = 0;
                        continue;
                    }
                } else if (Character.isSurrogate(c) && (Normalizer2Impl.UTF16Plus.isSurrogateLead(c) ? n2 + 1 == n || !Character.isLowSurrogate(stringBuilder.charAt(n2 + 1)) : n2 == n4 || !Character.isHighSurrogate(stringBuilder.charAt(n2 - 1)))) {
                    UTS46.addLabelError(info, IDNA.Error.DISALLOWED);
                    stringBuilder.setCharAt(n2, '\ufffd');
                }
            }
            ++n2;
        }
        if (n4 == 0 || n4 < n2) {
            this.processLabel(stringBuilder, n4, n2 - n4, bl2, info);
            UTS46.promoteAndResetLabelErrors(info);
        }
        return stringBuilder;
    }

    private static int replaceLabel(StringBuilder stringBuilder, int n, int n2, CharSequence charSequence, int n3) {
        if (charSequence != stringBuilder) {
            stringBuilder.delete(n, n + n2).insert(n, charSequence);
        }
        return n3;
    }

    @Override
    public StringBuilder labelToASCII(CharSequence charSequence, StringBuilder stringBuilder, IDNA.Info info) {
        return this.process(charSequence, true, true, stringBuilder, info);
    }

    @Override
    public StringBuilder labelToUnicode(CharSequence charSequence, StringBuilder stringBuilder, IDNA.Info info) {
        return this.process(charSequence, true, false, stringBuilder, info);
    }

    @Override
    public StringBuilder nameToASCII(CharSequence charSequence, StringBuilder stringBuilder, IDNA.Info info) {
        this.process(charSequence, false, true, stringBuilder, info);
        if (stringBuilder.length() >= 254 && !info.getErrors().contains((Object)IDNA.Error.DOMAIN_NAME_TOO_LONG) && UTS46.isASCIIString(stringBuilder) && (stringBuilder.length() > 254 || stringBuilder.charAt(253) != '.')) {
            UTS46.addError(info, IDNA.Error.DOMAIN_NAME_TOO_LONG);
        }
        return stringBuilder;
    }

    @Override
    public StringBuilder nameToUnicode(CharSequence charSequence, StringBuilder stringBuilder, IDNA.Info info) {
        return this.process(charSequence, false, false, stringBuilder, info);
    }
}

