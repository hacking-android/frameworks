/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.ntt;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;

public interface NTTObjectIdentifiers {
    public static final ASN1ObjectIdentifier id_camellia128_cbc = new ASN1ObjectIdentifier("1.2.392.200011.61.1.1.1.2");
    public static final ASN1ObjectIdentifier id_camellia128_wrap;
    public static final ASN1ObjectIdentifier id_camellia192_cbc;
    public static final ASN1ObjectIdentifier id_camellia192_wrap;
    public static final ASN1ObjectIdentifier id_camellia256_cbc;
    public static final ASN1ObjectIdentifier id_camellia256_wrap;

    static {
        id_camellia192_cbc = new ASN1ObjectIdentifier("1.2.392.200011.61.1.1.1.3");
        id_camellia256_cbc = new ASN1ObjectIdentifier("1.2.392.200011.61.1.1.1.4");
        id_camellia128_wrap = new ASN1ObjectIdentifier("1.2.392.200011.61.1.1.3.2");
        id_camellia192_wrap = new ASN1ObjectIdentifier("1.2.392.200011.61.1.1.3.3");
        id_camellia256_wrap = new ASN1ObjectIdentifier("1.2.392.200011.61.1.1.3.4");
    }
}

