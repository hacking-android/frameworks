/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.impl;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.impl.CallbackProxies;
import android.view.Surface;

public final class _$$Lambda$CallbackProxies$SessionStateCallbackProxy$tuajQwbKz3BV5CZZJdjl97HF6Tw
implements Runnable {
    private final /* synthetic */ CallbackProxies.SessionStateCallbackProxy f$0;
    private final /* synthetic */ CameraCaptureSession f$1;
    private final /* synthetic */ Surface f$2;

    public /* synthetic */ _$$Lambda$CallbackProxies$SessionStateCallbackProxy$tuajQwbKz3BV5CZZJdjl97HF6Tw(CallbackProxies.SessionStateCallbackProxy sessionStateCallbackProxy, CameraCaptureSession cameraCaptureSession, Surface surface) {
        this.f$0 = sessionStateCallbackProxy;
        this.f$1 = cameraCaptureSession;
        this.f$2 = surface;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onSurfacePrepared$6$CallbackProxies$SessionStateCallbackProxy(this.f$1, this.f$2);
    }
}

