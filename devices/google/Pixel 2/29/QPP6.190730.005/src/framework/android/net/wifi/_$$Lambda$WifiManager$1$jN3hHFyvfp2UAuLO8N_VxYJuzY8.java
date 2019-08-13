/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.net.wifi.WifiManager;
import android.net.wifi.WifiUsabilityStatsEntry;

public final class _$$Lambda$WifiManager$1$jN3hHFyvfp2UAuLO8N_VxYJuzY8
implements Runnable {
    private final /* synthetic */ WifiManager.OnWifiUsabilityStatsListener f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ boolean f$2;
    private final /* synthetic */ WifiUsabilityStatsEntry f$3;

    public /* synthetic */ _$$Lambda$WifiManager$1$jN3hHFyvfp2UAuLO8N_VxYJuzY8(WifiManager.OnWifiUsabilityStatsListener onWifiUsabilityStatsListener, int n, boolean bl, WifiUsabilityStatsEntry wifiUsabilityStatsEntry) {
        this.f$0 = onWifiUsabilityStatsListener;
        this.f$1 = n;
        this.f$2 = bl;
        this.f$3 = wifiUsabilityStatsEntry;
    }

    @Override
    public final void run() {
        WifiManager.1.lambda$onWifiUsabilityStats$0(this.f$0, this.f$1, this.f$2, this.f$3);
    }
}

