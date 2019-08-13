/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsFileTransferPart;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsFileTransferPart$Ju03J4o5Gnha0Ynbq35sw9HL5nU
implements RcsControllerCall.RcsServiceCallWithNoReturn {
    private final /* synthetic */ RcsFileTransferPart f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$RcsFileTransferPart$Ju03J4o5Gnha0Ynbq35sw9HL5nU(RcsFileTransferPart rcsFileTransferPart, int n) {
        this.f$0 = rcsFileTransferPart;
        this.f$1 = n;
    }

    @Override
    public final void methodOnIRcs(IRcs iRcs, String string2) {
        this.f$0.lambda$setHeight$15$RcsFileTransferPart(this.f$1, iRcs, string2);
    }
}

