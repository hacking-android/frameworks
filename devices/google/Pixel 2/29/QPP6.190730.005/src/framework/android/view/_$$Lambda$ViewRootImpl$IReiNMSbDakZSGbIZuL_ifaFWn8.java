/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.graphics.HardwareRenderer;
import android.view.ViewRootImpl;

public final class _$$Lambda$ViewRootImpl$IReiNMSbDakZSGbIZuL_ifaFWn8
implements HardwareRenderer.FrameDrawingCallback {
    private final /* synthetic */ HardwareRenderer.FrameDrawingCallback f$0;

    public /* synthetic */ _$$Lambda$ViewRootImpl$IReiNMSbDakZSGbIZuL_ifaFWn8(HardwareRenderer.FrameDrawingCallback frameDrawingCallback) {
        this.f$0 = frameDrawingCallback;
    }

    @Override
    public final void onFrameDraw(long l) {
        ViewRootImpl.lambda$registerRtFrameCallback$0(this.f$0, l);
    }
}

