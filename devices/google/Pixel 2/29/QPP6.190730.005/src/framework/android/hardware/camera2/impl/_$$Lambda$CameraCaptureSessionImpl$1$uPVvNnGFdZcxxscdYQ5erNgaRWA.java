/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.impl;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.impl.CameraCaptureSessionImpl;

public final class _$$Lambda$CameraCaptureSessionImpl$1$uPVvNnGFdZcxxscdYQ5erNgaRWA
implements Runnable {
    private final /* synthetic */ CameraCaptureSessionImpl.1 f$0;
    private final /* synthetic */ CameraCaptureSession.CaptureCallback f$1;
    private final /* synthetic */ CaptureRequest f$2;
    private final /* synthetic */ long f$3;
    private final /* synthetic */ long f$4;

    public /* synthetic */ _$$Lambda$CameraCaptureSessionImpl$1$uPVvNnGFdZcxxscdYQ5erNgaRWA(CameraCaptureSessionImpl.1 var1_1, CameraCaptureSession.CaptureCallback captureCallback, CaptureRequest captureRequest, long l, long l2) {
        this.f$0 = var1_1;
        this.f$1 = captureCallback;
        this.f$2 = captureRequest;
        this.f$3 = l;
        this.f$4 = l2;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onCaptureStarted$0$CameraCaptureSessionImpl$1(this.f$1, this.f$2, this.f$3, this.f$4);
    }
}

