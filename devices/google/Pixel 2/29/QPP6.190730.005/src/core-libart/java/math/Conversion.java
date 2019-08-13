/*
 * Decompiled with CFR 0.145.
 */
package java.math;

import java.io.Serializable;
import java.math.BigInteger;
import java.math.BitLevel;
import java.math.Division;

class Conversion {
    static final int[] bigRadices;
    static final int[] digitFitInInt;

    static {
        digitFitInInt = new int[]{-1, -1, 31, 19, 15, 13, 11, 11, 10, 9, 9, 8, 8, 8, 8, 7, 7, 7, 7, 7, 7, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 5};
        bigRadices = new int[]{Integer.MIN_VALUE, 1162261467, 1073741824, 1220703125, 362797056, 1977326743, 1073741824, 387420489, 1000000000, 214358881, 429981696, 815730721, 1475789056, 170859375, 268435456, 410338673, 612220032, 893871739, 1280000000, 1801088541, 113379904, 148035889, 191102976, 244140625, 308915776, 387420489, 481890304, 594823321, 729000000, 887503681, 1073741824, 1291467969, 1544804416, 1838265625, 60466176};
    }

    private Conversion() {
    }

    static double bigInteger2Double(BigInteger bigInteger) {
        block9 : {
            long l;
            long l2;
            long l3;
            block10 : {
                block11 : {
                    bigInteger.prepareJavaRepresentation();
                    if (bigInteger.numberLength < 2 || bigInteger.numberLength == 2 && bigInteger.digits[1] > 0) break block9;
                    int n = bigInteger.numberLength;
                    double d = Double.POSITIVE_INFINITY;
                    if (n > 32) {
                        if (bigInteger.sign <= 0) {
                            d = Double.NEGATIVE_INFINITY;
                        }
                        return d;
                    }
                    n = bigInteger.abs().bitLength();
                    l = n - 1;
                    l3 = bigInteger.abs().shiftRight(n -= 54).longValue() & 0x1FFFFFFFFFFFFFL;
                    if (l == 1023L) {
                        if (l3 == 0x1FFFFFFFFFFFFFL) {
                            if (bigInteger.sign <= 0) {
                                d = Double.NEGATIVE_INFINITY;
                            }
                            return d;
                        }
                        if (l3 == 9007199254740990L) {
                            d = bigInteger.sign > 0 ? Double.MAX_VALUE : -1.7976931348623157E308;
                            return d;
                        }
                    }
                    l2 = l3;
                    if ((l3 & 1L) != 1L) break block10;
                    if ((l3 & 2L) == 2L) break block11;
                    l2 = l3;
                    if (!BitLevel.nonZeroDroppedBits(n, bigInteger.digits)) break block10;
                }
                l2 = l3 + 2L;
            }
            l3 = bigInteger.sign < 0 ? Long.MIN_VALUE : 0L;
            return Double.longBitsToDouble(l3 | l + 1023L << 52 & 9218868437227405312L | l2 >> 1);
        }
        return bigInteger.longValue();
    }

    static String bigInteger2String(BigInteger arrn, int n) {
        arrn.prepareJavaRepresentation();
        int n2 = arrn.sign;
        int n3 = arrn.numberLength;
        int[] arrn2 = arrn.digits;
        if (n2 == 0) {
            return "0";
        }
        if (n3 == 1) {
            long l;
            long l2 = l = (long)arrn2[n3 - 1] & 0xFFFFFFFFL;
            if (n2 < 0) {
                l2 = -l;
            }
            return Long.toString(l2, n);
        }
        if (n != 10 && n >= 2 && n <= 36) {
            int n4;
            int n5;
            double d = Math.log(n) / Math.log(2.0);
            d = (double)arrn.abs().bitLength() / d;
            int n6 = n2 < 0 ? 1 : 0;
            int n7 = (int)(d + (double)n6) + 1;
            char[] arrc = new char[n7];
            if (n != 16) {
                arrn = new int[n3];
                System.arraycopy(arrn2, 0, arrn, 0, n3);
                int n8 = digitFitInInt[n];
                int n9 = bigRadices[n - 2];
                n6 = n7;
                n4 = n3;
                do {
                    n3 = n6;
                    int n10 = Division.divideArrayByInt(arrn, arrn, n4, n9);
                    n6 = n3;
                    do {
                        n5 = n6 - 1;
                        arrc[n5] = Character.forDigit(n10 % n, n);
                        n10 = n6 = n10 / n;
                        if (n6 == 0 || n5 == 0) break;
                        n6 = n5;
                    } while (true);
                    n6 = n5;
                    for (n10 = 0; n10 < n8 - n3 + n5 && n6 > 0; ++n10) {
                        arrc[--n6] = (char)48;
                    }
                    --n4;
                    while (n4 > 0 && arrn[n4] == 0) {
                        --n4;
                    }
                } while (++n4 != 1 || arrn[0] != 0);
                n = n6;
            } else {
                n4 = 0;
                n6 = n7;
                do {
                    n = n6;
                    if (n4 >= n3) break;
                    for (n = 0; n < 8 && n6 > 0; ++n) {
                        n5 = arrn2[n4];
                        arrc[--n6] = Character.forDigit(n5 >> (n << 2) & 15, 16);
                    }
                    ++n4;
                } while (true);
            }
            while (arrc[n] == '0') {
                ++n;
            }
            if (n2 == -1) {
                arrc[--n] = (char)45;
            }
            return new String(arrc, n, n7 - n);
        }
        return arrn.toString();
    }

    static long divideLongByBillion(long l) {
        long l2;
        if (l >= 0L) {
            long l3 = l / 1000000000L;
            l2 = l % 1000000000L;
            l = l3;
        } else {
            long l4 = l >>> 1;
            l2 = l4 / 500000000L;
            l4 = (l4 % 500000000L << 1) + (1L & l);
            l = l2;
            l2 = l4;
        }
        return l2 << 32 | 0xFFFFFFFFL & l;
    }

    static String toDecimalScaledString(long l, int n) {
        boolean bl;
        long l2;
        block24 : {
            StringBuilder stringBuilder;
            block25 : {
                l2 = l;
                bl = l2 < 0L;
                l = l2;
                if (bl) {
                    l = -l2;
                }
                if (l != 0L) break block24;
                switch (n) {
                    default: {
                        stringBuilder = new StringBuilder();
                        if (n >= 0) break;
                        stringBuilder.append("0E+");
                        break block25;
                    }
                    case 6: {
                        return "0.000000";
                    }
                    case 5: {
                        return "0.00000";
                    }
                    case 4: {
                        return "0.0000";
                    }
                    case 3: {
                        return "0.000";
                    }
                    case 2: {
                        return "0.00";
                    }
                    case 1: {
                        return "0.0";
                    }
                    case 0: {
                        return "0";
                    }
                }
                stringBuilder.append("0E");
            }
            String string = n == Integer.MIN_VALUE ? "2147483648" : Integer.toString(-n);
            stringBuilder.append(string);
            return stringBuilder.toString();
        }
        char[] arrc = new char[18 + 1];
        int n2 = 18;
        l2 = l;
        do {
            long l3 = l2 / 10L;
            arrc[--n2] = (char)(l2 - 10L * l3 + 48L);
            if (l3 == 0L) {
                l2 = (long)18 - (long)n2 - (long)n - 1L;
                if (n == 0) {
                    n = n2;
                    if (bl) {
                        n = n2 - 1;
                        arrc[n] = (char)45;
                    }
                    return new String(arrc, n, 18 - n);
                }
                if (n > 0 && l2 >= -6L) {
                    if (l2 >= 0L) {
                        int n3 = (int)l2 + n2;
                        for (n = 18 - 1; n >= n3; --n) {
                            arrc[n + 1] = arrc[n];
                        }
                        arrc[n3 + 1] = (char)46;
                        n = n2;
                        if (bl) {
                            n = n2 - 1;
                            arrc[n] = (char)45;
                        }
                        return new String(arrc, n, 18 - n + 1);
                    }
                    n = 2;
                    while ((long)n < -l2 + 1L) {
                        arrc[--n2] = (char)48;
                        ++n;
                    }
                    n = n2 - 1;
                    arrc[n] = (char)46;
                    n2 = n - 1;
                    arrc[n2] = (char)48;
                    n = n2;
                    if (bl) {
                        n = n2 - 1;
                        arrc[n] = (char)45;
                    }
                    return new String(arrc, n, 18 - n);
                }
                n = n2 + 1;
                StringBuilder stringBuilder = new StringBuilder(18 + 16 - n);
                if (bl) {
                    stringBuilder.append('-');
                }
                if (18 - n >= 1) {
                    stringBuilder.append(arrc[n2]);
                    stringBuilder.append('.');
                    stringBuilder.append(arrc, n2 + 1, 18 - n2 - 1);
                } else {
                    stringBuilder.append(arrc, n2, 18 - n2);
                }
                stringBuilder.append('E');
                if (l2 > 0L) {
                    stringBuilder.append('+');
                }
                stringBuilder.append(Long.toString(l2));
                return stringBuilder.toString();
            }
            l2 = l3;
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    static String toDecimalScaledString(BigInteger var0, int var1_1) {
        block35 : {
            block33 : {
                block34 : {
                    var0.prepareJavaRepresentation();
                    var2_2 = var0.sign;
                    var3_3 = var0.numberLength;
                    var4_4 = var0.digits;
                    if (var2_2 != 0) break block33;
                    switch (var1_1) {
                        default: {
                            var0 = new StringBuilder();
                            if (var1_1 >= 0) break;
                            var0.append("0E+");
                            break block34;
                        }
                        case 6: {
                            return "0.000000";
                        }
                        case 5: {
                            return "0.00000";
                        }
                        case 4: {
                            return "0.0000";
                        }
                        case 3: {
                            return "0.000";
                        }
                        case 2: {
                            return "0.00";
                        }
                        case 1: {
                            return "0.0";
                        }
                        case 0: {
                            return "0";
                        }
                    }
                    var0.append("0E");
                }
                var0.append(-var1_1);
                return var0.toString();
            }
            var5_9 = var3_3 * 10 + 1 + 7;
            var0 = new char[var5_9 + 1];
            var6_10 = var5_9;
            if (var3_3 != 1) break block35;
            var3_3 = var4_4[0];
            if (var3_3 >= 0) {
                var11_13 = var6_10;
                var6_10 = var3_3;
                do {
                    var12_14 = var6_10 / 10;
                    var3_3 = var11_13 - 1;
                    var0[var3_3] = (Serializable)((char)((char)(var6_10 - var12_14 * 10 + 48)));
                    var6_10 = var12_14;
                    var11_13 = var3_3;
                } while (var12_14 != 0);
                var6_10 = var3_3;
            } else {
                var7_11 = 0xFFFFFFFFL & (long)var3_3;
                do {
                    var9_12 = var7_11 / 10L;
                    var0[--var6_10] = (Serializable)((char)((char)((int)(var7_11 - 10L * var9_12) + 48)));
                    if (var9_12 == 0L) break;
                    var7_11 = var9_12;
                } while (true);
            }
            var4_5 = var0;
            ** GOTO lbl91
        }
        var13_15 = new int[var3_3];
        System.arraycopy(var4_4, 0, var13_15, 0, var3_3);
        var11_13 = var6_10;
        do {
            var7_11 = 0L;
            for (var6_10 = var3_3 - 1; var6_10 >= 0; var7_11 = (long)((int)(var7_11 >> 32)), --var6_10) {
                var7_11 = Conversion.divideLongByBillion((var7_11 << 32) + ((long)var13_15[var6_10] & 0xFFFFFFFFL));
                var13_15[var6_10] = (int)var7_11;
            }
            var14_16 = (int)var7_11;
            var6_10 = var11_13;
            do {
                var12_14 = var6_10 - 1;
                var0[var12_14] = (Serializable)((char)((char)(var14_16 % 10 + 48)));
                var14_16 = var6_10 = var14_16 / 10;
                if (var6_10 == 0) break;
                var6_10 = var12_14;
            } while (var12_14 != 0);
            var6_10 = var12_14;
            for (var14_16 = 0; var14_16 < 9 - var11_13 + var12_14 && var6_10 > 0; ++var14_16) {
                var0[--var6_10] = (Serializable)((char)48);
            }
            var11_13 = var3_3 - 1;
            while (var13_15[var11_13] == 0) {
                block36 : {
                    if (var11_13 != 0) break block36;
                    var11_13 = var6_10;
                    do {
                        var6_10 = ++var11_13;
                        var4_7 = var0;
                    } while (var0[var11_13] == 48);
lbl91: // 2 sources:
                    var11_13 = var2_2 < 0 ? 1 : 0;
                    var3_3 = var5_9 - var6_10 - var1_1 - 1;
                    if (var1_1 == 0) {
                        var1_1 = var6_10;
                        if (var11_13 == 0) return new String((char[])var4_8, var1_1, var5_9 - var1_1);
                        var1_1 = var6_10 - 1;
                        var4_8[var1_1] = (char)45;
                        return new String((char[])var4_8, var1_1, var5_9 - var1_1);
                    }
                    if (var1_1 > 0 && var3_3 >= -6) {
                        if (var3_3 >= 0) {
                            var3_3 = var6_10 + var3_3;
                            var1_1 = var5_9 - 1;
                            do {
                                if (var1_1 < var3_3) {
                                    var4_8[var3_3 + 1] = (char)46;
                                    var1_1 = var6_10;
                                    if (var11_13 == 0) return new String((char[])var4_8, var1_1, var5_9 - var1_1 + 1);
                                    var1_1 = var6_10 - 1;
                                    var4_8[var1_1] = (char)45;
                                    return new String((char[])var4_8, var1_1, var5_9 - var1_1 + 1);
                                }
                                var4_8[var1_1 + 1] = var4_8[var1_1];
                                --var1_1;
                            } while (true);
                        }
                        var1_1 = 2;
                        do {
                            if (var1_1 >= -var3_3 + 1) {
                                var1_1 = var6_10 - 1;
                                var4_8[var1_1] = (char)46;
                                var6_10 = var1_1 - 1;
                                var4_8[var6_10] = (char)48;
                                var1_1 = var6_10;
                                if (var11_13 == 0) return new String((char[])var4_8, var1_1, var5_9 - var1_1);
                                var1_1 = var6_10 - 1;
                                var4_8[var1_1] = (char)45;
                                return new String((char[])var4_8, var1_1, var5_9 - var1_1);
                            }
                            var4_8[--var6_10] = (char)48;
                            ++var1_1;
                        } while (true);
                    }
                    var1_1 = var6_10 + 1;
                    var0 = new StringBuilder(var5_9 + 16 - var1_1);
                    if (var11_13 != 0) {
                        var0.append('-');
                    }
                    if (var5_9 - var1_1 >= 1) {
                        var0.append((char)var4_8[var6_10]);
                        var0.append('.');
                        var0.append((char[])var4_8, var6_10 + 1, var5_9 - var6_10 - 1);
                    } else {
                        var0.append((char[])var4_8, var6_10, var5_9 - var6_10);
                    }
                    var0.append('E');
                    if (var3_3 > 0) {
                        var0.append('+');
                    }
                    var0.append(Integer.toString(var3_3));
                    return var0.toString();
                }
                --var11_13;
            }
            var3_3 = var11_13 + 1;
            var11_13 = var6_10;
        } while (true);
    }
}

