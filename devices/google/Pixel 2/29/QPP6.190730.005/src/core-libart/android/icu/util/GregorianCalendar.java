/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.icu.util.ULocale;
import java.util.Date;
import java.util.Locale;

public class GregorianCalendar
extends Calendar {
    public static final int AD = 1;
    public static final int BC = 0;
    private static final int EPOCH_YEAR = 1970;
    private static final int[][] LIMITS;
    private static final int[][] MONTH_COUNT;
    private static final long serialVersionUID = 9199388694351062137L;
    private transient int cutoverJulianDay = 2299161;
    private long gregorianCutover = -12219292800000L;
    private transient int gregorianCutoverYear = 1582;
    protected transient boolean invertGregorian;
    protected transient boolean isGregorian;

    static {
        int[] arrn = new int[]{30, 30, 90, 91};
        int[] arrn2 = new int[]{31, 31, 181, 182};
        int[] arrn3 = new int[]{31, 31, 212, 213};
        int[] arrn4 = new int[]{30, 30, 243, 244};
        int[] arrn5 = new int[]{31, 31, 273, 274};
        MONTH_COUNT = new int[][]{{31, 31, 0, 0}, {28, 29, 31, 31}, {31, 31, 59, 60}, arrn, {31, 31, 120, 121}, {30, 30, 151, 152}, arrn2, arrn3, arrn4, arrn5, {30, 30, 304, 305}, {31, 31, 334, 335}};
        arrn = new int[]{0, 0, 1, 1};
        arrn2 = new int[]{1, 1, 5828963, 5838270};
        arrn3 = new int[]{0, 0, 11, 11};
        arrn4 = new int[]{1, 1, 52, 53};
        arrn5 = new int[]{};
        int[] arrn6 = new int[]{1, 1, 28, 31};
        int[] arrn7 = new int[]{1, 1, 365, 366};
        int[] arrn8 = new int[]{};
        int[] arrn9 = new int[]{-1, -1, 4, 5};
        int[] arrn10 = new int[]{};
        int[] arrn11 = new int[]{};
        int[] arrn12 = new int[]{};
        int[] arrn13 = new int[]{};
        int[] arrn14 = new int[]{};
        int[] arrn15 = new int[]{};
        int[] arrn16 = new int[]{};
        int[] arrn17 = new int[]{-5838270, -5838270, 5828964, 5838271};
        int[] arrn18 = new int[]{};
        int[] arrn19 = new int[]{-5838269, -5838269, 5828963, 5838270};
        int[] arrn20 = new int[]{};
        int[] arrn21 = new int[]{};
        int[] arrn22 = new int[]{};
        LIMITS = new int[][]{arrn, arrn2, arrn3, arrn4, arrn5, arrn6, arrn7, arrn8, arrn9, arrn10, arrn11, arrn12, arrn13, new int[0], arrn14, arrn15, arrn16, arrn17, arrn18, arrn19, arrn20, arrn21, arrn22};
    }

    public GregorianCalendar() {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public GregorianCalendar(int n, int n2, int n3) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.set(0, 1);
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
    }

    public GregorianCalendar(int n, int n2, int n3, int n4, int n5) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.set(0, 1);
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
        this.set(11, n4);
        this.set(12, n5);
    }

    public GregorianCalendar(int n, int n2, int n3, int n4, int n5, int n6) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.set(0, 1);
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
        this.set(11, n4);
        this.set(12, n5);
        this.set(13, n6);
    }

    public GregorianCalendar(TimeZone timeZone) {
        this(timeZone, ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public GregorianCalendar(TimeZone timeZone, ULocale uLocale) {
        super(timeZone, uLocale);
        this.setTimeInMillis(System.currentTimeMillis());
    }

    public GregorianCalendar(TimeZone timeZone, Locale locale) {
        super(timeZone, locale);
        this.setTimeInMillis(System.currentTimeMillis());
    }

    public GregorianCalendar(ULocale uLocale) {
        this(TimeZone.getDefault(), uLocale);
    }

    public GregorianCalendar(Locale locale) {
        this(TimeZone.getDefault(), locale);
    }

    @Override
    public int getActualMaximum(int n) {
        if (n != 1) {
            return super.getActualMaximum(n);
        }
        Calendar calendar = (Calendar)this.clone();
        calendar.setLenient(true);
        int n2 = calendar.get(0);
        Date date = calendar.getTime();
        int[][] arrn = LIMITS;
        n = arrn[1][1];
        int n3 = arrn[1][2] + 1;
        while (n + 1 < n3) {
            int n4 = (n + n3) / 2;
            calendar.set(1, n4);
            if (calendar.get(1) == n4 && calendar.get(0) == n2) {
                n = n4;
                continue;
            }
            n3 = n4;
            calendar.setTime(date);
        }
        return n;
    }

    @Override
    public int getActualMinimum(int n) {
        return this.getMinimum(n);
    }

    public final Date getGregorianChange() {
        return new Date(this.gregorianCutover);
    }

    @Override
    public String getType() {
        return "gregorian";
    }

    @Override
    protected void handleComputeFields(int n) {
        int n2;
        int n3;
        int n4;
        if (n >= this.cutoverJulianDay) {
            n3 = this.getGregorianMonth();
            n4 = this.getGregorianDayOfMonth();
            n2 = this.getGregorianDayOfYear();
            n = this.getGregorianYear();
        } else {
            long l = n - 1721424;
            int n5 = (int)GregorianCalendar.floorDivide(l * 4L + 1464L, 1461L);
            int n6 = (int)(l - (((long)n5 - 1L) * 365L + GregorianCalendar.floorDivide((long)n5 - 1L, 4L)));
            n3 = (n5 & 3) == 0 ? 1 : 0;
            n4 = 0;
            n = n3 != 0 ? 60 : 59;
            if (n6 >= n) {
                n = n3 != 0 ? 1 : 2;
                n4 = n;
            }
            n4 = ((n6 + n4) * 12 + 6) / 367;
            int[] arrn = MONTH_COUNT[n4];
            n = n3 != 0 ? 3 : 2;
            n = arrn[n];
            n2 = n6 + 1;
            n = n6 - n + 1;
            n3 = n4;
            n4 = n;
            n = n5;
        }
        this.internalSet(2, n3);
        this.internalSet(5, n4);
        this.internalSet(6, n2);
        this.internalSet(19, n);
        n4 = 1;
        n3 = n;
        if (n < 1) {
            n4 = 0;
            n3 = 1 - n;
        }
        this.internalSet(0, n4);
        this.internalSet(1, n3);
    }

    @Override
    protected int handleComputeJulianDay(int n) {
        boolean bl = false;
        this.invertGregorian = false;
        int n2 = super.handleComputeJulianDay(n);
        boolean bl2 = this.isGregorian;
        if (n2 >= this.cutoverJulianDay) {
            bl = true;
        }
        if (bl2 != bl) {
            this.invertGregorian = true;
            n2 = super.handleComputeJulianDay(n);
        }
        return n2;
    }

    @Override
    protected int handleComputeMonthStart(int n, int n2, boolean bl) {
        int n3;
        int n4;
        int n5;
        int[] arrn;
        block11 : {
            int n6;
            int n7;
            block12 : {
                block13 : {
                    int n8;
                    int n9;
                    block10 : {
                        block9 : {
                            n8 = 0;
                            if (n2 < 0) break block9;
                            n9 = n;
                            n4 = n2;
                            if (n2 <= 11) break block10;
                        }
                        arrn = new int[1];
                        n9 = n + GregorianCalendar.floorDivide(n2, 12, arrn);
                        n4 = arrn[0];
                    }
                    n2 = n9 % 4 == 0 ? 1 : 0;
                    n6 = n9 - 1;
                    n7 = n6 * 365 + GregorianCalendar.floorDivide(n6, 4) + 1721423;
                    bl = n9 >= this.gregorianCutoverYear;
                    this.isGregorian = bl;
                    if (this.invertGregorian) {
                        this.isGregorian ^= true;
                    }
                    bl = this.isGregorian;
                    n3 = 2;
                    n5 = n2;
                    n = n7;
                    if (!bl) break block11;
                    n = n8;
                    if (n2 == 0) break block12;
                    if (n9 % 100 != 0) break block13;
                    n = n8;
                    if (n9 % 400 != 0) break block12;
                }
                n = 1;
            }
            n5 = n;
            n = n7 + (GregorianCalendar.floorDivide(n6, 400) - GregorianCalendar.floorDivide(n6, 100) + 2);
        }
        n2 = n;
        if (n4 != 0) {
            arrn = MONTH_COUNT[n4];
            n2 = n3;
            if (n5 != 0) {
                n2 = 3;
            }
            n2 = n + arrn[n2];
        }
        return n2;
    }

    @Override
    protected int handleGetExtendedYear() {
        int n = this.newerField(19, 1) == 19 ? this.internalGet(19, 1970) : (this.internalGet(0, 1) == 0 ? 1 - this.internalGet(1, 1) : this.internalGet(1, 1970));
        return n;
    }

    @Override
    protected int handleGetLimit(int n, int n2) {
        return LIMITS[n][n2];
    }

    @Override
    protected int handleGetMonthLength(int n, int n2) {
        int n3;
        int n4;
        block3 : {
            block2 : {
                if (n2 < 0) break block2;
                n4 = n;
                n3 = n2;
                if (n2 <= 11) break block3;
            }
            int[] arrn = new int[1];
            n4 = n + GregorianCalendar.floorDivide(n2, 12, arrn);
            n3 = arrn[0];
        }
        return MONTH_COUNT[n3][this.isLeapYear(n4)];
    }

    @Override
    protected int handleGetYearLength(int n) {
        n = this.isLeapYear(n) ? 366 : 365;
        return n;
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ (int)this.gregorianCutover;
    }

    boolean inDaylightTime() {
        boolean bl = this.getTimeZone().useDaylightTime();
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        this.complete();
        if (this.internalGet(16) != 0) {
            bl2 = true;
        }
        return bl2;
    }

    @Override
    public boolean isEquivalentTo(Calendar calendar) {
        boolean bl = super.isEquivalentTo(calendar) && this.gregorianCutover == ((GregorianCalendar)calendar).gregorianCutover;
        return bl;
    }

    public boolean isLeapYear(int n) {
        int n2 = this.gregorianCutoverYear;
        boolean bl = true;
        if (n >= n2) {
            if (n % 4 != 0 || n % 100 == 0 && n % 400 != 0) {
                bl = false;
            }
        } else if (n % 4 != 0) {
            bl = false;
        }
        return bl;
    }

    @Override
    public void roll(int n, int n2) {
        int n3;
        block12 : {
            int n4;
            int n5;
            block11 : {
                if (n != 3) {
                    super.roll(n, n2);
                    return;
                }
                n5 = this.get(3);
                n3 = this.get(17);
                n4 = this.internalGet(6);
                if (this.internalGet(2) == 0) {
                    n = n4;
                    if (n5 >= 52) {
                        n = n4 + this.handleGetYearLength(n3);
                    }
                } else {
                    n = n4;
                    if (n5 == 1) {
                        n = n4 - this.handleGetYearLength(n3 - 1);
                    }
                }
                if ((n4 = n5 + n2) < 1) break block11;
                n2 = n4;
                if (n4 <= 52) break block12;
            }
            n5 = this.handleGetYearLength(n3);
            n = n2 = (n5 - n + this.internalGet(7) - this.getFirstDayOfWeek()) % 7;
            if (n2 < 0) {
                n = n2 + 7;
            }
            n2 = n5;
            if (6 - n >= this.getMinimalDaysInFirstWeek()) {
                n2 = n5 - 7;
            }
            n = this.weekNumber(n2, n + 1);
            n2 = (n4 + n - 1) % n + 1;
        }
        this.set(3, n2);
        this.set(1, n3);
    }

    public void setGregorianChange(Date date) {
        this.gregorianCutover = date.getTime();
        long l = this.gregorianCutover;
        if (l <= -184303902528000000L) {
            this.cutoverJulianDay = Integer.MIN_VALUE;
            this.gregorianCutoverYear = Integer.MIN_VALUE;
        } else if (l >= 183882168921600000L) {
            this.cutoverJulianDay = Integer.MAX_VALUE;
            this.gregorianCutoverYear = Integer.MAX_VALUE;
        } else {
            this.cutoverJulianDay = (int)GregorianCalendar.floorDivide(l, 86400000L);
            GregorianCalendar gregorianCalendar = new GregorianCalendar(this.getTimeZone());
            gregorianCalendar.setTime(date);
            this.gregorianCutoverYear = gregorianCalendar.get(19);
        }
    }
}

