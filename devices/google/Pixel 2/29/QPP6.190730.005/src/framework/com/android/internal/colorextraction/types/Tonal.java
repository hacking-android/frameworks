/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package com.android.internal.colorextraction.types;

import android.app.WallpaperColors;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.util.Log;
import android.util.MathUtils;
import android.util.Range;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.colorextraction.ColorExtractor;
import com.android.internal.colorextraction.types.ExtractionType;
import com.android.internal.graphics.ColorUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class Tonal
implements ExtractionType {
    private static final boolean DEBUG = true;
    private static final float FIT_WEIGHT_H = 1.0f;
    private static final float FIT_WEIGHT_L = 10.0f;
    private static final float FIT_WEIGHT_S = 1.0f;
    public static final int MAIN_COLOR_DARK = -14671580;
    public static final int MAIN_COLOR_LIGHT = -2433824;
    public static final int MAIN_COLOR_REGULAR = -16777216;
    private static final String TAG = "Tonal";
    private final Context mContext;
    private final TonalPalette mGreyPalette;
    private float[] mTmpHSL = new float[3];
    private final ArrayList<TonalPalette> mTonalPalettes;

    public Tonal(Context context) {
        this.mTonalPalettes = new ConfigParser(context).getTonalPalettes();
        this.mContext = context;
        this.mGreyPalette = this.mTonalPalettes.get(0);
        this.mTonalPalettes.remove(0);
    }

    private void applyFallback(WallpaperColors wallpaperColors, ColorExtractor.GradientColors gradientColors, ColorExtractor.GradientColors gradientColors2, ColorExtractor.GradientColors gradientColors3) {
        this.applyFallback(wallpaperColors, gradientColors);
        this.applyFallback(wallpaperColors, gradientColors2);
        this.applyFallback(wallpaperColors, gradientColors3);
    }

    private static int bestFit(TonalPalette tonalPalette, float f, float f2, float f3) {
        int n = -1;
        float f4 = Float.POSITIVE_INFINITY;
        for (int i = 0; i < tonalPalette.h.length; ++i) {
            float f5 = Math.abs(f - tonalPalette.h[i]) * 1.0f + Math.abs(f2 - tonalPalette.s[i]) * 1.0f + Math.abs(f3 - tonalPalette.l[i]) * 10.0f;
            float f6 = f4;
            if (f5 < f4) {
                f6 = f5;
                n = i;
            }
            f4 = f6;
        }
        return n;
    }

    private TonalPalette findTonalPalette(float f, float f2) {
        TonalPalette tonalPalette;
        if (f2 < 0.05f) {
            return this.mGreyPalette;
        }
        TonalPalette tonalPalette2 = null;
        float f3 = Float.POSITIVE_INFINITY;
        int n = this.mTonalPalettes.size();
        int n2 = 0;
        do {
            TonalPalette tonalPalette3;
            tonalPalette = tonalPalette2;
            if (n2 >= n) break;
            tonalPalette = this.mTonalPalettes.get(n2);
            if (f >= tonalPalette.minHue && f <= tonalPalette.maxHue || tonalPalette.maxHue > 1.0f && f >= 0.0f && f <= Tonal.fract(tonalPalette.maxHue) || tonalPalette.minHue < 0.0f && f >= Tonal.fract(tonalPalette.minHue) && f <= 1.0f) break;
            if (f <= tonalPalette.minHue && tonalPalette.minHue - f < f3) {
                tonalPalette3 = tonalPalette;
                f2 = tonalPalette.minHue - f;
            } else if (f >= tonalPalette.maxHue && f - tonalPalette.maxHue < f3) {
                tonalPalette3 = tonalPalette;
                f2 = f - tonalPalette.maxHue;
            } else if (tonalPalette.maxHue > 1.0f && f >= Tonal.fract(tonalPalette.maxHue) && f - Tonal.fract(tonalPalette.maxHue) < f3) {
                tonalPalette3 = tonalPalette;
                f2 = f - Tonal.fract(tonalPalette.maxHue);
            } else {
                tonalPalette3 = tonalPalette2;
                f2 = f3;
                if (tonalPalette.minHue < 0.0f) {
                    tonalPalette3 = tonalPalette2;
                    f2 = f3;
                    if (f <= Tonal.fract(tonalPalette.minHue)) {
                        tonalPalette3 = tonalPalette2;
                        f2 = f3;
                        if (Tonal.fract(tonalPalette.minHue) - f < f3) {
                            tonalPalette3 = tonalPalette;
                            f2 = Tonal.fract(tonalPalette.minHue) - f;
                        }
                    }
                }
            }
            ++n2;
            tonalPalette2 = tonalPalette3;
            f3 = f2;
        } while (true);
        return tonalPalette;
    }

    private static float[] fit(float[] arrf, float f, int n, float f2, float f3) {
        float[] arrf2 = new float[arrf.length];
        float f4 = arrf[n];
        for (n = 0; n < arrf.length; ++n) {
            arrf2[n] = MathUtils.constrain(arrf[n] + (f - f4), f2, f3);
        }
        return arrf2;
    }

    private static float fract(float f) {
        return f - (float)Math.floor(f);
    }

    private int getColorInt(int n, float[] arrf, float[] arrf2, float[] arrf3) {
        this.mTmpHSL[0] = Tonal.fract(arrf[n]) * 360.0f;
        arrf = this.mTmpHSL;
        arrf[1] = arrf2[n];
        arrf[2] = arrf3[n];
        return ColorUtils.HSLToColor(arrf);
    }

    private int[] getColorPalette(TonalPalette tonalPalette) {
        return this.getColorPalette(tonalPalette.h, tonalPalette.s, tonalPalette.l);
    }

    private int[] getColorPalette(float[] arrf, float[] arrf2, float[] arrf3) {
        int[] arrn = new int[arrf.length];
        for (int i = 0; i < arrn.length; ++i) {
            arrn[i] = this.getColorInt(i, arrf, arrf2, arrf3);
        }
        return arrn;
    }

    private boolean runTonalExtraction(WallpaperColors object, ColorExtractor.GradientColors gradientColors, ColorExtractor.GradientColors gradientColors2, ColorExtractor.GradientColors gradientColors3) {
        if (object == null) {
            return false;
        }
        float[] arrf = ((WallpaperColors)object).getMainColors();
        int n = arrf.size();
        boolean bl = (((WallpaperColors)object).getColorHints() & 1) != 0;
        if (n == 0) {
            return false;
        }
        n = arrf.get(0).toArgb();
        float[] arrf2 = new float[3];
        ColorUtils.RGBToHSL(Color.red(n), Color.green(n), Color.blue(n), arrf2);
        arrf2[0] = arrf2[0] / 360.0f;
        float[] arrf3 = this.findTonalPalette(arrf2[0], arrf2[1]);
        if (arrf3 == null) {
            Log.w(TAG, "Could not find a tonal palette!");
            return false;
        }
        int n2 = Tonal.bestFit((TonalPalette)arrf3, arrf2[0], arrf2[1], arrf2[2]);
        if (n2 == -1) {
            Log.w(TAG, "Could not find best fit!");
            return false;
        }
        object = Tonal.fit(arrf3.h, arrf2[0], n2, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY);
        arrf = Tonal.fit(arrf3.s, arrf2[1], n2, 0.0f, 1.0f);
        arrf3 = Tonal.fit(arrf3.l, arrf2[2], n2, 0.0f, 1.0f);
        arrf2 = this.getColorPalette((float[])object, arrf, arrf3);
        float[] arrf4 = new StringBuilder();
        arrf4.append("Tonal Palette - index: ");
        arrf4.append(n2);
        arrf4.append(". Main color: ");
        arrf4.append(Integer.toHexString(this.getColorInt(n2, (float[])object, arrf, arrf3)));
        arrf4.append("\nColors: ");
        arrf4 = new StringBuilder(arrf4.toString());
        for (n = 0; n < ((float[])object).length; ++n) {
            arrf4.append(Integer.toHexString(this.getColorInt(n, (float[])object, arrf, arrf3)));
            if (n >= ((float[])object).length - 1) continue;
            arrf4.append(", ");
        }
        Log.d(TAG, arrf4.toString());
        n = this.getColorInt(n2, (float[])object, arrf, arrf3);
        ColorUtils.colorToHSL(n, this.mTmpHSL);
        arrf4 = this.mTmpHSL;
        float f = arrf4[2];
        ColorUtils.colorToHSL(-2433824, arrf4);
        arrf4 = this.mTmpHSL;
        if (f > arrf4[2]) {
            return false;
        }
        ColorUtils.colorToHSL(-14671580, arrf4);
        if (f < this.mTmpHSL[2]) {
            return false;
        }
        gradientColors.setMainColor(n);
        gradientColors.setSecondaryColor(n);
        gradientColors.setColorPalette((int[])arrf2);
        n = bl ? ((float[])object).length - 1 : (n2 < 2 ? 0 : Math.min(n2, 3));
        n = this.getColorInt(n, (float[])object, arrf, arrf3);
        gradientColors2.setMainColor(n);
        gradientColors2.setSecondaryColor(n);
        gradientColors2.setColorPalette((int[])arrf2);
        n = bl ? ((float[])object).length - 1 : (n2 < 2 ? 0 : 2);
        n = this.getColorInt(n, (float[])object, arrf, arrf3);
        gradientColors3.setMainColor(n);
        gradientColors3.setSecondaryColor(n);
        gradientColors3.setColorPalette((int[])arrf2);
        gradientColors.setSupportsDarkText(bl);
        gradientColors2.setSupportsDarkText(bl);
        gradientColors3.setSupportsDarkText(bl);
        object = new StringBuilder();
        ((StringBuilder)object).append("Gradients: \n\tNormal ");
        ((StringBuilder)object).append(gradientColors);
        ((StringBuilder)object).append("\n\tDark ");
        ((StringBuilder)object).append(gradientColors2);
        ((StringBuilder)object).append("\n\tExtra dark: ");
        ((StringBuilder)object).append(gradientColors3);
        Log.d(TAG, ((StringBuilder)object).toString());
        return true;
    }

    public void applyFallback(WallpaperColors arrf, ColorExtractor.GradientColors gradientColors) {
        boolean bl = arrf != null && (arrf.getColorHints() & 1) != 0;
        int n = arrf != null && (arrf.getColorHints() & 2) != 0 ? 1 : 0;
        boolean bl2 = (this.mContext.getResources().getConfiguration().uiMode & 48) == 32;
        n = bl ? -2433824 : (n == 0 && !bl2 ? -16777216 : -14671580);
        arrf = new float[3];
        ColorUtils.colorToHSL(n, arrf);
        gradientColors.setMainColor(n);
        gradientColors.setSecondaryColor(n);
        gradientColors.setSupportsDarkText(bl);
        gradientColors.setColorPalette(this.getColorPalette(this.findTonalPalette(arrf[0], arrf[1])));
    }

    @Override
    public void extractInto(WallpaperColors wallpaperColors, ColorExtractor.GradientColors gradientColors, ColorExtractor.GradientColors gradientColors2, ColorExtractor.GradientColors gradientColors3) {
        if (!this.runTonalExtraction(wallpaperColors, gradientColors, gradientColors2, gradientColors3)) {
            this.applyFallback(wallpaperColors, gradientColors, gradientColors2, gradientColors3);
        }
    }

    @VisibleForTesting
    public static class ColorRange {
        private Range<Float> mHue;
        private Range<Float> mLightness;
        private Range<Float> mSaturation;

        public ColorRange(Range<Float> range, Range<Float> range2, Range<Float> range3) {
            this.mHue = range;
            this.mSaturation = range2;
            this.mLightness = range3;
        }

        public boolean containsColor(float f, float f2, float f3) {
            if (!this.mHue.contains(Float.valueOf(f))) {
                return false;
            }
            if (!this.mSaturation.contains(Float.valueOf(f2))) {
                return false;
            }
            return this.mLightness.contains(Float.valueOf(f3));
        }

        public float[] getCenter() {
            return new float[]{this.mHue.getLower().floatValue() + (this.mHue.getUpper().floatValue() - this.mHue.getLower().floatValue()) / 2.0f, this.mSaturation.getLower().floatValue() + (this.mSaturation.getUpper().floatValue() - this.mSaturation.getLower().floatValue()) / 2.0f, this.mLightness.getLower().floatValue() + (this.mLightness.getUpper().floatValue() - this.mLightness.getLower().floatValue()) / 2.0f};
        }

        public String toString() {
            return String.format("H: %s, S: %s, L %s", this.mHue, this.mSaturation, this.mLightness);
        }
    }

    @VisibleForTesting
    public static class ConfigParser {
        private final ArrayList<TonalPalette> mTonalPalettes = new ArrayList();

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public ConfigParser(Context object) {
            try {
                XmlResourceParser xmlResourceParser = object.getResources().getXml(18284549);
                int n = xmlResourceParser.getEventType();
                do {
                    if (n == 1) {
                        return;
                    }
                    if (n != 0 && n != 3) {
                        if (n != 2) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Invalid XML event ");
                            stringBuilder.append(n);
                            stringBuilder.append(" - ");
                            stringBuilder.append(xmlResourceParser.getName());
                            super(stringBuilder.toString(), (XmlPullParser)xmlResourceParser, null);
                            throw object;
                        }
                        if (xmlResourceParser.getName().equals("palettes")) {
                            this.parsePalettes(xmlResourceParser);
                        }
                    }
                    n = xmlResourceParser.next();
                } while (true);
            }
            catch (IOException | XmlPullParserException throwable) {
                throw new RuntimeException(throwable);
            }
        }

        private void parsePalettes(XmlPullParser object) throws XmlPullParserException, IOException {
            object.require(2, null, "palettes");
            while (object.next() != 3) {
                if (object.getEventType() != 2) continue;
                String string2 = object.getName();
                if (string2.equals("palette")) {
                    this.mTonalPalettes.add(this.readPalette((XmlPullParser)object));
                    object.next();
                    continue;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Invalid tag: ");
                ((StringBuilder)object).append(string2);
                throw new XmlPullParserException(((StringBuilder)object).toString());
            }
        }

        private float[] readFloatArray(String arrstring) throws IOException, XmlPullParserException {
            arrstring = arrstring.replaceAll(" ", "").replaceAll("\n", "").split(",");
            float[] arrf = new float[arrstring.length];
            for (int i = 0; i < arrstring.length; ++i) {
                arrf[i] = Float.parseFloat(arrstring[i]);
            }
            return arrf;
        }

        private TonalPalette readPalette(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
            xmlPullParser.require(2, null, "palette");
            float[] arrf = this.readFloatArray(xmlPullParser.getAttributeValue(null, "h"));
            float[] arrf2 = this.readFloatArray(xmlPullParser.getAttributeValue(null, "s"));
            float[] arrf3 = this.readFloatArray(xmlPullParser.getAttributeValue(null, "l"));
            if (arrf != null && arrf2 != null && arrf3 != null) {
                return new TonalPalette(arrf, arrf2, arrf3);
            }
            throw new XmlPullParserException("Incomplete range tag.", xmlPullParser, null);
        }

        private ColorRange readRange(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
            xmlPullParser.require(2, null, "range");
            float[] arrf = this.readFloatArray(xmlPullParser.getAttributeValue(null, "h"));
            float[] arrf2 = this.readFloatArray(xmlPullParser.getAttributeValue(null, "s"));
            float[] arrf3 = this.readFloatArray(xmlPullParser.getAttributeValue(null, "l"));
            if (arrf != null && arrf2 != null && arrf3 != null) {
                return new ColorRange(new Range<Float>(Float.valueOf(arrf[0]), Float.valueOf(arrf[1])), new Range<Float>(Float.valueOf(arrf2[0]), Float.valueOf(arrf2[1])), new Range<Float>(Float.valueOf(arrf3[0]), Float.valueOf(arrf3[1])));
            }
            throw new XmlPullParserException("Incomplete range tag.", xmlPullParser, null);
        }

        public ArrayList<TonalPalette> getTonalPalettes() {
            return this.mTonalPalettes;
        }
    }

    @VisibleForTesting
    public static class TonalPalette {
        public final float[] h;
        public final float[] l;
        public final float maxHue;
        public final float minHue;
        public final float[] s;

        TonalPalette(float[] arrf, float[] arrf2, float[] arrf3) {
            if (arrf.length == arrf2.length && arrf2.length == arrf3.length) {
                this.h = arrf;
                this.s = arrf2;
                this.l = arrf3;
                float f = Float.POSITIVE_INFINITY;
                float f2 = Float.NEGATIVE_INFINITY;
                for (float f3 : arrf) {
                    f = Math.min(f3, f);
                    f2 = Math.max(f3, f2);
                }
                this.minHue = f;
                this.maxHue = f2;
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("All arrays should have the same size. h: ");
            stringBuilder.append(Arrays.toString(arrf));
            stringBuilder.append(" s: ");
            stringBuilder.append(Arrays.toString(arrf2));
            stringBuilder.append(" l: ");
            stringBuilder.append(Arrays.toString(arrf3));
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

}

