/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.EnterTransitionCoordinator;
import android.os.Bundle;

public final class _$$Lambda$EnterTransitionCoordinator$3$bzpzcEqxdHzyaWu6Gq6AOD9dFMo
implements Runnable {
    private final /* synthetic */ EnterTransitionCoordinator.3 f$0;
    private final /* synthetic */ Bundle f$1;

    public /* synthetic */ _$$Lambda$EnterTransitionCoordinator$3$bzpzcEqxdHzyaWu6Gq6AOD9dFMo(EnterTransitionCoordinator.3 var1_1, Bundle bundle) {
        this.f$0 = var1_1;
        this.f$1 = bundle;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onSharedElementsReady$0$EnterTransitionCoordinator$3(this.f$1);
    }
}

