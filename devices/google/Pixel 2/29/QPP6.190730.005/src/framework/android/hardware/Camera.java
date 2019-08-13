/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.OsConstants
 */
package android.hardware;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityThread;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.media.IAudioService;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RSIllegalArgumentException;
import android.renderscript.RenderScript;
import android.renderscript.Type;
import android.system.OsConstants;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.app.IAppOpsCallback;
import com.android.internal.app.IAppOpsService;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Deprecated
public class Camera {
    public static final String ACTION_NEW_PICTURE = "android.hardware.action.NEW_PICTURE";
    public static final String ACTION_NEW_VIDEO = "android.hardware.action.NEW_VIDEO";
    public static final int CAMERA_ERROR_DISABLED = 3;
    public static final int CAMERA_ERROR_EVICTED = 2;
    public static final int CAMERA_ERROR_SERVER_DIED = 100;
    public static final int CAMERA_ERROR_UNKNOWN = 1;
    private static final int CAMERA_FACE_DETECTION_HW = 0;
    private static final int CAMERA_FACE_DETECTION_SW = 1;
    @UnsupportedAppUsage
    public static final int CAMERA_HAL_API_VERSION_1_0 = 256;
    private static final int CAMERA_HAL_API_VERSION_NORMAL_CONNECT = -2;
    private static final int CAMERA_HAL_API_VERSION_UNSPECIFIED = -1;
    private static final int CAMERA_MSG_COMPRESSED_IMAGE = 256;
    private static final int CAMERA_MSG_ERROR = 1;
    private static final int CAMERA_MSG_FOCUS = 4;
    private static final int CAMERA_MSG_FOCUS_MOVE = 2048;
    private static final int CAMERA_MSG_POSTVIEW_FRAME = 64;
    private static final int CAMERA_MSG_PREVIEW_FRAME = 16;
    private static final int CAMERA_MSG_PREVIEW_METADATA = 1024;
    private static final int CAMERA_MSG_RAW_IMAGE = 128;
    private static final int CAMERA_MSG_RAW_IMAGE_NOTIFY = 512;
    private static final int CAMERA_MSG_SHUTTER = 2;
    private static final int CAMERA_MSG_VIDEO_FRAME = 32;
    private static final int CAMERA_MSG_ZOOM = 8;
    private static final int NO_ERROR = 0;
    private static final String TAG = "Camera";
    private IAppOpsService mAppOps;
    private IAppOpsCallback mAppOpsCallback;
    private AutoFocusCallback mAutoFocusCallback;
    private final Object mAutoFocusCallbackLock = new Object();
    private AutoFocusMoveCallback mAutoFocusMoveCallback;
    private ErrorCallback mDetailedErrorCallback;
    private ErrorCallback mErrorCallback;
    private EventHandler mEventHandler;
    private boolean mFaceDetectionRunning = false;
    private FaceDetectionListener mFaceListener;
    @GuardedBy(value={"mShutterSoundLock"})
    private boolean mHasAppOpsPlayAudio = true;
    private PictureCallback mJpegCallback;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private long mNativeContext;
    private boolean mOneShot;
    private PictureCallback mPostviewCallback;
    private PreviewCallback mPreviewCallback;
    private PictureCallback mRawImageCallback;
    private ShutterCallback mShutterCallback;
    @GuardedBy(value={"mShutterSoundLock"})
    private boolean mShutterSoundEnabledFromApp = true;
    private final Object mShutterSoundLock = new Object();
    private boolean mUsingPreviewAllocation;
    private boolean mWithBuffer;
    private OnZoomChangeListener mZoomListener;

    Camera() {
    }

    Camera(int n) {
        n = this.cameraInitNormal(n);
        if (Camera.checkInitErrors(n)) {
            if (n != -OsConstants.EACCES) {
                if (n == -OsConstants.ENODEV) {
                    throw new RuntimeException("Camera initialization failed");
                }
                throw new RuntimeException("Unknown camera error");
            }
            throw new RuntimeException("Fail to connect to camera service");
        }
        this.initAppOps();
    }

    private Camera(int n, int n2) {
        n = this.cameraInitVersion(n, n2);
        if (Camera.checkInitErrors(n)) {
            if (n != -OsConstants.EACCES) {
                if (n != -OsConstants.ENODEV) {
                    if (n != -OsConstants.ENOSYS) {
                        if (n != -OsConstants.EOPNOTSUPP) {
                            if (n != -OsConstants.EINVAL) {
                                if (n != -OsConstants.EBUSY) {
                                    if (n == -OsConstants.EUSERS) {
                                        throw new RuntimeException("Camera initialization failed because the max number of camera devices were already opened");
                                    }
                                    throw new RuntimeException("Unknown camera error");
                                }
                                throw new RuntimeException("Camera initialization failed because the camera device was already opened");
                            }
                            throw new RuntimeException("Camera initialization failed because the input arugments are invalid");
                        }
                        throw new RuntimeException("Camera initialization failed because the hal version is not supported by this device");
                    }
                    throw new RuntimeException("Camera initialization failed because some methods are not implemented");
                }
                throw new RuntimeException("Camera initialization failed");
            }
            throw new RuntimeException("Fail to connect to camera service");
        }
    }

    private final native void _addCallbackBuffer(byte[] var1, int var2);

    private final native boolean _enableShutterSound(boolean var1);

    private static native void _getCameraInfo(int var0, CameraInfo var1);

    private final native void _startFaceDetection(int var1);

    private final native void _stopFaceDetection();

    private final native void _stopPreview();

    @UnsupportedAppUsage
    private final void addCallbackBuffer(byte[] object, int n) {
        if (n != 16 && n != 128) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unsupported message type: ");
            ((StringBuilder)object).append(n);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        this._addCallbackBuffer((byte[])object, n);
    }

    private int cameraInitNormal(int n) {
        return this.cameraInitVersion(n, -2);
    }

    private int cameraInitVersion(int n, int n2) {
        this.mShutterCallback = null;
        this.mRawImageCallback = null;
        this.mJpegCallback = null;
        this.mPreviewCallback = null;
        this.mPostviewCallback = null;
        this.mUsingPreviewAllocation = false;
        this.mZoomListener = null;
        Looper looper = Looper.myLooper();
        this.mEventHandler = looper != null ? new EventHandler(this, looper) : ((looper = Looper.getMainLooper()) != null ? new EventHandler(this, looper) : null);
        return this.native_setup(new WeakReference<Camera>(this), n, n2, ActivityThread.currentOpPackageName());
    }

    public static boolean checkInitErrors(int n) {
        boolean bl = n != 0;
        return bl;
    }

    private native void enableFocusMoveCallback(int var1);

    public static void getCameraInfo(int n, CameraInfo cameraInfo) {
        Camera._getCameraInfo(n, cameraInfo);
        IAudioService iAudioService = IAudioService.Stub.asInterface(ServiceManager.getService("audio"));
        try {
            if (iAudioService.isCameraSoundForced()) {
                cameraInfo.canDisableShutterSound = false;
            }
        }
        catch (RemoteException remoteException) {
            Log.e("Camera", "Audio service is unavailable for queries");
        }
    }

    @UnsupportedAppUsage
    public static Parameters getEmptyParameters() {
        Camera camera = new Camera();
        Objects.requireNonNull(camera);
        return camera.new Parameters();
    }

    public static native int getNumberOfCameras();

    public static Parameters getParametersCopy(Parameters parameters) {
        if (parameters != null) {
            Object object = parameters.getOuter();
            Objects.requireNonNull(object);
            object = (Camera)object.new Parameters();
            ((Parameters)object).copyFrom(parameters);
            return object;
        }
        throw new NullPointerException("parameters must not be null");
    }

    private void initAppOps() {
        this.mAppOps = IAppOpsService.Stub.asInterface(ServiceManager.getService("appops"));
        this.updateAppOpsPlayAudio();
        this.mAppOpsCallback = new IAppOpsCallbackWrapper(this);
        try {
            this.mAppOps.startWatchingMode(28, ActivityThread.currentPackageName(), this.mAppOpsCallback);
        }
        catch (RemoteException remoteException) {
            Log.e("Camera", "Error registering appOps callback", remoteException);
            this.mHasAppOpsPlayAudio = false;
        }
    }

    private final native void native_autoFocus();

    private final native void native_cancelAutoFocus();

    @UnsupportedAppUsage
    private final native String native_getParameters();

    private final native void native_release();

    @UnsupportedAppUsage
    private final native void native_setParameters(String var1);

    @UnsupportedAppUsage
    private final native int native_setup(Object var1, int var2, int var3, String var4);

    private final native void native_takePicture(int var1);

    public static Camera open() {
        int n = Camera.getNumberOfCameras();
        CameraInfo cameraInfo = new CameraInfo();
        for (int i = 0; i < n; ++i) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing != 0) continue;
            return new Camera(i);
        }
        return null;
    }

    public static Camera open(int n) {
        return new Camera(n);
    }

    @UnsupportedAppUsage
    public static Camera openLegacy(int n, int n2) {
        if (n2 >= 256) {
            return new Camera(n, n2);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid HAL version ");
        stringBuilder.append(n2);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static Camera openUninitialized() {
        return new Camera();
    }

    @UnsupportedAppUsage
    private static void postEventFromNative(Object object, int n, int n2, int n3, Object object2) {
        if ((object = (Camera)((WeakReference)object).get()) == null) {
            return;
        }
        EventHandler eventHandler = ((Camera)object).mEventHandler;
        if (eventHandler != null) {
            object2 = eventHandler.obtainMessage(n, n2, n3, object2);
            ((Camera)object).mEventHandler.sendMessage((Message)object2);
        }
    }

    private void releaseAppOps() {
        try {
            if (this.mAppOps != null) {
                this.mAppOps.stopWatchingMode(this.mAppOpsCallback);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private final native void setHasPreviewCallback(boolean var1, boolean var2);

    private final native void setPreviewCallbackSurface(Surface var1);

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void updateAppOpsPlayAudio() {
        var1_1 = this.mShutterSoundLock;
        // MONITORENTER : var1_1
        var2_2 = this.mHasAppOpsPlayAudio;
        var3_3 = 1;
        try {
            if (this.mAppOps != null) {
                var3_3 = this.mAppOps.checkAudioOperation(28, 13, Process.myUid(), ActivityThread.currentPackageName());
            }
            var4_4 = var3_3 == 0;
            this.mHasAppOpsPlayAudio = var4_4;
        }
        catch (RemoteException var5_5) {
            Log.e("Camera", "AppOpsService check audio operation failed");
            this.mHasAppOpsPlayAudio = false;
        }
        if (var2_2 != this.mHasAppOpsPlayAudio) {
            if (!this.mHasAppOpsPlayAudio) {
                var5_6 = IAudioService.Stub.asInterface(ServiceManager.getService("audio"));
                try {
                    var4_4 = var5_6.isCameraSoundForced();
                    ** if (!var4_4) goto lbl-1000
                }
                catch (RemoteException var5_7) {
                    Log.e("Camera", "Audio service is unavailable for queries");
                }
lbl-1000: // 1 sources:
                {
                    // MONITOREXIT : var1_1
                    return;
                }
lbl-1000: // 1 sources:
                {
                }
                this._enableShutterSound(false);
                return;
            }
            this.enableShutterSound(this.mShutterSoundEnabledFromApp);
        }
        // MONITOREXIT : var1_1
    }

    public final void addCallbackBuffer(byte[] arrby) {
        this._addCallbackBuffer(arrby, 16);
    }

    @UnsupportedAppUsage
    public final void addRawImageCallbackBuffer(byte[] arrby) {
        this.addCallbackBuffer(arrby, 128);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void autoFocus(AutoFocusCallback autoFocusCallback) {
        Object object = this.mAutoFocusCallbackLock;
        synchronized (object) {
            this.mAutoFocusCallback = autoFocusCallback;
        }
        this.native_autoFocus();
    }

    public int cameraInitUnspecified(int n) {
        return this.cameraInitVersion(n, -1);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void cancelAutoFocus() {
        Object object = this.mAutoFocusCallbackLock;
        synchronized (object) {
            this.mAutoFocusCallback = null;
        }
        this.native_cancelAutoFocus();
        this.mEventHandler.removeMessages(4);
    }

    public final Allocation createPreviewAllocation(RenderScript renderScript, int n) throws RSIllegalArgumentException {
        Size size = this.getParameters().getPreviewSize();
        Type.Builder builder = new Type.Builder(renderScript, Element.createPixel(renderScript, Element.DataType.UNSIGNED_8, Element.DataKind.PIXEL_YUV));
        builder.setYuvFormat(842094169);
        builder.setX(size.width);
        builder.setY(size.height);
        return Allocation.createTyped(renderScript, builder.create(), n | 32);
    }

    public final boolean disableShutterSound() {
        return this._enableShutterSound(false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final boolean enableShutterSound(boolean bl) {
        boolean bl2;
        boolean bl3 = true;
        boolean bl4 = true;
        IAudioService iAudioService = IAudioService.Stub.asInterface(ServiceManager.getService("audio"));
        try {
            bl2 = iAudioService.isCameraSoundForced();
            if (bl2) {
                bl4 = false;
            }
        }
        catch (RemoteException remoteException) {
            Log.e("Camera", "Audio service is unavailable for queries");
            bl4 = bl3;
        }
        if (!bl && !bl4) {
            return false;
        }
        Object object = this.mShutterSoundLock;
        synchronized (object) {
            this.mShutterSoundEnabledFromApp = bl;
            bl2 = this._enableShutterSound(bl);
            if (bl && !this.mHasAppOpsPlayAudio) {
                Log.i("Camera", "Shutter sound is not allowed by AppOpsManager");
                if (bl4) {
                    this._enableShutterSound(false);
                }
            }
            return bl2;
        }
    }

    protected void finalize() {
        this.release();
    }

    public Parameters getParameters() {
        Parameters parameters = new Parameters();
        parameters.unflatten(this.native_getParameters());
        return parameters;
    }

    public final native void lock();

    @UnsupportedAppUsage
    public final native boolean previewEnabled();

    public final native void reconnect() throws IOException;

    public final void release() {
        this.native_release();
        this.mFaceDetectionRunning = false;
        this.releaseAppOps();
    }

    public void setAutoFocusMoveCallback(AutoFocusMoveCallback autoFocusMoveCallback) {
        this.mAutoFocusMoveCallback = autoFocusMoveCallback;
        int n = this.mAutoFocusMoveCallback != null ? 1 : 0;
        this.enableFocusMoveCallback(n);
    }

    public final void setDetailedErrorCallback(ErrorCallback errorCallback) {
        this.mDetailedErrorCallback = errorCallback;
    }

    public final native void setDisplayOrientation(int var1);

    public final void setErrorCallback(ErrorCallback errorCallback) {
        this.mErrorCallback = errorCallback;
    }

    public final void setFaceDetectionListener(FaceDetectionListener faceDetectionListener) {
        this.mFaceListener = faceDetectionListener;
    }

    public final void setOneShotPreviewCallback(PreviewCallback previewCallback) {
        this.mPreviewCallback = previewCallback;
        boolean bl = true;
        this.mOneShot = true;
        this.mWithBuffer = false;
        if (previewCallback != null) {
            this.mUsingPreviewAllocation = false;
        }
        if (previewCallback == null) {
            bl = false;
        }
        this.setHasPreviewCallback(bl, false);
    }

    public void setParameters(Parameters parameters) {
        if (this.mUsingPreviewAllocation) {
            Size size = parameters.getPreviewSize();
            Size size2 = this.getParameters().getPreviewSize();
            if (size.width != size2.width || size.height != size2.height) {
                throw new IllegalStateException("Cannot change preview size while a preview allocation is configured.");
            }
        }
        this.native_setParameters(parameters.flatten());
    }

    public final void setPreviewCallback(PreviewCallback previewCallback) {
        this.mPreviewCallback = previewCallback;
        this.mOneShot = false;
        this.mWithBuffer = false;
        if (previewCallback != null) {
            this.mUsingPreviewAllocation = false;
        }
        boolean bl = previewCallback != null;
        this.setHasPreviewCallback(bl, false);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public final void setPreviewCallbackAllocation(Allocation var1_1) throws IOException {
        var2_2 = null;
        if (var1_1 == null) ** GOTO lbl28
        var2_2 = this.getParameters().getPreviewSize();
        if (var2_2.width == var1_1.getType().getX() && var2_2.height == var1_1.getType().getY()) {
            if ((var1_1.getUsage() & 32) == 0) throw new IllegalArgumentException("Allocation usage does not include USAGE_IO_INPUT");
            if (var1_1.getType().getElement().getDataKind() != Element.DataKind.PIXEL_YUV) throw new IllegalArgumentException("Allocation is not of a YUV type");
            var1_1 = var1_1.getSurface();
            this.mUsingPreviewAllocation = true;
        } else {
            var3_3 = new StringBuilder();
            var3_3.append("Allocation dimensions don't match preview dimensions: Allocation is ");
            var3_3.append(var1_1.getType().getX());
            var3_3.append(", ");
            var3_3.append(var1_1.getType().getY());
            var3_3.append(". Preview is ");
            var3_3.append(var2_2.width);
            var3_3.append(", ");
            var3_3.append(var2_2.height);
            throw new IllegalArgumentException(var3_3.toString());
lbl28: // 1 sources:
            this.mUsingPreviewAllocation = false;
            var1_1 = var2_2;
        }
        this.setPreviewCallbackSurface((Surface)var1_1);
    }

    public final void setPreviewCallbackWithBuffer(PreviewCallback previewCallback) {
        this.mPreviewCallback = previewCallback;
        boolean bl = false;
        this.mOneShot = false;
        this.mWithBuffer = true;
        if (previewCallback != null) {
            this.mUsingPreviewAllocation = false;
        }
        if (previewCallback != null) {
            bl = true;
        }
        this.setHasPreviewCallback(bl, true);
    }

    public final void setPreviewDisplay(SurfaceHolder surfaceHolder) throws IOException {
        if (surfaceHolder != null) {
            this.setPreviewSurface(surfaceHolder.getSurface());
        } else {
            this.setPreviewSurface(null);
        }
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public final native void setPreviewSurface(Surface var1) throws IOException;

    public final native void setPreviewTexture(SurfaceTexture var1) throws IOException;

    public final void setZoomChangeListener(OnZoomChangeListener onZoomChangeListener) {
        this.mZoomListener = onZoomChangeListener;
    }

    public final void startFaceDetection() {
        if (!this.mFaceDetectionRunning) {
            this._startFaceDetection(0);
            this.mFaceDetectionRunning = true;
            return;
        }
        throw new RuntimeException("Face detection is already running");
    }

    public final native void startPreview();

    public final native void startSmoothZoom(int var1);

    public final void stopFaceDetection() {
        this._stopFaceDetection();
        this.mFaceDetectionRunning = false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void stopPreview() {
        this._stopPreview();
        this.mFaceDetectionRunning = false;
        this.mShutterCallback = null;
        this.mRawImageCallback = null;
        this.mPostviewCallback = null;
        this.mJpegCallback = null;
        Object object = this.mAutoFocusCallbackLock;
        synchronized (object) {
            this.mAutoFocusCallback = null;
        }
        this.mAutoFocusMoveCallback = null;
    }

    public final native void stopSmoothZoom();

    public final void takePicture(ShutterCallback shutterCallback, PictureCallback pictureCallback, PictureCallback pictureCallback2) {
        this.takePicture(shutterCallback, pictureCallback, null, pictureCallback2);
    }

    public final void takePicture(ShutterCallback shutterCallback, PictureCallback pictureCallback, PictureCallback pictureCallback2, PictureCallback pictureCallback3) {
        this.mShutterCallback = shutterCallback;
        this.mRawImageCallback = pictureCallback;
        this.mPostviewCallback = pictureCallback2;
        this.mJpegCallback = pictureCallback3;
        int n = 0;
        if (this.mShutterCallback != null) {
            n = 0 | 2;
        }
        int n2 = n;
        if (this.mRawImageCallback != null) {
            n2 = n | 128;
        }
        n = n2;
        if (this.mPostviewCallback != null) {
            n = n2 | 64;
        }
        n2 = n;
        if (this.mJpegCallback != null) {
            n2 = n | 256;
        }
        this.native_takePicture(n2);
        this.mFaceDetectionRunning = false;
    }

    public final native void unlock();

    @Deprecated
    public static class Area {
        public Rect rect;
        public int weight;

        public Area(Rect rect, int n) {
            this.rect = rect;
            this.weight = n;
        }

        public boolean equals(Object object) {
            boolean bl = object instanceof Area;
            boolean bl2 = false;
            if (!bl) {
                return false;
            }
            object = (Area)object;
            Rect rect = this.rect;
            if (rect == null ? ((Area)object).rect != null : !rect.equals(((Area)object).rect)) {
                return false;
            }
            if (this.weight == ((Area)object).weight) {
                bl2 = true;
            }
            return bl2;
        }
    }

    @Deprecated
    public static interface AutoFocusCallback {
        public void onAutoFocus(boolean var1, Camera var2);
    }

    @Deprecated
    public static interface AutoFocusMoveCallback {
        public void onAutoFocusMoving(boolean var1, Camera var2);
    }

    @Deprecated
    public static class CameraInfo {
        public static final int CAMERA_FACING_BACK = 0;
        public static final int CAMERA_FACING_FRONT = 1;
        public boolean canDisableShutterSound;
        public int facing;
        public int orientation;
    }

    @Deprecated
    public static interface ErrorCallback {
        public void onError(int var1, Camera var2);
    }

    private class EventHandler
    extends Handler {
        private final Camera mCamera;

        @UnsupportedAppUsage
        public EventHandler(Camera camera2, Looper looper) {
            super(looper);
            this.mCamera = camera2;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void handleMessage(Message message) {
            int n = message.what;
            boolean bl = true;
            boolean bl2 = true;
            boolean bl3 = true;
            if (n != 1) {
                AutoFocusCallback autoFocusCallback;
                if (n == 2) {
                    if (Camera.this.mShutterCallback == null) return;
                    Camera.this.mShutterCallback.onShutter();
                    return;
                }
                if (n != 4) {
                    if (n != 8) {
                        if (n != 16) {
                            if (n == 64) {
                                if (Camera.this.mPostviewCallback == null) return;
                                Camera.this.mPostviewCallback.onPictureTaken((byte[])message.obj, this.mCamera);
                                return;
                            }
                            if (n == 128) {
                                if (Camera.this.mRawImageCallback == null) return;
                                Camera.this.mRawImageCallback.onPictureTaken((byte[])message.obj, this.mCamera);
                                return;
                            }
                            if (n == 256) {
                                if (Camera.this.mJpegCallback == null) return;
                                Camera.this.mJpegCallback.onPictureTaken((byte[])message.obj, this.mCamera);
                                return;
                            }
                            if (n == 1024) {
                                if (Camera.this.mFaceListener == null) return;
                                Camera.this.mFaceListener.onFaceDetection((Face[])message.obj, this.mCamera);
                                return;
                            }
                            if (n != 2048) {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("Unknown message type ");
                                stringBuilder.append(message.what);
                                Log.e(Camera.TAG, stringBuilder.toString());
                                return;
                            }
                            if (Camera.this.mAutoFocusMoveCallback == null) return;
                            AutoFocusMoveCallback autoFocusMoveCallback = Camera.this.mAutoFocusMoveCallback;
                            if (message.arg1 == 0) {
                                bl3 = false;
                            }
                            autoFocusMoveCallback.onAutoFocusMoving(bl3, this.mCamera);
                            return;
                        }
                        PreviewCallback previewCallback = Camera.this.mPreviewCallback;
                        if (previewCallback == null) return;
                        if (Camera.this.mOneShot) {
                            Camera.this.mPreviewCallback = null;
                        } else if (!Camera.this.mWithBuffer) {
                            Camera.this.setHasPreviewCallback(true, false);
                        }
                        previewCallback.onPreviewFrame((byte[])message.obj, this.mCamera);
                        return;
                    }
                    if (Camera.this.mZoomListener == null) return;
                    OnZoomChangeListener onZoomChangeListener = Camera.this.mZoomListener;
                    n = message.arg1;
                    bl3 = message.arg2 != 0 ? bl : false;
                    onZoomChangeListener.onZoomChange(n, bl3, this.mCamera);
                    return;
                }
                Object object = Camera.this.mAutoFocusCallbackLock;
                synchronized (object) {
                    autoFocusCallback = Camera.this.mAutoFocusCallback;
                }
                if (autoFocusCallback == null) return;
                bl3 = bl2;
                if (message.arg1 == 0) {
                    bl3 = false;
                }
                autoFocusCallback.onAutoFocus(bl3, this.mCamera);
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error ");
            stringBuilder.append(message.arg1);
            Log.e(Camera.TAG, stringBuilder.toString());
            if (Camera.this.mDetailedErrorCallback != null) {
                Camera.this.mDetailedErrorCallback.onError(message.arg1, this.mCamera);
                return;
            }
            if (Camera.this.mErrorCallback == null) return;
            if (message.arg1 == 3) {
                Camera.this.mErrorCallback.onError(2, this.mCamera);
                return;
            }
            Camera.this.mErrorCallback.onError(message.arg1, this.mCamera);
        }
    }

    @Deprecated
    public static class Face {
        public int id = -1;
        public Point leftEye = null;
        public Point mouth = null;
        public Rect rect;
        public Point rightEye = null;
        public int score;
    }

    @Deprecated
    public static interface FaceDetectionListener {
        public void onFaceDetection(Face[] var1, Camera var2);
    }

    private static class IAppOpsCallbackWrapper
    extends IAppOpsCallback.Stub {
        private final WeakReference<Camera> mWeakCamera;

        IAppOpsCallbackWrapper(Camera camera) {
            this.mWeakCamera = new WeakReference<Camera>(camera);
        }

        @Override
        public void opChanged(int n, int n2, String object) {
            if (n == 28 && (object = (Camera)this.mWeakCamera.get()) != null) {
                ((Camera)object).updateAppOpsPlayAudio();
            }
        }
    }

    @Deprecated
    public static interface OnZoomChangeListener {
        public void onZoomChange(int var1, boolean var2, Camera var3);
    }

    @Deprecated
    public class Parameters {
        public static final String ANTIBANDING_50HZ = "50hz";
        public static final String ANTIBANDING_60HZ = "60hz";
        public static final String ANTIBANDING_AUTO = "auto";
        public static final String ANTIBANDING_OFF = "off";
        public static final String EFFECT_AQUA = "aqua";
        public static final String EFFECT_BLACKBOARD = "blackboard";
        public static final String EFFECT_MONO = "mono";
        public static final String EFFECT_NEGATIVE = "negative";
        public static final String EFFECT_NONE = "none";
        public static final String EFFECT_POSTERIZE = "posterize";
        public static final String EFFECT_SEPIA = "sepia";
        public static final String EFFECT_SOLARIZE = "solarize";
        public static final String EFFECT_WHITEBOARD = "whiteboard";
        private static final String FALSE = "false";
        public static final String FLASH_MODE_AUTO = "auto";
        public static final String FLASH_MODE_OFF = "off";
        public static final String FLASH_MODE_ON = "on";
        public static final String FLASH_MODE_RED_EYE = "red-eye";
        public static final String FLASH_MODE_TORCH = "torch";
        public static final int FOCUS_DISTANCE_FAR_INDEX = 2;
        public static final int FOCUS_DISTANCE_NEAR_INDEX = 0;
        public static final int FOCUS_DISTANCE_OPTIMAL_INDEX = 1;
        public static final String FOCUS_MODE_AUTO = "auto";
        public static final String FOCUS_MODE_CONTINUOUS_PICTURE = "continuous-picture";
        public static final String FOCUS_MODE_CONTINUOUS_VIDEO = "continuous-video";
        public static final String FOCUS_MODE_EDOF = "edof";
        public static final String FOCUS_MODE_FIXED = "fixed";
        public static final String FOCUS_MODE_INFINITY = "infinity";
        public static final String FOCUS_MODE_MACRO = "macro";
        private static final String KEY_ANTIBANDING = "antibanding";
        private static final String KEY_AUTO_EXPOSURE_LOCK = "auto-exposure-lock";
        private static final String KEY_AUTO_EXPOSURE_LOCK_SUPPORTED = "auto-exposure-lock-supported";
        private static final String KEY_AUTO_WHITEBALANCE_LOCK = "auto-whitebalance-lock";
        private static final String KEY_AUTO_WHITEBALANCE_LOCK_SUPPORTED = "auto-whitebalance-lock-supported";
        private static final String KEY_EFFECT = "effect";
        private static final String KEY_EXPOSURE_COMPENSATION = "exposure-compensation";
        private static final String KEY_EXPOSURE_COMPENSATION_STEP = "exposure-compensation-step";
        private static final String KEY_FLASH_MODE = "flash-mode";
        private static final String KEY_FOCAL_LENGTH = "focal-length";
        private static final String KEY_FOCUS_AREAS = "focus-areas";
        private static final String KEY_FOCUS_DISTANCES = "focus-distances";
        private static final String KEY_FOCUS_MODE = "focus-mode";
        private static final String KEY_GPS_ALTITUDE = "gps-altitude";
        private static final String KEY_GPS_LATITUDE = "gps-latitude";
        private static final String KEY_GPS_LONGITUDE = "gps-longitude";
        private static final String KEY_GPS_PROCESSING_METHOD = "gps-processing-method";
        private static final String KEY_GPS_TIMESTAMP = "gps-timestamp";
        private static final String KEY_HORIZONTAL_VIEW_ANGLE = "horizontal-view-angle";
        private static final String KEY_JPEG_QUALITY = "jpeg-quality";
        private static final String KEY_JPEG_THUMBNAIL_HEIGHT = "jpeg-thumbnail-height";
        private static final String KEY_JPEG_THUMBNAIL_QUALITY = "jpeg-thumbnail-quality";
        private static final String KEY_JPEG_THUMBNAIL_SIZE = "jpeg-thumbnail-size";
        private static final String KEY_JPEG_THUMBNAIL_WIDTH = "jpeg-thumbnail-width";
        private static final String KEY_MAX_EXPOSURE_COMPENSATION = "max-exposure-compensation";
        private static final String KEY_MAX_NUM_DETECTED_FACES_HW = "max-num-detected-faces-hw";
        private static final String KEY_MAX_NUM_DETECTED_FACES_SW = "max-num-detected-faces-sw";
        private static final String KEY_MAX_NUM_FOCUS_AREAS = "max-num-focus-areas";
        private static final String KEY_MAX_NUM_METERING_AREAS = "max-num-metering-areas";
        private static final String KEY_MAX_ZOOM = "max-zoom";
        private static final String KEY_METERING_AREAS = "metering-areas";
        private static final String KEY_MIN_EXPOSURE_COMPENSATION = "min-exposure-compensation";
        private static final String KEY_PICTURE_FORMAT = "picture-format";
        private static final String KEY_PICTURE_SIZE = "picture-size";
        private static final String KEY_PREFERRED_PREVIEW_SIZE_FOR_VIDEO = "preferred-preview-size-for-video";
        private static final String KEY_PREVIEW_FORMAT = "preview-format";
        private static final String KEY_PREVIEW_FPS_RANGE = "preview-fps-range";
        private static final String KEY_PREVIEW_FRAME_RATE = "preview-frame-rate";
        private static final String KEY_PREVIEW_SIZE = "preview-size";
        private static final String KEY_RECORDING_HINT = "recording-hint";
        private static final String KEY_ROTATION = "rotation";
        private static final String KEY_SCENE_MODE = "scene-mode";
        private static final String KEY_SMOOTH_ZOOM_SUPPORTED = "smooth-zoom-supported";
        private static final String KEY_VERTICAL_VIEW_ANGLE = "vertical-view-angle";
        private static final String KEY_VIDEO_SIZE = "video-size";
        private static final String KEY_VIDEO_SNAPSHOT_SUPPORTED = "video-snapshot-supported";
        private static final String KEY_VIDEO_STABILIZATION = "video-stabilization";
        private static final String KEY_VIDEO_STABILIZATION_SUPPORTED = "video-stabilization-supported";
        private static final String KEY_WHITE_BALANCE = "whitebalance";
        private static final String KEY_ZOOM = "zoom";
        private static final String KEY_ZOOM_RATIOS = "zoom-ratios";
        private static final String KEY_ZOOM_SUPPORTED = "zoom-supported";
        private static final String PIXEL_FORMAT_BAYER_RGGB = "bayer-rggb";
        private static final String PIXEL_FORMAT_JPEG = "jpeg";
        private static final String PIXEL_FORMAT_RGB565 = "rgb565";
        private static final String PIXEL_FORMAT_YUV420P = "yuv420p";
        private static final String PIXEL_FORMAT_YUV420SP = "yuv420sp";
        private static final String PIXEL_FORMAT_YUV422I = "yuv422i-yuyv";
        private static final String PIXEL_FORMAT_YUV422SP = "yuv422sp";
        public static final int PREVIEW_FPS_MAX_INDEX = 1;
        public static final int PREVIEW_FPS_MIN_INDEX = 0;
        public static final String SCENE_MODE_ACTION = "action";
        public static final String SCENE_MODE_AUTO = "auto";
        public static final String SCENE_MODE_BARCODE = "barcode";
        public static final String SCENE_MODE_BEACH = "beach";
        public static final String SCENE_MODE_CANDLELIGHT = "candlelight";
        public static final String SCENE_MODE_FIREWORKS = "fireworks";
        public static final String SCENE_MODE_HDR = "hdr";
        public static final String SCENE_MODE_LANDSCAPE = "landscape";
        public static final String SCENE_MODE_NIGHT = "night";
        public static final String SCENE_MODE_NIGHT_PORTRAIT = "night-portrait";
        public static final String SCENE_MODE_PARTY = "party";
        public static final String SCENE_MODE_PORTRAIT = "portrait";
        public static final String SCENE_MODE_SNOW = "snow";
        public static final String SCENE_MODE_SPORTS = "sports";
        public static final String SCENE_MODE_STEADYPHOTO = "steadyphoto";
        public static final String SCENE_MODE_SUNSET = "sunset";
        public static final String SCENE_MODE_THEATRE = "theatre";
        private static final String SUPPORTED_VALUES_SUFFIX = "-values";
        private static final String TRUE = "true";
        public static final String WHITE_BALANCE_AUTO = "auto";
        public static final String WHITE_BALANCE_CLOUDY_DAYLIGHT = "cloudy-daylight";
        public static final String WHITE_BALANCE_DAYLIGHT = "daylight";
        public static final String WHITE_BALANCE_FLUORESCENT = "fluorescent";
        public static final String WHITE_BALANCE_INCANDESCENT = "incandescent";
        public static final String WHITE_BALANCE_SHADE = "shade";
        public static final String WHITE_BALANCE_TWILIGHT = "twilight";
        public static final String WHITE_BALANCE_WARM_FLUORESCENT = "warm-fluorescent";
        private final LinkedHashMap<String, String> mMap = new LinkedHashMap(64);

        private Parameters() {
        }

        private String cameraFormatForPixelFormat(int n) {
            if (n != 4) {
                if (n != 20) {
                    if (n != 256) {
                        if (n != 842094169) {
                            if (n != 16) {
                                if (n != 17) {
                                    return null;
                                }
                                return PIXEL_FORMAT_YUV420SP;
                            }
                            return PIXEL_FORMAT_YUV422SP;
                        }
                        return PIXEL_FORMAT_YUV420P;
                    }
                    return PIXEL_FORMAT_JPEG;
                }
                return PIXEL_FORMAT_YUV422I;
            }
            return PIXEL_FORMAT_RGB565;
        }

        private float getFloat(String string2, float f) {
            try {
                float f2 = Float.parseFloat(this.mMap.get(string2));
                return f2;
            }
            catch (NumberFormatException numberFormatException) {
                return f;
            }
        }

        private int getInt(String string2, int n) {
            try {
                int n2 = Integer.parseInt(this.mMap.get(string2));
                return n2;
            }
            catch (NumberFormatException numberFormatException) {
                return n;
            }
        }

        private Camera getOuter() {
            return Camera.this;
        }

        private int pixelFormatForCameraFormat(String string2) {
            if (string2 == null) {
                return 0;
            }
            if (string2.equals(PIXEL_FORMAT_YUV422SP)) {
                return 16;
            }
            if (string2.equals(PIXEL_FORMAT_YUV420SP)) {
                return 17;
            }
            if (string2.equals(PIXEL_FORMAT_YUV422I)) {
                return 20;
            }
            if (string2.equals(PIXEL_FORMAT_YUV420P)) {
                return 842094169;
            }
            if (string2.equals(PIXEL_FORMAT_RGB565)) {
                return 4;
            }
            if (string2.equals(PIXEL_FORMAT_JPEG)) {
                return 256;
            }
            return 0;
        }

        private void put(String string2, String string3) {
            this.mMap.remove(string2);
            this.mMap.put(string2, string3);
        }

        private boolean same(String string2, String string3) {
            if (string2 == null && string3 == null) {
                return true;
            }
            return string2 != null && string2.equals(string3);
        }

        private void set(String string2, List<Area> list) {
            if (list == null) {
                this.set(string2, "(0,0,0,0,0)");
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < list.size(); ++i) {
                    Area area = list.get(i);
                    Rect rect = area.rect;
                    stringBuilder.append('(');
                    stringBuilder.append(rect.left);
                    stringBuilder.append(',');
                    stringBuilder.append(rect.top);
                    stringBuilder.append(',');
                    stringBuilder.append(rect.right);
                    stringBuilder.append(',');
                    stringBuilder.append(rect.bottom);
                    stringBuilder.append(',');
                    stringBuilder.append(area.weight);
                    stringBuilder.append(')');
                    if (i == list.size() - 1) continue;
                    stringBuilder.append(',');
                }
                this.set(string2, stringBuilder.toString());
            }
        }

        private ArrayList<String> split(String object) {
            if (object == null) {
                return null;
            }
            Iterator<String> iterator = new TextUtils.SimpleStringSplitter(',');
            iterator.setString((String)object);
            object = new ArrayList();
            iterator = iterator.iterator();
            while (iterator.hasNext()) {
                ((ArrayList)object).add(iterator.next());
            }
            return object;
        }

        @UnsupportedAppUsage
        private ArrayList<Area> splitArea(String object) {
            if (object != null && ((String)object).charAt(0) == '(' && ((String)object).charAt(((String)object).length() - 1) == ')') {
                int n;
                ArrayList<Area> arrayList = new ArrayList<Area>();
                int n2 = 1;
                Object object2 = new int[5];
                do {
                    int n3;
                    n = n3 = ((String)object).indexOf("),(", n2);
                    if (n3 == -1) {
                        n = ((String)object).length() - 1;
                    }
                    this.splitInt(((String)object).substring(n2, n), (int[])object2);
                    arrayList.add(new Area(new Rect(object2[0], object2[1], object2[2], object2[3]), object2[4]));
                    n2 = n + 3;
                } while (n != ((String)object).length() - 1);
                if (arrayList.size() == 0) {
                    return null;
                }
                if (arrayList.size() == 1) {
                    object = arrayList.get(0);
                    object2 = ((Area)object).rect;
                    if (object2.left == 0 && object2.top == 0 && object2.right == 0 && object2.bottom == 0 && ((Area)object).weight == 0) {
                        return null;
                    }
                }
                return arrayList;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid area string=");
            stringBuilder.append((String)object);
            Log.e(Camera.TAG, stringBuilder.toString());
            return null;
        }

        private void splitFloat(String object, float[] arrf) {
            if (object == null) {
                return;
            }
            TextUtils.SimpleStringSplitter simpleStringSplitter = new TextUtils.SimpleStringSplitter(',');
            simpleStringSplitter.setString((String)object);
            int n = 0;
            object = simpleStringSplitter.iterator();
            while (object.hasNext()) {
                arrf[n] = Float.parseFloat((String)object.next());
                ++n;
            }
        }

        private ArrayList<Integer> splitInt(String object) {
            if (object == null) {
                return null;
            }
            Iterator<String> iterator = new TextUtils.SimpleStringSplitter(',');
            iterator.setString((String)object);
            object = new ArrayList();
            iterator = iterator.iterator();
            while (iterator.hasNext()) {
                ((ArrayList)object).add(Integer.parseInt(iterator.next()));
            }
            if (((ArrayList)object).size() == 0) {
                return null;
            }
            return object;
        }

        private void splitInt(String object, int[] arrn) {
            if (object == null) {
                return;
            }
            TextUtils.SimpleStringSplitter simpleStringSplitter = new TextUtils.SimpleStringSplitter(',');
            simpleStringSplitter.setString((String)object);
            int n = 0;
            object = simpleStringSplitter.iterator();
            while (object.hasNext()) {
                arrn[n] = Integer.parseInt((String)object.next());
                ++n;
            }
        }

        private ArrayList<int[]> splitRange(String string2) {
            if (string2 != null && string2.charAt(0) == '(' && string2.charAt(string2.length() - 1) == ')') {
                int n;
                ArrayList<int[]> arrayList = new ArrayList<int[]>();
                int n2 = 1;
                do {
                    int n3;
                    int[] arrn = new int[2];
                    n = n3 = string2.indexOf("),(", n2);
                    if (n3 == -1) {
                        n = string2.length() - 1;
                    }
                    this.splitInt(string2.substring(n2, n), arrn);
                    arrayList.add(arrn);
                    n2 = n + 3;
                } while (n != string2.length() - 1);
                if (arrayList.size() == 0) {
                    return null;
                }
                return arrayList;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid range list string=");
            stringBuilder.append(string2);
            Log.e(Camera.TAG, stringBuilder.toString());
            return null;
        }

        private ArrayList<Size> splitSize(String object) {
            if (object == null) {
                return null;
            }
            Iterator<String> iterator = new TextUtils.SimpleStringSplitter(',');
            iterator.setString((String)object);
            object = new ArrayList();
            iterator = iterator.iterator();
            while (iterator.hasNext()) {
                Size size = this.strToSize(iterator.next());
                if (size == null) continue;
                ((ArrayList)object).add(size);
            }
            if (((ArrayList)object).size() == 0) {
                return null;
            }
            return object;
        }

        private Size strToSize(String string2) {
            if (string2 == null) {
                return null;
            }
            int n = string2.indexOf(120);
            if (n != -1) {
                String string3 = string2.substring(0, n);
                string2 = string2.substring(n + 1);
                return new Size(Integer.parseInt(string3), Integer.parseInt(string2));
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid size parameter string=");
            stringBuilder.append(string2);
            Log.e(Camera.TAG, stringBuilder.toString());
            return null;
        }

        @UnsupportedAppUsage
        public void copyFrom(Parameters parameters) {
            if (parameters != null) {
                this.mMap.putAll(parameters.mMap);
                return;
            }
            throw new NullPointerException("other must not be null");
        }

        @Deprecated
        @UnsupportedAppUsage
        public void dump() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("dump: size=");
            stringBuilder.append(this.mMap.size());
            Log.e(Camera.TAG, stringBuilder.toString());
            for (String string2 : this.mMap.keySet()) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("dump: ");
                stringBuilder2.append(string2);
                stringBuilder2.append("=");
                stringBuilder2.append(this.mMap.get(string2));
                Log.e(Camera.TAG, stringBuilder2.toString());
            }
        }

        public String flatten() {
            StringBuilder stringBuilder = new StringBuilder(128);
            for (String string2 : this.mMap.keySet()) {
                stringBuilder.append(string2);
                stringBuilder.append("=");
                stringBuilder.append(this.mMap.get(string2));
                stringBuilder.append(";");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            return stringBuilder.toString();
        }

        public String get(String string2) {
            return this.mMap.get(string2);
        }

        public String getAntibanding() {
            return this.get(KEY_ANTIBANDING);
        }

        public boolean getAutoExposureLock() {
            return TRUE.equals(this.get(KEY_AUTO_EXPOSURE_LOCK));
        }

        public boolean getAutoWhiteBalanceLock() {
            return TRUE.equals(this.get(KEY_AUTO_WHITEBALANCE_LOCK));
        }

        public String getColorEffect() {
            return this.get(KEY_EFFECT);
        }

        public int getExposureCompensation() {
            return this.getInt(KEY_EXPOSURE_COMPENSATION, 0);
        }

        public float getExposureCompensationStep() {
            return this.getFloat(KEY_EXPOSURE_COMPENSATION_STEP, 0.0f);
        }

        public String getFlashMode() {
            return this.get(KEY_FLASH_MODE);
        }

        public float getFocalLength() {
            return Float.parseFloat(this.get(KEY_FOCAL_LENGTH));
        }

        public List<Area> getFocusAreas() {
            return this.splitArea(this.get(KEY_FOCUS_AREAS));
        }

        public void getFocusDistances(float[] arrf) {
            if (arrf != null && arrf.length == 3) {
                this.splitFloat(this.get(KEY_FOCUS_DISTANCES), arrf);
                return;
            }
            throw new IllegalArgumentException("output must be a float array with three elements.");
        }

        public String getFocusMode() {
            return this.get(KEY_FOCUS_MODE);
        }

        public float getHorizontalViewAngle() {
            return Float.parseFloat(this.get(KEY_HORIZONTAL_VIEW_ANGLE));
        }

        public int getInt(String string2) {
            return Integer.parseInt(this.mMap.get(string2));
        }

        public int getJpegQuality() {
            return this.getInt(KEY_JPEG_QUALITY);
        }

        public int getJpegThumbnailQuality() {
            return this.getInt(KEY_JPEG_THUMBNAIL_QUALITY);
        }

        public Size getJpegThumbnailSize() {
            return new Size(this.getInt(KEY_JPEG_THUMBNAIL_WIDTH), this.getInt(KEY_JPEG_THUMBNAIL_HEIGHT));
        }

        public int getMaxExposureCompensation() {
            return this.getInt(KEY_MAX_EXPOSURE_COMPENSATION, 0);
        }

        public int getMaxNumDetectedFaces() {
            return this.getInt(KEY_MAX_NUM_DETECTED_FACES_HW, 0);
        }

        public int getMaxNumFocusAreas() {
            return this.getInt(KEY_MAX_NUM_FOCUS_AREAS, 0);
        }

        public int getMaxNumMeteringAreas() {
            return this.getInt(KEY_MAX_NUM_METERING_AREAS, 0);
        }

        public int getMaxZoom() {
            return this.getInt(KEY_MAX_ZOOM, 0);
        }

        public List<Area> getMeteringAreas() {
            return this.splitArea(this.get(KEY_METERING_AREAS));
        }

        public int getMinExposureCompensation() {
            return this.getInt(KEY_MIN_EXPOSURE_COMPENSATION, 0);
        }

        public int getPictureFormat() {
            return this.pixelFormatForCameraFormat(this.get(KEY_PICTURE_FORMAT));
        }

        public Size getPictureSize() {
            return this.strToSize(this.get(KEY_PICTURE_SIZE));
        }

        public Size getPreferredPreviewSizeForVideo() {
            return this.strToSize(this.get(KEY_PREFERRED_PREVIEW_SIZE_FOR_VIDEO));
        }

        public int getPreviewFormat() {
            return this.pixelFormatForCameraFormat(this.get(KEY_PREVIEW_FORMAT));
        }

        public void getPreviewFpsRange(int[] arrn) {
            if (arrn != null && arrn.length == 2) {
                this.splitInt(this.get(KEY_PREVIEW_FPS_RANGE), arrn);
                return;
            }
            throw new IllegalArgumentException("range must be an array with two elements.");
        }

        @Deprecated
        public int getPreviewFrameRate() {
            return this.getInt(KEY_PREVIEW_FRAME_RATE);
        }

        public Size getPreviewSize() {
            return this.strToSize(this.get(KEY_PREVIEW_SIZE));
        }

        public String getSceneMode() {
            return this.get(KEY_SCENE_MODE);
        }

        public List<String> getSupportedAntibanding() {
            return this.split(this.get("antibanding-values"));
        }

        public List<String> getSupportedColorEffects() {
            return this.split(this.get("effect-values"));
        }

        public List<String> getSupportedFlashModes() {
            return this.split(this.get("flash-mode-values"));
        }

        public List<String> getSupportedFocusModes() {
            return this.split(this.get("focus-mode-values"));
        }

        public List<Size> getSupportedJpegThumbnailSizes() {
            return this.splitSize(this.get("jpeg-thumbnail-size-values"));
        }

        public List<Integer> getSupportedPictureFormats() {
            Object object = this.get("picture-format-values");
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            object = this.split((String)object).iterator();
            while (object.hasNext()) {
                int n = this.pixelFormatForCameraFormat((String)object.next());
                if (n == 0) continue;
                arrayList.add(n);
            }
            return arrayList;
        }

        public List<Size> getSupportedPictureSizes() {
            return this.splitSize(this.get("picture-size-values"));
        }

        public List<Integer> getSupportedPreviewFormats() {
            Object object = this.get("preview-format-values");
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            object = this.split((String)object).iterator();
            while (object.hasNext()) {
                int n = this.pixelFormatForCameraFormat((String)object.next());
                if (n == 0) continue;
                arrayList.add(n);
            }
            return arrayList;
        }

        public List<int[]> getSupportedPreviewFpsRange() {
            return this.splitRange(this.get("preview-fps-range-values"));
        }

        @Deprecated
        public List<Integer> getSupportedPreviewFrameRates() {
            return this.splitInt(this.get("preview-frame-rate-values"));
        }

        public List<Size> getSupportedPreviewSizes() {
            return this.splitSize(this.get("preview-size-values"));
        }

        public List<String> getSupportedSceneModes() {
            return this.split(this.get("scene-mode-values"));
        }

        public List<Size> getSupportedVideoSizes() {
            return this.splitSize(this.get("video-size-values"));
        }

        public List<String> getSupportedWhiteBalance() {
            return this.split(this.get("whitebalance-values"));
        }

        public float getVerticalViewAngle() {
            return Float.parseFloat(this.get(KEY_VERTICAL_VIEW_ANGLE));
        }

        public boolean getVideoStabilization() {
            return TRUE.equals(this.get(KEY_VIDEO_STABILIZATION));
        }

        public String getWhiteBalance() {
            return this.get(KEY_WHITE_BALANCE);
        }

        public int getZoom() {
            return this.getInt(KEY_ZOOM, 0);
        }

        public List<Integer> getZoomRatios() {
            return this.splitInt(this.get(KEY_ZOOM_RATIOS));
        }

        public boolean isAutoExposureLockSupported() {
            return TRUE.equals(this.get(KEY_AUTO_EXPOSURE_LOCK_SUPPORTED));
        }

        public boolean isAutoWhiteBalanceLockSupported() {
            return TRUE.equals(this.get(KEY_AUTO_WHITEBALANCE_LOCK_SUPPORTED));
        }

        public boolean isSmoothZoomSupported() {
            return TRUE.equals(this.get(KEY_SMOOTH_ZOOM_SUPPORTED));
        }

        public boolean isVideoSnapshotSupported() {
            return TRUE.equals(this.get(KEY_VIDEO_SNAPSHOT_SUPPORTED));
        }

        public boolean isVideoStabilizationSupported() {
            return TRUE.equals(this.get(KEY_VIDEO_STABILIZATION_SUPPORTED));
        }

        public boolean isZoomSupported() {
            return TRUE.equals(this.get(KEY_ZOOM_SUPPORTED));
        }

        public void remove(String string2) {
            this.mMap.remove(string2);
        }

        public void removeGpsData() {
            this.remove(KEY_GPS_LATITUDE);
            this.remove(KEY_GPS_LONGITUDE);
            this.remove(KEY_GPS_ALTITUDE);
            this.remove(KEY_GPS_TIMESTAMP);
            this.remove(KEY_GPS_PROCESSING_METHOD);
        }

        public boolean same(Parameters parameters) {
            boolean bl = true;
            if (this == parameters) {
                return true;
            }
            if (parameters == null || !this.mMap.equals(parameters.mMap)) {
                bl = false;
            }
            return bl;
        }

        public void set(String string2, int n) {
            this.put(string2, Integer.toString(n));
        }

        public void set(String charSequence, String charSequence2) {
            if (((String)charSequence).indexOf(61) == -1 && ((String)charSequence).indexOf(59) == -1 && ((String)charSequence).indexOf(0) == -1) {
                if (((String)charSequence2).indexOf(61) == -1 && ((String)charSequence2).indexOf(59) == -1 && ((String)charSequence2).indexOf(0) == -1) {
                    this.put((String)charSequence, (String)charSequence2);
                    return;
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Value \"");
                ((StringBuilder)charSequence).append((String)charSequence2);
                ((StringBuilder)charSequence).append("\" contains invalid character (= or ; or \\0)");
                Log.e(Camera.TAG, ((StringBuilder)charSequence).toString());
                return;
            }
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("Key \"");
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("\" contains invalid character (= or ; or \\0)");
            Log.e(Camera.TAG, ((StringBuilder)charSequence2).toString());
        }

        public void setAntibanding(String string2) {
            this.set(KEY_ANTIBANDING, string2);
        }

        public void setAutoExposureLock(boolean bl) {
            String string2 = bl ? TRUE : FALSE;
            this.set(KEY_AUTO_EXPOSURE_LOCK, string2);
        }

        public void setAutoWhiteBalanceLock(boolean bl) {
            String string2 = bl ? TRUE : FALSE;
            this.set(KEY_AUTO_WHITEBALANCE_LOCK, string2);
        }

        public void setColorEffect(String string2) {
            this.set(KEY_EFFECT, string2);
        }

        public void setExposureCompensation(int n) {
            this.set(KEY_EXPOSURE_COMPENSATION, n);
        }

        public void setFlashMode(String string2) {
            this.set(KEY_FLASH_MODE, string2);
        }

        public void setFocusAreas(List<Area> list) {
            this.set(KEY_FOCUS_AREAS, list);
        }

        public void setFocusMode(String string2) {
            this.set(KEY_FOCUS_MODE, string2);
        }

        public void setGpsAltitude(double d) {
            this.set(KEY_GPS_ALTITUDE, Double.toString(d));
        }

        public void setGpsLatitude(double d) {
            this.set(KEY_GPS_LATITUDE, Double.toString(d));
        }

        public void setGpsLongitude(double d) {
            this.set(KEY_GPS_LONGITUDE, Double.toString(d));
        }

        public void setGpsProcessingMethod(String string2) {
            this.set(KEY_GPS_PROCESSING_METHOD, string2);
        }

        public void setGpsTimestamp(long l) {
            this.set(KEY_GPS_TIMESTAMP, Long.toString(l));
        }

        public void setJpegQuality(int n) {
            this.set(KEY_JPEG_QUALITY, n);
        }

        public void setJpegThumbnailQuality(int n) {
            this.set(KEY_JPEG_THUMBNAIL_QUALITY, n);
        }

        public void setJpegThumbnailSize(int n, int n2) {
            this.set(KEY_JPEG_THUMBNAIL_WIDTH, n);
            this.set(KEY_JPEG_THUMBNAIL_HEIGHT, n2);
        }

        public void setMeteringAreas(List<Area> list) {
            this.set(KEY_METERING_AREAS, list);
        }

        public void setPictureFormat(int n) {
            CharSequence charSequence = this.cameraFormatForPixelFormat(n);
            if (charSequence != null) {
                this.set(KEY_PICTURE_FORMAT, (String)charSequence);
                return;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Invalid pixel_format=");
            ((StringBuilder)charSequence).append(n);
            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }

        public void setPictureSize(int n, int n2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Integer.toString(n));
            stringBuilder.append("x");
            stringBuilder.append(Integer.toString(n2));
            this.set(KEY_PICTURE_SIZE, stringBuilder.toString());
        }

        public void setPreviewFormat(int n) {
            CharSequence charSequence = this.cameraFormatForPixelFormat(n);
            if (charSequence != null) {
                this.set(KEY_PREVIEW_FORMAT, (String)charSequence);
                return;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Invalid pixel_format=");
            ((StringBuilder)charSequence).append(n);
            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }

        public void setPreviewFpsRange(int n, int n2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(n);
            stringBuilder.append(",");
            stringBuilder.append(n2);
            this.set(KEY_PREVIEW_FPS_RANGE, stringBuilder.toString());
        }

        @Deprecated
        public void setPreviewFrameRate(int n) {
            this.set(KEY_PREVIEW_FRAME_RATE, n);
        }

        public void setPreviewSize(int n, int n2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Integer.toString(n));
            stringBuilder.append("x");
            stringBuilder.append(Integer.toString(n2));
            this.set(KEY_PREVIEW_SIZE, stringBuilder.toString());
        }

        public void setRecordingHint(boolean bl) {
            String string2 = bl ? TRUE : FALSE;
            this.set(KEY_RECORDING_HINT, string2);
        }

        public void setRotation(int n) {
            if (n != 0 && n != 90 && n != 180 && n != 270) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid rotation=");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            this.set(KEY_ROTATION, Integer.toString(n));
        }

        public void setSceneMode(String string2) {
            this.set(KEY_SCENE_MODE, string2);
        }

        public void setVideoStabilization(boolean bl) {
            String string2 = bl ? TRUE : FALSE;
            this.set(KEY_VIDEO_STABILIZATION, string2);
        }

        public void setWhiteBalance(String string2) {
            if (this.same(string2, this.get(KEY_WHITE_BALANCE))) {
                return;
            }
            this.set(KEY_WHITE_BALANCE, string2);
            this.set(KEY_AUTO_WHITEBALANCE_LOCK, FALSE);
        }

        public void setZoom(int n) {
            this.set(KEY_ZOOM, n);
        }

        public void unflatten(String object) {
            this.mMap.clear();
            Object object2 = new TextUtils.SimpleStringSplitter(';');
            object2.setString((String)object);
            object = object2.iterator();
            while (object.hasNext()) {
                String string2 = (String)object.next();
                int n = string2.indexOf(61);
                if (n == -1) continue;
                object2 = string2.substring(0, n);
                string2 = string2.substring(n + 1);
                this.mMap.put((String)object2, string2);
            }
        }
    }

    @Deprecated
    public static interface PictureCallback {
        public void onPictureTaken(byte[] var1, Camera var2);
    }

    @Deprecated
    public static interface PreviewCallback {
        public void onPreviewFrame(byte[] var1, Camera var2);
    }

    @Deprecated
    public static interface ShutterCallback {
        public void onShutter();
    }

    @Deprecated
    public class Size {
        public int height;
        public int width;

        public Size(int n, int n2) {
            this.width = n;
            this.height = n2;
        }

        public boolean equals(Object object) {
            boolean bl = object instanceof Size;
            boolean bl2 = false;
            if (!bl) {
                return false;
            }
            object = (Size)object;
            bl = bl2;
            if (this.width == ((Size)object).width) {
                bl = bl2;
                if (this.height == ((Size)object).height) {
                    bl = true;
                }
            }
            return bl;
        }

        public int hashCode() {
            return this.width * 32713 + this.height;
        }
    }

}

