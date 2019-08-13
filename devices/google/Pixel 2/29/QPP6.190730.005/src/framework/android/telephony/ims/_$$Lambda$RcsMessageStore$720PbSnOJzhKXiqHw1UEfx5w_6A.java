/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsMessageStore;
import android.telephony.ims.RcsParticipantQueryParams;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsMessageStore$720PbSnOJzhKXiqHw1UEfx5w_6A
implements RcsControllerCall.RcsServiceCall {
    private final /* synthetic */ RcsParticipantQueryParams f$0;

    public /* synthetic */ _$$Lambda$RcsMessageStore$720PbSnOJzhKXiqHw1UEfx5w_6A(RcsParticipantQueryParams rcsParticipantQueryParams) {
        this.f$0 = rcsParticipantQueryParams;
    }

    public final Object methodOnIRcs(IRcs iRcs, String string2) {
        return RcsMessageStore.lambda$getRcsParticipants$2(this.f$0, iRcs, string2);
    }
}

