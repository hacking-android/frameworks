/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsIncomingMessage;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsIncomingMessage$ye8KwJqH7fqnRAZlQY1PRVyh2b0
implements RcsControllerCall.RcsServiceCall {
    private final /* synthetic */ RcsIncomingMessage f$0;

    public /* synthetic */ _$$Lambda$RcsIncomingMessage$ye8KwJqH7fqnRAZlQY1PRVyh2b0(RcsIncomingMessage rcsIncomingMessage) {
        this.f$0 = rcsIncomingMessage;
    }

    public final Object methodOnIRcs(IRcs iRcs, String string2) {
        return this.f$0.lambda$getSenderParticipant$4$RcsIncomingMessage(iRcs, string2);
    }
}

