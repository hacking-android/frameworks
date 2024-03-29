/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.graphics.-$
 *  com.android.internal.graphics.-$$Lambda
 *  com.android.internal.graphics.-$$Lambda$ColorUtils
 *  com.android.internal.graphics.-$$Lambda$ColorUtils$r4F1J7Y711kBAemoOw8QHbDba_8
 */
package com.android.internal.graphics;

import android.graphics.Color;
import com.android.internal.graphics.-$;
import com.android.internal.graphics._$$Lambda$ColorUtils$r4F1J7Y711kBAemoOw8QHbDba_8;
import com.android.internal.graphics._$$Lambda$ColorUtils$zbDH_52c8D9XBeqmvTHi3Boxl14;

public final class ColorUtils {
    private static final int MIN_ALPHA_SEARCH_MAX_ITERATIONS = 10;
    private static final int MIN_ALPHA_SEARCH_PRECISION = 1;
    private static final ThreadLocal<double[]> TEMP_ARRAY = new ThreadLocal();
    private static final double XYZ_EPSILON = 0.008856;
    private static final double XYZ_KAPPA = 903.3;
    private static final double XYZ_WHITE_REFERENCE_X = 95.047;
    private static final double XYZ_WHITE_REFERENCE_Y = 100.0;
    private static final double XYZ_WHITE_REFERENCE_Z = 108.883;

    private ColorUtils() {
    }

    public static int HSLToColor(float[] arrf) {
        float f = arrf[0];
        float f2 = arrf[1];
        float f3 = arrf[2];
        f2 = (1.0f - Math.abs(f3 * 2.0f - 1.0f)) * f2;
        float f4 = f3 - 0.5f * f2;
        f3 = (1.0f - Math.abs(f / 60.0f % 2.0f - 1.0f)) * f2;
        int n = (int)f / 60;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        switch (n) {
            default: {
                break;
            }
            case 5: 
            case 6: {
                n2 = Math.round((f2 + f4) * 255.0f);
                n3 = Math.round(f4 * 255.0f);
                n4 = Math.round((f3 + f4) * 255.0f);
                break;
            }
            case 4: {
                n2 = Math.round((f3 + f4) * 255.0f);
                n3 = Math.round(f4 * 255.0f);
                n4 = Math.round((f2 + f4) * 255.0f);
                break;
            }
            case 3: {
                n2 = Math.round(f4 * 255.0f);
                n3 = Math.round((f3 + f4) * 255.0f);
                n4 = Math.round((f2 + f4) * 255.0f);
                break;
            }
            case 2: {
                n2 = Math.round(f4 * 255.0f);
                n3 = Math.round((f2 + f4) * 255.0f);
                n4 = Math.round((f3 + f4) * 255.0f);
                break;
            }
            case 1: {
                n2 = Math.round((f3 + f4) * 255.0f);
                n3 = Math.round((f2 + f4) * 255.0f);
                n4 = Math.round(255.0f * f4);
                break;
            }
            case 0: {
                n2 = Math.round((f2 + f4) * 255.0f);
                n3 = Math.round((f3 + f4) * 255.0f);
                n4 = Math.round(255.0f * f4);
            }
        }
        return Color.rgb(ColorUtils.constrain(n2, 0, 255), ColorUtils.constrain(n3, 0, 255), ColorUtils.constrain(n4, 0, 255));
    }

    public static int LABToColor(double d, double d2, double d3) {
        double[] arrd = ColorUtils.getTempDouble3Array();
        ColorUtils.LABToXYZ(d, d2, d3, arrd);
        return ColorUtils.XYZToColor(arrd[0], arrd[1], arrd[2]);
    }

    public static void LABToXYZ(double d, double d2, double d3, double[] arrd) {
        double d4 = (d + 16.0) / 116.0;
        double d5 = d2 / 500.0 + d4;
        double d6 = d4 - d3 / 200.0;
        d2 = Math.pow(d5, 3.0);
        if (!(d2 > 0.008856)) {
            d2 = (d5 * 116.0 - 16.0) / 903.3;
        }
        d = d > 7.9996247999999985 ? Math.pow(d4, 3.0) : (d /= 903.3);
        d3 = Math.pow(d6, 3.0);
        if (!(d3 > 0.008856)) {
            d3 = (116.0 * d6 - 16.0) / 903.3;
        }
        arrd[0] = 95.047 * d2;
        arrd[1] = 100.0 * d;
        arrd[2] = 108.883 * d3;
    }

    public static void RGBToHSL(int n, int n2, int n3, float[] arrf) {
        float f = (float)n / 255.0f;
        float f2 = (float)n2 / 255.0f;
        float f3 = (float)n3 / 255.0f;
        float f4 = Math.max(f, Math.max(f2, f3));
        float f5 = Math.min(f, Math.min(f2, f3));
        float f6 = f4 - f5;
        float f7 = (f4 + f5) / 2.0f;
        if (f4 == f5) {
            f3 = 0.0f;
            f6 = 0.0f;
        } else {
            f3 = f4 == f ? (f2 - f3) / f6 % 6.0f : (f4 == f2 ? (f3 - f) / f6 + 2.0f : (f - f2) / f6 + 4.0f);
            f5 = f6 / (1.0f - Math.abs(2.0f * f7 - 1.0f));
            f6 = f3;
            f3 = f5;
        }
        f6 = f5 = 60.0f * f6 % 360.0f;
        if (f5 < 0.0f) {
            f6 = f5 + 360.0f;
        }
        arrf[0] = ColorUtils.constrain(f6, 0.0f, 360.0f);
        arrf[1] = ColorUtils.constrain(f3, 0.0f, 1.0f);
        arrf[2] = ColorUtils.constrain(f7, 0.0f, 1.0f);
    }

    public static void RGBToLAB(int n, int n2, int n3, double[] arrd) {
        ColorUtils.RGBToXYZ(n, n2, n3, arrd);
        ColorUtils.XYZToLAB(arrd[0], arrd[1], arrd[2], arrd);
    }

    public static void RGBToXYZ(int n, int n2, int n3, double[] arrd) {
        if (arrd.length == 3) {
            double d = (double)n / 255.0;
            d = d < 0.04045 ? (d /= 12.92) : Math.pow((d + 0.055) / 1.055, 2.4);
            double d2 = (double)n2 / 255.0;
            d2 = d2 < 0.04045 ? (d2 /= 12.92) : Math.pow((d2 + 0.055) / 1.055, 2.4);
            double d3 = (double)n3 / 255.0;
            d3 = d3 < 0.04045 ? (d3 /= 12.92) : Math.pow((0.055 + d3) / 1.055, 2.4);
            arrd[0] = (0.4124 * d + 0.3576 * d2 + 0.1805 * d3) * 100.0;
            arrd[1] = (0.2126 * d + 0.7152 * d2 + 0.0722 * d3) * 100.0;
            arrd[2] = (0.0193 * d + 0.1192 * d2 + 0.9505 * d3) * 100.0;
            return;
        }
        throw new IllegalArgumentException("outXyz must have a length of 3.");
    }

    public static int XYZToColor(double d, double d2, double d3) {
        double d4 = (3.2406 * d + -1.5372 * d2 + -0.4986 * d3) / 100.0;
        double d5 = (-0.9689 * d + 1.8758 * d2 + 0.0415 * d3) / 100.0;
        d3 = (0.0557 * d + -0.204 * d2 + 1.057 * d3) / 100.0;
        d = d4 > 0.0031308 ? Math.pow(d4, 0.4166666666666667) * 1.055 - 0.055 : d4 * 12.92;
        d2 = d5 > 0.0031308 ? Math.pow(d5, 0.4166666666666667) * 1.055 - 0.055 : d5 * 12.92;
        d3 = d3 > 0.0031308 ? Math.pow(d3, 0.4166666666666667) * 1.055 - 0.055 : (d3 *= 12.92);
        return Color.rgb(ColorUtils.constrain((int)Math.round(d * 255.0), 0, 255), ColorUtils.constrain((int)Math.round(d2 * 255.0), 0, 255), ColorUtils.constrain((int)Math.round(255.0 * d3), 0, 255));
    }

    public static void XYZToLAB(double d, double d2, double d3, double[] arrd) {
        if (arrd.length == 3) {
            d = ColorUtils.pivotXyzComponent(d / 95.047);
            d2 = ColorUtils.pivotXyzComponent(d2 / 100.0);
            d3 = ColorUtils.pivotXyzComponent(d3 / 108.883);
            arrd[0] = Math.max(0.0, 116.0 * d2 - 16.0);
            arrd[1] = (d - d2) * 500.0;
            arrd[2] = (d2 - d3) * 200.0;
            return;
        }
        throw new IllegalArgumentException("outLab must have a length of 3.");
    }

    private static int binaryAlphaSearch(int n, int n2, float f, ContrastCalculator contrastCalculator) {
        int n3 = 0;
        int n4 = 255;
        for (int i = 0; i <= 10 && n4 - n3 > 1; ++i) {
            int n5 = (n3 + n4) / 2;
            if (contrastCalculator.calculateContrast(n, n2, n5) < (double)f) {
                n3 = n5;
                continue;
            }
            n4 = n5;
        }
        return n4;
    }

    public static int blendARGB(int n, int n2, float f) {
        float f2 = 1.0f - f;
        float f3 = Color.alpha(n);
        float f4 = Color.alpha(n2);
        float f5 = Color.red(n);
        float f6 = Color.red(n2);
        float f7 = Color.green(n);
        float f8 = Color.green(n2);
        float f9 = Color.blue(n);
        float f10 = Color.blue(n2);
        return Color.argb((int)(f3 * f2 + f4 * f), (int)(f5 * f2 + f6 * f), (int)(f7 * f2 + f8 * f), (int)(f9 * f2 + f10 * f));
    }

    public static void blendHSL(float[] arrf, float[] arrf2, float f, float[] arrf3) {
        if (arrf3.length == 3) {
            float f2 = 1.0f - f;
            arrf3[0] = ColorUtils.circularInterpolate(arrf[0], arrf2[0], f);
            arrf3[1] = arrf[1] * f2 + arrf2[1] * f;
            arrf3[2] = arrf[2] * f2 + arrf2[2] * f;
            return;
        }
        throw new IllegalArgumentException("result must have a length of 3.");
    }

    public static void blendLAB(double[] arrd, double[] arrd2, double d, double[] arrd3) {
        if (arrd3.length == 3) {
            double d2 = 1.0 - d;
            arrd3[0] = arrd[0] * d2 + arrd2[0] * d;
            arrd3[1] = arrd[1] * d2 + arrd2[1] * d;
            arrd3[2] = arrd[2] * d2 + arrd2[2] * d;
            return;
        }
        throw new IllegalArgumentException("outResult must have a length of 3.");
    }

    public static double calculateContrast(int n, int n2) {
        if (Color.alpha(n2) == 255) {
            int n3 = n;
            if (Color.alpha(n) < 255) {
                n3 = ColorUtils.compositeColors(n, n2);
            }
            double d = ColorUtils.calculateLuminance(n3) + 0.05;
            double d2 = ColorUtils.calculateLuminance(n2) + 0.05;
            return Math.max(d, d2) / Math.min(d, d2);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("background can not be translucent: #");
        stringBuilder.append(Integer.toHexString(n2));
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static double calculateLuminance(int n) {
        double[] arrd = ColorUtils.getTempDouble3Array();
        ColorUtils.colorToXYZ(n, arrd);
        return arrd[1] / 100.0;
    }

    public static int calculateMinimumAlpha(int n, int n2, float f) {
        if (Color.alpha(n2) == 255) {
            -$.Lambda.ColorUtils.r4F1J7Y711kBAemoOw8QHbDba_8 r4F1J7Y711kBAemoOw8QHbDba_82 = _$$Lambda$ColorUtils$r4F1J7Y711kBAemoOw8QHbDba_8.INSTANCE;
            if (r4F1J7Y711kBAemoOw8QHbDba_82.calculateContrast(n, n2, 255) < (double)f) {
                return -1;
            }
            return ColorUtils.binaryAlphaSearch(ColorUtils.setAlphaComponent(n, 255), n2, f, (ContrastCalculator)r4F1J7Y711kBAemoOw8QHbDba_82);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("background can not be translucent: #");
        stringBuilder.append(Integer.toHexString(n2));
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static int calculateMinimumBackgroundAlpha(int n, int n2, float f) {
        return ColorUtils.binaryAlphaSearch(n, ColorUtils.setAlphaComponent(n2, 255), f, new _$$Lambda$ColorUtils$zbDH_52c8D9XBeqmvTHi3Boxl14(ColorUtils.setAlphaComponent(n, 255)));
    }

    static float circularInterpolate(float f, float f2, float f3) {
        float f4 = f;
        float f5 = f2;
        if (Math.abs(f2 - f) > 180.0f) {
            if (f2 > f) {
                f4 = f + 360.0f;
                f5 = f2;
            } else {
                f5 = f2 + 360.0f;
                f4 = f;
            }
        }
        return ((f5 - f4) * f3 + f4) % 360.0f;
    }

    public static void colorToHSL(int n, float[] arrf) {
        ColorUtils.RGBToHSL(Color.red(n), Color.green(n), Color.blue(n), arrf);
    }

    public static void colorToLAB(int n, double[] arrd) {
        ColorUtils.RGBToLAB(Color.red(n), Color.green(n), Color.blue(n), arrd);
    }

    public static void colorToXYZ(int n, double[] arrd) {
        ColorUtils.RGBToXYZ(Color.red(n), Color.green(n), Color.blue(n), arrd);
    }

    private static int compositeAlpha(int n, int n2) {
        return 255 - (255 - n2) * (255 - n) / 255;
    }

    public static int compositeColors(int n, int n2) {
        int n3 = Color.alpha(n2);
        int n4 = Color.alpha(n);
        int n5 = ColorUtils.compositeAlpha(n4, n3);
        return Color.argb(n5, ColorUtils.compositeComponent(Color.red(n), n4, Color.red(n2), n3, n5), ColorUtils.compositeComponent(Color.green(n), n4, Color.green(n2), n3, n5), ColorUtils.compositeComponent(Color.blue(n), n4, Color.blue(n2), n3, n5));
    }

    private static int compositeComponent(int n, int n2, int n3, int n4, int n5) {
        if (n5 == 0) {
            return 0;
        }
        return (n * 255 * n2 + n3 * n4 * (255 - n2)) / (n5 * 255);
    }

    private static float constrain(float f, float f2, float f3) {
        block1 : {
            block0 : {
                if (!(f < f2)) break block0;
                f = f2;
                break block1;
            }
            if (!(f > f3)) break block1;
            f = f3;
        }
        return f;
    }

    private static int constrain(int n, int n2, int n3) {
        block1 : {
            block0 : {
                if (n >= n2) break block0;
                n = n2;
                break block1;
            }
            if (n <= n3) break block1;
            n = n3;
        }
        return n;
    }

    public static double distanceEuclidean(double[] arrd, double[] arrd2) {
        return Math.sqrt(Math.pow(arrd[0] - arrd2[0], 2.0) + Math.pow(arrd[1] - arrd2[1], 2.0) + Math.pow(arrd[2] - arrd2[2], 2.0));
    }

    private static double[] getTempDouble3Array() {
        double[] arrd;
        double[] arrd2 = arrd = TEMP_ARRAY.get();
        if (arrd == null) {
            arrd2 = new double[3];
            TEMP_ARRAY.set(arrd2);
        }
        return arrd2;
    }

    static /* synthetic */ double lambda$calculateMinimumAlpha$1(int n, int n2, int n3) {
        return ColorUtils.calculateContrast(ColorUtils.setAlphaComponent(n, n3), n2);
    }

    static /* synthetic */ double lambda$calculateMinimumBackgroundAlpha$0(int n, int n2, int n3, int n4) {
        return ColorUtils.calculateContrast(n2, ColorUtils.setAlphaComponent(ColorUtils.blendARGB(n, n3, (float)n4 / 255.0f), 255));
    }

    private static double pivotXyzComponent(double d) {
        d = d > 0.008856 ? Math.pow(d, 0.3333333333333333) : (903.3 * d + 16.0) / 116.0;
        return d;
    }

    public static int setAlphaComponent(int n, int n2) {
        if (n2 >= 0 && n2 <= 255) {
            return 16777215 & n | n2 << 24;
        }
        throw new IllegalArgumentException("alpha must be between 0 and 255.");
    }

    private static interface ContrastCalculator {
        public double calculateContrast(int var1, int var2, int var3);
    }

}

