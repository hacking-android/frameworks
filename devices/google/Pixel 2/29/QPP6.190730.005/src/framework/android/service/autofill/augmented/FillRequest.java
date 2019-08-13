/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill.augmented;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.service.autofill.augmented.AugmentedAutofillService;
import android.service.autofill.augmented.PresentationParams;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;

@SystemApi
public final class FillRequest {
    final AugmentedAutofillService.AutofillProxy mProxy;

    FillRequest(AugmentedAutofillService.AutofillProxy autofillProxy) {
        this.mProxy = autofillProxy;
    }

    public ComponentName getActivityComponent() {
        return this.mProxy.componentName;
    }

    public AutofillId getFocusedId() {
        return this.mProxy.getFocusedId();
    }

    public AutofillValue getFocusedValue() {
        return this.mProxy.getFocusedValue();
    }

    public PresentationParams getPresentationParams() {
        return this.mProxy.getSmartSuggestionParams();
    }

    public int getTaskId() {
        return this.mProxy.taskId;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("FillRequest[act=");
        stringBuilder.append(this.getActivityComponent().flattenToShortString());
        stringBuilder.append(", id=");
        stringBuilder.append(this.mProxy.getFocusedId());
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

