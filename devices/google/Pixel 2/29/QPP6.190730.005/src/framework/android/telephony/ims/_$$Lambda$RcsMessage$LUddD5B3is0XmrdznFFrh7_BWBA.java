/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsMessage;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsMessage$LUddD5B3is0XmrdznFFrh7_BWBA
implements RcsControllerCall.RcsServiceCallWithNoReturn {
    private final /* synthetic */ RcsMessage f$0;
    private final /* synthetic */ double f$1;

    public /* synthetic */ _$$Lambda$RcsMessage$LUddD5B3is0XmrdznFFrh7_BWBA(RcsMessage rcsMessage, double d) {
        this.f$0 = rcsMessage;
        this.f$1 = d;
    }

    @Override
    public final void methodOnIRcs(IRcs iRcs, String string2) {
        this.f$0.lambda$setLongitude$13$RcsMessage(this.f$1, iRcs, string2);
    }
}

