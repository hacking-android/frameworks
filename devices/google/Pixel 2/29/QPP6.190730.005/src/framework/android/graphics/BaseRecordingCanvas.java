/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.FastNative
 */
package android.graphics;

import android.graphics.BaseCanvas;
import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.Matrix;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.TemporaryBuffer;
import android.graphics.Xfermode;
import android.graphics.text.MeasuredText;
import android.text.GraphicsOperations;
import android.text.MeasuredParagraph;
import android.text.PrecomputedText;
import android.text.SpannableString;
import android.text.SpannedString;
import android.text.TextUtils;
import dalvik.annotation.optimization.FastNative;

public class BaseRecordingCanvas
extends Canvas {
    public BaseRecordingCanvas(long l) {
        super(l);
    }

    @FastNative
    private static native void nDrawArc(long var0, float var2, float var3, float var4, float var5, float var6, float var7, boolean var8, long var9);

    @FastNative
    private static native void nDrawBitmap(long var0, long var2, float var4, float var5, float var6, float var7, float var8, float var9, float var10, float var11, long var12, int var14, int var15);

    @FastNative
    private static native void nDrawBitmap(long var0, long var2, float var4, float var5, long var6, int var8, int var9, int var10);

    @FastNative
    private static native void nDrawBitmap(long var0, int[] var2, int var3, int var4, float var5, float var6, int var7, int var8, boolean var9, long var10);

    @FastNative
    private static native void nDrawBitmapMatrix(long var0, long var2, long var4, long var6);

    @FastNative
    private static native void nDrawBitmapMesh(long var0, long var2, int var4, int var5, float[] var6, int var7, int[] var8, int var9, long var10);

    @FastNative
    private static native void nDrawCircle(long var0, float var2, float var3, float var4, long var5);

    @FastNative
    private static native void nDrawColor(long var0, int var2, int var3);

    @FastNative
    private static native void nDrawColor(long var0, long var2, long var4, int var6);

    @FastNative
    private static native void nDrawDoubleRoundRect(long var0, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, float var11, float var12, float var13, long var14);

    @FastNative
    private static native void nDrawDoubleRoundRect(long var0, float var2, float var3, float var4, float var5, float[] var6, float var7, float var8, float var9, float var10, float[] var11, long var12);

    @FastNative
    private static native void nDrawLine(long var0, float var2, float var3, float var4, float var5, long var6);

    @FastNative
    private static native void nDrawLines(long var0, float[] var2, int var3, int var4, long var5);

    @FastNative
    private static native void nDrawNinePatch(long var0, long var2, long var4, float var6, float var7, float var8, float var9, long var10, int var12, int var13);

    @FastNative
    private static native void nDrawOval(long var0, float var2, float var3, float var4, float var5, long var6);

    @FastNative
    private static native void nDrawPaint(long var0, long var2);

    @FastNative
    private static native void nDrawPath(long var0, long var2, long var4);

    @FastNative
    private static native void nDrawPoint(long var0, float var2, float var3, long var4);

    @FastNative
    private static native void nDrawPoints(long var0, float[] var2, int var3, int var4, long var5);

    @FastNative
    private static native void nDrawRect(long var0, float var2, float var3, float var4, float var5, long var6);

    @FastNative
    private static native void nDrawRegion(long var0, long var2, long var4);

    @FastNative
    private static native void nDrawRoundRect(long var0, float var2, float var3, float var4, float var5, float var6, float var7, long var8);

    @FastNative
    private static native void nDrawText(long var0, String var2, int var3, int var4, float var5, float var6, int var7, long var8);

    @FastNative
    private static native void nDrawText(long var0, char[] var2, int var3, int var4, float var5, float var6, int var7, long var8);

    @FastNative
    private static native void nDrawTextOnPath(long var0, String var2, long var3, float var5, float var6, int var7, long var8);

    @FastNative
    private static native void nDrawTextOnPath(long var0, char[] var2, int var3, int var4, long var5, float var7, float var8, int var9, long var10);

    @FastNative
    private static native void nDrawTextRun(long var0, String var2, int var3, int var4, int var5, int var6, float var7, float var8, boolean var9, long var10);

    @FastNative
    private static native void nDrawTextRun(long var0, char[] var2, int var3, int var4, int var5, int var6, float var7, float var8, boolean var9, long var10, long var12);

    @FastNative
    private static native void nDrawVertices(long var0, int var2, int var3, float[] var4, int var5, float[] var6, int var7, int[] var8, int var9, short[] var10, int var11, int var12, long var13);

    @Override
    public final void drawARGB(int n, int n2, int n3, int n4) {
        this.drawColor(Color.argb(n, n2, n3, n4));
    }

    @Override
    public final void drawArc(float f, float f2, float f3, float f4, float f5, float f6, boolean bl, Paint paint) {
        BaseRecordingCanvas.nDrawArc(this.mNativeCanvasWrapper, f, f2, f3, f4, f5, f6, bl, paint.getNativeInstance());
    }

    @Override
    public final void drawArc(RectF rectF, float f, float f2, boolean bl, Paint paint) {
        this.drawArc(rectF.left, rectF.top, rectF.right, rectF.bottom, f, f2, bl, paint);
    }

    @Override
    public final void drawBitmap(Bitmap bitmap, float f, float f2, Paint paint) {
        this.throwIfCannotDraw(bitmap);
        long l = this.mNativeCanvasWrapper;
        long l2 = bitmap.getNativeInstance();
        long l3 = paint != null ? paint.getNativeInstance() : 0L;
        BaseRecordingCanvas.nDrawBitmap(l, l2, f, f2, l3, this.mDensity, this.mScreenDensity, bitmap.mDensity);
    }

    @Override
    public final void drawBitmap(Bitmap bitmap, Matrix matrix, Paint paint) {
        long l = this.mNativeCanvasWrapper;
        long l2 = bitmap.getNativeInstance();
        long l3 = matrix.ni();
        long l4 = paint != null ? paint.getNativeInstance() : 0L;
        BaseRecordingCanvas.nDrawBitmapMatrix(l, l2, l3, l4);
    }

    @Override
    public final void drawBitmap(Bitmap bitmap, Rect rect, Rect rect2, Paint paint) {
        if (rect2 != null) {
            int n;
            int n2;
            int n3;
            int n4;
            this.throwIfCannotDraw(bitmap);
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
            BaseRecordingCanvas.nDrawBitmap(this.mNativeCanvasWrapper, bitmap.getNativeInstance(), n3, n2, n4, n, rect2.left, rect2.top, rect2.right, rect2.bottom, l, this.mScreenDensity, bitmap.mDensity);
            return;
        }
        throw new NullPointerException();
    }

    @Override
    public final void drawBitmap(Bitmap bitmap, Rect rect, RectF rectF, Paint paint) {
        if (rectF != null) {
            float f;
            float f2;
            float f3;
            float f4;
            this.throwIfCannotDraw(bitmap);
            long l = paint == null ? 0L : paint.getNativeInstance();
            if (rect == null) {
                f4 = 0.0f;
                f2 = 0.0f;
                f = bitmap.getWidth();
                f3 = bitmap.getHeight();
            } else {
                f4 = rect.left;
                f = rect.right;
                f2 = rect.top;
                f3 = rect.bottom;
            }
            BaseRecordingCanvas.nDrawBitmap(this.mNativeCanvasWrapper, bitmap.getNativeInstance(), f4, f2, f, f3, rectF.left, rectF.top, rectF.right, rectF.bottom, l, this.mScreenDensity, bitmap.mDensity);
            return;
        }
        throw new NullPointerException();
    }

    @Deprecated
    @Override
    public final void drawBitmap(int[] arrn, int n, int n2, float f, float f2, int n3, int n4, boolean bl, Paint paint) {
        if (n3 >= 0) {
            if (n4 >= 0) {
                if (Math.abs(n2) >= n3) {
                    int n5 = n + (n4 - 1) * n2;
                    int n6 = arrn.length;
                    if (n >= 0 && n + n3 <= n6 && n5 >= 0 && n5 + n3 <= n6) {
                        if (n3 != 0 && n4 != 0) {
                            long l = this.mNativeCanvasWrapper;
                            long l2 = paint != null ? paint.getNativeInstance() : 0L;
                            BaseRecordingCanvas.nDrawBitmap(l, arrn, n, n2, f, f2, n3, n4, bl, l2);
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
    @Override
    public final void drawBitmap(int[] arrn, int n, int n2, int n3, int n4, int n5, int n6, boolean bl, Paint paint) {
        this.drawBitmap(arrn, n, n2, (float)n3, (float)n4, n5, n6, bl, paint);
    }

    @Override
    public final void drawBitmapMesh(Bitmap bitmap, int n, int n2, float[] arrf, int n3, int[] arrn, int n4, Paint paint) {
        if ((n | n2 | n3 | n4) >= 0) {
            if (n != 0 && n2 != 0) {
                int n5 = (n + 1) * (n2 + 1);
                BaseRecordingCanvas.checkRange(arrf.length, n3, n5 * 2);
                if (arrn != null) {
                    BaseRecordingCanvas.checkRange(arrn.length, n4, n5);
                }
                long l = this.mNativeCanvasWrapper;
                long l2 = bitmap.getNativeInstance();
                long l3 = paint != null ? paint.getNativeInstance() : 0L;
                BaseRecordingCanvas.nDrawBitmapMesh(l, l2, n, n2, arrf, n3, arrn, n4, l3);
                return;
            }
            return;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    @Override
    public final void drawCircle(float f, float f2, float f3, Paint paint) {
        BaseRecordingCanvas.nDrawCircle(this.mNativeCanvasWrapper, f, f2, f3, paint.getNativeInstance());
    }

    @Override
    public final void drawColor(int n) {
        BaseRecordingCanvas.nDrawColor(this.mNativeCanvasWrapper, n, BlendMode.SRC_OVER.getXfermode().porterDuffMode);
    }

    @Override
    public final void drawColor(int n, BlendMode blendMode) {
        BaseRecordingCanvas.nDrawColor(this.mNativeCanvasWrapper, n, blendMode.getXfermode().porterDuffMode);
    }

    @Override
    public final void drawColor(int n, PorterDuff.Mode mode) {
        BaseRecordingCanvas.nDrawColor(this.mNativeCanvasWrapper, n, mode.nativeInt);
    }

    @Override
    public final void drawColor(long l, BlendMode blendMode) {
        ColorSpace colorSpace = Color.colorSpace(l);
        BaseRecordingCanvas.nDrawColor(this.mNativeCanvasWrapper, colorSpace.getNativeInstance(), l, blendMode.getXfermode().porterDuffMode);
    }

    @Override
    public final void drawDoubleRoundRect(RectF rectF, float f, float f2, RectF rectF2, float f3, float f4, Paint paint) {
        BaseRecordingCanvas.nDrawDoubleRoundRect(this.mNativeCanvasWrapper, rectF.left, rectF.top, rectF.right, rectF.bottom, f, f2, rectF2.left, rectF2.top, rectF2.right, rectF2.bottom, f3, f4, paint.getNativeInstance());
    }

    @Override
    public final void drawDoubleRoundRect(RectF rectF, float[] arrf, RectF rectF2, float[] arrf2, Paint paint) {
        BaseRecordingCanvas.nDrawDoubleRoundRect(this.mNativeCanvasWrapper, rectF.left, rectF.top, rectF.right, rectF.bottom, arrf, rectF2.left, rectF2.top, rectF2.right, rectF2.bottom, arrf2, paint.getNativeInstance());
    }

    @Override
    public final void drawLine(float f, float f2, float f3, float f4, Paint paint) {
        BaseRecordingCanvas.nDrawLine(this.mNativeCanvasWrapper, f, f2, f3, f4, paint.getNativeInstance());
    }

    @Override
    public final void drawLines(float[] arrf, int n, int n2, Paint paint) {
        BaseRecordingCanvas.nDrawLines(this.mNativeCanvasWrapper, arrf, n, n2, paint.getNativeInstance());
    }

    @Override
    public final void drawLines(float[] arrf, Paint paint) {
        this.drawLines(arrf, 0, arrf.length, paint);
    }

    @Override
    public final void drawOval(float f, float f2, float f3, float f4, Paint paint) {
        BaseRecordingCanvas.nDrawOval(this.mNativeCanvasWrapper, f, f2, f3, f4, paint.getNativeInstance());
    }

    @Override
    public final void drawOval(RectF rectF, Paint paint) {
        if (rectF != null) {
            this.drawOval(rectF.left, rectF.top, rectF.right, rectF.bottom, paint);
            return;
        }
        throw new NullPointerException();
    }

    @Override
    public final void drawPaint(Paint paint) {
        BaseRecordingCanvas.nDrawPaint(this.mNativeCanvasWrapper, paint.getNativeInstance());
    }

    @Override
    public final void drawPatch(NinePatch ninePatch, Rect rect, Paint paint) {
        Bitmap bitmap = ninePatch.getBitmap();
        this.throwIfCannotDraw(bitmap);
        long l = paint == null ? 0L : paint.getNativeInstance();
        BaseRecordingCanvas.nDrawNinePatch(this.mNativeCanvasWrapper, bitmap.getNativeInstance(), ninePatch.mNativeChunk, rect.left, rect.top, rect.right, rect.bottom, l, this.mDensity, ninePatch.getDensity());
    }

    @Override
    public final void drawPatch(NinePatch ninePatch, RectF rectF, Paint paint) {
        Bitmap bitmap = ninePatch.getBitmap();
        this.throwIfCannotDraw(bitmap);
        long l = paint == null ? 0L : paint.getNativeInstance();
        BaseRecordingCanvas.nDrawNinePatch(this.mNativeCanvasWrapper, bitmap.getNativeInstance(), ninePatch.mNativeChunk, rectF.left, rectF.top, rectF.right, rectF.bottom, l, this.mDensity, ninePatch.getDensity());
    }

    @Override
    public final void drawPath(Path path, Paint paint) {
        if (path.isSimplePath && path.rects != null) {
            BaseRecordingCanvas.nDrawRegion(this.mNativeCanvasWrapper, path.rects.mNativeRegion, paint.getNativeInstance());
        } else {
            BaseRecordingCanvas.nDrawPath(this.mNativeCanvasWrapper, path.readOnlyNI(), paint.getNativeInstance());
        }
    }

    @Override
    public final void drawPicture(Picture picture) {
        picture.endRecording();
        int n = this.save();
        picture.draw(this);
        this.restoreToCount(n);
    }

    @Override
    public final void drawPicture(Picture picture, Rect rect) {
        this.save();
        this.translate(rect.left, rect.top);
        if (picture.getWidth() > 0 && picture.getHeight() > 0) {
            this.scale((float)rect.width() / (float)picture.getWidth(), (float)rect.height() / (float)picture.getHeight());
        }
        this.drawPicture(picture);
        this.restore();
    }

    @Override
    public final void drawPicture(Picture picture, RectF rectF) {
        this.save();
        this.translate(rectF.left, rectF.top);
        if (picture.getWidth() > 0 && picture.getHeight() > 0) {
            this.scale(rectF.width() / (float)picture.getWidth(), rectF.height() / (float)picture.getHeight());
        }
        this.drawPicture(picture);
        this.restore();
    }

    @Override
    public final void drawPoint(float f, float f2, Paint paint) {
        BaseRecordingCanvas.nDrawPoint(this.mNativeCanvasWrapper, f, f2, paint.getNativeInstance());
    }

    @Override
    public final void drawPoints(float[] arrf, int n, int n2, Paint paint) {
        BaseRecordingCanvas.nDrawPoints(this.mNativeCanvasWrapper, arrf, n, n2, paint.getNativeInstance());
    }

    @Override
    public final void drawPoints(float[] arrf, Paint paint) {
        this.drawPoints(arrf, 0, arrf.length, paint);
    }

    @Deprecated
    @Override
    public final void drawPosText(String string2, float[] arrf, Paint paint) {
        this.drawPosText(string2.toCharArray(), 0, string2.length(), arrf, paint);
    }

    @Deprecated
    @Override
    public final void drawPosText(char[] arrc, int n, int n2, float[] arrf, Paint paint) {
        if (n >= 0 && n + n2 <= arrc.length && n2 * 2 <= arrf.length) {
            for (int i = 0; i < n2; ++i) {
                this.drawText(arrc, n + i, 1, arrf[i * 2], arrf[i * 2 + 1], paint);
            }
            return;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public final void drawRGB(int n, int n2, int n3) {
        this.drawColor(Color.rgb(n, n2, n3));
    }

    @Override
    public final void drawRect(float f, float f2, float f3, float f4, Paint paint) {
        BaseRecordingCanvas.nDrawRect(this.mNativeCanvasWrapper, f, f2, f3, f4, paint.getNativeInstance());
    }

    @Override
    public final void drawRect(Rect rect, Paint paint) {
        this.drawRect(rect.left, rect.top, rect.right, rect.bottom, paint);
    }

    @Override
    public final void drawRect(RectF rectF, Paint paint) {
        BaseRecordingCanvas.nDrawRect(this.mNativeCanvasWrapper, rectF.left, rectF.top, rectF.right, rectF.bottom, paint.getNativeInstance());
    }

    @Override
    public final void drawRoundRect(float f, float f2, float f3, float f4, float f5, float f6, Paint paint) {
        BaseRecordingCanvas.nDrawRoundRect(this.mNativeCanvasWrapper, f, f2, f3, f4, f5, f6, paint.getNativeInstance());
    }

    @Override
    public final void drawRoundRect(RectF rectF, float f, float f2, Paint paint) {
        this.drawRoundRect(rectF.left, rectF.top, rectF.right, rectF.bottom, f, f2, paint);
    }

    @Override
    public final void drawText(CharSequence charSequence, int n, int n2, float f, float f2, Paint paint) {
        if ((n | n2 | n2 - n | charSequence.length() - n2) >= 0) {
            if (!(charSequence instanceof String || charSequence instanceof SpannedString || charSequence instanceof SpannableString)) {
                if (charSequence instanceof GraphicsOperations) {
                    ((GraphicsOperations)charSequence).drawText(this, n, n2, f, f2, paint);
                } else {
                    char[] arrc = TemporaryBuffer.obtain(n2 - n);
                    TextUtils.getChars(charSequence, n, n2, arrc, 0);
                    BaseRecordingCanvas.nDrawText(this.mNativeCanvasWrapper, arrc, 0, n2 - n, f, f2, paint.mBidiFlags, paint.getNativeInstance());
                    TemporaryBuffer.recycle(arrc);
                }
            } else {
                BaseRecordingCanvas.nDrawText(this.mNativeCanvasWrapper, charSequence.toString(), n, n2, f, f2, paint.mBidiFlags, paint.getNativeInstance());
            }
            return;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public final void drawText(String string2, float f, float f2, Paint paint) {
        BaseRecordingCanvas.nDrawText(this.mNativeCanvasWrapper, string2, 0, string2.length(), f, f2, paint.mBidiFlags, paint.getNativeInstance());
    }

    @Override
    public final void drawText(String string2, int n, int n2, float f, float f2, Paint paint) {
        if ((n | n2 | n2 - n | string2.length() - n2) >= 0) {
            BaseRecordingCanvas.nDrawText(this.mNativeCanvasWrapper, string2, n, n2, f, f2, paint.mBidiFlags, paint.getNativeInstance());
            return;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public final void drawText(char[] arrc, int n, int n2, float f, float f2, Paint paint) {
        if ((n | n2 | n + n2 | arrc.length - n - n2) >= 0) {
            BaseRecordingCanvas.nDrawText(this.mNativeCanvasWrapper, arrc, n, n2, f, f2, paint.mBidiFlags, paint.getNativeInstance());
            return;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public final void drawTextOnPath(String string2, Path path, float f, float f2, Paint paint) {
        block0 : {
            if (string2.length() <= 0) break block0;
            BaseRecordingCanvas.nDrawTextOnPath(this.mNativeCanvasWrapper, string2, path.readOnlyNI(), f, f2, paint.mBidiFlags, paint.getNativeInstance());
        }
    }

    @Override
    public final void drawTextOnPath(char[] arrc, int n, int n2, Path path, float f, float f2, Paint paint) {
        if (n >= 0 && n + n2 <= arrc.length) {
            BaseRecordingCanvas.nDrawTextOnPath(this.mNativeCanvasWrapper, arrc, n, n2, path.readOnlyNI(), f, f2, paint.mBidiFlags, paint.getNativeInstance());
            return;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    @Override
    public void drawTextRun(MeasuredText measuredText, int n, int n2, int n3, int n4, float f, float f2, boolean bl, Paint paint) {
        BaseRecordingCanvas.nDrawTextRun(this.mNativeCanvasWrapper, measuredText.getChars(), n, n2 - n, n3, n4 - n3, f, f2, bl, paint.getNativeInstance(), measuredText.getNativePtr());
    }

    @Override
    public final void drawTextRun(CharSequence charSequence, int n, int n2, int n3, int n4, float f, float f2, boolean bl, Paint paint) {
        if (charSequence != null) {
            if (paint != null) {
                if ((n | n2 | n3 | n4 | n - n3 | n2 - n | n4 - n2 | charSequence.length() - n4) >= 0) {
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
                            int n7 = n4 - n3;
                            arrc = TemporaryBuffer.obtain(n7);
                            TextUtils.getChars(charSequence, n3, n4, arrc, 0);
                            BaseRecordingCanvas.nDrawTextRun(this.mNativeCanvasWrapper, arrc, n - n3, n2 - n, 0, n7, f, f2, bl, paint.getNativeInstance(), 0L);
                            TemporaryBuffer.recycle(arrc);
                        }
                    } else {
                        BaseRecordingCanvas.nDrawTextRun(this.mNativeCanvasWrapper, charSequence.toString(), n, n2, n3, n4, f, f2, bl, paint.getNativeInstance());
                    }
                    return;
                }
                throw new IndexOutOfBoundsException();
            }
            throw new NullPointerException("paint is null");
        }
        throw new NullPointerException("text is null");
    }

    @Override
    public final void drawTextRun(char[] arrc, int n, int n2, int n3, int n4, float f, float f2, boolean bl, Paint paint) {
        if (arrc != null) {
            if (paint != null) {
                if ((n | n2 | n3 | n4 | n - n3 | n3 + n4 - (n + n2) | arrc.length - (n3 + n4)) >= 0) {
                    BaseRecordingCanvas.nDrawTextRun(this.mNativeCanvasWrapper, arrc, n, n2, n3, n4, f, f2, bl, paint.getNativeInstance(), 0L);
                    return;
                }
                throw new IndexOutOfBoundsException();
            }
            throw new NullPointerException("paint is null");
        }
        throw new NullPointerException("text is null");
    }

    @Override
    public final void drawVertices(Canvas.VertexMode vertexMode, int n, float[] arrf, int n2, float[] arrf2, int n3, int[] arrn, int n4, short[] arrs, int n5, int n6, Paint paint) {
        BaseRecordingCanvas.checkRange(arrf.length, n2, n);
        if (arrf2 != null) {
            BaseRecordingCanvas.checkRange(arrf2.length, n3, n);
        }
        if (arrn != null) {
            BaseRecordingCanvas.checkRange(arrn.length, n4, n / 2);
        }
        if (arrs != null) {
            BaseRecordingCanvas.checkRange(arrs.length, n5, n6);
        }
        BaseRecordingCanvas.nDrawVertices(this.mNativeCanvasWrapper, vertexMode.nativeInt, n, arrf, n2, arrf2, n3, arrn, n4, arrs, n5, n6, paint.getNativeInstance());
    }
}

