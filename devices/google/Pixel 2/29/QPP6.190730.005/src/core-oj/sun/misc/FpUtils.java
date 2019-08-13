/*
 * Decompiled with CFR 0.145.
 */
package sun.misc;

public class FpUtils {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private FpUtils() {
    }

    @Deprecated
    public static double copySign(double d, double d2) {
        return StrictMath.copySign(d, d2);
    }

    @Deprecated
    public static float copySign(float f, float f2) {
        return StrictMath.copySign(f, f2);
    }

    @Deprecated
    public static int getExponent(double d) {
        return Math.getExponent(d);
    }

    @Deprecated
    public static int getExponent(float f) {
        return Math.getExponent(f);
    }

    public static int ilogb(double d) {
        int n = FpUtils.getExponent(d);
        if (n != -1023) {
            if (n != 1024) {
                return n;
            }
            if (FpUtils.isNaN(d)) {
                return 1073741824;
            }
            return 268435456;
        }
        if (d == 0.0) {
            return -268435456;
        }
        long l = Double.doubleToRawLongBits(d) & 0xFFFFFFFFFFFFFL;
        while (l < 0x10000000000000L) {
            l *= 2L;
            --n;
        }
        return n + 1;
    }

    public static int ilogb(float f) {
        int n = FpUtils.getExponent(f);
        if (n != -127) {
            if (n != 128) {
                return n;
            }
            if (FpUtils.isNaN(f)) {
                return 1073741824;
            }
            return 268435456;
        }
        if (f == 0.0f) {
            return -268435456;
        }
        int n2 = Float.floatToRawIntBits(f) & 8388607;
        while (n2 < 8388608) {
            n2 *= 2;
            --n;
        }
        return n + 1;
    }

    @Deprecated
    public static boolean isFinite(double d) {
        return Double.isFinite(d);
    }

    @Deprecated
    public static boolean isFinite(float f) {
        return Float.isFinite(f);
    }

    public static boolean isInfinite(double d) {
        return Double.isInfinite(d);
    }

    public static boolean isInfinite(float f) {
        return Float.isInfinite(f);
    }

    public static boolean isNaN(double d) {
        return Double.isNaN(d);
    }

    public static boolean isNaN(float f) {
        return Float.isNaN(f);
    }

    public static boolean isUnordered(double d, double d2) {
        boolean bl = FpUtils.isNaN(d) || FpUtils.isNaN(d2);
        return bl;
    }

    public static boolean isUnordered(float f, float f2) {
        boolean bl = FpUtils.isNaN(f) || FpUtils.isNaN(f2);
        return bl;
    }

    @Deprecated
    public static double nextAfter(double d, double d2) {
        return Math.nextAfter(d, d2);
    }

    @Deprecated
    public static float nextAfter(float f, double d) {
        return Math.nextAfter(f, d);
    }

    @Deprecated
    public static double nextDown(double d) {
        return Math.nextDown(d);
    }

    @Deprecated
    public static double nextDown(float f) {
        return Math.nextDown(f);
    }

    @Deprecated
    public static double nextUp(double d) {
        return Math.nextUp(d);
    }

    @Deprecated
    public static float nextUp(float f) {
        return Math.nextUp(f);
    }

    @Deprecated
    public static double rawCopySign(double d, double d2) {
        return Math.copySign(d, d2);
    }

    @Deprecated
    public static float rawCopySign(float f, float f2) {
        return Math.copySign(f, f2);
    }

    @Deprecated
    public static double scalb(double d, int n) {
        return Math.scalb(d, n);
    }

    @Deprecated
    public static float scalb(float f, int n) {
        return Math.scalb(f, n);
    }

    @Deprecated
    public static double signum(double d) {
        return Math.signum(d);
    }

    @Deprecated
    public static float signum(float f) {
        return Math.signum(f);
    }

    @Deprecated
    public static double ulp(double d) {
        return Math.ulp(d);
    }

    @Deprecated
    public static float ulp(float f) {
        return Math.ulp(f);
    }
}

