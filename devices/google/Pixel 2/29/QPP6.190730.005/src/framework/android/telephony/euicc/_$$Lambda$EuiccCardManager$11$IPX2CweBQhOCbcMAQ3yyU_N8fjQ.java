/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.euicc;

import android.telephony.euicc.EuiccCardManager;
import android.telephony.euicc.EuiccRulesAuthTable;

public final class _$$Lambda$EuiccCardManager$11$IPX2CweBQhOCbcMAQ3yyU_N8fjQ
implements Runnable {
    private final /* synthetic */ EuiccCardManager.ResultCallback f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ EuiccRulesAuthTable f$2;

    public /* synthetic */ _$$Lambda$EuiccCardManager$11$IPX2CweBQhOCbcMAQ3yyU_N8fjQ(EuiccCardManager.ResultCallback resultCallback, int n, EuiccRulesAuthTable euiccRulesAuthTable) {
        this.f$0 = resultCallback;
        this.f$1 = n;
        this.f$2 = euiccRulesAuthTable;
    }

    @Override
    public final void run() {
        EuiccCardManager.11.lambda$onComplete$0(this.f$0, this.f$1, this.f$2);
    }
}

