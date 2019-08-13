/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsGroupThreadParticipantLeftEvent;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsGroupThreadParticipantLeftEvent$vX6x1bZueUi684uTuoFiWxhgs80
implements RcsControllerCall.RcsServiceCall {
    private final /* synthetic */ RcsGroupThreadParticipantLeftEvent f$0;

    public /* synthetic */ _$$Lambda$RcsGroupThreadParticipantLeftEvent$vX6x1bZueUi684uTuoFiWxhgs80(RcsGroupThreadParticipantLeftEvent rcsGroupThreadParticipantLeftEvent) {
        this.f$0 = rcsGroupThreadParticipantLeftEvent;
    }

    public final Object methodOnIRcs(IRcs iRcs, String string2) {
        return this.f$0.lambda$persist$0$RcsGroupThreadParticipantLeftEvent(iRcs, string2);
    }
}

