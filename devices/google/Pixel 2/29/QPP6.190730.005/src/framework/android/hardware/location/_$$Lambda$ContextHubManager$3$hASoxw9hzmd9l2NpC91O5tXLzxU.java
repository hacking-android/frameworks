/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.hardware.location.ContextHubClient;
import android.hardware.location.ContextHubClientCallback;
import android.hardware.location.ContextHubManager;

public final class _$$Lambda$ContextHubManager$3$hASoxw9hzmd9l2NpC91O5tXLzxU
implements Runnable {
    private final /* synthetic */ ContextHubClientCallback f$0;
    private final /* synthetic */ ContextHubClient f$1;
    private final /* synthetic */ long f$2;
    private final /* synthetic */ int f$3;

    public /* synthetic */ _$$Lambda$ContextHubManager$3$hASoxw9hzmd9l2NpC91O5tXLzxU(ContextHubClientCallback contextHubClientCallback, ContextHubClient contextHubClient, long l, int n) {
        this.f$0 = contextHubClientCallback;
        this.f$1 = contextHubClient;
        this.f$2 = l;
        this.f$3 = n;
    }

    @Override
    public final void run() {
        ContextHubManager.3.lambda$onNanoAppAborted$2(this.f$0, this.f$1, this.f$2, this.f$3);
    }
}

