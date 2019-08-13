/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.rtt;

import android.net.wifi.rtt.RangingResultCallback;
import android.net.wifi.rtt.WifiRttManager;

public final class _$$Lambda$WifiRttManager$1$j3tVizFtxt_z0tTXfTNSFM4Loi8
implements Runnable {
    private final /* synthetic */ RangingResultCallback f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$WifiRttManager$1$j3tVizFtxt_z0tTXfTNSFM4Loi8(RangingResultCallback rangingResultCallback, int n) {
        this.f$0 = rangingResultCallback;
        this.f$1 = n;
    }

    @Override
    public final void run() {
        WifiRttManager.1.lambda$onRangingFailure$0(this.f$0, this.f$1);
    }
}

