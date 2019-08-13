/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.CriticalNative
 *  dalvik.annotation.optimization.FastNative
 */
package android.graphics;

import android.graphics.Bitmap;
import android.graphics.CanvasProperty;
import android.graphics.Paint;
import android.graphics.RenderNode;
import android.util.Pools;
import android.view.DisplayListCanvas;
import android.view.TextureLayer;
import dalvik.annotation.optimization.CriticalNative;
import dalvik.annotation.optimization.FastNative;

public final class RecordingCanvas
extends DisplayListCanvas {
    public static final int MAX_BITMAP_SIZE = 104857600;
    private static final int POOL_LIMIT = 25;
    private static final Pools.SynchronizedPool<RecordingCanvas> sPool = new Pools.SynchronizedPool(25);
    private int mHeight;
    public RenderNode mNode;
    private int mWidth;

    protected RecordingCanvas(RenderNode renderNode, int n, int n2) {
        super(RecordingCanvas.nCreateDisplayListCanvas(renderNode.mNativeRenderNode, n, n2));
        this.mDensity = 0;
    }

    @FastNative
    private static native void nCallDrawGLFunction(long var0, long var2, Runnable var4);

    @CriticalNative
    private static native long nCreateDisplayListCanvas(long var0, int var2, int var3);

    @CriticalNative
    private static native void nDrawCircle(long var0, long var2, long var4, long var6, long var8);

    @CriticalNative
    private static native void nDrawRenderNode(long var0, long var2);

    @CriticalNative
    private static native void nDrawRoundRect(long var0, long var2, long var4, long var6, long var8, long var10, long var12, long var14);

    @CriticalNative
    private static native void nDrawTextureLayer(long var0, long var2);

    @CriticalNative
    private static native void nDrawWebViewFunctor(long var0, int var2);

    @CriticalNative
    private static native long nFinishRecording(long var0);

    @CriticalNative
    private static native int nGetMaximumTextureHeight();

    @CriticalNative
    private static native int nGetMaximumTextureWidth();

    @CriticalNative
    private static native void nInsertReorderBarrier(long var0, boolean var2);

    @CriticalNative
    private static native void nResetDisplayListCanvas(long var0, long var2, int var4, int var5);

    static RecordingCanvas obtain(RenderNode renderNode, int n, int n2) {
        if (renderNode != null) {
            RecordingCanvas recordingCanvas = sPool.acquire();
            if (recordingCanvas == null) {
                recordingCanvas = new RecordingCanvas(renderNode, n, n2);
            } else {
                RecordingCanvas.nResetDisplayListCanvas(recordingCanvas.mNativeCanvasWrapper, renderNode.mNativeRenderNode, n, n2);
            }
            recordingCanvas.mNode = renderNode;
            recordingCanvas.mWidth = n;
            recordingCanvas.mHeight = n2;
            return recordingCanvas;
        }
        throw new IllegalArgumentException("node cannot be null");
    }

    @Deprecated
    public void callDrawGLFunction2(long l) {
        RecordingCanvas.nCallDrawGLFunction(this.mNativeCanvasWrapper, l, null);
    }

    @Override
    public void disableZ() {
        RecordingCanvas.nInsertReorderBarrier(this.mNativeCanvasWrapper, false);
    }

    @Override
    public void drawCircle(CanvasProperty<Float> canvasProperty, CanvasProperty<Float> canvasProperty2, CanvasProperty<Float> canvasProperty3, CanvasProperty<Paint> canvasProperty4) {
        RecordingCanvas.nDrawCircle(this.mNativeCanvasWrapper, canvasProperty.getNativeContainer(), canvasProperty2.getNativeContainer(), canvasProperty3.getNativeContainer(), canvasProperty4.getNativeContainer());
    }

    @Deprecated
    public void drawGLFunctor2(long l, Runnable runnable) {
        RecordingCanvas.nCallDrawGLFunction(this.mNativeCanvasWrapper, l, runnable);
    }

    @Override
    public void drawRenderNode(RenderNode renderNode) {
        RecordingCanvas.nDrawRenderNode(this.mNativeCanvasWrapper, renderNode.mNativeRenderNode);
    }

    @Override
    public void drawRoundRect(CanvasProperty<Float> canvasProperty, CanvasProperty<Float> canvasProperty2, CanvasProperty<Float> canvasProperty3, CanvasProperty<Float> canvasProperty4, CanvasProperty<Float> canvasProperty5, CanvasProperty<Float> canvasProperty6, CanvasProperty<Paint> canvasProperty7) {
        RecordingCanvas.nDrawRoundRect(this.mNativeCanvasWrapper, canvasProperty.getNativeContainer(), canvasProperty2.getNativeContainer(), canvasProperty3.getNativeContainer(), canvasProperty4.getNativeContainer(), canvasProperty5.getNativeContainer(), canvasProperty6.getNativeContainer(), canvasProperty7.getNativeContainer());
    }

    public void drawTextureLayer(TextureLayer textureLayer) {
        RecordingCanvas.nDrawTextureLayer(this.mNativeCanvasWrapper, textureLayer.getLayerHandle());
    }

    public void drawWebViewFunctor(int n) {
        RecordingCanvas.nDrawWebViewFunctor(this.mNativeCanvasWrapper, n);
    }

    @Override
    public void enableZ() {
        RecordingCanvas.nInsertReorderBarrier(this.mNativeCanvasWrapper, true);
    }

    long finishRecording() {
        return RecordingCanvas.nFinishRecording(this.mNativeCanvasWrapper);
    }

    @Override
    public int getHeight() {
        return this.mHeight;
    }

    @Override
    public int getMaximumBitmapHeight() {
        return RecordingCanvas.nGetMaximumTextureHeight();
    }

    @Override
    public int getMaximumBitmapWidth() {
        return RecordingCanvas.nGetMaximumTextureWidth();
    }

    @Override
    public int getWidth() {
        return this.mWidth;
    }

    @Override
    public boolean isHardwareAccelerated() {
        return true;
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public boolean isRecordingFor(Object object) {
        boolean bl = object == this.mNode;
        return bl;
    }

    void recycle() {
        this.mNode = null;
        sPool.release(this);
    }

    @Override
    public void setBitmap(Bitmap bitmap) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setDensity(int n) {
    }

    @Override
    protected void throwIfCannotDraw(Bitmap object) {
        super.throwIfCannotDraw((Bitmap)object);
        int n = ((Bitmap)object).getByteCount();
        if (n <= 104857600) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Canvas: trying to draw too large(");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append("bytes) bitmap.");
        throw new RuntimeException(((StringBuilder)object).toString());
    }
}

