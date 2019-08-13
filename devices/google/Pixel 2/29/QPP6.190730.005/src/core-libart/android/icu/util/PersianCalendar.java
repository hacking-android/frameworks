/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.icu.util.ULocale;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.util.Date;
import java.util.Locale;

@Deprecated
public class PersianCalendar
extends Calendar {
    private static final int[][] LIMITS;
    private static final int[][] MONTH_COUNT;
    private static final int PERSIAN_EPOCH = 1948320;
    private static final long serialVersionUID = -6727306982975111643L;

    static {
        int[] arrn = new int[]{31, 31, 93};
        int[] arrn2 = new int[]{31, 31, 155};
        int[] arrn3 = new int[]{30, 30, 246};
        MONTH_COUNT = new int[][]{{31, 31, 0}, {31, 31, 31}, {31, 31, 62}, arrn, {31, 31, 124}, arrn2, {30, 30, 186}, {30, 30, 216}, arrn3, {30, 30, 276}, {30, 30, 306}, {29, 30, 336}};
        arrn = new int[]{0, 0, 0, 0};
        arrn2 = new int[]{-5000000, -5000000, 5000000, 5000000};
        arrn3 = new int[]{0, 0, 11, 11};
        int[] arrn4 = new int[]{1, 1, 52, 53};
        int[] arrn5 = new int[]{};
        int[] arrn6 = new int[]{1, 1, 29, 31};
        int[] arrn7 = new int[]{1, 1, 365, 366};
        int[] arrn8 = new int[]{};
        int[] arrn9 = new int[]{};
        int[] arrn10 = new int[]{};
        int[] arrn11 = new int[]{};
        int[] arrn12 = new int[]{};
        int[] arrn13 = new int[]{};
        int[] arrn14 = new int[]{-5000000, -5000000, 5000000, 5000000};
        int[] arrn15 = new int[]{};
        int[] arrn16 = new int[]{};
        LIMITS = new int[][]{arrn, arrn2, arrn3, arrn4, arrn5, arrn6, arrn7, new int[0], {-1, -1, 5, 5}, new int[0], arrn8, new int[0], arrn9, arrn10, arrn11, arrn12, arrn13, {-5000000, -5000000, 5000000, 5000000}, new int[0], arrn14, arrn15, arrn16};
    }

    @Deprecated
    public PersianCalendar() {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
    }

    @Deprecated
    public PersianCalendar(int n, int n2, int n3) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
    }

    @Deprecated
    public PersianCalendar(int n, int n2, int n3, int n4, int n5, int n6) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
        this.set(11, n4);
        this.set(12, n5);
        this.set(13, n6);
    }

    @Deprecated
    public PersianCalendar(TimeZone timeZone) {
        this(timeZone, ULocale.getDefault(ULocale.Category.FORMAT));
    }

    @Deprecated
    public PersianCalendar(TimeZone timeZone, ULocale uLocale) {
        super(timeZone, uLocale);
        this.setTimeInMillis(System.currentTimeMillis());
    }

    @Deprecated
    public PersianCalendar(TimeZone timeZone, Locale locale) {
        super(timeZone, locale);
        this.setTimeInMillis(System.currentTimeMillis());
    }

    @Deprecated
    public PersianCalendar(ULocale uLocale) {
        this(TimeZone.getDefault(), uLocale);
    }

    @Deprecated
    public PersianCalendar(Date date) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.setTime(date);
    }

    @Deprecated
    @UnsupportedAppUsage
    public PersianCalendar(Locale locale) {
        this(TimeZone.getDefault(), locale);
    }

    private static final boolean isLeapYear(int n) {
        boolean bl = true;
        int[] arrn = new int[1];
        PersianCalendar.floorDivide(n * 25 + 11, 33, arrn);
        if (arrn[0] >= 8) {
            bl = false;
        }
        return bl;
    }

    @Deprecated
    @Override
    public String getType() {
        return "persian";
    }

    @Deprecated
    @Override
    protected void handleComputeFields(int n) {
        long l = n - 1948320;
        int n2 = (int)PersianCalendar.floorDivide(l * 33L + 3L, 12053L) + 1;
        int n3 = (int)(l - (((long)n2 - 1L) * 365L + PersianCalendar.floorDivide((long)n2 * 8L + 21L, 33L)));
        n = n3 < 216 ? n3 / 31 : (n3 - 6) / 30;
        int n4 = MONTH_COUNT[n][2];
        this.internalSet(0, 0);
        this.internalSet(1, n2);
        this.internalSet(19, n2);
        this.internalSet(2, n);
        this.internalSet(5, n3 - n4 + 1);
        this.internalSet(6, n3 + 1);
    }

    @Deprecated
    @Override
    protected int handleComputeMonthStart(int n, int n2, boolean bl) {
        int n3;
        int n4;
        block5 : {
            block4 : {
                if (n2 < 0) break block4;
                n3 = n;
                n4 = n2;
                if (n2 <= 11) break block5;
            }
            int[] arrn = new int[1];
            n3 = n + PersianCalendar.floorDivide(n2, 12, arrn);
            n4 = arrn[0];
        }
        n = n2 = (n3 - 1) * 365 + 1948319 + PersianCalendar.floorDivide(n3 * 8 + 21, 33);
        if (n4 != 0) {
            n = n2 + MONTH_COUNT[n4][2];
        }
        return n;
    }

    @Deprecated
    @Override
    protected int handleGetExtendedYear() {
        int n = this.newerField(19, 1) == 19 ? this.internalGet(19, 1) : this.internalGet(1, 1);
        return n;
    }

    @Deprecated
    @Override
    protected int handleGetLimit(int n, int n2) {
        return LIMITS[n][n2];
    }

    @Deprecated
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
            n4 = n + PersianCalendar.floorDivide(n2, 12, arrn);
            n3 = arrn[0];
        }
        return MONTH_COUNT[n3][PersianCalendar.isLeapYear(n4)];
    }

    @Deprecated
    @Override
    protected int handleGetYearLength(int n) {
        n = PersianCalendar.isLeapYear(n) ? 366 : 365;
        return n;
    }
}

