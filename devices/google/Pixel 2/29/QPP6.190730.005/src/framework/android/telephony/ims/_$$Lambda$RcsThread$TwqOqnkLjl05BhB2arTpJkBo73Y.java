/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsMessageSnippet;
import android.telephony.ims.RcsThread;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsThread$TwqOqnkLjl05BhB2arTpJkBo73Y
implements RcsControllerCall.RcsServiceCall {
    private final /* synthetic */ RcsThread f$0;

    public /* synthetic */ _$$Lambda$RcsThread$TwqOqnkLjl05BhB2arTpJkBo73Y(RcsThread rcsThread) {
        this.f$0 = rcsThread;
    }

    public final Object methodOnIRcs(IRcs iRcs, String string2) {
        return this.f$0.lambda$getSnippet$0$RcsThread(iRcs, string2);
    }
}

