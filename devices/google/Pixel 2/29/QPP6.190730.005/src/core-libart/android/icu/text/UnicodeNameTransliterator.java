/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.lang.UCharacter;
import android.icu.text.Replaceable;
import android.icu.text.Transliterator;
import android.icu.text.UTF16;
import android.icu.text.UnicodeFilter;
import android.icu.text.UnicodeSet;

class UnicodeNameTransliterator
extends Transliterator {
    static final char CLOSE_DELIM = '}';
    static final String OPEN_DELIM = "\\N{";
    static final int OPEN_DELIM_LEN = 3;
    static final String _ID = "Any-Name";

    public UnicodeNameTransliterator(UnicodeFilter unicodeFilter) {
        super(_ID, unicodeFilter);
    }

    static void register() {
        Transliterator.registerFactory(_ID, new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new UnicodeNameTransliterator(null);
            }
        });
    }

    @Override
    public void addSourceTargetSet(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, UnicodeSet unicodeSet3) {
        if ((unicodeSet = this.getFilterAsUnicodeSet(unicodeSet)).size() > 0) {
            unicodeSet2.addAll(unicodeSet);
            unicodeSet3.addAll(48, 57).addAll(65, 90).add(45).add(32).addAll(OPEN_DELIM).add(125).addAll(97, 122).add(60).add(62).add(40).add(41);
        }
    }

    @Override
    protected void handleTransliterate(Replaceable replaceable, Transliterator.Position position, boolean bl) {
        int n = position.start;
        int n2 = position.limit;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(OPEN_DELIM);
        while (n < n2) {
            int n3 = replaceable.char32At(n);
            String string = UCharacter.getExtendedName(n3);
            if (string != null) {
                stringBuilder.setLength(3);
                stringBuilder.append(string);
                stringBuilder.append('}');
                n3 = UTF16.getCharCount(n3);
                replaceable.replace(n, n + n3, stringBuilder.toString());
                int n4 = stringBuilder.length();
                n3 = n2 + (n4 - n3);
                n2 = n += n4;
            } else {
                n3 = n2;
                n2 = ++n;
            }
            n = n2;
            n2 = n3;
        }
        position.contextLimit += n2 - position.limit;
        position.limit = n2;
        position.start = n;
    }

}

