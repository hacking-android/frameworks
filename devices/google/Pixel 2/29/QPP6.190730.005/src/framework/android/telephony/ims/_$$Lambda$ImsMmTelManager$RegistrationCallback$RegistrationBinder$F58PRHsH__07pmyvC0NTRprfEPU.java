/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.ImsMmTelManager;
import android.telephony.ims.ImsReasonInfo;
import com.android.internal.util.FunctionalUtils;

public final class _$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$F58PRHsH__07pmyvC0NTRprfEPU
implements FunctionalUtils.ThrowingRunnable {
    private final /* synthetic */ ImsMmTelManager.RegistrationCallback.RegistrationBinder f$0;
    private final /* synthetic */ ImsReasonInfo f$1;

    public /* synthetic */ _$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$F58PRHsH__07pmyvC0NTRprfEPU(ImsMmTelManager.RegistrationCallback.RegistrationBinder registrationBinder, ImsReasonInfo imsReasonInfo) {
        this.f$0 = registrationBinder;
        this.f$1 = imsReasonInfo;
    }

    @Override
    public final void runOrThrow() {
        this.f$0.lambda$onDeregistered$5$ImsMmTelManager$RegistrationCallback$RegistrationBinder(this.f$1);
    }
}

