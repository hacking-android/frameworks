/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.impl.CalendarAstronomer;
import android.icu.impl.CalendarCache;
import android.icu.text.DateFormat;
import android.icu.util.Calendar;
import android.icu.util.SimpleTimeZone;
import android.icu.util.TimeZone;
import android.icu.util.ULocale;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Date;
import java.util.Locale;

public class ChineseCalendar
extends Calendar {
    private static final TimeZone CHINA_ZONE;
    static final int[][][] CHINESE_DATE_PRECEDENCE;
    private static final int CHINESE_EPOCH_YEAR = -2636;
    private static final int[][] LIMITS;
    private static final int SYNODIC_GAP = 25;
    private static final long serialVersionUID = 7312110751940929420L;
    private transient CalendarAstronomer astro = new CalendarAstronomer();
    private int epochYear;
    private transient boolean isLeapYear;
    private transient CalendarCache newYearCache = new CalendarCache();
    private transient CalendarCache winterSolsticeCache = new CalendarCache();
    private TimeZone zoneAstro;

    static {
        int[] arrn = new int[]{1, 1, 83333, 83333};
        int[] arrn2 = new int[]{1, 1, 60, 60};
        int[] arrn3 = new int[]{1, 1, 50, 55};
        int[] arrn4 = new int[]{};
        int[] arrn5 = new int[]{1, 1, 29, 30};
        int[] arrn6 = new int[]{};
        int[] arrn7 = new int[]{-1, -1, 5, 5};
        int[] arrn8 = new int[]{};
        int[] arrn9 = new int[]{};
        int[] arrn10 = new int[]{};
        int[] arrn11 = new int[]{-5000000, -5000000, 5000000, 5000000};
        int[] arrn12 = new int[]{0, 0, 1, 1};
        LIMITS = new int[][]{arrn, arrn2, {0, 0, 11, 11}, arrn3, arrn4, arrn5, {1, 1, 353, 385}, arrn6, arrn7, arrn8, new int[0], arrn9, new int[0], new int[0], new int[0], arrn10, new int[0], {-5000000, -5000000, 5000000, 5000000}, new int[0], arrn11, new int[0], new int[0], arrn12};
        arrn = new int[]{4, 7};
        arrn2 = new int[]{3, 18};
        arrn3 = new int[]{8, 18};
        arrn4 = new int[]{3};
        arrn5 = new int[]{4};
        arrn6 = new int[]{8};
        arrn7 = new int[]{40, 7};
        arrn8 = new int[]{40, 18};
        CHINESE_DATE_PRECEDENCE = new int[][][]{{{5}, {3, 7}, arrn, {8, 7}, arrn2, {4, 18}, arrn3, {6}, {37, 22}}, {arrn4, arrn5, arrn6, arrn7, arrn8}};
        CHINA_ZONE = new SimpleTimeZone(28800000, "CHINA_ZONE").freeze();
    }

    public ChineseCalendar() {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT), -2636, CHINA_ZONE);
    }

    public ChineseCalendar(int n, int n2, int n3, int n4) {
        this(n, n2, n3, n4, 0, 0, 0);
    }

    public ChineseCalendar(int n, int n2, int n3, int n4, int n5) {
        this(n, n2, n3, n4, n5, 0, 0, 0);
    }

    public ChineseCalendar(int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT), -2636, CHINA_ZONE);
        this.set(14, 0);
        this.set(1, n);
        this.set(2, n2);
        this.set(22, n3);
        this.set(5, n4);
        this.set(11, n5);
        this.set(12, n6);
        this.set(13, n7);
    }

    public ChineseCalendar(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT), -2636, CHINA_ZONE);
        this.set(14, 0);
        this.set(0, n);
        this.set(1, n2);
        this.set(2, n3);
        this.set(22, n4);
        this.set(5, n5);
        this.set(11, n6);
        this.set(12, n7);
        this.set(13, n8);
    }

    public ChineseCalendar(TimeZone timeZone) {
        this(timeZone, ULocale.getDefault(ULocale.Category.FORMAT), -2636, CHINA_ZONE);
    }

    public ChineseCalendar(TimeZone timeZone, ULocale uLocale) {
        this(timeZone, uLocale, -2636, CHINA_ZONE);
    }

    @Deprecated
    protected ChineseCalendar(TimeZone timeZone, ULocale uLocale, int n, TimeZone timeZone2) {
        super(timeZone, uLocale);
        this.epochYear = n;
        this.zoneAstro = timeZone2;
        this.setTimeInMillis(System.currentTimeMillis());
    }

    public ChineseCalendar(TimeZone timeZone, Locale locale) {
        this(timeZone, ULocale.forLocale(locale), -2636, CHINA_ZONE);
    }

    public ChineseCalendar(ULocale uLocale) {
        this(TimeZone.getDefault(), uLocale, -2636, CHINA_ZONE);
    }

    public ChineseCalendar(Date date) {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT), -2636, CHINA_ZONE);
        this.setTime(date);
    }

    public ChineseCalendar(Locale locale) {
        this(TimeZone.getDefault(), ULocale.forLocale(locale), -2636, CHINA_ZONE);
    }

    private void computeChineseFields(int n, int n2, int n3, boolean bl) {
        block9 : {
            int n4;
            int n5;
            int n6;
            block11 : {
                int n7;
                int n8;
                block10 : {
                    n6 = this.winterSolstice(n2);
                    if (n < n6) {
                        n5 = this.winterSolstice(n2 - 1);
                    } else {
                        n7 = this.winterSolstice(n2 + 1);
                        n5 = n6;
                        n6 = n7;
                    }
                    n7 = this.newMoonNear(n5 + 1, true);
                    n6 = this.newMoonNear(n6 + 1, false);
                    n4 = this.newMoonNear(n + 1, false);
                    boolean bl2 = this.synodicMonthsBetween(n7, n6) == 12;
                    this.isLeapYear = bl2;
                    n6 = n5 = this.synodicMonthsBetween(n7, n4);
                    if (this.isLeapYear) {
                        n6 = n5;
                        if (this.isLeapMonthBetween(n7, n4)) {
                            n6 = n5 - 1;
                        }
                    }
                    n5 = n6;
                    if (n6 < 1) {
                        n5 = n6 + 12;
                    }
                    n6 = this.isLeapYear && this.hasNoMajorSolarTerm(n4) && !this.isLeapMonthBetween(n7, this.newMoonNear(n4 - 25, false)) ? 1 : 0;
                    this.internalSet(2, n5 - 1);
                    n6 = n6 != 0 ? 1 : 0;
                    this.internalSet(22, n6);
                    if (!bl) break block9;
                    n8 = n2 - this.epochYear;
                    n7 = n2 + 2636;
                    if (n5 < 11) break block10;
                    n5 = n8;
                    n6 = n7;
                    if (n3 < 6) break block11;
                }
                n5 = n8 + 1;
                n6 = n7 + 1;
            }
            this.internalSet(19, n5);
            int[] arrn = new int[1];
            this.internalSet(0, ChineseCalendar.floorDivide(n6 - 1, 60, arrn) + 1);
            this.internalSet(1, arrn[0] + 1);
            this.internalSet(5, n - n4 + 1);
            n3 = n6 = this.newYear(n2);
            if (n < n6) {
                n3 = this.newYear(n2 - 1);
            }
            this.internalSet(6, n - n3 + 1);
        }
    }

    private final long daysToMillis(int n) {
        long l = (long)n * 86400000L;
        return l - (long)this.zoneAstro.getOffset(l);
    }

    private boolean hasNoMajorSolarTerm(int n) {
        int n2 = this.majorSolarTerm(n);
        boolean bl = true;
        if (n2 != this.majorSolarTerm(this.newMoonNear(n + 25, true))) {
            bl = false;
        }
        return bl;
    }

    private boolean isLeapMonthBetween(int n, int n2) {
        if (this.synodicMonthsBetween(n, n2) < 50) {
            boolean bl = false;
            if (n2 >= n && (this.isLeapMonthBetween(n, this.newMoonNear(n2 - 25, false)) || this.hasNoMajorSolarTerm(n2))) {
                bl = true;
            }
            return bl;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("isLeapMonthBetween(");
        stringBuilder.append(n);
        stringBuilder.append(", ");
        stringBuilder.append(n2);
        stringBuilder.append("): Invalid parameters");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private int majorSolarTerm(int n) {
        int n2;
        this.astro.setTime(this.daysToMillis(n));
        n = n2 = ((int)Math.floor(this.astro.getSunLongitude() * 6.0 / 3.141592653589793) + 2) % 12;
        if (n2 < 1) {
            n = n2 + 12;
        }
        return n;
    }

    private final int millisToDays(long l) {
        return (int)ChineseCalendar.floorDivide((long)this.zoneAstro.getOffset(l) + l, 86400000L);
    }

    private int newMoonNear(int n, boolean bl) {
        this.astro.setTime(this.daysToMillis(n));
        return this.millisToDays(this.astro.getMoonTime(CalendarAstronomer.NEW_MOON, bl));
    }

    private int newYear(int n) {
        long l;
        long l2 = l = this.newYearCache.get(n);
        if (l == CalendarCache.EMPTY) {
            int n2 = this.winterSolstice(n - 1);
            int n3 = this.winterSolstice(n);
            int n4 = this.newMoonNear(n2 + 1, true);
            n2 = this.newMoonNear(n4 + 25, true);
            l2 = this.synodicMonthsBetween(n4, this.newMoonNear(n3 + 1, false)) == 12 && (this.hasNoMajorSolarTerm(n4) || this.hasNoMajorSolarTerm(n2)) ? (long)this.newMoonNear(n2 + 25, true) : (long)n2;
            this.newYearCache.put(n, l2);
        }
        return (int)l2;
    }

    private void offsetMonth(int n, int n2, int n3) {
        n = 2440588 + this.newMoonNear(n + (int)(((double)n3 - 0.5) * 29.530588853), true) - 1 + n2;
        if (n2 > 29) {
            this.set(20, n - 1);
            this.complete();
            if (this.getActualMaximum(5) >= n2) {
                this.set(20, n);
            }
        } else {
            this.set(20, n);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        this.epochYear = -2636;
        this.zoneAstro = CHINA_ZONE;
        objectInputStream.defaultReadObject();
        this.astro = new CalendarAstronomer();
        this.winterSolsticeCache = new CalendarCache();
        this.newYearCache = new CalendarCache();
    }

    private int synodicMonthsBetween(int n, int n2) {
        return (int)Math.round((double)(n2 - n) / 29.530588853);
    }

    private int winterSolstice(int n) {
        long l;
        long l2 = l = this.winterSolsticeCache.get(n);
        if (l == CalendarCache.EMPTY) {
            l2 = this.daysToMillis(this.computeGregorianMonthStart(n, 11) + 1 - 2440588);
            this.astro.setTime(l2);
            l2 = this.millisToDays(this.astro.getSunTime(CalendarAstronomer.WINTER_SOLSTICE, true));
            this.winterSolsticeCache.put(n, l2);
        }
        return (int)l2;
    }

    @Override
    public void add(int n, int n2) {
        if (n != 2) {
            super.add(n, n2);
        } else if (n2 != 0) {
            n = this.get(5);
            this.offsetMonth(this.get(20) - 2440588 - n + 1, n, n2);
        }
    }

    @Override
    protected int[][][] getFieldResolutionTable() {
        return CHINESE_DATE_PRECEDENCE;
    }

    @Override
    public String getType() {
        return "chinese";
    }

    @Override
    protected void handleComputeFields(int n) {
        this.computeChineseFields(n - 2440588, this.getGregorianYear(), this.getGregorianMonth(), true);
    }

    @Override
    protected int handleComputeMonthStart(int n, int n2, boolean bl) {
        int n3;
        int n4;
        block6 : {
            int n5;
            block5 : {
                if (n2 >= 0 && n2 <= 11) {
                    n4 = n;
                    n = n2;
                    n2 = n4;
                } else {
                    int[] arrn = new int[1];
                    n2 = n + ChineseCalendar.floorDivide(n2, 12, arrn);
                    n = arrn[0];
                }
                n5 = this.newMoonNear(n * 29 + this.newYear(this.epochYear + n2 - 1), true);
                int n6 = n5 + 2440588;
                n3 = this.internalGet(2);
                n4 = this.internalGet(22);
                n2 = bl ? n4 : 0;
                this.computeGregorianFields(n6);
                this.computeChineseFields(n5, this.getGregorianYear(), this.getGregorianMonth(), false);
                if (n != this.internalGet(2)) break block5;
                n = n6;
                if (n2 == this.internalGet(22)) break block6;
            }
            n = this.newMoonNear(n5 + 25, true) + 2440588;
        }
        this.internalSet(2, n3);
        this.internalSet(22, n4);
        return n - 1;
    }

    @Override
    protected DateFormat handleGetDateFormat(String string, String string2, ULocale uLocale) {
        return super.handleGetDateFormat(string, string2, uLocale);
    }

    @Override
    protected int handleGetExtendedYear() {
        int n = this.newestStamp(0, 1, 0) <= this.getStamp(19) ? this.internalGet(19, 1) : (this.internalGet(0, 1) - 1) * 60 + this.internalGet(1, 1) - (this.epochYear + 2636);
        return n;
    }

    @Override
    protected int handleGetLimit(int n, int n2) {
        return LIMITS[n][n2];
    }

    @Override
    protected int handleGetMonthLength(int n, int n2) {
        n = this.handleComputeMonthStart(n, n2, true) - 2440588 + 1;
        return this.newMoonNear(n + 25, true) - n;
    }

    @Deprecated
    @Override
    public boolean haveDefaultCentury() {
        return false;
    }

    @Override
    public void roll(int n, int n2) {
        if (n != 2) {
            super.roll(n, n2);
        } else if (n2 != 0) {
            int n3;
            int n4;
            int n5 = this.get(5);
            int n6 = this.get(20) - 2440588 - n5 + 1;
            n = n4 = this.get(2);
            if (this.isLeapYear) {
                if (this.get(22) == 1) {
                    n = n4 + 1;
                } else {
                    n = n4;
                    if (this.isLeapMonthBetween(this.newMoonNear(n6 - (int)(((double)n4 - 0.5) * 29.530588853), true), n6)) {
                        n = n4 + 1;
                    }
                }
            }
            n4 = this.isLeapYear ? 13 : 12;
            n2 = n3 = (n + n2) % n4;
            if (n3 < 0) {
                n2 = n3 + n4;
            }
            if (n2 != n) {
                this.offsetMonth(n6, n5, n2 - n);
            }
        }
    }
}

