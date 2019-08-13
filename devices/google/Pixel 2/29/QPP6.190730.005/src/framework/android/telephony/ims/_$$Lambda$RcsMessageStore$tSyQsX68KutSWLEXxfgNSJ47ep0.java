/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsMessageStore;
import android.telephony.ims.RcsQueryContinuationToken;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsMessageStore$tSyQsX68KutSWLEXxfgNSJ47ep0
implements RcsControllerCall.RcsServiceCall {
    private final /* synthetic */ RcsQueryContinuationToken f$0;

    public /* synthetic */ _$$Lambda$RcsMessageStore$tSyQsX68KutSWLEXxfgNSJ47ep0(RcsQueryContinuationToken rcsQueryContinuationToken) {
        this.f$0 = rcsQueryContinuationToken;
    }

    public final Object methodOnIRcs(IRcs iRcs, String string2) {
        return RcsMessageStore.lambda$getRcsParticipants$3(this.f$0, iRcs, string2);
    }
}

