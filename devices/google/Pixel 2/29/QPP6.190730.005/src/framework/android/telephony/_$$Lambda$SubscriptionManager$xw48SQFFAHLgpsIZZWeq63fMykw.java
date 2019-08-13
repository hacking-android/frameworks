/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.SubscriptionManager;
import com.android.internal.telephony.ISub;

public final class _$$Lambda$SubscriptionManager$xw48SQFFAHLgpsIZZWeq63fMykw
implements SubscriptionManager.CallISubMethodHelper {
    private final /* synthetic */ int f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$SubscriptionManager$xw48SQFFAHLgpsIZZWeq63fMykw(int n, int n2) {
        this.f$0 = n;
        this.f$1 = n2;
    }

    @Override
    public final int callMethod(ISub iSub) {
        return SubscriptionManager.lambda$setDataRoaming$4(this.f$0, this.f$1, iSub);
    }
}

