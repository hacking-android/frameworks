/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import android.net.lowpan.ILowpanInterface;
import android.net.lowpan.LowpanManager;

public final class _$$Lambda$LowpanManager$2$2qKIy18LeIjTlm4mROg_pHOPNU0
implements Runnable {
    private final /* synthetic */ LowpanManager.2 f$0;
    private final /* synthetic */ ILowpanInterface f$1;
    private final /* synthetic */ LowpanManager.Callback f$2;

    public /* synthetic */ _$$Lambda$LowpanManager$2$2qKIy18LeIjTlm4mROg_pHOPNU0(LowpanManager.2 var1_1, ILowpanInterface iLowpanInterface, LowpanManager.Callback callback) {
        this.f$0 = var1_1;
        this.f$1 = iLowpanInterface;
        this.f$2 = callback;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onInterfaceAdded$0$LowpanManager$2(this.f$1, this.f$2);
    }
}

