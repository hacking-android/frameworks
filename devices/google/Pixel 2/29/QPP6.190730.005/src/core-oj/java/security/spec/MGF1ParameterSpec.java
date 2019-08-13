/*
 * Decompiled with CFR 0.145.
 */
package java.security.spec;

import java.security.spec.AlgorithmParameterSpec;

public class MGF1ParameterSpec
implements AlgorithmParameterSpec {
    public static final MGF1ParameterSpec SHA1 = new MGF1ParameterSpec("SHA-1");
    public static final MGF1ParameterSpec SHA224 = new MGF1ParameterSpec("SHA-224");
    public static final MGF1ParameterSpec SHA256 = new MGF1ParameterSpec("SHA-256");
    public static final MGF1ParameterSpec SHA384 = new MGF1ParameterSpec("SHA-384");
    public static final MGF1ParameterSpec SHA512 = new MGF1ParameterSpec("SHA-512");
    private String mdName;

    public MGF1ParameterSpec(String string) {
        if (string != null) {
            this.mdName = string;
            return;
        }
        throw new NullPointerException("digest algorithm is null");
    }

    public String getDigestAlgorithm() {
        return this.mdName;
    }
}

