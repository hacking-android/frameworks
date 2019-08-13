/*
 * Decompiled with CFR 0.145.
 */
package java.math;

import java.math.BigInteger;

class BitLevel {
    private BitLevel() {
    }

    static int bitCount(BigInteger bigInteger) {
        int n;
        bigInteger.prepareJavaRepresentation();
        int n2 = 0;
        if (bigInteger.sign == 0) {
            return 0;
        }
        int n3 = bigInteger.getFirstNonzeroDigit();
        if (bigInteger.sign > 0) {
            do {
                n = n2;
                if (n3 < bigInteger.numberLength) {
                    n2 += Integer.bitCount(bigInteger.digits[n3]);
                    ++n3;
                    continue;
                }
                break;
            } while (true);
        } else {
            n2 = 0 + Integer.bitCount(-bigInteger.digits[n3]);
            while (++n3 < bigInteger.numberLength) {
                n2 += Integer.bitCount(bigInteger.digits[n3]);
            }
            n = (bigInteger.numberLength << 5) - n2;
        }
        return n;
    }

    static int bitLength(BigInteger bigInteger) {
        int n;
        bigInteger.prepareJavaRepresentation();
        if (bigInteger.sign == 0) {
            return 0;
        }
        int n2 = bigInteger.numberLength;
        int n3 = n = bigInteger.digits[bigInteger.numberLength - 1];
        if (bigInteger.sign < 0) {
            n3 = n;
            if (bigInteger.getFirstNonzeroDigit() == bigInteger.numberLength - 1) {
                n3 = n - 1;
            }
        }
        return (n2 << 5) - Integer.numberOfLeadingZeros(n3);
    }

    static BigInteger flipBit(BigInteger bigInteger, int n) {
        bigInteger.prepareJavaRepresentation();
        int n2 = bigInteger.sign == 0 ? 1 : bigInteger.sign;
        int n3 = n >> 5;
        int n4 = Math.max(n3 + 1, bigInteger.numberLength) + 1;
        int[] arrn = new int[n4];
        n = 1 << (n & 31);
        System.arraycopy(bigInteger.digits, 0, arrn, 0, bigInteger.numberLength);
        if (bigInteger.sign < 0) {
            if (n3 >= bigInteger.numberLength) {
                arrn[n3] = n;
            } else {
                int n5 = bigInteger.getFirstNonzeroDigit();
                if (n3 > n5) {
                    arrn[n3] = arrn[n3] ^ n;
                } else if (n3 < n5) {
                    arrn[n3] = -n;
                    for (n = n3 + 1; n < n5; ++n) {
                        arrn[n] = -1;
                    }
                    n5 = arrn[n];
                    arrn[n] = n5 - 1;
                    arrn[n] = n5;
                } else {
                    arrn[n3] = -(-arrn[n3] ^ n);
                    if (arrn[n3] == 0) {
                        n = n3 + 1;
                        while (arrn[n] == -1) {
                            arrn[n] = 0;
                            ++n;
                        }
                        arrn[n] = arrn[n] + 1;
                    }
                }
            }
        } else {
            arrn[n3] = arrn[n3] ^ n;
        }
        return new BigInteger(n2, n4, arrn);
    }

    static boolean nonZeroDroppedBits(int n, int[] arrn) {
        int n2;
        int n3 = n >> 5;
        for (n2 = 0; n2 < n3 && arrn[n2] == 0; ++n2) {
        }
        boolean bl = n2 != n3 || arrn[n2] << 32 - (n & 31) != 0;
        return bl;
    }

    static BigInteger shiftLeftOneBit(BigInteger bigInteger) {
        bigInteger.prepareJavaRepresentation();
        int n = bigInteger.numberLength;
        int n2 = n + 1;
        int[] arrn = new int[n2];
        BitLevel.shiftLeftOneBit(arrn, bigInteger.digits, n);
        return new BigInteger(bigInteger.sign, n2, arrn);
    }

    static void shiftLeftOneBit(int[] arrn, int[] arrn2, int n) {
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            int n3 = arrn2[i];
            arrn[i] = n3 << 1 | n2;
            n2 = n3 >>> 31;
        }
        if (n2 != 0) {
            arrn[n] = n2;
        }
    }

    static BigInteger shiftRight(BigInteger bigInteger, int n) {
        int[] arrn;
        block7 : {
            int n2;
            int n3;
            block8 : {
                bigInteger.prepareJavaRepresentation();
                int n4 = n >> 5;
                int n5 = n & 31;
                if (n4 >= bigInteger.numberLength) {
                    bigInteger = bigInteger.sign < 0 ? BigInteger.MINUS_ONE : BigInteger.ZERO;
                    return bigInteger;
                }
                n2 = bigInteger.numberLength - n4;
                arrn = new int[n2 + 1];
                BitLevel.shiftRight(arrn, n2, bigInteger.digits, n4, n5);
                n = n2;
                if (bigInteger.sign >= 0) break block7;
                for (n3 = 0; n3 < n4 && bigInteger.digits[n3] == 0; ++n3) {
                }
                if (n3 < n4) break block8;
                n = n2;
                if (n5 <= 0) break block7;
                n = n2;
                if (bigInteger.digits[n3] << 32 - n5 == 0) break block7;
            }
            for (n3 = 0; n3 < n2 && arrn[n3] == -1; ++n3) {
                arrn[n3] = 0;
            }
            n = n2;
            if (n3 == n2) {
                n = n2 + 1;
            }
            arrn[n3] = arrn[n3] + 1;
        }
        return new BigInteger(bigInteger.sign, n, arrn);
    }

    static boolean shiftRight(int[] arrn, int n, int[] arrn2, int n2, int n3) {
        int n4;
        int n5;
        int n6 = 1;
        int n7 = 0;
        do {
            n5 = 0;
            n4 = 0;
            if (n7 >= n2) break;
            n5 = n4;
            if (arrn2[n7] == 0) {
                n5 = 1;
            }
            n6 &= n5;
            ++n7;
        } while (true);
        if (n3 == 0) {
            System.arraycopy(arrn2, n2, arrn, 0, n);
        } else {
            n4 = 32 - n3;
            if (arrn2[n7] << n4 == 0) {
                n5 = 1;
            }
            n6 &= n5;
            for (n7 = 0; n7 < n - 1; ++n7) {
                arrn[n7] = arrn2[n7 + n2] >>> n3 | arrn2[n7 + n2 + 1] << n4;
            }
            arrn[n7] = arrn2[n7 + n2] >>> n3;
        }
        return n6 != 0;
    }

    static boolean testBit(BigInteger bigInteger, int n) {
        bigInteger.prepareJavaRepresentation();
        int n2 = bigInteger.digits[n >> 5];
        boolean bl = true;
        if ((n2 & 1 << (n & 31)) == 0) {
            bl = false;
        }
        return bl;
    }
}

