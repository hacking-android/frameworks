/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsFileTransferPart;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsFileTransferPart$m0Uztiu9azOAnoxBEWLsT8Br_HE
implements RcsControllerCall.RcsServiceCall {
    private final /* synthetic */ RcsFileTransferPart f$0;

    public /* synthetic */ _$$Lambda$RcsFileTransferPart$m0Uztiu9azOAnoxBEWLsT8Br_HE(RcsFileTransferPart rcsFileTransferPart) {
        this.f$0 = rcsFileTransferPart;
    }

    public final Object methodOnIRcs(IRcs iRcs, String string2) {
        return this.f$0.lambda$getTransferOffset$9$RcsFileTransferPart(iRcs, string2);
    }
}

