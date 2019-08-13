/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto.spec;

import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.MGF1ParameterSpec;
import javax.crypto.spec.PSource;

public class OAEPParameterSpec
implements AlgorithmParameterSpec {
    public static final OAEPParameterSpec DEFAULT = new OAEPParameterSpec();
    private String mdName = "SHA-1";
    private String mgfName = "MGF1";
    private AlgorithmParameterSpec mgfSpec = MGF1ParameterSpec.SHA1;
    private PSource pSrc = PSource.PSpecified.DEFAULT;

    private OAEPParameterSpec() {
    }

    public OAEPParameterSpec(String string, String string2, AlgorithmParameterSpec algorithmParameterSpec, PSource pSource) {
        if (string != null) {
            if (string2 != null) {
                if (pSource != null) {
                    this.mdName = string;
                    this.mgfName = string2;
                    this.mgfSpec = algorithmParameterSpec;
                    this.pSrc = pSource;
                    return;
                }
                throw new NullPointerException("source of the encoding input is null");
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

    public PSource getPSource() {
        return this.pSrc;
    }
}

