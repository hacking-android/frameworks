/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.impl;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.impl.CameraCaptureSessionImpl;

public final class _$$Lambda$CameraCaptureSessionImpl$1$VsKq1alEqL3XH_hLTWXgi7fSF3s
implements Runnable {
    private final /* synthetic */ CameraCaptureSessionImpl.1 f$0;
    private final /* synthetic */ CameraCaptureSession.CaptureCallback f$1;
    private final /* synthetic */ CaptureRequest f$2;
    private final /* synthetic */ CaptureFailure f$3;

    public /* synthetic */ _$$Lambda$CameraCaptureSessionImpl$1$VsKq1alEqL3XH_hLTWXgi7fSF3s(CameraCaptureSessionImpl.1 var1_1, CameraCaptureSession.CaptureCallback captureCallback, CaptureRequest captureRequest, CaptureFailure captureFailure) {
        this.f$0 = var1_1;
        this.f$1 = captureCallback;
        this.f$2 = captureRequest;
        this.f$3 = captureFailure;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onCaptureFailed$4$CameraCaptureSessionImpl$1(this.f$1, this.f$2, this.f$3);
    }
}

