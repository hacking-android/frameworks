/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.net.Uri;
import android.telephony.ims.ImsMmTelManager;
import com.android.internal.util.FunctionalUtils;

public final class _$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$AhnK6VJjwgpDNC1GXRrwfgtYvkM
implements FunctionalUtils.ThrowingRunnable {
    private final /* synthetic */ ImsMmTelManager.RegistrationCallback.RegistrationBinder f$0;
    private final /* synthetic */ Uri[] f$1;

    public /* synthetic */ _$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$AhnK6VJjwgpDNC1GXRrwfgtYvkM(ImsMmTelManager.RegistrationCallback.RegistrationBinder registrationBinder, Uri[] arruri) {
        this.f$0 = registrationBinder;
        this.f$1 = arruri;
    }

    @Override
    public final void runOrThrow() {
        this.f$0.lambda$onSubscriberAssociatedUriChanged$9$ImsMmTelManager$RegistrationCallback$RegistrationBinder(this.f$1);
    }
}

