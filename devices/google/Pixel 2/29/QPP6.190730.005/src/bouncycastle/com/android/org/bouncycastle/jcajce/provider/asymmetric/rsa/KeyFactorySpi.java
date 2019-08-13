/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.rsa;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPrivateCrtKey;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPrivateKey;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPublicKey;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.rsa.RSAUtil;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.ExtendedInvalidKeySpecException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class KeyFactorySpi
extends BaseKeyFactorySpi {
    @Override
    protected PrivateKey engineGeneratePrivate(KeySpec object) throws InvalidKeySpecException {
        if (object instanceof PKCS8EncodedKeySpec) {
            try {
                PrivateKey privateKey = this.generatePrivate(PrivateKeyInfo.getInstance(((PKCS8EncodedKeySpec)object).getEncoded()));
                return privateKey;
            }
            catch (Exception exception) {
                try {
                    object = new BCRSAPrivateCrtKey(com.android.org.bouncycastle.asn1.pkcs.RSAPrivateKey.getInstance(((PKCS8EncodedKeySpec)object).getEncoded()));
                    return object;
                }
                catch (Exception exception2) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("unable to process key spec: ");
                    stringBuilder.append(exception.toString());
                    throw new ExtendedInvalidKeySpecException(stringBuilder.toString(), exception);
                }
            }
        }
        if (object instanceof RSAPrivateCrtKeySpec) {
            return new BCRSAPrivateCrtKey((RSAPrivateCrtKeySpec)object);
        }
        if (object instanceof RSAPrivateKeySpec) {
            return new BCRSAPrivateKey((RSAPrivateKeySpec)object);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("unknown KeySpec type: ");
        stringBuilder.append(object.getClass().getName());
        throw new InvalidKeySpecException(stringBuilder.toString());
    }

    @Override
    protected PublicKey engineGeneratePublic(KeySpec keySpec) throws InvalidKeySpecException {
        if (keySpec instanceof RSAPublicKeySpec) {
            return new BCRSAPublicKey((RSAPublicKeySpec)keySpec);
        }
        return super.engineGeneratePublic(keySpec);
    }

    @Override
    protected KeySpec engineGetKeySpec(Key key, Class class_) throws InvalidKeySpecException {
        if (class_.isAssignableFrom(RSAPublicKeySpec.class) && key instanceof RSAPublicKey) {
            key = (RSAPublicKey)key;
            return new RSAPublicKeySpec(key.getModulus(), key.getPublicExponent());
        }
        if (class_.isAssignableFrom(RSAPrivateKeySpec.class) && key instanceof RSAPrivateKey) {
            key = (RSAPrivateKey)key;
            return new RSAPrivateKeySpec(key.getModulus(), key.getPrivateExponent());
        }
        if (class_.isAssignableFrom(RSAPrivateCrtKeySpec.class) && key instanceof RSAPrivateCrtKey) {
            key = (RSAPrivateCrtKey)key;
            return new RSAPrivateCrtKeySpec(key.getModulus(), key.getPublicExponent(), key.getPrivateExponent(), key.getPrimeP(), key.getPrimeQ(), key.getPrimeExponentP(), key.getPrimeExponentQ(), key.getCrtCoefficient());
        }
        return super.engineGetKeySpec(key, class_);
    }

    @Override
    protected Key engineTranslateKey(Key key) throws InvalidKeyException {
        if (key instanceof RSAPublicKey) {
            return new BCRSAPublicKey((RSAPublicKey)key);
        }
        if (key instanceof RSAPrivateCrtKey) {
            return new BCRSAPrivateCrtKey((RSAPrivateCrtKey)key);
        }
        if (key instanceof RSAPrivateKey) {
            return new BCRSAPrivateKey((RSAPrivateKey)key);
        }
        throw new InvalidKeyException("key type unknown");
    }

    @Override
    public PrivateKey generatePrivate(PrivateKeyInfo object) throws IOException {
        ASN1Object aSN1Object = ((PrivateKeyInfo)object).getPrivateKeyAlgorithm().getAlgorithm();
        if (RSAUtil.isRsaOid((ASN1ObjectIdentifier)aSN1Object)) {
            aSN1Object = com.android.org.bouncycastle.asn1.pkcs.RSAPrivateKey.getInstance(((PrivateKeyInfo)object).parsePrivateKey());
            if (((com.android.org.bouncycastle.asn1.pkcs.RSAPrivateKey)aSN1Object).getCoefficient().intValue() == 0) {
                return new BCRSAPrivateKey((com.android.org.bouncycastle.asn1.pkcs.RSAPrivateKey)aSN1Object);
            }
            return new BCRSAPrivateCrtKey((PrivateKeyInfo)object);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("algorithm identifier ");
        ((StringBuilder)object).append(aSN1Object);
        ((StringBuilder)object).append(" in key not recognised");
        throw new IOException(((StringBuilder)object).toString());
    }

    @Override
    public PublicKey generatePublic(SubjectPublicKeyInfo object) throws IOException {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = ((SubjectPublicKeyInfo)object).getAlgorithm().getAlgorithm();
        if (RSAUtil.isRsaOid(aSN1ObjectIdentifier)) {
            return new BCRSAPublicKey((SubjectPublicKeyInfo)object);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("algorithm identifier ");
        ((StringBuilder)object).append(aSN1ObjectIdentifier);
        ((StringBuilder)object).append(" in key not recognised");
        throw new IOException(((StringBuilder)object).toString());
    }
}

