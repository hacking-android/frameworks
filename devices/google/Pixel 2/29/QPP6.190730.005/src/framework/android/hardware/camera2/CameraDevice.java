/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2;

import android.annotation.SystemApi;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.InputConfiguration;
import android.hardware.camera2.params.OutputConfiguration;
import android.hardware.camera2.params.SessionConfiguration;
import android.os.Handler;
import android.view.Surface;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Set;

public abstract class CameraDevice
implements AutoCloseable {
    @SystemApi
    public static final int SESSION_OPERATION_MODE_CONSTRAINED_HIGH_SPEED = 1;
    @SystemApi
    public static final int SESSION_OPERATION_MODE_NORMAL = 0;
    @SystemApi
    public static final int SESSION_OPERATION_MODE_VENDOR_START = 32768;
    public static final int TEMPLATE_MANUAL = 6;
    public static final int TEMPLATE_PREVIEW = 1;
    public static final int TEMPLATE_RECORD = 3;
    public static final int TEMPLATE_STILL_CAPTURE = 2;
    public static final int TEMPLATE_VIDEO_SNAPSHOT = 4;
    public static final int TEMPLATE_ZERO_SHUTTER_LAG = 5;

    @Override
    public abstract void close();

    public abstract CaptureRequest.Builder createCaptureRequest(int var1) throws CameraAccessException;

    public CaptureRequest.Builder createCaptureRequest(int n, Set<String> set) throws CameraAccessException {
        throw new UnsupportedOperationException("Subclasses must override this method");
    }

    public void createCaptureSession(SessionConfiguration sessionConfiguration) throws CameraAccessException {
        throw new UnsupportedOperationException("No default implementation");
    }

    public abstract void createCaptureSession(List<Surface> var1, CameraCaptureSession.StateCallback var2, Handler var3) throws CameraAccessException;

    public abstract void createCaptureSessionByOutputConfigurations(List<OutputConfiguration> var1, CameraCaptureSession.StateCallback var2, Handler var3) throws CameraAccessException;

    public abstract void createConstrainedHighSpeedCaptureSession(List<Surface> var1, CameraCaptureSession.StateCallback var2, Handler var3) throws CameraAccessException;

    @SystemApi
    public abstract void createCustomCaptureSession(InputConfiguration var1, List<OutputConfiguration> var2, int var3, CameraCaptureSession.StateCallback var4, Handler var5) throws CameraAccessException;

    public abstract CaptureRequest.Builder createReprocessCaptureRequest(TotalCaptureResult var1) throws CameraAccessException;

    public abstract void createReprocessableCaptureSession(InputConfiguration var1, List<Surface> var2, CameraCaptureSession.StateCallback var3, Handler var4) throws CameraAccessException;

    public abstract void createReprocessableCaptureSessionByConfigurations(InputConfiguration var1, List<OutputConfiguration> var2, CameraCaptureSession.StateCallback var3, Handler var4) throws CameraAccessException;

    public abstract String getId();

    public boolean isSessionConfigurationSupported(SessionConfiguration sessionConfiguration) throws CameraAccessException {
        throw new UnsupportedOperationException("Subclasses must override this method");
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface RequestTemplate {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SessionOperatingMode {
    }

    public static abstract class StateCallback {
        public static final int ERROR_CAMERA_DEVICE = 4;
        public static final int ERROR_CAMERA_DISABLED = 3;
        public static final int ERROR_CAMERA_IN_USE = 1;
        public static final int ERROR_CAMERA_SERVICE = 5;
        public static final int ERROR_MAX_CAMERAS_IN_USE = 2;

        public void onClosed(CameraDevice cameraDevice) {
        }

        public abstract void onDisconnected(CameraDevice var1);

        public abstract void onError(CameraDevice var1, int var2);

        public abstract void onOpened(CameraDevice var1);

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface ErrorCode {
        }

    }

}

