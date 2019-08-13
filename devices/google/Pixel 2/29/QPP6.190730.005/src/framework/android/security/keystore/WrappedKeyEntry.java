/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import java.security.KeyStore;
import java.security.spec.AlgorithmParameterSpec;

public class WrappedKeyEntry
implements KeyStore.Entry {
    private final AlgorithmParameterSpec mAlgorithmParameterSpec;
    private final String mTransformation;
    private final byte[] mWrappedKeyBytes;
    private final String mWrappingKeyAlias;

    public WrappedKeyEntry(byte[] arrby, String string2, String string3, AlgorithmParameterSpec algorithmParameterSpec) {
        this.mWrappedKeyBytes = arrby;
        this.mWrappingKeyAlias = string2;
        this.mTransformation = string3;
        this.mAlgorithmParameterSpec = algorithmParameterSpec;
    }

    public AlgorithmParameterSpec getAlgorithmParameterSpec() {
        return this.mAlgorithmParameterSpec;
    }

    public String getTransformation() {
        return this.mTransformation;
    }

    public byte[] getWrappedKeyBytes() {
        return this.mWrappedKeyBytes;
    }

    public String getWrappingKeyAlias() {
        return this.mWrappingKeyAlias;
    }
}

