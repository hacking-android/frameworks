/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.graphics.HardwareRenderer;
import android.os.Handler;
import android.view.ViewRootImpl;
import java.util.ArrayList;

public final class _$$Lambda$ViewRootImpl$YBiqAhbCbXVPSKdbE3K4rH2gpxI
implements HardwareRenderer.FrameCompleteCallback {
    private final /* synthetic */ ViewRootImpl f$0;
    private final /* synthetic */ Handler f$1;
    private final /* synthetic */ ArrayList f$2;

    public /* synthetic */ _$$Lambda$ViewRootImpl$YBiqAhbCbXVPSKdbE3K4rH2gpxI(ViewRootImpl viewRootImpl, Handler handler, ArrayList arrayList) {
        this.f$0 = viewRootImpl;
        this.f$1 = handler;
        this.f$2 = arrayList;
    }

    @Override
    public final void onFrameComplete(long l) {
        this.f$0.lambda$performDraw$2$ViewRootImpl(this.f$1, this.f$2, l);
    }
}

