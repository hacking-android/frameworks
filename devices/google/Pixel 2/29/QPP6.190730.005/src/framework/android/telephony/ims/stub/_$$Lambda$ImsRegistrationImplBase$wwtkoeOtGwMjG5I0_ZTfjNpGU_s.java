/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims.stub;

import android.net.Uri;
import android.telephony.ims.aidl.IImsRegistrationCallback;
import android.telephony.ims.stub.ImsRegistrationImplBase;
import java.util.function.Consumer;

public final class _$$Lambda$ImsRegistrationImplBase$wwtkoeOtGwMjG5I0_ZTfjNpGU_s
implements Consumer {
    private final /* synthetic */ Uri[] f$0;

    public /* synthetic */ _$$Lambda$ImsRegistrationImplBase$wwtkoeOtGwMjG5I0_ZTfjNpGU_s(Uri[] arruri) {
        this.f$0 = arruri;
    }

    public final void accept(Object object) {
        ImsRegistrationImplBase.lambda$onSubscriberAssociatedUriChanged$4(this.f$0, (IImsRegistrationCallback)object);
    }
}

