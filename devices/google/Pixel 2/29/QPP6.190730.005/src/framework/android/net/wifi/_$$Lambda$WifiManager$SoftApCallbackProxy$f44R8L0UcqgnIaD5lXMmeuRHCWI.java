/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.net.wifi.WifiManager;

public final class _$$Lambda$WifiManager$SoftApCallbackProxy$f44R8L0UcqgnIaD5lXMmeuRHCWI
implements Runnable {
    private final /* synthetic */ WifiManager.SoftApCallbackProxy f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$WifiManager$SoftApCallbackProxy$f44R8L0UcqgnIaD5lXMmeuRHCWI(WifiManager.SoftApCallbackProxy softApCallbackProxy, int n) {
        this.f$0 = softApCallbackProxy;
        this.f$1 = n;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onNumClientsChanged$1$WifiManager$SoftApCallbackProxy(this.f$1);
    }
}

