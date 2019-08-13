/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.impl.ICUResourceBundle;
import android.icu.text.UnicodeSet;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import android.icu.util.VersionInfo;
import java.util.MissingResourceException;

public final class LocaleData {
    public static final int ALT_QUOTATION_END = 3;
    public static final int ALT_QUOTATION_START = 2;
    @Deprecated
    public static final int DELIMITER_COUNT = 4;
    private static final String[] DELIMITER_TYPES = new String[]{"quotationStart", "quotationEnd", "alternateQuotationStart", "alternateQuotationEnd"};
    public static final int ES_AUXILIARY = 1;
    @Deprecated
    public static final int ES_COUNT = 5;
    @Deprecated
    public static final int ES_CURRENCY = 3;
    public static final int ES_INDEX = 2;
    public static final int ES_PUNCTUATION = 4;
    public static final int ES_STANDARD = 0;
    private static final String LOCALE_DISPLAY_PATTERN = "localeDisplayPattern";
    private static final String MEASUREMENT_SYSTEM = "MeasurementSystem";
    private static final String PAPER_SIZE = "PaperSize";
    private static final String PATTERN = "pattern";
    public static final int QUOTATION_END = 1;
    public static final int QUOTATION_START = 0;
    private static final String SEPARATOR = "separator";
    private static VersionInfo gCLDRVersion = null;
    private ICUResourceBundle bundle;
    private ICUResourceBundle langBundle;
    private boolean noSubstitute;

    private LocaleData() {
    }

    public static VersionInfo getCLDRVersion() {
        if (gCLDRVersion == null) {
            gCLDRVersion = VersionInfo.getInstance(UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER).get("cldrVersion").getString());
        }
        return gCLDRVersion;
    }

    public static UnicodeSet getExemplarSet(ULocale uLocale, int n) {
        return LocaleData.getInstance(uLocale).getExemplarSet(n, 0);
    }

    public static UnicodeSet getExemplarSet(ULocale uLocale, int n, int n2) {
        return LocaleData.getInstance(uLocale).getExemplarSet(n, n2);
    }

    public static final LocaleData getInstance() {
        return LocaleData.getInstance(ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public static final LocaleData getInstance(ULocale uLocale) {
        LocaleData localeData = new LocaleData();
        localeData.bundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", uLocale);
        localeData.langBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b/lang", uLocale);
        localeData.noSubstitute = false;
        return localeData;
    }

    public static final MeasurementSystem getMeasurementSystem(ULocale uLocale) {
        int n = LocaleData.measurementTypeBundleForLocale(uLocale, MEASUREMENT_SYSTEM).getInt();
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return MeasurementSystem.UK;
            }
            return MeasurementSystem.US;
        }
        return MeasurementSystem.SI;
    }

    public static final PaperSize getPaperSize(ULocale arrn) {
        arrn = LocaleData.measurementTypeBundleForLocale((ULocale)arrn, PAPER_SIZE).getIntVector();
        return new PaperSize(arrn[0], arrn[1]);
    }

    private static UResourceBundle measurementTypeBundleForLocale(ULocale object, String string) {
        Object var2_4 = null;
        object = ULocale.getRegionForSupplementalData((ULocale)object, true);
        UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER).get("measurementData");
        try {
            object = uResourceBundle.get((String)object).get(string);
        }
        catch (MissingResourceException missingResourceException) {
            try {
                object = uResourceBundle.get("001").get(string);
            }
            catch (MissingResourceException missingResourceException2) {
                object = var2_4;
            }
        }
        return object;
    }

    public String getDelimiter(int n) {
        ICUResourceBundle iCUResourceBundle = ((ICUResourceBundle)this.bundle.get("delimiters")).getWithFallback(DELIMITER_TYPES[n]);
        if (this.noSubstitute && !this.bundle.isRoot() && iCUResourceBundle.isRoot()) {
            return null;
        }
        return iCUResourceBundle.getString();
    }

    public UnicodeSet getExemplarSet(int n, int n2) {
        Object object;
        Object object2;
        block6 : {
            object2 = null;
            object = null;
            if (n2 == 3) {
                object2 = this.noSubstitute ? object : UnicodeSet.EMPTY;
                return object2;
            }
            object = new String[]{"ExemplarCharacters", "AuxExemplarCharacters", "ExemplarCharactersIndex", "ExemplarCharactersCurrency", "ExemplarCharactersPunctuation"}[n2];
            object = (ICUResourceBundle)this.bundle.get((String)object);
            if (!this.noSubstitute || this.bundle.isRoot() || !((ICUResourceBundle)object).isRoot()) break block6;
            return null;
        }
        try {
            object = new UnicodeSet(((UResourceBundle)object).getString(), n | 1);
            return object;
        }
        catch (Exception exception) {
            if (!this.noSubstitute) {
                object2 = UnicodeSet.EMPTY;
            }
            return object2;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            throw new IllegalArgumentException(arrayIndexOutOfBoundsException);
        }
    }

    public String getLocaleDisplayPattern() {
        return ((ICUResourceBundle)this.langBundle.get(LOCALE_DISPLAY_PATTERN)).getStringWithFallback(PATTERN);
    }

    public String getLocaleSeparator() {
        String string = ((ICUResourceBundle)this.langBundle.get(LOCALE_DISPLAY_PATTERN)).getStringWithFallback(SEPARATOR);
        int n = string.indexOf("{0}");
        int n2 = string.indexOf("{1}");
        if (n >= 0 && n2 >= 0 && n <= n2) {
            return string.substring("{0}".length() + n, n2);
        }
        return string;
    }

    public boolean getNoSubstitute() {
        return this.noSubstitute;
    }

    public void setNoSubstitute(boolean bl) {
        this.noSubstitute = bl;
    }

    public static final class MeasurementSystem {
        public static final MeasurementSystem SI = new MeasurementSystem();
        public static final MeasurementSystem UK;
        public static final MeasurementSystem US;

        static {
            US = new MeasurementSystem();
            UK = new MeasurementSystem();
        }

        private MeasurementSystem() {
        }
    }

    public static final class PaperSize {
        private int height;
        private int width;

        private PaperSize(int n, int n2) {
            this.height = n;
            this.width = n2;
        }

        public int getHeight() {
            return this.height;
        }

        public int getWidth() {
            return this.width;
        }
    }

}

