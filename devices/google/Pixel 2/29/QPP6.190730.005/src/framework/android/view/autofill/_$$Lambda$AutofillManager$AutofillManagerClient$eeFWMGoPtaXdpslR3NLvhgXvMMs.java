/*
 * Decompiled with CFR 0.145.
 */
package android.view.autofill;

import android.view.autofill.AutofillManager;
import com.android.internal.os.IResultReceiver;

public final class _$$Lambda$AutofillManager$AutofillManagerClient$eeFWMGoPtaXdpslR3NLvhgXvMMs
implements Runnable {
    private final /* synthetic */ AutofillManager f$0;
    private final /* synthetic */ IResultReceiver f$1;

    public /* synthetic */ _$$Lambda$AutofillManager$AutofillManagerClient$eeFWMGoPtaXdpslR3NLvhgXvMMs(AutofillManager autofillManager, IResultReceiver iResultReceiver) {
        this.f$0 = autofillManager;
        this.f$1 = iResultReceiver;
    }

    @Override
    public final void run() {
        AutofillManager.AutofillManagerClient.lambda$getAugmentedAutofillClient$11(this.f$0, this.f$1);
    }
}

