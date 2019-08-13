/*
 * Decompiled with CFR 0.145.
 */
package libcore.util;

public class HexEncoding {
    private static final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();

    private HexEncoding() {
    }

    public static byte[] decode(String string) throws IllegalArgumentException {
        return HexEncoding.decode(string.toCharArray());
    }

    public static byte[] decode(String string, boolean bl) throws IllegalArgumentException {
        return HexEncoding.decode(string.toCharArray(), bl);
    }

    public static byte[] decode(char[] arrc) throws IllegalArgumentException {
        return HexEncoding.decode(arrc, false);
    }

    public static byte[] decode(char[] arrc, boolean bl) throws IllegalArgumentException {
        Object object;
        block8 : {
            int n;
            int n2;
            block7 : {
                block6 : {
                    object = new byte[(arrc.length + 1) / 2];
                    n2 = 0;
                    n = 0;
                    if (!bl) break block6;
                    if (arrc.length % 2 != 0) {
                        object[0] = (byte)HexEncoding.toDigit(arrc, 0);
                        n = 0 + 1;
                        n2 = 0 + 1;
                    }
                    break block7;
                }
                if (arrc.length % 2 != 0) break block8;
            }
            int n3 = arrc.length;
            while (n < n3) {
                object[n2] = (byte)(HexEncoding.toDigit(arrc, n) << 4 | HexEncoding.toDigit(arrc, n + 1));
                n += 2;
                ++n2;
            }
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Invalid input length: ");
        ((StringBuilder)object).append(arrc.length);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static char[] encode(byte[] arrby) {
        return HexEncoding.encode(arrby, 0, arrby.length);
    }

    public static char[] encode(byte[] arrby, int n, int n2) {
        char[] arrc = new char[n2 * 2];
        for (int i = 0; i < n2; ++i) {
            byte by = arrby[n + i];
            int n3 = i * 2;
            char[] arrc2 = HEX_DIGITS;
            arrc[n3] = arrc2[by >>> 4 & 15];
            arrc[n3 + 1] = arrc2[by & 15];
        }
        return arrc;
    }

    public static String encodeToString(byte[] arrby) {
        return new String(HexEncoding.encode(arrby));
    }

    private static int toDigit(char[] arrc, int n) throws IllegalArgumentException {
        char c = arrc[n];
        if ('0' <= c && c <= '9') {
            return c - 48;
        }
        if ('a' <= c && c <= 'f') {
            return c - 97 + 10;
        }
        if ('A' <= c && c <= 'F') {
            return c - 65 + 10;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Illegal char: ");
        stringBuilder.append(arrc[n]);
        stringBuilder.append(" at offset ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }
}

