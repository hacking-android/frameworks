/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.graphics.-$
 *  android.graphics.-$$Lambda
 *  android.graphics.-$$Lambda$ColorSpace
 *  android.graphics.-$$Lambda$ColorSpace$BNp-1CyCzsQzfE-Ads9uc4rJDfw
 *  android.graphics.-$$Lambda$ColorSpace$S2rlqJvkXGTpUF6mZhvkElds8JE
 *  libcore.util.NativeAllocationRegistry
 */
package android.graphics;

import android.graphics.-$;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics._$$Lambda$ColorSpace$BNp_1CyCzsQzfE_Ads9uc4rJDfw;
import android.graphics._$$Lambda$ColorSpace$Rgb$8EkhO2jIf14tuA3BvrmYJMa7YXM;
import android.graphics._$$Lambda$ColorSpace$Rgb$CqKld6797g7__JnuY0NeFz5q4_E;
import android.graphics._$$Lambda$ColorSpace$Rgb$V_0lmM2WEpxGBDV_1G1wvvidn7Y;
import android.graphics._$$Lambda$ColorSpace$Rgb$ZvS77aTfobOSa2o9MTqYMph4Rcg;
import android.graphics._$$Lambda$ColorSpace$Rgb$b9VGKuNnse0bbguR9jbOM_wK2Ac;
import android.graphics._$$Lambda$ColorSpace$Rgb$bWzafC8vMHNuVmRuTUPEFUMlfuY;
import android.graphics._$$Lambda$ColorSpace$Rgb$iMkODTKa3_8kPZUnZZerD2Lv_yo;
import android.graphics._$$Lambda$ColorSpace$S2rlqJvkXGTpUF6mZhvkElds8JE;
import android.util.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.DoubleUnaryOperator;
import libcore.util.NativeAllocationRegistry;

public abstract class ColorSpace {
    public static final float[] ILLUMINANT_A = new float[]{0.44757f, 0.40745f};
    public static final float[] ILLUMINANT_B = new float[]{0.34842f, 0.35161f};
    public static final float[] ILLUMINANT_C = new float[]{0.31006f, 0.31616f};
    public static final float[] ILLUMINANT_D50 = new float[]{0.34567f, 0.3585f};
    private static final float[] ILLUMINANT_D50_XYZ;
    public static final float[] ILLUMINANT_D55;
    public static final float[] ILLUMINANT_D60;
    public static final float[] ILLUMINANT_D65;
    public static final float[] ILLUMINANT_D75;
    public static final float[] ILLUMINANT_E;
    public static final int MAX_ID = 63;
    public static final int MIN_ID = -1;
    private static final float[] NTSC_1953_PRIMARIES;
    private static final float[] SRGB_PRIMARIES;
    private static final Rgb.TransferParameters SRGB_TRANSFER_PARAMETERS;
    private static final ColorSpace[] sNamedColorSpaces;
    private final int mId;
    private final Model mModel;
    private final String mName;

    static {
        ILLUMINANT_D55 = new float[]{0.33242f, 0.34743f};
        ILLUMINANT_D60 = new float[]{0.32168f, 0.33767f};
        ILLUMINANT_D65 = new float[]{0.31271f, 0.32902f};
        ILLUMINANT_D75 = new float[]{0.29902f, 0.31485f};
        ILLUMINANT_E = new float[]{0.33333f, 0.33333f};
        SRGB_PRIMARIES = new float[]{0.64f, 0.33f, 0.3f, 0.6f, 0.15f, 0.06f};
        NTSC_1953_PRIMARIES = new float[]{0.67f, 0.33f, 0.21f, 0.71f, 0.14f, 0.08f};
        ILLUMINANT_D50_XYZ = new float[]{0.964212f, 1.0f, 0.825188f};
        SRGB_TRANSFER_PARAMETERS = new Rgb.TransferParameters(0.9478672985781991, 0.05213270142180095, 0.07739938080495357, 0.04045, 2.4);
        sNamedColorSpaces = new ColorSpace[Named.values().length];
        ColorSpace.sNamedColorSpaces[Named.SRGB.ordinal()] = new Rgb("sRGB IEC61966-2.1", SRGB_PRIMARIES, ILLUMINANT_D65, SRGB_TRANSFER_PARAMETERS, Named.SRGB.ordinal());
        ColorSpace.sNamedColorSpaces[Named.LINEAR_SRGB.ordinal()] = new Rgb("sRGB IEC61966-2.1 (Linear)", SRGB_PRIMARIES, ILLUMINANT_D65, 1.0, 0.0f, 1.0f, Named.LINEAR_SRGB.ordinal());
        ColorSpace.sNamedColorSpaces[Named.EXTENDED_SRGB.ordinal()] = new Rgb("scRGB-nl IEC 61966-2-2:2003", SRGB_PRIMARIES, ILLUMINANT_D65, null, (DoubleUnaryOperator)_$$Lambda$ColorSpace$BNp_1CyCzsQzfE_Ads9uc4rJDfw.INSTANCE, (DoubleUnaryOperator)_$$Lambda$ColorSpace$S2rlqJvkXGTpUF6mZhvkElds8JE.INSTANCE, -0.799f, 2.399f, SRGB_TRANSFER_PARAMETERS, Named.EXTENDED_SRGB.ordinal());
        ColorSpace.sNamedColorSpaces[Named.LINEAR_EXTENDED_SRGB.ordinal()] = new Rgb("scRGB IEC 61966-2-2:2003", SRGB_PRIMARIES, ILLUMINANT_D65, 1.0, -0.5f, 7.499f, Named.LINEAR_EXTENDED_SRGB.ordinal());
        Object object = sNamedColorSpaces;
        int n = Named.BT709.ordinal();
        Object object2 = ILLUMINANT_D65;
        Object object3 = new Rgb.TransferParameters(0.9099181073703367, 0.09008189262966333, 0.2222222222222222, 0.081, 2.2222222222222223);
        int n2 = Named.BT709.ordinal();
        object[n] = new Rgb("Rec. ITU-R BT.709-5", new float[]{0.64f, 0.33f, 0.3f, 0.6f, 0.15f, 0.06f}, (float[])object2, (Rgb.TransferParameters)object3, n2);
        object2 = sNamedColorSpaces;
        n = Named.BT2020.ordinal();
        object = ILLUMINANT_D65;
        object3 = new Rgb.TransferParameters(0.9096697898662786, 0.09033021013372146, 0.2222222222222222, 0.08145, 2.2222222222222223);
        n2 = Named.BT2020.ordinal();
        object2[n] = (float)new Rgb("Rec. ITU-R BT.2020-1", new float[]{0.708f, 0.292f, 0.17f, 0.797f, 0.131f, 0.046f}, (float[])object, (Rgb.TransferParameters)object3, n2);
        object3 = sNamedColorSpaces;
        n2 = Named.DCI_P3.ordinal();
        n = Named.DCI_P3.ordinal();
        object3[n2] = new Rgb("SMPTE RP 431-2-2007 DCI (P3)", new float[]{0.68f, 0.32f, 0.265f, 0.69f, 0.15f, 0.06f}, new float[]{0.314f, 0.351f}, 2.6, 0.0f, 1.0f, n);
        object2 = sNamedColorSpaces;
        n2 = Named.DISPLAY_P3.ordinal();
        object = ILLUMINANT_D65;
        object3 = SRGB_TRANSFER_PARAMETERS;
        n = Named.DISPLAY_P3.ordinal();
        object2[n2] = (float)new Rgb("Display P3", new float[]{0.68f, 0.32f, 0.265f, 0.69f, 0.15f, 0.06f}, (float[])object, (Rgb.TransferParameters)object3, n);
        ColorSpace.sNamedColorSpaces[Named.NTSC_1953.ordinal()] = new Rgb("NTSC (1953)", NTSC_1953_PRIMARIES, ILLUMINANT_C, new Rgb.TransferParameters(0.9099181073703367, 0.09008189262966333, 0.2222222222222222, 0.081, 2.2222222222222223), Named.NTSC_1953.ordinal());
        object2 = sNamedColorSpaces;
        n2 = Named.SMPTE_C.ordinal();
        object3 = ILLUMINANT_D65;
        object = new Rgb.TransferParameters(0.9099181073703367, 0.09008189262966333, 0.2222222222222222, 0.081, 2.2222222222222223);
        n = Named.SMPTE_C.ordinal();
        object2[n2] = (float)new Rgb("SMPTE-C RGB", new float[]{0.63f, 0.34f, 0.31f, 0.595f, 0.155f, 0.07f}, (float[])object3, (Rgb.TransferParameters)object, n);
        object = sNamedColorSpaces;
        n2 = Named.ADOBE_RGB.ordinal();
        object3 = ILLUMINANT_D65;
        n = Named.ADOBE_RGB.ordinal();
        object[n2] = new Rgb("Adobe RGB (1998)", new float[]{0.64f, 0.33f, 0.21f, 0.71f, 0.15f, 0.06f}, (float[])object3, 2.2, 0.0f, 1.0f, n);
        object = sNamedColorSpaces;
        n2 = Named.PRO_PHOTO_RGB.ordinal();
        object3 = ILLUMINANT_D50;
        object2 = new Rgb.TransferParameters(1.0, 0.0, 0.0625, 0.031248, 1.8);
        n = Named.PRO_PHOTO_RGB.ordinal();
        object[n2] = new Rgb("ROMM RGB ISO 22028-2:2013", new float[]{0.7347f, 0.2653f, 0.1596f, 0.8404f, 0.0366f, 1.0E-4f}, (float[])object3, (Rgb.TransferParameters)object2, n);
        object = sNamedColorSpaces;
        n = Named.ACES.ordinal();
        object3 = ILLUMINANT_D60;
        n2 = Named.ACES.ordinal();
        object[n] = new Rgb("SMPTE ST 2065-1:2012 ACES", new float[]{0.7347f, 0.2653f, 0.0f, 1.0f, 1.0E-4f, -0.077f}, (float[])object3, 1.0, -65504.0f, 65504.0f, n2);
        object = sNamedColorSpaces;
        n2 = Named.ACESCG.ordinal();
        object3 = ILLUMINANT_D60;
        n = Named.ACESCG.ordinal();
        object[n2] = new Rgb("Academy S-2014-004 ACEScg", new float[]{0.713f, 0.293f, 0.165f, 0.83f, 0.128f, 0.044f}, (float[])object3, 1.0, -65504.0f, 65504.0f, n);
        ColorSpace.sNamedColorSpaces[Named.CIE_XYZ.ordinal()] = new Xyz("Generic XYZ", Named.CIE_XYZ.ordinal());
        ColorSpace.sNamedColorSpaces[Named.CIE_LAB.ordinal()] = new Lab("Generic L*a*b*", Named.CIE_LAB.ordinal());
    }

    private ColorSpace(String string2, Model model, int n) {
        if (string2 != null && string2.length() >= 1) {
            if (model != null) {
                if (n >= -1 && n <= 63) {
                    this.mName = string2;
                    this.mModel = model;
                    this.mId = n;
                    return;
                }
                throw new IllegalArgumentException("The id must be between -1 and 63");
            }
            throw new IllegalArgumentException("A color space must have a model");
        }
        throw new IllegalArgumentException("The name of a color space cannot be null and must contain at least 1 character");
    }

    private static double absRcpResponse(double d, double d2, double d3, double d4, double d5, double d6) {
        double d7 = d < 0.0 ? -d : d;
        return Math.copySign(ColorSpace.rcpResponse(d7, d2, d3, d4, d5, d6), d);
    }

    private static double absResponse(double d, double d2, double d3, double d4, double d5, double d6) {
        double d7 = d < 0.0 ? -d : d;
        return Math.copySign(ColorSpace.response(d7, d2, d3, d4, d5, d6), d);
    }

    public static ColorSpace adapt(ColorSpace colorSpace, float[] arrf) {
        return ColorSpace.adapt(colorSpace, arrf, Adaptation.BRADFORD);
    }

    public static ColorSpace adapt(ColorSpace arrf, float[] arrf2, Adaptation adaptation) {
        if (arrf.getModel() == Model.RGB) {
            Rgb rgb = (Rgb)arrf;
            if (ColorSpace.compare(rgb.mWhitePoint, arrf2)) {
                return arrf;
            }
            arrf = arrf2.length == 3 ? Arrays.copyOf(arrf2, 3) : ColorSpace.xyYToXyz(arrf2);
            return new Rgb(rgb, ColorSpace.mul3x3(ColorSpace.chromaticAdaptation(adaptation.mTransform, ColorSpace.xyYToXyz(rgb.getWhitePoint()), arrf), rgb.mTransform), arrf2);
        }
        return arrf;
    }

    private static float[] adaptToIlluminantD50(float[] arrf, float[] arrf2) {
        float[] arrf3 = ILLUMINANT_D50;
        if (ColorSpace.compare(arrf, arrf3)) {
            return arrf2;
        }
        arrf3 = ColorSpace.xyYToXyz(arrf3);
        return ColorSpace.mul3x3(ColorSpace.chromaticAdaptation(Adaptation.BRADFORD.mTransform, ColorSpace.xyYToXyz(arrf), arrf3), arrf2);
    }

    public static float[] cctToIlluminantdXyz(int n) {
        if (n >= 1) {
            float f = 1.0f / (float)n;
            float f2 = f * f;
            f = (float)n <= 7000.0f ? 99.11f * f + 0.244063f + 2967800.0f * f2 - 4.6070001E9f * f2 * f : 247.48f * f + 0.23704f + 1901800.0f * f2 - 2.0064E9f * f2 * f;
            return ColorSpace.xyYToXyz(new float[]{f, -3.0f * f * f + 2.87f * f - 0.275f});
        }
        throw new IllegalArgumentException("Temperature must be greater than 0");
    }

    public static float[] cctToXyz(int n) {
        if (n >= 1) {
            float f = 1000.0f / (float)n;
            float f2 = f * f;
            f = (float)n <= 4000.0f ? 0.8776956f * f + 0.17991f - 0.2343589f * f2 - 0.2661239f * f2 * f : 0.2226347f * f + 0.24039f + 2.1070378f * f2 - 3.025847f * f2 * f;
            f2 = f * f;
            f2 = (float)n <= 2222.0f ? 2.1855583f * f - 0.20219684f - 1.3481102f * f2 - 1.1063814f * f2 * f : ((float)n <= 4000.0f ? 2.09137f * f - 0.16748866f - 1.3741859f * f2 - 0.9549476f * f2 * f : 3.7511299f * f - 0.37001482f - 5.873387f * f2 + 3.081758f * f2 * f);
            return ColorSpace.xyYToXyz(new float[]{f, f2});
        }
        throw new IllegalArgumentException("Temperature must be greater than 0");
    }

    public static float[] chromaticAdaptation(Adaptation adaptation, float[] arrf, float[] arrf2) {
        if (ColorSpace.compare(arrf = arrf.length == 3 ? Arrays.copyOf(arrf, 3) : ColorSpace.xyYToXyz(arrf), arrf2 = arrf2.length == 3 ? Arrays.copyOf(arrf2, 3) : ColorSpace.xyYToXyz(arrf2))) {
            return new float[]{1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f};
        }
        return ColorSpace.chromaticAdaptation(adaptation.mTransform, arrf, arrf2);
    }

    private static float[] chromaticAdaptation(float[] arrf, float[] arrf2, float[] arrf3) {
        arrf2 = ColorSpace.mul3x3Float3(arrf, arrf2);
        arrf3 = ColorSpace.mul3x3Float3(arrf, arrf3);
        float f = arrf3[0] / arrf2[0];
        float f2 = arrf3[1] / arrf2[1];
        float f3 = arrf3[2] / arrf2[2];
        return ColorSpace.mul3x3(ColorSpace.inverse3x3(arrf), ColorSpace.mul3x3Diag(new float[]{f, f2, f3}, arrf));
    }

    private static boolean compare(Rgb.TransferParameters transferParameters, Rgb.TransferParameters transferParameters2) {
        boolean bl = true;
        if (transferParameters == null && transferParameters2 == null) {
            return true;
        }
        if (!(transferParameters != null && transferParameters2 != null && Math.abs(transferParameters.a - transferParameters2.a) < 0.001 && Math.abs(transferParameters.b - transferParameters2.b) < 0.001 && Math.abs(transferParameters.c - transferParameters2.c) < 0.001 && Math.abs(transferParameters.d - transferParameters2.d) < 0.002 && Math.abs(transferParameters.e - transferParameters2.e) < 0.001 && Math.abs(transferParameters.f - transferParameters2.f) < 0.001 && Math.abs(transferParameters.g - transferParameters2.g) < 0.001)) {
            bl = false;
        }
        return bl;
    }

    private static boolean compare(float[] arrf, float[] arrf2) {
        if (arrf == arrf2) {
            return true;
        }
        for (int i = 0; i < arrf.length; ++i) {
            if (Float.compare(arrf[i], arrf2[i]) == 0 || !(Math.abs(arrf[i] - arrf2[i]) > 0.001f)) continue;
            return false;
        }
        return true;
    }

    public static Connector connect(ColorSpace colorSpace) {
        return ColorSpace.connect(colorSpace, RenderIntent.PERCEPTUAL);
    }

    public static Connector connect(ColorSpace colorSpace, RenderIntent renderIntent) {
        if (colorSpace.isSrgb()) {
            return Connector.identity(colorSpace);
        }
        if (colorSpace.getModel() == Model.RGB) {
            return new Connector.Rgb((Rgb)colorSpace, (Rgb)ColorSpace.get(Named.SRGB), renderIntent);
        }
        return new Connector(colorSpace, ColorSpace.get(Named.SRGB), renderIntent);
    }

    public static Connector connect(ColorSpace colorSpace, ColorSpace colorSpace2) {
        return ColorSpace.connect(colorSpace, colorSpace2, RenderIntent.PERCEPTUAL);
    }

    public static Connector connect(ColorSpace colorSpace, ColorSpace colorSpace2, RenderIntent renderIntent) {
        if (colorSpace.equals(colorSpace2)) {
            return Connector.identity(colorSpace);
        }
        if (colorSpace.getModel() == Model.RGB && colorSpace2.getModel() == Model.RGB) {
            return new Connector.Rgb((Rgb)colorSpace, (Rgb)colorSpace2, renderIntent);
        }
        return new Connector(colorSpace, colorSpace2, renderIntent);
    }

    public static Renderer createRenderer() {
        return new Renderer();
    }

    static ColorSpace get(int n) {
        if (n >= 0 && n < Named.values().length) {
            return sNamedColorSpaces[n];
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid ID, must be in the range [0..");
        stringBuilder.append(Named.values().length);
        stringBuilder.append(")");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static ColorSpace get(Named named) {
        return sNamedColorSpaces[named.ordinal()];
    }

    private static float[] inverse3x3(float[] arrf) {
        float f = arrf[0];
        float f2 = arrf[3];
        float f3 = arrf[6];
        float f4 = arrf[1];
        float f5 = arrf[4];
        float f6 = arrf[7];
        float f7 = arrf[2];
        float f8 = arrf[5];
        float f9 = arrf[8];
        float f10 = f5 * f9 - f6 * f8;
        float f11 = f6 * f7 - f4 * f9;
        float f12 = f4 * f8 - f5 * f7;
        float f13 = f * f10 + f2 * f11 + f3 * f12;
        arrf = new float[arrf.length];
        arrf[0] = f10 / f13;
        arrf[1] = f11 / f13;
        arrf[2] = f12 / f13;
        arrf[3] = (f3 * f8 - f2 * f9) / f13;
        arrf[4] = (f * f9 - f3 * f7) / f13;
        arrf[5] = (f2 * f7 - f * f8) / f13;
        arrf[6] = (f2 * f6 - f3 * f5) / f13;
        arrf[7] = (f3 * f4 - f * f6) / f13;
        arrf[8] = (f * f5 - f2 * f4) / f13;
        return arrf;
    }

    static /* synthetic */ double lambda$static$0(double d) {
        return ColorSpace.absRcpResponse(d, 0.9478672985781991, 0.05213270142180095, 0.07739938080495357, 0.04045, 2.4);
    }

    static /* synthetic */ double lambda$static$1(double d) {
        return ColorSpace.absResponse(d, 0.9478672985781991, 0.05213270142180095, 0.07739938080495357, 0.04045, 2.4);
    }

    public static ColorSpace match(float[] arrf, Rgb.TransferParameters transferParameters) {
        for (ColorSpace colorSpace : sNamedColorSpaces) {
            Rgb rgb;
            if (colorSpace.getModel() != Model.RGB || !ColorSpace.compare(arrf, (rgb = (Rgb)ColorSpace.adapt(colorSpace, ILLUMINANT_D50_XYZ)).mTransform) || !ColorSpace.compare(transferParameters, rgb.mTransferParameters)) continue;
            return colorSpace;
        }
        return null;
    }

    public static float[] mul3x3(float[] arrf, float[] arrf2) {
        return new float[]{arrf[0] * arrf2[0] + arrf[3] * arrf2[1] + arrf[6] * arrf2[2], arrf[1] * arrf2[0] + arrf[4] * arrf2[1] + arrf[7] * arrf2[2], arrf[2] * arrf2[0] + arrf[5] * arrf2[1] + arrf[8] * arrf2[2], arrf[0] * arrf2[3] + arrf[3] * arrf2[4] + arrf[6] * arrf2[5], arrf[1] * arrf2[3] + arrf[4] * arrf2[4] + arrf[7] * arrf2[5], arrf[2] * arrf2[3] + arrf[5] * arrf2[4] + arrf[8] * arrf2[5], arrf[0] * arrf2[6] + arrf[3] * arrf2[7] + arrf[6] * arrf2[8], arrf[1] * arrf2[6] + arrf[4] * arrf2[7] + arrf[7] * arrf2[8], arrf[2] * arrf2[6] + arrf[5] * arrf2[7] + arrf[8] * arrf2[8]};
    }

    private static float[] mul3x3Diag(float[] arrf, float[] arrf2) {
        return new float[]{arrf[0] * arrf2[0], arrf[1] * arrf2[1], arrf[2] * arrf2[2], arrf[0] * arrf2[3], arrf[1] * arrf2[4], arrf[2] * arrf2[5], arrf[0] * arrf2[6], arrf[1] * arrf2[7], arrf[2] * arrf2[8]};
    }

    private static float[] mul3x3Float3(float[] arrf, float[] arrf2) {
        float f = arrf2[0];
        float f2 = arrf2[1];
        float f3 = arrf2[2];
        arrf2[0] = arrf[0] * f + arrf[3] * f2 + arrf[6] * f3;
        arrf2[1] = arrf[1] * f + arrf[4] * f2 + arrf[7] * f3;
        arrf2[2] = arrf[2] * f + arrf[5] * f2 + arrf[8] * f3;
        return arrf2;
    }

    private static double rcpResponse(double d, double d2, double d3, double d4, double d5, double d6) {
        d = d >= d5 * d4 ? (Math.pow(d, 1.0 / d6) - d3) / d2 : (d /= d4);
        return d;
    }

    private static double rcpResponse(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8) {
        d = d >= d5 * d4 ? (Math.pow(d - d6, 1.0 / d8) - d3) / d2 : (d - d7) / d4;
        return d;
    }

    private static double response(double d, double d2, double d3, double d4, double d5, double d6) {
        d = d >= d5 ? Math.pow(d2 * d + d3, d6) : d4 * d;
        return d;
    }

    private static double response(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8) {
        d = d >= d5 ? Math.pow(d2 * d + d3, d8) + d6 : d4 * d + d7;
        return d;
    }

    private static void xyYToUv(float[] arrf) {
        for (int i = 0; i < arrf.length; i += 2) {
            float f = arrf[i];
            float f2 = arrf[i + 1];
            float f3 = -2.0f * f + 12.0f * f2 + 3.0f;
            f = 4.0f * f / f3;
            f3 = 9.0f * f2 / f3;
            arrf[i] = f;
            arrf[i + 1] = f3;
        }
    }

    private static float[] xyYToXyz(float[] arrf) {
        return new float[]{arrf[0] / arrf[1], 1.0f, (1.0f - arrf[0] - arrf[1]) / arrf[1]};
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (ColorSpace)object;
            if (this.mId != ((ColorSpace)object).mId) {
                return false;
            }
            if (!this.mName.equals(((ColorSpace)object).mName)) {
                return false;
            }
            if (this.mModel != ((ColorSpace)object).mModel) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public float[] fromXyz(float f, float f2, float f3) {
        float[] arrf = new float[this.mModel.getComponentCount()];
        arrf[0] = f;
        arrf[1] = f2;
        arrf[2] = f3;
        return this.fromXyz(arrf);
    }

    public abstract float[] fromXyz(float[] var1);

    public int getComponentCount() {
        return this.mModel.getComponentCount();
    }

    public int getId() {
        return this.mId;
    }

    public abstract float getMaxValue(int var1);

    public abstract float getMinValue(int var1);

    public Model getModel() {
        return this.mModel;
    }

    public String getName() {
        return this.mName;
    }

    long getNativeInstance() {
        throw new IllegalArgumentException("colorSpace must be an RGB color space");
    }

    public int hashCode() {
        return (this.mName.hashCode() * 31 + this.mModel.hashCode()) * 31 + this.mId;
    }

    public boolean isSrgb() {
        return false;
    }

    public abstract boolean isWideGamut();

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.mName);
        stringBuilder.append(" (id=");
        stringBuilder.append(this.mId);
        stringBuilder.append(", model=");
        stringBuilder.append((Object)((Object)this.mModel));
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public float[] toXyz(float f, float f2, float f3) {
        return this.toXyz(new float[]{f, f2, f3});
    }

    public abstract float[] toXyz(float[] var1);

    public static enum Adaptation {
        BRADFORD(new float[]{0.8951f, -0.7502f, 0.0389f, 0.2664f, 1.7135f, -0.0685f, -0.1614f, 0.0367f, 1.0296f}),
        VON_KRIES(new float[]{0.40024f, -0.2263f, 0.0f, 0.7076f, 1.16532f, 0.0f, -0.08081f, 0.0457f, 0.91822f}),
        CIECAT02(new float[]{0.7328f, -0.7036f, 0.003f, 0.4296f, 1.6975f, 0.0136f, -0.1624f, 0.0061f, 0.9834f});
        
        final float[] mTransform;

        private Adaptation(float[] arrf) {
            this.mTransform = arrf;
        }
    }

    public static class Connector {
        private final ColorSpace mDestination;
        private final RenderIntent mIntent;
        private final ColorSpace mSource;
        private final float[] mTransform;
        private final ColorSpace mTransformDestination;
        private final ColorSpace mTransformSource;

        Connector(ColorSpace colorSpace, ColorSpace colorSpace2, RenderIntent renderIntent) {
            ColorSpace colorSpace3 = colorSpace.getModel() == Model.RGB ? ColorSpace.adapt(colorSpace, ILLUMINANT_D50_XYZ) : colorSpace;
            ColorSpace colorSpace4 = colorSpace2.getModel() == Model.RGB ? ColorSpace.adapt(colorSpace2, ILLUMINANT_D50_XYZ) : colorSpace2;
            this(colorSpace, colorSpace2, colorSpace3, colorSpace4, renderIntent, Connector.computeTransform(colorSpace, colorSpace2, renderIntent));
        }

        private Connector(ColorSpace colorSpace, ColorSpace colorSpace2, ColorSpace colorSpace3, ColorSpace colorSpace4, RenderIntent renderIntent, float[] arrf) {
            this.mSource = colorSpace;
            this.mDestination = colorSpace2;
            this.mTransformSource = colorSpace3;
            this.mTransformDestination = colorSpace4;
            this.mIntent = renderIntent;
            this.mTransform = arrf;
        }

        private static float[] computeTransform(ColorSpace arrf, ColorSpace arrf2, RenderIntent renderIntent) {
            if (renderIntent != RenderIntent.ABSOLUTE) {
                return null;
            }
            boolean bl = arrf.getModel() == Model.RGB;
            boolean bl2 = arrf2.getModel() == Model.RGB;
            if (bl && bl2) {
                return null;
            }
            if (!bl && !bl2) {
                return null;
            }
            if (!bl) {
                arrf = arrf2;
            }
            arrf2 = (android.graphics.ColorSpace$Rgb)arrf;
            arrf = bl ? ColorSpace.xyYToXyz(((android.graphics.ColorSpace$Rgb)arrf2).mWhitePoint) : ILLUMINANT_D50_XYZ;
            arrf2 = bl2 ? ColorSpace.xyYToXyz(((android.graphics.ColorSpace$Rgb)arrf2).mWhitePoint) : ILLUMINANT_D50_XYZ;
            return new float[]{arrf[0] / arrf2[0], arrf[1] / arrf2[1], arrf[2] / arrf2[2]};
        }

        static Connector identity(ColorSpace colorSpace) {
            return new Connector(colorSpace, colorSpace, RenderIntent.RELATIVE){

                @Override
                public float[] transform(float[] arrf) {
                    return arrf;
                }
            };
        }

        public ColorSpace getDestination() {
            return this.mDestination;
        }

        public RenderIntent getRenderIntent() {
            return this.mIntent;
        }

        public ColorSpace getSource() {
            return this.mSource;
        }

        public float[] transform(float f, float f2, float f3) {
            return this.transform(new float[]{f, f2, f3});
        }

        public float[] transform(float[] arrf) {
            arrf = this.mTransformSource.toXyz(arrf);
            float[] arrf2 = this.mTransform;
            if (arrf2 != null) {
                arrf[0] = arrf[0] * arrf2[0];
                arrf[1] = arrf[1] * arrf2[1];
                arrf[2] = arrf[2] * arrf2[2];
            }
            return this.mTransformDestination.fromXyz(arrf);
        }

        private static class Rgb
        extends Connector {
            private final android.graphics.ColorSpace$Rgb mDestination;
            private final android.graphics.ColorSpace$Rgb mSource;
            private final float[] mTransform;

            Rgb(android.graphics.ColorSpace$Rgb rgb, android.graphics.ColorSpace$Rgb rgb2, RenderIntent renderIntent) {
                super(rgb, rgb2, rgb, rgb2, renderIntent, null);
                this.mSource = rgb;
                this.mDestination = rgb2;
                this.mTransform = Rgb.computeTransform(rgb, rgb2, renderIntent);
            }

            private static float[] computeTransform(android.graphics.ColorSpace$Rgb arrf, android.graphics.ColorSpace$Rgb arrf2, RenderIntent renderIntent) {
                if (ColorSpace.compare(((android.graphics.ColorSpace$Rgb)arrf).mWhitePoint, ((android.graphics.ColorSpace$Rgb)arrf2).mWhitePoint)) {
                    return ColorSpace.mul3x3(((android.graphics.ColorSpace$Rgb)arrf2).mInverseTransform, ((android.graphics.ColorSpace$Rgb)arrf).mTransform);
                }
                float[] arrf3 = ((android.graphics.ColorSpace$Rgb)arrf).mTransform;
                float[] arrf4 = ((android.graphics.ColorSpace$Rgb)arrf2).mInverseTransform;
                float[] arrf5 = ColorSpace.xyYToXyz(((android.graphics.ColorSpace$Rgb)arrf).mWhitePoint);
                float[] arrf6 = ColorSpace.xyYToXyz(((android.graphics.ColorSpace$Rgb)arrf2).mWhitePoint);
                if (!ColorSpace.compare(((android.graphics.ColorSpace$Rgb)arrf).mWhitePoint, ILLUMINANT_D50)) {
                    arrf3 = ColorSpace.mul3x3(ColorSpace.chromaticAdaptation(Adaptation.BRADFORD.mTransform, arrf5, Arrays.copyOf(ILLUMINANT_D50_XYZ, 3)), ((android.graphics.ColorSpace$Rgb)arrf).mTransform);
                }
                arrf = arrf4;
                if (!ColorSpace.compare(((android.graphics.ColorSpace$Rgb)arrf2).mWhitePoint, ILLUMINANT_D50)) {
                    arrf = ColorSpace.inverse3x3(ColorSpace.mul3x3(ColorSpace.chromaticAdaptation(Adaptation.BRADFORD.mTransform, arrf6, Arrays.copyOf(ILLUMINANT_D50_XYZ, 3)), ((android.graphics.ColorSpace$Rgb)arrf2).mTransform));
                }
                arrf2 = arrf3;
                if (renderIntent == RenderIntent.ABSOLUTE) {
                    arrf2 = ColorSpace.mul3x3Diag(new float[]{arrf5[0] / arrf6[0], arrf5[1] / arrf6[1], arrf5[2] / arrf6[2]}, arrf3);
                }
                return ColorSpace.mul3x3(arrf, arrf2);
            }

            @Override
            public float[] transform(float[] arrf) {
                arrf[0] = (float)this.mSource.mClampedEotf.applyAsDouble(arrf[0]);
                arrf[1] = (float)this.mSource.mClampedEotf.applyAsDouble(arrf[1]);
                arrf[2] = (float)this.mSource.mClampedEotf.applyAsDouble(arrf[2]);
                ColorSpace.mul3x3Float3(this.mTransform, arrf);
                arrf[0] = (float)this.mDestination.mClampedOetf.applyAsDouble(arrf[0]);
                arrf[1] = (float)this.mDestination.mClampedOetf.applyAsDouble(arrf[1]);
                arrf[2] = (float)this.mDestination.mClampedOetf.applyAsDouble(arrf[2]);
                return arrf;
            }
        }

    }

    private static final class Lab
    extends ColorSpace {
        private static final float A = 0.008856452f;
        private static final float B = 7.787037f;
        private static final float C = 0.13793103f;
        private static final float D = 0.20689656f;

        private Lab(String string2, int n) {
            super(string2, Model.LAB, n);
        }

        private static float clamp(float f, float f2, float f3) {
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

        @Override
        public float[] fromXyz(float[] arrf) {
            float f = arrf[0] / ILLUMINANT_D50_XYZ[0];
            float f2 = arrf[1] / ILLUMINANT_D50_XYZ[1];
            float f3 = arrf[2] / ILLUMINANT_D50_XYZ[2];
            f = f > 0.008856452f ? (float)Math.pow(f, 0.3333333333333333) : f * 7.787037f + 0.13793103f;
            f2 = f2 > 0.008856452f ? (float)Math.pow(f2, 0.3333333333333333) : f2 * 7.787037f + 0.13793103f;
            f3 = f3 > 0.008856452f ? (float)Math.pow(f3, 0.3333333333333333) : 7.787037f * f3 + 0.13793103f;
            arrf[0] = Lab.clamp(116.0f * f2 - 16.0f, 0.0f, 100.0f);
            arrf[1] = Lab.clamp((f - f2) * 500.0f, -128.0f, 128.0f);
            arrf[2] = Lab.clamp((f2 - f3) * 200.0f, -128.0f, 128.0f);
            return arrf;
        }

        @Override
        public float getMaxValue(int n) {
            float f = n == 0 ? 100.0f : 128.0f;
            return f;
        }

        @Override
        public float getMinValue(int n) {
            float f = n == 0 ? 0.0f : -128.0f;
            return f;
        }

        @Override
        public boolean isWideGamut() {
            return true;
        }

        @Override
        public float[] toXyz(float[] arrf) {
            arrf[0] = Lab.clamp(arrf[0], 0.0f, 100.0f);
            arrf[1] = Lab.clamp(arrf[1], -128.0f, 128.0f);
            arrf[2] = Lab.clamp(arrf[2], -128.0f, 128.0f);
            float f = (arrf[0] + 16.0f) / 116.0f;
            float f2 = arrf[1] * 0.002f + f;
            float f3 = f - arrf[2] * 0.005f;
            f2 = f2 > 0.20689656f ? f2 * f2 * f2 : (f2 - 0.13793103f) * 0.12841855f;
            f = f > 0.20689656f ? f * f * f : (f - 0.13793103f) * 0.12841855f;
            f3 = f3 > 0.20689656f ? f3 * f3 * f3 : (f3 - 0.13793103f) * 0.12841855f;
            arrf[0] = ILLUMINANT_D50_XYZ[0] * f2;
            arrf[1] = ILLUMINANT_D50_XYZ[1] * f;
            arrf[2] = ILLUMINANT_D50_XYZ[2] * f3;
            return arrf;
        }
    }

    public static enum Model {
        RGB(3),
        XYZ(3),
        LAB(3),
        CMYK(4);
        
        private final int mComponentCount;

        private Model(int n2) {
            this.mComponentCount = n2;
        }

        public int getComponentCount() {
            return this.mComponentCount;
        }
    }

    public static enum Named {
        SRGB,
        LINEAR_SRGB,
        EXTENDED_SRGB,
        LINEAR_EXTENDED_SRGB,
        BT709,
        BT2020,
        DCI_P3,
        DISPLAY_P3,
        NTSC_1953,
        SMPTE_C,
        ADOBE_RGB,
        PRO_PHOTO_RGB,
        ACES,
        ACESCG,
        CIE_XYZ,
        CIE_LAB;
        
    }

    public static enum RenderIntent {
        PERCEPTUAL,
        RELATIVE,
        SATURATION,
        ABSOLUTE;
        
    }

    public static class Renderer {
        private static final int CHROMATICITY_RESOLUTION = 32;
        private static final int NATIVE_SIZE = 1440;
        private static final double ONE_THIRD = 0.3333333333333333;
        private static final float[] SPECTRUM_LOCUS_X = new float[]{0.175596f, 0.172787f, 0.170806f, 0.170085f, 0.160343f, 0.146958f, 0.139149f, 0.133536f, 0.126688f, 0.11583f, 0.109616f, 0.099146f, 0.09131f, 0.07813f, 0.068717f, 0.054675f, 0.040763f, 0.027497f, 0.01627f, 0.008169f, 0.004876f, 0.003983f, 0.003859f, 0.004646f, 0.007988f, 0.01387f, 0.022244f, 0.027273f, 0.03282f, 0.038851f, 0.045327f, 0.052175f, 0.059323f, 0.066713f, 0.074299f, 0.089937f, 0.114155f, 0.138695f, 0.154714f, 0.192865f, 0.229607f, 0.26576f, 0.301588f, 0.337346f, 0.373083f, 0.408717f, 0.444043f, 0.478755f, 0.512467f, 0.544767f, 0.575132f, 0.602914f, 0.627018f, 0.648215f, 0.665746f, 0.680061f, 0.691487f, 0.700589f, 0.707901f, 0.714015f, 0.719017f, 0.723016f, 0.734674f, 0.717203f, 0.699732f, 0.68226f, 0.664789f, 0.647318f, 0.629847f, 0.612376f, 0.594905f, 0.577433f, 0.559962f, 0.542491f, 0.52502f, 0.507549f, 0.490077f, 0.472606f, 0.455135f, 0.437664f, 0.420193f, 0.402721f, 0.38525f, 0.367779f, 0.350308f, 0.332837f, 0.315366f, 0.297894f, 0.280423f, 0.262952f, 0.245481f, 0.22801f, 0.210538f, 0.193067f, 0.175596f};
        private static final float[] SPECTRUM_LOCUS_Y = new float[]{0.005295f, 0.0048f, 0.005472f, 0.005976f, 0.014496f, 0.026643f, 0.035211f, 0.042704f, 0.053441f, 0.073601f, 0.086866f, 0.112037f, 0.132737f, 0.170464f, 0.200773f, 0.254155f, 0.317049f, 0.387997f, 0.463035f, 0.538504f, 0.587196f, 0.610526f, 0.654897f, 0.67597f, 0.715407f, 0.750246f, 0.779682f, 0.792153f, 0.802971f, 0.812059f, 0.81943f, 0.8252f, 0.82946f, 0.832306f, 0.833833f, 0.833316f, 0.826231f, 0.814796f, 0.805884f, 0.781648f, 0.754347f, 0.724342f, 0.692326f, 0.658867f, 0.62447f, 0.589626f, 0.554734f, 0.520222f, 0.486611f, 0.454454f, 0.424252f, 0.396516f, 0.37251f, 0.351413f, 0.334028f, 0.319765f, 0.308359f, 0.299317f, 0.292044f, 0.285945f, 0.280951f, 0.276964f, 0.265326f, 0.2572f, 0.249074f, 0.240948f, 0.232822f, 0.224696f, 0.21657f, 0.208444f, 0.200318f, 0.192192f, 0.184066f, 0.17594f, 0.167814f, 0.159688f, 0.151562f, 0.143436f, 0.135311f, 0.127185f, 0.119059f, 0.110933f, 0.102807f, 0.094681f, 0.086555f, 0.078429f, 0.070303f, 0.062177f, 0.054051f, 0.045925f, 0.037799f, 0.029673f, 0.021547f, 0.013421f, 0.005295f};
        private static final float UCS_SCALE = 1.5f;
        private boolean mClip = false;
        private final List<Pair<ColorSpace, Integer>> mColorSpaces = new ArrayList<Pair<ColorSpace, Integer>>(2);
        private final List<Point> mPoints = new ArrayList<Point>(0);
        private boolean mShowWhitePoint = true;
        private int mSize = 1024;
        private boolean mUcs = false;

        private Renderer() {
        }

        private static void computeChromaticityMesh(float[] arrf, int[] arrn) {
            float[] arrf2;
            ColorSpace colorSpace = ColorSpace.get(Named.SRGB);
            float[] arrf3 = new float[3];
            int n = 0;
            int n2 = 0;
            int n3 = 0;
            while (n3 < (arrf2 = SPECTRUM_LOCUS_X).length) {
                int n4 = n3 % (arrf2.length - 1) + 1;
                float f = (float)Math.atan2((double)SPECTRUM_LOCUS_Y[n3] - 0.3333333333333333, (double)arrf2[n3] - 0.3333333333333333);
                float f2 = (float)Math.atan2((double)SPECTRUM_LOCUS_Y[n4] - 0.3333333333333333, (double)SPECTRUM_LOCUS_X[n4] - 0.3333333333333333);
                float f3 = (float)Math.pow(Renderer.sqr((double)SPECTRUM_LOCUS_X[n3] - 0.3333333333333333) + Renderer.sqr((double)SPECTRUM_LOCUS_Y[n3] - 0.3333333333333333), 0.5);
                float f4 = (float)Math.pow(Renderer.sqr((double)SPECTRUM_LOCUS_X[n4] - 0.3333333333333333) + Renderer.sqr((double)SPECTRUM_LOCUS_Y[n4] - 0.3333333333333333), 0.5);
                int n5 = 1;
                int n6 = n3;
                n3 = n2;
                while (n5 <= 32) {
                    float f5 = (float)n5 / 32.0f;
                    float f6 = (float)(n5 - 1) / 32.0f;
                    double d = (double)f3 * Math.cos(f);
                    double d2 = (double)f3 * Math.sin(f);
                    double d3 = (double)f4 * Math.cos(f2);
                    double d4 = (double)f4 * Math.sin(f2);
                    float f7 = (float)((double)f5 * d + 0.3333333333333333);
                    float f8 = (float)((double)f5 * d2 + 0.3333333333333333);
                    float f9 = (float)((double)f6 * d + 0.3333333333333333);
                    float f10 = (float)((double)f6 * d2 + 0.3333333333333333);
                    float f11 = (float)((double)f6 * d3 + 0.3333333333333333);
                    float f12 = (float)((double)f6 * d4 + 0.3333333333333333);
                    f6 = (float)((double)f5 * d3 + 0.3333333333333333);
                    f5 = (float)((double)f5 * d4 + 0.3333333333333333);
                    arrn[n3] = Renderer.computeColor(arrf3, f7, f8, 1.0f - f7 - f8, colorSpace);
                    arrn[n3 + 1] = Renderer.computeColor(arrf3, f9, f10, 1.0f - f9 - f10, colorSpace);
                    arrn[n3 + 2] = Renderer.computeColor(arrf3, f11, f12, 1.0f - f11 - f12, colorSpace);
                    arrn[n3 + 3] = arrn[n3];
                    arrn[n3 + 4] = arrn[n3 + 2];
                    arrn[n3 + 5] = Renderer.computeColor(arrf3, f6, f5, 1.0f - f6 - f5, colorSpace);
                    n2 = n + 1;
                    arrf[n] = f7;
                    n = n2 + 1;
                    arrf[n2] = f8;
                    int n7 = n + 1;
                    arrf[n] = f9;
                    n2 = n7 + 1;
                    arrf[n7] = f10;
                    n = n2 + 1;
                    arrf[n2] = f11;
                    n7 = n + 1;
                    arrf[n] = f12;
                    n2 = n7 + 1;
                    arrf[n7] = f7;
                    n = n2 + 1;
                    arrf[n2] = f8;
                    n2 = n + 1;
                    arrf[n] = f11;
                    n7 = n2 + 1;
                    arrf[n2] = f12;
                    n = n7 + 1;
                    arrf[n7] = f6;
                    arrf[n] = f5;
                    ++n5;
                    n3 += 6;
                    ++n;
                }
                n4 = n6 + 1;
                n2 = n3;
                n3 = n4;
            }
        }

        private static int computeColor(float[] arrf, float f, float f2, float f3, ColorSpace colorSpace) {
            arrf[0] = f;
            arrf[1] = f2;
            arrf[2] = f3;
            colorSpace.fromXyz(arrf);
            return ((int)(arrf[0] * 255.0f) & 255) << 16 | -16777216 | ((int)(arrf[1] * 255.0f) & 255) << 8 | (int)(arrf[2] * 255.0f) & 255;
        }

        private void drawBox(Canvas canvas, int n, int n2, Paint paint, Path path) {
            float f;
            int n3;
            float f2;
            float f3;
            int n4;
            if (this.mUcs) {
                n4 = 7;
                f2 = 1.5f;
            } else {
                n4 = 10;
                f2 = 1.0f;
            }
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(2.0f);
            paint.setColor(-4144960);
            for (n3 = 1; n3 < n4 - 1; ++n3) {
                f = (float)n3 / 10.0f;
                f3 = (float)n * f * f2;
                f = (float)n2 - (float)n2 * f * f2;
                canvas.drawLine(0.0f, f, (float)n * 0.9f, f, paint);
                canvas.drawLine(f3, n2, f3, (float)n2 * 0.1f, paint);
            }
            paint.setStrokeWidth(4.0f);
            paint.setColor(-16777216);
            for (n3 = 1; n3 < n4 - 1; ++n3) {
                f = (float)n3 / 10.0f;
                f3 = (float)n * f * f2;
                f = (float)n2 - (float)n2 * f * f2;
                canvas.drawLine(0.0f, f, (float)n / 100.0f, f, paint);
                canvas.drawLine(f3, n2, f3, (float)n2 - (float)n2 / 100.0f, paint);
            }
            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(36.0f);
            paint.setTypeface(Typeface.create("sans-serif-light", 0));
            Rect rect = new Rect();
            for (n3 = 1; n3 < n4 - 1; ++n3) {
                CharSequence charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("0.");
                ((StringBuilder)charSequence).append(n3);
                charSequence = ((StringBuilder)charSequence).toString();
                paint.getTextBounds((String)charSequence, 0, ((String)charSequence).length(), rect);
                f3 = (float)n3 / 10.0f;
                f = n;
                float f4 = n2;
                float f5 = n2;
                canvas.drawText((String)charSequence, (float)n * -0.05f + 10.0f, (float)rect.height() / 2.0f + (f4 - f5 * f3 * f2), paint);
                canvas.drawText((String)charSequence, f * f3 * f2 - (float)rect.width() / 2.0f, rect.height() + n2 + 16, paint);
            }
            paint.setStyle(Paint.Style.STROKE);
            path.moveTo(0.0f, n2);
            path.lineTo((float)n * 0.9f, n2);
            path.lineTo((float)n * 0.9f, (float)n2 * 0.1f);
            path.lineTo(0.0f, (float)n2 * 0.1f);
            path.close();
            canvas.drawPath(path, paint);
        }

        private void drawGamuts(Canvas canvas, int n, int n2, Paint paint, Path path, float[] arrf, float[] arrf2) {
            float f = this.mUcs ? 1.5f : 1.0f;
            f = 4.0f / f;
            for (Pair<ColorSpace, Integer> pair : this.mColorSpaces) {
                ColorSpace colorSpace = (ColorSpace)pair.first;
                int n3 = (Integer)pair.second;
                if (colorSpace.getModel() != Model.RGB) continue;
                colorSpace = (Rgb)colorSpace;
                Renderer.getPrimaries((Rgb)colorSpace, arrf, this.mUcs);
                path.rewind();
                path.moveTo((float)n * arrf[0], (float)n2 - (float)n2 * arrf[1]);
                path.lineTo((float)n * arrf[2], (float)n2 - (float)n2 * arrf[3]);
                path.lineTo((float)n * arrf[4], (float)n2 - (float)n2 * arrf[5]);
                path.close();
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(n3);
                canvas.drawPath(path, paint);
                if (!this.mShowWhitePoint) continue;
                ((Rgb)colorSpace).getWhitePoint(arrf2);
                if (this.mUcs) {
                    ColorSpace.xyYToUv(arrf2);
                }
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(n3);
                canvas.drawCircle((float)n * arrf2[0], (float)n2 - (float)n2 * arrf2[1], f, paint);
            }
        }

        private void drawLocus(Canvas canvas, int n, int n2, Paint paint, Path path, float[] object) {
            float[] arrf = new float[SPECTRUM_LOCUS_X.length * 32 * 6 * 2];
            int[] arrn = new int[arrf.length];
            Renderer.computeChromaticityMesh(arrf, arrn);
            if (this.mUcs) {
                ColorSpace.xyYToUv(arrf);
            }
            for (int i = 0; i < arrf.length; i += 2) {
                arrf[i] = arrf[i] * (float)n;
                arrf[i + 1] = (float)n2 - arrf[i + 1] * (float)n2;
            }
            if (this.mClip && this.mColorSpaces.size() > 0) {
                Iterator<Pair<ColorSpace, Integer>> iterator = this.mColorSpaces.iterator();
                while (iterator.hasNext()) {
                    ColorSpace colorSpace = (ColorSpace)iterator.next().first;
                    if (colorSpace.getModel() != Model.RGB) continue;
                    Renderer.getPrimaries((Rgb)colorSpace, (float[])object, this.mUcs);
                    break;
                }
                path.rewind();
                path.moveTo((float)n * object[0], (float)n2 - (float)n2 * object[1]);
                path.lineTo((float)n * object[2], (float)n2 - (float)n2 * object[3]);
                path.lineTo((float)n * object[4], (float)n2 - (float)n2 * object[5]);
                path.close();
                object = new int[arrn.length];
                Arrays.fill((int[])object, -9671572);
                canvas.drawVertices(Canvas.VertexMode.TRIANGLES, arrf.length, arrf, 0, null, 0, (int[])object, 0, null, 0, 0, paint);
                canvas.save();
                canvas.clipPath(path);
                canvas.drawVertices(Canvas.VertexMode.TRIANGLES, arrf.length, arrf, 0, null, 0, arrn, 0, null, 0, 0, paint);
                canvas.restore();
            } else {
                canvas.drawVertices(Canvas.VertexMode.TRIANGLES, arrf.length, arrf, 0, null, 0, arrn, 0, null, 0, 0, paint);
            }
            object = path;
            n2 = 372;
            path.reset();
            ((Path)object).moveTo(arrf[372], arrf[372 + 1]);
            for (n = 2; n < SPECTRUM_LOCUS_X.length; ++n) {
                ((Path)object).lineTo(arrf[n2 += 384], arrf[n2 + 1]);
            }
            path.close();
            float f = this.mUcs ? 1.5f : 1.0f;
            paint.setStrokeWidth(4.0f / f);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(-16777216);
            canvas.drawPath((Path)object, paint);
        }

        private void drawPoints(Canvas canvas, int n, int n2, Paint paint) {
            paint.setStyle(Paint.Style.FILL);
            float f = this.mUcs ? 1.5f : 1.0f;
            f = 4.0f / f;
            float[] arrf = new float[3];
            float[] arrf2 = new float[2];
            for (Point point : this.mPoints) {
                arrf[0] = point.mRgb[0];
                arrf[1] = point.mRgb[1];
                arrf[2] = point.mRgb[2];
                point.mColorSpace.toXyz(arrf);
                paint.setColor(point.mColor);
                float f2 = arrf[0] + arrf[1] + arrf[2];
                arrf2[0] = arrf[0] / f2;
                arrf2[1] = arrf[1] / f2;
                if (this.mUcs) {
                    ColorSpace.xyYToUv(arrf2);
                }
                canvas.drawCircle((float)n * arrf2[0], (float)n2 - (float)n2 * arrf2[1], f, paint);
            }
        }

        private static void getPrimaries(Rgb rgb, float[] arrf, boolean bl) {
            if (!rgb.equals(ColorSpace.get(Named.EXTENDED_SRGB)) && !rgb.equals(ColorSpace.get(Named.LINEAR_EXTENDED_SRGB))) {
                rgb.getPrimaries(arrf);
            } else {
                arrf[0] = 1.41f;
                arrf[1] = 0.33f;
                arrf[2] = 0.27f;
                arrf[3] = 1.24f;
                arrf[4] = -0.23f;
                arrf[5] = -0.57f;
            }
            if (bl) {
                ColorSpace.xyYToUv(arrf);
            }
        }

        private void setTransform(Canvas canvas, int n, int n2, float[] arrf) {
            RectF rectF = new RectF();
            Iterator<Pair<ColorSpace, Integer>> iterator = this.mColorSpaces.iterator();
            while (iterator.hasNext()) {
                ColorSpace colorSpace = (ColorSpace)iterator.next().first;
                if (colorSpace.getModel() != Model.RGB) continue;
                Renderer.getPrimaries((Rgb)colorSpace, arrf, this.mUcs);
                rectF.left = Math.min(rectF.left, arrf[4]);
                rectF.top = Math.min(rectF.top, arrf[5]);
                rectF.right = Math.max(rectF.right, arrf[0]);
                rectF.bottom = Math.max(rectF.bottom, arrf[3]);
            }
            float f = this.mUcs ? 0.6f : 0.9f;
            rectF.left = Math.min(0.0f, rectF.left);
            rectF.top = Math.min(0.0f, rectF.top);
            rectF.right = Math.max(f, rectF.right);
            rectF.bottom = Math.max(f, rectF.bottom);
            float f2 = Math.min(f / rectF.width(), f / rectF.height());
            int n3 = this.mSize;
            canvas.scale((float)n3 / 1440.0f, (float)n3 / 1440.0f);
            canvas.scale(f2, f2);
            canvas.translate((rectF.width() - f) * (float)n / 2.0f, (rectF.height() - f) * (float)n2 / 2.0f);
            canvas.translate((float)n * 0.05f, (float)n2 * -0.05f);
        }

        private void setUcsTransform(Canvas canvas, int n) {
            if (this.mUcs) {
                canvas.translate(0.0f, (float)n - (float)n * 1.5f);
                canvas.scale(1.5f, 1.5f);
            }
        }

        private static double sqr(double d) {
            return d * d;
        }

        public Renderer add(ColorSpace colorSpace, float f, float f2, float f3, int n) {
            this.mPoints.add(new Point(colorSpace, new float[]{f, f2, f3}, n));
            return this;
        }

        public Renderer add(ColorSpace colorSpace, int n) {
            this.mColorSpaces.add(new Pair<ColorSpace, Integer>(colorSpace, n));
            return this;
        }

        public Renderer clip(boolean bl) {
            this.mClip = bl;
            return this;
        }

        public Bitmap render() {
            Paint paint = new Paint(1);
            int n = this.mSize;
            Bitmap bitmap = Bitmap.createBitmap(n, n, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            float[] arrf = new float[6];
            float[] arrf2 = new float[2];
            Path path = new Path();
            this.setTransform(canvas, 1440, 1440, arrf);
            this.drawBox(canvas, 1440, 1440, paint, path);
            this.setUcsTransform(canvas, 1440);
            this.drawLocus(canvas, 1440, 1440, paint, path, arrf);
            this.drawGamuts(canvas, 1440, 1440, paint, path, arrf, arrf2);
            this.drawPoints(canvas, 1440, 1440, paint);
            return bitmap;
        }

        public Renderer showWhitePoint(boolean bl) {
            this.mShowWhitePoint = bl;
            return this;
        }

        public Renderer size(int n) {
            this.mSize = Math.max(128, n);
            return this;
        }

        public Renderer uniformChromaticityScale(boolean bl) {
            this.mUcs = bl;
            return this;
        }

        private static class Point {
            final int mColor;
            final ColorSpace mColorSpace;
            final float[] mRgb;

            Point(ColorSpace colorSpace, float[] arrf, int n) {
                this.mColorSpace = colorSpace;
                this.mRgb = arrf;
                this.mColor = n;
            }
        }

    }

    public static class Rgb
    extends ColorSpace {
        private final DoubleUnaryOperator mClampedEotf;
        private final DoubleUnaryOperator mClampedOetf;
        private final DoubleUnaryOperator mEotf;
        private final float[] mInverseTransform;
        private final boolean mIsSrgb;
        private final boolean mIsWideGamut;
        private final float mMax;
        private final float mMin;
        private final long mNativePtr;
        private final DoubleUnaryOperator mOetf;
        private final float[] mPrimaries;
        private final TransferParameters mTransferParameters;
        private final float[] mTransform;
        private final float[] mWhitePoint;

        private Rgb(Rgb rgb, float[] arrf, float[] arrf2) {
            this(rgb.getName(), rgb.mPrimaries, arrf2, arrf, rgb.mOetf, rgb.mEotf, rgb.mMin, rgb.mMax, rgb.mTransferParameters, -1);
        }

        public Rgb(String string2, float[] arrf, double d) {
            this(string2, Rgb.computePrimaries(arrf), Rgb.computeWhitePoint(arrf), d, 0.0f, 1.0f, -1);
        }

        public Rgb(String string2, float[] arrf, TransferParameters transferParameters) {
            this(string2, Rgb.computePrimaries(arrf), Rgb.computeWhitePoint(arrf), transferParameters, -1);
        }

        public Rgb(String string2, float[] arrf, DoubleUnaryOperator doubleUnaryOperator, DoubleUnaryOperator doubleUnaryOperator2) {
            this(string2, Rgb.computePrimaries(arrf), Rgb.computeWhitePoint(arrf), null, doubleUnaryOperator, doubleUnaryOperator2, 0.0f, 1.0f, null, -1);
        }

        public Rgb(String string2, float[] arrf, float[] arrf2, double d) {
            this(string2, arrf, arrf2, d, 0.0f, 1.0f, -1);
        }

        private Rgb(String string2, float[] arrf, float[] arrf2, double d, float f, float f2, int n) {
            DoubleUnaryOperator doubleUnaryOperator = d == 1.0 ? DoubleUnaryOperator.identity() : new _$$Lambda$ColorSpace$Rgb$CqKld6797g7__JnuY0NeFz5q4_E(d);
            DoubleUnaryOperator doubleUnaryOperator2 = d == 1.0 ? DoubleUnaryOperator.identity() : new _$$Lambda$ColorSpace$Rgb$ZvS77aTfobOSa2o9MTqYMph4Rcg(d);
            this(string2, arrf, arrf2, null, doubleUnaryOperator, doubleUnaryOperator2, f, f2, new TransferParameters(1.0, 0.0, 0.0, 0.0, d), n);
        }

        public Rgb(String string2, float[] arrf, float[] arrf2, TransferParameters transferParameters) {
            this(string2, arrf, arrf2, transferParameters, -1);
        }

        private Rgb(String string2, float[] arrf, float[] arrf2, TransferParameters transferParameters, int n) {
            DoubleUnaryOperator doubleUnaryOperator = transferParameters.e == 0.0 && transferParameters.f == 0.0 ? new _$$Lambda$ColorSpace$Rgb$bWzafC8vMHNuVmRuTUPEFUMlfuY(transferParameters) : new _$$Lambda$ColorSpace$Rgb$V_0lmM2WEpxGBDV_1G1wvvidn7Y(transferParameters);
            DoubleUnaryOperator doubleUnaryOperator2 = transferParameters.e == 0.0 && transferParameters.f == 0.0 ? new _$$Lambda$ColorSpace$Rgb$b9VGKuNnse0bbguR9jbOM_wK2Ac(transferParameters) : new _$$Lambda$ColorSpace$Rgb$iMkODTKa3_8kPZUnZZerD2Lv_yo(transferParameters);
            this(string2, arrf, arrf2, null, doubleUnaryOperator, doubleUnaryOperator2, 0.0f, 1.0f, transferParameters, n);
        }

        public Rgb(String string2, float[] arrf, float[] arrf2, DoubleUnaryOperator doubleUnaryOperator, DoubleUnaryOperator doubleUnaryOperator2, float f, float f2) {
            this(string2, arrf, arrf2, null, doubleUnaryOperator, doubleUnaryOperator2, f, f2, null, -1);
        }

        /*
         * Enabled aggressive block sorting
         */
        private Rgb(String object, float[] arrf, float[] arrf2, float[] arrf3, DoubleUnaryOperator doubleUnaryOperator, DoubleUnaryOperator doubleUnaryOperator2, float f, float f2, TransferParameters transferParameters, int n) {
            super((String)object, Model.RGB, n);
            if (arrf == null) throw new IllegalArgumentException("The color space's primaries must be defined as an array of 6 floats in xyY or 9 floats in XYZ");
            if (arrf.length != 6) {
                if (arrf.length != 9) throw new IllegalArgumentException("The color space's primaries must be defined as an array of 6 floats in xyY or 9 floats in XYZ");
            }
            if (arrf2 == null) throw new IllegalArgumentException("The color space's white point must be defined as an array of 2 floats in xyY or 3 float in XYZ");
            if (arrf2.length != 2) {
                if (arrf2.length != 3) throw new IllegalArgumentException("The color space's white point must be defined as an array of 2 floats in xyY or 3 float in XYZ");
            }
            if (doubleUnaryOperator == null) throw new IllegalArgumentException("The transfer functions of a color space cannot be null");
            if (doubleUnaryOperator2 == null) throw new IllegalArgumentException("The transfer functions of a color space cannot be null");
            if (f >= f2) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Invalid range: min=");
                ((StringBuilder)object).append(f);
                ((StringBuilder)object).append(", max=");
                ((StringBuilder)object).append(f2);
                ((StringBuilder)object).append("; min must be strictly < max");
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            this.mWhitePoint = Rgb.xyWhitePoint(arrf2);
            this.mPrimaries = Rgb.xyPrimaries(arrf);
            if (arrf3 == null) {
                this.mTransform = Rgb.computeXYZMatrix(this.mPrimaries, this.mWhitePoint);
            } else {
                if (arrf3.length != 9) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Transform must have 9 entries! Has ");
                    ((StringBuilder)object).append(arrf3.length);
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                this.mTransform = arrf3;
            }
            this.mInverseTransform = ColorSpace.inverse3x3(this.mTransform);
            this.mOetf = doubleUnaryOperator;
            this.mEotf = doubleUnaryOperator2;
            this.mMin = f;
            this.mMax = f2;
            object = new _$$Lambda$ColorSpace$Rgb$8EkhO2jIf14tuA3BvrmYJMa7YXM(this);
            this.mClampedOetf = doubleUnaryOperator.andThen((DoubleUnaryOperator)object);
            this.mClampedEotf = object.andThen(doubleUnaryOperator2);
            this.mTransferParameters = transferParameters;
            this.mIsWideGamut = Rgb.isWideGamut(this.mPrimaries, f, f2);
            this.mIsSrgb = Rgb.isSrgb(this.mPrimaries, this.mWhitePoint, doubleUnaryOperator, doubleUnaryOperator2, f, f2, n);
            if (this.mTransferParameters == null) {
                this.mNativePtr = 0L;
                return;
            }
            arrf = this.mWhitePoint;
            if (arrf != null && (object = this.mTransform) != null) {
                object = ColorSpace.adaptToIlluminantD50(arrf, (float[])object);
                this.mNativePtr = Rgb.nativeCreate((float)this.mTransferParameters.a, (float)this.mTransferParameters.b, (float)this.mTransferParameters.c, (float)this.mTransferParameters.d, (float)this.mTransferParameters.e, (float)this.mTransferParameters.f, (float)this.mTransferParameters.g, (float[])object);
                NoImagePreloadHolder.sRegistry.registerNativeAllocation((Object)this, this.mNativePtr);
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("ColorSpace (");
            ((StringBuilder)object).append(this);
            ((StringBuilder)object).append(") cannot create native object! mWhitePoint: ");
            ((StringBuilder)object).append(this.mWhitePoint);
            ((StringBuilder)object).append(" mTransform: ");
            ((StringBuilder)object).append(this.mTransform);
            throw new IllegalStateException(((StringBuilder)object).toString());
        }

        static /* synthetic */ long access$1400() {
            return Rgb.nativeGetNativeFinalizer();
        }

        private static float area(float[] arrf) {
            float f;
            block0 : {
                float f2 = arrf[0];
                float f3 = arrf[1];
                float f4 = arrf[2];
                f = arrf[3];
                float f5 = arrf[4];
                float f6 = arrf[5];
                if (!((f = 0.5f * (f2 * f + f3 * f5 + f4 * f6 - f * f5 - f3 * f4 - f2 * f6)) < 0.0f)) break block0;
                f = -f;
            }
            return f;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private double clamp(double d) {
            float f = this.mMin;
            if (d < (double)f) {
                do {
                    return f;
                    break;
                } while (true);
            }
            f = this.mMax;
            if (!(d > (double)f)) return d;
            return f;
        }

        private static boolean compare(double d, DoubleUnaryOperator doubleUnaryOperator, DoubleUnaryOperator doubleUnaryOperator2) {
            boolean bl = Math.abs(doubleUnaryOperator.applyAsDouble(d) - doubleUnaryOperator2.applyAsDouble(d)) <= 0.001;
            return bl;
        }

        private static float[] computePrimaries(float[] arrf) {
            float[] arrf2 = ColorSpace.mul3x3Float3(arrf, new float[]{1.0f, 0.0f, 0.0f});
            float[] arrf3 = ColorSpace.mul3x3Float3(arrf, new float[]{0.0f, 1.0f, 0.0f});
            arrf = ColorSpace.mul3x3Float3(arrf, new float[]{0.0f, 0.0f, 1.0f});
            float f = arrf2[0] + arrf2[1] + arrf2[2];
            float f2 = arrf3[0] + arrf3[1] + arrf3[2];
            float f3 = arrf[0] + arrf[1] + arrf[2];
            return new float[]{arrf2[0] / f, arrf2[1] / f, arrf3[0] / f2, arrf3[1] / f2, arrf[0] / f3, arrf[1] / f3};
        }

        private static float[] computeWhitePoint(float[] arrf) {
            arrf = ColorSpace.mul3x3Float3(arrf, new float[]{1.0f, 1.0f, 1.0f});
            float f = arrf[0] + arrf[1] + arrf[2];
            return new float[]{arrf[0] / f, arrf[1] / f};
        }

        private static float[] computeXYZMatrix(float[] arrf, float[] arrf2) {
            float f = arrf[0];
            float f2 = arrf[1];
            float f3 = arrf[2];
            float f4 = arrf[3];
            float f5 = arrf[4];
            float f6 = arrf[5];
            float f7 = arrf2[0];
            float f8 = arrf2[1];
            float f9 = (1.0f - f) / f2;
            float f10 = (1.0f - f3) / f4;
            float f11 = (1.0f - f5) / f6;
            float f12 = (1.0f - f7) / f8;
            float f13 = f / f2;
            float f14 = f3 / f4;
            float f15 = f5 / f6;
            f12 = ((f12 - f9) * (f14 - f13) - ((f7 /= f8) - f13) * (f10 - f9)) / ((f11 - f9) * (f14 - f13) - (f15 - f13) * (f10 - f9));
            f14 = (f7 - f13 - (f15 - f13) * f12) / (f14 - f13);
            f9 = 1.0f - f14 - f12;
            f13 = f9 / f2;
            f15 = f14 / f4;
            f11 = f12 / f6;
            return new float[]{f13 * f, f9, (1.0f - f - f2) * f13, f15 * f3, f14, (1.0f - f3 - f4) * f15, f11 * f5, f12, (1.0f - f5 - f6) * f11};
        }

        private static boolean contains(float[] arrf, float[] arrf2) {
            float[] arrf3 = new float[]{arrf[0] - arrf2[0], arrf[1] - arrf2[1], arrf[2] - arrf2[2], arrf[3] - arrf2[3], arrf[4] - arrf2[4], arrf[5] - arrf2[5]};
            if (!(Rgb.cross(arrf3[0], arrf3[1], arrf2[0] - arrf2[4], arrf2[1] - arrf2[5]) < 0.0f) && !(Rgb.cross(arrf2[0] - arrf2[2], arrf2[1] - arrf2[3], arrf3[0], arrf3[1]) < 0.0f)) {
                if (!(Rgb.cross(arrf3[2], arrf3[3], arrf2[2] - arrf2[0], arrf2[3] - arrf2[1]) < 0.0f) && !(Rgb.cross(arrf2[2] - arrf2[4], arrf2[3] - arrf2[5], arrf3[2], arrf3[3]) < 0.0f)) {
                    return !(Rgb.cross(arrf3[4], arrf3[5], arrf2[4] - arrf2[2], arrf2[5] - arrf2[3]) < 0.0f) && !(Rgb.cross(arrf2[4] - arrf2[0], arrf2[5] - arrf2[1], arrf3[4], arrf3[5]) < 0.0f);
                    {
                    }
                }
                return false;
            }
            return false;
        }

        private static float cross(float f, float f2, float f3, float f4) {
            return f * f4 - f2 * f3;
        }

        private static boolean isSrgb(float[] object, float[] arrf, DoubleUnaryOperator doubleUnaryOperator, DoubleUnaryOperator doubleUnaryOperator2, float f, float f2, int n) {
            if (n == 0) {
                return true;
            }
            if (!ColorSpace.compare(object, SRGB_PRIMARIES)) {
                return false;
            }
            if (!ColorSpace.compare(arrf, Rgb.ILLUMINANT_D65)) {
                return false;
            }
            if (f != 0.0f) {
                return false;
            }
            if (f2 != 1.0f) {
                return false;
            }
            object = (Rgb)Rgb.get(Named.SRGB);
            for (double d = 0.0; d <= 1.0; d += 0.00392156862745098) {
                if (!Rgb.compare(d, doubleUnaryOperator, object.mOetf)) {
                    return false;
                }
                if (Rgb.compare(d, doubleUnaryOperator2, object.mEotf)) continue;
                return false;
            }
            return true;
        }

        private static boolean isWideGamut(float[] arrf, float f, float f2) {
            boolean bl = Rgb.area(arrf) / Rgb.area(NTSC_1953_PRIMARIES) > 0.9f && Rgb.contains(arrf, SRGB_PRIMARIES) || f < 0.0f && f2 > 1.0f;
            return bl;
        }

        public static /* synthetic */ double lambda$8EkhO2jIf14tuA3BvrmYJMa7YXM(Rgb rgb, double d) {
            return rgb.clamp(d);
        }

        static /* synthetic */ double lambda$new$0(TransferParameters transferParameters, double d) {
            return ColorSpace.rcpResponse(d, transferParameters.a, transferParameters.b, transferParameters.c, transferParameters.d, transferParameters.g);
        }

        static /* synthetic */ double lambda$new$1(TransferParameters transferParameters, double d) {
            return ColorSpace.rcpResponse(d, transferParameters.a, transferParameters.b, transferParameters.c, transferParameters.d, transferParameters.e, transferParameters.f, transferParameters.g);
        }

        static /* synthetic */ double lambda$new$2(TransferParameters transferParameters, double d) {
            return ColorSpace.response(d, transferParameters.a, transferParameters.b, transferParameters.c, transferParameters.d, transferParameters.g);
        }

        static /* synthetic */ double lambda$new$3(TransferParameters transferParameters, double d) {
            return ColorSpace.response(d, transferParameters.a, transferParameters.b, transferParameters.c, transferParameters.d, transferParameters.e, transferParameters.f, transferParameters.g);
        }

        static /* synthetic */ double lambda$new$4(double d, double d2) {
            block0 : {
                double d3 = 0.0;
                if (!(d2 < 0.0)) break block0;
                d2 = d3;
            }
            return Math.pow(d2, 1.0 / d);
        }

        static /* synthetic */ double lambda$new$5(double d, double d2) {
            block0 : {
                double d3 = 0.0;
                if (!(d2 < 0.0)) break block0;
                d2 = d3;
            }
            return Math.pow(d2, d);
        }

        private static native long nativeCreate(float var0, float var1, float var2, float var3, float var4, float var5, float var6, float[] var7);

        private static native long nativeGetNativeFinalizer();

        private static float[] xyPrimaries(float[] arrf) {
            float[] arrf2 = new float[6];
            if (arrf.length == 9) {
                float f = arrf[0] + arrf[1] + arrf[2];
                arrf2[0] = arrf[0] / f;
                arrf2[1] = arrf[1] / f;
                f = arrf[3] + arrf[4] + arrf[5];
                arrf2[2] = arrf[3] / f;
                arrf2[3] = arrf[4] / f;
                f = arrf[6] + arrf[7] + arrf[8];
                arrf2[4] = arrf[6] / f;
                arrf2[5] = arrf[7] / f;
            } else {
                System.arraycopy(arrf, 0, arrf2, 0, 6);
            }
            return arrf2;
        }

        private static float[] xyWhitePoint(float[] arrf) {
            float[] arrf2 = new float[2];
            if (arrf.length == 3) {
                float f = arrf[0] + arrf[1] + arrf[2];
                arrf2[0] = arrf[0] / f;
                arrf2[1] = arrf[1] / f;
            } else {
                System.arraycopy(arrf, 0, arrf2, 0, 2);
            }
            return arrf2;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object != null && this.getClass() == object.getClass()) {
                if (!super.equals(object)) {
                    return false;
                }
                Rgb rgb = (Rgb)object;
                if (Float.compare(rgb.mMin, this.mMin) != 0) {
                    return false;
                }
                if (Float.compare(rgb.mMax, this.mMax) != 0) {
                    return false;
                }
                if (!Arrays.equals(this.mWhitePoint, rgb.mWhitePoint)) {
                    return false;
                }
                if (!Arrays.equals(this.mPrimaries, rgb.mPrimaries)) {
                    return false;
                }
                object = this.mTransferParameters;
                if (object != null) {
                    return ((TransferParameters)object).equals(rgb.mTransferParameters);
                }
                if (rgb.mTransferParameters == null) {
                    return true;
                }
                if (!this.mOetf.equals(rgb.mOetf)) {
                    return false;
                }
                return this.mEotf.equals(rgb.mEotf);
            }
            return false;
        }

        public float[] fromLinear(float f, float f2, float f3) {
            return this.fromLinear(new float[]{f, f2, f3});
        }

        public float[] fromLinear(float[] arrf) {
            arrf[0] = (float)this.mClampedOetf.applyAsDouble(arrf[0]);
            arrf[1] = (float)this.mClampedOetf.applyAsDouble(arrf[1]);
            arrf[2] = (float)this.mClampedOetf.applyAsDouble(arrf[2]);
            return arrf;
        }

        @Override
        public float[] fromXyz(float[] arrf) {
            ColorSpace.mul3x3Float3(this.mInverseTransform, arrf);
            arrf[0] = (float)this.mClampedOetf.applyAsDouble(arrf[0]);
            arrf[1] = (float)this.mClampedOetf.applyAsDouble(arrf[1]);
            arrf[2] = (float)this.mClampedOetf.applyAsDouble(arrf[2]);
            return arrf;
        }

        public DoubleUnaryOperator getEotf() {
            return this.mClampedEotf;
        }

        public float[] getInverseTransform() {
            float[] arrf = this.mInverseTransform;
            return Arrays.copyOf(arrf, arrf.length);
        }

        public float[] getInverseTransform(float[] arrf) {
            float[] arrf2 = this.mInverseTransform;
            System.arraycopy(arrf2, 0, arrf, 0, arrf2.length);
            return arrf;
        }

        @Override
        public float getMaxValue(int n) {
            return this.mMax;
        }

        @Override
        public float getMinValue(int n) {
            return this.mMin;
        }

        @Override
        long getNativeInstance() {
            long l = this.mNativePtr;
            if (l != 0L) {
                return l;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ColorSpace must use an ICC parametric transfer function! used ");
            stringBuilder.append(this);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public DoubleUnaryOperator getOetf() {
            return this.mClampedOetf;
        }

        public float[] getPrimaries() {
            float[] arrf = this.mPrimaries;
            return Arrays.copyOf(arrf, arrf.length);
        }

        public float[] getPrimaries(float[] arrf) {
            float[] arrf2 = this.mPrimaries;
            System.arraycopy(arrf2, 0, arrf, 0, arrf2.length);
            return arrf;
        }

        public TransferParameters getTransferParameters() {
            return this.mTransferParameters;
        }

        public float[] getTransform() {
            float[] arrf = this.mTransform;
            return Arrays.copyOf(arrf, arrf.length);
        }

        public float[] getTransform(float[] arrf) {
            float[] arrf2 = this.mTransform;
            System.arraycopy(arrf2, 0, arrf, 0, arrf2.length);
            return arrf;
        }

        public float[] getWhitePoint() {
            float[] arrf = this.mWhitePoint;
            return Arrays.copyOf(arrf, arrf.length);
        }

        public float[] getWhitePoint(float[] arrf) {
            float[] arrf2 = this.mWhitePoint;
            arrf[0] = arrf2[0];
            arrf[1] = arrf2[1];
            return arrf;
        }

        @Override
        public int hashCode() {
            int n = super.hashCode();
            int n2 = Arrays.hashCode(this.mWhitePoint);
            int n3 = Arrays.hashCode(this.mPrimaries);
            float f = this.mMin;
            int n4 = 0;
            int n5 = f != 0.0f ? Float.floatToIntBits(f) : 0;
            f = this.mMax;
            int n6 = f != 0.0f ? Float.floatToIntBits(f) : 0;
            TransferParameters transferParameters = this.mTransferParameters;
            if (transferParameters != null) {
                n4 = transferParameters.hashCode();
            }
            n5 = n6 = ((((n * 31 + n2) * 31 + n3) * 31 + n5) * 31 + n6) * 31 + n4;
            if (this.mTransferParameters == null) {
                n5 = (n6 * 31 + this.mOetf.hashCode()) * 31 + this.mEotf.hashCode();
            }
            return n5;
        }

        @Override
        public boolean isSrgb() {
            return this.mIsSrgb;
        }

        @Override
        public boolean isWideGamut() {
            return this.mIsWideGamut;
        }

        public float[] toLinear(float f, float f2, float f3) {
            return this.toLinear(new float[]{f, f2, f3});
        }

        public float[] toLinear(float[] arrf) {
            arrf[0] = (float)this.mClampedEotf.applyAsDouble(arrf[0]);
            arrf[1] = (float)this.mClampedEotf.applyAsDouble(arrf[1]);
            arrf[2] = (float)this.mClampedEotf.applyAsDouble(arrf[2]);
            return arrf;
        }

        @Override
        public float[] toXyz(float[] arrf) {
            arrf[0] = (float)this.mClampedEotf.applyAsDouble(arrf[0]);
            arrf[1] = (float)this.mClampedEotf.applyAsDouble(arrf[1]);
            arrf[2] = (float)this.mClampedEotf.applyAsDouble(arrf[2]);
            return ColorSpace.mul3x3Float3(this.mTransform, arrf);
        }

        private static class NoImagePreloadHolder {
            public static final NativeAllocationRegistry sRegistry = new NativeAllocationRegistry(Rgb.class.getClassLoader(), Rgb.access$1400(), 0L);

            private NoImagePreloadHolder() {
            }
        }

        public static class TransferParameters {
            public final double a;
            public final double b;
            public final double c;
            public final double d;
            public final double e;
            public final double f;
            public final double g;

            public TransferParameters(double d, double d2, double d3, double d4, double d5) {
                this(d, d2, d3, d4, 0.0, 0.0, d5);
            }

            public TransferParameters(double d, double d2, double d3, double d4, double d5, double d6, double d7) {
                if (!(Double.isNaN(d) || Double.isNaN(d2) || Double.isNaN(d3) || Double.isNaN(d4) || Double.isNaN(d5) || Double.isNaN(d6) || Double.isNaN(d7))) {
                    if (d4 >= 0.0 && d4 <= (double)(Math.ulp(1.0f) + 1.0f)) {
                        if (d4 == 0.0 && (d == 0.0 || d7 == 0.0)) {
                            throw new IllegalArgumentException("Parameter a or g is zero, the transfer function is constant");
                        }
                        if (d4 >= 1.0 && d3 == 0.0) {
                            throw new IllegalArgumentException("Parameter c is zero, the transfer function is constant");
                        }
                        if (d != 0.0 && d7 != 0.0 || d3 != 0.0) {
                            if (!(d3 < 0.0)) {
                                if (!(d < 0.0) && !(d7 < 0.0)) {
                                    this.a = d;
                                    this.b = d2;
                                    this.c = d3;
                                    this.d = d4;
                                    this.e = d5;
                                    this.f = d6;
                                    this.g = d7;
                                    return;
                                }
                                throw new IllegalArgumentException("The transfer function must be positive or increasing");
                            }
                            throw new IllegalArgumentException("The transfer function must be increasing");
                        }
                        throw new IllegalArgumentException("Parameter a or g is zero, and c is zero, the transfer function is constant");
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Parameter d must be in the range [0..1], was ");
                    stringBuilder.append(d4);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                throw new IllegalArgumentException("Parameters cannot be NaN");
            }

            public boolean equals(Object object) {
                boolean bl = true;
                if (this == object) {
                    return true;
                }
                if (object != null && this.getClass() == object.getClass()) {
                    object = (TransferParameters)object;
                    if (Double.compare(((TransferParameters)object).a, this.a) != 0) {
                        return false;
                    }
                    if (Double.compare(((TransferParameters)object).b, this.b) != 0) {
                        return false;
                    }
                    if (Double.compare(((TransferParameters)object).c, this.c) != 0) {
                        return false;
                    }
                    if (Double.compare(((TransferParameters)object).d, this.d) != 0) {
                        return false;
                    }
                    if (Double.compare(((TransferParameters)object).e, this.e) != 0) {
                        return false;
                    }
                    if (Double.compare(((TransferParameters)object).f, this.f) != 0) {
                        return false;
                    }
                    if (Double.compare(((TransferParameters)object).g, this.g) != 0) {
                        bl = false;
                    }
                    return bl;
                }
                return false;
            }

            public int hashCode() {
                long l = Double.doubleToLongBits(this.a);
                int n = (int)(l >>> 32 ^ l);
                l = Double.doubleToLongBits(this.b);
                int n2 = (int)(l >>> 32 ^ l);
                l = Double.doubleToLongBits(this.c);
                int n3 = (int)(l >>> 32 ^ l);
                l = Double.doubleToLongBits(this.d);
                int n4 = (int)(l >>> 32 ^ l);
                l = Double.doubleToLongBits(this.e);
                int n5 = (int)(l >>> 32 ^ l);
                l = Double.doubleToLongBits(this.f);
                int n6 = (int)(l >>> 32 ^ l);
                l = Double.doubleToLongBits(this.g);
                return (((((n * 31 + n2) * 31 + n3) * 31 + n4) * 31 + n5) * 31 + n6) * 31 + (int)(l >>> 32 ^ l);
            }
        }

    }

    private static final class Xyz
    extends ColorSpace {
        private Xyz(String string2, int n) {
            super(string2, Model.XYZ, n);
        }

        private static float clamp(float f) {
            block1 : {
                block0 : {
                    float f2 = -2.0f;
                    if (!(f < -2.0f)) break block0;
                    f = f2;
                    break block1;
                }
                if (!(f > 2.0f)) break block1;
                f = 2.0f;
            }
            return f;
        }

        @Override
        public float[] fromXyz(float[] arrf) {
            arrf[0] = Xyz.clamp(arrf[0]);
            arrf[1] = Xyz.clamp(arrf[1]);
            arrf[2] = Xyz.clamp(arrf[2]);
            return arrf;
        }

        @Override
        public float getMaxValue(int n) {
            return 2.0f;
        }

        @Override
        public float getMinValue(int n) {
            return -2.0f;
        }

        @Override
        public boolean isWideGamut() {
            return true;
        }

        @Override
        public float[] toXyz(float[] arrf) {
            arrf[0] = Xyz.clamp(arrf[0]);
            arrf[1] = Xyz.clamp(arrf[1]);
            arrf[2] = Xyz.clamp(arrf[2]);
            return arrf;
        }
    }

}

