/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsFileTransferPart;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsFileTransferPart$kXXTp4pKFNyBztnIElEJdJrz8F8
implements RcsControllerCall.RcsServiceCallWithNoReturn {
    private final /* synthetic */ RcsFileTransferPart f$0;
    private final /* synthetic */ long f$1;

    public /* synthetic */ _$$Lambda$RcsFileTransferPart$kXXTp4pKFNyBztnIElEJdJrz8F8(RcsFileTransferPart rcsFileTransferPart, long l) {
        this.f$0 = rcsFileTransferPart;
        this.f$1 = l;
    }

    @Override
    public final void methodOnIRcs(IRcs iRcs, String string2) {
        this.f$0.lambda$setLength$17$RcsFileTransferPart(this.f$1, iRcs, string2);
    }
}

