/*
 * Decompiled with CFR 0.145.
 */
package android.text;

import java.text.CharacterIterator;

public class CharSequenceCharacterIterator
implements CharacterIterator {
    private final int mBeginIndex;
    private final CharSequence mCharSeq;
    private final int mEndIndex;
    private int mIndex;

    public CharSequenceCharacterIterator(CharSequence charSequence, int n, int n2) {
        this.mCharSeq = charSequence;
        this.mIndex = n;
        this.mBeginIndex = n;
        this.mEndIndex = n2;
    }

    @Override
    public Object clone() {
        try {
            Object object = super.clone();
            return object;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
    }

    @Override
    public char current() {
        int n = this.mIndex;
        int n2 = n == this.mEndIndex ? (n = 65535) : (n = (int)this.mCharSeq.charAt(n));
        return (char)n2;
    }

    @Override
    public char first() {
        this.mIndex = this.mBeginIndex;
        return this.current();
    }

    @Override
    public int getBeginIndex() {
        return this.mBeginIndex;
    }

    @Override
    public int getEndIndex() {
        return this.mEndIndex;
    }

    @Override
    public int getIndex() {
        return this.mIndex;
    }

    @Override
    public char last() {
        int n = this.mBeginIndex;
        int n2 = this.mEndIndex;
        if (n == n2) {
            this.mIndex = n2;
            return '\uffff';
        }
        this.mIndex = n2 - 1;
        return this.mCharSeq.charAt(this.mIndex);
    }

    @Override
    public char next() {
        ++this.mIndex;
        int n = this.mIndex;
        int n2 = this.mEndIndex;
        if (n >= n2) {
            this.mIndex = n2;
            return '\uffff';
        }
        return this.mCharSeq.charAt(n);
    }

    @Override
    public char previous() {
        int n = this.mIndex;
        if (n <= this.mBeginIndex) {
            return '\uffff';
        }
        this.mIndex = n - 1;
        return this.mCharSeq.charAt(this.mIndex);
    }

    @Override
    public char setIndex(int n) {
        if (this.mBeginIndex <= n && n <= this.mEndIndex) {
            this.mIndex = n;
            return this.current();
        }
        throw new IllegalArgumentException("invalid position");
    }
}

