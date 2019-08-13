/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.ims;

import com.android.internal.telephony.ims.ImsResolver;
import com.android.internal.telephony.ims.ImsServiceController;
import java.util.function.Predicate;

public final class _$$Lambda$ImsResolver$aWLlEvfonhYSfDR8cVsM6A5pmqI
implements Predicate {
    private final /* synthetic */ ImsResolver.ImsServiceInfo f$0;

    public /* synthetic */ _$$Lambda$ImsResolver$aWLlEvfonhYSfDR8cVsM6A5pmqI(ImsResolver.ImsServiceInfo imsServiceInfo) {
        this.f$0 = imsServiceInfo;
    }

    public final boolean test(Object object) {
        return ImsResolver.lambda$getControllerByServiceInfo$1(this.f$0, (ImsServiceController)object);
    }
}

