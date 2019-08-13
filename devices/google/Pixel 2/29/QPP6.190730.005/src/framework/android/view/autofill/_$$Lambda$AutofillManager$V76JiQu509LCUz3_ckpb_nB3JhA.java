/*
 * Decompiled with CFR 0.145.
 */
package android.view.autofill;

import android.view.autofill.AutofillManager;
import android.view.autofill.IAutoFillManager;
import android.view.autofill.IAutoFillManagerClient;

public final class _$$Lambda$AutofillManager$V76JiQu509LCUz3_ckpb_nB3JhA
implements Runnable {
    private final /* synthetic */ IAutoFillManager f$0;
    private final /* synthetic */ IAutoFillManagerClient f$1;
    private final /* synthetic */ int f$2;

    public /* synthetic */ _$$Lambda$AutofillManager$V76JiQu509LCUz3_ckpb_nB3JhA(IAutoFillManager iAutoFillManager, IAutoFillManagerClient iAutoFillManagerClient, int n) {
        this.f$0 = iAutoFillManager;
        this.f$1 = iAutoFillManagerClient;
        this.f$2 = n;
    }

    @Override
    public final void run() {
        AutofillManager.lambda$ensureServiceClientAddedIfNeededLocked$1(this.f$0, this.f$1, this.f$2);
    }
}

