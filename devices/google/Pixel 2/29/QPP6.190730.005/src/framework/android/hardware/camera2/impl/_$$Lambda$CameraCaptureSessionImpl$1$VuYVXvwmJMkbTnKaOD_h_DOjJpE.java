/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.impl;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.impl.CameraCaptureSessionImpl;
import android.view.Surface;

public final class _$$Lambda$CameraCaptureSessionImpl$1$VuYVXvwmJMkbTnKaOD_h_DOjJpE
implements Runnable {
    private final /* synthetic */ CameraCaptureSessionImpl.1 f$0;
    private final /* synthetic */ CameraCaptureSession.CaptureCallback f$1;
    private final /* synthetic */ CaptureRequest f$2;
    private final /* synthetic */ Surface f$3;
    private final /* synthetic */ long f$4;

    public /* synthetic */ _$$Lambda$CameraCaptureSessionImpl$1$VuYVXvwmJMkbTnKaOD_h_DOjJpE(CameraCaptureSessionImpl.1 var1_1, CameraCaptureSession.CaptureCallback captureCallback, CaptureRequest captureRequest, Surface surface, long l) {
        this.f$0 = var1_1;
        this.f$1 = captureCallback;
        this.f$2 = captureRequest;
        this.f$3 = surface;
        this.f$4 = l;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onCaptureBufferLost$7$CameraCaptureSessionImpl$1(this.f$1, this.f$2, this.f$3, this.f$4);
    }
}

