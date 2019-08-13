/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.VrManager;

public final class _$$Lambda$VrManager$CallbackEntry$1$rgUBVVG1QhelpvAp8W3UQHDHJdU
implements Runnable {
    private final /* synthetic */ VrManager.CallbackEntry.1 f$0;
    private final /* synthetic */ boolean f$1;

    public /* synthetic */ _$$Lambda$VrManager$CallbackEntry$1$rgUBVVG1QhelpvAp8W3UQHDHJdU(VrManager.CallbackEntry.1 var1_1, boolean bl) {
        this.f$0 = var1_1;
        this.f$1 = bl;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onVrStateChanged$0$VrManager$CallbackEntry$1(this.f$1);
    }
}

