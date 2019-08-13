/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactorySpi;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class DESEDESecretKeyFactory
extends SecretKeyFactorySpi {
    @Override
    protected SecretKey engineGenerateSecret(KeySpec object) throws InvalidKeySpecException {
        if (object != null) {
            if (object instanceof SecretKeySpec) {
                block6 : {
                    object = (SecretKeySpec)object;
                    try {
                        if (!DESedeKeySpec.isParityAdjusted(((SecretKeySpec)object).getEncoded(), 0)) break block6;
                        return object;
                    }
                    catch (InvalidKeyException invalidKeyException) {
                        throw new InvalidKeySpecException(invalidKeyException);
                    }
                }
                object = new InvalidKeySpecException("SecretKeySpec is not a parity-adjusted DESEDE key");
                throw object;
            }
            if (object instanceof DESedeKeySpec) {
                return new SecretKeySpec(((DESedeKeySpec)object).getKey(), "DESEDE");
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unsupported KeySpec class: ");
            stringBuilder.append(object.getClass().getName());
            throw new InvalidKeySpecException(stringBuilder.toString());
        }
        throw new InvalidKeySpecException("Null KeySpec");
    }

    protected KeySpec engineGetKeySpec(SecretKey object, Class class_) throws InvalidKeySpecException {
        if (object != null) {
            if (class_ == SecretKeySpec.class) {
                block9 : {
                    try {
                        boolean bl = DESedeKeySpec.isParityAdjusted(object.getEncoded(), 0);
                        if (!bl) break block9;
                    }
                    catch (InvalidKeyException invalidKeyException) {
                        throw new InvalidKeySpecException(invalidKeyException);
                    }
                    if (object instanceof SecretKeySpec) {
                        return (KeySpec)object;
                    }
                    return new SecretKeySpec(object.getEncoded(), "DESEDE");
                }
                object = new InvalidKeySpecException("SecretKey is not a parity-adjusted DESEDE key");
                throw object;
            }
            if (class_ == DESedeKeySpec.class) {
                try {
                    object = new DESedeKeySpec(object.getEncoded());
                    return object;
                }
                catch (InvalidKeyException invalidKeyException) {
                    throw new InvalidKeySpecException(invalidKeyException);
                }
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unsupported KeySpec class: ");
            ((StringBuilder)object).append(class_);
            throw new InvalidKeySpecException(((StringBuilder)object).toString());
        }
        throw new InvalidKeySpecException("Null SecretKey");
    }

    @Override
    protected SecretKey engineTranslateKey(SecretKey secretKey) throws InvalidKeyException {
        if (secretKey != null) {
            return new SecretKeySpec(secretKey.getEncoded(), secretKey.getAlgorithm());
        }
        throw new InvalidKeyException("Null SecretKey");
    }
}

