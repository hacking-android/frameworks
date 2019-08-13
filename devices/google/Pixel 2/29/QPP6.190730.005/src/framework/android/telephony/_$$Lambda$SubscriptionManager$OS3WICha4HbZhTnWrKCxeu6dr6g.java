/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.SubscriptionManager;
import com.android.internal.telephony.ISub;

public final class _$$Lambda$SubscriptionManager$OS3WICha4HbZhTnWrKCxeu6dr6g
implements SubscriptionManager.CallISubMethodHelper {
    private final /* synthetic */ String f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ int f$2;

    public /* synthetic */ _$$Lambda$SubscriptionManager$OS3WICha4HbZhTnWrKCxeu6dr6g(String string2, int n, int n2) {
        this.f$0 = string2;
        this.f$1 = n;
        this.f$2 = n2;
    }

    @Override
    public final int callMethod(ISub iSub) {
        return SubscriptionManager.lambda$setDisplayName$2(this.f$0, this.f$1, this.f$2, iSub);
    }
}

