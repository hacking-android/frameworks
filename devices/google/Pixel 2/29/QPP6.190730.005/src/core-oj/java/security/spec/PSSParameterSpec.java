/*
 * Decompiled with CFR 0.145.
 */
package java.security.spec;

import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.MGF1ParameterSpec;

public class PSSParameterSpec
implements AlgorithmParameterSpec {
    public static final PSSParameterSpec DEFAULT = new PSSParameterSpec();
    private String mdName = "SHA-1";
    private String mgfName = "MGF1";
    private AlgorithmParameterSpec mgfSpec = MGF1ParameterSpec.SHA1;
    private int saltLen = 20;
    private int trailerField = 1;

    private PSSParameterSpec() {
    }

    public PSSParameterSpec(int n) {
        if (n >= 0) {
            this.saltLen = n;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("negative saltLen value: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public PSSParameterSpec(String charSequence, String string, AlgorithmParameterSpec algorithmParameterSpec, int n, int n2) {
        if (charSequence != null) {
            if (string != null) {
                if (n >= 0) {
                    if (n2 >= 0) {
                        this.mdName = charSequence;
                        this.mgfName = string;
                        this.mgfSpec = algorithmParameterSpec;
                        this.saltLen = n;
                        this.trailerField = n2;
                        return;
                    }
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("negative trailerField: ");
                    ((StringBuilder)charSequence).append(n2);
                    throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("negative saltLen value: ");
                ((StringBuilder)charSequence).append(n);
                throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
            }
            throw new NullPointerException("mask generation function algorithm is null");
        }
        throw new NullPointerException("digest algorithm is null");
    }

    public String getDigestAlgorithm() {
        return this.mdName;
    }

    public String getMGFAlgorithm() {
        return this.mgfName;
    }

    public AlgorithmParameterSpec getMGFParameters() {
        return this.mgfSpec;
    }

    public int getSaltLength() {
        return this.saltLen;
    }

    public int getTrailerField() {
        return this.trailerField;
    }
}

