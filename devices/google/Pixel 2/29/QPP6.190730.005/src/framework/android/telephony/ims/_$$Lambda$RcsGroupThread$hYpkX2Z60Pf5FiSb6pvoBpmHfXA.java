/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.net.Uri;
import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsGroupThread;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsGroupThread$hYpkX2Z60Pf5FiSb6pvoBpmHfXA
implements RcsControllerCall.RcsServiceCall {
    private final /* synthetic */ RcsGroupThread f$0;

    public /* synthetic */ _$$Lambda$RcsGroupThread$hYpkX2Z60Pf5FiSb6pvoBpmHfXA(RcsGroupThread rcsGroupThread) {
        this.f$0 = rcsGroupThread;
    }

    public final Object methodOnIRcs(IRcs iRcs, String string2) {
        return this.f$0.lambda$getConferenceUri$9$RcsGroupThread(iRcs, string2);
    }
}

