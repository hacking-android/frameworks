/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.net.wifi.WifiManager;

public final class _$$Lambda$WifiManager$ProvisioningCallbackProxy$0_NXiwyrbrT_579x_6QMO0y3rzc
implements Runnable {
    private final /* synthetic */ WifiManager.ProvisioningCallbackProxy f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$WifiManager$ProvisioningCallbackProxy$0_NXiwyrbrT_579x_6QMO0y3rzc(WifiManager.ProvisioningCallbackProxy provisioningCallbackProxy, int n) {
        this.f$0 = provisioningCallbackProxy;
        this.f$1 = n;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onProvisioningStatus$0$WifiManager$ProvisioningCallbackProxy(this.f$1);
    }
}

