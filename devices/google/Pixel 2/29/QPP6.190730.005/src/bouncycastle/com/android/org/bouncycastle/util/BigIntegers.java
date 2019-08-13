/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.util;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;

public final class BigIntegers {
    private static final int MAX_ITERATIONS = 1000;
    public static final BigInteger ONE;
    private static final BigInteger THREE;
    private static final BigInteger TWO;
    public static final BigInteger ZERO;

    static {
        ZERO = BigInteger.valueOf(0L);
        ONE = BigInteger.valueOf(1L);
        TWO = BigInteger.valueOf(2L);
        THREE = BigInteger.valueOf(3L);
    }

    public static byte[] asUnsignedByteArray(int n, BigInteger arrby) {
        int n2;
        byte[] arrby2 = arrby.toByteArray();
        if (arrby2.length == n) {
            return arrby2;
        }
        int n3 = 0;
        if (arrby2[0] == 0) {
            n3 = 1;
        }
        if ((n2 = arrby2.length - n3) <= n) {
            arrby = new byte[n];
            System.arraycopy((byte[])arrby2, (int)n3, (byte[])arrby, (int)(arrby.length - n2), (int)n2);
            return arrby;
        }
        throw new IllegalArgumentException("standard length exceeded for value");
    }

    public static byte[] asUnsignedByteArray(BigInteger arrby) {
        if ((arrby = arrby.toByteArray())[0] == 0) {
            byte[] arrby2 = new byte[arrby.length - 1];
            System.arraycopy((byte[])arrby, (int)1, (byte[])arrby2, (int)0, (int)arrby2.length);
            return arrby2;
        }
        return arrby;
    }

    private static byte[] createRandom(int n, SecureRandom secureRandom) throws IllegalArgumentException {
        if (n >= 1) {
            int n2 = (n + 7) / 8;
            byte[] arrby = new byte[n2];
            secureRandom.nextBytes(arrby);
            arrby[0] = (byte)(arrby[0] & (byte)(255 >>> n2 * 8 - n));
            return arrby;
        }
        throw new IllegalArgumentException("bitLength must be at least 1");
    }

    public static BigInteger createRandomBigInteger(int n, SecureRandom secureRandom) {
        return new BigInteger(1, BigIntegers.createRandom(n, secureRandom));
    }

    public static BigInteger createRandomInRange(BigInteger bigInteger, BigInteger bigInteger2, SecureRandom secureRandom) {
        int n = bigInteger.compareTo(bigInteger2);
        if (n >= 0) {
            if (n <= 0) {
                return bigInteger;
            }
            throw new IllegalArgumentException("'min' may not be greater than 'max'");
        }
        if (bigInteger.bitLength() > bigInteger2.bitLength() / 2) {
            return BigIntegers.createRandomInRange(ZERO, bigInteger2.subtract(bigInteger), secureRandom).add(bigInteger);
        }
        for (n = 0; n < 1000; ++n) {
            BigInteger bigInteger3 = BigIntegers.createRandomBigInteger(bigInteger2.bitLength(), secureRandom);
            if (bigInteger3.compareTo(bigInteger) < 0 || bigInteger3.compareTo(bigInteger2) > 0) continue;
            return bigInteger3;
        }
        return BigIntegers.createRandomBigInteger(bigInteger2.subtract(bigInteger).bitLength() - 1, secureRandom).add(bigInteger);
    }

    public static BigInteger createRandomPrime(int n, int n2, SecureRandom serializable) {
        if (n >= 2) {
            Object object;
            if (n == 2) {
                serializable = serializable.nextInt() < 0 ? TWO : THREE;
                return serializable;
            }
            do {
                object = BigIntegers.createRandom(n, serializable);
                int n3 = 1 << 7 - (((byte[])object).length * 8 - n);
                object[0] = (byte)(object[0] | n3);
                n3 = ((byte[])object).length - 1;
                object[n3] = (byte)(object[n3] | 1);
            } while (!((BigInteger)(object = new BigInteger(1, (byte[])object))).isProbablePrime(n2));
            return object;
        }
        throw new IllegalArgumentException("bitLength < 2");
    }

    public static BigInteger fromUnsignedByteArray(byte[] arrby) {
        return new BigInteger(1, arrby);
    }

    public static BigInteger fromUnsignedByteArray(byte[] arrby, int n, int n2) {
        byte[] arrby2 = arrby;
        if (n != 0 || n2 != arrby.length) {
            arrby2 = new byte[n2];
            System.arraycopy((byte[])arrby, (int)n, (byte[])arrby2, (int)0, (int)n2);
        }
        return new BigInteger(1, arrby2);
    }

    public static int getUnsignedByteLength(BigInteger bigInteger) {
        return (bigInteger.bitLength() + 7) / 8;
    }
}

