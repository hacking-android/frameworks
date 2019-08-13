/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import java.text.CharacterIterator;

abstract class DictionaryMatcher {
    DictionaryMatcher() {
    }

    public abstract int getType();

    public int matches(CharacterIterator characterIterator, int n, int[] arrn, int[] arrn2, int n2) {
        return this.matches(characterIterator, n, arrn, arrn2, n2, null);
    }

    public abstract int matches(CharacterIterator var1, int var2, int[] var3, int[] var4, int var5, int[] var6);
}

