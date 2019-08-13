/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsParticipant;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsParticipant$xir_e_NE3auWDac4dOx89mKtRKU
implements RcsControllerCall.RcsServiceCallWithNoReturn {
    private final /* synthetic */ RcsParticipant f$0;
    private final /* synthetic */ String f$1;

    public /* synthetic */ _$$Lambda$RcsParticipant$xir_e_NE3auWDac4dOx89mKtRKU(RcsParticipant rcsParticipant, String string2) {
        this.f$0 = rcsParticipant;
        this.f$1 = string2;
    }

    @Override
    public final void methodOnIRcs(IRcs iRcs, String string2) {
        this.f$0.lambda$setAlias$2$RcsParticipant(this.f$1, iRcs, string2);
    }
}

