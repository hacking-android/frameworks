/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2;

import android.hardware.camera2.CameraManager;

public final class _$$Lambda$CameraManager$CameraManagerGlobal$CONvadOBAEkcHSpx8j61v67qRGM
implements Runnable {
    private final /* synthetic */ CameraManager.TorchCallback f$0;
    private final /* synthetic */ String f$1;
    private final /* synthetic */ int f$2;

    public /* synthetic */ _$$Lambda$CameraManager$CameraManagerGlobal$CONvadOBAEkcHSpx8j61v67qRGM(CameraManager.TorchCallback torchCallback, String string2, int n) {
        this.f$0 = torchCallback;
        this.f$1 = string2;
        this.f$2 = n;
    }

    @Override
    public final void run() {
        CameraManager.CameraManagerGlobal.lambda$postSingleTorchUpdate$0(this.f$0, this.f$1, this.f$2);
    }
}

