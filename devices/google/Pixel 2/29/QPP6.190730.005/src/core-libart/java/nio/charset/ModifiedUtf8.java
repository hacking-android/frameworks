/*
 * Decompiled with CFR 0.145.
 */
package java.nio.charset;

import java.io.UTFDataFormatException;

public class ModifiedUtf8 {
    public static long countBytes(String string, boolean bl) throws UTFDataFormatException {
        long l = 0L;
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            if (c < '') {
                long l2;
                l = l2 = l + 1L;
                if (c != '\u0000') continue;
                l = l2 + 1L;
                continue;
            }
            if (c < '\u0800') {
                l += 2L;
                continue;
            }
            l += 3L;
        }
        if (bl && l > 65535L) {
            throw new UTFDataFormatException("Size of the encoded string doesn't fit in two bytes");
        }
        return l;
    }

    public static String decode(byte[] object, char[] arrc, int n, int n2) throws UTFDataFormatException {
        if (n >= 0 && n2 >= 0) {
            int n3 = 0;
            int n4 = n + n2;
            n2 = n;
            n = n3;
            while (n2 < n4) {
                n3 = object[n2] & 255;
                ++n2;
                if (n3 < 128) {
                    arrc[n] = (char)n3;
                    ++n;
                    continue;
                }
                if (192 <= n3 && n3 < 224) {
                    if (n2 != n4) {
                        if ((object[n2] & 192) == 128) {
                            arrc[n] = (char)(object[n2] & 63 | (n3 & 31) << 6);
                            ++n2;
                            ++n;
                            continue;
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("bad second byte at ");
                        ((StringBuilder)object).append(n2);
                        throw new UTFDataFormatException(((StringBuilder)object).toString());
                    }
                    throw new UTFDataFormatException("unexpected end of input");
                }
                if (n3 < 240) {
                    if (n2 + 1 < n4) {
                        if ((object[n2] & 192) == 128) {
                            Object object2 = object[n2];
                            if ((object[++n2] & 192) == 128) {
                                arrc[n] = (char)(object[n2] & 63 | ((n3 & 31) << 12 | (object2 & 63) << 6));
                                ++n2;
                                ++n;
                                continue;
                            }
                            object = new StringBuilder();
                            ((StringBuilder)object).append("bad third byte at ");
                            ((StringBuilder)object).append(n2);
                            throw new UTFDataFormatException(((StringBuilder)object).toString());
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("bad second byte at ");
                        ((StringBuilder)object).append(n2);
                        throw new UTFDataFormatException(((StringBuilder)object).toString());
                    }
                    throw new UTFDataFormatException("unexpected end of input");
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Invalid UTF8 byte ");
                ((StringBuilder)object).append(n3);
                ((StringBuilder)object).append(" at position ");
                ((StringBuilder)object).append(n2 - 1);
                throw new UTFDataFormatException(((StringBuilder)object).toString());
            }
            return String.valueOf(arrc, 0, n);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Illegal arguments: offset ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(". Length: ");
        ((StringBuilder)object).append(n2);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static void encode(byte[] arrby, int n, String string) {
        int n2 = string.length();
        for (int i = 0; i < n2; ++i) {
            int n3;
            int n4 = string.charAt(i);
            if (n4 < 128) {
                if (n4 == 0) {
                    n4 = n + 1;
                    arrby[n] = (byte)-64;
                    n = n4 + 1;
                    arrby[n4] = (byte)-128;
                    continue;
                }
                arrby[n] = (byte)n4;
                ++n;
                continue;
            }
            if (n4 < 2048) {
                n3 = n + 1;
                arrby[n] = (byte)(n4 >>> 6 | 192);
                n = n3 + 1;
                arrby[n3] = (byte)(128 | n4 & 63);
                continue;
            }
            n3 = n + 1;
            arrby[n] = (byte)(n4 >>> 12 | 224);
            n = n3 + 1;
            arrby[n3] = (byte)(n4 >>> 6 & 63 | 128);
            arrby[n] = (byte)(128 | n4 & 63);
            ++n;
        }
    }

    public static byte[] encode(String string) throws UTFDataFormatException {
        long l = ModifiedUtf8.countBytes(string, true);
        byte[] arrby = new byte[(int)l + 2];
        ModifiedUtf8.encode(arrby, 2, string);
        arrby[0] = (byte)(l >>> 8);
        arrby[1] = (byte)l;
        return arrby;
    }
}

