/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.signers;

import com.android.org.bouncycastle.crypto.signers.DSAEncoding;
import com.android.org.bouncycastle.util.Arrays;
import com.android.org.bouncycastle.util.BigIntegers;
import java.math.BigInteger;

public class PlainDSAEncoding
implements DSAEncoding {
    public static final PlainDSAEncoding INSTANCE = new PlainDSAEncoding();

    private void encodeValue(BigInteger arrby, BigInteger bigInteger, byte[] arrby2, int n, int n2) {
        arrby = this.checkValue((BigInteger)arrby, bigInteger).toByteArray();
        int n3 = Math.max(0, arrby.length - n2);
        int n4 = arrby.length - n3;
        Arrays.fill(arrby2, n, n + (n2 -= n4), (byte)0);
        System.arraycopy((byte[])arrby, (int)n3, (byte[])arrby2, (int)(n + n2), (int)n4);
    }

    protected BigInteger checkValue(BigInteger bigInteger, BigInteger bigInteger2) {
        if (bigInteger2.signum() >= 0 && bigInteger2.compareTo(bigInteger) < 0) {
            return bigInteger2;
        }
        throw new IllegalArgumentException("Value out of range");
    }

    @Override
    public BigInteger[] decode(BigInteger bigInteger, byte[] arrby) {
        int n = BigIntegers.getUnsignedByteLength(bigInteger);
        if (arrby.length == n * 2) {
            return new BigInteger[]{this.decodeValue(bigInteger, arrby, 0, n), this.decodeValue(bigInteger, arrby, n, n)};
        }
        throw new IllegalArgumentException("Encoding has incorrect length");
    }

    protected BigInteger decodeValue(BigInteger bigInteger, byte[] arrby, int n, int n2) {
        return this.checkValue(bigInteger, new BigInteger(1, Arrays.copyOfRange(arrby, n, n + n2)));
    }

    @Override
    public byte[] encode(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        int n = BigIntegers.getUnsignedByteLength(bigInteger);
        byte[] arrby = new byte[n * 2];
        this.encodeValue(bigInteger, bigInteger2, arrby, 0, n);
        this.encodeValue(bigInteger, bigInteger3, arrby, n, n);
        return arrby;
    }
}

