/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number.parse;

import android.icu.impl.StringSegment;
import android.icu.impl.number.parse.NumberParseMatcher;
import android.icu.impl.number.parse.ParsedNumber;
import android.icu.impl.number.parse.SymbolMatcher;
import android.icu.text.UnicodeSet;

public class PaddingMatcher
extends SymbolMatcher
implements NumberParseMatcher.Flexible {
    private PaddingMatcher(String string) {
        super(string, UnicodeSet.EMPTY);
    }

    public static PaddingMatcher getInstance(String string) {
        return new PaddingMatcher(string);
    }

    @Override
    protected void accept(StringSegment stringSegment, ParsedNumber parsedNumber) {
    }

    @Override
    protected boolean isDisabled(ParsedNumber parsedNumber) {
        return false;
    }

    public String toString() {
        return "<PaddingMatcher>";
    }
}

