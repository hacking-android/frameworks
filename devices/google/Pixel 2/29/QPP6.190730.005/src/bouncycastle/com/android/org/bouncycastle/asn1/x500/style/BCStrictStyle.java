/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x500.style;

import com.android.org.bouncycastle.asn1.x500.RDN;
import com.android.org.bouncycastle.asn1.x500.X500Name;
import com.android.org.bouncycastle.asn1.x500.X500NameStyle;
import com.android.org.bouncycastle.asn1.x500.style.BCStyle;

public class BCStrictStyle
extends BCStyle {
    public static final X500NameStyle INSTANCE = new BCStrictStyle();

    @Override
    public boolean areEqual(X500Name arrrDN, X500Name arrrDN2) {
        if ((arrrDN = arrrDN.getRDNs()).length != (arrrDN2 = arrrDN2.getRDNs()).length) {
            return false;
        }
        for (int i = 0; i != arrrDN.length; ++i) {
            if (this.rdnAreEqual(arrrDN[i], arrrDN2[i])) continue;
            return false;
        }
        return true;
    }
}

