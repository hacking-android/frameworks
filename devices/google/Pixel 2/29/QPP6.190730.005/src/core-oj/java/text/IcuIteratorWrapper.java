/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.text.BreakIterator
 */
package java.text;

import java.text.BreakIterator;
import java.text.CharacterIterator;

class IcuIteratorWrapper
extends BreakIterator {
    private android.icu.text.BreakIterator wrapped;

    IcuIteratorWrapper(android.icu.text.BreakIterator breakIterator) {
        this.wrapped = breakIterator;
    }

    protected static final void checkOffset(int n, CharacterIterator characterIterator) {
        if (n >= characterIterator.getBeginIndex() && n <= characterIterator.getEndIndex()) {
            return;
        }
        throw new IllegalArgumentException("offset out of bounds");
    }

    @Override
    public Object clone() {
        IcuIteratorWrapper icuIteratorWrapper = (IcuIteratorWrapper)super.clone();
        icuIteratorWrapper.wrapped = (android.icu.text.BreakIterator)this.wrapped.clone();
        return icuIteratorWrapper;
    }

    @Override
    public int current() {
        return this.wrapped.current();
    }

    public boolean equals(Object object) {
        if (!(object instanceof IcuIteratorWrapper)) {
            return false;
        }
        return this.wrapped.equals((Object)((IcuIteratorWrapper)object).wrapped);
    }

    @Override
    public int first() {
        return this.wrapped.first();
    }

    @Override
    public int following(int n) {
        IcuIteratorWrapper.checkOffset(n, this.getText());
        return this.wrapped.following(n);
    }

    @Override
    public CharacterIterator getText() {
        return this.wrapped.getText();
    }

    public int hashCode() {
        return this.wrapped.hashCode();
    }

    @Override
    public boolean isBoundary(int n) {
        IcuIteratorWrapper.checkOffset(n, this.getText());
        return this.wrapped.isBoundary(n);
    }

    @Override
    public int last() {
        return this.wrapped.last();
    }

    @Override
    public int next() {
        return this.wrapped.next();
    }

    @Override
    public int next(int n) {
        return this.wrapped.next(n);
    }

    @Override
    public int preceding(int n) {
        IcuIteratorWrapper.checkOffset(n, this.getText());
        return this.wrapped.preceding(n);
    }

    @Override
    public int previous() {
        return this.wrapped.previous();
    }

    @Override
    public void setText(String string) {
        this.wrapped.setText(string);
    }

    @Override
    public void setText(CharacterIterator characterIterator) {
        characterIterator.current();
        this.wrapped.setText(characterIterator);
    }

    public String toString() {
        return this.wrapped.toString();
    }
}

