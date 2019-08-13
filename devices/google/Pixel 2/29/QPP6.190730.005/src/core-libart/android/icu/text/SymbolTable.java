/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.UnicodeMatcher;
import java.text.ParsePosition;

public interface SymbolTable {
    public static final char SYMBOL_REF = '$';

    public char[] lookup(String var1);

    public UnicodeMatcher lookupMatcher(int var1);

    public String parseReference(String var1, ParsePosition var2, int var3);
}

