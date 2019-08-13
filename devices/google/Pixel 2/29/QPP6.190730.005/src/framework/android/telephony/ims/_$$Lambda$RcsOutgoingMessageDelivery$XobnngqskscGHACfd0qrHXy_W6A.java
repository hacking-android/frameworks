/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsOutgoingMessageDelivery;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsOutgoingMessageDelivery$XobnngqskscGHACfd0qrHXy_W6A
implements RcsControllerCall.RcsServiceCall {
    private final /* synthetic */ RcsOutgoingMessageDelivery f$0;

    public /* synthetic */ _$$Lambda$RcsOutgoingMessageDelivery$XobnngqskscGHACfd0qrHXy_W6A(RcsOutgoingMessageDelivery rcsOutgoingMessageDelivery) {
        this.f$0 = rcsOutgoingMessageDelivery;
    }

    public final Object methodOnIRcs(IRcs iRcs, String string2) {
        return this.f$0.lambda$getDeliveredTimestamp$1$RcsOutgoingMessageDelivery(iRcs, string2);
    }
}

