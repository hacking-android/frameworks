/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number.parse;

import android.icu.impl.number.parse.ParsedNumber;
import android.icu.impl.number.parse.ValidationMatcher;

public class RequireCurrencyValidator
extends ValidationMatcher {
    @Override
    public void postProcess(ParsedNumber parsedNumber) {
        if (parsedNumber.currencyCode == null) {
            parsedNumber.flags |= 256;
        }
    }

    public String toString() {
        return "<RequireCurrency>";
    }
}

