/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.net.wifi.INetworkRequestUserSelectionCallback;
import android.net.wifi.WifiManager;

public final class _$$Lambda$WifiManager$NetworkRequestMatchCallbackProxy$DYo_nMH0tB37PG_5OviApSTSGXg
implements Runnable {
    private final /* synthetic */ WifiManager.NetworkRequestMatchCallbackProxy f$0;
    private final /* synthetic */ INetworkRequestUserSelectionCallback f$1;

    public /* synthetic */ _$$Lambda$WifiManager$NetworkRequestMatchCallbackProxy$DYo_nMH0tB37PG_5OviApSTSGXg(WifiManager.NetworkRequestMatchCallbackProxy networkRequestMatchCallbackProxy, INetworkRequestUserSelectionCallback iNetworkRequestUserSelectionCallback) {
        this.f$0 = networkRequestMatchCallbackProxy;
        this.f$1 = iNetworkRequestUserSelectionCallback;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onUserSelectionCallbackRegistration$0$WifiManager$NetworkRequestMatchCallbackProxy(this.f$1);
    }
}

