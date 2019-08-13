/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.Utility;
import android.icu.lang.UCharacter;
import android.icu.text.Replaceable;
import android.icu.text.Transliterator;
import android.icu.text.UTF16;
import android.icu.text.UnicodeFilter;
import android.icu.text.UnicodeSet;

class UnescapeTransliterator
extends Transliterator {
    private static final char END = '\uffff';
    private char[] spec;

    UnescapeTransliterator(String string, char[] arrc) {
        super(string, null);
        this.spec = arrc;
    }

    static void register() {
        Transliterator.registerFactory("Hex-Any/Unicode", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new UnescapeTransliterator("Hex-Any/Unicode", new char[]{'\u0002', '\u0000', '\u0010', '\u0004', '\u0006', 'U', '+', '\uffffffff'});
            }
        });
        Transliterator.registerFactory("Hex-Any/Java", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new UnescapeTransliterator("Hex-Any/Java", new char[]{'\u0002', '\u0000', '\u0010', '\u0004', '\u0004', '\\', 'u', '\uffffffff'});
            }
        });
        Transliterator.registerFactory("Hex-Any/C", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new UnescapeTransliterator("Hex-Any/C", new char[]{'\u0002', '\u0000', '\u0010', '\u0004', '\u0004', '\\', 'u', '\u0002', '\u0000', '\u0010', '\b', '\b', '\\', 'U', '\uffffffff'});
            }
        });
        Transliterator.registerFactory("Hex-Any/XML", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new UnescapeTransliterator("Hex-Any/XML", new char[]{'\u0003', '\u0001', '\u0010', '\u0001', '\u0006', '&', '#', 'x', ';', '\uffffffff'});
            }
        });
        Transliterator.registerFactory("Hex-Any/XML10", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new UnescapeTransliterator("Hex-Any/XML10", new char[]{'\u0002', '\u0001', '\n', '\u0001', '\u0007', '&', '#', ';', '\uffffffff'});
            }
        });
        Transliterator.registerFactory("Hex-Any/Perl", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new UnescapeTransliterator("Hex-Any/Perl", new char[]{'\u0003', '\u0001', '\u0010', '\u0001', '\u0006', '\\', 'x', '{', '}', '\uffffffff'});
            }
        });
        Transliterator.registerFactory("Hex-Any", new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new UnescapeTransliterator("Hex-Any", new char[]{'\u0002', '\u0000', '\u0010', '\u0004', '\u0006', 'U', '+', '\u0002', '\u0000', '\u0010', '\u0004', '\u0004', '\\', 'u', '\u0002', '\u0000', '\u0010', '\b', '\b', '\\', 'U', '\u0003', '\u0001', '\u0010', '\u0001', '\u0006', '&', '#', 'x', ';', '\u0002', '\u0001', '\n', '\u0001', '\u0007', '&', '#', ';', '\u0003', '\u0001', '\u0010', '\u0001', '\u0006', '\\', 'x', '{', '}', '\uffffffff'});
            }
        });
    }

    @Override
    public void addSourceTargetSet(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, UnicodeSet unicodeSet3) {
        char[] arrc;
        unicodeSet = this.getFilterAsUnicodeSet(unicodeSet);
        UnicodeSet unicodeSet4 = new UnicodeSet();
        StringBuilder stringBuilder = new StringBuilder();
        int n = 0;
        while ((arrc = this.spec)[n] != '\uffff') {
            int n2 = arrc[n] + n + arrc[n + 1] + 5;
            int n3 = arrc[n + 2];
            for (int i = 0; i < n3; ++i) {
                Utility.appendNumber(stringBuilder, i, n3, 0);
            }
            n += 5;
            while (n < n2) {
                unicodeSet4.add(this.spec[n]);
                ++n;
            }
            n = n2;
        }
        unicodeSet4.addAll(stringBuilder.toString());
        unicodeSet4.retainAll(unicodeSet);
        if (unicodeSet4.size() > 0) {
            unicodeSet2.addAll(unicodeSet4);
            unicodeSet3.addAll(0, 1114111);
        }
    }

    @Override
    protected void handleTransliterate(Replaceable replaceable, Transliterator.Position position, boolean bl) {
        int n = position.start;
        int n2 = position.limit;
        block0 : while (n < n2) {
            int n3;
            int n4 = 0;
            do {
                int n5;
                Object object = this.spec;
                n3 = n2;
                if (object[n4] == '\uffff') break;
                n3 = n4 + 1;
                int n6 = object[n4];
                n4 = n3 + 1;
                int n7 = object[n3];
                n3 = n4 + 1;
                char c = object[n4];
                n4 = n3 + 1;
                char c2 = object[n3];
                int n8 = n4 + 1;
                char c3 = object[n4];
                n3 = n;
                int n9 = 1;
                int n10 = 0;
                do {
                    n5 = n3;
                    n4 = n9;
                    if (n10 >= n6) break;
                    if (n3 >= n2 && n10 > 0) {
                        if (bl) break block0;
                        n4 = 0;
                        n5 = n3;
                        break;
                    }
                    n4 = n3 + 1;
                    if (replaceable.charAt(n3) != this.spec[n8 + n10]) {
                        n3 = 0;
                        n5 = n4;
                        n4 = n3;
                        break;
                    }
                    ++n10;
                    n3 = n4;
                } while (true);
                if (n4 != 0) {
                    n10 = 0;
                    n3 = 0;
                    do {
                        if (n5 >= n2) {
                            if (n5 <= n || !bl) break;
                            break block0;
                        }
                        int n11 = replaceable.char32At(n5);
                        n9 = UCharacter.digit(n11, c);
                        if (n9 < 0) break;
                        n5 += UTF16.getCharCount(n11);
                        n10 = n10 * c + n9;
                    } while (++n3 != c3);
                    n4 = n3 >= c2 ? 1 : 0;
                    if (n4 != 0) {
                        for (n3 = 0; n3 < n7; ++n3) {
                            if (n5 >= n2) {
                                if (n5 > n && bl) break block0;
                                n4 = 0;
                                break;
                            }
                            n9 = n5 + 1;
                            if (replaceable.charAt(n5) != this.spec[n8 + n6 + n3]) {
                                n4 = 0;
                                n5 = n9;
                                break;
                            }
                            n5 = n9;
                        }
                        if (n4 != 0) {
                            object = UTF16.valueOf(n10);
                            replaceable.replace(n, n5, (String)object);
                            n3 = n2 - (n5 - n - ((String)object).length());
                            break;
                        }
                    }
                }
                n4 = n8 + (n6 + n7);
            } while (true);
            n2 = n3;
            if (n >= n3) continue;
            n += UTF16.getCharCount(replaceable.char32At(n));
            n2 = n3;
        }
        position.contextLimit += n2 - position.limit;
        position.limit = n2;
        position.start = n;
    }

}

