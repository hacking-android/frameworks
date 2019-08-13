/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.app.ActivityThread;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.graphics.drawable.LayerDrawable;
import android.util.SparseArray;

public final class LauncherIcons {
    private static final int AMBIENT_SHADOW_ALPHA = 30;
    private static final float ICON_SIZE_BLUR_FACTOR = 0.010416667f;
    private static final float ICON_SIZE_KEY_SHADOW_DELTA_FACTOR = 0.020833334f;
    private static final int KEY_SHADOW_ALPHA = 61;
    private final int mIconSize;
    private final Resources mRes;
    private final SparseArray<Bitmap> mShadowCache = new SparseArray();

    public LauncherIcons(Context context) {
        this.mRes = context.getResources();
        this.mIconSize = this.mRes.getDimensionPixelSize(17104896);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Bitmap getShadowBitmap(AdaptiveIconDrawable object) {
        Object object2;
        int n = Math.max(this.mIconSize, ((AdaptiveIconDrawable)object).getIntrinsicHeight());
        Object object3 = this.mShadowCache;
        synchronized (object3) {
            object2 = this.mShadowCache.get(n);
            if (object2 != null) {
                return object2;
            }
        }
        ((Drawable)object).setBounds(0, 0, n, n);
        float f = (float)n * 0.010416667f;
        float f2 = (float)n * 0.020833334f;
        int n2 = (int)((float)n + f * 2.0f + f2);
        object3 = Bitmap.createBitmap(n2, n2, Bitmap.Config.ARGB_8888);
        object2 = new Canvas((Bitmap)object3);
        ((Canvas)object2).translate(f2 / 2.0f + f, f);
        Paint paint = new Paint(1);
        paint.setColor(0);
        paint.setShadowLayer(f, 0.0f, 0.0f, 503316480);
        ((Canvas)object2).drawPath(((AdaptiveIconDrawable)object).getIconMask(), paint);
        ((Canvas)object2).translate(0.0f, f2);
        paint.setShadowLayer(f, 0.0f, 0.0f, 1023410176);
        ((Canvas)object2).drawPath(((AdaptiveIconDrawable)object).getIconMask(), paint);
        ((Canvas)object2).setBitmap(null);
        object = this.mShadowCache;
        synchronized (object) {
            this.mShadowCache.put(n, (Bitmap)object3);
            return object3;
        }
    }

    public Drawable getBadgeDrawable(int n, int n2) {
        return this.getBadgedDrawable(null, n, n2);
    }

    public Drawable getBadgedDrawable(Drawable arrdrawable, int n, int n2) {
        Object object = ActivityThread.currentActivityThread().getApplication().getResources();
        Drawable drawable2 = ((Resources)object).getDrawable(17302368);
        Drawable drawable3 = ((Resources)object).getDrawable(17302367).getConstantState().newDrawable().mutate();
        object = ((Resources)object).getDrawable(n);
        ((Drawable)object).setTint(n2);
        arrdrawable = arrdrawable == null ? new Drawable[]{drawable2, drawable3, object} : new Drawable[]{arrdrawable, drawable2, drawable3, object};
        return new LayerDrawable(arrdrawable);
    }

    public Drawable wrapIconDrawableWithShadow(Drawable drawable2) {
        if (!(drawable2 instanceof AdaptiveIconDrawable)) {
            return drawable2;
        }
        return new ShadowDrawable(this.getShadowBitmap((AdaptiveIconDrawable)drawable2), drawable2);
    }

    private static class ShadowDrawable
    extends DrawableWrapper {
        final MyConstantState mState;

        public ShadowDrawable(Bitmap bitmap, Drawable drawable2) {
            super(drawable2);
            this.mState = new MyConstantState(bitmap, drawable2.getConstantState());
        }

        ShadowDrawable(MyConstantState myConstantState) {
            super(myConstantState.mChildState.newDrawable());
            this.mState = myConstantState;
        }

        @Override
        public void draw(Canvas canvas) {
            Rect rect = this.getBounds();
            canvas.drawBitmap(this.mState.mShadow, null, rect, this.mState.mPaint);
            canvas.save();
            canvas.translate((float)rect.width() * 0.9599999f * 0.020833334f, (float)rect.height() * 0.9599999f * 0.010416667f);
            canvas.scale(0.9599999f, 0.9599999f);
            super.draw(canvas);
            canvas.restore();
        }

        @Override
        public Drawable.ConstantState getConstantState() {
            return this.mState;
        }

        private static class MyConstantState
        extends Drawable.ConstantState {
            final Drawable.ConstantState mChildState;
            final Paint mPaint = new Paint(2);
            final Bitmap mShadow;

            MyConstantState(Bitmap bitmap, Drawable.ConstantState constantState) {
                this.mShadow = bitmap;
                this.mChildState = constantState;
            }

            @Override
            public int getChangingConfigurations() {
                return this.mChildState.getChangingConfigurations();
            }

            @Override
            public Drawable newDrawable() {
                return new ShadowDrawable(this);
            }
        }

    }

}

