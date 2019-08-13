/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer.utils;

import java.util.Arrays;
import org.apache.xml.serializer.utils.XMLChar;

public class XML11Char {
    public static final int MASK_XML11_CONTENT = 32;
    public static final int MASK_XML11_CONTENT_INTERNAL = 48;
    public static final int MASK_XML11_CONTROL = 16;
    public static final int MASK_XML11_NAME = 8;
    public static final int MASK_XML11_NAME_START = 4;
    public static final int MASK_XML11_NCNAME = 128;
    public static final int MASK_XML11_NCNAME_START = 64;
    public static final int MASK_XML11_SPACE = 2;
    public static final int MASK_XML11_VALID = 1;
    private static final byte[] XML11CHARS = new byte[65536];

    static {
        Arrays.fill(XML11CHARS, 1, 9, (byte)17);
        byte[] arrby = XML11CHARS;
        arrby[9] = (byte)35;
        arrby[10] = (byte)3;
        Arrays.fill(arrby, 11, 13, (byte)17);
        arrby = XML11CHARS;
        arrby[13] = (byte)3;
        Arrays.fill(arrby, 14, 32, (byte)17);
        arrby = XML11CHARS;
        arrby[32] = (byte)35;
        Arrays.fill(arrby, 33, 38, (byte)33);
        arrby = XML11CHARS;
        arrby[38] = (byte)(true ? 1 : 0);
        Arrays.fill(arrby, 39, 45, (byte)33);
        Arrays.fill(XML11CHARS, 45, 47, (byte)-87);
        arrby = XML11CHARS;
        arrby[47] = (byte)33;
        Arrays.fill(arrby, 48, 58, (byte)-87);
        arrby = XML11CHARS;
        arrby[58] = (byte)45;
        arrby[59] = (byte)33;
        arrby[60] = (byte)(true ? 1 : 0);
        Arrays.fill(arrby, 61, 65, (byte)33);
        Arrays.fill(XML11CHARS, 65, 91, (byte)-19);
        Arrays.fill(XML11CHARS, 91, 93, (byte)33);
        arrby = XML11CHARS;
        arrby[93] = (byte)(true ? 1 : 0);
        arrby[94] = (byte)33;
        arrby[95] = (byte)-19;
        arrby[96] = (byte)33;
        Arrays.fill(arrby, 97, 123, (byte)-19);
        Arrays.fill(XML11CHARS, 123, 127, (byte)33);
        Arrays.fill(XML11CHARS, 127, 133, (byte)17);
        arrby = XML11CHARS;
        arrby[133] = (byte)35;
        Arrays.fill(arrby, 134, 160, (byte)17);
        Arrays.fill(XML11CHARS, 160, 183, (byte)33);
        arrby = XML11CHARS;
        arrby[183] = (byte)-87;
        Arrays.fill(arrby, 184, 192, (byte)33);
        Arrays.fill(XML11CHARS, 192, 215, (byte)-19);
        arrby = XML11CHARS;
        arrby[215] = (byte)33;
        Arrays.fill(arrby, 216, 247, (byte)-19);
        arrby = XML11CHARS;
        arrby[247] = (byte)33;
        Arrays.fill(arrby, 248, 768, (byte)-19);
        Arrays.fill(XML11CHARS, 768, 880, (byte)-87);
        Arrays.fill(XML11CHARS, 880, 894, (byte)-19);
        arrby = XML11CHARS;
        arrby[894] = (byte)33;
        Arrays.fill(arrby, 895, 8192, (byte)-19);
        Arrays.fill(XML11CHARS, 8192, 8204, (byte)33);
        Arrays.fill(XML11CHARS, 8204, 8206, (byte)-19);
        Arrays.fill(XML11CHARS, 8206, 8232, (byte)33);
        arrby = XML11CHARS;
        arrby[8232] = (byte)35;
        Arrays.fill(arrby, 8233, 8255, (byte)33);
        Arrays.fill(XML11CHARS, 8255, 8257, (byte)-87);
        Arrays.fill(XML11CHARS, 8257, 8304, (byte)33);
        Arrays.fill(XML11CHARS, 8304, 8592, (byte)-19);
        Arrays.fill(XML11CHARS, 8592, 11264, (byte)33);
        Arrays.fill(XML11CHARS, 11264, 12272, (byte)-19);
        Arrays.fill(XML11CHARS, 12272, 12289, (byte)33);
        Arrays.fill(XML11CHARS, 12289, 55296, (byte)-19);
        Arrays.fill(XML11CHARS, 57344, 63744, (byte)33);
        Arrays.fill(XML11CHARS, 63744, 64976, (byte)-19);
        Arrays.fill(XML11CHARS, 64976, 65008, (byte)33);
        Arrays.fill(XML11CHARS, 65008, 65534, (byte)-19);
    }

    public static boolean isXML11Content(int n) {
        boolean bl = n < 65536 && (XML11CHARS[n] & 32) != 0 || 65536 <= n && n <= 1114111;
        return bl;
    }

    public static boolean isXML11InternalEntityContent(int n) {
        boolean bl = n < 65536 && (XML11CHARS[n] & 48) != 0 || 65536 <= n && n <= 1114111;
        return bl;
    }

    public static boolean isXML11Invalid(int n) {
        return XML11Char.isXML11Valid(n) ^ true;
    }

    public static boolean isXML11NCName(int n) {
        boolean bl = n < 65536 && (XML11CHARS[n] & 128) != 0 || 65536 <= n && n < 983040;
        return bl;
    }

    public static boolean isXML11NCNameStart(int n) {
        boolean bl = n < 65536 && (XML11CHARS[n] & 64) != 0 || 65536 <= n && n < 983040;
        return bl;
    }

    public static boolean isXML11Name(int n) {
        boolean bl = n < 65536 && (XML11CHARS[n] & 8) != 0 || n >= 65536 && n < 983040;
        return bl;
    }

    public static boolean isXML11NameHighSurrogate(int n) {
        boolean bl = 55296 <= n && n <= 56191;
        return bl;
    }

    public static boolean isXML11NameStart(int n) {
        boolean bl = n < 65536 && (XML11CHARS[n] & 4) != 0 || 65536 <= n && n < 983040;
        return bl;
    }

    public static boolean isXML11Space(int n) {
        boolean bl = n < 65536 && (XML11CHARS[n] & 2) != 0;
        return bl;
    }

    public static boolean isXML11Valid(int n) {
        boolean bl;
        block3 : {
            boolean bl2;
            block2 : {
                bl2 = true;
                if (n >= 65536) break block2;
                bl = bl2;
                if ((XML11CHARS[n] & 1) != 0) break block3;
            }
            bl = 65536 <= n && n <= 1114111 ? bl2 : false;
        }
        return bl;
    }

    public static boolean isXML11ValidLiteral(int n) {
        byte[] arrby;
        boolean bl = true;
        if (!(n < 65536 && ((arrby = XML11CHARS)[n] & 1) != 0 && (arrby[n] & 16) == 0 || 65536 <= n && n <= 1114111)) {
            bl = false;
        }
        return bl;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean isXML11ValidNCName(String string) {
        char c;
        int n = string.length();
        if (n == 0) {
            return false;
        }
        int n2 = 1;
        char c2 = string.charAt(0);
        if (!XML11Char.isXML11NCNameStart(c2)) {
            if (n <= 1 || !XML11Char.isXML11NameHighSurrogate(c2)) return false;
            c = string.charAt(1);
            if (!XMLChar.isLowSurrogate(c) || !XML11Char.isXML11NCNameStart(XMLChar.supplemental(c2, c))) return false;
            n2 = 2;
        }
        while (n2 < n) {
            c2 = string.charAt(n2);
            int n3 = n2;
            if (!XML11Char.isXML11NCName(c2)) {
                n3 = n2 + 1;
                if (n3 >= n || !XML11Char.isXML11NameHighSurrogate(c2)) return false;
                c = string.charAt(n3);
                if (!XMLChar.isLowSurrogate(c) || !XML11Char.isXML11NCName(XMLChar.supplemental(c2, c))) {
                    return false;
                }
            }
            n2 = n3 + 1;
        }
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean isXML11ValidName(String string) {
        char c;
        int n = string.length();
        if (n == 0) {
            return false;
        }
        int n2 = 1;
        char c2 = string.charAt(0);
        if (!XML11Char.isXML11NameStart(c2)) {
            if (n <= 1 || !XML11Char.isXML11NameHighSurrogate(c2)) return false;
            c = string.charAt(1);
            if (!XMLChar.isLowSurrogate(c) || !XML11Char.isXML11NameStart(XMLChar.supplemental(c2, c))) return false;
            n2 = 2;
        }
        while (n2 < n) {
            c2 = string.charAt(n2);
            int n3 = n2;
            if (!XML11Char.isXML11Name(c2)) {
                n3 = n2 + 1;
                if (n3 >= n || !XML11Char.isXML11NameHighSurrogate(c2)) return false;
                c = string.charAt(n3);
                if (!XMLChar.isLowSurrogate(c) || !XML11Char.isXML11Name(XMLChar.supplemental(c2, c))) {
                    return false;
                }
            }
            n2 = n3 + 1;
        }
        return true;
    }

    public static boolean isXML11ValidNmtoken(String string) {
        int n = string.length();
        if (n == 0) {
            return false;
        }
        int n2 = 0;
        while (n2 < n) {
            char c = string.charAt(n2);
            int n3 = n2;
            if (!XML11Char.isXML11Name(c)) {
                n3 = n2 + 1;
                if (n3 < n && XML11Char.isXML11NameHighSurrogate(c)) {
                    char c2 = string.charAt(n3);
                    if (!XMLChar.isLowSurrogate(c2) || !XML11Char.isXML11Name(XMLChar.supplemental(c, c2))) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            n2 = n3 + 1;
        }
        return true;
    }
}

