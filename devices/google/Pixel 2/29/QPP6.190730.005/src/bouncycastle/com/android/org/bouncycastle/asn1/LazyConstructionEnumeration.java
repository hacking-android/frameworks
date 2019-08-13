/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1InputStream;
import com.android.org.bouncycastle.asn1.ASN1ParsingException;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import java.io.IOException;
import java.util.Enumeration;

class LazyConstructionEnumeration
implements Enumeration {
    private ASN1InputStream aIn;
    private Object nextObj;

    public LazyConstructionEnumeration(byte[] arrby) {
        this.aIn = new ASN1InputStream(arrby, true);
        this.nextObj = this.readObject();
    }

    private Object readObject() {
        try {
            ASN1Primitive aSN1Primitive = this.aIn.readObject();
            return aSN1Primitive;
        }
        catch (IOException iOException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("malformed DER construction: ");
            stringBuilder.append(iOException);
            throw new ASN1ParsingException(stringBuilder.toString(), iOException);
        }
    }

    @Override
    public boolean hasMoreElements() {
        boolean bl = this.nextObj != null;
        return bl;
    }

    public Object nextElement() {
        Object object = this.nextObj;
        this.nextObj = this.readObject();
        return object;
    }
}

