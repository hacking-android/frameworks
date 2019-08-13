/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.data;

import java.util.ListResourceBundle;

public class HolidayBundle_it
extends ListResourceBundle {
    private static final Object[][] fContents;

    static {
        Object[] arrobject = new Object[]{"All Saints' Day", "Ognissanti"};
        Object[] arrobject2 = new Object[]{"Ascension", "ascensione"};
        Object[] arrobject3 = new Object[]{"Easter Sunday", "pasqua"};
        Object[] arrobject4 = new Object[]{"Epiphany", "Epifania"};
        Object[] arrobject5 = new Object[]{"Halloween", "vigilia di Ognissanti"};
        Object[] arrobject6 = new Object[]{"Palm Sunday", "domenica delle palme"};
        Object[] arrobject7 = new Object[]{"Pentecost", "di Pentecoste"};
        Object[] arrobject8 = new Object[]{"Thanksgiving", "Giorno del Ringraziamento"};
        fContents = new Object[][]{arrobject, {"Armistice Day", "armistizio"}, arrobject2, {"Ash Wednesday", "mercoled\u00ec delle ceneri"}, {"Boxing Day", "Santo Stefano"}, {"Christmas", "natale"}, arrobject3, arrobject4, {"Good Friday", "venerd\u00ec santo"}, arrobject5, {"Maundy Thursday", "gioved\u00ec santo"}, {"New Year's Day", "anno nuovo"}, arrobject6, arrobject7, {"Shrove Tuesday", "martedi grasso"}, {"St. Stephen's Day", "Santo Stefano"}, arrobject8};
    }

    @Override
    public Object[][] getContents() {
        synchronized (this) {
            Object[][] arrobject = fContents;
            return arrobject;
        }
    }
}

