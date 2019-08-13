/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.dh;

import com.android.org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import com.android.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.org.bouncycastle.crypto.KeyGenerationParameters;
import com.android.org.bouncycastle.crypto.generators.DHBasicKeyPairGenerator;
import com.android.org.bouncycastle.crypto.generators.DHParametersGenerator;
import com.android.org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import com.android.org.bouncycastle.crypto.params.DHKeyGenerationParameters;
import com.android.org.bouncycastle.crypto.params.DHParameters;
import com.android.org.bouncycastle.crypto.params.DHPrivateKeyParameters;
import com.android.org.bouncycastle.crypto.params.DHPublicKeyParameters;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.dh.BCDHPrivateKey;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.dh.BCDHPublicKey;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.PrimeCertaintyCalculator;
import com.android.org.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import com.android.org.bouncycastle.jce.provider.BouncyCastleProvider;
import com.android.org.bouncycastle.util.Integers;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Hashtable;
import javax.crypto.spec.DHParameterSpec;

public class KeyPairGeneratorSpi
extends KeyPairGenerator {
    private static Object lock;
    private static Hashtable params;
    DHBasicKeyPairGenerator engine = new DHBasicKeyPairGenerator();
    boolean initialised = false;
    DHKeyGenerationParameters param;
    SecureRandom random = CryptoServicesRegistrar.getSecureRandom();
    int strength = 2048;

    static {
        params = new Hashtable();
        lock = new Object();
    }

    public KeyPairGeneratorSpi() {
        super("DH");
    }

    private DHKeyGenerationParameters convertParams(SecureRandom secureRandom, DHParameterSpec dHParameterSpec) {
        return new DHKeyGenerationParameters(secureRandom, new DHParameters(dHParameterSpec.getP(), dHParameterSpec.getG(), null, dHParameterSpec.getL()));
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
            object = Integers.valueOf(this.strength);
            if (params.containsKey(object)) {
                this.param = (DHKeyGenerationParameters)params.get(object);
            } else {
                object2 = BouncyCastleProvider.CONFIGURATION.getDHDefaultParameters(this.strength);
                if (object2 != null) {
                    this.param = this.convertParams(this.random, (DHParameterSpec)object2);
                } else {
                    object2 = lock;
                    synchronized (object2) {
                        if (params.containsKey(object)) {
                            this.param = (DHKeyGenerationParameters)params.get(object);
                        } else {
                            DHKeyGenerationParameters dHKeyGenerationParameters;
                            DHParametersGenerator dHParametersGenerator = new DHParametersGenerator();
                            dHParametersGenerator.init(this.strength, PrimeCertaintyCalculator.getDefaultCertainty(this.strength), this.random);
                            this.param = dHKeyGenerationParameters = new DHKeyGenerationParameters(this.random, dHParametersGenerator.generateParameters());
                            params.put(object, this.param);
                        }
                    }
                }
            }
            this.engine.init(this.param);
            this.initialised = true;
        }
        object2 = this.engine.generateKeyPair();
        object = (DHPublicKeyParameters)((AsymmetricCipherKeyPair)object2).getPublic();
        object2 = (DHPrivateKeyParameters)((AsymmetricCipherKeyPair)object2).getPrivate();
        return new KeyPair(new BCDHPublicKey((DHPublicKeyParameters)object), new BCDHPrivateKey((DHPrivateKeyParameters)object2));
    }

    @Override
    public void initialize(int n, SecureRandom secureRandom) {
        this.strength = n;
        this.random = secureRandom;
        this.initialised = false;
    }

    @Override
    public void initialize(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        if (algorithmParameterSpec instanceof DHParameterSpec) {
            algorithmParameterSpec = (DHParameterSpec)algorithmParameterSpec;
            try {
                this.param = this.convertParams(secureRandom, (DHParameterSpec)algorithmParameterSpec);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw new InvalidAlgorithmParameterException(illegalArgumentException.getMessage(), illegalArgumentException);
            }
            this.engine.init(this.param);
            this.initialised = true;
            return;
        }
        throw new InvalidAlgorithmParameterException("parameter object not a DHParameterSpec");
    }
}

