/*
 * Decompiled with CFR 0.145.
 */
package android.companion;

import android.companion.CompanionDeviceManager;
import java.util.function.BiConsumer;

public final class _$$Lambda$CompanionDeviceManager$CallbackProxy$gkUVA3m3QgEEk8G84_kcBFARHvo
implements Runnable {
    private final /* synthetic */ CompanionDeviceManager.CallbackProxy f$0;
    private final /* synthetic */ BiConsumer f$1;
    private final /* synthetic */ Object f$2;

    public /* synthetic */ _$$Lambda$CompanionDeviceManager$CallbackProxy$gkUVA3m3QgEEk8G84_kcBFARHvo(CompanionDeviceManager.CallbackProxy callbackProxy, BiConsumer biConsumer, Object object) {
        this.f$0 = callbackProxy;
        this.f$1 = biConsumer;
        this.f$2 = object;
    }

    @Override
    public final void run() {
        this.f$0.lambda$lockAndPost$0$CompanionDeviceManager$CallbackProxy(this.f$1, this.f$2);
    }
}

