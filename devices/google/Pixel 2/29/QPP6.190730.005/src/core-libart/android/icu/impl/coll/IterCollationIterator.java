/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.coll;

import android.icu.impl.Trie2_32;
import android.icu.impl.coll.CollationData;
import android.icu.impl.coll.CollationIterator;
import android.icu.text.UCharacterIterator;

public class IterCollationIterator
extends CollationIterator {
    protected UCharacterIterator iter;

    public IterCollationIterator(CollationData collationData, boolean bl, UCharacterIterator uCharacterIterator) {
        super(collationData, bl);
        this.iter = uCharacterIterator;
    }

    @Override
    protected void backwardNumCodePoints(int n) {
        this.iter.moveCodePointIndex(-n);
    }

    @Override
    protected void forwardNumCodePoints(int n) {
        this.iter.moveCodePointIndex(n);
    }

    @Override
    public int getOffset() {
        return this.iter.getIndex();
    }

    @Override
    protected char handleGetTrailSurrogate() {
        int n = this.iter.next();
        if (!IterCollationIterator.isTrailSurrogate(n) && n >= 0) {
            this.iter.previous();
        }
        return (char)n;
    }

    @Override
    protected long handleNextCE32() {
        int n = this.iter.next();
        if (n < 0) {
            return -4294967104L;
        }
        return this.makeCodePointAndCE32Pair(n, this.trie.getFromU16SingleLead((char)n));
    }

    @Override
    public int nextCodePoint() {
        return this.iter.nextCodePoint();
    }

    @Override
    public int previousCodePoint() {
        return this.iter.previousCodePoint();
    }

    @Override
    public void resetToOffset(int n) {
        this.reset();
        this.iter.setIndex(n);
    }
}

