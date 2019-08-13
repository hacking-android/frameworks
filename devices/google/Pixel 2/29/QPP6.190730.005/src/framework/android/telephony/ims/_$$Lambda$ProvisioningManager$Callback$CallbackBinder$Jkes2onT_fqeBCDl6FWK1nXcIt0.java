/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.ProvisioningManager;
import com.android.internal.util.FunctionalUtils;

public final class _$$Lambda$ProvisioningManager$Callback$CallbackBinder$Jkes2onT_fqeBCDl6FWK1nXcIt0
implements FunctionalUtils.ThrowingRunnable {
    private final /* synthetic */ ProvisioningManager.Callback.CallbackBinder f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ String f$2;

    public /* synthetic */ _$$Lambda$ProvisioningManager$Callback$CallbackBinder$Jkes2onT_fqeBCDl6FWK1nXcIt0(ProvisioningManager.Callback.CallbackBinder callbackBinder, int n, String string2) {
        this.f$0 = callbackBinder;
        this.f$1 = n;
        this.f$2 = string2;
    }

    @Override
    public final void runOrThrow() {
        this.f$0.lambda$onStringConfigChanged$3$ProvisioningManager$Callback$CallbackBinder(this.f$1, this.f$2);
    }
}

