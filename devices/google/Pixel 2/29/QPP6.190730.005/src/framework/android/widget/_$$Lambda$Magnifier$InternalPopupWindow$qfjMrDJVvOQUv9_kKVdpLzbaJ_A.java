/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.graphics.HardwareRenderer;
import android.widget.Magnifier;

public final class _$$Lambda$Magnifier$InternalPopupWindow$qfjMrDJVvOQUv9_kKVdpLzbaJ_A
implements HardwareRenderer.FrameDrawingCallback {
    private final /* synthetic */ Magnifier.InternalPopupWindow f$0;
    private final /* synthetic */ boolean f$1;
    private final /* synthetic */ int f$2;
    private final /* synthetic */ int f$3;
    private final /* synthetic */ boolean f$4;

    public /* synthetic */ _$$Lambda$Magnifier$InternalPopupWindow$qfjMrDJVvOQUv9_kKVdpLzbaJ_A(Magnifier.InternalPopupWindow internalPopupWindow, boolean bl, int n, int n2, boolean bl2) {
        this.f$0 = internalPopupWindow;
        this.f$1 = bl;
        this.f$2 = n;
        this.f$3 = n2;
        this.f$4 = bl2;
    }

    @Override
    public final void onFrameDraw(long l) {
        this.f$0.lambda$doDraw$0$Magnifier$InternalPopupWindow(this.f$1, this.f$2, this.f$3, this.f$4, l);
    }
}

