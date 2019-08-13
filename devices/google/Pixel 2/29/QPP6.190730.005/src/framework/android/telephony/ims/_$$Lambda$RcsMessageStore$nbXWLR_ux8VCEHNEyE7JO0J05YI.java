/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsMessageStore;
import android.telephony.ims.RcsThread;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsMessageStore$nbXWLR_ux8VCEHNEyE7JO0J05YI
implements RcsControllerCall.RcsServiceCall {
    private final /* synthetic */ RcsThread f$0;

    public /* synthetic */ _$$Lambda$RcsMessageStore$nbXWLR_ux8VCEHNEyE7JO0J05YI(RcsThread rcsThread) {
        this.f$0 = rcsThread;
    }

    public final Object methodOnIRcs(IRcs iRcs, String string2) {
        return RcsMessageStore.lambda$deleteThread$10(this.f$0, iRcs, string2);
    }
}

