/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.engines;

import com.android.org.bouncycastle.crypto.BlockCipher;
import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.DataLengthException;
import com.android.org.bouncycastle.crypto.OutputLengthException;
import com.android.org.bouncycastle.crypto.params.KeyParameter;
import com.android.org.bouncycastle.crypto.params.RC2Parameters;

public class RC2Engine
implements BlockCipher {
    private static final int BLOCK_SIZE = 8;
    private static byte[] piTable = new byte[]{-39, 120, -7, -60, 25, -35, -75, -19, 40, -23, -3, 121, 74, -96, -40, -99, -58, 126, 55, -125, 43, 118, 83, -114, 98, 76, 100, -120, 68, -117, -5, -94, 23, -102, 89, -11, -121, -77, 79, 19, 97, 69, 109, -115, 9, -127, 125, 50, -67, -113, 64, -21, -122, -73, 123, 11, -16, -107, 33, 34, 92, 107, 78, -126, 84, -42, 101, -109, -50, 96, -78, 28, 115, 86, -64, 20, -89, -116, -15, -36, 18, 117, -54, 31, 59, -66, -28, -47, 66, 61, -44, 48, -93, 60, -74, 38, 111, -65, 14, -38, 70, 105, 7, 87, 39, -14, 29, -101, -68, -108, 67, 3, -8, 17, -57, -10, -112, -17, 62, -25, 6, -61, -43, 47, -56, 102, 30, -41, 8, -24, -22, -34, -128, 82, -18, -9, -124, -86, 114, -84, 53, 77, 106, 42, -106, 26, -46, 113, 90, 21, 73, 116, 75, -97, -48, 94, 4, 24, -92, -20, -62, -32, 65, 110, 15, 81, -53, -52, 36, -111, -81, 80, -95, -12, 112, 57, -103, 124, 58, -123, 35, -72, -76, 122, -4, 2, 54, 91, 37, 85, -105, 49, 45, 93, -6, -104, -29, -118, -110, -82, 5, -33, 41, 16, 103, 108, -70, -55, -45, 0, -26, -49, -31, -98, -88, 44, 99, 22, 1, 63, 88, -30, -119, -87, 13, 56, 52, 27, -85, 51, -1, -80, -69, 72, 12, 95, -71, -79, -51, 46, -59, -13, -37, 71, -27, -91, -100, 119, 10, -90, 32, 104, -2, 127, -63, -83};
    private boolean encrypting;
    private int[] workingKey;

    private void decryptBlock(byte[] arrby, int n, byte[] arrby2, int n2) {
        int n3;
        int n4 = ((arrby[n + 7] & 255) << 8) + (arrby[n + 6] & 255);
        int n5 = ((arrby[n + 5] & 255) << 8) + (arrby[n + 4] & 255);
        int n6 = ((arrby[n + 3] & 255) << 8) + (arrby[n + 2] & 255);
        n = ((arrby[n + 1] & 255) << 8) + (arrby[n + 0] & 255);
        for (n3 = 60; n3 >= 44; n3 -= 4) {
            n4 = this.rotateWordLeft(n4, 11) - ((n5 & n) + (n6 & n5) + this.workingKey[n3 + 3]);
            n5 = this.rotateWordLeft(n5, 13) - ((n6 & n4) + (n & n6) + this.workingKey[n3 + 2]);
            n6 = this.rotateWordLeft(n6, 14) - ((n & n5) + (n4 & n) + this.workingKey[n3 + 1]);
            n = this.rotateWordLeft(n, 15) - ((n4 & n6) + (n5 & n4) + this.workingKey[n3]);
        }
        arrby = this.workingKey;
        n4 -= arrby[n5 & 63];
        n5 -= arrby[n6 & 63];
        n3 = n6 - arrby[n & 63];
        n -= arrby[n4 & 63];
        for (n6 = 40; n6 >= 20; n6 -= 4) {
            n4 = this.rotateWordLeft(n4, 11) - ((n5 & n) + (n3 & n5) + this.workingKey[n6 + 3]);
            n5 = this.rotateWordLeft(n5, 13) - ((n3 & n4) + (n & n3) + this.workingKey[n6 + 2]);
            n3 = this.rotateWordLeft(n3, 14) - ((n & n5) + (n4 & n) + this.workingKey[n6 + 1]);
            n = this.rotateWordLeft(n, 15) - ((n4 & n3) + (n5 & n4) + this.workingKey[n6]);
        }
        arrby = this.workingKey;
        n4 -= arrby[n5 & 63];
        n5 -= arrby[n3 & 63];
        n6 = n3 - arrby[n & 63];
        n3 = n - arrby[n4 & 63];
        for (n = 16; n >= 0; n -= 4) {
            n4 = this.rotateWordLeft(n4, 11) - ((n5 & n3) + (n6 & n5) + this.workingKey[n + 3]);
            n5 = this.rotateWordLeft(n5, 13) - ((n6 & n4) + (n3 & n6) + this.workingKey[n + 2]);
            n6 = this.rotateWordLeft(n6, 14) - ((n3 & n5) + (n4 & n3) + this.workingKey[n + 1]);
            n3 = this.rotateWordLeft(n3, 15) - ((n4 & n6) + (n5 & n4) + this.workingKey[n]);
        }
        arrby2[n2 + 0] = (byte)n3;
        arrby2[n2 + 1] = (byte)(n3 >> 8);
        arrby2[n2 + 2] = (byte)n6;
        arrby2[n2 + 3] = (byte)(n6 >> 8);
        arrby2[n2 + 4] = (byte)n5;
        arrby2[n2 + 5] = (byte)(n5 >> 8);
        arrby2[n2 + 6] = (byte)n4;
        arrby2[n2 + 7] = (byte)(n4 >> 8);
    }

    private void encryptBlock(byte[] arrby, int n, byte[] arrby2, int n2) {
        int n3 = ((arrby[n + 7] & 255) << 8) + (arrby[n + 6] & 255);
        int n4 = ((arrby[n + 5] & 255) << 8) + (arrby[n + 4] & 255);
        int n5 = ((arrby[n + 3] & 255) << 8) + (arrby[n + 2] & 255);
        int n6 = ((arrby[n + 1] & 255) << 8) + (arrby[n + 0] & 255);
        for (n = 0; n <= 16; n += 4) {
            n6 = this.rotateWordLeft((n3 & n5) + n6 + (n4 & n3) + this.workingKey[n], 1);
            n5 = this.rotateWordLeft((n6 & n4) + n5 + (n3 & n6) + this.workingKey[n + 1], 2);
            n4 = this.rotateWordLeft((n5 & n3) + n4 + (n6 & n5) + this.workingKey[n + 2], 3);
            n3 = this.rotateWordLeft((n4 & n6) + n3 + (n5 & n4) + this.workingKey[n + 3], 5);
        }
        arrby = this.workingKey;
        n4 += arrby[(n5 += arrby[(n6 += arrby[n3 & 63]) & 63]) & 63];
        n3 += arrby[n4 & 63];
        for (n = 20; n <= 40; n += 4) {
            n6 = this.rotateWordLeft((n3 & n5) + n6 + (n4 & n3) + this.workingKey[n], 1);
            n5 = this.rotateWordLeft((n6 & n4) + n5 + (n3 & n6) + this.workingKey[n + 1], 2);
            n4 = this.rotateWordLeft((n5 & n3) + n4 + (n6 & n5) + this.workingKey[n + 2], 3);
            n3 = this.rotateWordLeft((n4 & n6) + n3 + (n5 & n4) + this.workingKey[n + 3], 5);
        }
        arrby = this.workingKey;
        int n7 = n3 + arrby[(n4 += arrby[(n5 += arrby[(n6 += arrby[n3 & 63]) & 63]) & 63]) & 63];
        n3 = n4;
        n4 = n7;
        for (n = 44; n < 64; n += 4) {
            n6 = this.rotateWordLeft((n4 & n5) + n6 + (n3 & n4) + this.workingKey[n], 1);
            n5 = this.rotateWordLeft((n6 & n3) + n5 + (n4 & n6) + this.workingKey[n + 1], 2);
            n3 = this.rotateWordLeft((n5 & n4) + n3 + (n6 & n5) + this.workingKey[n + 2], 3);
            n4 = this.rotateWordLeft((n3 & n6) + n4 + (n5 & n3) + this.workingKey[n + 3], 5);
        }
        arrby2[n2 + 0] = (byte)n6;
        arrby2[n2 + 1] = (byte)(n6 >> 8);
        arrby2[n2 + 2] = (byte)n5;
        arrby2[n2 + 3] = (byte)(n5 >> 8);
        arrby2[n2 + 4] = (byte)n3;
        arrby2[n2 + 5] = (byte)(n3 >> 8);
        arrby2[n2 + 6] = (byte)n4;
        arrby2[n2 + 7] = (byte)(n4 >> 8);
    }

    private int[] generateWorkingKey(byte[] arrby, int n) {
        int n2;
        int[] arrn = new int[128];
        for (n2 = 0; n2 != arrby.length; ++n2) {
            arrn[n2] = arrby[n2] & 255;
        }
        int n3 = arrby.length;
        if (n3 < 128) {
            n2 = 0;
            int n4 = arrn[n3 - 1];
            do {
                n4 = piTable[arrn[n2] + n4 & 255] & 255;
                int n5 = n3 + 1;
                arrn[n3] = n4;
                n3 = n5;
                if (n5 >= 128) break;
                ++n2;
            } while (true);
        }
        n3 = n + 7 >> 3;
        arrn[128 - n3] = n2 = piTable[arrn[128 - n3] & 255 >> (-n & 7)] & 255;
        for (n = 128 - n3 - 1; n >= 0; --n) {
            arrn[n] = n2 = piTable[arrn[n + n3] ^ n2] & 255;
        }
        arrby = new int[64];
        for (n = 0; n != arrby.length; ++n) {
            arrby[n] = arrn[n * 2] + (arrn[n * 2 + 1] << 8);
        }
        return arrby;
    }

    private int rotateWordLeft(int n, int n2) {
        return (n &= 65535) << n2 | n >> 16 - n2;
    }

    @Override
    public String getAlgorithmName() {
        return "RC2";
    }

    @Override
    public int getBlockSize() {
        return 8;
    }

    @Override
    public void init(boolean bl, CipherParameters arrby) {
        block4 : {
            block3 : {
                block2 : {
                    this.encrypting = bl;
                    if (!(arrby instanceof RC2Parameters)) break block2;
                    arrby = (RC2Parameters)arrby;
                    this.workingKey = this.generateWorkingKey(arrby.getKey(), arrby.getEffectiveKeyBits());
                    break block3;
                }
                if (!(arrby instanceof KeyParameter)) break block4;
                arrby = ((KeyParameter)arrby).getKey();
                this.workingKey = this.generateWorkingKey(arrby, arrby.length * 8);
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("invalid parameter passed to RC2 init - ");
        stringBuilder.append(arrby.getClass().getName());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public final int processBlock(byte[] arrby, int n, byte[] arrby2, int n2) {
        if (this.workingKey != null) {
            if (n + 8 <= arrby.length) {
                if (n2 + 8 <= arrby2.length) {
                    if (this.encrypting) {
                        this.encryptBlock(arrby, n, arrby2, n2);
                    } else {
                        this.decryptBlock(arrby, n, arrby2, n2);
                    }
                    return 8;
                }
                throw new OutputLengthException("output buffer too short");
            }
            throw new DataLengthException("input buffer too short");
        }
        throw new IllegalStateException("RC2 engine not initialised");
    }

    @Override
    public void reset() {
    }
}

