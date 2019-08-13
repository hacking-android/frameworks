/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.app.ActivityManager;
import android.graphics.Bitmap;
import android.graphics.FrameInfo;
import android.graphics.Picture;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RenderNode;
import android.graphics._$$Lambda$HardwareRenderer$FrameRenderRequest$dejdYejpuxp3nc7eP6FZ2zBu778;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.view.FrameMetricsObserver;
import android.view.IGraphicsStats;
import android.view.IGraphicsStatsCallback;
import android.view.NativeVectorDrawableAnimator;
import android.view.Surface;
import android.view.TextureLayer;
import android.view.animation.AnimationUtils;
import com.android.internal.util.VirtualRefBasePtr;
import java.io.File;
import java.io.FileDescriptor;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;
import sun.misc.Cleaner;

public class HardwareRenderer {
    private static final String CACHE_PATH_SHADERS = "com.android.opengl.shaders_cache";
    private static final String CACHE_PATH_SKIASHADERS = "com.android.skia.shaders_cache";
    public static final int FLAG_DUMP_ALL = 1;
    public static final int FLAG_DUMP_FRAMESTATS = 1;
    public static final int FLAG_DUMP_RESET = 2;
    private static final String LOG_TAG = "HardwareRenderer";
    public static final int SYNC_CONTEXT_IS_STOPPED = 4;
    public static final int SYNC_FRAME_DROPPED = 8;
    public static final int SYNC_LOST_SURFACE_REWARD_IF_FOUND = 2;
    public static final int SYNC_OK = 0;
    public static final int SYNC_REDRAW_REQUESTED = 1;
    private boolean mForceDark = false;
    private boolean mIsWideGamut = false;
    private final long mNativeProxy;
    private boolean mOpaque = true;
    private FrameRenderRequest mRenderRequest = new FrameRenderRequest();
    protected RenderNode mRootNode = RenderNode.adopt(HardwareRenderer.nCreateRootRenderNode());

    public HardwareRenderer() {
        this.mRootNode.setClipToBounds(false);
        long l = this.mNativeProxy = HardwareRenderer.nCreateProxy(true ^ this.mOpaque, this.mRootNode.mNativeRenderNode);
        if (l != 0L) {
            Cleaner.create(this, new DestroyContextRunnable(l));
            ProcessInitializer.sInstance.init(this.mNativeProxy);
            return;
        }
        throw new OutOfMemoryError("Unable to create hardware renderer");
    }

    public static int copySurfaceInto(Surface surface, Rect rect, Bitmap bitmap) {
        if (rect == null) {
            return HardwareRenderer.nCopySurfaceInto(surface, 0, 0, 0, 0, bitmap.getNativeInstance());
        }
        return HardwareRenderer.nCopySurfaceInto(surface, rect.left, rect.top, rect.right, rect.bottom, bitmap.getNativeInstance());
    }

    public static Bitmap createHardwareBitmap(RenderNode renderNode, int n, int n2) {
        return HardwareRenderer.nCreateHardwareBitmap(renderNode.mNativeRenderNode, n, n2);
    }

    public static native void disableVsync();

    public static void invokeFunctor(long l, boolean bl) {
        HardwareRenderer.nInvokeFunctor(l, bl);
    }

    static void invokePictureCapturedCallback(long l, PictureCapturedCallback pictureCapturedCallback) {
        pictureCapturedCallback.onPictureCaptured(new Picture(l));
    }

    private static native long nAddFrameMetricsObserver(long var0, FrameMetricsObserver var2);

    private static native void nAddRenderNode(long var0, long var2, boolean var4);

    private static native void nAllocateBuffers(long var0);

    private static native void nBuildLayer(long var0, long var2);

    private static native void nCancelLayerUpdate(long var0, long var2);

    private static native boolean nCopyLayerInto(long var0, long var2, long var4);

    private static native int nCopySurfaceInto(Surface var0, int var1, int var2, int var3, int var4, long var5);

    private static native Bitmap nCreateHardwareBitmap(long var0, int var2, int var3);

    private static native long nCreateProxy(boolean var0, long var1);

    private static native long nCreateRootRenderNode();

    private static native long nCreateTextureLayer(long var0);

    private static native void nDeleteProxy(long var0);

    private static native void nDestroy(long var0, long var2);

    private static native void nDestroyHardwareResources(long var0);

    private static native void nDetachSurfaceTexture(long var0, long var2);

    private static native void nDrawRenderNode(long var0, long var2);

    private static native void nDumpProfileInfo(long var0, FileDescriptor var2, int var3);

    private static native void nFence(long var0);

    private static native int nGetRenderThreadTid(long var0);

    private static native void nHackySetRTAnimationsEnabled(boolean var0);

    private static native void nInvokeFunctor(long var0, boolean var2);

    private static native boolean nLoadSystemProperties(long var0);

    private static native void nNotifyFramePending(long var0);

    private static native void nOverrideProperty(String var0, String var1);

    private static native boolean nPause(long var0);

    private static native void nPushLayerUpdate(long var0, long var2);

    private static native void nRegisterAnimatingRenderNode(long var0, long var2);

    private static native void nRegisterVectorDrawableAnimator(long var0, long var2);

    private static native void nRemoveFrameMetricsObserver(long var0, long var2);

    private static native void nRemoveRenderNode(long var0, long var2);

    private static native void nRotateProcessStatsBuffer();

    private static native void nSetContentDrawBounds(long var0, int var2, int var3, int var4, int var5);

    private static native void nSetContextPriority(int var0);

    private static native void nSetDebuggingEnabled(boolean var0);

    private static native void nSetForceDark(long var0, boolean var2);

    private static native void nSetFrameCallback(long var0, FrameDrawingCallback var2);

    private static native void nSetFrameCompleteCallback(long var0, FrameCompleteCallback var2);

    private static native void nSetHighContrastText(boolean var0);

    private static native void nSetIsolatedProcess(boolean var0);

    private static native void nSetLightAlpha(long var0, float var2, float var3);

    private static native void nSetLightGeometry(long var0, float var2, float var3, float var4, float var5);

    private static native void nSetName(long var0, String var2);

    private static native void nSetOpaque(long var0, boolean var2);

    private static native void nSetPictureCaptureCallback(long var0, PictureCapturedCallback var2);

    private static native void nSetProcessStatsBuffer(int var0);

    private static native void nSetStopped(long var0, boolean var2);

    private static native void nSetSurface(long var0, Surface var2);

    private static native void nSetWideGamut(long var0, boolean var2);

    private static native void nStopDrawing(long var0);

    private static native int nSyncAndDrawFrame(long var0, long[] var2, int var3);

    private static native void nTrimMemory(int var0);

    public static void overrideProperty(String string2, String string3) {
        if (string2 != null && string3 != null) {
            HardwareRenderer.nOverrideProperty(string2, string3);
            return;
        }
        throw new IllegalArgumentException("name and value must be non-null");
    }

    public static native void preload();

    public static void setContextPriority(int n) {
        HardwareRenderer.nSetContextPriority(n);
    }

    public static void setDebuggingEnabled(boolean bl) {
        HardwareRenderer.nSetDebuggingEnabled(bl);
    }

    public static void setFPSDivisor(int n) {
        boolean bl = true;
        if (n > 1) {
            bl = false;
        }
        HardwareRenderer.nHackySetRTAnimationsEnabled(bl);
    }

    public static void setHighContrastText(boolean bl) {
        HardwareRenderer.nSetHighContrastText(bl);
    }

    public static void setIsolatedProcess(boolean bl) {
        HardwareRenderer.nSetIsolatedProcess(bl);
    }

    public static void setPackageName(String string2) {
        ProcessInitializer.sInstance.setPackageName(string2);
    }

    public static void setupDiskCache(File file) {
        HardwareRenderer.setupShadersDiskCache(new File(file, "com.android.opengl.shaders_cache").getAbsolutePath(), new File(file, "com.android.skia.shaders_cache").getAbsolutePath());
    }

    protected static native void setupShadersDiskCache(String var0, String var1);

    public static void trimMemory(int n) {
        HardwareRenderer.nTrimMemory(n);
    }

    private static void validateAlpha(float f, String string2) {
        if (f >= 0.0f && f <= 1.0f) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" must be a valid alpha, ");
        stringBuilder.append(f);
        stringBuilder.append(" is not in the range of 0.0f to 1.0f");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static void validateFinite(float f, String string2) {
        if (Float.isFinite(f)) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" must be finite, given=");
        stringBuilder.append(f);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static void validatePositive(float f, String string2) {
        if (Float.isFinite(f) && f >= 0.0f) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" must be a finite positive, given=");
        stringBuilder.append(f);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void addFrameMetricsObserver(FrameMetricsObserver frameMetricsObserver) {
        frameMetricsObserver.mNative = new VirtualRefBasePtr(HardwareRenderer.nAddFrameMetricsObserver(this.mNativeProxy, frameMetricsObserver));
    }

    public void addRenderNode(RenderNode renderNode, boolean bl) {
        HardwareRenderer.nAddRenderNode(this.mNativeProxy, renderNode.mNativeRenderNode, bl);
    }

    public void allocateBuffers() {
        HardwareRenderer.nAllocateBuffers(this.mNativeProxy);
    }

    public void buildLayer(RenderNode renderNode) {
        if (renderNode.hasDisplayList()) {
            HardwareRenderer.nBuildLayer(this.mNativeProxy, renderNode.mNativeRenderNode);
        }
    }

    public void clearContent() {
        HardwareRenderer.nDestroyHardwareResources(this.mNativeProxy);
    }

    public boolean copyLayerInto(TextureLayer textureLayer, Bitmap bitmap) {
        return HardwareRenderer.nCopyLayerInto(this.mNativeProxy, textureLayer.getDeferredLayerUpdater(), bitmap.getNativeInstance());
    }

    public FrameRenderRequest createRenderRequest() {
        this.mRenderRequest.reset();
        return this.mRenderRequest;
    }

    public TextureLayer createTextureLayer() {
        return TextureLayer.adoptTextureLayer(this, HardwareRenderer.nCreateTextureLayer(this.mNativeProxy));
    }

    public void destroy() {
        HardwareRenderer.nDestroy(this.mNativeProxy, this.mRootNode.mNativeRenderNode);
    }

    public void detachSurfaceTexture(long l) {
        HardwareRenderer.nDetachSurfaceTexture(this.mNativeProxy, l);
    }

    public void drawRenderNode(RenderNode renderNode) {
        HardwareRenderer.nDrawRenderNode(this.mNativeProxy, renderNode.mNativeRenderNode);
    }

    public void dumpProfileInfo(FileDescriptor fileDescriptor, int n) {
        HardwareRenderer.nDumpProfileInfo(this.mNativeProxy, fileDescriptor, n);
    }

    public void fence() {
        HardwareRenderer.nFence(this.mNativeProxy);
    }

    public boolean isOpaque() {
        return this.mOpaque;
    }

    public boolean isWideGamut() {
        return this.mIsWideGamut;
    }

    public boolean loadSystemProperties() {
        return HardwareRenderer.nLoadSystemProperties(this.mNativeProxy);
    }

    public void notifyFramePending() {
        HardwareRenderer.nNotifyFramePending(this.mNativeProxy);
    }

    public void onLayerDestroyed(TextureLayer textureLayer) {
        HardwareRenderer.nCancelLayerUpdate(this.mNativeProxy, textureLayer.getDeferredLayerUpdater());
    }

    public boolean pause() {
        return HardwareRenderer.nPause(this.mNativeProxy);
    }

    public void pushLayerUpdate(TextureLayer textureLayer) {
        HardwareRenderer.nPushLayerUpdate(this.mNativeProxy, textureLayer.getDeferredLayerUpdater());
    }

    public void registerAnimatingRenderNode(RenderNode renderNode) {
        HardwareRenderer.nRegisterAnimatingRenderNode(this.mRootNode.mNativeRenderNode, renderNode.mNativeRenderNode);
    }

    public void registerVectorDrawableAnimator(NativeVectorDrawableAnimator nativeVectorDrawableAnimator) {
        HardwareRenderer.nRegisterVectorDrawableAnimator(this.mRootNode.mNativeRenderNode, nativeVectorDrawableAnimator.getAnimatorNativePtr());
    }

    public void removeFrameMetricsObserver(FrameMetricsObserver frameMetricsObserver) {
        HardwareRenderer.nRemoveFrameMetricsObserver(this.mNativeProxy, frameMetricsObserver.mNative.get());
        frameMetricsObserver.mNative = null;
    }

    public void removeRenderNode(RenderNode renderNode) {
        HardwareRenderer.nRemoveRenderNode(this.mNativeProxy, renderNode.mNativeRenderNode);
    }

    public void setContentDrawBounds(int n, int n2, int n3, int n4) {
        HardwareRenderer.nSetContentDrawBounds(this.mNativeProxy, n, n2, n3, n4);
    }

    public void setContentRoot(RenderNode renderNode) {
        RecordingCanvas recordingCanvas = this.mRootNode.beginRecording();
        if (renderNode != null) {
            recordingCanvas.drawRenderNode(renderNode);
        }
        this.mRootNode.endRecording();
    }

    public boolean setForceDark(boolean bl) {
        if (this.mForceDark != bl) {
            this.mForceDark = bl;
            HardwareRenderer.nSetForceDark(this.mNativeProxy, bl);
            return true;
        }
        return false;
    }

    public void setFrameCallback(FrameDrawingCallback frameDrawingCallback) {
        HardwareRenderer.nSetFrameCallback(this.mNativeProxy, frameDrawingCallback);
    }

    public void setFrameCompleteCallback(FrameCompleteCallback frameCompleteCallback) {
        HardwareRenderer.nSetFrameCompleteCallback(this.mNativeProxy, frameCompleteCallback);
    }

    public void setLightSourceAlpha(float f, float f2) {
        HardwareRenderer.validateAlpha(f, "ambientShadowAlpha");
        HardwareRenderer.validateAlpha(f2, "spotShadowAlpha");
        HardwareRenderer.nSetLightAlpha(this.mNativeProxy, f, f2);
    }

    public void setLightSourceGeometry(float f, float f2, float f3, float f4) {
        HardwareRenderer.validateFinite(f, "lightX");
        HardwareRenderer.validateFinite(f2, "lightY");
        HardwareRenderer.validatePositive(f3, "lightZ");
        HardwareRenderer.validatePositive(f4, "lightRadius");
        HardwareRenderer.nSetLightGeometry(this.mNativeProxy, f, f2, f3, f4);
    }

    public void setName(String string2) {
        HardwareRenderer.nSetName(this.mNativeProxy, string2);
    }

    public void setOpaque(boolean bl) {
        if (this.mOpaque != bl) {
            this.mOpaque = bl;
            HardwareRenderer.nSetOpaque(this.mNativeProxy, this.mOpaque);
        }
    }

    public void setPictureCaptureCallback(PictureCapturedCallback pictureCapturedCallback) {
        HardwareRenderer.nSetPictureCaptureCallback(this.mNativeProxy, pictureCapturedCallback);
    }

    public void setStopped(boolean bl) {
        HardwareRenderer.nSetStopped(this.mNativeProxy, bl);
    }

    public void setSurface(Surface surface) {
        if (surface != null && !surface.isValid()) {
            throw new IllegalArgumentException("Surface is invalid. surface.isValid() == false.");
        }
        HardwareRenderer.nSetSurface(this.mNativeProxy, surface);
    }

    public void setWideGamut(boolean bl) {
        this.mIsWideGamut = bl;
        HardwareRenderer.nSetWideGamut(this.mNativeProxy, bl);
    }

    public void start() {
        HardwareRenderer.nSetStopped(this.mNativeProxy, false);
    }

    public void stop() {
        HardwareRenderer.nSetStopped(this.mNativeProxy, true);
    }

    public void stopDrawing() {
        HardwareRenderer.nStopDrawing(this.mNativeProxy);
    }

    public int syncAndDrawFrame(FrameInfo frameInfo) {
        return HardwareRenderer.nSyncAndDrawFrame(this.mNativeProxy, frameInfo.frameInfo, frameInfo.frameInfo.length);
    }

    private static final class DestroyContextRunnable
    implements Runnable {
        private final long mNativeInstance;

        DestroyContextRunnable(long l) {
            this.mNativeInstance = l;
        }

        @Override
        public void run() {
            HardwareRenderer.nDeleteProxy(this.mNativeInstance);
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface DumpFlags {
    }

    public static interface FrameCompleteCallback {
        public void onFrameComplete(long var1);
    }

    public static interface FrameDrawingCallback {
        public void onFrameDraw(long var1);
    }

    public final class FrameRenderRequest {
        private FrameInfo mFrameInfo = new FrameInfo();
        private boolean mWaitForPresent;

        private FrameRenderRequest() {
        }

        static /* synthetic */ void lambda$setFrameCommitCallback$0(Executor executor, Runnable runnable, long l) {
            executor.execute(runnable);
        }

        private void reset() {
            this.mWaitForPresent = false;
            HardwareRenderer.this.mRenderRequest.setVsyncTime(AnimationUtils.currentAnimationTimeMillis() * 1000000L);
        }

        public FrameRenderRequest setFrameCommitCallback(Executor executor, Runnable runnable) {
            HardwareRenderer.this.setFrameCompleteCallback(new _$$Lambda$HardwareRenderer$FrameRenderRequest$dejdYejpuxp3nc7eP6FZ2zBu778(executor, runnable));
            return this;
        }

        public void setFrameInfo(FrameInfo frameInfo) {
            System.arraycopy(frameInfo.frameInfo, 0, this.mFrameInfo.frameInfo, 0, frameInfo.frameInfo.length);
        }

        public FrameRenderRequest setVsyncTime(long l) {
            this.mFrameInfo.setVsync(l, l);
            this.mFrameInfo.addFlags(4L);
            return this;
        }

        public FrameRenderRequest setWaitForPresent(boolean bl) {
            this.mWaitForPresent = bl;
            return this;
        }

        public int syncAndDraw() {
            int n = HardwareRenderer.this.syncAndDrawFrame(this.mFrameInfo);
            if (this.mWaitForPresent && (n & 8) == 0) {
                HardwareRenderer.this.fence();
            }
            return n;
        }
    }

    public static interface PictureCapturedCallback {
        public void onPictureCaptured(Picture var1);
    }

    private static class ProcessInitializer {
        static ProcessInitializer sInstance = new ProcessInitializer();
        private IGraphicsStatsCallback mGraphicsStatsCallback = new IGraphicsStatsCallback.Stub(){

            @Override
            public void onRotateGraphicsStatsBuffer() throws RemoteException {
                ProcessInitializer.this.rotateBuffer();
            }
        };
        private IGraphicsStats mGraphicsStatsService;
        private boolean mInitialized = false;
        private String mPackageName;

        private ProcessInitializer() {
        }

        private void initGraphicsStats() {
            IBinder iBinder;
            block4 : {
                if (this.mPackageName == null) {
                    return;
                }
                iBinder = ServiceManager.getService("graphicsstats");
                if (iBinder != null) break block4;
                return;
            }
            try {
                this.mGraphicsStatsService = IGraphicsStats.Stub.asInterface(iBinder);
                this.requestBuffer();
            }
            catch (Throwable throwable) {
                Log.w(HardwareRenderer.LOG_TAG, "Could not acquire gfx stats buffer", throwable);
            }
        }

        private void initSched(long l) {
            try {
                int n = HardwareRenderer.nGetRenderThreadTid(l);
                ActivityManager.getService().setRenderThread(n);
            }
            catch (Throwable throwable) {
                Log.w(HardwareRenderer.LOG_TAG, "Failed to set scheduler for RenderThread", throwable);
            }
        }

        private void requestBuffer() {
            try {
                ParcelFileDescriptor parcelFileDescriptor = this.mGraphicsStatsService.requestBufferForProcess(this.mPackageName, this.mGraphicsStatsCallback);
                HardwareRenderer.nSetProcessStatsBuffer(parcelFileDescriptor.getFd());
                parcelFileDescriptor.close();
            }
            catch (Throwable throwable) {
                Log.w(HardwareRenderer.LOG_TAG, "Could not acquire gfx stats buffer", throwable);
            }
        }

        private void rotateBuffer() {
            HardwareRenderer.nRotateProcessStatsBuffer();
            this.requestBuffer();
        }

        void init(long l) {
            synchronized (this) {
                block4 : {
                    boolean bl = this.mInitialized;
                    if (!bl) break block4;
                    return;
                }
                this.mInitialized = true;
                this.initSched(l);
                this.initGraphicsStats();
                return;
            }
        }

        void setPackageName(String string2) {
            synchronized (this) {
                block4 : {
                    boolean bl = this.mInitialized;
                    if (!bl) break block4;
                    return;
                }
                this.mPackageName = string2;
                return;
            }
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SyncAndDrawResult {
    }

}

