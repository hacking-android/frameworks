/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsMessageStore;
import android.telephony.ims.RcsParticipant;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsMessageStore$eOFObBGn_N5PMKJvVTBw06iJWQ4
implements RcsControllerCall.RcsServiceCall {
    private final /* synthetic */ RcsParticipant f$0;

    public /* synthetic */ _$$Lambda$RcsMessageStore$eOFObBGn_N5PMKJvVTBw06iJWQ4(RcsParticipant rcsParticipant) {
        this.f$0 = rcsParticipant;
    }

    public final Object methodOnIRcs(IRcs iRcs, String string2) {
        return RcsMessageStore.lambda$createRcs1To1Thread$8(this.f$0, iRcs, string2);
    }
}

