/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.Replaceable;
import android.icu.text.UnicodeSet;

public interface UnicodeMatcher {
    public static final char ETHER = '\uffff';
    public static final int U_MATCH = 2;
    public static final int U_MISMATCH = 0;
    public static final int U_PARTIAL_MATCH = 1;

    public void addMatchSetTo(UnicodeSet var1);

    public int matches(Replaceable var1, int[] var2, int var3, boolean var4);

    public boolean matchesIndexValue(int var1);

    public String toPattern(boolean var1);
}

