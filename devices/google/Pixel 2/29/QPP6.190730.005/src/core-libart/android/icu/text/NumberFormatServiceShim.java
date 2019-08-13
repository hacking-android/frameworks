/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.ICULocaleService;
import android.icu.impl.ICUResourceBundle;
import android.icu.impl.ICUService;
import android.icu.text.NumberFormat;
import android.icu.util.Currency;
import android.icu.util.ULocale;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;

class NumberFormatServiceShim
extends NumberFormat.NumberFormatShim {
    private static ICULocaleService service = new NFService();

    NumberFormatServiceShim() {
    }

    @Override
    NumberFormat createInstance(ULocale uLocale, int n) {
        ULocale[] arruLocale = new ULocale[1];
        NumberFormat numberFormat = (NumberFormat)service.get(uLocale, n, arruLocale);
        if (numberFormat != null) {
            numberFormat = (NumberFormat)numberFormat.clone();
            if (n == 1 || n == 5 || n == 6) {
                numberFormat.setCurrency(Currency.getInstance(uLocale));
            }
            uLocale = arruLocale[0];
            numberFormat.setLocale(uLocale, uLocale);
            return numberFormat;
        }
        throw new MissingResourceException("Unable to construct NumberFormat", "", "");
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
    Object registerFactory(NumberFormat.NumberFormatFactory numberFormatFactory) {
        return service.registerFactory(new NFFactory(numberFormatFactory));
    }

    @Override
    boolean unregister(Object object) {
        return service.unregisterFactory((ICUService.Factory)object);
    }

    private static final class NFFactory
    extends ICULocaleService.LocaleKeyFactory {
        private NumberFormat.NumberFormatFactory delegate;

        NFFactory(NumberFormat.NumberFormatFactory numberFormatFactory) {
            super(numberFormatFactory.visible());
            this.delegate = numberFormatFactory;
        }

        @Override
        public Object create(ICUService.Key key, ICUService iCUService) {
            if (this.handlesKey(key) && key instanceof ICULocaleService.LocaleKey) {
                Object object = (ICULocaleService.LocaleKey)key;
                NumberFormat numberFormat = this.delegate.createFormat(((ICULocaleService.LocaleKey)object).canonicalLocale(), ((ICULocaleService.LocaleKey)object).kind());
                object = numberFormat;
                if (numberFormat == null) {
                    object = iCUService.getKey(key, null, this);
                }
                return object;
            }
            return null;
        }

        @Override
        protected Set<String> getSupportedIDs() {
            return this.delegate.getSupportedLocaleNames();
        }
    }

    private static class NFService
    extends ICULocaleService {
        NFService() {
            super("NumberFormat");
            this.registerFactory(new 1RBNumberFormatFactory());
            this.markDefault();
        }

        class 1RBNumberFormatFactory
        extends ICULocaleService.ICUResourceBundleFactory {
            1RBNumberFormatFactory() {
            }

            @Override
            protected Object handleCreate(ULocale uLocale, int n, ICUService iCUService) {
                return NumberFormat.createInstance(uLocale, n);
            }
        }

    }

}

