/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.StaticUnicodeSets;
import android.icu.lang.UCharacter;
import android.icu.text.DecimalFormat;
import android.icu.text.DecimalFormatSymbols;
import android.icu.text.NumberFormat;
import android.icu.util.ULocale;
import java.io.Serializable;
import java.text.AttributedCharacterIterator;
import java.util.Map;

public final class ScientificNumberFormatter {
    private static final Style SUPER_SCRIPT = new SuperscriptStyle();
    private final DecimalFormat fmt;
    private final String preExponent;
    private final Style style;

    private ScientificNumberFormatter(DecimalFormat decimalFormat, String string, Style style) {
        this.fmt = decimalFormat;
        this.preExponent = string;
        this.style = style;
    }

    private static ScientificNumberFormatter getInstance(DecimalFormat decimalFormat, Style style) {
        DecimalFormatSymbols decimalFormatSymbols = decimalFormat.getDecimalFormatSymbols();
        return new ScientificNumberFormatter((DecimalFormat)decimalFormat.clone(), ScientificNumberFormatter.getPreExponent(decimalFormatSymbols), style);
    }

    private static ScientificNumberFormatter getInstanceForLocale(ULocale serializable, Style style) {
        serializable = (DecimalFormat)DecimalFormat.getScientificInstance((ULocale)serializable);
        return new ScientificNumberFormatter((DecimalFormat)serializable, ScientificNumberFormatter.getPreExponent(((DecimalFormat)serializable).getDecimalFormatSymbols()), style);
    }

    public static ScientificNumberFormatter getMarkupInstance(DecimalFormat decimalFormat, String string, String string2) {
        return ScientificNumberFormatter.getInstance(decimalFormat, new MarkupStyle(string, string2));
    }

    public static ScientificNumberFormatter getMarkupInstance(ULocale uLocale, String string, String string2) {
        return ScientificNumberFormatter.getInstanceForLocale(uLocale, new MarkupStyle(string, string2));
    }

    private static String getPreExponent(DecimalFormatSymbols arrc) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(arrc.getExponentMultiplicationSign());
        arrc = arrc.getDigits();
        stringBuilder.append(arrc[1]);
        stringBuilder.append(arrc[0]);
        return stringBuilder.toString();
    }

    public static ScientificNumberFormatter getSuperscriptInstance(DecimalFormat decimalFormat) {
        return ScientificNumberFormatter.getInstance(decimalFormat, SUPER_SCRIPT);
    }

    public static ScientificNumberFormatter getSuperscriptInstance(ULocale uLocale) {
        return ScientificNumberFormatter.getInstanceForLocale(uLocale, SUPER_SCRIPT);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String format(Object object) {
        DecimalFormat decimalFormat = this.fmt;
        synchronized (decimalFormat) {
            return this.style.format(this.fmt.formatToCharacterIterator(object), this.preExponent);
        }
    }

    private static class MarkupStyle
    extends Style {
        private final String beginMarkup;
        private final String endMarkup;

        MarkupStyle(String string, String string2) {
            this.beginMarkup = string;
            this.endMarkup = string2;
        }

        @Override
        String format(AttributedCharacterIterator attributedCharacterIterator, String string) {
            int n = 0;
            StringBuilder stringBuilder = new StringBuilder();
            attributedCharacterIterator.first();
            while (attributedCharacterIterator.current() != '\uffff') {
                Map<AttributedCharacterIterator.Attribute, Object> map = attributedCharacterIterator.getAttributes();
                if (map.containsKey(NumberFormat.Field.EXPONENT_SYMBOL)) {
                    MarkupStyle.append(attributedCharacterIterator, n, attributedCharacterIterator.getRunStart(NumberFormat.Field.EXPONENT_SYMBOL), stringBuilder);
                    n = attributedCharacterIterator.getRunLimit(NumberFormat.Field.EXPONENT_SYMBOL);
                    attributedCharacterIterator.setIndex(n);
                    stringBuilder.append(string);
                    stringBuilder.append(this.beginMarkup);
                    continue;
                }
                if (map.containsKey(NumberFormat.Field.EXPONENT)) {
                    int n2 = attributedCharacterIterator.getRunLimit(NumberFormat.Field.EXPONENT);
                    MarkupStyle.append(attributedCharacterIterator, n, n2, stringBuilder);
                    n = n2;
                    attributedCharacterIterator.setIndex(n);
                    stringBuilder.append(this.endMarkup);
                    continue;
                }
                attributedCharacterIterator.next();
            }
            MarkupStyle.append(attributedCharacterIterator, n, attributedCharacterIterator.getEndIndex(), stringBuilder);
            return stringBuilder.toString();
        }
    }

    private static abstract class Style {
        private Style() {
        }

        static void append(AttributedCharacterIterator attributedCharacterIterator, int n, int n2, StringBuilder stringBuilder) {
            int n3 = attributedCharacterIterator.getIndex();
            attributedCharacterIterator.setIndex(n);
            while (n < n2) {
                stringBuilder.append(attributedCharacterIterator.current());
                attributedCharacterIterator.next();
                ++n;
            }
            attributedCharacterIterator.setIndex(n3);
        }

        abstract String format(AttributedCharacterIterator var1, String var2);
    }

    private static class SuperscriptStyle
    extends Style {
        private static final char[] SUPERSCRIPT_DIGITS = new char[]{'\u2070', '\u00b9', '\u00b2', '\u00b3', '\u2074', '\u2075', '\u2076', '\u2077', '\u2078', '\u2079'};
        private static final char SUPERSCRIPT_MINUS_SIGN = '\u207b';
        private static final char SUPERSCRIPT_PLUS_SIGN = '\u207a';

        private SuperscriptStyle() {
        }

        private static int char32AtAndAdvance(AttributedCharacterIterator attributedCharacterIterator) {
            char c = attributedCharacterIterator.current();
            char c2 = attributedCharacterIterator.next();
            if (UCharacter.isHighSurrogate(c) && UCharacter.isLowSurrogate(c2)) {
                attributedCharacterIterator.next();
                return UCharacter.toCodePoint(c, c2);
            }
            return c;
        }

        private static void copyAsSuperscript(AttributedCharacterIterator attributedCharacterIterator, int n, int n2, StringBuilder stringBuilder) {
            int n3 = attributedCharacterIterator.getIndex();
            attributedCharacterIterator.setIndex(n);
            while (attributedCharacterIterator.getIndex() < n2) {
                n = UCharacter.digit(SuperscriptStyle.char32AtAndAdvance(attributedCharacterIterator));
                if (n >= 0) {
                    stringBuilder.append(SUPERSCRIPT_DIGITS[n]);
                    continue;
                }
                throw new IllegalArgumentException();
            }
            attributedCharacterIterator.setIndex(n3);
        }

        @Override
        String format(AttributedCharacterIterator attributedCharacterIterator, String string) {
            int n = 0;
            StringBuilder stringBuilder = new StringBuilder();
            attributedCharacterIterator.first();
            while (attributedCharacterIterator.current() != '\uffff') {
                int n2;
                int n3;
                Map<AttributedCharacterIterator.Attribute, Object> map;
                block6 : {
                    block9 : {
                        block8 : {
                            int n4;
                            block7 : {
                                map = attributedCharacterIterator.getAttributes();
                                if (map.containsKey(NumberFormat.Field.EXPONENT_SYMBOL)) {
                                    SuperscriptStyle.append(attributedCharacterIterator, n, attributedCharacterIterator.getRunStart(NumberFormat.Field.EXPONENT_SYMBOL), stringBuilder);
                                    n = attributedCharacterIterator.getRunLimit(NumberFormat.Field.EXPONENT_SYMBOL);
                                    attributedCharacterIterator.setIndex(n);
                                    stringBuilder.append(string);
                                    continue;
                                }
                                if (!map.containsKey(NumberFormat.Field.EXPONENT_SIGN)) break block6;
                                n4 = attributedCharacterIterator.getRunStart(NumberFormat.Field.EXPONENT_SIGN);
                                n3 = attributedCharacterIterator.getRunLimit(NumberFormat.Field.EXPONENT_SIGN);
                                n2 = SuperscriptStyle.char32AtAndAdvance(attributedCharacterIterator);
                                if (!StaticUnicodeSets.get(StaticUnicodeSets.Key.MINUS_SIGN).contains(n2)) break block7;
                                SuperscriptStyle.append(attributedCharacterIterator, n, n4, stringBuilder);
                                stringBuilder.append('\u207b');
                                break block8;
                            }
                            if (!StaticUnicodeSets.get(StaticUnicodeSets.Key.PLUS_SIGN).contains(n2)) break block9;
                            SuperscriptStyle.append(attributedCharacterIterator, n, n4, stringBuilder);
                            stringBuilder.append('\u207a');
                        }
                        n = n3;
                        attributedCharacterIterator.setIndex(n);
                        continue;
                    }
                    throw new IllegalArgumentException();
                }
                if (map.containsKey(NumberFormat.Field.EXPONENT)) {
                    n2 = attributedCharacterIterator.getRunStart(NumberFormat.Field.EXPONENT);
                    n3 = attributedCharacterIterator.getRunLimit(NumberFormat.Field.EXPONENT);
                    SuperscriptStyle.append(attributedCharacterIterator, n, n2, stringBuilder);
                    SuperscriptStyle.copyAsSuperscript(attributedCharacterIterator, n2, n3, stringBuilder);
                    n = n3;
                    attributedCharacterIterator.setIndex(n);
                    continue;
                }
                attributedCharacterIterator.next();
            }
            SuperscriptStyle.append(attributedCharacterIterator, n, attributedCharacterIterator.getEndIndex(), stringBuilder);
            return stringBuilder.toString();
        }
    }

}

