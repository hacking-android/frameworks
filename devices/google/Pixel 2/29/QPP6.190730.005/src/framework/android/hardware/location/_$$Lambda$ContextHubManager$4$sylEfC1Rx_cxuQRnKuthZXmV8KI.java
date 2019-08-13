/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.hardware.location.ContextHubManager;
import android.hardware.location.ContextHubMessage;

public final class _$$Lambda$ContextHubManager$4$sylEfC1Rx_cxuQRnKuthZXmV8KI
implements Runnable {
    private final /* synthetic */ ContextHubManager.4 f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ int f$2;
    private final /* synthetic */ ContextHubMessage f$3;

    public /* synthetic */ _$$Lambda$ContextHubManager$4$sylEfC1Rx_cxuQRnKuthZXmV8KI(ContextHubManager.4 var1_1, int n, int n2, ContextHubMessage contextHubMessage) {
        this.f$0 = var1_1;
        this.f$1 = n;
        this.f$2 = n2;
        this.f$3 = contextHubMessage;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onMessageReceipt$0$ContextHubManager$4(this.f$1, this.f$2, this.f$3);
    }
}

