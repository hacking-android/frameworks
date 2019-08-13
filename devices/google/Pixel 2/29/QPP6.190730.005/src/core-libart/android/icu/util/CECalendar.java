/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.icu.util.ULocale;
import java.util.Date;
import java.util.Locale;

abstract class CECalendar
extends Calendar {
    private static final int[][] LIMITS;
    private static final long serialVersionUID = -999547623066414271L;

    static {
        int[] arrn = new int[]{0, 0, 1, 1};
        int[] arrn2 = new int[]{1, 1, 52, 53};
        int[] arrn3 = new int[]{};
        int[] arrn4 = new int[]{-1, -1, 1, 5};
        int[] arrn5 = new int[]{};
        int[] arrn6 = new int[]{};
        int[] arrn7 = new int[]{};
        int[] arrn8 = new int[]{};
        int[] arrn9 = new int[]{};
        int[] arrn10 = new int[]{-5000000, -5000000, 5000000, 5000000};
        int[] arrn11 = new int[]{-5000000, -5000000, 5000000, 5000000};
        int[] arrn12 = new int[]{};
        int[] arrn13 = new int[]{};
        LIMITS = new int[][]{arrn, {1, 1, 5000000, 5000000}, {0, 0, 12, 12}, arrn2, new int[0], {1, 1, 5, 30}, {1, 1, 365, 366}, arrn3, arrn4, arrn5, new int[0], arrn6, arrn7, arrn8, arrn9, new int[0], new int[0], arrn10, new int[0], arrn11, arrn12, arrn13};
    }

    protected CECalendar() {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
    }

    protected CECalendar(int n, int n2, int n3) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.set(n, n2, n3);
    }

    protected CECalendar(int n, int n2, int n3, int n4, int n5, int n6) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.set(n, n2, n3, n4, n5, n6);
    }

    protected CECalendar(TimeZone timeZone) {
        this(timeZone, ULocale.getDefault(ULocale.Category.FORMAT));
    }

    protected CECalendar(TimeZone timeZone, ULocale uLocale) {
        super(timeZone, uLocale);
        this.setTimeInMillis(System.currentTimeMillis());
    }

    protected CECalendar(TimeZone timeZone, Locale locale) {
        super(timeZone, locale);
        this.setTimeInMillis(System.currentTimeMillis());
    }

    protected CECalendar(ULocale uLocale) {
        this(TimeZone.getDefault(), uLocale);
    }

    protected CECalendar(Date date) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.setTime(date);
    }

    protected CECalendar(Locale locale) {
        this(TimeZone.getDefault(), locale);
    }

    public static int ceToJD(long l, int n, int n2, int n3) {
        if (n >= 0) {
            l += (long)(n / 13);
            n %= 13;
        } else {
            l += (long)(++n / 13 - 1);
            n = n % 13 + 12;
        }
        return (int)((long)n3 + 365L * l + CECalendar.floorDivide(l, 4L) + (long)(n * 30) + (long)n2 - 1L);
    }

    public static void jdToCE(int n, int n2, int[] arrn) {
        int[] arrn2 = new int[1];
        n2 = CECalendar.floorDivide(n - n2, 1461, arrn2);
        int n3 = arrn2[0];
        n = 365;
        arrn[0] = n2 * 4 + (n3 / 365 - arrn2[0] / 1460);
        if (arrn2[0] != 1460) {
            n = arrn2[0] % 365;
        }
        arrn[1] = n / 30;
        arrn[2] = n % 30 + 1;
    }

    protected abstract int getJDEpochOffset();

    @Override
    protected int handleComputeMonthStart(int n, int n2, boolean bl) {
        return CECalendar.ceToJD(n, n2, 0, this.getJDEpochOffset());
    }

    @Override
    protected int handleGetLimit(int n, int n2) {
        return LIMITS[n][n2];
    }

    @Override
    protected int handleGetMonthLength(int n, int n2) {
        if ((n2 + 1) % 13 != 0) {
            return 30;
        }
        return n % 4 / 3 + 5;
    }
}

