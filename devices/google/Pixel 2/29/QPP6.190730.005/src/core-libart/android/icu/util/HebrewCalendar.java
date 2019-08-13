/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.impl.CalendarCache;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.icu.util.ULocale;
import java.util.Date;
import java.util.Locale;

public class HebrewCalendar
extends Calendar {
    public static final int ADAR = 6;
    public static final int ADAR_1 = 5;
    public static final int AV = 11;
    private static final long BAHARAD = 12084L;
    private static final long DAY_PARTS = 25920L;
    public static final int ELUL = 12;
    public static final int HESHVAN = 1;
    private static final long HOUR_PARTS = 1080L;
    public static final int IYAR = 8;
    public static final int KISLEV = 2;
    private static final int[][] LEAP_MONTH_START;
    private static final int[][] LIMITS;
    private static final int MONTH_DAYS = 29;
    private static final long MONTH_FRACT = 13753L;
    private static final int[][] MONTH_LENGTH;
    private static final long MONTH_PARTS = 765433L;
    private static final int[][] MONTH_START;
    public static final int NISAN = 7;
    public static final int SHEVAT = 4;
    public static final int SIVAN = 9;
    public static final int TAMUZ = 10;
    public static final int TEVET = 3;
    public static final int TISHRI = 0;
    private static CalendarCache cache;
    private static final long serialVersionUID = -1952524560588825816L;

    static {
        int[] arrn = new int[]{};
        int[] arrn2 = new int[]{-5000000, -5000000, 5000000, 5000000};
        LIMITS = new int[][]{{0, 0, 0, 0}, {-5000000, -5000000, 5000000, 5000000}, {0, 0, 12, 12}, {1, 1, 51, 56}, new int[0], {1, 1, 29, 30}, {1, 1, 353, 385}, new int[0], {-1, -1, 5, 5}, new int[0], new int[0], new int[0], new int[0], arrn, new int[0], new int[0], new int[0], {-5000000, -5000000, 5000000, 5000000}, new int[0], arrn2, new int[0], new int[0]};
        arrn = new int[]{30, 30, 30};
        arrn2 = new int[]{30, 30, 30};
        MONTH_LENGTH = new int[][]{{30, 30, 30}, {29, 29, 30}, {29, 30, 30}, {29, 29, 29}, {30, 30, 30}, arrn, {29, 29, 29}, arrn2, {29, 29, 29}, {30, 30, 30}, {29, 29, 29}, {30, 30, 30}, {29, 29, 29}};
        arrn = new int[]{235, 236, 237};
        MONTH_START = new int[][]{{0, 0, 0}, {30, 30, 30}, {59, 59, 60}, {88, 89, 90}, {117, 118, 119}, {147, 148, 149}, {147, 148, 149}, {176, 177, 178}, {206, 207, 208}, arrn, {265, 266, 267}, {294, 295, 296}, {324, 325, 326}, {353, 354, 355}};
        arrn = new int[]{0, 0, 0};
        arrn2 = new int[]{30, 30, 30};
        int[] arrn3 = new int[]{59, 59, 60};
        int[] arrn4 = new int[]{88, 89, 90};
        int[] arrn5 = new int[]{117, 118, 119};
        int[] arrn6 = new int[]{147, 148, 149};
        int[] arrn7 = new int[]{177, 178, 179};
        int[] arrn8 = new int[]{206, 207, 208};
        int[] arrn9 = new int[]{236, 237, 238};
        int[] arrn10 = new int[]{295, 296, 297};
        int[] arrn11 = new int[]{324, 325, 326};
        int[] arrn12 = new int[]{354, 355, 356};
        int[] arrn13 = new int[]{383, 384, 385};
        LEAP_MONTH_START = new int[][]{arrn, arrn2, arrn3, arrn4, arrn5, arrn6, arrn7, arrn8, arrn9, {265, 266, 267}, arrn10, arrn11, arrn12, arrn13};
        cache = new CalendarCache();
    }

    public HebrewCalendar() {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public HebrewCalendar(int n, int n2, int n3) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
    }

    public HebrewCalendar(int n, int n2, int n3, int n4, int n5, int n6) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
        this.set(11, n4);
        this.set(12, n5);
        this.set(13, n6);
    }

    public HebrewCalendar(TimeZone timeZone) {
        this(timeZone, ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public HebrewCalendar(TimeZone timeZone, ULocale uLocale) {
        super(timeZone, uLocale);
        this.setTimeInMillis(System.currentTimeMillis());
    }

    public HebrewCalendar(TimeZone timeZone, Locale locale) {
        super(timeZone, locale);
        this.setTimeInMillis(System.currentTimeMillis());
    }

    public HebrewCalendar(ULocale uLocale) {
        this(TimeZone.getDefault(), uLocale);
    }

    public HebrewCalendar(Date date) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.setTime(date);
    }

    public HebrewCalendar(Locale locale) {
        this(TimeZone.getDefault(), locale);
    }

    @Deprecated
    public static boolean isLeapYear(int n) {
        int n2 = (n * 12 + 17) % 19;
        n = n2 < 0 ? -7 : 12;
        boolean bl = n2 >= n;
        return bl;
    }

    private static int monthsInYear(int n) {
        n = HebrewCalendar.isLeapYear(n) ? 13 : 12;
        return n;
    }

    private static long startOfYear(int n) {
        long l;
        block6 : {
            int n2;
            long l2;
            block8 : {
                long l3;
                block7 : {
                    l = l3 = cache.get(n);
                    if (l3 != CalendarCache.EMPTY) break block6;
                    n2 = (n * 235 - 234) / 19;
                    l = (long)n2 * 13753L + 12084L;
                    l3 = (long)(n2 * 29) + l / 25920L;
                    l2 = l % 25920L;
                    int n3 = (int)(l3 % 7L);
                    if (n3 == 2 || n3 == 4) break block7;
                    l = l3;
                    n2 = n3;
                    if (n3 != 6) break block8;
                }
                l = l3 + 1L;
                n2 = (int)(l % 7L);
            }
            if (n2 == 1 && l2 > 16404L && !HebrewCalendar.isLeapYear(n)) {
                l += 2L;
            } else if (n2 == 0 && l2 > 23269L && HebrewCalendar.isLeapYear(n - 1)) {
                ++l;
            }
            cache.put(n, l);
        }
        return l;
    }

    private final int yearType(int n) {
        int n2;
        int n3 = n2 = this.handleGetYearLength(n);
        if (n2 > 380) {
            n3 = n2 - 30;
        }
        switch (n3) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Illegal year length ");
                stringBuilder.append(n3);
                stringBuilder.append(" in year ");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            case 355: {
                n = 2;
                break;
            }
            case 354: {
                n = 1;
                break;
            }
            case 353: {
                n = 0;
            }
        }
        return n;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void add(int n, int n2) {
        int n3;
        block14 : {
            if (n != 2) {
                super.add(n, n2);
                return;
            }
            int n4 = this.get(2);
            n3 = this.get(1);
            int n5 = 0;
            n = 0;
            if (n2 <= 0) {
                n = n5;
                if (n4 > 5) {
                    n = 1;
                }
            } else {
                if (n4 < 5) {
                    n = 1;
                }
                n2 = n4 + n2;
                n5 = n;
                n = n3;
                do {
                    n3 = n2;
                    if (n5 != 0) {
                        n3 = n2;
                        if (n2 >= 5) {
                            n3 = n2;
                            if (!HebrewCalendar.isLeapYear(n)) {
                                n3 = n2 + 1;
                            }
                        }
                    }
                    if (n3 > 12) {
                        n2 = n3 - 13;
                        ++n;
                        n5 = 1;
                        continue;
                    }
                    break block14;
                    break;
                } while (true);
            }
            n2 = n4 + n2;
            n5 = n;
            n = n3;
            do {
                n3 = n2;
                if (n5 != 0) {
                    n3 = n2;
                    if (n2 <= 5) {
                        n3 = n2;
                        if (!HebrewCalendar.isLeapYear(n)) {
                            n3 = n2 - 1;
                        }
                    }
                }
                if (n3 >= 0) break;
                n2 = n3 + 13;
                --n;
                n5 = 1;
            } while (true);
        }
        this.set(2, n3);
        this.set(1, n);
        this.pinField(5);
    }

    @Override
    public String getType() {
        return "hebrew";
    }

    @Override
    protected void handleComputeFields(int n) {
        long l = n - 347997;
        n = (int)((19L * (25920L * l / 765433L) + 234L) / 235L) + 1;
        int n2 = (int)(l - HebrewCalendar.startOfYear(n));
        while (n2 < 1) {
            n2 = (int)(l - HebrewCalendar.startOfYear(--n));
        }
        int n3 = this.yearType(n);
        int[][] arrn = HebrewCalendar.isLeapYear(n) ? LEAP_MONTH_START : MONTH_START;
        int n4 = 0;
        while (n2 > arrn[n4][n3]) {
            ++n4;
        }
        n3 = arrn[--n4][n3];
        this.internalSet(0, 0);
        this.internalSet(1, n);
        this.internalSet(19, n);
        this.internalSet(2, n4);
        this.internalSet(5, n2 - n3);
        this.internalSet(6, n2);
    }

    @Override
    protected int handleComputeMonthStart(int n, int n2, boolean bl) {
        int n3;
        long l;
        int n4;
        do {
            n3 = n--;
            n4 = n2;
            if (n2 >= 0) break;
            n2 += HebrewCalendar.monthsInYear(n);
        } while (true);
        while (n4 > 12) {
            n4 -= HebrewCalendar.monthsInYear(n3);
            ++n3;
        }
        long l2 = l = HebrewCalendar.startOfYear(n3);
        if (n4 != 0) {
            l2 = HebrewCalendar.isLeapYear(n3) ? l + (long)LEAP_MONTH_START[n4][this.yearType(n3)] : l + (long)MONTH_START[n4][this.yearType(n3)];
        }
        return (int)(347997L + l2);
    }

    @Override
    protected int handleGetExtendedYear() {
        int n = this.newerField(19, 1) == 19 ? this.internalGet(19, 1) : this.internalGet(1, 1);
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
        do {
            n4 = n--;
            n3 = n2;
            if (n2 >= 0) break;
            n2 += HebrewCalendar.monthsInYear(n);
        } while (true);
        while (n3 > 12) {
            n3 -= HebrewCalendar.monthsInYear(n4);
            ++n4;
        }
        if (n3 != 1 && n3 != 2) {
            return MONTH_LENGTH[n3][0];
        }
        return MONTH_LENGTH[n3][this.yearType(n4)];
    }

    @Override
    protected int handleGetYearLength(int n) {
        return (int)(HebrewCalendar.startOfYear(n + 1) - HebrewCalendar.startOfYear(n));
    }

    @Override
    public void roll(int n, int n2) {
        int n3;
        if (n != 2) {
            super.roll(n, n2);
            return;
        }
        int n4 = this.get(2);
        n = this.get(1);
        boolean bl = HebrewCalendar.isLeapYear(n);
        n = n3 = n2 % HebrewCalendar.monthsInYear(n) + n4;
        if (!bl) {
            if (n2 > 0 && n4 < 5 && n3 >= 5) {
                n = n3 + 1;
            } else {
                n = n3;
                if (n2 < 0) {
                    n = n3;
                    if (n4 > 5) {
                        n = n3;
                        if (n3 <= 5) {
                            n = n3 - 1;
                        }
                    }
                }
            }
        }
        this.set(2, (n + 13) % 13);
        this.pinField(5);
    }

    @Deprecated
    @Override
    protected void validateField(int n) {
        if (n == 2 && !HebrewCalendar.isLeapYear(this.handleGetExtendedYear()) && this.internalGet(2) == 5) {
            throw new IllegalArgumentException("MONTH cannot be ADAR_1(5) except leap years");
        }
        super.validateField(n);
    }
}

