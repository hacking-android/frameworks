/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsMessage;
import android.telephony.ims.RcsThread;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsThread$uAkHFwrvypgP5w5y0Uy4uwQ6blY
implements RcsControllerCall.RcsServiceCallWithNoReturn {
    private final /* synthetic */ RcsThread f$0;
    private final /* synthetic */ RcsMessage f$1;

    public /* synthetic */ _$$Lambda$RcsThread$uAkHFwrvypgP5w5y0Uy4uwQ6blY(RcsThread rcsThread, RcsMessage rcsMessage) {
        this.f$0 = rcsThread;
        this.f$1 = rcsMessage;
    }

    @Override
    public final void methodOnIRcs(IRcs iRcs, String string2) {
        this.f$0.lambda$deleteMessage$3$RcsThread(this.f$1, iRcs, string2);
    }
}

