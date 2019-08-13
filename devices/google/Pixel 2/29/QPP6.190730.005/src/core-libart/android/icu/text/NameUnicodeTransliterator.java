/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.PatternProps;
import android.icu.impl.UCharacterName;
import android.icu.impl.Utility;
import android.icu.lang.UCharacter;
import android.icu.text.Replaceable;
import android.icu.text.Transliterator;
import android.icu.text.UTF16;
import android.icu.text.UnicodeFilter;
import android.icu.text.UnicodeSet;

class NameUnicodeTransliterator
extends Transliterator {
    static final char CLOSE_DELIM = '}';
    static final char OPEN_DELIM = '\\';
    static final String OPEN_PAT = "\\N~{~";
    static final char SPACE = ' ';
    static final String _ID = "Name-Any";

    public NameUnicodeTransliterator(UnicodeFilter unicodeFilter) {
        super(_ID, unicodeFilter);
    }

    static void register() {
        Transliterator.registerFactory(_ID, new Transliterator.Factory(){

            @Override
            public Transliterator getInstance(String string) {
                return new NameUnicodeTransliterator(null);
            }
        });
    }

    @Override
    public void addSourceTargetSet(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, UnicodeSet unicodeSet3) {
        UnicodeSet unicodeSet4 = this.getFilterAsUnicodeSet(unicodeSet);
        if (unicodeSet4.containsAll("\\N{") && unicodeSet4.contains(125)) {
            unicodeSet = new UnicodeSet().addAll(48, 57).addAll(65, 70).addAll(97, 122).add(60).add(62).add(40).add(41).add(45).add(32).addAll("\\N{").add(125);
            unicodeSet.retainAll(unicodeSet4);
            if (unicodeSet.size() > 0) {
                unicodeSet2.addAll(unicodeSet);
                unicodeSet3.addAll(0, 1114111);
            }
            return;
        }
    }

    @Override
    protected void handleTransliterate(Replaceable replaceable, Transliterator.Position position, boolean bl) {
        int n = UCharacterName.INSTANCE.getMaxCharNameLength() + 1;
        StringBuffer stringBuffer = new StringBuffer(n);
        UnicodeSet unicodeSet = new UnicodeSet();
        UCharacterName.INSTANCE.getCharNameCharacters(unicodeSet);
        int n2 = position.start;
        int n3 = position.limit;
        int n4 = 0;
        int n5 = -1;
        while (n2 < n3) {
            int n6;
            int n7;
            int n8;
            int n9;
            block13 : {
                block11 : {
                    block14 : {
                        block12 : {
                            n9 = replaceable.char32At(n2);
                            if (n4 == 0) break block11;
                            if (n4 == true) break block12;
                            n6 = n2;
                            n7 = n4;
                            n8 = n5;
                            break block13;
                        }
                        if (!PatternProps.isWhiteSpace(n9)) break block14;
                        n6 = n2;
                        n7 = n4;
                        n8 = n5;
                        if (stringBuffer.length() <= 0) break block13;
                        n6 = n2;
                        n7 = n4;
                        n8 = n5;
                        if (stringBuffer.charAt(stringBuffer.length() - 1) == ' ') break block13;
                        stringBuffer.append(' ');
                        n6 = n2;
                        n7 = n4;
                        n8 = n5;
                        if (stringBuffer.length() <= n) break block13;
                        n7 = 0;
                        n6 = n2;
                        n8 = n5;
                        break block13;
                    }
                    if (n9 == 125) {
                        n4 = stringBuffer.length();
                        if (n4 > 0 && stringBuffer.charAt(n4 - 1) == ' ') {
                            stringBuffer.setLength(n4 - 1);
                        }
                        n6 = UCharacter.getCharFromExtendedName(stringBuffer.toString());
                        n7 = n2++;
                        n4 = n3;
                        if (n6 != -1) {
                            String string = UTF16.valueOf(n6);
                            replaceable.replace(n5, n2, string);
                            n5 = n2 - n5 - string.length();
                            n7 = n2 - n5;
                            n4 = n3 - n5;
                        }
                        n6 = 0;
                        n5 = -1;
                        n2 = n7;
                        n3 = n4;
                        n4 = n6;
                        continue;
                    }
                    if (unicodeSet.contains(n9)) {
                        UTF16.append(stringBuffer, n9);
                        n6 = n2;
                        n7 = n4;
                        n8 = n5;
                        if (stringBuffer.length() >= n) {
                            n7 = 0;
                            n6 = n2;
                            n8 = n5;
                        }
                    } else {
                        n6 = n2 - 1;
                        n7 = 0;
                        n8 = n5;
                    }
                    break block13;
                }
                n6 = n2;
                n7 = n4;
                n8 = n5;
                if (n9 == 92) {
                    n5 = n2;
                    int n10 = Utility.parsePattern(OPEN_PAT, replaceable, n2, n3);
                    n6 = n2;
                    n7 = n4;
                    n8 = n5;
                    if (n10 >= 0) {
                        n6 = n2;
                        n7 = n4;
                        n8 = n5;
                        if (n10 < n3) {
                            n4 = 1;
                            stringBuffer.setLength(0);
                            n2 = n10;
                            continue;
                        }
                    }
                }
            }
            n2 = n6 + UTF16.getCharCount(n9);
            n4 = n7;
            n5 = n8;
        }
        position.contextLimit += n3 - position.limit;
        position.limit = n3;
        if (!bl || n5 < 0) {
            n5 = n2;
        }
        position.start = n5;
    }

}

