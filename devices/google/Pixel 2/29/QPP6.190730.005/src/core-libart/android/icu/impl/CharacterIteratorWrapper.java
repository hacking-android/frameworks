/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.text.UCharacterIterator;
import java.text.CharacterIterator;

public class CharacterIteratorWrapper
extends UCharacterIterator {
    private CharacterIterator iterator;

    public CharacterIteratorWrapper(CharacterIterator characterIterator) {
        if (characterIterator != null) {
            this.iterator = characterIterator;
            return;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Object clone() {
        try {
            CharacterIteratorWrapper characterIteratorWrapper = (CharacterIteratorWrapper)super.clone();
            characterIteratorWrapper.iterator = (CharacterIterator)this.iterator.clone();
            return characterIteratorWrapper;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            return null;
        }
    }

    @Override
    public int current() {
        char c = this.iterator.current();
        if (c == '\uffff') {
            return -1;
        }
        return c;
    }

    @Override
    public CharacterIterator getCharacterIterator() {
        return (CharacterIterator)this.iterator.clone();
    }

    @Override
    public int getIndex() {
        return this.iterator.getIndex();
    }

    @Override
    public int getLength() {
        return this.iterator.getEndIndex() - this.iterator.getBeginIndex();
    }

    @Override
    public int getText(char[] arrc, int n) {
        int n2 = this.iterator.getEndIndex() - this.iterator.getBeginIndex();
        int n3 = this.iterator.getIndex();
        if (n >= 0 && n + n2 <= arrc.length) {
            char c = this.iterator.first();
            while (c != '\uffff') {
                arrc[n] = c;
                c = this.iterator.next();
                ++n;
            }
            this.iterator.setIndex(n3);
            return n2;
        }
        throw new IndexOutOfBoundsException(Integer.toString(n2));
    }

    @Override
    public int moveIndex(int n) {
        int n2 = this.iterator.getEndIndex() - this.iterator.getBeginIndex();
        int n3 = this.iterator.getIndex() + n;
        if (n3 < 0) {
            n = 0;
        } else {
            n = n3;
            if (n3 > n2) {
                n = n2;
            }
        }
        return this.iterator.setIndex(n);
    }

    @Override
    public int next() {
        char c = this.iterator.current();
        this.iterator.next();
        if (c == '\uffff') {
            return -1;
        }
        return c;
    }

    @Override
    public int previous() {
        char c = this.iterator.previous();
        if (c == '\uffff') {
            return -1;
        }
        return c;
    }

    @Override
    public void setIndex(int n) {
        try {
            this.iterator.setIndex(n);
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public void setToLimit() {
        CharacterIterator characterIterator = this.iterator;
        characterIterator.setIndex(characterIterator.getEndIndex());
    }
}

