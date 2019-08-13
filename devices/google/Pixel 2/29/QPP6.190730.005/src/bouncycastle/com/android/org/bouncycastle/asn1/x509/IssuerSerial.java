/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x509;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DERBitString;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.x500.X500Name;
import com.android.org.bouncycastle.asn1.x509.GeneralName;
import com.android.org.bouncycastle.asn1.x509.GeneralNames;
import java.math.BigInteger;

public class IssuerSerial
extends ASN1Object {
    GeneralNames issuer;
    DERBitString issuerUID;
    ASN1Integer serial;

    private IssuerSerial(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 2 && aSN1Sequence.size() != 3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Bad sequence size: ");
            stringBuilder.append(aSN1Sequence.size());
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.issuer = GeneralNames.getInstance(aSN1Sequence.getObjectAt(0));
        this.serial = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(1));
        if (aSN1Sequence.size() == 3) {
            this.issuerUID = DERBitString.getInstance(aSN1Sequence.getObjectAt(2));
        }
    }

    public IssuerSerial(X500Name x500Name, BigInteger bigInteger) {
        this(new GeneralNames(new GeneralName(x500Name)), new ASN1Integer(bigInteger));
    }

    public IssuerSerial(GeneralNames generalNames, ASN1Integer aSN1Integer) {
        this.issuer = generalNames;
        this.serial = aSN1Integer;
    }

    public IssuerSerial(GeneralNames generalNames, BigInteger bigInteger) {
        this(generalNames, new ASN1Integer(bigInteger));
    }

    public static IssuerSerial getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return IssuerSerial.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, bl));
    }

    public static IssuerSerial getInstance(Object object) {
        if (object instanceof IssuerSerial) {
            return (IssuerSerial)object;
        }
        if (object != null) {
            return new IssuerSerial(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public GeneralNames getIssuer() {
        return this.issuer;
    }

    public DERBitString getIssuerUID() {
        return this.issuerUID;
    }

    public ASN1Integer getSerial() {
        return this.serial;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.issuer);
        aSN1EncodableVector.add(this.serial);
        DERBitString dERBitString = this.issuerUID;
        if (dERBitString != null) {
            aSN1EncodableVector.add(dERBitString);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}

