/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.legacy;

import android.hardware.Camera;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.util.Size;
import com.android.internal.util.Preconditions;

public class LegacyRequest {
    public final CaptureRequest captureRequest;
    public final CameraCharacteristics characteristics;
    public final Camera.Parameters parameters;
    public final Size previewSize;

    public LegacyRequest(CameraCharacteristics cameraCharacteristics, CaptureRequest captureRequest, Size size, Camera.Parameters parameters) {
        this.characteristics = Preconditions.checkNotNull(cameraCharacteristics, "characteristics must not be null");
        this.captureRequest = Preconditions.checkNotNull(captureRequest, "captureRequest must not be null");
        this.previewSize = Preconditions.checkNotNull(size, "previewSize must not be null");
        Preconditions.checkNotNull(parameters, "parameters must not be null");
        this.parameters = Camera.getParametersCopy(parameters);
    }

    public void setParameters(Camera.Parameters parameters) {
        Preconditions.checkNotNull(parameters, "parameters must not be null");
        this.parameters.copyFrom(parameters);
    }
}

