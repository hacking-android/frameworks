/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.Replaceable;
import android.icu.text.UTF16;
import android.icu.text.UnicodeMatcher;

public abstract class UnicodeFilter
implements UnicodeMatcher {
    @Deprecated
    protected UnicodeFilter() {
    }

    public abstract boolean contains(int var1);

    @Override
    public int matches(Replaceable replaceable, int[] arrn, int n, boolean bl) {
        int n2;
        if (arrn[0] < n && this.contains(n2 = replaceable.char32At(arrn[0]))) {
            arrn[0] = arrn[0] + UTF16.getCharCount(n2);
            return 2;
        }
        if (arrn[0] > n && this.contains(replaceable.char32At(arrn[0]))) {
            arrn[0] = arrn[0] - 1;
            if (arrn[0] >= 0) {
                arrn[0] = arrn[0] - (UTF16.getCharCount(replaceable.char32At(arrn[0])) - 1);
            }
            return 2;
        }
        return bl && arrn[0] == n;
    }
}

