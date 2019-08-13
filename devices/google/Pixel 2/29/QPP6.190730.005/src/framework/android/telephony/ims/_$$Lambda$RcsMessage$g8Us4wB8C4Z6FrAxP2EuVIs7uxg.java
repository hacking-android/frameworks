/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsMessage;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsMessage$g8Us4wB8C4Z6FrAxP2EuVIs7uxg
implements RcsControllerCall.RcsServiceCallWithNoReturn {
    private final /* synthetic */ RcsMessage f$0;
    private final /* synthetic */ String f$1;

    public /* synthetic */ _$$Lambda$RcsMessage$g8Us4wB8C4Z6FrAxP2EuVIs7uxg(RcsMessage rcsMessage, String string2) {
        this.f$0 = rcsMessage;
        this.f$1 = string2;
    }

    @Override
    public final void methodOnIRcs(IRcs iRcs, String string2) {
        this.f$0.lambda$setRcsMessageId$6$RcsMessage(this.f$1, iRcs, string2);
    }
}

