/*
 * Decompiled with CFR 0.145.
 */
package sun.util.calendar;

import java.util.TimeZone;
import sun.util.calendar.BaseCalendar;
import sun.util.calendar.CalendarDate;
import sun.util.calendar.CalendarUtils;
import sun.util.calendar.Era;

public class JulianCalendar
extends BaseCalendar {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int BCE = 0;
    private static final int CE = 1;
    private static final int JULIAN_EPOCH = -1;
    private static final Era[] eras = new Era[]{new Era("BeforeCommonEra", "B.C.E.", Long.MIN_VALUE, false), new Era("CommonEra", "C.E.", -62135709175808L, true)};

    JulianCalendar() {
        this.setEras(eras);
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
    public void getCalendarDateFromFixedDate(CalendarDate calendarDate, long l) {
        calendarDate = (Date)calendarDate;
        long l2 = (l + 1L) * 4L + 1464L;
        int n = l2 >= 0L ? (int)(l2 / 1461L) : (int)CalendarUtils.floorDivide(l2, 1461L);
        int n2 = (int)(l - this.getFixedDate(n, 1, 1, (BaseCalendar.Date)calendarDate));
        boolean bl = CalendarUtils.isJulianLeapYear(n);
        int n3 = n2;
        if (l >= this.getFixedDate(n, 3, 1, (BaseCalendar.Date)calendarDate)) {
            n3 = bl ? 1 : 2;
            n3 = n2 + n3;
        }
        n3 = (n3 = n3 * 12 + 373) > 0 ? (n3 /= 367) : CalendarUtils.floorDivide(n3, 367);
        int n4 = (int)(l - this.getFixedDate(n, n3, 1, (BaseCalendar.Date)calendarDate));
        n2 = JulianCalendar.getDayOfWeekFromFixedDate(l);
        ((Date)calendarDate).setNormalizedYear(n);
        calendarDate.setMonth(n3);
        calendarDate.setDayOfMonth(n4 + 1);
        calendarDate.setDayOfWeek(n2);
        calendarDate.setLeapYear(bl);
        calendarDate.setNormalized(true);
    }

    @Override
    public int getDayOfWeek(CalendarDate calendarDate) {
        return JulianCalendar.getDayOfWeekFromFixedDate(this.getFixedDate(calendarDate));
    }

    @Override
    public long getFixedDate(int n, int n2, int n3, BaseCalendar.Date date) {
        boolean bl = true;
        if (n2 != 1 || n3 != 1) {
            bl = false;
        }
        if (date != null && date.hit(n)) {
            if (bl) {
                return date.getCachedJan1();
            }
            return date.getCachedJan1() + this.getDayOfYear(n, n2, n3) - 1L;
        }
        long l = n;
        long l2 = (l - 1L) * 365L - 2L + (long)n3;
        l2 = l > 0L ? (l2 += (l - 1L) / 4L) : (l2 += CalendarUtils.floorDivide(l - 1L, 4L));
        l2 = n2 > 0 ? (l2 += ((long)n2 * 367L - 362L) / 12L) : (l2 += CalendarUtils.floorDivide((long)n2 * 367L - 362L, 12L));
        l = l2;
        if (n2 > 2) {
            l = CalendarUtils.isJulianLeapYear(n) ? 1L : 2L;
            l = l2 - l;
        }
        if (date != null && bl) {
            n2 = CalendarUtils.isJulianLeapYear(n) ? 366 : 365;
            date.setCache(n, l, n2);
        }
        return l;
    }

    @Override
    public String getName() {
        return "julian";
    }

    @Override
    public int getYearFromFixedDate(long l) {
        return (int)CalendarUtils.floorDivide((l + 1L) * 4L + 1464L, 1461L);
    }

    @Override
    boolean isLeapYear(int n) {
        return CalendarUtils.isJulianLeapYear(n);
    }

    @Override
    public Date newCalendarDate() {
        return new Date();
    }

    @Override
    public Date newCalendarDate(TimeZone timeZone) {
        return new Date(timeZone);
    }

    private static class Date
    extends BaseCalendar.Date {
        protected Date() {
            this.setCache(1, -1L, 365);
        }

        protected Date(TimeZone timeZone) {
            super(timeZone);
            this.setCache(1, -1L, 365);
        }

        @Override
        public int getNormalizedYear() {
            if (this.getEra() == eras[0]) {
                return 1 - this.getYear();
            }
            return this.getYear();
        }

        @Override
        public Date setEra(Era era) {
            if (era != null) {
                if (era == eras[0] && era == eras[1]) {
                    super.setEra(era);
                    return this;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("unknown era: ");
                stringBuilder.append(era);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            throw new NullPointerException();
        }

        protected void setKnownEra(Era era) {
            super.setEra(era);
        }

        @Override
        public void setNormalizedYear(int n) {
            if (n <= 0) {
                this.setYear(1 - n);
                this.setKnownEra(eras[0]);
            } else {
                this.setYear(n);
                this.setKnownEra(eras[1]);
            }
        }

        @Override
        public String toString() {
            String string = super.toString();
            string = string.substring(string.indexOf(84));
            StringBuffer stringBuffer = new StringBuffer();
            Object object = this.getEra();
            if (object != null && (object = ((Era)object).getAbbreviation()) != null) {
                stringBuffer.append((String)object);
                stringBuffer.append(' ');
            }
            stringBuffer.append(this.getYear());
            stringBuffer.append('-');
            CalendarUtils.sprintf0d(stringBuffer, this.getMonth(), 2).append('-');
            CalendarUtils.sprintf0d(stringBuffer, this.getDayOfMonth(), 2);
            stringBuffer.append(string);
            return stringBuffer.toString();
        }
    }

}

