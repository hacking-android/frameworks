/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.graphics.palette;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseBooleanArray;
import com.android.internal.graphics.ColorUtils;
import com.android.internal.graphics.palette.ColorCutQuantizer;
import com.android.internal.graphics.palette.Quantizer;
import com.android.internal.graphics.palette.Target;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

public final class Palette {
    static final int DEFAULT_CALCULATE_NUMBER_COLORS = 16;
    static final Filter DEFAULT_FILTER = new Filter(){
        private static final float BLACK_MAX_LIGHTNESS = 0.05f;
        private static final float WHITE_MIN_LIGHTNESS = 0.95f;

        private boolean isBlack(float[] arrf) {
            boolean bl = arrf[2] <= 0.05f;
            return bl;
        }

        private boolean isNearRedILine(float[] arrf) {
            boolean bl;
            boolean bl2 = bl = false;
            if (arrf[0] >= 10.0f) {
                bl2 = bl;
                if (arrf[0] <= 37.0f) {
                    bl2 = bl;
                    if (arrf[1] <= 0.82f) {
                        bl2 = true;
                    }
                }
            }
            return bl2;
        }

        private boolean isWhite(float[] arrf) {
            boolean bl = arrf[2] >= 0.95f;
            return bl;
        }

        @Override
        public boolean isAllowed(int n, float[] arrf) {
            boolean bl = !this.isWhite(arrf) && !this.isBlack(arrf) && !this.isNearRedILine(arrf);
            return bl;
        }
    };
    static final int DEFAULT_RESIZE_BITMAP_AREA = 12544;
    static final String LOG_TAG = "Palette";
    static final boolean LOG_TIMINGS = false;
    static final float MIN_CONTRAST_BODY_TEXT = 4.5f;
    static final float MIN_CONTRAST_TITLE_TEXT = 3.0f;
    private final Swatch mDominantSwatch;
    private final Map<Target, Swatch> mSelectedSwatches;
    private final List<Swatch> mSwatches;
    private final List<Target> mTargets;
    private final SparseBooleanArray mUsedColors;

    Palette(List<Swatch> list, List<Target> list2) {
        this.mSwatches = list;
        this.mTargets = list2;
        this.mUsedColors = new SparseBooleanArray();
        this.mSelectedSwatches = new ArrayMap<Target, Swatch>();
        this.mDominantSwatch = this.findDominantSwatch();
    }

    private static float[] copyHslValues(Swatch swatch) {
        float[] arrf = new float[3];
        System.arraycopy(swatch.getHsl(), 0, arrf, 0, 3);
        return arrf;
    }

    private Swatch findDominantSwatch() {
        int n = Integer.MIN_VALUE;
        Swatch swatch = null;
        int n2 = this.mSwatches.size();
        for (int i = 0; i < n2; ++i) {
            Swatch swatch2 = this.mSwatches.get(i);
            int n3 = n;
            if (swatch2.getPopulation() > n) {
                swatch = swatch2;
                n3 = swatch2.getPopulation();
            }
            n = n3;
        }
        return swatch;
    }

    public static Builder from(Bitmap bitmap) {
        return new Builder(bitmap);
    }

    public static Palette from(List<Swatch> list) {
        return new Builder(list).generate();
    }

    @Deprecated
    public static Palette generate(Bitmap bitmap) {
        return Palette.from(bitmap).generate();
    }

    @Deprecated
    public static Palette generate(Bitmap bitmap, int n) {
        return Palette.from(bitmap).maximumColorCount(n).generate();
    }

    @Deprecated
    public static AsyncTask<Bitmap, Void, Palette> generateAsync(Bitmap bitmap, int n, PaletteAsyncListener paletteAsyncListener) {
        return Palette.from(bitmap).maximumColorCount(n).generate(paletteAsyncListener);
    }

    @Deprecated
    public static AsyncTask<Bitmap, Void, Palette> generateAsync(Bitmap bitmap, PaletteAsyncListener paletteAsyncListener) {
        return Palette.from(bitmap).generate(paletteAsyncListener);
    }

    private float generateScore(Swatch swatch, Target target) {
        float[] arrf = swatch.getHsl();
        float f = 0.0f;
        float f2 = 0.0f;
        float f3 = 0.0f;
        Swatch swatch2 = this.mDominantSwatch;
        int n = swatch2 != null ? swatch2.getPopulation() : 1;
        if (target.getSaturationWeight() > 0.0f) {
            f = target.getSaturationWeight() * (1.0f - Math.abs(arrf[1] - target.getTargetSaturation()));
        }
        if (target.getLightnessWeight() > 0.0f) {
            f2 = target.getLightnessWeight() * (1.0f - Math.abs(arrf[2] - target.getTargetLightness()));
        }
        if (target.getPopulationWeight() > 0.0f) {
            f3 = target.getPopulationWeight() * ((float)swatch.getPopulation() / (float)n);
        }
        return f + f2 + f3;
    }

    private Swatch generateScoredTarget(Target target) {
        Swatch swatch = this.getMaxScoredSwatchForTarget(target);
        if (swatch != null && target.isExclusive()) {
            this.mUsedColors.append(swatch.getRgb(), true);
        }
        return swatch;
    }

    private Swatch getMaxScoredSwatchForTarget(Target target) {
        float f = 0.0f;
        Swatch swatch = null;
        int n = this.mSwatches.size();
        for (int i = 0; i < n; ++i) {
            float f2;
            Swatch swatch2;
            block3 : {
                float f3;
                Swatch swatch3;
                block4 : {
                    swatch3 = this.mSwatches.get(i);
                    f2 = f;
                    swatch2 = swatch;
                    if (!this.shouldBeScoredForTarget(swatch3, target)) break block3;
                    f3 = this.generateScore(swatch3, target);
                    if (swatch == null) break block4;
                    f2 = f;
                    swatch2 = swatch;
                    if (!(f3 > f)) break block3;
                }
                swatch2 = swatch3;
                f2 = f3;
            }
            f = f2;
            swatch = swatch2;
        }
        return swatch;
    }

    private boolean shouldBeScoredForTarget(Swatch swatch, Target target) {
        float[] arrf = swatch.getHsl();
        boolean bl = true;
        if (!(arrf[1] >= target.getMinimumSaturation() && arrf[1] <= target.getMaximumSaturation() && arrf[2] >= target.getMinimumLightness() && arrf[2] <= target.getMaximumLightness() && !this.mUsedColors.get(swatch.getRgb()))) {
            bl = false;
        }
        return bl;
    }

    void generate() {
        int n = this.mTargets.size();
        for (int i = 0; i < n; ++i) {
            Target target = this.mTargets.get(i);
            target.normalizeWeights();
            this.mSelectedSwatches.put(target, this.generateScoredTarget(target));
        }
        this.mUsedColors.clear();
    }

    public int getColorForTarget(Target object, int n) {
        block0 : {
            if ((object = this.getSwatchForTarget((Target)object)) == null) break block0;
            n = ((Swatch)object).getRgb();
        }
        return n;
    }

    public int getDarkMutedColor(int n) {
        return this.getColorForTarget(Target.DARK_MUTED, n);
    }

    public Swatch getDarkMutedSwatch() {
        return this.getSwatchForTarget(Target.DARK_MUTED);
    }

    public int getDarkVibrantColor(int n) {
        return this.getColorForTarget(Target.DARK_VIBRANT, n);
    }

    public Swatch getDarkVibrantSwatch() {
        return this.getSwatchForTarget(Target.DARK_VIBRANT);
    }

    public int getDominantColor(int n) {
        block0 : {
            Swatch swatch = this.mDominantSwatch;
            if (swatch == null) break block0;
            n = swatch.getRgb();
        }
        return n;
    }

    public Swatch getDominantSwatch() {
        return this.mDominantSwatch;
    }

    public int getLightMutedColor(int n) {
        return this.getColorForTarget(Target.LIGHT_MUTED, n);
    }

    public Swatch getLightMutedSwatch() {
        return this.getSwatchForTarget(Target.LIGHT_MUTED);
    }

    public int getLightVibrantColor(int n) {
        return this.getColorForTarget(Target.LIGHT_VIBRANT, n);
    }

    public Swatch getLightVibrantSwatch() {
        return this.getSwatchForTarget(Target.LIGHT_VIBRANT);
    }

    public int getMutedColor(int n) {
        return this.getColorForTarget(Target.MUTED, n);
    }

    public Swatch getMutedSwatch() {
        return this.getSwatchForTarget(Target.MUTED);
    }

    public Swatch getSwatchForTarget(Target target) {
        return this.mSelectedSwatches.get(target);
    }

    public List<Swatch> getSwatches() {
        return Collections.unmodifiableList(this.mSwatches);
    }

    public List<Target> getTargets() {
        return Collections.unmodifiableList(this.mTargets);
    }

    public int getVibrantColor(int n) {
        return this.getColorForTarget(Target.VIBRANT, n);
    }

    public Swatch getVibrantSwatch() {
        return this.getSwatchForTarget(Target.VIBRANT);
    }

    public static final class Builder {
        private final Bitmap mBitmap;
        private final List<Filter> mFilters = new ArrayList<Filter>();
        private int mMaxColors = 16;
        private Quantizer mQuantizer;
        private Rect mRegion;
        private int mResizeArea = 12544;
        private int mResizeMaxDimension = -1;
        private final List<Swatch> mSwatches;
        private final List<Target> mTargets = new ArrayList<Target>();

        public Builder(Bitmap bitmap) {
            if (bitmap != null && !bitmap.isRecycled()) {
                this.mFilters.add(DEFAULT_FILTER);
                this.mBitmap = bitmap;
                this.mSwatches = null;
                this.mTargets.add(Target.LIGHT_VIBRANT);
                this.mTargets.add(Target.VIBRANT);
                this.mTargets.add(Target.DARK_VIBRANT);
                this.mTargets.add(Target.LIGHT_MUTED);
                this.mTargets.add(Target.MUTED);
                this.mTargets.add(Target.DARK_MUTED);
                return;
            }
            throw new IllegalArgumentException("Bitmap is not valid");
        }

        public Builder(List<Swatch> list) {
            if (list != null && !list.isEmpty()) {
                this.mFilters.add(DEFAULT_FILTER);
                this.mSwatches = list;
                this.mBitmap = null;
                return;
            }
            throw new IllegalArgumentException("List of Swatches is not valid");
        }

        private int[] getPixelsFromBitmap(Bitmap parcelable) {
            int n = ((Bitmap)parcelable).getWidth();
            int n2 = ((Bitmap)parcelable).getHeight();
            int[] arrn = new int[n * n2];
            ((Bitmap)parcelable).getPixels(arrn, 0, n, 0, 0, n, n2);
            parcelable = this.mRegion;
            if (parcelable == null) {
                return arrn;
            }
            int n3 = ((Rect)parcelable).width();
            int n4 = this.mRegion.height();
            parcelable = new int[n3 * n4];
            for (n2 = 0; n2 < n4; ++n2) {
                System.arraycopy(arrn, (this.mRegion.top + n2) * n + this.mRegion.left, parcelable, n2 * n3, n3);
            }
            return parcelable;
        }

        private Bitmap scaleBitmapDown(Bitmap bitmap) {
            double d;
            double d2 = -1.0;
            if (this.mResizeArea > 0) {
                int n = bitmap.getWidth() * bitmap.getHeight();
                int n2 = this.mResizeArea;
                d = d2;
                if (n > n2) {
                    d = Math.sqrt((double)n2 / (double)n);
                }
            } else {
                d = d2;
                if (this.mResizeMaxDimension > 0) {
                    int n = Math.max(bitmap.getWidth(), bitmap.getHeight());
                    int n3 = this.mResizeMaxDimension;
                    d = d2;
                    if (n > n3) {
                        d = (double)n3 / (double)n;
                    }
                }
            }
            if (d <= 0.0) {
                return bitmap;
            }
            return Bitmap.createScaledBitmap(bitmap, (int)Math.ceil((double)bitmap.getWidth() * d), (int)Math.ceil((double)bitmap.getHeight() * d), false);
        }

        public Builder addFilter(Filter filter) {
            if (filter != null) {
                this.mFilters.add(filter);
            }
            return this;
        }

        public Builder addTarget(Target target) {
            if (!this.mTargets.contains(target)) {
                this.mTargets.add(target);
            }
            return this;
        }

        public Builder clearFilters() {
            this.mFilters.clear();
            return this;
        }

        public Builder clearRegion() {
            this.mRegion = null;
            return this;
        }

        public Builder clearTargets() {
            List<Target> list = this.mTargets;
            if (list != null) {
                list.clear();
            }
            return this;
        }

        public AsyncTask<Bitmap, Void, Palette> generate(final PaletteAsyncListener paletteAsyncListener) {
            if (paletteAsyncListener != null) {
                return new AsyncTask<Bitmap, Void, Palette>(){

                    protected Palette doInBackground(Bitmap ... object) {
                        try {
                            object = Builder.this.generate();
                            return object;
                        }
                        catch (Exception exception) {
                            Log.e(Palette.LOG_TAG, "Exception thrown during async generate", exception);
                            return null;
                        }
                    }

                    @Override
                    protected void onPostExecute(Palette palette) {
                        paletteAsyncListener.onGenerated(palette);
                    }
                }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this.mBitmap);
            }
            throw new IllegalArgumentException("listener can not be null");
        }

        public Palette generate() {
            Object object = this.mBitmap;
            if (object != null) {
                Bitmap bitmap = this.scaleBitmapDown((Bitmap)object);
                if (false) {
                    throw new NullPointerException();
                }
                object = this.mRegion;
                if (bitmap != this.mBitmap && object != null) {
                    double d = (double)bitmap.getWidth() / (double)this.mBitmap.getWidth();
                    object.left = (int)Math.floor((double)object.left * d);
                    object.top = (int)Math.floor((double)object.top * d);
                    object.right = Math.min((int)Math.ceil((double)object.right * d), bitmap.getWidth());
                    object.bottom = Math.min((int)Math.ceil((double)object.bottom * d), bitmap.getHeight());
                }
                if (this.mQuantizer == null) {
                    this.mQuantizer = new ColorCutQuantizer();
                }
                Quantizer quantizer = this.mQuantizer;
                int[] arrn = this.getPixelsFromBitmap(bitmap);
                int n = this.mMaxColors;
                if (this.mFilters.isEmpty()) {
                    object = null;
                } else {
                    object = this.mFilters;
                    object = object.toArray(new Filter[object.size()]);
                }
                quantizer.quantize(arrn, n, (Filter[])object);
                if (bitmap != this.mBitmap) {
                    bitmap.recycle();
                }
                object = this.mQuantizer.getQuantizedColors();
                if (false) {
                    throw new NullPointerException();
                }
            } else {
                object = this.mSwatches;
            }
            object = new Palette((List<Swatch>)object, this.mTargets);
            object.generate();
            if (false) {
                throw new NullPointerException();
            }
            return object;
        }

        public Builder maximumColorCount(int n) {
            this.mMaxColors = n;
            return this;
        }

        public Builder resizeBitmapArea(int n) {
            this.mResizeArea = n;
            this.mResizeMaxDimension = -1;
            return this;
        }

        @Deprecated
        public Builder resizeBitmapSize(int n) {
            this.mResizeMaxDimension = n;
            this.mResizeArea = -1;
            return this;
        }

        public Builder setQuantizer(Quantizer quantizer) {
            this.mQuantizer = quantizer;
            return this;
        }

        public Builder setRegion(int n, int n2, int n3, int n4) {
            if (this.mBitmap != null) {
                if (this.mRegion == null) {
                    this.mRegion = new Rect();
                }
                this.mRegion.set(0, 0, this.mBitmap.getWidth(), this.mBitmap.getHeight());
                if (!this.mRegion.intersect(n, n2, n3, n4)) {
                    throw new IllegalArgumentException("The given region must intersect with the Bitmap's dimensions.");
                }
            }
            return this;
        }

    }

    public static interface Filter {
        public boolean isAllowed(int var1, float[] var2);
    }

    public static interface PaletteAsyncListener {
        public void onGenerated(Palette var1);
    }

    public static final class Swatch {
        private final int mBlue;
        private int mBodyTextColor;
        private boolean mGeneratedTextColors;
        private final int mGreen;
        private float[] mHsl;
        private final int mPopulation;
        private final int mRed;
        private final int mRgb;
        private int mTitleTextColor;

        public Swatch(int n, int n2) {
            this.mRed = Color.red(n);
            this.mGreen = Color.green(n);
            this.mBlue = Color.blue(n);
            this.mRgb = n;
            this.mPopulation = n2;
        }

        Swatch(int n, int n2, int n3, int n4) {
            this.mRed = n;
            this.mGreen = n2;
            this.mBlue = n3;
            this.mRgb = Color.rgb(n, n2, n3);
            this.mPopulation = n4;
        }

        Swatch(float[] arrf, int n) {
            this(ColorUtils.HSLToColor(arrf), n);
            this.mHsl = arrf;
        }

        private void ensureTextColorsGenerated() {
            if (!this.mGeneratedTextColors) {
                int n = ColorUtils.calculateMinimumAlpha(-1, this.mRgb, 4.5f);
                int n2 = ColorUtils.calculateMinimumAlpha(-1, this.mRgb, 3.0f);
                if (n != -1 && n2 != -1) {
                    this.mBodyTextColor = ColorUtils.setAlphaComponent(-1, n);
                    this.mTitleTextColor = ColorUtils.setAlphaComponent(-1, n2);
                    this.mGeneratedTextColors = true;
                    return;
                }
                int n3 = ColorUtils.calculateMinimumAlpha(-16777216, this.mRgb, 4.5f);
                int n4 = ColorUtils.calculateMinimumAlpha(-16777216, this.mRgb, 3.0f);
                if (n3 != -1 && n4 != -1) {
                    this.mBodyTextColor = ColorUtils.setAlphaComponent(-16777216, n3);
                    this.mTitleTextColor = ColorUtils.setAlphaComponent(-16777216, n4);
                    this.mGeneratedTextColors = true;
                    return;
                }
                n3 = n != -1 ? ColorUtils.setAlphaComponent(-1, n) : ColorUtils.setAlphaComponent(-16777216, n3);
                this.mBodyTextColor = n3;
                n3 = n2 != -1 ? ColorUtils.setAlphaComponent(-1, n2) : ColorUtils.setAlphaComponent(-16777216, n4);
                this.mTitleTextColor = n3;
                this.mGeneratedTextColors = true;
            }
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (object != null && this.getClass() == object.getClass()) {
                object = (Swatch)object;
                if (this.mPopulation != ((Swatch)object).mPopulation || this.mRgb != ((Swatch)object).mRgb) {
                    bl = false;
                }
                return bl;
            }
            return false;
        }

        public int getBodyTextColor() {
            this.ensureTextColorsGenerated();
            return this.mBodyTextColor;
        }

        public float[] getHsl() {
            if (this.mHsl == null) {
                this.mHsl = new float[3];
            }
            ColorUtils.RGBToHSL(this.mRed, this.mGreen, this.mBlue, this.mHsl);
            return this.mHsl;
        }

        public int getPopulation() {
            return this.mPopulation;
        }

        public int getRgb() {
            return this.mRgb;
        }

        public int getTitleTextColor() {
            this.ensureTextColorsGenerated();
            return this.mTitleTextColor;
        }

        public int hashCode() {
            return this.mRgb * 31 + this.mPopulation;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(this.getClass().getSimpleName());
            stringBuilder.append(" [RGB: #");
            stringBuilder.append(Integer.toHexString(this.getRgb()));
            stringBuilder.append(']');
            stringBuilder.append(" [HSL: ");
            stringBuilder.append(Arrays.toString(this.getHsl()));
            stringBuilder.append(']');
            stringBuilder.append(" [Population: ");
            stringBuilder.append(this.mPopulation);
            stringBuilder.append(']');
            stringBuilder.append(" [Title Text: #");
            stringBuilder.append(Integer.toHexString(this.getTitleTextColor()));
            stringBuilder.append(']');
            stringBuilder.append(" [Body Text: #");
            stringBuilder.append(Integer.toHexString(this.getBodyTextColor()));
            stringBuilder.append(']');
            return stringBuilder.toString();
        }
    }

}

