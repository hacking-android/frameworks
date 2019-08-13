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

public final class _$$Lambda$SubscriptionController$KLGYC8GQvJwXrWqyIaejMh0cYio
implements Predicate {
    private final /* synthetic */ int f$0;

    public /* synthetic */ _$$Lambda$SubscriptionController$KLGYC8GQvJwXrWqyIaejMh0cYio(int n) {
        this.f$0 = n;
    }

    public final boolean test(Object object) {
        return SubscriptionController.lambda$setSubscriptionEnabled$6(this.f$0, (SubscriptionInfo)object);
    }
}

