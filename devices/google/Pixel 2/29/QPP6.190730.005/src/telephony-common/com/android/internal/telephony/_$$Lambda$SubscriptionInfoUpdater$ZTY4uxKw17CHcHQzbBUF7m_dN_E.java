/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Message
 *  android.os.PersistableBundle
 */
package com.android.internal.telephony;

import android.os.Message;
import android.os.PersistableBundle;
import com.android.internal.telephony.SubscriptionInfoUpdater;

public final class _$$Lambda$SubscriptionInfoUpdater$ZTY4uxKw17CHcHQzbBUF7m_dN_E
implements Runnable {
    private final /* synthetic */ SubscriptionInfoUpdater f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ String f$2;
    private final /* synthetic */ PersistableBundle f$3;
    private final /* synthetic */ Message f$4;

    public /* synthetic */ _$$Lambda$SubscriptionInfoUpdater$ZTY4uxKw17CHcHQzbBUF7m_dN_E(SubscriptionInfoUpdater subscriptionInfoUpdater, int n, String string, PersistableBundle persistableBundle, Message message) {
        this.f$0 = subscriptionInfoUpdater;
        this.f$1 = n;
        this.f$2 = string;
        this.f$3 = persistableBundle;
        this.f$4 = message;
    }

    @Override
    public final void run() {
        this.f$0.lambda$updateSubscriptionByCarrierConfigAndNotifyComplete$6$SubscriptionInfoUpdater(this.f$1, this.f$2, this.f$3, this.f$4);
    }
}

