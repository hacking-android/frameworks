/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsMessageQueryParams;
import android.telephony.ims.RcsThread;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsThread$A9iPL3bU3iiRv1xCYNUNP76n6Vw
implements RcsControllerCall.RcsServiceCall {
    private final /* synthetic */ RcsMessageQueryParams f$0;

    public /* synthetic */ _$$Lambda$RcsThread$A9iPL3bU3iiRv1xCYNUNP76n6Vw(RcsMessageQueryParams rcsMessageQueryParams) {
        this.f$0 = rcsMessageQueryParams;
    }

    public final Object methodOnIRcs(IRcs iRcs, String string2) {
        return RcsThread.lambda$getMessages$4(this.f$0, iRcs, string2);
    }
}

