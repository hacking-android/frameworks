/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.hardware.location.ContextHubClient;
import android.hardware.location.ContextHubClientCallback;
import android.hardware.location.ContextHubManager;

public final class _$$Lambda$ContextHubManager$3$kLhhBRChCeue1LKohd5lK_lfKTU
implements Runnable {
    private final /* synthetic */ ContextHubClientCallback f$0;
    private final /* synthetic */ ContextHubClient f$1;

    public /* synthetic */ _$$Lambda$ContextHubManager$3$kLhhBRChCeue1LKohd5lK_lfKTU(ContextHubClientCallback contextHubClientCallback, ContextHubClient contextHubClient) {
        this.f$0 = contextHubClientCallback;
        this.f$1 = contextHubClient;
    }

    @Override
    public final void run() {
        ContextHubManager.3.lambda$onHubReset$1(this.f$0, this.f$1);
    }
}

