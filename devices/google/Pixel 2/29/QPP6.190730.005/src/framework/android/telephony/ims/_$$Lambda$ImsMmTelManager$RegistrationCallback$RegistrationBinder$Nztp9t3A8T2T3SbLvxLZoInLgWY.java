/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.ImsMmTelManager;
import android.telephony.ims.ImsReasonInfo;
import com.android.internal.util.FunctionalUtils;

public final class _$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$Nztp9t3A8T2T3SbLvxLZoInLgWY
implements FunctionalUtils.ThrowingRunnable {
    private final /* synthetic */ ImsMmTelManager.RegistrationCallback.RegistrationBinder f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ ImsReasonInfo f$2;

    public /* synthetic */ _$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$Nztp9t3A8T2T3SbLvxLZoInLgWY(ImsMmTelManager.RegistrationCallback.RegistrationBinder registrationBinder, int n, ImsReasonInfo imsReasonInfo) {
        this.f$0 = registrationBinder;
        this.f$1 = n;
        this.f$2 = imsReasonInfo;
    }

    @Override
    public final void runOrThrow() {
        this.f$0.lambda$onTechnologyChangeFailed$7$ImsMmTelManager$RegistrationCallback$RegistrationBinder(this.f$1, this.f$2);
    }
}

