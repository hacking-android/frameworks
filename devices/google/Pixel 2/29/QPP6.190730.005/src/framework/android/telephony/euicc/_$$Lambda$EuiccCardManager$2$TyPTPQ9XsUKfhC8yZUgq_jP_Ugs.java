/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.euicc;

import android.service.euicc.EuiccProfileInfo;
import android.telephony.euicc.EuiccCardManager;

public final class _$$Lambda$EuiccCardManager$2$TyPTPQ9XsUKfhC8yZUgq_jP_Ugs
implements Runnable {
    private final /* synthetic */ EuiccCardManager.ResultCallback f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ EuiccProfileInfo f$2;

    public /* synthetic */ _$$Lambda$EuiccCardManager$2$TyPTPQ9XsUKfhC8yZUgq_jP_Ugs(EuiccCardManager.ResultCallback resultCallback, int n, EuiccProfileInfo euiccProfileInfo) {
        this.f$0 = resultCallback;
        this.f$1 = n;
        this.f$2 = euiccProfileInfo;
    }

    @Override
    public final void run() {
        EuiccCardManager.2.lambda$onComplete$0(this.f$0, this.f$1, this.f$2);
    }
}

