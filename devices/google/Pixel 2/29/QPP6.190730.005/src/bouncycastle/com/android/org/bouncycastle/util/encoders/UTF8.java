/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.util.encoders;

public class UTF8 {
    private static final byte C_CR1 = 1;
    private static final byte C_CR2 = 2;
    private static final byte C_CR3 = 3;
    private static final byte C_ILL = 0;
    private static final byte C_L2A = 4;
    private static final byte C_L3A = 5;
    private static final byte C_L3B = 6;
    private static final byte C_L3C = 7;
    private static final byte C_L4A = 8;
    private static final byte C_L4B = 9;
    private static final byte C_L4C = 10;
    private static final byte S_CS1 = 0;
    private static final byte S_CS2 = 16;
    private static final byte S_CS3 = 32;
    private static final byte S_END = -1;
    private static final byte S_ERR = -2;
    private static final byte S_P3A = 48;
    private static final byte S_P3B = 64;
    private static final byte S_P4A = 80;
    private static final byte S_P4B = 96;
    private static final short[] firstUnitTable = new short[128];
    private static final byte[] transitionTable = new byte[112];

    static {
        byte[] arrby = new byte[128];
        UTF8.fill(arrby, 0, 15, (byte)1);
        UTF8.fill(arrby, 16, 31, (byte)2);
        UTF8.fill(arrby, 32, 63, (byte)3);
        UTF8.fill(arrby, 64, 65, (byte)0);
        UTF8.fill(arrby, 66, 95, (byte)4);
        UTF8.fill(arrby, 96, 96, (byte)5);
        UTF8.fill(arrby, 97, 108, (byte)6);
        UTF8.fill(arrby, 109, 109, (byte)7);
        UTF8.fill(arrby, 110, 111, (byte)6);
        UTF8.fill(arrby, 112, 112, (byte)8);
        UTF8.fill(arrby, 113, 115, (byte)9);
        UTF8.fill(arrby, 116, 116, (byte)10);
        UTF8.fill(arrby, 117, 127, (byte)0);
        byte[] arrby2 = transitionTable;
        UTF8.fill(arrby2, 0, arrby2.length - 1, (byte)-2);
        UTF8.fill(transitionTable, 8, 11, (byte)-1);
        UTF8.fill(transitionTable, 24, 27, (byte)0);
        UTF8.fill(transitionTable, 40, 43, (byte)16);
        UTF8.fill(transitionTable, 58, 59, (byte)0);
        UTF8.fill(transitionTable, 72, 73, (byte)0);
        UTF8.fill(transitionTable, 89, 91, (byte)16);
        UTF8.fill(transitionTable, 104, 104, (byte)16);
        for (int i = 0; i < 128; ++i) {
            byte by = arrby[i];
            byte by2 = new byte[]{0, 0, 0, 0, 31, 15, 15, 15, 7, 7, 7}[by];
            by = new byte[]{-2, -2, -2, -2, 0, 48, 16, 64, 80, 32, 96}[by];
            UTF8.firstUnitTable[i] = (short)((by2 & i) << 8 | by);
        }
    }

    private static void fill(byte[] arrby, int n, int n2, byte by) {
        while (n <= n2) {
            arrby[n] = by;
            ++n;
        }
    }

    public static int transcodeToUTF16(byte[] arrby, char[] arrc) {
        int n = 0;
        int n2 = 0;
        while (n < arrby.length) {
            int n3 = n + 1;
            if ((n = arrby[n]) >= 0) {
                if (n2 >= arrc.length) {
                    return -1;
                }
                arrc[n2] = (char)n;
                n = n3;
                ++n2;
                continue;
            }
            n = firstUnitTable[n & 127];
            int n4 = n >>> 8;
            int n5 = n;
            n = n3;
            n3 = n4;
            while (n5 >= 0) {
                if (n >= arrby.length) {
                    return -1;
                }
                n4 = arrby[n];
                n3 = n3 << 6 | n4 & 63;
                n5 = transitionTable[((n4 & 255) >>> 4) + n5];
                ++n;
            }
            if (n5 == -2) {
                return -1;
            }
            if (n3 <= 65535) {
                if (n2 >= arrc.length) {
                    return -1;
                }
                arrc[n2] = (char)n3;
                ++n2;
                continue;
            }
            if (n2 >= arrc.length - 1) {
                return -1;
            }
            n5 = n2 + 1;
            arrc[n2] = (char)((n3 >>> 10) + 55232);
            n2 = n5 + 1;
            arrc[n5] = (char)(56320 | n3 & 1023);
        }
        return n2;
    }
}

