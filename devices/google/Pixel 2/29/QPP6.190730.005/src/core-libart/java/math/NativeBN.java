/*
 * Decompiled with CFR 0.145.
 */
package java.math;

final class NativeBN {
    NativeBN() {
    }

    public static native void BN_add(long var0, long var2, long var4);

    public static native void BN_add_word(long var0, int var2);

    public static native void BN_bin2bn(byte[] var0, int var1, boolean var2, long var3);

    public static native byte[] BN_bn2bin(long var0);

    public static native String BN_bn2dec(long var0);

    public static native String BN_bn2hex(long var0);

    public static native int BN_cmp(long var0, long var2);

    public static native void BN_copy(long var0, long var2);

    public static native int BN_dec2bn(long var0, String var2);

    public static native void BN_div(long var0, long var2, long var4, long var6);

    public static native void BN_exp(long var0, long var2, long var4);

    public static native void BN_free(long var0);

    public static native void BN_gcd(long var0, long var2, long var4);

    public static native void BN_generate_prime_ex(long var0, int var2, boolean var3, long var4, long var6);

    public static native int BN_hex2bn(long var0, String var2);

    public static native boolean BN_is_bit_set(long var0, int var2);

    public static native void BN_mod_exp(long var0, long var2, long var4, long var6);

    public static native void BN_mod_inverse(long var0, long var2, long var4);

    public static native int BN_mod_word(long var0, int var2);

    public static native void BN_mul(long var0, long var2, long var4);

    public static native void BN_mul_word(long var0, int var2);

    public static native long BN_new();

    public static native void BN_nnmod(long var0, long var2, long var4);

    public static native boolean BN_primality_test(long var0, int var2, boolean var3);

    public static native void BN_set_negative(long var0, int var2);

    public static native void BN_shift(long var0, long var2, int var4);

    public static native void BN_sub(long var0, long var2, long var4);

    public static native int bitLength(long var0);

    public static native int[] bn2litEndInts(long var0);

    public static native long getNativeFinalizer();

    public static native void litEndInts2bn(int[] var0, int var1, boolean var2, long var3);

    public static native long longInt(long var0);

    public static native void putLongInt(long var0, long var2);

    public static native void putULongInt(long var0, long var2, boolean var4);

    public static native int sign(long var0);

    public static native void twosComp2bn(byte[] var0, int var1, long var2);
}

