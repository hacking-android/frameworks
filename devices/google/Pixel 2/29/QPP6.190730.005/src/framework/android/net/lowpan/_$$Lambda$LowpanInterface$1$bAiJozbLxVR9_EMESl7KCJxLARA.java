/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import android.net.LinkAddress;
import android.net.lowpan.LowpanInterface;

public final class _$$Lambda$LowpanInterface$1$bAiJozbLxVR9_EMESl7KCJxLARA
implements Runnable {
    private final /* synthetic */ LowpanInterface.Callback f$0;
    private final /* synthetic */ LinkAddress f$1;

    public /* synthetic */ _$$Lambda$LowpanInterface$1$bAiJozbLxVR9_EMESl7KCJxLARA(LowpanInterface.Callback callback, LinkAddress linkAddress) {
        this.f$0 = callback;
        this.f$1 = linkAddress;
    }

    @Override
    public final void run() {
        LowpanInterface.1.lambda$onLinkAddressRemoved$9(this.f$0, this.f$1);
    }
}

