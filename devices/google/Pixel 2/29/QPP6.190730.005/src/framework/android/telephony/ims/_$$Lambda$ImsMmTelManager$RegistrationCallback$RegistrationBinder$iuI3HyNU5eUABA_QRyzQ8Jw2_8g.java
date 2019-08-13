/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.ImsMmTelManager;
import com.android.internal.util.FunctionalUtils;

public final class _$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$iuI3HyNU5eUABA_QRyzQ8Jw2_8g
implements FunctionalUtils.ThrowingRunnable {
    private final /* synthetic */ ImsMmTelManager.RegistrationCallback.RegistrationBinder f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$iuI3HyNU5eUABA_QRyzQ8Jw2_8g(ImsMmTelManager.RegistrationCallback.RegistrationBinder registrationBinder, int n) {
        this.f$0 = registrationBinder;
        this.f$1 = n;
    }

    @Override
    public final void runOrThrow() {
        this.f$0.lambda$onRegistering$3$ImsMmTelManager$RegistrationCallback$RegistrationBinder(this.f$1);
    }
}

