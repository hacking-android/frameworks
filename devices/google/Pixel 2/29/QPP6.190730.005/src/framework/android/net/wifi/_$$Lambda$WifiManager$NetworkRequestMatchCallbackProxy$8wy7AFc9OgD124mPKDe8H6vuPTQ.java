/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.net.wifi.WifiManager;
import java.util.List;

public final class _$$Lambda$WifiManager$NetworkRequestMatchCallbackProxy$8wy7AFc9OgD124mPKDe8H6vuPTQ
implements Runnable {
    private final /* synthetic */ WifiManager.NetworkRequestMatchCallbackProxy f$0;
    private final /* synthetic */ List f$1;

    public /* synthetic */ _$$Lambda$WifiManager$NetworkRequestMatchCallbackProxy$8wy7AFc9OgD124mPKDe8H6vuPTQ(WifiManager.NetworkRequestMatchCallbackProxy networkRequestMatchCallbackProxy, List list) {
        this.f$0 = networkRequestMatchCallbackProxy;
        this.f$1 = list;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onMatch$2$WifiManager$NetworkRequestMatchCallbackProxy(this.f$1);
    }
}

