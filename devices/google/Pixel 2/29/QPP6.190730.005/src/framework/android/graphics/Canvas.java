/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.CriticalNative
 *  dalvik.annotation.optimization.FastNative
 *  libcore.util.NativeAllocationRegistry
 */
package android.graphics;

import android.annotation.UnsupportedAppUsage;
import android.graphics.BaseCanvas;
import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.DrawFilter;
import android.graphics.Matrix;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RenderNode;
import android.graphics.text.MeasuredText;
import dalvik.annotation.optimization.CriticalNative;
import dalvik.annotation.optimization.FastNative;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.microedition.khronos.opengles.GL;
import libcore.util.NativeAllocationRegistry;

public class Canvas
extends BaseCanvas {
    public static final int ALL_SAVE_FLAG = 31;
    public static final int CLIP_SAVE_FLAG = 2;
    public static final int CLIP_TO_LAYER_SAVE_FLAG = 16;
    public static final int FULL_COLOR_LAYER_SAVE_FLAG = 8;
    public static final int HAS_ALPHA_LAYER_SAVE_FLAG = 4;
    public static final int MATRIX_SAVE_FLAG = 1;
    private static final int MAXMIMUM_BITMAP_SIZE = 32766;
    public static boolean sCompatibilityRestore;
    public static boolean sCompatibilitySetBitmap;
    private static int sCompatiblityVersion;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=117521088L)
    private Bitmap mBitmap;
    private DrawFilter mDrawFilter;
    private Runnable mFinalizer;

    static {
        sCompatiblityVersion = 0;
        sCompatibilityRestore = false;
        sCompatibilitySetBitmap = false;
    }

    public Canvas() {
        if (!this.isHardwareAccelerated()) {
            this.mNativeCanvasWrapper = Canvas.nInitRaster(0L);
            this.mFinalizer = NoImagePreloadHolder.sRegistry.registerNativeAllocation((Object)this, this.mNativeCanvasWrapper);
        } else {
            this.mFinalizer = null;
        }
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public Canvas(long l) {
        if (l != 0L) {
            this.mNativeCanvasWrapper = l;
            this.mFinalizer = NoImagePreloadHolder.sRegistry.registerNativeAllocation((Object)this, this.mNativeCanvasWrapper);
            this.mDensity = Bitmap.getDefaultDensity();
            return;
        }
        throw new IllegalStateException();
    }

    public Canvas(Bitmap bitmap) {
        if (bitmap.isMutable()) {
            this.throwIfCannotDraw(bitmap);
            this.mNativeCanvasWrapper = Canvas.nInitRaster(bitmap.getNativeInstance());
            this.mFinalizer = NoImagePreloadHolder.sRegistry.registerNativeAllocation((Object)this, this.mNativeCanvasWrapper);
            this.mBitmap = bitmap;
            this.mDensity = bitmap.mDensity;
            return;
        }
        throw new IllegalStateException("Immutable bitmap passed to Canvas constructor");
    }

    static /* synthetic */ long access$000() {
        return Canvas.nGetNativeFinalizer();
    }

    private static void checkValidClipOp(Region.Op op) {
        if (sCompatiblityVersion >= 28 && op != Region.Op.INTERSECT && op != Region.Op.DIFFERENCE) {
            throw new IllegalArgumentException("Invalid Region.Op - only INTERSECT and DIFFERENCE are allowed");
        }
    }

    private static void checkValidSaveFlags(int n) {
        if (sCompatiblityVersion >= 28 && n != 31) {
            throw new IllegalArgumentException("Invalid Layer Save Flag - only ALL_SAVE_FLAGS is allowed");
        }
    }

    @UnsupportedAppUsage
    public static void freeCaches() {
        Canvas.nFreeCaches();
    }

    @UnsupportedAppUsage
    public static void freeTextLayoutCaches() {
        Canvas.nFreeTextLayoutCaches();
    }

    @CriticalNative
    private static native boolean nClipPath(long var0, long var2, int var4);

    @CriticalNative
    private static native boolean nClipRect(long var0, float var2, float var3, float var4, float var5, int var6);

    @CriticalNative
    private static native void nConcat(long var0, long var2);

    private static native void nFreeCaches();

    private static native void nFreeTextLayoutCaches();

    @FastNative
    private static native boolean nGetClipBounds(long var0, Rect var2);

    @CriticalNative
    private static native int nGetHeight(long var0);

    @CriticalNative
    private static native void nGetMatrix(long var0, long var2);

    private static native long nGetNativeFinalizer();

    @CriticalNative
    private static native int nGetSaveCount(long var0);

    @CriticalNative
    private static native int nGetWidth(long var0);

    @FastNative
    private static native long nInitRaster(long var0);

    @CriticalNative
    private static native boolean nIsOpaque(long var0);

    @CriticalNative
    private static native boolean nQuickReject(long var0, float var2, float var3, float var4, float var5);

    @CriticalNative
    private static native boolean nQuickReject(long var0, long var2);

    @CriticalNative
    private static native boolean nRestore(long var0);

    @CriticalNative
    private static native void nRestoreToCount(long var0, int var2);

    @CriticalNative
    private static native void nRestoreUnclippedLayer(long var0, int var2, long var3);

    @CriticalNative
    private static native void nRotate(long var0, float var2);

    @CriticalNative
    private static native int nSave(long var0, int var2);

    @CriticalNative
    private static native int nSaveLayer(long var0, float var2, float var3, float var4, float var5, long var6, int var8);

    @CriticalNative
    private static native int nSaveLayerAlpha(long var0, float var2, float var3, float var4, float var5, int var6, int var7);

    @CriticalNative
    private static native int nSaveUnclippedLayer(long var0, int var2, int var3, int var4, int var5);

    @CriticalNative
    private static native void nScale(long var0, float var2, float var3);

    @FastNative
    private static native void nSetBitmap(long var0, long var2);

    private static native void nSetCompatibilityVersion(int var0);

    @CriticalNative
    private static native void nSetDrawFilter(long var0, long var2);

    @CriticalNative
    private static native void nSetMatrix(long var0, long var2);

    @CriticalNative
    private static native void nSkew(long var0, float var2, float var3);

    @CriticalNative
    private static native void nTranslate(long var0, float var2, float var3);

    public static void setCompatibilityVersion(int n) {
        sCompatiblityVersion = n;
        Canvas.nSetCompatibilityVersion(n);
    }

    public boolean clipOutPath(Path path) {
        return this.clipPath(path, Region.Op.DIFFERENCE);
    }

    public boolean clipOutRect(float f, float f2, float f3, float f4) {
        return Canvas.nClipRect(this.mNativeCanvasWrapper, f, f2, f3, f4, Region.Op.DIFFERENCE.nativeInt);
    }

    public boolean clipOutRect(int n, int n2, int n3, int n4) {
        return Canvas.nClipRect(this.mNativeCanvasWrapper, n, n2, n3, n4, Region.Op.DIFFERENCE.nativeInt);
    }

    public boolean clipOutRect(Rect rect) {
        return Canvas.nClipRect(this.mNativeCanvasWrapper, rect.left, rect.top, rect.right, rect.bottom, Region.Op.DIFFERENCE.nativeInt);
    }

    public boolean clipOutRect(RectF rectF) {
        return Canvas.nClipRect(this.mNativeCanvasWrapper, rectF.left, rectF.top, rectF.right, rectF.bottom, Region.Op.DIFFERENCE.nativeInt);
    }

    public boolean clipPath(Path path) {
        return this.clipPath(path, Region.Op.INTERSECT);
    }

    @Deprecated
    public boolean clipPath(Path path, Region.Op op) {
        Canvas.checkValidClipOp(op);
        return Canvas.nClipPath(this.mNativeCanvasWrapper, path.readOnlyNI(), op.nativeInt);
    }

    public boolean clipRect(float f, float f2, float f3, float f4) {
        return Canvas.nClipRect(this.mNativeCanvasWrapper, f, f2, f3, f4, Region.Op.INTERSECT.nativeInt);
    }

    @Deprecated
    public boolean clipRect(float f, float f2, float f3, float f4, Region.Op op) {
        Canvas.checkValidClipOp(op);
        return Canvas.nClipRect(this.mNativeCanvasWrapper, f, f2, f3, f4, op.nativeInt);
    }

    public boolean clipRect(int n, int n2, int n3, int n4) {
        return Canvas.nClipRect(this.mNativeCanvasWrapper, n, n2, n3, n4, Region.Op.INTERSECT.nativeInt);
    }

    public boolean clipRect(Rect rect) {
        return Canvas.nClipRect(this.mNativeCanvasWrapper, rect.left, rect.top, rect.right, rect.bottom, Region.Op.INTERSECT.nativeInt);
    }

    @Deprecated
    public boolean clipRect(Rect rect, Region.Op op) {
        Canvas.checkValidClipOp(op);
        return Canvas.nClipRect(this.mNativeCanvasWrapper, rect.left, rect.top, rect.right, rect.bottom, op.nativeInt);
    }

    public boolean clipRect(RectF rectF) {
        return Canvas.nClipRect(this.mNativeCanvasWrapper, rectF.left, rectF.top, rectF.right, rectF.bottom, Region.Op.INTERSECT.nativeInt);
    }

    @Deprecated
    public boolean clipRect(RectF rectF, Region.Op op) {
        Canvas.checkValidClipOp(op);
        return Canvas.nClipRect(this.mNativeCanvasWrapper, rectF.left, rectF.top, rectF.right, rectF.bottom, op.nativeInt);
    }

    public boolean clipRectUnion(Rect rect) {
        return Canvas.nClipRect(this.mNativeCanvasWrapper, rect.left, rect.top, rect.right, rect.bottom, Region.Op.UNION.nativeInt);
    }

    @Deprecated
    public boolean clipRegion(Region region) {
        return false;
    }

    @Deprecated
    public boolean clipRegion(Region region, Region.Op op) {
        return false;
    }

    public void concat(Matrix matrix) {
        if (matrix != null) {
            Canvas.nConcat(this.mNativeCanvasWrapper, matrix.native_instance);
        }
    }

    public void disableZ() {
    }

    @Override
    public void drawARGB(int n, int n2, int n3, int n4) {
        super.drawARGB(n, n2, n3, n4);
    }

    @Override
    public void drawArc(float f, float f2, float f3, float f4, float f5, float f6, boolean bl, Paint paint) {
        super.drawArc(f, f2, f3, f4, f5, f6, bl, paint);
    }

    @Override
    public void drawArc(RectF rectF, float f, float f2, boolean bl, Paint paint) {
        super.drawArc(rectF, f, f2, bl, paint);
    }

    @Override
    public void drawBitmap(Bitmap bitmap, float f, float f2, Paint paint) {
        super.drawBitmap(bitmap, f, f2, paint);
    }

    @Override
    public void drawBitmap(Bitmap bitmap, Matrix matrix, Paint paint) {
        super.drawBitmap(bitmap, matrix, paint);
    }

    @Override
    public void drawBitmap(Bitmap bitmap, Rect rect, Rect rect2, Paint paint) {
        super.drawBitmap(bitmap, rect, rect2, paint);
    }

    @Override
    public void drawBitmap(Bitmap bitmap, Rect rect, RectF rectF, Paint paint) {
        super.drawBitmap(bitmap, rect, rectF, paint);
    }

    @Deprecated
    @Override
    public void drawBitmap(int[] arrn, int n, int n2, float f, float f2, int n3, int n4, boolean bl, Paint paint) {
        super.drawBitmap(arrn, n, n2, f, f2, n3, n4, bl, paint);
    }

    @Deprecated
    @Override
    public void drawBitmap(int[] arrn, int n, int n2, int n3, int n4, int n5, int n6, boolean bl, Paint paint) {
        super.drawBitmap(arrn, n, n2, n3, n4, n5, n6, bl, paint);
    }

    @Override
    public void drawBitmapMesh(Bitmap bitmap, int n, int n2, float[] arrf, int n3, int[] arrn, int n4, Paint paint) {
        super.drawBitmapMesh(bitmap, n, n2, arrf, n3, arrn, n4, paint);
    }

    @Override
    public void drawCircle(float f, float f2, float f3, Paint paint) {
        super.drawCircle(f, f2, f3, paint);
    }

    @Override
    public void drawColor(int n) {
        super.drawColor(n);
    }

    @Override
    public void drawColor(int n, BlendMode blendMode) {
        super.drawColor(n, blendMode);
    }

    @Override
    public void drawColor(int n, PorterDuff.Mode mode) {
        super.drawColor(n, mode);
    }

    public void drawColor(long l) {
        super.drawColor(l, BlendMode.SRC_OVER);
    }

    @Override
    public void drawColor(long l, BlendMode blendMode) {
        super.drawColor(l, blendMode);
    }

    @Override
    public void drawDoubleRoundRect(RectF rectF, float f, float f2, RectF rectF2, float f3, float f4, Paint paint) {
        super.drawDoubleRoundRect(rectF, f, f2, rectF2, f3, f4, paint);
    }

    @Override
    public void drawDoubleRoundRect(RectF rectF, float[] arrf, RectF rectF2, float[] arrf2, Paint paint) {
        super.drawDoubleRoundRect(rectF, arrf, rectF2, arrf2, paint);
    }

    @Override
    public void drawLine(float f, float f2, float f3, float f4, Paint paint) {
        super.drawLine(f, f2, f3, f4, paint);
    }

    @Override
    public void drawLines(float[] arrf, int n, int n2, Paint paint) {
        super.drawLines(arrf, n, n2, paint);
    }

    @Override
    public void drawLines(float[] arrf, Paint paint) {
        super.drawLines(arrf, paint);
    }

    @Override
    public void drawOval(float f, float f2, float f3, float f4, Paint paint) {
        super.drawOval(f, f2, f3, f4, paint);
    }

    @Override
    public void drawOval(RectF rectF, Paint paint) {
        super.drawOval(rectF, paint);
    }

    @Override
    public void drawPaint(Paint paint) {
        super.drawPaint(paint);
    }

    @Override
    public void drawPatch(NinePatch ninePatch, Rect rect, Paint paint) {
        super.drawPatch(ninePatch, rect, paint);
    }

    @Override
    public void drawPatch(NinePatch ninePatch, RectF rectF, Paint paint) {
        super.drawPatch(ninePatch, rectF, paint);
    }

    @Override
    public void drawPath(Path path, Paint paint) {
        super.drawPath(path, paint);
    }

    public void drawPicture(Picture picture) {
        picture.endRecording();
        int n = this.save();
        picture.draw(this);
        this.restoreToCount(n);
    }

    public void drawPicture(Picture picture, Rect rect) {
        this.save();
        this.translate(rect.left, rect.top);
        if (picture.getWidth() > 0 && picture.getHeight() > 0) {
            this.scale((float)rect.width() / (float)picture.getWidth(), (float)rect.height() / (float)picture.getHeight());
        }
        this.drawPicture(picture);
        this.restore();
    }

    public void drawPicture(Picture picture, RectF rectF) {
        this.save();
        this.translate(rectF.left, rectF.top);
        if (picture.getWidth() > 0 && picture.getHeight() > 0) {
            this.scale(rectF.width() / (float)picture.getWidth(), rectF.height() / (float)picture.getHeight());
        }
        this.drawPicture(picture);
        this.restore();
    }

    @Override
    public void drawPoint(float f, float f2, Paint paint) {
        super.drawPoint(f, f2, paint);
    }

    @Override
    public void drawPoints(float[] arrf, int n, int n2, Paint paint) {
        super.drawPoints(arrf, n, n2, paint);
    }

    @Override
    public void drawPoints(float[] arrf, Paint paint) {
        super.drawPoints(arrf, paint);
    }

    @Deprecated
    @Override
    public void drawPosText(String string2, float[] arrf, Paint paint) {
        super.drawPosText(string2, arrf, paint);
    }

    @Deprecated
    @Override
    public void drawPosText(char[] arrc, int n, int n2, float[] arrf, Paint paint) {
        super.drawPosText(arrc, n, n2, arrf, paint);
    }

    @Override
    public void drawRGB(int n, int n2, int n3) {
        super.drawRGB(n, n2, n3);
    }

    @Override
    public void drawRect(float f, float f2, float f3, float f4, Paint paint) {
        super.drawRect(f, f2, f3, f4, paint);
    }

    @Override
    public void drawRect(Rect rect, Paint paint) {
        super.drawRect(rect, paint);
    }

    @Override
    public void drawRect(RectF rectF, Paint paint) {
        super.drawRect(rectF, paint);
    }

    public void drawRenderNode(RenderNode renderNode) {
        throw new IllegalArgumentException("Software rendering doesn't support drawRenderNode");
    }

    @Override
    public void drawRoundRect(float f, float f2, float f3, float f4, float f5, float f6, Paint paint) {
        super.drawRoundRect(f, f2, f3, f4, f5, f6, paint);
    }

    @Override
    public void drawRoundRect(RectF rectF, float f, float f2, Paint paint) {
        super.drawRoundRect(rectF, f, f2, paint);
    }

    @Override
    public void drawText(CharSequence charSequence, int n, int n2, float f, float f2, Paint paint) {
        super.drawText(charSequence, n, n2, f, f2, paint);
    }

    @Override
    public void drawText(String string2, float f, float f2, Paint paint) {
        super.drawText(string2, f, f2, paint);
    }

    @Override
    public void drawText(String string2, int n, int n2, float f, float f2, Paint paint) {
        super.drawText(string2, n, n2, f, f2, paint);
    }

    @Override
    public void drawText(char[] arrc, int n, int n2, float f, float f2, Paint paint) {
        super.drawText(arrc, n, n2, f, f2, paint);
    }

    @Override
    public void drawTextOnPath(String string2, Path path, float f, float f2, Paint paint) {
        super.drawTextOnPath(string2, path, f, f2, paint);
    }

    @Override
    public void drawTextOnPath(char[] arrc, int n, int n2, Path path, float f, float f2, Paint paint) {
        super.drawTextOnPath(arrc, n, n2, path, f, f2, paint);
    }

    @Override
    public void drawTextRun(MeasuredText measuredText, int n, int n2, int n3, int n4, float f, float f2, boolean bl, Paint paint) {
        super.drawTextRun(measuredText, n, n2, n3, n4, f, f2, bl, paint);
    }

    @Override
    public void drawTextRun(CharSequence charSequence, int n, int n2, int n3, int n4, float f, float f2, boolean bl, Paint paint) {
        super.drawTextRun(charSequence, n, n2, n3, n4, f, f2, bl, paint);
    }

    @Override
    public void drawTextRun(char[] arrc, int n, int n2, int n3, int n4, float f, float f2, boolean bl, Paint paint) {
        super.drawTextRun(arrc, n, n2, n3, n4, f, f2, bl, paint);
    }

    @Override
    public void drawVertices(VertexMode vertexMode, int n, float[] arrf, int n2, float[] arrf2, int n3, int[] arrn, int n4, short[] arrs, int n5, int n6, Paint paint) {
        super.drawVertices(vertexMode, n, arrf, n2, arrf2, n3, arrn, n4, arrs, n5, n6, paint);
    }

    public void enableZ() {
    }

    public final Rect getClipBounds() {
        Rect rect = new Rect();
        this.getClipBounds(rect);
        return rect;
    }

    public boolean getClipBounds(Rect rect) {
        return Canvas.nGetClipBounds(this.mNativeCanvasWrapper, rect);
    }

    public int getDensity() {
        return this.mDensity;
    }

    public DrawFilter getDrawFilter() {
        return this.mDrawFilter;
    }

    @Deprecated
    @UnsupportedAppUsage
    protected GL getGL() {
        return null;
    }

    public int getHeight() {
        return Canvas.nGetHeight(this.mNativeCanvasWrapper);
    }

    @Deprecated
    public final Matrix getMatrix() {
        Matrix matrix = new Matrix();
        this.getMatrix(matrix);
        return matrix;
    }

    @Deprecated
    public void getMatrix(Matrix matrix) {
        Canvas.nGetMatrix(this.mNativeCanvasWrapper, matrix.native_instance);
    }

    public int getMaximumBitmapHeight() {
        return 32766;
    }

    public int getMaximumBitmapWidth() {
        return 32766;
    }

    @UnsupportedAppUsage
    public long getNativeCanvasWrapper() {
        return this.mNativeCanvasWrapper;
    }

    public int getSaveCount() {
        return Canvas.nGetSaveCount(this.mNativeCanvasWrapper);
    }

    public int getWidth() {
        return Canvas.nGetWidth(this.mNativeCanvasWrapper);
    }

    @Deprecated
    public void insertInorderBarrier() {
        this.disableZ();
    }

    @Deprecated
    public void insertReorderBarrier() {
        this.enableZ();
    }

    @Override
    public boolean isHardwareAccelerated() {
        return false;
    }

    public boolean isOpaque() {
        return Canvas.nIsOpaque(this.mNativeCanvasWrapper);
    }

    public boolean isRecordingFor(Object object) {
        return false;
    }

    public boolean quickReject(float f, float f2, float f3, float f4, EdgeType edgeType) {
        return Canvas.nQuickReject(this.mNativeCanvasWrapper, f, f2, f3, f4);
    }

    public boolean quickReject(Path path, EdgeType edgeType) {
        return Canvas.nQuickReject(this.mNativeCanvasWrapper, path.readOnlyNI());
    }

    public boolean quickReject(RectF rectF, EdgeType edgeType) {
        return Canvas.nQuickReject(this.mNativeCanvasWrapper, rectF.left, rectF.top, rectF.right, rectF.bottom);
    }

    @UnsupportedAppUsage
    public void release() {
        this.mNativeCanvasWrapper = 0L;
        Runnable runnable = this.mFinalizer;
        if (runnable != null) {
            runnable.run();
            this.mFinalizer = null;
        }
    }

    public void restore() {
        if (!(Canvas.nRestore(this.mNativeCanvasWrapper) || sCompatibilityRestore && this.isHardwareAccelerated())) {
            throw new IllegalStateException("Underflow in restore - more restores than saves");
        }
    }

    public void restoreToCount(int n) {
        int n2 = n;
        if (n < 1) {
            if (sCompatibilityRestore && this.isHardwareAccelerated()) {
                n2 = 1;
            } else {
                throw new IllegalArgumentException("Underflow in restoreToCount - more restores than saves");
            }
        }
        Canvas.nRestoreToCount(this.mNativeCanvasWrapper, n2);
    }

    public void restoreUnclippedLayer(int n, Paint paint) {
        Canvas.nRestoreUnclippedLayer(this.mNativeCanvasWrapper, n, paint.getNativeInstance());
    }

    public void rotate(float f) {
        if (f == 0.0f) {
            return;
        }
        Canvas.nRotate(this.mNativeCanvasWrapper, f);
    }

    public final void rotate(float f, float f2, float f3) {
        if (f == 0.0f) {
            return;
        }
        this.translate(f2, f3);
        this.rotate(f);
        this.translate(-f2, -f3);
    }

    public int save() {
        return Canvas.nSave(this.mNativeCanvasWrapper, 3);
    }

    public int save(int n) {
        return Canvas.nSave(this.mNativeCanvasWrapper, n);
    }

    public int saveLayer(float f, float f2, float f3, float f4, Paint paint) {
        return this.saveLayer(f, f2, f3, f4, paint, 31);
    }

    public int saveLayer(float f, float f2, float f3, float f4, Paint paint, int n) {
        Canvas.checkValidSaveFlags(n);
        long l = this.mNativeCanvasWrapper;
        long l2 = paint != null ? paint.getNativeInstance() : 0L;
        return Canvas.nSaveLayer(l, f, f2, f3, f4, l2, 31);
    }

    public int saveLayer(RectF rectF, Paint paint) {
        return this.saveLayer(rectF, paint, 31);
    }

    public int saveLayer(RectF rectF, Paint paint, int n) {
        RectF rectF2 = rectF;
        if (rectF == null) {
            rectF2 = new RectF(this.getClipBounds());
        }
        Canvas.checkValidSaveFlags(n);
        return this.saveLayer(rectF2.left, rectF2.top, rectF2.right, rectF2.bottom, paint, 31);
    }

    public int saveLayerAlpha(float f, float f2, float f3, float f4, int n) {
        return this.saveLayerAlpha(f, f2, f3, f4, n, 31);
    }

    public int saveLayerAlpha(float f, float f2, float f3, float f4, int n, int n2) {
        Canvas.checkValidSaveFlags(n2);
        n = Math.min(255, Math.max(0, n));
        return Canvas.nSaveLayerAlpha(this.mNativeCanvasWrapper, f, f2, f3, f4, n, 31);
    }

    public int saveLayerAlpha(RectF rectF, int n) {
        return this.saveLayerAlpha(rectF, n, 31);
    }

    public int saveLayerAlpha(RectF rectF, int n, int n2) {
        RectF rectF2 = rectF;
        if (rectF == null) {
            rectF2 = new RectF(this.getClipBounds());
        }
        Canvas.checkValidSaveFlags(n2);
        return this.saveLayerAlpha(rectF2.left, rectF2.top, rectF2.right, rectF2.bottom, n, 31);
    }

    public int saveUnclippedLayer(int n, int n2, int n3, int n4) {
        return Canvas.nSaveUnclippedLayer(this.mNativeCanvasWrapper, n, n2, n3, n4);
    }

    public void scale(float f, float f2) {
        if (f == 1.0f && f2 == 1.0f) {
            return;
        }
        Canvas.nScale(this.mNativeCanvasWrapper, f, f2);
    }

    public final void scale(float f, float f2, float f3, float f4) {
        if (f == 1.0f && f2 == 1.0f) {
            return;
        }
        this.translate(f3, f4);
        this.scale(f, f2);
        this.translate(-f3, -f4);
    }

    public void setBitmap(Bitmap bitmap) {
        block6 : {
            block9 : {
                Matrix matrix;
                block8 : {
                    block7 : {
                        Matrix matrix2;
                        if (this.isHardwareAccelerated()) break block6;
                        matrix = matrix2 = null;
                        if (bitmap != null) {
                            matrix = matrix2;
                            if (sCompatibilitySetBitmap) {
                                matrix = this.getMatrix();
                            }
                        }
                        if (bitmap != null) break block7;
                        Canvas.nSetBitmap(this.mNativeCanvasWrapper, 0L);
                        this.mDensity = 0;
                        break block8;
                    }
                    if (!bitmap.isMutable()) break block9;
                    this.throwIfCannotDraw(bitmap);
                    Canvas.nSetBitmap(this.mNativeCanvasWrapper, bitmap.getNativeInstance());
                    this.mDensity = bitmap.mDensity;
                }
                if (matrix != null) {
                    this.setMatrix(matrix);
                }
                this.mBitmap = bitmap;
                return;
            }
            throw new IllegalStateException();
        }
        throw new RuntimeException("Can't set a bitmap device on a HW accelerated canvas");
    }

    public void setDensity(int n) {
        Bitmap bitmap = this.mBitmap;
        if (bitmap != null) {
            bitmap.setDensity(n);
        }
        this.mDensity = n;
    }

    public void setDrawFilter(DrawFilter drawFilter) {
        long l = 0L;
        if (drawFilter != null) {
            l = drawFilter.mNativeInt;
        }
        this.mDrawFilter = drawFilter;
        Canvas.nSetDrawFilter(this.mNativeCanvasWrapper, l);
    }

    public void setMatrix(Matrix matrix) {
        long l = this.mNativeCanvasWrapper;
        long l2 = matrix == null ? 0L : matrix.native_instance;
        Canvas.nSetMatrix(l, l2);
    }

    @UnsupportedAppUsage
    public void setScreenDensity(int n) {
        this.mScreenDensity = n;
    }

    public void skew(float f, float f2) {
        if (f == 0.0f && f2 == 0.0f) {
            return;
        }
        Canvas.nSkew(this.mNativeCanvasWrapper, f, f2);
    }

    public void translate(float f, float f2) {
        if (f == 0.0f && f2 == 0.0f) {
            return;
        }
        Canvas.nTranslate(this.mNativeCanvasWrapper, f, f2);
    }

    public static enum EdgeType {
        BW(0),
        AA(1);
        
        public final int nativeInt;

        private EdgeType(int n2) {
            this.nativeInt = n2;
        }
    }

    private static class NoImagePreloadHolder {
        public static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced((ClassLoader)Canvas.class.getClassLoader(), (long)Canvas.access$000());

        private NoImagePreloadHolder() {
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Saveflags {
    }

    public static enum VertexMode {
        TRIANGLES(0),
        TRIANGLE_STRIP(1),
        TRIANGLE_FAN(2);
        
        public final int nativeInt;

        private VertexMode(int n2) {
            this.nativeInt = n2;
        }
    }

}

