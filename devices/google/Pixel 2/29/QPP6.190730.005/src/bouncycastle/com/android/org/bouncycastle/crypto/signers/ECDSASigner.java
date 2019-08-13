/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.signers;

import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.org.bouncycastle.crypto.DSAExt;
import com.android.org.bouncycastle.crypto.params.ECDomainParameters;
import com.android.org.bouncycastle.crypto.params.ECKeyParameters;
import com.android.org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import com.android.org.bouncycastle.crypto.params.ECPublicKeyParameters;
import com.android.org.bouncycastle.crypto.params.ParametersWithRandom;
import com.android.org.bouncycastle.crypto.signers.DSAKCalculator;
import com.android.org.bouncycastle.crypto.signers.RandomDSAKCalculator;
import com.android.org.bouncycastle.math.ec.ECAlgorithms;
import com.android.org.bouncycastle.math.ec.ECConstants;
import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECFieldElement;
import com.android.org.bouncycastle.math.ec.ECMultiplier;
import com.android.org.bouncycastle.math.ec.ECPoint;
import com.android.org.bouncycastle.math.ec.FixedPointCombMultiplier;
import java.math.BigInteger;
import java.security.SecureRandom;

public class ECDSASigner
implements ECConstants,
DSAExt {
    private final DSAKCalculator kCalculator;
    private ECKeyParameters key;
    private SecureRandom random;

    public ECDSASigner() {
        this.kCalculator = new RandomDSAKCalculator();
    }

    public ECDSASigner(DSAKCalculator dSAKCalculator) {
        this.kCalculator = dSAKCalculator;
    }

    protected BigInteger calculateE(BigInteger object, byte[] object2) {
        int n = ((BigInteger)object).bitLength();
        int n2 = ((byte[])object2).length * 8;
        object = object2 = new BigInteger(1, (byte[])object2);
        if (n < n2) {
            object = ((BigInteger)object2).shiftRight(n2 - n);
        }
        return object;
    }

    protected ECMultiplier createBasePointMultiplier() {
        return new FixedPointCombMultiplier();
    }

    @Override
    public BigInteger[] generateSignature(byte[] object) {
        BigInteger bigInteger;
        BigInteger bigInteger2;
        ECDomainParameters eCDomainParameters = this.key.getParameters();
        BigInteger bigInteger3 = eCDomainParameters.getN();
        BigInteger bigInteger4 = this.calculateE(bigInteger3, (byte[])object);
        BigInteger bigInteger5 = ((ECPrivateKeyParameters)this.key).getD();
        if (this.kCalculator.isDeterministic()) {
            this.kCalculator.init(bigInteger3, bigInteger5, (byte[])object);
        } else {
            this.kCalculator.init(bigInteger3, this.random);
        }
        object = this.createBasePointMultiplier();
        do {
            bigInteger = this.kCalculator.nextK();
        } while ((bigInteger2 = object.multiply(eCDomainParameters.getG(), bigInteger).normalize().getAffineXCoord().toBigInteger().mod(bigInteger3)).equals(ZERO) || (bigInteger = bigInteger.modInverse(bigInteger3).multiply(bigInteger4.add(bigInteger5.multiply(bigInteger2))).mod(bigInteger3)).equals(ZERO));
        return new BigInteger[]{bigInteger2, bigInteger};
    }

    protected ECFieldElement getDenominator(int n, ECPoint eCPoint) {
        if (n != 1) {
            if (n != 2 && n != 3 && n != 4) {
                if (n != 6 && n != 7) {
                    return null;
                }
            } else {
                return eCPoint.getZCoord(0).square();
            }
        }
        return eCPoint.getZCoord(0);
    }

    @Override
    public BigInteger getOrder() {
        return this.key.getParameters().getN();
    }

    @Override
    public void init(boolean bl, CipherParameters object) {
        Object var3_3 = null;
        if (bl) {
            if (object instanceof ParametersWithRandom) {
                object = (ParametersWithRandom)object;
                this.key = (ECPrivateKeyParameters)((ParametersWithRandom)object).getParameters();
                object = ((ParametersWithRandom)object).getRandom();
            } else {
                this.key = (ECPrivateKeyParameters)object;
                object = var3_3;
            }
        } else {
            this.key = (ECPublicKeyParameters)object;
            object = var3_3;
        }
        bl = bl && !this.kCalculator.isDeterministic();
        this.random = this.initSecureRandom(bl, (SecureRandom)object);
    }

    protected SecureRandom initSecureRandom(boolean bl, SecureRandom secureRandom) {
        if (!bl) {
            secureRandom = null;
        } else if (secureRandom == null) {
            secureRandom = CryptoServicesRegistrar.getSecureRandom();
        }
        return secureRandom;
    }

    @Override
    public boolean verifySignature(byte[] object, BigInteger bigInteger, BigInteger object2) {
        Object object3 = this.key.getParameters();
        BigInteger bigInteger2 = ((ECDomainParameters)object3).getN();
        object = this.calculateE(bigInteger2, (byte[])object);
        if (bigInteger.compareTo(ONE) >= 0 && bigInteger.compareTo(bigInteger2) < 0) {
            if (((BigInteger)object2).compareTo(ONE) >= 0 && ((BigInteger)object2).compareTo(bigInteger2) < 0) {
                object2 = ((BigInteger)object2).modInverse(bigInteger2);
                object = ((BigInteger)object).multiply((BigInteger)object2).mod(bigInteger2);
                object2 = bigInteger.multiply((BigInteger)object2).mod(bigInteger2);
                if (((ECPoint)(object3 = ECAlgorithms.sumOfTwoMultiplies(((ECDomainParameters)object3).getG(), (BigInteger)object, ((ECPublicKeyParameters)this.key).getQ(), (BigInteger)object2))).isInfinity()) {
                    return false;
                }
                object = ((ECPoint)object3).getCurve();
                if (object != null && (object2 = ((ECCurve)object).getCofactor()) != null && ((BigInteger)object2).compareTo(EIGHT) <= 0 && (object2 = this.getDenominator(((ECCurve)object).getCoordinateSystem(), (ECPoint)object3)) != null && !((ECFieldElement)object2).isZero()) {
                    object3 = ((ECPoint)object3).getXCoord();
                    while (((ECCurve)object).isValidFieldElement(bigInteger)) {
                        if (((ECCurve)object).fromBigInteger(bigInteger).multiply((ECFieldElement)object2).equals(object3)) {
                            return true;
                        }
                        bigInteger = bigInteger.add(bigInteger2);
                    }
                    return false;
                }
                return ((ECPoint)object3).normalize().getAffineXCoord().toBigInteger().mod(bigInteger2).equals(bigInteger);
            }
            return false;
        }
        return false;
    }
}

