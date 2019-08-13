/*
 * Decompiled with CFR 0.145.
 */
package android.view.autofill;

import android.view.autofill.AutofillManager;
import java.util.List;

public final class _$$Lambda$AutofillManager$AugmentedAutofillManagerClient$k_qssZkEBwVEPdSmrHGsi2QT_3Y
implements Runnable {
    private final /* synthetic */ AutofillManager f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ List f$2;
    private final /* synthetic */ List f$3;

    public /* synthetic */ _$$Lambda$AutofillManager$AugmentedAutofillManagerClient$k_qssZkEBwVEPdSmrHGsi2QT_3Y(AutofillManager autofillManager, int n, List list, List list2) {
        this.f$0 = autofillManager;
        this.f$1 = n;
        this.f$2 = list;
        this.f$3 = list2;
    }

    @Override
    public final void run() {
        AutofillManager.AugmentedAutofillManagerClient.lambda$autofill$0(this.f$0, this.f$1, this.f$2, this.f$3);
    }
}

