/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.hardware.camera2.impl.-$
 *  android.hardware.camera2.impl.-$$Lambda
 *  android.hardware.camera2.impl.-$$Lambda$CameraDeviceImpl
 *  android.hardware.camera2.impl.-$$Lambda$CameraDeviceImpl$CameraDeviceCallbacks
 *  android.hardware.camera2.impl.-$$Lambda$CameraDeviceImpl$CameraDeviceCallbacks$Sm85frAzwGZVMAK-NE_gwckYXVQ
 */
package android.hardware.camera2.impl;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.ICameraDeviceCallbacks;
import android.hardware.camera2.ICameraDeviceUser;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.impl.-$;
import android.hardware.camera2.impl.CameraCaptureSessionCore;
import android.hardware.camera2.impl.CameraCaptureSessionImpl;
import android.hardware.camera2.impl.CameraConstrainedHighSpeedCaptureSessionImpl;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.impl.CaptureResultExtras;
import android.hardware.camera2.impl.ICameraDeviceUserWrapper;
import android.hardware.camera2.impl.PhysicalCaptureResultInfo;
import android.hardware.camera2.impl._$$Lambda$CameraDeviceImpl$CameraDeviceCallbacks$Sm85frAzwGZVMAK_NE_gwckYXVQ;
import android.hardware.camera2.params.InputConfiguration;
import android.hardware.camera2.params.OutputConfiguration;
import android.hardware.camera2.params.SessionConfiguration;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.hardware.camera2.utils.SubmitInfo;
import android.hardware.camera2.utils.SurfaceUtils;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.util.Log;
import android.util.Range;
import android.util.Size;
import android.util.SparseArray;
import android.view.Surface;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.pooled.PooledLambda;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

public class CameraDeviceImpl
extends CameraDevice
implements IBinder.DeathRecipient {
    private static final long NANO_PER_SECOND = 1000000000L;
    private static final int REQUEST_ID_NONE = -1;
    private final boolean DEBUG;
    private final String TAG;
    private final int mAppTargetSdkVersion;
    private final Runnable mCallOnActive = new Runnable(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void run() {
            Object object = CameraDeviceImpl.this.mInterfaceLock;
            // MONITORENTER : object
            if (CameraDeviceImpl.this.mRemoteDevice == null) {
                // MONITOREXIT : object
                return;
            }
            StateCallbackKK stateCallbackKK = CameraDeviceImpl.this.mSessionStateCallback;
            // MONITOREXIT : object
            if (stateCallbackKK == null) return;
            stateCallbackKK.onActive(CameraDeviceImpl.this);
        }
    };
    private final Runnable mCallOnBusy = new Runnable(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void run() {
            Object object = CameraDeviceImpl.this.mInterfaceLock;
            // MONITORENTER : object
            if (CameraDeviceImpl.this.mRemoteDevice == null) {
                // MONITOREXIT : object
                return;
            }
            StateCallbackKK stateCallbackKK = CameraDeviceImpl.this.mSessionStateCallback;
            // MONITOREXIT : object
            if (stateCallbackKK == null) return;
            stateCallbackKK.onBusy(CameraDeviceImpl.this);
        }
    };
    private final Runnable mCallOnClosed = new Runnable(){
        private boolean mClosedOnce = false;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void run() {
            if (this.mClosedOnce) throw new AssertionError((Object)"Don't post #onClosed more than once");
            Object object = CameraDeviceImpl.this.mInterfaceLock;
            // MONITORENTER : object
            StateCallbackKK stateCallbackKK = CameraDeviceImpl.this.mSessionStateCallback;
            // MONITOREXIT : object
            if (stateCallbackKK != null) {
                stateCallbackKK.onClosed(CameraDeviceImpl.this);
            }
            CameraDeviceImpl.this.mDeviceCallback.onClosed(CameraDeviceImpl.this);
            this.mClosedOnce = true;
        }
    };
    private final Runnable mCallOnDisconnected = new Runnable(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void run() {
            Object object = CameraDeviceImpl.this.mInterfaceLock;
            // MONITORENTER : object
            if (CameraDeviceImpl.this.mRemoteDevice == null) {
                // MONITOREXIT : object
                return;
            }
            StateCallbackKK stateCallbackKK = CameraDeviceImpl.this.mSessionStateCallback;
            // MONITOREXIT : object
            if (stateCallbackKK != null) {
                stateCallbackKK.onDisconnected(CameraDeviceImpl.this);
            }
            CameraDeviceImpl.this.mDeviceCallback.onDisconnected(CameraDeviceImpl.this);
        }
    };
    private final Runnable mCallOnIdle = new Runnable(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void run() {
            Object object = CameraDeviceImpl.this.mInterfaceLock;
            // MONITORENTER : object
            if (CameraDeviceImpl.this.mRemoteDevice == null) {
                // MONITOREXIT : object
                return;
            }
            StateCallbackKK stateCallbackKK = CameraDeviceImpl.this.mSessionStateCallback;
            // MONITOREXIT : object
            if (stateCallbackKK == null) return;
            stateCallbackKK.onIdle(CameraDeviceImpl.this);
        }
    };
    private final Runnable mCallOnOpened = new Runnable(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void run() {
            Object object = CameraDeviceImpl.this.mInterfaceLock;
            // MONITORENTER : object
            if (CameraDeviceImpl.this.mRemoteDevice == null) {
                // MONITOREXIT : object
                return;
            }
            StateCallbackKK stateCallbackKK = CameraDeviceImpl.this.mSessionStateCallback;
            // MONITOREXIT : object
            if (stateCallbackKK != null) {
                stateCallbackKK.onOpened(CameraDeviceImpl.this);
            }
            CameraDeviceImpl.this.mDeviceCallback.onOpened(CameraDeviceImpl.this);
        }
    };
    private final Runnable mCallOnUnconfigured = new Runnable(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void run() {
            Object object = CameraDeviceImpl.this.mInterfaceLock;
            // MONITORENTER : object
            if (CameraDeviceImpl.this.mRemoteDevice == null) {
                // MONITOREXIT : object
                return;
            }
            StateCallbackKK stateCallbackKK = CameraDeviceImpl.this.mSessionStateCallback;
            // MONITOREXIT : object
            if (stateCallbackKK == null) return;
            stateCallbackKK.onUnconfigured(CameraDeviceImpl.this);
        }
    };
    private final CameraDeviceCallbacks mCallbacks = new CameraDeviceCallbacks();
    private final String mCameraId;
    private final SparseArray<CaptureCallbackHolder> mCaptureCallbackMap = new SparseArray();
    private final CameraCharacteristics mCharacteristics;
    private final AtomicBoolean mClosing = new AtomicBoolean();
    private AbstractMap.SimpleEntry<Integer, InputConfiguration> mConfiguredInput = new AbstractMap.SimpleEntry<Integer, Object>(-1, null);
    private final SparseArray<OutputConfiguration> mConfiguredOutputs = new SparseArray();
    private CameraCaptureSessionCore mCurrentSession;
    private final CameraDevice.StateCallback mDeviceCallback;
    private final Executor mDeviceExecutor;
    private final FrameNumberTracker mFrameNumberTracker = new FrameNumberTracker();
    private boolean mIdle = true;
    private boolean mInError = false;
    final Object mInterfaceLock = new Object();
    private int mNextSessionId = 0;
    private ICameraDeviceUserWrapper mRemoteDevice;
    private int mRepeatingRequestId = -1;
    private int[] mRepeatingRequestTypes;
    private final List<RequestLastFrameNumbersHolder> mRequestLastFrameNumbersList = new ArrayList<RequestLastFrameNumbersHolder>();
    private volatile StateCallbackKK mSessionStateCallback;
    private final int mTotalPartialCount;

    public CameraDeviceImpl(String object, CameraDevice.StateCallback object2, Executor executor, CameraCharacteristics cameraCharacteristics, int n) {
        this.DEBUG = false;
        if (object != null && object2 != null && executor != null && cameraCharacteristics != null) {
            this.mCameraId = object;
            this.mDeviceCallback = object2;
            this.mDeviceExecutor = executor;
            this.mCharacteristics = cameraCharacteristics;
            this.mAppTargetSdkVersion = n;
            object = object2 = String.format("CameraDevice-JV-%s", this.mCameraId);
            if (((String)object2).length() > 23) {
                object = ((String)object2).substring(0, 23);
            }
            this.TAG = object;
            object = this.mCharacteristics.get(CameraCharacteristics.REQUEST_PARTIAL_RESULT_COUNT);
            this.mTotalPartialCount = object == null ? 1 : (Integer)object;
            return;
        }
        throw new IllegalArgumentException("Null argument given");
    }

    static /* synthetic */ CameraCharacteristics access$1400(CameraDeviceImpl cameraDeviceImpl) {
        return cameraDeviceImpl.getCharacteristics();
    }

    static /* synthetic */ int access$1500(CameraDeviceImpl cameraDeviceImpl) {
        return cameraDeviceImpl.mTotalPartialCount;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void checkAndFireSequenceComplete() {
        Object object;
        long l = this.mFrameNumberTracker.getCompletedFrameNumber();
        long l2 = this.mFrameNumberTracker.getCompletedReprocessFrameNumber();
        long l3 = this.mFrameNumberTracker.getCompletedZslStillFrameNumber();
        Iterator<RequestLastFrameNumbersHolder> iterator = this.mRequestLastFrameNumbersList.iterator();
        while (iterator.hasNext()) {
            boolean bl;
            int n2;
            long l4;
            int n;
            Object object2;
            RequestLastFrameNumbersHolder requestLastFrameNumbersHolder;
            block18 : {
                requestLastFrameNumbersHolder = iterator.next();
                bl = false;
                n2 = requestLastFrameNumbersHolder.getRequestId();
                object2 = this.mInterfaceLock;
                // MONITORENTER : object2
                object = this.mRemoteDevice;
                if (object == null) {
                    Log.w(this.TAG, "Camera closed while checking sequences");
                    // MONITOREXIT : object2
                    return;
                }
                n = this.mCaptureCallbackMap.indexOfKey(n2);
                if (n < 0) break block18;
                try {
                    object = this.mCaptureCallbackMap.valueAt(n);
                }
                catch (Throwable throwable) {
                    throw object;
                }
            }
            object = null;
            if (object != null) {
                l4 = requestLastFrameNumbersHolder.getLastRegularFrameNumber();
                long l5 = requestLastFrameNumbersHolder.getLastReprocessFrameNumber();
                long l6 = requestLastFrameNumbersHolder.getLastZslStillFrameNumber();
                if (l4 <= l && l5 <= l2 && l6 <= l3) {
                    bl = true;
                    this.mCaptureCallbackMap.removeAt(n);
                    // MONITOREXIT : object2
                }
            }
            if (object == null || bl) {
                iterator.remove();
            }
            if (!bl) continue;
            object2 = new Runnable((CaptureCallbackHolder)object, requestLastFrameNumbersHolder){
                final /* synthetic */ CaptureCallbackHolder val$holder;
                final /* synthetic */ RequestLastFrameNumbersHolder val$requestLastFrameNumbers;
                {
                    this.val$holder = captureCallbackHolder;
                    this.val$requestLastFrameNumbers = requestLastFrameNumbersHolder;
                }

                @Override
                public void run() {
                    if (!CameraDeviceImpl.this.isClosed()) {
                        this.val$holder.getCallback().onCaptureSequenceCompleted(CameraDeviceImpl.this, n2, this.val$requestLastFrameNumbers.getLastFrameNumber());
                    }
                }
            };
            l4 = Binder.clearCallingIdentity();
            try {
                ((CaptureCallbackHolder)object).getExecutor().execute((Runnable)object2);
            }
            finally {
                Binder.restoreCallingIdentity(l4);
            }
        }
        return;
        catch (Throwable throwable) {
            throw object;
        }
    }

    public static Executor checkAndWrapHandler(Handler handler) {
        return new CameraHandlerExecutor(CameraDeviceImpl.checkHandler(handler));
    }

    private void checkEarlyTriggerSequenceComplete(final int n, long l, int[] object) {
        if (l == -1L) {
            int n2 = this.mCaptureCallbackMap.indexOfKey(n);
            object = n2 >= 0 ? this.mCaptureCallbackMap.valueAt(n2) : null;
            if (object != null) {
                this.mCaptureCallbackMap.removeAt(n2);
            }
            if (object != null) {
                Runnable runnable = new Runnable((CaptureCallbackHolder)object){
                    final /* synthetic */ CaptureCallbackHolder val$holder;
                    {
                        this.val$holder = captureCallbackHolder;
                    }

                    @Override
                    public void run() {
                        if (!CameraDeviceImpl.this.isClosed()) {
                            this.val$holder.getCallback().onCaptureSequenceAborted(CameraDeviceImpl.this, n);
                        }
                    }
                };
                l = Binder.clearCallingIdentity();
                try {
                    ((CaptureCallbackHolder)object).getExecutor().execute(runnable);
                }
                finally {
                    Binder.restoreCallingIdentity(l);
                }
            } else {
                Log.w(this.TAG, String.format("did not register callback to request %d", n));
            }
        } else {
            this.mRequestLastFrameNumbersList.add(new RequestLastFrameNumbersHolder(n, l, (int[])object));
            this.checkAndFireSequenceComplete();
        }
    }

    static Executor checkExecutor(Executor executor) {
        block0 : {
            if (executor != null) break block0;
            executor = CameraDeviceImpl.checkAndWrapHandler(null);
        }
        return executor;
    }

    public static <T> Executor checkExecutor(Executor executor, T t) {
        block0 : {
            if (t == null) break block0;
            executor = CameraDeviceImpl.checkExecutor(executor);
        }
        return executor;
    }

    static Handler checkHandler(Handler object) {
        Handler handler = object;
        if (object == null) {
            object = Looper.myLooper();
            if (object != null) {
                handler = new Handler((Looper)object);
            } else {
                throw new IllegalArgumentException("No handler given, and current thread has no looper!");
            }
        }
        return handler;
    }

    static <T> Handler checkHandler(Handler handler, T t) {
        if (t != null) {
            return CameraDeviceImpl.checkHandler(handler);
        }
        return handler;
    }

    private void checkIfCameraClosedOrInError() throws CameraAccessException {
        if (this.mRemoteDevice != null) {
            if (!this.mInError) {
                return;
            }
            throw new CameraAccessException(3, "The camera device has encountered a serious error");
        }
        throw new IllegalStateException("CameraDevice was already closed");
    }

    private void checkInputConfiguration(InputConfiguration inputConfiguration) {
        if (inputConfiguration != null) {
            int n;
            Object object = this.mCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            Object object2 = ((StreamConfigurationMap)object).getInputFormats();
            int n2 = ((int[])object2).length;
            int n3 = 0;
            int n4 = 0;
            for (n = 0; n < n2; ++n) {
                if (object2[n] != inputConfiguration.getFormat()) continue;
                n4 = 1;
            }
            if (n4 != 0) {
                n4 = 0;
                object2 = ((StreamConfigurationMap)object).getInputSizes(inputConfiguration.getFormat());
                n2 = ((int[])object2).length;
                for (n = n3; n < n2; ++n) {
                    object = object2[n];
                    n3 = n4;
                    if (inputConfiguration.getWidth() == ((Size)object).getWidth()) {
                        n3 = n4;
                        if (inputConfiguration.getHeight() == ((Size)object).getHeight()) {
                            n3 = 1;
                        }
                    }
                    n4 = n3;
                }
                if (n4 == 0) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("input size ");
                    ((StringBuilder)object2).append(inputConfiguration.getWidth());
                    ((StringBuilder)object2).append("x");
                    ((StringBuilder)object2).append(inputConfiguration.getHeight());
                    ((StringBuilder)object2).append(" is not valid");
                    throw new IllegalArgumentException(((StringBuilder)object2).toString());
                }
            } else {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("input format ");
                ((StringBuilder)object2).append(inputConfiguration.getFormat());
                ((StringBuilder)object2).append(" is not valid");
                throw new IllegalArgumentException(((StringBuilder)object2).toString());
            }
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void createCaptureSessionInternal(InputConfiguration object, List<OutputConfiguration> object2, CameraCaptureSession.StateCallback stateCallback, Executor executor, int n, CaptureRequest object3) throws CameraAccessException {
        Object object4 = this.mInterfaceLock;
        synchronized (object4) {
            int n2;
            this.checkIfCameraClosedOrInError();
            boolean bl = n2 == 1;
            if (bl && object != null) {
                object = new IllegalArgumentException("Constrained high speed session doesn't support input configuration yet.");
                throw object;
            }
            if (this.mCurrentSession != null) {
                this.mCurrentSession.replaceSessionClose();
            }
            ArrayList arrayList = null;
            try {
                boolean bl2;
                Object object5;
                void var3_5;
                void var4_6;
                Object object6;
                try {
                    bl2 = this.configureStreamsChecked((InputConfiguration)object, (List<OutputConfiguration>)object6, n2, (CaptureRequest)object5);
                    object5 = arrayList;
                    if (bl2) {
                        object5 = arrayList;
                        if (object != null) {
                            object5 = this.mRemoteDevice.getInputSurface();
                        }
                    }
                    object = null;
                }
                catch (CameraAccessException cameraAccessException) {
                    bl2 = false;
                    object5 = null;
                }
                if (bl) {
                    object5 = new ArrayList(object6.size());
                    object6 = object6.iterator();
                    while (object6.hasNext()) {
                        ((ArrayList)object5).add(((OutputConfiguration)object6.next()).getSurface());
                    }
                    SurfaceUtils.checkConstrainedHighSpeedSurfaces((Collection<Surface>)object5, null, this.getCharacteristics().get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP));
                    n2 = this.mNextSessionId;
                    this.mNextSessionId = n2 + 1;
                    object6 = new CameraConstrainedHighSpeedCaptureSessionImpl(n2, (CameraCaptureSession.StateCallback)var3_5, (Executor)var4_6, this, this.mDeviceExecutor, bl2, this.mCharacteristics);
                } else {
                    n2 = this.mNextSessionId;
                    this.mNextSessionId = n2 + 1;
                    object6 = new CameraCaptureSessionImpl(n2, (Surface)object5, (CameraCaptureSession.StateCallback)var3_5, (Executor)var4_6, this, this.mDeviceExecutor, bl2);
                }
                this.mCurrentSession = object6;
                if (object == null) {
                    this.mSessionStateCallback = this.mCurrentSession.getDeviceStateCallback();
                    return;
                }
                throw object;
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    private CameraCharacteristics getCharacteristics() {
        return this.mCharacteristics;
    }

    private int[] getRequestTypes(CaptureRequest[] arrcaptureRequest) {
        int[] arrn = new int[arrcaptureRequest.length];
        for (int i = 0; i < arrcaptureRequest.length; ++i) {
            arrn[i] = arrcaptureRequest[i].getRequestType();
        }
        return arrn;
    }

    private boolean isClosed() {
        return this.mClosing.get();
    }

    private void overrideEnableZsl(CameraMetadataNative cameraMetadataNative, boolean bl) {
        if (cameraMetadataNative.get(CaptureRequest.CONTROL_ENABLE_ZSL) == null) {
            return;
        }
        cameraMetadataNative.set(CaptureRequest.CONTROL_ENABLE_ZSL, Boolean.valueOf(bl));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private int submitCaptureRequest(List<CaptureRequest> list, CaptureCallback object, Executor object2, boolean bl) throws CameraAccessException {
        Object object3;
        int n;
        Object object42;
        object2 = CameraDeviceImpl.checkExecutor((Executor)object2, object);
        for (CaptureRequest[] arrcaptureRequest : list) {
            if (arrcaptureRequest.getTargets().isEmpty()) {
                throw new IllegalArgumentException("Each request must have at least one Surface target");
            }
            for (Object object42 : arrcaptureRequest.getTargets()) {
                if (object42 == null) {
                    throw new IllegalArgumentException("Null Surface targets are not allowed");
                }
                for (n = 0; n < this.mConfiguredOutputs.size(); ++n) {
                    object3 = this.mConfiguredOutputs.valueAt(n);
                    if (!((OutputConfiguration)object3).isForPhysicalCamera() || !((OutputConfiguration)object3).getSurfaces().contains(object42) || !arrcaptureRequest.isReprocess()) continue;
                    throw new IllegalArgumentException("Reprocess request on physical stream is not allowed");
                }
            }
        }
        object42 = this.mInterfaceLock;
        synchronized (object42) {
            CaptureRequest[] arrcaptureRequest;
            this.checkIfCameraClosedOrInError();
            if (bl) {
                this.stopRepeating();
            }
            arrcaptureRequest = list.toArray(new CaptureRequest[list.size()]);
            int n2 = arrcaptureRequest.length;
            for (n = 0; n < n2; ++n) {
                arrcaptureRequest[n].convertSurfaceToStreamId(this.mConfiguredOutputs);
            }
            SubmitInfo submitInfo = this.mRemoteDevice.submitRequestList(arrcaptureRequest, bl);
            n2 = arrcaptureRequest.length;
            for (n = 0; n < n2; ++n) {
                arrcaptureRequest[n].recoverStreamIdToSurface();
            }
            if (object != null) {
                object3 = this.mCaptureCallbackMap;
                n = submitInfo.getRequestId();
                Object object5 = new CaptureCallbackHolder((CaptureCallback)object, list, (Executor)object2, bl, this.mNextSessionId - 1);
                ((SparseArray)object3).put(n, object5);
            }
            if (bl) {
                if (this.mRepeatingRequestId != -1) {
                    this.checkEarlyTriggerSequenceComplete(this.mRepeatingRequestId, submitInfo.getLastFrameNumber(), this.mRepeatingRequestTypes);
                }
                this.mRepeatingRequestId = submitInfo.getRequestId();
                this.mRepeatingRequestTypes = this.getRequestTypes(arrcaptureRequest);
            } else {
                object = this.mRequestLastFrameNumbersList;
                object2 = new RequestLastFrameNumbersHolder(list, submitInfo);
                object.add(object2);
            }
            if (this.mIdle) {
                this.mDeviceExecutor.execute(this.mCallOnActive);
            }
            this.mIdle = false;
            return submitInfo.getRequestId();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void waitUntilIdle() throws CameraAccessException {
        Object object = this.mInterfaceLock;
        synchronized (object) {
            this.checkIfCameraClosedOrInError();
            if (this.mRepeatingRequestId == -1) {
                this.mRemoteDevice.waitUntilIdle();
                return;
            }
            IllegalStateException illegalStateException = new IllegalStateException("Active repeating request ongoing");
            throw illegalStateException;
        }
    }

    @Override
    public void binderDied() {
        String string2 = this.TAG;
        Object object = new StringBuilder();
        ((StringBuilder)object).append("CameraDevice ");
        ((StringBuilder)object).append(this.mCameraId);
        ((StringBuilder)object).append(" died unexpectedly");
        Log.w(string2, ((StringBuilder)object).toString());
        if (this.mRemoteDevice == null) {
            return;
        }
        this.mInError = true;
        object = new Runnable(){

            @Override
            public void run() {
                if (!CameraDeviceImpl.this.isClosed()) {
                    CameraDeviceImpl.this.mDeviceCallback.onError(CameraDeviceImpl.this, 5);
                }
            }
        };
        long l = Binder.clearCallingIdentity();
        try {
            this.mDeviceExecutor.execute((Runnable)object);
            return;
        }
        finally {
            Binder.restoreCallingIdentity(l);
        }
    }

    public int capture(CaptureRequest captureRequest, CaptureCallback captureCallback, Executor executor) throws CameraAccessException {
        ArrayList<CaptureRequest> arrayList = new ArrayList<CaptureRequest>();
        arrayList.add(captureRequest);
        return this.submitCaptureRequest(arrayList, captureCallback, executor, false);
    }

    public int captureBurst(List<CaptureRequest> list, CaptureCallback captureCallback, Executor executor) throws CameraAccessException {
        if (list != null && !list.isEmpty()) {
            return this.submitCaptureRequest(list, captureCallback, executor, false);
        }
        throw new IllegalArgumentException("At least one request must be given");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() {
        Object object = this.mInterfaceLock;
        synchronized (object) {
            if (this.mClosing.getAndSet(true)) {
                return;
            }
            if (this.mRemoteDevice != null) {
                this.mRemoteDevice.disconnect();
                this.mRemoteDevice.unlinkToDeath(this, 0);
            }
            if (this.mRemoteDevice != null || this.mInError) {
                this.mDeviceExecutor.execute(this.mCallOnClosed);
            }
            this.mRemoteDevice = null;
            return;
        }
    }

    public void configureOutputs(List<Surface> object) throws CameraAccessException {
        ArrayList<OutputConfiguration> arrayList = new ArrayList<OutputConfiguration>(object.size());
        object = object.iterator();
        while (object.hasNext()) {
            arrayList.add(new OutputConfiguration((Surface)object.next()));
        }
        this.configureStreamsChecked(null, arrayList, 0, null);
    }

    /*
     * Exception decompiling
     */
    public boolean configureStreamsChecked(InputConfiguration var1_1, List<OutputConfiguration> var2_3, int var3_4, CaptureRequest var4_5) throws CameraAccessException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [9[CATCHBLOCK]], but top level block is 5[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public CaptureRequest.Builder createCaptureRequest(int n) throws CameraAccessException {
        Object object = this.mInterfaceLock;
        synchronized (object) {
            this.checkIfCameraClosedOrInError();
            CameraMetadataNative cameraMetadataNative = this.mRemoteDevice.createDefaultRequest(n);
            if (this.mAppTargetSdkVersion >= 26) {
                if (n == 2) return new CaptureRequest.Builder(cameraMetadataNative, false, -1, this.getId(), null);
            }
            this.overrideEnableZsl(cameraMetadataNative, false);
            return new CaptureRequest.Builder(cameraMetadataNative, false, -1, this.getId(), null);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public CaptureRequest.Builder createCaptureRequest(int n, Set<String> object) throws CameraAccessException {
        Object object2 = this.mInterfaceLock;
        synchronized (object2) {
            this.checkIfCameraClosedOrInError();
            Iterator<String> iterator = object.iterator();
            while (iterator.hasNext()) {
                if (iterator.next() != this.getId()) continue;
                object = new IllegalStateException("Physical id matches the logical id!");
                throw object;
            }
            CameraMetadataNative cameraMetadataNative = this.mRemoteDevice.createDefaultRequest(n);
            if (this.mAppTargetSdkVersion >= 26) {
                if (n == 2) return new CaptureRequest.Builder(cameraMetadataNative, false, -1, this.getId(), (Set<String>)object);
            }
            this.overrideEnableZsl(cameraMetadataNative, false);
            return new CaptureRequest.Builder(cameraMetadataNative, false, -1, this.getId(), (Set<String>)object);
        }
    }

    @Override
    public void createCaptureSession(SessionConfiguration sessionConfiguration) throws CameraAccessException {
        if (sessionConfiguration != null) {
            List<OutputConfiguration> list = sessionConfiguration.getOutputConfigurations();
            if (list != null) {
                if (sessionConfiguration.getExecutor() != null) {
                    this.createCaptureSessionInternal(sessionConfiguration.getInputConfiguration(), list, sessionConfiguration.getStateCallback(), sessionConfiguration.getExecutor(), sessionConfiguration.getSessionType(), sessionConfiguration.getSessionParameters());
                    return;
                }
                throw new IllegalArgumentException("Invalid executor");
            }
            throw new IllegalArgumentException("Invalid output configurations");
        }
        throw new IllegalArgumentException("Invalid session configuration");
    }

    @Override
    public void createCaptureSession(List<Surface> object, CameraCaptureSession.StateCallback stateCallback, Handler handler) throws CameraAccessException {
        ArrayList<OutputConfiguration> arrayList = new ArrayList<OutputConfiguration>(object.size());
        object = object.iterator();
        while (object.hasNext()) {
            arrayList.add(new OutputConfiguration((Surface)object.next()));
        }
        this.createCaptureSessionInternal(null, arrayList, stateCallback, CameraDeviceImpl.checkAndWrapHandler(handler), 0, null);
    }

    @Override
    public void createCaptureSessionByOutputConfigurations(List<OutputConfiguration> list, CameraCaptureSession.StateCallback stateCallback, Handler handler) throws CameraAccessException {
        this.createCaptureSessionInternal(null, new ArrayList<OutputConfiguration>(list), stateCallback, CameraDeviceImpl.checkAndWrapHandler(handler), 0, null);
    }

    @Override
    public void createConstrainedHighSpeedCaptureSession(List<Surface> object, CameraCaptureSession.StateCallback stateCallback, Handler handler) throws CameraAccessException {
        if (object != null && object.size() != 0 && object.size() <= 2) {
            ArrayList<OutputConfiguration> arrayList = new ArrayList<OutputConfiguration>(object.size());
            object = object.iterator();
            while (object.hasNext()) {
                arrayList.add(new OutputConfiguration((Surface)object.next()));
            }
            this.createCaptureSessionInternal(null, arrayList, stateCallback, CameraDeviceImpl.checkAndWrapHandler(handler), 1, null);
            return;
        }
        throw new IllegalArgumentException("Output surface list must not be null and the size must be no more than 2");
    }

    @Override
    public void createCustomCaptureSession(InputConfiguration inputConfiguration, List<OutputConfiguration> object, int n, CameraCaptureSession.StateCallback stateCallback, Handler handler) throws CameraAccessException {
        ArrayList<OutputConfiguration> arrayList = new ArrayList<OutputConfiguration>();
        object = object.iterator();
        while (object.hasNext()) {
            arrayList.add(new OutputConfiguration((OutputConfiguration)object.next()));
        }
        this.createCaptureSessionInternal(inputConfiguration, arrayList, stateCallback, CameraDeviceImpl.checkAndWrapHandler(handler), n, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public CaptureRequest.Builder createReprocessCaptureRequest(TotalCaptureResult totalCaptureResult) throws CameraAccessException {
        Object object = this.mInterfaceLock;
        synchronized (object) {
            this.checkIfCameraClosedOrInError();
            CameraMetadataNative cameraMetadataNative = new CameraMetadataNative(totalCaptureResult.getNativeCopy());
            return new CaptureRequest.Builder(cameraMetadataNative, true, totalCaptureResult.getSessionId(), this.getId(), null);
        }
    }

    @Override
    public void createReprocessableCaptureSession(InputConfiguration inputConfiguration, List<Surface> object, CameraCaptureSession.StateCallback stateCallback, Handler handler) throws CameraAccessException {
        if (inputConfiguration != null) {
            ArrayList<OutputConfiguration> arrayList = new ArrayList<OutputConfiguration>(object.size());
            object = object.iterator();
            while (object.hasNext()) {
                arrayList.add(new OutputConfiguration((Surface)object.next()));
            }
            this.createCaptureSessionInternal(inputConfiguration, arrayList, stateCallback, CameraDeviceImpl.checkAndWrapHandler(handler), 0, null);
            return;
        }
        throw new IllegalArgumentException("inputConfig cannot be null when creating a reprocessable capture session");
    }

    @Override
    public void createReprocessableCaptureSessionByConfigurations(InputConfiguration inputConfiguration, List<OutputConfiguration> object, CameraCaptureSession.StateCallback stateCallback, Handler handler) throws CameraAccessException {
        if (inputConfiguration != null) {
            if (object != null) {
                ArrayList<OutputConfiguration> arrayList = new ArrayList<OutputConfiguration>();
                object = object.iterator();
                while (object.hasNext()) {
                    arrayList.add(new OutputConfiguration((OutputConfiguration)object.next()));
                }
                this.createCaptureSessionInternal(inputConfiguration, arrayList, stateCallback, CameraDeviceImpl.checkAndWrapHandler(handler), 0, null);
                return;
            }
            throw new IllegalArgumentException("Output configurations cannot be null when creating a reprocessable capture session");
        }
        throw new IllegalArgumentException("inputConfig cannot be null when creating a reprocessable capture session");
    }

    protected void finalize() throws Throwable {
        try {
            this.close();
            return;
        }
        finally {
            Object.super.finalize();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void finalizeOutputConfigs(List<OutputConfiguration> object) throws CameraAccessException {
        if (object != null && object.size() != 0) {
            Object object2 = this.mInterfaceLock;
            synchronized (object2) {
            }
        } else {
            throw new IllegalArgumentException("deferred config is null or empty");
        }
        {
            object = object.iterator();
            while (object.hasNext()) {
                int n;
                Object object3 = (OutputConfiguration)object.next();
                int n2 = -1;
                int n3 = 0;
                do {
                    n = n2;
                    if (n3 >= this.mConfiguredOutputs.size()) break;
                    if (((OutputConfiguration)object3).equals(this.mConfiguredOutputs.valueAt(n3))) {
                        n = this.mConfiguredOutputs.keyAt(n3);
                        break;
                    }
                    ++n3;
                } while (true);
                if (n == -1) {
                    object = new IllegalArgumentException("Deferred config is not part of this session");
                    throw object;
                }
                if (((OutputConfiguration)object3).getSurfaces().size() == 0) {
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("The final config for stream ");
                    ((StringBuilder)object3).append(n);
                    ((StringBuilder)object3).append(" must have at least 1 surface");
                    object = new IllegalArgumentException(((StringBuilder)object3).toString());
                    throw object;
                }
                this.mRemoteDevice.finalizeOutputConfigurations(n, (OutputConfiguration)object3);
                this.mConfiguredOutputs.put(n, (OutputConfiguration)object3);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void flush() throws CameraAccessException {
        Object object = this.mInterfaceLock;
        synchronized (object) {
            this.checkIfCameraClosedOrInError();
            this.mDeviceExecutor.execute(this.mCallOnBusy);
            if (this.mIdle) {
                this.mDeviceExecutor.execute(this.mCallOnIdle);
                return;
            }
            long l = this.mRemoteDevice.flush();
            if (this.mRepeatingRequestId != -1) {
                this.checkEarlyTriggerSequenceComplete(this.mRepeatingRequestId, l, this.mRepeatingRequestTypes);
                this.mRepeatingRequestId = -1;
                this.mRepeatingRequestTypes = null;
            }
            return;
        }
    }

    public CameraDeviceCallbacks getCallbacks() {
        return this.mCallbacks;
    }

    @Override
    public String getId() {
        return this.mCameraId;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean isSessionConfigurationSupported(SessionConfiguration sessionConfiguration) throws CameraAccessException, UnsupportedOperationException, IllegalArgumentException {
        Object object = this.mInterfaceLock;
        synchronized (object) {
            this.checkIfCameraClosedOrInError();
            return this.mRemoteDevice.isSessionConfigurationSupported(sessionConfiguration);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void prepare(int n, Surface object) throws CameraAccessException {
        if (object == null) {
            throw new IllegalArgumentException("Surface is null");
        }
        if (n <= 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid maxCount given: ");
            ((StringBuilder)object).append(n);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        Object object2 = this.mInterfaceLock;
        synchronized (object2) {
            int n2;
            int n3 = -1;
            int n4 = 0;
            do {
                n2 = n3;
                if (n4 >= this.mConfiguredOutputs.size()) break;
                if (object == this.mConfiguredOutputs.valueAt(n4).getSurface()) {
                    n2 = this.mConfiguredOutputs.keyAt(n4);
                    break;
                }
                ++n4;
            } while (true);
            if (n2 != -1) {
                this.mRemoteDevice.prepare2(n, n2);
                return;
            }
            object = new IllegalArgumentException("Surface is not part of this session");
            throw object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void prepare(Surface object) throws CameraAccessException {
        if (object == null) {
            throw new IllegalArgumentException("Surface is null");
        }
        Object object2 = this.mInterfaceLock;
        synchronized (object2) {
            int n;
            int n2 = -1;
            int n3 = 0;
            do {
                n = n2;
                if (n3 >= this.mConfiguredOutputs.size()) break;
                if (this.mConfiguredOutputs.valueAt(n3).getSurfaces().contains(object)) {
                    n = this.mConfiguredOutputs.keyAt(n3);
                    break;
                }
                ++n3;
            } while (true);
            if (n != -1) {
                this.mRemoteDevice.prepare(n);
                return;
            }
            object = new IllegalArgumentException("Surface is not part of this session");
            throw object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setRemoteDevice(ICameraDeviceUser object) throws CameraAccessException {
        Object object2 = this.mInterfaceLock;
        synchronized (object2) {
            ICameraDeviceUserWrapper iCameraDeviceUserWrapper;
            if (this.mInError) {
                return;
            }
            this.mRemoteDevice = iCameraDeviceUserWrapper = new ICameraDeviceUserWrapper((ICameraDeviceUser)object);
            if ((object = object.asBinder()) != null) {
                try {
                    object.linkToDeath(this, 0);
                }
                catch (RemoteException remoteException) {
                    this.mDeviceExecutor.execute(this.mCallOnDisconnected);
                    CameraAccessException cameraAccessException = new CameraAccessException(2, "The camera device has encountered a serious error");
                    throw cameraAccessException;
                }
            }
            this.mDeviceExecutor.execute(this.mCallOnOpened);
            this.mDeviceExecutor.execute(this.mCallOnUnconfigured);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setRemoteFailure(ServiceSpecificException object) {
        Object object2;
        Runnable runnable;
        final int n = 4;
        final boolean bl = true;
        int n2 = ((ServiceSpecificException)object).errorCode;
        if (n2 != 4) {
            if (n2 != 10) {
                if (n2 != 6) {
                    if (n2 != 7) {
                        if (n2 != 8) {
                            runnable = this.TAG;
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("Unexpected failure in opening camera device: ");
                            ((StringBuilder)object2).append(((ServiceSpecificException)object).errorCode);
                            ((StringBuilder)object2).append(((Throwable)object).getMessage());
                            Log.e((String)((Object)runnable), ((StringBuilder)object2).toString());
                        } else {
                            n = 2;
                        }
                    } else {
                        n = 1;
                    }
                } else {
                    n = 3;
                }
            } else {
                n = 4;
            }
        } else {
            bl = false;
        }
        object = this.mInterfaceLock;
        synchronized (object) {
            this.mInError = true;
            object2 = this.mDeviceExecutor;
            runnable = new Runnable(){

                @Override
                public void run() {
                    if (bl) {
                        CameraDeviceImpl.this.mDeviceCallback.onError(CameraDeviceImpl.this, n);
                    } else {
                        CameraDeviceImpl.this.mDeviceCallback.onDisconnected(CameraDeviceImpl.this);
                    }
                }
            };
            object2.execute(runnable);
            return;
        }
    }

    public int setRepeatingBurst(List<CaptureRequest> list, CaptureCallback captureCallback, Executor executor) throws CameraAccessException {
        if (list != null && !list.isEmpty()) {
            return this.submitCaptureRequest(list, captureCallback, executor, true);
        }
        throw new IllegalArgumentException("At least one request must be given");
    }

    public int setRepeatingRequest(CaptureRequest captureRequest, CaptureCallback captureCallback, Executor executor) throws CameraAccessException {
        ArrayList<CaptureRequest> arrayList = new ArrayList<CaptureRequest>();
        arrayList.add(captureRequest);
        return this.submitCaptureRequest(arrayList, captureCallback, executor, true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setSessionListener(StateCallbackKK stateCallbackKK) {
        Object object = this.mInterfaceLock;
        synchronized (object) {
            this.mSessionStateCallback = stateCallbackKK;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void stopRepeating() throws CameraAccessException {
        Object object = this.mInterfaceLock;
        synchronized (object) {
            this.checkIfCameraClosedOrInError();
            if (this.mRepeatingRequestId != -1) {
                long l;
                int n = this.mRepeatingRequestId;
                this.mRepeatingRequestId = -1;
                int[] arrn = this.mRepeatingRequestTypes;
                this.mRepeatingRequestTypes = null;
                try {
                    l = this.mRemoteDevice.cancelRequest(n);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    return;
                }
                this.checkEarlyTriggerSequenceComplete(n, l, arrn);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void tearDown(Surface object) throws CameraAccessException {
        if (object == null) {
            throw new IllegalArgumentException("Surface is null");
        }
        Object object2 = this.mInterfaceLock;
        synchronized (object2) {
            int n;
            int n2 = -1;
            int n3 = 0;
            do {
                n = n2;
                if (n3 >= this.mConfiguredOutputs.size()) break;
                if (object == this.mConfiguredOutputs.valueAt(n3).getSurface()) {
                    n = this.mConfiguredOutputs.keyAt(n3);
                    break;
                }
                ++n3;
            } while (true);
            if (n != -1) {
                this.mRemoteDevice.tearDown(n);
                return;
            }
            object = new IllegalArgumentException("Surface is not part of this session");
            throw object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void updateOutputConfiguration(OutputConfiguration object) throws CameraAccessException {
        Object object2 = this.mInterfaceLock;
        synchronized (object2) {
            int n;
            int n2 = -1;
            int n3 = 0;
            do {
                n = n2;
                if (n3 >= this.mConfiguredOutputs.size()) break;
                if (((OutputConfiguration)object).getSurface() == this.mConfiguredOutputs.valueAt(n3).getSurface()) {
                    n = this.mConfiguredOutputs.keyAt(n3);
                    break;
                }
                ++n3;
            } while (true);
            if (n != -1) {
                this.mRemoteDevice.updateOutputConfiguration(n, (OutputConfiguration)object);
                this.mConfiguredOutputs.put(n, (OutputConfiguration)object);
                return;
            }
            object = new IllegalArgumentException("Invalid output configuration");
            throw object;
        }
    }

    /*
     * Illegal identifiers - consider using --renameillegalidents true
     */
    public class CameraDeviceCallbacks
    extends ICameraDeviceCallbacks.Stub {
        public static /* synthetic */ void lambda$Sm85frAzwGZVMAK-NE_gwckYXVQ(CameraDeviceCallbacks cameraDeviceCallbacks, int n) {
            cameraDeviceCallbacks.notifyError(n);
        }

        private void notifyError(int n) {
            if (!CameraDeviceImpl.this.isClosed()) {
                CameraDeviceImpl.this.mDeviceCallback.onError(CameraDeviceImpl.this, n);
            }
        }

        private void onCaptureErrorLocked(int n, CaptureResultExtras object) {
            long l;
            int n2 = ((CaptureResultExtras)object).getRequestId();
            int n3 = ((CaptureResultExtras)object).getSubsequenceId();
            final long l2 = ((CaptureResultExtras)object).getFrameNumber();
            String object22 = ((CaptureResultExtras)object).getErrorPhysicalCameraId();
            final CaptureCallbackHolder captureCallbackHolder = (CaptureCallbackHolder)CameraDeviceImpl.this.mCaptureCallbackMap.get(n2);
            final CaptureRequest captureRequest = captureCallbackHolder.getRequest(n3);
            if (n == 5) {
                for (final Surface surface : ((OutputConfiguration)CameraDeviceImpl.this.mConfiguredOutputs.get(((CaptureResultExtras)object).getErrorStreamId())).getSurfaces()) {
                    if (!captureRequest.containsTarget(surface)) continue;
                    Runnable runnable = new Runnable(){

                        @Override
                        public void run() {
                            if (!CameraDeviceImpl.this.isClosed()) {
                                captureCallbackHolder.getCallback().onCaptureBufferLost(CameraDeviceImpl.this, captureRequest, surface, l2);
                            }
                        }
                    };
                    long l3 = Binder.clearCallingIdentity();
                    try {
                        captureCallbackHolder.getExecutor().execute(runnable);
                    }
                    finally {
                        Binder.restoreCallingIdentity(l3);
                    }
                }
            } else {
                n3 = 0;
                boolean bl = n == 4;
                n = CameraDeviceImpl.this.mCurrentSession != null && CameraDeviceImpl.this.mCurrentSession.isAborting() ? 1 : n3;
                object = new Runnable(new CaptureFailure(captureRequest, n, bl, n2, l2, object22)){
                    final /* synthetic */ CaptureFailure val$failure;
                    {
                        this.val$failure = captureFailure;
                    }

                    @Override
                    public void run() {
                        if (!CameraDeviceImpl.this.isClosed()) {
                            captureCallbackHolder.getCallback().onCaptureFailed(CameraDeviceImpl.this, captureRequest, this.val$failure);
                        }
                    }
                };
                CameraDeviceImpl.this.mFrameNumberTracker.updateTracker(l2, true, captureRequest.getRequestType());
                CameraDeviceImpl.this.checkAndFireSequenceComplete();
                l = Binder.clearCallingIdentity();
                captureCallbackHolder.getExecutor().execute((Runnable)object);
            }
            return;
            finally {
                Binder.restoreCallingIdentity(l);
            }
        }

        private void scheduleNotifyError(int n) {
            CameraDeviceImpl.this.mInError = true;
            long l = Binder.clearCallingIdentity();
            try {
                CameraDeviceImpl.this.mDeviceExecutor.execute((Runnable)((Object)PooledLambda.obtainRunnable(_$$Lambda$CameraDeviceImpl$CameraDeviceCallbacks$Sm85frAzwGZVMAK_NE_gwckYXVQ.INSTANCE, this, n).recycleOnUse()));
                return;
            }
            finally {
                Binder.restoreCallingIdentity(l);
            }
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onCaptureStarted(final CaptureResultExtras captureResultExtras, final long l) {
            int n = captureResultExtras.getRequestId();
            final long l2 = captureResultExtras.getFrameNumber();
            Object object = CameraDeviceImpl.this.mInterfaceLock;
            synchronized (object) {
                if (CameraDeviceImpl.this.mRemoteDevice == null) {
                    return;
                }
                final CaptureCallbackHolder captureCallbackHolder = (CaptureCallbackHolder)CameraDeviceImpl.this.mCaptureCallbackMap.get(n);
                if (captureCallbackHolder == null) {
                    return;
                }
                if (CameraDeviceImpl.this.isClosed()) {
                    return;
                }
                long l3 = Binder.clearCallingIdentity();
                try {
                    Executor executor = captureCallbackHolder.getExecutor();
                    Runnable runnable = new Runnable(){

                        @Override
                        public void run() {
                            if (!CameraDeviceImpl.this.isClosed()) {
                                int n = captureResultExtras.getSubsequenceId();
                                Object object = captureCallbackHolder.getRequest(n);
                                if (captureCallbackHolder.hasBatchedOutputs()) {
                                    object = ((CaptureRequest)object).get(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE);
                                    for (int i = 0; i < captureCallbackHolder.getRequestCount(); ++i) {
                                        captureCallbackHolder.getCallback().onCaptureStarted(CameraDeviceImpl.this, captureCallbackHolder.getRequest(i), l - (long)(n - i) * 1000000000L / (long)((Integer)((Range)object).getUpper()).intValue(), l2 - (long)(n - i));
                                    }
                                } else {
                                    captureCallbackHolder.getCallback().onCaptureStarted(CameraDeviceImpl.this, captureCallbackHolder.getRequest(captureResultExtras.getSubsequenceId()), l, l2);
                                }
                            }
                        }
                    };
                    executor.execute(runnable);
                    return;
                }
                finally {
                    Binder.restoreCallingIdentity(l3);
                }
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onDeviceError(int n, CaptureResultExtras object) {
            Object object2 = CameraDeviceImpl.this.mInterfaceLock;
            synchronized (object2) {
                long l;
                if (CameraDeviceImpl.this.mRemoteDevice == null) {
                    return;
                }
                if (n != 0) {
                    if (n != 1) {
                        if (n != 3 && n != 4 && n != 5) {
                            if (n != 6) {
                                object = CameraDeviceImpl.this.TAG;
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("Unknown error from camera device: ");
                                stringBuilder.append(n);
                                Log.e((String)object, stringBuilder.toString());
                                this.scheduleNotifyError(5);
                            } else {
                                this.scheduleNotifyError(3);
                            }
                        } else {
                            this.onCaptureErrorLocked(n, (CaptureResultExtras)object);
                        }
                    } else {
                        this.scheduleNotifyError(4);
                    }
                } else {
                    l = Binder.clearCallingIdentity();
                    CameraDeviceImpl.this.mDeviceExecutor.execute(CameraDeviceImpl.this.mCallOnDisconnected);
                }
                return;
                finally {
                    Binder.restoreCallingIdentity(l);
                }
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onDeviceIdle() {
            Object object = CameraDeviceImpl.this.mInterfaceLock;
            synchronized (object) {
                if (CameraDeviceImpl.this.mRemoteDevice == null) {
                    return;
                }
                if (!CameraDeviceImpl.this.mIdle) {
                    long l = Binder.clearCallingIdentity();
                    try {
                        CameraDeviceImpl.this.mDeviceExecutor.execute(CameraDeviceImpl.this.mCallOnIdle);
                    }
                    finally {
                        Binder.restoreCallingIdentity(l);
                    }
                }
                CameraDeviceImpl.this.mIdle = true;
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
        public void onPrepared(int n) {
            Iterator<Surface> iterator = CameraDeviceImpl.this.mInterfaceLock;
            // MONITORENTER : iterator
            OutputConfiguration outputConfiguration = (OutputConfiguration)CameraDeviceImpl.this.mConfiguredOutputs.get(n);
            StateCallbackKK stateCallbackKK = CameraDeviceImpl.this.mSessionStateCallback;
            // MONITOREXIT : iterator
            if (stateCallbackKK == null) {
                return;
            }
            if (outputConfiguration == null) {
                Log.w(CameraDeviceImpl.this.TAG, "onPrepared invoked for unknown output Surface");
                return;
            }
            iterator = outputConfiguration.getSurfaces().iterator();
            while (iterator.hasNext()) {
                stateCallbackKK.onSurfacePrepared(iterator.next());
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onRepeatingRequestError(long l, int n) {
            Object object = CameraDeviceImpl.this.mInterfaceLock;
            synchronized (object) {
                if (CameraDeviceImpl.this.mRemoteDevice != null && CameraDeviceImpl.this.mRepeatingRequestId != -1) {
                    CameraDeviceImpl.this.checkEarlyTriggerSequenceComplete(CameraDeviceImpl.this.mRepeatingRequestId, l, CameraDeviceImpl.this.mRepeatingRequestTypes);
                    if (CameraDeviceImpl.this.mRepeatingRequestId == n) {
                        CameraDeviceImpl.this.mRepeatingRequestId = -1;
                        CameraDeviceImpl.this.mRepeatingRequestTypes = null;
                    }
                    return;
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
        public void onRequestQueueEmpty() {
            Object object = CameraDeviceImpl.this.mInterfaceLock;
            // MONITORENTER : object
            StateCallbackKK stateCallbackKK = CameraDeviceImpl.this.mSessionStateCallback;
            // MONITOREXIT : object
            if (stateCallbackKK == null) {
                return;
            }
            stateCallbackKK.onRequestQueueEmpty();
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void onResultReceived(CameraMetadataNative var1_1, CaptureResultExtras var2_8, PhysicalCaptureResultInfo[] var3_9) throws RemoteException {
            block22 : {
                block23 : {
                    block21 : {
                        block20 : {
                            block19 : {
                                block18 : {
                                    var4_10 = var2_8.getRequestId();
                                    var5_11 = var2_8.getFrameNumber();
                                    var7_12 = CameraDeviceImpl.this.mInterfaceLock;
                                    // MONITORENTER : var7_12
                                    var8_13 = CameraDeviceImpl.access$000(CameraDeviceImpl.this);
                                    if (var8_13 != null) break block18;
                                    // MONITOREXIT : var7_12
                                    return;
                                }
                                var1_1.set(CameraCharacteristics.LENS_INFO_SHADING_MAP_SIZE, CameraDeviceImpl.access$1400(CameraDeviceImpl.this).get(CameraCharacteristics.LENS_INFO_SHADING_MAP_SIZE));
                                var9_14 = (CaptureCallbackHolder)CameraDeviceImpl.access$1300(CameraDeviceImpl.this).get(var4_10);
                                var10_15 = var9_14.getRequest(var2_8.getSubsequenceId());
                                var11_16 = var2_8.getPartialResultCount() < CameraDeviceImpl.access$1500(CameraDeviceImpl.this);
                                var12_17 = var10_15.getRequestType();
                                var13_18 = CameraDeviceImpl.access$300(CameraDeviceImpl.this);
                                if (!var13_18) break block19;
                                CameraDeviceImpl.access$1600(CameraDeviceImpl.this).updateTracker(var5_11, null, var11_16, var12_17);
                                // MONITOREXIT : var7_12
                                return;
                            }
                            var13_18 = var9_14.hasBatchedOutputs();
                            if (!var13_18) break block20;
                            try {
                                var8_13 = new CameraMetadataNative((CameraMetadataNative)var1_1);
                                break block21;
                            }
                            catch (Throwable var1_2) {
                                var2_8 = var7_12;
                            }
                            break block22;
                        }
                        var8_13 = null;
                    }
                    if (!var11_16) break block23;
                    var3_9 = new CaptureResult((CameraMetadataNative)var1_1, var10_15, (CaptureResultExtras)var2_8);
                    var1_1 = new Runnable((CameraMetadataNative)var8_13, (CaptureResultExtras)var2_8, var10_15, (CaptureResult)var3_9){
                        final /* synthetic */ CaptureRequest val$request;
                        final /* synthetic */ CaptureResult val$resultAsCapture;
                        final /* synthetic */ CameraMetadataNative val$resultCopy;
                        final /* synthetic */ CaptureResultExtras val$resultExtras;
                        {
                            this.val$resultCopy = cameraMetadataNative;
                            this.val$resultExtras = captureResultExtras;
                            this.val$request = captureRequest;
                            this.val$resultAsCapture = captureResult;
                        }

                        @Override
                        public void run() {
                            if (!CameraDeviceImpl.this.isClosed()) {
                                if (var9_14.hasBatchedOutputs()) {
                                    for (int i = 0; i < var9_14.getRequestCount(); ++i) {
                                        CaptureResult captureResult = new CaptureResult(new CameraMetadataNative(this.val$resultCopy), var9_14.getRequest(i), this.val$resultExtras);
                                        var9_14.getCallback().onCaptureProgressed(CameraDeviceImpl.this, var9_14.getRequest(i), captureResult);
                                    }
                                } else {
                                    var9_14.getCallback().onCaptureProgressed(CameraDeviceImpl.this, this.val$request, this.val$resultAsCapture);
                                }
                            }
                        }
                    };
                    var2_8 = var3_9;
                    ** GOTO lbl55
                }
                var14_19 = CameraDeviceImpl.access$1600(CameraDeviceImpl.this).popPartialResults(var5_11);
                var15_20 = var1_1.get(CaptureResult.SENSOR_TIMESTAMP);
                var17_21 = var10_15.get(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE);
                var4_10 = var2_8.getSubsequenceId();
                var19_23 = var9_14.getSessionId();
                var18_22 = new TotalCaptureResult((CameraMetadataNative)var1_1, var10_15, (CaptureResultExtras)var2_8, var14_19, var19_23, (PhysicalCaptureResultInfo[])var3_9);
                var3_9 = var7_12;
                var1_1 = new Runnable((CameraMetadataNative)var8_13, var15_20, var4_10, var17_21, (CaptureResultExtras)var2_8, var14_19, var10_15, var18_22){
                    final /* synthetic */ Range val$fpsRange;
                    final /* synthetic */ List val$partialResults;
                    final /* synthetic */ CaptureRequest val$request;
                    final /* synthetic */ TotalCaptureResult val$resultAsCapture;
                    final /* synthetic */ CameraMetadataNative val$resultCopy;
                    final /* synthetic */ CaptureResultExtras val$resultExtras;
                    final /* synthetic */ long val$sensorTimestamp;
                    final /* synthetic */ int val$subsequenceId;
                    {
                        this.val$resultCopy = cameraMetadataNative;
                        this.val$sensorTimestamp = l;
                        this.val$subsequenceId = n;
                        this.val$fpsRange = range;
                        this.val$resultExtras = captureResultExtras;
                        this.val$partialResults = list;
                        this.val$request = captureRequest;
                        this.val$resultAsCapture = totalCaptureResult;
                    }

                    @Override
                    public void run() {
                        if (!CameraDeviceImpl.this.isClosed()) {
                            if (var9_14.hasBatchedOutputs()) {
                                for (int i = 0; i < var9_14.getRequestCount(); ++i) {
                                    this.val$resultCopy.set(CaptureResult.SENSOR_TIMESTAMP, Long.valueOf(this.val$sensorTimestamp - (long)(this.val$subsequenceId - i) * 1000000000L / (long)((Integer)this.val$fpsRange.getUpper()).intValue()));
                                    TotalCaptureResult totalCaptureResult = new TotalCaptureResult(new CameraMetadataNative(this.val$resultCopy), var9_14.getRequest(i), this.val$resultExtras, this.val$partialResults, var9_14.getSessionId(), new PhysicalCaptureResultInfo[0]);
                                    var9_14.getCallback().onCaptureCompleted(CameraDeviceImpl.this, var9_14.getRequest(i), totalCaptureResult);
                                }
                            } else {
                                var9_14.getCallback().onCaptureCompleted(CameraDeviceImpl.this, this.val$request, this.val$resultAsCapture);
                            }
                        }
                    }
                };
                var2_8 = var18_22;
lbl55: // 2 sources:
                var3_9 = var7_12;
                var15_20 = Binder.clearCallingIdentity();
                var9_14.getExecutor().execute((Runnable)var1_1);
                var3_9 = var7_12;
                {
                    catch (Throwable var1_3) {
                        var3_9 = var7_12;
                        Binder.restoreCallingIdentity(var15_20);
                        var3_9 = var7_12;
                        throw var1_3;
                    }
                }
                try {
                    Binder.restoreCallingIdentity(var15_20);
                    var3_9 = var7_12;
                    CameraDeviceImpl.access$1600(CameraDeviceImpl.this).updateTracker(var5_11, (CaptureResult)var2_8, var11_16, var12_17);
                    if (!var11_16) {
                        var3_9 = var7_12;
                        CameraDeviceImpl.access$1700(CameraDeviceImpl.this);
                    }
                    var3_9 = var7_12;
                    // MONITOREXIT : var7_12
                    return;
                    catch (Throwable var1_4) {
                        var2_8 = var7_12;
                    }
                    break block22;
                    catch (Throwable var1_5) {
                        var2_8 = var7_12;
                    }
                }
                catch (Throwable var1_7) {
                    var2_8 = var3_9;
                }
            }
            var3_9 = var2_8;
            // MONITOREXIT : var2_8
            throw var1_6;
        }

    }

    private static class CameraHandlerExecutor
    implements Executor {
        private final Handler mHandler;

        public CameraHandlerExecutor(Handler handler) {
            this.mHandler = Preconditions.checkNotNull(handler);
        }

        @Override
        public void execute(Runnable runnable) {
            this.mHandler.post(runnable);
        }
    }

    public static interface CaptureCallback {
        public static final int NO_FRAMES_CAPTURED = -1;

        public void onCaptureBufferLost(CameraDevice var1, CaptureRequest var2, Surface var3, long var4);

        public void onCaptureCompleted(CameraDevice var1, CaptureRequest var2, TotalCaptureResult var3);

        public void onCaptureFailed(CameraDevice var1, CaptureRequest var2, CaptureFailure var3);

        public void onCapturePartial(CameraDevice var1, CaptureRequest var2, CaptureResult var3);

        public void onCaptureProgressed(CameraDevice var1, CaptureRequest var2, CaptureResult var3);

        public void onCaptureSequenceAborted(CameraDevice var1, int var2);

        public void onCaptureSequenceCompleted(CameraDevice var1, int var2, long var3);

        public void onCaptureStarted(CameraDevice var1, CaptureRequest var2, long var3, long var5);
    }

    static class CaptureCallbackHolder {
        private final CaptureCallback mCallback;
        private final Executor mExecutor;
        private final boolean mHasBatchedOutputs;
        private final boolean mRepeating;
        private final List<CaptureRequest> mRequestList;
        private final int mSessionId;

        CaptureCallbackHolder(CaptureCallback object, List<CaptureRequest> list, Executor executor, boolean bl, int n) {
            if (object != null && executor != null) {
                this.mRepeating = bl;
                this.mExecutor = executor;
                this.mRequestList = new ArrayList<CaptureRequest>(list);
                this.mCallback = object;
                this.mSessionId = n;
                boolean bl2 = true;
                n = 0;
                do {
                    bl = bl2;
                    if (n >= list.size()) break;
                    object = list.get(n);
                    if (!((CaptureRequest)object).isPartOfCRequestList()) {
                        bl = false;
                        break;
                    }
                    if (n == 0 && ((CaptureRequest)object).getTargets().size() != 2) {
                        bl = false;
                        break;
                    }
                    ++n;
                } while (true);
                this.mHasBatchedOutputs = bl;
                return;
            }
            throw new UnsupportedOperationException("Must have a valid handler and a valid callback");
        }

        public CaptureCallback getCallback() {
            return this.mCallback;
        }

        public Executor getExecutor() {
            return this.mExecutor;
        }

        public CaptureRequest getRequest() {
            return this.getRequest(0);
        }

        public CaptureRequest getRequest(int n) {
            if (n < this.mRequestList.size()) {
                if (n >= 0) {
                    return this.mRequestList.get(n);
                }
                throw new IllegalArgumentException(String.format("Requested subsequenceId %d is negative", n));
            }
            throw new IllegalArgumentException(String.format("Requested subsequenceId %d is larger than request list size %d.", n, this.mRequestList.size()));
        }

        public int getRequestCount() {
            return this.mRequestList.size();
        }

        public int getSessionId() {
            return this.mSessionId;
        }

        public boolean hasBatchedOutputs() {
            return this.mHasBatchedOutputs;
        }

        public boolean isRepeating() {
            return this.mRepeating;
        }
    }

    public class FrameNumberTracker {
        private long[] mCompletedFrameNumber = new long[3];
        private final TreeMap<Long, Integer> mFutureErrorMap = new TreeMap();
        private final HashMap<Long, List<CaptureResult>> mPartialResults = new HashMap();
        private final LinkedList<Long>[] mSkippedFrameNumbers = new LinkedList[3];
        private final LinkedList<Long>[] mSkippedOtherFrameNumbers = new LinkedList[3];

        public FrameNumberTracker() {
            for (int i = 0; i < 3; ++i) {
                this.mCompletedFrameNumber[i] = -1L;
                this.mSkippedOtherFrameNumbers[i] = new LinkedList();
                this.mSkippedFrameNumbers[i] = new LinkedList();
            }
        }

        private void update() {
            Iterator<Map.Entry<Long, Integer>> iterator = this.mFutureErrorMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Object object = iterator.next();
                Long l = object.getKey();
                int n = object.getValue();
                Boolean bl = false;
                long l2 = l;
                if (l2 == (object = this.mCompletedFrameNumber)[n] + 1L) {
                    object[n] = l;
                    object = true;
                } else if (!this.mSkippedFrameNumbers[n].isEmpty()) {
                    object = bl;
                    if (l == this.mSkippedFrameNumbers[n].element()) {
                        this.mCompletedFrameNumber[n] = l;
                        this.mSkippedFrameNumbers[n].remove();
                        object = true;
                    }
                } else {
                    int n2 = 1;
                    do {
                        object = bl;
                        if (n2 >= 3) break;
                        int n3 = (n + n2) % 3;
                        if (!this.mSkippedOtherFrameNumbers[n3].isEmpty() && l == this.mSkippedOtherFrameNumbers[n3].element()) {
                            this.mCompletedFrameNumber[n] = l;
                            this.mSkippedOtherFrameNumbers[n3].remove();
                            object = true;
                            break;
                        }
                        ++n2;
                    } while (true);
                }
                if (!object.booleanValue()) continue;
                iterator.remove();
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        private void updateCompletedFrameNumber(long l, int n) throws IllegalArgumentException {
            Object object = this.mCompletedFrameNumber;
            if (l <= object[n]) {
                object = new StringBuilder();
                ((StringBuilder)object).append("frame number ");
                ((StringBuilder)object).append(l);
                ((StringBuilder)object).append(" is a repeat");
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            int n2 = (n + 1) % 3;
            int n3 = (n + 2) % 3;
            long l2 = Math.max((long)object[n2], (long)object[n3]);
            if (l < l2) {
                if (!this.mSkippedFrameNumbers[n].isEmpty()) {
                    if (l < this.mSkippedFrameNumbers[n].element()) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("frame number ");
                        ((StringBuilder)object).append(l);
                        ((StringBuilder)object).append(" is a repeat");
                        throw new IllegalArgumentException(((StringBuilder)object).toString());
                    }
                    if (l > this.mSkippedFrameNumbers[n].element()) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("frame number ");
                        ((StringBuilder)object).append(l);
                        ((StringBuilder)object).append(" comes out of order. Expecting ");
                        ((StringBuilder)object).append(this.mSkippedFrameNumbers[n].element());
                        throw new IllegalArgumentException(((StringBuilder)object).toString());
                    }
                    this.mSkippedFrameNumbers[n].remove();
                } else {
                    LinkedList<Long> linkedList;
                    int n4 = this.mSkippedOtherFrameNumbers[n2].indexOf(l);
                    int n5 = this.mSkippedOtherFrameNumbers[n3].indexOf(l);
                    int n6 = 1;
                    int n7 = n4 != -1 ? 1 : 0;
                    if (n5 == -1) {
                        n6 = 0;
                    }
                    if (!(n7 ^ n6)) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("frame number ");
                        ((StringBuilder)object).append(l);
                        ((StringBuilder)object).append(" is a repeat or invalid");
                        throw new IllegalArgumentException(((StringBuilder)object).toString());
                    }
                    if (n7 != 0) {
                        linkedList = this.mSkippedOtherFrameNumbers[n2];
                        object = this.mSkippedFrameNumbers[n3];
                        n7 = n4;
                    } else {
                        linkedList = this.mSkippedOtherFrameNumbers[n3];
                        object = this.mSkippedFrameNumbers[n2];
                        n7 = n5;
                    }
                    for (n6 = 0; n6 < n7; ++n6) {
                        ((LinkedList)object).add(linkedList.removeFirst());
                    }
                    linkedList.remove();
                }
            } else {
                for (l2 = Math.max((long)l2, (long)this.mCompletedFrameNumber[n]) + 1L; l2 < l; ++l2) {
                    this.mSkippedOtherFrameNumbers[n].add(l2);
                }
            }
            this.mCompletedFrameNumber[n] = l;
        }

        public long getCompletedFrameNumber() {
            return this.mCompletedFrameNumber[0];
        }

        public long getCompletedReprocessFrameNumber() {
            return this.mCompletedFrameNumber[1];
        }

        public long getCompletedZslStillFrameNumber() {
            return this.mCompletedFrameNumber[2];
        }

        public List<CaptureResult> popPartialResults(long l) {
            return this.mPartialResults.remove(l);
        }

        public void updateTracker(long l, CaptureResult captureResult, boolean bl, int n) {
            List<CaptureResult> list;
            if (!bl) {
                this.updateTracker(l, false, n);
                return;
            }
            if (captureResult == null) {
                return;
            }
            List<CaptureResult> list2 = list = this.mPartialResults.get(l);
            if (list == null) {
                list2 = new ArrayList<CaptureResult>();
                this.mPartialResults.put(l, list2);
            }
            list2.add(captureResult);
        }

        public void updateTracker(long l, boolean bl, int n) {
            if (bl) {
                this.mFutureErrorMap.put(l, n);
            } else {
                try {
                    this.updateCompletedFrameNumber(l, n);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    Log.e(CameraDeviceImpl.this.TAG, illegalArgumentException.getMessage());
                }
            }
            this.update();
        }
    }

    static class RequestLastFrameNumbersHolder {
        private final long mLastRegularFrameNumber;
        private final long mLastReprocessFrameNumber;
        private final long mLastZslStillFrameNumber;
        private final int mRequestId;

        RequestLastFrameNumbersHolder(int n, long l, int[] arrn) {
            long l2 = -1L;
            long l3 = -1L;
            if (arrn != null) {
                if (l >= (long)(arrn.length - 1)) {
                    long l4;
                    long l5;
                    int n2 = arrn.length - 1;
                    do {
                        l5 = l2;
                        l4 = l3;
                        if (n2 < 0) break;
                        if (arrn[n2] == 2 && l3 == -1L) {
                            l4 = l;
                            l5 = l2;
                        } else {
                            l5 = l2;
                            l4 = l3;
                            if (arrn[n2] == 0) {
                                l5 = l2;
                                l4 = l3;
                                if (l2 == -1L) {
                                    l5 = l;
                                    l4 = l3;
                                }
                            }
                        }
                        if (l4 != -1L && l5 != -1L) break;
                        --l;
                        --n2;
                        l2 = l5;
                        l3 = l4;
                    } while (true);
                    this.mLastRegularFrameNumber = l5;
                    this.mLastZslStillFrameNumber = l4;
                    this.mLastReprocessFrameNumber = -1L;
                    this.mRequestId = n;
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("lastFrameNumber: ");
                stringBuilder.append(l);
                stringBuilder.append(" should be at least ");
                stringBuilder.append(arrn.length - 1);
                stringBuilder.append(" for the number of requests in the list: ");
                stringBuilder.append(arrn.length);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            throw new IllegalArgumentException("repeatingRequest list must not be null");
        }

        public RequestLastFrameNumbersHolder(List<CaptureRequest> list, SubmitInfo submitInfo) {
            long l = -1L;
            long l2 = -1L;
            long l3 = -1L;
            long l4 = submitInfo.getLastFrameNumber();
            if (submitInfo.getLastFrameNumber() >= (long)(list.size() - 1)) {
                for (int i = list.size() - 1; i >= 0; --i) {
                    long l5;
                    long l6;
                    long l7;
                    int n = list.get(i).getRequestType();
                    if (n == 1 && l2 == -1L) {
                        l6 = l4;
                        l7 = l;
                        l5 = l3;
                    } else if (n == 2 && l3 == -1L) {
                        l5 = l4;
                        l7 = l;
                        l6 = l2;
                    } else {
                        l7 = l;
                        l6 = l2;
                        l5 = l3;
                        if (n == 0) {
                            l7 = l;
                            l6 = l2;
                            l5 = l3;
                            if (l == -1L) {
                                l7 = l4;
                                l5 = l3;
                                l6 = l2;
                            }
                        }
                    }
                    if (l6 != -1L && l5 != -1L && l7 != -1L) {
                        l = l7;
                        l2 = l6;
                        l3 = l5;
                        break;
                    }
                    --l4;
                    l = l7;
                    l2 = l6;
                    l3 = l5;
                }
                this.mLastRegularFrameNumber = l;
                this.mLastReprocessFrameNumber = l2;
                this.mLastZslStillFrameNumber = l3;
                this.mRequestId = submitInfo.getRequestId();
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("lastFrameNumber: ");
            stringBuilder.append(submitInfo.getLastFrameNumber());
            stringBuilder.append(" should be at least ");
            stringBuilder.append(list.size() - 1);
            stringBuilder.append(" for the number of  requests in the list: ");
            stringBuilder.append(list.size());
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public long getLastFrameNumber() {
            return Math.max(this.mLastZslStillFrameNumber, Math.max(this.mLastRegularFrameNumber, this.mLastReprocessFrameNumber));
        }

        public long getLastRegularFrameNumber() {
            return this.mLastRegularFrameNumber;
        }

        public long getLastReprocessFrameNumber() {
            return this.mLastReprocessFrameNumber;
        }

        public long getLastZslStillFrameNumber() {
            return this.mLastZslStillFrameNumber;
        }

        public int getRequestId() {
            return this.mRequestId;
        }
    }

    public static abstract class StateCallbackKK
    extends CameraDevice.StateCallback {
        public void onActive(CameraDevice cameraDevice) {
        }

        public void onBusy(CameraDevice cameraDevice) {
        }

        public void onIdle(CameraDevice cameraDevice) {
        }

        public void onRequestQueueEmpty() {
        }

        public void onSurfacePrepared(Surface surface) {
        }

        public void onUnconfigured(CameraDevice cameraDevice) {
        }
    }

}

