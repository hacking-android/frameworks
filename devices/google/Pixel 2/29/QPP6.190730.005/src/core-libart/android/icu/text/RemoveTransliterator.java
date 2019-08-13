/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.Replaceable;
import android.icu.text.Transliterator;
import android.icu.text.UnicodeFilter;
import android.icu.text.UnicodeSet;

class RemoveTransliterator
extends Transliterator {
    private static final String _ID = "Any-Remove";

    public RemoveTransliterator() {
        super(_ID, null);
    }

    static void register() {
        Transliterator.registerFactory(_ID, new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new RemoveTransliterator();
            }
        });
        Transliterator.registerSpecialInverse("Remove", "Null", false);
    }

    @Override
    public void addSourceTargetSet(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, UnicodeSet unicodeSet3) {
        unicodeSet2.addAll(this.getFilterAsUnicodeSet(unicodeSet));
    }

    @Override
    protected void handleTransliterate(Replaceable replaceable, Transliterator.Position position, boolean bl) {
        replaceable.replace(position.start, position.limit, "");
        int n = position.limit - position.start;
        position.contextLimit -= n;
        position.limit -= n;
    }

}

