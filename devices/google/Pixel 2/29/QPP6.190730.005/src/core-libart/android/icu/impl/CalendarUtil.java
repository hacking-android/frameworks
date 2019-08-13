/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.ICUResourceBundle;
import android.icu.impl.UResource;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.TreeMap;

public final class CalendarUtil {
    private static final String CALKEY = "calendar";
    private static final String DEFCAL = "gregorian";

    public static String getCalendarType(ULocale object) {
        Object object2 = ((ULocale)object).getKeywordValue(CALKEY);
        if (object2 != null) {
            return ((String)object2).toLowerCase(Locale.ROOT);
        }
        object2 = ULocale.createCanonical(((ULocale)object).toString());
        object = ((ULocale)object2).getKeywordValue(CALKEY);
        if (object != null) {
            return object;
        }
        object = ULocale.getRegionForSupplementalData((ULocale)object2, true);
        return CalendarPreferences.INSTANCE.getCalendarTypeForRegion((String)object);
    }

    private static final class CalendarPreferences
    extends UResource.Sink {
        private static final CalendarPreferences INSTANCE = new CalendarPreferences();
        Map<String, String> prefs = new TreeMap<String, String>();

        CalendarPreferences() {
            try {
                ((ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", "supplementalData")).getAllItemsWithFallback("calendarPreferenceData", this);
            }
            catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
        }

        String getCalendarTypeForRegion(String string) {
            block0 : {
                if ((string = this.prefs.get(string)) != null) break block0;
                string = CalendarUtil.DEFCAL;
            }
            return string;
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                String string;
                if (value.getArray().getValue(0, value) && !(string = value.getString()).equals(CalendarUtil.DEFCAL)) {
                    this.prefs.put(key.toString(), string);
                }
                ++n;
            }
        }
    }

}

