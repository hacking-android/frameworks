/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto;

import java.security.Permission;
import java.security.spec.AlgorithmParameterSpec;

class CryptoPermission
extends Permission {
    static final String ALG_NAME_WILDCARD = null;

    CryptoPermission(String string) {
        super("");
    }

    CryptoPermission(String string, int n) {
        super("");
    }

    CryptoPermission(String string, int n, String string2) {
        super("");
    }

    CryptoPermission(String string, int n, AlgorithmParameterSpec algorithmParameterSpec) {
        super("");
    }

    CryptoPermission(String string, int n, AlgorithmParameterSpec algorithmParameterSpec, String string2) {
        super("");
    }

    CryptoPermission(String string, String string2) {
        super("");
    }

    @Override
    public String getActions() {
        return null;
    }

    final String getAlgorithm() {
        return null;
    }

    final AlgorithmParameterSpec getAlgorithmParameterSpec() {
        return null;
    }

    final boolean getCheckParam() {
        return false;
    }

    final String getExemptionMechanism() {
        return null;
    }

    final int getMaxKeySize() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean implies(Permission permission) {
        return true;
    }
}

