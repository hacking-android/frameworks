/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.util.Currency
 *  libcore.icu.ICU
 */
package java.util;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import libcore.icu.ICU;

public final class Currency
implements Serializable {
    private static HashSet<Currency> available;
    private static ConcurrentMap<String, Currency> instances;
    private static final long serialVersionUID = -158308464356906721L;
    private final String currencyCode;
    private final transient android.icu.util.Currency icuCurrency;

    static {
        instances = new ConcurrentHashMap<String, Currency>(7);
    }

    private Currency(android.icu.util.Currency currency) {
        this.icuCurrency = currency;
        this.currencyCode = currency.getCurrencyCode();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Set<Currency> getAvailableCurrencies() {
        synchronized (Currency.class) {
            if (available == null) {
                Serializable serializable = new Serializable();
                available = serializable;
                for (android.icu.util.Currency currency : android.icu.util.Currency.getAvailableCurrencies()) {
                    Currency currency2 = Currency.getInstance(currency.getCurrencyCode());
                    serializable = currency2;
                    if (currency2 == null) {
                        serializable = new Serializable(currency);
                        instances.put(((Currency)serializable).currencyCode, (Currency)serializable);
                    }
                    available.add((Currency)serializable);
                }
            }
            return (Set)available.clone();
        }
    }

    public static Currency getInstance(String object) {
        Currency currency = (Currency)instances.get(object);
        if (currency != null) {
            return currency;
        }
        currency = android.icu.util.Currency.getInstance((String)object);
        if (currency == null) {
            return null;
        }
        if ((object = instances.putIfAbsent((String)object, currency = new Currency((android.icu.util.Currency)currency))) == null) {
            object = currency;
        }
        return object;
    }

    public static Currency getInstance(Locale locale) {
        block5 : {
            CharSequence charSequence;
            android.icu.util.Currency currency;
            block6 : {
                String string;
                String string2;
                block7 : {
                    string2 = locale.getCountry();
                    if (string2 == null) break block5;
                    currency = android.icu.util.Currency.getInstance((Locale)locale);
                    string = locale.getVariant();
                    charSequence = string2;
                    if (string.isEmpty()) break block6;
                    if (string.equals("EURO") || string.equals("HK")) break block7;
                    charSequence = string2;
                    if (!string.equals("PREEURO")) break block6;
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string2);
                ((StringBuilder)charSequence).append("_");
                ((StringBuilder)charSequence).append(string);
                charSequence = ((StringBuilder)charSequence).toString();
            }
            if ((charSequence = ICU.getCurrencyCode((String)charSequence)) != null) {
                if (currency != null && !currency.getCurrencyCode().equals("XXX")) {
                    return Currency.getInstance((String)charSequence);
                }
                return null;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Unsupported ISO 3166 country: ");
            ((StringBuilder)charSequence).append(locale);
            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }
        throw new NullPointerException();
    }

    private Object readResolve() {
        return Currency.getInstance(this.currencyCode);
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public int getDefaultFractionDigits() {
        if (this.icuCurrency.getCurrencyCode().equals("XXX")) {
            return -1;
        }
        return this.icuCurrency.getDefaultFractionDigits();
    }

    public String getDisplayName() {
        return this.getDisplayName(Locale.getDefault(Locale.Category.DISPLAY));
    }

    public String getDisplayName(Locale locale) {
        return this.icuCurrency.getDisplayName(Objects.requireNonNull(locale));
    }

    public int getNumericCode() {
        return this.icuCurrency.getNumericCode();
    }

    public String getSymbol() {
        return this.getSymbol(Locale.getDefault(Locale.Category.DISPLAY));
    }

    public String getSymbol(Locale locale) {
        if (locale != null) {
            return this.icuCurrency.getSymbol(locale);
        }
        throw new NullPointerException("locale == null");
    }

    public String toString() {
        return this.icuCurrency.toString();
    }
}

