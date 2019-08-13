/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number.parse;

import android.icu.impl.StringSegment;
import android.icu.impl.number.parse.NumberParseMatcher;
import android.icu.impl.number.parse.ParsedNumber;

public abstract class ValidationMatcher
implements NumberParseMatcher {
    @Override
    public boolean match(StringSegment stringSegment, ParsedNumber parsedNumber) {
        return false;
    }

    @Override
    public boolean smokeTest(StringSegment stringSegment) {
        return false;
    }
}

