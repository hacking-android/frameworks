/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.euicc;

import android.telephony.euicc.EuiccCardManager;
import android.telephony.euicc.EuiccNotification;

public final class _$$Lambda$EuiccCardManager$21$srrmNYPqPTZF4uUZIcVq86p1JpU
implements Runnable {
    private final /* synthetic */ EuiccCardManager.ResultCallback f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ EuiccNotification f$2;

    public /* synthetic */ _$$Lambda$EuiccCardManager$21$srrmNYPqPTZF4uUZIcVq86p1JpU(EuiccCardManager.ResultCallback resultCallback, int n, EuiccNotification euiccNotification) {
        this.f$0 = resultCallback;
        this.f$1 = n;
        this.f$2 = euiccNotification;
    }

    @Override
    public final void run() {
        EuiccCardManager.21.lambda$onComplete$0(this.f$0, this.f$1, this.f$2);
    }
}

