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

class ThaiBreakEngine
extends DictionaryBreakEngine {
    private static final byte THAI_LOOKAHEAD = 3;
    private static final char THAI_MAIYAMOK = '\u0e46';
    private static final byte THAI_MIN_WORD = 2;
    private static final byte THAI_MIN_WORD_SPAN = 4;
    private static final char THAI_PAIYANNOI = '\u0e2f';
    private static final byte THAI_PREFIX_COMBINE_THRESHOLD = 3;
    private static final byte THAI_ROOT_COMBINE_THRESHOLD = 3;
    private static UnicodeSet fBeginWordSet;
    private static UnicodeSet fEndWordSet;
    private static UnicodeSet fMarkSet;
    private static UnicodeSet fSuffixSet;
    private static UnicodeSet fThaiWordSet;
    private DictionaryMatcher fDictionary;

    static {
        fThaiWordSet = new UnicodeSet();
        fMarkSet = new UnicodeSet();
        fBeginWordSet = new UnicodeSet();
        fSuffixSet = new UnicodeSet();
        fThaiWordSet.applyPattern("[[:Thai:]&[:LineBreak=SA:]]");
        fThaiWordSet.compact();
        fMarkSet.applyPattern("[[:Thai:]&[:LineBreak=SA:]&[:M:]]");
        fMarkSet.add(32);
        fEndWordSet = new UnicodeSet(fThaiWordSet);
        fEndWordSet.remove(3633);
        fEndWordSet.remove(3648, 3652);
        fBeginWordSet.add(3585, 3630);
        fBeginWordSet.add(3648, 3652);
        fSuffixSet.add(3631);
        fSuffixSet.add(3654);
        fMarkSet.compact();
        fEndWordSet.compact();
        fBeginWordSet.compact();
        fSuffixSet.compact();
        fThaiWordSet.freeze();
        fMarkSet.freeze();
        fEndWordSet.freeze();
        fBeginWordSet.freeze();
        fSuffixSet.freeze();
    }

    public ThaiBreakEngine() throws IOException {
        this.setCharacters(fThaiWordSet);
        this.fDictionary = DictionaryData.loadDictionaryFor("Thai");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public int divideUpDictionaryRange(CharacterIterator var1_1, int var2_2, int var3_3, DictionaryBreakEngine.DequeI var4_4) {
        if (var3_3 - var2_2 < 4) {
            return 0;
        }
        var5_5 = 0;
        var6_6 = new DictionaryBreakEngine.PossibleWord[3];
        for (var7_7 = 0; var7_7 < 3; ++var7_7) {
            var6_6[var7_7] = new DictionaryBreakEngine.PossibleWord();
        }
        var1_1.setIndex(var2_2);
        var7_7 = var5_5;
        do {
            block31 : {
                block27 : {
                    block28 : {
                        if ((var8_8 = var1_1.getIndex()) >= var3_3) {
                            var2_2 = var7_7;
                            if (var4_4.peek() < var3_3) return var2_2;
                            var4_4.pop();
                            return var7_7 - 1;
                        }
                        var5_5 = 0;
                        var9_9 = var6_6[var7_7 % 3].candidates(var1_1, this.fDictionary, var3_3);
                        if (var9_9 == 1) {
                            var5_5 = var6_6[var7_7 % 3].acceptMarked(var1_1);
                            var2_2 = var7_7 + 1;
                        } else {
                            var2_2 = var7_7;
                            if (var9_9 > 1) {
                                if (var1_1.getIndex() < var3_3) {
                                    block2 : do {
                                        if (var6_6[(var7_7 + 1) % 3].candidates(var1_1, this.fDictionary, var3_3) <= 0) continue;
                                        if (1 < 2) {
                                            var6_6[var7_7 % 3].markCurrent();
                                        }
                                        if (var1_1.getIndex() >= var3_3) break;
                                        do {
                                            if (var6_6[(var7_7 + 2) % 3].candidates(var1_1, this.fDictionary, var3_3) <= 0) continue;
                                            var6_6[var7_7 % 3].markCurrent();
                                            break block2;
                                        } while (var6_6[(var7_7 + 1) % 3].backUp(var1_1));
                                    } while (var6_6[var7_7 % 3].backUp(var1_1));
                                }
                                var5_5 = var6_6[var7_7 % 3].acceptMarked(var1_1);
                                var2_2 = var7_7 + 1;
                            }
                        }
                        var9_9 = var2_2;
                        var7_7 = var5_5;
                        if (var1_1.getIndex() >= var3_3) break block27;
                        var9_9 = var2_2;
                        var7_7 = var5_5;
                        if (var5_5 >= 3) break block27;
                        if (var6_6[var2_2 % 3].candidates(var1_1, this.fDictionary, var3_3) <= 0 && (var5_5 == 0 || var6_6[var2_2 % 3].longestPrefix() < 3)) break block28;
                        var1_1.setIndex(var8_8 + var5_5);
                        var7_7 = var5_5;
                        var9_9 = var2_2;
                        break block27;
                    }
                    var9_9 = var3_3 - (var8_8 + var5_5);
                    var7_7 = var1_1.current();
                    var10_10 = 0;
                    do {
                        block30 : {
                            block29 : {
                                var1_1.next();
                                var11_11 = var1_1.current();
                                ++var10_10;
                                if (--var9_9 <= 0) break block29;
                                if (!ThaiBreakEngine.fEndWordSet.contains(var7_7) || !ThaiBreakEngine.fBeginWordSet.contains(var11_11)) break block30;
                                var7_7 = var6_6[(var2_2 + 1) % 3].candidates(var1_1, this.fDictionary, var3_3);
                                var1_1.setIndex(var8_8 + var5_5 + var10_10);
                                if (var7_7 <= 0) break block30;
                            }
                            var9_9 = var2_2;
                            if (var5_5 <= 0) {
                                var9_9 = var2_2 + 1;
                            }
                            var7_7 = var5_5 + var10_10;
                            break;
                        }
                        var7_7 = var11_11;
                    } while (true);
                }
                while ((var2_2 = var1_1.getIndex()) < var3_3 && ThaiBreakEngine.fMarkSet.contains(var1_1.current())) {
                    var1_1.next();
                    var7_7 += var1_1.getIndex() - var2_2;
                }
                var5_5 = var7_7;
                if (var1_1.getIndex() >= var3_3) break block31;
                var5_5 = var7_7;
                if (var7_7 <= 0) break block31;
                if (var6_6[var9_9 % 3].candidates(var1_1, this.fDictionary, var3_3) > 0) ** GOTO lbl-1000
                var12_12 = ThaiBreakEngine.fSuffixSet;
                var5_5 = var2_2 = (int)var1_1.current();
                if (var12_12.contains(var2_2)) {
                    var2_2 = var7_7;
                    var10_10 = var5_5;
                    if (var5_5 == 3631) {
                        if (!ThaiBreakEngine.fSuffixSet.contains(var1_1.previous())) {
                            var1_1.next();
                            var1_1.next();
                            var2_2 = var7_7 + 1;
                            var10_10 = var1_1.current();
                        } else {
                            var1_1.next();
                            var10_10 = var5_5;
                            var2_2 = var7_7;
                        }
                    }
                    var5_5 = var2_2;
                    if (var10_10 == 3654) {
                        if (var1_1.previous() != '\u0e46') {
                            var1_1.next();
                            var1_1.next();
                            var5_5 = var2_2 + 1;
                        } else {
                            var1_1.next();
                            var5_5 = var2_2;
                        }
                    }
                } else lbl-1000: // 2 sources:
                {
                    var1_1.setIndex(var8_8 + var7_7);
                    var5_5 = var7_7;
                }
            }
            if (var5_5 > 0) {
                var4_4.push(var8_8 + var5_5);
            }
            var7_7 = var9_9;
        } while (true);
    }

    public boolean equals(Object object) {
        return object instanceof ThaiBreakEngine;
    }

    @Override
    public boolean handles(int n) {
        boolean bl = UCharacter.getIntPropertyValue(n, 4106) == 38;
        return bl;
    }

    public int hashCode() {
        return this.getClass().hashCode();
    }
}

