/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.data;

import android.icu.util.EasterHoliday;
import android.icu.util.Holiday;
import android.icu.util.SimpleHoliday;
import java.util.ListResourceBundle;

public class HolidayBundle_de_AT
extends ListResourceBundle {
    private static final Object[][] fContents;
    private static final Holiday[] fHolidays;

    static {
        Holiday[] arrholiday = fHolidays = new Holiday[]{SimpleHoliday.NEW_YEARS_DAY, SimpleHoliday.EPIPHANY, EasterHoliday.GOOD_FRIDAY, EasterHoliday.EASTER_SUNDAY, EasterHoliday.EASTER_MONDAY, EasterHoliday.ASCENSION, EasterHoliday.WHIT_SUNDAY, EasterHoliday.WHIT_MONDAY, EasterHoliday.CORPUS_CHRISTI, SimpleHoliday.ASSUMPTION, SimpleHoliday.ALL_SAINTS_DAY, SimpleHoliday.IMMACULATE_CONCEPTION, SimpleHoliday.CHRISTMAS, SimpleHoliday.ST_STEPHENS_DAY, new SimpleHoliday(4, 1, 0, "National Holiday"), new SimpleHoliday(9, 31, -2, "National Holiday")};
        Object[] arrobject = new Object[]{"Christmas", "Christtag"};
        Object[] arrobject2 = new Object[]{"New Year's Day", "Neujahrstag"};
        fContents = new Object[][]{{"holidays", arrholiday}, arrobject, arrobject2};
    }

    @Override
    public Object[][] getContents() {
        synchronized (this) {
            Object[][] arrobject = fContents;
            return arrobject;
        }
    }
}

