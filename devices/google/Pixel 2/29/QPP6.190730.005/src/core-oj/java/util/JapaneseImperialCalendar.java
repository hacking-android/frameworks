/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.ZoneInfo
 */
package java.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import libcore.util.ZoneInfo;
import sun.util.calendar.BaseCalendar;
import sun.util.calendar.CalendarDate;
import sun.util.calendar.CalendarSystem;
import sun.util.calendar.CalendarUtils;
import sun.util.calendar.Era;
import sun.util.calendar.Gregorian;
import sun.util.calendar.LocalGregorianCalendar;
import sun.util.locale.provider.CalendarDataUtility;

class JapaneseImperialCalendar
extends Calendar {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int BEFORE_MEIJI = 0;
    private static final Era BEFORE_MEIJI_ERA;
    private static final int EPOCH_OFFSET = 719163;
    private static final int EPOCH_YEAR = 1970;
    public static final int HEISEI = 4;
    static final int[] LEAST_MAX_VALUES;
    static final int[] MAX_VALUES;
    public static final int MEIJI = 1;
    static final int[] MIN_VALUES;
    private static final long ONE_DAY = 86400000L;
    private static final int ONE_HOUR = 3600000;
    private static final int ONE_MINUTE = 60000;
    private static final int ONE_SECOND = 1000;
    private static final long ONE_WEEK = 604800000L;
    private static final int REIWA = 5;
    public static final int SHOWA = 3;
    public static final int TAISHO = 2;
    private static final int currentEra;
    private static final Era[] eras;
    private static final Gregorian gcal;
    private static final LocalGregorianCalendar jcal;
    private static final long serialVersionUID = -3364572813905467929L;
    private static final long[] sinceFixedDates;
    private transient long cachedFixedDate = Long.MIN_VALUE;
    private transient LocalGregorianCalendar.Date jdate;
    private transient int[] originalFields;
    private transient int[] zoneOffsets;

    static {
        Object object;
        jcal = (LocalGregorianCalendar)CalendarSystem.forName("japanese");
        gcal = CalendarSystem.getGregorianCalendar();
        BEFORE_MEIJI_ERA = new Era("BeforeMeiji", "BM", Long.MIN_VALUE, false);
        MIN_VALUES = new int[]{0, -292275055, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, -46800000, 0};
        LEAST_MAX_VALUES = new int[]{0, 0, 0, 0, 4, 28, 0, 7, 4, 1, 11, 23, 59, 59, 999, 50400000, 1200000};
        MAX_VALUES = new int[]{0, 292278994, 11, 53, 6, 31, 366, 7, 6, 1, 11, 23, 59, 59, 999, 50400000, 7200000};
        Object object2 = jcal.getEras();
        int n = ((Era[])object2).length + 1;
        eras = new Era[n];
        sinceFixedDates = new long[n];
        JapaneseImperialCalendar.sinceFixedDates[0] = gcal.getFixedDate(BEFORE_MEIJI_ERA.getSinceDate());
        JapaneseImperialCalendar.eras[0] = BEFORE_MEIJI_ERA;
        int n2 = ((Era[])object2).length;
        n = 0 + 1;
        int n3 = 0;
        while (n3 < n2) {
            object = object2[n3];
            CalendarDate calendarDate = ((Era)object).getSinceDate();
            JapaneseImperialCalendar.sinceFixedDates[n] = gcal.getFixedDate(calendarDate);
            JapaneseImperialCalendar.eras[n] = object;
            ++n3;
            ++n;
        }
        currentEra = 5;
        object = LEAST_MAX_VALUES;
        object2 = MAX_VALUES;
        n = eras.length - 1;
        object2[0] = (Era)n;
        object[0] = n;
        int n4 = Integer.MAX_VALUE;
        n = Integer.MAX_VALUE;
        object = gcal.newCalendarDate(TimeZone.NO_TIMEZONE);
        for (n2 = 1; n2 < ((Era[])(object2 = eras)).length; ++n2) {
            block8 : {
                int n5;
                block7 : {
                    long l = sinceFixedDates[n2];
                    object2 = object2[n2].getSinceDate();
                    ((CalendarDate)object).setDate(((CalendarDate)object2).getYear(), 1, 1);
                    long l2 = gcal.getFixedDate((CalendarDate)object);
                    n3 = n;
                    if (l != l2) {
                        n3 = Math.min((int)(l - l2) + 1, n);
                    }
                    ((CalendarDate)object).setDate(((CalendarDate)object2).getYear(), 12, 31);
                    l2 = gcal.getFixedDate((CalendarDate)object);
                    n = n3;
                    if (l != l2) {
                        n = Math.min((int)(l2 - l) + 1, n3);
                    }
                    object2 = JapaneseImperialCalendar.getCalendarDate(l - 1L);
                    n5 = ((CalendarDate)object2).getYear();
                    if (((CalendarDate)object2).getMonth() != 1) break block7;
                    n3 = n5;
                    if (((CalendarDate)object2).getDayOfMonth() == 1) break block8;
                }
                n3 = n5 - 1;
            }
            n4 = Math.min(n3, n4);
        }
        object = LEAST_MAX_VALUES;
        object[1] = n4;
        object[6] = n;
    }

    JapaneseImperialCalendar(TimeZone timeZone, Locale locale) {
        super(timeZone, locale);
        this.jdate = jcal.newCalendarDate(timeZone);
        this.setTimeInMillis(System.currentTimeMillis());
    }

    JapaneseImperialCalendar(TimeZone timeZone, Locale locale, boolean bl) {
        super(timeZone, locale);
        this.jdate = jcal.newCalendarDate(timeZone);
    }

    private int actualMonthLength() {
        int n = jcal.getMonthLength(this.jdate);
        int n2 = JapaneseImperialCalendar.getTransitionEraIndex(this.jdate);
        int n3 = n;
        if (n2 == -1) {
            long l = sinceFixedDates[n2];
            CalendarDate calendarDate = eras[n2].getSinceDate();
            n3 = l <= this.cachedFixedDate ? n - (calendarDate.getDayOfMonth() - 1) : calendarDate.getDayOfMonth() - 1;
        }
        return n3;
    }

    private int computeFields(int n, int n2) {
        int n3;
        Object object;
        long l;
        int n4 = 0;
        Cloneable cloneable = this.getZone();
        if (this.zoneOffsets == null) {
            this.zoneOffsets = new int[2];
        }
        if (n2 != 98304) {
            if (cloneable instanceof ZoneInfo) {
                n4 = ((ZoneInfo)cloneable).getOffsetsByUtcTime(this.time, this.zoneOffsets);
            } else {
                n4 = ((TimeZone)cloneable).getOffset(this.time);
                this.zoneOffsets[0] = ((TimeZone)cloneable).getRawOffset();
                object = this.zoneOffsets;
                object[1] = n4 - object[0];
            }
        }
        if (n2 != 0) {
            if (JapaneseImperialCalendar.isFieldSet(n2, 15)) {
                this.zoneOffsets[0] = this.internalGet(15);
            }
            if (JapaneseImperialCalendar.isFieldSet(n2, 16)) {
                this.zoneOffsets[1] = this.internalGet(16);
            }
            object = this.zoneOffsets;
            n4 = object[0] + object[1];
        }
        long l2 = (long)n4 / 86400000L + this.time / 86400000L;
        n2 = n4 % 86400000 + (int)(this.time % 86400000L);
        if ((long)n2 >= 86400000L) {
            n3 = (int)((long)n2 - 86400000L);
            l = l2 + 1L;
        } else {
            do {
                n3 = n2;
                l = l2--;
                if (n2 >= 0) break;
                n2 = (int)((long)n2 + 86400000L);
            } while (true);
        }
        long l3 = l + 719163L;
        if (l3 != this.cachedFixedDate || l3 < 0L) {
            jcal.getCalendarDateFromFixedDate(this.jdate, l3);
            this.cachedFixedDate = l3;
        }
        int n5 = JapaneseImperialCalendar.getEraIndex(this.jdate);
        n2 = this.jdate.getYear();
        this.internalSet(0, n5);
        this.internalSet(1, n2);
        n4 = n | 3;
        int n6 = this.jdate.getMonth() - 1;
        int n7 = this.jdate.getDayOfMonth();
        n2 = n4;
        if ((n & 164) != 0) {
            this.internalSet(2, n6);
            this.internalSet(5, n7);
            this.internalSet(7, this.jdate.getDayOfWeek());
            n2 = n4 | 164;
        }
        n4 = n2;
        if ((n & 32256) != 0) {
            if (n3 != 0) {
                n4 = n3 / 3600000;
                this.internalSet(11, n4);
                this.internalSet(9, n4 / 12);
                this.internalSet(10, n4 % 12);
                n4 = n3 % 3600000;
                this.internalSet(12, n4 / 60000);
                this.internalSet(13, (n4 %= 60000) / 1000);
                this.internalSet(14, n4 % 1000);
            } else {
                this.internalSet(11, 0);
                this.internalSet(9, 0);
                this.internalSet(10, 0);
                this.internalSet(12, 0);
                this.internalSet(13, 0);
                this.internalSet(14, 0);
            }
            n4 = n2 | 32256;
        }
        n2 = n4;
        if ((n & 98304) != 0) {
            this.internalSet(15, this.zoneOffsets[0]);
            this.internalSet(16, this.zoneOffsets[1]);
            n2 = n4 | 98304;
        }
        if ((n & 344) != 0) {
            n3 = this.jdate.getNormalizedYear();
            boolean bl = this.isTransitionYear(this.jdate.getNormalizedYear());
            if (bl) {
                l2 = this.getFixedDateJan1(this.jdate, l3);
                n = (int)(l3 - l2) + 1;
            } else if (n3 == MIN_VALUES[1]) {
                object = jcal.getCalendarDate(Long.MIN_VALUE, this.getZone());
                l2 = jcal.getFixedDate((CalendarDate)object);
                n = (int)(l3 - l2) + 1;
            } else {
                n = (int)jcal.getDayOfYear(this.jdate);
                l2 = l3 - (long)n + 1L;
            }
            l = bl ? this.getFixedDateMonth1(this.jdate, l3) : l3 - (long)n7 + 1L;
            this.internalSet(6, n);
            this.internalSet(8, (n7 - 1) / 7 + 1);
            n4 = n = this.getWeekNumber(l2, l3);
            if (n4 == 0) {
                long l4 = l2 - 1L;
                cloneable = JapaneseImperialCalendar.getCalendarDate(l4);
                if (!bl && !this.isTransitionYear(((LocalGregorianCalendar.Date)cloneable).getNormalizedYear())) {
                    l2 -= 365L;
                    if (((CalendarDate)cloneable).isLeapYear()) {
                        --l2;
                    }
                } else if (bl) {
                    if (this.jdate.getYear() == 1) {
                        if (n5 > 5) {
                            object = eras[n5 - 1].getSinceDate();
                            if (n3 == ((CalendarDate)object).getYear()) {
                                ((CalendarDate)cloneable).setMonth(((CalendarDate)object).getMonth()).setDayOfMonth(((CalendarDate)object).getDayOfMonth());
                            }
                        } else {
                            ((CalendarDate)cloneable).setMonth(1).setDayOfMonth(1);
                        }
                        jcal.normalize((CalendarDate)cloneable);
                        l2 = jcal.getFixedDate((CalendarDate)cloneable);
                    } else {
                        l2 -= 365L;
                        if (((CalendarDate)cloneable).isLeapYear()) {
                            --l2;
                        }
                    }
                } else {
                    object = eras[JapaneseImperialCalendar.getEraIndex(this.jdate)].getSinceDate();
                    ((CalendarDate)cloneable).setMonth(((CalendarDate)object).getMonth()).setDayOfMonth(((CalendarDate)object).getDayOfMonth());
                    jcal.normalize((CalendarDate)cloneable);
                    l2 = jcal.getFixedDate((CalendarDate)cloneable);
                }
                n = this.getWeekNumber(l2, l4);
            } else if (!bl) {
                if (n4 >= 52) {
                    long l5;
                    l2 = l5 = l2 + 365L;
                    if (this.jdate.isLeapYear()) {
                        l2 = l5 + 1L;
                    }
                    l5 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l2 + 6L, this.getFirstDayOfWeek());
                    n = n4;
                    if ((int)(l5 - l2) >= this.getMinimalDaysInFirstWeek()) {
                        n = n4;
                        if (l3 >= l5 - 7L) {
                            n = 1;
                        }
                    }
                }
            } else {
                object = (LocalGregorianCalendar.Date)this.jdate.clone();
                if (this.jdate.getYear() == 1) {
                    ((LocalGregorianCalendar.Date)object).addYear(1);
                    ((CalendarDate)object).setMonth(1).setDayOfMonth(1);
                    l2 = jcal.getFixedDate((CalendarDate)object);
                } else {
                    n4 = JapaneseImperialCalendar.getEraIndex((LocalGregorianCalendar.Date)object) + 1;
                    cloneable = eras[n4].getSinceDate();
                    ((LocalGregorianCalendar.Date)object).setEra(eras[n4]);
                    ((CalendarDate)object).setDate(1, ((CalendarDate)cloneable).getMonth(), ((CalendarDate)cloneable).getDayOfMonth());
                    jcal.normalize((CalendarDate)object);
                    l2 = jcal.getFixedDate((CalendarDate)object);
                }
                long l6 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l2 + 6L, this.getFirstDayOfWeek());
                if ((int)(l6 - l2) >= this.getMinimalDaysInFirstWeek() && l3 >= l6 - 7L) {
                    n = 1;
                }
            }
            this.internalSet(3, n);
            this.internalSet(4, this.getWeekNumber(l, l3));
            n = n2 | 344;
        } else {
            n = n2;
        }
        return n;
    }

    private static LocalGregorianCalendar.Date getCalendarDate(long l) {
        CalendarDate calendarDate = jcal.newCalendarDate(TimeZone.NO_TIMEZONE);
        jcal.getCalendarDateFromFixedDate(calendarDate, l);
        return calendarDate;
    }

    private static int getEraIndex(LocalGregorianCalendar.Date object) {
        object = ((CalendarDate)object).getEra();
        for (int i = JapaneseImperialCalendar.eras.length - 1; i > 0; --i) {
            if (eras[i] != object) continue;
            return i;
        }
        return 0;
    }

    private long getFixedDate(int n, int object, int n2) {
        int n3;
        long l;
        Object object2;
        int n4;
        int n5 = object;
        int n6 = 0;
        int n7 = 1;
        if (JapaneseImperialCalendar.isFieldSet(n2, 2)) {
            n6 = this.internalGet(2);
            if (n6 > 11) {
                n4 = n5 + n6 / 12;
                object = n6 % 12;
                n3 = n7;
            } else {
                n4 = n5;
                object = n6;
                n3 = n7;
                if (n6 < 0) {
                    object2 = new int[1];
                    n4 = n5 + CalendarUtils.floorDivide(n6, 12, (int[])object2);
                    object = object2[0];
                    n3 = n7;
                }
            }
        } else {
            n4 = n5;
            object = n6;
            n3 = n7;
            if (n5 == 1) {
                n4 = n5;
                object = n6;
                n3 = n7;
                if (n != 0) {
                    object2 = eras[n].getSinceDate();
                    object = ((CalendarDate)object2).getMonth() - 1;
                    n3 = ((CalendarDate)object2).getDayOfMonth();
                    n4 = n5;
                }
            }
        }
        n7 = object;
        n6 = n3;
        if (n4 == MIN_VALUES[1]) {
            object2 = jcal.getCalendarDate(Long.MIN_VALUE, this.getZone());
            int n8 = ((CalendarDate)object2).getMonth() - 1;
            n5 = object;
            if (object < n8) {
                n5 = n8;
            }
            n7 = n5;
            n6 = n3;
            if (n5 == n8) {
                n6 = ((CalendarDate)object2).getDayOfMonth();
                n7 = n5;
            }
        }
        CalendarDate calendarDate = jcal.newCalendarDate(TimeZone.NO_TIMEZONE);
        object2 = n > 0 ? eras[n] : null;
        ((LocalGregorianCalendar.Date)calendarDate).setEra((Era)object2);
        calendarDate.setDate(n4, n7 + 1, n6);
        jcal.normalize(calendarDate);
        long l2 = jcal.getFixedDate(calendarDate);
        if (JapaneseImperialCalendar.isFieldSet(n2, 2)) {
            if (JapaneseImperialCalendar.isFieldSet(n2, 5)) {
                l = l2;
                if (this.isSet(5)) {
                    l = l2 + (long)this.internalGet(5) - (long)n6;
                }
            } else if (JapaneseImperialCalendar.isFieldSet(n2, 4)) {
                long l3;
                l = l3 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l2 + 6L, this.getFirstDayOfWeek());
                if (l3 - l2 >= (long)this.getMinimalDaysInFirstWeek()) {
                    l = l3 - 7L;
                }
                l2 = l;
                if (JapaneseImperialCalendar.isFieldSet(n2, 7)) {
                    l2 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(6L + l, this.internalGet(7));
                }
                l = l2 + (long)((this.internalGet(4) - 1) * 7);
            } else {
                n = JapaneseImperialCalendar.isFieldSet(n2, 7) ? this.internalGet(7) : this.getFirstDayOfWeek();
                object = JapaneseImperialCalendar.isFieldSet(n2, 8) ? this.internalGet(8) : 1;
                l = object >= 0 ? LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l2 + (long)(object * 7) - 1L, n) : LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l2 + (long)(this.monthLength(n7, n4) + (object + 1) * 7) - 1L, n);
            }
        } else if (JapaneseImperialCalendar.isFieldSet(n2, 6)) {
            if (this.isTransitionYear(((LocalGregorianCalendar.Date)calendarDate).getNormalizedYear())) {
                l2 = this.getFixedDateJan1((LocalGregorianCalendar.Date)calendarDate, l2);
            }
            l = l2 + (long)this.internalGet(6) - 1L;
        } else {
            long l4;
            l = l4 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l2 + 6L, this.getFirstDayOfWeek());
            if (l4 - l2 >= (long)this.getMinimalDaysInFirstWeek()) {
                l = l4 - 7L;
            }
            l2 = l;
            if (JapaneseImperialCalendar.isFieldSet(n2, 7)) {
                n = this.internalGet(7);
                l2 = l;
                if (n != this.getFirstDayOfWeek()) {
                    l2 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(6L + l, n);
                }
            }
            l = l2 + ((long)this.internalGet(3) - 1L) * 7L;
        }
        return l;
    }

    private long getFixedDateJan1(LocalGregorianCalendar.Date date, long l) {
        CalendarDate calendarDate;
        date.getEra();
        if (date.getEra() != null && date.getYear() == 1) {
            for (int i = JapaneseImperialCalendar.getEraIndex((LocalGregorianCalendar.Date)date); i > 0; --i) {
                calendarDate = eras[i].getSinceDate();
                long l2 = gcal.getFixedDate(calendarDate);
                if (l2 > l) {
                    continue;
                }
                return l2;
            }
        }
        calendarDate = gcal.newCalendarDate(TimeZone.NO_TIMEZONE);
        calendarDate.setDate(date.getNormalizedYear(), 1, 1);
        return gcal.getFixedDate(calendarDate);
    }

    private long getFixedDateMonth1(LocalGregorianCalendar.Date date, long l) {
        long l2;
        int n = JapaneseImperialCalendar.getTransitionEraIndex(date);
        if (n != -1 && (l2 = sinceFixedDates[n]) <= l) {
            return l2;
        }
        return l - (long)date.getDayOfMonth() + 1L;
    }

    private JapaneseImperialCalendar getNormalizedCalendar() {
        JapaneseImperialCalendar japaneseImperialCalendar;
        if (this.isFullyNormalized()) {
            japaneseImperialCalendar = this;
        } else {
            japaneseImperialCalendar = (JapaneseImperialCalendar)this.clone();
            japaneseImperialCalendar.setLenient(true);
            japaneseImperialCalendar.complete();
        }
        return japaneseImperialCalendar;
    }

    private static int getRolledValue(int n, int n2, int n3, int n4) {
        int n5 = n4 - n3 + 1;
        if ((n2 = n + n2 % n5) > n4) {
            n = n2 - n5;
        } else {
            n = n2;
            if (n2 < n3) {
                n = n2 + n5;
            }
        }
        return n;
    }

    private static int getTransitionEraIndex(LocalGregorianCalendar.Date date) {
        int n = JapaneseImperialCalendar.getEraIndex(date);
        Object object = eras[n].getSinceDate();
        if (((CalendarDate)object).getYear() == date.getNormalizedYear() && ((CalendarDate)object).getMonth() == date.getMonth()) {
            return n;
        }
        object = eras;
        if (n < ((Era[])object).length - 1 && ((CalendarDate)(object = object[++n].getSinceDate())).getYear() == date.getNormalizedYear() && ((CalendarDate)object).getMonth() == date.getMonth()) {
            return n;
        }
        return -1;
    }

    private int getWeekNumber(long l, long l2) {
        long l3 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(6L + l, this.getFirstDayOfWeek());
        int n = (int)(l3 - l);
        l = l3;
        if (n >= this.getMinimalDaysInFirstWeek()) {
            l = l3 - 7L;
        }
        if ((n = (int)(l2 - l)) >= 0) {
            return n / 7 + 1;
        }
        return CalendarUtils.floorDivide(n, 7) + 1;
    }

    private long getYearOffsetInMillis(CalendarDate calendarDate) {
        long l = jcal.getDayOfYear(calendarDate);
        return calendarDate.getTimeOfDay() + (l - 1L) * 86400000L - (long)calendarDate.getZoneOffset();
    }

    private int internalGetEra() {
        int n = this.isSet(0) ? this.internalGet(0) : currentEra;
        return n;
    }

    private boolean isTransitionYear(int n) {
        for (int i = JapaneseImperialCalendar.eras.length - 1; i > 0; --i) {
            int n2 = eras[i].getSinceDate().getYear();
            if (n == n2) {
                return true;
            }
            if (n > n2) break;
        }
        return false;
    }

    private int monthLength(int n) {
        n = this.jdate.isLeapYear() ? GregorianCalendar.LEAP_MONTH_LENGTH[n] : GregorianCalendar.MONTH_LENGTH[n];
        return n;
    }

    private int monthLength(int n, int n2) {
        n = CalendarUtils.isGregorianLeapYear(n2) ? GregorianCalendar.LEAP_MONTH_LENGTH[n] : GregorianCalendar.MONTH_LENGTH[n];
        return n;
    }

    private void pinDayOfMonth(LocalGregorianCalendar.Date date) {
        int n = date.getYear();
        int n2 = date.getDayOfMonth();
        if (n != this.getMinimum(1)) {
            date.setDayOfMonth(1);
            jcal.normalize(date);
            n = jcal.getMonthLength(date);
            if (n2 > n) {
                date.setDayOfMonth(n);
            } else {
                date.setDayOfMonth(n2);
            }
            jcal.normalize(date);
        } else {
            CalendarDate calendarDate = jcal.getCalendarDate(Long.MIN_VALUE, this.getZone());
            CalendarDate calendarDate2 = jcal.getCalendarDate(this.time, this.getZone());
            long l = calendarDate2.getTimeOfDay();
            ((LocalGregorianCalendar.Date)calendarDate2).addYear(400);
            calendarDate2.setMonth(date.getMonth());
            calendarDate2.setDayOfMonth(1);
            jcal.normalize(calendarDate2);
            int n3 = jcal.getMonthLength(calendarDate2);
            if (n2 > n3) {
                calendarDate2.setDayOfMonth(n3);
            } else if (n2 < calendarDate.getDayOfMonth()) {
                calendarDate2.setDayOfMonth(calendarDate.getDayOfMonth());
            } else {
                calendarDate2.setDayOfMonth(n2);
            }
            if (calendarDate2.getDayOfMonth() == calendarDate.getDayOfMonth() && l < calendarDate.getTimeOfDay()) {
                calendarDate2.setDayOfMonth(Math.min(n2 + 1, n3));
            }
            date.setDate(n, calendarDate2.getMonth(), calendarDate2.getDayOfMonth());
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.jdate == null) {
            this.jdate = jcal.newCalendarDate(this.getZone());
            this.cachedFixedDate = Long.MIN_VALUE;
        }
    }

    @Override
    public void add(int n, int n2) {
        if (n2 == 0) {
            return;
        }
        if (n >= 0 && n < 15) {
            this.complete();
            if (n == 1) {
                LocalGregorianCalendar.Date date = (LocalGregorianCalendar.Date)this.jdate.clone();
                date.addYear(n2);
                this.pinDayOfMonth(date);
                this.set(0, JapaneseImperialCalendar.getEraIndex(date));
                this.set(1, date.getYear());
                this.set(2, date.getMonth() - 1);
                this.set(5, date.getDayOfMonth());
            } else if (n == 2) {
                LocalGregorianCalendar.Date date = (LocalGregorianCalendar.Date)this.jdate.clone();
                date.addMonth(n2);
                this.pinDayOfMonth(date);
                this.set(0, JapaneseImperialCalendar.getEraIndex(date));
                this.set(1, date.getYear());
                this.set(2, date.getMonth() - 1);
                this.set(5, date.getDayOfMonth());
            } else if (n == 0) {
                n2 = this.internalGet(0) + n2;
                if (n2 < 0) {
                    n = 0;
                } else {
                    Era[] arrera = eras;
                    n = n2;
                    if (n2 > arrera.length - 1) {
                        n = arrera.length - 1;
                    }
                }
                this.set(0, n);
            } else {
                long l;
                long l2 = n2;
                long l3 = 0L;
                switch (n) {
                    default: {
                        break;
                    }
                    case 14: {
                        break;
                    }
                    case 13: {
                        l2 *= 1000L;
                        break;
                    }
                    case 12: {
                        l2 *= 60000L;
                        break;
                    }
                    case 10: 
                    case 11: {
                        l2 *= 3600000L;
                        break;
                    }
                    case 9: {
                        l2 = n2 / 2;
                        l3 = n2 % 2 * 12;
                        break;
                    }
                    case 5: 
                    case 6: 
                    case 7: {
                        break;
                    }
                    case 3: 
                    case 4: 
                    case 8: {
                        l2 *= 7L;
                    }
                }
                if (n >= 10) {
                    this.setTimeInMillis(this.time + l2);
                    return;
                }
                long l4 = this.cachedFixedDate;
                long l5 = (((l3 + (long)this.internalGet(11)) * 60L + (long)this.internalGet(12)) * 60L + (long)this.internalGet(13)) * 1000L + (long)this.internalGet(14);
                if (l5 >= 86400000L) {
                    l = l4 + 1L;
                    l3 = l5 - 86400000L;
                } else {
                    l3 = l5;
                    l = l4;
                    if (l5 < 0L) {
                        l = l4 - 1L;
                        l3 = l5 + 86400000L;
                    }
                }
                l2 = l + l2;
                n = this.internalGet(15) + this.internalGet(16);
                this.setTimeInMillis((l2 - 719163L) * 86400000L + l3 - (long)n);
                if ((n -= this.internalGet(15) + this.internalGet(16)) != 0) {
                    this.setTimeInMillis(this.time + (long)n);
                    if (this.cachedFixedDate != l2) {
                        this.setTimeInMillis(this.time - (long)n);
                    }
                }
            }
            return;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Object clone() {
        JapaneseImperialCalendar japaneseImperialCalendar = (JapaneseImperialCalendar)super.clone();
        japaneseImperialCalendar.jdate = (LocalGregorianCalendar.Date)this.jdate.clone();
        japaneseImperialCalendar.originalFields = null;
        japaneseImperialCalendar.zoneOffsets = null;
        return japaneseImperialCalendar;
    }

    @Override
    protected void computeFields() {
        int n;
        block4 : {
            block2 : {
                int n2;
                int n3;
                block3 : {
                    if (!this.isPartiallyNormalized()) break block2;
                    n2 = this.getSetStateFields();
                    n3 = n2 & 131071;
                    if (n3 != 0) break block3;
                    n = n2;
                    if (this.cachedFixedDate != Long.MIN_VALUE) break block4;
                }
                n = n2 | this.computeFields(n3, 98304 & n2);
                break block4;
            }
            n = 131071;
            this.computeFields(131071, 0);
        }
        this.setFieldsComputed(n);
    }

    @Override
    protected void computeTime() {
        int n;
        long l;
        long l2;
        int n2;
        if (!this.isLenient()) {
            if (this.originalFields == null) {
                this.originalFields = new int[17];
            }
            for (n = 0; n < 17; ++n) {
                n2 = this.internalGet(n);
                if (this.isExternallySet(n) && (n2 < this.getMinimum(n) || n2 > this.getMaximum(n))) {
                    throw new IllegalArgumentException(JapaneseImperialCalendar.getFieldName(n));
                }
                this.originalFields[n] = n2;
            }
        }
        int n3 = this.selectFields();
        if (this.isSet(0)) {
            n = this.internalGet(0);
            n2 = this.isSet(1) ? this.internalGet(1) : 1;
        } else if (this.isSet(1)) {
            n = currentEra;
            n2 = this.internalGet(1);
        } else {
            n = 3;
            n2 = 45;
        }
        if (JapaneseImperialCalendar.isFieldSet(n3, 11)) {
            l = 0L + (long)this.internalGet(11);
        } else {
            l = l2 = 0L + (long)this.internalGet(10);
            if (JapaneseImperialCalendar.isFieldSet(n3, 9)) {
                l = l2 + (long)(this.internalGet(9) * 12);
            }
        }
        l2 = ((l * 60L + (long)this.internalGet(12)) * 60L + (long)this.internalGet(13)) * 1000L + (long)this.internalGet(14);
        l = l2 / 86400000L;
        l2 %= 86400000L;
        while (l2 < 0L) {
            l2 += 86400000L;
            --l;
        }
        l = (l + this.getFixedDate(n, n2, n3) - 719163L) * 86400000L + l2;
        Object object = this.getZone();
        if (this.zoneOffsets == null) {
            this.zoneOffsets = new int[2];
        }
        if ((n = n3 & 98304) != 98304) {
            ((TimeZone)object).getOffsets(l - (long)((TimeZone)object).getRawOffset(), this.zoneOffsets);
        }
        if (n != 0) {
            if (JapaneseImperialCalendar.isFieldSet(n, 15)) {
                this.zoneOffsets[0] = this.internalGet(15);
            }
            if (JapaneseImperialCalendar.isFieldSet(n, 16)) {
                this.zoneOffsets[1] = this.internalGet(16);
            }
        }
        object = this.zoneOffsets;
        this.time = l - (long)(object[0] + object[1]);
        n2 = this.computeFields(this.getSetStateFields() | n3, n);
        if (!this.isLenient()) {
            for (n = 0; n < 17; ++n) {
                if (!this.isExternallySet(n) || this.originalFields[n] == this.internalGet(n)) {
                    continue;
                }
                n2 = this.internalGet(n);
                System.arraycopy((Object)this.originalFields, 0, (Object)this.fields, 0, this.fields.length);
                object = new StringBuilder();
                ((StringBuilder)object).append(JapaneseImperialCalendar.getFieldName(n));
                ((StringBuilder)object).append("=");
                ((StringBuilder)object).append(n2);
                ((StringBuilder)object).append(", expected ");
                ((StringBuilder)object).append(this.originalFields[n]);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
        }
        this.setFieldsNormalized(n2);
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = object instanceof JapaneseImperialCalendar && super.equals(object);
        return bl;
    }

    @Override
    public int getActualMaximum(int n) {
        if ((1 << n & 130689) != 0) {
            return this.getMaximum(n);
        }
        Cloneable cloneable = this.getNormalizedCalendar();
        Cloneable cloneable2 = ((JapaneseImperialCalendar)cloneable).jdate;
        ((LocalGregorianCalendar.Date)cloneable2).getNormalizedYear();
        switch (n) {
            default: {
                throw new ArrayIndexOutOfBoundsException(n);
            }
            case 8: {
                int n2;
                n = ((CalendarDate)cloneable2).getDayOfWeek();
                cloneable2 = (BaseCalendar.Date)((CalendarDate)cloneable2).clone();
                int n3 = jcal.getMonthLength((CalendarDate)cloneable2);
                ((CalendarDate)cloneable2).setDayOfMonth(1);
                jcal.normalize((CalendarDate)cloneable2);
                n = n2 = n - ((CalendarDate)cloneable2).getDayOfWeek();
                if (n2 < 0) {
                    n = n2 + 7;
                }
                n = (n3 - n + 6) / 7;
                break;
            }
            case 6: {
                if (this.isTransitionYear(((LocalGregorianCalendar.Date)cloneable2).getNormalizedYear())) {
                    int n4;
                    n = n4 = JapaneseImperialCalendar.getEraIndex((LocalGregorianCalendar.Date)cloneable2);
                    if (((CalendarDate)cloneable2).getYear() != 1) {
                        n = n4 + 1;
                    }
                    long l = sinceFixedDates[n];
                    long l2 = ((JapaneseImperialCalendar)cloneable).cachedFixedDate;
                    cloneable = gcal.newCalendarDate(TimeZone.NO_TIMEZONE);
                    ((CalendarDate)cloneable).setDate(((LocalGregorianCalendar.Date)cloneable2).getNormalizedYear(), 1, 1);
                    if (l2 < l) {
                        n = (int)(l - gcal.getFixedDate((CalendarDate)cloneable));
                        break;
                    }
                    ((CalendarDate)cloneable).addYear(1);
                    n = (int)(gcal.getFixedDate((CalendarDate)cloneable) - l);
                    break;
                }
                cloneable = jcal.getCalendarDate(Long.MAX_VALUE, this.getZone());
                if (((CalendarDate)cloneable2).getEra() == ((CalendarDate)cloneable).getEra() && ((CalendarDate)cloneable2).getYear() == ((CalendarDate)cloneable).getYear()) {
                    long l = jcal.getFixedDate((CalendarDate)cloneable);
                    n = (int)(l - this.getFixedDateJan1((LocalGregorianCalendar.Date)cloneable, l)) + 1;
                    break;
                }
                if (((CalendarDate)cloneable2).getYear() == this.getMinimum(1)) {
                    cloneable2 = jcal.getCalendarDate(Long.MIN_VALUE, this.getZone());
                    long l = jcal.getFixedDate((CalendarDate)cloneable2);
                    ((CalendarDate)cloneable2).addYear(1);
                    ((CalendarDate)cloneable2).setMonth(1).setDayOfMonth(1);
                    jcal.normalize((CalendarDate)cloneable2);
                    n = (int)(jcal.getFixedDate((CalendarDate)cloneable2) - l);
                    break;
                }
                n = jcal.getYearLength((CalendarDate)cloneable2);
                break;
            }
            case 5: {
                n = jcal.getMonthLength((CalendarDate)cloneable2);
                break;
            }
            case 4: {
                int n5;
                cloneable = jcal.getCalendarDate(Long.MAX_VALUE, this.getZone());
                if (((CalendarDate)cloneable2).getEra() == ((CalendarDate)cloneable).getEra() && ((CalendarDate)cloneable2).getYear() == ((CalendarDate)cloneable).getYear()) {
                    long l = jcal.getFixedDate((CalendarDate)cloneable);
                    n = this.getWeekNumber(l - (long)((CalendarDate)cloneable).getDayOfMonth() + 1L, l);
                    break;
                }
                cloneable = gcal.newCalendarDate(TimeZone.NO_TIMEZONE);
                ((CalendarDate)cloneable).setDate(((LocalGregorianCalendar.Date)cloneable2).getNormalizedYear(), ((CalendarDate)cloneable2).getMonth(), 1);
                n = gcal.getDayOfWeek((CalendarDate)cloneable);
                int n6 = gcal.getMonthLength((CalendarDate)cloneable);
                n = n5 = n - this.getFirstDayOfWeek();
                if (n5 < 0) {
                    n = n5 + 7;
                }
                n = 7 - n;
                n5 = 3;
                if (n >= this.getMinimalDaysInFirstWeek()) {
                    n5 = 3 + 1;
                }
                n6 -= n + 21;
                n = n5++;
                if (n6 <= 0) break;
                n = n5;
                if (n6 <= 7) break;
                n = n5 + 1;
                break;
            }
            case 3: {
                if (!this.isTransitionYear(((LocalGregorianCalendar.Date)cloneable2).getNormalizedYear())) {
                    int n7;
                    cloneable = jcal.getCalendarDate(Long.MAX_VALUE, this.getZone());
                    if (((CalendarDate)cloneable2).getEra() == ((CalendarDate)cloneable).getEra() && ((CalendarDate)cloneable2).getYear() == ((CalendarDate)cloneable).getYear()) {
                        long l = jcal.getFixedDate((CalendarDate)cloneable);
                        n = this.getWeekNumber(this.getFixedDateJan1((LocalGregorianCalendar.Date)cloneable, l), l);
                        break;
                    }
                    if (((CalendarDate)cloneable2).getEra() == null && ((CalendarDate)cloneable2).getYear() == this.getMinimum(1)) {
                        long l;
                        cloneable2 = jcal.getCalendarDate(Long.MIN_VALUE, this.getZone());
                        ((CalendarDate)cloneable2).addYear(400);
                        jcal.normalize((CalendarDate)cloneable2);
                        ((LocalGregorianCalendar.Date)cloneable).setEra(((CalendarDate)cloneable2).getEra());
                        ((CalendarDate)cloneable).setDate(((CalendarDate)cloneable2).getYear() + 1, 1, 1);
                        jcal.normalize((CalendarDate)cloneable);
                        long l3 = jcal.getFixedDate((CalendarDate)cloneable2);
                        long l4 = jcal.getFixedDate((CalendarDate)cloneable);
                        long l5 = l = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(6L + l4, this.getFirstDayOfWeek());
                        if ((int)(l - l4) >= this.getMinimalDaysInFirstWeek()) {
                            l5 = l - 7L;
                        }
                        n = this.getWeekNumber(l3, l5);
                        break;
                    }
                    cloneable = gcal.newCalendarDate(TimeZone.NO_TIMEZONE);
                    ((CalendarDate)cloneable).setDate(((LocalGregorianCalendar.Date)cloneable2).getNormalizedYear(), 1, 1);
                    n = n7 = gcal.getDayOfWeek((CalendarDate)cloneable) - this.getFirstDayOfWeek();
                    if (n7 < 0) {
                        n = n7 + 7;
                    }
                    if ((n = this.getMinimalDaysInFirstWeek() + n - 1) != 6 && (!((CalendarDate)cloneable2).isLeapYear() || n != 5 && n != 12)) {
                        n = 52;
                        break;
                    }
                    n = 52 + 1;
                    break;
                }
                cloneable2 = cloneable;
                if (cloneable == this) {
                    cloneable2 = (JapaneseImperialCalendar)((JapaneseImperialCalendar)cloneable).clone();
                }
                int n8 = this.getActualMaximum(6);
                ((Calendar)cloneable2).set(6, n8);
                n = ((Calendar)cloneable2).get(3);
                if (n != 1 || n8 <= 7) break;
                ((JapaneseImperialCalendar)cloneable2).add(3, -1);
                n = ((Calendar)cloneable2).get(3);
                break;
            }
            case 2: {
                if (this.isTransitionYear(((LocalGregorianCalendar.Date)cloneable2).getNormalizedYear())) {
                    int n9;
                    long l;
                    n = n9 = JapaneseImperialCalendar.getEraIndex((LocalGregorianCalendar.Date)cloneable2);
                    if (((CalendarDate)cloneable2).getYear() != 1) {
                        n = n9 + 1;
                    }
                    if (((JapaneseImperialCalendar)cloneable).cachedFixedDate < (l = sinceFixedDates[n])) {
                        cloneable2 = (LocalGregorianCalendar.Date)((CalendarDate)cloneable2).clone();
                        jcal.getCalendarDateFromFixedDate((CalendarDate)cloneable2, l - 1L);
                        n = ((CalendarDate)cloneable2).getMonth() - 1;
                        break;
                    }
                    n = 11;
                    break;
                }
                cloneable = jcal.getCalendarDate(Long.MAX_VALUE, this.getZone());
                if (((CalendarDate)cloneable2).getEra() == ((CalendarDate)cloneable).getEra() && ((CalendarDate)cloneable2).getYear() == ((CalendarDate)cloneable).getYear()) {
                    n = ((CalendarDate)cloneable).getMonth() - 1;
                    break;
                }
                n = 11;
                break;
            }
            case 1: {
                CalendarDate calendarDate = jcal.getCalendarDate(((Calendar)cloneable).getTimeInMillis(), this.getZone());
                n = JapaneseImperialCalendar.getEraIndex((LocalGregorianCalendar.Date)cloneable2);
                cloneable2 = eras;
                if (n == ((Cloneable)cloneable2).length - 1) {
                    cloneable = jcal.getCalendarDate(Long.MAX_VALUE, this.getZone());
                    int n10 = ((CalendarDate)cloneable).getYear();
                    cloneable2 = cloneable;
                    n = n10;
                    if (n10 > 400) {
                        calendarDate.setYear(n10 - 400);
                        cloneable2 = cloneable;
                        n = n10;
                    }
                } else {
                    cloneable2 = jcal.getCalendarDate(((Era)((Object)cloneable2[n + 1])).getSince(this.getZone()) - 1L, this.getZone());
                    n = ((CalendarDate)cloneable2).getYear();
                    calendarDate.setYear(n);
                }
                jcal.normalize(calendarDate);
                if (this.getYearOffsetInMillis(calendarDate) <= this.getYearOffsetInMillis((CalendarDate)cloneable2)) break;
                --n;
                break;
            }
        }
        return n;
    }

    @Override
    public int getActualMinimum(int n) {
        block14 : {
            int n2;
            Cloneable cloneable;
            int n3;
            block11 : {
                block12 : {
                    block15 : {
                        block13 : {
                            if (!JapaneseImperialCalendar.isFieldSet(14, n)) {
                                return this.getMinimum(n);
                            }
                            n3 = 0;
                            cloneable = this.getNormalizedCalendar();
                            cloneable = jcal.getCalendarDate(((Calendar)cloneable).getTimeInMillis(), this.getZone());
                            n2 = JapaneseImperialCalendar.getEraIndex((LocalGregorianCalendar.Date)cloneable);
                            if (n == 1) break block11;
                            if (n == 2) break block12;
                            if (n == 3) break block13;
                            n = n3;
                            break block14;
                        }
                        n3 = 1;
                        CalendarDate calendarDate = jcal.getCalendarDate(Long.MIN_VALUE, this.getZone());
                        calendarDate.addYear(400);
                        jcal.normalize(calendarDate);
                        ((LocalGregorianCalendar.Date)cloneable).setEra(calendarDate.getEra());
                        ((LocalGregorianCalendar.Date)cloneable).setYear(calendarDate.getYear());
                        jcal.normalize((CalendarDate)cloneable);
                        long l = jcal.getFixedDate(calendarDate);
                        long l2 = jcal.getFixedDate((CalendarDate)cloneable);
                        l2 -= (long)((this.getWeekNumber(l, l2) - 1) * 7);
                        if (l2 < l) break block15;
                        n = n3;
                        if (l2 != l) break block14;
                        n = n3;
                        if (((CalendarDate)cloneable).getTimeOfDay() >= calendarDate.getTimeOfDay()) break block14;
                    }
                    n = 1 + 1;
                    break block14;
                }
                n = n3;
                if (n2 > 1) {
                    n = n3;
                    if (((CalendarDate)cloneable).getYear() == 1) {
                        long l = eras[n2].getSince(this.getZone());
                        CalendarDate calendarDate = jcal.getCalendarDate(l, this.getZone());
                        n = calendarDate.getMonth() - 1;
                        if (((CalendarDate)cloneable).getDayOfMonth() < calendarDate.getDayOfMonth()) {
                            ++n;
                        }
                    }
                }
                break block14;
            }
            if (n2 > 0) {
                n = 1;
                long l = eras[n2].getSince(this.getZone());
                CalendarDate calendarDate = jcal.getCalendarDate(l, this.getZone());
                ((LocalGregorianCalendar.Date)cloneable).setYear(calendarDate.getYear());
                jcal.normalize((CalendarDate)cloneable);
                if (this.getYearOffsetInMillis((CalendarDate)cloneable) < this.getYearOffsetInMillis(calendarDate)) {
                    n = 1 + 1;
                }
            } else {
                n3 = this.getMinimum(n);
                CalendarDate calendarDate = jcal.getCalendarDate(Long.MIN_VALUE, this.getZone());
                n = n2 = calendarDate.getYear();
                if (n2 > 400) {
                    n = n2 - 400;
                }
                ((LocalGregorianCalendar.Date)cloneable).setYear(n);
                jcal.normalize((CalendarDate)cloneable);
                n = n3;
                if (this.getYearOffsetInMillis((CalendarDate)cloneable) < this.getYearOffsetInMillis(calendarDate)) {
                    n = n3 + 1;
                }
            }
        }
        return n;
    }

    @Override
    public String getCalendarType() {
        return "japanese";
    }

    @Override
    public String getDisplayName(int n, int n2, Locale object) {
        if (!this.checkDisplayNameParams(n, n2, 1, 4, (Locale)object, 647)) {
            return null;
        }
        int n3 = this.get(n);
        if (n == 1 && (this.getBaseStyle(n2) != 2 || n3 != 1 || this.get(0) == 0)) {
            return null;
        }
        String string = CalendarDataUtility.retrieveFieldValueName(this.getCalendarType(), n, n3, n2, (Locale)object);
        object = string;
        if (string == null) {
            object = string;
            if (n == 0) {
                Era[] arrera = eras;
                object = string;
                if (n3 < arrera.length) {
                    object = arrera[n3];
                    object = n2 == 1 ? ((Era)object).getAbbreviation() : ((Era)object).getName();
                }
            }
        }
        return object;
    }

    @Override
    public Map<String, Integer> getDisplayNames(int n, int n2, Locale map) {
        if (!this.checkDisplayNameParams(n, n2, 0, 4, (Locale)((Object)map), 647)) {
            return null;
        }
        map = CalendarDataUtility.retrieveFieldValueNames(this.getCalendarType(), n, n2, (Locale)((Object)map));
        if (map != null && n == 0) {
            Object object;
            n = map.size();
            if (n2 == 0) {
                object = new HashSet();
                Iterator iterator = map.keySet().iterator();
                while (iterator.hasNext()) {
                    object.add((Integer)map.get((String)iterator.next()));
                }
                n = object.size();
            }
            if (n < eras.length) {
                n2 = this.getBaseStyle(n2);
                while (n < ((Era[])(object = eras)).length) {
                    object = object[n];
                    if (n2 == 0 || n2 == 1 || n2 == 4) {
                        map.put(((Era)object).getAbbreviation(), n);
                    }
                    if (n2 == 0 || n2 == 2) {
                        map.put(((Era)object).getName(), n);
                    }
                    ++n;
                }
            }
        }
        return map;
    }

    @Override
    public int getGreatestMinimum(int n) {
        int n2 = 1;
        n = n == 1 ? n2 : MIN_VALUES[n];
        return n;
    }

    @Override
    public int getLeastMaximum(int n) {
        if (n != 1) {
            return LEAST_MAX_VALUES[n];
        }
        return Math.min(LEAST_MAX_VALUES[1], this.getMaximum(1));
    }

    @Override
    public int getMaximum(int n) {
        if (n != 1) {
            return MAX_VALUES[n];
        }
        CalendarDate calendarDate = jcal.getCalendarDate(Long.MAX_VALUE, this.getZone());
        return Math.max(LEAST_MAX_VALUES[1], calendarDate.getYear());
    }

    @Override
    public int getMinimum(int n) {
        return MIN_VALUES[n];
    }

    @Override
    public TimeZone getTimeZone() {
        TimeZone timeZone = super.getTimeZone();
        this.jdate.setZone(timeZone);
        return timeZone;
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ this.jdate.hashCode();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void roll(int var1_1, int var2_2) {
        if (var2_2 == 0) {
            return;
        }
        if (var1_1 < 0) throw new IllegalArgumentException();
        if (var1_1 >= 15) throw new IllegalArgumentException();
        this.complete();
        var3_3 = this.getMinimum(var1_1);
        var4_4 = this.getMaximum(var1_1);
        switch (var1_1) {
            default: {
                ** GOTO lbl302
            }
            case 10: 
            case 11: {
                var5_5 = var4_4 + 1;
                var4_4 = this.internalGet(var1_1);
                var2_2 = var3_3 = (var4_4 + var2_2) % var5_5;
                if (var3_3 < 0) {
                    var2_2 = var3_3 + var5_5;
                }
                this.time += (long)((var2_2 - var4_4) * 3600000);
                var6_10 = JapaneseImperialCalendar.jcal.getCalendarDate(this.time, this.getZone());
                if (this.internalGet(5) != var6_10.getDayOfMonth()) {
                    var6_10.setEra(this.jdate.getEra());
                    var6_10.setDate(this.internalGet(1), this.internalGet(2) + 1, this.internalGet(5));
                    if (var1_1 == 10) {
                        var6_10.addHours(12);
                    }
                    this.time = JapaneseImperialCalendar.jcal.getTime(var6_10);
                }
                var2_2 = var6_10.getHours();
                this.internalSet(var1_1, var2_2 % var5_5);
                if (var1_1 == 10) {
                    this.internalSet(11, var2_2);
                } else {
                    this.internalSet(9, var2_2 / 12);
                    this.internalSet(10, var2_2 % 12);
                }
                var1_1 = var6_10.getZoneOffset();
                var2_2 = var6_10.getDaylightSaving();
                this.internalSet(15, var1_1 - var2_2);
                this.internalSet(16, var2_2);
                return;
            }
            case 8: {
                if (!this.isTransitionYear(this.jdate.getNormalizedYear())) {
                    var4_4 = this.internalGet(5);
                    var7_19 = JapaneseImperialCalendar.jcal.getMonthLength(this.jdate);
                    var3_3 = var5_6 = var7_19 / 7;
                    if ((var4_4 - 1) % 7 < var7_19 % 7) {
                        var3_3 = var5_6 + 1;
                    }
                    this.set(7, this.internalGet(7));
                    var5_6 = 1;
                    break;
                }
                var8_23 = this.cachedFixedDate;
                var10_29 = this.getFixedDateMonth1(this.jdate, var8_23);
                var7_20 = this.actualMonthLength();
                var5_7 = var7_20 / 7;
                var4_4 = (int)(var8_23 - var10_29) % 7;
                var3_3 = var5_7;
                if (var4_4 < var7_20 % 7) {
                    var3_3 = var5_7 + 1;
                }
                this.set(5, JapaneseImperialCalendar.getCalendarDate((long)((JapaneseImperialCalendar.getRolledValue(this.internalGet(var1_1), var2_2, 1, var3_3) - 1) * 7) + var10_29 + (long)var4_4).getDayOfMonth());
                return;
            }
            case 7: {
                var5_6 = this.jdate.getNormalizedYear();
                if (!this.isTransitionYear(var5_6) && !this.isTransitionYear(var5_6 - 1) && (var5_6 = this.internalGet(3)) > 1 && var5_6 < 52) {
                    this.set(3, this.internalGet(3));
                    var4_4 = 7;
                    var5_6 = var3_3;
                    var3_3 = var4_4;
                    break;
                }
                var1_1 = var2_2 % 7;
                if (var1_1 == 0) {
                    return;
                }
                var10_30 = this.cachedFixedDate;
                var8_24 = var10_30 + (long)var1_1;
                var12_36 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(var10_30, this.getFirstDayOfWeek());
                if (var8_24 < var12_36) {
                    var10_30 = var8_24 + 7L;
                } else {
                    var10_30 = var8_24;
                    if (var8_24 >= var12_36 + 7L) {
                        var10_30 = var8_24 - 7L;
                    }
                }
                var6_11 = JapaneseImperialCalendar.getCalendarDate(var10_30);
                this.set(0, JapaneseImperialCalendar.getEraIndex(var6_11));
                this.set(var6_11.getYear(), var6_11.getMonth() - 1, var6_11.getDayOfMonth());
                return;
            }
            case 6: {
                var4_4 = this.getActualMaximum(var1_1);
                if (this.isTransitionYear(this.jdate.getNormalizedYear())) {
                    var1_1 = JapaneseImperialCalendar.getRolledValue(this.internalGet(6), var2_2, var3_3, var4_4);
                    var8_25 = this.cachedFixedDate;
                    var10_31 = this.internalGet(6);
                    var6_12 = JapaneseImperialCalendar.getCalendarDate((long)var1_1 + (var8_25 - var10_31));
                    this.set(2, var6_12.getMonth() - 1);
                    this.set(5, var6_12.getDayOfMonth());
                    return;
                }
                var5_6 = var3_3;
                var3_3 = var4_4;
                break;
            }
            case 5: {
                var5_6 = var3_3;
                if (this.isTransitionYear(this.jdate.getNormalizedYear())) {
                    var10_32 = this.getFixedDateMonth1(this.jdate, this.cachedFixedDate);
                    var6_13 = JapaneseImperialCalendar.getCalendarDate((long)JapaneseImperialCalendar.getRolledValue((int)(this.cachedFixedDate - var10_32), var2_2, 0, this.actualMonthLength() - 1) + var10_32);
                    this.set(5, var6_13.getDayOfMonth());
                    return;
                }
                var3_3 = JapaneseImperialCalendar.jcal.getMonthLength(this.jdate);
                break;
            }
            case 4: {
                var14_39 = this.isTransitionYear(this.jdate.getNormalizedYear());
                var3_3 = var5_8 = this.internalGet(7) - this.getFirstDayOfWeek();
                if (var5_8 < 0) {
                    var3_3 = var5_8 + 7;
                }
                var8_26 = this.cachedFixedDate;
                if (var14_39) {
                    var10_33 = this.getFixedDateMonth1(this.jdate, var8_26);
                    var5_8 = this.actualMonthLength();
                } else {
                    var10_33 = this.internalGet(5);
                    var5_8 = JapaneseImperialCalendar.jcal.getMonthLength(this.jdate);
                    var10_33 = var8_26 - var10_33 + 1L;
                }
                var8_26 = var12_37 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(var10_33 + 6L, this.getFirstDayOfWeek());
                if ((int)(var12_37 - var10_33) >= this.getMinimalDaysInFirstWeek()) {
                    var8_26 = var12_37 - 7L;
                }
                var4_4 = this.getActualMaximum(var1_1);
                var12_37 = (long)((JapaneseImperialCalendar.getRolledValue(this.internalGet(var1_1), var2_2, 1, var4_4) - 1) * 7) + var8_26 + (long)var3_3;
                if (var12_37 < var10_33) {
                    var8_26 = var10_33;
                } else {
                    var8_26 = var12_37;
                    if (var12_37 >= (long)var5_8 + var10_33) {
                        var8_26 = (long)var5_8 + var10_33 - 1L;
                    }
                }
                this.set(5, (int)(var8_26 - var10_33) + 1);
                return;
            }
            case 3: {
                var5_6 = var3_3;
                var15_40 = this.jdate.getNormalizedYear();
                var3_3 = this.getActualMaximum(3);
                this.set(7, this.internalGet(7));
                var16_42 = this.internalGet(3);
                var17_43 = var16_42 + var2_2;
                if (!this.isTransitionYear(this.jdate.getNormalizedYear())) {
                    var18_44 = this.jdate.getYear();
                    if (var18_44 == this.getMaximum(1)) {
                        var3_3 = this.getActualMaximum(3);
                        var4_4 = var5_6;
                    } else {
                        var4_4 = var5_6;
                        if (var18_44 == this.getMinimum(1)) {
                            var5_6 = this.getActualMinimum(3);
                            var3_3 = var7_21 = this.getActualMaximum(3);
                            var4_4 = var5_6;
                            if (var17_43 > var5_6) {
                                var3_3 = var7_21;
                                var4_4 = var5_6;
                                if (var17_43 < var7_21) {
                                    this.set(3, var17_43);
                                    return;
                                }
                            }
                        }
                    }
                    if (var17_43 > var4_4 && var17_43 < var3_3) {
                        this.set(3, var17_43);
                        return;
                    }
                    var10_34 = this.cachedFixedDate;
                    var8_27 = var10_34 - (long)((var16_42 - var4_4) * 7);
                    if (var18_44 != this.getMinimum(1)) {
                        var5_6 = JapaneseImperialCalendar.gcal.getYearFromFixedDate(var8_27) != var15_40 ? var4_4 + 1 : var4_4;
                    } else {
                        var6_14 = JapaneseImperialCalendar.jcal.getCalendarDate(Long.MIN_VALUE, this.getZone());
                        var5_6 = var4_4;
                        if (var8_27 < JapaneseImperialCalendar.jcal.getFixedDate(var6_14)) {
                            var5_6 = var4_4 + 1;
                        }
                    }
                    var8_27 = (var3_3 - this.internalGet(3)) * 7;
                    if (JapaneseImperialCalendar.gcal.getYearFromFixedDate(var10_34 + var8_27) == var15_40) break;
                    --var3_3;
                    break;
                }
                var12_38 = this.cachedFixedDate;
                var10_35 = var12_38 - (long)((var16_42 - var5_6) * 7);
                var6_15 = JapaneseImperialCalendar.getCalendarDate(var10_35);
                if (var6_15.getEra() != this.jdate.getEra()) ** GOTO lbl178
                var1_1 = var5_6;
                if (var6_15.getYear() == this.jdate.getYear()) ** GOTO lbl179
lbl178: // 2 sources:
                var1_1 = var5_6 + 1;
lbl179: // 2 sources:
                var8_28 = (var3_3 - var16_42) * 7;
                JapaneseImperialCalendar.jcal.getCalendarDateFromFixedDate(var6_15, var12_38 + var8_28);
                if (var6_15.getEra() != this.jdate.getEra()) ** GOTO lbl184
                var5_6 = var3_3;
                if (var6_15.getYear() == this.jdate.getYear()) ** GOTO lbl185
lbl184: // 2 sources:
                var5_6 = var3_3 - 1;
lbl185: // 2 sources:
                var6_15 = JapaneseImperialCalendar.getCalendarDate((long)((JapaneseImperialCalendar.getRolledValue(var16_42, var2_2, var1_1, var5_6) - 1) * 7) + var10_35);
                this.set(2, var6_15.getMonth() - 1);
                this.set(5, var6_15.getDayOfMonth());
                return;
            }
            case 2: {
                var5_9 = var3_3;
                if (!this.isTransitionYear(this.jdate.getNormalizedYear())) {
                    var3_3 = this.jdate.getYear();
                    if (var3_3 == this.getMaximum(1)) {
                        var6_16 = JapaneseImperialCalendar.jcal.getCalendarDate(this.time, this.getZone());
                        var19_45 = JapaneseImperialCalendar.jcal.getCalendarDate(Long.MAX_VALUE, this.getZone());
                        var3_3 = var19_45.getMonth() - 1;
                        var1_1 = var2_2 = JapaneseImperialCalendar.getRolledValue(this.internalGet(var1_1), var2_2, var5_9, var3_3);
                        if (var2_2 == var3_3) {
                            var6_16.addYear(-400);
                            var6_16.setMonth(var2_2 + 1);
                            if (var6_16.getDayOfMonth() > var19_45.getDayOfMonth()) {
                                var6_16.setDayOfMonth(var19_45.getDayOfMonth());
                                JapaneseImperialCalendar.jcal.normalize(var6_16);
                            }
                            var1_1 = var2_2;
                            if (var6_16.getDayOfMonth() == var19_45.getDayOfMonth()) {
                                var1_1 = var2_2;
                                if (var6_16.getTimeOfDay() > var19_45.getTimeOfDay()) {
                                    var6_16.setMonth(var2_2 + 1);
                                    var6_16.setDayOfMonth(var19_45.getDayOfMonth() - 1);
                                    JapaneseImperialCalendar.jcal.normalize(var6_16);
                                    var1_1 = var6_16.getMonth() - 1;
                                }
                            }
                            this.set(5, var6_16.getDayOfMonth());
                        }
                        this.set(2, var1_1);
                        return;
                    }
                    if (var3_3 == this.getMinimum(1)) {
                        var19_46 = JapaneseImperialCalendar.jcal.getCalendarDate(this.time, this.getZone());
                        var6_17 = JapaneseImperialCalendar.jcal.getCalendarDate(Long.MIN_VALUE, this.getZone());
                        var3_3 = var6_17.getMonth() - 1;
                        var1_1 = var2_2 = JapaneseImperialCalendar.getRolledValue(this.internalGet(var1_1), var2_2, var3_3, var4_4);
                        if (var2_2 == var3_3) {
                            var19_46.addYear(400);
                            var19_46.setMonth(var2_2 + 1);
                            if (var19_46.getDayOfMonth() < var6_17.getDayOfMonth()) {
                                var19_46.setDayOfMonth(var6_17.getDayOfMonth());
                                JapaneseImperialCalendar.jcal.normalize(var19_46);
                            }
                            var1_1 = var2_2;
                            if (var19_46.getDayOfMonth() == var6_17.getDayOfMonth()) {
                                var1_1 = var2_2;
                                if (var19_46.getTimeOfDay() < var6_17.getTimeOfDay()) {
                                    var19_46.setMonth(var2_2 + 1);
                                    var19_46.setDayOfMonth(var6_17.getDayOfMonth() + 1);
                                    JapaneseImperialCalendar.jcal.normalize(var19_46);
                                    var1_1 = var19_46.getMonth() - 1;
                                }
                            }
                            this.set(5, var19_46.getDayOfMonth());
                        }
                        this.set(2, var1_1);
                        return;
                    }
                    var1_1 = var2_2 = (this.internalGet(2) + var2_2) % 12;
                    if (var2_2 < 0) {
                        var1_1 = var2_2 + 12;
                    }
                    this.set(2, var1_1);
                    var1_1 = this.monthLength(var1_1);
                    if (this.internalGet(5) <= var1_1) return;
                    this.set(5, var1_1);
                    return;
                }
                var15_41 = JapaneseImperialCalendar.getEraIndex(this.jdate);
                var6_18 = null;
                if (this.jdate.getYear() == 1) {
                    var6_18 = JapaneseImperialCalendar.eras[var15_41].getSinceDate();
                    var7_22 = var6_18.getMonth() - 1;
                    var3_3 = var4_4;
                } else {
                    var19_47 = JapaneseImperialCalendar.eras;
                    var7_22 = var5_9;
                    var3_3 = var4_4;
                    if (var15_41 < ((Era[])var19_47).length - 1) {
                        var6_18 = var19_47 = var19_47[var15_41 + 1].getSinceDate();
                        var7_22 = var5_9;
                        var3_3 = var4_4;
                        if (var19_47.getYear() == this.jdate.getNormalizedYear()) {
                            var3_3 = var19_47.getMonth() - 1;
                            if (var19_47.getDayOfMonth() == 1) {
                                --var3_3;
                                var6_18 = var19_47;
                                var7_22 = var5_9;
                            } else {
                                var7_22 = var5_9;
                                var6_18 = var19_47;
                            }
                        }
                    }
                }
                if (var7_22 == var3_3) {
                    return;
                }
                var1_1 = JapaneseImperialCalendar.getRolledValue(this.internalGet(var1_1), var2_2, var7_22, var3_3);
                this.set(2, var1_1);
                if (var1_1 != var7_22) {
                    if (var1_1 != var3_3) return;
                    if (var6_18.getMonth() - 1 != var1_1) return;
                    var1_1 = var6_18.getDayOfMonth();
                    if (this.jdate.getDayOfMonth() < var1_1) return;
                    this.set(5, var1_1 - 1);
                    return;
                }
                if (var6_18.getMonth() == 1) {
                    if (var6_18.getDayOfMonth() == 1) return;
                }
                if (this.jdate.getDayOfMonth() >= var6_18.getDayOfMonth()) return;
                this.set(5, var6_18.getDayOfMonth());
                return;
            }
            case 1: {
                var5_6 = this.getActualMinimum(var1_1);
                var3_3 = this.getActualMaximum(var1_1);
                break;
            }
lbl302: // 2 sources:
            case 0: 
            case 9: 
            case 12: 
            case 13: 
            case 14: {
                var5_6 = var3_3;
                var3_3 = var4_4;
            }
        }
        this.set(var1_1, JapaneseImperialCalendar.getRolledValue(this.internalGet(var1_1), var2_2, var5_6, var3_3));
    }

    @Override
    public void roll(int n, boolean bl) {
        int n2 = bl ? 1 : -1;
        this.roll(n, n2);
    }

    @Override
    public void setTimeZone(TimeZone timeZone) {
        super.setTimeZone(timeZone);
        this.jdate.setZone(timeZone);
    }
}

