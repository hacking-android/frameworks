/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.CriticalNative
 */
package java.lang;

import dalvik.annotation.optimization.CriticalNative;
import java.util.Random;

public final class Math {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final double E = 2.718281828459045;
    public static final double PI = 3.141592653589793;
    private static long negativeZeroDoubleBits;
    private static long negativeZeroFloatBits;
    static double twoToTheDoubleScaleDown;
    static double twoToTheDoubleScaleUp;

    static {
        negativeZeroFloatBits = Float.floatToRawIntBits(0.0f);
        negativeZeroDoubleBits = Double.doubleToRawLongBits(0.0);
        twoToTheDoubleScaleUp = Math.powerOfTwoD(512);
        twoToTheDoubleScaleDown = Math.powerOfTwoD(-512);
    }

    private Math() {
    }

    @CriticalNative
    public static native double IEEEremainder(double var0, double var2);

    public static double abs(double d) {
        return Double.longBitsToDouble(Double.doubleToRawLongBits(d) & Long.MAX_VALUE);
    }

    public static float abs(float f) {
        return Float.intBitsToFloat(Float.floatToRawIntBits(f) & Integer.MAX_VALUE);
    }

    public static int abs(int n) {
        block0 : {
            if (n >= 0) break block0;
            n = -n;
        }
        return n;
    }

    public static long abs(long l) {
        block0 : {
            if (l >= 0L) break block0;
            l = -l;
        }
        return l;
    }

    @CriticalNative
    public static native double acos(double var0);

    public static int addExact(int n, int n2) {
        int n3 = n + n2;
        if (((n ^ n3) & (n2 ^ n3)) >= 0) {
            return n3;
        }
        throw new ArithmeticException("integer overflow");
    }

    public static long addExact(long l, long l2) {
        long l3 = l + l2;
        if (((l ^ l3) & (l2 ^ l3)) >= 0L) {
            return l3;
        }
        throw new ArithmeticException("long overflow");
    }

    @CriticalNative
    public static native double asin(double var0);

    @CriticalNative
    public static native double atan(double var0);

    @CriticalNative
    public static native double atan2(double var0, double var2);

    @CriticalNative
    public static native double cbrt(double var0);

    @CriticalNative
    public static native double ceil(double var0);

    public static double copySign(double d, double d2) {
        return Double.longBitsToDouble(Double.doubleToRawLongBits(d2) & Long.MIN_VALUE | Double.doubleToRawLongBits(d) & Long.MAX_VALUE);
    }

    public static float copySign(float f, float f2) {
        return Float.intBitsToFloat(Float.floatToRawIntBits(f2) & Integer.MIN_VALUE | Float.floatToRawIntBits(f) & Integer.MAX_VALUE);
    }

    @CriticalNative
    public static native double cos(double var0);

    @CriticalNative
    public static native double cosh(double var0);

    public static int decrementExact(int n) {
        if (n != Integer.MIN_VALUE) {
            return n - 1;
        }
        throw new ArithmeticException("integer overflow");
    }

    public static long decrementExact(long l) {
        if (l != Long.MIN_VALUE) {
            return l - 1L;
        }
        throw new ArithmeticException("long overflow");
    }

    @CriticalNative
    public static native double exp(double var0);

    @CriticalNative
    public static native double expm1(double var0);

    @CriticalNative
    public static native double floor(double var0);

    public static int floorDiv(int n, int n2) {
        int n3;
        int n4 = n3 = n / n2;
        if ((n ^ n2) < 0) {
            n4 = n3;
            if (n3 * n2 != n) {
                n4 = n3 - 1;
            }
        }
        return n4;
    }

    public static long floorDiv(long l, long l2) {
        long l3;
        long l4 = l3 = l / l2;
        if ((l ^ l2) < 0L) {
            l4 = l3;
            if (l3 * l2 != l) {
                l4 = l3 - 1L;
            }
        }
        return l4;
    }

    public static int floorMod(int n, int n2) {
        return n - Math.floorDiv(n, n2) * n2;
    }

    public static long floorMod(long l, long l2) {
        return l - Math.floorDiv(l, l2) * l2;
    }

    public static int getExponent(double d) {
        return (int)(((Double.doubleToRawLongBits(d) & 9218868437227405312L) >> 52) - 1023L);
    }

    public static int getExponent(float f) {
        return ((Float.floatToRawIntBits(f) & 2139095040) >> 23) - 127;
    }

    @CriticalNative
    public static native double hypot(double var0, double var2);

    public static int incrementExact(int n) {
        if (n != Integer.MAX_VALUE) {
            return n + 1;
        }
        throw new ArithmeticException("integer overflow");
    }

    public static long incrementExact(long l) {
        if (l != Long.MAX_VALUE) {
            return 1L + l;
        }
        throw new ArithmeticException("long overflow");
    }

    @CriticalNative
    public static native double log(double var0);

    @CriticalNative
    public static native double log10(double var0);

    @CriticalNative
    public static native double log1p(double var0);

    public static double max(double d, double d2) {
        if (d != d) {
            return d;
        }
        if (d == 0.0 && d2 == 0.0 && Double.doubleToRawLongBits(d) == negativeZeroDoubleBits) {
            return d2;
        }
        if (!(d >= d2)) {
            d = d2;
        }
        return d;
    }

    public static float max(float f, float f2) {
        if (f != f) {
            return f;
        }
        if (f == 0.0f && f2 == 0.0f && (long)Float.floatToRawIntBits(f) == negativeZeroFloatBits) {
            return f2;
        }
        if (!(f >= f2)) {
            f = f2;
        }
        return f;
    }

    public static int max(int n, int n2) {
        if (n < n2) {
            n = n2;
        }
        return n;
    }

    public static long max(long l, long l2) {
        if (l < l2) {
            l = l2;
        }
        return l;
    }

    public static double min(double d, double d2) {
        block2 : {
            if (d != d) {
                return d;
            }
            if (d == 0.0 && d2 == 0.0 && Double.doubleToRawLongBits(d2) == negativeZeroDoubleBits) {
                return d2;
            }
            if (!(d <= d2)) break block2;
            d2 = d;
        }
        return d2;
    }

    public static float min(float f, float f2) {
        if (f != f) {
            return f;
        }
        if (f == 0.0f && f2 == 0.0f && (long)Float.floatToRawIntBits(f2) == negativeZeroFloatBits) {
            return f2;
        }
        if (!(f <= f2)) {
            f = f2;
        }
        return f;
    }

    public static int min(int n, int n2) {
        if (n > n2) {
            n = n2;
        }
        return n;
    }

    public static long min(long l, long l2) {
        if (l > l2) {
            l = l2;
        }
        return l;
    }

    public static int multiplyExact(int n, int n2) {
        long l = (long)n * (long)n2;
        if ((long)((int)l) == l) {
            return (int)l;
        }
        throw new ArithmeticException("integer overflow");
    }

    public static long multiplyExact(long l, long l2) {
        long l3 = l * l2;
        if ((Math.abs(l) | Math.abs(l2)) >>> 31 != 0L && (l2 != 0L && l3 / l2 != l || l == Long.MIN_VALUE && l2 == -1L)) {
            throw new ArithmeticException("long overflow");
        }
        return l3;
    }

    public static int negateExact(int n) {
        if (n != Integer.MIN_VALUE) {
            return -n;
        }
        throw new ArithmeticException("integer overflow");
    }

    public static long negateExact(long l) {
        if (l != Long.MIN_VALUE) {
            return -l;
        }
        throw new ArithmeticException("long overflow");
    }

    public static double nextAfter(double d, double d2) {
        if (!Double.isNaN(d) && !Double.isNaN(d2)) {
            if (d == d2) {
                return d2;
            }
            long l = Double.doubleToRawLongBits(0.0 + d);
            long l2 = 1L;
            if (d2 > d) {
                if (l < 0L) {
                    l2 = -1L;
                }
                l2 = l + l2;
            } else {
                l2 = l > 0L ? l - 1L : (l < 0L ? l + 1L : -9223372036854775807L);
            }
            return Double.longBitsToDouble(l2);
        }
        return d + d2;
    }

    public static float nextAfter(float f, double d) {
        if (!Float.isNaN(f) && !Double.isNaN(d)) {
            if ((double)f == d) {
                return (float)d;
            }
            int n = Float.floatToRawIntBits(0.0f + f);
            double d2 = f;
            int n2 = 1;
            if (d > d2) {
                if (n < 0) {
                    n2 = -1;
                }
                n2 = n + n2;
            } else {
                n2 = n > 0 ? n - 1 : (n < 0 ? n + 1 : -2147483647);
            }
            return Float.intBitsToFloat(n2);
        }
        return (float)d + f;
    }

    public static double nextDown(double d) {
        if (!Double.isNaN(d) && d != Double.NEGATIVE_INFINITY) {
            if (d == 0.0) {
                return -4.9E-324;
            }
            long l = Double.doubleToRawLongBits(d);
            long l2 = d > 0.0 ? -1L : 1L;
            return Double.longBitsToDouble(l + l2);
        }
        return d;
    }

    public static float nextDown(float f) {
        if (!Float.isNaN(f) && f != Float.NEGATIVE_INFINITY) {
            if (f == 0.0f) {
                return -1.4E-45f;
            }
            int n = Float.floatToRawIntBits(f);
            int n2 = f > 0.0f ? -1 : 1;
            return Float.intBitsToFloat(n + n2);
        }
        return f;
    }

    public static double nextUp(double d) {
        if (!Double.isNaN(d) && d != Double.POSITIVE_INFINITY) {
            long l = Double.doubleToRawLongBits(d += 0.0);
            long l2 = d >= 0.0 ? 1L : -1L;
            return Double.longBitsToDouble(l + l2);
        }
        return d;
    }

    public static float nextUp(float f) {
        if (!Float.isNaN(f) && f != Float.POSITIVE_INFINITY) {
            int n = Float.floatToRawIntBits(f += 0.0f);
            int n2 = f >= 0.0f ? 1 : -1;
            return Float.intBitsToFloat(n + n2);
        }
        return f;
    }

    @CriticalNative
    public static native double pow(double var0, double var2);

    static double powerOfTwoD(int n) {
        return Double.longBitsToDouble((long)n + 1023L << 52 & 9218868437227405312L);
    }

    static float powerOfTwoF(int n) {
        return Float.intBitsToFloat(n + 127 << 23 & 2139095040);
    }

    public static double random() {
        return RandomNumberGeneratorHolder.randomNumberGenerator.nextDouble();
    }

    public static int randomIntInternal() {
        return RandomNumberGeneratorHolder.randomNumberGenerator.nextInt();
    }

    public static long randomLongInternal() {
        return RandomNumberGeneratorHolder.randomNumberGenerator.nextLong();
    }

    @CriticalNative
    public static native double rint(double var0);

    public static int round(float f) {
        int n = Float.floatToRawIntBits(f);
        int n2 = 149 - ((2139095040 & n) >> 23);
        if ((n2 & -32) == 0) {
            int n3;
            int n4 = n3 = 8388607 & n | 8388608;
            if (n < 0) {
                n4 = -n3;
            }
            return (n4 >> n2) + 1 >> 1;
        }
        return (int)f;
    }

    public static long round(double d) {
        long l = Double.doubleToRawLongBits(d);
        long l2 = 1074L - ((9218868437227405312L & l) >> 52);
        if ((-64L & l2) == 0L) {
            long l3;
            long l4 = l3 = 0xFFFFFFFFFFFFFL & l | 0x10000000000000L;
            if (l < 0L) {
                l4 = -l3;
            }
            return (l4 >> (int)l2) + 1L >> 1;
        }
        return (long)d;
    }

    public static double scalb(double d, int n) {
        double d2;
        int n2;
        if (n < 0) {
            n2 = Math.max(n, -2099);
            n = -512;
            d2 = twoToTheDoubleScaleDown;
        } else {
            n2 = Math.min(n, 2099);
            n = 512;
            d2 = twoToTheDoubleScaleUp;
        }
        int n3 = n2 >> 8 >>> 23;
        n3 = (n2 + n3 & 511) - n3;
        d *= Math.powerOfTwoD(n3);
        n2 -= n3;
        while (n2 != 0) {
            d *= d2;
            n2 -= n;
        }
        return d;
    }

    public static float scalb(float f, int n) {
        n = Math.max(Math.min(n, 278), -278);
        return (float)((double)f * Math.powerOfTwoD(n));
    }

    public static void setRandomSeedInternal(long l) {
        RandomNumberGeneratorHolder.randomNumberGenerator.setSeed(l);
    }

    public static double signum(double d) {
        block0 : {
            if (d == 0.0 || Double.isNaN(d)) break block0;
            d = Math.copySign(1.0, d);
        }
        return d;
    }

    public static float signum(float f) {
        block0 : {
            if (f == 0.0f || Float.isNaN(f)) break block0;
            f = Math.copySign(1.0f, f);
        }
        return f;
    }

    @CriticalNative
    public static native double sin(double var0);

    @CriticalNative
    public static native double sinh(double var0);

    @CriticalNative
    public static native double sqrt(double var0);

    public static int subtractExact(int n, int n2) {
        int n3 = n - n2;
        if (((n ^ n2) & (n ^ n3)) >= 0) {
            return n3;
        }
        throw new ArithmeticException("integer overflow");
    }

    public static long subtractExact(long l, long l2) {
        long l3 = l - l2;
        if (((l ^ l2) & (l ^ l3)) >= 0L) {
            return l3;
        }
        throw new ArithmeticException("long overflow");
    }

    @CriticalNative
    public static native double tan(double var0);

    @CriticalNative
    public static native double tanh(double var0);

    public static double toDegrees(double d) {
        return 180.0 * d / 3.141592653589793;
    }

    public static int toIntExact(long l) {
        if ((long)((int)l) == l) {
            return (int)l;
        }
        throw new ArithmeticException("integer overflow");
    }

    public static double toRadians(double d) {
        return d / 180.0 * 3.141592653589793;
    }

    public static double ulp(double d) {
        int n = Math.getExponent(d);
        if (n != -1023) {
            if (n != 1024) {
                if ((n -= 52) >= -1022) {
                    return Math.powerOfTwoD(n);
                }
                return Double.longBitsToDouble(1L << n + 1074);
            }
            return Math.abs(d);
        }
        return Double.MIN_VALUE;
    }

    public static float ulp(float f) {
        int n = Math.getExponent(f);
        if (n != -127) {
            if (n != 128) {
                if ((n -= 23) >= -126) {
                    return Math.powerOfTwoF(n);
                }
                return Float.intBitsToFloat(1 << n + 149);
            }
            return Math.abs(f);
        }
        return Float.MIN_VALUE;
    }

    private static final class RandomNumberGeneratorHolder {
        static final Random randomNumberGenerator = new Random();

        private RandomNumberGeneratorHolder() {
        }
    }

}

