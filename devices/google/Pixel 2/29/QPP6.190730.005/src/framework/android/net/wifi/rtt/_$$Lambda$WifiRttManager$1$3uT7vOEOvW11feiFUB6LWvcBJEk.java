/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.rtt;

import android.net.wifi.rtt.RangingResultCallback;
import android.net.wifi.rtt.WifiRttManager;
import java.util.List;

public final class _$$Lambda$WifiRttManager$1$3uT7vOEOvW11feiFUB6LWvcBJEk
implements Runnable {
    private final /* synthetic */ RangingResultCallback f$0;
    private final /* synthetic */ List f$1;

    public /* synthetic */ _$$Lambda$WifiRttManager$1$3uT7vOEOvW11feiFUB6LWvcBJEk(RangingResultCallback rangingResultCallback, List list) {
        this.f$0 = rangingResultCallback;
        this.f$1 = list;
    }

    @Override
    public final void run() {
        WifiRttManager.1.lambda$onRangingResults$1(this.f$0, this.f$1);
    }
}

