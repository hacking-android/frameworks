/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.text.Replaceable;
import android.icu.text.ReplaceableString;
import android.icu.text.UCharacterIterator;
import android.icu.text.UTF16;

public class ReplaceableUCharacterIterator
extends UCharacterIterator {
    private int currentIndex;
    private Replaceable replaceable;

    public ReplaceableUCharacterIterator(Replaceable replaceable) {
        if (replaceable != null) {
            this.replaceable = replaceable;
            this.currentIndex = 0;
            return;
        }
        throw new IllegalArgumentException();
    }

    public ReplaceableUCharacterIterator(String string) {
        if (string != null) {
            this.replaceable = new ReplaceableString(string);
            this.currentIndex = 0;
            return;
        }
        throw new IllegalArgumentException();
    }

    public ReplaceableUCharacterIterator(StringBuffer stringBuffer) {
        if (stringBuffer != null) {
            this.replaceable = new ReplaceableString(stringBuffer);
            this.currentIndex = 0;
            return;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Object clone() {
        try {
            Object object = super.clone();
            return object;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            return null;
        }
    }

    @Override
    public int current() {
        if (this.currentIndex < this.replaceable.length()) {
            return this.replaceable.charAt(this.currentIndex);
        }
        return -1;
    }

    @Override
    public int currentCodePoint() {
        int n = this.current();
        if (UTF16.isLeadSurrogate((char)n)) {
            this.next();
            int n2 = this.current();
            this.previous();
            if (UTF16.isTrailSurrogate((char)n2)) {
                return Character.toCodePoint((char)n, (char)n2);
            }
        }
        return n;
    }

    @Override
    public int getIndex() {
        return this.currentIndex;
    }

    @Override
    public int getLength() {
        return this.replaceable.length();
    }

    @Override
    public int getText(char[] arrc, int n) {
        int n2 = this.replaceable.length();
        if (n >= 0 && n + n2 <= arrc.length) {
            this.replaceable.getChars(0, n2, arrc, n);
            return n2;
        }
        throw new IndexOutOfBoundsException(Integer.toString(n2));
    }

    @Override
    public int next() {
        if (this.currentIndex < this.replaceable.length()) {
            Replaceable replaceable = this.replaceable;
            int n = this.currentIndex;
            this.currentIndex = n + 1;
            return replaceable.charAt(n);
        }
        return -1;
    }

    @Override
    public int previous() {
        int n = this.currentIndex;
        if (n > 0) {
            Replaceable replaceable = this.replaceable;
            this.currentIndex = --n;
            return replaceable.charAt(n);
        }
        return -1;
    }

    @Override
    public void setIndex(int n) throws IndexOutOfBoundsException {
        if (n >= 0 && n <= this.replaceable.length()) {
            this.currentIndex = n;
            return;
        }
        throw new IndexOutOfBoundsException();
    }
}

