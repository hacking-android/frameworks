/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.ExemptionMechanismException;
import javax.crypto.ExemptionMechanismSpi;
import javax.crypto.JceSecurity;
import javax.crypto.ShortBufferException;
import sun.security.jca.GetInstance;

public class ExemptionMechanism {
    private boolean done = false;
    private ExemptionMechanismSpi exmechSpi;
    private boolean initialized = false;
    private Key keyStored = null;
    private String mechanism;
    private Provider provider;

    protected ExemptionMechanism(ExemptionMechanismSpi exemptionMechanismSpi, Provider provider, String string) {
        this.exmechSpi = exemptionMechanismSpi;
        this.provider = provider;
        this.mechanism = string;
    }

    public static final ExemptionMechanism getInstance(String string) throws NoSuchAlgorithmException {
        GetInstance.Instance instance = JceSecurity.getInstance("ExemptionMechanism", ExemptionMechanismSpi.class, string);
        return new ExemptionMechanism((ExemptionMechanismSpi)instance.impl, instance.provider, string);
    }

    public static final ExemptionMechanism getInstance(String string, String object) throws NoSuchAlgorithmException, NoSuchProviderException {
        object = JceSecurity.getInstance("ExemptionMechanism", ExemptionMechanismSpi.class, string, (String)object);
        return new ExemptionMechanism((ExemptionMechanismSpi)((GetInstance.Instance)object).impl, ((GetInstance.Instance)object).provider, string);
    }

    public static final ExemptionMechanism getInstance(String string, Provider object) throws NoSuchAlgorithmException {
        object = JceSecurity.getInstance("ExemptionMechanism", ExemptionMechanismSpi.class, string, (Provider)object);
        return new ExemptionMechanism((ExemptionMechanismSpi)((GetInstance.Instance)object).impl, ((GetInstance.Instance)object).provider, string);
    }

    public final int genExemptionBlob(byte[] arrby) throws IllegalStateException, ShortBufferException, ExemptionMechanismException {
        if (this.initialized) {
            int n = this.exmechSpi.engineGenExemptionBlob(arrby, 0);
            this.done = true;
            return n;
        }
        throw new IllegalStateException("ExemptionMechanism not initialized");
    }

    public final int genExemptionBlob(byte[] arrby, int n) throws IllegalStateException, ShortBufferException, ExemptionMechanismException {
        if (this.initialized) {
            n = this.exmechSpi.engineGenExemptionBlob(arrby, n);
            this.done = true;
            return n;
        }
        throw new IllegalStateException("ExemptionMechanism not initialized");
    }

    public final byte[] genExemptionBlob() throws IllegalStateException, ExemptionMechanismException {
        if (this.initialized) {
            byte[] arrby = this.exmechSpi.engineGenExemptionBlob();
            this.done = true;
            return arrby;
        }
        throw new IllegalStateException("ExemptionMechanism not initialized");
    }

    public final String getName() {
        return this.mechanism;
    }

    public final int getOutputSize(int n) throws IllegalStateException {
        if (this.initialized) {
            if (n >= 0) {
                return this.exmechSpi.engineGetOutputSize(n);
            }
            throw new IllegalArgumentException("Input size must be equal to or greater than zero");
        }
        throw new IllegalStateException("ExemptionMechanism not initialized");
    }

    public final Provider getProvider() {
        return this.provider;
    }

    public final void init(Key key) throws InvalidKeyException, ExemptionMechanismException {
        this.done = false;
        this.initialized = false;
        this.keyStored = key;
        this.exmechSpi.engineInit(key);
        this.initialized = true;
    }

    public final void init(Key key, AlgorithmParameters algorithmParameters) throws InvalidKeyException, InvalidAlgorithmParameterException, ExemptionMechanismException {
        this.done = false;
        this.initialized = false;
        this.keyStored = key;
        this.exmechSpi.engineInit(key, algorithmParameters);
        this.initialized = true;
    }

    public final void init(Key key, AlgorithmParameterSpec algorithmParameterSpec) throws InvalidKeyException, InvalidAlgorithmParameterException, ExemptionMechanismException {
        this.done = false;
        this.initialized = false;
        this.keyStored = key;
        this.exmechSpi.engineInit(key, algorithmParameterSpec);
        this.initialized = true;
    }

    public final boolean isCryptoAllowed(Key key) throws ExemptionMechanismException {
        boolean bl;
        boolean bl2 = bl = false;
        if (this.done) {
            bl2 = bl;
            if (key != null) {
                bl2 = this.keyStored.equals(key);
            }
        }
        return bl2;
    }
}

