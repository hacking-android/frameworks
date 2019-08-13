/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.ec;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import com.android.org.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import com.android.org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;
import com.android.org.bouncycastle.jce.provider.BouncyCastleProvider;
import com.android.org.bouncycastle.jce.spec.ECPrivateKeySpec;
import com.android.org.bouncycastle.jce.spec.ECPublicKeySpec;
import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECPoint;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class KeyFactorySpi
extends BaseKeyFactorySpi
implements AsymmetricKeyInfoConverter {
    String algorithm;
    ProviderConfiguration configuration;

    KeyFactorySpi(String string, ProviderConfiguration providerConfiguration) {
        this.algorithm = string;
        this.configuration = providerConfiguration;
    }

    @Override
    protected PrivateKey engineGeneratePrivate(KeySpec keySpec) throws InvalidKeySpecException {
        if (keySpec instanceof ECPrivateKeySpec) {
            return new BCECPrivateKey(this.algorithm, (ECPrivateKeySpec)keySpec, this.configuration);
        }
        if (keySpec instanceof java.security.spec.ECPrivateKeySpec) {
            return new BCECPrivateKey(this.algorithm, (java.security.spec.ECPrivateKeySpec)keySpec, this.configuration);
        }
        return super.engineGeneratePrivate(keySpec);
    }

    @Override
    protected PublicKey engineGeneratePublic(KeySpec object) throws InvalidKeySpecException {
        block3 : {
            try {
                if (object instanceof ECPublicKeySpec) {
                    return new BCECPublicKey(this.algorithm, (ECPublicKeySpec)object, this.configuration);
                }
                if (!(object instanceof java.security.spec.ECPublicKeySpec)) break block3;
                object = new BCECPublicKey(this.algorithm, (java.security.spec.ECPublicKeySpec)object, this.configuration);
                return object;
            }
            catch (Exception exception) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("invalid KeySpec: ");
                stringBuilder.append(exception.getMessage());
                throw new InvalidKeySpecException(stringBuilder.toString(), exception);
            }
        }
        return super.engineGeneratePublic((KeySpec)object);
    }

    @Override
    protected KeySpec engineGetKeySpec(Key object, Class object2) throws InvalidKeySpecException {
        if (((Class)object2).isAssignableFrom(java.security.spec.ECPublicKeySpec.class) && object instanceof ECPublicKey) {
            if ((object = (ECPublicKey)object).getParams() != null) {
                return new java.security.spec.ECPublicKeySpec(object.getW(), object.getParams());
            }
            object2 = BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
            return new java.security.spec.ECPublicKeySpec(object.getW(), EC5Util.convertSpec(EC5Util.convertCurve(((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object2).getCurve(), ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object2).getSeed()), (com.android.org.bouncycastle.jce.spec.ECParameterSpec)object2));
        }
        if (((Class)object2).isAssignableFrom(java.security.spec.ECPrivateKeySpec.class) && object instanceof ECPrivateKey) {
            object2 = (ECPrivateKey)object;
            if (object2.getParams() != null) {
                return new java.security.spec.ECPrivateKeySpec(object2.getS(), object2.getParams());
            }
            object = BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
            return new java.security.spec.ECPrivateKeySpec(object2.getS(), EC5Util.convertSpec(EC5Util.convertCurve(((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getCurve(), ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getSeed()), (com.android.org.bouncycastle.jce.spec.ECParameterSpec)object));
        }
        if (((Class)object2).isAssignableFrom(ECPublicKeySpec.class) && object instanceof ECPublicKey) {
            if ((object = (ECPublicKey)object).getParams() != null) {
                return new ECPublicKeySpec(EC5Util.convertPoint(object.getParams(), object.getW(), false), EC5Util.convertSpec(object.getParams(), false));
            }
            object2 = BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
            return new ECPublicKeySpec(EC5Util.convertPoint(object.getParams(), object.getW(), false), (com.android.org.bouncycastle.jce.spec.ECParameterSpec)object2);
        }
        if (((Class)object2).isAssignableFrom(ECPrivateKeySpec.class) && object instanceof ECPrivateKey) {
            if ((object = (ECPrivateKey)object).getParams() != null) {
                return new ECPrivateKeySpec(object.getS(), EC5Util.convertSpec(object.getParams(), false));
            }
            object2 = BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
            return new ECPrivateKeySpec(object.getS(), (com.android.org.bouncycastle.jce.spec.ECParameterSpec)object2);
        }
        return super.engineGetKeySpec((Key)object, (Class)object2);
    }

    @Override
    protected Key engineTranslateKey(Key key) throws InvalidKeyException {
        if (key instanceof ECPublicKey) {
            return new BCECPublicKey((ECPublicKey)key, this.configuration);
        }
        if (key instanceof ECPrivateKey) {
            return new BCECPrivateKey((ECPrivateKey)key, this.configuration);
        }
        throw new InvalidKeyException("key type unknown");
    }

    @Override
    public PrivateKey generatePrivate(PrivateKeyInfo object) throws IOException {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = ((PrivateKeyInfo)object).getPrivateKeyAlgorithm().getAlgorithm();
        if (aSN1ObjectIdentifier.equals(X9ObjectIdentifiers.id_ecPublicKey)) {
            return new BCECPrivateKey(this.algorithm, (PrivateKeyInfo)object, this.configuration);
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
        if (aSN1ObjectIdentifier.equals(X9ObjectIdentifiers.id_ecPublicKey)) {
            return new BCECPublicKey(this.algorithm, (SubjectPublicKeyInfo)object, this.configuration);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("algorithm identifier ");
        ((StringBuilder)object).append(aSN1ObjectIdentifier);
        ((StringBuilder)object).append(" in key not recognised");
        throw new IOException(((StringBuilder)object).toString());
    }

    public static class EC
    extends KeyFactorySpi {
        public EC() {
            super("EC", BouncyCastleProvider.CONFIGURATION);
        }
    }

    public static class ECDH
    extends KeyFactorySpi {
        public ECDH() {
            super("ECDH", BouncyCastleProvider.CONFIGURATION);
        }
    }

    public static class ECDHC
    extends KeyFactorySpi {
        public ECDHC() {
            super("ECDHC", BouncyCastleProvider.CONFIGURATION);
        }
    }

    public static class ECDSA
    extends KeyFactorySpi {
        public ECDSA() {
            super("ECDSA", BouncyCastleProvider.CONFIGURATION);
        }
    }

    public static class ECMQV
    extends KeyFactorySpi {
        public ECMQV() {
            super("ECMQV", BouncyCastleProvider.CONFIGURATION);
        }
    }

}

