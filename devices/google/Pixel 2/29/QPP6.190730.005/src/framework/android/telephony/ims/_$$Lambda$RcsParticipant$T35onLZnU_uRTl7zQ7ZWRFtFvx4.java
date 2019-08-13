/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsParticipant;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsParticipant$T35onLZnU_uRTl7zQ7ZWRFtFvx4
implements RcsControllerCall.RcsServiceCall {
    private final /* synthetic */ RcsParticipant f$0;

    public /* synthetic */ _$$Lambda$RcsParticipant$T35onLZnU_uRTl7zQ7ZWRFtFvx4(RcsParticipant rcsParticipant) {
        this.f$0 = rcsParticipant;
    }

    public final Object methodOnIRcs(IRcs iRcs, String string2) {
        return this.f$0.lambda$getCanonicalAddress$0$RcsParticipant(iRcs, string2);
    }
}

