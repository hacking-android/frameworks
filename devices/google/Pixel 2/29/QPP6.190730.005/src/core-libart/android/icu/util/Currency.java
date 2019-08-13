/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.impl.CacheBase;
import android.icu.impl.ICUCache;
import android.icu.impl.ICUDebug;
import android.icu.impl.ICUResourceBundle;
import android.icu.impl.SimpleCache;
import android.icu.impl.SoftCache;
import android.icu.impl.StaticUnicodeSets;
import android.icu.impl.TextTrieMap;
import android.icu.text.CurrencyDisplayNames;
import android.icu.text.CurrencyMetaInfo;
import android.icu.util.MeasureUnit;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import java.io.ObjectStreamException;
import java.lang.ref.SoftReference;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;

public class Currency
extends MeasureUnit {
    private static SoftReference<Set<String>> ALL_CODES_AS_SET;
    private static SoftReference<List<String>> ALL_TENDER_CODES;
    private static ICUCache<ULocale, List<TextTrieMap<CurrencyStringInfo>>> CURRENCY_NAME_CACHE;
    private static final boolean DEBUG;
    private static final String[] EMPTY_STRING_ARRAY;
    private static final String EUR_STR = "EUR";
    public static final int LONG_NAME = 1;
    public static final int NARROW_SYMBOL_NAME = 3;
    public static final int PLURAL_LONG_NAME = 2;
    private static final int[] POW10;
    public static final int SYMBOL_NAME = 0;
    private static final ULocale UND;
    private static final CacheBase<String, Currency, Void> regionCurrencyCache;
    private static final long serialVersionUID = -5839973855554750484L;
    private static ServiceShim shim;
    private final String isoCode;

    static {
        DEBUG = ICUDebug.enabled("currency");
        CURRENCY_NAME_CACHE = new SimpleCache<ULocale, List<TextTrieMap<CurrencyStringInfo>>>();
        regionCurrencyCache = new SoftCache<String, Currency, Void>(){

            @Override
            protected Currency createInstance(String string, Void void_) {
                return Currency.loadCurrency(string);
            }
        };
        UND = new ULocale("und");
        EMPTY_STRING_ARRAY = new String[0];
        POW10 = new int[]{1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000};
    }

    protected Currency(String string) {
        super("currency", string);
        this.isoCode = string;
    }

    static Currency createCurrency(ULocale object) {
        String string = ((ULocale)object).getVariant();
        if ("EURO".equals(string)) {
            return Currency.getInstance(EUR_STR);
        }
        String string2 = ULocale.getRegionForSupplementalData((ULocale)object, false);
        object = string2;
        if ("PREEURO".equals(string)) {
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append('-');
            object = ((StringBuilder)object).toString();
        }
        return regionCurrencyCache.getInstance((String)object, null);
    }

    public static Currency fromJavaCurrency(java.util.Currency currency) {
        return Currency.getInstance(currency.getCurrencyCode());
    }

    private static Set<String> getAllCurrenciesAsSet() {
        synchronized (Currency.class) {
            SoftReference<Set<String>> softReference;
            block7 : {
                if (ALL_CODES_AS_SET != null) break block7;
                softReference = null;
            }
            softReference = ALL_CODES_AS_SET.get();
            Set<String> set = softReference;
            if (softReference == null) {
                set = CurrencyMetaInfo.getInstance();
                softReference = new SoftReference<Set<String>>(((CurrencyMetaInfo)((Object)set)).currencies(CurrencyMetaInfo.CurrencyFilter.all()));
                set = Collections.unmodifiableSet(softReference);
                softReference = new SoftReference<Set<String>>(set);
                ALL_CODES_AS_SET = softReference;
            }
            return set;
            finally {
            }
        }
    }

    private static List<String> getAllTenderCurrencies() {
        synchronized (Currency.class) {
            SoftReference<List<String>> softReference;
            block7 : {
                if (ALL_TENDER_CODES != null) break block7;
                softReference = null;
            }
            softReference = ALL_TENDER_CODES.get();
            List<String> list = softReference;
            if (softReference == null) {
                list = Collections.unmodifiableList(Currency.getTenderCurrencies(CurrencyMetaInfo.CurrencyFilter.all()));
                softReference = new SoftReference<List<String>>(list);
                ALL_TENDER_CODES = softReference;
            }
            return list;
            finally {
            }
        }
    }

    public static Set<Currency> getAvailableCurrencies() {
        Object object = CurrencyMetaInfo.getInstance().currencies(CurrencyMetaInfo.CurrencyFilter.all());
        HashSet<Currency> hashSet = new HashSet<Currency>(object.size());
        object = object.iterator();
        while (object.hasNext()) {
            hashSet.add(Currency.getInstance((String)object.next()));
        }
        return hashSet;
    }

    public static String[] getAvailableCurrencyCodes(ULocale list, Date date) {
        list = ULocale.getRegionForSupplementalData((ULocale)((Object)list), false);
        list = Currency.getTenderCurrencies(CurrencyMetaInfo.CurrencyFilter.onDate(date).withRegion((String)((Object)list)));
        if (list.isEmpty()) {
            return null;
        }
        return list.toArray(new String[list.size()]);
    }

    public static String[] getAvailableCurrencyCodes(Locale locale, Date date) {
        return Currency.getAvailableCurrencyCodes(ULocale.forLocale(locale), date);
    }

    public static Locale[] getAvailableLocales() {
        ServiceShim serviceShim = shim;
        if (serviceShim == null) {
            return ICUResourceBundle.getAvailableLocales();
        }
        return serviceShim.getAvailableLocales();
    }

    public static ULocale[] getAvailableULocales() {
        ServiceShim serviceShim = shim;
        if (serviceShim == null) {
            return ICUResourceBundle.getAvailableULocales();
        }
        return serviceShim.getAvailableULocales();
    }

    private static List<TextTrieMap<CurrencyStringInfo>> getCurrencyTrieVec(ULocale uLocale) {
        Object object = CURRENCY_NAME_CACHE.get(uLocale);
        List<TextTrieMap<CurrencyStringInfo>> list = object;
        if (object == null) {
            object = new TextTrieMap(true);
            TextTrieMap textTrieMap = new TextTrieMap(false);
            list = new ArrayList<TextTrieMap<CurrencyStringInfo>>();
            list.add(textTrieMap);
            list.add((TextTrieMap<CurrencyStringInfo>)object);
            Currency.setupCurrencyTrieVec(uLocale, list);
            CURRENCY_NAME_CACHE.put(uLocale, list);
        }
        return list;
    }

    public static Currency getInstance(ULocale uLocale) {
        Object object = uLocale.getKeywordValue("currency");
        if (object != null) {
            return Currency.getInstance((String)object);
        }
        object = shim;
        if (object == null) {
            return Currency.createCurrency(uLocale);
        }
        return ((ServiceShim)object).createInstance(uLocale);
    }

    public static Currency getInstance(String string) {
        if (string != null) {
            if (Currency.isAlpha3Code(string)) {
                return (Currency)MeasureUnit.internalGetInstance("currency", string.toUpperCase(Locale.ENGLISH));
            }
            throw new IllegalArgumentException("The input currency code is not 3-letter alphabetic code.");
        }
        throw new NullPointerException("The input currency code is null.");
    }

    public static Currency getInstance(Locale locale) {
        return Currency.getInstance(ULocale.forLocale(locale));
    }

    public static final String[] getKeywordValuesForLocale(String list, ULocale uLocale, boolean bl) {
        if (!"currency".equals(list)) {
            return EMPTY_STRING_ARRAY;
        }
        if (!bl) {
            return Currency.getAllTenderCurrencies().toArray(new String[0]);
        }
        if (UND.equals(uLocale)) {
            return EMPTY_STRING_ARRAY;
        }
        list = ULocale.getRegionForSupplementalData(uLocale, true);
        list = Currency.getTenderCurrencies(CurrencyMetaInfo.CurrencyFilter.now().withRegion((String)((Object)list)));
        if (list.size() == 0) {
            return EMPTY_STRING_ARRAY;
        }
        return list.toArray(new String[list.size()]);
    }

    @Deprecated
    public static TextTrieMap<CurrencyStringInfo> getParsingTrie(ULocale object, int n) {
        object = Currency.getCurrencyTrieVec((ULocale)object);
        if (n == 1) {
            return (TextTrieMap)object.get(1);
        }
        return (TextTrieMap)object.get(0);
    }

    private static ServiceShim getShim() {
        if (shim == null) {
            try {
                shim = (ServiceShim)Class.forName("android.icu.util.CurrencyServiceShim").newInstance();
            }
            catch (Exception exception) {
                if (DEBUG) {
                    exception.printStackTrace();
                }
                throw new RuntimeException(exception.getMessage());
            }
        }
        return shim;
    }

    private static List<String> getTenderCurrencies(CurrencyMetaInfo.CurrencyFilter currencyFilter) {
        return CurrencyMetaInfo.getInstance().currencies(currencyFilter.withTender());
    }

    private static boolean isAlpha3Code(String string) {
        if (string.length() != 3) {
            return false;
        }
        for (int i = 0; i < 3; ++i) {
            char c = string.charAt(i);
            if (c >= 'A' && (c <= 'Z' || c >= 'a') && c <= 'z') {
                continue;
            }
            return false;
        }
        return true;
    }

    public static boolean isAvailable(String object, Date date, Date date2) {
        if (!Currency.isAlpha3Code((String)object)) {
            return false;
        }
        if (date != null && date2 != null && date.after(date2)) {
            throw new IllegalArgumentException("To is before from");
        }
        String string = ((String)object).toUpperCase(Locale.ENGLISH);
        if (!Currency.getAllCurrenciesAsSet().contains(string)) {
            return false;
        }
        if (date == null && date2 == null) {
            return true;
        }
        object = CurrencyMetaInfo.getInstance();
        return ((CurrencyMetaInfo)object).currencies(CurrencyMetaInfo.CurrencyFilter.onDateRange(date, date2).withCurrency(string)).contains(string);
    }

    private static Currency loadCurrency(String string) {
        boolean bl;
        if (string.endsWith("-")) {
            string = string.substring(0, string.length() - 1);
            bl = true;
        } else {
            bl = false;
        }
        List<String> list = CurrencyMetaInfo.getInstance().currencies(CurrencyMetaInfo.CurrencyFilter.onRegion(string));
        if (!list.isEmpty()) {
            String string2;
            string = string2 = list.get(0);
            if (bl) {
                string = string2;
                if (EUR_STR.equals(string2)) {
                    if (list.size() < 2) {
                        return null;
                    }
                    string = list.get(1);
                }
            }
            return Currency.getInstance(string);
        }
        return null;
    }

    @Deprecated
    public static String parse(ULocale object, String string, int n, ParsePosition parsePosition) {
        int n2;
        Object object2 = Currency.getCurrencyTrieVec((ULocale)object);
        Object object3 = object2.get(1);
        object = new CurrencyNameResultHandler();
        ((TextTrieMap)object3).find(string, parsePosition.getIndex(), (TextTrieMap.ResultHandler<CurrencyStringInfo>)object);
        object3 = ((CurrencyNameResultHandler)object).getBestCurrencyISOCode();
        int n3 = n2 = ((CurrencyNameResultHandler)object).getBestMatchLength();
        object = object3;
        if (n != 1) {
            object = object2.get(0);
            object2 = new CurrencyNameResultHandler();
            ((TextTrieMap)object).find(string, parsePosition.getIndex(), (TextTrieMap.ResultHandler<CurrencyStringInfo>)object2);
            n3 = n2;
            object = object3;
            if (((CurrencyNameResultHandler)object2).getBestMatchLength() > n2) {
                object = ((CurrencyNameResultHandler)object2).getBestCurrencyISOCode();
                n3 = ((CurrencyNameResultHandler)object2).getBestMatchLength();
            }
        }
        parsePosition.setIndex(parsePosition.getIndex() + n3);
        return object;
    }

    private Object readResolve() throws ObjectStreamException {
        return Currency.getInstance(this.isoCode);
    }

    public static Object registerInstance(Currency currency, ULocale uLocale) {
        return Currency.getShim().registerInstance(currency, uLocale);
    }

    private static void setupCurrencyTrieVec(ULocale object6, List<TextTrieMap<CurrencyStringInfo>> object2) {
        TextTrieMap textTrieMap;
        TextTrieMap textTrieMap2 = (TextTrieMap)textTrieMap.get(0);
        textTrieMap = (TextTrieMap)textTrieMap.get(1);
        Object object = CurrencyDisplayNames.getInstance((ULocale)object6);
        for (Map.Entry<String, String> entry : ((CurrencyDisplayNames)object).symbolMap().entrySet()) {
            Object object3 = entry.getKey();
            String string = entry.getValue();
            StaticUnicodeSets.Key key = StaticUnicodeSets.chooseCurrency((String)object3);
            CurrencyStringInfo currencyStringInfo = new CurrencyStringInfo(string, (String)object3);
            if (key != null) {
                object3 = StaticUnicodeSets.get(key).iterator();
                while (object3.hasNext()) {
                    textTrieMap2.put((String)object3.next(), currencyStringInfo);
                }
                continue;
            }
            textTrieMap2.put((CharSequence)object3, currencyStringInfo);
        }
        for (Map.Entry entry : ((CurrencyDisplayNames)object).nameMap().entrySet()) {
            object = (String)entry.getKey();
            textTrieMap.put((CharSequence)object, new CurrencyStringInfo((String)entry.getValue(), (String)object));
        }
    }

    public static boolean unregister(Object object) {
        if (object != null) {
            ServiceShim serviceShim = shim;
            if (serviceShim == null) {
                return false;
            }
            return serviceShim.unregister(object);
        }
        throw new IllegalArgumentException("registryKey must not be null");
    }

    private Object writeReplace() throws ObjectStreamException {
        return new MeasureUnit.MeasureUnitProxy(this.type, this.subType);
    }

    public String getCurrencyCode() {
        return this.subType;
    }

    public int getDefaultFractionDigits() {
        return this.getDefaultFractionDigits(CurrencyUsage.STANDARD);
    }

    public int getDefaultFractionDigits(CurrencyUsage currencyUsage) {
        return CurrencyMetaInfo.getInstance().currencyDigits((String)this.subType, (CurrencyUsage)currencyUsage).fractionDigits;
    }

    public String getDisplayName() {
        return this.getName(Locale.getDefault(), 1, null);
    }

    public String getDisplayName(Locale locale) {
        return this.getName(locale, 1, null);
    }

    public String getName(ULocale uLocale, int n, String string, boolean[] arrbl) {
        if (n != 2) {
            return this.getName(uLocale, n, arrbl);
        }
        if (arrbl != null) {
            arrbl[0] = false;
        }
        return CurrencyDisplayNames.getInstance(uLocale).getPluralName(this.subType, string);
    }

    public String getName(ULocale object, int n, boolean[] arrbl) {
        if (arrbl != null) {
            arrbl[0] = false;
        }
        object = CurrencyDisplayNames.getInstance((ULocale)object);
        if (n != 0) {
            if (n != 1) {
                if (n == 3) {
                    return ((CurrencyDisplayNames)object).getNarrowSymbol(this.subType);
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("bad name style: ");
                ((StringBuilder)object).append(n);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            return ((CurrencyDisplayNames)object).getName(this.subType);
        }
        return ((CurrencyDisplayNames)object).getSymbol(this.subType);
    }

    public String getName(Locale locale, int n, String string, boolean[] arrbl) {
        return this.getName(ULocale.forLocale(locale), n, string, arrbl);
    }

    public String getName(Locale locale, int n, boolean[] arrbl) {
        return this.getName(ULocale.forLocale(locale), n, arrbl);
    }

    public int getNumericCode() {
        int n = 0;
        try {
            int n2;
            n = n2 = UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", "currencyNumericCodes", ICUResourceBundle.ICU_DATA_CLASS_LOADER).get("codeMap").get(this.subType).getInt();
        }
        catch (MissingResourceException missingResourceException) {
            // empty catch block
        }
        return n;
    }

    public double getRoundingIncrement() {
        return this.getRoundingIncrement(CurrencyUsage.STANDARD);
    }

    public double getRoundingIncrement(CurrencyUsage arrn) {
        arrn = CurrencyMetaInfo.getInstance().currencyDigits(this.subType, (CurrencyUsage)arrn);
        int n = arrn.roundingIncrement;
        if (n == 0) {
            return 0.0;
        }
        int n2 = arrn.fractionDigits;
        if (n2 >= 0 && n2 < (arrn = POW10).length) {
            return (double)n / (double)arrn[n2];
        }
        return 0.0;
    }

    public String getSymbol() {
        return this.getSymbol(ULocale.getDefault(ULocale.Category.DISPLAY));
    }

    public String getSymbol(ULocale uLocale) {
        return this.getName(uLocale, 0, null);
    }

    public String getSymbol(Locale locale) {
        return this.getSymbol(ULocale.forLocale(locale));
    }

    public java.util.Currency toJavaCurrency() {
        return java.util.Currency.getInstance(this.getCurrencyCode());
    }

    @Override
    public String toString() {
        return this.subType;
    }

    private static class CurrencyNameResultHandler
    implements TextTrieMap.ResultHandler<CurrencyStringInfo> {
        private String bestCurrencyISOCode;
        private int bestMatchLength;

        private CurrencyNameResultHandler() {
        }

        public String getBestCurrencyISOCode() {
            return this.bestCurrencyISOCode;
        }

        public int getBestMatchLength() {
            return this.bestMatchLength;
        }

        @Override
        public boolean handlePrefixMatch(int n, Iterator<CurrencyStringInfo> iterator) {
            if (iterator.hasNext()) {
                this.bestCurrencyISOCode = iterator.next().getISOCode();
                this.bestMatchLength = n;
            }
            return true;
        }
    }

    @Deprecated
    public static final class CurrencyStringInfo {
        private String currencyString;
        private String isoCode;

        @Deprecated
        public CurrencyStringInfo(String string, String string2) {
            this.isoCode = string;
            this.currencyString = string2;
        }

        @Deprecated
        public String getCurrencyString() {
            return this.currencyString;
        }

        @Deprecated
        public String getISOCode() {
            return this.isoCode;
        }
    }

    public static enum CurrencyUsage {
        STANDARD,
        CASH;
        
    }

    static abstract class ServiceShim {
        ServiceShim() {
        }

        abstract Currency createInstance(ULocale var1);

        abstract Locale[] getAvailableLocales();

        abstract ULocale[] getAvailableULocales();

        abstract Object registerInstance(Currency var1, ULocale var2);

        abstract boolean unregister(Object var1);
    }

}

