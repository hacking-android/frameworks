/*
 * Decompiled with CFR 0.145.
 */
package sun.util.calendar;

import java.util.Locale;
import java.util.TimeZone;
import sun.util.calendar.BaseCalendar;
import sun.util.calendar.CalendarDate;
import sun.util.calendar.Era;

class ImmutableGregorianDate
extends BaseCalendar.Date {
    private final BaseCalendar.Date date;

    ImmutableGregorianDate(BaseCalendar.Date date) {
        if (date != null) {
            this.date = date;
            return;
        }
        throw new NullPointerException();
    }

    private void unsupported() {
        throw new UnsupportedOperationException();
    }

    @Override
    public CalendarDate addDate(int n, int n2, int n3) {
        this.unsupported();
        return this;
    }

    @Override
    public CalendarDate addDayOfMonth(int n) {
        this.unsupported();
        return this;
    }

    @Override
    public CalendarDate addHours(int n) {
        this.unsupported();
        return this;
    }

    @Override
    public CalendarDate addMillis(int n) {
        this.unsupported();
        return this;
    }

    @Override
    public CalendarDate addMinutes(int n) {
        this.unsupported();
        return this;
    }

    @Override
    public CalendarDate addMonth(int n) {
        this.unsupported();
        return this;
    }

    @Override
    public CalendarDate addSeconds(int n) {
        this.unsupported();
        return this;
    }

    @Override
    public CalendarDate addTimeOfDay(int n, int n2, int n3, int n4) {
        this.unsupported();
        return this;
    }

    @Override
    public CalendarDate addYear(int n) {
        this.unsupported();
        return this;
    }

    @Override
    public Object clone() {
        return super.clone();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ImmutableGregorianDate)) {
            return false;
        }
        return this.date.equals(((ImmutableGregorianDate)object).date);
    }

    @Override
    public int getDayOfMonth() {
        return this.date.getDayOfMonth();
    }

    @Override
    public int getDayOfWeek() {
        return this.date.getDayOfWeek();
    }

    @Override
    public int getDaylightSaving() {
        return this.date.getDaylightSaving();
    }

    @Override
    public Era getEra() {
        return this.date.getEra();
    }

    @Override
    public int getHours() {
        return this.date.getHours();
    }

    @Override
    public int getMillis() {
        return this.date.getMillis();
    }

    @Override
    public int getMinutes() {
        return this.date.getMinutes();
    }

    @Override
    public int getMonth() {
        return this.date.getMonth();
    }

    @Override
    public int getNormalizedYear() {
        return this.date.getNormalizedYear();
    }

    @Override
    public int getSeconds() {
        return this.date.getSeconds();
    }

    @Override
    public long getTimeOfDay() {
        return this.date.getTimeOfDay();
    }

    @Override
    public int getYear() {
        return this.date.getYear();
    }

    @Override
    public TimeZone getZone() {
        return this.date.getZone();
    }

    @Override
    public int getZoneOffset() {
        return this.date.getZoneOffset();
    }

    @Override
    public int hashCode() {
        return this.date.hashCode();
    }

    @Override
    public boolean isDaylightTime() {
        return this.date.isDaylightTime();
    }

    @Override
    public boolean isLeapYear() {
        return this.date.isLeapYear();
    }

    @Override
    public boolean isNormalized() {
        return this.date.isNormalized();
    }

    @Override
    public boolean isSameDate(CalendarDate calendarDate) {
        return calendarDate.isSameDate(calendarDate);
    }

    @Override
    public boolean isStandardTime() {
        return this.date.isStandardTime();
    }

    @Override
    public CalendarDate setDate(int n, int n2, int n3) {
        this.unsupported();
        return this;
    }

    @Override
    public CalendarDate setDayOfMonth(int n) {
        this.unsupported();
        return this;
    }

    @Override
    protected void setDayOfWeek(int n) {
        this.unsupported();
    }

    @Override
    protected void setDaylightSaving(int n) {
        this.unsupported();
    }

    @Override
    public CalendarDate setEra(Era era) {
        this.unsupported();
        return this;
    }

    @Override
    public CalendarDate setHours(int n) {
        this.unsupported();
        return this;
    }

    @Override
    void setLeapYear(boolean bl) {
        this.unsupported();
    }

    @Override
    protected void setLocale(Locale locale) {
        this.unsupported();
    }

    @Override
    public CalendarDate setMillis(int n) {
        this.unsupported();
        return this;
    }

    @Override
    public CalendarDate setMinutes(int n) {
        this.unsupported();
        return this;
    }

    @Override
    public CalendarDate setMonth(int n) {
        this.unsupported();
        return this;
    }

    @Override
    protected void setNormalized(boolean bl) {
        this.unsupported();
    }

    @Override
    public void setNormalizedYear(int n) {
        this.unsupported();
    }

    @Override
    public CalendarDate setSeconds(int n) {
        this.unsupported();
        return this;
    }

    @Override
    public void setStandardTime(boolean bl) {
        this.unsupported();
    }

    @Override
    public CalendarDate setTimeOfDay(int n, int n2, int n3, int n4) {
        this.unsupported();
        return this;
    }

    @Override
    protected void setTimeOfDay(long l) {
        this.unsupported();
    }

    @Override
    public CalendarDate setYear(int n) {
        this.unsupported();
        return this;
    }

    @Override
    public CalendarDate setZone(TimeZone timeZone) {
        this.unsupported();
        return this;
    }

    @Override
    protected void setZoneOffset(int n) {
        this.unsupported();
    }

    @Override
    public String toString() {
        return this.date.toString();
    }
}

