/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1OutputStream;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.StreamUtil;
import com.android.org.bouncycastle.util.Arrays;
import com.android.org.bouncycastle.util.Properties;
import java.io.IOException;
import java.math.BigInteger;

public class ASN1Enumerated
extends ASN1Primitive {
    private static ASN1Enumerated[] cache = new ASN1Enumerated[12];
    private final byte[] bytes;

    public ASN1Enumerated(int n) {
        this.bytes = BigInteger.valueOf(n).toByteArray();
    }

    public ASN1Enumerated(BigInteger bigInteger) {
        this.bytes = bigInteger.toByteArray();
    }

    public ASN1Enumerated(byte[] arrby) {
        if (!Properties.isOverrideSet("com.android.org.bouncycastle.asn1.allow_unsafe_integer") && ASN1Integer.isMalformed(arrby)) {
            throw new IllegalArgumentException("malformed enumerated");
        }
        this.bytes = Arrays.clone(arrby);
    }

    static ASN1Enumerated fromOctetString(byte[] arrby) {
        if (arrby.length > 1) {
            return new ASN1Enumerated(arrby);
        }
        if (arrby.length != 0) {
            ASN1Enumerated aSN1Enumerated;
            int n = arrby[0] & 255;
            ASN1Enumerated[] arraSN1Enumerated = cache;
            if (n >= arraSN1Enumerated.length) {
                return new ASN1Enumerated(Arrays.clone(arrby));
            }
            ASN1Enumerated aSN1Enumerated2 = aSN1Enumerated = arraSN1Enumerated[n];
            if (aSN1Enumerated == null) {
                arraSN1Enumerated[n] = aSN1Enumerated2 = new ASN1Enumerated(Arrays.clone(arrby));
            }
            return aSN1Enumerated2;
        }
        throw new IllegalArgumentException("ENUMERATED has zero length");
    }

    public static ASN1Enumerated getInstance(ASN1TaggedObject aSN1Primitive, boolean bl) {
        aSN1Primitive = aSN1Primitive.getObject();
        if (!bl && !(aSN1Primitive instanceof ASN1Enumerated)) {
            return ASN1Enumerated.fromOctetString(((ASN1OctetString)aSN1Primitive).getOctets());
        }
        return ASN1Enumerated.getInstance(aSN1Primitive);
    }

    public static ASN1Enumerated getInstance(Object object) {
        if (object != null && !(object instanceof ASN1Enumerated)) {
            if (object instanceof byte[]) {
                try {
                    object = (ASN1Enumerated)ASN1Enumerated.fromByteArray((byte[])object);
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
        return (ASN1Enumerated)object;
    }

    @Override
    boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof ASN1Enumerated)) {
            return false;
        }
        aSN1Primitive = (ASN1Enumerated)aSN1Primitive;
        return Arrays.areEqual(this.bytes, ((ASN1Enumerated)aSN1Primitive).bytes);
    }

    @Override
    void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        aSN1OutputStream.writeEncoded(10, this.bytes);
    }

    @Override
    int encodedLength() {
        return StreamUtil.calculateBodyLength(this.bytes.length) + 1 + this.bytes.length;
    }

    public BigInteger getValue() {
        return new BigInteger(this.bytes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.bytes);
    }

    @Override
    boolean isConstructed() {
        return false;
    }
}

