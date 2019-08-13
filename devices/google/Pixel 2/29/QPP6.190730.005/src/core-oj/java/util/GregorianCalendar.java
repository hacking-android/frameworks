/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.ZoneInfo
 */
package java.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import libcore.util.ZoneInfo;
import sun.util.calendar.BaseCalendar;
import sun.util.calendar.CalendarDate;
import sun.util.calendar.CalendarSystem;
import sun.util.calendar.CalendarUtils;
import sun.util.calendar.Era;
import sun.util.calendar.Gregorian;
import sun.util.calendar.JulianCalendar;

public class GregorianCalendar
extends Calendar {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int AD = 1;
    public static final int BC = 0;
    static final int BCE = 0;
    static final int CE = 1;
    static final long DEFAULT_GREGORIAN_CUTOVER = -12219292800000L;
    private static final int EPOCH_OFFSET = 719163;
    private static final int EPOCH_YEAR = 1970;
    static final int[] LEAP_MONTH_LENGTH;
    static final int[] LEAST_MAX_VALUES;
    static final int[] MAX_VALUES;
    static final int[] MIN_VALUES;
    static final int[] MONTH_LENGTH;
    private static final long ONE_DAY = 86400000L;
    private static final int ONE_HOUR = 3600000;
    private static final int ONE_MINUTE = 60000;
    private static final int ONE_SECOND = 1000;
    private static final long ONE_WEEK = 604800000L;
    private static final Gregorian gcal;
    private static JulianCalendar jcal;
    private static Era[] jeras;
    static final long serialVersionUID = -8125100834729963327L;
    private transient long cachedFixedDate = Long.MIN_VALUE;
    private transient BaseCalendar calsys;
    private transient BaseCalendar.Date cdate;
    private transient BaseCalendar.Date gdate;
    private long gregorianCutover = -12219292800000L;
    private transient long gregorianCutoverDate = 577736L;
    private transient int gregorianCutoverYear = 1582;
    private transient int gregorianCutoverYearJulian = 1582;
    private transient int[] originalFields;
    private transient int[] zoneOffsets;

    static {
        MONTH_LENGTH = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        LEAP_MONTH_LENGTH = new int[]{31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        MIN_VALUES = new int[]{0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, -46800000, 0};
        LEAST_MAX_VALUES = new int[]{1, 292269054, 11, 52, 4, 28, 365, 7, 4, 1, 11, 23, 59, 59, 999, 50400000, 1200000};
        MAX_VALUES = new int[]{1, 292278994, 11, 53, 6, 31, 366, 7, 6, 1, 11, 23, 59, 59, 999, 50400000, 7200000};
        gcal = CalendarSystem.getGregorianCalendar();
    }

    public GregorianCalendar() {
        this(TimeZone.getDefaultRef(), Locale.getDefault(Locale.Category.FORMAT));
        this.setZoneShared(true);
    }

    public GregorianCalendar(int n, int n2, int n3) {
        this(n, n2, n3, 0, 0, 0, 0);
    }

    public GregorianCalendar(int n, int n2, int n3, int n4, int n5) {
        this(n, n2, n3, n4, n5, 0, 0);
    }

    public GregorianCalendar(int n, int n2, int n3, int n4, int n5, int n6) {
        this(n, n2, n3, n4, n5, n6, 0);
    }

    GregorianCalendar(int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        this.gdate = gcal.newCalendarDate(this.getZone());
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
        if (n4 >= 12 && n4 <= 23) {
            this.internalSet(9, 1);
            this.internalSet(10, n4 - 12);
        } else {
            this.internalSet(10, n4);
        }
        this.setFieldsComputed(1536);
        this.set(11, n4);
        this.set(12, n5);
        this.set(13, n6);
        this.internalSet(14, n7);
    }

    GregorianCalendar(long l) {
        this();
        this.setTimeInMillis(l);
    }

    public GregorianCalendar(Locale locale) {
        this(TimeZone.getDefaultRef(), locale);
        this.setZoneShared(true);
    }

    public GregorianCalendar(TimeZone timeZone) {
        this(timeZone, Locale.getDefault(Locale.Category.FORMAT));
    }

    public GregorianCalendar(TimeZone timeZone, Locale locale) {
        super(timeZone, locale);
        this.gdate = gcal.newCalendarDate(timeZone);
        this.setTimeInMillis(System.currentTimeMillis());
    }

    GregorianCalendar(TimeZone timeZone, Locale locale, boolean bl) {
        super(timeZone, locale);
        this.gdate = gcal.newCalendarDate(this.getZone());
    }

    private int actualMonthLength() {
        int n = this.cdate.getNormalizedYear();
        if (n != this.gregorianCutoverYear && n != this.gregorianCutoverYearJulian) {
            return this.calsys.getMonthLength(this.cdate);
        }
        CalendarDate calendarDate = (BaseCalendar.Date)this.cdate.clone();
        long l = this.getFixedDateMonth1((BaseCalendar.Date)calendarDate, this.calsys.getFixedDate(calendarDate));
        long l2 = (long)this.calsys.getMonthLength(calendarDate) + l;
        if (l2 < this.gregorianCutoverDate) {
            return (int)(l2 - l);
        }
        if (this.cdate != this.gdate) {
            calendarDate = gcal.newCalendarDate(TimeZone.NO_TIMEZONE);
        }
        gcal.getCalendarDateFromFixedDate(calendarDate, l2);
        return (int)(this.getFixedDateMonth1((BaseCalendar.Date)calendarDate, l2) - l);
    }

    private int adjustDstOffsetForInvalidWallClock(long l, TimeZone timeZone, int n) {
        int n2 = n;
        if (n != 0) {
            n2 = n;
            if (!timeZone.inDaylightTime(new Date(l - (long)n))) {
                n2 = 0;
            }
        }
        return n2;
    }

    private long adjustForZoneAndDaylightSavingsTime(int n, long l, TimeZone timeZone) {
        int n2 = 0;
        int n3 = 0;
        if (n != 98304) {
            if (this.zoneOffsets == null) {
                this.zoneOffsets = new int[2];
            }
            n2 = GregorianCalendar.isFieldSet(n, 15) ? this.internalGet(15) : timeZone.getRawOffset();
            long l2 = l - (long)n2;
            if (timeZone instanceof ZoneInfo) {
                ((ZoneInfo)timeZone).getOffsetsByUtcTime(l2, this.zoneOffsets);
            } else {
                timeZone.getOffsets(l2, this.zoneOffsets);
            }
            int[] arrn = this.zoneOffsets;
            n2 = arrn[0];
            n3 = this.adjustDstOffsetForInvalidWallClock(l2, timeZone, arrn[1]);
        }
        int n4 = n2;
        int n5 = n3;
        if (n != 0) {
            if (GregorianCalendar.isFieldSet(n, 15)) {
                n2 = this.internalGet(15);
            }
            n4 = n2;
            n5 = n3;
            if (GregorianCalendar.isFieldSet(n, 16)) {
                n5 = this.internalGet(16);
                n4 = n2;
            }
        }
        return l - (long)n4 - (long)n5;
    }

    private int computeFields(int n, int n2) {
        block50 : {
            int n3;
            long l;
            long l2;
            int n4;
            int n5 = 0;
            Object object = this.getZone();
            if (this.zoneOffsets == null) {
                this.zoneOffsets = new int[2];
            }
            if (n2 != 98304) {
                if (object instanceof ZoneInfo) {
                    n5 = ((ZoneInfo)object).getOffsetsByUtcTime(this.time, this.zoneOffsets);
                } else {
                    n5 = object.getOffset(this.time);
                    this.zoneOffsets[0] = object.getRawOffset();
                    object = this.zoneOffsets;
                    object[1] = n5 - object[0];
                }
            }
            if (n2 != 0) {
                if (GregorianCalendar.isFieldSet(n2, 15)) {
                    this.zoneOffsets[0] = this.internalGet(15);
                }
                if (GregorianCalendar.isFieldSet(n2, 16)) {
                    this.zoneOffsets[1] = this.internalGet(16);
                }
                object = this.zoneOffsets;
                n5 = object[0] + object[1];
            }
            long l3 = (long)n5 / 86400000L + this.time / 86400000L;
            n2 = n5 % 86400000 + (int)(this.time % 86400000L);
            if ((long)n2 >= 86400000L) {
                n4 = (int)((long)n2 - 86400000L);
                l2 = l3 + 1L;
            } else {
                do {
                    n4 = n2;
                    l2 = l3--;
                    if (n2 >= 0) break;
                    n2 = (int)((long)n2 + 86400000L);
                } while (true);
            }
            long l4 = l2 + 719163L;
            n2 = 1;
            n5 = 1;
            if (l4 >= this.gregorianCutoverDate) {
                if (l4 != this.cachedFixedDate) {
                    gcal.getCalendarDateFromFixedDate(this.gdate, l4);
                    this.cachedFixedDate = l4;
                }
                n2 = n3 = this.gdate.getYear();
                if (n3 <= 0) {
                    n2 = 1 - n3;
                    n5 = 0;
                }
                this.calsys = gcal;
                this.cdate = this.gdate;
                n3 = n2;
            } else {
                this.calsys = GregorianCalendar.getJulianCalendarSystem();
                this.cdate = jcal.newCalendarDate(this.getZone());
                jcal.getCalendarDateFromFixedDate(this.cdate, l4);
                if (this.cdate.getEra() == jeras[0]) {
                    n2 = 0;
                }
                n3 = this.cdate.getYear();
                n5 = n2;
            }
            this.internalSet(0, n5);
            this.internalSet(1, n3);
            n5 = n | 3;
            int n6 = this.cdate.getMonth();
            n3 = this.cdate.getDayOfMonth();
            n2 = n5;
            if ((n & 164) != 0) {
                this.internalSet(2, n6 - 1);
                this.internalSet(5, n3);
                this.internalSet(7, this.cdate.getDayOfWeek());
                n2 = n5 | 164;
            }
            n5 = n2;
            if ((n & 32256) != 0) {
                if (n4 != 0) {
                    n5 = n4 / 3600000;
                    this.internalSet(11, n5);
                    this.internalSet(9, n5 / 12);
                    this.internalSet(10, n5 % 12);
                    n5 = n4 % 3600000;
                    this.internalSet(12, n5 / 60000);
                    this.internalSet(13, (n5 %= 60000) / 1000);
                    this.internalSet(14, n5 % 1000);
                } else {
                    this.internalSet(11, 0);
                    this.internalSet(9, 0);
                    this.internalSet(10, 0);
                    this.internalSet(12, 0);
                    this.internalSet(13, 0);
                    this.internalSet(14, 0);
                }
                n5 = n2 | 32256;
            }
            n2 = n5;
            if ((n & 98304) != 0) {
                this.internalSet(15, this.zoneOffsets[0]);
                this.internalSet(16, this.zoneOffsets[1]);
                n2 = n5 | 98304;
            }
            if ((n & 344) == 0) break block50;
            n6 = this.cdate.getNormalizedYear();
            l2 = this.calsys.getFixedDate(n6, 1, 1, this.cdate);
            n4 = (int)(l4 - l2) + 1;
            l3 = l4 - (long)n3 + 1L;
            n = this.calsys == gcal ? this.gregorianCutoverYear : this.gregorianCutoverYearJulian;
            if (n6 == n) {
                if (this.gregorianCutoverYearJulian <= this.gregorianCutoverYear) {
                    l2 = this.getFixedDateJan1(this.cdate, l4);
                    if (l4 >= this.gregorianCutoverDate) {
                        l3 = this.getFixedDateMonth1(this.cdate, l4);
                    }
                }
                n4 = (int)(l4 - l2) + 1;
                n5 = (int)(l4 - l3);
                l = l2;
                l2 = l3;
                l3 = l;
            } else {
                l = l3;
                n5 = n3 - 1;
                l3 = l2;
                l2 = l;
            }
            this.internalSet(6, n4);
            this.internalSet(8, n5 / 7 + 1);
            n5 = this.getWeekNumber(l3, l4);
            if (n5 == 0) {
                l = l3 - 1L;
                l3 -= 365L;
                if (n6 > n + 1) {
                    if (CalendarUtils.isGregorianLeapYear(n6 - 1)) {
                        --l3;
                    }
                } else if (n6 <= this.gregorianCutoverYearJulian) {
                    if (CalendarUtils.isJulianLeapYear(n6 - 1)) {
                        --l3;
                    }
                } else {
                    object = this.calsys;
                    n = this.getCalendarDate(l).getNormalizedYear();
                    if (n == this.gregorianCutoverYear) {
                        object = this.getCutoverCalendarSystem();
                        if (object == jcal) {
                            l3 = object.getFixedDate(n, 1, 1, null);
                        } else {
                            l3 = this.gregorianCutoverDate;
                            object = gcal;
                        }
                    } else if (n <= this.gregorianCutoverYearJulian) {
                        l3 = GregorianCalendar.getJulianCalendarSystem().getFixedDate(n, 1, 1, null);
                    }
                }
                n = this.getWeekNumber(l3, l);
            } else {
                int n7;
                n3 = this.gregorianCutoverYear;
                if (n6 <= n3 && n6 >= (n7 = this.gregorianCutoverYearJulian) - 1) {
                    object = this.calsys;
                    n = n4 = n6 + 1;
                    if (n4 == n7 + 1) {
                        n = n4;
                        if (n4 < n3) {
                            n = this.gregorianCutoverYear;
                        }
                    }
                    if (n == this.gregorianCutoverYear) {
                        object = this.getCutoverCalendarSystem();
                    }
                    if (n <= (n4 = this.gregorianCutoverYear) && (n3 = this.gregorianCutoverYearJulian) != n4 && n != n3) {
                        l3 = this.gregorianCutoverDate;
                        object = gcal;
                    } else {
                        l3 = object.getFixedDate(n, 1, 1, null);
                    }
                    l = BaseCalendar.getDayOfWeekDateOnOrBefore(l3 + 6L, this.getFirstDayOfWeek());
                    n = n5;
                    if ((int)(l - l3) >= this.getMinimalDaysInFirstWeek()) {
                        n = n5;
                        if (l4 >= l - 7L) {
                            n = 1;
                        }
                    }
                } else {
                    n = n5;
                    if (n5 >= 52) {
                        l3 = l = 365L + l3;
                        if (this.cdate.isLeapYear()) {
                            l3 = l + 1L;
                        }
                        l = BaseCalendar.getDayOfWeekDateOnOrBefore(6L + l3, this.getFirstDayOfWeek());
                        n = n5;
                        if ((int)(l - l3) >= this.getMinimalDaysInFirstWeek()) {
                            n = n5;
                            if (l4 >= l - 7L) {
                                n = 1;
                            }
                        }
                    }
                }
            }
            this.internalSet(3, n);
            this.internalSet(4, this.getWeekNumber(l2, l4));
            n2 |= 344;
        }
        return n2;
    }

    public static GregorianCalendar from(ZonedDateTime zonedDateTime) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(TimeZone.getTimeZone(zonedDateTime.getZone()));
        gregorianCalendar.setGregorianChange(new Date(Long.MIN_VALUE));
        gregorianCalendar.setFirstDayOfWeek(2);
        gregorianCalendar.setMinimalDaysInFirstWeek(4);
        try {
            gregorianCalendar.setTimeInMillis(Math.addExact(Math.multiplyExact(zonedDateTime.toEpochSecond(), 1000L), (long)zonedDateTime.get(ChronoField.MILLI_OF_SECOND)));
            return gregorianCalendar;
        }
        catch (ArithmeticException arithmeticException) {
            throw new IllegalArgumentException(arithmeticException);
        }
    }

    private BaseCalendar.Date getCalendarDate(long l) {
        BaseCalendar baseCalendar = l >= this.gregorianCutoverDate ? gcal : GregorianCalendar.getJulianCalendarSystem();
        BaseCalendar.Date date = (BaseCalendar.Date)baseCalendar.newCalendarDate(TimeZone.NO_TIMEZONE);
        baseCalendar.getCalendarDateFromFixedDate(date, l);
        return date;
    }

    private long getCurrentFixedDate() {
        BaseCalendar baseCalendar = this.calsys;
        long l = baseCalendar == gcal ? this.cachedFixedDate : baseCalendar.getFixedDate(this.cdate);
        return l;
    }

    private BaseCalendar getCutoverCalendarSystem() {
        if (this.gregorianCutoverYearJulian < this.gregorianCutoverYear) {
            return gcal;
        }
        return GregorianCalendar.getJulianCalendarSystem();
    }

    private long getFixedDate(BaseCalendar baseCalendar, int n, int n2) {
        int n3;
        int[] arrn;
        long l;
        int n4 = 0;
        if (GregorianCalendar.isFieldSet(n2, 2)) {
            n4 = this.internalGet(2);
            if (n4 > 11) {
                n3 = n + n4 / 12;
                n = n4 % 12;
            } else if (n4 < 0) {
                arrn = new int[1];
                n4 = CalendarUtils.floorDivide(n4, 12, arrn);
                n3 = arrn[0];
                n4 = n + n4;
                n = n3;
                n3 = n4;
            } else {
                n3 = n;
                n = n4;
            }
        } else {
            n3 = n;
            n = n4;
        }
        arrn = baseCalendar == gcal ? this.gdate : null;
        long l2 = baseCalendar.getFixedDate(n3, n + 1, 1, (BaseCalendar.Date)arrn);
        if (GregorianCalendar.isFieldSet(n2, 2)) {
            if (GregorianCalendar.isFieldSet(n2, 5)) {
                l = this.isSet(5) ? l2 + (long)this.internalGet(5) - 1L : l2;
            } else if (GregorianCalendar.isFieldSet(n2, 4)) {
                long l3;
                l = l3 = BaseCalendar.getDayOfWeekDateOnOrBefore(l2 + 6L, this.getFirstDayOfWeek());
                if (l3 - l2 >= (long)this.getMinimalDaysInFirstWeek()) {
                    l = l3 - 7L;
                }
                l2 = l;
                if (GregorianCalendar.isFieldSet(n2, 7)) {
                    l2 = BaseCalendar.getDayOfWeekDateOnOrBefore(6L + l, this.internalGet(7));
                }
                l = l2 + (long)((this.internalGet(4) - 1) * 7);
            } else {
                n4 = GregorianCalendar.isFieldSet(n2, 7) ? this.internalGet(7) : this.getFirstDayOfWeek();
                l = (n2 = GregorianCalendar.isFieldSet(n2, 8) ? this.internalGet(8) : 1) >= 0 ? BaseCalendar.getDayOfWeekDateOnOrBefore((long)(n2 * 7) + l2 - 1L, n4) : BaseCalendar.getDayOfWeekDateOnOrBefore((long)(this.monthLength(n, n3) + (n2 + 1) * 7) + l2 - 1L, n4);
            }
        } else {
            n = this.gregorianCutoverYear;
            long l4 = l2;
            if (n3 == n) {
                l4 = l2;
                if (baseCalendar == gcal) {
                    l4 = l2;
                    if (l2 < this.gregorianCutoverDate) {
                        l4 = l2;
                        if (n != this.gregorianCutoverYearJulian) {
                            l4 = this.gregorianCutoverDate;
                        }
                    }
                }
            }
            if (GregorianCalendar.isFieldSet(n2, 6)) {
                l = l4 + (long)this.internalGet(6) - 1L;
            } else {
                l = l2 = BaseCalendar.getDayOfWeekDateOnOrBefore(l4 + 6L, this.getFirstDayOfWeek());
                if (l2 - l4 >= (long)this.getMinimalDaysInFirstWeek()) {
                    l = l2 - 7L;
                }
                l2 = l;
                if (GregorianCalendar.isFieldSet(n2, 7)) {
                    n = this.internalGet(7);
                    l2 = l;
                    if (n != this.getFirstDayOfWeek()) {
                        l2 = BaseCalendar.getDayOfWeekDateOnOrBefore(6L + l, n);
                    }
                }
                l = l2 + ((long)this.internalGet(3) - 1L) * 7L;
            }
        }
        return l;
    }

    private long getFixedDateJan1(BaseCalendar.Date date, long l) {
        long l2;
        if (this.gregorianCutoverYear != this.gregorianCutoverYearJulian && l >= (l2 = this.gregorianCutoverDate)) {
            return l2;
        }
        return GregorianCalendar.getJulianCalendarSystem().getFixedDate(date.getNormalizedYear(), 1, 1, null);
    }

    private long getFixedDateMonth1(BaseCalendar.Date date, long l) {
        BaseCalendar.Date date2 = this.getGregorianCutoverDate();
        if (date2.getMonth() == 1 && date2.getDayOfMonth() == 1) {
            return l - (long)date.getDayOfMonth() + 1L;
        }
        if (date.getMonth() == date2.getMonth()) {
            BaseCalendar.Date date3 = this.getLastJulianDate();
            l = this.gregorianCutoverYear == this.gregorianCutoverYearJulian && date2.getMonth() == date3.getMonth() ? jcal.getFixedDate(date.getNormalizedYear(), date.getMonth(), 1, null) : this.gregorianCutoverDate;
        } else {
            l = 1L + (l - (long)date.getDayOfMonth());
        }
        return l;
    }

    private BaseCalendar.Date getGregorianCutoverDate() {
        return this.getCalendarDate(this.gregorianCutoverDate);
    }

    private static BaseCalendar getJulianCalendarSystem() {
        synchronized (GregorianCalendar.class) {
            if (jcal == null) {
                jcal = (JulianCalendar)CalendarSystem.forName("julian");
                jeras = jcal.getEras();
            }
            JulianCalendar julianCalendar = jcal;
            return julianCalendar;
        }
    }

    private BaseCalendar.Date getLastJulianDate() {
        return this.getCalendarDate(this.gregorianCutoverDate - 1L);
    }

    private GregorianCalendar getNormalizedCalendar() {
        GregorianCalendar gregorianCalendar;
        if (this.isFullyNormalized()) {
            gregorianCalendar = this;
        } else {
            gregorianCalendar = (GregorianCalendar)this.clone();
            gregorianCalendar.setLenient(true);
            gregorianCalendar.complete();
        }
        return gregorianCalendar;
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

    private int getWeekNumber(long l, long l2) {
        long l3 = Gregorian.getDayOfWeekDateOnOrBefore(6L + l, this.getFirstDayOfWeek());
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

    private long getYearOffsetInMillis() {
        long l = (this.internalGet(6) - 1) * 24;
        long l2 = this.internalGet(11);
        long l3 = this.internalGet(12);
        long l4 = this.internalGet(13);
        return (long)this.internalGet(14) + (((l + l2) * 60L + l3) * 60L + l4) * 1000L - (long)(this.internalGet(15) + this.internalGet(16));
    }

    private int internalGetEra() {
        int n = this.isSet(0) ? this.internalGet(0) : 1;
        return n;
    }

    private boolean isCutoverYear(int n) {
        int n2 = this.calsys == gcal ? this.gregorianCutoverYear : this.gregorianCutoverYearJulian;
        boolean bl = n == n2;
        return bl;
    }

    private int monthLength(int n) {
        int n2;
        int n3 = n2 = this.internalGet(1);
        if (this.internalGetEra() == 0) {
            n3 = 1 - n2;
        }
        return this.monthLength(n, n3);
    }

    private int monthLength(int n, int n2) {
        n = this.isLeapYear(n2) ? LEAP_MONTH_LENGTH[n] : MONTH_LENGTH[n];
        return n;
    }

    private void pinDayOfMonth() {
        int n = this.internalGet(1);
        n = n <= this.gregorianCutoverYear && n >= this.gregorianCutoverYearJulian ? this.getNormalizedCalendar().getActualMaximum(5) : this.monthLength(this.internalGet(2));
        if (this.internalGet(5) > n) {
            this.set(5, n);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.gdate == null) {
            this.gdate = gcal.newCalendarDate(this.getZone());
            this.cachedFixedDate = Long.MIN_VALUE;
        }
        this.setGregorianChange(this.gregorianCutover);
    }

    private void setGregorianChange(long l) {
        this.gregorianCutover = l;
        this.gregorianCutoverDate = CalendarUtils.floorDivide(l, 86400000L) + 719163L;
        if (l == Long.MAX_VALUE) {
            ++this.gregorianCutoverDate;
        }
        this.gregorianCutoverYear = this.getGregorianCutoverDate().getYear();
        BaseCalendar baseCalendar = GregorianCalendar.getJulianCalendarSystem();
        BaseCalendar.Date date = (BaseCalendar.Date)baseCalendar.newCalendarDate(TimeZone.NO_TIMEZONE);
        baseCalendar.getCalendarDateFromFixedDate(date, this.gregorianCutoverDate - 1L);
        this.gregorianCutoverYearJulian = date.getNormalizedYear();
        if (this.time < this.gregorianCutover) {
            this.setUnnormalized();
        }
    }

    private int yearLength() {
        int n;
        int n2 = n = this.internalGet(1);
        if (this.internalGetEra() == 0) {
            n2 = 1 - n;
        }
        return this.yearLength(n2);
    }

    private int yearLength(int n) {
        n = this.isLeapYear(n) ? 366 : 365;
        return n;
    }

    @Override
    public void add(int n, int n2) {
        if (n2 == 0) {
            return;
        }
        if (n >= 0 && n < 15) {
            this.complete();
            if (n == 1) {
                n = this.internalGet(1);
                if (this.internalGetEra() == 1) {
                    if ((n += n2) > 0) {
                        this.set(1, n);
                    } else {
                        this.set(1, 1 - n);
                        this.set(0, 0);
                    }
                } else if ((n -= n2) > 0) {
                    this.set(1, n);
                } else {
                    this.set(1, 1 - n);
                    this.set(0, 1);
                }
                this.pinDayOfMonth();
            } else if (n == 2) {
                n2 = this.internalGet(2) + n2;
                int n3 = this.internalGet(1);
                n = n2 >= 0 ? n2 / 12 : (n2 + 1) / 12 - 1;
                if (n != 0) {
                    if (this.internalGetEra() == 1) {
                        if ((n = n3 + n) > 0) {
                            this.set(1, n);
                        } else {
                            this.set(1, 1 - n);
                            this.set(0, 0);
                        }
                    } else if ((n = n3 - n) > 0) {
                        this.set(1, n);
                    } else {
                        this.set(1, 1 - n);
                        this.set(0, 1);
                    }
                }
                if (n2 >= 0) {
                    this.set(2, n2 % 12);
                } else {
                    n = n2 %= 12;
                    if (n2 < 0) {
                        n = n2 + 12;
                    }
                    this.set(2, n + 0);
                }
                this.pinDayOfMonth();
            } else if (n == 0) {
                n = n2 = this.internalGet(0) + n2;
                if (n2 < 0) {
                    n = 0;
                }
                n2 = n;
                if (n > 1) {
                    n2 = 1;
                }
                this.set(0, n2);
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
                long l4 = this.getCurrentFixedDate();
                long l5 = (((l3 + (long)this.internalGet(11)) * 60L + (long)this.internalGet(12)) * 60L + (long)this.internalGet(13)) * 1000L + (long)this.internalGet(14);
                if (l5 >= 86400000L) {
                    l3 = l4 + 1L;
                    l = l5 - 86400000L;
                } else {
                    l = l5;
                    l3 = l4;
                    if (l5 < 0L) {
                        l3 = l4 - 1L;
                        l = l5 + 86400000L;
                    }
                }
                this.setTimeInMillis(this.adjustForZoneAndDaylightSavingsTime(0, (l3 + l2 - 719163L) * 86400000L + l, this.getZone()));
            }
            return;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Object clone() {
        GregorianCalendar gregorianCalendar = (GregorianCalendar)super.clone();
        gregorianCalendar.gdate = (BaseCalendar.Date)this.gdate.clone();
        BaseCalendar.Date date = this.cdate;
        if (date != null) {
            gregorianCalendar.cdate = date != this.gdate ? (BaseCalendar.Date)date.clone() : gregorianCalendar.gdate;
        }
        gregorianCalendar.originalFields = null;
        gregorianCalendar.zoneOffsets = null;
        return gregorianCalendar;
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
                    if (this.calsys != null) break block4;
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
        block23 : {
            block31 : {
                Object object;
                int n;
                long l;
                long l2;
                int n2;
                block26 : {
                    long l3;
                    block30 : {
                        long l4;
                        block29 : {
                            int n3;
                            block27 : {
                                block28 : {
                                    block24 : {
                                        block25 : {
                                            block22 : {
                                                block21 : {
                                                    if (!this.isLenient()) {
                                                        if (this.originalFields == null) {
                                                            this.originalFields = new int[17];
                                                        }
                                                        for (n = 0; n < 17; ++n) {
                                                            n2 = this.internalGet(n);
                                                            if (this.isExternallySet(n) && (n2 < this.getMinimum(n) || n2 > this.getMaximum(n))) {
                                                                throw new IllegalArgumentException(GregorianCalendar.getFieldName(n));
                                                            }
                                                            this.originalFields[n] = n2;
                                                        }
                                                    }
                                                    n3 = this.selectFields();
                                                    n = this.isSet(1) ? this.internalGet(1) : 1970;
                                                    n2 = this.internalGetEra();
                                                    if (n2 != 0) break block21;
                                                    n2 = 1 - n;
                                                    break block22;
                                                }
                                                if (n2 != 1) break block23;
                                                n2 = n;
                                            }
                                            n = n3;
                                            if (n2 <= 0) {
                                                n = n3;
                                                if (!this.isSet(0)) {
                                                    n = n3 | 1;
                                                    this.setFieldsComputed(1);
                                                }
                                            }
                                            if (GregorianCalendar.isFieldSet(n, 11)) {
                                                l = 0L + (long)this.internalGet(11);
                                            } else {
                                                l = l4 = 0L + (long)this.internalGet(10);
                                                if (GregorianCalendar.isFieldSet(n, 9)) {
                                                    l = l4 + (long)(this.internalGet(9) * 12);
                                                }
                                            }
                                            l4 = ((l * 60L + (long)this.internalGet(12)) * 60L + (long)this.internalGet(13)) * 1000L + (long)this.internalGet(14);
                                            l = l4 / 86400000L;
                                            l2 = l4 % 86400000L;
                                            while (l2 < 0L) {
                                                l2 += 86400000L;
                                                --l;
                                            }
                                            if (n2 <= this.gregorianCutoverYear || n2 <= this.gregorianCutoverYearJulian) break block24;
                                            l4 = this.getFixedDate(gcal, n2, n) + l;
                                            if (l4 < this.gregorianCutoverDate) break block25;
                                            l = l4;
                                            break block26;
                                        }
                                        l = this.getFixedDate(GregorianCalendar.getJulianCalendarSystem(), n2, n) + l;
                                        break block27;
                                    }
                                    if (n2 >= this.gregorianCutoverYear || n2 >= this.gregorianCutoverYearJulian) break block28;
                                    l = this.getFixedDate(GregorianCalendar.getJulianCalendarSystem(), n2, n) + l;
                                    if (l < this.gregorianCutoverDate) break block26;
                                    l4 = l;
                                    break block27;
                                }
                                l3 = this.getFixedDate(GregorianCalendar.getJulianCalendarSystem(), n2, n) + l;
                                l4 = this.getFixedDate(gcal, n2, n) + l;
                                l = l3;
                            }
                            if (!GregorianCalendar.isFieldSet(n, 6) && !GregorianCalendar.isFieldSet(n, 3)) break block29;
                            n3 = this.gregorianCutoverYear;
                            if (n3 == this.gregorianCutoverYearJulian) break block26;
                            if (n2 != n3) break block29;
                            l = l4;
                            break block26;
                        }
                        if (l4 < (l3 = this.gregorianCutoverDate)) break block30;
                        if (l >= l3) {
                            l = l4;
                        } else {
                            object = this.calsys;
                            if (object == gcal || object == null) {
                                l = l4;
                            }
                        }
                        break block26;
                    }
                    if (l >= l3 && !this.isLenient()) break block31;
                }
                object = this.getZone();
                n2 = 98304 & n;
                this.time = this.adjustForZoneAndDaylightSavingsTime(n2, (l - 719163L) * 86400000L + l2, (TimeZone)object);
                n2 = this.computeFields(this.getSetStateFields() | n, n2);
                if (!this.isLenient()) {
                    for (n = 0; n < 17; ++n) {
                        if (!this.isExternallySet(n) || this.originalFields[n] == this.internalGet(n)) {
                            continue;
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append(this.originalFields[n]);
                        ((StringBuilder)object).append(" -> ");
                        ((StringBuilder)object).append(this.internalGet(n));
                        object = ((StringBuilder)object).toString();
                        System.arraycopy((Object)this.originalFields, 0, (Object)this.fields, 0, this.fields.length);
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(GregorianCalendar.getFieldName(n));
                        stringBuilder.append(": ");
                        stringBuilder.append((String)object);
                        throw new IllegalArgumentException(stringBuilder.toString());
                    }
                }
                this.setFieldsNormalized(n2);
                return;
            }
            throw new IllegalArgumentException("the specified date doesn't exist");
        }
        throw new IllegalArgumentException("Invalid era");
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = object instanceof GregorianCalendar && super.equals(object) && this.gregorianCutover == ((GregorianCalendar)object).gregorianCutover;
        return bl;
    }

    @Override
    public int getActualMaximum(int n) {
        if ((1 << n & 130689) != 0) {
            return this.getMaximum(n);
        }
        Object object = this.getNormalizedCalendar();
        BaseCalendar.Date date = ((GregorianCalendar)object).cdate;
        BaseCalendar baseCalendar = ((GregorianCalendar)object).calsys;
        int n2 = date.getNormalizedYear();
        switch (n) {
            default: {
                throw new ArrayIndexOutOfBoundsException(n);
            }
            case 8: {
                int n3 = date.getDayOfWeek();
                if (!((GregorianCalendar)object).isCutoverYear(n2)) {
                    object = (BaseCalendar.Date)date.clone();
                    n = baseCalendar.getMonthLength((CalendarDate)object);
                    ((CalendarDate)object).setDayOfMonth(1);
                    baseCalendar.normalize((CalendarDate)object);
                    n2 = ((CalendarDate)object).getDayOfWeek();
                } else {
                    GregorianCalendar gregorianCalendar = object;
                    if (object == this) {
                        gregorianCalendar = (GregorianCalendar)this.clone();
                    }
                    n = gregorianCalendar.actualMonthLength();
                    gregorianCalendar.set(5, gregorianCalendar.getActualMinimum(5));
                    n2 = gregorianCalendar.get(7);
                }
                n3 -= n2;
                n2 = n3;
                if (n3 < 0) {
                    n2 = n3 + 7;
                }
                n = (n - n2 + 6) / 7;
                break;
            }
            case 6: {
                long l;
                if (!((GregorianCalendar)object).isCutoverYear(n2)) {
                    n = baseCalendar.getYearLength(date);
                    break;
                }
                n = this.gregorianCutoverYear;
                int n4 = this.gregorianCutoverYearJulian;
                long l2 = n == n4 ? GregorianCalendar.super.getCutoverCalendarSystem().getFixedDate(n2, 1, 1, null) : (n2 == n4 ? baseCalendar.getFixedDate(n2, 1, 1, null) : this.gregorianCutoverDate);
                long l3 = l = gcal.getFixedDate(n2 + 1, 1, 1, null);
                if (l < this.gregorianCutoverDate) {
                    l3 = this.gregorianCutoverDate;
                }
                n = (int)(l3 - l2);
                break;
            }
            case 5: {
                long l;
                n = baseCalendar.getMonthLength(date);
                if (!((GregorianCalendar)object).isCutoverYear(n2) || date.getDayOfMonth() == n || (l = GregorianCalendar.super.getCurrentFixedDate()) >= this.gregorianCutoverDate) break;
                n = GregorianCalendar.super.actualMonthLength();
                n = GregorianCalendar.super.getCalendarDate(GregorianCalendar.super.getFixedDateMonth1(((GregorianCalendar)object).cdate, l) + (long)n - 1L).getDayOfMonth();
                break;
            }
            case 4: {
                if (!((GregorianCalendar)object).isCutoverYear(n2)) {
                    object = baseCalendar.newCalendarDate(null);
                    ((CalendarDate)object).setDate(date.getYear(), date.getMonth(), 1);
                    n = baseCalendar.getDayOfWeek((CalendarDate)object);
                    int n5 = baseCalendar.getMonthLength((CalendarDate)object);
                    n = n2 = n - this.getFirstDayOfWeek();
                    if (n2 < 0) {
                        n = n2 + 7;
                    }
                    n = (n2 = 7 - n) >= this.getMinimalDaysInFirstWeek() ? 3 + 1 : 3;
                    if ((n2 = n5 - (n2 + 21)) <= 0) break;
                    ++n;
                    if (n2 <= 7) break;
                    ++n;
                    break;
                }
                GregorianCalendar gregorianCalendar = object;
                if (object == this) {
                    gregorianCalendar = (GregorianCalendar)((GregorianCalendar)object).clone();
                }
                n2 = gregorianCalendar.internalGet(1);
                int n6 = gregorianCalendar.internalGet(2);
                do {
                    n = gregorianCalendar.get(4);
                    gregorianCalendar.add(4, 1);
                } while (gregorianCalendar.get(1) == n2 && gregorianCalendar.get(2) == n6);
                break;
            }
            case 3: {
                if (!((GregorianCalendar)object).isCutoverYear(n2)) {
                    object = baseCalendar.newCalendarDate(TimeZone.NO_TIMEZONE);
                    ((CalendarDate)object).setDate(date.getYear(), 1, 1);
                    n = n2 = baseCalendar.getDayOfWeek((CalendarDate)object) - this.getFirstDayOfWeek();
                    if (n2 < 0) {
                        n = n2 + 7;
                    }
                    if ((n = this.getMinimalDaysInFirstWeek() + n - 1) != 6 && (!date.isLeapYear() || n != 5 && n != 12)) {
                        n = 52;
                        break;
                    }
                    n = 52 + 1;
                    break;
                }
                GregorianCalendar gregorianCalendar = object;
                if (object == this) {
                    gregorianCalendar = (GregorianCalendar)((GregorianCalendar)object).clone();
                }
                n2 = this.getActualMaximum(6);
                gregorianCalendar.set(6, n2);
                n = gregorianCalendar.get(3);
                if (this.internalGet(1) == gregorianCalendar.getWeekYear()) break;
                gregorianCalendar.set(6, n2 - 7);
                n = gregorianCalendar.get(3);
                break;
            }
            case 2: {
                long l;
                GregorianCalendar gregorianCalendar = object;
                n = n2;
                if (!((GregorianCalendar)object).isCutoverYear(n2)) {
                    n = 11;
                    break;
                }
                while ((l = ((BaseCalendar)(object = gcal)).getFixedDate(++n, 1, 1, null)) < this.gregorianCutoverDate) {
                }
                object = (BaseCalendar.Date)date.clone();
                baseCalendar.getCalendarDateFromFixedDate((CalendarDate)object, l - 1L);
                n = ((CalendarDate)object).getMonth() - 1;
                break;
            }
            case 1: {
                if (object == this) {
                    object = (GregorianCalendar)this.clone();
                }
                long l = ((GregorianCalendar)object).getYearOffsetInMillis();
                if (((GregorianCalendar)object).internalGetEra() == 1) {
                    ((Calendar)object).setTimeInMillis(Long.MAX_VALUE);
                    n = n2 = ((Calendar)object).get(1);
                    if (l <= GregorianCalendar.super.getYearOffsetInMillis()) break;
                    n = n2 - 1;
                    break;
                }
                object = ((Calendar)object).getTimeInMillis() >= this.gregorianCutover ? gcal : GregorianCalendar.getJulianCalendarSystem();
                object = ((CalendarSystem)object).getCalendarDate(Long.MIN_VALUE, this.getZone());
                long l4 = baseCalendar.getDayOfYear((CalendarDate)object);
                long l5 = ((CalendarDate)object).getHours();
                long l6 = ((CalendarDate)object).getMinutes();
                long l7 = ((CalendarDate)object).getSeconds();
                long l8 = ((CalendarDate)object).getMillis();
                n = n2 = ((CalendarDate)object).getYear();
                if (n2 <= 0) {
                    n = 1 - n2;
                }
                if (l >= ((((l4 - 1L) * 24L + l5) * 60L + l6) * 60L + l7) * 1000L + l8) break;
                --n;
                break;
            }
        }
        return n;
    }

    @Override
    public int getActualMinimum(int n) {
        if (n == 5) {
            GregorianCalendar gregorianCalendar = this.getNormalizedCalendar();
            int n2 = gregorianCalendar.cdate.getNormalizedYear();
            if (n2 == this.gregorianCutoverYear || n2 == this.gregorianCutoverYearJulian) {
                BaseCalendar.Date date = gregorianCalendar.cdate;
                return this.getCalendarDate(this.getFixedDateMonth1(date, gregorianCalendar.calsys.getFixedDate(date))).getDayOfMonth();
            }
        }
        return this.getMinimum(n);
    }

    @Override
    public String getCalendarType() {
        return "gregory";
    }

    @Override
    public int getGreatestMinimum(int n) {
        if (n == 5) {
            BaseCalendar.Date date = this.getCalendarDate(this.getFixedDateMonth1(this.getGregorianCutoverDate(), this.gregorianCutoverDate));
            return Math.max(MIN_VALUES[n], date.getDayOfMonth());
        }
        return MIN_VALUES[n];
    }

    public final Date getGregorianChange() {
        return new Date(this.gregorianCutover);
    }

    @Override
    public int getLeastMaximum(int n) {
        switch (n) {
            default: {
                return LEAST_MAX_VALUES[n];
            }
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 8: 
        }
        GregorianCalendar gregorianCalendar = (GregorianCalendar)this.clone();
        gregorianCalendar.setLenient(true);
        gregorianCalendar.setTimeInMillis(this.gregorianCutover);
        int n2 = gregorianCalendar.getActualMaximum(n);
        gregorianCalendar.setTimeInMillis(this.gregorianCutover - 1L);
        int n3 = gregorianCalendar.getActualMaximum(n);
        return Math.min(LEAST_MAX_VALUES[n], Math.min(n2, n3));
    }

    @Override
    public int getMaximum(int n) {
        switch (n) {
            default: {
                break;
            }
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 8: {
                if (this.gregorianCutoverYear > 200) break;
                GregorianCalendar gregorianCalendar = (GregorianCalendar)this.clone();
                gregorianCalendar.setLenient(true);
                gregorianCalendar.setTimeInMillis(this.gregorianCutover);
                int n2 = gregorianCalendar.getActualMaximum(n);
                gregorianCalendar.setTimeInMillis(this.gregorianCutover - 1L);
                int n3 = gregorianCalendar.getActualMaximum(n);
                return Math.max(MAX_VALUES[n], Math.max(n2, n3));
            }
        }
        return MAX_VALUES[n];
    }

    @Override
    public int getMinimum(int n) {
        return MIN_VALUES[n];
    }

    @Override
    public TimeZone getTimeZone() {
        TimeZone timeZone = super.getTimeZone();
        this.gdate.setZone(timeZone);
        BaseCalendar.Date date = this.cdate;
        if (date != null && date != this.gdate) {
            date.setZone(timeZone);
        }
        return timeZone;
    }

    @Override
    public int getWeekYear() {
        int n;
        int n2 = n = this.get(1);
        if (this.internalGetEra() == 0) {
            n2 = 1 - n;
        }
        if (n2 > this.gregorianCutoverYear + 1) {
            int n3 = this.internalGet(3);
            if (this.internalGet(2) == 0) {
                n = n2;
                if (n3 >= 52) {
                    n = n2 - 1;
                }
            } else {
                n = n2;
                if (n3 == 1) {
                    n = n2 + 1;
                }
            }
            return n;
        }
        int n4 = this.internalGet(6);
        int n5 = this.getActualMaximum(6);
        int n6 = this.getMinimalDaysInFirstWeek();
        if (n4 > n6 && n4 < n5 - 6) {
            return n2;
        }
        GregorianCalendar gregorianCalendar = (GregorianCalendar)this.clone();
        gregorianCalendar.setLenient(true);
        gregorianCalendar.setTimeZone(TimeZone.getTimeZone("GMT"));
        gregorianCalendar.set(6, 1);
        gregorianCalendar.complete();
        int n7 = this.getFirstDayOfWeek() - gregorianCalendar.get(7);
        if (n7 != 0) {
            n = n7;
            if (n7 < 0) {
                n = n7 + 7;
            }
            gregorianCalendar.add(6, n);
        }
        if (n4 < (n7 = gregorianCalendar.get(6))) {
            n = n2;
            if (n7 <= n6) {
                n = n2 - 1;
            }
        } else {
            gregorianCalendar.set(1, n2 + 1);
            gregorianCalendar.set(6, 1);
            gregorianCalendar.complete();
            n7 = this.getFirstDayOfWeek() - gregorianCalendar.get(7);
            if (n7 != 0) {
                n = n7;
                if (n7 < 0) {
                    n = n7 + 7;
                }
                gregorianCalendar.add(6, n);
            }
            if ((n7 = gregorianCalendar.get(6) - 1) == 0) {
                n7 = 7;
            }
            n = n2;
            if (n7 >= n6) {
                n = n2;
                if (n5 - n4 + 1 <= 7 - n7) {
                    n = n2 + 1;
                }
            }
        }
        return n;
    }

    @Override
    public int getWeeksInWeekYear() {
        GregorianCalendar gregorianCalendar = this.getNormalizedCalendar();
        int n = gregorianCalendar.getWeekYear();
        if (n == gregorianCalendar.internalGet(1)) {
            return gregorianCalendar.getActualMaximum(3);
        }
        GregorianCalendar gregorianCalendar2 = gregorianCalendar;
        if (gregorianCalendar == this) {
            gregorianCalendar2 = (GregorianCalendar)gregorianCalendar.clone();
        }
        gregorianCalendar2.setWeekDate(n, 2, this.internalGet(7));
        return gregorianCalendar2.getActualMaximum(3);
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ (int)this.gregorianCutoverDate;
    }

    public boolean isLeapYear(int n) {
        boolean bl = false;
        if ((n & 3) != 0) {
            return false;
        }
        int n2 = this.gregorianCutoverYear;
        boolean bl2 = true;
        if (n > n2) {
            if (n % 100 != 0 || n % 400 == 0) {
                bl = true;
            }
            return bl;
        }
        int n3 = this.gregorianCutoverYearJulian;
        if (n < n3) {
            return true;
        }
        n3 = n2 == n3 ? (this.getCalendarDate(this.gregorianCutoverDate).getMonth() < 3 ? 1 : 0) : (n == n2 ? 1 : 0);
        bl = bl2;
        if (n3 != 0) {
            bl = bl2;
            if (n % 100 == 0) {
                bl = n % 400 == 0 ? bl2 : false;
            }
        }
        return bl;
    }

    @Override
    public final boolean isWeekDateSupported() {
        return true;
    }

    @Override
    public void roll(int n, int n2) {
        int n3 = n2;
        if (n3 == 0) {
            return;
        }
        if (n >= 0 && n < 15) {
            this.complete();
            n2 = this.getMinimum(n);
            int n4 = this.getMaximum(n);
            switch (n) {
                default: {
                    break;
                }
                case 10: 
                case 11: {
                    int n5 = n4 + 1;
                    int n6 = this.internalGet(n);
                    n2 = n4 = (n6 + n3) % n5;
                    if (n4 < 0) {
                        n2 = n4 + n5;
                    }
                    this.time += (long)((n2 - n6) * 3600000);
                    CalendarDate calendarDate = this.calsys.getCalendarDate(this.time, this.getZone());
                    if (this.internalGet(5) != calendarDate.getDayOfMonth()) {
                        calendarDate.setDate(this.internalGet(1), this.internalGet(2) + 1, this.internalGet(5));
                        if (n == 10) {
                            calendarDate.addHours(12);
                        }
                        this.time = this.calsys.getTime(calendarDate);
                    }
                    n2 = calendarDate.getHours();
                    this.internalSet(n, n2 % n5);
                    if (n == 10) {
                        this.internalSet(11, n2);
                    } else {
                        this.internalSet(9, n2 / 12);
                        this.internalSet(10, n2 % 12);
                    }
                    n2 = calendarDate.getZoneOffset();
                    n = calendarDate.getDaylightSaving();
                    this.internalSet(15, n2 - n);
                    this.internalSet(16, n);
                    return;
                }
                case 8: {
                    n2 = 1;
                    if (!this.isCutoverYear(this.cdate.getNormalizedYear())) {
                        int n7;
                        int n8 = this.internalGet(5);
                        int n9 = this.calsys.getMonthLength(this.cdate);
                        n4 = n7 = n9 / 7;
                        if ((n8 - 1) % 7 < n9 % 7) {
                            n4 = n7 + 1;
                        }
                        this.set(7, this.internalGet(7));
                        break;
                    }
                    long l = this.getCurrentFixedDate();
                    long l2 = this.getFixedDateMonth1(this.cdate, l);
                    int n10 = this.actualMonthLength();
                    n4 = n10 / 7;
                    int n11 = (int)(l - l2) % 7;
                    n2 = n4;
                    if (n11 < n10 % 7) {
                        n2 = n4 + 1;
                    }
                    BaseCalendar baseCalendar = (l2 = (long)((GregorianCalendar.getRolledValue(this.internalGet(n), n3, 1, n2) - 1) * 7) + l2 + (long)n11) >= this.gregorianCutoverDate ? gcal : GregorianCalendar.getJulianCalendarSystem();
                    BaseCalendar.Date date = (BaseCalendar.Date)baseCalendar.newCalendarDate(TimeZone.NO_TIMEZONE);
                    baseCalendar.getCalendarDateFromFixedDate(date, l2);
                    this.set(5, date.getDayOfMonth());
                    return;
                }
                case 7: {
                    long l;
                    if (!this.isCutoverYear(this.cdate.getNormalizedYear()) && (n4 = this.internalGet(3)) > 1 && n4 < 52) {
                        this.set(3, n4);
                        n4 = 7;
                        break;
                    }
                    n = n3 % 7;
                    if (n == 0) {
                        return;
                    }
                    long l3 = this.getCurrentFixedDate();
                    long l4 = l3 + (long)n;
                    if (l4 < (l = BaseCalendar.getDayOfWeekDateOnOrBefore(l3, this.getFirstDayOfWeek()))) {
                        l3 = l4 + 7L;
                    } else {
                        l3 = l4;
                        if (l4 >= l + 7L) {
                            l3 = l4 - 7L;
                        }
                    }
                    BaseCalendar.Date date = this.getCalendarDate(l3);
                    n = date.getNormalizedYear() <= 0 ? 0 : 1;
                    this.set(0, n);
                    this.set(date.getYear(), date.getMonth() - 1, date.getDayOfMonth());
                    return;
                }
                case 6: {
                    n4 = this.getActualMaximum(n);
                    if (!this.isCutoverYear(this.cdate.getNormalizedYear())) break;
                    long l = this.getCurrentFixedDate();
                    long l5 = l - (long)this.internalGet(6) + 1L;
                    n = GregorianCalendar.getRolledValue((int)(l - l5) + 1, n3, n2, n4);
                    BaseCalendar.Date date = this.getCalendarDate((long)n + l5 - 1L);
                    this.set(2, date.getMonth() - 1);
                    this.set(5, date.getDayOfMonth());
                    return;
                }
                case 5: {
                    if (!this.isCutoverYear(this.cdate.getNormalizedYear())) {
                        n4 = this.calsys.getMonthLength(this.cdate);
                        break;
                    }
                    long l = this.getCurrentFixedDate();
                    long l6 = this.getFixedDateMonth1(this.cdate, l);
                    BaseCalendar.Date date = this.getCalendarDate((long)GregorianCalendar.getRolledValue((int)(l - l6), n3, 0, this.actualMonthLength() - 1) + l6);
                    this.set(5, date.getDayOfMonth());
                    return;
                }
                case 4: {
                    long l;
                    boolean bl = this.isCutoverYear(this.cdate.getNormalizedYear());
                    n2 = n4 = this.internalGet(7) - this.getFirstDayOfWeek();
                    if (n4 < 0) {
                        n2 = n4 + 7;
                    }
                    long l7 = this.getCurrentFixedDate();
                    if (bl) {
                        l7 = this.getFixedDateMonth1(this.cdate, l7);
                        n4 = this.actualMonthLength();
                    } else {
                        l7 = l7 - (long)this.internalGet(5) + 1L;
                        n4 = this.calsys.getMonthLength(this.cdate);
                    }
                    long l8 = l = BaseCalendar.getDayOfWeekDateOnOrBefore(6L + l7, this.getFirstDayOfWeek());
                    if ((int)(l - l7) >= this.getMinimalDaysInFirstWeek()) {
                        l8 = l - 7L;
                    }
                    int n12 = this.getActualMaximum(n);
                    l = (long)((GregorianCalendar.getRolledValue(this.internalGet(n), n3, 1, n12) - 1) * 7) + l8 + (long)n2;
                    if (l < l7) {
                        l8 = l7;
                    } else {
                        l8 = l;
                        if (l >= (long)n4 + l7) {
                            l8 = (long)n4 + l7 - 1L;
                        }
                    }
                    n = bl ? this.getCalendarDate(l8).getDayOfMonth() : (int)(l8 - l7) + 1;
                    this.set(5, n);
                    return;
                }
                case 3: {
                    int n13 = this.cdate.getNormalizedYear();
                    n4 = this.getActualMaximum(3);
                    this.set(7, this.internalGet(7));
                    int n14 = this.internalGet(3);
                    int n15 = n14 + n3;
                    if (!this.isCutoverYear(n13)) {
                        int n16 = this.getWeekYear();
                        if (n16 == n13) {
                            n16 = n2;
                            if (n15 > n16 && n15 < n4) {
                                this.set(3, n15);
                                return;
                            }
                            long l = this.getCurrentFixedDate();
                            long l9 = (n14 - n16) * 7;
                            n2 = n16;
                            if (this.calsys.getYearFromFixedDate(l - l9) != n13) {
                                n2 = n16 + 1;
                            }
                            l9 = (n4 - this.internalGet(3)) * 7;
                            n16 = n4;
                            if (this.calsys.getYearFromFixedDate(l + l9) != n13) {
                                n16 = n4 - 1;
                            }
                            n4 = n16;
                        } else if (n16 > n13) {
                            n16 = n3;
                            if (n3 < 0) {
                                n16 = n3 + 1;
                            }
                            n14 = n4;
                            n3 = n16;
                        } else {
                            n16 = n3;
                            if (n3 > 0) {
                                n16 = n3 - (n14 - n4);
                            }
                            n14 = n2;
                            n3 = n16;
                        }
                        this.set(n, GregorianCalendar.getRolledValue(n14, n3, n2, n4));
                        return;
                    }
                    long l = this.getCurrentFixedDate();
                    n = this.gregorianCutoverYear;
                    Object object = n == this.gregorianCutoverYearJulian ? this.getCutoverCalendarSystem() : (n13 == n ? gcal : GregorianCalendar.getJulianCalendarSystem());
                    long l10 = l - (long)((n14 - n2) * 7);
                    n = n2;
                    if (((BaseCalendar)object).getYearFromFixedDate(l10) != n13) {
                        n = n2 + 1;
                    }
                    object = (l += (long)((n4 - n14) * 7)) >= this.gregorianCutoverDate ? gcal : GregorianCalendar.getJulianCalendarSystem();
                    n2 = n4;
                    if (((BaseCalendar)object).getYearFromFixedDate(l) != n13) {
                        n2 = n4 - 1;
                    }
                    object = this.getCalendarDate((long)((GregorianCalendar.getRolledValue(n14, n3, n, n2) - 1) * 7) + l10);
                    this.set(2, ((CalendarDate)object).getMonth() - 1);
                    this.set(5, ((CalendarDate)object).getDayOfMonth());
                    return;
                }
                case 2: {
                    if (!this.isCutoverYear(this.cdate.getNormalizedYear())) {
                        n = n2 = (this.internalGet(2) + n3) % 12;
                        if (n2 < 0) {
                            n = n2 + 12;
                        }
                        this.set(2, n);
                        n = this.monthLength(n);
                        if (this.internalGet(5) > n) {
                            this.set(5, n);
                        }
                    } else {
                        n4 = this.getActualMaximum(2) + 1;
                        n = n2 = (this.internalGet(2) + n3) % n4;
                        if (n2 < 0) {
                            n = n2 + n4;
                        }
                        this.set(2, n);
                        n = this.getActualMaximum(5);
                        if (this.internalGet(5) > n) {
                            this.set(5, n);
                        }
                    }
                    return;
                }
                case 0: 
                case 1: 
                case 9: 
                case 12: 
                case 13: 
                case 14: 
            }
            this.set(n, GregorianCalendar.getRolledValue(this.internalGet(n), n3, n2, n4));
            return;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public void roll(int n, boolean bl) {
        int n2 = bl ? 1 : -1;
        this.roll(n, n2);
    }

    public void setGregorianChange(Date date) {
        long l = date.getTime();
        if (l == this.gregorianCutover) {
            return;
        }
        this.complete();
        this.setGregorianChange(l);
    }

    @Override
    public void setTimeZone(TimeZone timeZone) {
        super.setTimeZone(timeZone);
        this.gdate.setZone(timeZone);
        BaseCalendar.Date date = this.cdate;
        if (date != null && date != this.gdate) {
            date.setZone(timeZone);
        }
    }

    @Override
    public void setWeekDate(int n, int n2, int n3) {
        if (n3 >= 1 && n3 <= 7) {
            int n4;
            GregorianCalendar gregorianCalendar = (GregorianCalendar)this.clone();
            gregorianCalendar.setLenient(true);
            int n5 = gregorianCalendar.get(0);
            gregorianCalendar.clear();
            gregorianCalendar.setTimeZone(TimeZone.getTimeZone("GMT"));
            gregorianCalendar.set(0, n5);
            gregorianCalendar.set(1, n);
            gregorianCalendar.set(3, 1);
            gregorianCalendar.set(7, this.getFirstDayOfWeek());
            n5 = n4 = n3 - this.getFirstDayOfWeek();
            if (n4 < 0) {
                n5 = n4 + 7;
            }
            if ((n5 += (n2 - 1) * 7) != 0) {
                gregorianCalendar.add(6, n5);
            } else {
                gregorianCalendar.complete();
            }
            if (!(this.isLenient() || gregorianCalendar.getWeekYear() == n && gregorianCalendar.internalGet(3) == n2 && gregorianCalendar.internalGet(7) == n3)) {
                throw new IllegalArgumentException();
            }
            this.set(0, gregorianCalendar.internalGet(0));
            this.set(1, gregorianCalendar.internalGet(1));
            this.set(2, gregorianCalendar.internalGet(2));
            this.set(5, gregorianCalendar.internalGet(5));
            this.internalSet(3, n2);
            this.complete();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("invalid dayOfWeek: ");
        stringBuilder.append(n3);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public ZonedDateTime toZonedDateTime() {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(this.getTimeInMillis()), this.getTimeZone().toZoneId());
    }
}

