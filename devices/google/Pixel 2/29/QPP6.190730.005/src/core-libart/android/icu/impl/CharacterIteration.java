/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.text.UTF16;
import java.text.CharacterIterator;

public final class CharacterIteration {
    public static final int DONE32 = Integer.MAX_VALUE;

    private CharacterIteration() {
    }

    public static int current32(CharacterIterator characterIterator) {
        int n;
        int n2 = characterIterator.current();
        int n3 = n2;
        if (n3 < 55296) {
            return n3;
        }
        if (UTF16.isLeadSurrogate((char)n2)) {
            char c = characterIterator.next();
            characterIterator.previous();
            n = n3;
            if (UTF16.isTrailSurrogate(c)) {
                n = (n2 - 55296 << 10) + (c - 56320) + 65536;
            }
        } else {
            n = n3;
            if (n2 == 65535) {
                n = n3;
                if (characterIterator.getIndex() >= characterIterator.getEndIndex()) {
                    n = Integer.MAX_VALUE;
                }
            }
        }
        return n;
    }

    public static int next32(CharacterIterator characterIterator) {
        int n;
        int n2 = characterIterator.current();
        if (n2 >= 55296 && n2 <= 56319 && ((n2 = characterIterator.next()) < 56320 || n2 > 57343)) {
            characterIterator.previous();
        }
        n2 = n = characterIterator.next();
        if (n >= 55296) {
            n2 = CharacterIteration.nextTrail32(characterIterator, n);
        }
        if (n2 >= 65536 && n2 != Integer.MAX_VALUE) {
            characterIterator.previous();
        }
        return n2;
    }

    public static int nextTrail32(CharacterIterator characterIterator, int n) {
        int n2;
        if (n == 65535 && characterIterator.getIndex() >= characterIterator.getEndIndex()) {
            return Integer.MAX_VALUE;
        }
        int n3 = n2 = n;
        if (n <= 56319) {
            char c = characterIterator.next();
            if (UTF16.isTrailSurrogate(c)) {
                n3 = (n - 55296 << 10) + (c - 56320) + 65536;
            } else {
                characterIterator.previous();
                n3 = n2;
            }
        }
        return n3;
    }

    public static int previous32(CharacterIterator characterIterator) {
        int n;
        int n2;
        if (characterIterator.getIndex() <= characterIterator.getBeginIndex()) {
            return Integer.MAX_VALUE;
        }
        int n3 = n2 = (n = characterIterator.previous());
        if (UTF16.isTrailSurrogate((char)n)) {
            n3 = n2;
            if (characterIterator.getIndex() > characterIterator.getBeginIndex()) {
                char c = characterIterator.previous();
                if (UTF16.isLeadSurrogate(c)) {
                    n3 = (c - 55296 << 10) + (n - 56320) + 65536;
                } else {
                    characterIterator.next();
                    n3 = n2;
                }
            }
        }
        return n3;
    }
}

