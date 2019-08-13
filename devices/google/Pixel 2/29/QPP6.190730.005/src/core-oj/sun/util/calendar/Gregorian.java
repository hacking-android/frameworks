/*
 * Decompiled with CFR 0.145.
 */
package sun.util.calendar;

import java.util.TimeZone;
import sun.util.calendar.BaseCalendar;
import sun.util.calendar.CalendarDate;

public class Gregorian
extends BaseCalendar {
    Gregorian() {
    }

    @Override
    public Date getCalendarDate() {
        return this.getCalendarDate(System.currentTimeMillis(), this.newCalendarDate());
    }

    @Override
    public Date getCalendarDate(long l) {
        return this.getCalendarDate(l, this.newCalendarDate());
    }

    @Override
    public Date getCalendarDate(long l, TimeZone timeZone) {
        return this.getCalendarDate(l, this.newCalendarDate(timeZone));
    }

    @Override
    public Date getCalendarDate(long l, CalendarDate calendarDate) {
        return (Date)super.getCalendarDate(l, calendarDate);
    }

    @Override
    public String getName() {
        return "gregorian";
    }

    @Override
    public Date newCalendarDate() {
        return new Date();
    }

    @Override
    public Date newCalendarDate(TimeZone timeZone) {
        return new Date(timeZone);
    }

    static class Date
    extends BaseCalendar.Date {
        protected Date() {
        }

        protected Date(TimeZone timeZone) {
            super(timeZone);
        }

        @Override
        public int getNormalizedYear() {
            return this.getYear();
        }

        @Override
        public void setNormalizedYear(int n) {
            this.setYear(n);
        }
    }

}

