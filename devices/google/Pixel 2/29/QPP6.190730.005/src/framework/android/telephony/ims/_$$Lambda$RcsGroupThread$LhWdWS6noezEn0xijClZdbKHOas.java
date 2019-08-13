/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.net.Uri;
import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsGroupThread;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsGroupThread$LhWdWS6noezEn0xijClZdbKHOas
implements RcsControllerCall.RcsServiceCallWithNoReturn {
    private final /* synthetic */ RcsGroupThread f$0;
    private final /* synthetic */ Uri f$1;

    public /* synthetic */ _$$Lambda$RcsGroupThread$LhWdWS6noezEn0xijClZdbKHOas(RcsGroupThread rcsGroupThread, Uri uri) {
        this.f$0 = rcsGroupThread;
        this.f$1 = uri;
    }

    @Override
    public final void methodOnIRcs(IRcs iRcs, String string2) {
        this.f$0.lambda$setConferenceUri$10$RcsGroupThread(this.f$1, iRcs, string2);
    }
}

