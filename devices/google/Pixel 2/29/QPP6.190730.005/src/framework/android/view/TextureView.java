/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.TextureLayer;
import android.view.ThreadedRenderer;
import android.view.View;

public class TextureView
extends View {
    private static final String LOG_TAG = "TextureView";
    private Canvas mCanvas;
    private boolean mHadSurface;
    @UnsupportedAppUsage
    private TextureLayer mLayer;
    private SurfaceTextureListener mListener;
    private final Object[] mLock = new Object[0];
    private final Matrix mMatrix = new Matrix();
    private boolean mMatrixChanged;
    @UnsupportedAppUsage
    private long mNativeWindow;
    private final Object[] mNativeWindowLock = new Object[0];
    @UnsupportedAppUsage
    private boolean mOpaque = true;
    private int mSaveCount;
    @UnsupportedAppUsage
    private SurfaceTexture mSurface;
    private boolean mUpdateLayer;
    @UnsupportedAppUsage
    private final SurfaceTexture.OnFrameAvailableListener mUpdateListener = new SurfaceTexture.OnFrameAvailableListener(){

        @Override
        public void onFrameAvailable(SurfaceTexture surfaceTexture) {
            TextureView.this.updateLayer();
            TextureView.this.invalidate();
        }
    };
    @UnsupportedAppUsage
    private boolean mUpdateSurface;

    public TextureView(Context context) {
        super(context);
    }

    public TextureView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public TextureView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    public TextureView(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
    }

    private void applyTransformMatrix() {
        TextureLayer textureLayer;
        if (this.mMatrixChanged && (textureLayer = this.mLayer) != null) {
            textureLayer.setTransform(this.mMatrix);
            this.mMatrixChanged = false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void applyUpdate() {
        if (this.mLayer == null) {
            return;
        }
        Object[] arrobject = this.mLock;
        synchronized (arrobject) {
            if (!this.mUpdateLayer) {
                return;
            }
            this.mUpdateLayer = false;
        }
        this.mLayer.prepare(this.getWidth(), this.getHeight(), this.mOpaque);
        this.mLayer.updateSurfaceTexture();
        SurfaceTextureListener surfaceTextureListener = this.mListener;
        if (surfaceTextureListener != null) {
            surfaceTextureListener.onSurfaceTextureUpdated(this.mSurface);
        }
    }

    @UnsupportedAppUsage
    private void destroyHardwareLayer() {
        TextureLayer textureLayer = this.mLayer;
        if (textureLayer != null) {
            textureLayer.detachSurfaceTexture();
            this.mLayer.destroy();
            this.mLayer = null;
            this.mMatrixChanged = true;
        }
    }

    @UnsupportedAppUsage
    private native void nCreateNativeWindow(SurfaceTexture var1);

    @UnsupportedAppUsage
    private native void nDestroyNativeWindow();

    private static native boolean nLockCanvas(long var0, Canvas var2, Rect var3);

    private static native void nUnlockCanvasAndPost(long var0, Canvas var2);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void releaseSurfaceTexture() {
        Object[] arrobject = this.mSurface;
        if (arrobject == null) return;
        boolean bl = true;
        SurfaceTextureListener surfaceTextureListener = this.mListener;
        if (surfaceTextureListener != null) {
            bl = surfaceTextureListener.onSurfaceTextureDestroyed((SurfaceTexture)arrobject);
        }
        arrobject = this.mNativeWindowLock;
        // MONITORENTER : arrobject
        this.nDestroyNativeWindow();
        // MONITOREXIT : arrobject
        if (bl) {
            this.mSurface.release();
        }
        this.mSurface = null;
        this.mHadSurface = true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void updateLayer() {
        Object[] arrobject = this.mLock;
        synchronized (arrobject) {
            this.mUpdateLayer = true;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void updateLayerAndInvalidate() {
        Object[] arrobject = this.mLock;
        synchronized (arrobject) {
            this.mUpdateLayer = true;
        }
        this.invalidate();
    }

    @Override
    public void buildLayer() {
    }

    @UnsupportedAppUsage
    @Override
    protected void destroyHardwareResources() {
        super.destroyHardwareResources();
        this.destroyHardwareLayer();
    }

    @Override
    public final void draw(Canvas canvas) {
        this.mPrivateFlags = this.mPrivateFlags & -2097153 | 32;
        if (canvas.isHardwareAccelerated()) {
            canvas = (RecordingCanvas)canvas;
            TextureLayer textureLayer = this.getTextureLayer();
            if (textureLayer != null) {
                this.applyUpdate();
                this.applyTransformMatrix();
                this.mLayer.setLayerPaint(this.mLayerPaint);
                ((RecordingCanvas)canvas).drawTextureLayer(textureLayer);
            }
        }
    }

    public Bitmap getBitmap() {
        return this.getBitmap(this.getWidth(), this.getHeight());
    }

    public Bitmap getBitmap(int n, int n2) {
        if (this.isAvailable() && n > 0 && n2 > 0) {
            return this.getBitmap(Bitmap.createBitmap(this.getResources().getDisplayMetrics(), n, n2, Bitmap.Config.ARGB_8888));
        }
        return null;
    }

    public Bitmap getBitmap(Bitmap bitmap) {
        if (bitmap != null && this.isAvailable()) {
            TextureLayer textureLayer;
            this.applyUpdate();
            this.applyTransformMatrix();
            if (this.mLayer == null && this.mUpdateSurface) {
                this.getTextureLayer();
            }
            if ((textureLayer = this.mLayer) != null) {
                textureLayer.copyInto(bitmap);
            }
        }
        return bitmap;
    }

    @Override
    public int getLayerType() {
        return 2;
    }

    public SurfaceTexture getSurfaceTexture() {
        return this.mSurface;
    }

    public SurfaceTextureListener getSurfaceTextureListener() {
        return this.mListener;
    }

    TextureLayer getTextureLayer() {
        if (this.mLayer == null) {
            if (this.mAttachInfo != null && this.mAttachInfo.mThreadedRenderer != null) {
                this.mLayer = this.mAttachInfo.mThreadedRenderer.createTextureLayer();
                boolean bl = this.mSurface == null;
                if (bl) {
                    this.mSurface = new SurfaceTexture(false);
                    this.nCreateNativeWindow(this.mSurface);
                }
                this.mLayer.setSurfaceTexture(this.mSurface);
                this.mSurface.setDefaultBufferSize(this.getWidth(), this.getHeight());
                this.mSurface.setOnFrameAvailableListener(this.mUpdateListener, this.mAttachInfo.mHandler);
                SurfaceTextureListener surfaceTextureListener = this.mListener;
                if (surfaceTextureListener != null && bl) {
                    surfaceTextureListener.onSurfaceTextureAvailable(this.mSurface, this.getWidth(), this.getHeight());
                }
                this.mLayer.setLayerPaint(this.mLayerPaint);
            } else {
                return null;
            }
        }
        if (this.mUpdateSurface) {
            this.mUpdateSurface = false;
            this.updateLayer();
            this.mMatrixChanged = true;
            this.mLayer.setSurfaceTexture(this.mSurface);
            this.mSurface.setDefaultBufferSize(this.getWidth(), this.getHeight());
        }
        return this.mLayer;
    }

    public Matrix getTransform(Matrix matrix) {
        Matrix matrix2 = matrix;
        if (matrix == null) {
            matrix2 = new Matrix();
        }
        matrix2.set(this.mMatrix);
        return matrix2;
    }

    public boolean isAvailable() {
        boolean bl = this.mSurface != null;
        return bl;
    }

    @Override
    public boolean isOpaque() {
        return this.mOpaque;
    }

    public Canvas lockCanvas() {
        return this.lockCanvas(null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Canvas lockCanvas(Rect rect) {
        if (!this.isAvailable()) {
            return null;
        }
        if (this.mCanvas == null) {
            this.mCanvas = new Canvas();
        }
        Object[] arrobject = this.mNativeWindowLock;
        synchronized (arrobject) {
            if (!TextureView.nLockCanvas(this.mNativeWindow, this.mCanvas, rect)) {
                return null;
            }
        }
        this.mSaveCount = this.mCanvas.save();
        return this.mCanvas;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!this.isHardwareAccelerated()) {
            Log.w("TextureView", "A TextureView or a subclass can only be used with hardware acceleration enabled.");
        }
        if (this.mHadSurface) {
            this.invalidate(true);
            this.mHadSurface = false;
        }
    }

    @UnsupportedAppUsage
    @Override
    protected void onDetachedFromWindowInternal() {
        this.destroyHardwareLayer();
        this.releaseSurfaceTexture();
        super.onDetachedFromWindowInternal();
    }

    @Override
    protected final void onDraw(Canvas canvas) {
    }

    @Override
    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        Object object = this.mSurface;
        if (object != null) {
            ((SurfaceTexture)object).setDefaultBufferSize(this.getWidth(), this.getHeight());
            this.updateLayer();
            object = this.mListener;
            if (object != null) {
                object.onSurfaceTextureSizeChanged(this.mSurface, this.getWidth(), this.getHeight());
            }
        }
    }

    @Override
    protected void onVisibilityChanged(View object, int n) {
        super.onVisibilityChanged((View)object, n);
        object = this.mSurface;
        if (object != null) {
            if (n == 0) {
                if (this.mLayer != null) {
                    ((SurfaceTexture)object).setOnFrameAvailableListener(this.mUpdateListener, this.mAttachInfo.mHandler);
                }
                this.updateLayerAndInvalidate();
            } else {
                ((SurfaceTexture)object).setOnFrameAvailableListener(null);
            }
        }
    }

    @Override
    public void setBackgroundDrawable(Drawable drawable2) {
        if (drawable2 != null && !sTextureViewIgnoresDrawableSetters) {
            throw new UnsupportedOperationException("TextureView doesn't support displaying a background drawable");
        }
    }

    @Override
    public void setForeground(Drawable drawable2) {
        if (drawable2 != null && !sTextureViewIgnoresDrawableSetters) {
            throw new UnsupportedOperationException("TextureView doesn't support displaying a foreground drawable");
        }
    }

    @Override
    public void setLayerPaint(Paint paint) {
        if (paint != this.mLayerPaint) {
            this.mLayerPaint = paint;
            this.invalidate();
        }
    }

    @Override
    public void setLayerType(int n, Paint paint) {
        this.setLayerPaint(paint);
    }

    public void setOpaque(boolean bl) {
        if (bl != this.mOpaque) {
            this.mOpaque = bl;
            if (this.mLayer != null) {
                this.updateLayerAndInvalidate();
            }
        }
    }

    public void setSurfaceTexture(SurfaceTexture surfaceTexture) {
        if (surfaceTexture != null) {
            if (surfaceTexture != this.mSurface) {
                if (!surfaceTexture.isReleased()) {
                    if (this.mSurface != null) {
                        this.nDestroyNativeWindow();
                        this.mSurface.release();
                    }
                    this.mSurface = surfaceTexture;
                    this.nCreateNativeWindow(this.mSurface);
                    if ((this.mViewFlags & 12) == 0 && this.mLayer != null) {
                        this.mSurface.setOnFrameAvailableListener(this.mUpdateListener, this.mAttachInfo.mHandler);
                    }
                    this.mUpdateSurface = true;
                    this.invalidateParentIfNeeded();
                    return;
                }
                throw new IllegalArgumentException("Cannot setSurfaceTexture to a released SurfaceTexture");
            }
            throw new IllegalArgumentException("Trying to setSurfaceTexture to the same SurfaceTexture that's already set.");
        }
        throw new NullPointerException("surfaceTexture must not be null");
    }

    public void setSurfaceTextureListener(SurfaceTextureListener surfaceTextureListener) {
        this.mListener = surfaceTextureListener;
    }

    public void setTransform(Matrix matrix) {
        this.mMatrix.set(matrix);
        this.mMatrixChanged = true;
        this.invalidateParentIfNeeded();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unlockCanvasAndPost(Canvas canvas) {
        Object[] arrobject = this.mCanvas;
        if (arrobject == null) return;
        if (canvas != arrobject) return;
        canvas.restoreToCount(this.mSaveCount);
        this.mSaveCount = 0;
        arrobject = this.mNativeWindowLock;
        synchronized (arrobject) {
            TextureView.nUnlockCanvasAndPost(this.mNativeWindow, this.mCanvas);
            return;
        }
    }

    public static interface SurfaceTextureListener {
        public void onSurfaceTextureAvailable(SurfaceTexture var1, int var2, int var3);

        public boolean onSurfaceTextureDestroyed(SurfaceTexture var1);

        public void onSurfaceTextureSizeChanged(SurfaceTexture var1, int var2, int var3);

        public void onSurfaceTextureUpdated(SurfaceTexture var1);
    }

}

