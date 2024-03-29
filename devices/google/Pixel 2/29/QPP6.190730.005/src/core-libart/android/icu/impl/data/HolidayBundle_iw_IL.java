/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.data;

import android.icu.util.HebrewHoliday;
import android.icu.util.Holiday;
import java.util.ListResourceBundle;

public class HolidayBundle_iw_IL
extends ListResourceBundle {
    private static final Object[][] fContents;
    private static final Holiday[] fHolidays;

    static {
        fHolidays = new Holiday[]{HebrewHoliday.ROSH_HASHANAH, HebrewHoliday.YOM_KIPPUR, HebrewHoliday.HANUKKAH, HebrewHoliday.PURIM, HebrewHoliday.PASSOVER, HebrewHoliday.SHAVUOT, HebrewHoliday.SELIHOT};
        fContents = new Object[][]{{"holidays", fHolidays}};
    }

    @Override
    public Object[][] getContents() {
        synchronized (this) {
            Object[][] arrobject = fContents;
            return arrobject;
        }
    }
}

