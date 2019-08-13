/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1Enumerated;
import java.math.BigInteger;

public class DEREnumerated
extends ASN1Enumerated {
    public DEREnumerated(int n) {
        super(n);
    }

    public DEREnumerated(BigInteger bigInteger) {
        super(bigInteger);
    }

    DEREnumerated(byte[] arrby) {
        super(arrby);
    }
}

