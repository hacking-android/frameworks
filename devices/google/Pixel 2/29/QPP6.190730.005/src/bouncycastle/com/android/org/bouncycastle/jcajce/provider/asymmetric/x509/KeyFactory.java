/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.x509;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactorySpi;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class KeyFactory
extends KeyFactorySpi {
    @Override
    protected PrivateKey engineGeneratePrivate(KeySpec object) throws InvalidKeySpecException {
        if (object instanceof PKCS8EncodedKeySpec) {
            Serializable serializable;
            block4 : {
                try {
                    object = PrivateKeyInfo.getInstance(((PKCS8EncodedKeySpec)object).getEncoded());
                    serializable = BouncyCastleProvider.getPrivateKey((PrivateKeyInfo)object);
                    if (serializable == null) break block4;
                    return serializable;
                }
                catch (Exception exception) {
                    throw new InvalidKeySpecException(exception.toString());
                }
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("no factory found for OID: ");
            ((StringBuilder)serializable).append(((PrivateKeyInfo)object).getPrivateKeyAlgorithm().getAlgorithm());
            InvalidKeySpecException invalidKeySpecException = new InvalidKeySpecException(((StringBuilder)serializable).toString());
            throw invalidKeySpecException;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown KeySpec type: ");
        stringBuilder.append(object.getClass().getName());
        throw new InvalidKeySpecException(stringBuilder.toString());
    }

    @Override
    protected PublicKey engineGeneratePublic(KeySpec object) throws InvalidKeySpecException {
        if (object instanceof X509EncodedKeySpec) {
            Serializable serializable;
            block4 : {
                try {
                    object = SubjectPublicKeyInfo.getInstance(((X509EncodedKeySpec)object).getEncoded());
                    serializable = BouncyCastleProvider.getPublicKey((SubjectPublicKeyInfo)object);
                    if (serializable == null) break block4;
                    return serializable;
                }
                catch (Exception exception) {
                    throw new InvalidKeySpecException(exception.toString());
                }
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("no factory found for OID: ");
            ((StringBuilder)serializable).append(((SubjectPublicKeyInfo)object).getAlgorithm().getAlgorithm());
            InvalidKeySpecException invalidKeySpecException = new InvalidKeySpecException(((StringBuilder)serializable).toString());
            throw invalidKeySpecException;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown KeySpec type: ");
        stringBuilder.append(object.getClass().getName());
        throw new InvalidKeySpecException(stringBuilder.toString());
    }

    protected KeySpec engineGetKeySpec(Key key, Class class_) throws InvalidKeySpecException {
        if (class_.isAssignableFrom(PKCS8EncodedKeySpec.class) && key.getFormat().equals("PKCS#8")) {
            return new PKCS8EncodedKeySpec(key.getEncoded());
        }
        if (class_.isAssignableFrom(X509EncodedKeySpec.class) && key.getFormat().equals("X.509")) {
            return new X509EncodedKeySpec(key.getEncoded());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("not implemented yet ");
        stringBuilder.append(key);
        stringBuilder.append(" ");
        stringBuilder.append(class_);
        throw new InvalidKeySpecException(stringBuilder.toString());
    }

    @Override
    protected Key engineTranslateKey(Key key) throws InvalidKeyException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("not implemented yet ");
        stringBuilder.append(key);
        throw new InvalidKeyException(stringBuilder.toString());
    }
}

