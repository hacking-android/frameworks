/*
 * Decompiled with CFR 0.145.
 */
package java.util.prefs;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Random;

class Base64 {
    private static final byte[] altBase64ToInt;
    private static final byte[] base64ToInt;
    private static final char[] intToAltBase64;
    private static final char[] intToBase64;

    static {
        intToBase64 = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
        intToAltBase64 = new char[]{'!', '\"', '#', '$', '%', '&', '\'', '(', ')', ',', '-', '.', ':', ';', '<', '>', '@', '[', ']', '^', '`', '_', '{', '|', '}', '~', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '?'};
        base64ToInt = new byte[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51};
        altBase64ToInt = new byte[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, -1, 62, 9, 10, 11, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 12, 13, 14, -1, 15, 63, 16, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 17, -1, 18, 19, 21, 20, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 22, 23, 24, 25};
    }

    Base64() {
    }

    static byte[] altBase64ToByteArray(String string) {
        return Base64.base64ToByteArray(string, true);
    }

    static byte[] base64ToByteArray(String string) {
        return Base64.base64ToByteArray(string, false);
    }

    private static byte[] base64ToByteArray(String string, boolean bl) {
        byte[] arrby = bl ? altBase64ToInt : base64ToInt;
        int n = string.length();
        int n2 = n / 4;
        if (n2 * 4 == n) {
            int n3;
            int n4;
            int n5 = 0;
            int n6 = 0;
            int n7 = n4 = n2;
            if (n != 0) {
                n3 = n4;
                if (string.charAt(n - 1) == '=') {
                    n6 = 0 + 1;
                    n3 = n4 - 1;
                }
                n5 = n6;
                n7 = n3;
                if (string.charAt(n - 2) == '=') {
                    n5 = n6 + 1;
                    n7 = n3;
                }
            }
            byte[] arrby2 = new byte[n2 * 3 - n5];
            n6 = 0;
            n4 = 0;
            n3 = 0;
            while (n3 < n7) {
                n2 = n6 + 1;
                n = Base64.base64toInt(string.charAt(n6), arrby);
                int n8 = n2 + 1;
                n2 = Base64.base64toInt(string.charAt(n2), arrby);
                n6 = n8 + 1;
                int n9 = Base64.base64toInt(string.charAt(n8), arrby);
                n8 = Base64.base64toInt(string.charAt(n6), arrby);
                int n10 = n4 + 1;
                arrby2[n4] = (byte)(n << 2 | n2 >> 4);
                n4 = n10 + 1;
                arrby2[n10] = (byte)(n2 << 4 | n9 >> 2);
                arrby2[n4] = (byte)(n9 << 6 | n8);
                ++n3;
                ++n6;
                ++n4;
            }
            if (n5 != 0) {
                n7 = n6 + 1;
                n6 = Base64.base64toInt(string.charAt(n6), arrby);
                n3 = n7 + 1;
                n7 = Base64.base64toInt(string.charAt(n7), arrby);
                n2 = n4 + 1;
                arrby2[n4] = (byte)(n6 << 2 | n7 >> 4);
                if (n5 == 1) {
                    arrby2[n2] = (byte)(n7 << 4 | Base64.base64toInt(string.charAt(n3), arrby) >> 2);
                }
            }
            return arrby2;
        }
        throw new IllegalArgumentException("String length must be a multiple of four.");
    }

    private static int base64toInt(char c, byte[] object) {
        byte by = object[c];
        if (by >= 0) {
            return by;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Illegal character ");
        ((StringBuilder)object).append(c);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    static String byteArrayToAltBase64(byte[] arrby) {
        return Base64.byteArrayToBase64(arrby, true);
    }

    static String byteArrayToBase64(byte[] arrby) {
        return Base64.byteArrayToBase64(arrby, false);
    }

    private static String byteArrayToBase64(byte[] arrby, boolean bl) {
        int n = arrby.length;
        int n2 = n / 3;
        int n3 = n - n2 * 3;
        StringBuffer stringBuffer = new StringBuffer((n + 2) / 3 * 4);
        char[] arrc = bl ? intToAltBase64 : intToBase64;
        int n4 = 0;
        n = 0;
        while (n < n2) {
            int n5 = n4 + 1;
            int n6 = arrby[n4] & 255;
            n4 = n5 + 1;
            n5 = arrby[n5] & 255;
            int n7 = arrby[n4] & 255;
            stringBuffer.append(arrc[n6 >> 2]);
            stringBuffer.append(arrc[n6 << 4 & 63 | n5 >> 4]);
            stringBuffer.append(arrc[n5 << 2 & 63 | n7 >> 6]);
            stringBuffer.append(arrc[n7 & 63]);
            ++n;
            ++n4;
        }
        if (n3 != 0) {
            n = n4 + 1;
            n4 = arrby[n4] & 255;
            stringBuffer.append(arrc[n4 >> 2]);
            if (n3 == 1) {
                stringBuffer.append(arrc[n4 << 4 & 63]);
                stringBuffer.append("==");
            } else {
                n = arrby[n] & 255;
                stringBuffer.append(arrc[n4 << 4 & 63 | n >> 4]);
                stringBuffer.append(arrc[n << 2 & 63]);
                stringBuffer.append('=');
            }
        }
        return stringBuffer.toString();
    }

    public static void main(String[] object) {
        int n = Integer.parseInt(object[0]);
        int n2 = Integer.parseInt(object[1]);
        object = new Random();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n2; ++j) {
                byte[] arrby = new byte[j];
                for (int k = 0; k < j; ++k) {
                    arrby[k] = (byte)((Random)object).nextInt();
                }
                if (!Arrays.equals(arrby, Base64.base64ToByteArray(Base64.byteArrayToBase64(arrby)))) {
                    System.out.println("Dismal failure!");
                }
                if (Arrays.equals(arrby, Base64.altBase64ToByteArray(Base64.byteArrayToAltBase64(arrby)))) continue;
                System.out.println("Alternate dismal failure!");
            }
        }
    }
}

