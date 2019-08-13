/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.Rcs1To1Thread;
import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$Rcs1To1Thread$_6gUCvjDS6WXqf0AClQwrZ7ZpSc
implements RcsControllerCall.RcsServiceCall {
    private final /* synthetic */ Rcs1To1Thread f$0;

    public /* synthetic */ _$$Lambda$Rcs1To1Thread$_6gUCvjDS6WXqf0AClQwrZ7ZpSc(Rcs1To1Thread rcs1To1Thread) {
        this.f$0 = rcs1To1Thread;
    }

    public final Object methodOnIRcs(IRcs iRcs, String string2) {
        return this.f$0.lambda$getFallbackThreadId$0$Rcs1To1Thread(iRcs, string2);
    }
}

