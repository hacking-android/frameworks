/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.Replaceable;
import android.icu.text.UnicodeSet;

interface UnicodeReplacer {
    public void addReplacementSetTo(UnicodeSet var1);

    public int replace(Replaceable var1, int var2, int var3, int[] var4);

    public String toReplacerPattern(boolean var1);
}

