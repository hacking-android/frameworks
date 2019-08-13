/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number.parse;

import android.icu.impl.StandardPlural;
import android.icu.impl.StringSegment;
import android.icu.impl.TextTrieMap;
import android.icu.impl.number.parse.NumberParseMatcher;
import android.icu.impl.number.parse.ParsedNumber;
import android.icu.text.DecimalFormatSymbols;
import android.icu.util.Currency;
import android.icu.util.ULocale;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class CombinedCurrencyMatcher
implements NumberParseMatcher {
    private final String afterPrefixInsert;
    private final String beforeSuffixInsert;
    private final String currency1;
    private final String currency2;
    private final String isoCode;
    private final String[] localLongNames;
    private final TextTrieMap<Currency.CurrencyStringInfo> longNameTrie;
    private final TextTrieMap<Currency.CurrencyStringInfo> symbolTrie;

    private CombinedCurrencyMatcher(Currency currency, DecimalFormatSymbols decimalFormatSymbols, int n) {
        this.isoCode = currency.getSubtype();
        this.currency1 = currency.getSymbol(decimalFormatSymbols.getULocale());
        this.currency2 = currency.getCurrencyCode();
        this.afterPrefixInsert = decimalFormatSymbols.getPatternForCurrencySpacing(2, false);
        this.beforeSuffixInsert = decimalFormatSymbols.getPatternForCurrencySpacing(2, true);
        if ((n & 8192) == 0) {
            this.longNameTrie = Currency.getParsingTrie(decimalFormatSymbols.getULocale(), 1);
            this.symbolTrie = Currency.getParsingTrie(decimalFormatSymbols.getULocale(), 0);
            this.localLongNames = null;
        } else {
            this.longNameTrie = null;
            this.symbolTrie = null;
            this.localLongNames = new String[StandardPlural.COUNT];
            for (n = 0; n < StandardPlural.COUNT; ++n) {
                String string = StandardPlural.VALUES.get(n).getKeyword();
                this.localLongNames[n] = currency.getName(decimalFormatSymbols.getLocale(), 2, string, null);
            }
        }
    }

    public static CombinedCurrencyMatcher getInstance(Currency currency, DecimalFormatSymbols decimalFormatSymbols, int n) {
        return new CombinedCurrencyMatcher(currency, decimalFormatSymbols, n);
    }

    private boolean matchCurrency(StringSegment stringSegment, ParsedNumber parsedNumber) {
        boolean bl;
        int n = !this.currency1.isEmpty() ? stringSegment.getCaseSensitivePrefixLength(this.currency1) : -1;
        boolean bl2 = true;
        boolean bl3 = false || n == stringSegment.length();
        if (n == this.currency1.length()) {
            parsedNumber.currencyCode = this.isoCode;
            stringSegment.adjustOffset(n);
            parsedNumber.setCharsConsumed(stringSegment);
            return bl3;
        }
        n = !this.currency2.isEmpty() ? stringSegment.getCommonPrefixLength(this.currency2) : -1;
        bl3 = bl3 || n == stringSegment.length();
        if (n == this.currency2.length()) {
            parsedNumber.currencyCode = this.isoCode;
            stringSegment.adjustOffset(n);
            parsedNumber.setCharsConsumed(stringSegment);
            return bl3;
        }
        if (this.longNameTrie != null) {
            TextTrieMap.Output output = new TextTrieMap.Output();
            Iterator<Currency.CurrencyStringInfo> iterator = this.longNameTrie.get(stringSegment, 0, output);
            bl3 = bl3 || output.partialMatch;
            bl3 = bl = bl3;
            Iterator<Currency.CurrencyStringInfo> iterator2 = iterator;
            if (iterator == null) {
                iterator2 = this.symbolTrie.get(stringSegment, 0, output);
                bl3 = bl2;
                if (!bl) {
                    bl3 = output.partialMatch ? bl2 : false;
                }
            }
            if (iterator2 != null) {
                parsedNumber.currencyCode = iterator2.next().getISOCode();
                stringSegment.adjustOffset(output.matchLength);
                parsedNumber.setCharsConsumed(stringSegment);
                return bl3;
            }
            bl = bl3;
        } else {
            n = 0;
            for (int i = 0; i < StandardPlural.COUNT; ++i) {
                String string = this.localLongNames[i];
                if (string.isEmpty()) continue;
                int n2 = stringSegment.getCommonPrefixLength(string);
                int n3 = n;
                if (n2 == string.length()) {
                    n3 = n;
                    if (string.length() > n) {
                        n3 = string.length();
                    }
                }
                bl3 = bl3 || n2 > 0;
                n = n3;
            }
            bl = bl3;
            if (n > 0) {
                parsedNumber.currencyCode = this.isoCode;
                stringSegment.adjustOffset(n);
                parsedNumber.setCharsConsumed(stringSegment);
                return bl3;
            }
        }
        return bl;
    }

    @Override
    public boolean match(StringSegment stringSegment, ParsedNumber parsedNumber) {
        boolean bl;
        block9 : {
            boolean bl2;
            block11 : {
                block10 : {
                    int n;
                    String string = parsedNumber.currencyCode;
                    boolean bl3 = false;
                    if (string != null) {
                        return false;
                    }
                    int n2 = stringSegment.getOffset();
                    int n3 = n = 0;
                    if (parsedNumber.seenNumber()) {
                        n3 = n;
                        if (!this.beforeSuffixInsert.isEmpty()) {
                            n3 = stringSegment.getCommonPrefixLength(this.beforeSuffixInsert);
                            if (n3 == this.beforeSuffixInsert.length()) {
                                stringSegment.adjustOffset(n3);
                            }
                            n3 = !false && n3 != stringSegment.length() ? 0 : 1;
                        }
                    }
                    bl2 = n3 != 0 || this.matchCurrency(stringSegment, parsedNumber);
                    if (parsedNumber.currencyCode == null) {
                        stringSegment.setOffset(n2);
                        return bl2;
                    }
                    bl = bl2;
                    if (parsedNumber.seenNumber()) break block9;
                    bl = bl2;
                    if (this.afterPrefixInsert.isEmpty()) break block9;
                    n3 = stringSegment.getCommonPrefixLength(this.afterPrefixInsert);
                    if (n3 == this.afterPrefixInsert.length()) {
                        stringSegment.adjustOffset(n3);
                    }
                    if (bl2) break block10;
                    bl2 = bl3;
                    if (n3 != stringSegment.length()) break block11;
                }
                bl2 = true;
            }
            bl = bl2;
        }
        return bl;
    }

    @Override
    public void postProcess(ParsedNumber parsedNumber) {
    }

    @Override
    public boolean smokeTest(StringSegment stringSegment) {
        return true;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<CombinedCurrencyMatcher ");
        stringBuilder.append(this.isoCode);
        stringBuilder.append(">");
        return stringBuilder.toString();
    }
}

