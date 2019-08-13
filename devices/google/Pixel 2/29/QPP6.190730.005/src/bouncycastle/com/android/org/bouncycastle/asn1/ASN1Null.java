/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1OutputStream;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import java.io.IOException;

public abstract class ASN1Null
extends ASN1Primitive {
    ASN1Null() {
    }

    public static ASN1Null getInstance(Object object) {
        if (object instanceof ASN1Null) {
            return (ASN1Null)object;
        }
        if (object != null) {
            try {
                ASN1Null aSN1Null = ASN1Null.getInstance(ASN1Primitive.fromByteArray((byte[])object));
                return aSN1Null;
            }
            catch (ClassCastException classCastException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("unknown object in getInstance(): ");
                stringBuilder.append(object.getClass().getName());
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            catch (IOException iOException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("failed to construct NULL from byte[]: ");
                ((StringBuilder)object).append(iOException.getMessage());
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
        }
        return null;
    }

    @Override
    boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        return aSN1Primitive instanceof ASN1Null;
    }

    @Override
    abstract void encode(ASN1OutputStream var1) throws IOException;

    @Override
    public int hashCode() {
        return -1;
    }

    public String toString() {
        return "NULL";
    }
}

