/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill;

import android.os.Parcelable;
import android.service.autofill.Sanitizer;
import android.view.autofill.AutofillValue;

public abstract class InternalSanitizer
implements Sanitizer,
Parcelable {
    public abstract AutofillValue sanitize(AutofillValue var1);
}

