/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.params;

import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.params.DHValidationParameters;
import java.math.BigInteger;

public class DHParameters
implements CipherParameters {
    private static final int DEFAULT_MINIMUM_LENGTH = 160;
    private BigInteger g;
    private BigInteger j;
    private int l;
    private int m;
    private BigInteger p;
    private BigInteger q;
    private DHValidationParameters validation;

    public DHParameters(BigInteger bigInteger, BigInteger bigInteger2) {
        this(bigInteger, bigInteger2, null, 0);
    }

    public DHParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        this(bigInteger, bigInteger2, bigInteger3, 0);
    }

    public DHParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, int n) {
        this(bigInteger, bigInteger2, bigInteger3, DHParameters.getDefaultMParam(n), n, null, null);
    }

    public DHParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, int n, int n2) {
        this(bigInteger, bigInteger2, bigInteger3, n, n2, null, null);
    }

    public DHParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, int n, int n2, BigInteger bigInteger4, DHValidationParameters dHValidationParameters) {
        if (n2 != 0) {
            if (n2 <= bigInteger.bitLength()) {
                if (n2 < n) {
                    throw new IllegalArgumentException("when l value specified, it may not be less than m value");
                }
            } else {
                throw new IllegalArgumentException("when l value specified, it must satisfy 2^(l-1) <= p");
            }
        }
        if (n <= bigInteger.bitLength()) {
            this.g = bigInteger2;
            this.p = bigInteger;
            this.q = bigInteger3;
            this.m = n;
            this.l = n2;
            this.j = bigInteger4;
            this.validation = dHValidationParameters;
            return;
        }
        throw new IllegalArgumentException("unsafe p value so small specific l required");
    }

    public DHParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, DHValidationParameters dHValidationParameters) {
        this(bigInteger, bigInteger2, bigInteger3, 160, 0, bigInteger4, dHValidationParameters);
    }

    private static int getDefaultMParam(int n) {
        int n2 = 160;
        if (n == 0) {
            return 160;
        }
        if (n < 160) {
            n2 = n;
        }
        return n2;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof DHParameters;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (DHParameters)object;
        if (this.getQ() != null ? !this.getQ().equals(((DHParameters)object).getQ()) : ((DHParameters)object).getQ() != null) {
            return false;
        }
        bl = bl2;
        if (((DHParameters)object).getP().equals(this.p)) {
            bl = bl2;
            if (((DHParameters)object).getG().equals(this.g)) {
                bl = true;
            }
        }
        return bl;
    }

    public BigInteger getG() {
        return this.g;
    }

    public BigInteger getJ() {
        return this.j;
    }

    public int getL() {
        return this.l;
    }

    public int getM() {
        return this.m;
    }

    public BigInteger getP() {
        return this.p;
    }

    public BigInteger getQ() {
        return this.q;
    }

    public DHValidationParameters getValidationParameters() {
        return this.validation;
    }

    public int hashCode() {
        int n = this.getP().hashCode();
        int n2 = this.getG().hashCode();
        int n3 = this.getQ() != null ? this.getQ().hashCode() : 0;
        return n ^ n2 ^ n3;
    }
}

