/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.graphics.Point;
import android.widget.Magnifier;

public final class _$$Lambda$Magnifier$sEUKNU2_gseoDMBt_HOs_JGAfZ8
implements Runnable {
    private final /* synthetic */ Magnifier f$0;
    private final /* synthetic */ Magnifier.InternalPopupWindow f$1;
    private final /* synthetic */ Point f$2;

    public /* synthetic */ _$$Lambda$Magnifier$sEUKNU2_gseoDMBt_HOs_JGAfZ8(Magnifier magnifier, Magnifier.InternalPopupWindow internalPopupWindow, Point point) {
        this.f$0 = magnifier;
        this.f$1 = internalPopupWindow;
        this.f$2 = point;
    }

    @Override
    public final void run() {
        this.f$0.lambda$show$0$Magnifier(this.f$1, this.f$2);
    }
}

