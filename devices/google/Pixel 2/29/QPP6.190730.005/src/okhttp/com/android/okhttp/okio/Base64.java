/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.okio;

import java.io.UnsupportedEncodingException;

final class Base64 {
    private static final byte[] MAP = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    private static final byte[] URL_MAP = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};

    private Base64() {
    }

    public static byte[] decode(String arrby) {
        int n;
        int n2;
        int n3;
        for (n = arrby.length(); n > 0 && ((n2 = arrby.charAt(n - 1)) == 61 || n2 == 10 || n2 == 13 || n2 == 32 || n2 == 9); --n) {
        }
        byte[] arrby2 = new byte[(int)((long)n * 6L / 8L)];
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        for (n3 = 0; n3 < n; ++n3) {
            int n7;
            int n8;
            block14 : {
                block9 : {
                    block12 : {
                        block13 : {
                            block15 : {
                                char c;
                                block11 : {
                                    block10 : {
                                        block8 : {
                                            c = arrby.charAt(n3);
                                            if (c < 'A' || c > 'Z') break block8;
                                            n2 = c - 65;
                                            break block9;
                                        }
                                        if (c < 'a' || c > 'z') break block10;
                                        n2 = c - 71;
                                        break block9;
                                    }
                                    if (c < '0' || c > '9') break block11;
                                    n2 = c + 4;
                                    break block9;
                                }
                                if (c == '+' || c == '-') break block12;
                                if (c == '/' || c == '_') break block13;
                                n8 = n4;
                                n2 = n5;
                                n7 = n6;
                                if (c == '\n') break block14;
                                n8 = n4;
                                n2 = n5;
                                n7 = n6;
                                if (c == '\r') break block14;
                                n8 = n4;
                                n2 = n5;
                                n7 = n6;
                                if (c == ' ') break block14;
                                if (c != '\t') break block15;
                                n8 = n4;
                                n2 = n5;
                                n7 = n6;
                                break block14;
                            }
                            return null;
                        }
                        n2 = 63;
                        break block9;
                    }
                    n2 = 62;
                }
                n6 = n6 << 6 | (byte)n2;
                n8 = n4;
                n2 = ++n5;
                n7 = n6;
                if (n5 % 4 == 0) {
                    n2 = n4 + 1;
                    arrby2[n4] = (byte)(n6 >> 16);
                    n4 = n2 + 1;
                    arrby2[n2] = (byte)(n6 >> 8);
                    arrby2[n4] = (byte)n6;
                    n8 = n4 + 1;
                    n7 = n6;
                    n2 = n5;
                }
            }
            n4 = n8;
            n5 = n2;
            n6 = n7;
        }
        n = n5 % 4;
        if (n == 1) {
            return null;
        }
        if (n == 2) {
            arrby2[n4] = (byte)(n6 << 12 >> 16);
            n2 = n4 + 1;
        } else {
            n2 = n4;
            if (n == 3) {
                n3 = n6 << 6;
                n = n4 + 1;
                arrby2[n4] = (byte)(n3 >> 16);
                n2 = n + 1;
                arrby2[n] = (byte)(n3 >> 8);
            }
        }
        if (n2 == arrby2.length) {
            return arrby2;
        }
        arrby = new byte[n2];
        System.arraycopy((byte[])arrby2, (int)0, (byte[])arrby, (int)0, (int)n2);
        return arrby;
    }

    public static String encode(byte[] arrby) {
        return Base64.encode(arrby, MAP);
    }

    private static String encode(byte[] object, byte[] arrby) {
        int n;
        byte[] arrby2 = new byte[(((byte[])object).length + 2) * 4 / 3];
        int n2 = 0;
        int n3 = ((byte[])object).length - ((byte[])object).length % 3;
        for (n = 0; n < n3; n += 3) {
            int n4 = n2 + 1;
            arrby2[n2] = arrby[(object[n] & 255) >> 2];
            n2 = n4 + 1;
            arrby2[n4] = arrby[(object[n] & 3) << 4 | (object[n + 1] & 255) >> 4];
            n4 = n2 + 1;
            arrby2[n2] = arrby[(object[n + 1] & 15) << 2 | (object[n + 2] & 255) >> 6];
            n2 = n4 + 1;
            arrby2[n4] = arrby[object[n + 2] & 63];
        }
        n = ((byte[])object).length % 3;
        if (n != 1) {
            if (n == 2) {
                n = n2 + 1;
                arrby2[n2] = arrby[(object[n3] & 255) >> 2];
                n2 = n + 1;
                arrby2[n] = arrby[(object[n3] & 3) << 4 | (object[n3 + 1] & 255) >> 4];
                n = n2 + 1;
                arrby2[n2] = arrby[(object[n3 + 1] & 15) << 2];
                n2 = n + 1;
                arrby2[n] = (byte)61;
            }
        } else {
            n = n2 + 1;
            arrby2[n2] = arrby[(object[n3] & 255) >> 2];
            n2 = n + 1;
            arrby2[n] = arrby[(object[n3] & 3) << 4];
            n = n2 + 1;
            arrby2[n2] = (byte)61;
            arrby2[n] = (byte)61;
            n2 = n + 1;
        }
        try {
            object = new String(arrby2, 0, n2, "US-ASCII");
            return object;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new AssertionError(unsupportedEncodingException);
        }
    }

    public static String encodeUrl(byte[] arrby) {
        return Base64.encode(arrby, URL_MAP);
    }
}

