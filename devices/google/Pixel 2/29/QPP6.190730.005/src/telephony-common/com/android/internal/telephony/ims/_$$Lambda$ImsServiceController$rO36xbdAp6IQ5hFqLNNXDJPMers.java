/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.ims;

import com.android.internal.telephony.ims.ImsServiceController;
import java.util.function.Predicate;

public final class _$$Lambda$ImsServiceController$rO36xbdAp6IQ5hFqLNNXDJPMers
implements Predicate {
    private final /* synthetic */ int f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$ImsServiceController$rO36xbdAp6IQ5hFqLNNXDJPMers(int n, int n2) {
        this.f$0 = n;
        this.f$1 = n2;
    }

    public final boolean test(Object object) {
        return ImsServiceController.lambda$removeImsFeatureBinder$1(this.f$0, this.f$1, (ImsServiceController.ImsFeatureContainer)object);
    }
}

