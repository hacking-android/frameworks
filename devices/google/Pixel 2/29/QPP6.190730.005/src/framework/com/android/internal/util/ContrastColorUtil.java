/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.VectorDrawable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.util.Pair;
import com.android.internal.util.ImageUtils;
import java.util.Arrays;
import java.util.WeakHashMap;

public class ContrastColorUtil {
    private static final boolean DEBUG = false;
    private static final String TAG = "ContrastColorUtil";
    private static ContrastColorUtil sInstance;
    private static final Object sLock;
    private final WeakHashMap<Bitmap, Pair<Boolean, Integer>> mGrayscaleBitmapCache = new WeakHashMap();
    private final int mGrayscaleIconMaxSize;
    private final ImageUtils mImageUtils = new ImageUtils();

    static {
        sLock = new Object();
    }

    private ContrastColorUtil(Context context) {
        this.mGrayscaleIconMaxSize = context.getResources().getDimensionPixelSize(17104901);
    }

    public static double calculateContrast(int n, int n2) {
        return ColorUtilsFromCompat.calculateContrast(n, n2);
    }

    public static double calculateLuminance(int n) {
        return ColorUtilsFromCompat.calculateLuminance(n);
    }

    public static int changeColorLightness(int n, int n2) {
        double[] arrd = ColorUtilsFromCompat.getTempDouble3Array();
        ColorUtilsFromCompat.colorToLAB(n, arrd);
        arrd[0] = Math.max(Math.min(100.0, arrd[0] + (double)n2), 0.0);
        return ColorUtilsFromCompat.LABToColor(arrd[0], arrd[1], arrd[2]);
    }

    public static CharSequence clearColorSpans(CharSequence object) {
        if (object instanceof Spanned) {
            Spanned spanned = (Spanned)object;
            int n = spanned.length();
            Object[] arrobject = spanned.getSpans(0, n, Object.class);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(spanned.toString());
            n = arrobject.length;
            for (int i = 0; i < n; ++i) {
                Object object2;
                Object object3;
                object = object2 = (object3 = arrobject[i]);
                if (object2 instanceof CharacterStyle) {
                    object = ((CharacterStyle)object3).getUnderlying();
                }
                if (object instanceof TextAppearanceSpan) {
                    object2 = (TextAppearanceSpan)object;
                    if (((TextAppearanceSpan)object2).getTextColor() != null) {
                        object = new TextAppearanceSpan(((TextAppearanceSpan)object2).getFamily(), ((TextAppearanceSpan)object2).getTextStyle(), ((TextAppearanceSpan)object2).getTextSize(), null, ((TextAppearanceSpan)object2).getLinkTextColor());
                    }
                } else {
                    if (object instanceof ForegroundColorSpan || object instanceof BackgroundColorSpan) continue;
                    object = object3;
                }
                spannableStringBuilder.setSpan(object, spanned.getSpanStart(object3), spanned.getSpanEnd(object3), spanned.getSpanFlags(object3));
            }
            return spannableStringBuilder;
        }
        return object;
    }

    public static int compositeColors(int n, int n2) {
        return ColorUtilsFromCompat.compositeColors(n, n2);
    }

    private static String contrastChange(int n, int n2, int n3) {
        return String.format("from %.2f:1 to %.2f:1", ColorUtilsFromCompat.calculateContrast(n, n3), ColorUtilsFromCompat.calculateContrast(n2, n3));
    }

    public static int ensureContrast(int n, int n2, boolean bl, double d) {
        n = bl ? ContrastColorUtil.findContrastColorAgainstDark(n, n2, true, d) : ContrastColorUtil.findContrastColor(n, n2, true, d);
        return n;
    }

    public static int ensureLargeTextContrast(int n, int n2, boolean bl) {
        n = bl ? ContrastColorUtil.findContrastColorAgainstDark(n, n2, true, 3.0) : ContrastColorUtil.findContrastColor(n, n2, true, 3.0);
        return n;
    }

    public static int ensureTextBackgroundColor(int n, int n2, int n3) {
        return ContrastColorUtil.findContrastColor(ContrastColorUtil.findContrastColor(n, n3, false, 3.0), n2, false, 4.5);
    }

    public static int ensureTextContrast(int n, int n2, boolean bl) {
        return ContrastColorUtil.ensureContrast(n, n2, bl, 4.5);
    }

    public static int ensureTextContrastOnBlack(int n) {
        return ContrastColorUtil.findContrastColorAgainstDark(n, -16777216, true, 12.0);
    }

    public static int findAlphaToMeetContrast(int n, int n2, double d) {
        if (ColorUtilsFromCompat.calculateContrast(n, n2) >= d) {
            return n;
        }
        int n3 = Color.alpha(n);
        int n4 = Color.red(n);
        int n5 = Color.green(n);
        int n6 = Color.blue(n);
        int n7 = 255;
        for (n = 0; n < 15 && n7 - n3 > 0; ++n) {
            int n8 = (n3 + n7) / 2;
            if (ColorUtilsFromCompat.calculateContrast(Color.argb(n8, n4, n5, n6), n2) > d) {
                n7 = n8;
                continue;
            }
            n3 = n8;
        }
        return Color.argb(n7, n4, n5, n6);
    }

    public static int findContrastColor(int n, int n2, boolean bl, double d) {
        int n3 = bl ? n : n2;
        if (!bl) {
            n2 = n;
        }
        if (ColorUtilsFromCompat.calculateContrast(n3, n2) >= d) {
            return n;
        }
        double[] arrd = new double[3];
        n = bl ? n3 : n2;
        ColorUtilsFromCompat.colorToLAB(n, arrd);
        double d2 = 0.0;
        double d3 = arrd[0];
        double d4 = arrd[1];
        double d5 = arrd[2];
        for (n = 0; n < 15 && d3 - d2 > 1.0E-5; ++n) {
            double d6 = (d2 + d3) / 2.0;
            if (bl) {
                n3 = ColorUtilsFromCompat.LABToColor(d6, d4, d5);
            } else {
                n2 = ColorUtilsFromCompat.LABToColor(d6, d4, d5);
            }
            if (ColorUtilsFromCompat.calculateContrast(n3, n2) > d) {
                d2 = d6;
                continue;
            }
            d3 = d6;
        }
        return ColorUtilsFromCompat.LABToColor(d2, d4, d5);
    }

    public static int findContrastColorAgainstDark(int n, int n2, boolean bl, double d) {
        block6 : {
            int n3 = bl ? n : n2;
            if (!bl) {
                n2 = n;
            }
            if (ColorUtilsFromCompat.calculateContrast(n3, n2) >= d) {
                return n;
            }
            float[] arrf = new float[3];
            n = bl ? n3 : n2;
            ColorUtilsFromCompat.colorToHSL(n, arrf);
            float f = arrf[2];
            float f2 = 1.0f;
            for (n = 0; n < 15 && (double)(f2 - f) > 1.0E-5; ++n) {
                float f3;
                arrf[2] = f3 = (f + f2) / 2.0f;
                if (bl) {
                    n3 = ColorUtilsFromCompat.HSLToColor(arrf);
                } else {
                    n2 = ColorUtilsFromCompat.HSLToColor(arrf);
                }
                if (ColorUtilsFromCompat.calculateContrast(n3, n2) > d) {
                    f2 = f3;
                    continue;
                }
                f = f3;
            }
            if (!bl) break block6;
            n2 = n3;
        }
        return n2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static ContrastColorUtil getInstance(Context object) {
        Object object2 = sLock;
        synchronized (object2) {
            ContrastColorUtil contrastColorUtil;
            if (sInstance != null) return sInstance;
            sInstance = contrastColorUtil = new ContrastColorUtil((Context)object);
            return sInstance;
        }
    }

    public static int getMutedColor(int n, float f) {
        return ContrastColorUtil.compositeColors(ColorUtilsFromCompat.setAlphaComponent(-1, (int)(255.0f * f)), n);
    }

    public static int getShiftedColor(int n, int n2) {
        double[] arrd = ColorUtilsFromCompat.getTempDouble3Array();
        ColorUtilsFromCompat.colorToLAB(n, arrd);
        arrd[0] = arrd[0] >= 4.0 ? Math.max(0.0, arrd[0] - (double)n2) : Math.min(100.0, arrd[0] + (double)n2);
        return ColorUtilsFromCompat.LABToColor(arrd[0], arrd[1], arrd[2]);
    }

    public static boolean isColorLight(int n) {
        boolean bl = ContrastColorUtil.calculateLuminance(n) > 0.5;
        return bl;
    }

    private int processColor(int n) {
        return Color.argb(Color.alpha(n), 255 - Color.red(n), 255 - Color.green(n), 255 - Color.blue(n));
    }

    private TextAppearanceSpan processTextAppearanceSpan(TextAppearanceSpan textAppearanceSpan) {
        ColorStateList colorStateList = textAppearanceSpan.getTextColor();
        if (colorStateList != null) {
            int[] arrn = colorStateList.getColors();
            boolean bl = false;
            for (int i = 0; i < arrn.length; ++i) {
                int[] arrn2 = arrn;
                boolean bl2 = bl;
                if (ImageUtils.isGrayscale(arrn[i])) {
                    arrn2 = arrn;
                    if (!bl) {
                        arrn2 = Arrays.copyOf(arrn, arrn.length);
                    }
                    arrn2[i] = this.processColor(arrn2[i]);
                    bl2 = true;
                }
                arrn = arrn2;
                bl = bl2;
            }
            if (bl) {
                return new TextAppearanceSpan(textAppearanceSpan.getFamily(), textAppearanceSpan.getTextStyle(), textAppearanceSpan.getTextSize(), new ColorStateList(colorStateList.getStates(), arrn), textAppearanceSpan.getLinkTextColor());
            }
        }
        return textAppearanceSpan;
    }

    public static int resolveColor(Context context, int n, boolean bl) {
        if (n == 0) {
            n = bl ? 17170884 : 17170885;
            return context.getColor(n);
        }
        return n;
    }

    public static int resolveContrastColor(Context context, int n, int n2) {
        return ContrastColorUtil.resolveContrastColor(context, n, n2, false);
    }

    public static int resolveContrastColor(Context context, int n, int n2, boolean bl) {
        n = ContrastColorUtil.ensureTextContrast(ContrastColorUtil.resolveColor(context, n, bl), n2, bl);
        return n;
    }

    public static int resolveDefaultColor(Context context, int n, boolean bl) {
        if (ContrastColorUtil.shouldUseDark(n, bl)) {
            return context.getColor(17170885);
        }
        return context.getColor(17170884);
    }

    public static int resolvePrimaryColor(Context context, int n, boolean bl) {
        if (ContrastColorUtil.shouldUseDark(n, bl)) {
            return context.getColor(17170888);
        }
        return context.getColor(17170887);
    }

    public static int resolveSecondaryColor(Context context, int n, boolean bl) {
        if (ContrastColorUtil.shouldUseDark(n, bl)) {
            return context.getColor(17170891);
        }
        return context.getColor(17170890);
    }

    public static boolean satisfiesTextContrast(int n, int n2) {
        boolean bl = ContrastColorUtil.calculateContrast(n2, n) >= 4.5;
        return bl;
    }

    private static boolean shouldUseDark(int n, boolean bl) {
        if (n == 0) {
            return bl ^ true;
        }
        bl = ColorUtilsFromCompat.calculateLuminance(n) > 0.5;
        return bl;
    }

    public CharSequence invertCharSequenceColors(CharSequence object) {
        if (object instanceof Spanned) {
            Spanned spanned = (Spanned)object;
            int n = spanned.length();
            Object[] arrobject = spanned.getSpans(0, n, Object.class);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(spanned.toString());
            n = arrobject.length;
            for (int i = 0; i < n; ++i) {
                Object object2;
                Object object3 = object2 = (object = arrobject[i]);
                if (object2 instanceof CharacterStyle) {
                    object3 = ((CharacterStyle)object).getUnderlying();
                }
                object3 = object3 instanceof TextAppearanceSpan ? ((object2 = this.processTextAppearanceSpan((TextAppearanceSpan)object)) != object3 ? object2 : object) : (object3 instanceof ForegroundColorSpan ? new ForegroundColorSpan(this.processColor(((ForegroundColorSpan)object3).getForegroundColor())) : object);
                spannableStringBuilder.setSpan(object3, spanned.getSpanStart(object), spanned.getSpanEnd(object), spanned.getSpanFlags(object));
            }
            return spannableStringBuilder;
        }
        return object;
    }

    public boolean isGrayscaleIcon(Context context, int n) {
        if (n != 0) {
            try {
                boolean bl = this.isGrayscaleIcon(context.getDrawable(n));
                return bl;
            }
            catch (Resources.NotFoundException notFoundException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Drawable not found: ");
                stringBuilder.append(n);
                Log.e(TAG, stringBuilder.toString());
                return false;
            }
        }
        return false;
    }

    public boolean isGrayscaleIcon(Context context, Icon icon) {
        if (icon == null) {
            return false;
        }
        int n = icon.getType();
        if (n != 1) {
            if (n != 2) {
                return false;
            }
            return this.isGrayscaleIcon(context, icon.getResId());
        }
        return this.isGrayscaleIcon(icon.getBitmap());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isGrayscaleIcon(Bitmap bitmap) {
        int n;
        boolean bl;
        if (bitmap.getWidth() > this.mGrayscaleIconMaxSize) return false;
        if (bitmap.getHeight() > this.mGrayscaleIconMaxSize) {
            return false;
        }
        Object object = sLock;
        synchronized (object) {
            Pair<Boolean, Integer> pair = this.mGrayscaleBitmapCache.get(bitmap);
            if (pair != null && ((Integer)pair.second).intValue() == bitmap.getGenerationId()) {
                return (Boolean)pair.first;
            }
        }
        object = this.mImageUtils;
        synchronized (object) {
            bl = this.mImageUtils.isGrayscale(bitmap);
            n = bitmap.getGenerationId();
        }
        object = sLock;
        synchronized (object) {
            this.mGrayscaleBitmapCache.put(bitmap, Pair.create(bl, n));
            return bl;
        }
    }

    public boolean isGrayscaleIcon(Drawable drawable2) {
        boolean bl = false;
        boolean bl2 = false;
        if (drawable2 == null) {
            return false;
        }
        if (drawable2 instanceof BitmapDrawable) {
            drawable2 = (BitmapDrawable)drawable2;
            boolean bl3 = bl2;
            if (((BitmapDrawable)drawable2).getBitmap() != null) {
                bl3 = bl2;
                if (this.isGrayscaleIcon(((BitmapDrawable)drawable2).getBitmap())) {
                    bl3 = true;
                }
            }
            return bl3;
        }
        if (drawable2 instanceof AnimationDrawable) {
            drawable2 = (AnimationDrawable)drawable2;
            boolean bl4 = bl;
            if (((AnimationDrawable)drawable2).getNumberOfFrames() > 0) {
                bl4 = bl;
                if (this.isGrayscaleIcon(((AnimationDrawable)drawable2).getFrame(0))) {
                    bl4 = true;
                }
            }
            return bl4;
        }
        return drawable2 instanceof VectorDrawable;
    }

    private static class ColorUtilsFromCompat {
        private static final int MIN_ALPHA_SEARCH_MAX_ITERATIONS = 10;
        private static final int MIN_ALPHA_SEARCH_PRECISION = 1;
        private static final ThreadLocal<double[]> TEMP_ARRAY = new ThreadLocal();
        private static final double XYZ_EPSILON = 0.008856;
        private static final double XYZ_KAPPA = 903.3;
        private static final double XYZ_WHITE_REFERENCE_X = 95.047;
        private static final double XYZ_WHITE_REFERENCE_Y = 100.0;
        private static final double XYZ_WHITE_REFERENCE_Z = 108.883;

        private ColorUtilsFromCompat() {
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
            return Color.rgb(ColorUtilsFromCompat.constrain(n2, 0, 255), ColorUtilsFromCompat.constrain(n3, 0, 255), ColorUtilsFromCompat.constrain(n4, 0, 255));
        }

        public static int LABToColor(double d, double d2, double d3) {
            double[] arrd = ColorUtilsFromCompat.getTempDouble3Array();
            ColorUtilsFromCompat.LABToXYZ(d, d2, d3, arrd);
            return ColorUtilsFromCompat.XYZToColor(arrd[0], arrd[1], arrd[2]);
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
                f = 0.0f;
                f6 = 0.0f;
            } else {
                f = f4 == f ? (f2 - f3) / f6 % 6.0f : (f4 == f2 ? (f3 - f) / f6 + 2.0f : (f - f2) / f6 + 4.0f);
                f4 = f6 / (1.0f - Math.abs(2.0f * f7 - 1.0f));
                f6 = f;
                f = f4;
            }
            f6 = f4 = 60.0f * f6 % 360.0f;
            if (f4 < 0.0f) {
                f6 = f4 + 360.0f;
            }
            arrf[0] = ColorUtilsFromCompat.constrain(f6, 0.0f, 360.0f);
            arrf[1] = ColorUtilsFromCompat.constrain(f, 0.0f, 1.0f);
            arrf[2] = ColorUtilsFromCompat.constrain(f7, 0.0f, 1.0f);
        }

        public static void RGBToLAB(int n, int n2, int n3, double[] arrd) {
            ColorUtilsFromCompat.RGBToXYZ(n, n2, n3, arrd);
            ColorUtilsFromCompat.XYZToLAB(arrd[0], arrd[1], arrd[2], arrd);
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
            return Color.rgb(ColorUtilsFromCompat.constrain((int)Math.round(d * 255.0), 0, 255), ColorUtilsFromCompat.constrain((int)Math.round(d2 * 255.0), 0, 255), ColorUtilsFromCompat.constrain((int)Math.round(255.0 * d3), 0, 255));
        }

        public static void XYZToLAB(double d, double d2, double d3, double[] arrd) {
            if (arrd.length == 3) {
                d = ColorUtilsFromCompat.pivotXyzComponent(d / 95.047);
                d2 = ColorUtilsFromCompat.pivotXyzComponent(d2 / 100.0);
                d3 = ColorUtilsFromCompat.pivotXyzComponent(d3 / 108.883);
                arrd[0] = Math.max(0.0, 116.0 * d2 - 16.0);
                arrd[1] = (d - d2) * 500.0;
                arrd[2] = (d2 - d3) * 200.0;
                return;
            }
            throw new IllegalArgumentException("outLab must have a length of 3.");
        }

        public static double calculateContrast(int n, int n2) {
            if (Color.alpha(n2) != 255) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("background can not be translucent: #");
                stringBuilder.append(Integer.toHexString(n2));
                Log.wtf(ContrastColorUtil.TAG, stringBuilder.toString());
            }
            int n3 = n;
            if (Color.alpha(n) < 255) {
                n3 = ColorUtilsFromCompat.compositeColors(n, n2);
            }
            double d = ColorUtilsFromCompat.calculateLuminance(n3) + 0.05;
            double d2 = ColorUtilsFromCompat.calculateLuminance(n2) + 0.05;
            return Math.max(d, d2) / Math.min(d, d2);
        }

        public static double calculateLuminance(int n) {
            double[] arrd = ColorUtilsFromCompat.getTempDouble3Array();
            ColorUtilsFromCompat.colorToXYZ(n, arrd);
            return arrd[1] / 100.0;
        }

        public static void colorToHSL(int n, float[] arrf) {
            ColorUtilsFromCompat.RGBToHSL(Color.red(n), Color.green(n), Color.blue(n), arrf);
        }

        public static void colorToLAB(int n, double[] arrd) {
            ColorUtilsFromCompat.RGBToLAB(Color.red(n), Color.green(n), Color.blue(n), arrd);
        }

        public static void colorToXYZ(int n, double[] arrd) {
            ColorUtilsFromCompat.RGBToXYZ(Color.red(n), Color.green(n), Color.blue(n), arrd);
        }

        private static int compositeAlpha(int n, int n2) {
            return 255 - (255 - n2) * (255 - n) / 255;
        }

        public static int compositeColors(int n, int n2) {
            int n3 = Color.alpha(n2);
            int n4 = Color.alpha(n);
            int n5 = ColorUtilsFromCompat.compositeAlpha(n4, n3);
            return Color.argb(n5, ColorUtilsFromCompat.compositeComponent(Color.red(n), n4, Color.red(n2), n3, n5), ColorUtilsFromCompat.compositeComponent(Color.green(n), n4, Color.green(n2), n3, n5), ColorUtilsFromCompat.compositeComponent(Color.blue(n), n4, Color.blue(n2), n3, n5));
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

        public static double[] getTempDouble3Array() {
            double[] arrd;
            double[] arrd2 = arrd = TEMP_ARRAY.get();
            if (arrd == null) {
                arrd2 = new double[3];
                TEMP_ARRAY.set(arrd2);
            }
            return arrd2;
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
    }

}

