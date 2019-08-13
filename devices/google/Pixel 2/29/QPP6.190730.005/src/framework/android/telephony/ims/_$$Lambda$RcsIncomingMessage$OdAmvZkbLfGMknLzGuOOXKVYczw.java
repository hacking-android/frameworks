/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsIncomingMessage;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsIncomingMessage$OdAmvZkbLfGMknLzGuOOXKVYczw
implements RcsControllerCall.RcsServiceCallWithNoReturn {
    private final /* synthetic */ RcsIncomingMessage f$0;
    private final /* synthetic */ long f$1;

    public /* synthetic */ _$$Lambda$RcsIncomingMessage$OdAmvZkbLfGMknLzGuOOXKVYczw(RcsIncomingMessage rcsIncomingMessage, long l) {
        this.f$0 = rcsIncomingMessage;
        this.f$1 = l;
    }

    @Override
    public final void methodOnIRcs(IRcs iRcs, String string2) {
        this.f$0.lambda$setArrivalTimestamp$0$RcsIncomingMessage(this.f$1, iRcs, string2);
    }
}

