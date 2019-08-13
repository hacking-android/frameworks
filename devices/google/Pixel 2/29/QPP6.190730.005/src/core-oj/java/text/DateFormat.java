/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.icu.ICU
 */
package java.text;

import java.io.InvalidObjectException;
import java.io.Serializable;
import java.text.DontCareFieldPosition;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.TimeZone;
import libcore.icu.ICU;

public abstract class DateFormat
extends Format {
    public static final int AM_PM_FIELD = 14;
    public static final int DATE_FIELD = 3;
    public static final int DAY_OF_WEEK_FIELD = 9;
    public static final int DAY_OF_WEEK_IN_MONTH_FIELD = 11;
    public static final int DAY_OF_YEAR_FIELD = 10;
    public static final int DEFAULT = 2;
    public static final int ERA_FIELD = 0;
    public static final int FULL = 0;
    public static final int HOUR0_FIELD = 16;
    public static final int HOUR1_FIELD = 15;
    public static final int HOUR_OF_DAY0_FIELD = 5;
    public static final int HOUR_OF_DAY1_FIELD = 4;
    public static final int LONG = 1;
    public static final int MEDIUM = 2;
    public static final int MILLISECOND_FIELD = 8;
    public static final int MINUTE_FIELD = 6;
    public static final int MONTH_FIELD = 2;
    public static final int SECOND_FIELD = 7;
    public static final int SHORT = 3;
    public static final int TIMEZONE_FIELD = 17;
    public static final int WEEK_OF_MONTH_FIELD = 13;
    public static final int WEEK_OF_YEAR_FIELD = 12;
    public static final int YEAR_FIELD = 1;
    public static Boolean is24Hour;
    private static final long serialVersionUID = 7218322306649953788L;
    protected Calendar calendar;
    protected NumberFormat numberFormat;

    protected DateFormat() {
    }

    private static DateFormat get(int n, int n2, int n3, Locale serializable) {
        if ((n3 & 1) != 0) {
            if (n < 0 || n > 3) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Illegal time style ");
                ((StringBuilder)serializable).append(n);
                throw new IllegalArgumentException(((StringBuilder)serializable).toString());
            }
        } else {
            n = -1;
        }
        if ((n3 & 2) != 0) {
            if (n2 < 0 || n2 > 3) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Illegal date style ");
                ((StringBuilder)serializable).append(n2);
                throw new IllegalArgumentException(((StringBuilder)serializable).toString());
            }
        } else {
            n2 = -1;
        }
        try {
            serializable = new SimpleDateFormat(n, n2, (Locale)serializable);
            return serializable;
        }
        catch (MissingResourceException missingResourceException) {
            return new SimpleDateFormat("M/d/yy h:mm a");
        }
    }

    public static Locale[] getAvailableLocales() {
        return ICU.getAvailableLocales();
    }

    public static final DateFormat getDateInstance() {
        return DateFormat.get(0, 2, 2, Locale.getDefault(Locale.Category.FORMAT));
    }

    public static final DateFormat getDateInstance(int n) {
        return DateFormat.get(0, n, 2, Locale.getDefault(Locale.Category.FORMAT));
    }

    public static final DateFormat getDateInstance(int n, Locale locale) {
        return DateFormat.get(0, n, 2, locale);
    }

    public static final DateFormat getDateTimeInstance() {
        return DateFormat.get(2, 2, 3, Locale.getDefault(Locale.Category.FORMAT));
    }

    public static final DateFormat getDateTimeInstance(int n, int n2) {
        return DateFormat.get(n2, n, 3, Locale.getDefault(Locale.Category.FORMAT));
    }

    public static final DateFormat getDateTimeInstance(int n, int n2, Locale locale) {
        return DateFormat.get(n2, n, 3, locale);
    }

    public static final DateFormat getInstance() {
        return DateFormat.getDateTimeInstance(3, 3);
    }

    public static final DateFormat getTimeInstance() {
        return DateFormat.get(2, 0, 1, Locale.getDefault(Locale.Category.FORMAT));
    }

    public static final DateFormat getTimeInstance(int n) {
        return DateFormat.get(n, 0, 1, Locale.getDefault(Locale.Category.FORMAT));
    }

    public static final DateFormat getTimeInstance(int n, Locale locale) {
        return DateFormat.get(n, 0, 1, locale);
    }

    public static final void set24HourTimePref(Boolean bl) {
        is24Hour = bl;
    }

    @Override
    public Object clone() {
        DateFormat dateFormat = (DateFormat)super.clone();
        dateFormat.calendar = (Calendar)this.calendar.clone();
        dateFormat.numberFormat = (NumberFormat)this.numberFormat.clone();
        return dateFormat;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (DateFormat)object;
            if (this.calendar.getFirstDayOfWeek() != ((DateFormat)object).calendar.getFirstDayOfWeek() || this.calendar.getMinimalDaysInFirstWeek() != ((DateFormat)object).calendar.getMinimalDaysInFirstWeek() || this.calendar.isLenient() != ((DateFormat)object).calendar.isLenient() || !this.calendar.getTimeZone().equals(((DateFormat)object).calendar.getTimeZone()) || !this.numberFormat.equals(((DateFormat)object).numberFormat)) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public final String format(Date date) {
        return this.format(date, new StringBuffer(), DontCareFieldPosition.INSTANCE).toString();
    }

    @Override
    public final StringBuffer format(Object object, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        if (object instanceof Date) {
            return this.format((Date)object, stringBuffer, fieldPosition);
        }
        if (object instanceof Number) {
            return this.format(new Date(((Number)object).longValue()), stringBuffer, fieldPosition);
        }
        throw new IllegalArgumentException("Cannot format given Object as a Date");
    }

    public abstract StringBuffer format(Date var1, StringBuffer var2, FieldPosition var3);

    public Calendar getCalendar() {
        return this.calendar;
    }

    public NumberFormat getNumberFormat() {
        return this.numberFormat;
    }

    public TimeZone getTimeZone() {
        return this.calendar.getTimeZone();
    }

    public int hashCode() {
        return this.numberFormat.hashCode();
    }

    public boolean isLenient() {
        return this.calendar.isLenient();
    }

    public Date parse(String string) throws ParseException {
        ParsePosition parsePosition = new ParsePosition(0);
        Serializable serializable = this.parse(string, parsePosition);
        if (parsePosition.index != 0) {
            return serializable;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Unparseable date: \"");
        ((StringBuilder)serializable).append(string);
        ((StringBuilder)serializable).append("\"");
        throw new ParseException(((StringBuilder)serializable).toString(), parsePosition.errorIndex);
    }

    public abstract Date parse(String var1, ParsePosition var2);

    @Override
    public Object parseObject(String string, ParsePosition parsePosition) {
        return this.parse(string, parsePosition);
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public void setLenient(boolean bl) {
        this.calendar.setLenient(bl);
    }

    public void setNumberFormat(NumberFormat numberFormat) {
        this.numberFormat = numberFormat;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.calendar.setTimeZone(timeZone);
    }

    public static class Field
    extends Format.Field {
        public static final Field AM_PM;
        public static final Field DAY_OF_MONTH;
        public static final Field DAY_OF_WEEK;
        public static final Field DAY_OF_WEEK_IN_MONTH;
        public static final Field DAY_OF_YEAR;
        public static final Field ERA;
        public static final Field HOUR0;
        public static final Field HOUR1;
        public static final Field HOUR_OF_DAY0;
        public static final Field HOUR_OF_DAY1;
        public static final Field MILLISECOND;
        public static final Field MINUTE;
        public static final Field MONTH;
        public static final Field SECOND;
        public static final Field TIME_ZONE;
        public static final Field WEEK_OF_MONTH;
        public static final Field WEEK_OF_YEAR;
        public static final Field YEAR;
        private static final Field[] calendarToFieldMapping;
        private static final Map<String, Field> instanceMap;
        private static final long serialVersionUID = 7441350119349544720L;
        private int calendarField;

        static {
            instanceMap = new HashMap<String, Field>(18);
            calendarToFieldMapping = new Field[17];
            ERA = new Field("era", 0);
            YEAR = new Field("year", 1);
            MONTH = new Field("month", 2);
            DAY_OF_MONTH = new Field("day of month", 5);
            HOUR_OF_DAY1 = new Field("hour of day 1", -1);
            HOUR_OF_DAY0 = new Field("hour of day", 11);
            MINUTE = new Field("minute", 12);
            SECOND = new Field("second", 13);
            MILLISECOND = new Field("millisecond", 14);
            DAY_OF_WEEK = new Field("day of week", 7);
            DAY_OF_YEAR = new Field("day of year", 6);
            DAY_OF_WEEK_IN_MONTH = new Field("day of week in month", 8);
            WEEK_OF_YEAR = new Field("week of year", 3);
            WEEK_OF_MONTH = new Field("week of month", 4);
            AM_PM = new Field("am pm", 9);
            HOUR1 = new Field("hour 1", -1);
            HOUR0 = new Field("hour", 10);
            TIME_ZONE = new Field("time zone", -1);
        }

        protected Field(String string, int n) {
            super(string);
            this.calendarField = n;
            if (this.getClass() == Field.class) {
                instanceMap.put(string, this);
                if (n >= 0) {
                    Field.calendarToFieldMapping[n] = this;
                }
            }
        }

        public static Field ofCalendarField(int n) {
            Object object;
            if (n >= 0 && n < ((Field[])(object = calendarToFieldMapping)).length) {
                return object[n];
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown Calendar constant ");
            ((StringBuilder)object).append(n);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        public int getCalendarField() {
            return this.calendarField;
        }

        @Override
        protected Object readResolve() throws InvalidObjectException {
            if (this.getClass() == Field.class) {
                Field field = instanceMap.get(this.getName());
                if (field != null) {
                    return field;
                }
                throw new InvalidObjectException("unknown attribute name");
            }
            throw new InvalidObjectException("subclass didn't correctly implement readResolve");
        }
    }

}

