/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.ICUResourceBundle;
import android.icu.text.DateFormatSymbols;
import android.icu.util.Calendar;
import android.icu.util.ChineseCalendar;
import android.icu.util.ULocale;
import java.util.Locale;

@Deprecated
public class ChineseDateFormatSymbols
extends DateFormatSymbols {
    static final long serialVersionUID = 6827816119783952890L;
    String[] isLeapMonth;

    @Deprecated
    public ChineseDateFormatSymbols() {
        this(ULocale.getDefault(ULocale.Category.FORMAT));
    }

    @Deprecated
    public ChineseDateFormatSymbols(Calendar calendar, ULocale uLocale) {
        super(calendar.getClass(), uLocale);
    }

    @Deprecated
    public ChineseDateFormatSymbols(Calendar calendar, Locale locale) {
        super(calendar.getClass(), locale);
    }

    @Deprecated
    public ChineseDateFormatSymbols(ULocale uLocale) {
        super(ChineseCalendar.class, uLocale);
    }

    @Deprecated
    public ChineseDateFormatSymbols(Locale locale) {
        super(ChineseCalendar.class, ULocale.forLocale(locale));
    }

    private void initializeIsLeapMonth() {
        String[] arrstring = this.isLeapMonth = new String[2];
        String string = "";
        arrstring[0] = "";
        if (this.leapMonthPatterns != null) {
            string = this.leapMonthPatterns[0].replace("{0}", "");
        }
        arrstring[1] = string;
    }

    @Deprecated
    public String getLeapMonth(int n) {
        return this.isLeapMonth[n];
    }

    @Override
    void initializeData(DateFormatSymbols dateFormatSymbols) {
        super.initializeData(dateFormatSymbols);
        if (dateFormatSymbols instanceof ChineseDateFormatSymbols) {
            this.isLeapMonth = ((ChineseDateFormatSymbols)dateFormatSymbols).isLeapMonth;
        } else {
            this.initializeIsLeapMonth();
        }
    }

    @Deprecated
    @Override
    protected void initializeData(ULocale uLocale, ICUResourceBundle iCUResourceBundle, String string) {
        super.initializeData(uLocale, iCUResourceBundle, string);
        this.initializeIsLeapMonth();
    }
}

