/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import java.util.function.Predicate;

public final class _$$Lambda$SubscriptionManager$R_uORt9bKcmEo6JnjiGP2KgjIOQ
implements Predicate {
    private final /* synthetic */ SubscriptionManager f$0;

    public /* synthetic */ _$$Lambda$SubscriptionManager$R_uORt9bKcmEo6JnjiGP2KgjIOQ(SubscriptionManager subscriptionManager) {
        this.f$0 = subscriptionManager;
    }

    public final boolean test(Object object) {
        return this.f$0.lambda$getActiveSubscriptionInfoList$0$SubscriptionManager((SubscriptionInfo)object);
    }
}

