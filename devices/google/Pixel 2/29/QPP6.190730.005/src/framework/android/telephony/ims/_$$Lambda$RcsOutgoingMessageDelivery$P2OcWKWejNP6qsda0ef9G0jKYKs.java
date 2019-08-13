/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsOutgoingMessageDelivery;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsOutgoingMessageDelivery$P2OcWKWejNP6qsda0ef9G0jKYKs
implements RcsControllerCall.RcsServiceCallWithNoReturn {
    private final /* synthetic */ RcsOutgoingMessageDelivery f$0;
    private final /* synthetic */ long f$1;

    public /* synthetic */ _$$Lambda$RcsOutgoingMessageDelivery$P2OcWKWejNP6qsda0ef9G0jKYKs(RcsOutgoingMessageDelivery rcsOutgoingMessageDelivery, long l) {
        this.f$0 = rcsOutgoingMessageDelivery;
        this.f$1 = l;
    }

    @Override
    public final void methodOnIRcs(IRcs iRcs, String string2) {
        this.f$0.lambda$setSeenTimestamp$2$RcsOutgoingMessageDelivery(this.f$1, iRcs, string2);
    }
}

