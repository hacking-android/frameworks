/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.euicc;

import android.telephony.euicc.EuiccCardManager;

public final class _$$Lambda$EuiccCardManager$12$0dAgJQ0nijxpb9xsSsFqNuBkchU
implements Runnable {
    private final /* synthetic */ EuiccCardManager.ResultCallback f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ byte[] f$2;

    public /* synthetic */ _$$Lambda$EuiccCardManager$12$0dAgJQ0nijxpb9xsSsFqNuBkchU(EuiccCardManager.ResultCallback resultCallback, int n, byte[] arrby) {
        this.f$0 = resultCallback;
        this.f$1 = n;
        this.f$2 = arrby;
    }

    @Override
    public final void run() {
        EuiccCardManager.12.lambda$onComplete$0(this.f$0, this.f$1, this.f$2);
    }
}

