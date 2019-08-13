/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.engines;

import com.android.org.bouncycastle.crypto.BlockCipher;
import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.DataLengthException;
import com.android.org.bouncycastle.crypto.OutputLengthException;
import com.android.org.bouncycastle.crypto.params.KeyParameter;
import com.android.org.bouncycastle.util.Pack;

public class DESEngine
implements BlockCipher {
    protected static final int BLOCK_SIZE = 8;
    private static final int[] SP1;
    private static final int[] SP2;
    private static final int[] SP3;
    private static final int[] SP4;
    private static final int[] SP5;
    private static final int[] SP6;
    private static final int[] SP7;
    private static final int[] SP8;
    private static final int[] bigbyte;
    private static final short[] bytebit;
    private static final byte[] pc1;
    private static final byte[] pc2;
    private static final byte[] totrot;
    private int[] workingKey = null;

    static {
        bytebit = new short[]{128, 64, 32, 16, 8, 4, 2, 1};
        bigbyte = new int[]{8388608, 4194304, 2097152, 1048576, 524288, 262144, 131072, 65536, 32768, 16384, 8192, 4096, 2048, 1024, 512, 256, 128, 64, 32, 16, 8, 4, 2, 1};
        pc1 = new byte[]{56, 48, 40, 32, 24, 16, 8, 0, 57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 60, 52, 44, 36, 28, 20, 12, 4, 27, 19, 11, 3};
        totrot = new byte[]{1, 2, 4, 6, 8, 10, 12, 14, 15, 17, 19, 21, 23, 25, 27, 28};
        pc2 = new byte[]{13, 16, 10, 23, 0, 4, 2, 27, 14, 5, 20, 9, 22, 18, 11, 3, 25, 7, 15, 6, 26, 19, 12, 1, 40, 51, 30, 36, 46, 54, 29, 39, 50, 44, 32, 47, 43, 48, 38, 55, 33, 52, 45, 41, 49, 35, 28, 31};
        SP1 = new int[]{16843776, 0, 65536, 16843780, 16842756, 66564, 4, 65536, 1024, 16843776, 16843780, 1024, 16778244, 16842756, 16777216, 4, 1028, 16778240, 16778240, 66560, 66560, 16842752, 16842752, 16778244, 65540, 16777220, 16777220, 65540, 0, 1028, 66564, 16777216, 65536, 16843780, 4, 16842752, 16843776, 16777216, 16777216, 1024, 16842756, 65536, 66560, 16777220, 1024, 4, 16778244, 66564, 16843780, 65540, 16842752, 16778244, 16777220, 1028, 66564, 16843776, 1028, 16778240, 16778240, 0, 65540, 66560, 0, 16842756};
        SP2 = new int[]{-2146402272, -2147450880, 32768, 1081376, 1048576, 32, -2146435040, -2147450848, -2147483616, -2146402272, -2146402304, Integer.MIN_VALUE, -2147450880, 1048576, 32, -2146435040, 1081344, 1048608, -2147450848, 0, Integer.MIN_VALUE, 32768, 1081376, -2146435072, 1048608, -2147483616, 0, 1081344, 32800, -2146402304, -2146435072, 32800, 0, 1081376, -2146435040, 1048576, -2147450848, -2146435072, -2146402304, 32768, -2146435072, -2147450880, 32, -2146402272, 1081376, 32, 32768, Integer.MIN_VALUE, 32800, -2146402304, 1048576, -2147483616, 1048608, -2147450848, -2147483616, 1048608, 1081344, 0, -2147450880, 32800, Integer.MIN_VALUE, -2146435040, -2146402272, 1081344};
        SP3 = new int[]{520, 134349312, 0, 134348808, 134218240, 0, 131592, 134218240, 131080, 134217736, 134217736, 131072, 134349320, 131080, 134348800, 520, 134217728, 8, 134349312, 512, 131584, 134348800, 134348808, 131592, 134218248, 131584, 131072, 134218248, 8, 134349320, 512, 134217728, 134349312, 134217728, 131080, 520, 131072, 134349312, 134218240, 0, 512, 131080, 134349320, 134218240, 134217736, 512, 0, 134348808, 134218248, 131072, 134217728, 134349320, 8, 131592, 131584, 134217736, 134348800, 134218248, 520, 134348800, 131592, 8, 134348808, 131584};
        SP4 = new int[]{8396801, 8321, 8321, 128, 8396928, 8388737, 8388609, 8193, 0, 8396800, 8396800, 8396929, 129, 0, 8388736, 8388609, 1, 8192, 8388608, 8396801, 128, 8388608, 8193, 8320, 8388737, 1, 8320, 8388736, 8192, 8396928, 8396929, 129, 8388736, 8388609, 8396800, 8396929, 129, 0, 0, 8396800, 8320, 8388736, 8388737, 1, 8396801, 8321, 8321, 128, 8396929, 129, 1, 8192, 8388609, 8193, 8396928, 8388737, 8193, 8320, 8388608, 8396801, 128, 8388608, 8192, 8396928};
        SP5 = new int[]{256, 34078976, 34078720, 1107296512, 524288, 256, 1073741824, 34078720, 1074266368, 524288, 33554688, 1074266368, 1107296512, 1107820544, 524544, 1073741824, 33554432, 1074266112, 1074266112, 0, 1073742080, 1107820800, 1107820800, 33554688, 1107820544, 1073742080, 0, 1107296256, 34078976, 33554432, 1107296256, 524544, 524288, 1107296512, 256, 33554432, 1073741824, 34078720, 1107296512, 1074266368, 33554688, 1073741824, 1107820544, 34078976, 1074266368, 256, 33554432, 1107820544, 1107820800, 524544, 1107296256, 1107820800, 34078720, 0, 1074266112, 1107296256, 524544, 33554688, 1073742080, 524288, 0, 1074266112, 34078976, 1073742080};
        SP6 = new int[]{536870928, 541065216, 16384, 541081616, 541065216, 16, 541081616, 4194304, 536887296, 4210704, 4194304, 536870928, 4194320, 536887296, 536870912, 16400, 0, 4194320, 536887312, 16384, 4210688, 536887312, 16, 541065232, 541065232, 0, 4210704, 541081600, 16400, 4210688, 541081600, 536870912, 536887296, 16, 541065232, 4210688, 541081616, 4194304, 16400, 536870928, 4194304, 536887296, 536870912, 16400, 536870928, 541081616, 4210688, 541065216, 4210704, 541081600, 0, 541065232, 16, 16384, 541065216, 4210704, 16384, 4194320, 536887312, 0, 541081600, 536870912, 4194320, 536887312};
        SP7 = new int[]{2097152, 69206018, 67110914, 0, 2048, 67110914, 2099202, 69208064, 69208066, 2097152, 0, 67108866, 2, 67108864, 69206018, 2050, 67110912, 2099202, 2097154, 67110912, 67108866, 69206016, 69208064, 2097154, 69206016, 2048, 2050, 69208066, 2099200, 2, 67108864, 2099200, 67108864, 2099200, 2097152, 67110914, 67110914, 69206018, 69206018, 2, 2097154, 67108864, 67110912, 2097152, 69208064, 2050, 2099202, 69208064, 2050, 67108866, 69208066, 69206016, 2099200, 0, 2, 69208066, 0, 2099202, 69206016, 2048, 67108866, 67110912, 2048, 2097154};
        SP8 = new int[]{268439616, 4096, 262144, 268701760, 268435456, 268439616, 64, 268435456, 262208, 268697600, 268701760, 266240, 268701696, 266304, 4096, 64, 268697600, 268435520, 268439552, 4160, 266240, 262208, 268697664, 268701696, 4160, 0, 0, 268697664, 268435520, 268439552, 266304, 262144, 266304, 262144, 268701696, 4096, 64, 268697664, 4096, 266304, 268439552, 64, 268435520, 268697600, 268697664, 268435456, 262144, 268439616, 0, 268701760, 262208, 268435520, 268697600, 268439552, 268439616, 0, 268701760, 266240, 266240, 4160, 4160, 262208, 268435456, 268701696};
    }

    protected void desFunc(int[] arrn, byte[] arrby, int n, byte[] arrby2, int n2) {
        int n3 = Pack.bigEndianToInt(arrby, n);
        n = Pack.bigEndianToInt(arrby, n + 4);
        int n4 = (n3 >>> 4 ^ n) & 252645135;
        n ^= n4;
        n3 ^= n4 << 4;
        n4 = (n3 >>> 16 ^ n) & 65535;
        n ^= n4;
        n3 ^= n4 << 16;
        n4 = (n >>> 2 ^ n3) & 858993459;
        n3 ^= n4;
        n4 = n ^ n4 << 2;
        int n5 = (n4 >>> 8 ^ n3) & 16711935;
        n = n3 ^ n5;
        n3 = n4 ^ n5 << 8;
        n4 = n3 << 1 | n3 >>> 31;
        n3 = (n ^ n4) & -1431655766;
        n ^= n3;
        n3 = n4 ^ n3;
        n4 = n << 1 | n >>> 31;
        for (n = 0; n < 8; ++n) {
            int n6 = (n3 << 28 | n3 >>> 4) ^ arrn[n * 4 + 0];
            arrby = SP7;
            n5 = arrby[n6 & 63];
            int[] arrn2 = SP5;
            int n7 = arrn2[n6 >>> 8 & 63];
            int[] arrn3 = SP3;
            int n8 = arrn3[n6 >>> 16 & 63];
            int[] arrn4 = SP1;
            int n9 = arrn4[n6 >>> 24 & 63];
            int n10 = n3 ^ arrn[n * 4 + 1];
            int[] arrn5 = SP8;
            int n11 = arrn5[n10 & 63];
            int[] arrn6 = SP6;
            n6 = arrn6[n10 >>> 8 & 63];
            int[] arrn7 = SP4;
            int n12 = arrn7[n10 >>> 16 & 63];
            int[] arrn8 = SP2;
            n4 ^= n5 | n7 | n8 | n9 | n11 | n6 | n12 | arrn8[n10 >>> 24 & 63];
            n6 = (n4 << 28 | n4 >>> 4) ^ arrn[n * 4 + 2];
            n5 = arrby[n6 & 63];
            n8 = arrn2[n6 >>> 8 & 63];
            n7 = arrn3[n6 >>> 16 & 63];
            n6 = arrn4[n6 >>> 24 & 63];
            n12 = n4 ^ arrn[n * 4 + 3];
            n3 ^= n5 | n8 | n7 | n6 | arrn5[n12 & 63] | arrn6[n12 >>> 8 & 63] | arrn7[n12 >>> 16 & 63] | arrn8[n12 >>> 24 & 63];
        }
        n3 = n3 << 31 | n3 >>> 1;
        n5 = (n4 ^ n3) & -1431655766;
        n = n4 ^ n5;
        n5 = n3 ^ n5;
        n4 = n << 31 | n >>> 1;
        n3 = (n4 >>> 8 ^ n5) & 16711935;
        n = n5 ^ n3;
        n3 = n4 ^ n3 << 8;
        n4 = (n3 >>> 2 ^ n) & 858993459;
        n ^= n4;
        n3 ^= n4 << 2;
        n4 = (n >>> 16 ^ n3) & 65535;
        n3 ^= n4;
        n4 = n ^ n4 << 16;
        n = (n4 >>> 4 ^ n3) & 252645135;
        Pack.intToBigEndian(n4 ^ n << 4, arrby2, n2);
        Pack.intToBigEndian(n3 ^ n, arrby2, n2 + 4);
    }

    protected int[] generateWorkingKey(boolean bl, byte[] arrby) {
        int n;
        int n2;
        int[] arrn = new int[32];
        boolean[] arrbl = new boolean[56];
        boolean[] arrbl2 = new boolean[56];
        int n3 = 0;
        do {
            boolean bl2 = false;
            if (n3 >= 56) break;
            n = pc1[n3];
            if ((arrby[n >>> 3] & bytebit[n & 7]) != 0) {
                bl2 = true;
            }
            arrbl[n3] = bl2;
            ++n3;
        } while (true);
        for (n3 = 0; n3 < 16; ++n3) {
            int n4;
            n = bl ? n3 << 1 : 15 - n3 << 1;
            int n5 = n + 1;
            arrn[n5] = 0;
            arrn[n] = 0;
            for (n2 = 0; n2 < 28; ++n2) {
                n4 = totrot[n3] + n2;
                arrbl2[n2] = n4 < 28 ? arrbl[n4] : arrbl[n4 - 28];
            }
            for (n2 = 28; n2 < 56; ++n2) {
                n4 = totrot[n3] + n2;
                arrbl2[n2] = n4 < 56 ? arrbl[n4] : arrbl[n4 - 28];
            }
            for (n2 = 0; n2 < 24; ++n2) {
                if (arrbl2[pc2[n2]]) {
                    arrn[n] = arrn[n] | bigbyte[n2];
                }
                if (!arrbl2[pc2[n2 + 24]]) continue;
                arrn[n5] = arrn[n5] | bigbyte[n2];
            }
        }
        for (n3 = 0; n3 != 32; n3 += 2) {
            n2 = arrn[n3];
            n = arrn[n3 + 1];
            arrn[n3] = (16515072 & n) >>> 10 | ((n2 & 16515072) << 6 | (n2 & 4032) << 10) | (n & 4032) >>> 6;
            arrn[n3 + 1] = (258048 & n) >>> 4 | ((n2 & 258048) << 12 | (n2 & 63) << 16) | n & 63;
        }
        return arrn;
    }

    @Override
    public String getAlgorithmName() {
        return "DES";
    }

    @Override
    public int getBlockSize() {
        return 8;
    }

    @Override
    public void init(boolean bl, CipherParameters cipherParameters) {
        if (cipherParameters instanceof KeyParameter) {
            if (((KeyParameter)cipherParameters).getKey().length <= 8) {
                this.workingKey = this.generateWorkingKey(bl, ((KeyParameter)cipherParameters).getKey());
                return;
            }
            throw new IllegalArgumentException("DES key too long - should be 8 bytes");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("invalid parameter passed to DES init - ");
        stringBuilder.append(cipherParameters.getClass().getName());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public int processBlock(byte[] arrby, int n, byte[] arrby2, int n2) {
        int[] arrn = this.workingKey;
        if (arrn != null) {
            if (n + 8 <= arrby.length) {
                if (n2 + 8 <= arrby2.length) {
                    this.desFunc(arrn, arrby, n, arrby2, n2);
                    return 8;
                }
                throw new OutputLengthException("output buffer too short");
            }
            throw new DataLengthException("input buffer too short");
        }
        throw new IllegalStateException("DES engine not initialised");
    }

    @Override
    public void reset() {
    }
}

