/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number.parse;

import android.icu.impl.StringSegment;
import android.icu.impl.number.parse.ParsedNumber;
import android.icu.impl.number.parse.SymbolMatcher;
import android.icu.text.DecimalFormatSymbols;
import android.icu.text.UnicodeSet;

public class NanMatcher
extends SymbolMatcher {
    private static final NanMatcher DEFAULT = new NanMatcher("NaN");

    private NanMatcher(String string) {
        super(string, UnicodeSet.EMPTY);
    }

    public static NanMatcher getInstance(DecimalFormatSymbols object, int n) {
        if (NanMatcher.DEFAULT.string.equals(object = ((DecimalFormatSymbols)object).getNaN())) {
            return DEFAULT;
        }
        return new NanMatcher((String)object);
    }

    @Override
    protected void accept(StringSegment stringSegment, ParsedNumber parsedNumber) {
        parsedNumber.flags |= 64;
        parsedNumber.setCharsConsumed(stringSegment);
    }

    @Override
    protected boolean isDisabled(ParsedNumber parsedNumber) {
        return parsedNumber.seenNumber();
    }

    public String toString() {
        return "<NanMatcher>";
    }
}

