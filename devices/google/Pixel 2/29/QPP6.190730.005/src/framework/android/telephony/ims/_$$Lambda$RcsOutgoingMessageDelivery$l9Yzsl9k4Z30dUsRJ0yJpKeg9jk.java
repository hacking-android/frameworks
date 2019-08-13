/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsOutgoingMessageDelivery;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsOutgoingMessageDelivery$l9Yzsl9k4Z30dUsRJ0yJpKeg9jk
implements RcsControllerCall.RcsServiceCallWithNoReturn {
    private final /* synthetic */ RcsOutgoingMessageDelivery f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$RcsOutgoingMessageDelivery$l9Yzsl9k4Z30dUsRJ0yJpKeg9jk(RcsOutgoingMessageDelivery rcsOutgoingMessageDelivery, int n) {
        this.f$0 = rcsOutgoingMessageDelivery;
        this.f$1 = n;
    }

    @Override
    public final void methodOnIRcs(IRcs iRcs, String string2) {
        this.f$0.lambda$setStatus$4$RcsOutgoingMessageDelivery(this.f$1, iRcs, string2);
    }
}

