/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number;

import android.icu.impl.StandardPlural;
import android.icu.impl.number.AffixPatternProvider;
import android.icu.impl.number.AffixUtils;
import android.icu.impl.number.DecimalFormatProperties;
import android.icu.impl.number.Padder;
import android.icu.number.NumberFormatter;
import android.icu.text.DecimalFormatSymbols;
import java.io.Serializable;
import java.math.BigDecimal;

public class PatternStringUtils {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    public static String convertLocalized(String string, DecimalFormatSymbols serializable, boolean n) {
        int n2;
        block29 : {
            block28 : {
                if (string == null) {
                    return null;
                }
                String[][] arrstring = new String[21][2];
                int n3 = n ^ true;
                arrstring[0][n3] = "%";
                arrstring[0][n] = ((DecimalFormatSymbols)serializable).getPercentString();
                arrstring[1][n3] = "\u2030";
                arrstring[1][n] = ((DecimalFormatSymbols)serializable).getPerMillString();
                arrstring[2][n3] = ".";
                arrstring[2][n] = ((DecimalFormatSymbols)serializable).getDecimalSeparatorString();
                arrstring[3][n3] = ",";
                arrstring[3][n] = ((DecimalFormatSymbols)serializable).getGroupingSeparatorString();
                arrstring[4][n3] = "-";
                arrstring[4][n] = ((DecimalFormatSymbols)serializable).getMinusSignString();
                arrstring[5][n3] = "+";
                arrstring[5][n] = ((DecimalFormatSymbols)serializable).getPlusSignString();
                arrstring[6][n3] = ";";
                arrstring[6][n] = Character.toString(((DecimalFormatSymbols)serializable).getPatternSeparator());
                arrstring[7][n3] = "@";
                arrstring[7][n] = Character.toString(((DecimalFormatSymbols)serializable).getSignificantDigit());
                arrstring[8][n3] = "E";
                arrstring[8][n] = ((DecimalFormatSymbols)serializable).getExponentSeparator();
                arrstring[9][n3] = "*";
                arrstring[9][n] = Character.toString(((DecimalFormatSymbols)serializable).getPadEscape());
                arrstring[10][n3] = "#";
                arrstring[10][n] = Character.toString(((DecimalFormatSymbols)serializable).getDigit());
                for (n2 = 0; n2 < 10; ++n2) {
                    arrstring[n2 + 11][n3] = Character.toString((char)(n2 + 48));
                    arrstring[n2 + 11][n] = ((DecimalFormatSymbols)serializable).getDigitStringsLocal()[n2];
                }
                for (n2 = 0; n2 < arrstring.length; ++n2) {
                    arrstring[n2][n] = arrstring[n2][n].replace('\'', '\u2019');
                }
                serializable = new StringBuilder();
                n = 0;
                block2 : for (n2 = 0; n2 < string.length(); ++n2) {
                    char c;
                    block27 : {
                        block26 : {
                            String[] arrstring2;
                            c = string.charAt(n2);
                            if (c == '\'') {
                                if (n == 0) {
                                    ((StringBuilder)serializable).append('\'');
                                    n = 1;
                                    continue;
                                }
                                if (n == 1) {
                                    ((StringBuilder)serializable).append('\'');
                                    n = 0;
                                    continue;
                                }
                                if (n == 2) {
                                    n = 3;
                                    continue;
                                }
                                if (n == 3) {
                                    ((StringBuilder)serializable).append('\'');
                                    ((StringBuilder)serializable).append('\'');
                                    n = 1;
                                    continue;
                                }
                                if (n == 4) {
                                    n = 5;
                                    continue;
                                }
                                ((StringBuilder)serializable).append('\'');
                                ((StringBuilder)serializable).append('\'');
                                n = 4;
                                continue;
                            }
                            if (n != 0 && n != 3 && n != 4) {
                                ((StringBuilder)serializable).append(c);
                                n = 2;
                                continue;
                            }
                            int n4 = arrstring.length;
                            for (n3 = 0; n3 < n4; ++n3) {
                                block25 : {
                                    block24 : {
                                        arrstring2 = arrstring[n3];
                                        if (!string.regionMatches(n2, arrstring2[0], 0, arrstring2[0].length())) continue;
                                        n3 = n2 + (arrstring2[0].length() - 1);
                                        if (n == 3) break block24;
                                        n2 = n;
                                        if (n != 4) break block25;
                                    }
                                    ((StringBuilder)serializable).append('\'');
                                    n2 = 0;
                                }
                                ((StringBuilder)serializable).append(arrstring2[1]);
                                n = n2;
                                n2 = n3;
                                continue block2;
                            }
                            n4 = arrstring.length;
                            for (n3 = 0; n3 < n4; ++n3) {
                                arrstring2 = arrstring[n3];
                                if (!string.regionMatches(n2, arrstring2[1], 0, arrstring2[1].length())) continue;
                                n3 = n;
                                if (n == 0) {
                                    ((StringBuilder)serializable).append('\'');
                                    n3 = 4;
                                }
                                ((StringBuilder)serializable).append(c);
                                n = n3;
                                continue block2;
                            }
                            if (n == 3) break block26;
                            n3 = n;
                            if (n != 4) break block27;
                        }
                        ((StringBuilder)serializable).append('\'');
                        n3 = 0;
                    }
                    ((StringBuilder)serializable).append(c);
                    n = n3;
                }
                if (n == 3) break block28;
                n2 = n;
                if (n != 4) break block29;
            }
            ((StringBuilder)serializable).append('\'');
            n2 = 0;
        }
        if (n2 == 0) {
            return ((StringBuilder)serializable).toString();
        }
        throw new IllegalArgumentException("Malformed localized pattern: unterminated quote");
    }

    private static int escapePaddingString(CharSequence charSequence, StringBuilder stringBuilder, int n) {
        CharSequence charSequence2;
        block10 : {
            block9 : {
                if (charSequence == null) break block9;
                charSequence2 = charSequence;
                if (charSequence.length() != 0) break block10;
            }
            charSequence2 = " ";
        }
        int n2 = stringBuilder.length();
        if (charSequence2.length() == 1) {
            if (charSequence2.equals("'")) {
                stringBuilder.insert(n, "''");
            } else {
                stringBuilder.insert(n, charSequence2);
            }
        } else {
            stringBuilder.insert(n, '\'');
            int n3 = 1;
            for (int i = 0; i < charSequence2.length(); ++i) {
                char c = charSequence2.charAt(i);
                if (c == '\'') {
                    stringBuilder.insert(n + n3, "''");
                    n3 += 2;
                    continue;
                }
                stringBuilder.insert(n + n3, c);
                ++n3;
            }
            stringBuilder.insert(n + n3, '\'');
        }
        return stringBuilder.length() - n2;
    }

    public static void patternInfoToStringBuilder(AffixPatternProvider affixPatternProvider, boolean bl, int n, NumberFormatter.SignDisplay signDisplay, StandardPlural standardPlural, boolean bl2, StringBuilder stringBuilder) {
        int n2 = 1;
        int n3 = n != -1 && (signDisplay == NumberFormatter.SignDisplay.ALWAYS || signDisplay == NumberFormatter.SignDisplay.ACCOUNTING_ALWAYS || n == 1 && (signDisplay == NumberFormatter.SignDisplay.EXCEPT_ZERO || signDisplay == NumberFormatter.SignDisplay.ACCOUNTING_EXCEPT_ZERO)) && !affixPatternProvider.positiveHasPlusSign() ? 1 : 0;
        int n4 = affixPatternProvider.hasNegativeSubpattern() && (n == -1 || affixPatternProvider.negativeHasMinusSign() && n3 != 0) ? 1 : 0;
        int n5 = 0;
        if (n4 != 0) {
            n5 = 0 | 512;
        }
        int n6 = n5;
        if (bl) {
            n6 = n5 | 256;
        }
        n5 = n6;
        if (standardPlural != null) {
            n5 = n6 | standardPlural.ordinal();
        }
        n6 = bl && n4 == 0 ? (n == -1 ? (signDisplay != NumberFormatter.SignDisplay.NEVER ? 1 : 0) : n3) : 0;
        int n7 = affixPatternProvider.length(n5);
        n4 = n6 != 0 ? n2 : 0;
        stringBuilder.setLength(0);
        for (n2 = 0; n2 < n7 + n4; ++n2) {
            n = n6 != 0 && n2 == 0 ? 45 : (n6 != 0 ? (int)affixPatternProvider.charAt(n5, n2 - 1) : (int)affixPatternProvider.charAt(n5, n2));
            int n8 = n;
            if (n3 != 0) {
                n8 = n;
                if (n == 45) {
                    n8 = 43;
                }
            }
            int n9 = n8;
            if (bl2) {
                n9 = n8;
                if (n8 == 37) {
                    n9 = n = 8240;
                }
            }
            stringBuilder.append((char)n9);
        }
    }

    public static String propertiesToPatternString(DecimalFormatProperties serializable) {
        StringBuilder stringBuilder;
        block45 : {
            String string;
            String string2;
            int n;
            String string3;
            int n2;
            String string4;
            block44 : {
                int n3;
                int n4;
                stringBuilder = new StringBuilder();
                n = Math.min(((DecimalFormatProperties)serializable).getSecondaryGroupingSize(), 100);
                n2 = Math.min(((DecimalFormatProperties)serializable).getGroupingSize(), 100);
                int n5 = Math.min(((DecimalFormatProperties)serializable).getFormatWidth(), 100);
                Padder.PadPosition padPosition = ((DecimalFormatProperties)serializable).getPadPosition();
                String string5 = ((DecimalFormatProperties)serializable).getPadString();
                int n6 = Math.max(Math.min(((DecimalFormatProperties)serializable).getMinimumIntegerDigits(), 100), 0);
                int n7 = Math.min(((DecimalFormatProperties)serializable).getMaximumIntegerDigits(), 100);
                int n8 = Math.max(Math.min(((DecimalFormatProperties)serializable).getMinimumFractionDigits(), 100), 0);
                int n9 = Math.min(((DecimalFormatProperties)serializable).getMaximumFractionDigits(), 100);
                int n10 = Math.min(((DecimalFormatProperties)serializable).getMinimumSignificantDigits(), 100);
                int n11 = Math.min(((DecimalFormatProperties)serializable).getMaximumSignificantDigits(), 100);
                boolean bl = ((DecimalFormatProperties)serializable).getDecimalSeparatorAlwaysShown();
                int n12 = Math.min(((DecimalFormatProperties)serializable).getMinimumExponentDigits(), 100);
                boolean bl2 = ((DecimalFormatProperties)serializable).getExponentSignAlwaysShown();
                Object object = ((DecimalFormatProperties)serializable).getPositivePrefix();
                Object object2 = ((DecimalFormatProperties)serializable).getPositivePrefixPattern();
                String string6 = ((DecimalFormatProperties)serializable).getPositiveSuffix();
                String string7 = ((DecimalFormatProperties)serializable).getPositiveSuffixPattern();
                string3 = ((DecimalFormatProperties)serializable).getNegativePrefix();
                string = ((DecimalFormatProperties)serializable).getNegativePrefixPattern();
                string2 = ((DecimalFormatProperties)serializable).getNegativeSuffix();
                string4 = ((DecimalFormatProperties)serializable).getNegativeSuffixPattern();
                if (object2 != null) {
                    stringBuilder.append((String)object2);
                }
                AffixUtils.escape((CharSequence)object, stringBuilder);
                int n13 = stringBuilder.length();
                if (n != Math.min(100, -1) && n2 != Math.min(100, -1) && n != n2) {
                    n3 = n;
                    n4 = n;
                    n = n2;
                    n2 = n4;
                } else if (n != Math.min(100, -1)) {
                    n3 = 0;
                    n2 = n;
                } else if (n2 != Math.min(100, -1)) {
                    n3 = 0;
                    n4 = n;
                    n = n2;
                    n2 = n4;
                } else {
                    n3 = 0;
                    n2 = 0;
                    n = 0;
                }
                object = ((DecimalFormatProperties)serializable).getRoundingIncrement();
                serializable = new StringBuilder();
                n4 = 0;
                if (n11 != Math.min(100, -1)) {
                    while (((StringBuilder)serializable).length() < n10) {
                        ((StringBuilder)serializable).append('@');
                    }
                    object2 = serializable;
                    while (((StringBuilder)object2).length() < n11) {
                        ((StringBuilder)object2).append('#');
                    }
                } else {
                    object2 = serializable;
                    if (object != null) {
                        n4 = -((BigDecimal)object).scale();
                        if (((String)(object = ((BigDecimal)object).scaleByPowerOfTen(((BigDecimal)object).scale()).toPlainString())).charAt(0) == '-') {
                            ((StringBuilder)object2).append((CharSequence)object, 1, ((String)object).length());
                        } else {
                            ((StringBuilder)object2).append((String)object);
                        }
                    }
                }
                while (((StringBuilder)(object2 = serializable)).length() + n4 < n6) {
                    ((StringBuilder)object2).insert(0, '0');
                }
                while (-n4 < n8) {
                    ((StringBuilder)object2).append('0');
                    --n4;
                }
                n3 = Math.max(n3 + n + 1, ((StringBuilder)object2).length() + n4);
                n3 = n7 != 100 ? Math.max(n7, n3) - 1 : --n3;
                n9 = n9 != 100 ? Math.min(-n9, n4) : n4;
                n7 = n3;
                n3 = n6;
                while (n7 >= n9) {
                    n6 = ((StringBuilder)object2).length() + n4 - n7 - 1;
                    if (n6 >= 0 && n6 < ((StringBuilder)object2).length()) {
                        stringBuilder.append(((StringBuilder)object2).charAt(n6));
                    } else {
                        stringBuilder.append('#');
                    }
                    if (n7 > n && n2 > 0 && (n7 - n) % n2 == 0) {
                        stringBuilder.append(',');
                    } else if (n7 > 0 && n7 == n) {
                        stringBuilder.append(',');
                    } else if (n7 == 0 && (bl || n9 < 0)) {
                        stringBuilder.append('.');
                    }
                    --n7;
                }
                if (n12 != Math.min(100, -1)) {
                    stringBuilder.append('E');
                    if (bl2) {
                        stringBuilder.append('+');
                    }
                    for (n = 0; n < n12; ++n) {
                        stringBuilder.append('0');
                    }
                }
                n2 = stringBuilder.length();
                if (string7 != null) {
                    stringBuilder.append(string7);
                }
                AffixUtils.escape(string6, stringBuilder);
                if (n5 != -1) {
                    while (n5 - stringBuilder.length() > 0) {
                        stringBuilder.insert(n13, '#');
                        ++n2;
                    }
                    n = n13;
                    n3 = 1.$SwitchMap$android$icu$impl$number$Padder$PadPosition[padPosition.ordinal()];
                    if (n3 != 1) {
                        if (n3 != 2) {
                            if (n3 != 3) {
                                if (n3 == 4) {
                                    stringBuilder.append('*');
                                    PatternStringUtils.escapePaddingString(string5, stringBuilder, stringBuilder.length());
                                }
                            } else {
                                PatternStringUtils.escapePaddingString(string5, stringBuilder, n2);
                                stringBuilder.insert(n2, '*');
                            }
                        } else {
                            n3 = PatternStringUtils.escapePaddingString(string5, stringBuilder, n);
                            stringBuilder.insert(n, '*');
                            n += n3 + 1;
                            n2 += n3 + 1;
                        }
                    } else {
                        n3 = PatternStringUtils.escapePaddingString(string5, stringBuilder, 0);
                        stringBuilder.insert(0, '*');
                        n += n3 + 1;
                        n2 += n3 + 1;
                    }
                } else {
                    n = n13;
                }
                if (string3 != null || string2 != null || string == null && string4 != null) break block44;
                if (string == null || string.length() == 1 && string.charAt(0) == '-' && string4.length() == 0) break block45;
            }
            stringBuilder.append(';');
            if (string != null) {
                stringBuilder.append(string);
            }
            AffixUtils.escape(string3, stringBuilder);
            stringBuilder.append(stringBuilder, n, n2);
            if (string4 != null) {
                stringBuilder.append(string4);
            }
            AffixUtils.escape(string2, stringBuilder);
        }
        return stringBuilder.toString();
    }

}

