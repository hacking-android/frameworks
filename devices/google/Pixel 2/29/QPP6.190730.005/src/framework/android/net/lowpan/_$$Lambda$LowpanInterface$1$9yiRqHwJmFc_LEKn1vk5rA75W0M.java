/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import android.net.lowpan.LowpanInterface;

public final class _$$Lambda$LowpanInterface$1$9yiRqHwJmFc_LEKn1vk5rA75W0M
implements Runnable {
    private final /* synthetic */ LowpanInterface.Callback f$0;
    private final /* synthetic */ String f$1;

    public /* synthetic */ _$$Lambda$LowpanInterface$1$9yiRqHwJmFc_LEKn1vk5rA75W0M(LowpanInterface.Callback callback, String string2) {
        this.f$0 = callback;
        this.f$1 = string2;
    }

    @Override
    public final void run() {
        LowpanInterface.1.lambda$onRoleChanged$3(this.f$0, this.f$1);
    }
}

