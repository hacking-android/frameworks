/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.impl.ICULocaleService;
import android.icu.impl.ICUResourceBundle;
import android.icu.impl.ICUService;
import android.icu.util.Currency;
import android.icu.util.ULocale;
import java.util.Locale;

final class CurrencyServiceShim
extends Currency.ServiceShim {
    static final ICULocaleService service = new CFService();

    CurrencyServiceShim() {
    }

    @Override
    Currency createInstance(ULocale uLocale) {
        if (service.isDefault()) {
            return Currency.createCurrency(uLocale);
        }
        return (Currency)service.get(uLocale);
    }

    @Override
    Locale[] getAvailableLocales() {
        if (service.isDefault()) {
            return ICUResourceBundle.getAvailableLocales();
        }
        return service.getAvailableLocales();
    }

    @Override
    ULocale[] getAvailableULocales() {
        if (service.isDefault()) {
            return ICUResourceBundle.getAvailableULocales();
        }
        return service.getAvailableULocales();
    }

    @Override
    Object registerInstance(Currency currency, ULocale uLocale) {
        return service.registerObject(currency, uLocale);
    }

    @Override
    boolean unregister(Object object) {
        return service.unregisterFactory((ICUService.Factory)object);
    }

    private static class CFService
    extends ICULocaleService {
        CFService() {
            super("Currency");
            this.registerFactory(new 1CurrencyFactory());
            this.markDefault();
        }

        class 1CurrencyFactory
        extends ICULocaleService.ICUResourceBundleFactory {
            1CurrencyFactory() {
            }

            @Override
            protected Object handleCreate(ULocale uLocale, int n, ICUService iCUService) {
                return Currency.createCurrency(uLocale);
            }
        }

    }

}

