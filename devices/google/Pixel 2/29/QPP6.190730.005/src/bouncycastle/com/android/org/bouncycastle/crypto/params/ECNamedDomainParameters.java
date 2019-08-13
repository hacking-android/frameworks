/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.params;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.crypto.params.ECDomainParameters;
import com.android.org.bouncycastle.math.ec.ECConstants;
import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECPoint;
import java.math.BigInteger;

public class ECNamedDomainParameters
extends ECDomainParameters {
    private ASN1ObjectIdentifier name;

    public ECNamedDomainParameters(ASN1ObjectIdentifier aSN1ObjectIdentifier, ECDomainParameters eCDomainParameters) {
        super(eCDomainParameters.getCurve(), eCDomainParameters.getG(), eCDomainParameters.getN(), eCDomainParameters.getH(), eCDomainParameters.getSeed());
        this.name = aSN1ObjectIdentifier;
    }

    public ECNamedDomainParameters(ASN1ObjectIdentifier aSN1ObjectIdentifier, ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger) {
        this(aSN1ObjectIdentifier, eCCurve, eCPoint, bigInteger, ECConstants.ONE, null);
    }

    public ECNamedDomainParameters(ASN1ObjectIdentifier aSN1ObjectIdentifier, ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger, BigInteger bigInteger2) {
        this(aSN1ObjectIdentifier, eCCurve, eCPoint, bigInteger, bigInteger2, null);
    }

    public ECNamedDomainParameters(ASN1ObjectIdentifier aSN1ObjectIdentifier, ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger, BigInteger bigInteger2, byte[] arrby) {
        super(eCCurve, eCPoint, bigInteger, bigInteger2, arrby);
        this.name = aSN1ObjectIdentifier;
    }

    public ASN1ObjectIdentifier getName() {
        return this.name;
    }
}

