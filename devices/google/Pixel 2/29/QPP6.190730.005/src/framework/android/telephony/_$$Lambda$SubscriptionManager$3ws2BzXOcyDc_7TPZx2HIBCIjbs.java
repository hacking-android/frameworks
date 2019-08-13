/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.SubscriptionManager;
import com.android.internal.telephony.ISub;

public final class _$$Lambda$SubscriptionManager$3ws2BzXOcyDc_7TPZx2HIBCIjbs
implements SubscriptionManager.CallISubMethodHelper {
    private final /* synthetic */ String f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$SubscriptionManager$3ws2BzXOcyDc_7TPZx2HIBCIjbs(String string2, int n) {
        this.f$0 = string2;
        this.f$1 = n;
    }

    @Override
    public final int callMethod(ISub iSub) {
        return SubscriptionManager.lambda$setDisplayNumber$3(this.f$0, this.f$1, iSub);
    }
}

