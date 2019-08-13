/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.impl;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.impl.CallbackProxies;
import android.hardware.camera2.impl.CameraCaptureSessionCore;
import android.hardware.camera2.impl.CameraDeviceImpl;
import android.hardware.camera2.impl._$$Lambda$CameraCaptureSessionImpl$1$7mSdNTTAoYA0D3ITDxzDJKGykz0;
import android.hardware.camera2.impl._$$Lambda$CameraCaptureSessionImpl$1$HRzGZkXU2X5JDcudK0jcqdLZzV8;
import android.hardware.camera2.impl._$$Lambda$CameraCaptureSessionImpl$1$KZ4tthx5TnA5BizPVljsPqqdHck;
import android.hardware.camera2.impl._$$Lambda$CameraCaptureSessionImpl$1$OA1Yz_YgzMO8qcV8esRjyt7ykp4;
import android.hardware.camera2.impl._$$Lambda$CameraCaptureSessionImpl$1$TIJELOXvjSbPh6mpBLfBJ5ciNic;
import android.hardware.camera2.impl._$$Lambda$CameraCaptureSessionImpl$1$VsKq1alEqL3XH_hLTWXgi7fSF3s;
import android.hardware.camera2.impl._$$Lambda$CameraCaptureSessionImpl$1$VuYVXvwmJMkbTnKaOD_h_DOjJpE;
import android.hardware.camera2.impl._$$Lambda$CameraCaptureSessionImpl$1$uPVvNnGFdZcxxscdYQ5erNgaRWA;
import android.hardware.camera2.params.InputConfiguration;
import android.hardware.camera2.params.OutputConfiguration;
import android.hardware.camera2.utils.TaskDrainer;
import android.hardware.camera2.utils.TaskSingleDrainer;
import android.os.Binder;
import android.os.Handler;
import android.util.Log;
import android.view.Surface;
import com.android.internal.util.Preconditions;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

public class CameraCaptureSessionImpl
extends CameraCaptureSession
implements CameraCaptureSessionCore {
    private static final boolean DEBUG = false;
    private static final String TAG = "CameraCaptureSession";
    private final TaskSingleDrainer mAbortDrainer;
    private volatile boolean mAborting;
    private boolean mClosed = false;
    private final boolean mConfigureSuccess;
    private final Executor mDeviceExecutor;
    private final CameraDeviceImpl mDeviceImpl;
    private final int mId;
    private final String mIdString;
    private final TaskSingleDrainer mIdleDrainer;
    private final Surface mInput;
    private final TaskDrainer<Integer> mSequenceDrainer;
    private boolean mSkipUnconfigure = false;
    private final CameraCaptureSession.StateCallback mStateCallback;
    private final Executor mStateExecutor;

    CameraCaptureSessionImpl(int n, Surface object, CameraCaptureSession.StateCallback stateCallback, Executor executor, CameraDeviceImpl cameraDeviceImpl, Executor executor2, boolean bl) {
        if (stateCallback != null) {
            this.mId = n;
            this.mIdString = String.format("Session %d: ", this.mId);
            this.mInput = object;
            this.mStateExecutor = Preconditions.checkNotNull(executor, "stateExecutor must not be null");
            this.mStateCallback = this.createUserStateCallbackProxy(this.mStateExecutor, stateCallback);
            this.mDeviceExecutor = Preconditions.checkNotNull(executor2, "deviceStateExecutor must not be null");
            this.mDeviceImpl = Preconditions.checkNotNull(cameraDeviceImpl, "deviceImpl must not be null");
            this.mSequenceDrainer = new TaskDrainer(this.mDeviceExecutor, new SequenceDrainListener(), "seq");
            this.mIdleDrainer = new TaskSingleDrainer(this.mDeviceExecutor, new IdleDrainListener(), "idle");
            this.mAbortDrainer = new TaskSingleDrainer(this.mDeviceExecutor, new AbortDrainListener(), "abort");
            if (bl) {
                this.mStateCallback.onConfigured(this);
                this.mConfigureSuccess = true;
            } else {
                this.mStateCallback.onConfigureFailed(this);
                this.mClosed = true;
                object = new StringBuilder();
                ((StringBuilder)object).append(this.mIdString);
                ((StringBuilder)object).append("Failed to create capture session; configuration failed");
                Log.e(TAG, ((StringBuilder)object).toString());
                this.mConfigureSuccess = false;
            }
            return;
        }
        throw new IllegalArgumentException("callback must not be null");
    }

    private int addPendingSequence(int n) {
        this.mSequenceDrainer.taskStarted(n);
        return n;
    }

    private void checkCaptureRequest(CaptureRequest captureRequest) {
        if (captureRequest != null) {
            if (captureRequest.isReprocess() && !this.isReprocessable()) {
                throw new IllegalArgumentException("this capture session cannot handle reprocess requests");
            }
            if (captureRequest.isReprocess() && captureRequest.getReprocessableSessionId() != this.mId) {
                throw new IllegalArgumentException("capture request was created for another session");
            }
            return;
        }
        throw new IllegalArgumentException("request must not be null");
    }

    private void checkCaptureRequests(List<CaptureRequest> object) {
        if (object != null) {
            if (!object.isEmpty()) {
                object = object.iterator();
                while (object.hasNext()) {
                    CaptureRequest captureRequest = (CaptureRequest)object.next();
                    if (!captureRequest.isReprocess()) continue;
                    if (this.isReprocessable()) {
                        if (captureRequest.getReprocessableSessionId() == this.mId) continue;
                        throw new IllegalArgumentException("Capture request was created for another session");
                    }
                    throw new IllegalArgumentException("This capture session cannot handle reprocess requests");
                }
                return;
            }
            throw new IllegalArgumentException("Requests must have at least one element");
        }
        throw new IllegalArgumentException("Requests must not be null");
    }

    private void checkNotClosed() {
        if (!this.mClosed) {
            return;
        }
        throw new IllegalStateException("Session has been closed; further changes are illegal.");
    }

    private void checkRepeatingRequest(CaptureRequest captureRequest) {
        if (captureRequest != null) {
            if (!captureRequest.isReprocess()) {
                return;
            }
            throw new IllegalArgumentException("repeating reprocess requests are not supported");
        }
        throw new IllegalArgumentException("request must not be null");
    }

    private void checkRepeatingRequests(List<CaptureRequest> object) {
        if (object != null) {
            if (!object.isEmpty()) {
                object = object.iterator();
                while (object.hasNext()) {
                    if (!((CaptureRequest)object.next()).isReprocess()) continue;
                    throw new IllegalArgumentException("repeating reprocess burst requests are not supported");
                }
                return;
            }
            throw new IllegalArgumentException("requests must have at least one element");
        }
        throw new IllegalArgumentException("requests must not be null");
    }

    private CameraDeviceImpl.CaptureCallback createCaptureCallbackProxy(Handler object, CameraCaptureSession.CaptureCallback captureCallback) {
        object = captureCallback != null ? CameraDeviceImpl.checkAndWrapHandler((Handler)object) : null;
        return this.createCaptureCallbackProxyWithExecutor((Executor)object, captureCallback);
    }

    private CameraDeviceImpl.CaptureCallback createCaptureCallbackProxyWithExecutor(final Executor executor, final CameraCaptureSession.CaptureCallback captureCallback) {
        return new CameraDeviceImpl.CaptureCallback(){

            public /* synthetic */ void lambda$onCaptureBufferLost$7$CameraCaptureSessionImpl$1(CameraCaptureSession.CaptureCallback captureCallback2, CaptureRequest captureRequest, Surface surface, long l) {
                captureCallback2.onCaptureBufferLost(CameraCaptureSessionImpl.this, captureRequest, surface, l);
            }

            public /* synthetic */ void lambda$onCaptureCompleted$3$CameraCaptureSessionImpl$1(CameraCaptureSession.CaptureCallback captureCallback2, CaptureRequest captureRequest, TotalCaptureResult totalCaptureResult) {
                captureCallback2.onCaptureCompleted(CameraCaptureSessionImpl.this, captureRequest, totalCaptureResult);
            }

            public /* synthetic */ void lambda$onCaptureFailed$4$CameraCaptureSessionImpl$1(CameraCaptureSession.CaptureCallback captureCallback2, CaptureRequest captureRequest, CaptureFailure captureFailure) {
                captureCallback2.onCaptureFailed(CameraCaptureSessionImpl.this, captureRequest, captureFailure);
            }

            public /* synthetic */ void lambda$onCapturePartial$1$CameraCaptureSessionImpl$1(CameraCaptureSession.CaptureCallback captureCallback2, CaptureRequest captureRequest, CaptureResult captureResult) {
                captureCallback2.onCapturePartial(CameraCaptureSessionImpl.this, captureRequest, captureResult);
            }

            public /* synthetic */ void lambda$onCaptureProgressed$2$CameraCaptureSessionImpl$1(CameraCaptureSession.CaptureCallback captureCallback2, CaptureRequest captureRequest, CaptureResult captureResult) {
                captureCallback2.onCaptureProgressed(CameraCaptureSessionImpl.this, captureRequest, captureResult);
            }

            public /* synthetic */ void lambda$onCaptureSequenceAborted$6$CameraCaptureSessionImpl$1(CameraCaptureSession.CaptureCallback captureCallback2, int n) {
                captureCallback2.onCaptureSequenceAborted(CameraCaptureSessionImpl.this, n);
            }

            public /* synthetic */ void lambda$onCaptureSequenceCompleted$5$CameraCaptureSessionImpl$1(CameraCaptureSession.CaptureCallback captureCallback2, int n, long l) {
                captureCallback2.onCaptureSequenceCompleted(CameraCaptureSessionImpl.this, n, l);
            }

            public /* synthetic */ void lambda$onCaptureStarted$0$CameraCaptureSessionImpl$1(CameraCaptureSession.CaptureCallback captureCallback2, CaptureRequest captureRequest, long l, long l2) {
                captureCallback2.onCaptureStarted(CameraCaptureSessionImpl.this, captureRequest, l, l2);
            }

            @Override
            public void onCaptureBufferLost(CameraDevice object, CaptureRequest captureRequest, Surface surface, long l) {
                if (captureCallback != null && executor != null) {
                    long l2 = Binder.clearCallingIdentity();
                    try {
                        Executor executor2 = executor;
                        CameraCaptureSession.CaptureCallback captureCallback2 = captureCallback;
                        object = new _$$Lambda$CameraCaptureSessionImpl$1$VuYVXvwmJMkbTnKaOD_h_DOjJpE(this, captureCallback2, captureRequest, surface, l);
                        executor2.execute((Runnable)object);
                    }
                    finally {
                        Binder.restoreCallingIdentity(l2);
                    }
                }
            }

            @Override
            public void onCaptureCompleted(CameraDevice object, CaptureRequest captureRequest, TotalCaptureResult totalCaptureResult) {
                if (captureCallback != null && executor != null) {
                    long l = Binder.clearCallingIdentity();
                    try {
                        Executor executor2 = executor;
                        object = captureCallback;
                        _$$Lambda$CameraCaptureSessionImpl$1$OA1Yz_YgzMO8qcV8esRjyt7ykp4 _$$Lambda$CameraCaptureSessionImpl$1$OA1Yz_YgzMO8qcV8esRjyt7ykp4 = new _$$Lambda$CameraCaptureSessionImpl$1$OA1Yz_YgzMO8qcV8esRjyt7ykp4(this, (CameraCaptureSession.CaptureCallback)object, captureRequest, totalCaptureResult);
                        executor2.execute(_$$Lambda$CameraCaptureSessionImpl$1$OA1Yz_YgzMO8qcV8esRjyt7ykp4);
                    }
                    finally {
                        Binder.restoreCallingIdentity(l);
                    }
                }
            }

            @Override
            public void onCaptureFailed(CameraDevice object, CaptureRequest captureRequest, CaptureFailure captureFailure) {
                if (captureCallback != null && executor != null) {
                    long l = Binder.clearCallingIdentity();
                    try {
                        Executor executor2 = executor;
                        CameraCaptureSession.CaptureCallback captureCallback2 = captureCallback;
                        object = new _$$Lambda$CameraCaptureSessionImpl$1$VsKq1alEqL3XH_hLTWXgi7fSF3s(this, captureCallback2, captureRequest, captureFailure);
                        executor2.execute((Runnable)object);
                    }
                    finally {
                        Binder.restoreCallingIdentity(l);
                    }
                }
            }

            @Override
            public void onCapturePartial(CameraDevice object, CaptureRequest captureRequest, CaptureResult captureResult) {
                if (captureCallback != null && executor != null) {
                    long l = Binder.clearCallingIdentity();
                    try {
                        object = executor;
                        CameraCaptureSession.CaptureCallback captureCallback2 = captureCallback;
                        _$$Lambda$CameraCaptureSessionImpl$1$HRzGZkXU2X5JDcudK0jcqdLZzV8 _$$Lambda$CameraCaptureSessionImpl$1$HRzGZkXU2X5JDcudK0jcqdLZzV8 = new _$$Lambda$CameraCaptureSessionImpl$1$HRzGZkXU2X5JDcudK0jcqdLZzV8(this, captureCallback2, captureRequest, captureResult);
                        object.execute(_$$Lambda$CameraCaptureSessionImpl$1$HRzGZkXU2X5JDcudK0jcqdLZzV8);
                    }
                    finally {
                        Binder.restoreCallingIdentity(l);
                    }
                }
            }

            @Override
            public void onCaptureProgressed(CameraDevice object, CaptureRequest captureRequest, CaptureResult captureResult) {
                if (captureCallback != null && executor != null) {
                    long l = Binder.clearCallingIdentity();
                    try {
                        object = executor;
                        CameraCaptureSession.CaptureCallback captureCallback2 = captureCallback;
                        _$$Lambda$CameraCaptureSessionImpl$1$7mSdNTTAoYA0D3ITDxzDJKGykz0 _$$Lambda$CameraCaptureSessionImpl$1$7mSdNTTAoYA0D3ITDxzDJKGykz0 = new _$$Lambda$CameraCaptureSessionImpl$1$7mSdNTTAoYA0D3ITDxzDJKGykz0(this, captureCallback2, captureRequest, captureResult);
                        object.execute(_$$Lambda$CameraCaptureSessionImpl$1$7mSdNTTAoYA0D3ITDxzDJKGykz0);
                    }
                    finally {
                        Binder.restoreCallingIdentity(l);
                    }
                }
            }

            @Override
            public void onCaptureSequenceAborted(CameraDevice object, int n) {
                if (captureCallback != null && executor != null) {
                    long l = Binder.clearCallingIdentity();
                    try {
                        Executor executor2 = executor;
                        object = captureCallback;
                        _$$Lambda$CameraCaptureSessionImpl$1$TIJELOXvjSbPh6mpBLfBJ5ciNic _$$Lambda$CameraCaptureSessionImpl$1$TIJELOXvjSbPh6mpBLfBJ5ciNic = new _$$Lambda$CameraCaptureSessionImpl$1$TIJELOXvjSbPh6mpBLfBJ5ciNic(this, (CameraCaptureSession.CaptureCallback)object, n);
                        executor2.execute(_$$Lambda$CameraCaptureSessionImpl$1$TIJELOXvjSbPh6mpBLfBJ5ciNic);
                    }
                    finally {
                        Binder.restoreCallingIdentity(l);
                    }
                }
                CameraCaptureSessionImpl.this.finishPendingSequence(n);
            }

            @Override
            public void onCaptureSequenceCompleted(CameraDevice object, int n, long l) {
                if (captureCallback != null && executor != null) {
                    long l2 = Binder.clearCallingIdentity();
                    try {
                        Executor executor2 = executor;
                        CameraCaptureSession.CaptureCallback captureCallback2 = captureCallback;
                        object = new _$$Lambda$CameraCaptureSessionImpl$1$KZ4tthx5TnA5BizPVljsPqqdHck(this, captureCallback2, n, l);
                        executor2.execute((Runnable)object);
                    }
                    finally {
                        Binder.restoreCallingIdentity(l2);
                    }
                }
                CameraCaptureSessionImpl.this.finishPendingSequence(n);
            }

            @Override
            public void onCaptureStarted(CameraDevice object, CaptureRequest captureRequest, long l, long l2) {
                if (captureCallback != null && executor != null) {
                    long l3 = Binder.clearCallingIdentity();
                    try {
                        Executor executor2 = executor;
                        object = captureCallback;
                        _$$Lambda$CameraCaptureSessionImpl$1$uPVvNnGFdZcxxscdYQ5erNgaRWA _$$Lambda$CameraCaptureSessionImpl$1$uPVvNnGFdZcxxscdYQ5erNgaRWA = new _$$Lambda$CameraCaptureSessionImpl$1$uPVvNnGFdZcxxscdYQ5erNgaRWA(this, (CameraCaptureSession.CaptureCallback)object, captureRequest, l, l2);
                        executor2.execute(_$$Lambda$CameraCaptureSessionImpl$1$uPVvNnGFdZcxxscdYQ5erNgaRWA);
                    }
                    finally {
                        Binder.restoreCallingIdentity(l3);
                    }
                }
            }
        };
    }

    private CameraCaptureSession.StateCallback createUserStateCallbackProxy(Executor executor, CameraCaptureSession.StateCallback stateCallback) {
        return new CallbackProxies.SessionStateCallbackProxy(executor, stateCallback);
    }

    private void finishPendingSequence(int n) {
        try {
            this.mSequenceDrainer.taskFinished(n);
        }
        catch (IllegalStateException illegalStateException) {
            Log.w(TAG, illegalStateException.getMessage());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void abortCaptures() throws CameraAccessException {
        Object object = this.mDeviceImpl.mInterfaceLock;
        synchronized (object) {
            this.checkNotClosed();
            if (this.mAborting) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.mIdString);
                stringBuilder.append("abortCaptures - Session is already aborting; doing nothing");
                Log.w(TAG, stringBuilder.toString());
                return;
            }
            this.mAborting = true;
            this.mAbortDrainer.taskStarted();
            this.mDeviceImpl.flush();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int capture(CaptureRequest captureRequest, CameraCaptureSession.CaptureCallback captureCallback, Handler handler) throws CameraAccessException {
        this.checkCaptureRequest(captureRequest);
        Object object = this.mDeviceImpl.mInterfaceLock;
        synchronized (object) {
            this.checkNotClosed();
            handler = CameraDeviceImpl.checkHandler(handler, captureCallback);
            return this.addPendingSequence(this.mDeviceImpl.capture(captureRequest, this.createCaptureCallbackProxy(handler, captureCallback), this.mDeviceExecutor));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int captureBurst(List<CaptureRequest> list, CameraCaptureSession.CaptureCallback captureCallback, Handler handler) throws CameraAccessException {
        this.checkCaptureRequests(list);
        Object object = this.mDeviceImpl.mInterfaceLock;
        synchronized (object) {
            this.checkNotClosed();
            handler = CameraDeviceImpl.checkHandler(handler, captureCallback);
            return this.addPendingSequence(this.mDeviceImpl.captureBurst(list, this.createCaptureCallbackProxy(handler, captureCallback), this.mDeviceExecutor));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int captureBurstRequests(List<CaptureRequest> list, Executor executor, CameraCaptureSession.CaptureCallback captureCallback) throws CameraAccessException {
        if (executor == null) {
            throw new IllegalArgumentException("executor must not be null");
        }
        if (captureCallback == null) throw new IllegalArgumentException("callback must not be null");
        this.checkCaptureRequests(list);
        Object object = this.mDeviceImpl.mInterfaceLock;
        synchronized (object) {
            this.checkNotClosed();
            executor = CameraDeviceImpl.checkExecutor(executor, captureCallback);
            return this.addPendingSequence(this.mDeviceImpl.captureBurst(list, this.createCaptureCallbackProxyWithExecutor(executor, captureCallback), this.mDeviceExecutor));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int captureSingleRequest(CaptureRequest captureRequest, Executor executor, CameraCaptureSession.CaptureCallback captureCallback) throws CameraAccessException {
        if (executor == null) {
            throw new IllegalArgumentException("executor must not be null");
        }
        if (captureCallback == null) throw new IllegalArgumentException("callback must not be null");
        this.checkCaptureRequest(captureRequest);
        Object object = this.mDeviceImpl.mInterfaceLock;
        synchronized (object) {
            this.checkNotClosed();
            executor = CameraDeviceImpl.checkExecutor(executor, captureCallback);
            return this.addPendingSequence(this.mDeviceImpl.capture(captureRequest, this.createCaptureCallbackProxyWithExecutor(executor, captureCallback), this.mDeviceExecutor));
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void close() {
        Object object = this.mDeviceImpl.mInterfaceLock;
        // MONITORENTER : object
        if (this.mClosed) {
            // MONITOREXIT : object
            return;
        }
        this.mClosed = true;
        try {
            this.mDeviceImpl.stopRepeating();
        }
        catch (CameraAccessException cameraAccessException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.mIdString);
            stringBuilder.append("Exception while stopping repeating: ");
            Log.e(TAG, stringBuilder.toString(), cameraAccessException);
        }
        this.mSequenceDrainer.beginDrain();
        // MONITOREXIT : object
        object = this.mInput;
        if (object == null) return;
        ((Surface)object).release();
        return;
        catch (IllegalStateException illegalStateException) {
            this.mStateCallback.onClosed(this);
            // MONITOREXIT : object
            return;
        }
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

    @Override
    public void finalizeOutputConfigurations(List<OutputConfiguration> list) throws CameraAccessException {
        this.mDeviceImpl.finalizeOutputConfigs(list);
    }

    @Override
    public CameraDevice getDevice() {
        return this.mDeviceImpl;
    }

    @Override
    public CameraDeviceImpl.StateCallbackKK getDeviceStateCallback() {
        return new CameraDeviceImpl.StateCallbackKK(this.mDeviceImpl.mInterfaceLock){
            private boolean mActive = false;
            private boolean mBusy = false;
            final /* synthetic */ Object val$interfaceLock;
            {
                this.val$interfaceLock = object;
            }

            @Override
            public void onActive(CameraDevice cameraDevice) {
                CameraCaptureSessionImpl.this.mIdleDrainer.taskStarted();
                this.mActive = true;
                CameraCaptureSessionImpl.this.mStateCallback.onActive(this);
            }

            @Override
            public void onBusy(CameraDevice cameraDevice) {
                this.mBusy = true;
            }

            @Override
            public void onDisconnected(CameraDevice cameraDevice) {
                CameraCaptureSessionImpl.this.close();
            }

            @Override
            public void onError(CameraDevice object, int n) {
                object = new StringBuilder();
                ((StringBuilder)object).append(CameraCaptureSessionImpl.this.mIdString);
                ((StringBuilder)object).append("Got device error ");
                ((StringBuilder)object).append(n);
                Log.wtf(CameraCaptureSessionImpl.TAG, ((StringBuilder)object).toString());
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Converted monitor instructions to comments
             * Lifted jumps to return sites
             */
            @Override
            public void onIdle(CameraDevice cameraDevice) {
                Object object = this.val$interfaceLock;
                // MONITORENTER : object
                boolean bl = CameraCaptureSessionImpl.this.mAborting;
                // MONITOREXIT : object
                if (this.mBusy && bl) {
                    CameraCaptureSessionImpl.this.mAbortDrainer.taskFinished();
                    object = this.val$interfaceLock;
                    // MONITORENTER : object
                    CameraCaptureSessionImpl.this.mAborting = false;
                    // MONITOREXIT : object
                }
                if (this.mActive) {
                    CameraCaptureSessionImpl.this.mIdleDrainer.taskFinished();
                }
                this.mBusy = false;
                this.mActive = false;
                CameraCaptureSessionImpl.this.mStateCallback.onReady(this);
            }

            @Override
            public void onOpened(CameraDevice cameraDevice) {
                throw new AssertionError((Object)"Camera must already be open before creating a session");
            }

            @Override
            public void onRequestQueueEmpty() {
                CameraCaptureSessionImpl.this.mStateCallback.onCaptureQueueEmpty(this);
            }

            @Override
            public void onSurfacePrepared(Surface surface) {
                CameraCaptureSessionImpl.this.mStateCallback.onSurfacePrepared(this, surface);
            }

            @Override
            public void onUnconfigured(CameraDevice cameraDevice) {
            }
        };
    }

    @Override
    public Surface getInputSurface() {
        return this.mInput;
    }

    @Override
    public boolean isAborting() {
        return this.mAborting;
    }

    @Override
    public boolean isReprocessable() {
        boolean bl = this.mInput != null;
        return bl;
    }

    @Override
    public void prepare(int n, Surface surface) throws CameraAccessException {
        this.mDeviceImpl.prepare(n, surface);
    }

    @Override
    public void prepare(Surface surface) throws CameraAccessException {
        this.mDeviceImpl.prepare(surface);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void replaceSessionClose() {
        Object object = this.mDeviceImpl.mInterfaceLock;
        synchronized (object) {
            this.mSkipUnconfigure = true;
            this.close();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int setRepeatingBurst(List<CaptureRequest> list, CameraCaptureSession.CaptureCallback captureCallback, Handler handler) throws CameraAccessException {
        this.checkRepeatingRequests(list);
        Object object = this.mDeviceImpl.mInterfaceLock;
        synchronized (object) {
            this.checkNotClosed();
            handler = CameraDeviceImpl.checkHandler(handler, captureCallback);
            return this.addPendingSequence(this.mDeviceImpl.setRepeatingBurst(list, this.createCaptureCallbackProxy(handler, captureCallback), this.mDeviceExecutor));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int setRepeatingBurstRequests(List<CaptureRequest> list, Executor executor, CameraCaptureSession.CaptureCallback captureCallback) throws CameraAccessException {
        if (executor == null) {
            throw new IllegalArgumentException("executor must not be null");
        }
        if (captureCallback == null) throw new IllegalArgumentException("callback must not be null");
        this.checkRepeatingRequests(list);
        Object object = this.mDeviceImpl.mInterfaceLock;
        synchronized (object) {
            this.checkNotClosed();
            executor = CameraDeviceImpl.checkExecutor(executor, captureCallback);
            return this.addPendingSequence(this.mDeviceImpl.setRepeatingBurst(list, this.createCaptureCallbackProxyWithExecutor(executor, captureCallback), this.mDeviceExecutor));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int setRepeatingRequest(CaptureRequest captureRequest, CameraCaptureSession.CaptureCallback captureCallback, Handler handler) throws CameraAccessException {
        this.checkRepeatingRequest(captureRequest);
        Object object = this.mDeviceImpl.mInterfaceLock;
        synchronized (object) {
            this.checkNotClosed();
            handler = CameraDeviceImpl.checkHandler(handler, captureCallback);
            return this.addPendingSequence(this.mDeviceImpl.setRepeatingRequest(captureRequest, this.createCaptureCallbackProxy(handler, captureCallback), this.mDeviceExecutor));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int setSingleRepeatingRequest(CaptureRequest captureRequest, Executor executor, CameraCaptureSession.CaptureCallback captureCallback) throws CameraAccessException {
        if (executor == null) {
            throw new IllegalArgumentException("executor must not be null");
        }
        if (captureCallback == null) throw new IllegalArgumentException("callback must not be null");
        this.checkRepeatingRequest(captureRequest);
        Object object = this.mDeviceImpl.mInterfaceLock;
        synchronized (object) {
            this.checkNotClosed();
            executor = CameraDeviceImpl.checkExecutor(executor, captureCallback);
            return this.addPendingSequence(this.mDeviceImpl.setRepeatingRequest(captureRequest, this.createCaptureCallbackProxyWithExecutor(executor, captureCallback), this.mDeviceExecutor));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void stopRepeating() throws CameraAccessException {
        Object object = this.mDeviceImpl.mInterfaceLock;
        synchronized (object) {
            this.checkNotClosed();
            this.mDeviceImpl.stopRepeating();
            return;
        }
    }

    @Override
    public void tearDown(Surface surface) throws CameraAccessException {
        this.mDeviceImpl.tearDown(surface);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void updateOutputConfiguration(OutputConfiguration outputConfiguration) throws CameraAccessException {
        Object object = this.mDeviceImpl.mInterfaceLock;
        synchronized (object) {
            this.checkNotClosed();
            this.mDeviceImpl.updateOutputConfiguration(outputConfiguration);
            return;
        }
    }

    private class AbortDrainListener
    implements TaskDrainer.DrainListener {
        private AbortDrainListener() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onDrained() {
            Object object = CameraCaptureSessionImpl.access$1000((CameraCaptureSessionImpl)CameraCaptureSessionImpl.this).mInterfaceLock;
            synchronized (object) {
                if (CameraCaptureSessionImpl.this.mSkipUnconfigure) {
                    return;
                }
                CameraCaptureSessionImpl.this.mIdleDrainer.beginDrain();
                return;
            }
        }
    }

    private class IdleDrainListener
    implements TaskDrainer.DrainListener {
        private IdleDrainListener() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onDrained() {
            Object object = CameraCaptureSessionImpl.access$1000((CameraCaptureSessionImpl)CameraCaptureSessionImpl.this).mInterfaceLock;
            synchronized (object) {
                if (CameraCaptureSessionImpl.this.mSkipUnconfigure) {
                    return;
                }
                try {
                    CameraCaptureSessionImpl.this.mDeviceImpl.configureStreamsChecked(null, null, 0, null);
                }
                catch (IllegalStateException illegalStateException) {
                }
                catch (CameraAccessException cameraAccessException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(CameraCaptureSessionImpl.this.mIdString);
                    stringBuilder.append("Exception while unconfiguring outputs: ");
                    Log.e(CameraCaptureSessionImpl.TAG, stringBuilder.toString(), cameraAccessException);
                }
                return;
            }
        }
    }

    private class SequenceDrainListener
    implements TaskDrainer.DrainListener {
        private SequenceDrainListener() {
        }

        @Override
        public void onDrained() {
            CameraCaptureSessionImpl.this.mStateCallback.onClosed(CameraCaptureSessionImpl.this);
            if (CameraCaptureSessionImpl.this.mSkipUnconfigure) {
                return;
            }
            CameraCaptureSessionImpl.this.mAbortDrainer.beginDrain();
        }
    }

}

