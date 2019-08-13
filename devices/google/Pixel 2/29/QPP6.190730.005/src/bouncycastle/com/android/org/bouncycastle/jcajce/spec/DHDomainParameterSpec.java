/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.spec;

import com.android.org.bouncycastle.crypto.params.DHParameters;
import com.android.org.bouncycastle.crypto.params.DHValidationParameters;
import java.math.BigInteger;
import javax.crypto.spec.DHParameterSpec;

public class DHDomainParameterSpec
extends DHParameterSpec {
    private final BigInteger j;
    private final int m;
    private final BigInteger q;
    private DHValidationParameters validationParameters;

    public DHDomainParameterSpec(DHParameters dHParameters) {
        this(dHParameters.getP(), dHParameters.getQ(), dHParameters.getG(), dHParameters.getJ(), dHParameters.getM(), dHParameters.getL());
        this.validationParameters = dHParameters.getValidationParameters();
    }

    public DHDomainParameterSpec(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        this(bigInteger, bigInteger2, bigInteger3, null, 0);
    }

    public DHDomainParameterSpec(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, int n) {
        this(bigInteger, bigInteger2, bigInteger3, null, n);
    }

    public DHDomainParameterSpec(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, int n) {
        this(bigInteger, bigInteger2, bigInteger3, bigInteger4, 0, n);
    }

    public DHDomainParameterSpec(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, int n, int n2) {
        super(bigInteger, bigInteger3, n2);
        this.q = bigInteger2;
        this.j = bigInteger4;
        this.m = n;
    }

    public DHParameters getDomainParameters() {
        return new DHParameters(this.getP(), this.getG(), this.q, this.m, this.getL(), this.j, this.validationParameters);
    }

    public BigInteger getJ() {
        return this.j;
    }

    public int getM() {
        return this.m;
    }

    public BigInteger getQ() {
        return this.q;
    }
}

