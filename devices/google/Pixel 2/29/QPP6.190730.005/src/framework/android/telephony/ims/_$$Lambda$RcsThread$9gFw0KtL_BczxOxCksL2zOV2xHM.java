/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsIncomingMessageCreationParams;
import android.telephony.ims.RcsThread;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsThread$9gFw0KtL_BczxOxCksL2zOV2xHM
implements RcsControllerCall.RcsServiceCall {
    private final /* synthetic */ RcsThread f$0;
    private final /* synthetic */ RcsIncomingMessageCreationParams f$1;

    public /* synthetic */ _$$Lambda$RcsThread$9gFw0KtL_BczxOxCksL2zOV2xHM(RcsThread rcsThread, RcsIncomingMessageCreationParams rcsIncomingMessageCreationParams) {
        this.f$0 = rcsThread;
        this.f$1 = rcsIncomingMessageCreationParams;
    }

    public final Object methodOnIRcs(IRcs iRcs, String string2) {
        return this.f$0.lambda$addIncomingMessage$1$RcsThread(this.f$1, iRcs, string2);
    }
}

