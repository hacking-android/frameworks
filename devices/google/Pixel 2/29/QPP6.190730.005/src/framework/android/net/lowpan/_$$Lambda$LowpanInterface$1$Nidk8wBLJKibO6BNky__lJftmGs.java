/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import android.net.lowpan.LowpanInterface;

public final class _$$Lambda$LowpanInterface$1$Nidk8wBLJKibO6BNky__lJftmGs
implements Runnable {
    private final /* synthetic */ LowpanInterface.Callback f$0;
    private final /* synthetic */ boolean f$1;

    public /* synthetic */ _$$Lambda$LowpanInterface$1$Nidk8wBLJKibO6BNky__lJftmGs(LowpanInterface.Callback callback, boolean bl) {
        this.f$0 = callback;
        this.f$1 = bl;
    }

    @Override
    public final void run() {
        LowpanInterface.1.lambda$onConnectedChanged$1(this.f$0, this.f$1);
    }
}

