/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.ImsMmTelManager;
import android.telephony.ims.ImsReasonInfo;

public final class _$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$IeNlpXTAPM2z2VxFA81E0v9udZw
implements Runnable {
    private final /* synthetic */ ImsMmTelManager.RegistrationCallback.RegistrationBinder f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ ImsReasonInfo f$2;

    public /* synthetic */ _$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$IeNlpXTAPM2z2VxFA81E0v9udZw(ImsMmTelManager.RegistrationCallback.RegistrationBinder registrationBinder, int n, ImsReasonInfo imsReasonInfo) {
        this.f$0 = registrationBinder;
        this.f$1 = n;
        this.f$2 = imsReasonInfo;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onTechnologyChangeFailed$6$ImsMmTelManager$RegistrationCallback$RegistrationBinder(this.f$1, this.f$2);
    }
}

