/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.InMemoryRepresentable;
import java.io.IOException;

public interface ASN1SetParser
extends ASN1Encodable,
InMemoryRepresentable {
    public ASN1Encodable readObject() throws IOException;
}

