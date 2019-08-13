/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.metrics;

import com.android.internal.telephony.metrics.TelephonyMetrics;
import java.util.function.Predicate;

public final class _$$Lambda$TelephonyMetrics$tQOsX1lKb2eTuPp_1rpkeIAEOoY
implements Predicate {
    private final /* synthetic */ int f$0;

    public /* synthetic */ _$$Lambda$TelephonyMetrics$tQOsX1lKb2eTuPp_1rpkeIAEOoY(int n) {
        this.f$0 = n;
    }

    public final boolean test(Object object) {
        return TelephonyMetrics.lambda$updateActiveSubscriptionInfoList$0(this.f$0, (Integer)object);
    }
}

