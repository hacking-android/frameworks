/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.SubscriptionManager;
import java.util.function.Consumer;

public final class _$$Lambda$SubscriptionManager$1$oi86t06gqdSgTtWgRmCc5dJIfEs
implements Runnable {
    private final /* synthetic */ Consumer f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$SubscriptionManager$1$oi86t06gqdSgTtWgRmCc5dJIfEs(Consumer consumer, int n) {
        this.f$0 = consumer;
        this.f$1 = n;
    }

    @Override
    public final void run() {
        SubscriptionManager.1.lambda$onComplete$0(this.f$0, this.f$1);
    }
}

