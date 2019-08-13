/*
 * Decompiled with CFR 0.145.
 */
package sun.util.locale;

public class StringTokenIterator {
    private char delimiterChar;
    private String dlms;
    private boolean done;
    private int end;
    private int start;
    private String text;
    private String token;

    public StringTokenIterator(String string, String string2) {
        this.text = string;
        if (string2.length() == 1) {
            this.delimiterChar = string2.charAt(0);
        } else {
            this.dlms = string2;
        }
        this.setStart(0);
    }

    private int nextDelimiter(int n) {
        int n2 = this.text.length();
        String string = this.dlms;
        if (string == null) {
            while (n < n2) {
                if (this.text.charAt(n) == this.delimiterChar) {
                    return n;
                }
                ++n;
            }
        } else {
            int n3 = string.length();
            while (n < n2) {
                char c = this.text.charAt(n);
                for (int i = 0; i < n3; ++i) {
                    if (c != this.dlms.charAt(i)) continue;
                    return n;
                }
                ++n;
            }
        }
        return n2;
    }

    public String current() {
        return this.token;
    }

    public int currentEnd() {
        return this.end;
    }

    public int currentStart() {
        return this.start;
    }

    public String first() {
        this.setStart(0);
        return this.token;
    }

    public boolean hasNext() {
        boolean bl = this.end < this.text.length();
        return bl;
    }

    public boolean isDone() {
        return this.done;
    }

    public String next() {
        if (this.hasNext()) {
            this.start = this.end + 1;
            this.end = this.nextDelimiter(this.start);
            this.token = this.text.substring(this.start, this.end);
        } else {
            this.start = this.end;
            this.token = null;
            this.done = true;
        }
        return this.token;
    }

    public StringTokenIterator setStart(int n) {
        if (n <= this.text.length()) {
            this.start = n;
            this.end = this.nextDelimiter(this.start);
            this.token = this.text.substring(this.start, this.end);
            this.done = false;
            return this;
        }
        throw new IndexOutOfBoundsException();
    }

    public StringTokenIterator setText(String string) {
        this.text = string;
        this.setStart(0);
        return this;
    }
}

