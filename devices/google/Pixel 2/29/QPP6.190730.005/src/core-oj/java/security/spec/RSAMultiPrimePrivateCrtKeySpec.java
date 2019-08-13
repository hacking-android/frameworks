/*
 * Decompiled with CFR 0.145.
 */
package java.security.spec;

import java.math.BigInteger;
import java.security.spec.RSAOtherPrimeInfo;
import java.security.spec.RSAPrivateKeySpec;

public class RSAMultiPrimePrivateCrtKeySpec
extends RSAPrivateKeySpec {
    private final BigInteger crtCoefficient;
    private final RSAOtherPrimeInfo[] otherPrimeInfo;
    private final BigInteger primeExponentP;
    private final BigInteger primeExponentQ;
    private final BigInteger primeP;
    private final BigInteger primeQ;
    private final BigInteger publicExponent;

    public RSAMultiPrimePrivateCrtKeySpec(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, BigInteger bigInteger5, BigInteger bigInteger6, BigInteger bigInteger7, BigInteger bigInteger8, RSAOtherPrimeInfo[] arrrSAOtherPrimeInfo) {
        block2 : {
            block3 : {
                block4 : {
                    block5 : {
                        block6 : {
                            block7 : {
                                block8 : {
                                    block9 : {
                                        block12 : {
                                            block11 : {
                                                block10 : {
                                                    super(bigInteger, bigInteger3);
                                                    if (bigInteger == null) break block2;
                                                    if (bigInteger2 == null) break block3;
                                                    if (bigInteger3 == null) break block4;
                                                    if (bigInteger4 == null) break block5;
                                                    if (bigInteger5 == null) break block6;
                                                    if (bigInteger6 == null) break block7;
                                                    if (bigInteger7 == null) break block8;
                                                    if (bigInteger8 == null) break block9;
                                                    this.publicExponent = bigInteger2;
                                                    this.primeP = bigInteger4;
                                                    this.primeQ = bigInteger5;
                                                    this.primeExponentP = bigInteger6;
                                                    this.primeExponentQ = bigInteger7;
                                                    this.crtCoefficient = bigInteger8;
                                                    if (arrrSAOtherPrimeInfo != null) break block10;
                                                    this.otherPrimeInfo = null;
                                                    break block11;
                                                }
                                                if (arrrSAOtherPrimeInfo.length == 0) break block12;
                                                this.otherPrimeInfo = (RSAOtherPrimeInfo[])arrrSAOtherPrimeInfo.clone();
                                            }
                                            return;
                                        }
                                        throw new IllegalArgumentException("the otherPrimeInfo parameter must not be empty");
                                    }
                                    throw new NullPointerException("the crtCoefficient parameter must be non-null");
                                }
                                throw new NullPointerException("the primeExponentQ parameter must be non-null");
                            }
                            throw new NullPointerException("the primeExponentP parameter must be non-null");
                        }
                        throw new NullPointerException("the primeQ parameter must be non-null");
                    }
                    throw new NullPointerException("the primeP parameter must be non-null");
                }
                throw new NullPointerException("the privateExponent parameter must be non-null");
            }
            throw new NullPointerException("the publicExponent parameter must be non-null");
        }
        throw new NullPointerException("the modulus parameter must be non-null");
    }

    public BigInteger getCrtCoefficient() {
        return this.crtCoefficient;
    }

    public RSAOtherPrimeInfo[] getOtherPrimeInfo() {
        RSAOtherPrimeInfo[] arrrSAOtherPrimeInfo = this.otherPrimeInfo;
        if (arrrSAOtherPrimeInfo == null) {
            return null;
        }
        return (RSAOtherPrimeInfo[])arrrSAOtherPrimeInfo.clone();
    }

    public BigInteger getPrimeExponentP() {
        return this.primeExponentP;
    }

    public BigInteger getPrimeExponentQ() {
        return this.primeExponentQ;
    }

    public BigInteger getPrimeP() {
        return this.primeP;
    }

    public BigInteger getPrimeQ() {
        return this.primeQ;
    }

    public BigInteger getPublicExponent() {
        return this.publicExponent;
    }
}

