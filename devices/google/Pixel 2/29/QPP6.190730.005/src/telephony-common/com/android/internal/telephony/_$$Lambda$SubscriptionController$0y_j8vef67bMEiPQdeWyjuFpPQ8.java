/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.SubscriptionInfo
 */
package com.android.internal.telephony;

import android.telephony.SubscriptionInfo;
import com.android.internal.telephony.SubscriptionController;
import java.util.function.Predicate;

public final class _$$Lambda$SubscriptionController$0y_j8vef67bMEiPQdeWyjuFpPQ8
implements Predicate {
    private final /* synthetic */ SubscriptionController f$0;
    private final /* synthetic */ String f$1;

    public /* synthetic */ _$$Lambda$SubscriptionController$0y_j8vef67bMEiPQdeWyjuFpPQ8(SubscriptionController subscriptionController, String string) {
        this.f$0 = subscriptionController;
        this.f$1 = string;
    }

    public final boolean test(Object object) {
        return this.f$0.lambda$getSubscriptionInfoListFromCacheHelper$7$SubscriptionController(this.f$1, (SubscriptionInfo)object);
    }
}

