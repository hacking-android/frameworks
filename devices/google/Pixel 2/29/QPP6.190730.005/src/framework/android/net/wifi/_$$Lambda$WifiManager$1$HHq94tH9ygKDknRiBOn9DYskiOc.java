/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.net.wifi.WifiManager;
import android.net.wifi.WifiUsabilityStatsEntry;
import com.android.internal.util.FunctionalUtils;
import java.util.concurrent.Executor;

public final class _$$Lambda$WifiManager$1$HHq94tH9ygKDknRiBOn9DYskiOc
implements FunctionalUtils.ThrowingRunnable {
    private final /* synthetic */ Executor f$0;
    private final /* synthetic */ WifiManager.OnWifiUsabilityStatsListener f$1;
    private final /* synthetic */ int f$2;
    private final /* synthetic */ boolean f$3;
    private final /* synthetic */ WifiUsabilityStatsEntry f$4;

    public /* synthetic */ _$$Lambda$WifiManager$1$HHq94tH9ygKDknRiBOn9DYskiOc(Executor executor, WifiManager.OnWifiUsabilityStatsListener onWifiUsabilityStatsListener, int n, boolean bl, WifiUsabilityStatsEntry wifiUsabilityStatsEntry) {
        this.f$0 = executor;
        this.f$1 = onWifiUsabilityStatsListener;
        this.f$2 = n;
        this.f$3 = bl;
        this.f$4 = wifiUsabilityStatsEntry;
    }

    @Override
    public final void runOrThrow() {
        WifiManager.1.lambda$onWifiUsabilityStats$1(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4);
    }
}

