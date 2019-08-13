/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import com.android.internal.telephony.SubscriptionInfoUpdater;
import java.util.List;

public final class _$$Lambda$SubscriptionInfoUpdater$qyDxq2AWyReUxdc6HttVGQeDD3Y
implements Runnable {
    private final /* synthetic */ SubscriptionInfoUpdater f$0;
    private final /* synthetic */ List f$1;
    private final /* synthetic */ SubscriptionInfoUpdater.UpdateEmbeddedSubsCallback f$2;

    public /* synthetic */ _$$Lambda$SubscriptionInfoUpdater$qyDxq2AWyReUxdc6HttVGQeDD3Y(SubscriptionInfoUpdater subscriptionInfoUpdater, List list, SubscriptionInfoUpdater.UpdateEmbeddedSubsCallback updateEmbeddedSubsCallback) {
        this.f$0 = subscriptionInfoUpdater;
        this.f$1 = list;
        this.f$2 = updateEmbeddedSubsCallback;
    }

    @Override
    public final void run() {
        this.f$0.lambda$updateEmbeddedSubscriptions$5$SubscriptionInfoUpdater(this.f$1, this.f$2);
    }
}

