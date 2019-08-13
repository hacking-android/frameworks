/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.engines;

import com.android.org.bouncycastle.crypto.BlockCipher;
import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.DataLengthException;
import com.android.org.bouncycastle.crypto.OutputLengthException;
import com.android.org.bouncycastle.crypto.params.KeyParameter;

public final class TwofishEngine
implements BlockCipher {
    private static final int BLOCK_SIZE = 16;
    private static final int GF256_FDBK = 361;
    private static final int GF256_FDBK_2 = 180;
    private static final int GF256_FDBK_4 = 90;
    private static final int INPUT_WHITEN = 0;
    private static final int MAX_KEY_BITS = 256;
    private static final int MAX_ROUNDS = 16;
    private static final int OUTPUT_WHITEN = 4;
    private static final byte[][] P = new byte[][]{{-87, 103, -77, -24, 4, -3, -93, 118, -102, -110, -128, 120, -28, -35, -47, 56, 13, -58, 53, -104, 24, -9, -20, 108, 67, 117, 55, 38, -6, 19, -108, 72, -14, -48, -117, 48, -124, 84, -33, 35, 25, 91, 61, 89, -13, -82, -94, -126, 99, 1, -125, 46, -39, 81, -101, 124, -90, -21, -91, -66, 22, 12, -29, 97, -64, -116, 58, -11, 115, 44, 37, 11, -69, 78, -119, 107, 83, 106, -76, -15, -31, -26, -67, 69, -30, -12, -74, 102, -52, -107, 3, 86, -44, 28, 30, -41, -5, -61, -114, -75, -23, -49, -65, -70, -22, 119, 57, -81, 51, -55, 98, 113, -127, 121, 9, -83, 36, -51, -7, -40, -27, -59, -71, 77, 68, 8, -122, -25, -95, 29, -86, -19, 6, 112, -78, -46, 65, 123, -96, 17, 49, -62, 39, -112, 32, -10, 96, -1, -106, 92, -79, -85, -98, -100, 82, 27, 95, -109, 10, -17, -111, -123, 73, -18, 45, 79, -113, 59, 71, -121, 109, 70, -42, 62, 105, 100, 42, -50, -53, 47, -4, -105, 5, 122, -84, 127, -43, 26, 75, 14, -89, 90, 40, 20, 63, 41, -120, 60, 76, 2, -72, -38, -80, 23, 85, 31, -118, 125, 87, -57, -115, 116, -73, -60, -97, 114, 126, 21, 34, 18, 88, 7, -103, 52, 110, 80, -34, 104, 101, -68, -37, -8, -56, -88, 43, 64, -36, -2, 50, -92, -54, 16, 33, -16, -45, 93, 15, 0, 111, -99, 54, 66, 74, 94, -63, -32}, {117, -13, -58, -12, -37, 123, -5, -56, 74, -45, -26, 107, 69, 125, -24, 75, -42, 50, -40, -3, 55, 113, -15, -31, 48, 15, -8, 27, -121, -6, 6, 63, 94, -70, -82, 91, -118, 0, -68, -99, 109, -63, -79, 14, -128, 93, -46, -43, -96, -124, 7, 20, -75, -112, 44, -93, -78, 115, 76, 84, -110, 116, 54, 81, 56, -80, -67, 90, -4, 96, 98, -106, 108, 66, -9, 16, 124, 40, 39, -116, 19, -107, -100, -57, 36, 70, 59, 112, -54, -29, -123, -53, 17, -48, -109, -72, -90, -125, 32, -1, -97, 119, -61, -52, 3, 111, 8, -65, 64, -25, 43, -30, 121, 12, -86, -126, 65, 58, -22, -71, -28, -102, -92, -105, 126, -38, 122, 23, 102, -108, -95, 29, 61, -16, -34, -77, 11, 114, -89, 28, -17, -47, 83, 62, -113, 51, 38, 95, -20, 118, 42, 73, -127, -120, -18, 33, -60, 26, -21, -39, -59, 57, -103, -51, -83, 49, -117, 1, 24, 35, -35, 31, 78, 45, -7, 72, 79, -14, 101, -114, 120, 92, 88, 25, -115, -27, -104, 87, 103, 127, 5, 100, -81, 99, -74, -2, -11, -73, 60, -91, -50, -23, 104, 68, -32, 77, 67, 105, 41, 46, -84, 21, 89, -88, 10, -98, 110, 71, -33, 52, 53, 106, -49, -36, 34, -55, -64, -101, -119, -44, -19, -85, 18, -94, 13, 82, -69, 2, 47, -87, -41, 97, 30, -76, 80, 4, -10, -62, 22, 37, -122, 86, 85, 9, -66, -111}};
    private static final int P_00 = 1;
    private static final int P_01 = 0;
    private static final int P_02 = 0;
    private static final int P_03 = 1;
    private static final int P_04 = 1;
    private static final int P_10 = 0;
    private static final int P_11 = 0;
    private static final int P_12 = 1;
    private static final int P_13 = 1;
    private static final int P_14 = 0;
    private static final int P_20 = 1;
    private static final int P_21 = 1;
    private static final int P_22 = 0;
    private static final int P_23 = 0;
    private static final int P_24 = 0;
    private static final int P_30 = 0;
    private static final int P_31 = 1;
    private static final int P_32 = 1;
    private static final int P_33 = 0;
    private static final int P_34 = 1;
    private static final int ROUNDS = 16;
    private static final int ROUND_SUBKEYS = 8;
    private static final int RS_GF_FDBK = 333;
    private static final int SK_BUMP = 16843009;
    private static final int SK_ROTL = 9;
    private static final int SK_STEP = 33686018;
    private static final int TOTAL_SUBKEYS = 40;
    private boolean encrypting = false;
    private int[] gMDS0 = new int[256];
    private int[] gMDS1 = new int[256];
    private int[] gMDS2 = new int[256];
    private int[] gMDS3 = new int[256];
    private int[] gSBox;
    private int[] gSubKeys;
    private int k64Cnt = 0;
    private byte[] workingKey = null;

    public TwofishEngine() {
        int[] arrn = new int[2];
        int[] arrn2 = new int[2];
        int[] arrn3 = new int[2];
        for (int i = 0; i < 256; ++i) {
            int n;
            arrn[0] = n = P[0][i] & 255;
            arrn2[0] = this.Mx_X(n) & 255;
            arrn3[0] = this.Mx_Y(n) & 255;
            arrn[1] = n = P[1][i] & 255;
            arrn2[1] = this.Mx_X(n) & 255;
            arrn3[1] = this.Mx_Y(n) & 255;
            this.gMDS0[i] = arrn[1] | arrn2[1] << 8 | arrn3[1] << 16 | arrn3[1] << 24;
            this.gMDS1[i] = arrn3[0] | arrn3[0] << 8 | arrn2[0] << 16 | arrn[0] << 24;
            int[] arrn4 = this.gMDS2;
            int n2 = arrn2[1];
            n = arrn3[1];
            int n3 = arrn[1];
            arrn4[i] = arrn3[1] << 24 | (n2 | n << 8 | n3 << 16);
            this.gMDS3[i] = arrn2[0] | arrn[0] << 8 | arrn3[0] << 16 | arrn2[0] << 24;
        }
    }

    private void Bits32ToBytes(int n, byte[] arrby, int n2) {
        arrby[n2] = (byte)n;
        arrby[n2 + 1] = (byte)(n >> 8);
        arrby[n2 + 2] = (byte)(n >> 16);
        arrby[n2 + 3] = (byte)(n >> 24);
    }

    private int BytesTo32Bits(byte[] arrby, int n) {
        return arrby[n] & 255 | (arrby[n + 1] & 255) << 8 | (arrby[n + 2] & 255) << 16 | (arrby[n + 3] & 255) << 24;
    }

    private int F32(int n, int[] arrn) {
        block4 : {
            int n2;
            int n3;
            int n4;
            int n5;
            int n6;
            int n7;
            int n8;
            int n9;
            block2 : {
                int n10;
                int n11;
                block3 : {
                    block0 : {
                        block1 : {
                            n10 = this.b0(n);
                            n2 = this.b1(n);
                            n9 = this.b2(n);
                            n = this.b3(n);
                            n4 = arrn[0];
                            n5 = arrn[1];
                            n11 = arrn[2];
                            n3 = arrn[3];
                            int n12 = 0;
                            int n13 = this.k64Cnt & 3;
                            if (n13 == 0) break block0;
                            if (n13 == 1) break block1;
                            n6 = n10;
                            n7 = n2;
                            n8 = n9;
                            n3 = n;
                            if (n13 == 2) break block2;
                            if (n13 == 3) break block3;
                            n = n12;
                            break block4;
                        }
                        n3 = this.gMDS0[P[0][n10] & 255 ^ this.b0(n4)];
                        n = this.gMDS1[P[0][n2] & 255 ^ this.b1(n4)] ^ n3 ^ this.gMDS2[P[1][n9] & 255 ^ this.b2(n4)] ^ this.gMDS3[P[1][n] & 255 ^ this.b3(n4)];
                        break block4;
                    }
                    n10 = P[1][n10] & 255 ^ this.b0(n3);
                    n2 = P[0][n2] & 255 ^ this.b1(n3);
                    n9 = P[0][n9] & 255 ^ this.b2(n3);
                    n = P[1][n] & 255 ^ this.b3(n3);
                }
                n6 = P[1][n10] & 255 ^ this.b0(n11);
                n7 = P[1][n2] & 255 ^ this.b1(n11);
                n8 = P[0][n9] & 255 ^ this.b2(n11);
                n3 = P[0][n] & 255 ^ this.b3(n11);
            }
            arrn = this.gMDS0;
            byte[][] arrby = P;
            n = arrn[arrby[0][arrby[0][n6] & 255 ^ this.b0(n5)] & 255 ^ this.b0(n4)];
            arrn = this.gMDS1;
            arrby = P;
            n9 = arrn[arrby[0][arrby[1][n7] & 255 ^ this.b1(n5)] & 255 ^ this.b1(n4)];
            arrn = this.gMDS2;
            arrby = P;
            n2 = arrn[arrby[1][arrby[0][n8] & 255 ^ this.b2(n5)] & 255 ^ this.b2(n4)];
            arrby = this.gMDS3;
            arrn = P;
            n = n2 ^ (n ^ n9) ^ arrby[arrn[1][arrn[1][n3] & 255 ^ this.b3(n5)] & 255 ^ this.b3(n4)];
        }
        return n;
    }

    private int Fe32_0(int n) {
        int[] arrn = this.gSBox;
        int n2 = arrn[(n & 255) * 2 + 0];
        int n3 = arrn[(n >>> 8 & 255) * 2 + 1];
        int n4 = arrn[(n >>> 16 & 255) * 2 + 512];
        return arrn[(n >>> 24 & 255) * 2 + 513] ^ (n2 ^ n3 ^ n4);
    }

    private int Fe32_3(int n) {
        int[] arrn = this.gSBox;
        int n2 = arrn[(n >>> 24 & 255) * 2 + 0];
        int n3 = arrn[(n & 255) * 2 + 1];
        int n4 = arrn[(n >>> 8 & 255) * 2 + 512];
        return arrn[(n >>> 16 & 255) * 2 + 513] ^ (n2 ^ n3 ^ n4);
    }

    private int LFSR1(int n) {
        int n2 = (n & 1) != 0 ? 180 : 0;
        return n >> 1 ^ n2;
    }

    private int LFSR2(int n) {
        int n2 = 0;
        int n3 = (n & 2) != 0 ? 180 : 0;
        if ((n & 1) != 0) {
            n2 = 90;
        }
        return n >> 2 ^ n3 ^ n2;
    }

    private int Mx_X(int n) {
        return this.LFSR2(n) ^ n;
    }

    private int Mx_Y(int n) {
        return this.LFSR1(n) ^ n ^ this.LFSR2(n);
    }

    private int RS_MDS_Encode(int n, int n2) {
        int n3 = n2;
        for (n2 = 0; n2 < 4; ++n2) {
            n3 = this.RS_rem(n3);
        }
        n2 = n3 ^ n;
        for (n = 0; n < 4; ++n) {
            n2 = this.RS_rem(n2);
        }
        return n2;
    }

    private int RS_rem(int n) {
        int n2 = n >>> 24 & 255;
        int n3 = 0;
        int n4 = (n2 & 128) != 0 ? 333 : 0;
        int n5 = (n2 << 1 ^ n4) & 255;
        n4 = n3;
        if ((n2 & 1) != 0) {
            n4 = 166;
        }
        n4 = n2 >>> 1 ^ n4 ^ n5;
        return n << 8 ^ n4 << 24 ^ n5 << 16 ^ n4 << 8 ^ n2;
    }

    private int b0(int n) {
        return n & 255;
    }

    private int b1(int n) {
        return n >>> 8 & 255;
    }

    private int b2(int n) {
        return n >>> 16 & 255;
    }

    private int b3(int n) {
        return n >>> 24 & 255;
    }

    private void decryptBlock(byte[] arrby, int n, byte[] arrby2, int n2) {
        int n3 = this.BytesTo32Bits(arrby, n) ^ this.gSubKeys[4];
        int n4 = this.BytesTo32Bits(arrby, n + 4) ^ this.gSubKeys[5];
        int n5 = this.BytesTo32Bits(arrby, n + 8) ^ this.gSubKeys[6];
        int n6 = this.BytesTo32Bits(arrby, n + 12) ^ this.gSubKeys[7];
        int n7 = 39;
        for (n = 0; n < 16; n += 2) {
            int n8 = this.Fe32_0(n3);
            int n9 = this.Fe32_3(n4);
            arrby = this.gSubKeys;
            int n10 = n7 - 1;
            n6 ^= n9 * 2 + n8 + arrby[n7];
            n7 = n10 - 1;
            n5 = (n5 << 1 | n5 >>> 31) ^ n8 + n9 + arrby[n10];
            n6 = n6 >>> 1 | n6 << 31;
            n8 = this.Fe32_0(n5);
            n10 = this.Fe32_3(n6);
            arrby = this.gSubKeys;
            n9 = n7 - 1;
            n4 ^= n10 * 2 + n8 + arrby[n7];
            n3 = (n3 << 1 | n3 >>> 31) ^ n8 + n10 + arrby[n9];
            n4 = n4 >>> 1 | n4 << 31;
            n7 = n9 - 1;
        }
        this.Bits32ToBytes(this.gSubKeys[0] ^ n5, arrby2, n2);
        this.Bits32ToBytes(this.gSubKeys[1] ^ n6, arrby2, n2 + 4);
        this.Bits32ToBytes(this.gSubKeys[2] ^ n3, arrby2, n2 + 8);
        this.Bits32ToBytes(this.gSubKeys[3] ^ n4, arrby2, n2 + 12);
    }

    private void encryptBlock(byte[] arrby, int n, byte[] arrby2, int n2) {
        int n3 = this.BytesTo32Bits(arrby, n) ^ this.gSubKeys[0];
        int n4 = this.BytesTo32Bits(arrby, n + 4) ^ this.gSubKeys[1];
        int n5 = this.BytesTo32Bits(arrby, n + 8) ^ this.gSubKeys[2];
        int n6 = this.BytesTo32Bits(arrby, n + 12) ^ this.gSubKeys[3];
        int n7 = 8;
        for (n = 0; n < 16; n += 2) {
            int n8 = this.Fe32_0(n3);
            int n9 = this.Fe32_3(n4);
            arrby = this.gSubKeys;
            int n10 = n7 + 1;
            n7 = n5 ^ n8 + n9 + arrby[n7];
            n5 = n7 >>> 1 | n7 << 31;
            n7 = n10 + 1;
            n6 = (n6 << 1 | n6 >>> 31) ^ n9 * 2 + n8 + arrby[n10];
            n8 = this.Fe32_0(n5);
            n9 = this.Fe32_3(n6);
            arrby = this.gSubKeys;
            n10 = n7 + 1;
            n3 ^= n8 + n9 + arrby[n7];
            n3 = n3 >>> 1 | n3 << 31;
            n4 = (n4 << 1 | n4 >>> 31) ^ n9 * 2 + n8 + arrby[n10];
            n7 = n10 + 1;
        }
        this.Bits32ToBytes(this.gSubKeys[4] ^ n5, arrby2, n2);
        this.Bits32ToBytes(this.gSubKeys[5] ^ n6, arrby2, n2 + 4);
        this.Bits32ToBytes(this.gSubKeys[6] ^ n3, arrby2, n2 + 8);
        this.Bits32ToBytes(this.gSubKeys[7] ^ n4, arrby2, n2 + 12);
    }

    private void setKey(byte[] arrby) {
        block3 : {
            block4 : {
                int n;
                int n2;
                int[] arrn = new int[4];
                int[] arrn2 = new int[4];
                int[] arrn3 = new int[4];
                this.gSubKeys = new int[40];
                int n3 = this.k64Cnt;
                int n4 = 1;
                if (n3 < 1) break block3;
                if (n3 > 4) break block4;
                for (n3 = 0; n3 < this.k64Cnt; ++n3) {
                    n = n3 * 8;
                    arrn[n3] = this.BytesTo32Bits(arrby, n);
                    arrn2[n3] = this.BytesTo32Bits(arrby, n + 4);
                    arrn3[this.k64Cnt - 1 - n3] = this.RS_MDS_Encode(arrn[n3], arrn2[n3]);
                }
                for (n3 = 0; n3 < 20; ++n3) {
                    n2 = 33686018 * n3;
                    n = this.F32(n2, arrn);
                    n2 = this.F32(16843009 + n2, arrn2);
                    n2 = n2 << 8 | n2 >>> 24;
                    arrby = this.gSubKeys;
                    arrby[n3 * 2] = n += n2;
                    arrby[n3 * 2 + 1] = (n += n2) << 9 | n >>> 23;
                }
                int n5 = arrn3[0];
                int n6 = arrn3[1];
                int n7 = arrn3[2];
                int n8 = arrn3[3];
                this.gSBox = new int[1024];
                for (n3 = 0; n3 < 256; ++n3) {
                    block9 : {
                        int n9;
                        int n10;
                        int n11;
                        int n12;
                        block7 : {
                            int n13;
                            int n14;
                            block8 : {
                                block5 : {
                                    block6 : {
                                        n = n3;
                                        n13 = n3;
                                        n2 = n3;
                                        n14 = n3;
                                        int n15 = this.k64Cnt & 3;
                                        if (n15 == 0) break block5;
                                        if (n15 == n4) break block6;
                                        n11 = n;
                                        n10 = n13;
                                        n9 = n2;
                                        n12 = n14;
                                        if (n15 == 2) break block7;
                                        if (n15 == 3) break block8;
                                        break block9;
                                    }
                                    this.gSBox[n3 * 2] = this.gMDS0[P[0][n14] & 255 ^ this.b0(n5)];
                                    this.gSBox[n3 * 2 + n4] = this.gMDS1[P[0][n2] & 255 ^ this.b1(n5)];
                                    this.gSBox[n3 * 2 + 512] = this.gMDS2[P[1][n13] & 255 ^ this.b2(n5)];
                                    this.gSBox[n3 * 2 + 513] = this.gMDS3[P[1][n] & 255 ^ this.b3(n5)];
                                    break block9;
                                }
                                n14 = P[1][n14] & 255 ^ this.b0(n8);
                                n2 = P[0][n2] & 255 ^ this.b1(n8);
                                n13 = P[0][n13] & 255 ^ this.b2(n8);
                                arrby = P;
                                n4 = 1;
                                n = arrby[1][n] & 255 ^ this.b3(n8);
                            }
                            n12 = P[n4][n14];
                            n14 = this.b0(n7);
                            n9 = P[n4][n2];
                            n2 = this.b1(n7);
                            n10 = P[0][n13];
                            n13 = this.b2(n7);
                            n4 = P[0][n];
                            n = this.b3(n7);
                            n12 = n12 & 255 ^ n14;
                            n9 = n9 & 255 ^ n2;
                            n10 = n10 & 255 ^ n13;
                            n11 = n4 & 255 ^ n;
                        }
                        arrn2 = this.gSBox;
                        arrby = this.gMDS0;
                        arrn = P;
                        arrn2[n3 * 2] = arrby[arrn[0][arrn[0][n12] & 255 ^ this.b0(n6)] & 255 ^ this.b0(n5)];
                        arrby = this.gSBox;
                        arrn = this.gMDS1;
                        arrn2 = P;
                        arrby[n3 * 2 + 1] = arrn[arrn2[0][arrn2[1][n9] & 255 ^ this.b1(n6)] & 255 ^ this.b1(n5)];
                        arrn2 = this.gSBox;
                        arrby = this.gMDS2;
                        arrn = P;
                        arrn2[n3 * 2 + 512] = arrby[arrn[1][arrn[0][n10] & 255 ^ this.b2(n6)] & 255 ^ this.b2(n5)];
                        arrn = this.gSBox;
                        arrn2 = this.gMDS3;
                        arrby = P;
                        arrn[n3 * 2 + 513] = arrn2[arrby[1][arrby[1][n11] & 255 ^ this.b3(n6)] & 255 ^ this.b3(n5)];
                    }
                    n4 = 1;
                }
                return;
            }
            throw new IllegalArgumentException("Key size larger than 256 bits");
        }
        throw new IllegalArgumentException("Key size less than 64 bits");
    }

    @Override
    public String getAlgorithmName() {
        return "Twofish";
    }

    @Override
    public int getBlockSize() {
        return 16;
    }

    @Override
    public void init(boolean bl, CipherParameters arrby) {
        if (arrby instanceof KeyParameter) {
            this.encrypting = bl;
            arrby = this.workingKey = ((KeyParameter)arrby).getKey();
            this.k64Cnt = arrby.length / 8;
            this.setKey(arrby);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("invalid parameter passed to Twofish init - ");
        stringBuilder.append(arrby.getClass().getName());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public int processBlock(byte[] arrby, int n, byte[] arrby2, int n2) {
        if (this.workingKey != null) {
            if (n + 16 <= arrby.length) {
                if (n2 + 16 <= arrby2.length) {
                    if (this.encrypting) {
                        this.encryptBlock(arrby, n, arrby2, n2);
                    } else {
                        this.decryptBlock(arrby, n, arrby2, n2);
                    }
                    return 16;
                }
                throw new OutputLengthException("output buffer too short");
            }
            throw new DataLengthException("input buffer too short");
        }
        throw new IllegalStateException("Twofish not initialised");
    }

    @Override
    public void reset() {
        byte[] arrby = this.workingKey;
        if (arrby != null) {
            this.setKey(arrby);
        }
    }
}

