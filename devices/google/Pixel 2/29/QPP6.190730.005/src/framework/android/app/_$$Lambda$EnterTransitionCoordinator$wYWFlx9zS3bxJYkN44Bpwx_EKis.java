/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.EnterTransitionCoordinator;
import android.util.ArrayMap;

public final class _$$Lambda$EnterTransitionCoordinator$wYWFlx9zS3bxJYkN44Bpwx_EKis
implements Runnable {
    private final /* synthetic */ EnterTransitionCoordinator f$0;
    private final /* synthetic */ ArrayMap f$1;

    public /* synthetic */ _$$Lambda$EnterTransitionCoordinator$wYWFlx9zS3bxJYkN44Bpwx_EKis(EnterTransitionCoordinator enterTransitionCoordinator, ArrayMap arrayMap) {
        this.f$0 = enterTransitionCoordinator;
        this.f$1 = arrayMap;
    }

    @Override
    public final void run() {
        this.f$0.lambda$triggerViewsReady$0$EnterTransitionCoordinator(this.f$1);
    }
}

