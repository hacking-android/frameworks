/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.CharsetDetector;
import android.icu.text.CharsetMatch;

abstract class CharsetRecognizer {
    CharsetRecognizer() {
    }

    public String getLanguage() {
        return null;
    }

    abstract String getName();

    abstract CharsetMatch match(CharsetDetector var1);
}

