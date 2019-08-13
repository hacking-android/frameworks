/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number;

import android.icu.impl.number.ConstantMultiFieldModifier;
import android.icu.impl.number.NumberStringBuilder;
import android.icu.text.DecimalFormatSymbols;
import android.icu.text.NumberFormat;
import android.icu.text.UnicodeSet;

public class CurrencySpacingEnabledModifier
extends ConstantMultiFieldModifier {
    static final short IN_CURRENCY = 0;
    static final short IN_NUMBER = 1;
    static final byte PREFIX = 0;
    static final byte SUFFIX = 1;
    private static final UnicodeSet UNISET_DIGIT = new UnicodeSet("[:digit:]").freeze();
    private static final UnicodeSet UNISET_NOTS = new UnicodeSet("[:^S:]").freeze();
    private final String afterPrefixInsert;
    private final UnicodeSet afterPrefixUnicodeSet;
    private final String beforeSuffixInsert;
    private final UnicodeSet beforeSuffixUnicodeSet;

    public CurrencySpacingEnabledModifier(NumberStringBuilder numberStringBuilder, NumberStringBuilder numberStringBuilder2, boolean bl, boolean bl2, DecimalFormatSymbols decimalFormatSymbols) {
        super(numberStringBuilder, numberStringBuilder2, bl, bl2);
        int n;
        if (numberStringBuilder.length() > 0 && numberStringBuilder.fieldAt(numberStringBuilder.length() - 1) == NumberFormat.Field.CURRENCY) {
            n = numberStringBuilder.getLastCodePoint();
            if (CurrencySpacingEnabledModifier.getUnicodeSet(decimalFormatSymbols, (short)0, (byte)0).contains(n)) {
                this.afterPrefixUnicodeSet = CurrencySpacingEnabledModifier.getUnicodeSet(decimalFormatSymbols, (short)1, (byte)0);
                this.afterPrefixUnicodeSet.freeze();
                this.afterPrefixInsert = CurrencySpacingEnabledModifier.getInsertString(decimalFormatSymbols, (byte)0);
            } else {
                this.afterPrefixUnicodeSet = null;
                this.afterPrefixInsert = null;
            }
        } else {
            this.afterPrefixUnicodeSet = null;
            this.afterPrefixInsert = null;
        }
        if (numberStringBuilder2.length() > 0 && numberStringBuilder2.fieldAt(0) == NumberFormat.Field.CURRENCY) {
            n = numberStringBuilder2.getLastCodePoint();
            if (CurrencySpacingEnabledModifier.getUnicodeSet(decimalFormatSymbols, (short)0, (byte)1).contains(n)) {
                this.beforeSuffixUnicodeSet = CurrencySpacingEnabledModifier.getUnicodeSet(decimalFormatSymbols, (short)1, (byte)1);
                this.beforeSuffixUnicodeSet.freeze();
                this.beforeSuffixInsert = CurrencySpacingEnabledModifier.getInsertString(decimalFormatSymbols, (byte)1);
            } else {
                this.beforeSuffixUnicodeSet = null;
                this.beforeSuffixInsert = null;
            }
        } else {
            this.beforeSuffixUnicodeSet = null;
            this.beforeSuffixInsert = null;
        }
    }

    public static int applyCurrencySpacing(NumberStringBuilder numberStringBuilder, int n, int n2, int n3, int n4, DecimalFormatSymbols decimalFormatSymbols) {
        int n5 = 0;
        boolean bl = n2 > 0;
        boolean bl2 = n4 > 0;
        boolean bl3 = n3 - n - n2 > 0;
        n4 = n5;
        if (bl) {
            n4 = n5;
            if (bl3) {
                n4 = 0 + CurrencySpacingEnabledModifier.applyCurrencySpacingAffix(numberStringBuilder, n + n2, (byte)0, decimalFormatSymbols);
            }
        }
        n = n4;
        if (bl2) {
            n = n4;
            if (bl3) {
                n = n4 + CurrencySpacingEnabledModifier.applyCurrencySpacingAffix(numberStringBuilder, n3 + n4, (byte)1, decimalFormatSymbols);
            }
        }
        return n;
    }

    private static int applyCurrencySpacingAffix(NumberStringBuilder numberStringBuilder, int n, byte by, DecimalFormatSymbols decimalFormatSymbols) {
        NumberFormat.Field field = by == 0 ? numberStringBuilder.fieldAt(n - 1) : numberStringBuilder.fieldAt(n);
        if (field != NumberFormat.Field.CURRENCY) {
            return 0;
        }
        int n2 = by == 0 ? numberStringBuilder.codePointBefore(n) : numberStringBuilder.codePointAt(n);
        if (!CurrencySpacingEnabledModifier.getUnicodeSet(decimalFormatSymbols, (short)0, by).contains(n2)) {
            return 0;
        }
        n2 = by == 0 ? numberStringBuilder.codePointAt(n) : numberStringBuilder.codePointBefore(n);
        if (!CurrencySpacingEnabledModifier.getUnicodeSet(decimalFormatSymbols, (short)1, by).contains(n2)) {
            return 0;
        }
        return numberStringBuilder.insert(n, CurrencySpacingEnabledModifier.getInsertString(decimalFormatSymbols, by), null);
    }

    private static String getInsertString(DecimalFormatSymbols decimalFormatSymbols, byte by) {
        boolean bl = true;
        if (by != 1) {
            bl = false;
        }
        return decimalFormatSymbols.getPatternForCurrencySpacing(2, bl);
    }

    private static UnicodeSet getUnicodeSet(DecimalFormatSymbols object, short s, byte by) {
        boolean bl = false;
        s = s == 0 ? (short)0 : 1;
        if (by == 1) {
            bl = true;
        }
        if (((String)(object = ((DecimalFormatSymbols)object).getPatternForCurrencySpacing(s, bl))).equals("[:digit:]")) {
            return UNISET_DIGIT;
        }
        if (((String)object).equals("[:^S:]")) {
            return UNISET_NOTS;
        }
        return new UnicodeSet((String)object);
    }

    @Override
    public int apply(NumberStringBuilder numberStringBuilder, int n, int n2) {
        int n3;
        UnicodeSet unicodeSet;
        int n4 = n3 = 0;
        if (n2 - n > 0) {
            unicodeSet = this.afterPrefixUnicodeSet;
            n4 = n3;
            if (unicodeSet != null) {
                n4 = n3;
                if (unicodeSet.contains(numberStringBuilder.codePointAt(n))) {
                    n4 = 0 + numberStringBuilder.insert(n, this.afterPrefixInsert, null);
                }
            }
        }
        n3 = n4;
        if (n2 - n > 0) {
            unicodeSet = this.beforeSuffixUnicodeSet;
            n3 = n4;
            if (unicodeSet != null) {
                n3 = n4;
                if (unicodeSet.contains(numberStringBuilder.codePointBefore(n2))) {
                    n3 = n4 + numberStringBuilder.insert(n2 + n4, this.beforeSuffixInsert, null);
                }
            }
        }
        return n3 + super.apply(numberStringBuilder, n, n2 + n3);
    }
}

