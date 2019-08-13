/*
 * Decompiled with CFR 0.145.
 */
package sun.net.util;

public class IPAddressUtil {
    private static final int INADDR16SZ = 16;
    private static final int INADDR4SZ = 4;
    private static final int INT16SZ = 2;

    public static byte[] convertFromIPv4MappedAddress(byte[] arrby) {
        if (IPAddressUtil.isIPv4MappedAddress(arrby)) {
            byte[] arrby2 = new byte[4];
            System.arraycopy(arrby, 12, arrby2, 0, 4);
            return arrby2;
        }
        return null;
    }

    public static boolean isIPv4LiteralAddress(String string) {
        boolean bl = IPAddressUtil.textToNumericFormatV4(string) != null;
        return bl;
    }

    private static boolean isIPv4MappedAddress(byte[] arrby) {
        if (arrby.length < 16) {
            return false;
        }
        return arrby[0] == 0 && arrby[1] == 0 && arrby[2] == 0 && arrby[3] == 0 && arrby[4] == 0 && arrby[5] == 0 && arrby[6] == 0 && arrby[7] == 0 && arrby[8] == 0 && arrby[9] == 0 && arrby[10] == -1 && arrby[11] == -1;
    }

    public static boolean isIPv6LiteralAddress(String string) {
        boolean bl = IPAddressUtil.textToNumericFormatV6(string) != null;
        return bl;
    }

    public static byte[] textToNumericFormatV4(String string) {
        byte[] arrby = new byte[4];
        long l = 0L;
        int n = 0;
        int n2 = 1;
        int n3 = string.length();
        if (n3 != 0 && n3 <= 15) {
            for (int i = 0; i < n3; ++i) {
                char c = string.charAt(i);
                if (c == '.') {
                    if (n2 == 0 && l >= 0L && l <= 255L && n != 3) {
                        arrby[n] = (byte)(l & 255L);
                        l = 0L;
                        n2 = 1;
                        ++n;
                        continue;
                    }
                    return null;
                }
                n2 = Character.digit(c, 10);
                if (n2 < 0) {
                    return null;
                }
                l = l * 10L + (long)n2;
                n2 = 0;
            }
            if (n2 == 0 && l >= 0L && l < 1L << (4 - n) * 8) {
                if (n != 0 && n != 1 && n != 2) {
                    if (n == 3) {
                        arrby[3] = (byte)(l >> 0 & 255L);
                    }
                    return arrby;
                }
                return null;
            }
            return null;
        }
        return null;
    }

    public static byte[] textToNumericFormatV6(String arrobject) {
        int n;
        int n2;
        int n3;
        byte[] arrby;
        int n4;
        int n5;
        block25 : {
            if (arrobject.length() < 2) {
                return null;
            }
            char[] arrc = arrobject.toCharArray();
            arrby = new byte[16];
            int n6 = arrc.length;
            n2 = arrobject.indexOf("%");
            if (n2 == n6 - 1) {
                return null;
            }
            if (n2 != -1) {
                n6 = n2;
            }
            n = -1;
            int n7 = 0;
            n2 = 0;
            if (arrc[0] == ':') {
                n7 = n5 = 0 + 1;
                if (arrc[n5] != ':') {
                    return null;
                }
            }
            int n8 = n7;
            n5 = 0;
            n4 = 0;
            while (n7 < n6) {
                n3 = n7 + 1;
                char c = arrc[n7];
                n7 = Character.digit(c, 16);
                if (n7 != -1) {
                    if ((n4 = n4 << 4 | n7) > 65535) {
                        return null;
                    }
                    n5 = 1;
                    n7 = n3;
                    continue;
                }
                if (c == ':') {
                    n8 = n3;
                    if (n5 == 0) {
                        if (n != -1) {
                            return null;
                        }
                        n = n2;
                        n7 = n3;
                        continue;
                    }
                    if (n3 == n6) {
                        return null;
                    }
                    if (n2 + 2 > 16) {
                        return null;
                    }
                    n5 = n2 + 1;
                    arrby[n2] = (byte)(n4 >> 8 & 255);
                    n2 = n5 + 1;
                    arrby[n5] = (byte)(n4 & 255);
                    n5 = 0;
                    n4 = 0;
                    n7 = n3;
                    continue;
                }
                if (c == '.' && n2 + 4 <= 16) {
                    String string = arrobject.substring(n8, n6);
                    n5 = 0;
                    n3 = 0;
                    arrobject = arrc;
                    while ((n3 = string.indexOf(46, n3)) != -1) {
                        ++n5;
                        ++n3;
                    }
                    if (n5 != 3) {
                        return null;
                    }
                    arrobject = IPAddressUtil.textToNumericFormatV4(string);
                    if (arrobject == null) {
                        return null;
                    }
                    n3 = 0;
                    while (n3 < 4) {
                        arrby[n2] = arrobject[n3];
                        ++n3;
                        ++n2;
                    }
                    n3 = 0;
                    break block25;
                }
                return null;
            }
            n3 = n5;
        }
        n5 = n2;
        if (n3 != 0) {
            if (n2 + 2 > 16) {
                return null;
            }
            n3 = n2 + 1;
            arrby[n2] = (byte)(n4 >> 8 & 255);
            n5 = n3 + 1;
            arrby[n3] = (byte)(n4 & 255);
        }
        n2 = n5;
        if (n != -1) {
            n3 = n5 - n;
            if (n5 == 16) {
                return null;
            }
            for (n2 = 1; n2 <= n3; ++n2) {
                arrby[16 - n2] = arrby[n + n3 - n2];
                arrby[n + n3 - n2] = (byte)(false ? 1 : 0);
            }
            n2 = 16;
        }
        if (n2 != 16) {
            return null;
        }
        arrobject = IPAddressUtil.convertFromIPv4MappedAddress(arrby);
        if (arrobject != null) {
            return arrobject;
        }
        return arrby;
    }
}

