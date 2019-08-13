/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Parcelable;

public class NinePatch {
    @UnsupportedAppUsage
    private final Bitmap mBitmap;
    @UnsupportedAppUsage
    public long mNativeChunk;
    private Paint mPaint;
    private String mSrcName;

    public NinePatch(Bitmap bitmap, byte[] arrby) {
        this(bitmap, arrby, null);
    }

    public NinePatch(Bitmap bitmap, byte[] arrby, String string2) {
        this.mBitmap = bitmap;
        this.mSrcName = string2;
        this.mNativeChunk = NinePatch.validateNinePatchChunk(arrby);
    }

    public NinePatch(NinePatch ninePatch) {
        this.mBitmap = ninePatch.mBitmap;
        this.mSrcName = ninePatch.mSrcName;
        Paint paint = ninePatch.mPaint;
        if (paint != null) {
            this.mPaint = new Paint(paint);
        }
        this.mNativeChunk = ninePatch.mNativeChunk;
    }

    public static native boolean isNinePatchChunk(byte[] var0);

    private static native void nativeFinalize(long var0);

    private static native long nativeGetTransparentRegion(long var0, long var2, Rect var4);

    private static native long validateNinePatchChunk(byte[] var0);

    public void draw(Canvas canvas, Rect rect) {
        canvas.drawPatch(this, rect, this.mPaint);
    }

    public void draw(Canvas canvas, Rect rect, Paint paint) {
        canvas.drawPatch(this, rect, paint);
    }

    public void draw(Canvas canvas, RectF rectF) {
        canvas.drawPatch(this, rectF, this.mPaint);
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mNativeChunk != 0L) {
                NinePatch.nativeFinalize(this.mNativeChunk);
                this.mNativeChunk = 0L;
            }
            return;
        }
        finally {
            super.finalize();
        }
    }

    public Bitmap getBitmap() {
        return this.mBitmap;
    }

    public int getDensity() {
        return this.mBitmap.mDensity;
    }

    public int getHeight() {
        return this.mBitmap.getHeight();
    }

    public String getName() {
        return this.mSrcName;
    }

    public Paint getPaint() {
        return this.mPaint;
    }

    public final Region getTransparentRegion(Rect parcelable) {
        long l = NinePatch.nativeGetTransparentRegion(this.mBitmap.getNativeInstance(), this.mNativeChunk, parcelable);
        parcelable = l != 0L ? new Region(l) : null;
        return parcelable;
    }

    public int getWidth() {
        return this.mBitmap.getWidth();
    }

    public final boolean hasAlpha() {
        return this.mBitmap.hasAlpha();
    }

    public void setPaint(Paint paint) {
        this.mPaint = paint;
    }

    public static class InsetStruct {
        public final Rect opticalRect;
        public final float outlineAlpha;
        public final float outlineRadius;
        public final Rect outlineRect;

        @UnsupportedAppUsage
        InsetStruct(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, float f, int n9, float f2) {
            this.opticalRect = new Rect(n, n2, n3, n4);
            this.opticalRect.scale(f2);
            this.outlineRect = InsetStruct.scaleInsets(n5, n6, n7, n8, f2);
            this.outlineRadius = f * f2;
            this.outlineAlpha = (float)n9 / 255.0f;
        }

        public static Rect scaleInsets(int n, int n2, int n3, int n4, float f) {
            if (f == 1.0f) {
                return new Rect(n, n2, n3, n4);
            }
            Rect rect = new Rect();
            rect.left = (int)Math.ceil((float)n * f);
            rect.top = (int)Math.ceil((float)n2 * f);
            rect.right = (int)Math.ceil((float)n3 * f);
            rect.bottom = (int)Math.ceil((float)n4 * f);
            return rect;
        }
    }

}

