/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.Rcs1To1Thread;
import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$Rcs1To1Thread$vx_evSYitgJIMB6l_hANvSJpdBE
implements RcsControllerCall.RcsServiceCallWithNoReturn {
    private final /* synthetic */ Rcs1To1Thread f$0;
    private final /* synthetic */ long f$1;

    public /* synthetic */ _$$Lambda$Rcs1To1Thread$vx_evSYitgJIMB6l_hANvSJpdBE(Rcs1To1Thread rcs1To1Thread, long l) {
        this.f$0 = rcs1To1Thread;
        this.f$1 = l;
    }

    @Override
    public final void methodOnIRcs(IRcs iRcs, String string2) {
        this.f$0.lambda$setFallbackThreadId$1$Rcs1To1Thread(this.f$1, iRcs, string2);
    }
}

