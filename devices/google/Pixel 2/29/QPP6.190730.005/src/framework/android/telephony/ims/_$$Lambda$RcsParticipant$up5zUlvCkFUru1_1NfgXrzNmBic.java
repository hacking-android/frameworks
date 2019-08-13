/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsParticipant;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsParticipant$up5zUlvCkFUru1_1NfgXrzNmBic
implements RcsControllerCall.RcsServiceCall {
    private final /* synthetic */ RcsParticipant f$0;

    public /* synthetic */ _$$Lambda$RcsParticipant$up5zUlvCkFUru1_1NfgXrzNmBic(RcsParticipant rcsParticipant) {
        this.f$0 = rcsParticipant;
    }

    public final Object methodOnIRcs(IRcs iRcs, String string2) {
        return this.f$0.lambda$getContactId$3$RcsParticipant(iRcs, string2);
    }
}

