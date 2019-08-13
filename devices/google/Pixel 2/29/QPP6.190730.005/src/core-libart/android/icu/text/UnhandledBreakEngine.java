/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.CharacterIteration;
import android.icu.lang.UCharacter;
import android.icu.text.DictionaryBreakEngine;
import android.icu.text.LanguageBreakEngine;
import android.icu.text.UnicodeSet;
import java.text.CharacterIterator;

final class UnhandledBreakEngine
implements LanguageBreakEngine {
    volatile UnicodeSet fHandled = new UnicodeSet();

    @Override
    public int findBreaks(CharacterIterator characterIterator, int n, int n2, DictionaryBreakEngine.DequeI cloneable) {
        cloneable = this.fHandled;
        n = CharacterIteration.current32(characterIterator);
        while (characterIterator.getIndex() < n2 && ((UnicodeSet)cloneable).contains(n)) {
            CharacterIteration.next32(characterIterator);
            n = CharacterIteration.current32(characterIterator);
        }
        return 0;
    }

    public void handleChar(int n) {
        UnicodeSet unicodeSet = this.fHandled;
        if (!unicodeSet.contains(n)) {
            n = UCharacter.getIntPropertyValue(n, 4106);
            UnicodeSet unicodeSet2 = new UnicodeSet();
            unicodeSet2.applyIntPropertyValue(4106, n);
            unicodeSet2.addAll(unicodeSet);
            this.fHandled = unicodeSet2;
        }
    }

    @Override
    public boolean handles(int n) {
        return this.fHandled.contains(n);
    }
}

