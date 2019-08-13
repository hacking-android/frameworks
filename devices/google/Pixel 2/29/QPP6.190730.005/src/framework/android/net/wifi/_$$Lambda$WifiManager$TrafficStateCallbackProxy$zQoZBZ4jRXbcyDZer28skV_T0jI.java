/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.net.wifi.WifiManager;

public final class _$$Lambda$WifiManager$TrafficStateCallbackProxy$zQoZBZ4jRXbcyDZer28skV_T0jI
implements Runnable {
    private final /* synthetic */ WifiManager.TrafficStateCallbackProxy f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$WifiManager$TrafficStateCallbackProxy$zQoZBZ4jRXbcyDZer28skV_T0jI(WifiManager.TrafficStateCallbackProxy trafficStateCallbackProxy, int n) {
        this.f$0 = trafficStateCallbackProxy;
        this.f$1 = n;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onStateChanged$0$WifiManager$TrafficStateCallbackProxy(this.f$1);
    }
}

