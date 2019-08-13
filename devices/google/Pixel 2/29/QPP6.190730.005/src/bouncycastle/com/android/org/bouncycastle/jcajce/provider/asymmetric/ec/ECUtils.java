/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.ec;

import com.android.org.bouncycastle.asn1.ASN1Null;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.DERNull;
import com.android.org.bouncycastle.asn1.x9.X962Parameters;
import com.android.org.bouncycastle.asn1.x9.X9ECParameters;
import com.android.org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import com.android.org.bouncycastle.crypto.params.ECPublicKeyParameters;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import com.android.org.bouncycastle.jce.spec.ECNamedCurveSpec;
import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECPoint;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.EllipticCurve;

class ECUtils {
    ECUtils() {
    }

    static AsymmetricKeyParameter generatePublicKeyParameter(PublicKey object) throws InvalidKeyException {
        object = object instanceof BCECPublicKey ? ((BCECPublicKey)object).engineGetKeyParameters() : ECUtil.generatePublicKeyParameter((PublicKey)object);
        return object;
    }

    static X9ECParameters getDomainParametersFromGenSpec(ECGenParameterSpec eCGenParameterSpec) {
        return ECUtils.getDomainParametersFromName(eCGenParameterSpec.getName());
    }

    static X962Parameters getDomainParametersFromName(ECParameterSpec object, boolean bl) {
        if (object instanceof ECNamedCurveSpec) {
            ASN1ObjectIdentifier aSN1ObjectIdentifier;
            ASN1ObjectIdentifier aSN1ObjectIdentifier2 = aSN1ObjectIdentifier = ECUtil.getNamedCurveOid(((ECNamedCurveSpec)object).getName());
            if (aSN1ObjectIdentifier == null) {
                aSN1ObjectIdentifier2 = new ASN1ObjectIdentifier(((ECNamedCurveSpec)object).getName());
            }
            object = new X962Parameters(aSN1ObjectIdentifier2);
        } else if (object == null) {
            object = new X962Parameters(DERNull.INSTANCE);
        } else {
            ECCurve eCCurve = EC5Util.convertCurve(((ECParameterSpec)object).getCurve());
            object = new X962Parameters(new X9ECParameters(eCCurve, EC5Util.convertPoint(eCCurve, ((ECParameterSpec)object).getGenerator(), bl), ((ECParameterSpec)object).getOrder(), BigInteger.valueOf(((ECParameterSpec)object).getCofactor()), ((ECParameterSpec)object).getCurve().getSeed()));
        }
        return object;
    }

    static X9ECParameters getDomainParametersFromName(String object) {
        Object object2;
        block11 : {
            block10 : {
                object2 = object;
                if (((String)object).charAt(0) < '0') break block10;
                object2 = object;
                if (((String)object).charAt(0) > '2') break block10;
                object2 = object;
                object2 = object;
                ASN1ObjectIdentifier aSN1ObjectIdentifier = new ASN1ObjectIdentifier((String)object);
                object2 = object;
                object = ECUtil.getNamedCurveByOid(aSN1ObjectIdentifier);
            }
            object2 = object;
            if (((String)object).indexOf(32) <= 0) break block11;
            object2 = object;
            object2 = object = ((String)object).substring(((String)object).indexOf(32) + 1);
            object = ECUtil.getNamedCurveByName((String)object);
        }
        object2 = object;
        try {
            object = ECUtil.getNamedCurveByName((String)object);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            object = ECUtil.getNamedCurveByName((String)object2);
        }
        return object;
    }
}

