/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.graphics.HardwareRenderer;
import android.os.Handler;
import android.view.ViewRootImpl;
import java.util.ArrayList;

public final class _$$Lambda$ViewRootImpl$zlBUjCwDtoAWMNaHI62DIq_eKFY
implements HardwareRenderer.FrameCompleteCallback {
    private final /* synthetic */ Handler f$0;
    private final /* synthetic */ ArrayList f$1;

    public /* synthetic */ _$$Lambda$ViewRootImpl$zlBUjCwDtoAWMNaHI62DIq_eKFY(Handler handler, ArrayList arrayList) {
        this.f$0 = handler;
        this.f$1 = arrayList;
    }

    @Override
    public final void onFrameComplete(long l) {
        ViewRootImpl.lambda$performDraw$4(this.f$0, this.f$1, l);
    }
}

