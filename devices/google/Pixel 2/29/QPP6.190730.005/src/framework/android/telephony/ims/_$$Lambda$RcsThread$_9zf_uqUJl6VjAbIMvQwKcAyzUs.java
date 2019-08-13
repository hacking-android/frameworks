/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsOutgoingMessageCreationParams;
import android.telephony.ims.RcsThread;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsThread$_9zf_uqUJl6VjAbIMvQwKcAyzUs
implements RcsControllerCall.RcsServiceCall {
    private final /* synthetic */ RcsThread f$0;
    private final /* synthetic */ RcsOutgoingMessageCreationParams f$1;

    public /* synthetic */ _$$Lambda$RcsThread$_9zf_uqUJl6VjAbIMvQwKcAyzUs(RcsThread rcsThread, RcsOutgoingMessageCreationParams rcsOutgoingMessageCreationParams) {
        this.f$0 = rcsThread;
        this.f$1 = rcsOutgoingMessageCreationParams;
    }

    public final Object methodOnIRcs(IRcs iRcs, String string2) {
        return this.f$0.lambda$addOutgoingMessage$2$RcsThread(this.f$1, iRcs, string2);
    }
}

