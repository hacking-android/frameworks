/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import com.android.internal.telephony.SubscriptionInfoUpdater;

public final class _$$Lambda$SubscriptionInfoUpdater$UFyB0ValfLD0rdGDibCjTnGFkeo
implements SubscriptionInfoUpdater.UpdateEmbeddedSubsCallback {
    private final /* synthetic */ Runnable f$0;

    public /* synthetic */ _$$Lambda$SubscriptionInfoUpdater$UFyB0ValfLD0rdGDibCjTnGFkeo(Runnable runnable) {
        this.f$0 = runnable;
    }

    @Override
    public final void run(boolean bl) {
        SubscriptionInfoUpdater.lambda$handleMessage$2(this.f$0, bl);
    }
}

