/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto;

import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Iterator;
import javax.crypto.JceSecurity;
import javax.crypto.KeyGeneratorSpi;
import javax.crypto.SecretKey;
import sun.security.jca.GetInstance;
import sun.security.jca.Providers;

public class KeyGenerator {
    private static final int I_NONE = 1;
    private static final int I_PARAMS = 3;
    private static final int I_RANDOM = 2;
    private static final int I_SIZE = 4;
    private final String algorithm;
    private int initKeySize;
    private AlgorithmParameterSpec initParams;
    private SecureRandom initRandom;
    private int initType;
    private final Object lock = new Object();
    private Provider provider;
    private Iterator<Provider.Service> serviceIterator;
    private volatile KeyGeneratorSpi spi;

    private KeyGenerator(String string) throws NoSuchAlgorithmException {
        this.algorithm = string;
        this.serviceIterator = GetInstance.getServices("KeyGenerator", string).iterator();
        this.initType = 1;
        if (this.nextSpi(null, false) != null) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(" KeyGenerator not available");
        throw new NoSuchAlgorithmException(stringBuilder.toString());
    }

    protected KeyGenerator(KeyGeneratorSpi keyGeneratorSpi, Provider provider, String string) {
        this.spi = keyGeneratorSpi;
        this.provider = provider;
        this.algorithm = string;
    }

    public static final KeyGenerator getInstance(String string) throws NoSuchAlgorithmException {
        return new KeyGenerator(string);
    }

    public static final KeyGenerator getInstance(String string, String object) throws NoSuchAlgorithmException, NoSuchProviderException {
        Providers.checkBouncyCastleDeprecation((String)object, "KeyGenerator", string);
        object = JceSecurity.getInstance("KeyGenerator", KeyGeneratorSpi.class, string, (String)object);
        return new KeyGenerator((KeyGeneratorSpi)((GetInstance.Instance)object).impl, ((GetInstance.Instance)object).provider, string);
    }

    public static final KeyGenerator getInstance(String string, Provider object) throws NoSuchAlgorithmException {
        Providers.checkBouncyCastleDeprecation((Provider)object, "KeyGenerator", string);
        object = JceSecurity.getInstance("KeyGenerator", KeyGeneratorSpi.class, string, (Provider)object);
        return new KeyGenerator((KeyGeneratorSpi)((GetInstance.Instance)object).impl, ((GetInstance.Instance)object).provider, string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private KeyGeneratorSpi nextSpi(KeyGeneratorSpi object, boolean bl) {
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
                boolean bl2 = JceSecurity.canUseProvider(((Provider.Service)object).getProvider());
                if (!bl2) continue;
                try {
                    Object object3 = ((Provider.Service)object).newInstance(null);
                    if (!(object3 instanceof KeyGeneratorSpi)) continue;
                    object3 = (KeyGeneratorSpi)object3;
                    if (bl) {
                        if (this.initType == 4) {
                            ((KeyGeneratorSpi)object3).engineInit(this.initKeySize, this.initRandom);
                        } else if (this.initType == 3) {
                            ((KeyGeneratorSpi)object3).engineInit(this.initParams, this.initRandom);
                        } else if (this.initType == 2) {
                            ((KeyGeneratorSpi)object3).engineInit(this.initRandom);
                        } else if (this.initType != 1) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("KeyGenerator initType: ");
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

    void disableFailover() {
        this.serviceIterator = null;
        this.initType = 0;
        this.initParams = null;
        this.initRandom = null;
    }

    public final SecretKey generateKey() {
        if (this.serviceIterator == null) {
            return this.spi.engineGenerateKey();
        }
        Serializable serializable = null;
        KeyGeneratorSpi keyGeneratorSpi = this.spi;
        do {
            Serializable serializable2;
            try {
                serializable2 = keyGeneratorSpi.engineGenerateKey();
                return serializable2;
            }
            catch (RuntimeException runtimeException) {
                serializable2 = serializable;
                if (serializable == null) {
                    serializable2 = runtimeException;
                }
                if ((keyGeneratorSpi = this.nextSpi(keyGeneratorSpi, true)) != null) {
                    serializable = serializable2;
                    continue;
                }
                throw serializable2;
            }
            break;
        } while (true);
    }

    public final String getAlgorithm() {
        return this.algorithm;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final Provider getProvider() {
        Object object = this.lock;
        synchronized (object) {
            this.disableFailover();
            return this.provider;
        }
    }

    public final void init(int n) {
        this.init(n, JceSecurity.RANDOM);
    }

    public final void init(int n, SecureRandom secureRandom) {
        if (this.serviceIterator == null) {
            this.spi.engineInit(n, secureRandom);
            return;
        }
        RuntimeException runtimeException = null;
        KeyGeneratorSpi keyGeneratorSpi = this.spi;
        do {
            try {
                keyGeneratorSpi.engineInit(n, secureRandom);
                this.initType = 4;
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
                if ((keyGeneratorSpi = this.nextSpi(keyGeneratorSpi, false)) != null) {
                    runtimeException = runtimeException3;
                    continue;
                }
                throw runtimeException3;
            }
            break;
        } while (true);
    }

    public final void init(SecureRandom secureRandom) {
        if (this.serviceIterator == null) {
            this.spi.engineInit(secureRandom);
            return;
        }
        RuntimeException runtimeException = null;
        KeyGeneratorSpi keyGeneratorSpi = this.spi;
        do {
            try {
                keyGeneratorSpi.engineInit(secureRandom);
                this.initType = 2;
                this.initKeySize = 0;
                this.initParams = null;
                this.initRandom = secureRandom;
                return;
            }
            catch (RuntimeException runtimeException2) {
                RuntimeException runtimeException3 = runtimeException;
                if (runtimeException == null) {
                    runtimeException3 = runtimeException2;
                }
                if ((keyGeneratorSpi = this.nextSpi(keyGeneratorSpi, false)) != null) {
                    runtimeException = runtimeException3;
                    continue;
                }
                throw runtimeException3;
            }
            break;
        } while (true);
    }

    public final void init(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException {
        this.init(algorithmParameterSpec, JceSecurity.RANDOM);
    }

    public final void init(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        if (this.serviceIterator == null) {
            this.spi.engineInit(algorithmParameterSpec, secureRandom);
            return;
        }
        Exception exception = null;
        KeyGeneratorSpi keyGeneratorSpi = this.spi;
        do {
            try {
                keyGeneratorSpi.engineInit(algorithmParameterSpec, secureRandom);
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
                if ((keyGeneratorSpi = this.nextSpi(keyGeneratorSpi, false)) == null) {
                    if (!(exception3 instanceof InvalidAlgorithmParameterException)) {
                        if (exception3 instanceof RuntimeException) {
                            throw (RuntimeException)exception3;
                        }
                        throw new InvalidAlgorithmParameterException("init() failed", exception3);
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

