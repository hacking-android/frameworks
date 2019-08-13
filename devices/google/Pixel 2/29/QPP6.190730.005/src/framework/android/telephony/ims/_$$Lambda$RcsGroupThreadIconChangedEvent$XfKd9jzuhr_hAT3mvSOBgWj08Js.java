/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsGroupThreadIconChangedEvent;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsGroupThreadIconChangedEvent$XfKd9jzuhr_hAT3mvSOBgWj08Js
implements RcsControllerCall.RcsServiceCall {
    private final /* synthetic */ RcsGroupThreadIconChangedEvent f$0;

    public /* synthetic */ _$$Lambda$RcsGroupThreadIconChangedEvent$XfKd9jzuhr_hAT3mvSOBgWj08Js(RcsGroupThreadIconChangedEvent rcsGroupThreadIconChangedEvent) {
        this.f$0 = rcsGroupThreadIconChangedEvent;
    }

    public final Object methodOnIRcs(IRcs iRcs, String string2) {
        return this.f$0.lambda$persist$0$RcsGroupThreadIconChangedEvent(iRcs, string2);
    }
}

