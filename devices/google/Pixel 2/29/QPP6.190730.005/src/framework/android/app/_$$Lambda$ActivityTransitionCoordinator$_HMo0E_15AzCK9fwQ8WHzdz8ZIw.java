/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.ActivityTransitionCoordinator;

public final class _$$Lambda$ActivityTransitionCoordinator$_HMo0E_15AzCK9fwQ8WHzdz8ZIw
implements Runnable {
    private final /* synthetic */ ActivityTransitionCoordinator f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$ActivityTransitionCoordinator$_HMo0E_15AzCK9fwQ8WHzdz8ZIw(ActivityTransitionCoordinator activityTransitionCoordinator, int n) {
        this.f$0 = activityTransitionCoordinator;
        this.f$1 = n;
    }

    @Override
    public final void run() {
        this.f$0.lambda$scheduleGhostVisibilityChange$1$ActivityTransitionCoordinator(this.f$1);
    }
}

