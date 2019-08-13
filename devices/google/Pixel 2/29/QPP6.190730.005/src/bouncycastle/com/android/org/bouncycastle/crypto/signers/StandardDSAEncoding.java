/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.signers;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.crypto.signers.DSAEncoding;
import com.android.org.bouncycastle.util.Arrays;
import java.io.IOException;
import java.math.BigInteger;

public class StandardDSAEncoding
implements DSAEncoding {
    public static final StandardDSAEncoding INSTANCE = new StandardDSAEncoding();

    protected BigInteger checkValue(BigInteger bigInteger, BigInteger bigInteger2) {
        if (bigInteger2.signum() >= 0 && (bigInteger == null || bigInteger2.compareTo(bigInteger) < 0)) {
            return bigInteger2;
        }
        throw new IllegalArgumentException("Value out of range");
    }

    @Override
    public BigInteger[] decode(BigInteger bigInteger, byte[] arrby) throws IOException {
        Object object = (ASN1Sequence)ASN1Primitive.fromByteArray(arrby);
        if (((ASN1Sequence)object).size() == 2) {
            BigInteger bigInteger2 = this.decodeValue(bigInteger, (ASN1Sequence)object, 0);
            if (Arrays.areEqual(this.encode(bigInteger, bigInteger2, (BigInteger)(object = this.decodeValue(bigInteger, (ASN1Sequence)object, 1))), arrby)) {
                return new BigInteger[]{bigInteger2, object};
            }
        }
        throw new IllegalArgumentException("Malformed signature");
    }

    protected BigInteger decodeValue(BigInteger bigInteger, ASN1Sequence aSN1Sequence, int n) {
        return this.checkValue(bigInteger, ((ASN1Integer)aSN1Sequence.getObjectAt(n)).getValue());
    }

    @Override
    public byte[] encode(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) throws IOException {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        this.encodeValue(bigInteger, aSN1EncodableVector, bigInteger2);
        this.encodeValue(bigInteger, aSN1EncodableVector, bigInteger3);
        return new DERSequence(aSN1EncodableVector).getEncoded("DER");
    }

    protected void encodeValue(BigInteger bigInteger, ASN1EncodableVector aSN1EncodableVector, BigInteger bigInteger2) {
        aSN1EncodableVector.add(new ASN1Integer(this.checkValue(bigInteger, bigInteger2)));
    }
}

