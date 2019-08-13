/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number.parse;

import android.icu.impl.StaticUnicodeSets;
import android.icu.impl.StringSegment;
import android.icu.impl.number.parse.ParsedNumber;
import android.icu.impl.number.parse.ParsingUtils;
import android.icu.impl.number.parse.SymbolMatcher;
import android.icu.text.DecimalFormatSymbols;
import android.icu.text.UnicodeSet;

public class MinusSignMatcher
extends SymbolMatcher {
    private static final MinusSignMatcher DEFAULT = new MinusSignMatcher(false);
    private static final MinusSignMatcher DEFAULT_ALLOW_TRAILING = new MinusSignMatcher(true);
    private final boolean allowTrailing;

    private MinusSignMatcher(String string, boolean bl) {
        super(string, MinusSignMatcher.DEFAULT.uniSet);
        this.allowTrailing = bl;
    }

    private MinusSignMatcher(boolean bl) {
        super(StaticUnicodeSets.Key.MINUS_SIGN);
        this.allowTrailing = bl;
    }

    public static MinusSignMatcher getInstance(DecimalFormatSymbols object, boolean bl) {
        if (ParsingUtils.safeContains(MinusSignMatcher.DEFAULT.uniSet, (CharSequence)(object = ((DecimalFormatSymbols)object).getMinusSignString()))) {
            object = bl ? DEFAULT_ALLOW_TRAILING : DEFAULT;
            return object;
        }
        return new MinusSignMatcher((String)object, bl);
    }

    @Override
    protected void accept(StringSegment stringSegment, ParsedNumber parsedNumber) {
        parsedNumber.flags |= 1;
        parsedNumber.setCharsConsumed(stringSegment);
    }

    @Override
    protected boolean isDisabled(ParsedNumber parsedNumber) {
        boolean bl = !this.allowTrailing && parsedNumber.seenNumber();
        return bl;
    }

    public String toString() {
        return "<MinusSignMatcher>";
    }
}

