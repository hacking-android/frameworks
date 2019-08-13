/*
 * Decompiled with CFR 0.145.
 */
package android.view.autofill;

import android.view.KeyEvent;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillManager;

public final class _$$Lambda$AutofillManager$AutofillManagerClient$xqXjXW0fvc8JdYR5fgGKw9lJc3I
implements Runnable {
    private final /* synthetic */ AutofillManager f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ AutofillId f$2;
    private final /* synthetic */ KeyEvent f$3;

    public /* synthetic */ _$$Lambda$AutofillManager$AutofillManagerClient$xqXjXW0fvc8JdYR5fgGKw9lJc3I(AutofillManager autofillManager, int n, AutofillId autofillId, KeyEvent keyEvent) {
        this.f$0 = autofillManager;
        this.f$1 = n;
        this.f$2 = autofillId;
        this.f$3 = keyEvent;
    }

    @Override
    public final void run() {
        AutofillManager.AutofillManagerClient.lambda$dispatchUnhandledKey$6(this.f$0, this.f$1, this.f$2, this.f$3);
    }
}

