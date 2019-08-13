/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number.parse;

import android.icu.impl.StringSegment;
import android.icu.impl.number.parse.NumberParseMatcher;
import android.icu.impl.number.parse.ParsedNumber;

public class CodePointMatcher
implements NumberParseMatcher {
    private final int cp;

    private CodePointMatcher(int n) {
        this.cp = n;
    }

    public static CodePointMatcher getInstance(int n) {
        return new CodePointMatcher(n);
    }

    @Override
    public boolean match(StringSegment stringSegment, ParsedNumber parsedNumber) {
        if (stringSegment.startsWith(this.cp)) {
            stringSegment.adjustOffsetByCodePoint();
            parsedNumber.setCharsConsumed(stringSegment);
        }
        return false;
    }

    @Override
    public void postProcess(ParsedNumber parsedNumber) {
    }

    @Override
    public boolean smokeTest(StringSegment stringSegment) {
        return stringSegment.startsWith(this.cp);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<CodePointMatcher U+");
        stringBuilder.append(Integer.toHexString(this.cp));
        stringBuilder.append(">");
        return stringBuilder.toString();
    }
}

