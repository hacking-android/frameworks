/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsGroupThread;
import android.telephony.ims.RcsParticipant;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsGroupThread$xvETBJ_gzJJ5zvelRSNsYZBdXKw
implements RcsControllerCall.RcsServiceCallWithNoReturn {
    private final /* synthetic */ RcsGroupThread f$0;
    private final /* synthetic */ RcsParticipant f$1;

    public /* synthetic */ _$$Lambda$RcsGroupThread$xvETBJ_gzJJ5zvelRSNsYZBdXKw(RcsGroupThread rcsGroupThread, RcsParticipant rcsParticipant) {
        this.f$0 = rcsGroupThread;
        this.f$1 = rcsParticipant;
    }

    @Override
    public final void methodOnIRcs(IRcs iRcs, String string2) {
        this.f$0.lambda$removeParticipant$7$RcsGroupThread(this.f$1, iRcs, string2);
    }
}

