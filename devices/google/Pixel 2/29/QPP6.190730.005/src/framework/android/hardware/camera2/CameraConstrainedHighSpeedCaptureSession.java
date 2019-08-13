/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;
import java.util.List;

public abstract class CameraConstrainedHighSpeedCaptureSession
extends CameraCaptureSession {
    public abstract List<CaptureRequest> createHighSpeedRequestList(CaptureRequest var1) throws CameraAccessException;
}

