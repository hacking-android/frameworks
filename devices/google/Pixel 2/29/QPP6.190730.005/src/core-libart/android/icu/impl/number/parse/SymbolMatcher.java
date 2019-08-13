/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number.parse;

import android.icu.impl.StaticUnicodeSets;
import android.icu.impl.StringSegment;
import android.icu.impl.number.parse.NumberParseMatcher;
import android.icu.impl.number.parse.ParsedNumber;
import android.icu.text.UnicodeSet;

public abstract class SymbolMatcher
implements NumberParseMatcher {
    protected final String string;
    protected final UnicodeSet uniSet;

    protected SymbolMatcher(StaticUnicodeSets.Key key) {
        this.string = "";
        this.uniSet = StaticUnicodeSets.get(key);
    }

    protected SymbolMatcher(String string, UnicodeSet unicodeSet) {
        this.string = string;
        this.uniSet = unicodeSet;
    }

    protected abstract void accept(StringSegment var1, ParsedNumber var2);

    public UnicodeSet getSet() {
        return this.uniSet;
    }

    protected abstract boolean isDisabled(ParsedNumber var1);

    @Override
    public boolean match(StringSegment stringSegment, ParsedNumber parsedNumber) {
        boolean bl = this.isDisabled(parsedNumber);
        boolean bl2 = false;
        if (bl) {
            return false;
        }
        int n = 0;
        if (!this.string.isEmpty()) {
            int n2;
            n = n2 = stringSegment.getCommonPrefixLength(this.string);
            if (n2 == this.string.length()) {
                stringSegment.adjustOffset(this.string.length());
                this.accept(stringSegment, parsedNumber);
                return false;
            }
        }
        if (stringSegment.startsWith(this.uniSet)) {
            stringSegment.adjustOffsetByCodePoint();
            this.accept(stringSegment, parsedNumber);
            return false;
        }
        if (n == stringSegment.length()) {
            bl2 = true;
        }
        return bl2;
    }

    @Override
    public void postProcess(ParsedNumber parsedNumber) {
    }

    @Override
    public boolean smokeTest(StringSegment stringSegment) {
        boolean bl = stringSegment.startsWith(this.uniSet) || stringSegment.startsWith(this.string);
        return bl;
    }
}

