/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.engines;

import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.DataLengthException;
import com.android.org.bouncycastle.crypto.params.ParametersWithRandom;
import com.android.org.bouncycastle.crypto.params.RSAKeyParameters;
import com.android.org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import com.android.org.bouncycastle.util.Arrays;
import java.math.BigInteger;

class RSACoreEngine {
    private boolean forEncryption;
    private RSAKeyParameters key;

    RSACoreEngine() {
    }

    public BigInteger convertInput(byte[] object, int n, int n2) {
        if (n2 <= this.getInputBlockSize() + 1) {
            if (n2 == this.getInputBlockSize() + 1 && !this.forEncryption) {
                throw new DataLengthException("input too large for RSA cipher.");
            }
            if (n != 0 || n2 != ((byte[])object).length) {
                byte[] arrby = new byte[n2];
                System.arraycopy((byte[])object, (int)n, (byte[])arrby, (int)0, (int)n2);
                object = arrby;
            }
            object = new BigInteger(1, (byte[])object);
            if (((BigInteger)object).compareTo(this.key.getModulus()) < 0) {
                return object;
            }
            throw new DataLengthException("input too large for RSA cipher.");
        }
        throw new DataLengthException("input too large for RSA cipher.");
    }

    public byte[] convertOutput(BigInteger arrby) {
        byte[] arrby2 = arrby.toByteArray();
        if (this.forEncryption) {
            if (arrby2[0] == 0 && arrby2.length > this.getOutputBlockSize()) {
                arrby = new byte[arrby2.length - 1];
                System.arraycopy((byte[])arrby2, (int)1, (byte[])arrby, (int)0, (int)arrby.length);
                return arrby;
            }
            if (arrby2.length < this.getOutputBlockSize()) {
                arrby = new byte[this.getOutputBlockSize()];
                System.arraycopy((byte[])arrby2, (int)0, (byte[])arrby, (int)(arrby.length - arrby2.length), (int)arrby2.length);
                return arrby;
            }
            return arrby2;
        }
        if (arrby2[0] == 0) {
            arrby = new byte[arrby2.length - 1];
            System.arraycopy((byte[])arrby2, (int)1, (byte[])arrby, (int)0, (int)arrby.length);
        } else {
            arrby = new byte[arrby2.length];
            System.arraycopy((byte[])arrby2, (int)0, (byte[])arrby, (int)0, (int)arrby.length);
        }
        Arrays.fill(arrby2, (byte)0);
        return arrby;
    }

    public int getInputBlockSize() {
        int n = this.key.getModulus().bitLength();
        if (this.forEncryption) {
            return (n + 7) / 8 - 1;
        }
        return (n + 7) / 8;
    }

    public int getOutputBlockSize() {
        int n = this.key.getModulus().bitLength();
        if (this.forEncryption) {
            return (n + 7) / 8;
        }
        return (n + 7) / 8 - 1;
    }

    public void init(boolean bl, CipherParameters cipherParameters) {
        this.key = cipherParameters instanceof ParametersWithRandom ? (RSAKeyParameters)((ParametersWithRandom)cipherParameters).getParameters() : (RSAKeyParameters)cipherParameters;
        this.forEncryption = bl;
    }

    public BigInteger processBlock(BigInteger bigInteger) {
        Object object = this.key;
        if (object instanceof RSAPrivateCrtKeyParameters) {
            Object object2 = (RSAPrivateCrtKeyParameters)object;
            BigInteger bigInteger2 = ((RSAPrivateCrtKeyParameters)object2).getP();
            object = ((RSAPrivateCrtKeyParameters)object2).getQ();
            BigInteger bigInteger3 = ((RSAPrivateCrtKeyParameters)object2).getDP();
            BigInteger bigInteger4 = ((RSAPrivateCrtKeyParameters)object2).getDQ();
            object2 = ((RSAPrivateCrtKeyParameters)object2).getQInv();
            bigInteger3 = bigInteger.remainder(bigInteger2).modPow(bigInteger3, bigInteger2);
            bigInteger = bigInteger.remainder((BigInteger)object).modPow(bigInteger4, (BigInteger)object);
            return bigInteger3.subtract(bigInteger).multiply((BigInteger)object2).mod(bigInteger2).multiply((BigInteger)object).add(bigInteger);
        }
        return bigInteger.modPow(((RSAKeyParameters)object).getExponent(), this.key.getModulus());
    }
}

