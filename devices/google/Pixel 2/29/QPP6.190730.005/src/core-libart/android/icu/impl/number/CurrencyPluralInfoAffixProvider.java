/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number;

import android.icu.impl.StandardPlural;
import android.icu.impl.number.AffixPatternProvider;
import android.icu.impl.number.DecimalFormatProperties;
import android.icu.impl.number.PatternStringParser;
import android.icu.impl.number.PropertiesAffixPatternProvider;
import android.icu.text.CurrencyPluralInfo;
import java.util.List;

public class CurrencyPluralInfoAffixProvider
implements AffixPatternProvider {
    private final PropertiesAffixPatternProvider[] affixesByPlural = new PropertiesAffixPatternProvider[StandardPlural.COUNT];

    public CurrencyPluralInfoAffixProvider(CurrencyPluralInfo currencyPluralInfo, DecimalFormatProperties object2) {
        DecimalFormatProperties decimalFormatProperties = new DecimalFormatProperties();
        decimalFormatProperties.copyFrom((DecimalFormatProperties)object2);
        for (StandardPlural standardPlural : StandardPlural.VALUES) {
            PatternStringParser.parseToExistingProperties(currencyPluralInfo.getCurrencyPluralPattern(standardPlural.getKeyword()), decimalFormatProperties);
            this.affixesByPlural[standardPlural.ordinal()] = new PropertiesAffixPatternProvider(decimalFormatProperties);
        }
    }

    @Override
    public char charAt(int n, int n2) {
        return this.affixesByPlural[n & 255].charAt(n, n2);
    }

    @Override
    public boolean containsSymbolType(int n) {
        return this.affixesByPlural[StandardPlural.OTHER.ordinal()].containsSymbolType(n);
    }

    @Override
    public String getString(int n) {
        return this.affixesByPlural[n & 255].getString(n);
    }

    @Override
    public boolean hasBody() {
        return this.affixesByPlural[StandardPlural.OTHER.ordinal()].hasBody();
    }

    @Override
    public boolean hasCurrencySign() {
        return this.affixesByPlural[StandardPlural.OTHER.ordinal()].hasCurrencySign();
    }

    @Override
    public boolean hasNegativeSubpattern() {
        return this.affixesByPlural[StandardPlural.OTHER.ordinal()].hasNegativeSubpattern();
    }

    @Override
    public int length(int n) {
        return this.affixesByPlural[n & 255].length(n);
    }

    @Override
    public boolean negativeHasMinusSign() {
        return this.affixesByPlural[StandardPlural.OTHER.ordinal()].negativeHasMinusSign();
    }

    @Override
    public boolean positiveHasPlusSign() {
        return this.affixesByPlural[StandardPlural.OTHER.ordinal()].positiveHasPlusSign();
    }
}

