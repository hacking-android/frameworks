/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.params;

import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.params.DSAValidationParameters;
import java.math.BigInteger;

public class DSAParameters
implements CipherParameters {
    private BigInteger g;
    private BigInteger p;
    private BigInteger q;
    private DSAValidationParameters validation;

    public DSAParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        this.g = bigInteger3;
        this.p = bigInteger;
        this.q = bigInteger2;
    }

    public DSAParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, DSAValidationParameters dSAValidationParameters) {
        this.g = bigInteger3;
        this.p = bigInteger;
        this.q = bigInteger2;
        this.validation = dSAValidationParameters;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof DSAParameters;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (DSAParameters)object;
        bl = bl2;
        if (((DSAParameters)object).getP().equals(this.p)) {
            bl = bl2;
            if (((DSAParameters)object).getQ().equals(this.q)) {
                bl = bl2;
                if (((DSAParameters)object).getG().equals(this.g)) {
                    bl = true;
                }
            }
        }
        return bl;
    }

    public BigInteger getG() {
        return this.g;
    }

    public BigInteger getP() {
        return this.p;
    }

    public BigInteger getQ() {
        return this.q;
    }

    public DSAValidationParameters getValidationParameters() {
        return this.validation;
    }

    public int hashCode() {
        return this.getP().hashCode() ^ this.getQ().hashCode() ^ this.getG().hashCode();
    }
}

