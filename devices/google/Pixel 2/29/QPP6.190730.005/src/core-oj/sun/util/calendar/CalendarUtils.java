/*
 * Decompiled with CFR 0.145.
 */
package sun.util.calendar;

public class CalendarUtils {
    public static final int amod(int n, int n2) {
        block0 : {
            if ((n = CalendarUtils.mod(n, n2)) != 0) break block0;
            n = n2;
        }
        return n;
    }

    public static final long amod(long l, long l2) {
        block0 : {
            if ((l = CalendarUtils.mod(l, l2)) != 0L) break block0;
            l = l2;
        }
        return l;
    }

    public static final int floorDivide(int n, int n2) {
        n = n >= 0 ? (n /= n2) : (n + 1) / n2 - 1;
        return n;
    }

    public static final int floorDivide(int n, int n2, int[] arrn) {
        if (n >= 0) {
            arrn[0] = n % n2;
            return n / n2;
        }
        int n3 = (n + 1) / n2 - 1;
        arrn[0] = n - n3 * n2;
        return n3;
    }

    public static final int floorDivide(long l, int n, int[] arrn) {
        if (l >= 0L) {
            arrn[0] = (int)(l % (long)n);
            return (int)(l / (long)n);
        }
        int n2 = (int)((l + 1L) / (long)n - 1L);
        arrn[0] = (int)(l - (long)(n2 * n));
        return n2;
    }

    public static final long floorDivide(long l, long l2) {
        l = l >= 0L ? (l /= l2) : (l + 1L) / l2 - 1L;
        return l;
    }

    public static final boolean isGregorianLeapYear(int n) {
        boolean bl = n % 4 == 0 && (n % 100 != 0 || n % 400 == 0);
        return bl;
    }

    public static final boolean isJulianLeapYear(int n) {
        boolean bl = n % 4 == 0;
        return bl;
    }

    public static final int mod(int n, int n2) {
        return n - CalendarUtils.floorDivide(n, n2) * n2;
    }

    public static final long mod(long l, long l2) {
        return l - CalendarUtils.floorDivide(l, l2) * l2;
    }

    public static final StringBuffer sprintf0d(StringBuffer stringBuffer, int n, int n2) {
        long l;
        long l2 = l = (long)n;
        int n3 = n2;
        if (l < 0L) {
            stringBuffer.append('-');
            l2 = -l;
            n3 = n2 - 1;
        }
        n = 10;
        for (n2 = 2; n2 < n3; ++n2) {
            n *= 10;
        }
        for (n2 = 1; n2 < n3 && l2 < (long)n; ++n2) {
            stringBuffer.append('0');
            n /= 10;
        }
        stringBuffer.append(l2);
        return stringBuffer;
    }

    public static final StringBuilder sprintf0d(StringBuilder stringBuilder, int n, int n2) {
        long l;
        long l2 = l = (long)n;
        int n3 = n2;
        if (l < 0L) {
            stringBuilder.append('-');
            l2 = -l;
            n3 = n2 - 1;
        }
        n = 10;
        for (n2 = 2; n2 < n3; ++n2) {
            n *= 10;
        }
        for (n2 = 1; n2 < n3 && l2 < (long)n; ++n2) {
            stringBuilder.append('0');
            n /= 10;
        }
        stringBuilder.append(l2);
        return stringBuilder;
    }
}

