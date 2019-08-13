/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsMessageStore;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsMessageStore$d1Om4XlR70Dyh7qD9d6F4NZZkQI
implements RcsControllerCall.RcsServiceCall {
    private final /* synthetic */ String f$0;
    private final /* synthetic */ String f$1;

    public /* synthetic */ _$$Lambda$RcsMessageStore$d1Om4XlR70Dyh7qD9d6F4NZZkQI(String string2, String string3) {
        this.f$0 = string2;
        this.f$1 = string3;
    }

    public final Object methodOnIRcs(IRcs iRcs, String string2) {
        return RcsMessageStore.lambda$createRcsParticipant$11(this.f$0, this.f$1, iRcs, string2);
    }
}

