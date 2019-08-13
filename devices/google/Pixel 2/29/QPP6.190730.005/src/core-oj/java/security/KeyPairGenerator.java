/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGeneratorSpi;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Iterator;
import sun.security.jca.GetInstance;
import sun.security.jca.JCAUtil;
import sun.security.jca.Providers;

public abstract class KeyPairGenerator
extends KeyPairGeneratorSpi {
    private final String algorithm;
    Provider provider;

    protected KeyPairGenerator(String string) {
        this.algorithm = string;
    }

    public static KeyPairGenerator getInstance(String string) throws NoSuchAlgorithmException {
        Iterator<Provider.Service> iterator = GetInstance.getServices("KeyPairGenerator", string).iterator();
        if (iterator.hasNext()) {
            Object object = null;
            do {
                Object object2 = iterator.next();
                try {
                    object2 = GetInstance.getInstance((Provider.Service)object2, KeyPairGeneratorSpi.class);
                    if (((GetInstance.Instance)object2).impl instanceof KeyPairGenerator) {
                        return KeyPairGenerator.getInstance((GetInstance.Instance)object2, string);
                    }
                    object2 = new Delegate((GetInstance.Instance)object2, iterator, string);
                    return object2;
                }
                catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                    object2 = object;
                    if (object == null) {
                        object2 = noSuchAlgorithmException;
                    }
                    if (iterator.hasNext()) {
                        object = object2;
                        continue;
                    }
                    throw object2;
                }
                break;
            } while (true);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(" KeyPairGenerator not available");
        throw new NoSuchAlgorithmException(stringBuilder.toString());
    }

    public static KeyPairGenerator getInstance(String string, String string2) throws NoSuchAlgorithmException, NoSuchProviderException {
        Providers.checkBouncyCastleDeprecation(string2, "KeyPairGenerator", string);
        return KeyPairGenerator.getInstance(GetInstance.getInstance("KeyPairGenerator", KeyPairGeneratorSpi.class, string, string2), string);
    }

    public static KeyPairGenerator getInstance(String string, Provider provider) throws NoSuchAlgorithmException {
        Providers.checkBouncyCastleDeprecation(provider, "KeyPairGenerator", string);
        return KeyPairGenerator.getInstance(GetInstance.getInstance("KeyPairGenerator", KeyPairGeneratorSpi.class, string, provider), string);
    }

    private static KeyPairGenerator getInstance(GetInstance.Instance instance, String object) {
        object = instance.impl instanceof KeyPairGenerator ? (KeyPairGenerator)instance.impl : new Delegate((KeyPairGeneratorSpi)instance.impl, (String)object);
        ((KeyPairGenerator)object).provider = instance.provider;
        return object;
    }

    void disableFailover() {
    }

    public final KeyPair genKeyPair() {
        return this.generateKeyPair();
    }

    @Override
    public KeyPair generateKeyPair() {
        return null;
    }

    public String getAlgorithm() {
        return this.algorithm;
    }

    public final Provider getProvider() {
        this.disableFailover();
        return this.provider;
    }

    public void initialize(int n) {
        this.initialize(n, JCAUtil.getSecureRandom());
    }

    @Override
    public void initialize(int n, SecureRandom secureRandom) {
    }

    public void initialize(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException {
        this.initialize(algorithmParameterSpec, JCAUtil.getSecureRandom());
    }

    @Override
    public void initialize(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
    }

    private static final class Delegate
    extends KeyPairGenerator {
        private static final int I_NONE = 1;
        private static final int I_PARAMS = 3;
        private static final int I_SIZE = 2;
        private int initKeySize;
        private AlgorithmParameterSpec initParams;
        private SecureRandom initRandom;
        private int initType;
        private final Object lock = new Object();
        private Iterator<Provider.Service> serviceIterator;
        private volatile KeyPairGeneratorSpi spi;

        Delegate(KeyPairGeneratorSpi keyPairGeneratorSpi, String string) {
            super(string);
            this.spi = keyPairGeneratorSpi;
        }

        Delegate(GetInstance.Instance instance, Iterator<Provider.Service> iterator, String string) {
            super(string);
            this.spi = (KeyPairGeneratorSpi)instance.impl;
            this.provider = instance.provider;
            this.serviceIterator = iterator;
            this.initType = 1;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private KeyPairGeneratorSpi nextSpi(KeyPairGeneratorSpi object, boolean bl) {
            Object object2 = this.lock;
            synchronized (object2) {
                if (object != null && object != this.spi) {
                    return this.spi;
                }
                if (this.serviceIterator == null) {
                    return null;
                }
                do {
                    if (!this.serviceIterator.hasNext()) {
                        this.disableFailover();
                        return null;
                    }
                    object = this.serviceIterator.next();
                    try {
                        Object object3 = ((Provider.Service)object).newInstance(null);
                        if (!(object3 instanceof KeyPairGeneratorSpi) || object3 instanceof KeyPairGenerator) continue;
                        object3 = (KeyPairGeneratorSpi)object3;
                        if (bl) {
                            if (this.initType == 2) {
                                ((KeyPairGeneratorSpi)object3).initialize(this.initKeySize, this.initRandom);
                            } else if (this.initType == 3) {
                                ((KeyPairGeneratorSpi)object3).initialize(this.initParams, this.initRandom);
                            } else if (this.initType != 1) {
                                object = new StringBuilder();
                                ((StringBuilder)object).append("KeyPairGenerator initType: ");
                                ((StringBuilder)object).append(this.initType);
                                object3 = new AssertionError((Object)((StringBuilder)object).toString());
                                throw object3;
                            }
                        }
                        this.provider = ((Provider.Service)object).getProvider();
                        this.spi = object3;
                        return object3;
                    }
                    catch (Exception exception) {
                        continue;
                    }
                    break;
                } while (true);
            }
        }

        @Override
        void disableFailover() {
            this.serviceIterator = null;
            this.initType = 0;
            this.initParams = null;
            this.initRandom = null;
        }

        @Override
        public KeyPair generateKeyPair() {
            if (this.serviceIterator == null) {
                return this.spi.generateKeyPair();
            }
            Serializable serializable = null;
            KeyPairGeneratorSpi keyPairGeneratorSpi = this.spi;
            do {
                Serializable serializable2;
                try {
                    serializable2 = keyPairGeneratorSpi.generateKeyPair();
                    return serializable2;
                }
                catch (RuntimeException runtimeException) {
                    serializable2 = serializable;
                    if (serializable == null) {
                        serializable2 = runtimeException;
                    }
                    if ((keyPairGeneratorSpi = this.nextSpi(keyPairGeneratorSpi, true)) != null) {
                        serializable = serializable2;
                        continue;
                    }
                    throw serializable2;
                }
                break;
            } while (true);
        }

        @Override
        public void initialize(int n, SecureRandom secureRandom) {
            if (this.serviceIterator == null) {
                this.spi.initialize(n, secureRandom);
                return;
            }
            RuntimeException runtimeException = null;
            KeyPairGeneratorSpi keyPairGeneratorSpi = this.spi;
            do {
                try {
                    keyPairGeneratorSpi.initialize(n, secureRandom);
                    this.initType = 2;
                    this.initKeySize = n;
                    this.initParams = null;
                    this.initRandom = secureRandom;
                    return;
                }
                catch (RuntimeException runtimeException2) {
                    RuntimeException runtimeException3 = runtimeException;
                    if (runtimeException == null) {
                        runtimeException3 = runtimeException2;
                    }
                    if ((keyPairGeneratorSpi = this.nextSpi(keyPairGeneratorSpi, false)) != null) {
                        runtimeException = runtimeException3;
                        continue;
                    }
                    throw runtimeException3;
                }
                break;
            } while (true);
        }

        @Override
        public void initialize(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
            if (this.serviceIterator == null) {
                this.spi.initialize(algorithmParameterSpec, secureRandom);
                return;
            }
            Exception exception = null;
            KeyPairGeneratorSpi keyPairGeneratorSpi = this.spi;
            do {
                try {
                    keyPairGeneratorSpi.initialize(algorithmParameterSpec, secureRandom);
                    this.initType = 3;
                    this.initKeySize = 0;
                    this.initParams = algorithmParameterSpec;
                    this.initRandom = secureRandom;
                    return;
                }
                catch (Exception exception2) {
                    Exception exception3 = exception;
                    if (exception == null) {
                        exception3 = exception2;
                    }
                    if ((keyPairGeneratorSpi = this.nextSpi(keyPairGeneratorSpi, false)) == null) {
                        if (exception3 instanceof RuntimeException) {
                            throw (RuntimeException)exception3;
                        }
                        throw (InvalidAlgorithmParameterException)exception3;
                    }
                    exception = exception3;
                    continue;
                }
                break;
            } while (true);
        }
    }

}

