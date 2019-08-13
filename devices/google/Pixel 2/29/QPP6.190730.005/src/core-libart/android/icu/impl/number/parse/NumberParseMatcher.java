/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number.parse;

import android.icu.impl.StringSegment;
import android.icu.impl.number.parse.ParsedNumber;

public interface NumberParseMatcher {
    public boolean match(StringSegment var1, ParsedNumber var2);

    public void postProcess(ParsedNumber var1);

    public boolean smokeTest(StringSegment var1);

    public static interface Flexible {
    }

}

