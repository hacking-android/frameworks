/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import java.text.CharacterIterator;

public class CSCharacterIterator
implements CharacterIterator {
    private int index;
    private CharSequence seq;

    public CSCharacterIterator(CharSequence charSequence) {
        if (charSequence != null) {
            this.seq = charSequence;
            this.index = 0;
            return;
        }
        throw new NullPointerException();
    }

    @Override
    public Object clone() {
        CSCharacterIterator cSCharacterIterator = new CSCharacterIterator(this.seq);
        cSCharacterIterator.setIndex(this.index);
        return cSCharacterIterator;
    }

    @Override
    public char current() {
        if (this.index == this.seq.length()) {
            return '\uffff';
        }
        return this.seq.charAt(this.index);
    }

    @Override
    public char first() {
        this.index = 0;
        return this.current();
    }

    @Override
    public int getBeginIndex() {
        return 0;
    }

    @Override
    public int getEndIndex() {
        return this.seq.length();
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    @Override
    public char last() {
        this.index = this.seq.length();
        return this.previous();
    }

    @Override
    public char next() {
        if (this.index < this.seq.length()) {
            ++this.index;
        }
        return this.current();
    }

    @Override
    public char previous() {
        int n = this.index;
        if (n == 0) {
            return '\uffff';
        }
        this.index = n - 1;
        return this.current();
    }

    @Override
    public char setIndex(int n) {
        if (n >= 0 && n <= this.seq.length()) {
            this.index = n;
            return this.current();
        }
        throw new IllegalArgumentException();
    }
}

