/*
 * Decompiled with CFR 0.145.
 */
package android.util;

public final class ByteStringUtils {
    private static final char[] HEX_LOWERCASE_ARRAY = "0123456789abcdef".toCharArray();
    private static final char[] HEX_UPPERCASE_ARRAY = "0123456789ABCDEF".toCharArray();

    private ByteStringUtils() {
    }

    public static byte[] fromHexToByteArray(String arrc) {
        if (arrc != null && arrc.length() != 0 && arrc.length() % 2 == 0) {
            arrc = arrc.toCharArray();
            byte[] arrby = new byte[arrc.length / 2];
            for (int i = 0; i < arrby.length; ++i) {
                arrby[i] = (byte)(ByteStringUtils.getIndex(arrc[i * 2]) << 4 & 240 | ByteStringUtils.getIndex(arrc[i * 2 + 1]) & 15);
            }
            return arrby;
        }
        return null;
    }

    private static int getIndex(char c) {
        char[] arrc;
        for (int i = 0; i < (arrc = HEX_UPPERCASE_ARRAY).length; ++i) {
            if (arrc[i] != c && HEX_LOWERCASE_ARRAY[i] != c) {
                continue;
            }
            return i;
        }
        return -1;
    }

    public static String toHexString(byte[] arrby) {
        if (arrby != null && arrby.length != 0 && arrby.length % 2 == 0) {
            int n = arrby.length;
            char[] arrc = new char[n * 2];
            for (int i = 0; i < n; ++i) {
                int n2 = arrby[i] & 255;
                char[] arrc2 = HEX_UPPERCASE_ARRAY;
                arrc[i * 2] = arrc2[n2 >>> 4];
                arrc[i * 2 + 1] = arrc2[n2 & 15];
            }
            return new String(arrc);
        }
        return null;
    }
}

