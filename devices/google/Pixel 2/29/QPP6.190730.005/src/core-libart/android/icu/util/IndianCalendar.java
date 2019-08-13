/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.icu.util.ULocale;
import java.util.Date;
import java.util.Locale;

public class IndianCalendar
extends Calendar {
    public static final int AGRAHAYANA = 8;
    public static final int ASADHA = 3;
    public static final int ASVINA = 6;
    public static final int BHADRA = 5;
    public static final int CHAITRA = 0;
    public static final int IE = 0;
    private static final int INDIAN_ERA_START = 78;
    private static final int INDIAN_YEAR_START = 80;
    public static final int JYAISTHA = 2;
    public static final int KARTIKA = 7;
    private static final int[][] LIMITS;
    public static final int MAGHA = 10;
    public static final int PAUSA = 9;
    public static final int PHALGUNA = 11;
    public static final int SRAVANA = 4;
    public static final int VAISAKHA = 1;
    private static final long serialVersionUID = 3617859668165014834L;

    static {
        int[] arrn = new int[]{};
        int[] arrn2 = new int[]{};
        int[] arrn3 = new int[]{};
        LIMITS = new int[][]{{0, 0, 0, 0}, {-5000000, -5000000, 5000000, 5000000}, {0, 0, 11, 11}, {1, 1, 52, 53}, new int[0], {1, 1, 30, 31}, {1, 1, 365, 366}, new int[0], {-1, -1, 5, 5}, new int[0], new int[0], new int[0], new int[0], arrn, new int[0], new int[0], new int[0], {-5000000, -5000000, 5000000, 5000000}, arrn2, {-5000000, -5000000, 5000000, 5000000}, new int[0], arrn3};
    }

    public IndianCalendar() {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public IndianCalendar(int n, int n2, int n3) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
    }

    public IndianCalendar(int n, int n2, int n3, int n4, int n5, int n6) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
        this.set(11, n4);
        this.set(12, n5);
        this.set(13, n6);
    }

    public IndianCalendar(TimeZone timeZone) {
        this(timeZone, ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public IndianCalendar(TimeZone timeZone, ULocale uLocale) {
        super(timeZone, uLocale);
        this.setTimeInMillis(System.currentTimeMillis());
    }

    public IndianCalendar(TimeZone timeZone, Locale locale) {
        super(timeZone, locale);
        this.setTimeInMillis(System.currentTimeMillis());
    }

    public IndianCalendar(ULocale uLocale) {
        this(TimeZone.getDefault(), uLocale);
    }

    public IndianCalendar(Date date) {
        super(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.setTime(date);
    }

    public IndianCalendar(Locale locale) {
        this(TimeZone.getDefault(), locale);
    }

    private static double IndianToJD(int n, int n2, int n3) {
        double d;
        int n4 = n + 78;
        if (IndianCalendar.isGregorianLeap(n4)) {
            n = 31;
            d = IndianCalendar.gregorianToJD(n4, 3, 21);
        } else {
            n = 30;
            d = IndianCalendar.gregorianToJD(n4, 3, 22);
        }
        if (n2 == 1) {
            d = (double)(n3 - 1) + d;
        } else {
            double d2;
            d = d2 = (double)n + d + (double)(Math.min(n2 - 2, 5) * 31);
            if (n2 >= 8) {
                d = d2 + (double)((n2 - 7) * 30);
            }
            d += (double)(n3 - 1);
        }
        return d;
    }

    private static double gregorianToJD(int n, int n2, int n3) {
        int n4 = n - 1;
        int n5 = n4 / 4;
        int n6 = n4 / 100;
        int n7 = n4 / 400;
        int n8 = (n2 * 367 - 362) / 12;
        n = n2 <= 2 ? 0 : (IndianCalendar.isGregorianLeap(n) ? -1 : -2);
        return (double)(n4 * 365 + n5 - n6 + n7 + n8 + n + n3 - 1) + 1721425.5;
    }

    private static boolean isGregorianLeap(int n) {
        boolean bl = n % 4 == 0 && (n % 100 != 0 || n % 400 == 0);
        return bl;
    }

    private static int[] jdToGregorian(double d) {
        int n;
        d = Math.floor(d - 0.5) + 0.5;
        double d2 = d - 1721425.5;
        double d3 = Math.floor(d2 / 146097.0);
        double d4 = d2 % 146097.0;
        d2 = Math.floor(d4 / 36524.0);
        double d5 = d4 % 36524.0;
        d4 = Math.floor(d5 / 1461.0);
        d5 = Math.floor(d5 % 1461.0 / 365.0);
        int n2 = n = (int)(400.0 * d3 + 100.0 * d2 + d4 * 4.0 + d5);
        if (d2 != 4.0) {
            n2 = n;
            if (d5 != 4.0) {
                n2 = n + 1;
            }
        }
        d3 = IndianCalendar.gregorianToJD(n2, 1, 1);
        n = d < IndianCalendar.gregorianToJD(n2, 3, 1) ? 0 : (IndianCalendar.isGregorianLeap(n2) ? 1 : 2);
        n = (int)Math.floor(((d - d3 + (double)n) * 12.0 + 373.0) / 367.0);
        return new int[]{n2, n, (int)(d - IndianCalendar.gregorianToJD(n2, n, 1)) + 1};
    }

    @Override
    public String getType() {
        return "indian";
    }

    @Override
    protected void handleComputeFields(int n) {
        int n2;
        int[] arrn = IndianCalendar.jdToGregorian(n);
        int n3 = arrn[0] - 78;
        double d = IndianCalendar.gregorianToJD(arrn[0], 1, 1);
        int n4 = (int)((double)n - d);
        n = 31;
        if (n4 < 80) {
            --n3;
            if (!IndianCalendar.isGregorianLeap(arrn[0] - 1)) {
                n = 30;
            }
            n2 = n4 + (n + 155 + 90 + 10);
        } else {
            if (!IndianCalendar.isGregorianLeap(arrn[0])) {
                n = 30;
            }
            n2 = n4 - 80;
        }
        if (n2 < n) {
            n4 = 0;
            n = n2 + 1;
        } else if ((n = n2 - n) < 155) {
            n4 = n / 31 + 1;
            n = n % 31 + 1;
        } else {
            n4 = (n -= 155) / 30 + 6;
            n = n % 30 + 1;
        }
        this.internalSet(0, 0);
        this.internalSet(19, n3);
        this.internalSet(1, n3);
        this.internalSet(2, n4);
        this.internalSet(5, n);
        this.internalSet(6, n2 + 1);
    }

    @Override
    protected int handleComputeMonthStart(int n, int n2, boolean bl) {
        int n3;
        int n4;
        block3 : {
            block2 : {
                if (n2 < 0) break block2;
                n3 = n;
                n4 = n2;
                if (n2 <= 11) break block3;
            }
            n3 = n + n2 / 12;
            n4 = n2 % 12;
        }
        return (int)IndianCalendar.IndianToJD(n3, n4 + 1, 1);
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
        block6 : {
            block5 : {
                if (n2 < 0) break block5;
                n4 = n;
                n3 = n2;
                if (n2 <= 11) break block6;
            }
            int[] arrn = new int[1];
            n4 = n + IndianCalendar.floorDivide(n2, 12, arrn);
            n3 = arrn[0];
        }
        if (IndianCalendar.isGregorianLeap(n4 + 78) && n3 == 0) {
            return 31;
        }
        if (n3 >= 1 && n3 <= 5) {
            return 31;
        }
        return 30;
    }

    @Override
    protected int handleGetYearLength(int n) {
        return super.handleGetYearLength(n);
    }
}

