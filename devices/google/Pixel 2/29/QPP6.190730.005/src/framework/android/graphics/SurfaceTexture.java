/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.annotation.UnsupportedAppUsage;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Surface;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

public class SurfaceTexture {
    private final Looper mCreatorLooper = Looper.myLooper();
    @UnsupportedAppUsage
    private long mFrameAvailableListener;
    private boolean mIsSingleBuffered;
    @UnsupportedAppUsage
    private Handler mOnFrameAvailableHandler;
    @UnsupportedAppUsage
    private long mProducer;
    @UnsupportedAppUsage
    private long mSurfaceTexture;

    public SurfaceTexture(int n) {
        this(n, false);
    }

    public SurfaceTexture(int n, boolean bl) {
        this.mIsSingleBuffered = bl;
        this.nativeInit(false, n, bl, new WeakReference<SurfaceTexture>(this));
    }

    public SurfaceTexture(boolean bl) {
        this.mIsSingleBuffered = bl;
        this.nativeInit(true, 0, bl, new WeakReference<SurfaceTexture>(this));
    }

    private native int nativeAttachToGLContext(int var1);

    @UnsupportedAppUsage
    private native int nativeDetachFromGLContext();

    private native void nativeFinalize();

    private native long nativeGetTimestamp();

    private native void nativeGetTransformMatrix(float[] var1);

    private native void nativeInit(boolean var1, int var2, boolean var3, WeakReference<SurfaceTexture> var4) throws Surface.OutOfResourcesException;

    private native boolean nativeIsReleased();

    private native void nativeRelease();

    private native void nativeReleaseTexImage();

    private native void nativeSetDefaultBufferSize(int var1, int var2);

    private native void nativeUpdateTexImage();

    @UnsupportedAppUsage
    private static void postEventFromNative(WeakReference<SurfaceTexture> object) {
        if ((object = (SurfaceTexture)((Reference)object).get()) != null && (object = ((SurfaceTexture)object).mOnFrameAvailableHandler) != null) {
            ((Handler)object).sendEmptyMessage(0);
        }
    }

    public void attachToGLContext(int n) {
        if (this.nativeAttachToGLContext(n) == 0) {
            return;
        }
        throw new RuntimeException("Error during attachToGLContext (see logcat for details)");
    }

    public void detachFromGLContext() {
        if (this.nativeDetachFromGLContext() == 0) {
            return;
        }
        throw new RuntimeException("Error during detachFromGLContext (see logcat for details)");
    }

    protected void finalize() throws Throwable {
        try {
            this.nativeFinalize();
            return;
        }
        finally {
            super.finalize();
        }
    }

    public long getTimestamp() {
        return this.nativeGetTimestamp();
    }

    public void getTransformMatrix(float[] arrf) {
        if (arrf.length == 16) {
            this.nativeGetTransformMatrix(arrf);
            return;
        }
        throw new IllegalArgumentException();
    }

    public boolean isReleased() {
        return this.nativeIsReleased();
    }

    public boolean isSingleBuffered() {
        return this.mIsSingleBuffered;
    }

    public void release() {
        this.nativeRelease();
    }

    public void releaseTexImage() {
        this.nativeReleaseTexImage();
    }

    public void setDefaultBufferSize(int n, int n2) {
        this.nativeSetDefaultBufferSize(n, n2);
    }

    public void setOnFrameAvailableListener(OnFrameAvailableListener onFrameAvailableListener) {
        this.setOnFrameAvailableListener(onFrameAvailableListener, null);
    }

    public void setOnFrameAvailableListener(final OnFrameAvailableListener onFrameAvailableListener, Handler object) {
        if (onFrameAvailableListener != null) {
            if (object != null) {
                object = ((Handler)object).getLooper();
            } else {
                object = this.mCreatorLooper;
                if (object == null) {
                    object = Looper.getMainLooper();
                }
            }
            this.mOnFrameAvailableHandler = new Handler((Looper)object, null, true){

                @Override
                public void handleMessage(Message message) {
                    onFrameAvailableListener.onFrameAvailable(SurfaceTexture.this);
                }
            };
        } else {
            this.mOnFrameAvailableHandler = null;
        }
    }

    public void updateTexImage() {
        this.nativeUpdateTexImage();
    }

    public static interface OnFrameAvailableListener {
        public void onFrameAvailable(SurfaceTexture var1);
    }

    @Deprecated
    public static class OutOfResourcesException
    extends Exception {
        public OutOfResourcesException() {
        }

        public OutOfResourcesException(String string2) {
            super(string2);
        }
    }

}

