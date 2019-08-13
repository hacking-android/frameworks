/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsMessageQueryParams;
import android.telephony.ims.RcsMessageStore;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsMessageStore$5QXAY7bGFdmsWgLF0pk1tyYYovg
implements RcsControllerCall.RcsServiceCall {
    private final /* synthetic */ RcsMessageQueryParams f$0;

    public /* synthetic */ _$$Lambda$RcsMessageStore$5QXAY7bGFdmsWgLF0pk1tyYYovg(RcsMessageQueryParams rcsMessageQueryParams) {
        this.f$0 = rcsMessageQueryParams;
    }

    public final Object methodOnIRcs(IRcs iRcs, String string2) {
        return RcsMessageStore.lambda$getRcsMessages$4(this.f$0, iRcs, string2);
    }
}

