/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

public class OIDTokenizer {
    private int index;
    private String oid;

    public OIDTokenizer(String string) {
        this.oid = string;
        this.index = 0;
    }

    public boolean hasMoreTokens() {
        boolean bl = this.index != -1;
        return bl;
    }

    public String nextToken() {
        int n = this.index;
        if (n == -1) {
            return null;
        }
        if ((n = this.oid.indexOf(46, n)) == -1) {
            String string = this.oid.substring(this.index);
            this.index = -1;
            return string;
        }
        String string = this.oid.substring(this.index, n);
        this.index = n + 1;
        return string;
    }
}

