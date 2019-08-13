/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto;

import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.ProviderException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Iterator;
import javax.crypto.JceSecurity;
import javax.crypto.KeyAgreementSpi;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import sun.security.jca.GetInstance;
import sun.security.jca.Providers;

public class KeyAgreement {
    private static final int I_NO_PARAMS = 1;
    private static final int I_PARAMS = 2;
    private static int warnCount = 10;
    private final String algorithm;
    private final Object lock;
    private Provider provider;
    private KeyAgreementSpi spi;

    private KeyAgreement(String string) {
        this.algorithm = string;
        this.lock = new Object();
    }

    protected KeyAgreement(KeyAgreementSpi keyAgreementSpi, Provider provider, String string) {
        this.spi = keyAgreementSpi;
        this.provider = provider;
        this.algorithm = string;
        this.lock = null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void chooseProvider(int n, Key object, AlgorithmParameterSpec object2, SecureRandom serializable) throws InvalidKeyException, InvalidAlgorithmParameterException {
        Object object3 = this.lock;
        synchronized (object3) {
            if (this.spi != null && object == null) {
                this.implInit(this.spi, n, (Key)object, (AlgorithmParameterSpec)object2, (SecureRandom)serializable);
                return;
            }
            Provider.Service service = null;
            for (Object object4 : GetInstance.getServices("KeyAgreement", this.algorithm)) {
                boolean bl;
                if (!((Provider.Service)object4).supportsParameter(object) || !(bl = JceSecurity.canUseProvider(((Provider.Service)object4).getProvider()))) continue;
                try {
                    KeyAgreementSpi keyAgreementSpi = (KeyAgreementSpi)((Provider.Service)object4).newInstance(null);
                    this.implInit(keyAgreementSpi, n, (Key)object, (AlgorithmParameterSpec)object2, (SecureRandom)serializable);
                    this.provider = ((Provider.Service)object4).getProvider();
                    this.spi = keyAgreementSpi;
                    return;
                }
                catch (Exception exception) {
                    object4 = service;
                    if (service == null) {
                        object4 = exception;
                    }
                    service = object4;
                }
            }
            if (service instanceof InvalidKeyException) {
                throw (InvalidKeyException)((Object)service);
            }
            if (service instanceof InvalidAlgorithmParameterException) {
                throw (InvalidAlgorithmParameterException)((Object)service);
            }
            if (service instanceof RuntimeException) {
                throw (RuntimeException)((Object)service);
            }
            object = object != null ? object.getClass().getName() : "(null)";
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("No installed provider supports this key: ");
            ((StringBuilder)object2).append((String)object);
            serializable = new InvalidKeyException(((StringBuilder)object2).toString(), (Throwable)((Object)service));
            throw serializable;
        }
    }

    public static final KeyAgreement getInstance(String string) throws NoSuchAlgorithmException {
        Object object = GetInstance.getServices("KeyAgreement", string).iterator();
        while (object.hasNext()) {
            if (!JceSecurity.canUseProvider(object.next().getProvider())) continue;
            return new KeyAgreement(string);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Algorithm ");
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append(" not available");
        throw new NoSuchAlgorithmException(((StringBuilder)object).toString());
    }

    public static final KeyAgreement getInstance(String string, String object) throws NoSuchAlgorithmException, NoSuchProviderException {
        Providers.checkBouncyCastleDeprecation((String)object, "KeyAgreement", string);
        object = JceSecurity.getInstance("KeyAgreement", KeyAgreementSpi.class, string, (String)object);
        return new KeyAgreement((KeyAgreementSpi)((GetInstance.Instance)object).impl, ((GetInstance.Instance)object).provider, string);
    }

    public static final KeyAgreement getInstance(String string, Provider object) throws NoSuchAlgorithmException {
        Providers.checkBouncyCastleDeprecation((Provider)object, "KeyAgreement", string);
        object = JceSecurity.getInstance("KeyAgreement", KeyAgreementSpi.class, string, (Provider)object);
        return new KeyAgreement((KeyAgreementSpi)((GetInstance.Instance)object).impl, ((GetInstance.Instance)object).provider, string);
    }

    private void implInit(KeyAgreementSpi keyAgreementSpi, int n, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (n == 1) {
            keyAgreementSpi.engineInit(key, secureRandom);
        } else {
            keyAgreementSpi.engineInit(key, algorithmParameterSpec, secureRandom);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void chooseFirstProvider() {
        if (this.spi != null) {
            return;
        }
        Object object = this.lock;
        synchronized (object) {
            if (this.spi != null) {
                return;
            }
            Throwable throwable = null;
            for (Provider.Service service : GetInstance.getServices("KeyAgreement", this.algorithm)) {
                boolean bl = JceSecurity.canUseProvider(service.getProvider());
                if (!bl) continue;
                try {
                    Object object2 = service.newInstance(null);
                    if (!(object2 instanceof KeyAgreementSpi)) continue;
                    this.spi = (KeyAgreementSpi)object2;
                    this.provider = service.getProvider();
                    return;
                }
                catch (Exception exception) {
                }
            }
            Object object3 = new ProviderException("Could not construct KeyAgreementSpi instance");
            if (throwable != null) {
                ((Throwable)object3).initCause(throwable);
            }
            throw object3;
        }
    }

    public final Key doPhase(Key key, boolean bl) throws InvalidKeyException, IllegalStateException {
        this.chooseFirstProvider();
        return this.spi.engineDoPhase(key, bl);
    }

    public final int generateSecret(byte[] arrby, int n) throws IllegalStateException, ShortBufferException {
        this.chooseFirstProvider();
        return this.spi.engineGenerateSecret(arrby, n);
    }

    public final SecretKey generateSecret(String string) throws IllegalStateException, NoSuchAlgorithmException, InvalidKeyException {
        this.chooseFirstProvider();
        return this.spi.engineGenerateSecret(string);
    }

    public final byte[] generateSecret() throws IllegalStateException {
        this.chooseFirstProvider();
        return this.spi.engineGenerateSecret();
    }

    public final String getAlgorithm() {
        return this.algorithm;
    }

    public final Provider getProvider() {
        this.chooseFirstProvider();
        return this.provider;
    }

    public final void init(Key key) throws InvalidKeyException {
        this.init(key, JceSecurity.RANDOM);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void init(Key key, SecureRandom secureRandom) throws InvalidKeyException {
        if (this.spi != null && (key == null || this.lock == null)) {
            this.spi.engineInit(key, secureRandom);
            return;
        }
        try {
            this.chooseProvider(1, key, null, secureRandom);
            return;
        }
        catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
            throw new InvalidKeyException(invalidAlgorithmParameterException);
        }
    }

    public final void init(Key key, AlgorithmParameterSpec algorithmParameterSpec) throws InvalidKeyException, InvalidAlgorithmParameterException {
        this.init(key, algorithmParameterSpec, JceSecurity.RANDOM);
    }

    public final void init(Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        KeyAgreementSpi keyAgreementSpi = this.spi;
        if (keyAgreementSpi != null) {
            keyAgreementSpi.engineInit(key, algorithmParameterSpec, secureRandom);
        } else {
            this.chooseProvider(2, key, algorithmParameterSpec, secureRandom);
        }
    }
}

