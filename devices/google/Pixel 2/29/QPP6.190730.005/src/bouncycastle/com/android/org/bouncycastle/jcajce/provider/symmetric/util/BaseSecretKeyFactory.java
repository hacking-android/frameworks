/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.symmetric.util;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.PBE;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactorySpi;
import javax.crypto.spec.SecretKeySpec;

public class BaseSecretKeyFactory
extends SecretKeyFactorySpi
implements PBE {
    protected String algName;
    protected ASN1ObjectIdentifier algOid;

    protected BaseSecretKeyFactory(String string, ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.algName = string;
        this.algOid = aSN1ObjectIdentifier;
    }

    @Override
    protected SecretKey engineGenerateSecret(KeySpec keySpec) throws InvalidKeySpecException {
        if (keySpec instanceof SecretKeySpec) {
            return new SecretKeySpec(((SecretKeySpec)keySpec).getEncoded(), this.algName);
        }
        throw new InvalidKeySpecException("Invalid KeySpec");
    }

    protected KeySpec engineGetKeySpec(SecretKey object, Class class_) throws InvalidKeySpecException {
        if (class_ != null) {
            if (object != null) {
                if (SecretKeySpec.class.isAssignableFrom(class_)) {
                    return new SecretKeySpec(object.getEncoded(), this.algName);
                }
                try {
                    object = (KeySpec)class_.getConstructor(byte[].class).newInstance(new Object[]{object.getEncoded()});
                    return object;
                }
                catch (Exception exception) {
                    throw new InvalidKeySpecException(exception.toString());
                }
            }
            throw new InvalidKeySpecException("key parameter is null");
        }
        throw new InvalidKeySpecException("keySpec parameter is null");
    }

    @Override
    protected SecretKey engineTranslateKey(SecretKey serializable) throws InvalidKeyException {
        if (serializable != null) {
            if (serializable.getAlgorithm().equalsIgnoreCase(this.algName)) {
                return new SecretKeySpec(serializable.getEncoded(), this.algName);
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Key not of type ");
            ((StringBuilder)serializable).append(this.algName);
            ((StringBuilder)serializable).append(".");
            throw new InvalidKeyException(((StringBuilder)serializable).toString());
        }
        throw new InvalidKeyException("key parameter is null");
    }
}

