/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number.parse;

import android.icu.impl.number.parse.ParsedNumber;
import android.icu.impl.number.parse.ValidationMatcher;

public class RequireDecimalSeparatorValidator
extends ValidationMatcher {
    private static final RequireDecimalSeparatorValidator A = new RequireDecimalSeparatorValidator(true);
    private static final RequireDecimalSeparatorValidator B = new RequireDecimalSeparatorValidator(false);
    private final boolean patternHasDecimalSeparator;

    private RequireDecimalSeparatorValidator(boolean bl) {
        this.patternHasDecimalSeparator = bl;
    }

    public static RequireDecimalSeparatorValidator getInstance(boolean bl) {
        RequireDecimalSeparatorValidator requireDecimalSeparatorValidator = bl ? A : B;
        return requireDecimalSeparatorValidator;
    }

    @Override
    public void postProcess(ParsedNumber parsedNumber) {
        boolean bl = (parsedNumber.flags & 32) != 0;
        if (bl != this.patternHasDecimalSeparator) {
            parsedNumber.flags |= 256;
        }
    }

    public String toString() {
        return "<RequireDecimalSeparator>";
    }
}

