/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.dh;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.dh.BCDHPrivateKey;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.dh.BCDHPublicKey;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.ExtendedInvalidKeySpecException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPrivateKeySpec;
import javax.crypto.spec.DHPublicKeySpec;

public class KeyFactorySpi
extends BaseKeyFactorySpi {
    @Override
    protected PrivateKey engineGeneratePrivate(KeySpec keySpec) throws InvalidKeySpecException {
        if (keySpec instanceof DHPrivateKeySpec) {
            return new BCDHPrivateKey((DHPrivateKeySpec)keySpec);
        }
        return super.engineGeneratePrivate(keySpec);
    }

    @Override
    protected PublicKey engineGeneratePublic(KeySpec object) throws InvalidKeySpecException {
        if (object instanceof DHPublicKeySpec) {
            try {
                object = new BCDHPublicKey((DHPublicKeySpec)object);
                return object;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw new ExtendedInvalidKeySpecException(illegalArgumentException.getMessage(), illegalArgumentException);
            }
        }
        return super.engineGeneratePublic((KeySpec)object);
    }

    @Override
    protected KeySpec engineGetKeySpec(Key key, Class class_) throws InvalidKeySpecException {
        if (class_.isAssignableFrom(DHPrivateKeySpec.class) && key instanceof DHPrivateKey) {
            key = (DHPrivateKey)key;
            return new DHPrivateKeySpec(key.getX(), key.getParams().getP(), key.getParams().getG());
        }
        if (class_.isAssignableFrom(DHPublicKeySpec.class) && key instanceof DHPublicKey) {
            key = (DHPublicKey)key;
            return new DHPublicKeySpec(key.getY(), key.getParams().getP(), key.getParams().getG());
        }
        return super.engineGetKeySpec(key, class_);
    }

    @Override
    protected Key engineTranslateKey(Key key) throws InvalidKeyException {
        if (key instanceof DHPublicKey) {
            return new BCDHPublicKey((DHPublicKey)key);
        }
        if (key instanceof DHPrivateKey) {
            return new BCDHPrivateKey((DHPrivateKey)key);
        }
        throw new InvalidKeyException("key type unknown");
    }

    @Override
    public PrivateKey generatePrivate(PrivateKeyInfo object) throws IOException {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = ((PrivateKeyInfo)object).getPrivateKeyAlgorithm().getAlgorithm();
        if (aSN1ObjectIdentifier.equals(PKCSObjectIdentifiers.dhKeyAgreement)) {
            return new BCDHPrivateKey((PrivateKeyInfo)object);
        }
        if (aSN1ObjectIdentifier.equals(X9ObjectIdentifiers.dhpublicnumber)) {
            return new BCDHPrivateKey((PrivateKeyInfo)object);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("algorithm identifier ");
        ((StringBuilder)object).append(aSN1ObjectIdentifier);
        ((StringBuilder)object).append(" in key not recognised");
        throw new IOException(((StringBuilder)object).toString());
    }

    @Override
    public PublicKey generatePublic(SubjectPublicKeyInfo object) throws IOException {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = ((SubjectPublicKeyInfo)object).getAlgorithm().getAlgorithm();
        if (aSN1ObjectIdentifier.equals(PKCSObjectIdentifiers.dhKeyAgreement)) {
            return new BCDHPublicKey((SubjectPublicKeyInfo)object);
        }
        if (aSN1ObjectIdentifier.equals(X9ObjectIdentifiers.dhpublicnumber)) {
            return new BCDHPublicKey((SubjectPublicKeyInfo)object);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("algorithm identifier ");
        ((StringBuilder)object).append(aSN1ObjectIdentifier);
        ((StringBuilder)object).append(" in key not recognised");
        throw new IOException(((StringBuilder)object).toString());
    }
}

