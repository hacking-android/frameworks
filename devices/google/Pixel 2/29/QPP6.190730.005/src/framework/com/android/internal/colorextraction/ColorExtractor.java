/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.colorextraction;

import android.app.WallpaperColors;
import android.app.WallpaperManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.colorextraction.types.ExtractionType;
import com.android.internal.colorextraction.types.Tonal;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Executor;

public class ColorExtractor
implements WallpaperManager.OnColorsChangedListener {
    private static final boolean DEBUG = false;
    private static final String TAG = "ColorExtractor";
    public static final int TYPE_DARK = 1;
    public static final int TYPE_EXTRA_DARK = 2;
    public static final int TYPE_NORMAL = 0;
    private static final int[] sGradientTypes = new int[]{0, 1, 2};
    private final Context mContext;
    private final ExtractionType mExtractionType;
    protected final SparseArray<GradientColors[]> mGradientColors;
    protected WallpaperColors mLockColors;
    private final ArrayList<WeakReference<OnColorsChangedListener>> mOnColorsChangedListeners;
    protected WallpaperColors mSystemColors;

    public ColorExtractor(Context context) {
        this(context, new Tonal(context), true, context.getSystemService(WallpaperManager.class));
    }

    @VisibleForTesting
    public ColorExtractor(Context arrn, ExtractionType arrn2, boolean bl, WallpaperManager wallpaperManager) {
        this.mContext = arrn;
        this.mExtractionType = arrn2;
        this.mGradientColors = new SparseArray();
        int[] arrn3 = arrn2 = new int[2];
        arrn3[0] = 2;
        arrn3[1] = 1;
        for (int n : arrn2) {
            GradientColors[] arrgradientColors = new GradientColors[sGradientTypes.length];
            this.mGradientColors.append(n, arrgradientColors);
            arrn = sGradientTypes;
            int n2 = arrn.length;
            for (n = 0; n < n2; ++n) {
                arrgradientColors[arrn[n]] = new GradientColors();
            }
        }
        this.mOnColorsChangedListeners = new ArrayList();
        if (wallpaperManager == null) {
            Log.w(TAG, "Can't listen to color changes!");
        } else {
            wallpaperManager.addOnColorsChangedListener(this, null);
            this.initExtractColors(wallpaperManager, bl);
        }
    }

    private void extractInto(WallpaperColors wallpaperColors, GradientColors gradientColors, GradientColors gradientColors2, GradientColors gradientColors3) {
        this.mExtractionType.extractInto(wallpaperColors, gradientColors, gradientColors2, gradientColors3);
    }

    private void initExtractColors(WallpaperManager wallpaperManager, boolean bl) {
        if (bl) {
            this.mSystemColors = wallpaperManager.getWallpaperColors(1);
            this.mLockColors = wallpaperManager.getWallpaperColors(2);
            this.extractWallpaperColors();
        } else {
            new LoadWallpaperColors().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, wallpaperManager);
        }
    }

    public void addOnColorsChangedListener(OnColorsChangedListener onColorsChangedListener) {
        this.mOnColorsChangedListeners.add(new WeakReference<OnColorsChangedListener>(onColorsChangedListener));
    }

    public void destroy() {
        WallpaperManager wallpaperManager = this.mContext.getSystemService(WallpaperManager.class);
        if (wallpaperManager != null) {
            wallpaperManager.removeOnColorsChangedListener(this);
        }
    }

    protected void extractWallpaperColors() {
        GradientColors[] arrgradientColors = this.mGradientColors.get(1);
        GradientColors[] arrgradientColors2 = this.mGradientColors.get(2);
        this.extractInto(this.mSystemColors, arrgradientColors[0], arrgradientColors[1], arrgradientColors[2]);
        this.extractInto(this.mLockColors, arrgradientColors2[0], arrgradientColors2[1], arrgradientColors2[2]);
    }

    public GradientColors getColors(int n) {
        return this.getColors(n, 1);
    }

    public GradientColors getColors(int n, int n2) {
        if (n2 != 0 && n2 != 1 && n2 != 2) {
            throw new IllegalArgumentException("type should be TYPE_NORMAL, TYPE_DARK or TYPE_EXTRA_DARK");
        }
        if (n != 2 && n != 1) {
            throw new IllegalArgumentException("which should be FLAG_SYSTEM or FLAG_NORMAL");
        }
        return this.mGradientColors.get(n)[n2];
    }

    public WallpaperColors getWallpaperColors(int n) {
        if (n == 2) {
            return this.mLockColors;
        }
        if (n == 1) {
            return this.mSystemColors;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid value for which: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public void onColorsChanged(WallpaperColors wallpaperColors, int n) {
        GradientColors[] arrgradientColors;
        boolean bl = false;
        if ((n & 2) != 0) {
            this.mLockColors = wallpaperColors;
            arrgradientColors = this.mGradientColors.get(2);
            this.extractInto(wallpaperColors, arrgradientColors[0], arrgradientColors[1], arrgradientColors[2]);
            bl = true;
        }
        if ((n & 1) != 0) {
            this.mSystemColors = wallpaperColors;
            arrgradientColors = this.mGradientColors.get(1);
            this.extractInto(wallpaperColors, arrgradientColors[0], arrgradientColors[1], arrgradientColors[2]);
            bl = true;
        }
        if (bl) {
            this.triggerColorsChanged(n);
        }
    }

    public void removeOnColorsChangedListener(OnColorsChangedListener onColorsChangedListener) {
        ArrayList<WeakReference<OnColorsChangedListener>> arrayList = new ArrayList<WeakReference<OnColorsChangedListener>>(this.mOnColorsChangedListeners);
        int n = arrayList.size();
        for (int i = 0; i < n; ++i) {
            WeakReference<OnColorsChangedListener> weakReference = arrayList.get(i);
            if (weakReference.get() != onColorsChangedListener) continue;
            this.mOnColorsChangedListeners.remove(weakReference);
            break;
        }
    }

    protected void triggerColorsChanged(int n) {
        ArrayList<WeakReference<OnColorsChangedListener>> arrayList = new ArrayList<WeakReference<OnColorsChangedListener>>(this.mOnColorsChangedListeners);
        int n2 = arrayList.size();
        for (int i = 0; i < n2; ++i) {
            WeakReference<OnColorsChangedListener> weakReference = arrayList.get(i);
            OnColorsChangedListener onColorsChangedListener = (OnColorsChangedListener)weakReference.get();
            if (onColorsChangedListener == null) {
                this.mOnColorsChangedListeners.remove(weakReference);
                continue;
            }
            onColorsChangedListener.onColorsChanged(this, n);
        }
    }

    public static class GradientColors {
        private int[] mColorPalette;
        private int mMainColor;
        private int mSecondaryColor;
        private boolean mSupportsDarkText;

        public boolean equals(Object object) {
            boolean bl = false;
            if (object != null && object.getClass() == this.getClass()) {
                object = (GradientColors)object;
                boolean bl2 = bl;
                if (((GradientColors)object).mMainColor == this.mMainColor) {
                    bl2 = bl;
                    if (((GradientColors)object).mSecondaryColor == this.mSecondaryColor) {
                        bl2 = bl;
                        if (((GradientColors)object).mSupportsDarkText == this.mSupportsDarkText) {
                            bl2 = true;
                        }
                    }
                }
                return bl2;
            }
            return false;
        }

        public int[] getColorPalette() {
            return this.mColorPalette;
        }

        public int getMainColor() {
            return this.mMainColor;
        }

        public int getSecondaryColor() {
            return this.mSecondaryColor;
        }

        public int hashCode() {
            return (this.mMainColor * 31 + this.mSecondaryColor) * 31 + (this.mSupportsDarkText ^ true);
        }

        public void set(GradientColors gradientColors) {
            this.mMainColor = gradientColors.mMainColor;
            this.mSecondaryColor = gradientColors.mSecondaryColor;
            this.mColorPalette = gradientColors.mColorPalette;
            this.mSupportsDarkText = gradientColors.mSupportsDarkText;
        }

        public void setColorPalette(int[] arrn) {
            this.mColorPalette = arrn;
        }

        public void setMainColor(int n) {
            this.mMainColor = n;
        }

        public void setSecondaryColor(int n) {
            this.mSecondaryColor = n;
        }

        public void setSupportsDarkText(boolean bl) {
            this.mSupportsDarkText = bl;
        }

        public boolean supportsDarkText() {
            return this.mSupportsDarkText;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("GradientColors(");
            stringBuilder.append(Integer.toHexString(this.mMainColor));
            stringBuilder.append(", ");
            stringBuilder.append(Integer.toHexString(this.mSecondaryColor));
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    private class LoadWallpaperColors
    extends AsyncTask<WallpaperManager, Void, Void> {
        private WallpaperColors mLockColors;
        private WallpaperColors mSystemColors;

        private LoadWallpaperColors() {
        }

        protected Void doInBackground(WallpaperManager ... arrwallpaperManager) {
            this.mSystemColors = arrwallpaperManager[0].getWallpaperColors(1);
            this.mLockColors = arrwallpaperManager[0].getWallpaperColors(2);
            return null;
        }

        @Override
        protected void onPostExecute(Void object) {
            object = ColorExtractor.this;
            ((ColorExtractor)object).mSystemColors = this.mSystemColors;
            ((ColorExtractor)object).mLockColors = this.mLockColors;
            ((ColorExtractor)object).extractWallpaperColors();
            ColorExtractor.this.triggerColorsChanged(3);
        }
    }

    public static interface OnColorsChangedListener {
        public void onColorsChanged(ColorExtractor var1, int var2);
    }

}

