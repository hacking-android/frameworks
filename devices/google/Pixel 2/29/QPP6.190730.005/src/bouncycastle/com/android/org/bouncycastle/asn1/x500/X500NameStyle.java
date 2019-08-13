/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x500;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.x500.RDN;
import com.android.org.bouncycastle.asn1.x500.X500Name;

public interface X500NameStyle {
    public boolean areEqual(X500Name var1, X500Name var2);

    public ASN1ObjectIdentifier attrNameToOID(String var1);

    public int calculateHashCode(X500Name var1);

    public RDN[] fromString(String var1);

    public String[] oidToAttrNames(ASN1ObjectIdentifier var1);

    public String oidToDisplayName(ASN1ObjectIdentifier var1);

    public ASN1Encodable stringToValue(ASN1ObjectIdentifier var1, String var2);

    public String toString(X500Name var1);
}

