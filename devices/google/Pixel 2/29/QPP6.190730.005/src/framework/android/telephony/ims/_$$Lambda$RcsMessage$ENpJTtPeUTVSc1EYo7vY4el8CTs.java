/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsMessage;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsMessage$ENpJTtPeUTVSc1EYo7vY4el8CTs
implements RcsControllerCall.RcsServiceCall {
    private final /* synthetic */ RcsMessage f$0;

    public /* synthetic */ _$$Lambda$RcsMessage$ENpJTtPeUTVSc1EYo7vY4el8CTs(RcsMessage rcsMessage) {
        this.f$0 = rcsMessage;
    }

    public final Object methodOnIRcs(IRcs iRcs, String string2) {
        return this.f$0.lambda$getStatus$3$RcsMessage(iRcs, string2);
    }
}

