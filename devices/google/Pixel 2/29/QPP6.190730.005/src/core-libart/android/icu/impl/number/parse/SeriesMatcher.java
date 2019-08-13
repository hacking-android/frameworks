/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number.parse;

import android.icu.impl.StringSegment;
import android.icu.impl.number.parse.NumberParseMatcher;
import android.icu.impl.number.parse.ParsedNumber;
import java.util.ArrayList;
import java.util.List;

public class SeriesMatcher
implements NumberParseMatcher {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    protected boolean frozen = false;
    protected List<NumberParseMatcher> matchers = null;

    public void addMatcher(NumberParseMatcher numberParseMatcher) {
        if (this.matchers == null) {
            this.matchers = new ArrayList<NumberParseMatcher>();
        }
        this.matchers.add(numberParseMatcher);
    }

    public void freeze() {
        this.frozen = true;
    }

    public int length() {
        List<NumberParseMatcher> list = this.matchers;
        int n = list == null ? 0 : list.size();
        return n;
    }

    @Override
    public boolean match(StringSegment stringSegment, ParsedNumber parsedNumber) {
        if (this.matchers == null) {
            return false;
        }
        ParsedNumber parsedNumber2 = new ParsedNumber();
        parsedNumber2.copyFrom(parsedNumber);
        int n = stringSegment.getOffset();
        boolean bl = true;
        int n2 = 0;
        while (n2 < this.matchers.size()) {
            NumberParseMatcher numberParseMatcher = this.matchers.get(n2);
            int n3 = stringSegment.getOffset();
            bl = stringSegment.length() != 0 ? numberParseMatcher.match(stringSegment, parsedNumber) : true;
            int n4 = stringSegment.getOffset() != n3 ? 1 : 0;
            boolean bl2 = numberParseMatcher instanceof NumberParseMatcher.Flexible;
            if (n4 != 0 && bl2) continue;
            if (n4 != 0) {
                n2 = n4 = n2 + 1;
                if (n4 >= this.matchers.size()) continue;
                n2 = n4;
                if (stringSegment.getOffset() == parsedNumber.charEnd) continue;
                n2 = n4;
                if (parsedNumber.charEnd <= n3) continue;
                stringSegment.setOffset(parsedNumber.charEnd);
                n2 = n4;
                continue;
            }
            if (bl2) {
                ++n2;
                continue;
            }
            stringSegment.setOffset(n);
            parsedNumber.copyFrom(parsedNumber2);
            return bl;
        }
        return bl;
    }

    @Override
    public void postProcess(ParsedNumber parsedNumber) {
        if (this.matchers == null) {
            return;
        }
        for (int i = 0; i < this.matchers.size(); ++i) {
            this.matchers.get(i).postProcess(parsedNumber);
        }
    }

    @Override
    public boolean smokeTest(StringSegment stringSegment) {
        List<NumberParseMatcher> list = this.matchers;
        if (list == null) {
            return false;
        }
        return list.get(0).smokeTest(stringSegment);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<SeriesMatcher ");
        stringBuilder.append(this.matchers);
        stringBuilder.append(">");
        return stringBuilder.toString();
    }
}

