/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsFileTransferPart;
import android.telephony.ims.RcsMessage;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsMessage$ArUQB5LoWlQIN8Wq6WO2D83_1MM
implements RcsControllerCall.RcsServiceCallWithNoReturn {
    private final /* synthetic */ RcsFileTransferPart f$0;

    public /* synthetic */ _$$Lambda$RcsMessage$ArUQB5LoWlQIN8Wq6WO2D83_1MM(RcsFileTransferPart rcsFileTransferPart) {
        this.f$0 = rcsFileTransferPart;
    }

    @Override
    public final void methodOnIRcs(IRcs iRcs, String string2) {
        RcsMessage.lambda$removeFileTransferPart$16(this.f$0, iRcs, string2);
    }
}

