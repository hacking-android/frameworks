/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.misc;

import com.android.org.bouncycastle.asn1.DERIA5String;

public class VerisignCzagExtension
extends DERIA5String {
    public VerisignCzagExtension(DERIA5String dERIA5String) {
        super(dERIA5String.getString());
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("VerisignCzagExtension: ");
        stringBuilder.append(this.getString());
        return stringBuilder.toString();
    }
}

