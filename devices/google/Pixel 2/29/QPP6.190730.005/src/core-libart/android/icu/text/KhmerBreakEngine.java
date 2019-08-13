/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.lang.UCharacter;
import android.icu.text.DictionaryBreakEngine;
import android.icu.text.DictionaryData;
import android.icu.text.DictionaryMatcher;
import android.icu.text.UnicodeSet;
import java.io.IOException;
import java.text.CharacterIterator;

class KhmerBreakEngine
extends DictionaryBreakEngine {
    private static final byte KHMER_LOOKAHEAD = 3;
    private static final byte KHMER_MIN_WORD = 2;
    private static final byte KHMER_MIN_WORD_SPAN = 4;
    private static final byte KHMER_PREFIX_COMBINE_THRESHOLD = 3;
    private static final byte KHMER_ROOT_COMBINE_THRESHOLD = 3;
    private static UnicodeSet fBeginWordSet;
    private static UnicodeSet fEndWordSet;
    private static UnicodeSet fKhmerWordSet;
    private static UnicodeSet fMarkSet;
    private DictionaryMatcher fDictionary;

    static {
        fKhmerWordSet = new UnicodeSet();
        fMarkSet = new UnicodeSet();
        fBeginWordSet = new UnicodeSet();
        fKhmerWordSet.applyPattern("[[:Khmer:]&[:LineBreak=SA:]]");
        fKhmerWordSet.compact();
        fMarkSet.applyPattern("[[:Khmer:]&[:LineBreak=SA:]&[:M:]]");
        fMarkSet.add(32);
        fEndWordSet = new UnicodeSet(fKhmerWordSet);
        fBeginWordSet.add(6016, 6067);
        fEndWordSet.remove(6098);
        fMarkSet.compact();
        fEndWordSet.compact();
        fBeginWordSet.compact();
        fKhmerWordSet.freeze();
        fMarkSet.freeze();
        fEndWordSet.freeze();
        fBeginWordSet.freeze();
    }

    public KhmerBreakEngine() throws IOException {
        this.setCharacters(fKhmerWordSet);
        this.fDictionary = DictionaryData.loadDictionaryFor("Khmr");
    }

    @Override
    public int divideUpDictionaryRange(CharacterIterator characterIterator, int n, int n2, DictionaryBreakEngine.DequeI dequeI) {
        int n3;
        int n4;
        if (n2 - n < 4) {
            return 0;
        }
        int n5 = 0;
        DictionaryBreakEngine.PossibleWord[] arrpossibleWord = new DictionaryBreakEngine.PossibleWord[3];
        for (n4 = 0; n4 < 3; ++n4) {
            arrpossibleWord[n4] = new DictionaryBreakEngine.PossibleWord();
        }
        characterIterator.setIndex(n);
        while ((n3 = characterIterator.getIndex()) < n2) {
            int n6;
            block18 : {
                block19 : {
                    n4 = 0;
                    n6 = arrpossibleWord[n5 % 3].candidates(characterIterator, this.fDictionary, n2);
                    if (n6 == 1) {
                        n4 = arrpossibleWord[n5 % 3].acceptMarked(characterIterator);
                        n = n5 + 1;
                    } else {
                        n = n5;
                        if (n6 > 1) {
                            n4 = 0;
                            if (characterIterator.getIndex() < n2) {
                                do {
                                    block17 : {
                                        n = n4;
                                        if (arrpossibleWord[(n5 + 1) % 3].candidates(characterIterator, this.fDictionary, n2) > 0) {
                                            if (1 < 2) {
                                                arrpossibleWord[n5 % 3].markCurrent();
                                            }
                                            if (characterIterator.getIndex() >= n2) break;
                                            do {
                                                if (arrpossibleWord[(n5 + 2) % 3].candidates(characterIterator, this.fDictionary, n2) <= 0) continue;
                                                arrpossibleWord[n5 % 3].markCurrent();
                                                n = 1;
                                                break block17;
                                            } while (arrpossibleWord[(n5 + 1) % 3].backUp(characterIterator));
                                            n = n4;
                                        }
                                    }
                                    if (!arrpossibleWord[n5 % 3].backUp(characterIterator)) break;
                                    n4 = n;
                                } while (n == 0);
                            }
                            n4 = arrpossibleWord[n5 % 3].acceptMarked(characterIterator);
                            n = n5 + 1;
                        }
                    }
                    n5 = n;
                    n6 = n4;
                    if (characterIterator.getIndex() >= n2) break block18;
                    n5 = n;
                    n6 = n4;
                    if (n4 >= 3) break block18;
                    if (arrpossibleWord[n % 3].candidates(characterIterator, this.fDictionary, n2) > 0 || n4 != 0 && arrpossibleWord[n % 3].longestPrefix() >= 3) break block19;
                    n6 = n2 - (n3 + n4);
                    n5 = characterIterator.current();
                    int n7 = 0;
                    do {
                        char c;
                        block21 : {
                            block20 : {
                                characterIterator.next();
                                c = characterIterator.current();
                                ++n7;
                                if (--n6 <= 0) break block20;
                                if (!fEndWordSet.contains(n5) || !fBeginWordSet.contains(c)) break block21;
                                n5 = arrpossibleWord[(n + 1) % 3].candidates(characterIterator, this.fDictionary, n2);
                                characterIterator.setIndex(n3 + n4 + n7);
                                if (n5 <= 0) break block21;
                            }
                            n5 = n;
                            if (n4 <= 0) {
                                n5 = n + 1;
                            }
                            n6 = n4 + n7;
                            break block18;
                        }
                        n5 = c;
                    } while (true);
                }
                characterIterator.setIndex(n3 + n4);
                n6 = n4;
                n5 = n;
            }
            while ((n = characterIterator.getIndex()) < n2 && fMarkSet.contains(characterIterator.current())) {
                characterIterator.next();
                n6 += characterIterator.getIndex() - n;
            }
            if (n6 <= 0) continue;
            dequeI.push(n3 + n6);
        }
        n = n5;
        if (dequeI.peek() >= n2) {
            dequeI.pop();
            n = n5 - 1;
        }
        return n;
    }

    public boolean equals(Object object) {
        return object instanceof KhmerBreakEngine;
    }

    @Override
    public boolean handles(int n) {
        boolean bl = UCharacter.getIntPropertyValue(n, 4106) == 23;
        return bl;
    }

    public int hashCode() {
        return this.getClass().hashCode();
    }
}

