/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.data;

import android.icu.util.Holiday;
import android.icu.util.SimpleHoliday;
import java.util.ListResourceBundle;

public class HolidayBundle_ja_JP
extends ListResourceBundle {
    private static final Object[][] fContents;
    private static final Holiday[] fHolidays;

    static {
        fHolidays = new Holiday[]{new SimpleHoliday(1, 11, 0, "National Foundation Day")};
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

