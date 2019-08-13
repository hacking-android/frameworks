/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.OutputConfiguration;
import android.os.Handler;
import android.view.Surface;
import java.util.List;
import java.util.concurrent.Executor;

public abstract class CameraCaptureSession
implements AutoCloseable {
    public static final int SESSION_ID_NONE = -1;

    public abstract void abortCaptures() throws CameraAccessException;

    public abstract int capture(CaptureRequest var1, CaptureCallback var2, Handler var3) throws CameraAccessException;

    public abstract int captureBurst(List<CaptureRequest> var1, CaptureCallback var2, Handler var3) throws CameraAccessException;

    public int captureBurstRequests(List<CaptureRequest> list, Executor executor, CaptureCallback captureCallback) throws CameraAccessException {
        throw new UnsupportedOperationException("Subclasses must override this method");
    }

    public int captureSingleRequest(CaptureRequest captureRequest, Executor executor, CaptureCallback captureCallback) throws CameraAccessException {
        throw new UnsupportedOperationException("Subclasses must override this method");
    }

    @Override
    public abstract void close();

    public abstract void finalizeOutputConfigurations(List<OutputConfiguration> var1) throws CameraAccessException;

    public abstract CameraDevice getDevice();

    public abstract Surface getInputSurface();

    public abstract boolean isReprocessable();

    public abstract void prepare(int var1, Surface var2) throws CameraAccessException;

    public abstract void prepare(Surface var1) throws CameraAccessException;

    public abstract int setRepeatingBurst(List<CaptureRequest> var1, CaptureCallback var2, Handler var3) throws CameraAccessException;

    public int setRepeatingBurstRequests(List<CaptureRequest> list, Executor executor, CaptureCallback captureCallback) throws CameraAccessException {
        throw new UnsupportedOperationException("Subclasses must override this method");
    }

    public abstract int setRepeatingRequest(CaptureRequest var1, CaptureCallback var2, Handler var3) throws CameraAccessException;

    public int setSingleRepeatingRequest(CaptureRequest captureRequest, Executor executor, CaptureCallback captureCallback) throws CameraAccessException {
        throw new UnsupportedOperationException("Subclasses must override this method");
    }

    public abstract void stopRepeating() throws CameraAccessException;

    public abstract void tearDown(Surface var1) throws CameraAccessException;

    public void updateOutputConfiguration(OutputConfiguration outputConfiguration) throws CameraAccessException {
        throw new UnsupportedOperationException("Subclasses must override this method");
    }

    public static abstract class CaptureCallback {
        public static final int NO_FRAMES_CAPTURED = -1;

        public void onCaptureBufferLost(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, Surface surface, long l) {
        }

        public void onCaptureCompleted(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, TotalCaptureResult totalCaptureResult) {
        }

        public void onCaptureFailed(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, CaptureFailure captureFailure) {
        }

        public void onCapturePartial(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, CaptureResult captureResult) {
        }

        public void onCaptureProgressed(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, CaptureResult captureResult) {
        }

        public void onCaptureSequenceAborted(CameraCaptureSession cameraCaptureSession, int n) {
        }

        public void onCaptureSequenceCompleted(CameraCaptureSession cameraCaptureSession, int n, long l) {
        }

        public void onCaptureStarted(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, long l, long l2) {
        }
    }

    public static abstract class StateCallback {
        public void onActive(CameraCaptureSession cameraCaptureSession) {
        }

        public void onCaptureQueueEmpty(CameraCaptureSession cameraCaptureSession) {
        }

        public void onClosed(CameraCaptureSession cameraCaptureSession) {
        }

        public abstract void onConfigureFailed(CameraCaptureSession var1);

        public abstract void onConfigured(CameraCaptureSession var1);

        public void onReady(CameraCaptureSession cameraCaptureSession) {
        }

        public void onSurfacePrepared(CameraCaptureSession cameraCaptureSession, Surface surface) {
        }
    }

}

