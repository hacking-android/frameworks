/*
 * Decompiled with CFR 0.145.
 */
package java.security.spec;

import java.security.spec.AlgorithmParameterSpec;

public class ECGenParameterSpec
implements AlgorithmParameterSpec {
    private String name;

    public ECGenParameterSpec(String string) {
        if (string != null) {
            this.name = string;
            return;
        }
        throw new NullPointerException("stdName is null");
    }

    public String getName() {
        return this.name;
    }
}

