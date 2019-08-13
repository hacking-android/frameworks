/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.euicc;

import android.service.euicc.EuiccProfileInfo;
import android.telephony.euicc.EuiccCardManager;

public final class _$$Lambda$EuiccCardManager$1$WCF3YSMl2TGGvaCq1GRblRP0j8M
implements Runnable {
    private final /* synthetic */ EuiccCardManager.ResultCallback f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ EuiccProfileInfo[] f$2;

    public /* synthetic */ _$$Lambda$EuiccCardManager$1$WCF3YSMl2TGGvaCq1GRblRP0j8M(EuiccCardManager.ResultCallback resultCallback, int n, EuiccProfileInfo[] arreuiccProfileInfo) {
        this.f$0 = resultCallback;
        this.f$1 = n;
        this.f$2 = arreuiccProfileInfo;
    }

    @Override
    public final void run() {
        EuiccCardManager.1.lambda$onComplete$0(this.f$0, this.f$1, this.f$2);
    }
}

