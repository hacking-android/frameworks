/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.Assert;
import android.icu.text.DictionaryMatcher;
import android.icu.text.UCharacterIterator;
import android.icu.util.BytesTrie;
import java.text.CharacterIterator;

class BytesDictionaryMatcher
extends DictionaryMatcher {
    private final byte[] characters;
    private final int transform;

    public BytesDictionaryMatcher(byte[] arrby, int n) {
        this.characters = arrby;
        boolean bl = (2130706432 & n) == 16777216;
        Assert.assrt(bl);
        this.transform = n;
    }

    private int transform(int n) {
        if (n == 8205) {
            return 255;
        }
        if (n == 8204) {
            return 254;
        }
        if ((n -= this.transform & 2097151) >= 0 && 253 >= n) {
            return n;
        }
        return -1;
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public int matches(CharacterIterator object, int n, int[] arrn, int[] arrn2, int n2, int[] arrn3) {
        UCharacterIterator uCharacterIterator = UCharacterIterator.getInstance(object);
        BytesTrie bytesTrie = new BytesTrie(this.characters, 0);
        int n3 = uCharacterIterator.nextCodePoint();
        if (n3 == -1) {
            return 0;
        }
        object = bytesTrie.first(this.transform(n3));
        int n4 = 1;
        n3 = 0;
        do {
            int n5;
            block11 : {
                block9 : {
                    block10 : {
                        block8 : {
                            block7 : {
                                if (!((BytesTrie.Result)((Object)object)).hasValue()) break block7;
                                int n6 = n3;
                                if (n3 < n2) {
                                    if (arrn3 != null) {
                                        arrn3[n3] = bytesTrie.getValue();
                                    }
                                    arrn[n3] = n4;
                                    n6 = n3 + 1;
                                }
                                n5 = n6;
                                if (object != BytesTrie.Result.FINAL_VALUE) break block8;
                                n3 = n6;
                                break block9;
                            }
                            n5 = n3;
                            if (object == BytesTrie.Result.NO_MATCH) break block9;
                        }
                        if (n4 < n) break block10;
                        n3 = n5;
                        break block9;
                    }
                    n3 = uCharacterIterator.nextCodePoint();
                    if (n3 != -1) break block11;
                    n3 = n5;
                }
                arrn2[0] = n3;
                return n4;
            }
            ++n4;
            object = bytesTrie.next(this.transform(n3));
            n3 = n5;
        } while (true);
    }
}

