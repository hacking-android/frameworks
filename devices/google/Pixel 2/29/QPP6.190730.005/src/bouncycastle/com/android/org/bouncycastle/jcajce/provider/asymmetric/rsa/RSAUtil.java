/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.rsa;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import com.android.org.bouncycastle.crypto.params.RSAKeyParameters;
import com.android.org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import com.android.org.bouncycastle.util.Fingerprint;
import java.math.BigInteger;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class RSAUtil {
    public static final ASN1ObjectIdentifier[] rsaOids = new ASN1ObjectIdentifier[]{PKCSObjectIdentifiers.rsaEncryption, X509ObjectIdentifiers.id_ea_rsa, PKCSObjectIdentifiers.id_RSAES_OAEP, PKCSObjectIdentifiers.id_RSASSA_PSS};

    static String generateExponentFingerprint(BigInteger bigInteger) {
        return new Fingerprint(bigInteger.toByteArray(), 32).toString();
    }

    static String generateKeyFingerprint(BigInteger bigInteger) {
        return new Fingerprint(bigInteger.toByteArray()).toString();
    }

    static RSAKeyParameters generatePrivateKeyParameter(RSAPrivateKey rSAPrivateKey) {
        if (rSAPrivateKey instanceof RSAPrivateCrtKey) {
            rSAPrivateKey = (RSAPrivateCrtKey)rSAPrivateKey;
            return new RSAPrivateCrtKeyParameters(rSAPrivateKey.getModulus(), rSAPrivateKey.getPublicExponent(), rSAPrivateKey.getPrivateExponent(), rSAPrivateKey.getPrimeP(), rSAPrivateKey.getPrimeQ(), rSAPrivateKey.getPrimeExponentP(), rSAPrivateKey.getPrimeExponentQ(), rSAPrivateKey.getCrtCoefficient());
        }
        return new RSAKeyParameters(true, rSAPrivateKey.getModulus(), rSAPrivateKey.getPrivateExponent());
    }

    static RSAKeyParameters generatePublicKeyParameter(RSAPublicKey rSAPublicKey) {
        return new RSAKeyParameters(false, rSAPublicKey.getModulus(), rSAPublicKey.getPublicExponent());
    }

    public static boolean isRsaOid(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        ASN1ObjectIdentifier[] arraSN1ObjectIdentifier;
        for (int i = 0; i != (arraSN1ObjectIdentifier = rsaOids).length; ++i) {
            if (!aSN1ObjectIdentifier.equals(arraSN1ObjectIdentifier[i])) continue;
            return true;
        }
        return false;
    }
}

