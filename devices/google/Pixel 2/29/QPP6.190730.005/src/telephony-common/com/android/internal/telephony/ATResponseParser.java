/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import com.android.internal.telephony.ATParseEx;

public class ATResponseParser {
    private String mLine;
    private int mNext = 0;
    private int mTokEnd;
    private int mTokStart;

    public ATResponseParser(String string) {
        this.mLine = string;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void nextTok() {
        int n;
        int n2 = this.mLine.length();
        if (this.mNext == 0) {
            this.skipPrefix();
        }
        if ((n = this.mNext) >= n2) throw new ATParseEx();
        try {
            Object object = this.mLine;
            this.mNext = n + 1;
            n = this.skipWhiteSpace(((String)object).charAt(n));
            if (n == 34) {
                if (this.mNext >= n2) {
                    object = new ATParseEx();
                    throw object;
                }
                object = this.mLine;
                n = this.mNext;
                this.mNext = n + 1;
                n = ((String)object).charAt(n);
                this.mTokStart = this.mNext - 1;
                while (n != 34 && this.mNext < n2) {
                    object = this.mLine;
                    n = this.mNext;
                    this.mNext = n + 1;
                    n = ((String)object).charAt(n);
                }
                if (n != 34) {
                    object = new ATParseEx();
                    throw object;
                }
                this.mTokEnd = this.mNext - 1;
                if (this.mNext >= n2) return;
                object = this.mLine;
                n = this.mNext;
                this.mNext = n + 1;
                if (((String)object).charAt(n) == ',') {
                    return;
                }
                object = new ATParseEx();
                throw object;
            }
            this.mTokEnd = this.mTokStart = this.mNext - 1;
            int n3 = n;
            while (n3 != 44) {
                if (!Character.isWhitespace((char)n3)) {
                    this.mTokEnd = this.mNext;
                }
                if (this.mNext == n2) {
                    return;
                }
                object = this.mLine;
                n = this.mNext;
                this.mNext = n + 1;
                n3 = n = (int)((String)object).charAt(n);
            }
            return;
        }
        catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
            throw new ATParseEx();
        }
    }

    private void skipPrefix() {
        int n;
        this.mNext = 0;
        int n2 = this.mLine.length();
        while ((n = this.mNext) < n2) {
            String string = this.mLine;
            this.mNext = n + 1;
            if (string.charAt(n) != ':') continue;
            return;
        }
        throw new ATParseEx("missing prefix");
    }

    private char skipWhiteSpace(char c) {
        int n = this.mLine.length();
        char c2 = c;
        while (this.mNext < n && Character.isWhitespace(c2)) {
            String string = this.mLine;
            c = (char)this.mNext;
            this.mNext = c + '\u0001';
            c2 = c = string.charAt(c);
        }
        if (!Character.isWhitespace(c2)) {
            return c2;
        }
        throw new ATParseEx();
    }

    public boolean hasMore() {
        boolean bl = this.mNext < this.mLine.length();
        return bl;
    }

    public boolean nextBoolean() {
        this.nextTok();
        int n = this.mTokEnd;
        int n2 = this.mTokStart;
        if (n - n2 <= 1) {
            n = this.mLine.charAt(n2);
            if (n == 48) {
                return false;
            }
            if (n == 49) {
                return true;
            }
            throw new ATParseEx();
        }
        throw new ATParseEx();
    }

    public int nextInt() {
        int n = 0;
        this.nextTok();
        for (int i = this.mTokStart; i < this.mTokEnd; ++i) {
            char c = this.mLine.charAt(i);
            if (c >= '0' && c <= '9') {
                n = n * 10 + (c - 48);
                continue;
            }
            throw new ATParseEx();
        }
        return n;
    }

    public String nextString() {
        this.nextTok();
        return this.mLine.substring(this.mTokStart, this.mTokEnd);
    }
}

