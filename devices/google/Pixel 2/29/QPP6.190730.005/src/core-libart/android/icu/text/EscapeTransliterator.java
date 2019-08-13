/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.Utility;
import android.icu.text.Replaceable;
import android.icu.text.Transliterator;
import android.icu.text.UTF16;
import android.icu.text.UnicodeFilter;
import android.icu.text.UnicodeSet;

class EscapeTransliterator
extends Transliterator {
    private boolean grokSupplementals;
    private int minDigits;
    private String prefix;
    private int radix;
    private String suffix;
    private EscapeTransliterator supplementalHandler;

    EscapeTransliterator(String string, String string2, String string3, int n, int n2, boolean bl, EscapeTransliterator escapeTransliterator) {
        super(string, null);
        this.prefix = string2;
        this.suffix = string3;
        this.radix = n;
        this.minDigits = n2;
        this.grokSupplementals = bl;
        this.supplementalHandler = escapeTransliterator;
    }

    static void register() {
        Transliterator.registerFactory("Any-Hex/Unicode", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new EscapeTransliterator("Any-Hex/Unicode", "U+", "", 16, 4, true, null);
            }
        });
        Transliterator.registerFactory("Any-Hex/Java", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new EscapeTransliterator("Any-Hex/Java", "\\u", "", 16, 4, false, null);
            }
        });
        Transliterator.registerFactory("Any-Hex/C", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new EscapeTransliterator("Any-Hex/C", "\\u", "", 16, 4, true, new EscapeTransliterator("", "\\U", "", 16, 8, true, null));
            }
        });
        Transliterator.registerFactory("Any-Hex/XML", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new EscapeTransliterator("Any-Hex/XML", "&#x", ";", 16, 1, true, null);
            }
        });
        Transliterator.registerFactory("Any-Hex/XML10", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new EscapeTransliterator("Any-Hex/XML10", "&#", ";", 10, 1, true, null);
            }
        });
        Transliterator.registerFactory("Any-Hex/Perl", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new EscapeTransliterator("Any-Hex/Perl", "\\x{", "}", 16, 1, true, null);
            }
        });
        Transliterator.registerFactory("Any-Hex/Plain", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new EscapeTransliterator("Any-Hex/Plain", "", "", 16, 4, true, null);
            }
        });
        Transliterator.registerFactory("Any-Hex", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new EscapeTransliterator("Any-Hex", "\\u", "", 16, 4, false, null);
            }
        });
    }

    @Override
    public void addSourceTargetSet(UnicodeSet unicodeSet, UnicodeSet object, UnicodeSet unicodeSet2) {
        ((UnicodeSet)object).addAll(this.getFilterAsUnicodeSet(unicodeSet));
        object = this;
        while (object != null) {
            if (unicodeSet.size() != 0) {
                int n;
                unicodeSet2.addAll(((EscapeTransliterator)object).prefix);
                unicodeSet2.addAll(((EscapeTransliterator)object).suffix);
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < (n = ((EscapeTransliterator)object).radix); ++i) {
                    Utility.appendNumber(stringBuilder, i, n, ((EscapeTransliterator)object).minDigits);
                }
                unicodeSet2.addAll(stringBuilder.toString());
            }
            object = ((EscapeTransliterator)object).supplementalHandler;
        }
    }

    @Override
    protected void handleTransliterate(Replaceable replaceable, Transliterator.Position position, boolean bl) {
        int n;
        int n2;
        int n3 = position.start;
        StringBuilder stringBuilder = new StringBuilder(this.prefix);
        int n4 = this.prefix.length();
        boolean bl2 = false;
        for (n2 = position.limit; n3 < n2; n3 += stringBuilder.length(), n2 += stringBuilder.length() - n) {
            int n5 = this.grokSupplementals ? replaceable.char32At(n3) : (int)replaceable.charAt(n3);
            n = this.grokSupplementals ? UTF16.getCharCount(n5) : 1;
            if ((-65536 & n5) != 0 && this.supplementalHandler != null) {
                stringBuilder.setLength(0);
                stringBuilder.append(this.supplementalHandler.prefix);
                EscapeTransliterator escapeTransliterator = this.supplementalHandler;
                Utility.appendNumber(stringBuilder, n5, escapeTransliterator.radix, escapeTransliterator.minDigits);
                stringBuilder.append(this.supplementalHandler.suffix);
                bl2 = true;
            } else {
                if (bl2) {
                    stringBuilder.setLength(0);
                    stringBuilder.append(this.prefix);
                    bl2 = false;
                } else {
                    stringBuilder.setLength(n4);
                }
                Utility.appendNumber(stringBuilder, n5, this.radix, this.minDigits);
                stringBuilder.append(this.suffix);
            }
            replaceable.replace(n3, n3 + n, stringBuilder.toString());
        }
        position.contextLimit += n2 - position.limit;
        position.limit = n2;
        position.start = n3;
    }

}

