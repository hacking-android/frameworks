/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.io.IOException;
import java.security.AlgorithmParametersSpi;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import sun.security.jca.Providers;

public class AlgorithmParameters {
    private String algorithm;
    private boolean initialized = false;
    private AlgorithmParametersSpi paramSpi;
    private Provider provider;

    protected AlgorithmParameters(AlgorithmParametersSpi algorithmParametersSpi, Provider provider, String string) {
        this.paramSpi = algorithmParametersSpi;
        this.provider = provider;
        this.algorithm = string;
    }

    public static AlgorithmParameters getInstance(String string) throws NoSuchAlgorithmException {
        try {
            Object object = Security.getImpl(string, "AlgorithmParameters", (String)null);
            object = new AlgorithmParameters((AlgorithmParametersSpi)object[0], (Provider)object[1], string);
            return object;
        }
        catch (NoSuchProviderException noSuchProviderException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(" not found");
            throw new NoSuchAlgorithmException(stringBuilder.toString());
        }
    }

    public static AlgorithmParameters getInstance(String string, String arrobject) throws NoSuchAlgorithmException, NoSuchProviderException {
        if (arrobject != null && arrobject.length() != 0) {
            Providers.checkBouncyCastleDeprecation((String)arrobject, "AlgorithmParameters", string);
            arrobject = Security.getImpl(string, "AlgorithmParameters", (String)arrobject);
            return new AlgorithmParameters((AlgorithmParametersSpi)arrobject[0], (Provider)arrobject[1], string);
        }
        throw new IllegalArgumentException("missing provider");
    }

    public static AlgorithmParameters getInstance(String string, Provider arrobject) throws NoSuchAlgorithmException {
        if (arrobject != null) {
            Providers.checkBouncyCastleDeprecation((Provider)arrobject, "AlgorithmParameters", string);
            arrobject = Security.getImpl(string, "AlgorithmParameters", (Provider)arrobject);
            return new AlgorithmParameters((AlgorithmParametersSpi)arrobject[0], (Provider)arrobject[1], string);
        }
        throw new IllegalArgumentException("missing provider");
    }

    public final String getAlgorithm() {
        return this.algorithm;
    }

    public final byte[] getEncoded() throws IOException {
        if (this.initialized) {
            return this.paramSpi.engineGetEncoded();
        }
        throw new IOException("not initialized");
    }

    public final byte[] getEncoded(String string) throws IOException {
        if (this.initialized) {
            return this.paramSpi.engineGetEncoded(string);
        }
        throw new IOException("not initialized");
    }

    public final <T extends AlgorithmParameterSpec> T getParameterSpec(Class<T> class_) throws InvalidParameterSpecException {
        if (this.initialized) {
            return this.paramSpi.engineGetParameterSpec(class_);
        }
        throw new InvalidParameterSpecException("not initialized");
    }

    public final Provider getProvider() {
        return this.provider;
    }

    public final void init(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
        if (!this.initialized) {
            this.paramSpi.engineInit(algorithmParameterSpec);
            this.initialized = true;
            return;
        }
        throw new InvalidParameterSpecException("already initialized");
    }

    public final void init(byte[] arrby) throws IOException {
        if (!this.initialized) {
            this.paramSpi.engineInit(arrby);
            this.initialized = true;
            return;
        }
        throw new IOException("already initialized");
    }

    public final void init(byte[] arrby, String string) throws IOException {
        if (!this.initialized) {
            this.paramSpi.engineInit(arrby, string);
            this.initialized = true;
            return;
        }
        throw new IOException("already initialized");
    }

    public final String toString() {
        if (!this.initialized) {
            return null;
        }
        return this.paramSpi.engineToString();
    }
}

