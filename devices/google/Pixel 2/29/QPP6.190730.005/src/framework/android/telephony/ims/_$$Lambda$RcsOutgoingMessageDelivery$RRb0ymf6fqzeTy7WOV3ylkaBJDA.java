/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsOutgoingMessageDelivery;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsOutgoingMessageDelivery$RRb0ymf6fqzeTy7WOV3ylkaBJDA
implements RcsControllerCall.RcsServiceCallWithNoReturn {
    private final /* synthetic */ RcsOutgoingMessageDelivery f$0;
    private final /* synthetic */ long f$1;

    public /* synthetic */ _$$Lambda$RcsOutgoingMessageDelivery$RRb0ymf6fqzeTy7WOV3ylkaBJDA(RcsOutgoingMessageDelivery rcsOutgoingMessageDelivery, long l) {
        this.f$0 = rcsOutgoingMessageDelivery;
        this.f$1 = l;
    }

    @Override
    public final void methodOnIRcs(IRcs iRcs, String string2) {
        this.f$0.lambda$setDeliveredTimestamp$0$RcsOutgoingMessageDelivery(this.f$1, iRcs, string2);
    }
}

