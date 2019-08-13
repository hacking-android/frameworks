/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.net.wifi.WifiManager;

public final class _$$Lambda$WifiManager$EasyConnectCallbackProxy$ObU39aoKguVIx_qQTyZyomhDAAg
implements Runnable {
    private final /* synthetic */ WifiManager.EasyConnectCallbackProxy f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$WifiManager$EasyConnectCallbackProxy$ObU39aoKguVIx_qQTyZyomhDAAg(WifiManager.EasyConnectCallbackProxy easyConnectCallbackProxy, int n) {
        this.f$0 = easyConnectCallbackProxy;
        this.f$1 = n;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onSuccessConfigReceived$0$WifiManager$EasyConnectCallbackProxy(this.f$1);
    }
}

