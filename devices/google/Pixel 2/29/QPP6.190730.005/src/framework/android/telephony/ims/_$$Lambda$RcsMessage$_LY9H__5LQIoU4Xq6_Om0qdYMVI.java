/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsMessage;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsMessage$_LY9H__5LQIoU4Xq6_Om0qdYMVI
implements RcsControllerCall.RcsServiceCallWithNoReturn {
    private final /* synthetic */ RcsMessage f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$RcsMessage$_LY9H__5LQIoU4Xq6_Om0qdYMVI(RcsMessage rcsMessage, int n) {
        this.f$0 = rcsMessage;
        this.f$1 = n;
    }

    @Override
    public final void methodOnIRcs(IRcs iRcs, String string2) {
        this.f$0.lambda$setSubscriptionId$1$RcsMessage(this.f$1, iRcs, string2);
    }
}

