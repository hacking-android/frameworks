/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.euicc;

import android.telephony.euicc.EuiccCardManager;

public final class _$$Lambda$EuiccCardManager$9$cPEHH7JlllMuvBHJOu0A2hY4QyU
implements Runnable {
    private final /* synthetic */ EuiccCardManager.ResultCallback f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ String f$2;

    public /* synthetic */ _$$Lambda$EuiccCardManager$9$cPEHH7JlllMuvBHJOu0A2hY4QyU(EuiccCardManager.ResultCallback resultCallback, int n, String string2) {
        this.f$0 = resultCallback;
        this.f$1 = n;
        this.f$2 = string2;
    }

    @Override
    public final void run() {
        EuiccCardManager.9.lambda$onComplete$0(this.f$0, this.f$1, this.f$2);
    }
}

