/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.euicc;

import android.telephony.euicc.EuiccCardManager;
import android.telephony.euicc.EuiccNotification;

public final class _$$Lambda$EuiccCardManager$20$BvkqzlF_5oeo0InlIzG65QhyNT0
implements Runnable {
    private final /* synthetic */ EuiccCardManager.ResultCallback f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ EuiccNotification[] f$2;

    public /* synthetic */ _$$Lambda$EuiccCardManager$20$BvkqzlF_5oeo0InlIzG65QhyNT0(EuiccCardManager.ResultCallback resultCallback, int n, EuiccNotification[] arreuiccNotification) {
        this.f$0 = resultCallback;
        this.f$1 = n;
        this.f$2 = arreuiccNotification;
    }

    @Override
    public final void run() {
        EuiccCardManager.20.lambda$onComplete$0(this.f$0, this.f$1, this.f$2);
    }
}

