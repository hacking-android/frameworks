/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.Enumeration;
import java.util.NoSuchElementException;

public class StringTokenizer
implements Enumeration<Object> {
    private int currentPosition = 0;
    private int[] delimiterCodePoints;
    private String delimiters;
    private boolean delimsChanged = false;
    private boolean hasSurrogates = false;
    private int maxDelimCodePoint;
    private int maxPosition;
    private int newPosition = -1;
    private boolean retDelims;
    private String str;

    public StringTokenizer(String string) {
        this(string, " \t\n\r\f", false);
    }

    public StringTokenizer(String string, String string2) {
        this(string, string2, false);
    }

    public StringTokenizer(String string, String string2, boolean bl) {
        this.str = string;
        this.maxPosition = string.length();
        this.delimiters = string2;
        this.retDelims = bl;
        this.setMaxDelimCodePoint();
    }

    private boolean isDelimiter(int n) {
        int[] arrn;
        for (int i = 0; i < (arrn = this.delimiterCodePoints).length; ++i) {
            if (arrn[i] != n) continue;
            return true;
        }
        return false;
    }

    private int scanToken(int n) {
        int n2;
        int n3 = n;
        while (n3 < this.maxPosition) {
            if (!this.hasSurrogates) {
                n2 = this.str.charAt(n3);
                if (n2 <= this.maxDelimCodePoint && this.delimiters.indexOf(n2) >= 0) break;
                ++n3;
                continue;
            }
            n2 = this.str.codePointAt(n3);
            if (n2 <= this.maxDelimCodePoint && this.isDelimiter(n2)) break;
            n3 += Character.charCount(n2);
        }
        n2 = n3;
        if (this.retDelims) {
            n2 = n3;
            if (n == n3) {
                if (!this.hasSurrogates) {
                    n = this.str.charAt(n3);
                    n2 = n3;
                    if (n <= this.maxDelimCodePoint) {
                        n2 = n3;
                        if (this.delimiters.indexOf(n) >= 0) {
                            n2 = n3 + 1;
                        }
                    }
                } else {
                    n = this.str.codePointAt(n3);
                    n2 = n3;
                    if (n <= this.maxDelimCodePoint) {
                        n2 = n3;
                        if (this.isDelimiter(n)) {
                            n2 = n3 + Character.charCount(n);
                        }
                    }
                }
            }
        }
        return n2;
    }

    private void setMaxDelimCodePoint() {
        int n;
        int n2;
        if (this.delimiters == null) {
            this.maxDelimCodePoint = 0;
            return;
        }
        int n3 = 0;
        int n4 = 0;
        for (n2 = 0; n2 < this.delimiters.length(); n2 += Character.charCount((int)n)) {
            int n5;
            n = n5 = this.delimiters.charAt(n2);
            if (n5 >= 55296) {
                n = n5;
                if (n5 <= 57343) {
                    n = this.delimiters.codePointAt(n2);
                    this.hasSurrogates = true;
                }
            }
            n5 = n3;
            if (n3 < n) {
                n5 = n;
            }
            ++n4;
            n3 = n5;
        }
        this.maxDelimCodePoint = n3;
        if (this.hasSurrogates) {
            this.delimiterCodePoints = new int[n4];
            n2 = 0;
            n = 0;
            while (n2 < n4) {
                this.delimiterCodePoints[n2] = n3 = this.delimiters.codePointAt(n);
                ++n2;
                n += Character.charCount(n3);
            }
        }
    }

    private int skipDelimiters(int n) {
        if (this.delimiters != null) {
            while (!this.retDelims && n < this.maxPosition) {
                int n2;
                if (!this.hasSurrogates) {
                    n2 = this.str.charAt(n);
                    if (n2 > this.maxDelimCodePoint || this.delimiters.indexOf(n2) < 0) break;
                    ++n;
                    continue;
                }
                n2 = this.str.codePointAt(n);
                if (n2 > this.maxDelimCodePoint || !this.isDelimiter(n2)) break;
                n += Character.charCount(n2);
            }
            return n;
        }
        throw new NullPointerException();
    }

    public int countTokens() {
        int n = 0;
        int n2 = this.currentPosition;
        while (n2 < this.maxPosition && (n2 = this.skipDelimiters(n2)) < this.maxPosition) {
            n2 = this.scanToken(n2);
            ++n;
        }
        return n;
    }

    @Override
    public boolean hasMoreElements() {
        return this.hasMoreTokens();
    }

    public boolean hasMoreTokens() {
        this.newPosition = this.skipDelimiters(this.currentPosition);
        boolean bl = this.newPosition < this.maxPosition;
        return bl;
    }

    @Override
    public Object nextElement() {
        return this.nextToken();
    }

    public String nextToken() {
        int n = this.newPosition;
        if (n < 0 || this.delimsChanged) {
            n = this.skipDelimiters(this.currentPosition);
        }
        this.currentPosition = n;
        this.delimsChanged = false;
        this.newPosition = -1;
        n = this.currentPosition;
        if (n < this.maxPosition) {
            int n2 = this.currentPosition;
            this.currentPosition = this.scanToken(n);
            return this.str.substring(n2, this.currentPosition);
        }
        throw new NoSuchElementException();
    }

    public String nextToken(String string) {
        this.delimiters = string;
        this.delimsChanged = true;
        this.setMaxDelimCodePoint();
        return this.nextToken();
    }
}

