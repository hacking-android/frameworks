/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill;

import android.os.Parcelable;
import android.service.autofill.Validator;
import android.service.autofill.ValueFinder;

public abstract class InternalValidator
implements Validator,
Parcelable {
    public abstract boolean isValid(ValueFinder var1);
}

