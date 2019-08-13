/*
 * Decompiled with CFR 0.145.
 */
package android.view.autofill;

import android.view.autofill.AutofillManager;
import java.util.List;

public final class _$$Lambda$AutofillManager$AutofillManagerClient$_IhPS_W7AwZ4M9TKIIFigmQd5SE
implements Runnable {
    private final /* synthetic */ AutofillManager f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ List f$2;

    public /* synthetic */ _$$Lambda$AutofillManager$AutofillManagerClient$_IhPS_W7AwZ4M9TKIIFigmQd5SE(AutofillManager autofillManager, int n, List list) {
        this.f$0 = autofillManager;
        this.f$1 = n;
        this.f$2 = list;
    }

    @Override
    public final void run() {
        AutofillManager.AutofillManagerClient.lambda$setSessionFinished$10(this.f$0, this.f$1, this.f$2);
    }
}

