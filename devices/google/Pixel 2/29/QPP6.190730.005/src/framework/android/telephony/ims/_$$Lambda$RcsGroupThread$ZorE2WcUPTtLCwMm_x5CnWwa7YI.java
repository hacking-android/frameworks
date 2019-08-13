/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsGroupThread;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsGroupThread$ZorE2WcUPTtLCwMm_x5CnWwa7YI
implements RcsControllerCall.RcsServiceCallWithNoReturn {
    private final /* synthetic */ RcsGroupThread f$0;
    private final /* synthetic */ String f$1;

    public /* synthetic */ _$$Lambda$RcsGroupThread$ZorE2WcUPTtLCwMm_x5CnWwa7YI(RcsGroupThread rcsGroupThread, String string2) {
        this.f$0 = rcsGroupThread;
        this.f$1 = string2;
    }

    @Override
    public final void methodOnIRcs(IRcs iRcs, String string2) {
        this.f$0.lambda$setGroupName$1$RcsGroupThread(this.f$1, iRcs, string2);
    }
}

