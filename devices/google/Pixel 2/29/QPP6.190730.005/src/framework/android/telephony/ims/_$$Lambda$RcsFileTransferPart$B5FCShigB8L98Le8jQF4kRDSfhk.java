/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsFileTransferPart;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsFileTransferPart$B5FCShigB8L98Le8jQF4kRDSfhk
implements RcsControllerCall.RcsServiceCall {
    private final /* synthetic */ RcsFileTransferPart f$0;

    public /* synthetic */ _$$Lambda$RcsFileTransferPart$B5FCShigB8L98Le8jQF4kRDSfhk(RcsFileTransferPart rcsFileTransferPart) {
        this.f$0 = rcsFileTransferPart;
    }

    public final Object methodOnIRcs(IRcs iRcs, String string2) {
        return this.f$0.lambda$getPreviewMimeType$20$RcsFileTransferPart(iRcs, string2);
    }
}

