/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsMessage;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsMessage$OWkNB5jXq4SHPk_hN01pKQSg5Z0
implements RcsControllerCall.RcsServiceCallWithNoReturn {
    private final /* synthetic */ RcsMessage f$0;
    private final /* synthetic */ double f$1;

    public /* synthetic */ _$$Lambda$RcsMessage$OWkNB5jXq4SHPk_hN01pKQSg5Z0(RcsMessage rcsMessage, double d) {
        this.f$0 = rcsMessage;
        this.f$1 = d;
    }

    @Override
    public final void methodOnIRcs(IRcs iRcs, String string2) {
        this.f$0.lambda$setLatitude$11$RcsMessage(this.f$1, iRcs, string2);
    }
}

