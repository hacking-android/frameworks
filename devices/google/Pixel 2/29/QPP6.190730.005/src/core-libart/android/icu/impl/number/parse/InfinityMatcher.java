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

public class InfinityMatcher
extends SymbolMatcher {
    private static final InfinityMatcher DEFAULT = new InfinityMatcher();

    private InfinityMatcher() {
        super(StaticUnicodeSets.Key.INFINITY);
    }

    private InfinityMatcher(String string) {
        super(string, InfinityMatcher.DEFAULT.uniSet);
    }

    public static InfinityMatcher getInstance(DecimalFormatSymbols object) {
        if (ParsingUtils.safeContains(InfinityMatcher.DEFAULT.uniSet, (CharSequence)(object = ((DecimalFormatSymbols)object).getInfinity()))) {
            return DEFAULT;
        }
        return new InfinityMatcher((String)object);
    }

    @Override
    protected void accept(StringSegment stringSegment, ParsedNumber parsedNumber) {
        parsedNumber.flags |= 128;
        parsedNumber.setCharsConsumed(stringSegment);
    }

    @Override
    protected boolean isDisabled(ParsedNumber parsedNumber) {
        boolean bl = (parsedNumber.flags & 128) != 0;
        return bl;
    }

    public String toString() {
        return "<InfinityMatcher>";
    }
}

