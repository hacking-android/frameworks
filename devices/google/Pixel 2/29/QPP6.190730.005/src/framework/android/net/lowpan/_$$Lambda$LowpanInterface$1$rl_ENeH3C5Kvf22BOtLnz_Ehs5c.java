/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import android.net.lowpan.LowpanIdentity;
import android.net.lowpan.LowpanInterface;

public final class _$$Lambda$LowpanInterface$1$rl_ENeH3C5Kvf22BOtLnz_Ehs5c
implements Runnable {
    private final /* synthetic */ LowpanInterface.Callback f$0;
    private final /* synthetic */ LowpanIdentity f$1;

    public /* synthetic */ _$$Lambda$LowpanInterface$1$rl_ENeH3C5Kvf22BOtLnz_Ehs5c(LowpanInterface.Callback callback, LowpanIdentity lowpanIdentity) {
        this.f$0 = callback;
        this.f$1 = lowpanIdentity;
    }

    @Override
    public final void run() {
        LowpanInterface.1.lambda$onLowpanIdentityChanged$5(this.f$0, this.f$1);
    }
}

