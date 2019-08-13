/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.uicc.asn1;

public class TagNotFoundException
extends Exception {
    private final int mTag;

    public TagNotFoundException(int n) {
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

