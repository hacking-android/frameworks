/*
 * Decompiled with CFR 0.145.
 */
package sun.util.calendar;

import java.util.Locale;
import java.util.TimeZone;
import sun.util.calendar.CalendarUtils;
import sun.util.calendar.Era;

public abstract class CalendarDate
implements Cloneable {
    public static final int FIELD_UNDEFINED = Integer.MIN_VALUE;
    public static final long TIME_UNDEFINED = Long.MIN_VALUE;
    private int dayOfMonth;
    private int dayOfWeek = Integer.MIN_VALUE;
    private int daylightSaving;
    private Era era;
    private boolean forceStandardTime;
    private long fraction;
    private int hours;
    private boolean leapYear;
    private Locale locale;
    private int millis;
    private int minutes;
    private int month;
    private boolean normalized;
    private int seconds;
    private int year;
    private int zoneOffset;
    private TimeZone zoneinfo;

    protected CalendarDate() {
        this(TimeZone.getDefault());
    }

    protected CalendarDate(TimeZone timeZone) {
        this.zoneinfo = timeZone;
    }

    public CalendarDate addDate(int n, int n2, int n3) {
        this.addYear(n);
        this.addMonth(n2);
        this.addDayOfMonth(n3);
        return this;
    }

    public CalendarDate addDayOfMonth(int n) {
        if (n != 0) {
            this.dayOfMonth += n;
            this.normalized = false;
        }
        return this;
    }

    public CalendarDate addHours(int n) {
        if (n != 0) {
            this.hours += n;
            this.normalized = false;
        }
        return this;
    }

    public CalendarDate addMillis(int n) {
        if (n != 0) {
            this.millis += n;
            this.normalized = false;
        }
        return this;
    }

    public CalendarDate addMinutes(int n) {
        if (n != 0) {
            this.minutes += n;
            this.normalized = false;
        }
        return this;
    }

    public CalendarDate addMonth(int n) {
        if (n != 0) {
            this.month += n;
            this.normalized = false;
        }
        return this;
    }

    public CalendarDate addSeconds(int n) {
        if (n != 0) {
            this.seconds += n;
            this.normalized = false;
        }
        return this;
    }

    public CalendarDate addTimeOfDay(int n, int n2, int n3, int n4) {
        this.addHours(n);
        this.addMinutes(n2);
        this.addSeconds(n3);
        this.addMillis(n4);
        return this;
    }

    public CalendarDate addYear(int n) {
        if (n != 0) {
            this.year += n;
            this.normalized = false;
        }
        return this;
    }

    public Object clone() {
        try {
            Object object = super.clone();
            return object;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError(cloneNotSupportedException);
        }
    }

    public boolean equals(Object object) {
        boolean bl;
        boolean bl2 = object instanceof CalendarDate;
        boolean bl3 = false;
        if (!bl2) {
            return false;
        }
        object = (CalendarDate)object;
        if (this.isNormalized() != ((CalendarDate)object).isNormalized()) {
            return false;
        }
        boolean bl4 = this.zoneinfo != null;
        if (bl4 != (bl = ((CalendarDate)object).zoneinfo != null)) {
            return false;
        }
        if (bl4 && !this.zoneinfo.equals(((CalendarDate)object).zoneinfo)) {
            return false;
        }
        bl2 = bl3;
        if (this.getEra() == ((CalendarDate)object).getEra()) {
            bl2 = bl3;
            if (this.year == ((CalendarDate)object).year) {
                bl2 = bl3;
                if (this.month == ((CalendarDate)object).month) {
                    bl2 = bl3;
                    if (this.dayOfMonth == ((CalendarDate)object).dayOfMonth) {
                        bl2 = bl3;
                        if (this.hours == ((CalendarDate)object).hours) {
                            bl2 = bl3;
                            if (this.minutes == ((CalendarDate)object).minutes) {
                                bl2 = bl3;
                                if (this.seconds == ((CalendarDate)object).seconds) {
                                    bl2 = bl3;
                                    if (this.millis == ((CalendarDate)object).millis) {
                                        bl2 = bl3;
                                        if (this.zoneOffset == ((CalendarDate)object).zoneOffset) {
                                            bl2 = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return bl2;
    }

    public int getDayOfMonth() {
        return this.dayOfMonth;
    }

    public int getDayOfWeek() {
        if (!this.isNormalized()) {
            this.dayOfWeek = Integer.MIN_VALUE;
        }
        return this.dayOfWeek;
    }

    public int getDaylightSaving() {
        return this.daylightSaving;
    }

    public Era getEra() {
        return this.era;
    }

    public int getHours() {
        return this.hours;
    }

    public int getMillis() {
        return this.millis;
    }

    public int getMinutes() {
        return this.minutes;
    }

    public int getMonth() {
        return this.month;
    }

    public int getSeconds() {
        return this.seconds;
    }

    public long getTimeOfDay() {
        if (!this.isNormalized()) {
            this.fraction = Long.MIN_VALUE;
            return Long.MIN_VALUE;
        }
        return this.fraction;
    }

    public int getYear() {
        return this.year;
    }

    public TimeZone getZone() {
        return this.zoneinfo;
    }

    public int getZoneOffset() {
        return this.zoneOffset;
    }

    public int hashCode() {
        long l = this.year;
        long l2 = this.month - 1;
        long l3 = this.dayOfMonth;
        l = ((((long)this.hours + (((l - 1970L) * 12L + l2) * 30L + l3) * 24L) * 60L + (long)this.minutes) * 60L + (long)this.seconds) * 1000L + (long)this.millis - (long)this.zoneOffset;
        int n = this.isNormalized();
        int n2 = 0;
        Object object = this.getEra();
        if (object != null) {
            n2 = ((Era)object).hashCode();
        }
        int n3 = (object = this.zoneinfo) != null ? object.hashCode() : 0;
        return (int)l * (int)(l >> 32) ^ n2 ^ n ^ n3;
    }

    public boolean isDaylightTime() {
        boolean bl = this.isStandardTime();
        boolean bl2 = false;
        if (bl) {
            return false;
        }
        if (this.daylightSaving != 0) {
            bl2 = true;
        }
        return bl2;
    }

    public boolean isLeapYear() {
        return this.leapYear;
    }

    public boolean isNormalized() {
        return this.normalized;
    }

    public boolean isSameDate(CalendarDate calendarDate) {
        boolean bl = this.getDayOfWeek() == calendarDate.getDayOfWeek() && this.getMonth() == calendarDate.getMonth() && this.getYear() == calendarDate.getYear() && this.getEra() == calendarDate.getEra();
        return bl;
    }

    public boolean isStandardTime() {
        return this.forceStandardTime;
    }

    public CalendarDate setDate(int n, int n2, int n3) {
        this.setYear(n);
        this.setMonth(n2);
        this.setDayOfMonth(n3);
        return this;
    }

    public CalendarDate setDayOfMonth(int n) {
        if (this.dayOfMonth != n) {
            this.dayOfMonth = n;
            this.normalized = false;
        }
        return this;
    }

    protected void setDayOfWeek(int n) {
        this.dayOfWeek = n;
    }

    protected void setDaylightSaving(int n) {
        this.daylightSaving = n;
    }

    public CalendarDate setEra(Era era) {
        if (this.era == era) {
            return this;
        }
        this.era = era;
        this.normalized = false;
        return this;
    }

    public CalendarDate setHours(int n) {
        if (this.hours != n) {
            this.hours = n;
            this.normalized = false;
        }
        return this;
    }

    void setLeapYear(boolean bl) {
        this.leapYear = bl;
    }

    protected void setLocale(Locale locale) {
        this.locale = locale;
    }

    public CalendarDate setMillis(int n) {
        if (this.millis != n) {
            this.millis = n;
            this.normalized = false;
        }
        return this;
    }

    public CalendarDate setMinutes(int n) {
        if (this.minutes != n) {
            this.minutes = n;
            this.normalized = false;
        }
        return this;
    }

    public CalendarDate setMonth(int n) {
        if (this.month != n) {
            this.month = n;
            this.normalized = false;
        }
        return this;
    }

    protected void setNormalized(boolean bl) {
        this.normalized = bl;
    }

    public CalendarDate setSeconds(int n) {
        if (this.seconds != n) {
            this.seconds = n;
            this.normalized = false;
        }
        return this;
    }

    public void setStandardTime(boolean bl) {
        this.forceStandardTime = bl;
    }

    public CalendarDate setTimeOfDay(int n, int n2, int n3, int n4) {
        this.setHours(n);
        this.setMinutes(n2);
        this.setSeconds(n3);
        this.setMillis(n4);
        return this;
    }

    protected void setTimeOfDay(long l) {
        this.fraction = l;
    }

    public CalendarDate setYear(int n) {
        if (this.year != n) {
            this.year = n;
            this.normalized = false;
        }
        return this;
    }

    public CalendarDate setZone(TimeZone timeZone) {
        this.zoneinfo = timeZone;
        return this;
    }

    protected void setZoneOffset(int n) {
        this.zoneOffset = n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        CalendarUtils.sprintf0d(stringBuilder, this.year, 4).append('-');
        CalendarUtils.sprintf0d(stringBuilder, this.month, 2).append('-');
        CalendarUtils.sprintf0d(stringBuilder, this.dayOfMonth, 2).append('T');
        CalendarUtils.sprintf0d(stringBuilder, this.hours, 2).append(':');
        CalendarUtils.sprintf0d(stringBuilder, this.minutes, 2).append(':');
        CalendarUtils.sprintf0d(stringBuilder, this.seconds, 2).append('.');
        CalendarUtils.sprintf0d(stringBuilder, this.millis, 3);
        int n = this.zoneOffset;
        if (n == 0) {
            stringBuilder.append('Z');
        } else if (n != Integer.MIN_VALUE) {
            char c;
            if (n > 0) {
                char c2;
                n = this.zoneOffset;
                c = c2 = '+';
            } else {
                char c3;
                n = -n;
                c = c3 = '-';
            }
            stringBuilder.append(c);
            CalendarUtils.sprintf0d(stringBuilder, (n /= 60000) / 60, 2);
            CalendarUtils.sprintf0d(stringBuilder, n % 60, 2);
        } else {
            stringBuilder.append(" local time");
        }
        return stringBuilder.toString();
    }
}

