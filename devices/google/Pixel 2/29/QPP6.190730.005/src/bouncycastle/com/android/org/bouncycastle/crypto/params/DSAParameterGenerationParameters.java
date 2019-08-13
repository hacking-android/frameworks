/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.params;

import java.security.SecureRandom;

public class DSAParameterGenerationParameters {
    public static final int DIGITAL_SIGNATURE_USAGE = 1;
    public static final int KEY_ESTABLISHMENT_USAGE = 2;
    private final int certainty;
    private final int l;
    private final int n;
    private final SecureRandom random;
    private final int usageIndex;

    public DSAParameterGenerationParameters(int n, int n2, int n3, SecureRandom secureRandom) {
        this(n, n2, n3, secureRandom, -1);
    }

    public DSAParameterGenerationParameters(int n, int n2, int n3, SecureRandom secureRandom, int n4) {
        this.l = n;
        this.n = n2;
        this.certainty = n3;
        this.usageIndex = n4;
        this.random = secureRandom;
    }

    public int getCertainty() {
        return this.certainty;
    }

    public int getL() {
        return this.l;
    }

    public int getN() {
        return this.n;
    }

    public SecureRandom getRandom() {
        return this.random;
    }

    public int getUsageIndex() {
        return this.usageIndex;
    }
}

