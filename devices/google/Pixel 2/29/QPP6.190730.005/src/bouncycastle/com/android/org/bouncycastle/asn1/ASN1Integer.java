/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1OutputStream;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.StreamUtil;
import com.android.org.bouncycastle.util.Arrays;
import com.android.org.bouncycastle.util.Properties;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.IOException;
import java.math.BigInteger;

public class ASN1Integer
extends ASN1Primitive {
    private final byte[] bytes;

    public ASN1Integer(long l) {
        this.bytes = BigInteger.valueOf(l).toByteArray();
    }

    @UnsupportedAppUsage
    public ASN1Integer(BigInteger bigInteger) {
        this.bytes = bigInteger.toByteArray();
    }

    public ASN1Integer(byte[] arrby) {
        this(arrby, true);
    }

    ASN1Integer(byte[] arrby, boolean bl) {
        if (!Properties.isOverrideSet("com.android.org.bouncycastle.asn1.allow_unsafe_integer") && ASN1Integer.isMalformed(arrby)) {
            throw new IllegalArgumentException("malformed integer");
        }
        if (bl) {
            arrby = Arrays.clone(arrby);
        }
        this.bytes = arrby;
    }

    public static ASN1Integer getInstance(ASN1TaggedObject aSN1Primitive, boolean bl) {
        aSN1Primitive = aSN1Primitive.getObject();
        if (!bl && !(aSN1Primitive instanceof ASN1Integer)) {
            return new ASN1Integer(ASN1OctetString.getInstance(aSN1Primitive).getOctets());
        }
        return ASN1Integer.getInstance(aSN1Primitive);
    }

    public static ASN1Integer getInstance(Object object) {
        if (object != null && !(object instanceof ASN1Integer)) {
            if (object instanceof byte[]) {
                try {
                    object = (ASN1Integer)ASN1Integer.fromByteArray((byte[])object);
                    return object;
                }
                catch (Exception exception) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("encoding error in getInstance: ");
                    ((StringBuilder)object).append(exception.toString());
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("illegal object in getInstance: ");
            stringBuilder.append(object.getClass().getName());
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return (ASN1Integer)object;
    }

    static boolean isMalformed(byte[] arrby) {
        if (arrby.length > 1) {
            if (arrby[0] == 0 && (arrby[1] & 128) == 0) {
                return true;
            }
            if (arrby[0] == -1 && (arrby[1] & 128) != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof ASN1Integer)) {
            return false;
        }
        aSN1Primitive = (ASN1Integer)aSN1Primitive;
        return Arrays.areEqual(this.bytes, ((ASN1Integer)aSN1Primitive).bytes);
    }

    @Override
    void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        aSN1OutputStream.writeEncoded(2, this.bytes);
    }

    @Override
    int encodedLength() {
        return StreamUtil.calculateBodyLength(this.bytes.length) + 1 + this.bytes.length;
    }

    public BigInteger getPositiveValue() {
        return new BigInteger(1, this.bytes);
    }

    public BigInteger getValue() {
        return new BigInteger(this.bytes);
    }

    @Override
    public int hashCode() {
        byte[] arrby;
        int n = 0;
        for (int i = 0; i != (arrby = this.bytes).length; ++i) {
            n ^= (arrby[i] & 255) << i % 4;
        }
        return n;
    }

    @Override
    boolean isConstructed() {
        return false;
    }

    public String toString() {
        return this.getValue().toString();
    }
}

