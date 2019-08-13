/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number;

import android.icu.impl.number.AffixPatternProvider;
import android.icu.impl.number.AffixUtils;
import android.icu.impl.number.DecimalFormatProperties;

public class PropertiesAffixPatternProvider
implements AffixPatternProvider {
    private final String negPrefix;
    private final String negSuffix;
    private final String posPrefix;
    private final String posSuffix;

    public PropertiesAffixPatternProvider(DecimalFormatProperties object) {
        String string = AffixUtils.escape(((DecimalFormatProperties)object).getPositivePrefix());
        String string2 = AffixUtils.escape(((DecimalFormatProperties)object).getPositiveSuffix());
        String string3 = AffixUtils.escape(((DecimalFormatProperties)object).getNegativePrefix());
        String string4 = AffixUtils.escape(((DecimalFormatProperties)object).getNegativeSuffix());
        String string5 = ((DecimalFormatProperties)object).getPositivePrefixPattern();
        String string6 = ((DecimalFormatProperties)object).getPositiveSuffixPattern();
        String string7 = ((DecimalFormatProperties)object).getNegativePrefixPattern();
        String string8 = ((DecimalFormatProperties)object).getNegativeSuffixPattern();
        String string9 = "";
        this.posPrefix = string != null ? string : (string5 != null ? string5 : "");
        this.posSuffix = string2 != null ? string2 : (string6 != null ? string6 : "");
        if (string3 != null) {
            this.negPrefix = string3;
        } else if (string7 != null) {
            this.negPrefix = string7;
        } else {
            object = "-";
            if (string5 != null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("-");
                ((StringBuilder)object).append(string5);
                object = ((StringBuilder)object).toString();
            }
            this.negPrefix = object;
        }
        if (string4 != null) {
            this.negSuffix = string4;
        } else if (string8 != null) {
            this.negSuffix = string8;
        } else {
            object = string6 == null ? string9 : string6;
            this.negSuffix = object;
        }
    }

    @Override
    public char charAt(int n, int n2) {
        return this.getString(n).charAt(n2);
    }

    @Override
    public boolean containsSymbolType(int n) {
        boolean bl = AffixUtils.containsType(this.posPrefix, n) || AffixUtils.containsType(this.posSuffix, n) || AffixUtils.containsType(this.negPrefix, n) || AffixUtils.containsType(this.negSuffix, n);
        return bl;
    }

    @Override
    public String getString(int n) {
        int n2 = 1;
        boolean bl = (n & 256) != 0;
        n = (n & 512) != 0 ? n2 : 0;
        if (bl && n != 0) {
            return this.negPrefix;
        }
        if (bl) {
            return this.posPrefix;
        }
        if (n != 0) {
            return this.negSuffix;
        }
        return this.posSuffix;
    }

    @Override
    public boolean hasBody() {
        return true;
    }

    @Override
    public boolean hasCurrencySign() {
        boolean bl = AffixUtils.hasCurrencySymbols(this.posPrefix) || AffixUtils.hasCurrencySymbols(this.posSuffix) || AffixUtils.hasCurrencySymbols(this.negPrefix) || AffixUtils.hasCurrencySymbols(this.negSuffix);
        return bl;
    }

    @Override
    public boolean hasNegativeSubpattern() {
        return true;
    }

    @Override
    public int length(int n) {
        return this.getString(n).length();
    }

    @Override
    public boolean negativeHasMinusSign() {
        boolean bl = AffixUtils.containsType(this.negPrefix, -1) || AffixUtils.containsType(this.negSuffix, -1);
        return bl;
    }

    @Override
    public boolean positiveHasPlusSign() {
        boolean bl = AffixUtils.containsType(this.posPrefix, -2) || AffixUtils.containsType(this.posSuffix, -2);
        return bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append(" {");
        stringBuilder.append(this.posPrefix);
        stringBuilder.append("#");
        stringBuilder.append(this.posSuffix);
        stringBuilder.append(";");
        stringBuilder.append(this.negPrefix);
        stringBuilder.append("#");
        stringBuilder.append(this.negSuffix);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

