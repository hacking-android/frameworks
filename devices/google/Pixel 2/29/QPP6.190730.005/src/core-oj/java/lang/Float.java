/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

import sun.misc.FloatingDecimal;

public final class Float
extends Number
implements Comparable<Float> {
    public static final int BYTES = 4;
    public static final int MAX_EXPONENT = 127;
    public static final float MAX_VALUE = Float.MAX_VALUE;
    public static final int MIN_EXPONENT = -126;
    public static final float MIN_NORMAL = Float.MIN_NORMAL;
    public static final float MIN_VALUE = Float.MIN_VALUE;
    public static final float NEGATIVE_INFINITY = Float.NEGATIVE_INFINITY;
    public static final float NaN = Float.NaN;
    public static final float POSITIVE_INFINITY = Float.POSITIVE_INFINITY;
    public static final int SIZE = 32;
    public static final Class<Float> TYPE = Class.getPrimitiveClass("float");
    private static final long serialVersionUID = -2671257302660747028L;
    private final float value;

    public Float(double d) {
        this.value = (float)d;
    }

    public Float(float f) {
        this.value = f;
    }

    public Float(String string) throws NumberFormatException {
        this.value = Float.parseFloat(string);
    }

    public static int compare(float f, float f2) {
        int n;
        int n2 = -1;
        if (f < f2) {
            return -1;
        }
        if (f > f2) {
            return 1;
        }
        int n3 = Float.floatToIntBits(f);
        if (n3 == (n = Float.floatToIntBits(f2))) {
            n2 = 0;
        } else if (n3 >= n) {
            n2 = 1;
        }
        return n2;
    }

    public static int floatToIntBits(float f) {
        int n;
        int n2 = n = Float.floatToRawIntBits(f);
        if ((n & 2139095040) == 2139095040) {
            n2 = n;
            if ((8388607 & n) != 0) {
                n2 = 2143289344;
            }
        }
        return n2;
    }

    public static native int floatToRawIntBits(float var0);

    public static int hashCode(float f) {
        return Float.floatToIntBits(f);
    }

    public static native float intBitsToFloat(int var0);

    public static boolean isFinite(float f) {
        boolean bl = Math.abs(f) <= Float.MAX_VALUE;
        return bl;
    }

    public static boolean isInfinite(float f) {
        boolean bl = f == Float.POSITIVE_INFINITY || f == Float.NEGATIVE_INFINITY;
        return bl;
    }

    public static boolean isNaN(float f) {
        boolean bl = f != f;
        return bl;
    }

    public static float max(float f, float f2) {
        return Math.max(f, f2);
    }

    public static float min(float f, float f2) {
        return Math.min(f, f2);
    }

    public static float parseFloat(String string) throws NumberFormatException {
        return FloatingDecimal.parseFloat(string);
    }

    public static float sum(float f, float f2) {
        return f + f2;
    }

    public static String toHexString(float f) {
        if (Math.abs(f) < Float.MIN_NORMAL && f != 0.0f) {
            return Double.toHexString(Math.scalb((double)f, -896)).replaceFirst("p-1022$", "p-126");
        }
        return Double.toHexString(f);
    }

    public static String toString(float f) {
        return FloatingDecimal.toJavaFormatString(f);
    }

    public static Float valueOf(float f) {
        return new Float(f);
    }

    public static Float valueOf(String string) throws NumberFormatException {
        return new Float(Float.parseFloat(string));
    }

    @Override
    public byte byteValue() {
        return (byte)this.value;
    }

    @Override
    public int compareTo(Float f) {
        return Float.compare(this.value, f.value);
    }

    @Override
    public double doubleValue() {
        return this.value;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof Float && Float.floatToIntBits(((Float)object).value) == Float.floatToIntBits(this.value);
        return bl;
    }

    @Override
    public float floatValue() {
        return this.value;
    }

    public int hashCode() {
        return Float.hashCode(this.value);
    }

    @Override
    public int intValue() {
        return (int)this.value;
    }

    public boolean isInfinite() {
        return Float.isInfinite(this.value);
    }

    public boolean isNaN() {
        return Float.isNaN(this.value);
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
        return Float.toString(this.value);
    }
}

