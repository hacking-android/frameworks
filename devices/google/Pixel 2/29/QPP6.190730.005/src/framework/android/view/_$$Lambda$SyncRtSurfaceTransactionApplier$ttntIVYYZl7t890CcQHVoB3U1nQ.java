/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.graphics.HardwareRenderer;
import android.view.SyncRtSurfaceTransactionApplier;

public final class _$$Lambda$SyncRtSurfaceTransactionApplier$ttntIVYYZl7t890CcQHVoB3U1nQ
implements HardwareRenderer.FrameDrawingCallback {
    private final /* synthetic */ SyncRtSurfaceTransactionApplier f$0;
    private final /* synthetic */ SyncRtSurfaceTransactionApplier.SurfaceParams[] f$1;

    public /* synthetic */ _$$Lambda$SyncRtSurfaceTransactionApplier$ttntIVYYZl7t890CcQHVoB3U1nQ(SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier, SyncRtSurfaceTransactionApplier.SurfaceParams[] arrsurfaceParams) {
        this.f$0 = syncRtSurfaceTransactionApplier;
        this.f$1 = arrsurfaceParams;
    }

    @Override
    public final void onFrameDraw(long l) {
        this.f$0.lambda$scheduleApply$0$SyncRtSurfaceTransactionApplier(this.f$1, l);
    }
}

