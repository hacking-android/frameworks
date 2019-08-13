/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.signers;

import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.org.bouncycastle.crypto.DSAExt;
import com.android.org.bouncycastle.crypto.params.DSAKeyParameters;
import com.android.org.bouncycastle.crypto.params.DSAParameters;
import com.android.org.bouncycastle.crypto.params.DSAPrivateKeyParameters;
import com.android.org.bouncycastle.crypto.params.DSAPublicKeyParameters;
import com.android.org.bouncycastle.crypto.params.ParametersWithRandom;
import com.android.org.bouncycastle.crypto.signers.DSAKCalculator;
import com.android.org.bouncycastle.crypto.signers.RandomDSAKCalculator;
import com.android.org.bouncycastle.util.BigIntegers;
import java.math.BigInteger;
import java.security.SecureRandom;

public class DSASigner
implements DSAExt {
    private final DSAKCalculator kCalculator;
    private DSAKeyParameters key;
    private SecureRandom random;

    public DSASigner() {
        this.kCalculator = new RandomDSAKCalculator();
    }

    public DSASigner(DSAKCalculator dSAKCalculator) {
        this.kCalculator = dSAKCalculator;
    }

    private BigInteger calculateE(BigInteger arrby, byte[] arrby2) {
        if (arrby.bitLength() >= arrby2.length * 8) {
            return new BigInteger(1, arrby2);
        }
        arrby = new byte[arrby.bitLength() / 8];
        System.arraycopy((byte[])arrby2, (int)0, (byte[])arrby, (int)0, (int)arrby.length);
        return new BigInteger(1, arrby);
    }

    private BigInteger getRandomizer(BigInteger bigInteger, SecureRandom secureRandom) {
        if (secureRandom == null) {
            secureRandom = CryptoServicesRegistrar.getSecureRandom();
        }
        return BigIntegers.createRandomBigInteger(7, secureRandom).add(BigInteger.valueOf(128L)).multiply(bigInteger);
    }

    @Override
    public BigInteger[] generateSignature(byte[] object) {
        Object object2 = this.key.getParameters();
        BigInteger bigInteger = ((DSAParameters)object2).getQ();
        BigInteger bigInteger2 = this.calculateE(bigInteger, (byte[])object);
        BigInteger bigInteger3 = ((DSAPrivateKeyParameters)this.key).getX();
        if (this.kCalculator.isDeterministic()) {
            this.kCalculator.init(bigInteger, bigInteger3, (byte[])object);
        } else {
            this.kCalculator.init(bigInteger, this.random);
        }
        object = this.kCalculator.nextK();
        object2 = ((DSAParameters)object2).getG().modPow(((BigInteger)object).add(this.getRandomizer(bigInteger, this.random)), ((DSAParameters)object2).getP()).mod(bigInteger);
        return new BigInteger[]{object2, ((BigInteger)object).modInverse(bigInteger).multiply(bigInteger2.add(bigInteger3.multiply((BigInteger)object2))).mod(bigInteger)};
    }

    @Override
    public BigInteger getOrder() {
        return this.key.getParameters().getQ();
    }

    @Override
    public void init(boolean bl, CipherParameters object) {
        Object var3_3 = null;
        if (bl) {
            if (object instanceof ParametersWithRandom) {
                object = (ParametersWithRandom)object;
                this.key = (DSAPrivateKeyParameters)((ParametersWithRandom)object).getParameters();
                object = ((ParametersWithRandom)object).getRandom();
            } else {
                this.key = (DSAPrivateKeyParameters)object;
                object = var3_3;
            }
        } else {
            this.key = (DSAPublicKeyParameters)object;
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
    public boolean verifySignature(byte[] object, BigInteger bigInteger, BigInteger bigInteger2) {
        DSAParameters dSAParameters = this.key.getParameters();
        BigInteger bigInteger3 = dSAParameters.getQ();
        object = this.calculateE(bigInteger3, (byte[])object);
        BigInteger bigInteger4 = BigInteger.valueOf(0L);
        if (bigInteger4.compareTo(bigInteger) < 0 && bigInteger3.compareTo(bigInteger) > 0) {
            if (bigInteger4.compareTo(bigInteger2) < 0 && bigInteger3.compareTo(bigInteger2) > 0) {
                bigInteger2 = bigInteger2.modInverse(bigInteger3);
                object = ((BigInteger)object).multiply(bigInteger2).mod(bigInteger3);
                bigInteger4 = bigInteger.multiply(bigInteger2).mod(bigInteger3);
                bigInteger2 = dSAParameters.getP();
                return dSAParameters.getG().modPow((BigInteger)object, bigInteger2).multiply(((DSAPublicKeyParameters)this.key).getY().modPow(bigInteger4, bigInteger2)).mod(bigInteger2).mod(bigInteger3).equals(bigInteger);
            }
            return false;
        }
        return false;
    }
}

