/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.ParcelUuid
 *  android.telephony.SubscriptionInfo
 */
package com.android.internal.telephony;

import android.os.ParcelUuid;
import android.telephony.SubscriptionInfo;
import com.android.internal.telephony.SubscriptionController;
import java.util.function.Predicate;

public final class _$$Lambda$SubscriptionController$CaRmFtDrpSD7YdPKEdfMgAOlVZY
implements Predicate {
    private final /* synthetic */ SubscriptionController f$0;
    private final /* synthetic */ ParcelUuid f$1;
    private final /* synthetic */ String f$2;

    public /* synthetic */ _$$Lambda$SubscriptionController$CaRmFtDrpSD7YdPKEdfMgAOlVZY(SubscriptionController subscriptionController, ParcelUuid parcelUuid, String string) {
        this.f$0 = subscriptionController;
        this.f$1 = parcelUuid;
        this.f$2 = string;
    }

    public final boolean test(Object object) {
        return this.f$0.lambda$getSubscriptionsInGroup$5$SubscriptionController(this.f$1, this.f$2, (SubscriptionInfo)object);
    }
}

