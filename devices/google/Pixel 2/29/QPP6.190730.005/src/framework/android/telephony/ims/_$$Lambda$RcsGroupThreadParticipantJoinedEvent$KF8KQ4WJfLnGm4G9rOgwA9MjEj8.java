/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsGroupThreadParticipantJoinedEvent;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsGroupThreadParticipantJoinedEvent$KF8KQ4WJfLnGm4G9rOgwA9MjEj8
implements RcsControllerCall.RcsServiceCall {
    private final /* synthetic */ RcsGroupThreadParticipantJoinedEvent f$0;

    public /* synthetic */ _$$Lambda$RcsGroupThreadParticipantJoinedEvent$KF8KQ4WJfLnGm4G9rOgwA9MjEj8(RcsGroupThreadParticipantJoinedEvent rcsGroupThreadParticipantJoinedEvent) {
        this.f$0 = rcsGroupThreadParticipantJoinedEvent;
    }

    public final Object methodOnIRcs(IRcs iRcs, String string2) {
        return this.f$0.lambda$persist$0$RcsGroupThreadParticipantJoinedEvent(iRcs, string2);
    }
}

