/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.hardware.location.ContextHubClient;
import android.hardware.location.ContextHubClientCallback;
import android.hardware.location.ContextHubManager;

public final class _$$Lambda$ContextHubManager$3$8oeFzBAC_VuH1d32Kod8BVn0Os8
implements Runnable {
    private final /* synthetic */ ContextHubClientCallback f$0;
    private final /* synthetic */ ContextHubClient f$1;
    private final /* synthetic */ long f$2;

    public /* synthetic */ _$$Lambda$ContextHubManager$3$8oeFzBAC_VuH1d32Kod8BVn0Os8(ContextHubClientCallback contextHubClientCallback, ContextHubClient contextHubClient, long l) {
        this.f$0 = contextHubClientCallback;
        this.f$1 = contextHubClient;
        this.f$2 = l;
    }

    @Override
    public final void run() {
        ContextHubManager.3.lambda$onNanoAppEnabled$5(this.f$0, this.f$1, this.f$2);
    }
}

