/*
 * Decompiled with CFR 0.145.
 */
package java.math;

import dalvik.annotation.optimization.ReachabilitySensitive;
import java.math.NativeBN;
import libcore.util.NativeAllocationRegistry;

final class BigInt {
    private static NativeAllocationRegistry registry = NativeAllocationRegistry.createMalloced(BigInt.class.getClassLoader(), NativeBN.getNativeFinalizer());
    @ReachabilitySensitive
    private transient long bignum = 0L;

    BigInt() {
    }

    static BigInt addition(BigInt bigInt, BigInt bigInt2) {
        BigInt bigInt3 = BigInt.newBigInt();
        NativeBN.BN_add(bigInt3.bignum, bigInt.bignum, bigInt2.bignum);
        return bigInt3;
    }

    static BigInt bigExp(BigInt bigInt, BigInt bigInt2) {
        BigInt bigInt3 = BigInt.newBigInt();
        NativeBN.BN_exp(bigInt3.bignum, bigInt.bignum, bigInt2.bignum);
        return bigInt3;
    }

    static int cmp(BigInt bigInt, BigInt bigInt2) {
        return NativeBN.BN_cmp(bigInt.bignum, bigInt2.bignum);
    }

    static void division(BigInt bigInt, BigInt bigInt2, BigInt bigInt3, BigInt bigInt4) {
        long l;
        long l2;
        if (bigInt3 != null) {
            bigInt3.makeValid();
            l = bigInt3.bignum;
        } else {
            l = 0L;
        }
        if (bigInt4 != null) {
            bigInt4.makeValid();
            l2 = bigInt4.bignum;
        } else {
            l2 = 0L;
        }
        NativeBN.BN_div(l, l2, bigInt.bignum, bigInt2.bignum);
    }

    static BigInt exp(BigInt bigInt, int n) {
        BigInt bigInt2 = new BigInt();
        bigInt2.putLongInt(n);
        return BigInt.bigExp(bigInt, bigInt2);
    }

    static BigInt gcd(BigInt bigInt, BigInt bigInt2) {
        BigInt bigInt3 = BigInt.newBigInt();
        NativeBN.BN_gcd(bigInt3.bignum, bigInt.bignum, bigInt2.bignum);
        return bigInt3;
    }

    static BigInt generatePrimeDefault(int n) {
        BigInt bigInt = BigInt.newBigInt();
        NativeBN.BN_generate_prime_ex(bigInt.bignum, n, false, 0L, 0L);
        return bigInt;
    }

    private NumberFormatException invalidBigInteger(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid BigInteger: ");
        stringBuilder.append(string);
        throw new NumberFormatException(stringBuilder.toString());
    }

    private void makeValid() {
        if (this.bignum == 0L) {
            this.bignum = NativeBN.BN_new();
            registry.registerNativeAllocation(this, this.bignum);
        }
    }

    static BigInt modExp(BigInt bigInt, BigInt bigInt2, BigInt bigInt3) {
        BigInt bigInt4 = BigInt.newBigInt();
        NativeBN.BN_mod_exp(bigInt4.bignum, bigInt.bignum, bigInt2.bignum, bigInt3.bignum);
        return bigInt4;
    }

    static BigInt modInverse(BigInt bigInt, BigInt bigInt2) {
        BigInt bigInt3 = BigInt.newBigInt();
        NativeBN.BN_mod_inverse(bigInt3.bignum, bigInt.bignum, bigInt2.bignum);
        return bigInt3;
    }

    static BigInt modulus(BigInt bigInt, BigInt bigInt2) {
        BigInt bigInt3 = BigInt.newBigInt();
        NativeBN.BN_nnmod(bigInt3.bignum, bigInt.bignum, bigInt2.bignum);
        return bigInt3;
    }

    private static BigInt newBigInt() {
        BigInt bigInt = new BigInt();
        bigInt.bignum = NativeBN.BN_new();
        registry.registerNativeAllocation(bigInt, bigInt.bignum);
        return bigInt;
    }

    static BigInt product(BigInt bigInt, BigInt bigInt2) {
        BigInt bigInt3 = BigInt.newBigInt();
        NativeBN.BN_mul(bigInt3.bignum, bigInt.bignum, bigInt2.bignum);
        return bigInt3;
    }

    static int remainderByPositiveInt(BigInt bigInt, int n) {
        return NativeBN.BN_mod_word(bigInt.bignum, n);
    }

    static BigInt shift(BigInt bigInt, int n) {
        BigInt bigInt2 = BigInt.newBigInt();
        NativeBN.BN_shift(bigInt2.bignum, bigInt.bignum, n);
        return bigInt2;
    }

    static BigInt subtraction(BigInt bigInt, BigInt bigInt2) {
        BigInt bigInt3 = BigInt.newBigInt();
        NativeBN.BN_sub(bigInt3.bignum, bigInt.bignum, bigInt2.bignum);
        return bigInt3;
    }

    private static String toAscii(String string, int n) {
        int n2 = string.length();
        StringBuilder stringBuilder = new StringBuilder(n2);
        for (int i = 0; i < n2; ++i) {
            int n3 = string.charAt(i);
            int n4 = Character.digit((char)n3, n);
            int n5 = n3;
            if (n4 >= 0) {
                n5 = n3;
                if (n4 <= 9) {
                    n5 = n4 = (int)((char)(n4 + 48));
                }
            }
            stringBuilder.append((char)n5);
        }
        return stringBuilder.toString();
    }

    void add(BigInt bigInt) {
        long l = this.bignum;
        NativeBN.BN_add(l, l, bigInt.bignum);
    }

    void addPositiveInt(int n) {
        NativeBN.BN_add_word(this.bignum, n);
    }

    byte[] bigEndianMagnitude() {
        return NativeBN.BN_bn2bin(this.bignum);
    }

    int bitLength() {
        return NativeBN.bitLength(this.bignum);
    }

    String checkString(String string, int n) {
        if (string != null) {
            int n2 = string.length();
            int n3 = 0;
            int n4 = n2;
            int n5 = n3;
            String string2 = string;
            if (n2 > 0) {
                char c = string.charAt(0);
                if (c == '+') {
                    string2 = string.substring(1);
                    n4 = n2 - 1;
                    n5 = n3;
                } else {
                    n4 = n2;
                    n5 = n3;
                    string2 = string;
                    if (c == '-') {
                        n5 = 0 + 1;
                        string2 = string;
                        n4 = n2;
                    }
                }
            }
            if (n4 - n5 != 0) {
                n2 = 0;
                while (n5 < n4) {
                    char c = string2.charAt(n5);
                    if (Character.digit(c, n) != -1) {
                        if (c > '') {
                            n2 = 1;
                        }
                        ++n5;
                        continue;
                    }
                    throw this.invalidBigInteger(string2);
                }
                string = n2 != 0 ? BigInt.toAscii(string2, n) : string2;
                return string;
            }
            throw this.invalidBigInteger(string2);
        }
        throw new NullPointerException("s == null");
    }

    BigInt copy() {
        BigInt bigInt = new BigInt();
        bigInt.putCopy(this);
        return bigInt;
    }

    String decString() {
        return NativeBN.BN_bn2dec(this.bignum);
    }

    boolean hasNativeBignum() {
        boolean bl = this.bignum != 0L;
        return bl;
    }

    String hexString() {
        return NativeBN.BN_bn2hex(this.bignum);
    }

    boolean isBitSet(int n) {
        return NativeBN.BN_is_bit_set(this.bignum, n);
    }

    boolean isPrime(int n) {
        return NativeBN.BN_primality_test(this.bignum, n, false);
    }

    int[] littleEndianIntsMagnitude() {
        return NativeBN.bn2litEndInts(this.bignum);
    }

    long longInt() {
        return NativeBN.longInt(this.bignum);
    }

    void multiplyByPositiveInt(int n) {
        NativeBN.BN_mul_word(this.bignum, n);
    }

    void putBigEndian(byte[] arrby, boolean bl) {
        this.makeValid();
        NativeBN.BN_bin2bn(arrby, arrby.length, bl, this.bignum);
    }

    void putBigEndianTwosComplement(byte[] arrby) {
        this.makeValid();
        NativeBN.twosComp2bn(arrby, arrby.length, this.bignum);
    }

    void putCopy(BigInt bigInt) {
        this.makeValid();
        NativeBN.BN_copy(this.bignum, bigInt.bignum);
    }

    void putDecString(String string) {
        String string2 = this.checkString(string, 10);
        this.makeValid();
        if (NativeBN.BN_dec2bn(this.bignum, string2) >= string2.length()) {
            return;
        }
        throw this.invalidBigInteger(string);
    }

    void putHexString(String string) {
        String string2 = this.checkString(string, 16);
        this.makeValid();
        if (NativeBN.BN_hex2bn(this.bignum, string2) >= string2.length()) {
            return;
        }
        throw this.invalidBigInteger(string);
    }

    void putLittleEndianInts(int[] arrn, boolean bl) {
        this.makeValid();
        NativeBN.litEndInts2bn(arrn, arrn.length, bl, this.bignum);
    }

    void putLongInt(long l) {
        this.makeValid();
        NativeBN.putLongInt(this.bignum, l);
    }

    void putULongInt(long l, boolean bl) {
        this.makeValid();
        NativeBN.putULongInt(this.bignum, l, bl);
    }

    void setSign(int n) {
        if (n > 0) {
            NativeBN.BN_set_negative(this.bignum, 0);
        } else if (n < 0) {
            NativeBN.BN_set_negative(this.bignum, 1);
        }
    }

    void shift(int n) {
        long l = this.bignum;
        NativeBN.BN_shift(l, l, n);
    }

    int sign() {
        return NativeBN.sign(this.bignum);
    }

    public String toString() {
        return this.decString();
    }

    boolean twosCompFitsIntoBytes(int n) {
        boolean bl = (NativeBN.bitLength(this.bignum) + 7) / 8 <= n;
        return bl;
    }
}

