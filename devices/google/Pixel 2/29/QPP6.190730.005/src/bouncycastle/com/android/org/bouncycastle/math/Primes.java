/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math;

import com.android.org.bouncycastle.crypto.Digest;
import com.android.org.bouncycastle.util.Arrays;
import com.android.org.bouncycastle.util.BigIntegers;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;

public abstract class Primes {
    private static final BigInteger ONE = BigInteger.valueOf(1L);
    public static final int SMALL_FACTOR_LIMIT = 211;
    private static final BigInteger THREE;
    private static final BigInteger TWO;

    static {
        TWO = BigInteger.valueOf(2L);
        THREE = BigInteger.valueOf(3L);
    }

    private static void checkCandidate(BigInteger serializable, String string) {
        if (serializable != null && ((BigInteger)serializable).signum() >= 1 && ((BigInteger)serializable).bitLength() >= 2) {
            return;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("'");
        ((StringBuilder)serializable).append(string);
        ((StringBuilder)serializable).append("' must be non-null and >= 2");
        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
    }

    public static MROutput enhancedMRProbablePrimeTest(BigInteger bigInteger, SecureRandom serializable, int n) {
        Primes.checkCandidate(bigInteger, "candidate");
        if (serializable != null) {
            if (n >= 1) {
                if (bigInteger.bitLength() == 2) {
                    return MROutput.probablyPrime();
                }
                if (!bigInteger.testBit(0)) {
                    return MROutput.provablyCompositeWithFactor(Primes.TWO);
                }
                BigInteger bigInteger2 = bigInteger.subtract(ONE);
                BigInteger bigInteger3 = bigInteger.subtract(TWO);
                int n2 = bigInteger2.getLowestSetBit();
                BigInteger bigInteger4 = bigInteger2.shiftRight(n2);
                for (int i = 0; i < n; ++i) {
                    boolean bl;
                    BigInteger bigInteger5 = BigIntegers.createRandomInRange(TWO, bigInteger3, (SecureRandom)serializable);
                    Serializable serializable2 = bigInteger5.gcd(bigInteger);
                    if (serializable2.compareTo(ONE) > 0) {
                        return MROutput.provablyCompositeWithFactor(serializable2);
                    }
                    BigInteger bigInteger6 = bigInteger5.modPow(bigInteger4, bigInteger);
                    if (bigInteger6.equals(ONE) || bigInteger6.equals(bigInteger2)) continue;
                    boolean bl2 = false;
                    serializable2 = bigInteger6;
                    int n3 = 1;
                    do {
                        bigInteger5 = bigInteger6;
                        bl = bl2;
                        if (n3 >= n2) break;
                        bigInteger5 = bigInteger6.modPow(TWO, bigInteger);
                        if (bigInteger5.equals(bigInteger2)) {
                            bl = true;
                            break;
                        }
                        if (bigInteger5.equals(ONE)) {
                            bl = bl2;
                            break;
                        }
                        serializable2 = bigInteger5;
                        ++n3;
                        bigInteger6 = bigInteger5;
                    } while (true);
                    if (bl) continue;
                    if (!bigInteger5.equals(ONE)) {
                        serializable2 = bigInteger5;
                        serializable = bigInteger5.modPow(TWO, bigInteger);
                        if (!((BigInteger)serializable).equals(ONE)) {
                            serializable2 = serializable;
                        }
                    }
                    if ((bigInteger = serializable2.subtract(ONE).gcd(bigInteger)).compareTo(ONE) > 0) {
                        return MROutput.provablyCompositeWithFactor(bigInteger);
                    }
                    return MROutput.provablyCompositeNotPrimePower();
                }
                return MROutput.probablyPrime();
            }
            throw new IllegalArgumentException("'iterations' must be > 0");
        }
        throw new IllegalArgumentException("'random' cannot be null");
    }

    private static int extract32(byte[] arrby) {
        int n = 0;
        int n2 = Math.min(4, arrby.length);
        for (int i = 0; i < n2; ++i) {
            n |= (arrby[arrby.length - (i + 1)] & 255) << i * 8;
        }
        return n;
    }

    public static STOutput generateSTRandomPrime(Digest digest, int n, byte[] arrby) {
        if (digest != null) {
            if (n >= 2) {
                if (arrby != null && arrby.length != 0) {
                    return Primes.implSTRandomPrime(digest, n, Arrays.clone(arrby));
                }
                throw new IllegalArgumentException("'inputSeed' cannot be null or empty");
            }
            throw new IllegalArgumentException("'length' must be >= 2");
        }
        throw new IllegalArgumentException("'hash' cannot be null");
    }

    public static boolean hasAnySmallFactors(BigInteger bigInteger) {
        Primes.checkCandidate(bigInteger, "candidate");
        return Primes.implHasAnySmallFactors(bigInteger);
    }

    private static void hash(Digest digest, byte[] arrby, byte[] arrby2, int n) {
        digest.update(arrby, 0, arrby.length);
        digest.doFinal(arrby2, n);
    }

    private static BigInteger hashGen(Digest digest, byte[] arrby, int n) {
        int n2 = digest.getDigestSize();
        int n3 = n * n2;
        byte[] arrby2 = new byte[n3];
        for (int i = 0; i < n; ++i) {
            Primes.hash(digest, arrby, arrby2, n3 -= n2);
            Primes.inc(arrby, 1);
        }
        return new BigInteger(1, arrby2);
    }

    private static boolean implHasAnySmallFactors(BigInteger bigInteger) {
        int n = bigInteger.mod(BigInteger.valueOf(223092870)).intValue();
        if (n % 2 != 0 && n % 3 != 0 && n % 5 != 0 && n % 7 != 0 && n % 11 != 0 && n % 13 != 0 && n % 17 != 0 && n % 19 != 0 && n % 23 != 0) {
            n = bigInteger.mod(BigInteger.valueOf(58642669)).intValue();
            if (n % 29 != 0 && n % 31 != 0 && n % 37 != 0 && n % 41 != 0 && n % 43 != 0) {
                n = bigInteger.mod(BigInteger.valueOf(600662303)).intValue();
                if (n % 47 != 0 && n % 53 != 0 && n % 59 != 0 && n % 61 != 0 && n % 67 != 0) {
                    n = bigInteger.mod(BigInteger.valueOf(33984931)).intValue();
                    if (n % 71 != 0 && n % 73 != 0 && n % 79 != 0 && n % 83 != 0) {
                        n = bigInteger.mod(BigInteger.valueOf(89809099)).intValue();
                        if (n % 89 != 0 && n % 97 != 0 && n % 101 != 0 && n % 103 != 0) {
                            n = bigInteger.mod(BigInteger.valueOf(167375713)).intValue();
                            if (n % 107 != 0 && n % 109 != 0 && n % 113 != 0 && n % 127 != 0) {
                                n = bigInteger.mod(BigInteger.valueOf(371700317)).intValue();
                                if (n % 131 != 0 && n % 137 != 0 && n % 139 != 0 && n % 149 != 0) {
                                    n = bigInteger.mod(BigInteger.valueOf(645328247)).intValue();
                                    if (n % 151 != 0 && n % 157 != 0 && n % 163 != 0 && n % 167 != 0) {
                                        n = bigInteger.mod(BigInteger.valueOf(1070560157)).intValue();
                                        if (n % 173 != 0 && n % 179 != 0 && n % 181 != 0 && n % 191 != 0) {
                                            n = bigInteger.mod(BigInteger.valueOf(1596463769)).intValue();
                                            return n % 193 == 0 || n % 197 == 0 || n % 199 == 0 || n % 211 == 0;
                                            {
                                            }
                                        }
                                        return true;
                                    }
                                    return true;
                                }
                                return true;
                            }
                            return true;
                        }
                        return true;
                    }
                    return true;
                }
                return true;
            }
            return true;
        }
        return true;
    }

    private static boolean implMRProbablePrimeToBase(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, int n, BigInteger bigInteger4) {
        if (!(bigInteger3 = bigInteger4.modPow(bigInteger3, bigInteger)).equals(ONE) && !bigInteger3.equals(bigInteger2)) {
            boolean bl;
            boolean bl2 = false;
            int n2 = 1;
            do {
                bl = bl2;
                if (n2 >= n) break;
                if ((bigInteger3 = bigInteger3.modPow(TWO, bigInteger)).equals(bigInteger2)) {
                    bl = true;
                    break;
                }
                if (bigInteger3.equals(ONE)) {
                    return false;
                }
                ++n2;
            } while (true);
            return bl;
        }
        return true;
    }

    private static STOutput implSTRandomPrime(Digest arrby, int n, byte[] object) {
        Object object2 = arrby;
        int n2 = arrby.getDigestSize();
        if (n < 33) {
            int n3 = 0;
            byte[] arrby2 = new byte[n2];
            arrby = new byte[n2];
            do {
                Primes.hash((Digest)object2, (byte[])object, arrby2, 0);
                Primes.inc((byte[])object, 1);
                Primes.hash((Digest)object2, (byte[])object, arrby, 0);
                Primes.inc((byte[])object, 1);
                n2 = Primes.extract32(arrby2);
                int n4 = Primes.extract32(arrby);
                ++n3;
                long l = (long)((n2 ^ n4) & -1 >>> 32 - n | (1 << n - 1 | 1)) & 0xFFFFFFFFL;
                if (!Primes.isPrime32(l)) continue;
                return new STOutput(BigInteger.valueOf(l), (byte[])object, n3);
            } while (n3 <= n * 4);
            throw new IllegalStateException("Too many iterations in Shawe-Taylor Random_Prime Routine");
        }
        STOutput sTOutput = Primes.implSTRandomPrime((Digest)object2, (n + 3) / 2, (byte[])object);
        BigInteger bigInteger = sTOutput.getPrime();
        byte[] arrby3 = sTOutput.getPrimeSeed();
        int n5 = sTOutput.getPrimeGenCounter();
        int n6 = (n - 1) / (n2 * 8);
        object = Primes.hashGen((Digest)object2, arrby3, n6 + 1).mod(ONE.shiftLeft(n - 1)).setBit(n - 1);
        BigInteger bigInteger2 = bigInteger.shiftLeft(1);
        object = ((BigInteger)object).subtract(ONE).divide(bigInteger2).add(ONE).shiftLeft(1);
        int n7 = 0;
        object2 = ((BigInteger)object).multiply(bigInteger).add(ONE);
        n2 = n5;
        do {
            if (((BigInteger)object2).bitLength() > n) {
                object = ONE.shiftLeft(n - 1).subtract(ONE).divide(bigInteger2).add(ONE).shiftLeft(1);
                object2 = ((BigInteger)object).multiply(bigInteger).add(ONE);
            }
            ++n2;
            if (!Primes.implHasAnySmallFactors((BigInteger)object2)) {
                BigInteger bigInteger3 = Primes.hashGen((Digest)arrby, arrby3, n6 + 1).mod(((BigInteger)object2).subtract(THREE)).add(TWO);
                object = ((BigInteger)object).add(BigInteger.valueOf(n7));
                n7 = 0;
                if (((BigInteger)object2).gcd((bigInteger3 = bigInteger3.modPow((BigInteger)object, (BigInteger)object2)).subtract(ONE)).equals(ONE) && bigInteger3.modPow(bigInteger, (BigInteger)object2).equals(ONE)) {
                    return new STOutput((BigInteger)object2, arrby3, n2);
                }
            } else {
                Primes.inc(arrby3, n6 + 1);
            }
            if (n2 >= n * 4 + n5) break;
            n7 += 2;
            object2 = ((BigInteger)object2).add(bigInteger2);
        } while (true);
        throw new IllegalStateException("Too many iterations in Shawe-Taylor Random_Prime Routine");
    }

    private static void inc(byte[] arrby, int n) {
        int n2 = arrby.length;
        while (n > 0 && --n2 >= 0) {
            arrby[n2] = (byte)(n += arrby[n2] & 255);
            n >>>= 8;
        }
    }

    public static boolean isMRProbablePrime(BigInteger bigInteger, SecureRandom secureRandom, int n) {
        Primes.checkCandidate(bigInteger, "candidate");
        if (secureRandom != null) {
            if (n >= 1) {
                if (bigInteger.bitLength() == 2) {
                    return true;
                }
                if (!bigInteger.testBit(0)) {
                    return false;
                }
                BigInteger bigInteger2 = bigInteger.subtract(ONE);
                BigInteger bigInteger3 = bigInteger.subtract(TWO);
                int n2 = bigInteger2.getLowestSetBit();
                BigInteger bigInteger4 = bigInteger2.shiftRight(n2);
                for (int i = 0; i < n; ++i) {
                    if (Primes.implMRProbablePrimeToBase(bigInteger, bigInteger2, bigInteger4, n2, BigIntegers.createRandomInRange(TWO, bigInteger3, secureRandom))) continue;
                    return false;
                }
                return true;
            }
            throw new IllegalArgumentException("'iterations' must be > 0");
        }
        throw new IllegalArgumentException("'random' cannot be null");
    }

    public static boolean isMRProbablePrimeToBase(BigInteger bigInteger, BigInteger bigInteger2) {
        Primes.checkCandidate(bigInteger, "candidate");
        Primes.checkCandidate(bigInteger2, "base");
        if (bigInteger2.compareTo(bigInteger.subtract(ONE)) < 0) {
            if (bigInteger.bitLength() == 2) {
                return true;
            }
            BigInteger bigInteger3 = bigInteger.subtract(ONE);
            int n = bigInteger3.getLowestSetBit();
            return Primes.implMRProbablePrimeToBase(bigInteger, bigInteger3, bigInteger3.shiftRight(n), n, bigInteger2);
        }
        throw new IllegalArgumentException("'base' must be < ('candidate' - 1)");
    }

    private static boolean isPrime32(long l) {
        if (l >>> 32 == 0L) {
            boolean bl = false;
            boolean bl2 = false;
            if (l <= 5L) {
                if (l == 2L || l == 3L || l == 5L) {
                    bl2 = true;
                }
                return bl2;
            }
            if ((1L & l) != 0L && l % 3L != 0L && l % 5L != 0L) {
                long[] arrl;
                long[] arrl2 = arrl = new long[8];
                arrl2[0] = 1L;
                arrl2[1] = 7L;
                arrl2[2] = 11L;
                arrl2[3] = 13L;
                arrl2[4] = 17L;
                arrl2[5] = 19L;
                arrl2[6] = 23L;
                arrl2[7] = 29L;
                long l2 = 0L;
                int n = 1;
                do {
                    if (n < arrl.length) {
                        if (l % (arrl[n] + l2) == 0L) {
                            bl2 = bl;
                            if (l < 30L) {
                                bl2 = true;
                            }
                            return bl2;
                        }
                        ++n;
                        continue;
                    }
                    if ((l2 += 30L) * l2 >= l) {
                        return true;
                    }
                    n = 0;
                } while (true);
            }
            return false;
        }
        throw new IllegalArgumentException("Size limit exceeded");
    }

    public static class MROutput {
        private BigInteger factor;
        private boolean provablyComposite;

        private MROutput(boolean bl, BigInteger bigInteger) {
            this.provablyComposite = bl;
            this.factor = bigInteger;
        }

        private static MROutput probablyPrime() {
            return new MROutput(false, null);
        }

        private static MROutput provablyCompositeNotPrimePower() {
            return new MROutput(true, null);
        }

        private static MROutput provablyCompositeWithFactor(BigInteger bigInteger) {
            return new MROutput(true, bigInteger);
        }

        public BigInteger getFactor() {
            return this.factor;
        }

        public boolean isNotPrimePower() {
            boolean bl = this.provablyComposite && this.factor == null;
            return bl;
        }

        public boolean isProvablyComposite() {
            return this.provablyComposite;
        }
    }

    public static class STOutput {
        private BigInteger prime;
        private int primeGenCounter;
        private byte[] primeSeed;

        private STOutput(BigInteger bigInteger, byte[] arrby, int n) {
            this.prime = bigInteger;
            this.primeSeed = arrby;
            this.primeGenCounter = n;
        }

        public BigInteger getPrime() {
            return this.prime;
        }

        public int getPrimeGenCounter() {
            return this.primeGenCounter;
        }

        public byte[] getPrimeSeed() {
            return this.primeSeed;
        }
    }

}

