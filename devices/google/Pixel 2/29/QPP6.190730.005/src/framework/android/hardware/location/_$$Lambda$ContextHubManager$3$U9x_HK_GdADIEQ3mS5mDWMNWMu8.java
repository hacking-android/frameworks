/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.hardware.location.ContextHubClient;
import android.hardware.location.ContextHubClientCallback;
import android.hardware.location.ContextHubManager;
import android.hardware.location.NanoAppMessage;

public final class _$$Lambda$ContextHubManager$3$U9x_HK_GdADIEQ3mS5mDWMNWMu8
implements Runnable {
    private final /* synthetic */ ContextHubClientCallback f$0;
    private final /* synthetic */ ContextHubClient f$1;
    private final /* synthetic */ NanoAppMessage f$2;

    public /* synthetic */ _$$Lambda$ContextHubManager$3$U9x_HK_GdADIEQ3mS5mDWMNWMu8(ContextHubClientCallback contextHubClientCallback, ContextHubClient contextHubClient, NanoAppMessage nanoAppMessage) {
        this.f$0 = contextHubClientCallback;
        this.f$1 = contextHubClient;
        this.f$2 = nanoAppMessage;
    }

    @Override
    public final void run() {
        ContextHubManager.3.lambda$onMessageFromNanoApp$0(this.f$0, this.f$1, this.f$2);
    }
}

