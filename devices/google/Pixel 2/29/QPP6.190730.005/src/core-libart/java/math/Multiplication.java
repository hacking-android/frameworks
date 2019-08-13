/*
 * Decompiled with CFR 0.145.
 */
package java.math;

import java.math.BigInt;
import java.math.BigInteger;

class Multiplication {
    static final BigInteger[] bigFivePows;
    static final BigInteger[] bigTenPows;
    static final int[] fivePows;
    static final int[] tenPows;

    static {
        tenPows = new int[]{1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000};
        fivePows = new int[]{1, 5, 25, 125, 625, 3125, 15625, 78125, 390625, 1953125, 9765625, 48828125, 244140625, 1220703125};
        bigTenPows = new BigInteger[32];
        bigFivePows = new BigInteger[32];
        long l = 1L;
        int n = 0;
        do {
            if (n > 18) break;
            Multiplication.bigFivePows[n] = BigInteger.valueOf(l);
            Multiplication.bigTenPows[n] = BigInteger.valueOf(l << n);
            l *= 5L;
        } while (true);
        for (int i = ++n; i < bigTenPows.length; ++i) {
            BigInteger[] arrbigInteger = bigFivePows;
            arrbigInteger[i] = arrbigInteger[i - 1].multiply(arrbigInteger[1]);
            arrbigInteger = bigTenPows;
            arrbigInteger[i] = arrbigInteger[i - 1].multiply(BigInteger.TEN);
        }
    }

    private Multiplication() {
    }

    static BigInteger multiplyByFivePow(BigInteger bigInteger, int n) {
        Object[] arrobject = fivePows;
        if (n < arrobject.length) {
            return Multiplication.multiplyByPositiveInt(bigInteger, arrobject[n]);
        }
        arrobject = bigFivePows;
        if (n < arrobject.length) {
            return bigInteger.multiply((BigInteger)arrobject[n]);
        }
        return bigInteger.multiply(arrobject[1].pow(n));
    }

    static BigInteger multiplyByPositiveInt(BigInteger object, int n) {
        object = ((BigInteger)object).getBigInt().copy();
        ((BigInt)object).multiplyByPositiveInt(n);
        return new BigInteger((BigInt)object);
    }

    static BigInteger multiplyByTenPow(BigInteger bigInteger, long l) {
        int[] arrn = tenPows;
        bigInteger = l < (long)arrn.length ? Multiplication.multiplyByPositiveInt(bigInteger, arrn[(int)l]) : bigInteger.multiply(Multiplication.powerOf10(l));
        return bigInteger;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static BigInteger powerOf10(long var0) {
        var2_1 = (int)var0;
        var3_2 = Multiplication.bigTenPows;
        if (var0 < (long)((BigInteger[])var3_2).length) {
            return var3_2[var2_1];
        }
        if (var0 <= 50L) {
            return BigInteger.TEN.pow(var2_1);
        }
        if (var0 > Integer.MAX_VALUE) ** GOTO lbl10
        return Multiplication.bigFivePows[1].pow(var2_1).shiftLeft(var2_1);
lbl10: // 1 sources:
        var4_4 = Multiplication.bigFivePows[1].pow(Integer.MAX_VALUE);
        var3_2 = var4_4;
        var2_1 = (int)(var0 % Integer.MAX_VALUE);
        for (var5_5 = var0 - Integer.MAX_VALUE; var5_5 > Integer.MAX_VALUE; var3_2 = var3_2.multiply((BigInteger)var4_4), var5_5 -= Integer.MAX_VALUE) {
            continue;
        }
        var3_2 = var3_2.multiply(Multiplication.bigFivePows[1].pow(var2_1)).shiftLeft(Integer.MAX_VALUE);
        var0 -= Integer.MAX_VALUE;
        while (var0 > Integer.MAX_VALUE) {
            var3_2 = var3_2.shiftLeft(Integer.MAX_VALUE);
            var0 -= Integer.MAX_VALUE;
        }
        try {
            return var3_2.shiftLeft(var2_1);
        }
        catch (OutOfMemoryError var3_3) {
            throw new ArithmeticException(var3_3.getMessage());
        }
    }
}

