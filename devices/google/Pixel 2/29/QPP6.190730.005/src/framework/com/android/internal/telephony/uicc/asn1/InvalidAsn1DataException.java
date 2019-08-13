/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.uicc.asn1;

public class InvalidAsn1DataException
extends Exception {
    private final int mTag;

    public InvalidAsn1DataException(int n, String string2) {
        super(string2);
        this.mTag = n;
    }

    public InvalidAsn1DataException(int n, String string2, Throwable throwable) {
        super(string2, throwable);
        this.mTag = n;
    }

    @Override
    public String getMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.getMessage());
        stringBuilder.append(" (tag=");
        stringBuilder.append(this.mTag);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public int getTag() {
        return this.mTag;
    }
}

