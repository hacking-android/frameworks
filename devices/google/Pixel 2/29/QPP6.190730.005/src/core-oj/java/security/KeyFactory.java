/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactorySpi;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Iterator;
import sun.security.jca.GetInstance;
import sun.security.jca.Providers;
import sun.security.util.Debug;

public class KeyFactory {
    private static final Debug debug = Debug.getInstance("jca", "KeyFactory");
    private final String algorithm;
    private final Object lock = new Object();
    private Provider provider;
    private Iterator<Provider.Service> serviceIterator;
    private volatile KeyFactorySpi spi;

    private KeyFactory(String string) throws NoSuchAlgorithmException {
        this.algorithm = string;
        this.serviceIterator = GetInstance.getServices("KeyFactory", string).iterator();
        if (this.nextSpi(null) != null) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(" KeyFactory not available");
        throw new NoSuchAlgorithmException(stringBuilder.toString());
    }

    protected KeyFactory(KeyFactorySpi keyFactorySpi, Provider provider, String string) {
        this.spi = keyFactorySpi;
        this.provider = provider;
        this.algorithm = string;
    }

    public static KeyFactory getInstance(String string) throws NoSuchAlgorithmException {
        return new KeyFactory(string);
    }

    public static KeyFactory getInstance(String string, String object) throws NoSuchAlgorithmException, NoSuchProviderException {
        Providers.checkBouncyCastleDeprecation((String)object, "KeyFactory", string);
        object = GetInstance.getInstance("KeyFactory", KeyFactorySpi.class, string, (String)object);
        return new KeyFactory((KeyFactorySpi)((GetInstance.Instance)object).impl, ((GetInstance.Instance)object).provider, string);
    }

    public static KeyFactory getInstance(String string, Provider object) throws NoSuchAlgorithmException {
        Providers.checkBouncyCastleDeprecation((Provider)object, "KeyFactory", string);
        object = GetInstance.getInstance("KeyFactory", KeyFactorySpi.class, string, (Provider)object);
        return new KeyFactory((KeyFactorySpi)((GetInstance.Instance)object).impl, ((GetInstance.Instance)object).provider, string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private KeyFactorySpi nextSpi(KeyFactorySpi object) {
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
                    this.serviceIterator = null;
                    return null;
                }
                object = this.serviceIterator.next();
                try {
                    Object object3 = ((Provider.Service)object).newInstance(null);
                    if (!(object3 instanceof KeyFactorySpi)) continue;
                    object3 = (KeyFactorySpi)object3;
                    this.provider = ((Provider.Service)object).getProvider();
                    this.spi = object3;
                    return object3;
                }
                catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                    continue;
                }
                break;
            } while (true);
        }
    }

    public final PrivateKey generatePrivate(KeySpec keySpec) throws InvalidKeySpecException {
        if (this.serviceIterator == null) {
            return this.spi.engineGeneratePrivate(keySpec);
        }
        Serializable serializable = null;
        KeyFactorySpi keyFactorySpi = this.spi;
        do {
            Serializable serializable2;
            try {
                serializable2 = keyFactorySpi.engineGeneratePrivate(keySpec);
                return serializable2;
            }
            catch (Exception exception) {
                serializable2 = serializable;
                if (serializable == null) {
                    serializable2 = exception;
                }
                if ((keyFactorySpi = this.nextSpi(keyFactorySpi)) == null) {
                    if (!(serializable2 instanceof RuntimeException)) {
                        if (serializable2 instanceof InvalidKeySpecException) {
                            throw (InvalidKeySpecException)serializable2;
                        }
                        throw new InvalidKeySpecException("Could not generate private key", (Throwable)serializable2);
                    }
                    throw (RuntimeException)serializable2;
                }
                serializable = serializable2;
                continue;
            }
            break;
        } while (true);
    }

    public final PublicKey generatePublic(KeySpec keySpec) throws InvalidKeySpecException {
        if (this.serviceIterator == null) {
            return this.spi.engineGeneratePublic(keySpec);
        }
        Serializable serializable = null;
        KeyFactorySpi keyFactorySpi = this.spi;
        do {
            Serializable serializable2;
            try {
                serializable2 = keyFactorySpi.engineGeneratePublic(keySpec);
                return serializable2;
            }
            catch (Exception exception) {
                serializable2 = serializable;
                if (serializable == null) {
                    serializable2 = exception;
                }
                if ((keyFactorySpi = this.nextSpi(keyFactorySpi)) == null) {
                    if (!(serializable2 instanceof RuntimeException)) {
                        if (serializable2 instanceof InvalidKeySpecException) {
                            throw (InvalidKeySpecException)serializable2;
                        }
                        throw new InvalidKeySpecException("Could not generate public key", (Throwable)serializable2);
                    }
                    throw (RuntimeException)serializable2;
                }
                serializable = serializable2;
                continue;
            }
            break;
        } while (true);
    }

    public final String getAlgorithm() {
        return this.algorithm;
    }

    public final <T extends KeySpec> T getKeySpec(Key key, Class<T> class_) throws InvalidKeySpecException {
        if (this.serviceIterator == null) {
            return this.spi.engineGetKeySpec(key, class_);
        }
        Exception exception = null;
        KeyFactorySpi keyFactorySpi = this.spi;
        do {
            Exception exception2;
            try {
                exception2 = (Exception)keyFactorySpi.engineGetKeySpec(key, class_);
            }
            catch (Exception exception3) {
                exception2 = exception;
                if (exception == null) {
                    exception2 = exception3;
                }
                if ((keyFactorySpi = this.nextSpi(keyFactorySpi)) == null) {
                    if (!(exception2 instanceof RuntimeException)) {
                        if (exception2 instanceof InvalidKeySpecException) {
                            throw (InvalidKeySpecException)exception2;
                        }
                        throw new InvalidKeySpecException("Could not get key spec", exception2);
                    }
                    throw (RuntimeException)exception2;
                }
                exception = exception2;
                continue;
            }
            return (T)exception2;
            break;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final Provider getProvider() {
        Object object = this.lock;
        synchronized (object) {
            this.serviceIterator = null;
            return this.provider;
        }
    }

    public final Key translateKey(Key key) throws InvalidKeyException {
        if (this.serviceIterator == null) {
            return this.spi.engineTranslateKey(key);
        }
        Serializable serializable = null;
        KeyFactorySpi keyFactorySpi = this.spi;
        do {
            Serializable serializable2;
            try {
                serializable2 = keyFactorySpi.engineTranslateKey(key);
                return serializable2;
            }
            catch (Exception exception) {
                serializable2 = serializable;
                if (serializable == null) {
                    serializable2 = exception;
                }
                if ((keyFactorySpi = this.nextSpi(keyFactorySpi)) == null) {
                    if (!(serializable2 instanceof RuntimeException)) {
                        if (serializable2 instanceof InvalidKeyException) {
                            throw (InvalidKeyException)serializable2;
                        }
                        throw new InvalidKeyException("Could not translate key", (Throwable)serializable2);
                    }
                    throw (RuntimeException)serializable2;
                }
                serializable = serializable2;
                continue;
            }
            break;
        } while (true);
    }
}

