/*
 * Decompiled with CFR 0.145.
 */
package java.math;

import java.math.BigInteger;

class Logical {
    private Logical() {
    }

    static BigInteger and(BigInteger bigInteger, BigInteger bigInteger2) {
        if (bigInteger2.sign != 0 && bigInteger.sign != 0) {
            if (bigInteger2.equals(BigInteger.MINUS_ONE)) {
                return bigInteger;
            }
            if (bigInteger.equals(BigInteger.MINUS_ONE)) {
                return bigInteger2;
            }
            if (bigInteger.sign > 0) {
                if (bigInteger2.sign > 0) {
                    return Logical.andPositive(bigInteger, bigInteger2);
                }
                return Logical.andDiffSigns(bigInteger, bigInteger2);
            }
            if (bigInteger2.sign > 0) {
                return Logical.andDiffSigns(bigInteger2, bigInteger);
            }
            if (bigInteger.numberLength > bigInteger2.numberLength) {
                return Logical.andNegative(bigInteger, bigInteger2);
            }
            return Logical.andNegative(bigInteger2, bigInteger);
        }
        return BigInteger.ZERO;
    }

    static BigInteger andDiffSigns(BigInteger bigInteger, BigInteger bigInteger2) {
        int n;
        int n2 = bigInteger.getFirstNonzeroDigit();
        int n3 = bigInteger2.getFirstNonzeroDigit();
        if (n3 >= bigInteger.numberLength) {
            return BigInteger.ZERO;
        }
        int n4 = bigInteger.numberLength;
        int[] arrn = new int[n4];
        n2 = n = Math.max(n2, n3);
        if (n == n3) {
            arrn[n] = -bigInteger2.digits[n] & bigInteger.digits[n];
            n2 = n + 1;
        }
        n = Math.min(bigInteger2.numberLength, bigInteger.numberLength);
        while (n2 < n) {
            arrn[n2] = bigInteger2.digits[n2] & bigInteger.digits[n2];
            ++n2;
        }
        if (n2 >= bigInteger2.numberLength) {
            while (n2 < bigInteger.numberLength) {
                arrn[n2] = bigInteger.digits[n2];
                ++n2;
            }
        }
        return new BigInteger(1, n4, arrn);
    }

    static BigInteger andNegative(BigInteger arrn, BigInteger bigInteger) {
        int n = arrn.getFirstNonzeroDigit();
        int n2 = bigInteger.getFirstNonzeroDigit();
        if (n >= bigInteger.numberLength) {
            return arrn;
        }
        int n3 = Math.max(n2, n);
        n = n2 > n ? -bigInteger.digits[n3] & arrn.digits[n3] : (n2 < n ? bigInteger.digits[n3] & -arrn.digits[n3] : -bigInteger.digits[n3] & -arrn.digits[n3]);
        n2 = n3;
        int n4 = n;
        if (n == 0) {
            while (++n3 < bigInteger.numberLength) {
                n = n2 = (n4 = arrn.digits[n3] | bigInteger.digits[n3]);
                if (n4 != 0) break;
                n = n2;
            }
            n2 = n3;
            n4 = n;
            if (n == 0) {
                while (n3 < arrn.numberLength) {
                    n = n2 = (n4 = arrn.digits[n3]);
                    if (n4 != 0) break;
                    ++n3;
                    n = n2;
                }
                n2 = n3;
                n4 = n;
                if (n == 0) {
                    n = arrn.numberLength + 1;
                    arrn = new int[n];
                    arrn[n - 1] = 1;
                    return new BigInteger(-1, n, arrn);
                }
            }
        }
        n3 = arrn.numberLength;
        int[] arrn2 = new int[n3];
        arrn2[n2] = -n4;
        n = n2 + 1;
        do {
            if (n >= bigInteger.numberLength) break;
            arrn2[n] = arrn.digits[n] | bigInteger.digits[n];
        } while (true);
        for (n2 = ++n; n2 < arrn.numberLength; ++n2) {
            arrn2[n2] = arrn.digits[n2];
        }
        return new BigInteger(-1, n3, arrn2);
    }

    static BigInteger andNot(BigInteger bigInteger, BigInteger bigInteger2) {
        if (bigInteger2.sign == 0) {
            return bigInteger;
        }
        if (bigInteger.sign == 0) {
            return BigInteger.ZERO;
        }
        if (bigInteger.equals(BigInteger.MINUS_ONE)) {
            return bigInteger2.not();
        }
        if (bigInteger2.equals(BigInteger.MINUS_ONE)) {
            return BigInteger.ZERO;
        }
        if (bigInteger.sign > 0) {
            if (bigInteger2.sign > 0) {
                return Logical.andNotPositive(bigInteger, bigInteger2);
            }
            return Logical.andNotPositiveNegative(bigInteger, bigInteger2);
        }
        if (bigInteger2.sign > 0) {
            return Logical.andNotNegativePositive(bigInteger, bigInteger2);
        }
        return Logical.andNotNegative(bigInteger, bigInteger2);
    }

    static BigInteger andNotNegative(BigInteger bigInteger, BigInteger bigInteger2) {
        int n = bigInteger.getFirstNonzeroDigit();
        int n2 = bigInteger2.getFirstNonzeroDigit();
        if (n >= bigInteger2.numberLength) {
            return BigInteger.ZERO;
        }
        int n3 = bigInteger2.numberLength;
        int[] arrn = new int[n3];
        int n4 = n;
        if (n < n2) {
            arrn[n4] = -bigInteger.digits[n4];
            n = Math.min(bigInteger.numberLength, n2);
            while (++n4 < n) {
                arrn[n4] = bigInteger.digits[n4];
            }
            if (n4 == bigInteger.numberLength) {
                while (n4 < n2) {
                    arrn[n4] = -1;
                    ++n4;
                }
                arrn[n4] = bigInteger2.digits[n4] - 1;
            } else {
                arrn[n4] = bigInteger.digits[n4] & bigInteger2.digits[n4] - 1;
            }
        } else {
            arrn[n4] = n2 < n ? -bigInteger.digits[n4] & bigInteger2.digits[n4] : -bigInteger.digits[n4] & bigInteger2.digits[n4] - 1;
        }
        n2 = Math.min(bigInteger.numberLength, bigInteger2.numberLength);
        ++n4;
        do {
            if (n4 >= n2) break;
            arrn[n4] = bigInteger.digits[n4] & bigInteger2.digits[n4];
        } while (true);
        for (n = ++n4; n < bigInteger2.numberLength; ++n) {
            arrn[n] = bigInteger2.digits[n];
        }
        return new BigInteger(1, n3, arrn);
    }

    static BigInteger andNotNegativePositive(BigInteger arrn, BigInteger bigInteger) {
        int[] arrn2;
        int n;
        int n2 = arrn.getFirstNonzeroDigit();
        int n3 = bigInteger.getFirstNonzeroDigit();
        if (n2 >= bigInteger.numberLength) {
            return arrn;
        }
        int n4 = Math.max(arrn.numberLength, bigInteger.numberLength);
        int n5 = n2;
        if (n3 > n2) {
            int[] arrn3 = new int[n4];
            n = Math.min(arrn.numberLength, n3);
            for (n2 = n5; n2 < n; ++n2) {
                arrn3[n2] = arrn.digits[n2];
            }
            n5 = n2;
            arrn2 = arrn3;
            if (n2 == arrn.numberLength) {
                n2 = n3;
                do {
                    n5 = ++n2;
                    arrn2 = arrn3;
                    if (n2 < bigInteger.numberLength) {
                        arrn3[n2] = bigInteger.digits[n2];
                        continue;
                    }
                    break;
                } while (true);
            }
        } else {
            n2 = -arrn.digits[n5] & bigInteger.digits[n5];
            n3 = n5;
            n = n2;
            if (n2 == 0) {
                int n6 = Math.min(bigInteger.numberLength, arrn.numberLength);
                n3 = n2;
                do {
                    n2 = n5 + 1;
                    n5 = n3;
                    if (n2 >= n6) break;
                    n5 = n3 = (n = arrn.digits[n2] | bigInteger.digits[n2]);
                    if (n != 0) break;
                    n5 = n2;
                } while (true);
                n3 = n2;
                n = n5;
                if (n5 == 0) {
                    n = n2;
                    do {
                        n2 = n;
                        if (n >= bigInteger.numberLength) break;
                        n3 = n6 = bigInteger.digits[n];
                        n2 = n++;
                        n5 = n3;
                        if (n6 != 0) break;
                        n5 = n3;
                    } while (true);
                    while (n2 < arrn.numberLength) {
                        n5 = n3 = (n = arrn.digits[n2]);
                        if (n != 0) break;
                        ++n2;
                        n5 = n3;
                    }
                    n3 = n2;
                    n = n5;
                    if (n5 == 0) {
                        n5 = n4 + 1;
                        arrn = new int[n5];
                        arrn[n5 - 1] = 1;
                        return new BigInteger(-1, n5, arrn);
                    }
                }
            }
            arrn2 = new int[n4];
            arrn2[n3] = -n;
            n5 = n3 + 1;
        }
        n3 = Math.min(bigInteger.numberLength, arrn.numberLength);
        n2 = n5;
        do {
            n5 = ++n2;
            if (n2 >= n3) break;
            arrn2[n2] = arrn.digits[n2] | bigInteger.digits[n2];
        } while (true);
        do {
            if (n5 >= arrn.numberLength) break;
            arrn2[n5] = arrn.digits[n5];
        } while (true);
        for (n2 = ++n5; n2 < bigInteger.numberLength; ++n2) {
            arrn2[n2] = bigInteger.digits[n2];
        }
        return new BigInteger(-1, n4, arrn2);
    }

    static BigInteger andNotPositive(BigInteger bigInteger, BigInteger bigInteger2) {
        int[] arrn = new int[bigInteger.numberLength];
        int n = Math.min(bigInteger.numberLength, bigInteger2.numberLength);
        int n2 = bigInteger.getFirstNonzeroDigit();
        do {
            if (n2 >= n) break;
            arrn[n2] = bigInteger.digits[n2] & bigInteger2.digits[n2];
        } while (true);
        for (int i = ++n2; i < bigInteger.numberLength; ++i) {
            arrn[i] = bigInteger.digits[i];
        }
        return new BigInteger(1, bigInteger.numberLength, arrn);
    }

    static BigInteger andNotPositiveNegative(BigInteger bigInteger, BigInteger bigInteger2) {
        int n;
        int n2 = bigInteger2.getFirstNonzeroDigit();
        if (n2 >= bigInteger.numberLength) {
            return bigInteger;
        }
        int n3 = Math.min(bigInteger.numberLength, bigInteger2.numberLength);
        int[] arrn = new int[n3];
        for (n = bigInteger.getFirstNonzeroDigit(); n < n2; ++n) {
            arrn[n] = bigInteger.digits[n];
        }
        int n4 = n;
        if (n == n2) {
            arrn[n] = bigInteger.digits[n] & bigInteger2.digits[n] - 1;
            n4 = n + 1;
        }
        while (n4 < n3) {
            arrn[n4] = bigInteger.digits[n4] & bigInteger2.digits[n4];
            ++n4;
        }
        return new BigInteger(1, n3, arrn);
    }

    static BigInteger andPositive(BigInteger bigInteger, BigInteger bigInteger2) {
        int n = Math.min(bigInteger.numberLength, bigInteger2.numberLength);
        int n2 = Math.max(bigInteger.getFirstNonzeroDigit(), bigInteger2.getFirstNonzeroDigit());
        if (n2 >= n) {
            return BigInteger.ZERO;
        }
        int[] arrn = new int[n];
        while (n2 < n) {
            arrn[n2] = bigInteger.digits[n2] & bigInteger2.digits[n2];
            ++n2;
        }
        return new BigInteger(1, n, arrn);
    }

    static BigInteger not(BigInteger bigInteger) {
        int n;
        int n2;
        if (bigInteger.sign == 0) {
            return BigInteger.MINUS_ONE;
        }
        if (bigInteger.equals(BigInteger.MINUS_ONE)) {
            return BigInteger.ZERO;
        }
        int[] arrn = new int[bigInteger.numberLength + 1];
        if (bigInteger.sign > 0) {
            if (bigInteger.digits[bigInteger.numberLength - 1] != -1) {
                n = 0;
                do {
                    n2 = ++n;
                    if (bigInteger.digits[n] == -1) {
                        continue;
                    }
                    break;
                } while (true);
            } else {
                for (n = 0; n < bigInteger.numberLength && bigInteger.digits[n] == -1; ++n) {
                }
                n2 = n;
                if (n == bigInteger.numberLength) {
                    arrn[n] = 1;
                    return new BigInteger(-bigInteger.sign, n + 1, arrn);
                }
            }
        } else {
            n = 0;
            do {
                n2 = ++n;
                if (bigInteger.digits[n] != 0) break;
                arrn[n] = -1;
            } while (true);
        }
        arrn[n2] = bigInteger.digits[n2] + bigInteger.sign;
        ++n2;
        while (n2 < bigInteger.numberLength) {
            arrn[n2] = bigInteger.digits[n2];
            ++n2;
        }
        return new BigInteger(-bigInteger.sign, n2, arrn);
    }

    static BigInteger or(BigInteger bigInteger, BigInteger bigInteger2) {
        if (!bigInteger2.equals(BigInteger.MINUS_ONE) && !bigInteger.equals(BigInteger.MINUS_ONE)) {
            if (bigInteger2.sign == 0) {
                return bigInteger;
            }
            if (bigInteger.sign == 0) {
                return bigInteger2;
            }
            if (bigInteger.sign > 0) {
                if (bigInteger2.sign > 0) {
                    if (bigInteger.numberLength > bigInteger2.numberLength) {
                        return Logical.orPositive(bigInteger, bigInteger2);
                    }
                    return Logical.orPositive(bigInteger2, bigInteger);
                }
                return Logical.orDiffSigns(bigInteger, bigInteger2);
            }
            if (bigInteger2.sign > 0) {
                return Logical.orDiffSigns(bigInteger2, bigInteger);
            }
            if (bigInteger2.getFirstNonzeroDigit() > bigInteger.getFirstNonzeroDigit()) {
                return Logical.orNegative(bigInteger2, bigInteger);
            }
            return Logical.orNegative(bigInteger, bigInteger2);
        }
        return BigInteger.MINUS_ONE;
    }

    static BigInteger orDiffSigns(BigInteger bigInteger, BigInteger bigInteger2) {
        int n;
        int n2;
        int n3 = bigInteger2.getFirstNonzeroDigit();
        int n4 = bigInteger.getFirstNonzeroDigit();
        if (n4 >= bigInteger2.numberLength) {
            return bigInteger2;
        }
        int n5 = bigInteger2.numberLength;
        int[] arrn = new int[n5];
        if (n3 < n4) {
            n2 = n3;
            do {
                n = ++n2;
                if (n2 < n4) {
                    arrn[n2] = bigInteger2.digits[n2];
                    continue;
                }
                break;
            } while (true);
        } else if (n4 < n3) {
            n = n4;
            arrn[n] = -bigInteger.digits[n];
            n2 = Math.min(bigInteger.numberLength, n3);
            while (++n < n2) {
                arrn[n] = bigInteger.digits[n];
            }
            if (n != bigInteger.numberLength) {
                arrn[n] = -bigInteger2.digits[n] | bigInteger.digits[n];
            } else {
                for (n2 = n; n2 < n3; ++n2) {
                    arrn[n2] = -1;
                }
                arrn[n2] = bigInteger2.digits[n2] - 1;
                n = n2;
            }
            ++n;
        } else {
            arrn[n4] = -(-bigInteger2.digits[n4] | bigInteger.digits[n4]);
            n = n4 + 1;
        }
        n3 = Math.min(bigInteger2.numberLength, bigInteger.numberLength);
        do {
            if (n >= n3) break;
            arrn[n] = bigInteger2.digits[n] & bigInteger.digits[n];
        } while (true);
        for (n2 = ++n; n2 < bigInteger2.numberLength; ++n2) {
            arrn[n2] = bigInteger2.digits[n2];
        }
        return new BigInteger(-1, n5, arrn);
    }

    static BigInteger orNegative(BigInteger bigInteger, BigInteger bigInteger2) {
        int n;
        int n2 = bigInteger.getFirstNonzeroDigit();
        if (n2 >= bigInteger2.numberLength) {
            return bigInteger2;
        }
        if (n >= bigInteger.numberLength) {
            return bigInteger;
        }
        int n3 = Math.min(bigInteger.numberLength, bigInteger2.numberLength);
        int[] arrn = new int[n3];
        if (n == n2) {
            arrn[n2] = -(-bigInteger.digits[n2] | -bigInteger2.digits[n2]);
            n = n2;
        } else {
            for (n = bigInteger2.getFirstNonzeroDigit(); n < n2; ++n) {
                arrn[n] = bigInteger2.digits[n];
            }
            arrn[n] = bigInteger2.digits[n] & bigInteger.digits[n] - 1;
        }
        while (++n < n3) {
            arrn[n] = bigInteger.digits[n] & bigInteger2.digits[n];
        }
        return new BigInteger(-1, n3, arrn);
    }

    static BigInteger orPositive(BigInteger bigInteger, BigInteger bigInteger2) {
        int n = bigInteger.numberLength;
        int[] arrn = new int[n];
        int n2 = 0;
        do {
            if (n2 >= bigInteger2.numberLength) break;
            arrn[n2] = bigInteger.digits[n2] | bigInteger2.digits[n2];
        } while (true);
        for (int i = ++n2; i < n; ++i) {
            arrn[i] = bigInteger.digits[i];
        }
        return new BigInteger(1, n, arrn);
    }

    static BigInteger xor(BigInteger bigInteger, BigInteger bigInteger2) {
        if (bigInteger2.sign == 0) {
            return bigInteger;
        }
        if (bigInteger.sign == 0) {
            return bigInteger2;
        }
        if (bigInteger2.equals(BigInteger.MINUS_ONE)) {
            return bigInteger.not();
        }
        if (bigInteger.equals(BigInteger.MINUS_ONE)) {
            return bigInteger2.not();
        }
        if (bigInteger.sign > 0) {
            if (bigInteger2.sign > 0) {
                if (bigInteger.numberLength > bigInteger2.numberLength) {
                    return Logical.xorPositive(bigInteger, bigInteger2);
                }
                return Logical.xorPositive(bigInteger2, bigInteger);
            }
            return Logical.xorDiffSigns(bigInteger, bigInteger2);
        }
        if (bigInteger2.sign > 0) {
            return Logical.xorDiffSigns(bigInteger2, bigInteger);
        }
        if (bigInteger2.getFirstNonzeroDigit() > bigInteger.getFirstNonzeroDigit()) {
            return Logical.xorNegative(bigInteger2, bigInteger);
        }
        return Logical.xorNegative(bigInteger, bigInteger2);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    static BigInteger xorDiffSigns(BigInteger var0, BigInteger var1_1) {
        block20 : {
            block21 : {
                var2_2 = Math.max(var1_1.numberLength, var0.numberLength);
                var3_3 = var1_1.getFirstNonzeroDigit();
                if (var3_3 >= (var4_4 = var0.getFirstNonzeroDigit())) break block21;
                var5_5 = new int[var2_2];
                var5_5[var3_3] = var1_1.digits[var3_3];
                var4_4 = Math.min(var1_1.numberLength, var4_4);
                for (var6_6 = var3_3 + 1; var6_6 < var4_4; ++var6_6) {
                    var5_5[var6_6] = var1_1.digits[var6_6];
                }
                var7_7 = var5_5;
                var4_4 = var6_6;
                if (var6_6 == var1_1.numberLength) {
                    do {
                        var7_7 = var5_5;
                        var4_4 = ++var6_6;
                        if (var6_6 < var0.numberLength) {
                            var5_5[var6_6] = var0.digits[var6_6];
                            continue;
                        }
                        break block20;
                        break;
                    } while (true);
                }
                break block20;
            }
            if (var4_4 >= var3_3) ** GOTO lbl49
            var5_5 = new int[var2_2];
            var5_5[var4_4] = -var0.digits[var4_4];
            var6_6 = Math.min(var0.numberLength, var3_3);
            ++var4_4;
            while (var4_4 < var6_6) {
                var5_5[var4_4] = var0.digits[var4_4];
                ++var4_4;
            }
            var8_8 = var4_4;
            if (var4_4 == var3_3) {
                var5_5[var4_4] = var0.digits[var4_4] ^ -var1_1.digits[var4_4];
                ++var4_4;
                var7_7 = var5_5;
            } else {
                do {
                    var6_6 = ++var8_8;
                    if (var8_8 >= var3_3) break;
                    var5_5[var8_8] = -1;
                } while (true);
                do {
                    var7_7 = var5_5;
                    var4_4 = ++var6_6;
                    if (var6_6 < var1_1.numberLength) {
                        var5_5[var6_6] = var1_1.digits[var6_6];
                        continue;
                    }
                    break block20;
                    break;
                } while (true);
lbl49: // 1 sources:
                var4_4 = var3_3;
                var6_6 = var0.digits[var4_4] ^ -var1_1.digits[var4_4];
                var3_3 = var4_4;
                var8_8 = var6_6;
                if (var6_6 == 0) {
                    var9_9 = Math.min(var0.numberLength, var1_1.numberLength);
                    var8_8 = var6_6;
                    do {
                        var6_6 = var4_4 + 1;
                        var4_4 = var8_8;
                        if (var6_6 >= var9_9) break;
                        var4_4 = var8_8 = (var3_3 = var0.digits[var6_6] ^ var1_1.digits[var6_6]);
                        if (var3_3 != 0) break;
                        var4_4 = var6_6;
                    } while (true);
                    var3_3 = var6_6;
                    var8_8 = var4_4;
                    if (var4_4 == 0) {
                        var8_8 = var6_6;
                        do {
                            var6_6 = var8_8;
                            if (var8_8 >= var0.numberLength) break;
                            var3_3 = var9_9 = var0.digits[var8_8];
                            var6_6 = var8_8++;
                            var4_4 = var3_3;
                            if (var9_9 != 0) break;
                            var4_4 = var3_3;
                        } while (true);
                        while (var6_6 < var1_1.numberLength) {
                            var4_4 = var8_8 = (var3_3 = var1_1.digits[var6_6]);
                            if (var3_3 != 0) break;
                            ++var6_6;
                            var4_4 = var8_8;
                        }
                        var3_3 = var6_6;
                        var8_8 = var4_4;
                        if (var4_4 == 0) {
                            var4_4 = var2_2 + 1;
                            var0 = new int[var4_4];
                            var0[var4_4 - 1] = 1;
                            return new BigInteger(-1, var4_4, var0);
                        }
                    }
                }
                var7_7 = new int[var2_2];
                var7_7[var3_3] = -var8_8;
                var4_4 = 1 + var3_3;
            }
        }
        var8_8 = Math.min(var1_1.numberLength, var0.numberLength);
        var6_6 = var4_4;
        do {
            var4_4 = ++var6_6;
            if (var6_6 >= var8_8) break;
            var7_7[var6_6] = var1_1.digits[var6_6] ^ var0.digits[var6_6];
        } while (true);
        do {
            var6_6 = ++var4_4;
            if (var4_4 >= var0.numberLength) {
                while (var6_6 < var1_1.numberLength) {
                    var7_7[var6_6] = var1_1.digits[var6_6];
                    ++var6_6;
                }
                return new BigInteger(-1, var2_2, var7_7);
            }
            var7_7[var4_4] = var0.digits[var4_4];
        } while (true);
    }

    static BigInteger xorNegative(BigInteger bigInteger, BigInteger bigInteger2) {
        int n;
        int n2 = Math.max(bigInteger.numberLength, bigInteger2.numberLength);
        int[] arrn = new int[n2];
        int n3 = bigInteger.getFirstNonzeroDigit();
        int n4 = n = bigInteger2.getFirstNonzeroDigit();
        if (n3 == n) {
            arrn[n4] = -bigInteger.digits[n4] ^ -bigInteger2.digits[n4];
        } else {
            arrn[n4] = -bigInteger2.digits[n4];
            n = Math.min(bigInteger2.numberLength, n3);
            while (++n4 < n) {
                arrn[n4] = bigInteger2.digits[n4];
            }
            if (n4 == bigInteger2.numberLength) {
                while (n4 < n3) {
                    arrn[n4] = -1;
                    ++n4;
                }
                arrn[n4] = bigInteger.digits[n4] - 1;
            } else {
                arrn[n4] = -bigInteger.digits[n4] ^ bigInteger2.digits[n4];
            }
        }
        n3 = Math.min(bigInteger.numberLength, bigInteger2.numberLength);
        n = n4 + 1;
        do {
            n4 = ++n;
            if (n >= n3) break;
            arrn[n] = bigInteger.digits[n] ^ bigInteger2.digits[n];
        } while (true);
        do {
            if (n4 >= bigInteger.numberLength) break;
            arrn[n4] = bigInteger.digits[n4];
        } while (true);
        for (n = ++n4; n < bigInteger2.numberLength; ++n) {
            arrn[n] = bigInteger2.digits[n];
        }
        return new BigInteger(1, n2, arrn);
    }

    static BigInteger xorPositive(BigInteger bigInteger, BigInteger bigInteger2) {
        int n = bigInteger.numberLength;
        int[] arrn = new int[n];
        int n2 = Math.min(bigInteger.getFirstNonzeroDigit(), bigInteger2.getFirstNonzeroDigit());
        do {
            if (n2 >= bigInteger2.numberLength) break;
            arrn[n2] = bigInteger.digits[n2] ^ bigInteger2.digits[n2];
        } while (true);
        for (int i = ++n2; i < bigInteger.numberLength; ++i) {
            arrn[i] = bigInteger.digits[i];
        }
        return new BigInteger(1, n, arrn);
    }
}

