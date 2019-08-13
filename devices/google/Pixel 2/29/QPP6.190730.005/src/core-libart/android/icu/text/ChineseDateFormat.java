/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.ChineseDateFormatSymbols;
import android.icu.text.DateFormat;
import android.icu.text.DateFormatSymbols;
import android.icu.text.DisplayContext;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.ChineseCalendar;
import android.icu.util.TimeZone;
import android.icu.util.ULocale;
import java.io.InvalidObjectException;
import java.text.FieldPosition;
import java.util.Locale;

@Deprecated
public class ChineseDateFormat
extends SimpleDateFormat {
    static final long serialVersionUID = -4610300753104099899L;

    @Deprecated
    public ChineseDateFormat(String string, ULocale uLocale) {
        this(string, null, uLocale);
    }

    @Deprecated
    public ChineseDateFormat(String string, String string2, ULocale uLocale) {
        super(string, new ChineseDateFormatSymbols(uLocale), new ChineseCalendar(TimeZone.getDefault(), uLocale), uLocale, true, string2);
    }

    @Deprecated
    public ChineseDateFormat(String string, Locale locale) {
        this(string, ULocale.forLocale(locale));
    }

    @Deprecated
    @Override
    protected DateFormat.Field patternCharToDateFormatField(char c) {
        return super.patternCharToDateFormatField(c);
    }

    @Deprecated
    @Override
    protected void subFormat(StringBuffer stringBuffer, char c, int n, int n2, int n3, DisplayContext displayContext, FieldPosition fieldPosition, Calendar calendar) {
        super.subFormat(stringBuffer, c, n, n2, n3, displayContext, fieldPosition, calendar);
    }

    @Deprecated
    @Override
    protected int subParse(String string, int n, char c, int n2, boolean bl, boolean bl2, boolean[] arrbl, Calendar calendar) {
        return super.subParse(string, n, c, n2, bl, bl2, arrbl, calendar);
    }

    @Deprecated
    public static class Field
    extends DateFormat.Field {
        @Deprecated
        public static final Field IS_LEAP_MONTH = new Field("is leap month", 22);
        private static final long serialVersionUID = -5102130532751400330L;

        @Deprecated
        protected Field(String string, int n) {
            super(string, n);
        }

        @Deprecated
        public static DateFormat.Field ofCalendarField(int n) {
            if (n == 22) {
                return IS_LEAP_MONTH;
            }
            return DateFormat.Field.ofCalendarField(n);
        }

        @Deprecated
        @Override
        protected Object readResolve() throws InvalidObjectException {
            if (this.getClass() == Field.class) {
                if (this.getName().equals(IS_LEAP_MONTH.getName())) {
                    return IS_LEAP_MONTH;
                }
                throw new InvalidObjectException("Unknown attribute name.");
            }
            throw new InvalidObjectException("A subclass of ChineseDateFormat.Field must implement readResolve.");
        }
    }

}

