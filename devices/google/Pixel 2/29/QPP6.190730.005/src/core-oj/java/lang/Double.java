/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

import sun.misc.FloatingDecimal;

public final class Double
extends Number
implements Comparable<Double> {
    public static final int BYTES = 8;
    public static final int MAX_EXPONENT = 1023;
    public static final double MAX_VALUE = Double.MAX_VALUE;
    public static final int MIN_EXPONENT = -1022;
    public static final double MIN_NORMAL = Double.MIN_NORMAL;
    public static final double MIN_VALUE = Double.MIN_VALUE;
    public static final double NEGATIVE_INFINITY = Double.NEGATIVE_INFINITY;
    public static final double NaN = Double.NaN;
    public static final double POSITIVE_INFINITY = Double.POSITIVE_INFINITY;
    public static final int SIZE = 64;
    public static final Class<Double> TYPE = Class.getPrimitiveClass("double");
    private static final long serialVersionUID = -9172774392245257468L;
    private final double value;

    public Double(double d) {
        this.value = d;
    }

    public Double(String string) throws NumberFormatException {
        this.value = Double.parseDouble(string);
    }

    public static int compare(double d, double d2) {
        long l;
        int n = -1;
        if (d < d2) {
            return -1;
        }
        if (d > d2) {
            return 1;
        }
        long l2 = Double.doubleToLongBits(d);
        if (l2 == (l = Double.doubleToLongBits(d2))) {
            n = 0;
        } else if (l2 >= l) {
            n = 1;
        }
        return n;
    }

    public static long doubleToLongBits(double d) {
        long l;
        long l2 = l = Double.doubleToRawLongBits(d);
        if ((l & 9218868437227405312L) == 9218868437227405312L) {
            l2 = l;
            if ((0xFFFFFFFFFFFFFL & l) != 0L) {
                l2 = 9221120237041090560L;
            }
        }
        return l2;
    }

    public static native long doubleToRawLongBits(double var0);

    public static int hashCode(double d) {
        long l = Double.doubleToLongBits(d);
        return (int)(l >>> 32 ^ l);
    }

    public static boolean isFinite(double d) {
        boolean bl = Math.abs(d) <= Double.MAX_VALUE;
        return bl;
    }

    public static boolean isInfinite(double d) {
        boolean bl = d == Double.POSITIVE_INFINITY || d == Double.NEGATIVE_INFINITY;
        return bl;
    }

    public static boolean isNaN(double d) {
        boolean bl = d != d;
        return bl;
    }

    public static native double longBitsToDouble(long var0);

    public static double max(double d, double d2) {
        return Math.max(d, d2);
    }

    public static double min(double d, double d2) {
        return Math.min(d, d2);
    }

    public static double parseDouble(String string) throws NumberFormatException {
        return FloatingDecimal.parseDouble(string);
    }

    public static double sum(double d, double d2) {
        return d + d2;
    }

    public static String toHexString(double d) {
        if (!Double.isFinite(d)) {
            return Double.toString(d);
        }
        StringBuilder stringBuilder = new StringBuilder(24);
        if (Math.copySign(1.0, d) == -1.0) {
            stringBuilder.append("-");
        }
        stringBuilder.append("0x");
        d = Math.abs(d);
        if (d == 0.0) {
            stringBuilder.append("0.0p0");
        } else {
            int n = d < Double.MIN_NORMAL ? 1 : 0;
            long l = Double.doubleToLongBits(d);
            String string = n != 0 ? "0." : "1.";
            stringBuilder.append(string);
            string = Long.toHexString(l & 0xFFFFFFFFFFFFFL | 0x1000000000000000L).substring(3, 16);
            string = string.equals("0000000000000") ? "0" : string.replaceFirst("0{1,12}$", "");
            stringBuilder.append(string);
            stringBuilder.append('p');
            n = n != 0 ? -1022 : Math.getExponent(d);
            stringBuilder.append(n);
        }
        return stringBuilder.toString();
    }

    public static String toString(double d) {
        return FloatingDecimal.toJavaFormatString(d);
    }

    public static Double valueOf(double d) {
        return new Double(d);
    }

    public static Double valueOf(String string) throws NumberFormatException {
        return new Double(Double.parseDouble(string));
    }

    @Override
    public byte byteValue() {
        return (byte)this.value;
    }

    @Override
    public int compareTo(Double d) {
        return Double.compare(this.value, d.value);
    }

    @Override
    public double doubleValue() {
        return this.value;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof Double && Double.doubleToLongBits(((Double)object).value) == Double.doubleToLongBits(this.value);
        return bl;
    }

    @Override
    public float floatValue() {
        return (float)this.value;
    }

    public int hashCode() {
        return Double.hashCode(this.value);
    }

    @Override
    public int intValue() {
        return (int)this.value;
    }

    public boolean isInfinite() {
        return Double.isInfinite(this.value);
    }

    public boolean isNaN() {
        return Double.isNaN(this.value);
    }

    @Override
    public long longValue() {
        return (long)this.value;
    }

    @Override
    public short shortValue() {
        return (short)this.value;
    }

    public String toString() {
        return Double.toString(this.value);
    }
}

