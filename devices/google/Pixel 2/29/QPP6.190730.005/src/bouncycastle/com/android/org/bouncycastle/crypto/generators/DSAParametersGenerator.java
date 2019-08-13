/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.generators;

import com.android.org.bouncycastle.crypto.Digest;
import com.android.org.bouncycastle.crypto.digests.AndroidDigestFactory;
import com.android.org.bouncycastle.crypto.params.DSAParameterGenerationParameters;
import com.android.org.bouncycastle.crypto.params.DSAParameters;
import com.android.org.bouncycastle.crypto.params.DSAValidationParameters;
import com.android.org.bouncycastle.util.Arrays;
import com.android.org.bouncycastle.util.BigIntegers;
import com.android.org.bouncycastle.util.encoders.Hex;
import java.math.BigInteger;
import java.security.SecureRandom;

public class DSAParametersGenerator {
    private static final BigInteger ONE;
    private static final BigInteger TWO;
    private static final BigInteger ZERO;
    private int L;
    private int N;
    private int certainty;
    private Digest digest;
    private int iterations;
    private SecureRandom random;
    private int usageIndex;
    private boolean use186_3;

    static {
        ZERO = BigInteger.valueOf(0L);
        ONE = BigInteger.valueOf(1L);
        TWO = BigInteger.valueOf(2L);
    }

    public DSAParametersGenerator() {
        this(AndroidDigestFactory.getSHA1());
    }

    public DSAParametersGenerator(Digest digest) {
        this.digest = digest;
    }

    private static BigInteger calculateGenerator_FIPS186_2(BigInteger bigInteger, BigInteger bigInteger2, SecureRandom secureRandom) {
        BigInteger bigInteger3 = bigInteger.subtract(ONE).divide(bigInteger2);
        BigInteger bigInteger4 = bigInteger.subtract(TWO);
        while ((bigInteger2 = BigIntegers.createRandomInRange(TWO, bigInteger4, secureRandom).modPow(bigInteger3, bigInteger)).bitLength() <= 1) {
        }
        return bigInteger2;
    }

    private static BigInteger calculateGenerator_FIPS186_3_Unverifiable(BigInteger bigInteger, BigInteger bigInteger2, SecureRandom secureRandom) {
        return DSAParametersGenerator.calculateGenerator_FIPS186_2(bigInteger, bigInteger2, secureRandom);
    }

    private static BigInteger calculateGenerator_FIPS186_3_Verifiable(Digest digest, BigInteger bigInteger, BigInteger arrby, byte[] arrby2, int n) {
        BigInteger bigInteger2 = bigInteger.subtract(ONE).divide((BigInteger)arrby);
        Object object = Hex.decode("6767656E");
        arrby = new byte[arrby2.length + ((byte[])object).length + 1 + 2];
        System.arraycopy((byte[])arrby2, (int)0, (byte[])arrby, (int)0, (int)arrby2.length);
        System.arraycopy((byte[])object, (int)0, (byte[])arrby, (int)arrby2.length, (int)((byte[])object).length);
        arrby[arrby.length - 3] = (byte)n;
        arrby2 = new byte[digest.getDigestSize()];
        for (n = 1; n < 65536; ++n) {
            DSAParametersGenerator.inc(arrby);
            DSAParametersGenerator.hash(digest, arrby, arrby2, 0);
            object = new BigInteger(1, arrby2).modPow(bigInteger2, bigInteger);
            if (((BigInteger)object).compareTo(TWO) < 0) continue;
            return object;
        }
        return null;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private DSAParameters generateParameters_FIPS186_2() {
        var1_1 = new byte[20];
        var2_2 = new byte[20];
        var3_3 = new byte[20];
        var4_4 = new byte[20];
        var5_5 = this.L;
        var6_6 = (var5_5 - 1) / 160;
        var7_7 = new byte[var5_5 / 8];
        if (this.digest.getAlgorithmName().equals("SHA-1") == false) throw new IllegalStateException("can only use SHA-1 for generating FIPS 186-2 parameters");
        block0 : do lbl-1000: // 3 sources:
        {
            this.random.nextBytes(var1_1);
            DSAParametersGenerator.hash(this.digest, var1_1, var2_2, 0);
            System.arraycopy((byte[])var1_1, (int)0, (byte[])var3_3, (int)0, (int)var1_1.length);
            DSAParametersGenerator.inc(var3_3);
            DSAParametersGenerator.hash(this.digest, var3_3, var3_3, 0);
            for (var5_5 = 0; var5_5 != var4_4.length; ++var5_5) {
                var4_4[var5_5] = (byte)(var2_2[var5_5] ^ var3_3[var5_5]);
            }
            var4_4[0] = (byte)(var4_4[0] | -128);
            var4_4[19] = (byte)(var4_4[19] | 1);
            var8_8 = new BigInteger(1, var4_4);
            if (!this.isProbablePrime(var8_8)) ** GOTO lbl-1000
            var9_9 = Arrays.clone(var1_1);
            DSAParametersGenerator.inc(var9_9);
            var5_5 = 0;
            do {
                if (var5_5 >= 4096) continue block0;
                for (var10_10 = 1; var10_10 <= var6_6; ++var10_10) {
                    DSAParametersGenerator.inc(var9_9);
                    DSAParametersGenerator.hash(this.digest, var9_9, var7_7, var7_7.length - var2_2.length * var10_10);
                }
                var10_10 = var7_7.length - var2_2.length * var6_6;
                DSAParametersGenerator.inc(var9_9);
                DSAParametersGenerator.hash(this.digest, var9_9, var2_2, 0);
                System.arraycopy((byte[])var2_2, (int)(var2_2.length - var10_10), (byte[])var7_7, (int)0, (int)var10_10);
                var7_7[0] = (byte)(var7_7[0] | -128);
                var11_11 = new BigInteger(1, var7_7);
                var11_11 = var11_11.subtract(var11_11.mod(var8_8.shiftLeft(1)).subtract(DSAParametersGenerator.ONE));
                if (var11_11.bitLength() == this.L && this.isProbablePrime(var11_11)) {
                    return new DSAParameters(var11_11, var8_8, DSAParametersGenerator.calculateGenerator_FIPS186_2(var11_11, var8_8, this.random), new DSAValidationParameters(var1_1, var5_5));
                }
                ++var5_5;
            } while (true);
            break;
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private DSAParameters generateParameters_FIPS186_3() {
        var1_1 = this.digest;
        var2_2 = var1_1.getDigestSize() * 8;
        var3_3 = this.N;
        var4_4 = new byte[var3_3 / 8];
        var5_5 = this.L;
        var6_6 = (var5_5 - 1) / var2_2;
        var7_7 = new byte[var5_5 / 8];
        var8_9 = new byte[var1_1.getDigestSize()];
        block0 : do lbl-1000: // 3 sources:
        {
            this.random.nextBytes(var4_4);
            DSAParametersGenerator.hash(var1_1, var4_4, var8_9, 0);
            var9_10 = new BigInteger(1, var8_9).mod(DSAParametersGenerator.ONE.shiftLeft(this.N - 1)).setBit(0).setBit(this.N - 1);
            if (!this.isProbablePrime(var9_10)) ** GOTO lbl-1000
            var10_11 = Arrays.clone(var4_4);
            var11_12 = this.L;
            var5_5 = 0;
            do {
                if (var5_5 >= var11_12 * 4) continue block0;
                for (var12_13 = 1; var12_13 <= var6_6; ++var12_13) {
                    DSAParametersGenerator.inc(var10_11);
                    DSAParametersGenerator.hash(var1_1, var10_11, var7_7, var7_7.length - var8_9.length * var12_13);
                }
                var12_13 = var7_7.length - var8_9.length * var6_6;
                DSAParametersGenerator.inc(var10_11);
                DSAParametersGenerator.hash(var1_1, var10_11, var8_9, 0);
                System.arraycopy((byte[])var8_9, (int)(var8_9.length - var12_13), (byte[])var7_7, (int)0, (int)var12_13);
                var7_7[0] = (byte)(var7_7[0] | -128);
                var13_14 = new BigInteger(1, var7_7);
                var13_14 = var13_14.subtract(var13_14.mod(var9_10.shiftLeft(1)).subtract(DSAParametersGenerator.ONE));
                if (var13_14.bitLength() == this.L && this.isProbablePrime(var13_14)) {
                    var2_2 = this.usageIndex;
                    if (var2_2 < 0) return new DSAParameters(var13_14, var9_10, DSAParametersGenerator.calculateGenerator_FIPS186_3_Unverifiable(var13_14, var9_10, this.random), new DSAValidationParameters(var4_4, var5_5));
                    var7_8 = DSAParametersGenerator.calculateGenerator_FIPS186_3_Verifiable(var1_1, var13_14, var9_10, var4_4, var2_2);
                    if (var7_8 == null) return new DSAParameters(var13_14, var9_10, DSAParametersGenerator.calculateGenerator_FIPS186_3_Unverifiable(var13_14, var9_10, this.random), new DSAValidationParameters(var4_4, var5_5));
                    return new DSAParameters(var13_14, var9_10, var7_8, new DSAValidationParameters(var4_4, var5_5, this.usageIndex));
                }
                ++var5_5;
            } while (true);
            break;
        } while (true);
    }

    private static int getDefaultN(int n) {
        n = n > 1024 ? 256 : 160;
        return n;
    }

    private static int getMinimumIterations(int n) {
        n = n <= 1024 ? 40 : (n - 1) / 1024 * 8 + 48;
        return n;
    }

    private static void hash(Digest digest, byte[] arrby, byte[] arrby2, int n) {
        digest.update(arrby, 0, arrby.length);
        digest.doFinal(arrby2, n);
    }

    private static void inc(byte[] arrby) {
        for (int i = arrby.length - 1; i >= 0; --i) {
            byte by = (byte)(arrby[i] + 1 & 255);
            arrby[i] = by;
            if (by != 0) break;
        }
    }

    private boolean isProbablePrime(BigInteger bigInteger) {
        return bigInteger.isProbablePrime(this.certainty);
    }

    public DSAParameters generateParameters() {
        DSAParameters dSAParameters = this.use186_3 ? this.generateParameters_FIPS186_3() : this.generateParameters_FIPS186_2();
        return dSAParameters;
    }

    public void init(int n, int n2, SecureRandom secureRandom) {
        this.L = n;
        this.N = DSAParametersGenerator.getDefaultN(n);
        this.certainty = n2;
        this.iterations = Math.max(DSAParametersGenerator.getMinimumIterations(this.L), (n2 + 1) / 2);
        this.random = secureRandom;
        this.use186_3 = false;
        this.usageIndex = -1;
    }

    public void init(DSAParameterGenerationParameters dSAParameterGenerationParameters) {
        int n = dSAParameterGenerationParameters.getL();
        int n2 = dSAParameterGenerationParameters.getN();
        if (n >= 1024 && n <= 3072 && n % 1024 == 0) {
            if (n == 1024 && n2 != 160) {
                throw new IllegalArgumentException("N must be 160 for L = 1024");
            }
            if (n == 2048 && n2 != 224 && n2 != 256) {
                throw new IllegalArgumentException("N must be 224 or 256 for L = 2048");
            }
            if (n == 3072 && n2 != 256) {
                throw new IllegalArgumentException("N must be 256 for L = 3072");
            }
            if (this.digest.getDigestSize() * 8 >= n2) {
                this.L = n;
                this.N = n2;
                this.certainty = dSAParameterGenerationParameters.getCertainty();
                this.iterations = Math.max(DSAParametersGenerator.getMinimumIterations(n), (this.certainty + 1) / 2);
                this.random = dSAParameterGenerationParameters.getRandom();
                this.use186_3 = true;
                this.usageIndex = dSAParameterGenerationParameters.getUsageIndex();
                return;
            }
            throw new IllegalStateException("Digest output size too small for value of N");
        }
        throw new IllegalArgumentException("L values must be between 1024 and 3072 and a multiple of 1024");
    }
}

