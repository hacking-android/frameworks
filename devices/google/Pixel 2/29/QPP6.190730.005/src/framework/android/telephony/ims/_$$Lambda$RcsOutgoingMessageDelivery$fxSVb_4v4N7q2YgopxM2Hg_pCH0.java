/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsOutgoingMessageDelivery;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsOutgoingMessageDelivery$fxSVb_4v4N7q2YgopxM2Hg_pCH0
implements RcsControllerCall.RcsServiceCall {
    private final /* synthetic */ RcsOutgoingMessageDelivery f$0;

    public /* synthetic */ _$$Lambda$RcsOutgoingMessageDelivery$fxSVb_4v4N7q2YgopxM2Hg_pCH0(RcsOutgoingMessageDelivery rcsOutgoingMessageDelivery) {
        this.f$0 = rcsOutgoingMessageDelivery;
    }

    public final Object methodOnIRcs(IRcs iRcs, String string2) {
        return this.f$0.lambda$getSeenTimestamp$3$RcsOutgoingMessageDelivery(iRcs, string2);
    }
}

