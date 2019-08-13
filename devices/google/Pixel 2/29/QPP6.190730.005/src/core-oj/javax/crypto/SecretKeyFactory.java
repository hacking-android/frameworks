/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto;

import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Iterator;
import javax.crypto.JceSecurity;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactorySpi;
import sun.security.jca.GetInstance;
import sun.security.jca.Providers;

public class SecretKeyFactory {
    private final String algorithm;
    private final Object lock = new Object();
    private Provider provider;
    private Iterator<Provider.Service> serviceIterator;
    private volatile SecretKeyFactorySpi spi;

    private SecretKeyFactory(String string) throws NoSuchAlgorithmException {
        this.algorithm = string;
        this.serviceIterator = GetInstance.getServices("SecretKeyFactory", string).iterator();
        if (this.nextSpi(null) != null) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(" SecretKeyFactory not available");
        throw new NoSuchAlgorithmException(stringBuilder.toString());
    }

    protected SecretKeyFactory(SecretKeyFactorySpi secretKeyFactorySpi, Provider provider, String string) {
        this.spi = secretKeyFactorySpi;
        this.provider = provider;
        this.algorithm = string;
    }

    public static final SecretKeyFactory getInstance(String string) throws NoSuchAlgorithmException {
        return new SecretKeyFactory(string);
    }

    public static final SecretKeyFactory getInstance(String string, String object) throws NoSuchAlgorithmException, NoSuchProviderException {
        Providers.checkBouncyCastleDeprecation((String)object, "SecretKeyFactory", string);
        object = JceSecurity.getInstance("SecretKeyFactory", SecretKeyFactorySpi.class, string, (String)object);
        return new SecretKeyFactory((SecretKeyFactorySpi)((GetInstance.Instance)object).impl, ((GetInstance.Instance)object).provider, string);
    }

    public static final SecretKeyFactory getInstance(String string, Provider object) throws NoSuchAlgorithmException {
        Providers.checkBouncyCastleDeprecation((Provider)object, "SecretKeyFactory", string);
        object = JceSecurity.getInstance("SecretKeyFactory", SecretKeyFactorySpi.class, string, (Provider)object);
        return new SecretKeyFactory((SecretKeyFactorySpi)((GetInstance.Instance)object).impl, ((GetInstance.Instance)object).provider, string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private SecretKeyFactorySpi nextSpi(SecretKeyFactorySpi object) {
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
                boolean bl = JceSecurity.canUseProvider(((Provider.Service)object).getProvider());
                if (!bl) continue;
                try {
                    Object object3 = ((Provider.Service)object).newInstance(null);
                    if (!(object3 instanceof SecretKeyFactorySpi)) continue;
                    object3 = (SecretKeyFactorySpi)object3;
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

    public final SecretKey generateSecret(KeySpec keySpec) throws InvalidKeySpecException {
        if (this.serviceIterator == null) {
            return this.spi.engineGenerateSecret(keySpec);
        }
        Serializable serializable = null;
        SecretKeyFactorySpi secretKeyFactorySpi = this.spi;
        do {
            Serializable serializable2;
            try {
                serializable2 = secretKeyFactorySpi.engineGenerateSecret(keySpec);
                return serializable2;
            }
            catch (Exception exception) {
                serializable2 = serializable;
                if (serializable == null) {
                    serializable2 = exception;
                }
                if ((secretKeyFactorySpi = this.nextSpi(secretKeyFactorySpi)) == null) {
                    if (serializable2 instanceof InvalidKeySpecException) {
                        throw (InvalidKeySpecException)serializable2;
                    }
                    throw new InvalidKeySpecException("Could not generate secret key", (Throwable)serializable2);
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

    public final KeySpec getKeySpec(SecretKey secretKey, Class<?> class_) throws InvalidKeySpecException {
        if (this.serviceIterator == null) {
            return this.spi.engineGetKeySpec(secretKey, class_);
        }
        Object object = null;
        SecretKeyFactorySpi secretKeyFactorySpi = this.spi;
        do {
            Object object2;
            try {
                object2 = secretKeyFactorySpi.engineGetKeySpec(secretKey, class_);
                return object2;
            }
            catch (Exception exception) {
                object2 = object;
                if (object == null) {
                    object2 = exception;
                }
                if ((secretKeyFactorySpi = this.nextSpi(secretKeyFactorySpi)) == null) {
                    if (object2 instanceof InvalidKeySpecException) {
                        throw (InvalidKeySpecException)object2;
                    }
                    throw new InvalidKeySpecException("Could not get key spec", (Throwable)object2);
                }
                object = object2;
                continue;
            }
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

    public final SecretKey translateKey(SecretKey secretKey) throws InvalidKeyException {
        if (this.serviceIterator == null) {
            return this.spi.engineTranslateKey(secretKey);
        }
        Serializable serializable = null;
        SecretKeyFactorySpi secretKeyFactorySpi = this.spi;
        do {
            Serializable serializable2;
            try {
                serializable2 = secretKeyFactorySpi.engineTranslateKey(secretKey);
                return serializable2;
            }
            catch (Exception exception) {
                serializable2 = serializable;
                if (serializable == null) {
                    serializable2 = exception;
                }
                if ((secretKeyFactorySpi = this.nextSpi(secretKeyFactorySpi)) == null) {
                    if (serializable2 instanceof InvalidKeyException) {
                        throw (InvalidKeyException)serializable2;
                    }
                    throw new InvalidKeyException("Could not translate key", (Throwable)serializable2);
                }
                serializable = serializable2;
                continue;
            }
            break;
        } while (true);
    }
}

