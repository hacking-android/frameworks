/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number.parse;

import android.icu.impl.StringSegment;
import android.icu.impl.number.AffixPatternProvider;
import android.icu.impl.number.CurrencyPluralInfoAffixProvider;
import android.icu.impl.number.CustomSymbolCurrency;
import android.icu.impl.number.DecimalFormatProperties;
import android.icu.impl.number.Grouper;
import android.icu.impl.number.PatternStringParser;
import android.icu.impl.number.PropertiesAffixPatternProvider;
import android.icu.impl.number.RoundingUtils;
import android.icu.impl.number.parse.AffixMatcher;
import android.icu.impl.number.parse.AffixTokenMatcherFactory;
import android.icu.impl.number.parse.CombinedCurrencyMatcher;
import android.icu.impl.number.parse.DecimalMatcher;
import android.icu.impl.number.parse.IgnorablesMatcher;
import android.icu.impl.number.parse.InfinityMatcher;
import android.icu.impl.number.parse.MinusSignMatcher;
import android.icu.impl.number.parse.MultiplierParseHandler;
import android.icu.impl.number.parse.NanMatcher;
import android.icu.impl.number.parse.NumberParseMatcher;
import android.icu.impl.number.parse.PaddingMatcher;
import android.icu.impl.number.parse.ParsedNumber;
import android.icu.impl.number.parse.PercentMatcher;
import android.icu.impl.number.parse.PermilleMatcher;
import android.icu.impl.number.parse.PlusSignMatcher;
import android.icu.impl.number.parse.RequireAffixValidator;
import android.icu.impl.number.parse.RequireCurrencyValidator;
import android.icu.impl.number.parse.RequireDecimalSeparatorValidator;
import android.icu.impl.number.parse.RequireNumberValidator;
import android.icu.impl.number.parse.ScientificMatcher;
import android.icu.impl.number.parse.SymbolMatcher;
import android.icu.number.NumberFormatter;
import android.icu.number.Scale;
import android.icu.text.CurrencyPluralInfo;
import android.icu.text.DecimalFormatSymbols;
import android.icu.text.UnicodeSet;
import android.icu.util.Currency;
import android.icu.util.CurrencyAmount;
import android.icu.util.ULocale;
import java.io.Serializable;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class NumberParserImpl {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private boolean frozen;
    private final List<NumberParseMatcher> matchers = new ArrayList<NumberParseMatcher>();
    private final int parseFlags;

    public NumberParserImpl(int n) {
        this.parseFlags = n;
        this.frozen = false;
    }

    public static NumberParserImpl createDefaultParserForLocale(ULocale serializable) {
        serializable = DecimalFormatSymbols.getInstance(serializable);
        return NumberParserImpl.createParserFromProperties(PatternStringParser.parseToProperties("0"), (DecimalFormatSymbols)serializable, false);
    }

    public static NumberParserImpl createParserFromProperties(DecimalFormatProperties object, DecimalFormatSymbols decimalFormatSymbols, boolean bl) {
        boolean bl2;
        Object object2;
        Currency currency;
        boolean bl3;
        Object object3;
        int n;
        int n2;
        Object object4;
        ULocale uLocale;
        block20 : {
            block19 : {
                uLocale = decimalFormatSymbols.getULocale();
                object2 = ((DecimalFormatProperties)object).getCurrencyPluralInfo() == null ? new PropertiesAffixPatternProvider((DecimalFormatProperties)object) : new CurrencyPluralInfoAffixProvider(((DecimalFormatProperties)object).getCurrencyPluralInfo(), (DecimalFormatProperties)object);
                currency = CustomSymbolCurrency.resolve(((DecimalFormatProperties)object).getCurrency(), uLocale, decimalFormatSymbols);
                object4 = ((DecimalFormatProperties)object).getParseMode();
                object3 = DecimalFormatProperties.ParseMode.STRICT;
                bl2 = true;
                bl3 = object4 == object3;
                object4 = Grouper.forProperties((DecimalFormatProperties)object);
                n = 0;
                if (!((DecimalFormatProperties)object).getParseCaseSensitive()) {
                    n = false | true;
                }
                n2 = n;
                if (((DecimalFormatProperties)object).getParseIntegerOnly()) {
                    n2 = n | 16;
                }
                n = n2;
                if (((DecimalFormatProperties)object).getParseToBigDecimal()) {
                    n = n2 | 4096;
                }
                n2 = n;
                if (((DecimalFormatProperties)object).getSignAlwaysShown()) {
                    n2 = n | 1024;
                }
                n2 = bl3 ? n2 | 8 | 4 | 256 | 512 : (n2 |= 128);
                n = n2;
                if (((Grouper)object4).getPrimary() <= 0) {
                    n = n2 | 32;
                }
                if (bl) break block19;
                n2 = n;
                if (!object2.hasCurrencySign()) break block20;
            }
            n2 = n | 2;
        }
        n = n2;
        if (!bl) {
            n = n2 | 8192;
        }
        object3 = bl3 ? IgnorablesMatcher.STRICT : IgnorablesMatcher.DEFAULT;
        NumberParserImpl numberParserImpl = new NumberParserImpl(n);
        AffixTokenMatcherFactory affixTokenMatcherFactory = new AffixTokenMatcherFactory();
        affixTokenMatcherFactory.currency = currency;
        affixTokenMatcherFactory.symbols = decimalFormatSymbols;
        affixTokenMatcherFactory.ignorables = object3;
        affixTokenMatcherFactory.locale = uLocale;
        affixTokenMatcherFactory.parseFlags = n;
        AffixMatcher.createMatchers((AffixPatternProvider)object2, numberParserImpl, affixTokenMatcherFactory, (IgnorablesMatcher)object3, n);
        if (bl || object2.hasCurrencySign()) {
            numberParserImpl.addMatcher(CombinedCurrencyMatcher.getInstance(currency, decimalFormatSymbols, n));
        }
        if (!bl3 && object2.containsSymbolType(-3)) {
            numberParserImpl.addMatcher(PercentMatcher.getInstance(decimalFormatSymbols));
        }
        if (!bl3 && object2.containsSymbolType(-4)) {
            numberParserImpl.addMatcher(PermilleMatcher.getInstance(decimalFormatSymbols));
        }
        if (!bl3) {
            numberParserImpl.addMatcher(PlusSignMatcher.getInstance(decimalFormatSymbols, false));
            numberParserImpl.addMatcher(MinusSignMatcher.getInstance(decimalFormatSymbols, false));
        }
        numberParserImpl.addMatcher(NanMatcher.getInstance(decimalFormatSymbols, n));
        numberParserImpl.addMatcher(InfinityMatcher.getInstance(decimalFormatSymbols));
        object2 = ((DecimalFormatProperties)object).getPadString();
        if (object2 != null && !((SymbolMatcher)object3).getSet().contains((CharSequence)object2)) {
            numberParserImpl.addMatcher(PaddingMatcher.getInstance((String)object2));
        }
        numberParserImpl.addMatcher((NumberParseMatcher)object3);
        numberParserImpl.addMatcher(DecimalMatcher.getInstance(decimalFormatSymbols, (Grouper)object4, n));
        if (!((DecimalFormatProperties)object).getParseNoExponent() || ((DecimalFormatProperties)object).getMinimumExponentDigits() > 0) {
            numberParserImpl.addMatcher(ScientificMatcher.getInstance(decimalFormatSymbols, (Grouper)object4));
        }
        numberParserImpl.addMatcher(new RequireNumberValidator());
        if (bl3) {
            numberParserImpl.addMatcher(new RequireAffixValidator());
        }
        if (bl) {
            numberParserImpl.addMatcher(new RequireCurrencyValidator());
        }
        if (((DecimalFormatProperties)object).getDecimalPatternMatchRequired()) {
            bl = !((DecimalFormatProperties)object).getDecimalSeparatorAlwaysShown() && ((DecimalFormatProperties)object).getMaximumFractionDigits() == 0 ? false : bl2;
            numberParserImpl.addMatcher(RequireDecimalSeparatorValidator.getInstance(bl));
        }
        if ((object = RoundingUtils.scaleFromProperties((DecimalFormatProperties)object)) != null) {
            numberParserImpl.addMatcher(new MultiplierParseHandler((Scale)object));
        }
        numberParserImpl.freeze();
        return numberParserImpl;
    }

    public static NumberParserImpl createSimpleParser(ULocale object, String object2, int n) {
        NumberParserImpl numberParserImpl = new NumberParserImpl(n);
        Currency currency = Currency.getInstance("USD");
        DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance((ULocale)object);
        IgnorablesMatcher ignorablesMatcher = IgnorablesMatcher.DEFAULT;
        AffixTokenMatcherFactory affixTokenMatcherFactory = new AffixTokenMatcherFactory();
        affixTokenMatcherFactory.currency = currency;
        affixTokenMatcherFactory.symbols = decimalFormatSymbols;
        affixTokenMatcherFactory.ignorables = ignorablesMatcher;
        affixTokenMatcherFactory.locale = object;
        affixTokenMatcherFactory.parseFlags = n;
        object2 = PatternStringParser.parseToPatternInfo((String)object2);
        AffixMatcher.createMatchers((AffixPatternProvider)object2, numberParserImpl, affixTokenMatcherFactory, ignorablesMatcher, n);
        object = Grouper.forStrategy(NumberFormatter.GroupingStrategy.AUTO).withLocaleData((ULocale)object, (PatternStringParser.ParsedPatternInfo)object2);
        numberParserImpl.addMatcher(ignorablesMatcher);
        numberParserImpl.addMatcher(DecimalMatcher.getInstance(decimalFormatSymbols, (Grouper)object, n));
        numberParserImpl.addMatcher(MinusSignMatcher.getInstance(decimalFormatSymbols, false));
        numberParserImpl.addMatcher(PlusSignMatcher.getInstance(decimalFormatSymbols, false));
        numberParserImpl.addMatcher(PercentMatcher.getInstance(decimalFormatSymbols));
        numberParserImpl.addMatcher(PermilleMatcher.getInstance(decimalFormatSymbols));
        numberParserImpl.addMatcher(NanMatcher.getInstance(decimalFormatSymbols, n));
        numberParserImpl.addMatcher(InfinityMatcher.getInstance(decimalFormatSymbols));
        numberParserImpl.addMatcher(PaddingMatcher.getInstance("@"));
        numberParserImpl.addMatcher(ScientificMatcher.getInstance(decimalFormatSymbols, (Grouper)object));
        numberParserImpl.addMatcher(CombinedCurrencyMatcher.getInstance(currency, decimalFormatSymbols, n));
        numberParserImpl.addMatcher(new RequireNumberValidator());
        numberParserImpl.freeze();
        return numberParserImpl;
    }

    private void parseGreedyRecursive(StringSegment stringSegment, ParsedNumber parsedNumber) {
        if (stringSegment.length() == 0) {
            return;
        }
        int n = stringSegment.getOffset();
        for (int i = 0; i < this.matchers.size(); ++i) {
            NumberParseMatcher numberParseMatcher = this.matchers.get(i);
            if (!numberParseMatcher.smokeTest(stringSegment)) continue;
            numberParseMatcher.match(stringSegment, parsedNumber);
            if (stringSegment.getOffset() == n) continue;
            this.parseGreedyRecursive(stringSegment, parsedNumber);
            stringSegment.setOffset(n);
            return;
        }
    }

    private void parseLongestRecursive(StringSegment stringSegment, ParsedNumber parsedNumber) {
        if (stringSegment.length() == 0) {
            return;
        }
        ParsedNumber parsedNumber2 = new ParsedNumber();
        parsedNumber2.copyFrom(parsedNumber);
        ParsedNumber parsedNumber3 = new ParsedNumber();
        int n = stringSegment.getOffset();
        block0 : for (int i = 0; i < this.matchers.size(); ++i) {
            NumberParseMatcher numberParseMatcher = this.matchers.get(i);
            if (!numberParseMatcher.smokeTest(stringSegment)) continue;
            int n2 = 0;
            while (n2 < stringSegment.length()) {
                n2 += Character.charCount(stringSegment.codePointAt(n2));
                parsedNumber3.copyFrom(parsedNumber2);
                stringSegment.setLength(n2);
                boolean bl = numberParseMatcher.match(stringSegment, parsedNumber3);
                stringSegment.resetLength();
                if (stringSegment.getOffset() - n == n2) {
                    this.parseLongestRecursive(stringSegment, parsedNumber3);
                    if (parsedNumber3.isBetterThan(parsedNumber)) {
                        parsedNumber.copyFrom(parsedNumber3);
                    }
                }
                stringSegment.setOffset(n);
                if (bl) continue;
                continue block0;
            }
        }
    }

    public static Number parseStatic(String string, ParsePosition parsePosition, DecimalFormatProperties object, DecimalFormatSymbols object2) {
        object = NumberParserImpl.createParserFromProperties((DecimalFormatProperties)object, (DecimalFormatSymbols)object2, false);
        object2 = new ParsedNumber();
        ((NumberParserImpl)object).parse(string, true, (ParsedNumber)object2);
        if (((ParsedNumber)object2).success()) {
            parsePosition.setIndex(((ParsedNumber)object2).charEnd);
            return ((ParsedNumber)object2).getNumber();
        }
        parsePosition.setErrorIndex(((ParsedNumber)object2).charEnd);
        return null;
    }

    public static CurrencyAmount parseStaticCurrency(String string, ParsePosition parsePosition, DecimalFormatProperties object, DecimalFormatSymbols object2) {
        object = NumberParserImpl.createParserFromProperties((DecimalFormatProperties)object, (DecimalFormatSymbols)object2, true);
        object2 = new ParsedNumber();
        ((NumberParserImpl)object).parse(string, true, (ParsedNumber)object2);
        if (((ParsedNumber)object2).success()) {
            parsePosition.setIndex(((ParsedNumber)object2).charEnd);
            return new CurrencyAmount(((ParsedNumber)object2).getNumber(), Currency.getInstance(((ParsedNumber)object2).currencyCode));
        }
        parsePosition.setErrorIndex(((ParsedNumber)object2).charEnd);
        return null;
    }

    public void addMatcher(NumberParseMatcher numberParseMatcher) {
        this.matchers.add(numberParseMatcher);
    }

    public void addMatchers(Collection<? extends NumberParseMatcher> collection) {
        this.matchers.addAll(collection);
    }

    public void freeze() {
        this.frozen = true;
    }

    public int getParseFlags() {
        return this.parseFlags;
    }

    public void parse(String object, int n, boolean bl, ParsedNumber parsedNumber) {
        int n2 = this.parseFlags;
        boolean bl2 = true;
        if ((n2 & 1) == 0) {
            bl2 = false;
        }
        object = new StringSegment((String)object, bl2);
        ((StringSegment)object).adjustOffset(n);
        if (bl) {
            this.parseGreedyRecursive((StringSegment)object, parsedNumber);
        } else {
            this.parseLongestRecursive((StringSegment)object, parsedNumber);
        }
        object = this.matchers.iterator();
        while (object.hasNext()) {
            ((NumberParseMatcher)object.next()).postProcess(parsedNumber);
        }
        parsedNumber.postProcess();
    }

    public void parse(String string, boolean bl, ParsedNumber parsedNumber) {
        this.parse(string, 0, bl, parsedNumber);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<NumberParserImpl matchers=");
        stringBuilder.append(this.matchers.toString());
        stringBuilder.append(">");
        return stringBuilder.toString();
    }
}

