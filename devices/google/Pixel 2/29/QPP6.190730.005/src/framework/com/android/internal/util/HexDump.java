/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.annotation.UnsupportedAppUsage;

public class HexDump {
    private static final char[] HEX_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final char[] HEX_LOWER_CASE_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static StringBuilder appendByteAsHex(StringBuilder stringBuilder, byte by, boolean bl) {
        char[] arrc = bl ? HEX_DIGITS : HEX_LOWER_CASE_DIGITS;
        stringBuilder.append(arrc[by >> 4 & 15]);
        stringBuilder.append(arrc[by & 15]);
        return stringBuilder;
    }

    public static String dumpHexString(byte[] arrby) {
        if (arrby == null) {
            return "(null)";
        }
        return HexDump.dumpHexString(arrby, 0, arrby.length);
    }

    public static String dumpHexString(byte[] arrby, int n, int n2) {
        if (arrby == null) {
            return "(null)";
        }
        StringBuilder stringBuilder = new StringBuilder();
        byte[] arrby2 = new byte[16];
        int n3 = 0;
        stringBuilder.append("\n0x");
        stringBuilder.append(HexDump.toHexString(n));
        for (int i = n; i < n + n2; ++i) {
            int n4 = n3;
            if (n3 == 16) {
                stringBuilder.append(" ");
                for (n3 = 0; n3 < 16; ++n3) {
                    if (arrby2[n3] > 32 && arrby2[n3] < 126) {
                        stringBuilder.append(new String(arrby2, n3, 1));
                        continue;
                    }
                    stringBuilder.append(".");
                }
                stringBuilder.append("\n0x");
                stringBuilder.append(HexDump.toHexString(i));
                n4 = 0;
            }
            n3 = arrby[i];
            stringBuilder.append(" ");
            stringBuilder.append(HEX_DIGITS[n3 >>> 4 & 15]);
            stringBuilder.append(HEX_DIGITS[n3 & 15]);
            arrby2[n4] = (byte)n3;
            n3 = n4 + 1;
        }
        if (n3 != 16) {
            for (n = 0; n < (16 - n3) * 3 + 1; ++n) {
                stringBuilder.append(" ");
            }
            for (n = 0; n < n3; ++n) {
                if (arrby2[n] > 32 && arrby2[n] < 126) {
                    stringBuilder.append(new String(arrby2, n, 1));
                    continue;
                }
                stringBuilder.append(".");
            }
        }
        return stringBuilder.toString();
    }

    @UnsupportedAppUsage
    public static byte[] hexStringToByteArray(String string2) {
        int n = string2.length();
        byte[] arrby = new byte[n / 2];
        for (int i = 0; i < n; i += 2) {
            arrby[i / 2] = (byte)(HexDump.toByte(string2.charAt(i)) << 4 | HexDump.toByte(string2.charAt(i + 1)));
        }
        return arrby;
    }

    private static int toByte(char c) {
        if (c >= '0' && c <= '9') {
            return c - 48;
        }
        if (c >= 'A' && c <= 'F') {
            return c - 65 + 10;
        }
        if (c >= 'a' && c <= 'f') {
            return c - 97 + 10;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid hex char '");
        stringBuilder.append(c);
        stringBuilder.append("'");
        throw new RuntimeException(stringBuilder.toString());
    }

    public static byte[] toByteArray(byte by) {
        return new byte[]{by};
    }

    public static byte[] toByteArray(int n) {
        byte by = (byte)(n & 255);
        byte by2 = (byte)(n >> 8 & 255);
        byte by3 = (byte)(n >> 16 & 255);
        return new byte[]{(byte)(n >> 24 & 255), by3, by2, by};
    }

    public static String toHexString(byte by) {
        return HexDump.toHexString(HexDump.toByteArray(by));
    }

    @UnsupportedAppUsage
    public static String toHexString(int n) {
        return HexDump.toHexString(HexDump.toByteArray(n));
    }

    @UnsupportedAppUsage
    public static String toHexString(byte[] arrby) {
        return HexDump.toHexString(arrby, 0, arrby.length, true);
    }

    @UnsupportedAppUsage
    public static String toHexString(byte[] arrby, int n, int n2) {
        return HexDump.toHexString(arrby, n, n2, true);
    }

    public static String toHexString(byte[] arrby, int n, int n2, boolean bl) {
        char[] arrc = bl ? HEX_DIGITS : HEX_LOWER_CASE_DIGITS;
        char[] arrc2 = new char[n2 * 2];
        int n3 = 0;
        for (int i = n; i < n + n2; ++i) {
            byte by = arrby[i];
            int n4 = n3 + 1;
            arrc2[n3] = arrc[by >>> 4 & 15];
            n3 = n4 + 1;
            arrc2[n4] = arrc[by & 15];
        }
        return new String(arrc2);
    }

    @UnsupportedAppUsage
    public static String toHexString(byte[] arrby, boolean bl) {
        return HexDump.toHexString(arrby, 0, arrby.length, bl);
    }
}

