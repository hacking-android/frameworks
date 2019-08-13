/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.cms;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.DEROctetString;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.util.Arrays;
import java.math.BigInteger;

public class GCMParameters
extends ASN1Object {
    private int icvLen;
    private byte[] nonce;

    private GCMParameters(ASN1Sequence aSN1Sequence) {
        this.nonce = ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(0)).getOctets();
        this.icvLen = aSN1Sequence.size() == 2 ? ASN1Integer.getInstance(aSN1Sequence.getObjectAt(1)).getValue().intValue() : 12;
    }

    public GCMParameters(byte[] arrby, int n) {
        this.nonce = Arrays.clone(arrby);
        this.icvLen = n;
    }

    public static GCMParameters getInstance(Object object) {
        if (object instanceof GCMParameters) {
            return (GCMParameters)object;
        }
        if (object != null) {
            return new GCMParameters(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public int getIcvLen() {
        return this.icvLen;
    }

    public byte[] getNonce() {
        return Arrays.clone(this.nonce);
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(new DEROctetString(this.nonce));
        int n = this.icvLen;
        if (n != 12) {
            aSN1EncodableVector.add(new ASN1Integer(n));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}

