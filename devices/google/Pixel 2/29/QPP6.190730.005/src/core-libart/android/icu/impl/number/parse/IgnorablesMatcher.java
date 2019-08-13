/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number.parse;

import android.icu.impl.StaticUnicodeSets;
import android.icu.impl.StringSegment;
import android.icu.impl.number.parse.NumberParseMatcher;
import android.icu.impl.number.parse.ParsedNumber;
import android.icu.impl.number.parse.SymbolMatcher;
import android.icu.text.UnicodeSet;

public class IgnorablesMatcher
extends SymbolMatcher
implements NumberParseMatcher.Flexible {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final IgnorablesMatcher DEFAULT = new IgnorablesMatcher(StaticUnicodeSets.get(StaticUnicodeSets.Key.DEFAULT_IGNORABLES));
    public static final IgnorablesMatcher STRICT = new IgnorablesMatcher(StaticUnicodeSets.get(StaticUnicodeSets.Key.STRICT_IGNORABLES));

    private IgnorablesMatcher(UnicodeSet unicodeSet) {
        super("", unicodeSet);
    }

    public static IgnorablesMatcher getInstance(UnicodeSet unicodeSet) {
        return new IgnorablesMatcher(unicodeSet);
    }

    @Override
    protected void accept(StringSegment stringSegment, ParsedNumber parsedNumber) {
    }

    @Override
    protected boolean isDisabled(ParsedNumber parsedNumber) {
        return false;
    }

    public String toString() {
        return "<IgnorablesMatcher>";
    }
}

