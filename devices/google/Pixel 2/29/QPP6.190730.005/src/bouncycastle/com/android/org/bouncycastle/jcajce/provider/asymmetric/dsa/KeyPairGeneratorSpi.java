/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.dsa;

import com.android.org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import com.android.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.org.bouncycastle.crypto.Digest;
import com.android.org.bouncycastle.crypto.KeyGenerationParameters;
import com.android.org.bouncycastle.crypto.digests.SHA256Digest;
import com.android.org.bouncycastle.crypto.generators.DSAKeyPairGenerator;
import com.android.org.bouncycastle.crypto.generators.DSAParametersGenerator;
import com.android.org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import com.android.org.bouncycastle.crypto.params.DSAKeyGenerationParameters;
import com.android.org.bouncycastle.crypto.params.DSAParameterGenerationParameters;
import com.android.org.bouncycastle.crypto.params.DSAParameters;
import com.android.org.bouncycastle.crypto.params.DSAPrivateKeyParameters;
import com.android.org.bouncycastle.crypto.params.DSAPublicKeyParameters;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.dsa.BCDSAPrivateKey;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.dsa.BCDSAPublicKey;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.PrimeCertaintyCalculator;
import com.android.org.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import com.android.org.bouncycastle.jce.provider.BouncyCastleProvider;
import com.android.org.bouncycastle.util.Integers;
import com.android.org.bouncycastle.util.Properties;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.DSAParameterSpec;
import java.util.Hashtable;

public class KeyPairGeneratorSpi
extends KeyPairGenerator {
    private static Object lock;
    private static Hashtable params;
    DSAKeyPairGenerator engine = new DSAKeyPairGenerator();
    boolean initialised = false;
    DSAKeyGenerationParameters param;
    SecureRandom random = CryptoServicesRegistrar.getSecureRandom();
    int strength = 1024;

    static {
        params = new Hashtable();
        lock = new Object();
    }

    public KeyPairGeneratorSpi() {
        super("DSA");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public KeyPair generateKeyPair() {
        Object object;
        Object object2;
        if (!this.initialised) {
            Integer n = Integers.valueOf(this.strength);
            if (params.containsKey(n)) {
                this.param = (DSAKeyGenerationParameters)params.get(n);
            } else {
                object2 = lock;
                synchronized (object2) {
                    if (params.containsKey(n)) {
                        this.param = (DSAKeyGenerationParameters)params.get(n);
                    } else {
                        Object object3;
                        int n2 = PrimeCertaintyCalculator.getDefaultCertainty(this.strength);
                        if (this.strength == 1024) {
                            object = new DSAParametersGenerator();
                            if (Properties.isOverrideSet("com.android.org.bouncycastle.dsa.FIPS186-2for1024bits")) {
                                ((DSAParametersGenerator)object).init(this.strength, n2, this.random);
                            } else {
                                object3 = new DSAParameterGenerationParameters(1024, 160, n2, this.random);
                                ((DSAParametersGenerator)object).init((DSAParameterGenerationParameters)object3);
                            }
                        } else if (this.strength > 1024) {
                            object3 = new DSAParameterGenerationParameters(this.strength, 256, n2, this.random);
                            SHA256Digest sHA256Digest = new SHA256Digest();
                            object = new DSAParametersGenerator(sHA256Digest);
                            ((DSAParametersGenerator)object).init((DSAParameterGenerationParameters)object3);
                        } else {
                            object = new DSAParametersGenerator();
                            ((DSAParametersGenerator)object).init(this.strength, n2, this.random);
                        }
                        this.param = object3 = new DSAKeyGenerationParameters(this.random, ((DSAParametersGenerator)object).generateParameters());
                        params.put(n, this.param);
                    }
                }
            }
            this.engine.init(this.param);
            this.initialised = true;
        }
        object2 = this.engine.generateKeyPair();
        object = (DSAPublicKeyParameters)((AsymmetricCipherKeyPair)object2).getPublic();
        object2 = (DSAPrivateKeyParameters)((AsymmetricCipherKeyPair)object2).getPrivate();
        return new KeyPair(new BCDSAPublicKey((DSAPublicKeyParameters)object), new BCDSAPrivateKey((DSAPrivateKeyParameters)object2));
    }

    @Override
    public void initialize(int n, SecureRandom object) {
        if (!(n < 512 || n > 4096 || n < 1024 && n % 64 != 0 || n >= 1024 && n % 1024 != 0)) {
            SecureRandom secureRandom = object;
            if (object == null) {
                secureRandom = new SecureRandom();
            }
            if ((object = BouncyCastleProvider.CONFIGURATION.getDSADefaultParameters(n)) != null) {
                this.param = new DSAKeyGenerationParameters(secureRandom, new DSAParameters(((DSAParameterSpec)object).getP(), ((DSAParameterSpec)object).getQ(), ((DSAParameterSpec)object).getG()));
                this.engine.init(this.param);
                this.initialised = true;
            } else {
                this.strength = n;
                this.random = secureRandom;
                this.initialised = false;
            }
            return;
        }
        throw new InvalidParameterException("strength must be from 512 - 4096 and a multiple of 1024 above 1024");
    }

    @Override
    public void initialize(AlgorithmParameterSpec object, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        if (object instanceof DSAParameterSpec) {
            DSAParameterSpec dSAParameterSpec = (DSAParameterSpec)object;
            object = secureRandom;
            if (secureRandom == null) {
                object = new SecureRandom();
            }
            this.param = new DSAKeyGenerationParameters((SecureRandom)object, new DSAParameters(dSAParameterSpec.getP(), dSAParameterSpec.getQ(), dSAParameterSpec.getG()));
            this.engine.init(this.param);
            this.initialised = true;
            return;
        }
        throw new InvalidAlgorithmParameterException("parameter object not a DSAParameterSpec");
    }
}

