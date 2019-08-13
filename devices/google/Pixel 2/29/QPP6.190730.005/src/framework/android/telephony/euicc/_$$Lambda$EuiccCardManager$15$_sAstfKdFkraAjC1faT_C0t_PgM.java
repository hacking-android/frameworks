/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.euicc;

import android.telephony.euicc.EuiccCardManager;

public final class _$$Lambda$EuiccCardManager$15$_sAstfKdFkraAjC1faT_C0t_PgM
implements Runnable {
    private final /* synthetic */ EuiccCardManager.ResultCallback f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ byte[] f$2;

    public /* synthetic */ _$$Lambda$EuiccCardManager$15$_sAstfKdFkraAjC1faT_C0t_PgM(EuiccCardManager.ResultCallback resultCallback, int n, byte[] arrby) {
        this.f$0 = resultCallback;
        this.f$1 = n;
        this.f$2 = arrby;
    }

    @Override
    public final void run() {
        EuiccCardManager.15.lambda$onComplete$0(this.f$0, this.f$1, this.f$2);
    }
}

