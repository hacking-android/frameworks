/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.ImsMmTelManager;
import android.telephony.ims.ImsReasonInfo;

public final class _$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$q0Uz23ATIYan5EBJYUigIVvwE3g
implements Runnable {
    private final /* synthetic */ ImsMmTelManager.RegistrationCallback.RegistrationBinder f$0;
    private final /* synthetic */ ImsReasonInfo f$1;

    public /* synthetic */ _$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$q0Uz23ATIYan5EBJYUigIVvwE3g(ImsMmTelManager.RegistrationCallback.RegistrationBinder registrationBinder, ImsReasonInfo imsReasonInfo) {
        this.f$0 = registrationBinder;
        this.f$1 = imsReasonInfo;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onDeregistered$4$ImsMmTelManager$RegistrationCallback$RegistrationBinder(this.f$1);
    }
}

