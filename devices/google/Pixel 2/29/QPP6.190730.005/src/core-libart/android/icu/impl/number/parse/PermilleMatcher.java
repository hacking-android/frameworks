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

public class PermilleMatcher
extends SymbolMatcher {
    private static final PermilleMatcher DEFAULT = new PermilleMatcher();

    private PermilleMatcher() {
        super(StaticUnicodeSets.Key.PERMILLE_SIGN);
    }

    private PermilleMatcher(String string) {
        super(string, PermilleMatcher.DEFAULT.uniSet);
    }

    public static PermilleMatcher getInstance(DecimalFormatSymbols object) {
        if (PermilleMatcher.DEFAULT.uniSet.contains((CharSequence)(object = ((DecimalFormatSymbols)object).getPerMillString()))) {
            return DEFAULT;
        }
        return new PermilleMatcher((String)object);
    }

    @Override
    protected void accept(StringSegment stringSegment, ParsedNumber parsedNumber) {
        parsedNumber.flags |= 4;
        parsedNumber.setCharsConsumed(stringSegment);
    }

    @Override
    protected boolean isDisabled(ParsedNumber parsedNumber) {
        boolean bl = (parsedNumber.flags & 4) != 0;
        return bl;
    }

    public String toString() {
        return "<PermilleMatcher>";
    }
}

