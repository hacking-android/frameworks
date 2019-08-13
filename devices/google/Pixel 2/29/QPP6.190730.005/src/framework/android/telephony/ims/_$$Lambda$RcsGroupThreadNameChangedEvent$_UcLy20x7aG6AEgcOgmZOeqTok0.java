/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsGroupThreadNameChangedEvent;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsGroupThreadNameChangedEvent$_UcLy20x7aG6AEgcOgmZOeqTok0
implements RcsControllerCall.RcsServiceCall {
    private final /* synthetic */ RcsGroupThreadNameChangedEvent f$0;

    public /* synthetic */ _$$Lambda$RcsGroupThreadNameChangedEvent$_UcLy20x7aG6AEgcOgmZOeqTok0(RcsGroupThreadNameChangedEvent rcsGroupThreadNameChangedEvent) {
        this.f$0 = rcsGroupThreadNameChangedEvent;
    }

    public final Object methodOnIRcs(IRcs iRcs, String string2) {
        return this.f$0.lambda$persist$0$RcsGroupThreadNameChangedEvent(iRcs, string2);
    }
}

