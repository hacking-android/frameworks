/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.CurrencyData;
import android.icu.impl.ICUResourceBundle;
import android.icu.impl.StandardPlural;
import android.icu.impl.UResource;
import android.icu.util.ICUException;
import android.icu.util.ULocale;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;

public class ICUCurrencyDisplayInfoProvider
implements CurrencyData.CurrencyDisplayInfoProvider {
    private volatile ICUCurrencyDisplayInfo currencyDisplayInfoCache = null;

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public CurrencyData.CurrencyDisplayInfo getInstance(ULocale object, boolean bl) {
        ICUCurrencyDisplayInfo iCUCurrencyDisplayInfo;
        Object uLocale = object;
        if (object == null) {
            uLocale = ULocale.ROOT;
        }
        if ((iCUCurrencyDisplayInfo = this.currencyDisplayInfoCache) != null && iCUCurrencyDisplayInfo.locale.equals(uLocale)) {
            object = iCUCurrencyDisplayInfo;
            if (iCUCurrencyDisplayInfo.fallback == bl) return object;
        }
        if (bl) {
            object = ICUResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b/curr", (ULocale)uLocale, ICUResourceBundle.OpenType.LOCALE_DEFAULT_ROOT);
        } else {
            object = ICUResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b/curr", (ULocale)uLocale, ICUResourceBundle.OpenType.LOCALE_ONLY);
        }
        this.currencyDisplayInfoCache = object = new ICUCurrencyDisplayInfo((ULocale)uLocale, (ICUResourceBundle)object, bl);
        return object;
        catch (MissingResourceException missingResourceException) {
            return null;
        }
    }

    @Override
    public boolean hasData() {
        return true;
    }

    static class ICUCurrencyDisplayInfo
    extends CurrencyData.CurrencyDisplayInfo {
        final boolean fallback;
        private volatile FormattingData formattingDataCache = null;
        final ULocale locale;
        private volatile NarrowSymbol narrowSymbolCache = null;
        private volatile SoftReference<ParsingData> parsingDataCache = new SoftReference<Object>(null);
        private volatile String[] pluralsDataCache = null;
        private final ICUResourceBundle rb;
        private volatile CurrencyData.CurrencySpacingInfo spacingInfoCache = null;
        private volatile Map<String, String> unitPatternsCache = null;

        public ICUCurrencyDisplayInfo(ULocale uLocale, ICUResourceBundle iCUResourceBundle, boolean bl) {
            this.locale = uLocale;
            this.fallback = bl;
            this.rb = iCUResourceBundle;
        }

        FormattingData fetchFormattingData(String string) {
            FormattingData formattingData;
            block3 : {
                Object object;
                block2 : {
                    object = this.formattingDataCache;
                    if (object == null) break block2;
                    formattingData = object;
                    if (((FormattingData)object).isoCode.equals(string)) break block3;
                }
                formattingData = new FormattingData(string);
                object = new CurrencySink(this.fallback ^ true, CurrencySink.EntrypointTable.CURRENCIES);
                ((CurrencySink)object).formattingData = formattingData;
                ICUResourceBundle iCUResourceBundle = this.rb;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Currencies/");
                stringBuilder.append(string);
                iCUResourceBundle.getAllItemsWithFallbackNoFail(stringBuilder.toString(), (UResource.Sink)object);
                this.formattingDataCache = formattingData;
            }
            return formattingData;
        }

        NarrowSymbol fetchNarrowSymbol(String string) {
            NarrowSymbol narrowSymbol;
            block3 : {
                Object object;
                block2 : {
                    object = this.narrowSymbolCache;
                    if (object == null) break block2;
                    narrowSymbol = object;
                    if (((NarrowSymbol)object).isoCode.equals(string)) break block3;
                }
                narrowSymbol = new NarrowSymbol(string);
                CurrencySink currencySink = new CurrencySink(this.fallback ^ true, CurrencySink.EntrypointTable.CURRENCY_NARROW);
                currencySink.narrowSymbol = narrowSymbol;
                object = this.rb;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Currencies%narrow/");
                stringBuilder.append(string);
                ((ICUResourceBundle)object).getAllItemsWithFallbackNoFail(stringBuilder.toString(), currencySink);
                this.narrowSymbolCache = narrowSymbol;
            }
            return narrowSymbol;
        }

        ParsingData fetchParsingData() {
            Object object = this.parsingDataCache.get();
            ParsingData parsingData = object;
            if (object == null) {
                parsingData = new ParsingData();
                object = new CurrencySink(this.fallback ^ true, CurrencySink.EntrypointTable.TOP);
                ((CurrencySink)object).parsingData = parsingData;
                this.rb.getAllItemsWithFallback("", (UResource.Sink)object);
                this.parsingDataCache = new SoftReference<ParsingData>(parsingData);
            }
            return parsingData;
        }

        String[] fetchPluralsData(String string) {
            String[] arrstring;
            block3 : {
                Object object;
                block2 : {
                    object = this.pluralsDataCache;
                    if (object == null) break block2;
                    arrstring = object;
                    if (object[0].equals(string)) break block3;
                }
                arrstring = new String[StandardPlural.COUNT + 1];
                arrstring[0] = string;
                CurrencySink currencySink = new CurrencySink(this.fallback ^ true, CurrencySink.EntrypointTable.CURRENCY_PLURALS);
                currencySink.pluralsData = arrstring;
                object = this.rb;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("CurrencyPlurals/");
                stringBuilder.append(string);
                ((ICUResourceBundle)object).getAllItemsWithFallbackNoFail(stringBuilder.toString(), currencySink);
                this.pluralsDataCache = arrstring;
            }
            return arrstring;
        }

        CurrencyData.CurrencySpacingInfo fetchSpacingInfo() {
            Object object = this.spacingInfoCache;
            CurrencyData.CurrencySpacingInfo currencySpacingInfo = object;
            if (object == null) {
                currencySpacingInfo = new CurrencyData.CurrencySpacingInfo();
                object = new CurrencySink(this.fallback ^ true, CurrencySink.EntrypointTable.CURRENCY_SPACING);
                ((CurrencySink)object).spacingInfo = currencySpacingInfo;
                this.rb.getAllItemsWithFallback("currencySpacing", (UResource.Sink)object);
                this.spacingInfoCache = currencySpacingInfo;
            }
            return currencySpacingInfo;
        }

        Map<String, String> fetchUnitPatterns() {
            Object object = this.unitPatternsCache;
            Map<String, String> map = object;
            if (object == null) {
                map = new HashMap<String, String>();
                object = new CurrencySink(this.fallback ^ true, CurrencySink.EntrypointTable.CURRENCY_UNIT_PATTERNS);
                ((CurrencySink)object).unitPatterns = map;
                this.rb.getAllItemsWithFallback("CurrencyUnitPatterns", (UResource.Sink)object);
                this.unitPatternsCache = map;
            }
            return map;
        }

        @Override
        public CurrencyData.CurrencyFormatInfo getFormatInfo(String string) {
            return this.fetchFormattingData((String)string).formatInfo;
        }

        @Override
        public String getName(String string) {
            FormattingData formattingData = this.fetchFormattingData(string);
            if (formattingData.displayName == null && this.fallback) {
                return string;
            }
            return formattingData.displayName;
        }

        @Override
        public String getNarrowSymbol(String string) {
            NarrowSymbol narrowSymbol = this.fetchNarrowSymbol(string);
            if (narrowSymbol.narrowSymbol == null && this.fallback) {
                return string;
            }
            return narrowSymbol.narrowSymbol;
        }

        @Override
        public String getPluralName(String string, String object) {
            Object object2 = StandardPlural.orNullFromString(object);
            String[] arrstring = this.fetchPluralsData(string);
            object = null;
            if (object2 != null) {
                object = arrstring[object2.ordinal() + 1];
            }
            object2 = object;
            if (object == null) {
                object2 = object;
                if (this.fallback) {
                    object2 = arrstring[StandardPlural.OTHER.ordinal() + 1];
                }
            }
            object = object2;
            if (object2 == null) {
                object = object2;
                if (this.fallback) {
                    object = this.fetchFormattingData((String)string).displayName;
                }
            }
            object2 = object;
            if (object == null) {
                object2 = object;
                if (this.fallback) {
                    object2 = string;
                }
            }
            return object2;
        }

        @Override
        public CurrencyData.CurrencySpacingInfo getSpacingInfo() {
            CurrencyData.CurrencySpacingInfo currencySpacingInfo = this.fetchSpacingInfo();
            if (!(currencySpacingInfo.hasBeforeCurrency && currencySpacingInfo.hasAfterCurrency || !this.fallback)) {
                return CurrencyData.CurrencySpacingInfo.DEFAULT;
            }
            return currencySpacingInfo;
        }

        @Override
        public String getSymbol(String string) {
            FormattingData formattingData = this.fetchFormattingData(string);
            if (formattingData.symbol == null && this.fallback) {
                return string;
            }
            return formattingData.symbol;
        }

        @Override
        public ULocale getULocale() {
            return this.rb.getULocale();
        }

        @Override
        public Map<String, String> getUnitPatterns() {
            return this.fetchUnitPatterns();
        }

        @Override
        public Map<String, String> nameMap() {
            return this.fetchParsingData().nameToIsoCode;
        }

        @Override
        public Map<String, String> symbolMap() {
            return this.fetchParsingData().symbolToIsoCode;
        }

        private static final class CurrencySink
        extends UResource.Sink {
            static final /* synthetic */ boolean $assertionsDisabled = false;
            final EntrypointTable entrypointTable;
            FormattingData formattingData = null;
            NarrowSymbol narrowSymbol = null;
            final boolean noRoot;
            ParsingData parsingData = null;
            String[] pluralsData = null;
            CurrencyData.CurrencySpacingInfo spacingInfo = null;
            Map<String, String> unitPatterns = null;

            CurrencySink(boolean bl, EntrypointTable entrypointTable) {
                this.noRoot = bl;
                this.entrypointTable = entrypointTable;
            }

            private void consumeTopTable(UResource.Key key, UResource.Value value) {
                UResource.Table table = value.getTable();
                int n = 0;
                while (table.getKeyAndValue(n, key, value)) {
                    if (key.contentEquals("Currencies")) {
                        this.consumeCurrenciesTable(key, value);
                    } else if (key.contentEquals("Currencies%variant")) {
                        this.consumeCurrenciesVariantTable(key, value);
                    } else if (key.contentEquals("CurrencyPlurals")) {
                        this.consumeCurrencyPluralsTable(key, value);
                    }
                    ++n;
                }
            }

            void consumeCurrenciesEntry(UResource.Key charSequence, UResource.Value object) {
                charSequence = charSequence.toString();
                if (((UResource.Value)object).getType() == 8) {
                    Object object2 = ((UResource.Value)object).getArray();
                    if (this.formattingData.symbol == null) {
                        object2.getValue(0, (UResource.Value)object);
                        this.formattingData.symbol = ((UResource.Value)object).getString();
                    }
                    if (this.formattingData.displayName == null) {
                        object2.getValue(1, (UResource.Value)object);
                        this.formattingData.displayName = ((UResource.Value)object).getString();
                    }
                    if (object2.getSize() > 2 && this.formattingData.formatInfo == null) {
                        object2.getValue(2, (UResource.Value)object);
                        UResource.Array array = ((UResource.Value)object).getArray();
                        array.getValue(0, (UResource.Value)object);
                        object2 = ((UResource.Value)object).getString();
                        array.getValue(1, (UResource.Value)object);
                        String string = ((UResource.Value)object).getString();
                        array.getValue(2, (UResource.Value)object);
                        object = ((UResource.Value)object).getString();
                        this.formattingData.formatInfo = new CurrencyData.CurrencyFormatInfo((String)charSequence, (String)object2, string, (String)object);
                    }
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Unexpected data type in Currencies table for ");
                ((StringBuilder)object).append((String)charSequence);
                throw new ICUException(((StringBuilder)object).toString());
            }

            void consumeCurrenciesNarrowEntry(UResource.Key key, UResource.Value value) {
                if (this.narrowSymbol.narrowSymbol == null) {
                    this.narrowSymbol.narrowSymbol = value.getString();
                }
            }

            void consumeCurrenciesTable(UResource.Key charSequence, UResource.Value value) {
                UResource.Table table = value.getTable();
                int n = 0;
                while (table.getKeyAndValue(n, (UResource.Key)charSequence, value)) {
                    String string = ((UResource.Key)charSequence).toString();
                    if (value.getType() == 8) {
                        UResource.Array array = value.getArray();
                        this.parsingData.symbolToIsoCode.put(string, string);
                        array.getValue(0, value);
                        this.parsingData.symbolToIsoCode.put(value.getString(), string);
                        array.getValue(1, value);
                        this.parsingData.nameToIsoCode.put(value.getString(), string);
                        ++n;
                        continue;
                    }
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Unexpected data type in Currencies table for ");
                    ((StringBuilder)charSequence).append(string);
                    throw new ICUException(((StringBuilder)charSequence).toString());
                }
            }

            void consumeCurrenciesVariantTable(UResource.Key key, UResource.Value value) {
                UResource.Table table = value.getTable();
                int n = 0;
                while (table.getKeyAndValue(n, key, value)) {
                    String string = key.toString();
                    this.parsingData.symbolToIsoCode.put(value.getString(), string);
                    ++n;
                }
            }

            void consumeCurrencyPluralsEntry(UResource.Key key, UResource.Value object) {
                UResource.Table table = ((UResource.Value)object).getTable();
                int n = 0;
                while (table.getKeyAndValue(n, key, (UResource.Value)object)) {
                    StandardPlural standardPlural = StandardPlural.orNullFromString(key.toString());
                    if (standardPlural != null) {
                        if (this.pluralsData[standardPlural.ordinal() + 1] == null) {
                            this.pluralsData[standardPlural.ordinal() + 1] = ((UResource.Value)object).getString();
                        }
                        ++n;
                        continue;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Could not make StandardPlural from keyword ");
                    ((StringBuilder)object).append((Object)key);
                    throw new ICUException(((StringBuilder)object).toString());
                }
            }

            void consumeCurrencyPluralsTable(UResource.Key key, UResource.Value object) {
                UResource.Table table = ((UResource.Value)object).getTable();
                int n = 0;
                while (table.getKeyAndValue(n, key, (UResource.Value)object)) {
                    String string = key.toString();
                    UResource.Table table2 = ((UResource.Value)object).getTable();
                    int n2 = 0;
                    while (table2.getKeyAndValue(n2, key, (UResource.Value)object)) {
                        if (StandardPlural.orNullFromString(key.toString()) != null) {
                            this.parsingData.nameToIsoCode.put(((UResource.Value)object).getString(), string);
                            ++n2;
                            continue;
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Could not make StandardPlural from keyword ");
                        ((StringBuilder)object).append((Object)key);
                        throw new ICUException(((StringBuilder)object).toString());
                    }
                    ++n;
                }
            }

            void consumeCurrencySpacingTable(UResource.Key key, UResource.Value value) {
                UResource.Table table = value.getTable();
                int n = 0;
                while (table.getKeyAndValue(n, key, value)) {
                    block6 : {
                        CurrencyData.CurrencySpacingInfo.SpacingType spacingType;
                        block5 : {
                            block4 : {
                                if (!key.contentEquals("beforeCurrency")) break block4;
                                spacingType = CurrencyData.CurrencySpacingInfo.SpacingType.BEFORE;
                                this.spacingInfo.hasBeforeCurrency = true;
                                break block5;
                            }
                            if (!key.contentEquals("afterCurrency")) break block6;
                            spacingType = CurrencyData.CurrencySpacingInfo.SpacingType.AFTER;
                            this.spacingInfo.hasAfterCurrency = true;
                        }
                        UResource.Table table2 = value.getTable();
                        int n2 = 0;
                        while (table2.getKeyAndValue(n2, key, value)) {
                            block10 : {
                                CurrencyData.CurrencySpacingInfo.SpacingPattern spacingPattern;
                                block8 : {
                                    block9 : {
                                        block7 : {
                                            if (!key.contentEquals("currencyMatch")) break block7;
                                            spacingPattern = CurrencyData.CurrencySpacingInfo.SpacingPattern.CURRENCY_MATCH;
                                            break block8;
                                        }
                                        if (!key.contentEquals("surroundingMatch")) break block9;
                                        spacingPattern = CurrencyData.CurrencySpacingInfo.SpacingPattern.SURROUNDING_MATCH;
                                        break block8;
                                    }
                                    if (!key.contentEquals("insertBetween")) break block10;
                                    spacingPattern = CurrencyData.CurrencySpacingInfo.SpacingPattern.INSERT_BETWEEN;
                                }
                                this.spacingInfo.setSymbolIfNull(spacingType, spacingPattern, value.getString());
                            }
                            ++n2;
                        }
                    }
                    ++n;
                }
            }

            void consumeCurrencyUnitPatternsTable(UResource.Key key, UResource.Value value) {
                UResource.Table table = value.getTable();
                int n = 0;
                while (table.getKeyAndValue(n, key, value)) {
                    String string = key.toString();
                    if (this.unitPatterns.get(string) == null) {
                        this.unitPatterns.put(string, value.getString());
                    }
                    ++n;
                }
            }

            @Override
            public void put(UResource.Key key, UResource.Value value, boolean bl) {
                if (this.noRoot && bl) {
                    return;
                }
                switch (this.entrypointTable) {
                    default: {
                        break;
                    }
                    case CURRENCY_UNIT_PATTERNS: {
                        this.consumeCurrencyUnitPatternsTable(key, value);
                        break;
                    }
                    case CURRENCY_SPACING: {
                        this.consumeCurrencySpacingTable(key, value);
                        break;
                    }
                    case CURRENCY_NARROW: {
                        this.consumeCurrenciesNarrowEntry(key, value);
                        break;
                    }
                    case CURRENCY_PLURALS: {
                        this.consumeCurrencyPluralsEntry(key, value);
                        break;
                    }
                    case CURRENCIES: {
                        this.consumeCurrenciesEntry(key, value);
                        break;
                    }
                    case TOP: {
                        this.consumeTopTable(key, value);
                    }
                }
            }

            static enum EntrypointTable {
                TOP,
                CURRENCIES,
                CURRENCY_PLURALS,
                CURRENCY_NARROW,
                CURRENCY_SPACING,
                CURRENCY_UNIT_PATTERNS;
                
            }

        }

        static class FormattingData {
            String displayName = null;
            CurrencyData.CurrencyFormatInfo formatInfo = null;
            final String isoCode;
            String symbol = null;

            FormattingData(String string) {
                this.isoCode = string;
            }
        }

        static class NarrowSymbol {
            final String isoCode;
            String narrowSymbol = null;

            NarrowSymbol(String string) {
                this.isoCode = string;
            }
        }

        static class ParsingData {
            Map<String, String> nameToIsoCode = new HashMap<String, String>();
            Map<String, String> symbolToIsoCode = new HashMap<String, String>();

            ParsingData() {
            }
        }

    }

}

