/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.security.AlgorithmParameterGeneratorSpi;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;

public class AlgorithmParameterGenerator {
    private String algorithm;
    private AlgorithmParameterGeneratorSpi paramGenSpi;
    private Provider provider;

    protected AlgorithmParameterGenerator(AlgorithmParameterGeneratorSpi algorithmParameterGeneratorSpi, Provider provider, String string) {
        this.paramGenSpi = algorithmParameterGeneratorSpi;
        this.provider = provider;
        this.algorithm = string;
    }

    public static AlgorithmParameterGenerator getInstance(String string) throws NoSuchAlgorithmException {
        try {
            Object object = Security.getImpl(string, "AlgorithmParameterGenerator", (String)null);
            object = new AlgorithmParameterGenerator((AlgorithmParameterGeneratorSpi)object[0], (Provider)object[1], string);
            return object;
        }
        catch (NoSuchProviderException noSuchProviderException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(" not found");
            throw new NoSuchAlgorithmException(stringBuilder.toString());
        }
    }

    public static AlgorithmParameterGenerator getInstance(String string, String arrobject) throws NoSuchAlgorithmException, NoSuchProviderException {
        if (arrobject != null && arrobject.length() != 0) {
            arrobject = Security.getImpl(string, "AlgorithmParameterGenerator", (String)arrobject);
            return new AlgorithmParameterGenerator((AlgorithmParameterGeneratorSpi)arrobject[0], (Provider)arrobject[1], string);
        }
        throw new IllegalArgumentException("missing provider");
    }

    public static AlgorithmParameterGenerator getInstance(String string, Provider arrobject) throws NoSuchAlgorithmException {
        if (arrobject != null) {
            arrobject = Security.getImpl(string, "AlgorithmParameterGenerator", (Provider)arrobject);
            return new AlgorithmParameterGenerator((AlgorithmParameterGeneratorSpi)arrobject[0], (Provider)arrobject[1], string);
        }
        throw new IllegalArgumentException("missing provider");
    }

    public final AlgorithmParameters generateParameters() {
        return this.paramGenSpi.engineGenerateParameters();
    }

    public final String getAlgorithm() {
        return this.algorithm;
    }

    public final Provider getProvider() {
        return this.provider;
    }

    public final void init(int n) {
        this.paramGenSpi.engineInit(n, new SecureRandom());
    }

    public final void init(int n, SecureRandom secureRandom) {
        this.paramGenSpi.engineInit(n, secureRandom);
    }

    public final void init(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException {
        this.paramGenSpi.engineInit(algorithmParameterSpec, new SecureRandom());
    }

    public final void init(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        this.paramGenSpi.engineInit(algorithmParameterSpec, secureRandom);
    }
}

