/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.ProvisioningManager;

public final class _$$Lambda$ProvisioningManager$Callback$CallbackBinder$rsWuitP9riQDO6nFxj5wJBdYX40
implements Runnable {
    private final /* synthetic */ ProvisioningManager.Callback.CallbackBinder f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ String f$2;

    public /* synthetic */ _$$Lambda$ProvisioningManager$Callback$CallbackBinder$rsWuitP9riQDO6nFxj5wJBdYX40(ProvisioningManager.Callback.CallbackBinder callbackBinder, int n, String string2) {
        this.f$0 = callbackBinder;
        this.f$1 = n;
        this.f$2 = string2;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onStringConfigChanged$2$ProvisioningManager$Callback$CallbackBinder(this.f$1, this.f$2);
    }
}

