/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsEventQueryParams;
import android.telephony.ims.RcsMessageStore;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsMessageStore$IvBKppwBc6MDwzIkAi2XJcVB_iI
implements RcsControllerCall.RcsServiceCall {
    private final /* synthetic */ RcsEventQueryParams f$0;

    public /* synthetic */ _$$Lambda$RcsMessageStore$IvBKppwBc6MDwzIkAi2XJcVB_iI(RcsEventQueryParams rcsEventQueryParams) {
        this.f$0 = rcsEventQueryParams;
    }

    public final Object methodOnIRcs(IRcs iRcs, String string2) {
        return RcsMessageStore.lambda$getRcsEvents$6(this.f$0, iRcs, string2);
    }
}

