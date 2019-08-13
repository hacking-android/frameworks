/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.ProvisioningManager;

public final class _$$Lambda$ProvisioningManager$Callback$CallbackBinder$R_8jXQuOM7aV7dIwYBzcWwV_YpM
implements Runnable {
    private final /* synthetic */ ProvisioningManager.Callback.CallbackBinder f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ int f$2;

    public /* synthetic */ _$$Lambda$ProvisioningManager$Callback$CallbackBinder$R_8jXQuOM7aV7dIwYBzcWwV_YpM(ProvisioningManager.Callback.CallbackBinder callbackBinder, int n, int n2) {
        this.f$0 = callbackBinder;
        this.f$1 = n;
        this.f$2 = n2;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onIntConfigChanged$0$ProvisioningManager$Callback$CallbackBinder(this.f$1, this.f$2);
    }
}

