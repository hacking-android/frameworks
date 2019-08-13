/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.Replaceable;
import android.icu.text.Transliterator;
import android.icu.text.UnicodeReplacer;
import android.icu.text.UnicodeSet;

class FunctionReplacer
implements UnicodeReplacer {
    private UnicodeReplacer replacer;
    private Transliterator translit;

    public FunctionReplacer(Transliterator transliterator, UnicodeReplacer unicodeReplacer) {
        this.translit = transliterator;
        this.replacer = unicodeReplacer;
    }

    @Override
    public void addReplacementSetTo(UnicodeSet unicodeSet) {
        unicodeSet.addAll(this.translit.getTargetSet());
    }

    @Override
    public int replace(Replaceable replaceable, int n, int n2, int[] arrn) {
        n2 = this.replacer.replace(replaceable, n, n2, arrn);
        return this.translit.transliterate(replaceable, n, n + n2) - n;
    }

    @Override
    public String toReplacerPattern(boolean bl) {
        StringBuilder stringBuilder = new StringBuilder("&");
        stringBuilder.append(this.translit.getID());
        stringBuilder.append("( ");
        stringBuilder.append(this.replacer.toReplacerPattern(bl));
        stringBuilder.append(" )");
        return stringBuilder.toString();
    }
}

