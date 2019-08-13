/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.net.wifi.WifiManager;

public final class _$$Lambda$WifiManager$SoftApCallbackProxy$vmSW5veUpC52oRINBy419US5snk
implements Runnable {
    private final /* synthetic */ WifiManager.SoftApCallbackProxy f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ int f$2;

    public /* synthetic */ _$$Lambda$WifiManager$SoftApCallbackProxy$vmSW5veUpC52oRINBy419US5snk(WifiManager.SoftApCallbackProxy softApCallbackProxy, int n, int n2) {
        this.f$0 = softApCallbackProxy;
        this.f$1 = n;
        this.f$2 = n2;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onStateChanged$0$WifiManager$SoftApCallbackProxy(this.f$1, this.f$2);
    }
}

