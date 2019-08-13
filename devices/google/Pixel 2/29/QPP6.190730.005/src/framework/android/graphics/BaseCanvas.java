/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.ComposeShader;
import android.graphics.Matrix;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.TemporaryBuffer;
import android.graphics.Xfermode;
import android.graphics.text.MeasuredText;
import android.text.GraphicsOperations;
import android.text.MeasuredParagraph;
import android.text.PrecomputedText;
import android.text.SpannableString;
import android.text.SpannedString;
import android.text.TextUtils;

public abstract class BaseCanvas {
    private boolean mAllowHwBitmapsInSwMode = false;
    protected int mDensity = 0;
    @UnsupportedAppUsage
    protected long mNativeCanvasWrapper;
    protected int mScreenDensity = 0;

    protected static final void checkRange(int n, int n2, int n3) {
        if ((n2 | n3) >= 0 && n2 + n3 <= n) {
            return;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    private static native void nDrawArc(long var0, float var2, float var3, float var4, float var5, float var6, float var7, boolean var8, long var9);

    private static native void nDrawBitmap(long var0, long var2, float var4, float var5, float var6, float var7, float var8, float var9, float var10, float var11, long var12, int var14, int var15);

    private static native void nDrawBitmap(long var0, long var2, float var4, float var5, long var6, int var8, int var9, int var10);

    private static native void nDrawBitmap(long var0, int[] var2, int var3, int var4, float var5, float var6, int var7, int var8, boolean var9, long var10);

    private static native void nDrawBitmapMatrix(long var0, long var2, long var4, long var6);

    private static native void nDrawBitmapMesh(long var0, long var2, int var4, int var5, float[] var6, int var7, int[] var8, int var9, long var10);

    private static native void nDrawCircle(long var0, float var2, float var3, float var4, long var5);

    private static native void nDrawColor(long var0, int var2, int var3);

    private static native void nDrawColor(long var0, long var2, long var4, int var6);

    private static native void nDrawDoubleRoundRect(long var0, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, float var11, float var12, float var13, long var14);

    private static native void nDrawDoubleRoundRect(long var0, float var2, float var3, float var4, float var5, float[] var6, float var7, float var8, float var9, float var10, float[] var11, long var12);

    private static native void nDrawLine(long var0, float var2, float var3, float var4, float var5, long var6);

    private static native void nDrawLines(long var0, float[] var2, int var3, int var4, long var5);

    private static native void nDrawNinePatch(long var0, long var2, long var4, float var6, float var7, float var8, float var9, long var10, int var12, int var13);

    private static native void nDrawOval(long var0, float var2, float var3, float var4, float var5, long var6);

    private static native void nDrawPaint(long var0, long var2);

    private static native void nDrawPath(long var0, long var2, long var4);

    private static native void nDrawPoint(long var0, float var2, float var3, long var4);

    private static native void nDrawPoints(long var0, float[] var2, int var3, int var4, long var5);

    private static native void nDrawRect(long var0, float var2, float var3, float var4, float var5, long var6);

    private static native void nDrawRegion(long var0, long var2, long var4);

    private static native void nDrawRoundRect(long var0, float var2, float var3, float var4, float var5, float var6, float var7, long var8);

    private static native void nDrawText(long var0, String var2, int var3, int var4, float var5, float var6, int var7, long var8);

    private static native void nDrawText(long var0, char[] var2, int var3, int var4, float var5, float var6, int var7, long var8);

    private static native void nDrawTextOnPath(long var0, String var2, long var3, float var5, float var6, int var7, long var8);

    private static native void nDrawTextOnPath(long var0, char[] var2, int var3, int var4, long var5, float var7, float var8, int var9, long var10);

    private static native void nDrawTextRun(long var0, String var2, int var3, int var4, int var5, int var6, float var7, float var8, boolean var9, long var10);

    private static native void nDrawTextRun(long var0, char[] var2, int var3, int var4, int var5, int var6, float var7, float var8, boolean var9, long var10, long var12);

    private static native void nDrawVertices(long var0, int var2, int var3, float[] var4, int var5, float[] var6, int var7, int[] var8, int var9, short[] var10, int var11, int var12, long var13);

    private void throwIfHasHwBitmapInSwMode(Paint paint) {
        if (!this.isHardwareAccelerated() && paint != null) {
            this.throwIfHasHwBitmapInSwMode(paint.getShader());
            return;
        }
    }

    private void throwIfHasHwBitmapInSwMode(Shader shader) {
        if (shader == null) {
            return;
        }
        if (shader instanceof BitmapShader) {
            this.throwIfHwBitmapInSwMode(((BitmapShader)shader).mBitmap);
        }
        if (shader instanceof ComposeShader) {
            this.throwIfHasHwBitmapInSwMode(((ComposeShader)shader).mShaderA);
            this.throwIfHasHwBitmapInSwMode(((ComposeShader)shader).mShaderB);
        }
    }

    private void throwIfHwBitmapInSwMode(Bitmap bitmap) {
        if (!this.isHardwareAccelerated() && bitmap.getConfig() == Bitmap.Config.HARDWARE) {
            this.onHwBitmapInSwMode();
        }
    }

    public void drawARGB(int n, int n2, int n3, int n4) {
        this.drawColor(Color.argb(n, n2, n3, n4));
    }

    public void drawArc(float f, float f2, float f3, float f4, float f5, float f6, boolean bl, Paint paint) {
        this.throwIfHasHwBitmapInSwMode(paint);
        BaseCanvas.nDrawArc(this.mNativeCanvasWrapper, f, f2, f3, f4, f5, f6, bl, paint.getNativeInstance());
    }

    public void drawArc(RectF rectF, float f, float f2, boolean bl, Paint paint) {
        this.throwIfHasHwBitmapInSwMode(paint);
        this.drawArc(rectF.left, rectF.top, rectF.right, rectF.bottom, f, f2, bl, paint);
    }

    public void drawBitmap(Bitmap bitmap, float f, float f2, Paint paint) {
        this.throwIfCannotDraw(bitmap);
        this.throwIfHasHwBitmapInSwMode(paint);
        long l = this.mNativeCanvasWrapper;
        long l2 = bitmap.getNativeInstance();
        long l3 = paint != null ? paint.getNativeInstance() : 0L;
        BaseCanvas.nDrawBitmap(l, l2, f, f2, l3, this.mDensity, this.mScreenDensity, bitmap.mDensity);
    }

    public void drawBitmap(Bitmap bitmap, Matrix matrix, Paint paint) {
        this.throwIfHasHwBitmapInSwMode(paint);
        long l = this.mNativeCanvasWrapper;
        long l2 = bitmap.getNativeInstance();
        long l3 = matrix.ni();
        long l4 = paint != null ? paint.getNativeInstance() : 0L;
        BaseCanvas.nDrawBitmapMatrix(l, l2, l3, l4);
    }

    public void drawBitmap(Bitmap bitmap, Rect rect, Rect rect2, Paint paint) {
        if (rect2 != null) {
            int n;
            int n2;
            int n3;
            int n4;
            this.throwIfCannotDraw(bitmap);
            this.throwIfHasHwBitmapInSwMode(paint);
            long l = paint == null ? 0L : paint.getNativeInstance();
            if (rect == null) {
                n3 = 0;
                n2 = 0;
                n4 = bitmap.getWidth();
                n = bitmap.getHeight();
            } else {
                n3 = rect.left;
                n4 = rect.right;
                n2 = rect.top;
                n = rect.bottom;
            }
            BaseCanvas.nDrawBitmap(this.mNativeCanvasWrapper, bitmap.getNativeInstance(), n3, n2, n4, n, rect2.left, rect2.top, rect2.right, rect2.bottom, l, this.mScreenDensity, bitmap.mDensity);
            return;
        }
        throw new NullPointerException();
    }

    public void drawBitmap(Bitmap bitmap, Rect rect, RectF rectF, Paint paint) {
        if (rectF != null) {
            float f;
            float f2;
            float f3;
            float f4;
            this.throwIfCannotDraw(bitmap);
            this.throwIfHasHwBitmapInSwMode(paint);
            long l = paint == null ? 0L : paint.getNativeInstance();
            if (rect == null) {
                f4 = 0.0f;
                f2 = 0.0f;
                f = bitmap.getWidth();
                f3 = bitmap.getHeight();
            } else {
                float f5 = rect.left;
                f4 = rect.right;
                f2 = rect.top;
                f3 = rect.bottom;
                f = f4;
                f4 = f5;
            }
            BaseCanvas.nDrawBitmap(this.mNativeCanvasWrapper, bitmap.getNativeInstance(), f4, f2, f, f3, rectF.left, rectF.top, rectF.right, rectF.bottom, l, this.mScreenDensity, bitmap.mDensity);
            return;
        }
        throw new NullPointerException();
    }

    @Deprecated
    public void drawBitmap(int[] arrn, int n, int n2, float f, float f2, int n3, int n4, boolean bl, Paint paint) {
        if (n3 >= 0) {
            if (n4 >= 0) {
                if (Math.abs(n2) >= n3) {
                    int n5 = n + (n4 - 1) * n2;
                    int n6 = arrn.length;
                    if (n >= 0 && n + n3 <= n6 && n5 >= 0 && n5 + n3 <= n6) {
                        this.throwIfHasHwBitmapInSwMode(paint);
                        if (n3 != 0 && n4 != 0) {
                            long l = this.mNativeCanvasWrapper;
                            long l2 = paint != null ? paint.getNativeInstance() : 0L;
                            BaseCanvas.nDrawBitmap(l, arrn, n, n2, f, f2, n3, n4, bl, l2);
                            return;
                        }
                        return;
                    }
                    throw new ArrayIndexOutOfBoundsException();
                }
                throw new IllegalArgumentException("abs(stride) must be >= width");
            }
            throw new IllegalArgumentException("height must be >= 0");
        }
        throw new IllegalArgumentException("width must be >= 0");
    }

    @Deprecated
    public void drawBitmap(int[] arrn, int n, int n2, int n3, int n4, int n5, int n6, boolean bl, Paint paint) {
        this.drawBitmap(arrn, n, n2, (float)n3, (float)n4, n5, n6, bl, paint);
    }

    public void drawBitmapMesh(Bitmap bitmap, int n, int n2, float[] arrf, int n3, int[] arrn, int n4, Paint paint) {
        if ((n | n2 | n3 | n4) >= 0) {
            this.throwIfHasHwBitmapInSwMode(paint);
            if (n != 0 && n2 != 0) {
                int n5 = (n + 1) * (n2 + 1);
                BaseCanvas.checkRange(arrf.length, n3, n5 * 2);
                if (arrn != null) {
                    BaseCanvas.checkRange(arrn.length, n4, n5);
                }
                long l = this.mNativeCanvasWrapper;
                long l2 = bitmap.getNativeInstance();
                long l3 = paint != null ? paint.getNativeInstance() : 0L;
                BaseCanvas.nDrawBitmapMesh(l, l2, n, n2, arrf, n3, arrn, n4, l3);
                return;
            }
            return;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public void drawCircle(float f, float f2, float f3, Paint paint) {
        this.throwIfHasHwBitmapInSwMode(paint);
        BaseCanvas.nDrawCircle(this.mNativeCanvasWrapper, f, f2, f3, paint.getNativeInstance());
    }

    public void drawColor(int n) {
        BaseCanvas.nDrawColor(this.mNativeCanvasWrapper, n, BlendMode.SRC_OVER.getXfermode().porterDuffMode);
    }

    public void drawColor(int n, BlendMode blendMode) {
        BaseCanvas.nDrawColor(this.mNativeCanvasWrapper, n, blendMode.getXfermode().porterDuffMode);
    }

    public void drawColor(int n, PorterDuff.Mode mode) {
        BaseCanvas.nDrawColor(this.mNativeCanvasWrapper, n, mode.nativeInt);
    }

    public void drawColor(long l, BlendMode blendMode) {
        ColorSpace colorSpace = Color.colorSpace(l);
        BaseCanvas.nDrawColor(this.mNativeCanvasWrapper, colorSpace.getNativeInstance(), l, blendMode.getXfermode().porterDuffMode);
    }

    public void drawDoubleRoundRect(RectF rectF, float f, float f2, RectF rectF2, float f3, float f4, Paint paint) {
        this.throwIfHasHwBitmapInSwMode(paint);
        float f5 = rectF.left;
        float f6 = rectF.top;
        float f7 = rectF.right;
        float f8 = rectF.bottom;
        float f9 = rectF2.left;
        float f10 = rectF2.top;
        float f11 = rectF2.right;
        float f12 = rectF2.bottom;
        BaseCanvas.nDrawDoubleRoundRect(this.mNativeCanvasWrapper, f5, f6, f7, f8, f, f2, f9, f10, f11, f12, f3, f4, paint.getNativeInstance());
    }

    public void drawDoubleRoundRect(RectF rectF, float[] arrf, RectF rectF2, float[] arrf2, Paint paint) {
        this.throwIfHasHwBitmapInSwMode(paint);
        if (arrf2 != null && arrf != null && arrf2.length == 8 && arrf.length == 8) {
            float f = rectF.left;
            float f2 = rectF.top;
            float f3 = rectF.right;
            float f4 = rectF.bottom;
            float f5 = rectF2.left;
            float f6 = rectF2.top;
            float f7 = rectF2.right;
            float f8 = rectF2.bottom;
            BaseCanvas.nDrawDoubleRoundRect(this.mNativeCanvasWrapper, f, f2, f3, f4, arrf, f5, f6, f7, f8, arrf2, paint.getNativeInstance());
            return;
        }
        throw new IllegalArgumentException("Both inner and outer radii arrays must contain exactly 8 values");
    }

    public void drawLine(float f, float f2, float f3, float f4, Paint paint) {
        this.throwIfHasHwBitmapInSwMode(paint);
        BaseCanvas.nDrawLine(this.mNativeCanvasWrapper, f, f2, f3, f4, paint.getNativeInstance());
    }

    public void drawLines(float[] arrf, int n, int n2, Paint paint) {
        this.throwIfHasHwBitmapInSwMode(paint);
        BaseCanvas.nDrawLines(this.mNativeCanvasWrapper, arrf, n, n2, paint.getNativeInstance());
    }

    public void drawLines(float[] arrf, Paint paint) {
        this.throwIfHasHwBitmapInSwMode(paint);
        this.drawLines(arrf, 0, arrf.length, paint);
    }

    public void drawOval(float f, float f2, float f3, float f4, Paint paint) {
        this.throwIfHasHwBitmapInSwMode(paint);
        BaseCanvas.nDrawOval(this.mNativeCanvasWrapper, f, f2, f3, f4, paint.getNativeInstance());
    }

    public void drawOval(RectF rectF, Paint paint) {
        if (rectF != null) {
            this.throwIfHasHwBitmapInSwMode(paint);
            this.drawOval(rectF.left, rectF.top, rectF.right, rectF.bottom, paint);
            return;
        }
        throw new NullPointerException();
    }

    public void drawPaint(Paint paint) {
        BaseCanvas.nDrawPaint(this.mNativeCanvasWrapper, paint.getNativeInstance());
    }

    public void drawPatch(NinePatch ninePatch, Rect rect, Paint paint) {
        Bitmap bitmap = ninePatch.getBitmap();
        this.throwIfCannotDraw(bitmap);
        this.throwIfHasHwBitmapInSwMode(paint);
        long l = paint == null ? 0L : paint.getNativeInstance();
        BaseCanvas.nDrawNinePatch(this.mNativeCanvasWrapper, bitmap.getNativeInstance(), ninePatch.mNativeChunk, rect.left, rect.top, rect.right, rect.bottom, l, this.mDensity, ninePatch.getDensity());
    }

    public void drawPatch(NinePatch ninePatch, RectF rectF, Paint paint) {
        Bitmap bitmap = ninePatch.getBitmap();
        this.throwIfCannotDraw(bitmap);
        this.throwIfHasHwBitmapInSwMode(paint);
        long l = paint == null ? 0L : paint.getNativeInstance();
        BaseCanvas.nDrawNinePatch(this.mNativeCanvasWrapper, bitmap.getNativeInstance(), ninePatch.mNativeChunk, rectF.left, rectF.top, rectF.right, rectF.bottom, l, this.mDensity, ninePatch.getDensity());
    }

    public void drawPath(Path path, Paint paint) {
        this.throwIfHasHwBitmapInSwMode(paint);
        if (path.isSimplePath && path.rects != null) {
            BaseCanvas.nDrawRegion(this.mNativeCanvasWrapper, path.rects.mNativeRegion, paint.getNativeInstance());
        } else {
            BaseCanvas.nDrawPath(this.mNativeCanvasWrapper, path.readOnlyNI(), paint.getNativeInstance());
        }
    }

    public void drawPoint(float f, float f2, Paint paint) {
        this.throwIfHasHwBitmapInSwMode(paint);
        BaseCanvas.nDrawPoint(this.mNativeCanvasWrapper, f, f2, paint.getNativeInstance());
    }

    public void drawPoints(float[] arrf, int n, int n2, Paint paint) {
        this.throwIfHasHwBitmapInSwMode(paint);
        BaseCanvas.nDrawPoints(this.mNativeCanvasWrapper, arrf, n, n2, paint.getNativeInstance());
    }

    public void drawPoints(float[] arrf, Paint paint) {
        this.throwIfHasHwBitmapInSwMode(paint);
        this.drawPoints(arrf, 0, arrf.length, paint);
    }

    @Deprecated
    public void drawPosText(String string2, float[] arrf, Paint paint) {
        this.throwIfHasHwBitmapInSwMode(paint);
        this.drawPosText(string2.toCharArray(), 0, string2.length(), arrf, paint);
    }

    @Deprecated
    public void drawPosText(char[] arrc, int n, int n2, float[] arrf, Paint paint) {
        if (n >= 0 && n + n2 <= arrc.length && n2 * 2 <= arrf.length) {
            this.throwIfHasHwBitmapInSwMode(paint);
            for (int i = 0; i < n2; ++i) {
                this.drawText(arrc, n + i, 1, arrf[i * 2], arrf[i * 2 + 1], paint);
            }
            return;
        }
        throw new IndexOutOfBoundsException();
    }

    public void drawRGB(int n, int n2, int n3) {
        this.drawColor(Color.rgb(n, n2, n3));
    }

    public void drawRect(float f, float f2, float f3, float f4, Paint paint) {
        this.throwIfHasHwBitmapInSwMode(paint);
        BaseCanvas.nDrawRect(this.mNativeCanvasWrapper, f, f2, f3, f4, paint.getNativeInstance());
    }

    public void drawRect(Rect rect, Paint paint) {
        this.throwIfHasHwBitmapInSwMode(paint);
        this.drawRect(rect.left, rect.top, rect.right, rect.bottom, paint);
    }

    public void drawRect(RectF rectF, Paint paint) {
        this.throwIfHasHwBitmapInSwMode(paint);
        BaseCanvas.nDrawRect(this.mNativeCanvasWrapper, rectF.left, rectF.top, rectF.right, rectF.bottom, paint.getNativeInstance());
    }

    public void drawRoundRect(float f, float f2, float f3, float f4, float f5, float f6, Paint paint) {
        this.throwIfHasHwBitmapInSwMode(paint);
        BaseCanvas.nDrawRoundRect(this.mNativeCanvasWrapper, f, f2, f3, f4, f5, f6, paint.getNativeInstance());
    }

    public void drawRoundRect(RectF rectF, float f, float f2, Paint paint) {
        this.throwIfHasHwBitmapInSwMode(paint);
        this.drawRoundRect(rectF.left, rectF.top, rectF.right, rectF.bottom, f, f2, paint);
    }

    public void drawText(CharSequence charSequence, int n, int n2, float f, float f2, Paint paint) {
        if ((n | n2 | n2 - n | charSequence.length() - n2) >= 0) {
            this.throwIfHasHwBitmapInSwMode(paint);
            if (!(charSequence instanceof String || charSequence instanceof SpannedString || charSequence instanceof SpannableString)) {
                if (charSequence instanceof GraphicsOperations) {
                    ((GraphicsOperations)charSequence).drawText(this, n, n2, f, f2, paint);
                } else {
                    char[] arrc = TemporaryBuffer.obtain(n2 - n);
                    TextUtils.getChars(charSequence, n, n2, arrc, 0);
                    BaseCanvas.nDrawText(this.mNativeCanvasWrapper, arrc, 0, n2 - n, f, f2, paint.mBidiFlags, paint.getNativeInstance());
                    TemporaryBuffer.recycle(arrc);
                }
            } else {
                BaseCanvas.nDrawText(this.mNativeCanvasWrapper, charSequence.toString(), n, n2, f, f2, paint.mBidiFlags, paint.getNativeInstance());
            }
            return;
        }
        throw new IndexOutOfBoundsException();
    }

    public void drawText(String string2, float f, float f2, Paint paint) {
        this.throwIfHasHwBitmapInSwMode(paint);
        BaseCanvas.nDrawText(this.mNativeCanvasWrapper, string2, 0, string2.length(), f, f2, paint.mBidiFlags, paint.getNativeInstance());
    }

    public void drawText(String string2, int n, int n2, float f, float f2, Paint paint) {
        if ((n | n2 | n2 - n | string2.length() - n2) >= 0) {
            this.throwIfHasHwBitmapInSwMode(paint);
            BaseCanvas.nDrawText(this.mNativeCanvasWrapper, string2, n, n2, f, f2, paint.mBidiFlags, paint.getNativeInstance());
            return;
        }
        throw new IndexOutOfBoundsException();
    }

    public void drawText(char[] arrc, int n, int n2, float f, float f2, Paint paint) {
        if ((n | n2 | n + n2 | arrc.length - n - n2) >= 0) {
            this.throwIfHasHwBitmapInSwMode(paint);
            BaseCanvas.nDrawText(this.mNativeCanvasWrapper, arrc, n, n2, f, f2, paint.mBidiFlags, paint.getNativeInstance());
            return;
        }
        throw new IndexOutOfBoundsException();
    }

    public void drawTextOnPath(String string2, Path path, float f, float f2, Paint paint) {
        if (string2.length() > 0) {
            this.throwIfHasHwBitmapInSwMode(paint);
            BaseCanvas.nDrawTextOnPath(this.mNativeCanvasWrapper, string2, path.readOnlyNI(), f, f2, paint.mBidiFlags, paint.getNativeInstance());
        }
    }

    public void drawTextOnPath(char[] arrc, int n, int n2, Path path, float f, float f2, Paint paint) {
        if (n >= 0 && n + n2 <= arrc.length) {
            this.throwIfHasHwBitmapInSwMode(paint);
            BaseCanvas.nDrawTextOnPath(this.mNativeCanvasWrapper, arrc, n, n2, path.readOnlyNI(), f, f2, paint.mBidiFlags, paint.getNativeInstance());
            return;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public void drawTextRun(MeasuredText measuredText, int n, int n2, int n3, int n4, float f, float f2, boolean bl, Paint paint) {
        BaseCanvas.nDrawTextRun(this.mNativeCanvasWrapper, measuredText.getChars(), n, n2 - n, n3, n4 - n3, f, f2, bl, paint.getNativeInstance(), measuredText.getNativePtr());
    }

    public void drawTextRun(CharSequence charSequence, int n, int n2, int n3, int n4, float f, float f2, boolean bl, Paint paint) {
        if (charSequence != null) {
            if (paint != null) {
                if ((n | n2 | n3 | n4 | n - n3 | n2 - n | n4 - n2 | charSequence.length() - n4) >= 0) {
                    this.throwIfHasHwBitmapInSwMode(paint);
                    if (!(charSequence instanceof String || charSequence instanceof SpannedString || charSequence instanceof SpannableString)) {
                        if (charSequence instanceof GraphicsOperations) {
                            ((GraphicsOperations)charSequence).drawTextRun(this, n, n2, n3, n4, f, f2, bl, paint);
                        } else {
                            char[] arrc;
                            int n5;
                            if (charSequence instanceof PrecomputedText && n2 <= (arrc = (char[])charSequence).getParagraphEnd(n5 = arrc.findParaIndex(n))) {
                                int n6 = arrc.getParagraphStart(n5);
                                this.drawTextRun(arrc.getMeasuredParagraph(n5).getMeasuredText(), n - n6, n2 - n6, n3 - n6, n4 - n6, f, f2, bl, paint);
                                return;
                            }
                            n5 = n4 - n3;
                            arrc = TemporaryBuffer.obtain(n5);
                            TextUtils.getChars(charSequence, n3, n4, arrc, 0);
                            BaseCanvas.nDrawTextRun(this.mNativeCanvasWrapper, arrc, n - n3, n2 - n, 0, n5, f, f2, bl, paint.getNativeInstance(), 0L);
                            TemporaryBuffer.recycle(arrc);
                        }
                    } else {
                        BaseCanvas.nDrawTextRun(this.mNativeCanvasWrapper, charSequence.toString(), n, n2, n3, n4, f, f2, bl, paint.getNativeInstance());
                    }
                    return;
                }
                throw new IndexOutOfBoundsException();
            }
            throw new NullPointerException("paint is null");
        }
        throw new NullPointerException("text is null");
    }

    public void drawTextRun(char[] arrc, int n, int n2, int n3, int n4, float f, float f2, boolean bl, Paint paint) {
        if (arrc != null) {
            if (paint != null) {
                if ((n | n2 | n3 | n4 | n - n3 | n3 + n4 - (n + n2) | arrc.length - (n3 + n4)) >= 0) {
                    this.throwIfHasHwBitmapInSwMode(paint);
                    BaseCanvas.nDrawTextRun(this.mNativeCanvasWrapper, arrc, n, n2, n3, n4, f, f2, bl, paint.getNativeInstance(), 0L);
                    return;
                }
                throw new IndexOutOfBoundsException();
            }
            throw new NullPointerException("paint is null");
        }
        throw new NullPointerException("text is null");
    }

    public void drawVertices(Canvas.VertexMode vertexMode, int n, float[] arrf, int n2, float[] arrf2, int n3, int[] arrn, int n4, short[] arrs, int n5, int n6, Paint paint) {
        BaseCanvas.checkRange(arrf.length, n2, n);
        if (arrf2 != null) {
            BaseCanvas.checkRange(arrf2.length, n3, n);
        }
        if (arrn != null) {
            BaseCanvas.checkRange(arrn.length, n4, n / 2);
        }
        if (arrs != null) {
            BaseCanvas.checkRange(arrs.length, n5, n6);
        }
        this.throwIfHasHwBitmapInSwMode(paint);
        BaseCanvas.nDrawVertices(this.mNativeCanvasWrapper, vertexMode.nativeInt, n, arrf, n2, arrf2, n3, arrn, n4, arrs, n5, n6, paint.getNativeInstance());
    }

    public boolean isHardwareAccelerated() {
        return false;
    }

    public boolean isHwBitmapsInSwModeEnabled() {
        return this.mAllowHwBitmapsInSwMode;
    }

    protected void onHwBitmapInSwMode() {
        if (this.mAllowHwBitmapsInSwMode) {
            return;
        }
        throw new IllegalArgumentException("Software rendering doesn't support hardware bitmaps");
    }

    public void setHwBitmapsInSwModeEnabled(boolean bl) {
        this.mAllowHwBitmapsInSwMode = bl;
    }

    protected void throwIfCannotDraw(Bitmap bitmap) {
        if (!bitmap.isRecycled()) {
            if (!bitmap.isPremultiplied() && bitmap.getConfig() == Bitmap.Config.ARGB_8888 && bitmap.hasAlpha()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Canvas: trying to use a non-premultiplied bitmap ");
                stringBuilder.append(bitmap);
                throw new RuntimeException(stringBuilder.toString());
            }
            this.throwIfHwBitmapInSwMode(bitmap);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Canvas: trying to use a recycled bitmap ");
        stringBuilder.append(bitmap);
        throw new RuntimeException(stringBuilder.toString());
    }
}

