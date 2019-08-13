/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill;

import android.view.autofill.AutofillId;
import com.android.internal.util.Preconditions;

final class AutofillServiceHelper {
    private AutofillServiceHelper() {
        throw new UnsupportedOperationException("contains static members only");
    }

    static AutofillId[] assertValid(AutofillId[] object) {
        boolean bl = object != null && ((AutofillId[])object).length > 0;
        Preconditions.checkArgument(bl, "must have at least one id");
        for (int i = 0; i < ((AutofillId[])object).length; ++i) {
            if (object[i] != null) {
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("ids[");
            ((StringBuilder)object).append(i);
            ((StringBuilder)object).append("] must not be null");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        return object;
    }
}

