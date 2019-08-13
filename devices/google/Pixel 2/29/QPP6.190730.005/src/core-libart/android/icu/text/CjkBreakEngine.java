/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.Assert;
import android.icu.impl.CharacterIteration;
import android.icu.text.DictionaryBreakEngine;
import android.icu.text.DictionaryData;
import android.icu.text.DictionaryMatcher;
import android.icu.text.Normalizer;
import android.icu.text.UnicodeSet;
import java.io.IOException;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

class CjkBreakEngine
extends DictionaryBreakEngine {
    private static final UnicodeSet fHanWordSet;
    private static final UnicodeSet fHangulWordSet;
    private static final UnicodeSet fHiraganaWordSet;
    private static final UnicodeSet fKatakanaWordSet;
    private static final int kMaxKatakanaGroupLength = 20;
    private static final int kMaxKatakanaLength = 8;
    private static final int kint32max = Integer.MAX_VALUE;
    private static final int maxSnlp = 255;
    private DictionaryMatcher fDictionary = DictionaryData.loadDictionaryFor("Hira");

    static {
        fHangulWordSet = new UnicodeSet();
        fHanWordSet = new UnicodeSet();
        fKatakanaWordSet = new UnicodeSet();
        fHiraganaWordSet = new UnicodeSet();
        fHangulWordSet.applyPattern("[\\uac00-\\ud7a3]");
        fHanWordSet.applyPattern("[:Han:]");
        fKatakanaWordSet.applyPattern("[[:Katakana:]\\uff9e\\uff9f]");
        fHiraganaWordSet.applyPattern("[:Hiragana:]");
        fHangulWordSet.freeze();
        fHanWordSet.freeze();
        fKatakanaWordSet.freeze();
        fHiraganaWordSet.freeze();
    }

    public CjkBreakEngine(boolean bl) throws IOException {
        if (bl) {
            this.setCharacters(fHangulWordSet);
        } else {
            UnicodeSet unicodeSet = new UnicodeSet();
            unicodeSet.addAll(fHanWordSet);
            unicodeSet.addAll(fKatakanaWordSet);
            unicodeSet.addAll(fHiraganaWordSet);
            unicodeSet.add(65392);
            unicodeSet.add(12540);
            this.setCharacters(unicodeSet);
        }
    }

    private static int getKatakanaCost(int n) {
        n = n > 8 ? 8192 : new int[]{8192, 984, 408, 240, 204, 252, 300, 372, 480}[n];
        return n;
    }

    private static boolean isKatakana(int n) {
        boolean bl = n >= 12449 && n <= 12542 && n != 12539 || n >= 65382 && n <= 65439;
        return bl;
    }

    @Override
    public int divideUpDictionaryRange(CharacterIterator characterIterator, int n, int n2, DictionaryBreakEngine.DequeI dequeI) {
        block35 : {
            int n3;
            int n4;
            int[] arrn;
            int n5;
            int[] arrn2;
            int n6;
            block34 : {
                block33 : {
                    StringCharacterIterator stringCharacterIterator;
                    int[] arrn3;
                    if (n >= n2) {
                        return 0;
                    }
                    characterIterator.setIndex(n);
                    n4 = n2 - n;
                    arrn2 = new int[n4 + 1];
                    StringBuffer stringBuffer = new StringBuffer("");
                    characterIterator.setIndex(n);
                    while (characterIterator.getIndex() < n2) {
                        stringBuffer.append(characterIterator.current());
                        characterIterator.next();
                    }
                    Object object = stringBuffer.toString();
                    n5 = Normalizer.quickCheck((String)object, Normalizer.NFKC) != Normalizer.YES && !Normalizer.isNormalized((String)object, Normalizer.NFKC, 0) ? 0 : 1;
                    int n7 = 0;
                    n6 = 0;
                    if (n5 != 0) {
                        stringCharacterIterator = new StringCharacterIterator((String)object);
                        arrn2[0] = 0;
                        n5 = n6;
                        for (n3 = 0; n3 < object.length(); n3 += Character.charCount((int)object.codePointAt((int)n3))) {
                            arrn2[++n5] = n3;
                        }
                    } else {
                        arrn3 = Normalizer.normalize((String)object, Normalizer.NFKC);
                        stringCharacterIterator = new StringCharacterIterator((String)arrn3);
                        arrn2 = new int[arrn3.length() + 1];
                        object = new Normalizer((String)object, Normalizer.NFKC, 0);
                        n3 = 0;
                        arrn2[0] = 0;
                        n5 = n7;
                        while (n3 < object.endIndex()) {
                            object.next();
                            arrn2[++n5] = n3 = object.getIndex();
                        }
                    }
                    object = new int[n5 + 1];
                    object[0] = 0;
                    for (n3 = 1; n3 <= n5; ++n3) {
                        object[n3] = Integer.MAX_VALUE;
                    }
                    arrn3 = new int[n5 + 1];
                    for (n3 = 0; n3 <= n5; ++n3) {
                        arrn3[n3] = -1;
                    }
                    arrn = new int[n5];
                    int[] arrn4 = new int[n5];
                    stringCharacterIterator.setIndex(0);
                    boolean bl = false;
                    n6 = 0;
                    n3 = n4;
                    for (n4 = n6; n4 < n5; ++n4) {
                        int n8;
                        block30 : {
                            int[] arrn5;
                            int[] arrn6;
                            int[] arrn7;
                            block32 : {
                                block31 : {
                                    n8 = stringCharacterIterator.getIndex();
                                    if (object[n4] == Integer.MAX_VALUE) break block30;
                                    n6 = n4 + 20 < n5 ? 20 : n5 - n4;
                                    arrn6 = arrn4;
                                    int[] arrn8 = new int[1];
                                    DictionaryMatcher dictionaryMatcher = this.fDictionary;
                                    arrn7 = arrn;
                                    int[] arrn9 = arrn3;
                                    arrn5 = object;
                                    dictionaryMatcher.matches(stringCharacterIterator, n6, arrn6, arrn8, n6, arrn7);
                                    n7 = arrn8[0];
                                    stringCharacterIterator.setIndex(n8);
                                    if (n7 == 0) break block31;
                                    n6 = n7;
                                    if (arrn6[0] == 1) break block32;
                                }
                                n6 = n7;
                                if (CharacterIteration.current32(stringCharacterIterator) != Integer.MAX_VALUE) {
                                    n6 = n7;
                                    if (!fHangulWordSet.contains(CharacterIteration.current32(stringCharacterIterator))) {
                                        arrn7[n7] = 255;
                                        arrn6[n7] = 1;
                                        n6 = n7 + 1;
                                    }
                                }
                            }
                            for (n7 = 0; n7 < n6; ++n7) {
                                int n9 = arrn5[n4] + arrn7[n7];
                                if (n9 >= arrn5[arrn6[n7] + n4]) continue;
                                arrn5[arrn6[n7] + n4] = n9;
                                arrn9[arrn6[n7] + n4] = n4;
                            }
                            boolean bl2 = CjkBreakEngine.isKatakana(CharacterIteration.current32(stringCharacterIterator));
                            if (!bl && bl2) {
                                CharacterIteration.next32(stringCharacterIterator);
                                for (n6 = n4 + 1; n6 < n5 && n6 - n4 < 20 && CjkBreakEngine.isKatakana(CharacterIteration.current32(stringCharacterIterator)); ++n6) {
                                    CharacterIteration.next32(stringCharacterIterator);
                                }
                                if (n6 - n4 < 20 && (n7 = arrn5[n4] + CjkBreakEngine.getKatakanaCost(n6 - n4)) < arrn5[n6]) {
                                    arrn5[n6] = n7;
                                    arrn9[n6] = n4;
                                }
                            }
                            bl = bl2;
                        }
                        stringCharacterIterator.setIndex(n8);
                        CharacterIteration.next32(stringCharacterIterator);
                    }
                    arrn = new int[n5 + 1];
                    n4 = 0;
                    if (object[n5] == Integer.MAX_VALUE) {
                        arrn[0] = n5;
                        n5 = 0 + 1;
                    } else {
                        bl = true;
                        n3 = n5;
                        n5 = n4;
                        while (n3 > 0) {
                            arrn[n5] = n3;
                            ++n5;
                            n3 = arrn3[n3];
                        }
                        if (arrn3[arrn[n5 - 1]] != 0) {
                            bl = false;
                        }
                        Assert.assrt(bl);
                    }
                    if (dequeI.size() == 0) break block33;
                    n3 = n5;
                    if (dequeI.peek() >= n) break block34;
                }
                arrn[n5] = 0;
                n3 = n5 + 1;
            }
            n5 = 0;
            --n3;
            while (n3 >= 0) {
                n6 = arrn2[arrn[n3]] + n;
                n4 = n5;
                if (!dequeI.contains(n6)) {
                    n4 = n5;
                    if (n6 != n) {
                        dequeI.push(arrn2[arrn[n3]] + n);
                        n4 = n5 + 1;
                    }
                }
                --n3;
                n5 = n4;
            }
            n = n5;
            if (!dequeI.isEmpty()) {
                n = n5;
                if (dequeI.peek() == n2) {
                    dequeI.pop();
                    n = n5 - 1;
                }
            }
            if (dequeI.isEmpty()) break block35;
            characterIterator.setIndex(dequeI.peek());
        }
        return n;
    }

    public boolean equals(Object object) {
        if (object instanceof CjkBreakEngine) {
            object = (CjkBreakEngine)object;
            return this.fSet.equals(((CjkBreakEngine)object).fSet);
        }
        return false;
    }

    public int hashCode() {
        return this.getClass().hashCode();
    }
}

