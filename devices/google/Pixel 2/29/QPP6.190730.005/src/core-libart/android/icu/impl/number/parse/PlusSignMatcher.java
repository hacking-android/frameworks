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

public class PlusSignMatcher
extends SymbolMatcher {
    private static final PlusSignMatcher DEFAULT = new PlusSignMatcher(false);
    private static final PlusSignMatcher DEFAULT_ALLOW_TRAILING = new PlusSignMatcher(true);
    private final boolean allowTrailing;

    private PlusSignMatcher(String string, boolean bl) {
        super(string, PlusSignMatcher.DEFAULT.uniSet);
        this.allowTrailing = bl;
    }

    private PlusSignMatcher(boolean bl) {
        super(StaticUnicodeSets.Key.PLUS_SIGN);
        this.allowTrailing = bl;
    }

    public static PlusSignMatcher getInstance(DecimalFormatSymbols object, boolean bl) {
        if (ParsingUtils.safeContains(PlusSignMatcher.DEFAULT.uniSet, (CharSequence)(object = ((DecimalFormatSymbols)object).getPlusSignString()))) {
            object = bl ? DEFAULT_ALLOW_TRAILING : DEFAULT;
            return object;
        }
        return new PlusSignMatcher((String)object, bl);
    }

    @Override
    protected void accept(StringSegment stringSegment, ParsedNumber parsedNumber) {
        parsedNumber.setCharsConsumed(stringSegment);
    }

    @Override
    protected boolean isDisabled(ParsedNumber parsedNumber) {
        boolean bl = !this.allowTrailing && parsedNumber.seenNumber();
        return bl;
    }

    public String toString() {
        return "<PlusSignMatcher>";
    }
}

