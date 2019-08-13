/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number.parse;

import android.icu.impl.StaticUnicodeSets;
import android.icu.impl.StringSegment;
import android.icu.impl.number.parse.ParsedNumber;
import android.icu.impl.number.parse.SymbolMatcher;
import android.icu.text.DecimalFormatSymbols;
import android.icu.text.UnicodeSet;

public class PercentMatcher
extends SymbolMatcher {
    private static final PercentMatcher DEFAULT = new PercentMatcher();

    private PercentMatcher() {
        super(StaticUnicodeSets.Key.PERCENT_SIGN);
    }

    private PercentMatcher(String string) {
        super(string, PercentMatcher.DEFAULT.uniSet);
    }

    public static PercentMatcher getInstance(DecimalFormatSymbols object) {
        if (PercentMatcher.DEFAULT.uniSet.contains((CharSequence)(object = ((DecimalFormatSymbols)object).getPercentString()))) {
            return DEFAULT;
        }
        return new PercentMatcher((String)object);
    }

    @Override
    protected void accept(StringSegment stringSegment, ParsedNumber parsedNumber) {
        parsedNumber.flags |= 2;
        parsedNumber.setCharsConsumed(stringSegment);
    }

    @Override
    protected boolean isDisabled(ParsedNumber parsedNumber) {
        boolean bl = (parsedNumber.flags & 2) != 0;
        return bl;
    }

    public String toString() {
        return "<PercentMatcher>";
    }
}

