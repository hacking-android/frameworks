/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

public final class _$$Lambda$WifiManager$NetworkRequestMatchCallbackProxy$MJqaBvGtvUfHUJtjhgTRIQ7GCr4
implements Runnable {
    private final /* synthetic */ WifiManager.NetworkRequestMatchCallbackProxy f$0;
    private final /* synthetic */ WifiConfiguration f$1;

    public /* synthetic */ _$$Lambda$WifiManager$NetworkRequestMatchCallbackProxy$MJqaBvGtvUfHUJtjhgTRIQ7GCr4(WifiManager.NetworkRequestMatchCallbackProxy networkRequestMatchCallbackProxy, WifiConfiguration wifiConfiguration) {
        this.f$0 = networkRequestMatchCallbackProxy;
        this.f$1 = wifiConfiguration;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onUserSelectionConnectFailure$4$WifiManager$NetworkRequestMatchCallbackProxy(this.f$1);
    }
}

