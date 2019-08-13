/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsMessage;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsMessage$aRPnqqKzd_0_r7d0L_yxEGwwqhc
implements RcsControllerCall.RcsServiceCall {
    private final /* synthetic */ RcsMessage f$0;

    public /* synthetic */ _$$Lambda$RcsMessage$aRPnqqKzd_0_r7d0L_yxEGwwqhc(RcsMessage rcsMessage) {
        this.f$0 = rcsMessage;
    }

    public final Object methodOnIRcs(IRcs iRcs, String string2) {
        return this.f$0.lambda$getSubscriptionId$0$RcsMessage(iRcs, string2);
    }
}

