/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.view.ViewRootImpl;

public final class _$$Lambda$dj1hfDQd0iEp_uBDBPEUMMYJJwk
implements Runnable {
    private final /* synthetic */ ViewRootImpl f$0;

    public /* synthetic */ _$$Lambda$dj1hfDQd0iEp_uBDBPEUMMYJJwk(ViewRootImpl viewRootImpl) {
        this.f$0 = viewRootImpl;
    }

    @Override
    public final void run() {
        this.f$0.destroyHardwareResources();
    }
}

