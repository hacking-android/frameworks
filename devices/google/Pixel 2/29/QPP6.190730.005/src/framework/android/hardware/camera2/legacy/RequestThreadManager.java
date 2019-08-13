/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.legacy;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.legacy.CameraDeviceState;
import android.hardware.camera2.legacy.CaptureCollector;
import android.hardware.camera2.legacy.GLThreadManager;
import android.hardware.camera2.legacy.LegacyCameraDevice;
import android.hardware.camera2.legacy.LegacyExceptionUtils;
import android.hardware.camera2.legacy.LegacyFaceDetectMapper;
import android.hardware.camera2.legacy.LegacyFocusStateMapper;
import android.hardware.camera2.legacy.LegacyRequest;
import android.hardware.camera2.legacy.LegacyResultMapper;
import android.hardware.camera2.legacy.ParameterUtils;
import android.hardware.camera2.legacy.RequestHandlerThread;
import android.hardware.camera2.legacy.RequestHolder;
import android.hardware.camera2.legacy.RequestQueue;
import android.hardware.camera2.utils.SizeAreaComparator;
import android.hardware.camera2.utils.SubmitInfo;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.util.Pair;
import android.util.Size;
import android.view.Surface;
import com.android.internal.util.Preconditions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class RequestThreadManager {
    private static final float ASPECT_RATIO_TOLERANCE = 0.01f;
    private static final boolean DEBUG = false;
    private static final int JPEG_FRAME_TIMEOUT = 4000;
    private static final int MAX_IN_FLIGHT_REQUESTS = 2;
    private static final int MSG_CLEANUP = 3;
    private static final int MSG_CONFIGURE_OUTPUTS = 1;
    private static final int MSG_SUBMIT_CAPTURE_REQUEST = 2;
    private static final int PREVIEW_FRAME_TIMEOUT = 1000;
    private static final int REQUEST_COMPLETE_TIMEOUT = 4000;
    private static final boolean USE_BLOB_FORMAT_OVERRIDE = true;
    private static final boolean VERBOSE = false;
    private final String TAG;
    private final List<Surface> mCallbackOutputs = new ArrayList<Surface>();
    private Camera mCamera;
    private final int mCameraId;
    private final CaptureCollector mCaptureCollector;
    private final CameraCharacteristics mCharacteristics;
    private final CameraDeviceState mDeviceState;
    private Surface mDummySurface;
    private SurfaceTexture mDummyTexture;
    private final Camera.ErrorCallback mErrorCallback = new Camera.ErrorCallback(){

        @Override
        public void onError(int n, Camera object) {
            if (n != 2) {
                if (n != 3) {
                    String string2 = RequestThreadManager.this.TAG;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Received error ");
                    ((StringBuilder)object).append(n);
                    ((StringBuilder)object).append(" from the Camera1 ErrorCallback");
                    Log.e(string2, ((StringBuilder)object).toString());
                    RequestThreadManager.this.mDeviceState.setError(1);
                } else {
                    RequestThreadManager.this.flush();
                    RequestThreadManager.this.mDeviceState.setError(6);
                }
            } else {
                RequestThreadManager.this.flush();
                RequestThreadManager.this.mDeviceState.setError(0);
            }
        }
    };
    private final LegacyFaceDetectMapper mFaceDetectMapper;
    private final LegacyFocusStateMapper mFocusStateMapper;
    private GLThreadManager mGLThreadManager;
    private final Object mIdleLock = new Object();
    private Size mIntermediateBufferSize;
    private final Camera.PictureCallback mJpegCallback = new Camera.PictureCallback(){

        @Override
        public void onPictureTaken(byte[] arrby, Camera iterator) {
            Log.i(RequestThreadManager.this.TAG, "Received jpeg.");
            Pair<RequestHolder, Long> object2 = RequestThreadManager.this.mCaptureCollector.jpegProduced();
            if (object2 != null && object2.first != null) {
                iterator = (RequestHolder)object2.first;
                long l = (Long)object2.second;
                for (Surface surface : ((RequestHolder)((Object)iterator)).getHolderTargets()) {
                    try {
                        if (!LegacyCameraDevice.containsSurfaceId(surface, RequestThreadManager.this.mJpegSurfaceIds)) continue;
                        Log.i(RequestThreadManager.this.TAG, "Producing jpeg buffer...");
                        int n = arrby.length;
                        int n2 = LegacyCameraDevice.nativeGetJpegFooterSize();
                        LegacyCameraDevice.setNextTimestamp(surface, l);
                        LegacyCameraDevice.setSurfaceFormat(surface, 1);
                        n2 = (int)Math.ceil(Math.sqrt(n + n2 + 3 & -4)) + 15 & -16;
                        LegacyCameraDevice.setSurfaceDimens(surface, n2, n2);
                        LegacyCameraDevice.produceFrame(surface, arrby, n2, n2, 33);
                    }
                    catch (LegacyExceptionUtils.BufferQueueAbandonedException bufferQueueAbandonedException) {
                        Log.w(RequestThreadManager.this.TAG, "Surface abandoned, dropping frame. ", bufferQueueAbandonedException);
                    }
                }
                RequestThreadManager.this.mReceivedJpeg.open();
                return;
            }
            Log.e(RequestThreadManager.this.TAG, "Dropping jpeg frame.");
        }
    };
    private final Camera.ShutterCallback mJpegShutterCallback = new Camera.ShutterCallback(){

        @Override
        public void onShutter() {
            RequestThreadManager.this.mCaptureCollector.jpegCaptured(SystemClock.elapsedRealtimeNanos());
        }
    };
    private final List<Long> mJpegSurfaceIds = new ArrayList<Long>();
    private LegacyRequest mLastRequest = null;
    private Camera.Parameters mParams;
    private final FpsCounter mPrevCounter = new FpsCounter("Incoming Preview");
    private final SurfaceTexture.OnFrameAvailableListener mPreviewCallback = new SurfaceTexture.OnFrameAvailableListener(){

        @Override
        public void onFrameAvailable(SurfaceTexture surfaceTexture) {
            RequestThreadManager.this.mGLThreadManager.queueNewFrame();
        }
    };
    private final List<Surface> mPreviewOutputs = new ArrayList<Surface>();
    private boolean mPreviewRunning = false;
    private SurfaceTexture mPreviewTexture;
    private final AtomicBoolean mQuit = new AtomicBoolean(false);
    private final ConditionVariable mReceivedJpeg = new ConditionVariable(false);
    private final FpsCounter mRequestCounter = new FpsCounter("Incoming Requests");
    private final Handler.Callback mRequestHandlerCb = new Handler.Callback(){
        private boolean mCleanup = false;
        private final LegacyResultMapper mMapper = new LegacyResultMapper();

        /*
         * Exception decompiling
         */
        @Override
        public boolean handleMessage(Message var1_1) {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // java.lang.IllegalStateException: Backjump on non jumping statement [] lbl133 : TryStatement: try { 11[TRYBLOCK]

            // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Cleaner$1.call(Cleaner.java:44)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Cleaner$1.call(Cleaner.java:22)
            // org.benf.cfr.reader.util.graph.GraphVisitorDFS.process(GraphVisitorDFS.java:68)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Cleaner.removeUnreachableCode(Cleaner.java:54)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.RemoveDeterministicJumps.apply(RemoveDeterministicJumps.java:38)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:447)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
            // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
            // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:778)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:886)
            // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
            // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
            // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
            // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
            // org.benf.cfr.reader.Main.main(Main.java:48)
            throw new IllegalStateException("Decompilation failed");
        }
    };
    private final RequestQueue mRequestQueue = new RequestQueue(this.mJpegSurfaceIds);
    private final RequestHandlerThread mRequestThread;

    public RequestThreadManager(int n, Camera object, CameraCharacteristics cameraCharacteristics, CameraDeviceState cameraDeviceState) {
        this.mCamera = Preconditions.checkNotNull(object, "camera must not be null");
        this.mCameraId = n;
        this.mCharacteristics = Preconditions.checkNotNull(cameraCharacteristics, "characteristics must not be null");
        this.TAG = object = String.format("RequestThread-%d", n);
        this.mDeviceState = Preconditions.checkNotNull(cameraDeviceState, "deviceState must not be null");
        this.mFocusStateMapper = new LegacyFocusStateMapper(this.mCamera);
        this.mFaceDetectMapper = new LegacyFaceDetectMapper(this.mCamera, this.mCharacteristics);
        this.mCaptureCollector = new CaptureCollector(2, this.mDeviceState);
        this.mRequestThread = new RequestHandlerThread((String)object, this.mRequestHandlerCb);
        this.mCamera.setDetailedErrorCallback(this.mErrorCallback);
    }

    static /* synthetic */ LegacyRequest access$1000(RequestThreadManager requestThreadManager) {
        return requestThreadManager.mLastRequest;
    }

    static /* synthetic */ LegacyRequest access$1002(RequestThreadManager requestThreadManager, LegacyRequest legacyRequest) {
        requestThreadManager.mLastRequest = legacyRequest;
        return legacyRequest;
    }

    static /* synthetic */ Camera.Parameters access$1100(RequestThreadManager requestThreadManager) {
        return requestThreadManager.mParams;
    }

    static /* synthetic */ Camera.Parameters access$1102(RequestThreadManager requestThreadManager, Camera.Parameters parameters) {
        requestThreadManager.mParams = parameters;
        return parameters;
    }

    static /* synthetic */ CameraCharacteristics access$1200(RequestThreadManager requestThreadManager) {
        return requestThreadManager.mCharacteristics;
    }

    static /* synthetic */ Camera access$1300(RequestThreadManager requestThreadManager) {
        return requestThreadManager.mCamera;
    }

    static /* synthetic */ Camera access$1302(RequestThreadManager requestThreadManager, Camera camera) {
        requestThreadManager.mCamera = camera;
        return camera;
    }

    static /* synthetic */ void access$1400(RequestThreadManager requestThreadManager, RequestHolder requestHolder) throws IOException {
        requestThreadManager.doPreviewCapture(requestHolder);
    }

    static /* synthetic */ void access$1500(RequestThreadManager requestThreadManager, RequestHolder requestHolder) throws IOException {
        requestThreadManager.doJpegCapturePrepare(requestHolder);
    }

    static /* synthetic */ LegacyFaceDetectMapper access$1600(RequestThreadManager requestThreadManager) {
        return requestThreadManager.mFaceDetectMapper;
    }

    static /* synthetic */ LegacyFocusStateMapper access$1700(RequestThreadManager requestThreadManager) {
        return requestThreadManager.mFocusStateMapper;
    }

    static /* synthetic */ void access$1800(RequestThreadManager requestThreadManager, RequestHolder requestHolder) {
        requestThreadManager.doJpegCapture(requestHolder);
    }

    static /* synthetic */ void access$1900(RequestThreadManager requestThreadManager) {
        requestThreadManager.disconnectCallbackSurfaces();
    }

    static /* synthetic */ GLThreadManager access$502(RequestThreadManager requestThreadManager, GLThreadManager gLThreadManager) {
        requestThreadManager.mGLThreadManager = gLThreadManager;
        return gLThreadManager;
    }

    static /* synthetic */ void access$600(RequestThreadManager requestThreadManager, Collection collection) {
        requestThreadManager.configureOutputs(collection);
    }

    static /* synthetic */ RequestHandlerThread access$700(RequestThreadManager requestThreadManager) {
        return requestThreadManager.mRequestThread;
    }

    static /* synthetic */ RequestQueue access$800(RequestThreadManager requestThreadManager) {
        return requestThreadManager.mRequestQueue;
    }

    static /* synthetic */ Object access$900(RequestThreadManager requestThreadManager) {
        return requestThreadManager.mIdleLock;
    }

    private Size calculatePictureSize(List<Surface> object, List<Size> object2, Camera.Parameters object3) {
        if (object.size() == object2.size()) {
            Object object4 = new ArrayList<Size>();
            object2 = object2.iterator();
            object = object.iterator();
            while (object.hasNext()) {
                Surface surface = (Surface)object.next();
                Size size = (Size)object2.next();
                if (!LegacyCameraDevice.containsSurfaceId(surface, this.mJpegSurfaceIds)) continue;
                object4.add(size);
            }
            if (!object4.isEmpty()) {
                int n = -1;
                int n2 = -1;
                object2 = object4.iterator();
                while (object2.hasNext()) {
                    object = (Size)object2.next();
                    if (((Size)object).getWidth() > n) {
                        n = ((Size)object).getWidth();
                    }
                    if (((Size)object).getHeight() <= n2) continue;
                    n2 = ((Size)object).getHeight();
                }
                object = new Size(n, n2);
                object3 = ParameterUtils.convertSizeList(((Camera.Parameters)object3).getSupportedPictureSizes());
                object2 = new ArrayList();
                object4 = object3.iterator();
                while (object4.hasNext()) {
                    object3 = (Size)object4.next();
                    if (((Size)object3).getWidth() < n || ((Size)object3).getHeight() < n2) continue;
                    object2.add(object3);
                }
                if (!object2.isEmpty()) {
                    if (!((Size)(object2 = Collections.min(object2, new SizeAreaComparator()))).equals(object)) {
                        Log.w(this.TAG, String.format("configureOutputs - Will need to crop picture %s into smallest bound size %s", object2, object));
                    }
                    return object2;
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Could not find any supported JPEG sizes large enough to fit ");
                ((StringBuilder)object2).append(object);
                throw new AssertionError((Object)((StringBuilder)object2).toString());
            }
            return null;
        }
        throw new IllegalStateException("Input collections must be same length");
    }

    private static boolean checkAspectRatiosMatch(Size size, Size size2) {
        boolean bl = Math.abs((float)size.getWidth() / (float)size.getHeight() - (float)size2.getWidth() / (float)size2.getHeight()) < 0.01f;
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void configureOutputs(Collection<Pair<Surface, Size>> object) {
        ArrayList<Size> arrayList;
        int n;
        Iterator<Surface> iterator;
        int n2;
        ArrayList<Size> arrayList2;
        block28 : {
            try {
                this.stopPreview();
            }
            catch (RuntimeException runtimeException) {
                Log.e(this.TAG, "Received device exception in configure call: ", runtimeException);
                this.mDeviceState.setError(1);
                return;
            }
            try {
                this.mCamera.setPreviewTexture(null);
            }
            catch (RuntimeException runtimeException) {
                Log.e(this.TAG, "Received device exception in configure call: ", runtimeException);
                this.mDeviceState.setError(1);
                return;
            }
            catch (IOException iOException) {
                Log.w(this.TAG, "Failed to clear prior SurfaceTexture, may cause GL deadlock: ", iOException);
            }
            GLThreadManager gLThreadManager = this.mGLThreadManager;
            if (gLThreadManager != null) {
                gLThreadManager.waitUntilStarted();
                this.mGLThreadManager.ignoreNewFrames();
                this.mGLThreadManager.waitUntilIdle();
            }
            this.resetJpegSurfaceFormats(this.mCallbackOutputs);
            this.disconnectCallbackSurfaces();
            this.mPreviewOutputs.clear();
            this.mCallbackOutputs.clear();
            this.mJpegSurfaceIds.clear();
            this.mPreviewTexture = null;
            arrayList2 = new ArrayList<Size>();
            arrayList = new ArrayList<Size>();
            n = this.mCharacteristics.get(CameraCharacteristics.LENS_FACING);
            n2 = this.mCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
            if (object == null) break block28;
            object = object.iterator();
            while (object.hasNext()) {
                iterator = (Pair)object.next();
                Surface surface = (Surface)((Pair)iterator).first;
                iterator = (Size)((Pair)iterator).second;
                try {
                    int n3 = LegacyCameraDevice.detectSurfaceType(surface);
                    LegacyCameraDevice.setSurfaceOrientation(surface, n, n2);
                    if (n3 != 33) {
                        LegacyCameraDevice.setScalingMode(surface, 1);
                        this.mPreviewOutputs.add(surface);
                        arrayList2.add((Size)((Object)iterator));
                        continue;
                    }
                    LegacyCameraDevice.setSurfaceFormat(surface, 1);
                    this.mJpegSurfaceIds.add(LegacyCameraDevice.getSurfaceId(surface));
                    this.mCallbackOutputs.add(surface);
                    arrayList.add((Size)((Object)iterator));
                    LegacyCameraDevice.connectSurface(surface);
                }
                catch (LegacyExceptionUtils.BufferQueueAbandonedException bufferQueueAbandonedException) {
                    Log.w(this.TAG, "Surface abandoned, skipping...", bufferQueueAbandonedException);
                }
            }
        }
        try {
            this.mParams = this.mCamera.getParameters();
        }
        catch (RuntimeException runtimeException) {
            Log.e(this.TAG, "Received device exception: ", runtimeException);
            this.mDeviceState.setError(1);
            return;
        }
        iterator = this.mParams.getSupportedPreviewFpsRange();
        int[] arrn = this.getPhotoPreviewFpsRange((List<int[]>)((Object)iterator));
        this.mParams.setPreviewFpsRange(arrn[0], arrn[1]);
        Size size = this.calculatePictureSize(this.mCallbackOutputs, arrayList, this.mParams);
        if (arrayList2.size() > 0) {
            Size size2 = SizeAreaComparator.findLargestByArea(arrayList2);
            object = ParameterUtils.getLargestSupportedJpegSizeByArea(this.mParams);
            if (size != null) {
                object = size;
            }
            Object object2 = ParameterUtils.convertSizeList(this.mParams.getSupportedPreviewSizes());
            long l = size2.getHeight();
            long l2 = size2.getWidth();
            Size size3 = SizeAreaComparator.findLargestByArea(object2);
            object2 = object2.iterator();
            while (object2.hasNext()) {
                Size size4 = (Size)object2.next();
                long l3 = size4.getWidth() * size4.getHeight();
                long l4 = size3.getWidth() * size3.getHeight();
                Size size5 = size3;
                if (RequestThreadManager.checkAspectRatiosMatch((Size)object, size4)) {
                    size5 = size3;
                    if (l3 < l4) {
                        size5 = size3;
                        if (l3 >= l * l2) {
                            size5 = size4;
                        }
                    }
                }
                size3 = size5;
            }
            this.mIntermediateBufferSize = size3;
            this.mParams.setPreviewSize(this.mIntermediateBufferSize.getWidth(), this.mIntermediateBufferSize.getHeight());
        } else {
            this.mIntermediateBufferSize = null;
        }
        if (size != null) {
            String string2 = this.TAG;
            object = new StringBuilder();
            ((StringBuilder)object).append("configureOutputs - set take picture size to ");
            ((StringBuilder)object).append(size);
            Log.i(string2, ((StringBuilder)object).toString());
            this.mParams.setPictureSize(size.getWidth(), size.getHeight());
        }
        if (this.mGLThreadManager == null) {
            this.mGLThreadManager = new GLThreadManager(this.mCameraId, n, this.mDeviceState);
            this.mGLThreadManager.start();
        }
        this.mGLThreadManager.waitUntilStarted();
        object = new ArrayList<Pair<Surface, Size>>();
        Iterator iterator2 = arrayList2.iterator();
        iterator = this.mPreviewOutputs.iterator();
        while (iterator.hasNext()) {
            object.add(new Pair<Surface, Size>(iterator.next(), (Size)iterator2.next()));
        }
        this.mGLThreadManager.setConfigurationAndWait((Collection<Pair<Surface, Size>>)object, this.mCaptureCollector);
        object = this.mPreviewOutputs.iterator();
        while (object.hasNext()) {
            Surface surface = (Surface)object.next();
            try {
                LegacyCameraDevice.setSurfaceOrientation(surface, n, n2);
            }
            catch (LegacyExceptionUtils.BufferQueueAbandonedException bufferQueueAbandonedException) {
                Log.e(this.TAG, "Surface abandoned, skipping setSurfaceOrientation()", bufferQueueAbandonedException);
            }
        }
        this.mGLThreadManager.allowNewFrames();
        this.mPreviewTexture = this.mGLThreadManager.getCurrentSurfaceTexture();
        object = this.mPreviewTexture;
        if (object != null) {
            ((SurfaceTexture)object).setOnFrameAvailableListener(this.mPreviewCallback);
        }
        try {
            this.mCamera.setParameters(this.mParams);
            return;
        }
        catch (RuntimeException runtimeException) {
            Log.e(this.TAG, "Received device exception while configuring: ", runtimeException);
            this.mDeviceState.setError(1);
        }
    }

    private void createDummySurface() {
        if (this.mDummyTexture == null || this.mDummySurface == null) {
            this.mDummyTexture = new SurfaceTexture(0);
            this.mDummyTexture.setDefaultBufferSize(640, 480);
            this.mDummySurface = new Surface(this.mDummyTexture);
        }
    }

    private void disconnectCallbackSurfaces() {
        for (Surface surface : this.mCallbackOutputs) {
            try {
                LegacyCameraDevice.disconnectSurface(surface);
            }
            catch (LegacyExceptionUtils.BufferQueueAbandonedException bufferQueueAbandonedException) {
                Log.d(this.TAG, "Surface abandoned, skipping...", bufferQueueAbandonedException);
            }
        }
    }

    private void doJpegCapture(RequestHolder requestHolder) {
        this.mCamera.takePicture(this.mJpegShutterCallback, null, this.mJpegCallback);
        this.mPreviewRunning = false;
    }

    private void doJpegCapturePrepare(RequestHolder requestHolder) throws IOException {
        if (!this.mPreviewRunning) {
            this.createDummySurface();
            this.mCamera.setPreviewTexture(this.mDummyTexture);
            this.startPreview();
        }
    }

    private void doPreviewCapture(RequestHolder object) throws IOException {
        if (this.mPreviewRunning) {
            return;
        }
        object = this.mPreviewTexture;
        if (object != null) {
            ((SurfaceTexture)object).setDefaultBufferSize(this.mIntermediateBufferSize.getWidth(), this.mIntermediateBufferSize.getHeight());
            this.mCamera.setPreviewTexture(this.mPreviewTexture);
            this.startPreview();
            return;
        }
        throw new IllegalStateException("Preview capture called with no preview surfaces configured.");
    }

    private int[] getPhotoPreviewFpsRange(List<int[]> list) {
        if (list.size() == 0) {
            Log.e(this.TAG, "No supported frame rates returned!");
            return null;
        }
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        for (int[] arrn : list) {
            int n5;
            int n6;
            int n7;
            block6 : {
                int n8;
                int n9;
                block5 : {
                    n9 = arrn[0];
                    n8 = arrn[1];
                    if (n8 > n2) break block5;
                    n7 = n;
                    n5 = n2;
                    n6 = n3;
                    if (n8 != n2) break block6;
                    n7 = n;
                    n5 = n2;
                    n6 = n3;
                    if (n9 <= n) break block6;
                }
                n7 = n9;
                n5 = n8;
                n6 = n4;
            }
            ++n4;
            n = n7;
            n2 = n5;
            n3 = n6;
        }
        return list.get(n3);
    }

    private void resetJpegSurfaceFormats(Collection<Surface> object) {
        if (object == null) {
            return;
        }
        object = object.iterator();
        while (object.hasNext()) {
            Surface surface = (Surface)object.next();
            if (surface != null && surface.isValid()) {
                try {
                    LegacyCameraDevice.setSurfaceFormat(surface, 33);
                }
                catch (LegacyExceptionUtils.BufferQueueAbandonedException bufferQueueAbandonedException) {
                    Log.w(this.TAG, "Surface abandoned, skipping...", bufferQueueAbandonedException);
                }
                continue;
            }
            Log.w(this.TAG, "Jpeg surface is invalid, skipping...");
        }
    }

    private void startPreview() {
        if (!this.mPreviewRunning) {
            this.mCamera.startPreview();
            this.mPreviewRunning = true;
        }
    }

    private void stopPreview() {
        if (this.mPreviewRunning) {
            this.mCamera.stopPreview();
            this.mPreviewRunning = false;
        }
    }

    public long cancelRepeating(int n) {
        return this.mRequestQueue.stopRepeating(n);
    }

    public void configure(Collection<Pair<Surface, Size>> collection) {
        Handler handler = this.mRequestThread.waitAndGetHandler();
        ConditionVariable conditionVariable = new ConditionVariable(false);
        handler.sendMessage(handler.obtainMessage(1, 0, 0, new ConfigureHolder(conditionVariable, collection)));
        conditionVariable.block();
    }

    public long flush() {
        Log.i(this.TAG, "Flushing all pending requests.");
        long l = this.mRequestQueue.stopRepeating();
        this.mCaptureCollector.failAll();
        return l;
    }

    public void quit() {
        if (!this.mQuit.getAndSet(true)) {
            Handler handler = this.mRequestThread.waitAndGetHandler();
            handler.sendMessageAtFrontOfQueue(handler.obtainMessage(3));
            this.mRequestThread.quitSafely();
            try {
                this.mRequestThread.join();
            }
            catch (InterruptedException interruptedException) {
                Log.e(this.TAG, String.format("Thread %s (%d) interrupted while quitting.", this.mRequestThread.getName(), this.mRequestThread.getId()));
            }
        }
    }

    public void start() {
        this.mRequestThread.start();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public SubmitInfo submitCaptureRequests(CaptureRequest[] object, boolean bl) {
        Handler handler = this.mRequestThread.waitAndGetHandler();
        Object object2 = this.mIdleLock;
        synchronized (object2) {
            object = this.mRequestQueue.submit((CaptureRequest[])object, bl);
            handler.sendEmptyMessage(2);
            return object;
        }
    }

    private static class ConfigureHolder {
        public final ConditionVariable condition;
        public final Collection<Pair<Surface, Size>> surfaces;

        public ConfigureHolder(ConditionVariable conditionVariable, Collection<Pair<Surface, Size>> collection) {
            this.condition = conditionVariable;
            this.surfaces = collection;
        }
    }

    public static class FpsCounter {
        private static final long NANO_PER_SECOND = 1000000000L;
        private static final String TAG = "FpsCounter";
        private int mFrameCount = 0;
        private double mLastFps = 0.0;
        private long mLastPrintTime = 0L;
        private long mLastTime = 0L;
        private final String mStreamType;

        public FpsCounter(String string2) {
            this.mStreamType = string2;
        }

        public double checkFps() {
            synchronized (this) {
                double d = this.mLastFps;
                return d;
            }
        }

        public void countAndLog() {
            synchronized (this) {
                this.countFrame();
                this.staggeredLog();
                return;
            }
        }

        public void countFrame() {
            synchronized (this) {
                ++this.mFrameCount;
                long l = SystemClock.elapsedRealtimeNanos();
                if (this.mLastTime == 0L) {
                    this.mLastTime = l;
                }
                if (l > this.mLastTime + 1000000000L) {
                    long l2 = this.mLastTime;
                    this.mLastFps = (double)this.mFrameCount * (1.0E9 / (double)(l - l2));
                    this.mFrameCount = 0;
                    this.mLastTime = l;
                }
                return;
            }
        }

        public void staggeredLog() {
            synchronized (this) {
                if (this.mLastTime > this.mLastPrintTime + 5000000000L) {
                    this.mLastPrintTime = this.mLastTime;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("FPS for ");
                    stringBuilder.append(this.mStreamType);
                    stringBuilder.append(" stream: ");
                    stringBuilder.append(this.mLastFps);
                    Log.d(TAG, stringBuilder.toString());
                }
                return;
            }
        }
    }

}

