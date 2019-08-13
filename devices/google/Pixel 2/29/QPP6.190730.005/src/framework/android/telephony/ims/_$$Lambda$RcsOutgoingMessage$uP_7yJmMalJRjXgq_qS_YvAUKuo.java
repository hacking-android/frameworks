/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsOutgoingMessage;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsOutgoingMessage$uP_7yJmMalJRjXgq_qS_YvAUKuo
implements RcsControllerCall.RcsServiceCall {
    private final /* synthetic */ RcsOutgoingMessage f$0;

    public /* synthetic */ _$$Lambda$RcsOutgoingMessage$uP_7yJmMalJRjXgq_qS_YvAUKuo(RcsOutgoingMessage rcsOutgoingMessage) {
        this.f$0 = rcsOutgoingMessage;
    }

    public final Object methodOnIRcs(IRcs iRcs, String string2) {
        return this.f$0.lambda$getOutgoingDeliveries$0$RcsOutgoingMessage(iRcs, string2);
    }
}

