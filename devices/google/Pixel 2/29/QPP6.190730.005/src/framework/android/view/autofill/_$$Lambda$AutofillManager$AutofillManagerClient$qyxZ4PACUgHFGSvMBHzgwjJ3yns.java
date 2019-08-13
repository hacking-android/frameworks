/*
 * Decompiled with CFR 0.145.
 */
package android.view.autofill;

import android.content.Intent;
import android.content.IntentSender;
import android.view.autofill.AutofillManager;

public final class _$$Lambda$AutofillManager$AutofillManagerClient$qyxZ4PACUgHFGSvMBHzgwjJ3yns
implements Runnable {
    private final /* synthetic */ AutofillManager f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ int f$2;
    private final /* synthetic */ IntentSender f$3;
    private final /* synthetic */ Intent f$4;

    public /* synthetic */ _$$Lambda$AutofillManager$AutofillManagerClient$qyxZ4PACUgHFGSvMBHzgwjJ3yns(AutofillManager autofillManager, int n, int n2, IntentSender intentSender, Intent intent) {
        this.f$0 = autofillManager;
        this.f$1 = n;
        this.f$2 = n2;
        this.f$3 = intentSender;
        this.f$4 = intent;
    }

    @Override
    public final void run() {
        AutofillManager.AutofillManagerClient.lambda$authenticate$2(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4);
    }
}

