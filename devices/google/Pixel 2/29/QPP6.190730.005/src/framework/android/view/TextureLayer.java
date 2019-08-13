/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.graphics.Bitmap;
import android.graphics.HardwareRenderer;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.SurfaceTexture;
import com.android.internal.util.VirtualRefBasePtr;

public final class TextureLayer {
    private VirtualRefBasePtr mFinalizer;
    private HardwareRenderer mRenderer;

    private TextureLayer(HardwareRenderer hardwareRenderer, long l) {
        if (hardwareRenderer != null && l != 0L) {
            this.mRenderer = hardwareRenderer;
            this.mFinalizer = new VirtualRefBasePtr(l);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Either hardware renderer: ");
        stringBuilder.append(hardwareRenderer);
        stringBuilder.append(" or deferredUpdater: ");
        stringBuilder.append(l);
        stringBuilder.append(" is invalid");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static TextureLayer adoptTextureLayer(HardwareRenderer hardwareRenderer, long l) {
        return new TextureLayer(hardwareRenderer, l);
    }

    private static native boolean nPrepare(long var0, int var2, int var3, boolean var4);

    private static native void nSetLayerPaint(long var0, long var2);

    private static native void nSetSurfaceTexture(long var0, SurfaceTexture var2);

    private static native void nSetTransform(long var0, long var2);

    private static native void nUpdateSurfaceTexture(long var0);

    public boolean copyInto(Bitmap bitmap) {
        return this.mRenderer.copyLayerInto(this, bitmap);
    }

    public void destroy() {
        if (!this.isValid()) {
            return;
        }
        this.mRenderer.onLayerDestroyed(this);
        this.mRenderer = null;
        this.mFinalizer.release();
        this.mFinalizer = null;
    }

    public void detachSurfaceTexture() {
        this.mRenderer.detachSurfaceTexture(this.mFinalizer.get());
    }

    public long getDeferredLayerUpdater() {
        return this.mFinalizer.get();
    }

    public long getLayerHandle() {
        return this.mFinalizer.get();
    }

    public boolean isValid() {
        VirtualRefBasePtr virtualRefBasePtr = this.mFinalizer;
        boolean bl = virtualRefBasePtr != null && virtualRefBasePtr.get() != 0L;
        return bl;
    }

    public boolean prepare(int n, int n2, boolean bl) {
        return TextureLayer.nPrepare(this.mFinalizer.get(), n, n2, bl);
    }

    public void setLayerPaint(Paint paint) {
        long l = this.mFinalizer.get();
        long l2 = paint != null ? paint.getNativeInstance() : 0L;
        TextureLayer.nSetLayerPaint(l, l2);
        this.mRenderer.pushLayerUpdate(this);
    }

    public void setSurfaceTexture(SurfaceTexture surfaceTexture) {
        TextureLayer.nSetSurfaceTexture(this.mFinalizer.get(), surfaceTexture);
        this.mRenderer.pushLayerUpdate(this);
    }

    public void setTransform(Matrix matrix) {
        TextureLayer.nSetTransform(this.mFinalizer.get(), matrix.native_instance);
        this.mRenderer.pushLayerUpdate(this);
    }

    public void updateSurfaceTexture() {
        TextureLayer.nUpdateSurfaceTexture(this.mFinalizer.get());
        this.mRenderer.pushLayerUpdate(this);
    }
}

