/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.Replaceable;
import android.icu.text.Transliterator;
import android.icu.text.UnicodeFilter;
import android.icu.text.UnicodeSet;

class NullTransliterator
extends Transliterator {
    static final String SHORT_ID = "Null";
    static final String _ID = "Any-Null";

    public NullTransliterator() {
        super(_ID, null);
    }

    @Override
    public void addSourceTargetSet(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, UnicodeSet unicodeSet3) {
    }

    @Override
    protected void handleTransliterate(Replaceable replaceable, Transliterator.Position position, boolean bl) {
        position.start = position.limit;
    }
}

