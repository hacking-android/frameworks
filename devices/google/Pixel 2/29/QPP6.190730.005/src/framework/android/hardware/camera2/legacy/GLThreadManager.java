/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.legacy;

import android.graphics.SurfaceTexture;
import android.hardware.camera2.legacy.CameraDeviceState;
import android.hardware.camera2.legacy.CaptureCollector;
import android.hardware.camera2.legacy.RequestHandlerThread;
import android.hardware.camera2.legacy.RequestThreadManager;
import android.hardware.camera2.legacy.SurfaceTextureRenderer;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Pair;
import android.util.Size;
import android.view.Surface;
import com.android.internal.util.Preconditions;
import java.util.Collection;

public class GLThreadManager {
    private static final boolean DEBUG = false;
    private static final int MSG_ALLOW_FRAMES = 5;
    private static final int MSG_CLEANUP = 3;
    private static final int MSG_DROP_FRAMES = 4;
    private static final int MSG_NEW_CONFIGURATION = 1;
    private static final int MSG_NEW_FRAME = 2;
    private final String TAG;
    private CaptureCollector mCaptureCollector;
    private final CameraDeviceState mDeviceState;
    private final Handler.Callback mGLHandlerCb = new Handler.Callback(){
        private boolean mCleanup = false;
        private boolean mConfigured = false;
        private boolean mDroppingFrames = false;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean handleMessage(Message object) {
            if (this.mCleanup) {
                return true;
            }
            try {
                int n = ((Message)object).what;
                if (n == -1) return true;
                if (n == 1) {
                    object = (ConfigureHolder)((Message)object).obj;
                    GLThreadManager.this.mTextureRenderer.cleanupEGLContext();
                    GLThreadManager.this.mTextureRenderer.configureSurfaces(((ConfigureHolder)object).surfaces);
                    GLThreadManager.this.mCaptureCollector = Preconditions.checkNotNull(((ConfigureHolder)object).collector);
                    ((ConfigureHolder)object).condition.open();
                    this.mConfigured = true;
                    return true;
                }
                if (n != 2) {
                    if (n == 3) {
                        GLThreadManager.this.mTextureRenderer.cleanupEGLContext();
                        this.mCleanup = true;
                        this.mConfigured = false;
                        return true;
                    }
                    if (n == 4) {
                        this.mDroppingFrames = true;
                        return true;
                    }
                    if (n != 5) {
                        String string2 = GLThreadManager.this.TAG;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unhandled message ");
                        stringBuilder.append(((Message)object).what);
                        stringBuilder.append(" on GLThread.");
                        Log.e(string2, stringBuilder.toString());
                        return true;
                    }
                    this.mDroppingFrames = false;
                    return true;
                }
                if (this.mDroppingFrames) {
                    Log.w(GLThreadManager.this.TAG, "Ignoring frame.");
                    return true;
                }
                if (!this.mConfigured) {
                    Log.e(GLThreadManager.this.TAG, "Dropping frame, EGL context not configured!");
                }
                GLThreadManager.this.mTextureRenderer.drawIntoSurfaces(GLThreadManager.this.mCaptureCollector);
                return true;
            }
            catch (Exception exception) {
                Log.e(GLThreadManager.this.TAG, "Received exception on GL render thread: ", exception);
                GLThreadManager.this.mDeviceState.setError(1);
            }
            return true;
        }
    };
    private final RequestHandlerThread mGLHandlerThread;
    private final RequestThreadManager.FpsCounter mPrevCounter = new RequestThreadManager.FpsCounter("GL Preview Producer");
    private final SurfaceTextureRenderer mTextureRenderer;

    public GLThreadManager(int n, int n2, CameraDeviceState cameraDeviceState) {
        this.mTextureRenderer = new SurfaceTextureRenderer(n2);
        this.TAG = String.format("CameraDeviceGLThread-%d", n);
        this.mGLHandlerThread = new RequestHandlerThread(this.TAG, this.mGLHandlerCb);
        this.mDeviceState = cameraDeviceState;
    }

    public void allowNewFrames() {
        this.mGLHandlerThread.getHandler().sendEmptyMessage(5);
    }

    public SurfaceTexture getCurrentSurfaceTexture() {
        return this.mTextureRenderer.getSurfaceTexture();
    }

    public void ignoreNewFrames() {
        this.mGLHandlerThread.getHandler().sendEmptyMessage(4);
    }

    public void queueNewFrame() {
        Handler handler = this.mGLHandlerThread.getHandler();
        if (!handler.hasMessages(2)) {
            handler.sendMessage(handler.obtainMessage(2));
        } else {
            Log.e(this.TAG, "GLThread dropping frame.  Not consuming frames quickly enough!");
        }
    }

    public void quit() {
        Handler handler = this.mGLHandlerThread.getHandler();
        handler.sendMessageAtFrontOfQueue(handler.obtainMessage(3));
        this.mGLHandlerThread.quitSafely();
        try {
            this.mGLHandlerThread.join();
        }
        catch (InterruptedException interruptedException) {
            Log.e(this.TAG, String.format("Thread %s (%d) interrupted while quitting.", this.mGLHandlerThread.getName(), this.mGLHandlerThread.getId()));
        }
    }

    public void setConfigurationAndWait(Collection<Pair<Surface, Size>> collection, CaptureCollector captureCollector) {
        Preconditions.checkNotNull(captureCollector, "collector must not be null");
        Handler handler = this.mGLHandlerThread.getHandler();
        ConditionVariable conditionVariable = new ConditionVariable(false);
        handler.sendMessage(handler.obtainMessage(1, 0, 0, new ConfigureHolder(conditionVariable, collection, captureCollector)));
        conditionVariable.block();
    }

    public void start() {
        this.mGLHandlerThread.start();
    }

    public void waitUntilIdle() {
        this.mGLHandlerThread.waitUntilIdle();
    }

    public void waitUntilStarted() {
        this.mGLHandlerThread.waitUntilStarted();
    }

    private static class ConfigureHolder {
        public final CaptureCollector collector;
        public final ConditionVariable condition;
        public final Collection<Pair<Surface, Size>> surfaces;

        public ConfigureHolder(ConditionVariable conditionVariable, Collection<Pair<Surface, Size>> collection, CaptureCollector captureCollector) {
            this.condition = conditionVariable;
            this.surfaces = collection;
            this.collector = captureCollector;
        }
    }

}

