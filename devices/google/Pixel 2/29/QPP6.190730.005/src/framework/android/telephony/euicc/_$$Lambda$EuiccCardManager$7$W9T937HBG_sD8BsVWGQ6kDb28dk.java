/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.euicc;

import android.telephony.euicc.EuiccCardManager;

public final class _$$Lambda$EuiccCardManager$7$W9T937HBG_sD8BsVWGQ6kDb28dk
implements Runnable {
    private final /* synthetic */ EuiccCardManager.ResultCallback f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$EuiccCardManager$7$W9T937HBG_sD8BsVWGQ6kDb28dk(EuiccCardManager.ResultCallback resultCallback, int n) {
        this.f$0 = resultCallback;
        this.f$1 = n;
    }

    @Override
    public final void run() {
        EuiccCardManager.7.lambda$onComplete$0(this.f$0, this.f$1);
    }
}

