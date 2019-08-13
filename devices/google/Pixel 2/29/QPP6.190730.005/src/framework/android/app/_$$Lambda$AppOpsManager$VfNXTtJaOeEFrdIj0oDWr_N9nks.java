/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.AppOpsManager;
import java.util.function.Consumer;

public final class _$$Lambda$AppOpsManager$VfNXTtJaOeEFrdIj0oDWr_N9nks
implements Runnable {
    private final /* synthetic */ Consumer f$0;
    private final /* synthetic */ AppOpsManager.HistoricalOps f$1;

    public /* synthetic */ _$$Lambda$AppOpsManager$VfNXTtJaOeEFrdIj0oDWr_N9nks(Consumer consumer, AppOpsManager.HistoricalOps historicalOps) {
        this.f$0 = consumer;
        this.f$1 = historicalOps;
    }

    @Override
    public final void run() {
        AppOpsManager.lambda$getHistoricalOpsFromDiskRaw$2(this.f$0, this.f$1);
    }
}

