/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.Assert;
import android.icu.impl.ICUBinary;
import android.icu.impl.ICULocaleService;
import android.icu.impl.ICUResourceBundle;
import android.icu.impl.ICUService;
import android.icu.text.BreakIterator;
import android.icu.text.FilteredBreakIteratorBuilder;
import android.icu.text.RuleBasedBreakIterator;
import android.icu.util.ULocale;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Locale;
import java.util.MissingResourceException;

final class BreakIteratorFactory
extends BreakIterator.BreakIteratorServiceShim {
    private static final String[] KIND_NAMES;
    static final ICULocaleService service;

    static {
        service = new BFService();
        KIND_NAMES = new String[]{"grapheme", "word", "line", "sentence", "title"};
    }

    BreakIteratorFactory() {
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static BreakIterator createBreakInstance(ULocale var0, int var1_2) {
        block8 : {
            block6 : {
                block7 : {
                    var2_3 = null;
                    var3_4 = ICUResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b/brkitr", var0, ICUResourceBundle.OpenType.LOCALE_ROOT);
                    var4_5 = null;
                    var5_10 = var4_5;
                    if (var1_2 != 2) break block6;
                    var6_12 = var0.getKeywordValue("lb");
                    var5_10 = var4_5;
                    if (var6_12 == null) break block6;
                    if (var6_12.equals("strict") || var6_12.equals("normal")) break block7;
                    var5_10 = var4_5;
                    if (!var6_12.equals("loose")) break block6;
                }
                var5_10 = new StringBuilder();
                var5_10.append("_");
                var5_10.append(var6_12);
                var5_10 = var5_10.toString();
            }
            if (var5_10 != null) ** GOTO lbl24
            var5_10 = BreakIteratorFactory.KIND_NAMES[var1_2];
            break block8;
lbl24: // 1 sources:
            var4_6 = new StringBuilder();
            var4_6.append(BreakIteratorFactory.KIND_NAMES[var1_2]);
            var4_6.append((String)var5_10);
            var5_10 = var4_6.toString();
        }
        var4_8 = new StringBuilder();
        var4_8.append("boundaries/");
        var4_8.append((String)var5_10);
        var4_9 = var3_4.getStringWithFallback(var4_8.toString());
        var5_10 = new StringBuilder();
        var5_10.append("brkitr/");
        var5_10.append(var4_9);
        var5_10 = ICUBinary.getData(var5_10.toString());
        try {
            var5_10 = RuleBasedBreakIterator.getInstanceFromCompiledRules((ByteBuffer)var5_10);
        }
        catch (IOException var5_11) {
            Assert.fail(var5_11);
            var5_10 = var2_3;
        }
        var2_3 = ULocale.forLocale(var3_4.getLocale());
        var5_10.setLocale((ULocale)var2_3, (ULocale)var2_3);
        if (var1_2 != 3) return var5_10;
        var2_3 = var0.getKeywordValue("ss");
        if (var2_3 == null) return var5_10;
        if (var2_3.equals("standard") == false) return var5_10;
        return FilteredBreakIteratorBuilder.getInstance(new ULocale(var0.getBaseName())).wrapIteratorWithFilter((BreakIterator)var5_10);
        catch (Exception var0_1) {
            throw new MissingResourceException(var0_1.toString(), "", "");
        }
    }

    @Override
    public BreakIterator createBreakIterator(ULocale object, int n) {
        if (service.isDefault()) {
            return BreakIteratorFactory.createBreakInstance((ULocale)object, n);
        }
        ULocale[] arruLocale = new ULocale[1];
        object = (BreakIterator)service.get((ULocale)object, n, arruLocale);
        ((BreakIterator)object).setLocale(arruLocale[0], arruLocale[0]);
        return object;
    }

    @Override
    public Locale[] getAvailableLocales() {
        ICULocaleService iCULocaleService = service;
        if (iCULocaleService == null) {
            return ICUResourceBundle.getAvailableLocales();
        }
        return iCULocaleService.getAvailableLocales();
    }

    @Override
    public ULocale[] getAvailableULocales() {
        ICULocaleService iCULocaleService = service;
        if (iCULocaleService == null) {
            return ICUResourceBundle.getAvailableULocales();
        }
        return iCULocaleService.getAvailableULocales();
    }

    @Override
    public Object registerInstance(BreakIterator breakIterator, ULocale uLocale, int n) {
        breakIterator.setText(new StringCharacterIterator(""));
        return service.registerObject((Object)breakIterator, uLocale, n);
    }

    @Override
    public boolean unregister(Object object) {
        if (service.isDefault()) {
            return false;
        }
        return service.unregisterFactory((ICUService.Factory)object);
    }

    private static class BFService
    extends ICULocaleService {
        BFService() {
            super("BreakIterator");
            this.registerFactory(new 1RBBreakIteratorFactory());
            this.markDefault();
        }

        @Override
        public String validateFallbackLocale() {
            return "";
        }

        class 1RBBreakIteratorFactory
        extends ICULocaleService.ICUResourceBundleFactory {
            1RBBreakIteratorFactory() {
            }

            @Override
            protected Object handleCreate(ULocale uLocale, int n, ICUService iCUService) {
                return BreakIteratorFactory.createBreakInstance(uLocale, n);
            }
        }

    }

}

