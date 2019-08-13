/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.ActivityTransitionCoordinator;
import java.util.ArrayList;

public final class _$$Lambda$ActivityTransitionCoordinator$fkaPvc8GCghP2GMwEgS_J5m_T_4
implements Runnable {
    private final /* synthetic */ ActivityTransitionCoordinator f$0;
    private final /* synthetic */ ArrayList f$1;

    public /* synthetic */ _$$Lambda$ActivityTransitionCoordinator$fkaPvc8GCghP2GMwEgS_J5m_T_4(ActivityTransitionCoordinator activityTransitionCoordinator, ArrayList arrayList) {
        this.f$0 = activityTransitionCoordinator;
        this.f$1 = arrayList;
    }

    @Override
    public final void run() {
        this.f$0.lambda$scheduleSetSharedElementEnd$0$ActivityTransitionCoordinator(this.f$1);
    }
}

