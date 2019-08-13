/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill;

import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;

public interface ValueFinder {
    default public String findByAutofillId(AutofillId object) {
        object = (object = this.findRawValueByAutofillId((AutofillId)object)) != null && ((AutofillValue)object).isText() ? ((AutofillValue)object).getTextValue().toString() : null;
        return object;
    }

    public AutofillValue findRawValueByAutofillId(AutofillId var1);
}

