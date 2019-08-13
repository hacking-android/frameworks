/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.IpSecTransform;

public final class _$$Lambda$IpSecTransform$1$_ae2VrMToKvertNlEIezU0bdvXE
implements Runnable {
    private final /* synthetic */ IpSecTransform.1 f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$IpSecTransform$1$_ae2VrMToKvertNlEIezU0bdvXE(IpSecTransform.1 var1_1, int n) {
        this.f$0 = var1_1;
        this.f$1 = n;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onError$2$IpSecTransform$1(this.f$1);
    }
}

