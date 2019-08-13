/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.content.res.CompatibilityInfo;
import android.graphics.Canvas;
import android.graphics.GraphicBuffer;
import android.graphics.Matrix;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RenderNode;
import android.graphics.SurfaceTexture;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.SurfaceControl;
import dalvik.system.CloseGuard;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Surface
implements Parcelable {
    public static final Parcelable.Creator<Surface> CREATOR = new Parcelable.Creator<Surface>(){

        @Override
        public Surface createFromParcel(Parcel parcel) {
            try {
                Surface surface = new Surface();
                surface.readFromParcel(parcel);
                return surface;
            }
            catch (Exception exception) {
                Log.e(Surface.TAG, "Exception creating surface from parcel", exception);
                return null;
            }
        }

        public Surface[] newArray(int n) {
            return new Surface[n];
        }
    };
    public static final int ROTATION_0 = 0;
    public static final int ROTATION_180 = 2;
    public static final int ROTATION_270 = 3;
    public static final int ROTATION_90 = 1;
    public static final int SCALING_MODE_FREEZE = 0;
    public static final int SCALING_MODE_NO_SCALE_CROP = 3;
    public static final int SCALING_MODE_SCALE_CROP = 2;
    public static final int SCALING_MODE_SCALE_TO_WINDOW = 1;
    private static final String TAG = "Surface";
    private final Canvas mCanvas = new CompatibleCanvas();
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private Matrix mCompatibleMatrix;
    private int mGenerationId;
    private HwuiContext mHwuiContext;
    private boolean mIsAutoRefreshEnabled;
    private boolean mIsSharedBufferModeEnabled;
    private boolean mIsSingleBuffered;
    @UnsupportedAppUsage
    final Object mLock = new Object();
    @UnsupportedAppUsage
    private long mLockedObject;
    @UnsupportedAppUsage
    private String mName;
    @UnsupportedAppUsage
    long mNativeObject;

    @UnsupportedAppUsage
    public Surface() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    private Surface(long l) {
        Object object = this.mLock;
        synchronized (object) {
            this.setNativeObjectLocked(l);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Surface(SurfaceTexture surfaceTexture) {
        if (surfaceTexture != null) {
            this.mIsSingleBuffered = surfaceTexture.isSingleBuffered();
            Object object = this.mLock;
            synchronized (object) {
                this.mName = surfaceTexture.toString();
                this.setNativeObjectLocked(Surface.nativeCreateFromSurfaceTexture(surfaceTexture));
                return;
            }
        }
        throw new IllegalArgumentException("surfaceTexture must not be null");
    }

    public Surface(SurfaceControl surfaceControl) {
        this.copyFrom(surfaceControl);
    }

    private void checkNotReleasedLocked() {
        if (this.mNativeObject != 0L) {
            return;
        }
        throw new IllegalStateException("Surface has already been released.");
    }

    private static native long nHwuiCreate(long var0, long var2, boolean var4);

    private static native void nHwuiDestroy(long var0);

    private static native void nHwuiDraw(long var0);

    private static native void nHwuiSetSurface(long var0, long var2);

    private static native void nativeAllocateBuffers(long var0);

    private static native int nativeAttachAndQueueBuffer(long var0, GraphicBuffer var2);

    private static native long nativeCreateFromSurfaceControl(long var0);

    private static native long nativeCreateFromSurfaceTexture(SurfaceTexture var0) throws OutOfResourcesException;

    private static native int nativeForceScopedDisconnect(long var0);

    private static native long nativeGetFromSurfaceControl(long var0, long var2);

    private static native int nativeGetHeight(long var0);

    private static native long nativeGetNextFrameNumber(long var0);

    private static native int nativeGetWidth(long var0);

    private static native boolean nativeIsConsumerRunningBehind(long var0);

    private static native boolean nativeIsValid(long var0);

    private static native long nativeLockCanvas(long var0, Canvas var2, Rect var3) throws OutOfResourcesException;

    private static native long nativeReadFromParcel(long var0, Parcel var2);

    @UnsupportedAppUsage
    private static native void nativeRelease(long var0);

    private static native int nativeSetAutoRefreshEnabled(long var0, boolean var2);

    private static native int nativeSetScalingMode(long var0, int var2);

    private static native int nativeSetSharedBufferModeEnabled(long var0, boolean var2);

    private static native void nativeUnlockCanvasAndPost(long var0, Canvas var2);

    private static native void nativeWriteToParcel(long var0, Parcel var2);

    public static String rotationToString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return Integer.toString(n);
                    }
                    return "ROTATION_270";
                }
                return "ROTATION_180";
            }
            return "ROTATION_90";
        }
        return "ROTATION_0";
    }

    private void setNativeObjectLocked(long l) {
        long l2 = this.mNativeObject;
        if (l2 != l) {
            if (l2 == 0L && l != 0L) {
                this.mCloseGuard.open("release");
            } else if (this.mNativeObject != 0L && l == 0L) {
                this.mCloseGuard.close();
            }
            this.mNativeObject = l;
            ++this.mGenerationId;
            HwuiContext hwuiContext = this.mHwuiContext;
            if (hwuiContext != null) {
                hwuiContext.updateSurface();
            }
        }
    }

    private void unlockSwCanvasAndPost(Canvas canvas) {
        if (canvas == this.mCanvas) {
            long l;
            if (this.mNativeObject != this.mLockedObject) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("WARNING: Surface's mNativeObject (0x");
                stringBuilder.append(Long.toHexString(this.mNativeObject));
                stringBuilder.append(") != mLockedObject (0x");
                stringBuilder.append(Long.toHexString(this.mLockedObject));
                stringBuilder.append(")");
                Log.w("Surface", stringBuilder.toString());
            }
            if ((l = this.mLockedObject) != 0L) {
                try {
                    Surface.nativeUnlockCanvasAndPost(l, canvas);
                    return;
                }
                finally {
                    Surface.nativeRelease(this.mLockedObject);
                    this.mLockedObject = 0L;
                }
            }
            throw new IllegalStateException("Surface was not locked");
        }
        throw new IllegalArgumentException("canvas object must be the same instance that was previously returned by lockCanvas");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void allocateBuffers() {
        Object object = this.mLock;
        synchronized (object) {
            this.checkNotReleasedLocked();
            Surface.nativeAllocateBuffers(this.mNativeObject);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void attachAndQueueBuffer(GraphicBuffer object) {
        Object object2 = this.mLock;
        synchronized (object2) {
            this.checkNotReleasedLocked();
            if (Surface.nativeAttachAndQueueBuffer(this.mNativeObject, (GraphicBuffer)object) == 0) {
                return;
            }
            object = new RuntimeException("Failed to attach and queue buffer to Surface (bad object?)");
            throw object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void copyFrom(SurfaceControl surfaceControl) {
        if (surfaceControl == null) {
            throw new IllegalArgumentException("other must not be null");
        }
        long l = surfaceControl.mNativeObject;
        if (l == 0L) {
            throw new NullPointerException("null SurfaceControl native object. Are you using a released SurfaceControl?");
        }
        l = Surface.nativeGetFromSurfaceControl(this.mNativeObject, l);
        Object object = this.mLock;
        synchronized (object) {
            if (l == this.mNativeObject) {
                return;
            }
            if (this.mNativeObject != 0L) {
                Surface.nativeRelease(this.mNativeObject);
            }
            this.setNativeObjectLocked(l);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void createFrom(SurfaceControl surfaceControl) {
        if (surfaceControl == null) {
            throw new IllegalArgumentException("other must not be null");
        }
        long l = surfaceControl.mNativeObject;
        if (l == 0L) {
            throw new NullPointerException("null SurfaceControl native object. Are you using a released SurfaceControl?");
        }
        l = Surface.nativeCreateFromSurfaceControl(l);
        Object object = this.mLock;
        synchronized (object) {
            if (this.mNativeObject != 0L) {
                Surface.nativeRelease(this.mNativeObject);
            }
            this.setNativeObjectLocked(l);
            return;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @UnsupportedAppUsage
    public void destroy() {
        this.release();
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            this.release();
            return;
        }
        finally {
            super.finalize();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void forceScopedDisconnect() {
        Object object = this.mLock;
        synchronized (object) {
            this.checkNotReleasedLocked();
            if (Surface.nativeForceScopedDisconnect(this.mNativeObject) == 0) {
                return;
            }
            RuntimeException runtimeException = new RuntimeException("Failed to disconnect Surface instance (bad object?)");
            throw runtimeException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getGenerationId() {
        Object object = this.mLock;
        synchronized (object) {
            return this.mGenerationId;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public long getNextFrameNumber() {
        Object object = this.mLock;
        synchronized (object) {
            this.checkNotReleasedLocked();
            return Surface.nativeGetNextFrameNumber(this.mNativeObject);
        }
    }

    public void hwuiDestroy() {
        HwuiContext hwuiContext = this.mHwuiContext;
        if (hwuiContext != null) {
            hwuiContext.destroy();
            this.mHwuiContext = null;
        }
    }

    public boolean isAutoRefreshEnabled() {
        return this.mIsAutoRefreshEnabled;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isConsumerRunningBehind() {
        Object object = this.mLock;
        synchronized (object) {
            this.checkNotReleasedLocked();
            return Surface.nativeIsConsumerRunningBehind(this.mNativeObject);
        }
    }

    public boolean isSharedBufferModeEnabled() {
        return this.mIsSharedBufferModeEnabled;
    }

    public boolean isSingleBuffered() {
        return this.mIsSingleBuffered;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isValid() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mNativeObject != 0L) return Surface.nativeIsValid(this.mNativeObject);
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Canvas lockCanvas(Rect object) throws OutOfResourcesException, IllegalArgumentException {
        Object object2 = this.mLock;
        synchronized (object2) {
            this.checkNotReleasedLocked();
            if (this.mLockedObject == 0L) {
                this.mLockedObject = Surface.nativeLockCanvas(this.mNativeObject, this.mCanvas, (Rect)object);
                return this.mCanvas;
            }
            object = new IllegalArgumentException("Surface was already locked");
            throw object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Canvas lockHardwareCanvas() {
        Object object = this.mLock;
        synchronized (object) {
            this.checkNotReleasedLocked();
            if (this.mHwuiContext != null) return this.mHwuiContext.lockCanvas(Surface.nativeGetWidth(this.mNativeObject), Surface.nativeGetHeight(this.mNativeObject));
            Object object2 = new HwuiContext(false);
            this.mHwuiContext = object2;
            return this.mHwuiContext.lockCanvas(Surface.nativeGetWidth(this.mNativeObject), Surface.nativeGetHeight(this.mNativeObject));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Canvas lockHardwareWideColorGamutCanvas() {
        Object object = this.mLock;
        synchronized (object) {
            this.checkNotReleasedLocked();
            if (this.mHwuiContext != null && !this.mHwuiContext.isWideColorGamut()) {
                this.mHwuiContext.destroy();
                this.mHwuiContext = null;
            }
            if (this.mHwuiContext != null) return this.mHwuiContext.lockCanvas(Surface.nativeGetWidth(this.mNativeObject), Surface.nativeGetHeight(this.mNativeObject));
            Object object2 = new HwuiContext(true);
            this.mHwuiContext = object2;
            return this.mHwuiContext.lockCanvas(Surface.nativeGetWidth(this.mNativeObject), Surface.nativeGetHeight(this.mNativeObject));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void readFromParcel(Parcel parcel) {
        if (parcel == null) {
            throw new IllegalArgumentException("source must not be null");
        }
        Object object = this.mLock;
        synchronized (object) {
            this.mName = parcel.readString();
            boolean bl = parcel.readInt() != 0;
            this.mIsSingleBuffered = bl;
            this.setNativeObjectLocked(Surface.nativeReadFromParcel(this.mNativeObject, parcel));
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void release() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mNativeObject != 0L) {
                Surface.nativeRelease(this.mNativeObject);
                this.setNativeObjectLocked(0L);
            }
            if (this.mHwuiContext != null) {
                this.mHwuiContext.destroy();
                this.mHwuiContext = null;
            }
            return;
        }
    }

    public void setAutoRefreshEnabled(boolean bl) {
        if (this.mIsAutoRefreshEnabled != bl) {
            if (Surface.nativeSetAutoRefreshEnabled(this.mNativeObject, bl) == 0) {
                this.mIsAutoRefreshEnabled = bl;
            } else {
                throw new RuntimeException("Failed to set auto refresh on Surface (bad object?)");
            }
        }
    }

    void setCompatibilityTranslator(CompatibilityInfo.Translator translator) {
        if (translator != null) {
            float f = translator.applicationScale;
            this.mCompatibleMatrix = new Matrix();
            this.mCompatibleMatrix.setScale(f, f);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void setScalingMode(int n) {
        Object object = this.mLock;
        synchronized (object) {
            this.checkNotReleasedLocked();
            if (Surface.nativeSetScalingMode(this.mNativeObject, n) == 0) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid scaling mode: ");
            stringBuilder.append(n);
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException(stringBuilder.toString());
            throw illegalArgumentException;
        }
    }

    public void setSharedBufferModeEnabled(boolean bl) {
        if (this.mIsSharedBufferModeEnabled != bl) {
            if (Surface.nativeSetSharedBufferModeEnabled(this.mNativeObject, bl) == 0) {
                this.mIsSharedBufferModeEnabled = bl;
            } else {
                throw new RuntimeException("Failed to set shared buffer mode on Surface (bad object?)");
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String toString() {
        Object object = this.mLock;
        synchronized (object) {
            CharSequence charSequence = new StringBuilder();
            charSequence.append("Surface(name=");
            charSequence.append(this.mName);
            charSequence.append(")/@0x");
            charSequence.append(Integer.toHexString(System.identityHashCode(this)));
            return charSequence.toString();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    @UnsupportedAppUsage
    public void transferFrom(Surface surface) {
        if (surface == null) {
            throw new IllegalArgumentException("other must not be null");
        }
        if (surface != this) {
            long l;
            Object object = surface.mLock;
            synchronized (object) {
                l = surface.mNativeObject;
                surface.setNativeObjectLocked(0L);
            }
            object = this.mLock;
            synchronized (object) {
                if (this.mNativeObject != 0L) {
                    Surface.nativeRelease(this.mNativeObject);
                }
                this.setNativeObjectLocked(l);
            }
        }
    }

    @Deprecated
    public void unlockCanvas(Canvas canvas) {
        throw new UnsupportedOperationException();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unlockCanvasAndPost(Canvas canvas) {
        Object object = this.mLock;
        synchronized (object) {
            this.checkNotReleasedLocked();
            if (this.mHwuiContext != null) {
                this.mHwuiContext.unlockAndPost(canvas);
            } else {
                this.unlockSwCanvasAndPost(canvas);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void writeToParcel(Parcel parcel, int n) {
        if (parcel == null) throw new IllegalArgumentException("dest must not be null");
        Object object = this.mLock;
        // MONITORENTER : object
        parcel.writeString(this.mName);
        int n2 = this.mIsSingleBuffered ? 1 : 0;
        parcel.writeInt(n2);
        Surface.nativeWriteToParcel(this.mNativeObject, parcel);
        // MONITOREXIT : object
        if ((n & 1) == 0) return;
        this.release();
    }

    private final class CompatibleCanvas
    extends Canvas {
        private Matrix mOrigMatrix = null;

        private CompatibleCanvas() {
        }

        @Override
        public void getMatrix(Matrix matrix) {
            super.getMatrix(matrix);
            if (this.mOrigMatrix == null) {
                this.mOrigMatrix = new Matrix();
            }
            this.mOrigMatrix.set(matrix);
        }

        @Override
        public void setMatrix(Matrix matrix) {
            Matrix matrix2;
            if (Surface.this.mCompatibleMatrix != null && (matrix2 = this.mOrigMatrix) != null && !matrix2.equals(matrix)) {
                matrix2 = new Matrix(Surface.this.mCompatibleMatrix);
                matrix2.preConcat(matrix);
                super.setMatrix(matrix2);
            } else {
                super.setMatrix(matrix);
            }
        }
    }

    private final class HwuiContext {
        private RecordingCanvas mCanvas;
        private long mHwuiRenderer;
        private final boolean mIsWideColorGamut;
        private final RenderNode mRenderNode = RenderNode.create("HwuiCanvas", null);

        HwuiContext(boolean bl) {
            this.mRenderNode.setClipToBounds(false);
            this.mRenderNode.setForceDarkAllowed(false);
            this.mIsWideColorGamut = bl;
            this.mHwuiRenderer = Surface.nHwuiCreate(this.mRenderNode.mNativeRenderNode, Surface.this.mNativeObject, bl);
        }

        void destroy() {
            long l = this.mHwuiRenderer;
            if (l != 0L) {
                Surface.nHwuiDestroy(l);
                this.mHwuiRenderer = 0L;
            }
        }

        boolean isWideColorGamut() {
            return this.mIsWideColorGamut;
        }

        Canvas lockCanvas(int n, int n2) {
            if (this.mCanvas == null) {
                this.mCanvas = this.mRenderNode.beginRecording(n, n2);
                return this.mCanvas;
            }
            throw new IllegalStateException("Surface was already locked!");
        }

        void unlockAndPost(Canvas canvas) {
            if (canvas == this.mCanvas) {
                this.mRenderNode.endRecording();
                this.mCanvas = null;
                Surface.nHwuiDraw(this.mHwuiRenderer);
                return;
            }
            throw new IllegalArgumentException("canvas object must be the same instance that was previously returned by lockCanvas");
        }

        void updateSurface() {
            Surface.nHwuiSetSurface(this.mHwuiRenderer, Surface.this.mNativeObject);
        }
    }

    public static class OutOfResourcesException
    extends RuntimeException {
        public OutOfResourcesException() {
        }

        public OutOfResourcesException(String string2) {
            super(string2);
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Rotation {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ScalingMode {
    }

}

