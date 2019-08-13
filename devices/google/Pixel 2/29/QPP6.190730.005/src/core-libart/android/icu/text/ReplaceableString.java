/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.Replaceable;
import android.icu.text.UTF16;

public class ReplaceableString
implements Replaceable {
    private StringBuffer buf;

    public ReplaceableString() {
        this.buf = new StringBuffer();
    }

    public ReplaceableString(String string) {
        this.buf = new StringBuffer(string);
    }

    public ReplaceableString(StringBuffer stringBuffer) {
        this.buf = stringBuffer;
    }

    @Override
    public int char32At(int n) {
        return UTF16.charAt(this.buf, n);
    }

    @Override
    public char charAt(int n) {
        return this.buf.charAt(n);
    }

    @Override
    public void copy(int n, int n2, int n3) {
        if (n == n2 && n >= 0 && n <= this.buf.length()) {
            return;
        }
        char[] arrc = new char[n2 - n];
        this.getChars(n, n2, arrc, 0);
        this.replace(n3, n3, arrc, 0, n2 - n);
    }

    @Override
    public void getChars(int n, int n2, char[] arrc, int n3) {
        if (n != n2) {
            this.buf.getChars(n, n2, arrc, n3);
        }
    }

    @Override
    public boolean hasMetaData() {
        return false;
    }

    @Override
    public int length() {
        return this.buf.length();
    }

    @Override
    public void replace(int n, int n2, String string) {
        this.buf.replace(n, n2, string);
    }

    @Override
    public void replace(int n, int n2, char[] arrc, int n3, int n4) {
        this.buf.delete(n, n2);
        this.buf.insert(n, arrc, n3, n4);
    }

    public String substring(int n, int n2) {
        return this.buf.substring(n, n2);
    }

    public String toString() {
        return this.buf.toString();
    }
}

