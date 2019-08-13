/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.SubscriptionManager;
import com.android.internal.telephony.ISub;

public final class _$$Lambda$SubscriptionManager$NazcIP1h3U0cfnY__L174e3u4tk
implements SubscriptionManager.CallISubMethodHelper {
    private final /* synthetic */ SubscriptionManager f$0;
    private final /* synthetic */ boolean f$1;
    private final /* synthetic */ int f$2;

    public /* synthetic */ _$$Lambda$SubscriptionManager$NazcIP1h3U0cfnY__L174e3u4tk(SubscriptionManager subscriptionManager, boolean bl, int n) {
        this.f$0 = subscriptionManager;
        this.f$1 = bl;
        this.f$2 = n;
    }

    @Override
    public final int callMethod(ISub iSub) {
        return this.f$0.lambda$setOpportunistic$5$SubscriptionManager(this.f$1, this.f$2, iSub);
    }
}

