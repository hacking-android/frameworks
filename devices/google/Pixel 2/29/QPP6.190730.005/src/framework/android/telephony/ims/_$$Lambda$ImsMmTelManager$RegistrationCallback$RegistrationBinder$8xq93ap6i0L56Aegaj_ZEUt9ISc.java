/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.ImsMmTelManager;
import com.android.internal.util.FunctionalUtils;

public final class _$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$8xq93ap6i0L56Aegaj_ZEUt9ISc
implements FunctionalUtils.ThrowingRunnable {
    private final /* synthetic */ ImsMmTelManager.RegistrationCallback.RegistrationBinder f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$8xq93ap6i0L56Aegaj_ZEUt9ISc(ImsMmTelManager.RegistrationCallback.RegistrationBinder registrationBinder, int n) {
        this.f$0 = registrationBinder;
        this.f$1 = n;
    }

    @Override
    public final void runOrThrow() {
        this.f$0.lambda$onRegistered$1$ImsMmTelManager$RegistrationCallback$RegistrationBinder(this.f$1);
    }
}

