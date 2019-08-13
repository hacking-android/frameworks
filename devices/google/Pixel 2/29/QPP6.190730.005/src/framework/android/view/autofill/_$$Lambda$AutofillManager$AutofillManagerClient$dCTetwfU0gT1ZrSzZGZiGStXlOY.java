/*
 * Decompiled with CFR 0.145.
 */
package android.view.autofill;

import android.view.autofill.AutofillId;
import android.view.autofill.AutofillManager;

public final class _$$Lambda$AutofillManager$AutofillManagerClient$dCTetwfU0gT1ZrSzZGZiGStXlOY
implements Runnable {
    private final /* synthetic */ AutofillManager f$0;
    private final /* synthetic */ AutofillId f$1;

    public /* synthetic */ _$$Lambda$AutofillManager$AutofillManagerClient$dCTetwfU0gT1ZrSzZGZiGStXlOY(AutofillManager autofillManager, AutofillId autofillId) {
        this.f$0 = autofillManager;
        this.f$1 = autofillId;
    }

    @Override
    public final void run() {
        AutofillManager.AutofillManagerClient.lambda$requestHideFillUi$4(this.f$0, this.f$1);
    }
}

