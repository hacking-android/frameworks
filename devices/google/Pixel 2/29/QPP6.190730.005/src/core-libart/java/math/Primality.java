/*
 * Decompiled with CFR 0.145.
 */
package java.math;

import java.math.BigInt;
import java.math.BigInteger;
import java.util.Arrays;

class Primality {
    private static final BigInteger[] BIprimes;
    private static final int[] primes;

    static {
        int[] arrn;
        primes = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691, 701, 709, 719, 727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797, 809, 811, 821, 823, 827, 829, 839, 853, 857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937, 941, 947, 953, 967, 971, 977, 983, 991, 997, 1009, 1013, 1019, 1021};
        BIprimes = new BigInteger[primes.length];
        for (int i = 0; i < (arrn = primes).length; ++i) {
            Primality.BIprimes[i] = BigInteger.valueOf(arrn[i]);
        }
    }

    private Primality() {
    }

    static BigInteger nextProbablePrime(BigInteger arrn) {
        int n;
        int n2;
        int[] arrn2 = new int[primes.length];
        boolean[] arrbl = new boolean[1024];
        int[] arrn3 = arrn.getBigInt();
        if (arrn3.bitLength() <= 10 && (n = (int)arrn3.longInt()) < (arrn = primes)[arrn.length - 1]) {
            int n3 = 0;
            while (n >= primes[n3]) {
                ++n3;
            }
            return BIprimes[n3];
        }
        arrn = arrn3.copy();
        BigInt bigInt = new BigInt();
        arrn.addPositiveInt(BigInt.remainderByPositiveInt((BigInt)arrn3, 2) + 1);
        for (n2 = 0; n2 < (arrn3 = primes).length; ++n2) {
            arrn2[n2] = BigInt.remainderByPositiveInt((BigInt)arrn, arrn3[n2]) - 1024;
        }
        do {
            Arrays.fill(arrbl, false);
            for (n = 0; n < (arrn3 = primes).length; ++n) {
                arrn2[n] = (arrn2[n] + 1024) % arrn3[n];
                for (n2 = arrn2[n] == 0 ? 0 : arrn3[n] - arrn2[n]; n2 < 1024; n2 += Primality.primes[n]) {
                    arrbl[n2] = true;
                }
            }
            for (n2 = 0; n2 < 1024; ++n2) {
                if (arrbl[n2]) continue;
                bigInt.putCopy((BigInt)arrn);
                bigInt.addPositiveInt(n2);
                if (!bigInt.isPrime(100)) continue;
                return new BigInteger(bigInt);
            }
            arrn.addPositiveInt(1024);
        } while (true);
    }
}

