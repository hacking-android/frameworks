/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims;

@Deprecated
public class ImsException
extends Exception {
    private int mCode;

    public ImsException() {
    }

    public ImsException(String string2, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("(");
        stringBuilder.append(n);
        stringBuilder.append(")");
        super(stringBuilder.toString());
        this.mCode = n;
    }

    public ImsException(String string2, Throwable throwable, int n) {
        super(string2, throwable);
        this.mCode = n;
    }

    public int getCode() {
        return this.mCode;
    }
}

