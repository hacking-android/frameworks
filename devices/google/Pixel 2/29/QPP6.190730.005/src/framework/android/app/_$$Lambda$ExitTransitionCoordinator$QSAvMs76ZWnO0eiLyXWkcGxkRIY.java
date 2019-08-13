/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.ExitTransitionCoordinator;
import java.util.ArrayList;

public final class _$$Lambda$ExitTransitionCoordinator$QSAvMs76ZWnO0eiLyXWkcGxkRIY
implements Runnable {
    private final /* synthetic */ ExitTransitionCoordinator f$0;
    private final /* synthetic */ ArrayList f$1;

    public /* synthetic */ _$$Lambda$ExitTransitionCoordinator$QSAvMs76ZWnO0eiLyXWkcGxkRIY(ExitTransitionCoordinator exitTransitionCoordinator, ArrayList arrayList) {
        this.f$0 = exitTransitionCoordinator;
        this.f$1 = arrayList;
    }

    @Override
    public final void run() {
        this.f$0.lambda$startSharedElementExit$0$ExitTransitionCoordinator(this.f$1);
    }
}

