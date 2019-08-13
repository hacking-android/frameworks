/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsParticipantAliasChangedEvent;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsParticipantAliasChangedEvent$iaidodGQwVEX4DZ8FekRuR_x3gQ
implements RcsControllerCall.RcsServiceCall {
    private final /* synthetic */ RcsParticipantAliasChangedEvent f$0;

    public /* synthetic */ _$$Lambda$RcsParticipantAliasChangedEvent$iaidodGQwVEX4DZ8FekRuR_x3gQ(RcsParticipantAliasChangedEvent rcsParticipantAliasChangedEvent) {
        this.f$0 = rcsParticipantAliasChangedEvent;
    }

    public final Object methodOnIRcs(IRcs iRcs, String string2) {
        return this.f$0.lambda$persist$0$RcsParticipantAliasChangedEvent(iRcs, string2);
    }
}

