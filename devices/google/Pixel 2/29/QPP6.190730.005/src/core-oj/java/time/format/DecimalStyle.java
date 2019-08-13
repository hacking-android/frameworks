/*
 * Decompiled with CFR 0.145.
 */
package java.time.format;

import java.text.DecimalFormatSymbols;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class DecimalStyle {
    private static final ConcurrentMap<Locale, DecimalStyle> CACHE;
    public static final DecimalStyle STANDARD;
    private final char decimalSeparator;
    private final char negativeSign;
    private final char positiveSign;
    private final char zeroDigit;

    static {
        STANDARD = new DecimalStyle('0', '+', '-', '.');
        CACHE = new ConcurrentHashMap<Locale, DecimalStyle>(16, 0.75f, 2);
    }

    private DecimalStyle(char c, char c2, char c3, char c4) {
        this.zeroDigit = c;
        this.positiveSign = c2;
        this.negativeSign = c3;
        this.decimalSeparator = c4;
    }

    private static DecimalStyle create(Locale cloneable) {
        cloneable = DecimalFormatSymbols.getInstance((Locale)cloneable);
        char c = ((DecimalFormatSymbols)cloneable).getZeroDigit();
        char c2 = ((DecimalFormatSymbols)cloneable).getMinusSign();
        char c3 = ((DecimalFormatSymbols)cloneable).getDecimalSeparator();
        if (c == '0' && c2 == '-' && c3 == '.') {
            return STANDARD;
        }
        return new DecimalStyle(c, '+', c2, c3);
    }

    public static Set<Locale> getAvailableLocales() {
        Locale[] arrlocale = DecimalFormatSymbols.getAvailableLocales();
        HashSet<Locale> hashSet = new HashSet<Locale>(arrlocale.length);
        Collections.addAll(hashSet, arrlocale);
        return hashSet;
    }

    public static DecimalStyle of(Locale locale) {
        DecimalStyle decimalStyle;
        Objects.requireNonNull(locale, "locale");
        DecimalStyle decimalStyle2 = decimalStyle = (DecimalStyle)CACHE.get(locale);
        if (decimalStyle == null) {
            decimalStyle2 = DecimalStyle.create(locale);
            CACHE.putIfAbsent(locale, decimalStyle2);
            decimalStyle2 = (DecimalStyle)CACHE.get(locale);
        }
        return decimalStyle2;
    }

    public static DecimalStyle ofDefaultLocale() {
        return DecimalStyle.of(Locale.getDefault(Locale.Category.FORMAT));
    }

    String convertNumberToI18N(String arrc) {
        char c = this.zeroDigit;
        if (c == '0') {
            return arrc;
        }
        arrc = arrc.toCharArray();
        for (int i = 0; i < arrc.length; ++i) {
            arrc[i] = (char)(arrc[i] + (c - 48));
        }
        return new String(arrc);
    }

    int convertToDigit(char c) {
        if ((c = (char)(c - this.zeroDigit)) < '\u0000' || c > '\t') {
            c = (char)-1;
        }
        return c;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object instanceof DecimalStyle) {
            object = (DecimalStyle)object;
            if (this.zeroDigit != ((DecimalStyle)object).zeroDigit || this.positiveSign != ((DecimalStyle)object).positiveSign || this.negativeSign != ((DecimalStyle)object).negativeSign || this.decimalSeparator != ((DecimalStyle)object).decimalSeparator) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public char getDecimalSeparator() {
        return this.decimalSeparator;
    }

    public char getNegativeSign() {
        return this.negativeSign;
    }

    public char getPositiveSign() {
        return this.positiveSign;
    }

    public char getZeroDigit() {
        return this.zeroDigit;
    }

    public int hashCode() {
        return this.zeroDigit + this.positiveSign + this.negativeSign + this.decimalSeparator;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DecimalStyle[");
        stringBuilder.append(this.zeroDigit);
        stringBuilder.append(this.positiveSign);
        stringBuilder.append(this.negativeSign);
        stringBuilder.append(this.decimalSeparator);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public DecimalStyle withDecimalSeparator(char c) {
        if (c == this.decimalSeparator) {
            return this;
        }
        return new DecimalStyle(this.zeroDigit, this.positiveSign, this.negativeSign, c);
    }

    public DecimalStyle withNegativeSign(char c) {
        if (c == this.negativeSign) {
            return this;
        }
        return new DecimalStyle(this.zeroDigit, this.positiveSign, c, this.decimalSeparator);
    }

    public DecimalStyle withPositiveSign(char c) {
        if (c == this.positiveSign) {
            return this;
        }
        return new DecimalStyle(this.zeroDigit, c, this.negativeSign, this.decimalSeparator);
    }

    public DecimalStyle withZeroDigit(char c) {
        if (c == this.zeroDigit) {
            return this;
        }
        return new DecimalStyle(c, this.positiveSign, this.negativeSign, this.decimalSeparator);
    }
}

