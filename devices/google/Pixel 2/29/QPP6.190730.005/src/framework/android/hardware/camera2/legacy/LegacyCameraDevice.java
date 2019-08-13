/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.legacy;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.ICameraDeviceCallbacks;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.impl.CaptureResultExtras;
import android.hardware.camera2.impl.PhysicalCaptureResultInfo;
import android.hardware.camera2.legacy.CameraDeviceState;
import android.hardware.camera2.legacy.LegacyExceptionUtils;
import android.hardware.camera2.legacy.RequestHolder;
import android.hardware.camera2.legacy.RequestThreadManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.hardware.camera2.utils.ArrayUtils;
import android.hardware.camera2.utils.SubmitInfo;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.util.Log;
import android.util.Pair;
import android.util.Size;
import android.util.SparseArray;
import android.view.Surface;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class LegacyCameraDevice
implements AutoCloseable {
    private static final boolean DEBUG = false;
    private static final int GRALLOC_USAGE_HW_COMPOSER = 2048;
    private static final int GRALLOC_USAGE_HW_RENDER = 512;
    private static final int GRALLOC_USAGE_HW_TEXTURE = 256;
    private static final int GRALLOC_USAGE_HW_VIDEO_ENCODER = 65536;
    private static final int GRALLOC_USAGE_RENDERSCRIPT = 1048576;
    private static final int GRALLOC_USAGE_SW_READ_OFTEN = 3;
    private static final int ILLEGAL_VALUE = -1;
    public static final int MAX_DIMEN_FOR_ROUNDING = 1920;
    public static final int NATIVE_WINDOW_SCALING_MODE_SCALE_TO_WINDOW = 1;
    private final String TAG;
    private final Handler mCallbackHandler;
    private final HandlerThread mCallbackHandlerThread = new HandlerThread("CallbackThread");
    private final int mCameraId;
    private boolean mClosed = false;
    private SparseArray<Surface> mConfiguredSurfaces;
    private final ICameraDeviceCallbacks mDeviceCallbacks;
    private final CameraDeviceState mDeviceState = new CameraDeviceState();
    private final ConditionVariable mIdle = new ConditionVariable(true);
    private final RequestThreadManager mRequestThreadManager;
    private final Handler mResultHandler;
    private final HandlerThread mResultThread = new HandlerThread("ResultThread");
    private final CameraDeviceState.CameraDeviceStateListener mStateListener = new CameraDeviceState.CameraDeviceStateListener(){

        @Override
        public void onBusy() {
            LegacyCameraDevice.this.mIdle.close();
        }

        @Override
        public void onCaptureResult(final CameraMetadataNative cameraMetadataNative, final RequestHolder requestHolder) {
            final CaptureResultExtras captureResultExtras = LegacyCameraDevice.this.getExtrasFromRequest(requestHolder);
            LegacyCameraDevice.this.mResultHandler.post(new Runnable(){

                @Override
                public void run() {
                    try {
                        LegacyCameraDevice.this.mDeviceCallbacks.onResultReceived(cameraMetadataNative, captureResultExtras, new PhysicalCaptureResultInfo[0]);
                        return;
                    }
                    catch (RemoteException remoteException) {
                        throw new IllegalStateException("Received remote exception during onCameraError callback: ", remoteException);
                    }
                }
            });
        }

        @Override
        public void onCaptureStarted(final RequestHolder requestHolder, final long l) {
            final CaptureResultExtras captureResultExtras = LegacyCameraDevice.this.getExtrasFromRequest(requestHolder);
            LegacyCameraDevice.this.mResultHandler.post(new Runnable(){

                @Override
                public void run() {
                    try {
                        LegacyCameraDevice.this.mDeviceCallbacks.onCaptureStarted(captureResultExtras, l);
                        return;
                    }
                    catch (RemoteException remoteException) {
                        throw new IllegalStateException("Received remote exception during onCameraError callback: ", remoteException);
                    }
                }
            });
        }

        @Override
        public void onConfiguring() {
        }

        @Override
        public void onError(final int n, Object object, final RequestHolder requestHolder) {
            if (n == 0 || n == 1 || n == 2) {
                LegacyCameraDevice.this.mIdle.open();
            }
            object = LegacyCameraDevice.this.getExtrasFromRequest(requestHolder, n, object);
            LegacyCameraDevice.this.mResultHandler.post(new Runnable((CaptureResultExtras)object){
                final /* synthetic */ CaptureResultExtras val$extras;
                {
                    this.val$extras = captureResultExtras;
                }

                @Override
                public void run() {
                    try {
                        LegacyCameraDevice.this.mDeviceCallbacks.onDeviceError(n, this.val$extras);
                        return;
                    }
                    catch (RemoteException remoteException) {
                        throw new IllegalStateException("Received remote exception during onCameraError callback: ", remoteException);
                    }
                }
            });
        }

        @Override
        public void onIdle() {
            LegacyCameraDevice.this.mIdle.open();
            LegacyCameraDevice.this.mResultHandler.post(new Runnable(){

                @Override
                public void run() {
                    try {
                        LegacyCameraDevice.this.mDeviceCallbacks.onDeviceIdle();
                        return;
                    }
                    catch (RemoteException remoteException) {
                        throw new IllegalStateException("Received remote exception during onCameraIdle callback: ", remoteException);
                    }
                }
            });
        }

        @Override
        public void onRepeatingRequestError(final long l, final int n) {
            LegacyCameraDevice.this.mResultHandler.post(new Runnable(){

                @Override
                public void run() {
                    try {
                        LegacyCameraDevice.this.mDeviceCallbacks.onRepeatingRequestError(l, n);
                        return;
                    }
                    catch (RemoteException remoteException) {
                        throw new IllegalStateException("Received remote exception during onRepeatingRequestError callback: ", remoteException);
                    }
                }
            });
        }

        @Override
        public void onRequestQueueEmpty() {
            LegacyCameraDevice.this.mResultHandler.post(new Runnable(){

                @Override
                public void run() {
                    try {
                        LegacyCameraDevice.this.mDeviceCallbacks.onRequestQueueEmpty();
                        return;
                    }
                    catch (RemoteException remoteException) {
                        throw new IllegalStateException("Received remote exception during onRequestQueueEmpty callback: ", remoteException);
                    }
                }
            });
        }

    };
    private final CameraCharacteristics mStaticCharacteristics;

    public LegacyCameraDevice(int n, Camera camera, CameraCharacteristics cameraCharacteristics, ICameraDeviceCallbacks iCameraDeviceCallbacks) {
        this.mCameraId = n;
        this.mDeviceCallbacks = iCameraDeviceCallbacks;
        this.TAG = String.format("CameraDevice-%d-LE", this.mCameraId);
        this.mResultThread.start();
        this.mResultHandler = new Handler(this.mResultThread.getLooper());
        this.mCallbackHandlerThread.start();
        this.mCallbackHandler = new Handler(this.mCallbackHandlerThread.getLooper());
        this.mDeviceState.setCameraDeviceCallbacks(this.mCallbackHandler, this.mStateListener);
        this.mStaticCharacteristics = cameraCharacteristics;
        this.mRequestThreadManager = new RequestThreadManager(n, camera, cameraCharacteristics, this.mDeviceState);
        this.mRequestThreadManager.start();
    }

    static void connectSurface(Surface surface) throws LegacyExceptionUtils.BufferQueueAbandonedException {
        Preconditions.checkNotNull(surface);
        LegacyExceptionUtils.throwOnError(LegacyCameraDevice.nativeConnectSurface(surface));
    }

    static boolean containsSurfaceId(Surface surface, Collection<Long> collection) {
        try {
            long l = LegacyCameraDevice.getSurfaceId(surface);
            return collection.contains(l);
        }
        catch (LegacyExceptionUtils.BufferQueueAbandonedException bufferQueueAbandonedException) {
            return false;
        }
    }

    public static int detectSurfaceDataspace(Surface surface) throws LegacyExceptionUtils.BufferQueueAbandonedException {
        Preconditions.checkNotNull(surface);
        return LegacyExceptionUtils.throwOnError(LegacyCameraDevice.nativeDetectSurfaceDataspace(surface));
    }

    public static int detectSurfaceType(Surface surface) throws LegacyExceptionUtils.BufferQueueAbandonedException {
        int n;
        Preconditions.checkNotNull(surface);
        int n2 = n = LegacyCameraDevice.nativeDetectSurfaceType(surface);
        if (n >= 1) {
            n2 = n;
            if (n <= 5) {
                n2 = 34;
            }
        }
        return LegacyExceptionUtils.throwOnError(n2);
    }

    static int detectSurfaceUsageFlags(Surface surface) {
        Preconditions.checkNotNull(surface);
        return LegacyCameraDevice.nativeDetectSurfaceUsageFlags(surface);
    }

    static void disconnectSurface(Surface surface) throws LegacyExceptionUtils.BufferQueueAbandonedException {
        if (surface == null) {
            return;
        }
        LegacyExceptionUtils.throwOnError(LegacyCameraDevice.nativeDisconnectSurface(surface));
    }

    static Size findClosestSize(Size size, Size[] arrsize) {
        block5 : {
            if (size == null || arrsize == null) break block5;
            Size size2 = null;
            for (Size size3 : arrsize) {
                Size size4;
                block6 : {
                    block7 : {
                        if (size3.equals(size)) {
                            return size;
                        }
                        size4 = size2;
                        if (size3.getWidth() > 1920) break block6;
                        if (size2 == null) break block7;
                        size4 = size2;
                        if (LegacyCameraDevice.findEuclidDistSquare(size, size3) >= LegacyCameraDevice.findEuclidDistSquare(size2, size3)) break block6;
                    }
                    size4 = size3;
                }
                size2 = size4;
            }
            return size2;
        }
        return null;
    }

    static long findEuclidDistSquare(Size size, Size size2) {
        long l = size.getWidth() - size2.getWidth();
        long l2 = size.getHeight() - size2.getHeight();
        return l * l + l2 * l2;
    }

    private CaptureResultExtras getExtrasFromRequest(RequestHolder requestHolder) {
        return this.getExtrasFromRequest(requestHolder, -1, null);
    }

    private CaptureResultExtras getExtrasFromRequest(RequestHolder requestHolder, int n, Object object) {
        int n2;
        int n3 = n2 = -1;
        if (n == 5) {
            n = this.mConfiguredSurfaces.indexOfValue((Surface)(object = (Surface)object));
            if (n < 0) {
                Log.e(this.TAG, "Buffer drop error reported for unknown Surface");
                n3 = n2;
            } else {
                n3 = this.mConfiguredSurfaces.keyAt(n);
            }
        }
        if (requestHolder == null) {
            return new CaptureResultExtras(-1, -1, -1, -1, -1L, -1, -1, null);
        }
        return new CaptureResultExtras(requestHolder.getRequestId(), requestHolder.getSubsequeceId(), 0, 0, requestHolder.getFrameNumber(), 1, n3, null);
    }

    public static long getSurfaceId(Surface surface) throws LegacyExceptionUtils.BufferQueueAbandonedException {
        Preconditions.checkNotNull(surface);
        try {
            long l = LegacyCameraDevice.nativeGetSurfaceId(surface);
            return l;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new LegacyExceptionUtils.BufferQueueAbandonedException();
        }
    }

    static List<Long> getSurfaceIds(SparseArray<Surface> sparseArray) throws LegacyExceptionUtils.BufferQueueAbandonedException {
        if (sparseArray != null) {
            ArrayList<Long> arrayList = new ArrayList<Long>();
            int n = sparseArray.size();
            for (int i = 0; i < n; ++i) {
                long l = LegacyCameraDevice.getSurfaceId(sparseArray.valueAt(i));
                if (l != 0L) {
                    arrayList.add(l);
                    continue;
                }
                throw new IllegalStateException("Configured surface had null native GraphicBufferProducer pointer!");
            }
            return arrayList;
        }
        throw new NullPointerException("Null argument surfaces");
    }

    static List<Long> getSurfaceIds(Collection<Surface> object) throws LegacyExceptionUtils.BufferQueueAbandonedException {
        if (object != null) {
            ArrayList<Long> arrayList = new ArrayList<Long>();
            object = object.iterator();
            while (object.hasNext()) {
                long l = LegacyCameraDevice.getSurfaceId((Surface)object.next());
                if (l != 0L) {
                    arrayList.add(l);
                    continue;
                }
                throw new IllegalStateException("Configured surface had null native GraphicBufferProducer pointer!");
            }
            return arrayList;
        }
        throw new NullPointerException("Null argument surfaces");
    }

    public static Size getSurfaceSize(Surface surface) throws LegacyExceptionUtils.BufferQueueAbandonedException {
        Preconditions.checkNotNull(surface);
        int[] arrn = new int[2];
        LegacyExceptionUtils.throwOnError(LegacyCameraDevice.nativeDetectSurfaceDimens(surface, arrn));
        return new Size(arrn[0], arrn[1]);
    }

    static Size getTextureSize(SurfaceTexture surfaceTexture) throws LegacyExceptionUtils.BufferQueueAbandonedException {
        Preconditions.checkNotNull(surfaceTexture);
        int[] arrn = new int[2];
        LegacyExceptionUtils.throwOnError(LegacyCameraDevice.nativeDetectTextureDimens(surfaceTexture, arrn));
        return new Size(arrn[0], arrn[1]);
    }

    public static boolean isFlexibleConsumer(Surface surface) {
        int n = LegacyCameraDevice.detectSurfaceUsageFlags(surface);
        boolean bl = (n & 1114112) == 0 && (n & 2307) != 0;
        return bl;
    }

    public static boolean isPreviewConsumer(Surface surface) {
        int n = LegacyCameraDevice.detectSurfaceUsageFlags(surface);
        boolean bl = (n & 1114115) == 0 && (n & 2816) != 0;
        try {
            LegacyCameraDevice.detectSurfaceType(surface);
            return bl;
        }
        catch (LegacyExceptionUtils.BufferQueueAbandonedException bufferQueueAbandonedException) {
            throw new IllegalArgumentException("Surface was abandoned", bufferQueueAbandonedException);
        }
    }

    public static boolean isVideoEncoderConsumer(Surface surface) {
        int n = LegacyCameraDevice.detectSurfaceUsageFlags(surface);
        boolean bl = (n & 1050883) == 0 && (n & 65536) != 0;
        try {
            LegacyCameraDevice.detectSurfaceType(surface);
            return bl;
        }
        catch (LegacyExceptionUtils.BufferQueueAbandonedException bufferQueueAbandonedException) {
            throw new IllegalArgumentException("Surface was abandoned", bufferQueueAbandonedException);
        }
    }

    private static native int nativeConnectSurface(Surface var0);

    private static native int nativeDetectSurfaceDataspace(Surface var0);

    private static native int nativeDetectSurfaceDimens(Surface var0, int[] var1);

    private static native int nativeDetectSurfaceType(Surface var0);

    private static native int nativeDetectSurfaceUsageFlags(Surface var0);

    private static native int nativeDetectTextureDimens(SurfaceTexture var0, int[] var1);

    private static native int nativeDisconnectSurface(Surface var0);

    static native int nativeGetJpegFooterSize();

    private static native long nativeGetSurfaceId(Surface var0);

    private static native int nativeProduceFrame(Surface var0, byte[] var1, int var2, int var3, int var4);

    private static native int nativeSetNextTimestamp(Surface var0, long var1);

    private static native int nativeSetScalingMode(Surface var0, int var1);

    private static native int nativeSetSurfaceDimens(Surface var0, int var1, int var2);

    private static native int nativeSetSurfaceFormat(Surface var0, int var1);

    private static native int nativeSetSurfaceOrientation(Surface var0, int var1, int var2);

    static boolean needsConversion(Surface surface) throws LegacyExceptionUtils.BufferQueueAbandonedException {
        int n = LegacyCameraDevice.detectSurfaceType(surface);
        boolean bl = n == 35 || n == 842094169 || n == 17;
        return bl;
    }

    static void produceFrame(Surface surface, byte[] arrby, int n, int n2, int n3) throws LegacyExceptionUtils.BufferQueueAbandonedException {
        Preconditions.checkNotNull(surface);
        Preconditions.checkNotNull(arrby);
        Preconditions.checkArgumentPositive(n, "width must be positive.");
        Preconditions.checkArgumentPositive(n2, "height must be positive.");
        LegacyExceptionUtils.throwOnError(LegacyCameraDevice.nativeProduceFrame(surface, arrby, n, n2, n3));
    }

    static void setNextTimestamp(Surface surface, long l) throws LegacyExceptionUtils.BufferQueueAbandonedException {
        Preconditions.checkNotNull(surface);
        LegacyExceptionUtils.throwOnError(LegacyCameraDevice.nativeSetNextTimestamp(surface, l));
    }

    static void setScalingMode(Surface surface, int n) throws LegacyExceptionUtils.BufferQueueAbandonedException {
        Preconditions.checkNotNull(surface);
        LegacyExceptionUtils.throwOnError(LegacyCameraDevice.nativeSetScalingMode(surface, n));
    }

    static void setSurfaceDimens(Surface surface, int n, int n2) throws LegacyExceptionUtils.BufferQueueAbandonedException {
        Preconditions.checkNotNull(surface);
        Preconditions.checkArgumentPositive(n, "width must be positive.");
        Preconditions.checkArgumentPositive(n2, "height must be positive.");
        LegacyExceptionUtils.throwOnError(LegacyCameraDevice.nativeSetSurfaceDimens(surface, n, n2));
    }

    static void setSurfaceFormat(Surface surface, int n) throws LegacyExceptionUtils.BufferQueueAbandonedException {
        Preconditions.checkNotNull(surface);
        LegacyExceptionUtils.throwOnError(LegacyCameraDevice.nativeSetSurfaceFormat(surface, n));
    }

    static void setSurfaceOrientation(Surface surface, int n, int n2) throws LegacyExceptionUtils.BufferQueueAbandonedException {
        Preconditions.checkNotNull(surface);
        LegacyExceptionUtils.throwOnError(LegacyCameraDevice.nativeSetSurfaceOrientation(surface, n, n2));
    }

    public long cancelRequest(int n) {
        return this.mRequestThreadManager.cancelRepeating(n);
    }

    @Override
    public void close() {
        this.mRequestThreadManager.quit();
        this.mCallbackHandlerThread.quitSafely();
        this.mResultThread.quitSafely();
        try {
            this.mCallbackHandlerThread.join();
        }
        catch (InterruptedException interruptedException) {
            Log.e(this.TAG, String.format("Thread %s (%d) interrupted while quitting.", this.mCallbackHandlerThread.getName(), this.mCallbackHandlerThread.getId()));
        }
        try {
            this.mResultThread.join();
        }
        catch (InterruptedException interruptedException) {
            Log.e(this.TAG, String.format("Thread %s (%d) interrupted while quitting.", this.mResultThread.getName(), this.mResultThread.getId()));
        }
        this.mClosed = true;
    }

    public int configureOutputs(SparseArray<Surface> sparseArray) {
        return this.configureOutputs(sparseArray, false);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public int configureOutputs(SparseArray<Surface> var1_1, boolean var2_3) {
        block15 : {
            var3_4 = new ArrayList<Pair<Surface, Size>>();
            if (var1_1 == null) break block15;
            var4_5 = var1_1.size();
            for (var5_6 = 0; var5_6 < var4_5; ++var5_6) {
                var6_7 = (Surface)var1_1.valueAt(var5_6);
                if (var6_7 == null) {
                    Log.e(this.TAG, "configureOutputs - null outputs are not allowed");
                    return LegacyExceptionUtils.BAD_VALUE;
                }
                if (!var6_7.isValid()) {
                    Log.e(this.TAG, "configureOutputs - invalid output surfaces are not allowed");
                    return LegacyExceptionUtils.BAD_VALUE;
                }
                var7_8 = this.mStaticCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                try {
                    var8_9 = LegacyCameraDevice.getSurfaceSize(var6_7);
                    var9_10 = LegacyCameraDevice.detectSurfaceType(var6_7);
                    var10_11 = LegacyCameraDevice.isFlexibleConsumer(var6_7);
                    var11_12 = var7_8.getOutputSizes(var9_10);
                    var12_14 = var11_12;
                    if (var11_12 == null) {
                        if (var9_10 == 34) {
                            var12_15 = var7_8.getOutputSizes(35);
                        } else {
                            var12_16 = var11_12;
                            if (var9_10 == 33) {
                                var12_17 = var7_8.getOutputSizes(256);
                            }
                        }
                    }
                    if (ArrayUtils.contains(var12_18, var8_9)) ** GOTO lbl49
                    var11_12 = var8_9;
                    if (!var10_11) ** GOTO lbl-1000
                    var11_12 = var8_9 = (var7_8 = LegacyCameraDevice.findClosestSize((Size)var8_9, (Size[])var12_18));
                    if (var7_8 != null) {
                        var12_19 = new Pair<Surface, Object>(var6_7, var8_9);
                        var3_4.add(var12_19);
                    } else lbl-1000: // 2 sources:
                    {
                        if (var12_18 == null) {
                            var1_1 = "format is invalid.";
                        } else {
                            var1_1 = new StringBuilder();
                            var1_1.append("size not in valid set: ");
                            var1_1.append(Arrays.toString((Object[])var12_18));
                            var1_1 = var1_1.toString();
                        }
                        Log.e(this.TAG, String.format("Surface with size (w=%d, h=%d) and format 0x%x is not valid, %s", new Object[]{var11_12.getWidth(), var11_12.getHeight(), var9_10, var1_1}));
                        return LegacyExceptionUtils.BAD_VALUE;
lbl49: // 1 sources:
                        var12_20 = new Pair<Surface, Size>(var6_7, (Size)var8_9);
                        var3_4.add(var12_20);
                    }
                    if (var2_3) continue;
                    LegacyCameraDevice.setSurfaceDimens(var6_7, var8_9.getWidth(), var8_9.getHeight());
                    continue;
                }
                catch (LegacyExceptionUtils.BufferQueueAbandonedException var1_2) {
                    Log.e(this.TAG, "Surface bufferqueue is abandoned, cannot configure as output: ", var1_2);
                    return LegacyExceptionUtils.BAD_VALUE;
                }
            }
        }
        if (var2_3) {
            return 0;
        }
        var2_3 = false;
        if (this.mDeviceState.setConfiguring()) {
            this.mRequestThreadManager.configure(var3_4);
            var2_3 = this.mDeviceState.setIdle();
        }
        if (var2_3 == false) return LegacyExceptionUtils.INVALID_OPERATION;
        this.mConfiguredSurfaces = var1_1;
        return 0;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected void finalize() throws Throwable {
        this.close();
lbl3: // 2 sources:
        do {
            super.finalize();
            return;
            break;
        } while (true);
        {
            catch (Throwable var1_1) {
            }
            catch (ServiceSpecificException var2_3) {}
            {
                var3_4 = this.TAG;
                var1_2 = new StringBuilder();
                var1_2.append("Got error while trying to finalize, ignoring: ");
                var1_2.append(var2_3.getMessage());
                Log.e(var3_4, var1_2.toString());
                ** continue;
            }
        }
        super.finalize();
        throw var1_1;
    }

    public long flush() {
        long l = this.mRequestThreadManager.flush();
        this.waitUntilIdle();
        return l;
    }

    public boolean isClosed() {
        return this.mClosed;
    }

    public SubmitInfo submitRequest(CaptureRequest captureRequest, boolean bl) {
        return this.submitRequestList(new CaptureRequest[]{captureRequest}, bl);
    }

    public SubmitInfo submitRequestList(CaptureRequest[] arrcaptureRequest, boolean bl) {
        if (arrcaptureRequest != null && arrcaptureRequest.length != 0) {
            List<Object> list;
            try {
                list = this.mConfiguredSurfaces == null ? new ArrayList<E>() : LegacyCameraDevice.getSurfaceIds(this.mConfiguredSurfaces);
            }
            catch (LegacyExceptionUtils.BufferQueueAbandonedException bufferQueueAbandonedException) {
                throw new ServiceSpecificException(LegacyExceptionUtils.BAD_VALUE, "submitRequestList - configured surface is abandoned.");
            }
            int n = arrcaptureRequest.length;
            for (int i = 0; i < n; ++i) {
                CaptureRequest captureRequest = arrcaptureRequest[i];
                if (!captureRequest.getTargets().isEmpty()) {
                    for (Surface surface : captureRequest.getTargets()) {
                        if (surface != null) {
                            if (this.mConfiguredSurfaces != null) {
                                if (LegacyCameraDevice.containsSurfaceId(surface, list)) continue;
                                Log.e(this.TAG, "submitRequestList - cannot use a surface that wasn't configured");
                                throw new ServiceSpecificException(LegacyExceptionUtils.BAD_VALUE, "submitRequestList - cannot use a surface that wasn't configured");
                            }
                            Log.e(this.TAG, "submitRequestList - must configure  device with valid surfaces before submitting requests");
                            throw new ServiceSpecificException(LegacyExceptionUtils.INVALID_OPERATION, "submitRequestList - must configure  device with valid surfaces before submitting requests");
                        }
                        Log.e(this.TAG, "submitRequestList - Null Surface targets are not allowed");
                        throw new ServiceSpecificException(LegacyExceptionUtils.BAD_VALUE, "submitRequestList - Null Surface targets are not allowed");
                    }
                    continue;
                }
                Log.e(this.TAG, "submitRequestList - Each request must have at least one Surface target");
                throw new ServiceSpecificException(LegacyExceptionUtils.BAD_VALUE, "submitRequestList - Each request must have at least one Surface target");
            }
            this.mIdle.close();
            return this.mRequestThreadManager.submitCaptureRequests(arrcaptureRequest, bl);
        }
        Log.e(this.TAG, "submitRequestList - Empty/null requests are not allowed");
        throw new ServiceSpecificException(LegacyExceptionUtils.BAD_VALUE, "submitRequestList - Empty/null requests are not allowed");
    }

    public void waitUntilIdle() {
        this.mIdle.block();
    }

}

