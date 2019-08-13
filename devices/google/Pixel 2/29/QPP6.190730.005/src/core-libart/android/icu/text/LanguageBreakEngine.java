/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.DictionaryBreakEngine;
import java.text.CharacterIterator;

interface LanguageBreakEngine {
    public int findBreaks(CharacterIterator var1, int var2, int var3, DictionaryBreakEngine.DequeI var4);

    public boolean handles(int var1);
}

