/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1Primitive;
import java.io.IOException;

public interface InMemoryRepresentable {
    public ASN1Primitive getLoadedObject() throws IOException;
}

