/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 */
package com.android.internal.app;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.os.UserHandle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Pools;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import org.xmlpull.v1.XmlPullParser;

@Deprecated
public class SimpleIconFactory {
    private static final int AMBIENT_SHADOW_ALPHA = 30;
    private static final float BLUR_FACTOR = 0.010416667f;
    private static final float CIRCLE_AREA_BY_RECT = 0.7853982f;
    private static final int DEFAULT_WRAPPER_BACKGROUND = -1;
    private static final int KEY_SHADOW_ALPHA = 61;
    private static final float KEY_SHADOW_DISTANCE = 0.020833334f;
    private static final float LINEAR_SCALE_SLOPE = 0.040449437f;
    private static final float MAX_CIRCLE_AREA_FACTOR = 0.6597222f;
    private static final float MAX_SQUARE_AREA_FACTOR = 0.6510417f;
    private static final int MIN_VISIBLE_ALPHA = 40;
    private static final float SCALE_NOT_INITIALIZED = 0.0f;
    private static final Pools.SynchronizedPool<SimpleIconFactory> sPool = new Pools.SynchronizedPool(Runtime.getRuntime().availableProcessors());
    private final Rect mAdaptiveIconBounds;
    private float mAdaptiveIconScale;
    private int mBadgeBitmapSize;
    private final Bitmap mBitmap;
    private Paint mBlurPaint = new Paint(3);
    private final Rect mBounds;
    private Canvas mCanvas;
    private Context mContext;
    private BlurMaskFilter mDefaultBlurMaskFilter;
    private Paint mDrawPaint = new Paint(3);
    private int mFillResIconDpi;
    private int mIconBitmapSize;
    private final float[] mLeftBorder;
    private final int mMaxSize;
    private final Rect mOldBounds = new Rect();
    private final byte[] mPixels;
    private PackageManager mPm;
    private final float[] mRightBorder;
    private final Canvas mScaleCheckCanvas;
    private int mWrapperBackgroundColor;
    private Drawable mWrapperIcon;

    @Deprecated
    private SimpleIconFactory(Context context, int n, int n2, int n3) {
        this.mContext = context.getApplicationContext();
        this.mPm = this.mContext.getPackageManager();
        this.mIconBitmapSize = n2;
        this.mBadgeBitmapSize = n3;
        this.mFillResIconDpi = n;
        this.mCanvas = new Canvas();
        this.mCanvas.setDrawFilter(new PaintFlagsDrawFilter(4, 2));
        n = this.mMaxSize = n2 * 2;
        this.mBitmap = Bitmap.createBitmap(n, n, Bitmap.Config.ALPHA_8);
        this.mScaleCheckCanvas = new Canvas(this.mBitmap);
        n = this.mMaxSize;
        this.mPixels = new byte[n * n];
        this.mLeftBorder = new float[n];
        this.mRightBorder = new float[n];
        this.mBounds = new Rect();
        this.mAdaptiveIconBounds = new Rect();
        this.mAdaptiveIconScale = 0.0f;
        this.mDefaultBlurMaskFilter = new BlurMaskFilter((float)n2 * 0.010416667f, BlurMaskFilter.Blur.NORMAL);
    }

    private static void convertToConvexArray(float[] arrf, int n, int n2, int n3) {
        float[] arrf2 = new float[arrf.length - 1];
        int n4 = -1;
        float f = Float.MAX_VALUE;
        for (int i = n2 + 1; i <= n3; ++i) {
            int n5;
            if (arrf[i] <= -1.0f) continue;
            if (f == Float.MAX_VALUE) {
                n4 = n2;
            } else if (((arrf[i] - arrf[n4]) / (float)(i - n4) - f) * (float)n < 0.0f) {
                n5 = n4;
                do {
                    n4 = n5;
                    if (n5 <= n2) break;
                    n5 = n4 = n5 - 1;
                } while (!(((arrf[i] - arrf[n4]) / (float)(i - n4) - arrf2[n4]) * (float)n >= 0.0f));
            }
            f = (arrf[i] - arrf[n4]) / (float)(i - n4);
            for (n5 = n4; n5 < i; ++n5) {
                arrf2[n5] = f;
                arrf[n5] = arrf[n4] + (float)(n5 - n4) * f;
            }
            n4 = i;
        }
    }

    private Bitmap createIconBitmap(Drawable drawable2, float f) {
        return this.createIconBitmap(drawable2, f, this.mIconBitmapSize);
    }

    private Bitmap createIconBitmap(Drawable drawable2, float f, int n) {
        Bitmap bitmap = Bitmap.createBitmap(n, n, Bitmap.Config.ARGB_8888);
        this.mCanvas.setBitmap(bitmap);
        this.mOldBounds.set(drawable2.getBounds());
        if (drawable2 instanceof AdaptiveIconDrawable) {
            int n2 = Math.max((int)Math.ceil((float)n * 0.010416667f), Math.round((float)n * (1.0f - f) / 2.0f));
            drawable2.setBounds(n2, n2, n - n2, n - n2);
            drawable2.draw(this.mCanvas);
        } else {
            if (drawable2 instanceof BitmapDrawable) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable)drawable2;
                Bitmap bitmap2 = bitmapDrawable.getBitmap();
                if (bitmap != null && bitmap2.getDensity() == 0) {
                    bitmapDrawable.setTargetDensity(this.mContext.getResources().getDisplayMetrics());
                }
            }
            int n3 = n;
            int n4 = n;
            int n5 = drawable2.getIntrinsicWidth();
            int n6 = drawable2.getIntrinsicHeight();
            int n7 = n3;
            int n8 = n4;
            if (n5 > 0) {
                n7 = n3;
                n8 = n4;
                if (n6 > 0) {
                    float f2 = (float)n5 / (float)n6;
                    if (n5 > n6) {
                        n8 = (int)((float)n3 / f2);
                        n7 = n3;
                    } else {
                        n7 = n3;
                        n8 = n4;
                        if (n6 > n5) {
                            n7 = (int)((float)n4 * f2);
                            n8 = n4;
                        }
                    }
                }
            }
            n3 = (n - n7) / 2;
            n4 = (n - n8) / 2;
            drawable2.setBounds(n3, n4, n3 + n7, n4 + n8);
            this.mCanvas.save();
            this.mCanvas.scale(f, f, n / 2, n / 2);
            drawable2.draw(this.mCanvas);
            this.mCanvas.restore();
        }
        drawable2.setBounds(this.mOldBounds);
        this.mCanvas.setBitmap(null);
        return bitmap;
    }

    private static Drawable getFullResDefaultActivityIcon(int n) {
        return Resources.getSystem().getDrawableForDensity(17629184, n);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private float getScale(Drawable drawable2, RectF rectF) {
        synchronized (this) {
            int n;
            void var2_2;
            int n2;
            int n3;
            int n4;
            float f;
            block22 : {
                block20 : {
                    block21 : {
                        if (drawable2 instanceof AdaptiveIconDrawable && this.mAdaptiveIconScale != 0.0f) {
                            if (var2_2 == null) return this.mAdaptiveIconScale;
                            var2_2.set(this.mAdaptiveIconBounds);
                            return this.mAdaptiveIconScale;
                        }
                        n4 = drawable2.getIntrinsicWidth();
                        n = drawable2.getIntrinsicHeight();
                        if (n4 <= 0 || n <= 0) break block20;
                        if (n4 > this.mMaxSize) break block21;
                        n3 = n4;
                        n2 = n;
                        if (n <= this.mMaxSize) break block22;
                    }
                    n2 = Math.max(n4, n);
                    n3 = this.mMaxSize * n4 / n2;
                    n2 = this.mMaxSize * n / n2;
                    break block22;
                }
                if (n4 <= 0 || n4 > this.mMaxSize) {
                    n4 = this.mMaxSize;
                }
                n3 = n4;
                n4 = n > 0 && n <= this.mMaxSize ? n : this.mMaxSize;
                n2 = n4;
            }
            this.mBitmap.eraseColor(0);
            drawable2.setBounds(0, 0, n3, n2);
            drawable2.draw(this.mScaleCheckCanvas);
            ByteBuffer byteBuffer = ByteBuffer.wrap(this.mPixels);
            byteBuffer.rewind();
            this.mBitmap.copyPixelsToBuffer(byteBuffer);
            int n5 = -1;
            int n6 = -1;
            int n7 = this.mMaxSize + 1;
            int n8 = -1;
            n = 0;
            int n9 = this.mMaxSize;
            for (n4 = 0; n4 < n2; ++n4) {
                int n10;
                int n11 = -1;
                int n12 = -1;
                int n13 = 0;
                int n14 = n;
                for (n = n13; n < n3; ++n14, ++n) {
                    n10 = n12;
                    n13 = n11;
                    if ((this.mPixels[n14] & 255) > 40) {
                        n11 = n12;
                        if (n12 == -1) {
                            n11 = n;
                        }
                        n13 = n;
                        n10 = n11;
                    }
                    n12 = n10;
                    n11 = n13;
                }
                n10 = n14 + (n9 - n3);
                this.mLeftBorder[n4] = n12;
                this.mRightBorder[n4] = n11;
                n = n5;
                n13 = n7;
                n14 = n8;
                if (n12 != -1) {
                    n6 = n4;
                    n = n5;
                    if (n5 == -1) {
                        n = n4;
                    }
                    n13 = Math.min(n7, n12);
                    n14 = Math.max(n8, n11);
                }
                n5 = n;
                n7 = n13;
                n8 = n14;
                n = n10;
            }
            if (n5 == -1) return 1.0f;
            if (n8 != -1) {
                SimpleIconFactory.convertToConvexArray(this.mLeftBorder, 1, n5, n6);
                SimpleIconFactory.convertToConvexArray(this.mRightBorder, -1, n5, n6);
                f = 0.0f;
            } else {
                return 1.0f;
            }
            for (n4 = 0; n4 < n2; ++n4) {
                if (this.mLeftBorder[n4] <= -1.0f) continue;
                f += this.mRightBorder[n4] - this.mLeftBorder[n4] + 1.0f;
            }
            float f2 = f / (float)((n6 + 1 - n5) * (n8 + 1 - n7));
            f2 = f2 < 0.7853982f ? 0.6597222f : (1.0f - f2) * 0.040449437f + 0.6510417f;
            this.mBounds.left = n7;
            this.mBounds.right = n8;
            this.mBounds.top = n5;
            this.mBounds.bottom = n6;
            if (var2_2 != null) {
                var2_2.set((float)this.mBounds.left / (float)n3, (float)this.mBounds.top / (float)n2, 1.0f - (float)this.mBounds.right / (float)n3, 1.0f - (float)this.mBounds.bottom / (float)n2);
            }
            f = (f /= (float)(n3 * n2)) > f2 ? (float)Math.sqrt(f2 / f) : 1.0f;
            if (!(drawable2 instanceof AdaptiveIconDrawable)) return f;
            if (this.mAdaptiveIconScale != 0.0f) return f;
            this.mAdaptiveIconScale = f;
            this.mAdaptiveIconBounds.set(this.mBounds);
            return f;
        }
    }

    private Bitmap maskBitmapToCircle(Bitmap bitmap) {
        Bitmap bitmap2 = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap2);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(-1);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle((float)bitmap.getWidth() / 2.0f, (float)bitmap.getHeight() / 2.0f, (float)bitmap.getWidth() / 2.0f - 1.0f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return bitmap2;
    }

    private Drawable normalizeAndWrapToAdaptiveIcon(Drawable drawable2, RectF rectF, float[] arrf) {
        float f;
        if (this.mWrapperIcon == null) {
            this.mWrapperIcon = this.mContext.getDrawable(17302853).mutate();
        }
        AdaptiveIconDrawable adaptiveIconDrawable = (AdaptiveIconDrawable)this.mWrapperIcon;
        adaptiveIconDrawable.setBounds(0, 0, 1, 1);
        float f2 = f = this.getScale(drawable2, rectF);
        Drawable drawable3 = drawable2;
        if (!(drawable2 instanceof AdaptiveIconDrawable)) {
            drawable3 = (FixedScaleDrawable)adaptiveIconDrawable.getForeground();
            ((DrawableWrapper)drawable3).setDrawable(drawable2);
            ((FixedScaleDrawable)drawable3).setScale(f);
            drawable3 = adaptiveIconDrawable;
            f2 = this.getScale(drawable3, rectF);
            ((ColorDrawable)adaptiveIconDrawable.getBackground()).setColor(this.mWrapperBackgroundColor);
        }
        arrf[0] = f2;
        return drawable3;
    }

    @Deprecated
    public static SimpleIconFactory obtain(Context context) {
        SimpleIconFactory simpleIconFactory = sPool.acquire();
        Object object = simpleIconFactory;
        if (simpleIconFactory == null) {
            object = (ActivityManager)context.getSystemService("activity");
            int n = object == null ? 0 : ((ActivityManager)object).getLauncherLargeIconDensity();
            object = context.getResources();
            object = new SimpleIconFactory(context, n, ((Resources)object).getDimensionPixelSize(17105397), ((Resources)object).getDimensionPixelSize(17105395));
            ((SimpleIconFactory)object).setWrapperBackgroundColor(-1);
        }
        return object;
    }

    private void recreateIcon(Bitmap bitmap, BlurMaskFilter object, int n, int n2, Canvas canvas) {
        synchronized (this) {
            int[] arrn = new int[2];
            this.mBlurPaint.setMaskFilter((MaskFilter)object);
            object = bitmap.extractAlpha(this.mBlurPaint, arrn);
            this.mDrawPaint.setAlpha(n);
            canvas.drawBitmap((Bitmap)object, arrn[0], arrn[1], this.mDrawPaint);
            this.mDrawPaint.setAlpha(n2);
            canvas.drawBitmap((Bitmap)object, arrn[0], (float)arrn[1] + (float)this.mIconBitmapSize * 0.020833334f, this.mDrawPaint);
            this.mDrawPaint.setAlpha(255);
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, this.mDrawPaint);
            return;
        }
    }

    private void recreateIcon(Bitmap bitmap, Canvas canvas) {
        synchronized (this) {
            this.recreateIcon(bitmap, this.mDefaultBlurMaskFilter, 30, 61, canvas);
            return;
        }
    }

    @Deprecated
    Bitmap createAppBadgedIconBitmap(Drawable object, Bitmap bitmap) {
        float f;
        Object object2 = object;
        if (object == null) {
            object2 = SimpleIconFactory.getFullResDefaultActivityIcon(this.mFillResIconDpi);
        }
        int n = ((Drawable)object2).getIntrinsicWidth();
        int n2 = ((Drawable)object2).getIntrinsicHeight();
        float f2 = 1.0f;
        if (n2 > n && n > 0) {
            f = (float)n2 / (float)n;
        } else {
            f = f2;
            if (n > n2) {
                f = f2;
                if (n2 > 0) {
                    f = (float)n / (float)n2;
                }
            }
        }
        object = this.maskBitmapToCircle(this.createIconBitmap((Drawable)object2, f));
        object = new BitmapDrawable(this.mContext.getResources(), (Bitmap)object);
        object = this.createIconBitmap((Drawable)object, this.getScale((Drawable)object, null));
        this.mCanvas.setBitmap((Bitmap)object);
        this.recreateIcon(Bitmap.createBitmap((Bitmap)object), this.mCanvas);
        if (bitmap != null) {
            n2 = this.mBadgeBitmapSize;
            bitmap = Bitmap.createScaledBitmap(bitmap, n2, n2, false);
            object2 = this.mCanvas;
            n2 = this.mIconBitmapSize;
            n = this.mBadgeBitmapSize;
            ((Canvas)object2).drawBitmap(bitmap, n2 - n, n2 - n, null);
        }
        this.mCanvas.setBitmap(null);
        return object;
    }

    @Deprecated
    Bitmap createUserBadgedIconBitmap(Drawable object, UserHandle userHandle) {
        block2 : {
            float[] arrf = new float[1];
            Drawable drawable2 = object;
            if (object == null) {
                drawable2 = SimpleIconFactory.getFullResDefaultActivityIcon(this.mFillResIconDpi);
            }
            drawable2 = this.normalizeAndWrapToAdaptiveIcon(drawable2, null, arrf);
            object = this.createIconBitmap(drawable2, arrf[0]);
            if (drawable2 instanceof AdaptiveIconDrawable) {
                this.mCanvas.setBitmap((Bitmap)object);
                this.recreateIcon(Bitmap.createBitmap((Bitmap)object), this.mCanvas);
                this.mCanvas.setBitmap(null);
            }
            if (userHandle == null) break block2;
            object = new FixedSizeBitmapDrawable((Bitmap)object);
            object = (object = this.mPm.getUserBadgedIcon((Drawable)object, userHandle)) instanceof BitmapDrawable ? ((BitmapDrawable)object).getBitmap() : this.createIconBitmap((Drawable)object, 1.0f);
        }
        return object;
    }

    @Deprecated
    public void recycle() {
        this.setWrapperBackgroundColor(-1);
        sPool.release(this);
    }

    @Deprecated
    void setWrapperBackgroundColor(int n) {
        if (Color.alpha(n) < 255) {
            n = -1;
        }
        this.mWrapperBackgroundColor = n;
    }

    public static class FixedScaleDrawable
    extends DrawableWrapper {
        private static final float LEGACY_ICON_SCALE = 0.46669f;
        private float mScaleX = 0.46669f;
        private float mScaleY = 0.46669f;

        public FixedScaleDrawable() {
            super(new ColorDrawable());
        }

        @Override
        public void draw(Canvas canvas) {
            int n = canvas.save();
            canvas.scale(this.mScaleX, this.mScaleY, this.getBounds().exactCenterX(), this.getBounds().exactCenterY());
            super.draw(canvas);
            canvas.restoreToCount(n);
        }

        @Override
        public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) {
        }

        @Override
        public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        }

        public void setScale(float f) {
            float f2 = this.getIntrinsicHeight();
            float f3 = this.getIntrinsicWidth();
            this.mScaleX = f * 0.46669f;
            this.mScaleY = 0.46669f * f;
            if (f2 > f3 && f3 > 0.0f) {
                this.mScaleX *= f3 / f2;
            } else if (f3 > f2 && f2 > 0.0f) {
                this.mScaleY *= f2 / f3;
            }
        }
    }

    private static class FixedSizeBitmapDrawable
    extends BitmapDrawable {
        FixedSizeBitmapDrawable(Bitmap bitmap) {
            super(null, bitmap);
        }

        @Override
        public int getIntrinsicHeight() {
            return this.getBitmap().getWidth();
        }

        @Override
        public int getIntrinsicWidth() {
            return this.getBitmap().getWidth();
        }
    }

}

