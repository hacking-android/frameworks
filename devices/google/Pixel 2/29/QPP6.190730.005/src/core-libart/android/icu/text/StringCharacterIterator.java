/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.util.ICUCloneNotSupportedException;
import java.text.CharacterIterator;

@Deprecated
public final class StringCharacterIterator
implements CharacterIterator {
    private int begin;
    private int end;
    private int pos;
    private String text;

    @Deprecated
    public StringCharacterIterator(String string) {
        this(string, 0);
    }

    @Deprecated
    public StringCharacterIterator(String string, int n) {
        this(string, 0, string.length(), n);
    }

    @Deprecated
    public StringCharacterIterator(String string, int n, int n2, int n3) {
        if (string != null) {
            this.text = string;
            if (n >= 0 && n <= n2 && n2 <= string.length()) {
                if (n3 >= n && n3 <= n2) {
                    this.begin = n;
                    this.end = n2;
                    this.pos = n3;
                    return;
                }
                throw new IllegalArgumentException("Invalid position");
            }
            throw new IllegalArgumentException("Invalid substring range");
        }
        throw new NullPointerException();
    }

    @Deprecated
    @Override
    public Object clone() {
        try {
            StringCharacterIterator stringCharacterIterator = (StringCharacterIterator)super.clone();
            return stringCharacterIterator;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new ICUCloneNotSupportedException(cloneNotSupportedException);
        }
    }

    @Deprecated
    @Override
    public char current() {
        int n = this.pos;
        if (n >= this.begin && n < this.end) {
            return this.text.charAt(n);
        }
        return '\uffff';
    }

    @Deprecated
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof StringCharacterIterator)) {
            return false;
        }
        object = (StringCharacterIterator)object;
        if (this.hashCode() != ((StringCharacterIterator)object).hashCode()) {
            return false;
        }
        if (!this.text.equals(((StringCharacterIterator)object).text)) {
            return false;
        }
        return this.pos == ((StringCharacterIterator)object).pos && this.begin == ((StringCharacterIterator)object).begin && this.end == ((StringCharacterIterator)object).end;
        {
        }
    }

    @Deprecated
    @Override
    public char first() {
        this.pos = this.begin;
        return this.current();
    }

    @Deprecated
    @Override
    public int getBeginIndex() {
        return this.begin;
    }

    @Deprecated
    @Override
    public int getEndIndex() {
        return this.end;
    }

    @Deprecated
    @Override
    public int getIndex() {
        return this.pos;
    }

    @Deprecated
    public int hashCode() {
        return this.text.hashCode() ^ this.pos ^ this.begin ^ this.end;
    }

    @Deprecated
    @Override
    public char last() {
        int n = this.end;
        this.pos = n != this.begin ? n - 1 : n;
        return this.current();
    }

    @Deprecated
    @Override
    public char next() {
        int n = this.pos;
        int n2 = this.end;
        if (n < n2 - 1) {
            this.pos = n + 1;
            return this.text.charAt(this.pos);
        }
        this.pos = n2;
        return '\uffff';
    }

    @Deprecated
    @Override
    public char previous() {
        int n = this.pos;
        if (n > this.begin) {
            this.pos = n - 1;
            return this.text.charAt(this.pos);
        }
        return '\uffff';
    }

    @Deprecated
    @Override
    public char setIndex(int n) {
        if (n >= this.begin && n <= this.end) {
            this.pos = n;
            return this.current();
        }
        throw new IllegalArgumentException("Invalid index");
    }

    @Deprecated
    public void setText(String string) {
        if (string != null) {
            this.text = string;
            this.begin = 0;
            this.end = string.length();
            this.pos = 0;
            return;
        }
        throw new NullPointerException();
    }
}

