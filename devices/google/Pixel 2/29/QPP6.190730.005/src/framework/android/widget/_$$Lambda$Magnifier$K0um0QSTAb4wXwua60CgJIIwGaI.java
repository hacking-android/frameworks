/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.PixelCopy;
import android.widget.Magnifier;

public final class _$$Lambda$Magnifier$K0um0QSTAb4wXwua60CgJIIwGaI
implements PixelCopy.OnPixelCopyFinishedListener {
    private final /* synthetic */ Magnifier f$0;
    private final /* synthetic */ Magnifier.InternalPopupWindow f$1;
    private final /* synthetic */ boolean f$2;
    private final /* synthetic */ Point f$3;
    private final /* synthetic */ Bitmap f$4;

    public /* synthetic */ _$$Lambda$Magnifier$K0um0QSTAb4wXwua60CgJIIwGaI(Magnifier magnifier, Magnifier.InternalPopupWindow internalPopupWindow, boolean bl, Point point, Bitmap bitmap) {
        this.f$0 = magnifier;
        this.f$1 = internalPopupWindow;
        this.f$2 = bl;
        this.f$3 = point;
        this.f$4 = bitmap;
    }

    @Override
    public final void onPixelCopyFinished(int n) {
        this.f$0.lambda$performPixelCopy$1$Magnifier(this.f$1, this.f$2, this.f$3, this.f$4, n);
    }
}

