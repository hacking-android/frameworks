/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.VrManager;

public final class _$$Lambda$VrManager$CallbackEntry$2$KvHLIXm3_7igcOqTEl46YdjhHMk
implements Runnable {
    private final /* synthetic */ VrManager.CallbackEntry.2 f$0;
    private final /* synthetic */ boolean f$1;

    public /* synthetic */ _$$Lambda$VrManager$CallbackEntry$2$KvHLIXm3_7igcOqTEl46YdjhHMk(VrManager.CallbackEntry.2 var1_1, boolean bl) {
        this.f$0 = var1_1;
        this.f$1 = bl;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onPersistentVrStateChanged$0$VrManager$CallbackEntry$2(this.f$1);
    }
}

