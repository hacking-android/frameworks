/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import android.net.lowpan.LowpanInterface;

public final class _$$Lambda$LowpanInterface$1$a1rvbSIFSC6J5j7aKUf1ekbmIIA
implements Runnable {
    private final /* synthetic */ LowpanInterface.Callback f$0;
    private final /* synthetic */ boolean f$1;

    public /* synthetic */ _$$Lambda$LowpanInterface$1$a1rvbSIFSC6J5j7aKUf1ekbmIIA(LowpanInterface.Callback callback, boolean bl) {
        this.f$0 = callback;
        this.f$1 = bl;
    }

    @Override
    public final void run() {
        LowpanInterface.1.lambda$onUpChanged$2(this.f$0, this.f$1);
    }
}

