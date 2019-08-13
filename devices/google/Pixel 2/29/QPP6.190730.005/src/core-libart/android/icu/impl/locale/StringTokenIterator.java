/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.locale;

public class StringTokenIterator {
    private String _dlms;
    private boolean _done;
    private int _end;
    private int _start;
    private String _text;
    private String _token;

    public StringTokenIterator(String string, String string2) {
        this._text = string;
        this._dlms = string2;
        this.setStart(0);
    }

    private int nextDelimiter(int n) {
        block0 : while (n < this._text.length()) {
            char c = this._text.charAt(n);
            for (int i = 0; i < this._dlms.length(); ++i) {
                if (c == this._dlms.charAt(i)) break block0;
            }
            ++n;
        }
        return n;
    }

    public String current() {
        return this._token;
    }

    public int currentEnd() {
        return this._end;
    }

    public int currentStart() {
        return this._start;
    }

    public String first() {
        this.setStart(0);
        return this._token;
    }

    public boolean hasNext() {
        boolean bl = this._end < this._text.length();
        return bl;
    }

    public boolean isDone() {
        return this._done;
    }

    public String next() {
        if (this.hasNext()) {
            this._start = this._end + 1;
            this._end = this.nextDelimiter(this._start);
            this._token = this._text.substring(this._start, this._end);
        } else {
            this._start = this._end;
            this._token = null;
            this._done = true;
        }
        return this._token;
    }

    public StringTokenIterator setStart(int n) {
        if (n <= this._text.length()) {
            this._start = n;
            this._end = this.nextDelimiter(this._start);
            this._token = this._text.substring(this._start, this._end);
            this._done = false;
            return this;
        }
        throw new IndexOutOfBoundsException();
    }

    public StringTokenIterator setText(String string) {
        this._text = string;
        this.setStart(0);
        return this;
    }
}

