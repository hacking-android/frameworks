/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.util;

import com.android.org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;
import java.security.Key;
import java.security.KeyFactorySpi;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public abstract class BaseKeyFactorySpi
extends KeyFactorySpi
implements AsymmetricKeyInfoConverter {
    @Override
    protected PrivateKey engineGeneratePrivate(KeySpec object) throws InvalidKeySpecException {
        if (object instanceof PKCS8EncodedKeySpec) {
            try {
                object = this.generatePrivate(PrivateKeyInfo.getInstance(((PKCS8EncodedKeySpec)object).getEncoded()));
                return object;
            }
            catch (Exception exception) {
                object = new StringBuilder();
                ((StringBuilder)object).append("encoded key spec not recognized: ");
                ((StringBuilder)object).append(exception.getMessage());
                throw new InvalidKeySpecException(((StringBuilder)object).toString());
            }
        }
        throw new InvalidKeySpecException("key spec not recognized");
    }

    @Override
    protected PublicKey engineGeneratePublic(KeySpec object) throws InvalidKeySpecException {
        if (object instanceof X509EncodedKeySpec) {
            try {
                object = this.generatePublic(SubjectPublicKeyInfo.getInstance(((X509EncodedKeySpec)object).getEncoded()));
                return object;
            }
            catch (Exception exception) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("encoded key spec not recognized: ");
                stringBuilder.append(exception.getMessage());
                throw new InvalidKeySpecException(stringBuilder.toString());
            }
        }
        throw new InvalidKeySpecException("key spec not recognized");
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
}

