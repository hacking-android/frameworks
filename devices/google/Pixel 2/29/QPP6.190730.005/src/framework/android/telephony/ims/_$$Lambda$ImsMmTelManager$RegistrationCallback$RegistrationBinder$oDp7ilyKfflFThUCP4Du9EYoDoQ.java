/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.ImsMmTelManager;

public final class _$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$oDp7ilyKfflFThUCP4Du9EYoDoQ
implements Runnable {
    private final /* synthetic */ ImsMmTelManager.RegistrationCallback.RegistrationBinder f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$oDp7ilyKfflFThUCP4Du9EYoDoQ(ImsMmTelManager.RegistrationCallback.RegistrationBinder registrationBinder, int n) {
        this.f$0 = registrationBinder;
        this.f$1 = n;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onRegistered$0$ImsMmTelManager$RegistrationCallback$RegistrationBinder(this.f$1);
    }
}

