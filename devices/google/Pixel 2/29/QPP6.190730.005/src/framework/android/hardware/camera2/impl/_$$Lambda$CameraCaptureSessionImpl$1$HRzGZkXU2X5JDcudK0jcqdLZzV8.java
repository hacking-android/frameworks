/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.impl;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.impl.CameraCaptureSessionImpl;

public final class _$$Lambda$CameraCaptureSessionImpl$1$HRzGZkXU2X5JDcudK0jcqdLZzV8
implements Runnable {
    private final /* synthetic */ CameraCaptureSessionImpl.1 f$0;
    private final /* synthetic */ CameraCaptureSession.CaptureCallback f$1;
    private final /* synthetic */ CaptureRequest f$2;
    private final /* synthetic */ CaptureResult f$3;

    public /* synthetic */ _$$Lambda$CameraCaptureSessionImpl$1$HRzGZkXU2X5JDcudK0jcqdLZzV8(CameraCaptureSessionImpl.1 var1_1, CameraCaptureSession.CaptureCallback captureCallback, CaptureRequest captureRequest, CaptureResult captureResult) {
        this.f$0 = var1_1;
        this.f$1 = captureCallback;
        this.f$2 = captureRequest;
        this.f$3 = captureResult;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onCapturePartial$1$CameraCaptureSessionImpl$1(this.f$1, this.f$2, this.f$3);
    }
}

