/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.impl;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraConstrainedHighSpeedCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.impl.CameraCaptureSessionCore;
import android.hardware.camera2.impl.CameraCaptureSessionImpl;
import android.hardware.camera2.impl.CameraDeviceImpl;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.params.OutputConfiguration;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.hardware.camera2.utils.SurfaceUtils;
import android.os.ConditionVariable;
import android.os.Handler;
import android.util.Range;
import android.view.Surface;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

public class CameraConstrainedHighSpeedCaptureSessionImpl
extends CameraConstrainedHighSpeedCaptureSession
implements CameraCaptureSessionCore {
    private final CameraCharacteristics mCharacteristics;
    private final ConditionVariable mInitialized = new ConditionVariable();
    private final CameraCaptureSessionImpl mSessionImpl;

    CameraConstrainedHighSpeedCaptureSessionImpl(int n, CameraCaptureSession.StateCallback stateCallback, Executor executor, CameraDeviceImpl cameraDeviceImpl, Executor executor2, boolean bl, CameraCharacteristics cameraCharacteristics) {
        this.mCharacteristics = cameraCharacteristics;
        this.mSessionImpl = new CameraCaptureSessionImpl(n, null, new WrapperCallback(stateCallback), executor, cameraDeviceImpl, executor2, bl);
        this.mInitialized.open();
    }

    private boolean isConstrainedHighSpeedRequestList(List<CaptureRequest> object) {
        Preconditions.checkCollectionNotEmpty(object, "High speed request list");
        object = object.iterator();
        while (object.hasNext()) {
            if (((CaptureRequest)object.next()).isPartOfCRequestList()) continue;
            return false;
        }
        return true;
    }

    @Override
    public void abortCaptures() throws CameraAccessException {
        this.mSessionImpl.abortCaptures();
    }

    @Override
    public int capture(CaptureRequest captureRequest, CameraCaptureSession.CaptureCallback captureCallback, Handler handler) throws CameraAccessException {
        throw new UnsupportedOperationException("Constrained high speed session doesn't support this method");
    }

    @Override
    public int captureBurst(List<CaptureRequest> list, CameraCaptureSession.CaptureCallback captureCallback, Handler handler) throws CameraAccessException {
        if (this.isConstrainedHighSpeedRequestList(list)) {
            return this.mSessionImpl.captureBurst(list, captureCallback, handler);
        }
        throw new IllegalArgumentException("Only request lists created by createHighSpeedRequestList() can be submitted to a constrained high speed capture session");
    }

    @Override
    public int captureBurstRequests(List<CaptureRequest> list, Executor executor, CameraCaptureSession.CaptureCallback captureCallback) throws CameraAccessException {
        if (this.isConstrainedHighSpeedRequestList(list)) {
            return this.mSessionImpl.captureBurstRequests(list, executor, captureCallback);
        }
        throw new IllegalArgumentException("Only request lists created by createHighSpeedRequestList() can be submitted to a constrained high speed capture session");
    }

    @Override
    public int captureSingleRequest(CaptureRequest captureRequest, Executor executor, CameraCaptureSession.CaptureCallback captureCallback) throws CameraAccessException {
        throw new UnsupportedOperationException("Constrained high speed session doesn't support this method");
    }

    @Override
    public void close() {
        this.mSessionImpl.close();
    }

    @Override
    public List<CaptureRequest> createHighSpeedRequestList(CaptureRequest object) throws CameraAccessException {
        if (object != null) {
            Object object2 = ((CaptureRequest)object).getTargets();
            Object object3 = ((CaptureRequest)object).get(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE);
            SurfaceUtils.checkConstrainedHighSpeedSurfaces(object2, object3, this.mCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP));
            int n = ((Range)object3).getUpper() / 30;
            ArrayList<CaptureRequest> arrayList = new ArrayList<CaptureRequest>();
            CaptureRequest.Builder builder = new CaptureRequest.Builder(new CameraMetadataNative(((CaptureRequest)object).getNativeCopy()), false, -1, ((CaptureRequest)object).getLogicalCameraId(), null);
            builder.setTag(((CaptureRequest)object).getTag());
            Iterator<Surface> iterator = object2.iterator();
            object3 = iterator.next();
            if (object2.size() == 1 && SurfaceUtils.isSurfaceForHwVideoEncoder((Surface)object3)) {
                builder.set(CaptureRequest.CONTROL_CAPTURE_INTENT, 1);
            } else {
                builder.set(CaptureRequest.CONTROL_CAPTURE_INTENT, 3);
            }
            builder.setPartOfCHSRequestList(true);
            CaptureRequest.Builder builder2 = null;
            if (object2.size() == 2) {
                builder2 = new CaptureRequest.Builder(new CameraMetadataNative(((CaptureRequest)object).getNativeCopy()), false, -1, ((CaptureRequest)object).getLogicalCameraId(), null);
                builder2.setTag(((CaptureRequest)object).getTag());
                builder2.set(CaptureRequest.CONTROL_CAPTURE_INTENT, 3);
                builder2.addTarget((Surface)object3);
                object2 = iterator.next();
                builder2.addTarget((Surface)object2);
                builder2.setPartOfCHSRequestList(true);
                object3 = object = object3;
                if (!SurfaceUtils.isSurfaceForHwVideoEncoder((Surface)object)) {
                    object3 = object2;
                }
                builder.addTarget((Surface)object3);
                object = builder2;
            } else {
                builder.addTarget((Surface)object3);
                object = builder2;
            }
            for (int i = 0; i < n; ++i) {
                if (i == 0 && object != null) {
                    arrayList.add(((CaptureRequest.Builder)object).build());
                    continue;
                }
                arrayList.add(builder.build());
            }
            return Collections.unmodifiableList(arrayList);
        }
        throw new IllegalArgumentException("Input capture request must not be null");
    }

    @Override
    public void finalizeOutputConfigurations(List<OutputConfiguration> list) throws CameraAccessException {
        this.mSessionImpl.finalizeOutputConfigurations(list);
    }

    @Override
    public CameraDevice getDevice() {
        return this.mSessionImpl.getDevice();
    }

    @Override
    public CameraDeviceImpl.StateCallbackKK getDeviceStateCallback() {
        return this.mSessionImpl.getDeviceStateCallback();
    }

    @Override
    public Surface getInputSurface() {
        return null;
    }

    @Override
    public boolean isAborting() {
        return this.mSessionImpl.isAborting();
    }

    @Override
    public boolean isReprocessable() {
        return false;
    }

    @Override
    public void prepare(int n, Surface surface) throws CameraAccessException {
        this.mSessionImpl.prepare(n, surface);
    }

    @Override
    public void prepare(Surface surface) throws CameraAccessException {
        this.mSessionImpl.prepare(surface);
    }

    @Override
    public void replaceSessionClose() {
        this.mSessionImpl.replaceSessionClose();
    }

    @Override
    public int setRepeatingBurst(List<CaptureRequest> list, CameraCaptureSession.CaptureCallback captureCallback, Handler handler) throws CameraAccessException {
        if (this.isConstrainedHighSpeedRequestList(list)) {
            return this.mSessionImpl.setRepeatingBurst(list, captureCallback, handler);
        }
        throw new IllegalArgumentException("Only request lists created by createHighSpeedRequestList() can be submitted to a constrained high speed capture session");
    }

    @Override
    public int setRepeatingBurstRequests(List<CaptureRequest> list, Executor executor, CameraCaptureSession.CaptureCallback captureCallback) throws CameraAccessException {
        if (this.isConstrainedHighSpeedRequestList(list)) {
            return this.mSessionImpl.setRepeatingBurstRequests(list, executor, captureCallback);
        }
        throw new IllegalArgumentException("Only request lists created by createHighSpeedRequestList() can be submitted to a constrained high speed capture session");
    }

    @Override
    public int setRepeatingRequest(CaptureRequest captureRequest, CameraCaptureSession.CaptureCallback captureCallback, Handler handler) throws CameraAccessException {
        throw new UnsupportedOperationException("Constrained high speed session doesn't support this method");
    }

    @Override
    public int setSingleRepeatingRequest(CaptureRequest captureRequest, Executor executor, CameraCaptureSession.CaptureCallback captureCallback) throws CameraAccessException {
        throw new UnsupportedOperationException("Constrained high speed session doesn't support this method");
    }

    @Override
    public void stopRepeating() throws CameraAccessException {
        this.mSessionImpl.stopRepeating();
    }

    @Override
    public void tearDown(Surface surface) throws CameraAccessException {
        this.mSessionImpl.tearDown(surface);
    }

    @Override
    public void updateOutputConfiguration(OutputConfiguration outputConfiguration) throws CameraAccessException {
        throw new UnsupportedOperationException("Constrained high speed session doesn't support this method");
    }

    private class WrapperCallback
    extends CameraCaptureSession.StateCallback {
        private final CameraCaptureSession.StateCallback mCallback;

        public WrapperCallback(CameraCaptureSession.StateCallback stateCallback) {
            this.mCallback = stateCallback;
        }

        @Override
        public void onActive(CameraCaptureSession cameraCaptureSession) {
            this.mCallback.onActive(CameraConstrainedHighSpeedCaptureSessionImpl.this);
        }

        @Override
        public void onCaptureQueueEmpty(CameraCaptureSession cameraCaptureSession) {
            this.mCallback.onCaptureQueueEmpty(CameraConstrainedHighSpeedCaptureSessionImpl.this);
        }

        @Override
        public void onClosed(CameraCaptureSession cameraCaptureSession) {
            this.mCallback.onClosed(CameraConstrainedHighSpeedCaptureSessionImpl.this);
        }

        @Override
        public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
            CameraConstrainedHighSpeedCaptureSessionImpl.this.mInitialized.block();
            this.mCallback.onConfigureFailed(CameraConstrainedHighSpeedCaptureSessionImpl.this);
        }

        @Override
        public void onConfigured(CameraCaptureSession cameraCaptureSession) {
            CameraConstrainedHighSpeedCaptureSessionImpl.this.mInitialized.block();
            this.mCallback.onConfigured(CameraConstrainedHighSpeedCaptureSessionImpl.this);
        }

        @Override
        public void onReady(CameraCaptureSession cameraCaptureSession) {
            this.mCallback.onReady(CameraConstrainedHighSpeedCaptureSessionImpl.this);
        }

        @Override
        public void onSurfacePrepared(CameraCaptureSession cameraCaptureSession, Surface surface) {
            this.mCallback.onSurfacePrepared(CameraConstrainedHighSpeedCaptureSessionImpl.this, surface);
        }
    }

}

