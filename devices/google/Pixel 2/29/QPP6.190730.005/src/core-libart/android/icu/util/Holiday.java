/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.util.DateRule;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import java.util.Date;
import java.util.Locale;
import java.util.MissingResourceException;

public abstract class Holiday
implements DateRule {
    private static Holiday[] noHolidays = new Holiday[0];
    private String name;
    private DateRule rule;

    protected Holiday(String string, DateRule dateRule) {
        this.name = string;
        this.rule = dateRule;
    }

    public static Holiday[] getHolidays() {
        return Holiday.getHolidays(ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public static Holiday[] getHolidays(ULocale arrholiday) {
        Holiday[] arrholiday2 = noHolidays;
        try {
            arrholiday = (Holiday[])UResourceBundle.getBundleInstance("android.icu.impl.data.HolidayBundle", (ULocale)arrholiday).getObject("holidays");
        }
        catch (MissingResourceException missingResourceException) {
            arrholiday = arrholiday2;
        }
        return arrholiday;
    }

    public static Holiday[] getHolidays(Locale locale) {
        return Holiday.getHolidays(ULocale.forLocale(locale));
    }

    @Override
    public Date firstAfter(Date date) {
        return this.rule.firstAfter(date);
    }

    @Override
    public Date firstBetween(Date date, Date date2) {
        return this.rule.firstBetween(date, date2);
    }

    public String getDisplayName() {
        return this.getDisplayName(ULocale.getDefault(ULocale.Category.DISPLAY));
    }

    public String getDisplayName(ULocale object) {
        String string = this.name;
        try {
            object = UResourceBundle.getBundleInstance("android.icu.impl.data.HolidayBundle", (ULocale)object).getString(this.name);
        }
        catch (MissingResourceException missingResourceException) {
            object = string;
        }
        return object;
    }

    public String getDisplayName(Locale locale) {
        return this.getDisplayName(ULocale.forLocale(locale));
    }

    public DateRule getRule() {
        return this.rule;
    }

    @Override
    public boolean isBetween(Date date, Date date2) {
        return this.rule.isBetween(date, date2);
    }

    @Override
    public boolean isOn(Date date) {
        return this.rule.isOn(date);
    }

    public void setRule(DateRule dateRule) {
        this.rule = dateRule;
    }
}

