/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x509;

public class X509NameTokenizer {
    private StringBuffer buf = new StringBuffer();
    private int index;
    private char separator;
    private String value;

    public X509NameTokenizer(String string) {
        this(string, ',');
    }

    public X509NameTokenizer(String string, char c) {
        this.value = string;
        this.index = -1;
        this.separator = c;
    }

    public boolean hasMoreTokens() {
        boolean bl = this.index != this.value.length();
        return bl;
    }

    public String nextToken() {
        int n;
        if (this.index == this.value.length()) {
            return null;
        }
        boolean bl = false;
        boolean bl2 = false;
        this.buf.setLength(0);
        for (n = this.index + 1; n != this.value.length(); ++n) {
            boolean bl3;
            char c = this.value.charAt(n);
            if (c == '\"') {
                bl3 = bl;
                if (!bl2) {
                    bl3 = !bl;
                }
                this.buf.append(c);
                bl2 = false;
                bl = bl3;
                bl3 = bl2;
            } else if (!bl2 && !bl) {
                if (c == '\\') {
                    this.buf.append(c);
                    bl3 = true;
                } else {
                    StringBuffer stringBuffer;
                    if (c == this.separator) break;
                    if (c == '#' && (stringBuffer = this.buf).charAt(stringBuffer.length() - 1) == '=') {
                        this.buf.append('\\');
                    } else if (c == '+' && this.separator != '+') {
                        this.buf.append('\\');
                    }
                    this.buf.append(c);
                    bl3 = bl2;
                }
            } else {
                this.buf.append(c);
                bl3 = false;
            }
            bl2 = bl3;
        }
        this.index = n;
        return this.buf.toString();
    }
}

