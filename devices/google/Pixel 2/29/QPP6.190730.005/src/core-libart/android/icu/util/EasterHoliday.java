/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.util.DateRule;
import android.icu.util.EasterRule;
import android.icu.util.Holiday;

public class EasterHoliday
extends Holiday {
    public static final EasterHoliday ASCENSION;
    public static final EasterHoliday ASH_WEDNESDAY;
    public static final EasterHoliday CORPUS_CHRISTI;
    public static final EasterHoliday EASTER_MONDAY;
    public static final EasterHoliday EASTER_SUNDAY;
    public static final EasterHoliday GOOD_FRIDAY;
    public static final EasterHoliday MAUNDY_THURSDAY;
    public static final EasterHoliday PALM_SUNDAY;
    public static final EasterHoliday PENTECOST;
    public static final EasterHoliday SHROVE_TUESDAY;
    public static final EasterHoliday WHIT_MONDAY;
    public static final EasterHoliday WHIT_SUNDAY;

    static {
        SHROVE_TUESDAY = new EasterHoliday(-48, "Shrove Tuesday");
        ASH_WEDNESDAY = new EasterHoliday(-47, "Ash Wednesday");
        PALM_SUNDAY = new EasterHoliday(-7, "Palm Sunday");
        MAUNDY_THURSDAY = new EasterHoliday(-3, "Maundy Thursday");
        GOOD_FRIDAY = new EasterHoliday(-2, "Good Friday");
        EASTER_SUNDAY = new EasterHoliday(0, "Easter Sunday");
        EASTER_MONDAY = new EasterHoliday(1, "Easter Monday");
        ASCENSION = new EasterHoliday(39, "Ascension");
        PENTECOST = new EasterHoliday(49, "Pentecost");
        WHIT_SUNDAY = new EasterHoliday(49, "Whit Sunday");
        WHIT_MONDAY = new EasterHoliday(50, "Whit Monday");
        CORPUS_CHRISTI = new EasterHoliday(60, "Corpus Christi");
    }

    public EasterHoliday(int n, String string) {
        super(string, new EasterRule(n, false));
    }

    public EasterHoliday(int n, boolean bl, String string) {
        super(string, new EasterRule(n, bl));
    }

    public EasterHoliday(String string) {
        super(string, new EasterRule(0, false));
    }
}

