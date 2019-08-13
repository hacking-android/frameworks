/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.ec;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.x9.ECNamedCurveTable;
import com.android.org.bouncycastle.asn1.x9.X9ECParameters;
import com.android.org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import com.android.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.org.bouncycastle.crypto.KeyGenerationParameters;
import com.android.org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import com.android.org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import com.android.org.bouncycastle.crypto.params.ECDomainParameters;
import com.android.org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import com.android.org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import com.android.org.bouncycastle.crypto.params.ECPublicKeyParameters;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.ec.ECUtils;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import com.android.org.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import com.android.org.bouncycastle.jce.provider.BouncyCastleProvider;
import com.android.org.bouncycastle.jce.spec.ECNamedCurveGenParameterSpec;
import com.android.org.bouncycastle.jce.spec.ECNamedCurveSpec;
import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECPoint;
import com.android.org.bouncycastle.util.Integers;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.EllipticCurve;
import java.util.Hashtable;
import java.util.Map;

public abstract class KeyPairGeneratorSpi
extends KeyPairGenerator {
    public KeyPairGeneratorSpi(String string) {
        super(string);
    }

    public static class EC
    extends KeyPairGeneratorSpi {
        private static Hashtable ecParameters = new Hashtable();
        String algorithm;
        ProviderConfiguration configuration;
        Object ecParams = null;
        ECKeyPairGenerator engine = new ECKeyPairGenerator();
        boolean initialised = false;
        ECKeyGenerationParameters param;
        SecureRandom random = CryptoServicesRegistrar.getSecureRandom();
        int strength = 256;

        static {
            ecParameters.put(Integers.valueOf(192), new ECGenParameterSpec("prime192v1"));
            ecParameters.put(Integers.valueOf(239), new ECGenParameterSpec("prime239v1"));
            ecParameters.put(Integers.valueOf(256), new ECGenParameterSpec("prime256v1"));
            ecParameters.put(Integers.valueOf(224), new ECGenParameterSpec("P-224"));
            ecParameters.put(Integers.valueOf(384), new ECGenParameterSpec("P-384"));
            ecParameters.put(Integers.valueOf(521), new ECGenParameterSpec("P-521"));
        }

        public EC() {
            super("EC");
            this.algorithm = "EC";
            this.configuration = BouncyCastleProvider.CONFIGURATION;
        }

        public EC(String string, ProviderConfiguration providerConfiguration) {
            super(string);
            this.algorithm = string;
            this.configuration = providerConfiguration;
        }

        protected ECKeyGenerationParameters createKeyGenParamsBC(com.android.org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec, SecureRandom secureRandom) {
            return new ECKeyGenerationParameters(new ECDomainParameters(eCParameterSpec.getCurve(), eCParameterSpec.getG(), eCParameterSpec.getN(), eCParameterSpec.getH()), secureRandom);
        }

        protected ECKeyGenerationParameters createKeyGenParamsJCE(ECParameterSpec eCParameterSpec, SecureRandom secureRandom) {
            ECCurve eCCurve = EC5Util.convertCurve(eCParameterSpec.getCurve());
            return new ECKeyGenerationParameters(new ECDomainParameters(eCCurve, EC5Util.convertPoint(eCCurve, eCParameterSpec.getGenerator(), false), eCParameterSpec.getOrder(), BigInteger.valueOf(eCParameterSpec.getCofactor())), secureRandom);
        }

        protected ECNamedCurveSpec createNamedCurveSpec(String string) throws InvalidAlgorithmParameterException {
            Object object;
            block5 : {
                Object object2;
                object = object2 = ECUtils.getDomainParametersFromName(string);
                if (object2 == null) {
                    try {
                        object = new ASN1ObjectIdentifier(string);
                        object = object2 = ECNamedCurveTable.getByOID((ASN1ObjectIdentifier)object);
                        if (object2 != null) break block5;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("unknown curve name: ");
                        stringBuilder.append(string);
                        throw new InvalidAlgorithmParameterException(stringBuilder.toString());
                    }
                    object = this.configuration.getAdditionalECParameters();
                    object2 = new ASN1ObjectIdentifier(string);
                    object = (X9ECParameters)object.get(object2);
                    if (object != null) break block5;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("unknown curve OID: ");
                    ((StringBuilder)object).append(string);
                    object2 = new InvalidAlgorithmParameterException(((StringBuilder)object).toString());
                    throw object2;
                }
            }
            return new ECNamedCurveSpec(string, ((X9ECParameters)object).getCurve(), ((X9ECParameters)object).getG(), ((X9ECParameters)object).getN(), ((X9ECParameters)object).getH(), null);
        }

        @Override
        public KeyPair generateKeyPair() {
            if (!this.initialised) {
                this.initialize(this.strength, new SecureRandom());
            }
            Object object = this.engine.generateKeyPair();
            Object object2 = (ECPublicKeyParameters)((AsymmetricCipherKeyPair)object).getPublic();
            object = (ECPrivateKeyParameters)((AsymmetricCipherKeyPair)object).getPrivate();
            Object object3 = this.ecParams;
            if (object3 instanceof com.android.org.bouncycastle.jce.spec.ECParameterSpec) {
                object3 = (com.android.org.bouncycastle.jce.spec.ECParameterSpec)object3;
                object2 = new BCECPublicKey(this.algorithm, (ECPublicKeyParameters)object2, (com.android.org.bouncycastle.jce.spec.ECParameterSpec)object3, this.configuration);
                return new KeyPair((PublicKey)object2, new BCECPrivateKey(this.algorithm, (ECPrivateKeyParameters)object, (BCECPublicKey)object2, (com.android.org.bouncycastle.jce.spec.ECParameterSpec)object3, this.configuration));
            }
            if (object3 == null) {
                return new KeyPair(new BCECPublicKey(this.algorithm, (ECPublicKeyParameters)object2, this.configuration), new BCECPrivateKey(this.algorithm, (ECPrivateKeyParameters)object, this.configuration));
            }
            object3 = (ECParameterSpec)object3;
            object2 = new BCECPublicKey(this.algorithm, (ECPublicKeyParameters)object2, (ECParameterSpec)object3, this.configuration);
            return new KeyPair((PublicKey)object2, new BCECPrivateKey(this.algorithm, (ECPrivateKeyParameters)object, (BCECPublicKey)object2, (ECParameterSpec)object3, this.configuration));
        }

        @Override
        public void initialize(int n, SecureRandom secureRandom) {
            ECGenParameterSpec eCGenParameterSpec;
            this.strength = n;
            if (secureRandom != null) {
                this.random = secureRandom;
            }
            if ((eCGenParameterSpec = (ECGenParameterSpec)ecParameters.get(Integers.valueOf(n))) != null) {
                try {
                    this.initialize(eCGenParameterSpec, secureRandom);
                    return;
                }
                catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
                    throw new InvalidParameterException("key size not configurable.");
                }
            }
            throw new InvalidParameterException("unknown key size.");
        }

        @Override
        public void initialize(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
            block10 : {
                block6 : {
                    SecureRandom secureRandom2;
                    block9 : {
                        block8 : {
                            block7 : {
                                block4 : {
                                    block5 : {
                                        secureRandom2 = secureRandom;
                                        if (secureRandom == null) {
                                            secureRandom2 = this.random;
                                        }
                                        if (algorithmParameterSpec != null) break block4;
                                        algorithmParameterSpec = this.configuration.getEcImplicitlyCa();
                                        if (algorithmParameterSpec == null) break block5;
                                        this.ecParams = null;
                                        this.param = this.createKeyGenParamsBC((com.android.org.bouncycastle.jce.spec.ECParameterSpec)algorithmParameterSpec, secureRandom2);
                                        break block6;
                                    }
                                    throw new InvalidAlgorithmParameterException("null parameter passed but no implicitCA set");
                                }
                                if (!(algorithmParameterSpec instanceof com.android.org.bouncycastle.jce.spec.ECParameterSpec)) break block7;
                                this.ecParams = algorithmParameterSpec;
                                this.param = this.createKeyGenParamsBC((com.android.org.bouncycastle.jce.spec.ECParameterSpec)algorithmParameterSpec, secureRandom2);
                                break block6;
                            }
                            if (!(algorithmParameterSpec instanceof ECParameterSpec)) break block8;
                            this.ecParams = algorithmParameterSpec;
                            this.param = this.createKeyGenParamsJCE((ECParameterSpec)algorithmParameterSpec, secureRandom2);
                            break block6;
                        }
                        if (!(algorithmParameterSpec instanceof ECGenParameterSpec)) break block9;
                        this.initializeNamedCurve(((ECGenParameterSpec)algorithmParameterSpec).getName(), secureRandom2);
                        break block6;
                    }
                    if (!(algorithmParameterSpec instanceof ECNamedCurveGenParameterSpec)) break block10;
                    this.initializeNamedCurve(((ECNamedCurveGenParameterSpec)algorithmParameterSpec).getName(), secureRandom2);
                }
                this.engine.init(this.param);
                this.initialised = true;
                return;
            }
            throw new InvalidAlgorithmParameterException("parameter object not a ECParameterSpec");
        }

        protected void initializeNamedCurve(String object, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
            this.ecParams = object = this.createNamedCurveSpec((String)object);
            this.param = this.createKeyGenParamsJCE((ECParameterSpec)object, secureRandom);
        }
    }

    public static class ECDH
    extends EC {
        public ECDH() {
            super("ECDH", BouncyCastleProvider.CONFIGURATION);
        }
    }

    public static class ECDHC
    extends EC {
        public ECDHC() {
            super("ECDHC", BouncyCastleProvider.CONFIGURATION);
        }
    }

    public static class ECDSA
    extends EC {
        public ECDSA() {
            super("ECDSA", BouncyCastleProvider.CONFIGURATION);
        }
    }

    public static class ECMQV
    extends EC {
        public ECMQV() {
            super("ECMQV", BouncyCastleProvider.CONFIGURATION);
        }
    }

}

