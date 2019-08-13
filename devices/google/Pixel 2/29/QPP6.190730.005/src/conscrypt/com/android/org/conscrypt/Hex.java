/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

public final class Hex {
    private static final char[] DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private Hex() {
    }

    public static String bytesToHexString(byte[] arrby) {
        char[] arrc = new char[arrby.length * 2];
        int n = 0;
        for (byte by : arrby) {
            int n2 = n + 1;
            char[] arrc2 = DIGITS;
            arrc[n] = arrc2[by >> 4 & 15];
            n = n2 + 1;
            arrc[n2] = arrc2[by & 15];
        }
        return new String(arrc);
    }

    public static String intToHexString(int n, int n2) {
        int n3;
        char[] arrc = new char[8];
        int n4 = 8;
        do {
            arrc[--n4] = DIGITS[n & 15];
            n = n3 = n >>> 4;
        } while (n3 != 0 || 8 - n4 < n2);
        return new String(arrc, n4, 8 - n4);
    }
}

