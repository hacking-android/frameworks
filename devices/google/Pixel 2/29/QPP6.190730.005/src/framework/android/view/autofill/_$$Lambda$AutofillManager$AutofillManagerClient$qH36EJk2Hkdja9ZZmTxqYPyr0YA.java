/*
 * Decompiled with CFR 0.145.
 */
package android.view.autofill;

import android.view.autofill.AutofillManager;

public final class _$$Lambda$AutofillManager$AutofillManagerClient$qH36EJk2Hkdja9ZZmTxqYPyr0YA
implements Runnable {
    private final /* synthetic */ AutofillManager f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$AutofillManager$AutofillManagerClient$qH36EJk2Hkdja9ZZmTxqYPyr0YA(AutofillManager autofillManager, int n) {
        this.f$0 = autofillManager;
        this.f$1 = n;
    }

    @Override
    public final void run() {
        AutofillManager.AutofillManagerClient.lambda$setState$0(this.f$0, this.f$1);
    }
}

