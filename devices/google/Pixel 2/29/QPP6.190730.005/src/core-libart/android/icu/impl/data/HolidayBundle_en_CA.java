/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.data;

import android.icu.util.Holiday;
import android.icu.util.SimpleHoliday;
import java.util.ListResourceBundle;

public class HolidayBundle_en_CA
extends ListResourceBundle {
    private static final Object[][] fContents;
    private static final Holiday[] fHolidays;

    static {
        fHolidays = new Holiday[]{SimpleHoliday.NEW_YEARS_DAY, new SimpleHoliday(4, 19, 0, "Victoria Day"), new SimpleHoliday(6, 1, 0, "Canada Day"), new SimpleHoliday(7, 1, 2, "Civic Holiday"), new SimpleHoliday(8, 1, 2, "Labor Day"), new SimpleHoliday(9, 8, 2, "Thanksgiving"), new SimpleHoliday(10, 11, 0, "Remembrance Day"), SimpleHoliday.CHRISTMAS, SimpleHoliday.BOXING_DAY, SimpleHoliday.NEW_YEARS_EVE};
        fContents = new Object[][]{{"holidays", fHolidays}, {"Labor Day", "Labour Day"}};
    }

    @Override
    public Object[][] getContents() {
        synchronized (this) {
            Object[][] arrobject = fContents;
            return arrobject;
        }
    }
}

