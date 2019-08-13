/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.impl;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.impl.CameraCaptureSessionImpl;

public final class _$$Lambda$CameraCaptureSessionImpl$1$KZ4tthx5TnA5BizPVljsPqqdHck
implements Runnable {
    private final /* synthetic */ CameraCaptureSessionImpl.1 f$0;
    private final /* synthetic */ CameraCaptureSession.CaptureCallback f$1;
    private final /* synthetic */ int f$2;
    private final /* synthetic */ long f$3;

    public /* synthetic */ _$$Lambda$CameraCaptureSessionImpl$1$KZ4tthx5TnA5BizPVljsPqqdHck(CameraCaptureSessionImpl.1 var1_1, CameraCaptureSession.CaptureCallback captureCallback, int n, long l) {
        this.f$0 = var1_1;
        this.f$1 = captureCallback;
        this.f$2 = n;
        this.f$3 = l;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onCaptureSequenceCompleted$5$CameraCaptureSessionImpl$1(this.f$1, this.f$2, this.f$3);
    }
}

