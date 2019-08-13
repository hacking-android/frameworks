/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.view.ViewRootImpl;
import android.view.WindowManagerGlobal;

public final class _$$Lambda$WindowManagerGlobal$2bR3FsEm4EdRwuXfttH0wA2xOW4
implements Runnable {
    private final /* synthetic */ ViewRootImpl f$0;
    private final /* synthetic */ boolean f$1;

    public /* synthetic */ _$$Lambda$WindowManagerGlobal$2bR3FsEm4EdRwuXfttH0wA2xOW4(ViewRootImpl viewRootImpl, boolean bl) {
        this.f$0 = viewRootImpl;
        this.f$1 = bl;
    }

    @Override
    public final void run() {
        WindowManagerGlobal.lambda$setStoppedState$0(this.f$0, this.f$1);
    }
}

