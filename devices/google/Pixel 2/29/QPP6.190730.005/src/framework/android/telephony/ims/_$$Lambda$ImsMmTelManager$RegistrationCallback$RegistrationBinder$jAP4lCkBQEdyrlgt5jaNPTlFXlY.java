/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.net.Uri;
import android.telephony.ims.ImsMmTelManager;

public final class _$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$jAP4lCkBQEdyrlgt5jaNPTlFXlY
implements Runnable {
    private final /* synthetic */ ImsMmTelManager.RegistrationCallback.RegistrationBinder f$0;
    private final /* synthetic */ Uri[] f$1;

    public /* synthetic */ _$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$jAP4lCkBQEdyrlgt5jaNPTlFXlY(ImsMmTelManager.RegistrationCallback.RegistrationBinder registrationBinder, Uri[] arruri) {
        this.f$0 = registrationBinder;
        this.f$1 = arruri;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onSubscriberAssociatedUriChanged$8$ImsMmTelManager$RegistrationCallback$RegistrationBinder(this.f$1);
    }
}

