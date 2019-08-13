/*
 * Decompiled with CFR 0.145.
 */
package com.google.android.mms.pdu;

public class Base64 {
    static final int BASELENGTH = 255;
    static final int FOURBYTE = 4;
    static final byte PAD = 61;
    private static byte[] base64Alphabet;

    static {
        int n;
        base64Alphabet = new byte[255];
        for (n = 0; n < 255; ++n) {
            Base64.base64Alphabet[n] = (byte)-1;
        }
        for (n = 90; n >= 65; --n) {
            Base64.base64Alphabet[n] = (byte)(n - 65);
        }
        for (n = 122; n >= 97; --n) {
            Base64.base64Alphabet[n] = (byte)(n - 97 + 26);
        }
        for (n = 57; n >= 48; --n) {
            Base64.base64Alphabet[n] = (byte)(n - 48 + 52);
        }
        byte[] arrby = base64Alphabet;
        arrby[43] = (byte)62;
        arrby[47] = (byte)63;
    }

    public static byte[] decodeBase64(byte[] arrby) {
        int n;
        if ((arrby = Base64.discardNonBase64(arrby)).length == 0) {
            return new byte[0];
        }
        int n2 = arrby.length / 4;
        int n3 = 0;
        int n4 = arrby.length;
        while (arrby[n4 - 1] == 61) {
            n4 = n = n4 - 1;
            if (n != 0) continue;
            return new byte[0];
        }
        byte[] arrby2 = new byte[n4 - n2];
        for (n4 = 0; n4 < n2; ++n4) {
            int n5 = n4 * 4;
            byte by = arrby[n5 + 2];
            byte by2 = arrby[n5 + 3];
            byte[] arrby3 = base64Alphabet;
            n = arrby3[arrby[n5]];
            n5 = arrby3[arrby[n5 + 1]];
            if (by != 61 && by2 != 61) {
                by = arrby3[by];
                by2 = arrby3[by2];
                arrby2[n3] = (byte)(n << 2 | n5 >> 4);
                arrby2[n3 + 1] = (byte)((n5 & 15) << 4 | by >> 2 & 15);
                arrby2[n3 + 2] = (byte)(by << 6 | by2);
            } else if (by == 61) {
                arrby2[n3] = (byte)(n << 2 | n5 >> 4);
            } else if (by2 == 61) {
                by2 = base64Alphabet[by];
                arrby2[n3] = (byte)(n << 2 | n5 >> 4);
                arrby2[n3 + 1] = (byte)((n5 & 15) << 4 | by2 >> 2 & 15);
            }
            n3 += 3;
        }
        return arrby2;
    }

    static byte[] discardNonBase64(byte[] arrby) {
        byte[] arrby2 = new byte[arrby.length];
        int n = 0;
        for (int i = 0; i < arrby.length; ++i) {
            int n2 = n;
            if (Base64.isBase64(arrby[i])) {
                arrby2[n] = arrby[i];
                n2 = n + 1;
            }
            n = n2;
        }
        arrby = new byte[n];
        System.arraycopy(arrby2, 0, arrby, 0, n);
        return arrby;
    }

    private static boolean isBase64(byte by) {
        if (by == 61) {
            return true;
        }
        return base64Alphabet[by] != -1;
    }
}

