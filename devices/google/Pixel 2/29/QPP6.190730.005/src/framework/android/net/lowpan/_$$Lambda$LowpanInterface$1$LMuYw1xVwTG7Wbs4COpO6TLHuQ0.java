/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import android.net.lowpan.LowpanInterface;

public final class _$$Lambda$LowpanInterface$1$LMuYw1xVwTG7Wbs4COpO6TLHuQ0
implements Runnable {
    private final /* synthetic */ LowpanInterface.Callback f$0;
    private final /* synthetic */ boolean f$1;

    public /* synthetic */ _$$Lambda$LowpanInterface$1$LMuYw1xVwTG7Wbs4COpO6TLHuQ0(LowpanInterface.Callback callback, boolean bl) {
        this.f$0 = callback;
        this.f$1 = bl;
    }

    @Override
    public final void run() {
        LowpanInterface.1.lambda$onEnabledChanged$0(this.f$0, this.f$1);
    }
}

