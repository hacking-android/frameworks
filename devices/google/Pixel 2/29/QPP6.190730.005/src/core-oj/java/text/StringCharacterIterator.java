/*
 * Decompiled with CFR 0.145.
 */
package java.text;

import java.text.CharacterIterator;

public final class StringCharacterIterator
implements CharacterIterator {
    private int begin;
    private int end;
    private int pos;
    private String text;

    public StringCharacterIterator(String string) {
        this(string, 0);
    }

    public StringCharacterIterator(String string, int n) {
        this(string, 0, string.length(), n);
    }

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

    @Override
    public Object clone() {
        try {
            StringCharacterIterator stringCharacterIterator = (StringCharacterIterator)super.clone();
            return stringCharacterIterator;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError(cloneNotSupportedException);
        }
    }

    @Override
    public char current() {
        int n = this.pos;
        if (n >= this.begin && n < this.end) {
            return this.text.charAt(n);
        }
        return '\uffff';
    }

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

    @Override
    public char first() {
        this.pos = this.begin;
        return this.current();
    }

    @Override
    public int getBeginIndex() {
        return this.begin;
    }

    @Override
    public int getEndIndex() {
        return this.end;
    }

    @Override
    public int getIndex() {
        return this.pos;
    }

    public int hashCode() {
        return this.text.hashCode() ^ this.pos ^ this.begin ^ this.end;
    }

    @Override
    public char last() {
        int n = this.end;
        this.pos = n != this.begin ? n - 1 : n;
        return this.current();
    }

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

    @Override
    public char previous() {
        int n = this.pos;
        if (n > this.begin) {
            this.pos = n - 1;
            return this.text.charAt(this.pos);
        }
        return '\uffff';
    }

    @Override
    public char setIndex(int n) {
        if (n >= this.begin && n <= this.end) {
            this.pos = n;
            return this.current();
        }
        throw new IllegalArgumentException("Invalid index");
    }

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

