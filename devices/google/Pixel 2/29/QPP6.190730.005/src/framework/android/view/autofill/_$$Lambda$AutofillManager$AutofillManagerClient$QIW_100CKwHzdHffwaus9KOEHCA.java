/*
 * Decompiled with CFR 0.145.
 */
package android.view.autofill;

import android.view.autofill.AutofillManager;

public final class _$$Lambda$AutofillManager$AutofillManagerClient$QIW_100CKwHzdHffwaus9KOEHCA
implements Runnable {
    private final /* synthetic */ AutofillManager f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ boolean f$2;

    public /* synthetic */ _$$Lambda$AutofillManager$AutofillManagerClient$QIW_100CKwHzdHffwaus9KOEHCA(AutofillManager autofillManager, int n, boolean bl) {
        this.f$0 = autofillManager;
        this.f$1 = n;
        this.f$2 = bl;
    }

    @Override
    public final void run() {
        AutofillManager.AutofillManagerClient.lambda$setSaveUiState$9(this.f$0, this.f$1, this.f$2);
    }
}

