/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.InMemoryRepresentable;
import java.io.IOException;

public interface ASN1TaggedObjectParser
extends ASN1Encodable,
InMemoryRepresentable {
    public ASN1Encodable getObjectParser(int var1, boolean var2) throws IOException;

    public int getTagNo();
}

