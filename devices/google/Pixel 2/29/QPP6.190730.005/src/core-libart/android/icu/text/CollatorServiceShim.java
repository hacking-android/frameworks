/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.ICULocaleService;
import android.icu.impl.ICUResourceBundle;
import android.icu.impl.ICUService;
import android.icu.impl.coll.CollationLoader;
import android.icu.impl.coll.CollationTailoring;
import android.icu.text.Collator;
import android.icu.text.RuleBasedCollator;
import android.icu.util.ICUCloneNotSupportedException;
import android.icu.util.Output;
import android.icu.util.ULocale;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;

final class CollatorServiceShim
extends Collator.ServiceShim {
    private static ICULocaleService service = new CService();

    CollatorServiceShim() {
    }

    private static final Collator makeInstance(ULocale uLocale) {
        Output<ULocale> output = new Output<ULocale>(ULocale.ROOT);
        return new RuleBasedCollator(CollationLoader.loadTailoring(uLocale, output), (ULocale)output.value);
    }

    @Override
    Locale[] getAvailableLocales() {
        Locale[] arrlocale = service.isDefault() ? ICUResourceBundle.getAvailableLocales("android/icu/impl/data/icudt63b/coll", ICUResourceBundle.ICU_DATA_CLASS_LOADER) : service.getAvailableLocales();
        return arrlocale;
    }

    @Override
    ULocale[] getAvailableULocales() {
        ULocale[] arruLocale = service.isDefault() ? ICUResourceBundle.getAvailableULocales("android/icu/impl/data/icudt63b/coll", ICUResourceBundle.ICU_DATA_CLASS_LOADER) : service.getAvailableULocales();
        return arruLocale;
    }

    @Override
    String getDisplayName(ULocale object, ULocale uLocale) {
        object = ((ULocale)object).getName();
        return service.getDisplayName((String)object, uLocale);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    Collator getInstance(ULocale object) {
        try {
            ULocale[] arruLocale = new ULocale[1];
            object = (Collator)service.get((ULocale)object, arruLocale);
            if (object != null) {
                return (Collator)((Collator)object).clone();
            }
            object = new MissingResourceException("Could not locate Collator data", "", "");
            throw object;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new ICUCloneNotSupportedException(cloneNotSupportedException);
        }
    }

    @Override
    Object registerFactory(Collator.CollatorFactory collatorFactory) {
        return service.registerFactory(new 1CFactory(collatorFactory));
    }

    @Override
    Object registerInstance(Collator collator, ULocale uLocale) {
        collator.setLocale(uLocale, uLocale);
        return service.registerObject(collator, uLocale);
    }

    @Override
    boolean unregister(Object object) {
        return service.unregisterFactory((ICUService.Factory)object);
    }

    class 1CFactory
    extends ICULocaleService.LocaleKeyFactory {
        Collator.CollatorFactory delegate;

        1CFactory(Collator.CollatorFactory collatorFactory) {
            super(collatorFactory.visible());
            this.delegate = collatorFactory;
        }

        @Override
        public String getDisplayName(String object, ULocale uLocale) {
            object = new ULocale((String)object);
            return this.delegate.getDisplayName((ULocale)object, uLocale);
        }

        @Override
        public Set<String> getSupportedIDs() {
            return this.delegate.getSupportedLocaleIDs();
        }

        @Override
        public Object handleCreate(ULocale uLocale, int n, ICUService iCUService) {
            return this.delegate.createCollator(uLocale);
        }
    }

    private static class CService
    extends ICULocaleService {
        CService() {
            super("Collator");
            this.registerFactory(new 1CollatorFactory());
            this.markDefault();
        }

        @Override
        protected Object handleDefault(ICUService.Key object, String[] arrstring) {
            if (arrstring != null) {
                arrstring[0] = "root";
            }
            try {
                object = CollatorServiceShim.makeInstance(ULocale.ROOT);
                return object;
            }
            catch (MissingResourceException missingResourceException) {
                return null;
            }
        }

        @Override
        public String validateFallbackLocale() {
            return "";
        }

        class 1CollatorFactory
        extends ICULocaleService.ICUResourceBundleFactory {
            1CollatorFactory() {
                super("android/icu/impl/data/icudt63b/coll");
            }

            @Override
            protected Object handleCreate(ULocale uLocale, int n, ICUService iCUService) {
                return CollatorServiceShim.makeInstance(uLocale);
            }
        }

    }

}

