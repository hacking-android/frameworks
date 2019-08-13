/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import java.util.Locale;

public class Grego {
    private static final int[] DAYS_BEFORE;
    private static final int JULIAN_1970_CE = 2440588;
    private static final int JULIAN_1_CE = 1721426;
    public static final long MAX_MILLIS = 183882168921600000L;
    public static final int MILLIS_PER_DAY = 86400000;
    public static final int MILLIS_PER_HOUR = 3600000;
    public static final int MILLIS_PER_MINUTE = 60000;
    public static final int MILLIS_PER_SECOND = 1000;
    public static final long MIN_MILLIS = -184303902528000000L;
    private static final int[] MONTH_LENGTH;

    static {
        MONTH_LENGTH = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        DAYS_BEFORE = new int[]{0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335};
    }

    public static int dayOfWeek(long l) {
        int n;
        block0 : {
            long[] arrl = new long[1];
            Grego.floorDivide(5L + l, 7L, arrl);
            n = (int)arrl[0];
            if (n != 0) break block0;
            n = 7;
        }
        return n;
    }

    public static int[] dayToFields(long l, int[] arrn) {
        int n;
        int[] arrn2;
        block8 : {
            block7 : {
                arrn2 = arrn;
                if (arrn2 == null) break block7;
                arrn = arrn2;
                if (arrn2.length >= 5) break block8;
            }
            arrn = new int[5];
        }
        arrn2 = new long[1];
        long l2 = Grego.floorDivide(l += 719162L, 146097L, arrn2);
        long l3 = Grego.floorDivide(arrn2[0], 36524L, arrn2);
        long l4 = Grego.floorDivide(arrn2[0], 1461L, arrn2);
        long l5 = Grego.floorDivide(arrn2[0], 365L, arrn2);
        int n2 = (int)(400L * l2 + 100L * l3 + l4 * 4L + l5);
        int n3 = arrn2[0];
        if (l3 != 4L && l5 != 4L) {
            ++n2;
        } else {
            n3 = 365;
        }
        boolean bl = Grego.isLeapYear(n2);
        int n4 = 0;
        int n5 = bl ? 60 : 59;
        if (n3 >= n5) {
            n5 = bl ? 1 : 2;
            n4 = n5;
        }
        n4 = ((n3 + n4) * 12 + 6) / 367;
        arrn2 = DAYS_BEFORE;
        n5 = bl ? n4 + 12 : n4;
        int n6 = arrn2[n5];
        n5 = n = (int)((l + 2L) % 7L);
        if (n < 1) {
            n5 = n + 7;
        }
        arrn[0] = n2;
        arrn[1] = n4;
        arrn[2] = n3 - n6 + 1;
        arrn[3] = n5;
        arrn[4] = n3 + 1;
        return arrn;
    }

    public static long fieldsToDay(int n, int n2, int n3) {
        int n4 = n - 1;
        long l = n4 * 365;
        long l2 = Grego.floorDivide(n4, 4L);
        long l3 = Grego.floorDivide(n4, 400L);
        long l4 = Grego.floorDivide(n4, 100L);
        int[] arrn = DAYS_BEFORE;
        n = Grego.isLeapYear(n) ? 12 : 0;
        return l + l2 + 1721423L + l3 - l4 + 2L + (long)arrn[n + n2] + (long)n3 - 2440588L;
    }

    public static long floorDivide(long l, long l2) {
        l = l >= 0L ? (l /= l2) : (l + 1L) / l2 - 1L;
        return l;
    }

    private static long floorDivide(long l, long l2, long[] arrl) {
        if (l >= 0L) {
            arrl[0] = l % l2;
            return l / l2;
        }
        long l3 = (l + 1L) / l2 - 1L;
        arrl[0] = l - l3 * l2;
        return l3;
    }

    public static int getDayOfWeekInMonth(int n, int n2, int n3) {
        int n4;
        int n5 = (n3 + 6) / 7;
        if (n5 == 4) {
            n4 = n5;
            if (n3 + 7 > Grego.monthLength(n, n2)) {
                n4 = -1;
            }
        } else {
            n4 = n5;
            if (n5 == 5) {
                n4 = -1;
            }
        }
        return n4;
    }

    public static final boolean isLeapYear(int n) {
        boolean bl = (n & 3) == 0 && (n % 100 != 0 || n % 400 == 0);
        return bl;
    }

    public static final int monthLength(int n, int n2) {
        int[] arrn = MONTH_LENGTH;
        n = Grego.isLeapYear(n) ? 12 : 0;
        return arrn[n + n2];
    }

    public static final int previousMonthLength(int n, int n2) {
        n = n2 > 0 ? Grego.monthLength(n, n2 - 1) : 31;
        return n;
    }

    public static int[] timeToFields(long l, int[] arrn) {
        int[] arrn2;
        block3 : {
            block2 : {
                if (arrn == null) break block2;
                arrn2 = arrn;
                if (arrn.length >= 6) break block3;
            }
            arrn2 = new int[6];
        }
        arrn = new long[1];
        Grego.dayToFields(Grego.floorDivide(l, 86400000L, arrn), arrn2);
        arrn2[5] = arrn[0];
        return arrn2;
    }

    public static String timeToString(long l) {
        int[] arrn = Grego.timeToFields(l, null);
        int n = arrn[5];
        int n2 = n / 3600000;
        int n3 = n % 3600000;
        n = n3 / 60000;
        int n4 = n3 % 60000;
        n3 = n4 / 1000;
        return String.format((Locale)null, "%04d-%02d-%02dT%02d:%02d:%02d.%03dZ", arrn[0], arrn[1] + 1, arrn[2], n2, n, n3, n4 % 1000);
    }
}

