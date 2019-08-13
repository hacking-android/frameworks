/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill.augmented;

import android.annotation.SystemApi;
import android.service.autofill.augmented.AugmentedAutofillService;
import android.service.autofill.augmented.FillResponse;
import android.service.autofill.augmented.FillWindow;
import android.util.Log;

@SystemApi
public final class FillCallback {
    private static final String TAG = FillCallback.class.getSimpleName();
    private final AugmentedAutofillService.AutofillProxy mProxy;

    FillCallback(AugmentedAutofillService.AutofillProxy autofillProxy) {
        this.mProxy = autofillProxy;
    }

    public void onSuccess(FillResponse object) {
        if (AugmentedAutofillService.sDebug) {
            String string2 = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onSuccess(): ");
            stringBuilder.append(object);
            Log.d(string2, stringBuilder.toString());
        }
        if (object == null) {
            this.mProxy.report(1);
            return;
        }
        if ((object = ((FillResponse)object).getFillWindow()) != null) {
            ((FillWindow)object).show();
        }
    }
}

