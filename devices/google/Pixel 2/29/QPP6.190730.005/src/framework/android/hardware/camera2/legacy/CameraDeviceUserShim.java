/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.OsConstants
 */
package android.hardware.camera2.legacy;

import android.hardware.Camera;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.ICameraDeviceCallbacks;
import android.hardware.camera2.ICameraDeviceUser;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.impl.CaptureResultExtras;
import android.hardware.camera2.impl.PhysicalCaptureResultInfo;
import android.hardware.camera2.legacy.LegacyCameraDevice;
import android.hardware.camera2.legacy.LegacyExceptionUtils;
import android.hardware.camera2.legacy.LegacyMetadataMapper;
import android.hardware.camera2.params.InputConfiguration;
import android.hardware.camera2.params.OutputConfiguration;
import android.hardware.camera2.params.SessionConfiguration;
import android.hardware.camera2.utils.SubmitInfo;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.system.OsConstants;
import android.util.Log;
import android.util.Size;
import android.util.SparseArray;
import android.view.Surface;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public class CameraDeviceUserShim
implements ICameraDeviceUser {
    private static final boolean DEBUG = false;
    private static final int OPEN_CAMERA_TIMEOUT_MS = 5000;
    private static final String TAG = "CameraDeviceUserShim";
    private final CameraCallbackThread mCameraCallbacks;
    private final CameraCharacteristics mCameraCharacteristics;
    private final CameraLooper mCameraInit;
    private final Object mConfigureLock = new Object();
    private boolean mConfiguring;
    private final LegacyCameraDevice mLegacyDevice;
    private int mSurfaceIdCounter;
    private final SparseArray<Surface> mSurfaces;

    protected CameraDeviceUserShim(int n, LegacyCameraDevice legacyCameraDevice, CameraCharacteristics cameraCharacteristics, CameraLooper cameraLooper, CameraCallbackThread cameraCallbackThread) {
        this.mLegacyDevice = legacyCameraDevice;
        this.mConfiguring = false;
        this.mSurfaces = new SparseArray();
        this.mCameraCharacteristics = cameraCharacteristics;
        this.mCameraInit = cameraLooper;
        this.mCameraCallbacks = cameraCallbackThread;
        this.mSurfaceIdCounter = 0;
    }

    public static CameraDeviceUserShim connectBinderShim(ICameraDeviceCallbacks object, int n, Size object2) {
        Camera.Parameters parameters;
        CameraLooper cameraLooper = new CameraLooper(n);
        CameraCallbackThread cameraCallbackThread = new CameraCallbackThread((ICameraDeviceCallbacks)object);
        int n2 = cameraLooper.waitForOpen(5000);
        object = cameraLooper.getCamera();
        LegacyExceptionUtils.throwOnServiceError(n2);
        ((Camera)object).disableShutterSound();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(n, cameraInfo);
        try {
            parameters = ((Camera)object).getParameters();
        }
        catch (RuntimeException runtimeException) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Unable to get initial parameters: ");
            ((StringBuilder)object2).append(runtimeException.getMessage());
            throw new ServiceSpecificException(10, ((StringBuilder)object2).toString());
        }
        object2 = LegacyMetadataMapper.createCharacteristics(parameters, cameraInfo, n, (Size)object2);
        return new CameraDeviceUserShim(n, new LegacyCameraDevice(n, (Camera)object, (CameraCharacteristics)object2, cameraCallbackThread), (CameraCharacteristics)object2, cameraLooper, cameraCallbackThread);
    }

    private static int translateErrorsFromCamera1(int n) {
        if (n == -OsConstants.EACCES) {
            return 1;
        }
        return n;
    }

    @Override
    public IBinder asBinder() {
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void beginConfigure() {
        if (this.mLegacyDevice.isClosed()) {
            Log.e(TAG, "Cannot begin configure, device has been closed.");
            throw new ServiceSpecificException(4, "Cannot begin configure, device has been closed.");
        }
        Object object = this.mConfigureLock;
        synchronized (object) {
            if (!this.mConfiguring) {
                this.mConfiguring = true;
                return;
            }
            Log.e(TAG, "Cannot begin configure, configuration change already in progress.");
            ServiceSpecificException serviceSpecificException = new ServiceSpecificException(10, "Cannot begin configure, configuration change already in progress.");
            throw serviceSpecificException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public long cancelRequest(int n) {
        if (this.mLegacyDevice.isClosed()) {
            Log.e(TAG, "Cannot cancel request, device has been closed.");
            throw new ServiceSpecificException(4, "Cannot cancel request, device has been closed.");
        }
        Object object = this.mConfigureLock;
        synchronized (object) {
            if (!this.mConfiguring) {
                return this.mLegacyDevice.cancelRequest(n);
            }
            Log.e(TAG, "Cannot cancel request, configuration change in progress.");
            ServiceSpecificException serviceSpecificException = new ServiceSpecificException(10, "Cannot cancel request, configuration change in progress.");
            throw serviceSpecificException;
        }
    }

    @Override
    public CameraMetadataNative createDefaultRequest(int n) {
        if (!this.mLegacyDevice.isClosed()) {
            try {
                CameraMetadataNative cameraMetadataNative = LegacyMetadataMapper.createRequestTemplate(this.mCameraCharacteristics, n);
                return cameraMetadataNative;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                Log.e(TAG, "createDefaultRequest - invalid templateId specified");
                throw new ServiceSpecificException(3, "createDefaultRequest - invalid templateId specified");
            }
        }
        Log.e(TAG, "Cannot create default request, device has been closed.");
        throw new ServiceSpecificException(4, "Cannot create default request, device has been closed.");
    }

    @Override
    public int createInputStream(int n, int n2, int n3) {
        Log.e(TAG, "Creating input stream is not supported on legacy devices");
        throw new ServiceSpecificException(10, "Creating input stream is not supported on legacy devices");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int createStream(OutputConfiguration object) {
        if (this.mLegacyDevice.isClosed()) {
            Log.e(TAG, "Cannot create stream, device has been closed.");
            throw new ServiceSpecificException(4, "Cannot create stream, device has been closed.");
        }
        Object object2 = this.mConfigureLock;
        synchronized (object2) {
            if (!this.mConfiguring) {
                Log.e(TAG, "Cannot create stream, beginConfigure hasn't been called yet.");
                object = new ServiceSpecificException(10, "Cannot create stream, beginConfigure hasn't been called yet.");
                throw object;
            }
            if (((OutputConfiguration)object).getRotation() == 0) {
                int n;
                this.mSurfaceIdCounter = n = this.mSurfaceIdCounter + 1;
                this.mSurfaces.put(n, ((OutputConfiguration)object).getSurface());
                return n;
            }
            Log.e(TAG, "Cannot create stream, stream rotation is not supported.");
            object = new ServiceSpecificException(3, "Cannot create stream, stream rotation is not supported.");
            throw object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void deleteStream(int n) {
        if (this.mLegacyDevice.isClosed()) {
            Log.e(TAG, "Cannot delete stream, device has been closed.");
            throw new ServiceSpecificException(4, "Cannot delete stream, device has been closed.");
        }
        Object object = this.mConfigureLock;
        synchronized (object) {
            if (!this.mConfiguring) {
                Log.e(TAG, "Cannot delete stream, no configuration change in progress.");
                ServiceSpecificException serviceSpecificException = new ServiceSpecificException(10, "Cannot delete stream, no configuration change in progress.");
                throw serviceSpecificException;
            }
            int n2 = this.mSurfaces.indexOfKey(n);
            if (n2 >= 0) {
                this.mSurfaces.removeAt(n2);
                return;
            }
            Serializable serializable = new StringBuilder();
            serializable.append("Cannot delete stream, stream id ");
            serializable.append(n);
            serializable.append(" doesn't exist.");
            String string2 = serializable.toString();
            Log.e(TAG, string2);
            serializable = new ServiceSpecificException(3, string2);
            throw serializable;
        }
    }

    @Override
    public void disconnect() {
        if (this.mLegacyDevice.isClosed()) {
            Log.w(TAG, "Cannot disconnect, device has already been closed.");
        }
        try {
            this.mLegacyDevice.close();
            return;
        }
        finally {
            this.mCameraInit.close();
            this.mCameraCallbacks.close();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void endConfigure(int n, CameraMetadataNative object) {
        if (this.mLegacyDevice.isClosed()) {
            Log.e(TAG, "Cannot end configure, device has been closed.");
            Object object2 = this.mConfigureLock;
            synchronized (object2) {
                this.mConfiguring = false;
                throw new ServiceSpecificException(4, "Cannot end configure, device has been closed.");
            }
        }
        if (n != 0) {
            Log.e(TAG, "LEGACY devices do not support this operating mode");
            Object object3 = this.mConfigureLock;
            synchronized (object3) {
                this.mConfiguring = false;
                throw new ServiceSpecificException(3, "LEGACY devices do not support this operating mode");
            }
        }
        object = null;
        Object object4 = this.mConfigureLock;
        synchronized (object4) {
            if (!this.mConfiguring) {
                Log.e(TAG, "Cannot end configure, no configuration change in progress.");
                object = new ServiceSpecificException(10, "Cannot end configure, no configuration change in progress.");
                throw object;
            }
            if (this.mSurfaces != null) {
                object = this.mSurfaces.clone();
            }
            this.mConfiguring = false;
        }
        this.mLegacyDevice.configureOutputs((SparseArray<Surface>)object);
    }

    @Override
    public void finalizeOutputConfigurations(int n, OutputConfiguration outputConfiguration) {
        Log.e(TAG, "Finalizing output configuration is not supported on legacy devices");
        throw new ServiceSpecificException(10, "Finalizing output configuration is not supported on legacy devices");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public long flush() {
        if (this.mLegacyDevice.isClosed()) {
            Log.e(TAG, "Cannot flush, device has been closed.");
            throw new ServiceSpecificException(4, "Cannot flush, device has been closed.");
        }
        Object object = this.mConfigureLock;
        synchronized (object) {
            if (!this.mConfiguring) {
                return this.mLegacyDevice.flush();
            }
            Log.e(TAG, "Cannot flush, configuration change in progress.");
            ServiceSpecificException serviceSpecificException = new ServiceSpecificException(10, "Cannot flush, configuration change in progress.");
            throw serviceSpecificException;
        }
    }

    @Override
    public CameraMetadataNative getCameraInfo() {
        Log.e(TAG, "getCameraInfo unimplemented.");
        return null;
    }

    @Override
    public Surface getInputSurface() {
        Log.e(TAG, "Getting input surface is not supported on legacy devices");
        throw new ServiceSpecificException(10, "Getting input surface is not supported on legacy devices");
    }

    @Override
    public boolean isSessionConfigurationSupported(SessionConfiguration object) {
        int n = ((SessionConfiguration)object).getSessionType();
        boolean bl = false;
        if (n != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Session type: ");
            stringBuilder.append(((SessionConfiguration)object).getSessionType());
            stringBuilder.append(" is different from  regular. Legacy devices support only regular session types!");
            Log.e(TAG, stringBuilder.toString());
            return false;
        }
        if (((SessionConfiguration)object).getInputConfiguration() != null) {
            Log.e(TAG, "Input configuration present, legacy devices do not support this feature!");
            return false;
        }
        Object object2 = ((SessionConfiguration)object).getOutputConfigurations();
        if (object2.isEmpty()) {
            Log.e(TAG, "Empty output configuration list!");
            return false;
        }
        object = new SparseArray(object2.size());
        n = 0;
        Iterator<OutputConfiguration> iterator = object2.iterator();
        while (iterator.hasNext()) {
            object2 = iterator.next();
            List<Surface> list = ((OutputConfiguration)object2).getSurfaces();
            if (!list.isEmpty() && list.size() <= 1) {
                ((SparseArray)object).put(n, ((OutputConfiguration)object2).getSurface());
                ++n;
                continue;
            }
            Log.e(TAG, "Legacy devices do not support deferred or shared surfaces!");
            return false;
        }
        if (this.mLegacyDevice.configureOutputs((SparseArray<Surface>)object, true) == 0) {
            bl = true;
        }
        return bl;
    }

    @Override
    public void prepare(int n) {
        if (!this.mLegacyDevice.isClosed()) {
            this.mCameraCallbacks.onPrepared(n);
            return;
        }
        Log.e(TAG, "Cannot prepare stream, device has been closed.");
        throw new ServiceSpecificException(4, "Cannot prepare stream, device has been closed.");
    }

    @Override
    public void prepare2(int n, int n2) {
        this.prepare(n2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public SubmitInfo submitRequest(CaptureRequest object, boolean bl) {
        if (this.mLegacyDevice.isClosed()) {
            Log.e(TAG, "Cannot submit request, device has been closed.");
            throw new ServiceSpecificException(4, "Cannot submit request, device has been closed.");
        }
        Object object2 = this.mConfigureLock;
        synchronized (object2) {
            if (!this.mConfiguring) {
                return this.mLegacyDevice.submitRequest((CaptureRequest)object, bl);
            }
            Log.e(TAG, "Cannot submit request, configuration change in progress.");
            object = new ServiceSpecificException(10, "Cannot submit request, configuration change in progress.");
            throw object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public SubmitInfo submitRequestList(CaptureRequest[] object, boolean bl) {
        if (this.mLegacyDevice.isClosed()) {
            Log.e(TAG, "Cannot submit request list, device has been closed.");
            throw new ServiceSpecificException(4, "Cannot submit request list, device has been closed.");
        }
        Object object2 = this.mConfigureLock;
        synchronized (object2) {
            if (!this.mConfiguring) {
                return this.mLegacyDevice.submitRequestList((CaptureRequest[])object, bl);
            }
            Log.e(TAG, "Cannot submit request, configuration change in progress.");
            object = new ServiceSpecificException(10, "Cannot submit request, configuration change in progress.");
            throw object;
        }
    }

    @Override
    public void tearDown(int n) {
        if (!this.mLegacyDevice.isClosed()) {
            return;
        }
        Log.e(TAG, "Cannot tear down stream, device has been closed.");
        throw new ServiceSpecificException(4, "Cannot tear down stream, device has been closed.");
    }

    @Override
    public void updateOutputConfiguration(int n, OutputConfiguration outputConfiguration) {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void waitUntilIdle() throws RemoteException {
        if (this.mLegacyDevice.isClosed()) {
            Log.e(TAG, "Cannot wait until idle, device has been closed.");
            throw new ServiceSpecificException(4, "Cannot wait until idle, device has been closed.");
        }
        Object object = this.mConfigureLock;
        synchronized (object) {
            if (!this.mConfiguring) {
                // MONITOREXIT [2, 3] lbl8 : MonitorExitStatement: MONITOREXIT : var1_1
                this.mLegacyDevice.waitUntilIdle();
                return;
            }
            Log.e(TAG, "Cannot wait until idle, configuration change in progress.");
            ServiceSpecificException serviceSpecificException = new ServiceSpecificException(10, "Cannot wait until idle, configuration change in progress.");
            throw serviceSpecificException;
        }
    }

    private static class CameraCallbackThread
    implements ICameraDeviceCallbacks {
        private static final int CAMERA_ERROR = 0;
        private static final int CAMERA_IDLE = 1;
        private static final int CAPTURE_STARTED = 2;
        private static final int PREPARED = 4;
        private static final int REPEATING_REQUEST_ERROR = 5;
        private static final int REQUEST_QUEUE_EMPTY = 6;
        private static final int RESULT_RECEIVED = 3;
        private final ICameraDeviceCallbacks mCallbacks;
        private Handler mHandler;
        private final HandlerThread mHandlerThread;

        public CameraCallbackThread(ICameraDeviceCallbacks iCameraDeviceCallbacks) {
            this.mCallbacks = iCameraDeviceCallbacks;
            this.mHandlerThread = new HandlerThread("LegacyCameraCallback");
            this.mHandlerThread.start();
        }

        private Handler getHandler() {
            if (this.mHandler == null) {
                this.mHandler = new CallbackHandler(this.mHandlerThread.getLooper());
            }
            return this.mHandler;
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        public void close() {
            this.mHandlerThread.quitSafely();
        }

        @Override
        public void onCaptureStarted(CaptureResultExtras parcelable, long l) {
            parcelable = this.getHandler().obtainMessage(2, (int)(l & 0xFFFFFFFFL), (int)(0xFFFFFFFFL & l >> 32), parcelable);
            this.getHandler().sendMessage((Message)parcelable);
        }

        @Override
        public void onDeviceError(int n, CaptureResultExtras parcelable) {
            parcelable = this.getHandler().obtainMessage(0, n, 0, parcelable);
            this.getHandler().sendMessage((Message)parcelable);
        }

        @Override
        public void onDeviceIdle() {
            Message message = this.getHandler().obtainMessage(1);
            this.getHandler().sendMessage(message);
        }

        @Override
        public void onPrepared(int n) {
            Message message = this.getHandler().obtainMessage(4, n, 0);
            this.getHandler().sendMessage(message);
        }

        @Override
        public void onRepeatingRequestError(long l, int n) {
            Message message = this.getHandler().obtainMessage(5, new Object[]{l, n});
            this.getHandler().sendMessage(message);
        }

        @Override
        public void onRequestQueueEmpty() {
            Message message = this.getHandler().obtainMessage(6, 0, 0);
            this.getHandler().sendMessage(message);
        }

        @Override
        public void onResultReceived(CameraMetadataNative parcelable, CaptureResultExtras captureResultExtras, PhysicalCaptureResultInfo[] arrphysicalCaptureResultInfo) {
            parcelable = this.getHandler().obtainMessage(3, new Object[]{parcelable, captureResultExtras});
            this.getHandler().sendMessage((Message)parcelable);
        }

        private class CallbackHandler
        extends Handler {
            public CallbackHandler(Looper looper) {
                super(looper);
            }

            @Override
            public void handleMessage(Message message) {
                try {
                    block11 : {
                        switch (message.what) {
                            default: {
                                break block11;
                            }
                            case 6: {
                                CameraCallbackThread.this.mCallbacks.onRequestQueueEmpty();
                                break;
                            }
                            case 5: {
                                Object[] arrobject = (Object[])message.obj;
                                long l = (Long)arrobject[0];
                                int n = (Integer)arrobject[1];
                                CameraCallbackThread.this.mCallbacks.onRepeatingRequestError(l, n);
                                break;
                            }
                            case 4: {
                                int n = message.arg1;
                                CameraCallbackThread.this.mCallbacks.onPrepared(n);
                                break;
                            }
                            case 3: {
                                Object object = (Object[])message.obj;
                                CameraMetadataNative cameraMetadataNative = (CameraMetadataNative)object[0];
                                object = (CaptureResultExtras)object[1];
                                CameraCallbackThread.this.mCallbacks.onResultReceived(cameraMetadataNative, (CaptureResultExtras)object, new PhysicalCaptureResultInfo[0]);
                                break;
                            }
                            case 2: {
                                long l = message.arg2;
                                long l2 = message.arg1;
                                CaptureResultExtras captureResultExtras = (CaptureResultExtras)message.obj;
                                CameraCallbackThread.this.mCallbacks.onCaptureStarted(captureResultExtras, (l & 0xFFFFFFFFL) << 32 | 0xFFFFFFFFL & l2);
                                break;
                            }
                            case 1: {
                                CameraCallbackThread.this.mCallbacks.onDeviceIdle();
                                break;
                            }
                            case 0: {
                                int n = message.arg1;
                                CaptureResultExtras captureResultExtras = (CaptureResultExtras)message.obj;
                                CameraCallbackThread.this.mCallbacks.onDeviceError(n, captureResultExtras);
                            }
                        }
                        return;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unknown callback message ");
                    stringBuilder.append(message.what);
                    IllegalArgumentException illegalArgumentException = new IllegalArgumentException(stringBuilder.toString());
                    throw illegalArgumentException;
                }
                catch (RemoteException remoteException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Received remote exception during camera callback ");
                    stringBuilder.append(message.what);
                    throw new IllegalStateException(stringBuilder.toString(), remoteException);
                }
            }
        }

    }

    private static class CameraLooper
    implements Runnable,
    AutoCloseable {
        private final Camera mCamera = Camera.openUninitialized();
        private final int mCameraId;
        private volatile int mInitErrors;
        private Looper mLooper;
        private final ConditionVariable mStartDone = new ConditionVariable();
        private final Thread mThread;

        public CameraLooper(int n) {
            this.mCameraId = n;
            this.mThread = new Thread(this);
            this.mThread.start();
        }

        @Override
        public void close() {
            Looper looper = this.mLooper;
            if (looper == null) {
                return;
            }
            looper.quitSafely();
            try {
                this.mThread.join();
                this.mLooper = null;
                return;
            }
            catch (InterruptedException interruptedException) {
                throw new AssertionError(interruptedException);
            }
        }

        public Camera getCamera() {
            return this.mCamera;
        }

        @Override
        public void run() {
            Looper.prepare();
            this.mLooper = Looper.myLooper();
            this.mInitErrors = this.mCamera.cameraInitUnspecified(this.mCameraId);
            this.mStartDone.open();
            Looper.loop();
        }

        public int waitForOpen(int n) {
            if (this.mStartDone.block(n)) {
                return this.mInitErrors;
            }
            Log.e(CameraDeviceUserShim.TAG, "waitForOpen - Camera failed to open after timeout of 5000 ms");
            try {
                this.mCamera.release();
            }
            catch (RuntimeException runtimeException) {
                Log.e(CameraDeviceUserShim.TAG, "connectBinderShim - Failed to release camera after timeout ", runtimeException);
            }
            throw new ServiceSpecificException(10);
        }
    }

}

