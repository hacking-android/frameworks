/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.util.DateRule;
import android.icu.util.GregorianCalendar;
import android.icu.util.Holiday;
import android.icu.util.RangeDateRule;
import android.icu.util.SimpleDateRule;
import java.util.Date;

public class SimpleHoliday
extends Holiday {
    public static final SimpleHoliday ALL_SAINTS_DAY;
    public static final SimpleHoliday ALL_SOULS_DAY;
    public static final SimpleHoliday ASSUMPTION;
    public static final SimpleHoliday BOXING_DAY;
    public static final SimpleHoliday CHRISTMAS;
    public static final SimpleHoliday CHRISTMAS_EVE;
    public static final SimpleHoliday EPIPHANY;
    public static final SimpleHoliday IMMACULATE_CONCEPTION;
    public static final SimpleHoliday MAY_DAY;
    public static final SimpleHoliday NEW_YEARS_DAY;
    public static final SimpleHoliday NEW_YEARS_EVE;
    public static final SimpleHoliday ST_STEPHENS_DAY;

    static {
        NEW_YEARS_DAY = new SimpleHoliday(0, 1, "New Year's Day");
        EPIPHANY = new SimpleHoliday(0, 6, "Epiphany");
        MAY_DAY = new SimpleHoliday(4, 1, "May Day");
        ASSUMPTION = new SimpleHoliday(7, 15, "Assumption");
        ALL_SAINTS_DAY = new SimpleHoliday(10, 1, "All Saints' Day");
        ALL_SOULS_DAY = new SimpleHoliday(10, 2, "All Souls' Day");
        IMMACULATE_CONCEPTION = new SimpleHoliday(11, 8, "Immaculate Conception");
        CHRISTMAS_EVE = new SimpleHoliday(11, 24, "Christmas Eve");
        CHRISTMAS = new SimpleHoliday(11, 25, "Christmas");
        BOXING_DAY = new SimpleHoliday(11, 26, "Boxing Day");
        ST_STEPHENS_DAY = new SimpleHoliday(11, 26, "St. Stephen's Day");
        NEW_YEARS_EVE = new SimpleHoliday(11, 31, "New Year's Eve");
    }

    public SimpleHoliday(int n, int n2, int n3, String string) {
        int n4 = n3 > 0 ? n3 : -n3;
        boolean bl = n3 > 0;
        super(string, new SimpleDateRule(n, n2, n4, bl));
    }

    public SimpleHoliday(int n, int n2, int n3, String string, int n4) {
        int n5 = n3 > 0 ? n3 : -n3;
        boolean bl = n3 > 0;
        super(string, SimpleHoliday.rangeRule(n4, 0, new SimpleDateRule(n, n2, n5, bl)));
    }

    public SimpleHoliday(int n, int n2, int n3, String string, int n4, int n5) {
        int n6 = n3 > 0 ? n3 : -n3;
        boolean bl = n3 > 0;
        super(string, SimpleHoliday.rangeRule(n4, n5, new SimpleDateRule(n, n2, n6, bl)));
    }

    public SimpleHoliday(int n, int n2, String string) {
        super(string, new SimpleDateRule(n, n2));
    }

    public SimpleHoliday(int n, int n2, String string, int n3) {
        super(string, SimpleHoliday.rangeRule(n3, 0, new SimpleDateRule(n, n2)));
    }

    public SimpleHoliday(int n, int n2, String string, int n3, int n4) {
        super(string, SimpleHoliday.rangeRule(n3, n4, new SimpleDateRule(n, n2)));
    }

    private static DateRule rangeRule(int n, int n2, DateRule dateRule) {
        if (n == 0 && n2 == 0) {
            return dateRule;
        }
        RangeDateRule rangeDateRule = new RangeDateRule();
        if (n != 0) {
            rangeDateRule.add(new GregorianCalendar(n, 0, 1).getTime(), dateRule);
        } else {
            rangeDateRule.add(dateRule);
        }
        if (n2 != 0) {
            rangeDateRule.add(new GregorianCalendar(n2, 11, 31).getTime(), null);
        }
        return rangeDateRule;
    }
}

