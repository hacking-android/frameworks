/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number.parse;

import android.icu.impl.number.parse.ParsedNumber;
import android.icu.impl.number.parse.ValidationMatcher;

public class RequireAffixValidator
extends ValidationMatcher {
    @Override
    public void postProcess(ParsedNumber parsedNumber) {
        if (parsedNumber.prefix == null || parsedNumber.suffix == null) {
            parsedNumber.flags |= 256;
        }
    }

    public String toString() {
        return "<RequireAffix>";
    }
}

