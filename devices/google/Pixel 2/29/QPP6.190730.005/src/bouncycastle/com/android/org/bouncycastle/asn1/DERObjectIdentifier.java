/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import dalvik.annotation.compat.UnsupportedAppUsage;

public class DERObjectIdentifier
extends ASN1ObjectIdentifier {
    DERObjectIdentifier(ASN1ObjectIdentifier aSN1ObjectIdentifier, String string) {
        super(aSN1ObjectIdentifier, string);
    }

    @UnsupportedAppUsage
    public DERObjectIdentifier(String string) {
        super(string);
    }

    DERObjectIdentifier(byte[] arrby) {
        super(arrby);
    }
}

