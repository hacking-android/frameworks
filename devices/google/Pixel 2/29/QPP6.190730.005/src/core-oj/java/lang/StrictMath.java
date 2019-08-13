/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

import java.util.Random;

public final class StrictMath {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final double E = 2.718281828459045;
    public static final double PI = 3.141592653589793;

    private StrictMath() {
    }

    public static native double IEEEremainder(double var0, double var2);

    public static double abs(double d) {
        return Math.abs(d);
    }

    public static float abs(float f) {
        return Math.abs(f);
    }

    public static int abs(int n) {
        return Math.abs(n);
    }

    public static long abs(long l) {
        return Math.abs(l);
    }

    public static native double acos(double var0);

    public static int addExact(int n, int n2) {
        return Math.addExact(n, n2);
    }

    public static long addExact(long l, long l2) {
        return Math.addExact(l, l2);
    }

    public static native double asin(double var0);

    public static native double atan(double var0);

    public static native double atan2(double var0, double var2);

    public static native double cbrt(double var0);

    public static double ceil(double d) {
        return StrictMath.floorOrCeil(d, 0.0, 1.0, 1.0);
    }

    public static double copySign(double d, double d2) {
        block0 : {
            if (!Double.isNaN(d2)) break block0;
            d2 = 1.0;
        }
        return Math.copySign(d, d2);
    }

    public static float copySign(float f, float f2) {
        block0 : {
            if (!Float.isNaN(f2)) break block0;
            f2 = 1.0f;
        }
        return Math.copySign(f, f2);
    }

    public static native double cos(double var0);

    public static native double cosh(double var0);

    public static native double exp(double var0);

    public static native double expm1(double var0);

    public static double floor(double d) {
        return StrictMath.floorOrCeil(d, -1.0, 0.0, -1.0);
    }

    public static int floorDiv(int n, int n2) {
        return Math.floorDiv(n, n2);
    }

    public static long floorDiv(long l, long l2) {
        return Math.floorDiv(l, l2);
    }

    public static int floorMod(int n, int n2) {
        return Math.floorMod(n, n2);
    }

    public static long floorMod(long l, long l2) {
        return Math.floorMod(l, l2);
    }

    private static double floorOrCeil(double d, double d2, double d3, double d4) {
        int n = Math.getExponent(d);
        if (n < 0) {
            if (d != 0.0) {
                d = d < 0.0 ? d2 : d3;
            }
            return d;
        }
        if (n >= 52) {
            return d;
        }
        long l = 0xFFFFFFFFFFFFFL >> n;
        long l2 = Double.doubleToRawLongBits(d);
        if ((l & l2) == 0L) {
            return d;
        }
        d2 = d3 = Double.longBitsToDouble(l & l2);
        if (d4 * d > 0.0) {
            d2 = d3 + d4;
        }
        return d2;
    }

    public static int getExponent(double d) {
        return Math.getExponent(d);
    }

    public static int getExponent(float f) {
        return Math.getExponent(f);
    }

    public static native double hypot(double var0, double var2);

    public static native double log(double var0);

    public static native double log10(double var0);

    public static native double log1p(double var0);

    public static double max(double d, double d2) {
        return Math.max(d, d2);
    }

    public static float max(float f, float f2) {
        return Math.max(f, f2);
    }

    public static int max(int n, int n2) {
        return Math.max(n, n2);
    }

    public static long max(long l, long l2) {
        return Math.max(l, l2);
    }

    public static double min(double d, double d2) {
        return Math.min(d, d2);
    }

    public static float min(float f, float f2) {
        return Math.min(f, f2);
    }

    public static int min(int n, int n2) {
        return Math.min(n, n2);
    }

    public static long min(long l, long l2) {
        return Math.min(l, l2);
    }

    public static int multiplyExact(int n, int n2) {
        return Math.multiplyExact(n, n2);
    }

    public static long multiplyExact(long l, long l2) {
        return Math.multiplyExact(l, l2);
    }

    public static double nextAfter(double d, double d2) {
        return Math.nextAfter(d, d2);
    }

    public static float nextAfter(float f, double d) {
        return Math.nextAfter(f, d);
    }

    public static double nextDown(double d) {
        return Math.nextDown(d);
    }

    public static float nextDown(float f) {
        return Math.nextDown(f);
    }

    public static double nextUp(double d) {
        return Math.nextUp(d);
    }

    public static float nextUp(float f) {
        return Math.nextUp(f);
    }

    public static native double pow(double var0, double var2);

    public static double random() {
        return RandomNumberGeneratorHolder.randomNumberGenerator.nextDouble();
    }

    public static double rint(double d) {
        double d2;
        double d3 = Math.copySign(1.0, d);
        d = d2 = Math.abs(d);
        if (d2 < 4.503599627370496E15) {
            d = 4.503599627370496E15 + d2 - 4.503599627370496E15;
        }
        return d3 * d;
    }

    public static int round(float f) {
        return Math.round(f);
    }

    public static long round(double d) {
        return Math.round(d);
    }

    public static double scalb(double d, int n) {
        return Math.scalb(d, n);
    }

    public static float scalb(float f, int n) {
        return Math.scalb(f, n);
    }

    public static double signum(double d) {
        return Math.signum(d);
    }

    public static float signum(float f) {
        return Math.signum(f);
    }

    public static native double sin(double var0);

    public static native double sinh(double var0);

    public static native double sqrt(double var0);

    public static int subtractExact(int n, int n2) {
        return Math.subtractExact(n, n2);
    }

    public static long subtractExact(long l, long l2) {
        return Math.subtractExact(l, l2);
    }

    public static native double tan(double var0);

    public static native double tanh(double var0);

    public static strictfp double toDegrees(double d) {
        return 180.0 * d / 3.141592653589793;
    }

    public static int toIntExact(long l) {
        return Math.toIntExact(l);
    }

    public static strictfp double toRadians(double d) {
        return d / 180.0 * 3.141592653589793;
    }

    public static double ulp(double d) {
        return Math.ulp(d);
    }

    public static float ulp(float f) {
        return Math.ulp(f);
    }

    private static final class RandomNumberGeneratorHolder {
        static final Random randomNumberGenerator = new Random();

        private RandomNumberGeneratorHolder() {
        }
    }

}

