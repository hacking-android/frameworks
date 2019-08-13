/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2;

import android.hardware.camera2.CameraManager;

public final class _$$Lambda$CameraManager$CameraManagerGlobal$6Ptxoe4wF_VCkE_pml8t66mklao
implements Runnable {
    private final /* synthetic */ CameraManager.TorchCallback f$0;
    private final /* synthetic */ String f$1;

    public /* synthetic */ _$$Lambda$CameraManager$CameraManagerGlobal$6Ptxoe4wF_VCkE_pml8t66mklao(CameraManager.TorchCallback torchCallback, String string2) {
        this.f$0 = torchCallback;
        this.f$1 = string2;
    }

    @Override
    public final void run() {
        CameraManager.CameraManagerGlobal.lambda$postSingleTorchUpdate$1(this.f$0, this.f$1);
    }
}

