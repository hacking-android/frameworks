/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill;

import android.service.autofill.InternalValidator;
import android.service.autofill.NegationValidator;
import android.service.autofill.OptionalValidators;
import android.service.autofill.RequiredValidators;
import android.service.autofill.Validator;
import com.android.internal.util.Preconditions;

public final class Validators {
    private Validators() {
        throw new UnsupportedOperationException("contains static methods only");
    }

    public static Validator and(Validator ... arrvalidator) {
        return new RequiredValidators(Validators.getInternalValidators(arrvalidator));
    }

    private static InternalValidator[] getInternalValidators(Validator[] arrvalidator) {
        Preconditions.checkArrayElementsNotNull(arrvalidator, "validators");
        InternalValidator[] arrinternalValidator = new InternalValidator[arrvalidator.length];
        for (int i = 0; i < arrvalidator.length; ++i) {
            boolean bl = arrvalidator[i] instanceof InternalValidator;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("element ");
            stringBuilder.append(i);
            stringBuilder.append(" not provided by Android System: ");
            stringBuilder.append(arrvalidator[i]);
            Preconditions.checkArgument(bl, stringBuilder.toString());
            arrinternalValidator[i] = (InternalValidator)arrvalidator[i];
        }
        return arrinternalValidator;
    }

    public static Validator not(Validator validator) {
        boolean bl = validator instanceof InternalValidator;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("validator not provided by Android System: ");
        stringBuilder.append(validator);
        Preconditions.checkArgument(bl, stringBuilder.toString());
        return new NegationValidator((InternalValidator)validator);
    }

    public static Validator or(Validator ... arrvalidator) {
        return new OptionalValidators(Validators.getInternalValidators(arrvalidator));
    }
}

