/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.alsa;

public class LineTokenizer {
    public static final int kTokenNotFound = -1;
    private final String mDelimiters;

    public LineTokenizer(String string2) {
        this.mDelimiters = string2;
    }

    int nextDelimiter(String string2, int n) {
        int n2;
        int n3 = string2.length();
        do {
            n2 = -1;
            if (n >= n3 || this.mDelimiters.indexOf(string2.charAt(n)) != -1) break;
            ++n;
        } while (true);
        if (n < n3) {
            n2 = n;
        }
        return n2;
    }

    int nextToken(String string2, int n) {
        int n2;
        int n3 = string2.length();
        do {
            n2 = -1;
            if (n >= n3 || this.mDelimiters.indexOf(string2.charAt(n)) == -1) break;
            ++n;
        } while (true);
        if (n < n3) {
            n2 = n;
        }
        return n2;
    }
}

