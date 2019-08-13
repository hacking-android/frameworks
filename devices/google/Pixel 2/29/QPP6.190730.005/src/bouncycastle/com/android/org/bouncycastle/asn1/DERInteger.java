/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1Integer;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.math.BigInteger;

public class DERInteger
extends ASN1Integer {
    @UnsupportedAppUsage
    public DERInteger(long l) {
        super(l);
    }

    @UnsupportedAppUsage
    public DERInteger(BigInteger bigInteger) {
        super(bigInteger);
    }

    public DERInteger(byte[] arrby) {
        super(arrby, true);
    }
}

