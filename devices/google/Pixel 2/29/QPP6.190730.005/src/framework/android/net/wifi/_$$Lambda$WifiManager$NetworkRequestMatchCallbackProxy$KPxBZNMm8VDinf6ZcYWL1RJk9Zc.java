/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

public final class _$$Lambda$WifiManager$NetworkRequestMatchCallbackProxy$KPxBZNMm8VDinf6ZcYWL1RJk9Zc
implements Runnable {
    private final /* synthetic */ WifiManager.NetworkRequestMatchCallbackProxy f$0;
    private final /* synthetic */ WifiConfiguration f$1;

    public /* synthetic */ _$$Lambda$WifiManager$NetworkRequestMatchCallbackProxy$KPxBZNMm8VDinf6ZcYWL1RJk9Zc(WifiManager.NetworkRequestMatchCallbackProxy networkRequestMatchCallbackProxy, WifiConfiguration wifiConfiguration) {
        this.f$0 = networkRequestMatchCallbackProxy;
        this.f$1 = wifiConfiguration;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onUserSelectionConnectSuccess$3$WifiManager$NetworkRequestMatchCallbackProxy(this.f$1);
    }
}

