/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsFileTransferCreationParams;
import android.telephony.ims.RcsMessage;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsMessage$b6noI0B_AJvyHWAuKOL2fM_kHI4
implements RcsControllerCall.RcsServiceCall {
    private final /* synthetic */ RcsMessage f$0;
    private final /* synthetic */ RcsFileTransferCreationParams f$1;

    public /* synthetic */ _$$Lambda$RcsMessage$b6noI0B_AJvyHWAuKOL2fM_kHI4(RcsMessage rcsMessage, RcsFileTransferCreationParams rcsFileTransferCreationParams) {
        this.f$0 = rcsMessage;
        this.f$1 = rcsFileTransferCreationParams;
    }

    public final Object methodOnIRcs(IRcs iRcs, String string2) {
        return this.f$0.lambda$insertFileTransfer$14$RcsMessage(this.f$1, iRcs, string2);
    }
}

