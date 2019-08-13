/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsGroupThread;
import android.telephony.ims.RcsParticipantQueryParams;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsGroupThread$X2eY_CkF7PfEGF8QwmaD6Cv0PhI
implements RcsControllerCall.RcsServiceCall {
    private final /* synthetic */ RcsParticipantQueryParams f$0;

    public /* synthetic */ _$$Lambda$RcsGroupThread$X2eY_CkF7PfEGF8QwmaD6Cv0PhI(RcsParticipantQueryParams rcsParticipantQueryParams) {
        this.f$0 = rcsParticipantQueryParams;
    }

    public final Object methodOnIRcs(IRcs iRcs, String string2) {
        return RcsGroupThread.lambda$getParticipants$8(this.f$0, iRcs, string2);
    }
}

