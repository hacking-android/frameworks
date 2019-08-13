/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.ims;

import com.android.internal.telephony.ims.ImsServiceController;
import java.util.function.Predicate;

public final class _$$Lambda$ImsServiceController$w3xbtqEhKr7IY81qFuw0e94p84Y
implements Predicate {
    private final /* synthetic */ int f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$ImsServiceController$w3xbtqEhKr7IY81qFuw0e94p84Y(int n, int n2) {
        this.f$0 = n;
        this.f$1 = n2;
    }

    public final boolean test(Object object) {
        return ImsServiceController.lambda$getImsFeatureContainer$2(this.f$0, this.f$1, (ImsServiceController.ImsFeatureContainer)object);
    }
}

