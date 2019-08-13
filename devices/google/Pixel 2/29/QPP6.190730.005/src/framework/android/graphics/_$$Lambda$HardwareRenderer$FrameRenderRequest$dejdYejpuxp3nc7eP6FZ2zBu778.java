/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.graphics.HardwareRenderer;
import java.util.concurrent.Executor;

public final class _$$Lambda$HardwareRenderer$FrameRenderRequest$dejdYejpuxp3nc7eP6FZ2zBu778
implements HardwareRenderer.FrameCompleteCallback {
    private final /* synthetic */ Executor f$0;
    private final /* synthetic */ Runnable f$1;

    public /* synthetic */ _$$Lambda$HardwareRenderer$FrameRenderRequest$dejdYejpuxp3nc7eP6FZ2zBu778(Executor executor, Runnable runnable) {
        this.f$0 = executor;
        this.f$1 = runnable;
    }

    @Override
    public final void onFrameComplete(long l) {
        HardwareRenderer.FrameRenderRequest.lambda$setFrameCommitCallback$0(this.f$0, this.f$1, l);
    }
}

