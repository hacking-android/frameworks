/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.InMemoryRepresentable;
import java.io.InputStream;

public interface ASN1OctetStringParser
extends ASN1Encodable,
InMemoryRepresentable {
    public InputStream getOctetStream();
}

