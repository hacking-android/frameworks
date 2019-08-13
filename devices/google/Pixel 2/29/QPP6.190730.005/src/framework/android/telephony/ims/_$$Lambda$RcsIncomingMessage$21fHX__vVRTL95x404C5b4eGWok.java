/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsIncomingMessage;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsIncomingMessage$21fHX__vVRTL95x404C5b4eGWok
implements RcsControllerCall.RcsServiceCall {
    private final /* synthetic */ RcsIncomingMessage f$0;

    public /* synthetic */ _$$Lambda$RcsIncomingMessage$21fHX__vVRTL95x404C5b4eGWok(RcsIncomingMessage rcsIncomingMessage) {
        this.f$0 = rcsIncomingMessage;
    }

    public final Object methodOnIRcs(IRcs iRcs, String string2) {
        return this.f$0.lambda$getSeenTimestamp$3$RcsIncomingMessage(iRcs, string2);
    }
}

