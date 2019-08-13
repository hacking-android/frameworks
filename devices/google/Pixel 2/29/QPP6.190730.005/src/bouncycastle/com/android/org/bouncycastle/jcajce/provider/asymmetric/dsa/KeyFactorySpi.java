/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.dsa;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.dsa.BCDSAPrivateKey;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.dsa.BCDSAPublicKey;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.dsa.DSAUtil;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.DSAPrivateKeySpec;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class KeyFactorySpi
extends BaseKeyFactorySpi {
    @Override
    protected PrivateKey engineGeneratePrivate(KeySpec keySpec) throws InvalidKeySpecException {
        if (keySpec instanceof DSAPrivateKeySpec) {
            return new BCDSAPrivateKey((DSAPrivateKeySpec)keySpec);
        }
        return super.engineGeneratePrivate(keySpec);
    }

    @Override
    protected PublicKey engineGeneratePublic(KeySpec object) throws InvalidKeySpecException {
        if (object instanceof DSAPublicKeySpec) {
            try {
                object = new BCDSAPublicKey((DSAPublicKeySpec)object);
                return object;
            }
            catch (Exception exception) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("invalid KeySpec: ");
                stringBuilder.append(exception.getMessage());
                throw new InvalidKeySpecException(stringBuilder.toString()){

                    @Override
                    public Throwable getCause() {
                        return exception;
                    }
                };
            }
        }
        return super.engineGeneratePublic((KeySpec)object);
    }

    @Override
    protected KeySpec engineGetKeySpec(Key key, Class class_) throws InvalidKeySpecException {
        if (class_.isAssignableFrom(DSAPublicKeySpec.class) && key instanceof DSAPublicKey) {
            key = (DSAPublicKey)key;
            return new DSAPublicKeySpec(key.getY(), key.getParams().getP(), key.getParams().getQ(), key.getParams().getG());
        }
        if (class_.isAssignableFrom(DSAPrivateKeySpec.class) && key instanceof DSAPrivateKey) {
            key = (DSAPrivateKey)key;
            return new DSAPrivateKeySpec(key.getX(), key.getParams().getP(), key.getParams().getQ(), key.getParams().getG());
        }
        return super.engineGetKeySpec(key, class_);
    }

    @Override
    protected Key engineTranslateKey(Key key) throws InvalidKeyException {
        if (key instanceof DSAPublicKey) {
            return new BCDSAPublicKey((DSAPublicKey)key);
        }
        if (key instanceof DSAPrivateKey) {
            return new BCDSAPrivateKey((DSAPrivateKey)key);
        }
        throw new InvalidKeyException("key type unknown");
    }

    @Override
    public PrivateKey generatePrivate(PrivateKeyInfo object) throws IOException {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = ((PrivateKeyInfo)object).getPrivateKeyAlgorithm().getAlgorithm();
        if (DSAUtil.isDsaOid(aSN1ObjectIdentifier)) {
            return new BCDSAPrivateKey((PrivateKeyInfo)object);
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
        if (DSAUtil.isDsaOid(aSN1ObjectIdentifier)) {
            return new BCDSAPublicKey((SubjectPublicKeyInfo)object);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("algorithm identifier ");
        ((StringBuilder)object).append(aSN1ObjectIdentifier);
        ((StringBuilder)object).append(" in key not recognised");
        throw new IOException(((StringBuilder)object).toString());
    }

}

