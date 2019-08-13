/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsFileTransferPart;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsFileTransferPart$Js49W5j_aEL3sBPRKR3zwBZEwQc
implements RcsControllerCall.RcsServiceCallWithNoReturn {
    private final /* synthetic */ RcsFileTransferPart f$0;
    private final /* synthetic */ String f$1;

    public /* synthetic */ _$$Lambda$RcsFileTransferPart$Js49W5j_aEL3sBPRKR3zwBZEwQc(RcsFileTransferPart rcsFileTransferPart, String string2) {
        this.f$0 = rcsFileTransferPart;
        this.f$1 = string2;
    }

    @Override
    public final void methodOnIRcs(IRcs iRcs, String string2) {
        this.f$0.lambda$setPreviewMimeType$21$RcsFileTransferPart(this.f$1, iRcs, string2);
    }
}

