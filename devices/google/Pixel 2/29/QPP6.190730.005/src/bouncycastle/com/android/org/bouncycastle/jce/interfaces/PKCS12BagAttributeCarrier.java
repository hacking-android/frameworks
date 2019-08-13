/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jce.interfaces;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import java.util.Enumeration;

public interface PKCS12BagAttributeCarrier {
    public ASN1Encodable getBagAttribute(ASN1ObjectIdentifier var1);

    public Enumeration getBagAttributeKeys();

    public void setBagAttribute(ASN1ObjectIdentifier var1, ASN1Encodable var2);
}

