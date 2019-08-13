/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.impl;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.impl.CallbackProxies;

public final class _$$Lambda$CallbackProxies$SessionStateCallbackProxy$Hoz_iT1tD_pl7sCGu4flyo_xB90
implements Runnable {
    private final /* synthetic */ CallbackProxies.SessionStateCallbackProxy f$0;
    private final /* synthetic */ CameraCaptureSession f$1;

    public /* synthetic */ _$$Lambda$CallbackProxies$SessionStateCallbackProxy$Hoz_iT1tD_pl7sCGu4flyo_xB90(CallbackProxies.SessionStateCallbackProxy sessionStateCallbackProxy, CameraCaptureSession cameraCaptureSession) {
        this.f$0 = sessionStateCallbackProxy;
        this.f$1 = cameraCaptureSession;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onReady$2$CallbackProxies$SessionStateCallbackProxy(this.f$1);
    }
}

