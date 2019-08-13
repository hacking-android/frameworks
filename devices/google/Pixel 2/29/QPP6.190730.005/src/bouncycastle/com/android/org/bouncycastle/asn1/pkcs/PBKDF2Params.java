/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.pkcs;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.DERNull;
import com.android.org.bouncycastle.asn1.DEROctetString;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.util.Arrays;
import java.math.BigInteger;
import java.util.Enumeration;

public class PBKDF2Params
extends ASN1Object {
    private static final AlgorithmIdentifier algid_hmacWithSHA1 = new AlgorithmIdentifier(PKCSObjectIdentifiers.id_hmacWithSHA1, DERNull.INSTANCE);
    private final ASN1Integer iterationCount;
    private final ASN1Integer keyLength;
    private final ASN1OctetString octStr;
    private final AlgorithmIdentifier prf;

    private PBKDF2Params(ASN1Sequence aSN1Sequence) {
        Enumeration enumeration = aSN1Sequence.getObjects();
        this.octStr = (ASN1OctetString)enumeration.nextElement();
        this.iterationCount = (ASN1Integer)enumeration.nextElement();
        if (enumeration.hasMoreElements()) {
            aSN1Sequence = enumeration.nextElement();
            if (aSN1Sequence instanceof ASN1Integer) {
                this.keyLength = ASN1Integer.getInstance(aSN1Sequence);
                aSN1Sequence = enumeration.hasMoreElements() ? enumeration.nextElement() : null;
            } else {
                this.keyLength = null;
            }
            this.prf = aSN1Sequence != null ? AlgorithmIdentifier.getInstance(aSN1Sequence) : null;
        } else {
            this.keyLength = null;
            this.prf = null;
        }
    }

    public PBKDF2Params(byte[] arrby, int n) {
        this(arrby, n, 0);
    }

    public PBKDF2Params(byte[] arrby, int n, int n2) {
        this(arrby, n, n2, null);
    }

    public PBKDF2Params(byte[] arrby, int n, int n2, AlgorithmIdentifier algorithmIdentifier) {
        this.octStr = new DEROctetString(Arrays.clone(arrby));
        this.iterationCount = new ASN1Integer(n);
        this.keyLength = n2 > 0 ? new ASN1Integer(n2) : null;
        this.prf = algorithmIdentifier;
    }

    public PBKDF2Params(byte[] arrby, int n, AlgorithmIdentifier algorithmIdentifier) {
        this(arrby, n, 0, algorithmIdentifier);
    }

    public static PBKDF2Params getInstance(Object object) {
        if (object instanceof PBKDF2Params) {
            return (PBKDF2Params)object;
        }
        if (object != null) {
            return new PBKDF2Params(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public BigInteger getIterationCount() {
        return this.iterationCount.getValue();
    }

    public BigInteger getKeyLength() {
        ASN1Integer aSN1Integer = this.keyLength;
        if (aSN1Integer != null) {
            return aSN1Integer.getValue();
        }
        return null;
    }

    public AlgorithmIdentifier getPrf() {
        AlgorithmIdentifier algorithmIdentifier = this.prf;
        if (algorithmIdentifier != null) {
            return algorithmIdentifier;
        }
        return algid_hmacWithSHA1;
    }

    public byte[] getSalt() {
        return this.octStr.getOctets();
    }

    public boolean isDefaultPrf() {
        AlgorithmIdentifier algorithmIdentifier = this.prf;
        boolean bl = algorithmIdentifier == null || algorithmIdentifier.equals(algid_hmacWithSHA1);
        return bl;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.octStr);
        aSN1EncodableVector.add(this.iterationCount);
        ASN1Object aSN1Object = this.keyLength;
        if (aSN1Object != null) {
            aSN1EncodableVector.add(aSN1Object);
        }
        if ((aSN1Object = this.prf) != null && !aSN1Object.equals(algid_hmacWithSHA1)) {
            aSN1EncodableVector.add(this.prf);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}

