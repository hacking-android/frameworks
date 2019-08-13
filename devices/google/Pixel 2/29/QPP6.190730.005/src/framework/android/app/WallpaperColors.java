/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.-$
 *  android.app.-$$Lambda
 *  android.app.-$$Lambda$WallpaperColors
 *  android.app.-$$Lambda$WallpaperColors$MQFGJ9EZ9CDeGbIhMufJKqru3IE
 */
package android.app;

import android.annotation.SystemApi;
import android.app.-$;
import android.app._$$Lambda$WallpaperColors$8R5kfKKLfHjpw_QXmD1mWOKwJxc;
import android.app._$$Lambda$WallpaperColors$MQFGJ9EZ9CDeGbIhMufJKqru3IE;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Size;
import com.android.internal.graphics.ColorUtils;
import com.android.internal.graphics.palette.Palette;
import com.android.internal.graphics.palette.Quantizer;
import com.android.internal.graphics.palette.VariationalKMeansQuantizer;
import com.android.internal.util.ContrastColorUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public final class WallpaperColors
implements Parcelable {
    private static final float BRIGHT_IMAGE_MEAN_LUMINANCE = 0.75f;
    public static final Parcelable.Creator<WallpaperColors> CREATOR = new Parcelable.Creator<WallpaperColors>(){

        @Override
        public WallpaperColors createFromParcel(Parcel parcel) {
            return new WallpaperColors(parcel);
        }

        public WallpaperColors[] newArray(int n) {
            return new WallpaperColors[n];
        }
    };
    private static final float DARK_PIXEL_CONTRAST = 6.0f;
    private static final float DARK_THEME_MEAN_LUMINANCE = 0.25f;
    private static final boolean DEBUG_DARK_PIXELS = false;
    public static final int HINT_FROM_BITMAP = 4;
    @SystemApi
    public static final int HINT_SUPPORTS_DARK_TEXT = 1;
    @SystemApi
    public static final int HINT_SUPPORTS_DARK_THEME = 2;
    private static final int MAX_BITMAP_SIZE = 112;
    private static final float MAX_DARK_AREA = 0.025f;
    private static final int MAX_WALLPAPER_EXTRACTION_AREA = 12544;
    private static final float MIN_COLOR_OCCURRENCE = 0.05f;
    private int mColorHints;
    private final ArrayList<Color> mMainColors;

    public WallpaperColors(Color color2, Color color3, Color color4) {
        this(color2, color3, color4, 0);
    }

    @SystemApi
    public WallpaperColors(Color color2, Color color3, Color color4, int n) {
        if (color2 != null) {
            this.mMainColors = new ArrayList(3);
            this.mMainColors.add(color2);
            if (color3 != null) {
                this.mMainColors.add(color3);
            }
            if (color4 != null) {
                if (color3 != null) {
                    this.mMainColors.add(color4);
                } else {
                    throw new IllegalArgumentException("tertiaryColor can't be specified when secondaryColor is null");
                }
            }
            this.mColorHints = n;
            return;
        }
        throw new IllegalArgumentException("Primary color should never be null.");
    }

    public WallpaperColors(Parcel parcel) {
        this.mMainColors = new ArrayList();
        int n = parcel.readInt();
        for (int i = 0; i < n; ++i) {
            Color color2 = Color.valueOf(parcel.readInt());
            this.mMainColors.add(color2);
        }
        this.mColorHints = parcel.readInt();
    }

    private static int calculateDarkHints(Bitmap arrf) {
        int n;
        int n2;
        if (arrf == null) {
            return 0;
        }
        int[] arrn = new int[arrf.getWidth() * arrf.getHeight()];
        double d = 0.0;
        int n3 = (int)((float)arrn.length * 0.025f);
        int n4 = 0;
        arrf.getPixels(arrn, 0, arrf.getWidth(), 0, 0, arrf.getWidth(), arrf.getHeight());
        arrf = new float[3];
        for (n2 = 0; n2 < arrn.length; ++n2) {
            ColorUtils.colorToHSL(arrn[n2], arrf);
            float f = arrf[2];
            int n5 = Color.alpha(arrn[n2]);
            boolean bl = ContrastColorUtil.calculateContrast(arrn[n2], -16777216) > 6.0;
            n = n4;
            if (!bl) {
                n = n4;
                if (n5 != 0) {
                    n = n4 + 1;
                }
            }
            d += (double)f;
            n4 = n;
        }
        n = 0;
        d /= (double)arrn.length;
        n2 = n;
        if (d > 0.75) {
            n2 = n;
            if (n4 < n3) {
                n2 = false | true;
            }
        }
        n4 = n2;
        if (d < 0.25) {
            n4 = n2 | 2;
        }
        return n4;
    }

    private static Size calculateOptimalSize(int n, int n2) {
        int n3 = n * n2;
        double d = 1.0;
        if (n3 > 12544) {
            d = Math.sqrt(12544.0 / (double)n3);
        }
        int n4 = (int)((double)n * d);
        n3 = (int)((double)n2 * d);
        n = n4;
        if (n4 == 0) {
            n = 1;
        }
        n2 = n3;
        if (n3 == 0) {
            n2 = 1;
        }
        return new Size(n, n2);
    }

    public static WallpaperColors fromBitmap(Bitmap object) {
        if (object != null) {
            int n = ((Bitmap)object).getWidth();
            int n2 = ((Bitmap)object).getHeight();
            boolean bl = false;
            Object object2 = object;
            if (n * n2 > 12544) {
                bl = true;
                object2 = WallpaperColors.calculateOptimalSize(((Bitmap)object).getWidth(), ((Bitmap)object).getHeight());
                object2 = Bitmap.createScaledBitmap((Bitmap)object, ((Size)object2).getWidth(), ((Size)object2).getHeight(), true);
            }
            ArrayList<Palette.Swatch> arrayList = new ArrayList<Palette.Swatch>(Palette.from((Bitmap)object2).setQuantizer(new VariationalKMeansQuantizer()).maximumColorCount(5).clearFilters().resizeBitmapArea(12544).generate().getSwatches());
            arrayList.removeIf(new _$$Lambda$WallpaperColors$8R5kfKKLfHjpw_QXmD1mWOKwJxc((float)(((Bitmap)object2).getWidth() * ((Bitmap)object2).getHeight()) * 0.05f));
            arrayList.sort((Comparator<Palette.Swatch>)_$$Lambda$WallpaperColors$MQFGJ9EZ9CDeGbIhMufJKqru3IE.INSTANCE);
            n2 = arrayList.size();
            Object object3 = null;
            Object object4 = null;
            Object object5 = null;
            for (n = 0; n < n2; ++n) {
                object = Color.valueOf(arrayList.get(n).getRgb());
                if (n != 0) {
                    if (n != 1) {
                        if (n != 2) break;
                        object5 = object;
                        continue;
                    }
                    object4 = object;
                    continue;
                }
                object3 = object;
            }
            n = WallpaperColors.calculateDarkHints((Bitmap)object2);
            if (bl) {
                ((Bitmap)object2).recycle();
            }
            return new WallpaperColors((Color)object3, (Color)object4, (Color)object5, n | 4);
        }
        throw new IllegalArgumentException("Bitmap can't be null");
    }

    public static WallpaperColors fromDrawable(Drawable drawable2) {
        block2 : {
            int n;
            Rect rect;
            int n2;
            block4 : {
                block3 : {
                    if (drawable2 == null) break block2;
                    rect = drawable2.copyBounds();
                    n = drawable2.getIntrinsicWidth();
                    int n3 = drawable2.getIntrinsicHeight();
                    if (n <= 0) break block3;
                    n2 = n3;
                    if (n3 > 0) break block4;
                }
                n = 112;
                n2 = 112;
            }
            Object object = WallpaperColors.calculateOptimalSize(n, n2);
            object = Bitmap.createBitmap(((Size)object).getWidth(), ((Size)object).getHeight(), Bitmap.Config.ARGB_8888);
            Object object2 = new Canvas((Bitmap)object);
            drawable2.setBounds(0, 0, ((Bitmap)object).getWidth(), ((Bitmap)object).getHeight());
            drawable2.draw((Canvas)object2);
            object2 = WallpaperColors.fromBitmap((Bitmap)object);
            ((Bitmap)object).recycle();
            drawable2.setBounds(rect);
            return object2;
        }
        throw new IllegalArgumentException("Drawable cannot be null");
    }

    static /* synthetic */ boolean lambda$fromBitmap$0(float f, Palette.Swatch swatch) {
        boolean bl = (float)swatch.getPopulation() < f;
        return bl;
    }

    static /* synthetic */ int lambda$fromBitmap$1(Palette.Swatch swatch, Palette.Swatch swatch2) {
        return swatch2.getPopulation() - swatch.getPopulation();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object != null && this.getClass() == object.getClass()) {
            object = (WallpaperColors)object;
            boolean bl2 = bl;
            if (this.mMainColors.equals(((WallpaperColors)object).mMainColors)) {
                bl2 = bl;
                if (this.mColorHints == ((WallpaperColors)object).mColorHints) {
                    bl2 = true;
                }
            }
            return bl2;
        }
        return false;
    }

    @SystemApi
    public int getColorHints() {
        return this.mColorHints;
    }

    public List<Color> getMainColors() {
        return Collections.unmodifiableList(this.mMainColors);
    }

    public Color getPrimaryColor() {
        return this.mMainColors.get(0);
    }

    public Color getSecondaryColor() {
        Color color2 = this.mMainColors.size() < 2 ? null : this.mMainColors.get(1);
        return color2;
    }

    public Color getTertiaryColor() {
        Color color2 = this.mMainColors.size() < 3 ? null : this.mMainColors.get(2);
        return color2;
    }

    public int hashCode() {
        return this.mMainColors.hashCode() * 31 + this.mColorHints;
    }

    public void setColorHints(int n) {
        this.mColorHints = n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.mMainColors.size(); ++i) {
            stringBuilder.append(Integer.toHexString(this.mMainColors.get(i).toArgb()));
            stringBuilder.append(" ");
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("[WallpaperColors: ");
        stringBuilder2.append(stringBuilder.toString());
        stringBuilder2.append("h: ");
        stringBuilder2.append(this.mColorHints);
        stringBuilder2.append("]");
        return stringBuilder2.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        List<Color> list = this.getMainColors();
        int n2 = list.size();
        parcel.writeInt(n2);
        for (n = 0; n < n2; ++n) {
            parcel.writeInt(list.get(n).toArgb());
        }
        parcel.writeInt(this.mColorHints);
    }

}

