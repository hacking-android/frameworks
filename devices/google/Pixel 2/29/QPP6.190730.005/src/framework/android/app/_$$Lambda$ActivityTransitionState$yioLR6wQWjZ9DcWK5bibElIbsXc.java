/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.Activity;
import android.app.ActivityTransitionState;

public final class _$$Lambda$ActivityTransitionState$yioLR6wQWjZ9DcWK5bibElIbsXc
implements Runnable {
    private final /* synthetic */ ActivityTransitionState f$0;
    private final /* synthetic */ Activity f$1;

    public /* synthetic */ _$$Lambda$ActivityTransitionState$yioLR6wQWjZ9DcWK5bibElIbsXc(ActivityTransitionState activityTransitionState, Activity activity) {
        this.f$0 = activityTransitionState;
        this.f$1 = activity;
    }

    @Override
    public final void run() {
        this.f$0.lambda$startExitBackTransition$0$ActivityTransitionState(this.f$1);
    }
}

