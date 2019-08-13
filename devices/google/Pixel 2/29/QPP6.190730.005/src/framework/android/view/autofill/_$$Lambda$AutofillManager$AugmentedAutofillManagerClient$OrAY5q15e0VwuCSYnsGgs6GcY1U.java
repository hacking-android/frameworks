/*
 * Decompiled with CFR 0.145.
 */
package android.view.autofill;

import android.graphics.Rect;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillManager;
import android.view.autofill.IAutofillWindowPresenter;

public final class _$$Lambda$AutofillManager$AugmentedAutofillManagerClient$OrAY5q15e0VwuCSYnsGgs6GcY1U
implements Runnable {
    private final /* synthetic */ AutofillManager f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ AutofillId f$2;
    private final /* synthetic */ int f$3;
    private final /* synthetic */ int f$4;
    private final /* synthetic */ Rect f$5;
    private final /* synthetic */ IAutofillWindowPresenter f$6;

    public /* synthetic */ _$$Lambda$AutofillManager$AugmentedAutofillManagerClient$OrAY5q15e0VwuCSYnsGgs6GcY1U(AutofillManager autofillManager, int n, AutofillId autofillId, int n2, int n3, Rect rect, IAutofillWindowPresenter iAutofillWindowPresenter) {
        this.f$0 = autofillManager;
        this.f$1 = n;
        this.f$2 = autofillId;
        this.f$3 = n2;
        this.f$4 = n3;
        this.f$5 = rect;
        this.f$6 = iAutofillWindowPresenter;
    }

    @Override
    public final void run() {
        AutofillManager.AugmentedAutofillManagerClient.lambda$requestShowFillUi$1(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, this.f$6);
    }
}

