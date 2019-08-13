/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.dsa;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import com.android.org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import com.android.org.bouncycastle.crypto.params.DSAParameters;
import com.android.org.bouncycastle.crypto.params.DSAPrivateKeyParameters;
import com.android.org.bouncycastle.crypto.params.DSAPublicKeyParameters;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.dsa.BCDSAPublicKey;
import com.android.org.bouncycastle.util.Arrays;
import com.android.org.bouncycastle.util.Fingerprint;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;

public class DSAUtil {
    public static final ASN1ObjectIdentifier[] dsaOids = new ASN1ObjectIdentifier[]{X9ObjectIdentifiers.id_dsa, OIWObjectIdentifiers.dsaWithSHA1, X9ObjectIdentifiers.id_dsa_with_sha1};

    static String generateKeyFingerprint(BigInteger bigInteger, DSAParams dSAParams) {
        return new Fingerprint(Arrays.concatenate(bigInteger.toByteArray(), dSAParams.getP().toByteArray(), dSAParams.getQ().toByteArray(), dSAParams.getG().toByteArray())).toString();
    }

    public static AsymmetricKeyParameter generatePrivateKeyParameter(PrivateKey privateKey) throws InvalidKeyException {
        if (privateKey instanceof DSAPrivateKey) {
            privateKey = (DSAPrivateKey)privateKey;
            return new DSAPrivateKeyParameters(privateKey.getX(), new DSAParameters(privateKey.getParams().getP(), privateKey.getParams().getQ(), privateKey.getParams().getG()));
        }
        throw new InvalidKeyException("can't identify DSA private key.");
    }

    public static AsymmetricKeyParameter generatePublicKeyParameter(PublicKey publicKey) throws InvalidKeyException {
        if (publicKey instanceof BCDSAPublicKey) {
            return ((BCDSAPublicKey)publicKey).engineGetKeyParameters();
        }
        if (publicKey instanceof DSAPublicKey) {
            return new BCDSAPublicKey((DSAPublicKey)publicKey).engineGetKeyParameters();
        }
        try {
            byte[] arrby = publicKey.getEncoded();
            Object object = new BCDSAPublicKey(SubjectPublicKeyInfo.getInstance(arrby));
            object = ((BCDSAPublicKey)object).engineGetKeyParameters();
            return object;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("can't identify DSA public key: ");
            stringBuilder.append(publicKey.getClass().getName());
            throw new InvalidKeyException(stringBuilder.toString());
        }
    }

    public static boolean isDsaOid(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        ASN1ObjectIdentifier[] arraSN1ObjectIdentifier;
        for (int i = 0; i != (arraSN1ObjectIdentifier = dsaOids).length; ++i) {
            if (!aSN1ObjectIdentifier.equals(arraSN1ObjectIdentifier[i])) continue;
            return true;
        }
        return false;
    }

    static DSAParameters toDSAParameters(DSAParams dSAParams) {
        if (dSAParams != null) {
            return new DSAParameters(dSAParams.getP(), dSAParams.getQ(), dSAParams.getG());
        }
        return null;
    }
}

