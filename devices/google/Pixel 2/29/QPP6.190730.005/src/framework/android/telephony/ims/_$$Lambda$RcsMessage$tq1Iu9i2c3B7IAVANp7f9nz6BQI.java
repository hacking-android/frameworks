/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsMessage;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsMessage$tq1Iu9i2c3B7IAVANp7f9nz6BQI
implements RcsControllerCall.RcsServiceCallWithNoReturn {
    private final /* synthetic */ RcsMessage f$0;
    private final /* synthetic */ long f$1;

    public /* synthetic */ _$$Lambda$RcsMessage$tq1Iu9i2c3B7IAVANp7f9nz6BQI(RcsMessage rcsMessage, long l) {
        this.f$0 = rcsMessage;
        this.f$1 = l;
    }

    @Override
    public final void methodOnIRcs(IRcs iRcs, String string2) {
        this.f$0.lambda$setOriginationTimestamp$4$RcsMessage(this.f$1, iRcs, string2);
    }
}

