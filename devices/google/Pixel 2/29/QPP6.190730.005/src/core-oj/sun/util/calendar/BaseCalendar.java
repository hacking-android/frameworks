/*
 * Decompiled with CFR 0.145.
 */
package sun.util.calendar;

import java.util.TimeZone;
import sun.util.calendar.AbstractCalendar;
import sun.util.calendar.CalendarDate;
import sun.util.calendar.CalendarUtils;

public abstract class BaseCalendar
extends AbstractCalendar {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int[] ACCUMULATED_DAYS_IN_MONTH;
    static final int[] ACCUMULATED_DAYS_IN_MONTH_LEAP;
    public static final int APRIL = 4;
    public static final int AUGUST = 8;
    private static final int BASE_YEAR = 1970;
    static final int[] DAYS_IN_MONTH;
    public static final int DECEMBER = 12;
    public static final int FEBRUARY = 2;
    private static final int[] FIXED_DATES;
    public static final int FRIDAY = 6;
    public static final int JANUARY = 1;
    public static final int JULY = 7;
    public static final int JUNE = 6;
    public static final int MARCH = 3;
    public static final int MAY = 5;
    public static final int MONDAY = 2;
    public static final int NOVEMBER = 11;
    public static final int OCTOBER = 10;
    public static final int SATURDAY = 7;
    public static final int SEPTEMBER = 9;
    public static final int SUNDAY = 1;
    public static final int THURSDAY = 5;
    public static final int TUESDAY = 3;
    public static final int WEDNESDAY = 4;

    static {
        FIXED_DATES = new int[]{719163, 719528, 719893, 720259, 720624, 720989, 721354, 721720, 722085, 722450, 722815, 723181, 723546, 723911, 724276, 724642, 725007, 725372, 725737, 726103, 726468, 726833, 727198, 727564, 727929, 728294, 728659, 729025, 729390, 729755, 730120, 730486, 730851, 731216, 731581, 731947, 732312, 732677, 733042, 733408, 733773, 734138, 734503, 734869, 735234, 735599, 735964, 736330, 736695, 737060, 737425, 737791, 738156, 738521, 738886, 739252, 739617, 739982, 740347, 740713, 741078, 741443, 741808, 742174, 742539, 742904, 743269, 743635, 744000, 744365};
        DAYS_IN_MONTH = new int[]{31, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        ACCUMULATED_DAYS_IN_MONTH = new int[]{-30, 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334};
        ACCUMULATED_DAYS_IN_MONTH_LEAP = new int[]{-30, 0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335};
    }

    public static final int getDayOfWeekFromFixedDate(long l) {
        if (l >= 0L) {
            return (int)(l % 7L) + 1;
        }
        return (int)CalendarUtils.mod(l, 7L) + 1;
    }

    private int getMonthLength(int n, int n2) {
        int n3;
        int n4 = n3 = DAYS_IN_MONTH[n2];
        if (n2 == 2) {
            n4 = n3;
            if (this.isLeapYear(n)) {
                n4 = n3 + 1;
            }
        }
        return n4;
    }

    @Override
    public void getCalendarDateFromFixedDate(CalendarDate calendarDate, long l) {
        int n;
        boolean bl;
        long l2;
        long l3;
        int n2;
        if (((Date)(calendarDate = (Date)calendarDate)).hit(l)) {
            n = ((Date)calendarDate).getCachedYear();
            l2 = ((Date)calendarDate).getCachedJan1();
            bl = this.isLeapYear(n);
        } else {
            n2 = this.getGregorianYearFromFixedDate(l);
            l2 = this.getFixedDate(n2, 1, 1, null);
            bl = this.isLeapYear(n2);
            n = bl ? 366 : 365;
            ((Date)calendarDate).setCache(n2, l2, n);
            n = n2;
        }
        int n3 = (int)(l - l2);
        long l4 = l3 = 31L + l2 + 28L;
        if (bl) {
            l4 = l3 + 1L;
        }
        n2 = n3;
        if (l >= l4) {
            n2 = bl ? 1 : 2;
            n2 = n3 + n2;
        }
        n2 = (n2 = n2 * 12 + 373) > 0 ? (n2 /= 367) : CalendarUtils.floorDivide(n2, 367);
        l2 = l4 = (long)ACCUMULATED_DAYS_IN_MONTH[n2] + l2;
        if (bl) {
            l2 = l4;
            if (n2 >= 3) {
                l2 = l4 + 1L;
            }
        }
        int n4 = (int)(l - l2);
        n3 = BaseCalendar.getDayOfWeekFromFixedDate(l);
        ((Date)calendarDate).setNormalizedYear(n);
        calendarDate.setMonth(n2);
        calendarDate.setDayOfMonth(n4 + 1);
        calendarDate.setDayOfWeek(n3);
        calendarDate.setLeapYear(bl);
        calendarDate.setNormalized(true);
    }

    public int getDayOfWeek(CalendarDate calendarDate) {
        return BaseCalendar.getDayOfWeekFromFixedDate(this.getFixedDate(calendarDate));
    }

    final long getDayOfYear(int n, int n2, int n3) {
        long l = n3;
        n = this.isLeapYear(n) ? ACCUMULATED_DAYS_IN_MONTH_LEAP[n2] : ACCUMULATED_DAYS_IN_MONTH[n2];
        return l + (long)n;
    }

    public long getDayOfYear(CalendarDate calendarDate) {
        return this.getDayOfYear(((Date)calendarDate).getNormalizedYear(), calendarDate.getMonth(), calendarDate.getDayOfMonth());
    }

    public long getFixedDate(int n, int n2, int n3, Date date) {
        int[] arrn;
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
        int n4 = n - 1970;
        if (n4 >= 0 && n4 < (arrn = FIXED_DATES).length) {
            long l = arrn[n4];
            if (date != null) {
                n4 = this.isLeapYear(n) ? 366 : 365;
                date.setCache(n, l, n4);
            }
            if (!bl) {
                l = this.getDayOfYear(n, n2, n3) + l - 1L;
            }
            return l;
        }
        long l = (long)n - 1L;
        long l2 = n3;
        l = l >= 0L ? l2 + (365L * l + l / 4L - l / 100L + l / 400L + (long)((n2 * 367 - 362) / 12)) : l2 + (365L * l + CalendarUtils.floorDivide(l, 4L) - CalendarUtils.floorDivide(l, 100L) + CalendarUtils.floorDivide(l, 400L) + (long)CalendarUtils.floorDivide(n2 * 367 - 362, 12));
        l2 = l;
        if (n2 > 2) {
            l2 = this.isLeapYear(n) ? 1L : 2L;
            l2 = l - l2;
        }
        if (date != null && bl) {
            n2 = this.isLeapYear(n) ? 366 : 365;
            date.setCache(n, l2, n2);
        }
        return l2;
    }

    @Override
    public long getFixedDate(CalendarDate calendarDate) {
        if (!calendarDate.isNormalized()) {
            this.normalizeMonth(calendarDate);
        }
        return this.getFixedDate(((Date)calendarDate).getNormalizedYear(), calendarDate.getMonth(), calendarDate.getDayOfMonth(), (Date)calendarDate);
    }

    final int getGregorianYearFromFixedDate(long l) {
        int n;
        int n2;
        int n3;
        int n4;
        if (l > 0L) {
            n3 = (int)(--l / 146097L);
            n = (int)(l % 146097L);
            n4 = n / 36524;
            n2 = (n %= 36524) / 1461;
            n %= 1461;
            n /= 365;
        } else {
            n3 = (int)CalendarUtils.floorDivide(--l, 146097L);
            n = (int)CalendarUtils.mod(l, 146097L);
            n4 = CalendarUtils.floorDivide(n, 36524);
            n = CalendarUtils.mod(n, 36524);
            n2 = CalendarUtils.floorDivide(n, 1461);
            int n5 = CalendarUtils.mod(n, 1461);
            n = CalendarUtils.floorDivide(n5, 365);
            CalendarUtils.mod(n5, 365);
        }
        n3 = n2 = n3 * 400 + n4 * 100 + n2 * 4 + n;
        if (n4 != 4) {
            n3 = n2;
            if (n != 4) {
                n3 = n2 + 1;
            }
        }
        return n3;
    }

    @Override
    public int getMonthLength(CalendarDate object) {
        int n = ((CalendarDate)(object = (Date)object)).getMonth();
        if (n >= 1 && n <= 12) {
            return this.getMonthLength(((Date)object).getNormalizedYear(), n);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Illegal month value: ");
        ((StringBuilder)object).append(n);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public int getYearFromFixedDate(long l) {
        return this.getGregorianYearFromFixedDate(l);
    }

    @Override
    public int getYearLength(CalendarDate calendarDate) {
        int n = this.isLeapYear(((Date)calendarDate).getNormalizedYear()) ? 366 : 365;
        return n;
    }

    @Override
    public int getYearLengthInMonths(CalendarDate calendarDate) {
        return 12;
    }

    boolean isLeapYear(int n) {
        return CalendarUtils.isGregorianLeapYear(n);
    }

    @Override
    protected boolean isLeapYear(CalendarDate calendarDate) {
        return this.isLeapYear(((Date)calendarDate).getNormalizedYear());
    }

    @Override
    public boolean normalize(CalendarDate calendarDate) {
        if (calendarDate.isNormalized()) {
            return true;
        }
        Date date = (Date)calendarDate;
        if (date.getZone() != null) {
            this.getTime(calendarDate);
            return true;
        }
        int n = this.normalizeTime(date);
        this.normalizeMonth(date);
        long l = (long)date.getDayOfMonth() + (long)n;
        n = date.getMonth();
        int n2 = date.getNormalizedYear();
        int n3 = this.getMonthLength(n2, n);
        if (l > 0L && l <= (long)n3) {
            date.setDayOfWeek(this.getDayOfWeek(date));
        } else if (l <= 0L && l > -28L) {
            n3 = n - 1;
            date.setDayOfMonth((int)(l + (long)this.getMonthLength(n2, n3)));
            n = n3;
            if (n3 == 0) {
                n = 12;
                date.setNormalizedYear(n2 - 1);
            }
            date.setMonth(n);
        } else if (l > (long)n3 && l < (long)(n3 + 28)) {
            long l2 = n3;
            n3 = n + 1;
            date.setDayOfMonth((int)(l - l2));
            n = n3;
            if (n3 > 12) {
                date.setNormalizedYear(n2 + 1);
                n = 1;
            }
            date.setMonth(n);
        } else {
            this.getCalendarDateFromFixedDate(date, this.getFixedDate(n2, n, 1, date) + l - 1L);
        }
        calendarDate.setLeapYear(this.isLeapYear(date.getNormalizedYear()));
        calendarDate.setZoneOffset(0);
        calendarDate.setDaylightSaving(0);
        date.setNormalized(true);
        return true;
    }

    void normalizeMonth(CalendarDate calendarDate) {
        block1 : {
            long l;
            int n;
            block0 : {
                calendarDate = (Date)calendarDate;
                n = ((Date)calendarDate).getNormalizedYear();
                l = calendarDate.getMonth();
                if (l > 0L) break block0;
                l = 1L - l;
                ((Date)calendarDate).setNormalizedYear(n - (int)(l / 12L + 1L));
                calendarDate.setMonth((int)(13L - l % 12L));
                break block1;
            }
            if (l <= 12L) break block1;
            ((Date)calendarDate).setNormalizedYear(n + (int)((l - 1L) / 12L));
            calendarDate.setMonth((int)((l - 1L) % 12L + 1L));
        }
    }

    @Override
    public boolean validate(CalendarDate calendarDate) {
        Date date = (Date)calendarDate;
        if (date.isNormalized()) {
            return true;
        }
        int n = date.getMonth();
        if (n >= 1 && n <= 12) {
            int n2 = date.getDayOfMonth();
            if (n2 > 0 && n2 <= this.getMonthLength(date.getNormalizedYear(), n)) {
                n = date.getDayOfWeek();
                if (n != Integer.MIN_VALUE && n != this.getDayOfWeek(date)) {
                    return false;
                }
                if (!this.validateTime(calendarDate)) {
                    return false;
                }
                date.setNormalized(true);
                return true;
            }
            return false;
        }
        return false;
    }

    public static abstract class Date
    extends CalendarDate {
        long cachedFixedDateJan1 = 731581L;
        long cachedFixedDateNextJan1 = this.cachedFixedDateJan1 + 366L;
        int cachedYear = 2004;

        protected Date() {
        }

        protected Date(TimeZone timeZone) {
            super(timeZone);
        }

        protected long getCachedJan1() {
            return this.cachedFixedDateJan1;
        }

        protected int getCachedYear() {
            return this.cachedYear;
        }

        public abstract int getNormalizedYear();

        protected final boolean hit(int n) {
            boolean bl = n == this.cachedYear;
            return bl;
        }

        protected final boolean hit(long l) {
            boolean bl = l >= this.cachedFixedDateJan1 && l < this.cachedFixedDateNextJan1;
            return bl;
        }

        protected void setCache(int n, long l, int n2) {
            this.cachedYear = n;
            this.cachedFixedDateJan1 = l;
            this.cachedFixedDateNextJan1 = (long)n2 + l;
        }

        public Date setNormalizedDate(int n, int n2, int n3) {
            this.setNormalizedYear(n);
            this.setMonth(n2).setDayOfMonth(n3);
            return this;
        }

        public abstract void setNormalizedYear(int var1);
    }

}

