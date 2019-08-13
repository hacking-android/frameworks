/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsMessageStore;
import android.telephony.ims.RcsThreadQueryParams;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsMessageStore$z090Zf4wxRrBwUxXanwm4N3vb7w
implements RcsControllerCall.RcsServiceCall {
    private final /* synthetic */ RcsThreadQueryParams f$0;

    public /* synthetic */ _$$Lambda$RcsMessageStore$z090Zf4wxRrBwUxXanwm4N3vb7w(RcsThreadQueryParams rcsThreadQueryParams) {
        this.f$0 = rcsThreadQueryParams;
    }

    public final Object methodOnIRcs(IRcs iRcs, String string2) {
        return RcsMessageStore.lambda$getRcsThreads$0(this.f$0, iRcs, string2);
    }
}

