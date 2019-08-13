/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.view.ViewRootImpl;
import java.util.ArrayList;

public final class _$$Lambda$ViewRootImpl$7A_3tkr_Kw4TZAeIUGVlOoTcZhg
implements Runnable {
    private final /* synthetic */ ViewRootImpl f$0;
    private final /* synthetic */ ArrayList f$1;

    public /* synthetic */ _$$Lambda$ViewRootImpl$7A_3tkr_Kw4TZAeIUGVlOoTcZhg(ViewRootImpl viewRootImpl, ArrayList arrayList) {
        this.f$0 = viewRootImpl;
        this.f$1 = arrayList;
    }

    @Override
    public final void run() {
        this.f$0.lambda$performDraw$1$ViewRootImpl(this.f$1);
    }
}

