/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.ZoneInfo
 */
package sun.util.calendar;

import java.util.TimeZone;
import libcore.util.ZoneInfo;
import sun.util.calendar.CalendarDate;
import sun.util.calendar.CalendarSystem;
import sun.util.calendar.CalendarUtils;
import sun.util.calendar.Era;

public abstract class AbstractCalendar
extends CalendarSystem {
    static final int DAY_IN_MILLIS = 86400000;
    static final int EPOCH_OFFSET = 719163;
    static final int HOUR_IN_MILLIS = 3600000;
    static final int MINUTE_IN_MILLIS = 60000;
    static final int SECOND_IN_MILLIS = 1000;
    private Era[] eras;

    protected AbstractCalendar() {
    }

    static long getDayOfWeekDateAfter(long l, int n) {
        return AbstractCalendar.getDayOfWeekDateOnOrBefore(7L + l, n);
    }

    static long getDayOfWeekDateBefore(long l, int n) {
        return AbstractCalendar.getDayOfWeekDateOnOrBefore(l - 1L, n);
    }

    public static long getDayOfWeekDateOnOrBefore(long l, int n) {
        long l2 = l - (long)(n - 1);
        if (l2 >= 0L) {
            return l - l2 % 7L;
        }
        return l - CalendarUtils.mod(l2, 7L);
    }

    @Override
    public CalendarDate getCalendarDate() {
        return this.getCalendarDate(System.currentTimeMillis(), this.newCalendarDate());
    }

    @Override
    public CalendarDate getCalendarDate(long l) {
        return this.getCalendarDate(l, this.newCalendarDate());
    }

    @Override
    public CalendarDate getCalendarDate(long l, TimeZone timeZone) {
        return this.getCalendarDate(l, this.newCalendarDate(timeZone));
    }

    @Override
    public CalendarDate getCalendarDate(long l, CalendarDate calendarDate) {
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        long l2 = 0L;
        TimeZone timeZone = calendarDate.getZone();
        if (timeZone != null) {
            int[] arrn = new int[2];
            if (timeZone instanceof ZoneInfo) {
                n = ((ZoneInfo)timeZone).getOffsetsByUtcTime(l, arrn);
            } else {
                n = timeZone.getOffset(l);
                arrn[0] = timeZone.getRawOffset();
                arrn[1] = n - arrn[0];
            }
            l2 = n / 86400000;
            int n4 = n % 86400000;
            n3 = arrn[1];
            n2 = n;
            n = n4;
        }
        calendarDate.setZoneOffset(n2);
        calendarDate.setDaylightSaving(n3);
        l2 += l / 86400000L;
        n = n3 = n + (int)(l % 86400000L);
        l = l2++;
        if (n3 >= 86400000) {
            n3 -= 86400000;
        } else {
            do {
                n3 = n;
                l2 = l--;
                if (n >= 0) break;
                n += 86400000;
            } while (true);
        }
        this.getCalendarDateFromFixedDate(calendarDate, l2 + 719163L);
        this.setTimeOfDay(calendarDate, n3);
        calendarDate.setLeapYear(this.isLeapYear(calendarDate));
        calendarDate.setNormalized(true);
        return calendarDate;
    }

    protected abstract void getCalendarDateFromFixedDate(CalendarDate var1, long var2);

    @Override
    public Era getEra(String string) {
        if (this.eras != null) {
            Era[] arrera;
            for (int i = 0; i < (arrera = this.eras).length; ++i) {
                if (!arrera[i].equals(string)) continue;
                return this.eras[i];
            }
        }
        return null;
    }

    @Override
    public Era[] getEras() {
        Era[] arrera = null;
        Era[] arrera2 = this.eras;
        if (arrera2 != null) {
            arrera = new Era[arrera2.length];
            System.arraycopy(arrera2, 0, arrera, 0, arrera2.length);
        }
        return arrera;
    }

    protected abstract long getFixedDate(CalendarDate var1);

    @Override
    public CalendarDate getNthDayOfWeek(int n, int n2, CalendarDate calendarDate) {
        calendarDate = (CalendarDate)calendarDate.clone();
        this.normalize(calendarDate);
        long l = this.getFixedDate(calendarDate);
        l = n > 0 ? (long)(n * 7) + AbstractCalendar.getDayOfWeekDateBefore(l, n2) : (long)(n * 7) + AbstractCalendar.getDayOfWeekDateAfter(l, n2);
        this.getCalendarDateFromFixedDate(calendarDate, l);
        return calendarDate;
    }

    @Override
    public long getTime(CalendarDate calendarDate) {
        long l = (this.getFixedDate(calendarDate) - 719163L) * 86400000L + this.getTimeOfDay(calendarDate);
        int n = 0;
        TimeZone timeZone = calendarDate.getZone();
        if (timeZone != null) {
            if (calendarDate.isNormalized()) {
                return l - (long)calendarDate.getZoneOffset();
            }
            int[] arrn = new int[2];
            n = calendarDate.isStandardTime() ? timeZone.getOffset(l - (long)timeZone.getRawOffset()) : timeZone.getOffset(l - (long)timeZone.getRawOffset());
        }
        this.getCalendarDate(l -= (long)n, calendarDate);
        return l;
    }

    protected long getTimeOfDay(CalendarDate calendarDate) {
        long l = calendarDate.getTimeOfDay();
        if (l != Long.MIN_VALUE) {
            return l;
        }
        l = this.getTimeOfDayValue(calendarDate);
        calendarDate.setTimeOfDay(l);
        return l;
    }

    public long getTimeOfDayValue(CalendarDate calendarDate) {
        return (((long)calendarDate.getHours() * 60L + (long)calendarDate.getMinutes()) * 60L + (long)calendarDate.getSeconds()) * 1000L + (long)calendarDate.getMillis();
    }

    @Override
    public int getWeekLength() {
        return 7;
    }

    protected abstract boolean isLeapYear(CalendarDate var1);

    int normalizeTime(CalendarDate calendarDate) {
        long l;
        long l2 = this.getTimeOfDay(calendarDate);
        long l3 = 0L;
        if (l2 >= 86400000L) {
            l3 = l2 / 86400000L;
            l = l2 % 86400000L;
        } else {
            l = l2;
            if (l2 < 0L) {
                long l4 = CalendarUtils.floorDivide(l2, 86400000L);
                l = l2;
                l3 = l4;
                if (l4 != 0L) {
                    l = l2 - 86400000L * l4;
                    l3 = l4;
                }
            }
        }
        if (l3 != 0L) {
            calendarDate.setTimeOfDay(l);
        }
        calendarDate.setMillis((int)(l % 1000L));
        calendarDate.setSeconds((int)((l /= 1000L) % 60L));
        calendarDate.setMinutes((int)((l /= 60L) % 60L));
        calendarDate.setHours((int)(l / 60L));
        return (int)l3;
    }

    @Override
    public void setEra(CalendarDate object, String string) {
        Object object2;
        if (this.eras == null) {
            return;
        }
        for (int i = 0; i < ((Era[])(object2 = this.eras)).length; ++i) {
            if ((object2 = object2[i]) == null || !((Era)object2).getName().equals(string)) continue;
            ((CalendarDate)object).setEra((Era)object2);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("unknown era name: ");
        ((StringBuilder)object).append(string);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    protected void setEras(Era[] arrera) {
        this.eras = arrera;
    }

    @Override
    public CalendarDate setTimeOfDay(CalendarDate calendarDate, int n) {
        if (n >= 0) {
            boolean bl = calendarDate.isNormalized();
            int n2 = n / 3600000;
            int n3 = n % 3600000;
            int n4 = n3 / 60000;
            int n5 = (n3 %= 60000) / 1000;
            calendarDate.setHours(n2);
            calendarDate.setMinutes(n4);
            calendarDate.setSeconds(n5);
            calendarDate.setMillis(n3 % 1000);
            calendarDate.setTimeOfDay(n);
            if (n2 < 24 && bl) {
                calendarDate.setNormalized(bl);
            }
            return calendarDate;
        }
        throw new IllegalArgumentException();
    }

    public boolean validateTime(CalendarDate calendarDate) {
        int n = calendarDate.getHours();
        if (n >= 0 && n < 24) {
            n = calendarDate.getMinutes();
            if (n >= 0 && n < 60) {
                n = calendarDate.getSeconds();
                if (n >= 0 && n < 60) {
                    n = calendarDate.getMillis();
                    return n >= 0 && n < 1000;
                    {
                    }
                }
                return false;
            }
            return false;
        }
        return false;
    }
}

