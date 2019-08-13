/*
 * Decompiled with CFR 0.145.
 */
package android.view.autofill;

import android.view.autofill.AutofillId;
import android.view.autofill.AutofillManager;

public final class _$$Lambda$AutofillManager$AutofillManagerClient$BPlC2x7GLNHFS92rPUSzbcpFhUc
implements Runnable {
    private final /* synthetic */ AutofillManager f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ AutofillId[] f$2;
    private final /* synthetic */ boolean f$3;
    private final /* synthetic */ boolean f$4;
    private final /* synthetic */ AutofillId[] f$5;
    private final /* synthetic */ AutofillId f$6;

    public /* synthetic */ _$$Lambda$AutofillManager$AutofillManagerClient$BPlC2x7GLNHFS92rPUSzbcpFhUc(AutofillManager autofillManager, int n, AutofillId[] arrautofillId, boolean bl, boolean bl2, AutofillId[] arrautofillId2, AutofillId autofillId) {
        this.f$0 = autofillManager;
        this.f$1 = n;
        this.f$2 = arrautofillId;
        this.f$3 = bl;
        this.f$4 = bl2;
        this.f$5 = arrautofillId2;
        this.f$6 = autofillId;
    }

    @Override
    public final void run() {
        AutofillManager.AutofillManagerClient.lambda$setTrackedViews$8(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, this.f$6);
    }
}

