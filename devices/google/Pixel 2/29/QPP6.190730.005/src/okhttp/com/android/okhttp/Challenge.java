/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp;

import com.android.okhttp.internal.Util;

public final class Challenge {
    private final String realm;
    private final String scheme;

    public Challenge(String string, String string2) {
        this.scheme = string;
        this.realm = string2;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof Challenge && Util.equal(this.scheme, ((Challenge)object).scheme) && Util.equal(this.realm, ((Challenge)object).realm);
        return bl;
    }

    public String getRealm() {
        return this.realm;
    }

    public String getScheme() {
        return this.scheme;
    }

    public int hashCode() {
        String string = this.realm;
        int n = 0;
        int n2 = string != null ? string.hashCode() : 0;
        string = this.scheme;
        if (string != null) {
            n = string.hashCode();
        }
        return (29 * 31 + n2) * 31 + n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.scheme);
        stringBuilder.append(" realm=\"");
        stringBuilder.append(this.realm);
        stringBuilder.append("\"");
        return stringBuilder.toString();
    }
}

