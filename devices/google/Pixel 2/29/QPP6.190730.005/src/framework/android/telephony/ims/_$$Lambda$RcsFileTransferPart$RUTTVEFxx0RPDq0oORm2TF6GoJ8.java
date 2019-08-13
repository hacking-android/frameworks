/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsFileTransferPart;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsFileTransferPart$RUTTVEFxx0RPDq0oORm2TF6GoJ8
implements RcsControllerCall.RcsServiceCall {
    private final /* synthetic */ RcsFileTransferPart f$0;

    public /* synthetic */ _$$Lambda$RcsFileTransferPart$RUTTVEFxx0RPDq0oORm2TF6GoJ8(RcsFileTransferPart rcsFileTransferPart) {
        this.f$0 = rcsFileTransferPart;
    }

    public final Object methodOnIRcs(IRcs iRcs, String string2) {
        return this.f$0.lambda$getFileSize$7$RcsFileTransferPart(iRcs, string2);
    }
}

