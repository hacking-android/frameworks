/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.coll;

import android.icu.impl.Trie2_32;
import android.icu.impl.coll.CollationData;
import android.icu.impl.coll.CollationIterator;

public class UTF16CollationIterator
extends CollationIterator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    protected int limit;
    protected int pos;
    protected CharSequence seq;
    protected int start;

    public UTF16CollationIterator(CollationData collationData) {
        super(collationData);
    }

    public UTF16CollationIterator(CollationData collationData, boolean bl, CharSequence charSequence, int n) {
        super(collationData, bl);
        this.seq = charSequence;
        this.start = 0;
        this.pos = n;
        this.limit = charSequence.length();
    }

    @Override
    protected void backwardNumCodePoints(int n) {
        int n2;
        while (n > 0 && (n2 = this.pos) != this.start) {
            CharSequence charSequence = this.seq;
            this.pos = --n2;
            char c = charSequence.charAt(n2);
            --n;
            if (!Character.isLowSurrogate(c) || (n2 = this.pos--) == this.start || !Character.isHighSurrogate(this.seq.charAt(n2 - 1))) continue;
        }
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = super.equals(object);
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (UTF16CollationIterator)object;
        if (this.pos - this.start == ((UTF16CollationIterator)object).pos - ((UTF16CollationIterator)object).start) {
            bl2 = true;
        }
        return bl2;
    }

    @Override
    protected void forwardNumCodePoints(int n) {
        int n2;
        while (n > 0 && (n2 = this.pos) != this.limit) {
            CharSequence charSequence = this.seq;
            this.pos = n2 + 1;
            char c = charSequence.charAt(n2);
            --n;
            if (!Character.isHighSurrogate(c) || (n2 = this.pos++) == this.limit || !Character.isLowSurrogate(this.seq.charAt(n2))) continue;
        }
    }

    @Override
    public int getOffset() {
        return this.pos - this.start;
    }

    @Override
    protected char handleGetTrailSurrogate() {
        int n;
        if ((n = this.pos++) == this.limit) {
            return '\u0000';
        }
        char c = this.seq.charAt(n);
        if (Character.isLowSurrogate(c)) {
            // empty if block
        }
        return c;
    }

    @Override
    protected long handleNextCE32() {
        int n = this.pos;
        if (n == this.limit) {
            return -4294967104L;
        }
        CharSequence charSequence = this.seq;
        this.pos = n + 1;
        char c = charSequence.charAt(n);
        return this.makeCodePointAndCE32Pair(c, this.trie.getFromU16SingleLead(c));
    }

    @Override
    public int hashCode() {
        return 42;
    }

    @Override
    public int nextCodePoint() {
        char c;
        int n = this.pos;
        if (n == this.limit) {
            return -1;
        }
        CharSequence charSequence = this.seq;
        this.pos = n + 1;
        char c2 = charSequence.charAt(n);
        if (Character.isHighSurrogate(c2) && (n = this.pos++) != this.limit && Character.isLowSurrogate(c = this.seq.charAt(n))) {
            return Character.toCodePoint(c2, c);
        }
        return c2;
    }

    @Override
    public int previousCodePoint() {
        char c;
        int n = this.pos;
        if (n == this.start) {
            return -1;
        }
        CharSequence charSequence = this.seq;
        this.pos = --n;
        char c2 = charSequence.charAt(n);
        if (Character.isLowSurrogate(c2) && (n = this.pos--) != this.start && Character.isHighSurrogate(c = this.seq.charAt(n - 1))) {
            return Character.toCodePoint(c, c2);
        }
        return c2;
    }

    @Override
    public void resetToOffset(int n) {
        this.reset();
        this.pos = this.start + n;
    }

    public void setText(boolean bl, CharSequence charSequence, int n) {
        this.reset(bl);
        this.seq = charSequence;
        this.start = 0;
        this.pos = n;
        this.limit = charSequence.length();
    }
}

